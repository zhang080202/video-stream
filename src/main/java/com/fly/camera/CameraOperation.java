package com.fly.camera;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fly.data.entity.CameraConfigDO;
import com.fly.utils.ComandManagerHolder;

import cc.eguid.commandManager.commandbuidler.CommandBuidlerFactory;
import cc.eguid.commandManager.data.CommandTasker;

@Component
public class CameraOperation implements CameraOperationSuper {
	
	@Value("${local-ip}")
	private String localIp;
	
	@Override
	public String openCamera(CameraConfigDO cameraConfigDO) {
		String rtmpResult = null;
		//检查配置中的摄像头是否已经开始拉流
		CommandTasker tasker = ComandManagerHolder.getCommandManager().query(cameraConfigDO.getCameraName());
		if (tasker != null) {
			String command = tasker.getCommand();
			Process process = tasker.getProcess();
			if (process.isAlive()) {
				rtmpResult = command.substring(command.indexOf("rtmp"));
			} else {
				ComandManagerHolder.getCommandManager().stop(cameraConfigDO.getCameraName());
				rtmpResult = open(cameraConfigDO);
			}
		} else {
			rtmpResult = open(cameraConfigDO);
		}
		
		return rtmpResult;
	}
	
	private String open(CameraConfigDO cameraConfigDO) {
		StringBuffer output = new StringBuffer("rtmp://");
		output.append(localIp)
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
