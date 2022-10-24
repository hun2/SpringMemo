package com.example.Memo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.Memo.common.FileManagerServices;

@Configuration        //파일을 맵핑해주는 셋팅클래스 라고 생각하셈.
public class WebMvcConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**") // 웹주소 url path       // http://localhost
		.addResourceLocations("file:///" + FileManagerServices.FILE_UPLOAD_PATH); // 실제 파일 위치 경로 맵핑/    //윈도우는 3개 슬러시 
		
	}
}
