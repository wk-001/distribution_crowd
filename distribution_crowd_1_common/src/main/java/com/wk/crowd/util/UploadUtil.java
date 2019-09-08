package com.wk.crowd.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UploadUtil {

	//上传单个文件到OSS
	public static void uploadSingleFile(
			String endpoint,
			String accessKeyId,
			String accessKeySecret,
			String fileName,
			String folderName,
			String bucketName,
			InputStream inputStream) {
		try {

			// 创建OSSClient实例。
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

			// 存入对象的名称=目录名称+"/"+文件名
			String objectName = folderName + "/" + fileName;

			ossClient.putObject(bucketName, objectName, inputStream);

			// 关闭OSSClient。
			ossClient.shutdown();
		} catch (OSSException e) {
			e.printStackTrace();

			throw new RuntimeException(e.getMessage());
		} catch (ClientException e) {
			e.printStackTrace();

			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 生成文件名
	 * @param originalFileName 原始文件名
	 * @return
	 */
	public static String generateFileName(String originalFileName) {

		// 截取扩展名部分
		String extensibleName = "";

		if(originalFileName.contains(".")) {
			extensibleName = originalFileName.substring(originalFileName.lastIndexOf("."));
		}

		return UUID.randomUUID().toString().replaceAll("-", "")+extensibleName;
	}

	//根据日期生成目录名称
	public static String generateFoldNameByDate(String ossProjectParentFolder){
		return ossProjectParentFolder+"/"+new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

}
