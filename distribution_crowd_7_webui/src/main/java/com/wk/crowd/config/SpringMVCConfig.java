package com.wk.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

	/**
	 * 根据路径映射到页面，适合直接跳转页面的路径
	 * 为什么不直接访问页面？？？
	 * 真的不能直接访问页面啊
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		/*
		访问toLogin，跳转到login页面，相当于以前在mvc中配置的
		<mvc:view-controller path="/toLogin" viewName="login"/>
		* */
		//跳转到登录页面
		String urlPath="member/login.html";
		String viewName="member/login";
		registry.addViewController(urlPath).setViewName(viewName);
		//跳转到个人中心页面
		urlPath="member/center.html";
		viewName="member/center";
		registry.addViewController(urlPath).setViewName(viewName);
		//跳转到同意协议页面
		urlPath = "project/to/agree/page";
		viewName = "project/start";
		registry.addViewController(urlPath).setViewName(viewName);
	}
}
