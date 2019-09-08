package com.wk.crowd.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO implements Serializable {
	private String loginacct;

	private String userpswd;

	private String phoneNum;

	private String randomCode;
}
