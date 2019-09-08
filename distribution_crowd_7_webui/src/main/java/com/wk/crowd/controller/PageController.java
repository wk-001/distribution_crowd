package com.wk.crowd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	/*@RequestMapping("/")
	public String toIndex(){
		return "index";
	}*/

	@RequestMapping("toPage/{fold}/{page}")
	public String showPage(@PathVariable String fold,@PathVariable String page){
		return fold+"/"+page;
	}
}
