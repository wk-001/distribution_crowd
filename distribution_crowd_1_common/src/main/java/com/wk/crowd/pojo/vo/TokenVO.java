package com.wk.crowd.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO {

	// 用户登录系统后，系统分配的token值，用于识别用户身份。
	// 用户的id可以根据token值查询Redis得到
	private String memberSignToken;

	// 在Redis中临时存储项目数据的token值
	private String projectTempToken;

}
