package priv.anna.reggie.common;

//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/* 自定义元数据对象处理器
由Mybatis-plus实现的 公共字段填充
 */
//@Component
//@Slf4j
//public class MyMetaObjecthandler implements MetaObjectHandler{
//
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        log.info("公共字段自动填充【insert】");
//        log.info(metaObject.toString());
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        log.info("公共字段自动填充【update】");
//        log.info(metaObject.toString());
//    }
//}
