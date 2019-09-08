package com.wk.crowd.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101100663717";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCV42R3siolYUkK9cLBBZuk9cE29BkU5ouGxMrDjVpUkz/piw8ioQWkFobsKIjuQMKarJEu6byZjYuf/SWl9lG1R7UoyY9Ta5u97X+MZa92YFyNjh/frI3eDlpv6ctoYl5Lc3aySv8pXagMbwZ3q5oRus8c6xgiHnmIOsLI1yKCk6eHaqw40LhFRapMfKS4+V+YOZq4yOSjP6qE9IId4rsHhR8r7F+GqkcOcR4jU5K94fkMNg93DS9e1fnQmNV2CfM+c9ZL6n78qPWjDuh2BLwCLIyaGIYpZkNd3SQpJvWw6KklYeLv4XVF8worPiGHTF+KeHZKGOXKmqy1mcx0PAWVAgMBAAECggEBAIzf9+F4FpyjXx9p9O2t86eLQ1n++kPmm2TDOT+qbyfmbb9273bAmKisc4sqXehv/XeakaniDVzU6FIqf2QIqqsTBnWbW5YwIWTU/3hZahx8XQC+f76eU4hNPTrKJl+pdK69iuXwDvo2CGgHQbDgHCJlzZuEal5G/jrH36MccFcAMv2EUGQF54PzMf9HDbKAeHBmgxaHS59aWO4t4jvs+pGqr/35RaJ7REYNh0jf6wjmEIikbErB24Y7YSl5ohDzsI5YHW0I1se9qzAKgxspxd/E7l558K7zgFEfDbJYNhKB9sGrsvYhe+2poVOm8rzrPgpZmmxzh46a/KGuvgoNq1ECgYEA5kS2kiIRExNLU6BmT4h0RQjyzapP9CChGDr2MGP+m/BImDJEO9hmWGb5NwIo+T1K4NmWBnaZeSJ0Xgb8M4ZOtJ51j7DF4CR1Zzu/S9e1Jl+J9qSF/7TuZamR4eD8RF4ELaSNmnZYfssb0lEzexeiJQQ6w16y4jh2eeBmDgPFCgcCgYEApqM+oCWmteUlHy+mGNYXK63i9Oap33nM3o9ShTifYrVDZyL63UKzjdPuabzoWf5VQXcMI97LkAhRazlVTIaf0xstfX7FtlUdnc6svtuaAgU02wDK5vkMKMLXzpYk59c5GH1Prw6BQdNPQD3jDrBPKga7oD3hhFoF9jvgmyzv/IMCgYAdFO6N9Dl91ED+NdLWFqDAm0m3CdyBM6HE4qV3JRnh7e3tMuQFPuZmIdM1zFMKb2BGl5RoCpCFiQFzYqR1iW/lReIJHMVQ8qnVOOmHFZ4uU4CkItE9o0c8lbbz9StHIyyZ6x9rM6knklVYMaMz159F14voYsxq38Cu0dVxza4T/wKBgETVqkj9RKBex7baOAKKbYfdjKMdpY5rAfXtvmjJuw1LU0yHZpl/tCwiH5oOLuz94rnoM1ud7PjSds+UH2g9NEhAMcplU8a94j4N4c31aOjsvai56xb+SceMtRjhbN1YSn2TRhdKJprKbzJaXjDzI8B4h4jqBv5lMZFFqsJvDbOBAoGANh4OlqMrKmm+p6StZdEzWFuWX7sosp2D4ukAgHnNjCtOPy5l/+aot7CRTsa25BwIDLRK3NJwSWxYqBIorjMEWmM0rBB0WkFNsVQDY/A06zNu0NCHqXTPLRp63LK3qXMOt50BIk9NYEFGRACHUMzNuOVu/E4kK2R4/Xt2BdlT7F4=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAleNkd7IqJWFJCvXCwQWbpPXBNvQZFOaLhsTKw41aVJM/6YsPIqEFpBaG7CiI7kDCmqyRLum8mY2Ln/0lpfZRtUe1KMmPU2ubve1/jGWvdmBcjY4f36yN3g5ab+nLaGJeS3N2skr/KV2oDG8Gd6uaEbrPHOsYIh55iDrCyNcigpOnh2qsONC4RUWqTHykuPlfmDmauMjkoz+qhPSCHeK7B4UfK+xfhqpHDnEeI1OSveH5DDYPdw0vXtX50JjVdgnzPnPWS+p+/Kj1ow7odgS8AiyMmhiGKWZDXd0kKSb1sOipJWHi7+F1RfMKKz4hh0xfinh2ShjlypqstZnMdDwFlQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://xuz7a2.natappfree.cc/pay/notify.html";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://xuz7a2.natappfree.cc/pay/return.html";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关,正式的和沙箱的不同
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
}

