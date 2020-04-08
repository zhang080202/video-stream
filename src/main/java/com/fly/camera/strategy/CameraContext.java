package com.fly.camera.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fly.camera.CameraOperationSuper;
import com.fly.data.entity.CameraConfigDO;

@Component
public class CameraContext {

	@Autowired
	@Qualifier("cameraOperation")
	private CameraOperationSuper cameraOperation;

	public String openCamera(CameraConfigDO cameraConfigDO) {
		return getCameraInstance(cameraConfigDO.getCameraType()).openCamera(cameraConfigDO);
	}
	
	public CameraOperationSuper getCameraInstance(String type) {
		CameraOperationSuper instance = null;

		switch (type) {
			case "camera":
				instance = cameraOperation;
				break;
	
			default:
				break;
		}
		return instance;
	}

}
