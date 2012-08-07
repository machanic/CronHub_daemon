package com.baofeng.dispatchexecutor.bean;
/**
 * @author 作者 E-mail:*@baofeng.com
 * @version 创建时间：Mar 26, 2010 1:03:53 AM
 * 类说明
 */
public class HttpResult {
	private Integer httpCode;
	private String content;
	
	public Integer getHttpCode() {
		return httpCode;
	}
	
	public void setHttpCode(Integer httpCode) {
		this.httpCode = httpCode;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
