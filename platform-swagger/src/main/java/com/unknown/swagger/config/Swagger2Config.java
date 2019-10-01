package com.unknown.swagger.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <br>(c) Copyright koujiang901123@sina.com
 * <br>@description :Swagger 基础配置
 * <br>@file_name   :Swagger2Config.java
 * <br>@system_name :com.unknown.platform.swagger.config.Swagger2Config
 * <br>@author      :Administrator
 * <br>@create_time :2019/9/30 16:55
 * <br>@mender      :(Please add the modifier name)
 * <br>@Modified    :(Please add modification date)
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    /*
    swagger通过注解表明该接口会生成文档，包括接口名、请求方法、参数、返回信息的等等。
    @Api：修饰整个类，描述Controller的作用
    @ApiOperation：描述一个类的一个方法，或者说一个接口
    @ApiParam：单个参数描述
    @ApiModel：用对象来接收参数
    @ApiProperty：用对象接收参数时，描述对象的一个字段
    @ApiResponse：HTTP响应其中1个描述
    @ApiResponses：HTTP响应整体描述
    @ApiIgnore：使用该注解忽略这个API
    @ApiError ：发生错误返回的信息
    @ApiImplicitParam：一个请求参数
    @ApiImplicitParams：多个请求参数
    */
    @Bean
    public Docket getDocket() {


        return new Docket(DocumentationType.SWAGGER_2)
            //.host("192.168.8.154:8080")
            .apiInfo(getApiInfo())
            //.pathMapping("/")
            .select()
            // 自行修改为自己的包路径
            .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
            //.apis(Swagger2Config.basePackage("com.koujiang901123@sima.com.lottery.web.rest,com.koujiang901123@sima.com.lottery.mobile.rest"))
            //.apis(RequestHandlerSelectors.basePackage("com.koujiang901123@sima.com.lottery.web.rest"))
            .paths(PathSelectors.any())
            .build();
    }

    public ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
            .title("api文档")
            .description("《懂球客Pro》接入教程")
            //服务条款网址
            .termsOfServiceUrl("http://www.cnblogs.com/JoiT/p/6378086.html")
            .version("1.0")
            .contact(new Contact("Koujiang", "https://blog.csdn.net/sanyaoxu_2/article/details/80555328", "koujiang901123@sina.com"))
            .build();
    }

    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        //return  ((input) -> declaringClass(input).transform(handlerPackage(basePackage)).or(true));
        return (input) -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return (input) -> {
            for (String strPackage : basePackage.split(",")) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch)
                    return true;
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}
