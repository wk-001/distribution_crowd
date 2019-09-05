package com.wk.crowd.controller;

import com.wk.crowd.pojo.ResultEntity;
import com.wk.crowd.pojo.vo.ProjectVO;
import com.wk.crowd.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@RequestMapping("save/project/remote/{memberId}")
	ResultEntity<String> saveProjectRemote(@RequestBody ProjectVO projectVO, @PathVariable("memberId")String memberId){
		try {
			projectService.saveProject(projectVO,memberId);
			return ResultEntity.successNoData();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}

}
