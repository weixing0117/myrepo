package com.caffeincache.demo.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface IDictDao {
	List<CDEDictVO> getDicts();
}
