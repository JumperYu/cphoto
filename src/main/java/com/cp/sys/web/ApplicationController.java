package com.cp.sys.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cp.sys.entity.Application;

@Controller
@RequestMapping("/sys")
public class ApplicationController {

	@RequestMapping("/index")
	@ResponseBody
	public List<Application> index() {
	
		List<Application> nodes = new ArrayList<Application>();
		
		return nodes;
	}
	
}
