package com.caffeincache.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.caffeincache.demo.entity.CDEDictVO;
import com.caffeincache.demo.service.ICacheService;
import com.caffeincache.demo.service.IDictService;

@Service
public class CacheServiceImpl implements ICacheService{

	
	@Autowired
	private IDictService dictService;
	
	@Override
	@CachePut(value="dictCache")
	public List<CDEDictVO> queryAllDict() {
		List<CDEDictVO> list = dictService.queryAllDict().subList(0, 1);
		return list;
	}

}
