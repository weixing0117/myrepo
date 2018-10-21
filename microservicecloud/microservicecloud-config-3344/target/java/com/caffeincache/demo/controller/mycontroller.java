package com.caffeincache.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caffeincache.demo.entity.CDEDictVO;
import com.caffeincache.demo.service.ICacheService;
import com.caffeincache.demo.service.IDictService;

@RestController
public class mycontroller {
	@Autowired
	private IDictService dickService;
	@Autowired
	private ICacheService cacheService;
	
	@GetMapping("getdict")
	public List<CDEDictVO> queryDict() {
		List<CDEDictVO> list = dickService.queryAllDict();
		return list;
		
	}
	
	
	@GetMapping("getcache")
	public List<CDEDictVO> queryDictCache() {
		List<CDEDictVO> list = cacheService.queryAllDict();
		return list;
		
	}
}
