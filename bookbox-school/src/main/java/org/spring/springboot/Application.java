package org.spring.springboot;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring Boot 应用启动类
 *
 * Created by ganwb on 03/04/2019.
 */
// Spring Boot 应用的标识
@EnableAutoConfiguration
@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@ImportResource("classpath:dubbo-consumer.xml")
public class Application extends SpringBootServletInitializer {
	
	private static Logger logger = Logger.getLogger(Application.class);

    @Bean
    public ThreadPoolTaskExecutor getExcutor() {
    	ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
    	return poolTaskExecutor;
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        setRegisterErrorPageFilter(false); 
        return application.sources(Application.class);
    }
  //启动类中加如下代码，成功解决问题  
    @Bean      
     public ErrorPageFilter errorPageFilter() {     
          return new ErrorPageFilter();     
     }      
    @Bean  
    public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {          
          FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();             
          filterRegistrationBean.setFilter(filter);        
          filterRegistrationBean.setEnabled(false);        
          return filterRegistrationBean;    
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
//    	new SpringApplicationBuilder(Application.class).bannerMode(Banner.Mode.OFF).run(args);
        logger.info("SpringBoot Start Success!!!");
    }

}
