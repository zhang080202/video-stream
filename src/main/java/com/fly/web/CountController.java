package com.fly.web;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fly.data.common.ResponseBean;
import com.fly.exception.ServiceException;
import com.ruijie.wmc.open.ClientHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/count")
@Api("景区数据相关接口")
public class CountController {
	
	@Autowired
	private Environment env;
	
	private static final Logger log = LoggerFactory.getLogger(CountController.class);
	
	@GetMapping
	@ApiOperation("景区人流数据")
	public ResponseBean count() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("method", "userInfo.onlineUserCount");
		String url = env.getProperty("countUrl");
		String des3 = env.getProperty("des3");
		String md5 = env.getProperty("md5");
		String appid = env.getProperty("appid");
		
		String resultJson = ClientHelper.sendRequest(url, md5, des3,
				appid, JSON.toJSONString(params));
		
		JSONObject jsonObject = JSON.parseObject(resultJson);
		String code = jsonObject.getString("code");
		if (!code.equals("100")) {
			log.error(resultJson);
			throw new ServiceException("景区人流数据服务异常");
		}
		return ResponseBean.succ(jsonObject.get("count"));
	}

}
