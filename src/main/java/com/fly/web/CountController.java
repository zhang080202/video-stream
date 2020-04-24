package com.fly.web;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fly.data.common.ResponseBean;
import com.fly.exception.ServiceException;
import com.ruijie.wmc.open.ClientHelper;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/count")
public class CountController {
	
	@Autowired
	private Environment env;
	
	@GetMapping
	@ApiOperation("景区人流数据")
	public ResponseBean count(@RequestParam("md5") String md5,
							  @RequestParam("des3") String des3,
							  @RequestParam("appid") String appid) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("method", "userInfo.onlineUserCount");
		String url = env.getProperty("countUrl");
		String resultJson = ClientHelper.sendRequest(url, md5, des3,
				appid, JSON.toJSONString(params));
		
		JSONObject jsonObject = JSON.parseObject(resultJson);
		String code = jsonObject.getString("code");
		if (!code.equals("100")) {
			throw new ServiceException("景区人流数据服务异常");
		}
		return ResponseBean.succ(jsonObject.get("count"));
	}

}
