package market.lib.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.ResponseFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.modelmapper.internal.util.Assert;
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
import market.lib.aop.annotation.Login;
import market.lib.aop.annotation.UserInfo;
import market.lib.vo.UserVo;

@Aspect
@Order(1)
@Component
@Slf4j
@RequiredArgsConstructor
public class RestControllerAspect {
  
  //@Around("execution(* market.*.controller.*Controller.*(..))") // market 하위 1단계만 weaving 됨 
  @SuppressWarnings("unused")
  @Around("execution(* market..controller.*Controller.*(..))")  // market 하위 모든 단계가 weaving 됨
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    jakarta.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    jakarta.servlet.http.HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    String reqUri = request.getRequestURI();
    String method = request.getMethod();
    String contentType = request.getHeader("Content-Type");
    String remoteAddr = request.getRemoteAddr();
    
    log.debug("*Class        : {}", joinPoint.getTarget());
    log.debug("*Method       : {}", method);
    log.info("*RequestURI   : {}", reqUri);
    log.debug("*ContentType  : {}", contentType);
    log.debug("*RemoteAddr   : {}", remoteAddr);
    
    String userId = request.getHeader("X-USER_ID");
    String userNm = request.getHeader("X-USER_NM");
    log.info("*userId   : {}", userId);
    log.info("*userNm   : {}", userNm);
    
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method refMethod = methodSignature.getMethod();
    Login login = refMethod.getAnnotation(Login.class);
    if (login != null) {
      Assert.isTrue(StringUtils.hasLength(userId), "userId 가 null 입니다.");
    }
    Object result = null;
    Throwable throwable = null;
    
    //Annotation[][] parameterAnnotations = refMethod.getParameterAnnotations();
    Object[] args = joinPoint.getArgs();
    for (int i=0; i<args.length; i++) {
      Object arg = args[i];
      //Annotation[] annotations = parameterAnnotations[i];
      //boolean hasUserInfoAnnotation = Arrays.stream(annotations)
      //    .anyMatch(annotation -> annotation.annotationType().equals(UserInfo.class));
      //if (hasUserInfoAnnotation && arg instanceof UserVo) {
      if (arg instanceof UserVo) {
        UserVo userVo = new UserVo();
        userVo.setUserId(userId);
        args[i] = userVo;
      }
    }
    
    try {
      result = joinPoint.proceed(args);
    } catch (Throwable e) {
      throwable = e;
      throw e;
    } finally {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
      Object[] newArgs = Arrays.stream(args).map(item -> {
        if(item instanceof RequestFacade || item instanceof ResponseFacade || item instanceof MultipartFile) {
          item = item.getClass();
        }
        return item;
      }).toArray();
      String reqstr = objectMapper.writeValueAsString(newArgs);
      String resstr = null;
      if (throwable != null) {
        //
      }
    }
    
    return result;
  }
}
