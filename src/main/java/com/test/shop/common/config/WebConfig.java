package com.test.shop.common.config;


import com.test.shop.common.interceptor.AdminAuthCheckInterceptor;
import com.test.shop.common.interceptor.MemberAuthCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MemberAuthCheckInterceptor memberAuthCheckInterceptor;
    private final AdminAuthCheckInterceptor adminAuthCheckInterceptor;


    @Configuration
    public class WebMvcConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000", "http://localhost:8080")
                    .allowedMethods("GET", "POST") // 허용할 HTTP method
                    .allowCredentials(true) // 쿠키 인증 요청 허용
                    .exposedHeaders("X-Auth-Token")
                    .maxAge(3000);

        }
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(memberAuthCheckInterceptor);
        registry.addInterceptor(adminAuthCheckInterceptor);
    }

    //@Override
   // public void addCorsMappings(CorsRegistry registry) {
    //    registry.addMapping("/**")
    //            .allowedOrigins("*")
     //           .allowedMethods("GET", "POST")
     //           .maxAge(3000);
   // }

}

