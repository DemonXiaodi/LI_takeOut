package priv.anna.reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import priv.anna.reggie.common.JacksonObjectMapper;

import java.util.List;

//配置静态资源映射，使不是在static目录下的静态资源可以被访问
@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 设置静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");

    }

    /**
     * 扩展mvc框架的消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器");

        //创建消息转换器对象
        /*
        创建消息转换器对象的作用
        将controller返回给前端的R对象转换成json，再通过输出流方式响应给页面
         */
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

    //现在要拓展消息转化器
        //设置对象，底层使用Jackson将java转换成json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0,messageConverter); //将自己的转换器放到最前面，可以被优先使用

        super.extendMessageConverters(converters);
    }
}
