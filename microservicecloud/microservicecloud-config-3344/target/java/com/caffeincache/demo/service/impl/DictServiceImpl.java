package com.caffeincache.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.caffeincache.demo.entity.CDEDictVO;
import com.caffeincache.demo.entity.IDictDao;
import com.caffeincache.demo.service.IDictService;


@Service
public class DictServiceImpl implements IDictService{
	
	
	@Autowired
	private IDictDao dictDao;
	
	@Cacheable(value="dictCache")
	public List<CDEDictVO> queryAllDict() {
		// TODO Auto-generated method stub
		System.out.println("进入数据库查询");
		return dictDao.getDicts();
	}

}
