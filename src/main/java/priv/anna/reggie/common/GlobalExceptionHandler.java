package priv.anna.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})  //拦截有@RestController或者@Controller的controller,
@ResponseBody  //将结果封装成json
@Slf4j
public class GlobalExceptionHandler {

    /**
     * sql异常处理
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)  //处理这种异常
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        //判断是否是唯一约束异常
        if (ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg  = split[2] + "已存在";  //获取到异常的用户名
            return R.error(msg);
        }
        return R.error("未知错误！");
    }


    /**
     * 自定义异常的处理器
     * @return
     */
    @ExceptionHandler(CustomException.class)  //处理这种异常
    public R<String> exceptionHandler(CustomException ex){
        log.error(ex.getMessage());
        //返回service中抛出异常时写入的信息
        return R.error(ex.getMessage());
    }

}
