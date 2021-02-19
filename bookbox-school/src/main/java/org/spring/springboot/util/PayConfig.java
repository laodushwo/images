package org.spring.springboot.util;

public class PayConfig {

	// 签名方式
	public static String sign_type = "MD5";
		
	// 字符编码格式 目前支持utf-8
	public static String input_charset = "utf-8";
		
	// 微信订单查询接口
	public final static String DS_ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";
		
	// 微信支付订单生成地址
	public final static String DS_ORDER_XIADAN_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	// 微信退款接口
	public final static String DS_ORDER_TUIKUAN_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	// 微信提现接口
	public final static String DS_ACCOUNT_TIXIAN_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	
	// 微信提现查询
	public final static String DS_TX_QUERY_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";
	
	// 微信退款状态查询接口
	public final static String DS_ORDER_TUIKUAN_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	
	// 微信订单关闭
	public final static String DS_ORDER_CLOSE_URL = "https://api.mch.weixin.qq.com/pay/closeorder";

	public final static String DS_NOTIFY_URL = "www.baidu.com";
		
	/********************微信开放平台APP支付***********************/
	// 微信公众号（相关配置）
	public final static String DS_APP_ID = "";
	public final static String DS_MCH_ID = "";
	// 商户Key：改成公司申请的即可
	public final static String DS_KEY = "";
	
	/**********************微信服务平台*************************/
	public final static String WX_APP_ID = "";
	public final static String WX_MCH_ID = "";
	public final static String WX_APP_SECRET = "";
	// 商户Key
	public final static String WX_KEY = "";	
	
		
}
