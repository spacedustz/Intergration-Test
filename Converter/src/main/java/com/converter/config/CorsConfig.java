package com.converter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    //    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**")
//                .allowedOrigins("http://192.168.0.157:8080") // 허용할 origin (프론트엔드 주소)
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
//                .allowedHeaders("*") // 허용할 헤더
//                .allowCredentials(true); // Credentials (쿠키 등) 허용 여부
//
//        registry.addMapping("/video/**") // 추가된 부분
//                .allowedOrigins("http://192.168.0.157:8080") // 허용할 origin (프론트엔드 주소)
//                .allowedMethods("GET") // GET 메서드만 허용
//                .allowCredentials(true); // Credentials (쿠키 등) 허용 여부
//
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("*")
                .allowedOrigins("http://localhost:3000") // 허용할 origin (프론트엔드 주소)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 허용할 헤더
                .allowCredentials(true); // Credentials (쿠키 등) 허용 여부

//        registry.addMapping("/video/**") // 추가된 부분
//                .allowedOrigins("http://localhost:8080") // 허용할 origin (프론트엔드 주소)
//                .allowedMethods("GET") // GET 메서드만 허용
//                .allowCredentials(true); // Credentials (쿠키 등) 허용 여부
//
//    }
    }
}
