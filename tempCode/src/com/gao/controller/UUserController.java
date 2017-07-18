package com.gao.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.api.UUserService;

@Controller
@RequestMapping("UUser")
public class UUserController {

	@Autowired
	private UUserService uUserService;


}