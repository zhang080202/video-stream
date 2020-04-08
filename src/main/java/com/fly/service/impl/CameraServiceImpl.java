package com.fly.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fly.camera.strategy.CameraContext;
import com.fly.data.entity.CameraConfigDO;
import com.fly.service.ICameraService;
import com.fly.utils.ComandManagerHolder;

import cc.eguid.commandManager.data.CommandTasker;

@Service
public class CameraServiceImpl implements ICameraService {
	
	private static final Logger logger = LoggerFactory.getLogger(CameraServiceImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CameraContext cameraContext;
	
	@Override
	public List<CameraConfigDO> getCameras(Integer type) {
		String sql = "SELECT * FROM camera_config";
		
		if (type != null) {
			sql += " WHERE type = " + type;
		}
		List<CameraConfigDO> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<CameraConfigDO>(CameraConfigDO.class));
		return result;
	}

	@Override
	public Map<String, Object> openCamera(Integer type) {
		List<CameraConfigDO> cameras = getCameras(type);
		Map<String, Object> result = new HashMap<>();
		
		for (CameraConfigDO cameraConfigDO : cameras) {
			String rtmpResult = cameraContext.openCamera(cameraConfigDO);
			if (rtmpResult == null) continue;
			
			result.put(cameraConfigDO.getCameraName(), rtmpResult);
		}
		Collection<CommandTasker> tasks = ComandManagerHolder.getCommandManager().queryAll();
		logger.info("当前已开启摄像头有 {} 个，详情：", tasks.size(), tasks);
		return result;
	}

	@Override
	public int stopCameraAll() {
		logger.info("所有摄像头已关闭");
		return ComandManagerHolder.getCommandManager().stopAll();
	}
	
	@Override
	public boolean stopByDeviceName(String cameraName) {
		logger.info("摄像头 {} 已关闭", cameraName);
		return ComandManagerHolder.getCommandManager().stop(cameraName);
	}
	
	

}
