package com.wk.crowd.service;

import com.wk.crowd.pojo.vo.ProjectVO;

public interface ProjectService {

	void saveProject(ProjectVO projectVO,String memberId);
}