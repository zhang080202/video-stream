package com.fly.camera;

import org.springframework.stereotype.Component;

import com.fly.data.entity.CameraConfigDO;
import com.fly.utils.ComandManagerHolder;
import com.fly.utils.CommonUtils;

import cc.eguid.commandManager.commandbuidler.CommandBuidlerFactory;
import cc.eguid.commandManager.data.CommandTasker;

@Component
public class CameraOperation implements CameraOperationSuper {
	
	@Override
	public String openCamera(CameraConfigDO cameraConfigDO) {
		//检查配置中的摄像头是否已经开始拉流
		CommandTasker tasker = ComandManagerHolder.getCommandManager().query(cameraConfigDO.getCameraName());
		if (tasker != null) return null;
		
		StringBuffer output = new StringBuffer("rtmp://");
		output.append(CommonUtils.getLocalIP())
			  .append("/live/")
			  .append(cameraConfigDO.getCameraName());
		
		String id = cameraConfigDO.getCameraName();
		String rtmpResult = output.toString(); 
		
		String start = ComandManagerHolder.getCommandManager().start(cameraConfigDO.getCameraName(), CommandBuidlerFactory.createBuidler()
				.add("ffmpeg")
				.add("-rtsp_transport", "tcp")
				.add("-i", cameraConfigDO.getCameraUrl())
				.add("-f", "flv")
				.add("-an", output.toString()));
		
		if (start == null || start.equals("")) {
			rtmpResult = id + " 该摄像头开启失败，请检查配置";
		}
		return rtmpResult;
	} 
	
}
