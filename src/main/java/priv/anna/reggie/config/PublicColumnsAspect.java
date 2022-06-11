package priv.anna.reggie.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
///**
// * 公共字段自动填充
// * 注意：老师用的是mybatisplus，公共字段自动填充功能是由mp提供的
// * 我这里使用AOP织入来处理公共字段!!!!
// */
//@Component
//@Slf4j
//@Aspect   //这是切面
//public class PublicColumnsAspect {
//
//    //1.配置连接点
////    @Pointcut("execution(void priv.anna.reggie.service.Impl.TtAspect.lala())")
////    //此方法名与下面注解中的参数一一对应
////    public void methodAcpect(){
////
////    }
//
//    //2. 实现要对某个方法增强的功能
//   // @Before("methodAcpect()",value="args(param)", argNames="param")
//  //  @Before(value = "execution(void priv.anna.reggie..*.*(..)) && args(page,pageSize,name)", argNames = "page,pageSize,name")
//   // @Before("execution(void priv.anna.reggie.ttt..*.*(..))")
//   // @Before("execution(void priv.anna.reggie.service.Impl.TtAspect())")
//
//
// //   @Before("execution(void priv.anna.reggie.service.Impl.EmployeeServiceImpl.findById())")
// //   @Before("methodAcpect()")
//
//  //  @Before("execution(void priv.anna.reggie.service..*.*(..))")
//
//    @Around("execution(void priv.anna.reggie.service.Impl.TtAspect.*(..))")
//    public  void methodAcpectBefore(){  //int page, int pageSize, String name
//        log.info("AOP织入。。。。。。。。。");
//        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//    }
//
//
//}
