package org.spring.springboot.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
 
@SuppressWarnings("restriction")
public class Base64Utils {
 
	public static void main(String[] args) throws Exception {
 
		//本地图片地址
//		String url = "D:/logs/20190604160310.jpg";
		String url = "F:\\ui\\banner\\banner_anli.png";
		//在线图片地址
//		String string = "http://bpic.588ku.com//element_origin_min_pic/17/03/03/7bf4480888f35addcf2ce942701c728a.jpg";
		
		String str = Base64Utils.ImageToBase64ByLocal(url);
		
//		String ste = Base64Utils.ImageToBase64ByOnline(string);
		
		System.out.println(str.length());
		
//		Base64Utils.Base64ToImage(str,"C:/Users/Administrator/Desktop/test1.jpg");
		
//		Base64Utils.Base64ToImage(ste, "C:/Users/Administrator/Desktop/test2.jpg");
		String imgData="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\r\n" + 
				"DlriqIytZf7oIIzwBTTxar4aiiiirf1MbexG/IfdDV1M0LrTp8gbp9xES0hf74IJygBBT5apYaii\r\n" + 
				"iivOCK+uidZY73ZN5dytyvxezy9z0767b774ILyAABTxapYKiiizjC/riUsTrTShH3/6qQVbJzf7\r\n" + 
				"7777777zzwILigABTwL5YrhQAg/6x1OnGpk7u17olgqtB79df77777777zzwJwCgABTxb5aLxRQQ\r\n" + 
				"WvOPyEgZYwFSp3RWtWdr9df77777777yiBTwCAABThZZarxRRS4c6xtYp3d5fyDSFZVuMv5df777\r\n" + 
				"777776IBbwAAABThb5arxRhD0miSS2EtkMOBLZfN8Min4F/7777777wAIBbyoAABShb4apRCgmuo\r\n" + 
				"dtiAEoUIcWZ/ZtwIeN8z7777777744ww4LIAABShZZa5QixCCKiceNYz+Vc1OHNTcthDS/8A++++\r\n" + 
				"+++++40+CCEAAWoWWCkIoo4w22gqaMXRPOb5wP673/qW+++++++++++88+CCAAAWoWSuQogo25M/\r\n" + 
				"JcA4XW/lyEiziCx6GW+2++++++++qeu+KCAUAWoeKSokEspAVmJe5MWOS7OdywbV2G+6iC+6++++\r\n" + 
				"++2e++uSAQAcCq2K0UU0rbfySXSmDdeuVPXLzzYxJKGCCC2+++++q68++eAAM4aSK0UYUQ390KhI\r\n" + 
				"gp865H+AT3vaJKXZCrKCC+++++uG++++IU4Gmm2Ioo8GynRF1wy0IdDsDzHDWsEn8y2WyCCW++++\r\n" + 
				"++++6yQ4Umqaokkgo4DXF5fL9iUFLzIERtYaF0wMeuoXCS++++uCW6CC8MmiaeYYUkuhD/FFDfPB\r\n" + 
				"69NB3NBMmKxKWUKmYwiGe++6CCCGeGoKyaekko4UuNf9BdzJ9LPvRpFNHRDolXtui8sYSW+++iCe\r\n" + 
				"u+++uSCOg4cUAc4E99BpbT//AGa8iXZbRc0TRknILZMwsvuogHvtvotgpmjGBIOBPtF3eQecYya6\r\n" + 
				"w3y5SVXqRHr0MXU5S6vHIghvuAIgguonpDIOAHPHMDbQaUX3W4PR+S3e6RYEwrBS8+4UlsQFvvAA\r\n" + 
				"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\r\n" + 
				"KIzdTceIsprmS8zR4+bx6//Z";
		Base64ToImage(imgData, "d:/logs/dd.jpg");
	}
 
	/**
	 * 本地图片转换成base64字符串
	 * @param imgFile	图片本地路径
	 * @return
	 *
	 * @author ZHANGJL
	 * @dateTime 2018-02-23 14:40:46
	 */
	public static String ImageToBase64ByLocal(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
 
 
		InputStream in = null;
		byte[] data = null;
 
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
 
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
 
	
	
	/**
	 * 在线图片转换成base64字符串
	 * 
	 * @param imgURL	图片线上路径
	 * @return
	 *
	 * @author ZHANGJL
	 * @dateTime 2018-02-23 14:43:18
	 */
	public static String ImageToBase64ByOnline(String imgURL) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		try {
			// 创建URL
			URL url = new URL(imgURL);
			byte[] by = new byte[1024];
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			InputStream is = conn.getInputStream();
			// 将内容读取内存中
			int len = -1;
			while ((len = is.read(by)) != -1) {
				data.write(by, 0, len);
			}
			// 关闭流
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data.toByteArray());
	}
	
	
	/**
	 * base64字符串转换成图片
	 * @param imgStr		base64字符串
	 * @param imgFilePath	图片存放路径
	 * @return
	 *
	 * @author ZHANGJL
	 * @dateTime 2018-02-23 14:42:17
	 */
	public static boolean Base64ToImage(String imgStr,String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
 
		if (StringUtils.isBlank(imgStr)) // 图像数据为空
			return false;
 
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
 
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
 
			return true;
		} catch (Exception e) {
			return false;
		}
 
	}
 
	
}
