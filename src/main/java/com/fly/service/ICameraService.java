package com.fly.service;

import java.util.List;
import java.util.Map;

import com.fly.data.entity.CameraConfigDO;

public interface ICameraService {
	
	List<CameraConfigDO> getCameras(Integer type);

	Map<String, Object> openCamera(Integer type);

	int stopCameraAll();

	boolean stopByDeviceName(String cameraName);
	
}
