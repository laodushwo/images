package org.spring.springboot.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration //让Spring来加载该类配置
@EnableSwagger2 //启用Swagger2
public class Swagger2 {
	
    @Bean
    public Docket alipayApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("服务平台 API")  
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.spring.springboot.controller"))
                .paths(PathSelectors.any()).build();
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("IM通讯API系统")
//                .description("描述")
                .termsOfServiceUrl("http://localhost:6065")
//                .contact(new Contact("", "", ""))
                .version("1.0").build();
    }

}
