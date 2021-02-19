package org.spring.springboot.util;

import lombok.Data;

/**
 * 统一下单提交为微信的参数
 * 
 * @author DS
 */
@Data
public class Unifiedorder {
	
	private String appid;			// 微信分配的公众账号ID（企业号corpid即为此appId）,例如：wxd678efh567hg6787
	private String mch_id;			// 商户id
	private String device_info;		// 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
	private String nonce_str;		// 随机字符串:数字+大写字母的组合，32位
	private String sign;			// 签名
	private String body;			// 商品或支付单简要描述
	private String detail;			// 商品名称明细列表
	private String attach; 			// 附加参数
	private String out_trade_no;	// 商户系统内部的订单号
	private String fee_type;		// 货币类型:符合ISO 4217标准的三位字母代码，默认人民币：CNY
	private Integer total_fee;		// 总金额
	private String spbill_create_ip;// APP和网页支付提交[用户端ip]，Native支付填调用微信支付API的机器IP。
	private String time_start;		// 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
	private String time_expire;		// 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010;最短失效时间间隔必须大于5分钟[支付宝是30分钟，同样30分钟]
	private String goods_tag;		// 商品标记，代金券或立减优惠功能的参数
	private String notify_url;		// 接收微信支付异步通知回调地址
	private String trade_type;		// 交易类型:JSAPI，NATIVE，APP
	private String product_id;		// trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
	private String limit_pay;		// no_credit--指定不能使用信用卡支付
	private String openid;			// trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
	
}
