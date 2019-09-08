package com.wk.crowd.controller;

import com.wk.crowd.api.MemberManagerRemoteService;
import com.wk.crowd.pojo.ResultEntity;
import com.wk.crowd.pojo.vo.MemberSignSuccessVO;
import com.wk.crowd.pojo.vo.MemberVO;
import com.wk.crowd.util.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
	private MemberManagerRemoteService managerRemoteService;

	//登录
	@RequestMapping("member/do/login.html")
	public String doLogin(MemberVO memberVO, Model model, HttpSession httpSession){
		String loginacct = memberVO.getLoginacct();
		String userpswd = memberVO.getUserpswd();
		//调用远程方法执行登录操作
		ResultEntity<MemberSignSuccessVO> resultEntity = managerRemoteService.login(loginacct, userpswd);
		//检查远程方法执行结果
		if(ResultEntity.FAILED.equals(resultEntity.getResult())){
			//如果登录失败，返回失败信息到前端
			model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
			return "member/login";
		}
		//如果登录成功，则获取对象，并放入到httpSession中，存入Redis
		MemberSignSuccessVO memberSignSuccessVO = resultEntity.getData();
		if (memberSignSuccessVO != null) {
			httpSession.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER,memberSignSuccessVO);
		}
		//重定向到个人中心页面
		return "redirect:/member/center.html";
	}

	//退出，用户带着session信息访问该方法，通过携带的session获取用户token
	@RequestMapping("member/logout.html")
	public String logout(HttpSession httpSession){
		//从现有session中获取已登录的member对象
		MemberSignSuccessVO signSuccessVO = (MemberSignSuccessVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		//如果对象为null，则已经退出
		if (signSuccessVO == null) {
			return "redirect:/";
		}
		//从member对象中获取token
		String token = signSuccessVO.getToken();
		//调用远程方法执行退出操作
		ResultEntity<String> resultEntity = managerRemoteService.logout(token);
		//远程方法当用失败则抛出异常
		if(ResultEntity.FAILED.equals(resultEntity.getResult())){
			throw new RuntimeException(resultEntity.getMessage());
		}
		//销毁session
		httpSession.invalidate();
		return "redirect:/";
	}

}
