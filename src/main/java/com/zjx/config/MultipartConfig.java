/**
 * 
 * @title: MultipartConfig.java
 * @package com.spek.file.configure
 * @author zhengjiaxing
 * @date 2018-12-27 15:03:31
 * @version V1.0
 * @project: 智能云链公共服务平台
 * @description: Copyright © 2018 Spek, All Rights Reserved.
 * 
 */
package com.zjx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @description: 文件上传大小限制配置类
 * @className: MultipartConfig.java
 * @author zhengjiaxing
 * @date 2018-12-27 15:03:31
 * 
 */
@Configuration
public class MultipartConfig {
	
	@Value("${spek.file.maxFileSize}")
	private String maxFileSize;
	
	@Value("${spek.file.maxRequestSize}")
	private String maxRequestSize;

	/**
	 * 配置上传文件大小的配置
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 单个数据大小
		factory.setMaxFileSize(maxFileSize);
		/// 总上传数据大小
		factory.setMaxRequestSize(maxRequestSize);
		return factory.createMultipartConfig();
	}
}
