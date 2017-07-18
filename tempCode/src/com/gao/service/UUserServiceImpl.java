package com.gao.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.gao.dao.UUserMapper;
import com.gao.api.UUserService;

@Service(value="uUserService")
public class UUserServiceImpl implements UUserService {

	@Autowired
	private UUserMapper uUserMapper;

}
