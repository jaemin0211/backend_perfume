package com.test.shop.common.config;


import com.test.shop.common.interceptor.AdminAuthCheckInterceptor;
import com.test.shop.common.interceptor.MemberAuthCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MemberAuthCheckInterceptor memberAuthCheckInterceptor;
    private final AdminAuthCheckInterceptor adminAuthCheckInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(memberAuthCheckInterceptor);
        registry.addInterceptor(adminAuthCheckInterceptor);
    }
}
