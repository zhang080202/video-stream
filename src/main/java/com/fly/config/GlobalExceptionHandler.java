package com.fly.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fly.data.common.ResponseBean;
import com.fly.exception.ServiceException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	public ResponseBean error(Exception e) {
		ResponseBean r = null;
		if (e instanceof ServiceException) {
			logger.info("happend businessException, Caused by " + e.getMessage());
			r = ResponseBean.fail(e.getMessage(), ((ServiceException) e).getCode());
		} else {
			logger.error("happend systemException, Caused by " + e.getMessage());
			r = ResponseBean.fail();
		}
		e.printStackTrace();
		return r;
	}

}
