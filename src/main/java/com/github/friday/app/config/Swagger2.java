package com.github.friday.app.config;

import com.github.friday.common.base.ResultCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket buildDocket() {
        ArrayList<ResponseMessage> responseMessages = new ArrayList<>();
        for (ResultCode rc : ResultCode.values()) {
            responseMessages.add(new ResponseMessageBuilder().code(rc.getCode()).message(rc.getValue()).build());
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.github.friday"))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages);

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Friday RESTful API")
                .version("1.0")
                .description(null)
                .build();
    }


}
