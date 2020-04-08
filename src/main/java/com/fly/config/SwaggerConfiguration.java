package com.fly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket createRestfulApi() {// api文档实例
		return new Docket(DocumentationType.SWAGGER_2)// 文档类型：DocumentationType.SWAGGER_2
				.apiInfo(apiInfo())// api信息
				.select()// 构建api选择器
				.apis(RequestHandlerSelectors.basePackage("com.fly"))// api选择器选择api的包
				.paths(PathSelectors.any())// api选择器选择包路径下任何api显示在文档中
				.build();
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts());// 创建文档
	}

	private ApiInfo apiInfo() {// 接口的相关信息
		return new ApiInfoBuilder().title("摄像头拉流服务")
								   .description("开发文档")
								   .version("1.0")
					               .build();
	}
	
//	private List<ApiKey> securitySchemes() {
//        List<ApiKey> apiKeyList= new ArrayList<ApiKey>();
//        //注意，这里应对应登录token鉴权对应的k-v
//        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
//        return apiKeyList;
//    }
//
//    private List<SecurityContext> securityContexts() {
//        List<SecurityContext> securityContexts=new ArrayList<>();
//        securityContexts.add(
//                SecurityContext.builder()
//                        .securityReferences(defaultAuth())
//                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
//                        .build());
//        return securityContexts;
//    }
//
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        List<SecurityReference> securityReferences=new ArrayList<>();
//        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
//        return securityReferences;
//    }
}
