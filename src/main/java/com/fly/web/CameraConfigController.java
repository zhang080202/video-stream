package com.fly.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fly.data.common.ResponseBean;
import com.fly.data.entity.CameraConfigDO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/config")
@Api(tags = "摄像头配置相关接口 （非管理员请勿使用）")
public class CameraConfigController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping
	@ApiOperation("查询所有配置")
	public ResponseBean queryAll() {
		String sql = "SELECT * FROM camera_config";
		List<CameraConfigDO> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<CameraConfigDO>(CameraConfigDO.class));
		return ResponseBean.succ(result);
	}
	
	@PostMapping
	@ApiOperation("新增一条摄像头配置")
	public ResponseBean store(@RequestBody CameraConfigDO cameraConfigDO) {
		String sqlQ = "SELECT * FROM camera_config WHERE camera_name = '" + cameraConfigDO.getCameraName() + "'";
		try {
			jdbcTemplate.queryForObject(sqlQ, new BeanPropertyRowMapper<CameraConfigDO>(CameraConfigDO.class));
			return ResponseBean.fail("该摄像头编码（camera_name）已存在");
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO camera_config(id, camera_name, camera_url, camera_type, type, remark) VALUES (?, ?, ?, ?, ?, ?)";
		int result = jdbcTemplate.update(sql, cameraConfigDO.getId(), cameraConfigDO.getCameraName(), cameraConfigDO.getCameraUrl(), cameraConfigDO.getCameraType(), 
				cameraConfigDO.getType(), cameraConfigDO.getRemark());
		return ResponseBean.succ(result);
	}
	
	@PutMapping("/{id}")
	@ApiOperation("修改摄像头配置")
	public ResponseBean update(@PathVariable("id") String id, @RequestBody CameraConfigDO cameraConfigDO) {
		String sql = "UPDATE camera_config SET camera_name=?,camera_url=?,camera_type=?,type=?,remark=? WHERE id=?";
		int result = jdbcTemplate.update(sql, cameraConfigDO.getCameraName(), cameraConfigDO.getCameraUrl(), cameraConfigDO.getCameraType(), 
				cameraConfigDO.getType(), cameraConfigDO.getRemark(), id);
		return ResponseBean.succ(result);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation("根据ID删除摄像头配置")
	public ResponseBean delete(@PathVariable("id") String id) {
		String sql = "DELETE FROM camera_config WHERE id=?";
		int result = jdbcTemplate.update(sql, id);
		return ResponseBean.succ(result);
	}

}
