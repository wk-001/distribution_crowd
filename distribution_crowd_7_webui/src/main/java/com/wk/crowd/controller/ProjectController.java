package com.wk.crowd.controller;

import com.wk.crowd.api.MemberManagerRemoteService;
import com.wk.crowd.api.ProjectOperationRemoteService;
import com.wk.crowd.pojo.ResultEntity;
import com.wk.crowd.pojo.po.MemberLaunchInfoPO;
import com.wk.crowd.pojo.vo.MemberSignSuccessVO;
import com.wk.crowd.pojo.vo.ProjectVO;
import com.wk.crowd.util.CrowdConstant;
import com.wk.crowd.util.CrowdUtils;
import com.wk.crowd.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class ProjectController {

	@Autowired
	private ProjectOperationRemoteService projectRemoteService;

	@Autowired
	private MemberManagerRemoteService managerRemoteService;

	@Value(value="${oss.project.parent.folder}")
	private String ossProjectParentFolder;

	@Value(value="${oss.endpoint}")
	private String endpoint;

	@Value(value="${oss.accessKeyId}")
	private String accessKeyId;

	@Value(value="${oss.accessKeySecret}")
	private String accessKeySecret;

	@Value(value="${oss.bucketName}")
	private String bucketName;

	@Value(value="${oss.bucket.domain}")
	private String bucketDomain;

	//抽取上传文件冗余代码
	public String uploadFile(MultipartFile file) throws IOException {
		//准备上传
		String originalFilename = file.getOriginalFilename();
		//生成新的文件名
		String newFileName = UploadUtil.generateFileName(originalFilename);
		//文件夹名称
		String foldName = UploadUtil.generateFoldNameByDate(ossProjectParentFolder);
		//获取文件输入流
		InputStream inputStream = file.getInputStream();
		//上传到oss服务器
		UploadUtil.uploadSingleFile(endpoint,accessKeyId,accessKeySecret,newFileName,foldName,bucketName,inputStream);
		//拼接图片在服务器的路径
		String detailPicPath = bucketDomain+"/"+foldName+"/"+newFileName;
		return detailPicPath;
	}

	//保存项目信息到Redis
	@RequestMapping("save/project/info")
	@ResponseBody
	public ResultEntity<String> saveProjectInfo(@RequestBody ProjectVO projectVO, HttpSession httpSession){
		//登录检查从现有session中获取已登录的member对象
		MemberSignSuccessVO signSuccessVO = (MemberSignSuccessVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		//如果对象为null，提示登录
		if (signSuccessVO == null) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_ACCESS_DENIED);
		}
		String memberSignToken = signSuccessVO.getToken();
		ProjectVO project = (ProjectVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_INIT_PROJECT);
		String projectToken = project.getProjectTempToken();
		if (projectVO == null) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_PROJECTVO_DENIED);
		}
		//项目对象的令牌
		projectVO.setMemberSignToken(memberSignToken);
		projectVO.setProjectTempToken(projectToken);
		//项目发起人对象的令牌
		projectVO.getMemberLaunchInfoVO().setMemberSignToken(memberSignToken);
		projectVO.getMemberLaunchInfoVO().setProjectTempToken(projectToken);
		ResultEntity<String> resultEntity = projectRemoteService.saveProjectInfo(projectVO);
		if (ResultEntity.FAILED.equals(resultEntity.getResult())){
			return ResultEntity.failed(resultEntity.getMessage());
		}
		return ResultEntity.successNoData();
	}

	//上传详情图片,一个键多个值，用list接收文件数据
	@RequestMapping("project/upload/detailPicture")
	@ResponseBody
	public ResultEntity<String> uploadDetailPic(
			@RequestParam("detailPicList") List<MultipartFile> detailPicList
			, HttpSession httpSession) throws IOException {
		//登录检查从现有session中获取已登录的member对象
		MemberSignSuccessVO signSuccessVO = (MemberSignSuccessVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		//如果对象为null，提示登录
		if (signSuccessVO == null) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_ACCESS_DENIED);
		}
		//判断用户上传的文件是否有效
		if(!CrowdUtils.collectionEffectiveCheck(detailPicList)){
			return ResultEntity.failed(CrowdConstant.MESSAGE_UPLOAD_FILE_EMPTY);
		}
		//存放文件名
		//List<String> pathList = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		//遍历用户上传的文件
		for (MultipartFile detailPic : detailPicList) {
			//如果其中一个文件为空，就结束本次循环，执行下一个
			if(detailPic.isEmpty()){
				continue;
			}
			String detailPicPath = uploadFile(detailPic);
			sb.append(detailPicPath).append(",");
		}
		/*保存头图相关信息
		原方法：图片单独上传，并存入Redis
		现在改为： 把上传图片的路径返回到前台，保存到隐藏域，和其他数据一起提交
		String memberSignToken = signSuccessVO.getToken();
		ProjectVO projectVO = (ProjectVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_INIT_PROJECT);
		String projectToken = projectVO.getProjectTempToken();
		return projectRemoteService.saveDetailPictureListPath(memberSignToken,projectToken,pathList);*/
		return ResultEntity.successWithData(sb.toString());
	}
	//上传头图
	@RequestMapping("project/upload/headPicture")
	@ResponseBody
	public ResultEntity<String> uploadHeadPic(
			@RequestParam("headPicPath") MultipartFile headPic
			,HttpSession httpSession) throws IOException {
		//登录检查从现有session中获取已登录的member对象
		MemberSignSuccessVO signSuccessVO = (MemberSignSuccessVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		//如果对象为null，提示登录
		if (signSuccessVO == null) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_ACCESS_DENIED);
		}
		//排除上传文件为空的情况
		if (headPic.isEmpty()) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_UPLOAD_FILE_EMPTY);
		}
		String headPicPath = uploadFile(headPic);
		/*保存头图相关信息
		原方法：图片单独上传，并存入Redis
		现在改为： 把上传图片的路径返回到前台，保存到隐藏域，和其他数据一起提交
		String memberSignToken = signSuccessVO.getToken();
		ProjectVO projectVO = (ProjectVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_INIT_PROJECT);
		String projectToken = projectVO.getProjectTempToken();
		return projectRemoteService.saveHeadPicturePath(memberSignToken,projectToken,headPicPath);*/
		return ResultEntity.successWithData(headPicPath);
	}

	//点击同意协议按钮，初始化项目
	@RequestMapping("project/agree/protocol")
	public String agreeProtocol(HttpSession httpSession, Model model){
		//登录检查从现有session中获取已登录的member对象
		MemberSignSuccessVO signSuccessVO = (MemberSignSuccessVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		//如果对象为null，跳转到登录页面
		if (signSuccessVO == null) {
			model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_ACCESS_DENIED);
			return "member/login";
		}
		//从member对象中获取token
		String token = signSuccessVO.getToken();
		//调用远程方法初始化项目
		ResultEntity<ProjectVO> projectVOResultEntity = projectRemoteService.initCreation(token);
		//初始化失败抛出异常
		if(ResultEntity.FAILED.equals(projectVOResultEntity.getResult())){
			throw new RuntimeException(projectVOResultEntity.getMessage());
		}
		//获取初始化项目的对象存入session
		ProjectVO data = projectVOResultEntity.getData();
		httpSession.setAttribute(CrowdConstant.ATTR_NAME_INIT_PROJECT,data);
		//查询项目发起人信息并跳转页面
		return "redirect:/to/create/project/page";
	}

	@RequestMapping("to/create/project/page")
	public String toCreateProjectPage(HttpSession httpSession,Model model){
		//获取当前登录的用户
		MemberSignSuccessVO signSuccessVO = (MemberSignSuccessVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		//检查是否登录
		if (signSuccessVO == null) {
			model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_ACCESS_DENIED);
			return "member/login";
		}
		//获取登录成功用户的token
		String token = signSuccessVO.getToken();
		//通过token在Redis中获取用户ID查询发起人信息
		ResultEntity<MemberLaunchInfoPO> memberLaunchInfoPOByToken = managerRemoteService.getMemberLaunchInfoPOByToken(token);
		if(ResultEntity.FAILED.equals(memberLaunchInfoPOByToken.getResult())){
			throw new RuntimeException(memberLaunchInfoPOByToken.getMessage());
		}
		//将发起人信息存放到作用域传递到前台显示
		model.addAttribute("memberLaunchInfoPO",memberLaunchInfoPOByToken.getData());
		return "project/start-step-1";
	}
}
