package market.common.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.ResponseFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.common.aop.annotation.Login;
import market.common.aop.annotation.UserInfo;
import market.common.constant.Constant;
import market.common.enumcode.RESPONSE_CODE;
import market.common.exception.MarketException;
import market.common.util.AsciiTable;
import market.common.vo.UserVo;

@Aspect
@Order(1)
@Component
@Slf4j
@RequiredArgsConstructor
public class RestControllerAspect {
  
  @SuppressWarnings("unused")
  @Around("execution(* market..controller.*Controller.*(..))")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    jakarta.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    jakarta.servlet.http.HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    String reqUri = request.getRequestURI();
    String method = request.getMethod();
    String contentType = request.getHeader("Content-Type");
    String remoteAddr = request.getRemoteAddr();
    String tokenId = request.getHeader(Constant.HTTP_HEADER.X_TOKEN_ID);
    
    List<AsciiTable.Column> columns = AsciiTable.ColumnLayout.NAME_VALUE;
    Formatter fmt = AsciiTable.header(columns);
    AsciiTable.body(columns, new Object[] {"RequestURI", String.format("%s %s", method, reqUri)}, fmt);
    AsciiTable.body(columns, new Object[] {"Class", joinPoint.getSignature().toShortString()}, fmt);
    AsciiTable.body(columns, new Object[] {"RemoteAddr", String.format("%s  %s", remoteAddr, tokenId)}, fmt);
    AsciiTable.space(columns, fmt);
    log.info("Request Info \n{}", fmt);
    
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method refMethod = methodSignature.getMethod();
    Annotation[][] parameterAnnotations = refMethod.getParameterAnnotations();
    Login login = refMethod.getAnnotation(Login.class);
    Object[] args = joinPoint.getArgs();
    if (login != null && !StringUtils.hasLength(tokenId)) {
      throw new MarketException(RESPONSE_CODE.AL01);
    }
    
    for (int i=0; i<args.length; i++) {
      Object arg = args[i];
      Annotation[] annotations = parameterAnnotations[i];
      boolean hasUserInfoAnnotation = Arrays.stream(annotations)
          .anyMatch(annotation -> annotation.annotationType().equals(UserInfo.class));
      if (hasUserInfoAnnotation && arg instanceof UserVo) {
        UserVo userVo = new UserVo();
        userVo.setUserId(tokenId);
        args[i] = userVo;
      }
    }
    
    Object result = null;
    Throwable throwable = null;
    try {
      result = joinPoint.proceed(args);
    } catch (Throwable e) {
      throwable = e;
      throw e;
    } finally {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
      Object[] newArgs = Arrays.stream(args).map(item -> 
        (item instanceof RequestFacade || 
        item instanceof ResponseFacade || 
        item instanceof MultipartFile) ? item.getClass() : null
      ).toArray();
      String reqstr = objectMapper.writeValueAsString(newArgs);
      String resstr = null;
      if (throwable != null) {
        //
      }
    }
    
    return result;
  }
}
