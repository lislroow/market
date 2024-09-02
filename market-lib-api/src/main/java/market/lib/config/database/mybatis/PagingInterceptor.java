package market.lib.config.database.mybatis;

import java.util.List;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Intercepts({
    @Signature(type = Executor.class, method = "query", 
        args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }
    ),
})
public class PagingInterceptor implements Interceptor {

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }
  
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    Object[] args = invocation.getArgs();
    MappedStatement ms = (MappedStatement) args[0];
    Object parameter = args[1];
    ResultHandler<?> resultHandler = (ResultHandler<?>) args[3];
    Executor executor = (Executor) invocation.getTarget();
    
    log.info("sqlid: {}", ms.getId());
    if (parameter instanceof Pageable &&
        SqlCommandType.SELECT == ms.getSqlCommandType()) {
      PagedList<Object> pagedList = new PagedList<>();
      executor.query(ms, parameter,
          new RowBounds(0, RowBounds.NO_ROW_LIMIT),
          new ResultHandler<>() {
            @Override
            public void handleResult(ResultContext<?> resultContext) {
              pagedList.setTotal(resultContext.getResultCount());
            }
          });
      int page = ((Pageable<?>) parameter).getPage();
      int pageSize = ((Pageable<?>) parameter).getPageSize();
      int offset = (page - 1) * pageSize;
      int limit = pageSize;
      int start = offset + 1;
      int end = offset + limit;
      
      List<Object> result = executor.query(ms, parameter,
          new RowBounds(offset, limit),
          resultHandler);
      
      pagedList.setList(result);
      pagedList.setPage(page);
      pagedList.setPageSize(pageSize);
      pagedList.setStart(start);
      pagedList.setEnd(end);
      log.info("total: {}, range: {}~{}", pagedList.getTotal(), pagedList.getStart(), pagedList.getEnd());
      return pagedList;
    }
    
    return invocation.proceed();
  }
}
