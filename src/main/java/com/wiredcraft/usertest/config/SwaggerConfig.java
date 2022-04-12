package com.wiredcraft.usertest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @ClassName SwaggerConfig
 * @Description swagger2配置类
 * @Author wanglei
 * @Date 2022/4/8 19:43
 * @Version 1.0
 **/
@Configuration
public class SwaggerConfig {
//    @Bean
//    Docket docket(){
//        return new Docket(DocumentationType.SWAGGER_2);
//    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        /** swagger配置 */
//        registry.addResourceHandler("/swagger-ui/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
//
//    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        System.out.println("====yyds====");
//        registry.addViewController( "/swagger-ui/")
//                .setViewName("forward:/swagger-ui/index.html");
//    }


    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wiredcraft.usertest.user.controller"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("UserTest API Doc")
                .description("This is a restful api document of UserTest.")
                .version("1.0")
                .build();
    }

    //这个是可要可不要的，具体看需求
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
}
