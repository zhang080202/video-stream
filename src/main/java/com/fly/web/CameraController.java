package com.fly.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fly.data.common.ResponseBean;
import com.fly.service.ICameraService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/camera")
@Api(tags = "摄像头相关操作")
public class CameraController {
	
	@Autowired
	private ICameraService cameraService;
	
	@GetMapping("/open")
	@ApiOperation("打开摄像头 可根据type打开指定类型摄像头 若不传则打开所有")
	public ResponseBean open(@RequestParam(value = "type", required = false) Integer type) {
		Map<String, Object> result = cameraService.openCamera(type);
		return ResponseBean.succ(result);
	}
	
	@GetMapping("/stopAll")
	@ApiOperation("关闭所有摄像头")
	public ResponseBean stopAll() {
		int result = cameraService.stopCameraAll();
		return ResponseBean.succ(result);
	}

	@GetMapping("/stop/{cameraName}")
	@ApiOperation("关闭指定名称摄像头")
	public ResponseBean stopByDeviceName(@PathVariable("cameraName") String cameraName) {
		boolean result = cameraService.stopByDeviceName(cameraName);
		return ResponseBean.succ(result);
	}

}
