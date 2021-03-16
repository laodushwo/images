package org.spring.springboot.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.spring.springboot.ao.RedisAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amall.admin.commons.util.Q;
import com.amall.app.abstacts.AbstractService;
import com.amall.commons.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeixinUtils extends AbstractService {
	
	@Autowired
	private RedisAO redisAO;
	
	/**
	 * 执行请求
	 * 
	 * @param action
	 * @param map
	 * @param method
	 * @return
	 */
	public static String execute(String action, String json, String method) {
		String result = null;
		try {
			URL url = new URL(action);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod(method);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setRequestProperty("Content-Type", "application/json; charset=utf-8");  
			http.setRequestProperty("Accept-Charset", "UTF-8");  
			http.setRequestProperty("contentType", "UTF-8"); 
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			OutputStream os = http.getOutputStream();
			if (StringUtils.isNotBlank(json)) {
				os.write(json.getBytes("UTF-8"));// 传入参数
			}
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			result = new String(jsonBytes, "UTF-8");
			os.flush();
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	/**
	 * 依据文件id下载文件
	 * 
	 * @param mediaId 媒体id
	 * @throws Exception
	 */
	public static InputStream getInputStream(String url) {
		 InputStream is = null;
		 try {
			 URL urlGet = new URL(url);
			 HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			 http.setRequestMethod("GET"); // 必须是get方式请求
			 http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			 http.setDoOutput(true);
			 http.setDoInput(true);
			 System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			 System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			 http.connect();
			 // 获取文件转化为byte流
			 is = http.getInputStream();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 return is;
	}
	
	/**
	 * 获取微信JSSDK的ticket
	 * 
	 * @author Benson
	 */
	private String getJSSDKTicket(String access_token, String appid) {
		String returnString = "";
		String requestUrl = getMsg("wx.weixin_jssdk_ticket_url", new String[] { access_token });
		JSONObject jsonObject = JSONObject.parseObject(execute(requestUrl, null, "GET"));
		System.out.println("jssdk_json=" + JSON.toJSONString(jsonObject));
		// 如果请求成功
		if (null != jsonObject) {
			// 05-08修改，根据access_token返回信息判断是否有效，若无效则更新accesstoken
			// {"errcode":40001,"errmsg":"invalid credential, access_token is invalid or not latest hints: [2EDcEEPce-C40yza!]"}
			int codeVal = jsonObject.getInteger("errcode");
			returnString = jsonObject.getString("ticket");
		}
		return returnString;
	}

	/**
	 * 签名获取信息
	 * 
	 * @param result
	 * @param appId
	 * @param appSecret
	 * @param url
	 */
	public void sign(Result result, String appId, String appSecret, String url) {
		String accessToken = redisAO.getFromCache(getMsg("redis_accesstoken_key"));//getAccessToken(appId).getAccessToken();
		String jsapi_ticket = getJSSDKTicket(accessToken, appId);
		System.out.println("jsapi_ticket=" +jsapi_ticket + "appid="+ appId + "secret="+ getMsg("secret"));
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		result.setModel("url", url);
		result.setModel("jsapi_ticket", jsapi_ticket);
		result.setModel("nonceStr", nonce_str);
		result.setModel("timestamp", ""+timestamp);
		result.setModel("signature", signature);
		result.setModel("appId", appId);
	}

	/**
	 * 随机加密
	 * 
	 * @param hash
	 * @return
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**
	 * 产生随机串--由程序自己随机产生
	 * 
	 * @return
	 */
	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 由程序自己获取当前时间
	 * 
	 * @return
	 */
	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	// 数组转字符串
	public static String ArrayToString(String[] arr) {
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			bf.append(arr[i]);
		}
		return bf.toString();
	}

	// sha1加密
	public static String SHA1Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	/**
	 * 字节转字符串，用于验证签名
	 * 
	 * @param bytes
	 * @return
	 */
	private static String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}
	
	/**
	 * 向微信服务器上传文件
	 * 
	 * @param fileType  文件类型(语音或者是图片)(对于文档不适合)
	 * @param filePath 文件的存储路径
	 * @param accessToken accessToken
	 * @return json的格式{"media_id":
	 *         "nrSKG2eY1E_svLs0Iv2Vvh46PleKk55a47cNO1ZS5_pdiNiSXuijbCmWWc8unzBQ"
	 *         ,"created_at":1408436207,"type":"image"}
	 */

	public JSONObject uploadFile(String fileType, String filePath, String accessToken) throws Exception {
		// 上传文件请求路径
		String action = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type="+fileType;
		URL url = new URL(action);
		String result = null;
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("上传的文件不存在");
		}
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="	+ BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		JSONObject jsonObj = JSONObject.parseObject(result);

		return jsonObj;

	}
	
	public static File saveUrlAs(String url, String filePath, String method) {
	    System.out.println("filePath:" + filePath);
	    //创建不同的文件夹目录
	    File file = new File(filePath);
	    //判断文件夹是否存在
	    if (!file.exists()) {
	        //如果文件夹不存在，则创建新的的文件夹
	        file.mkdirs();
	    }
	    FileOutputStream fileOut = null;
	    HttpURLConnection conn = null;
	    InputStream inputStream = null;
	    try {
	        // 建立链接
	        URL httpUrl = new URL(url);
	        conn = (HttpURLConnection) httpUrl.openConnection();
	        // 以Post方式提交表单，默认get方式
	        conn.setRequestMethod(method);
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        // post方式不能使用缓存
	        conn.setUseCaches(false);
	        // 连接指定的资源
	        conn.connect();
	        // 获取网络输入流
	        inputStream = conn.getInputStream();
	        BufferedInputStream bis = new BufferedInputStream(inputStream);
	        // 判断文件的保存路径后面是否以/结尾
	        if (!filePath.endsWith("/")) {
	            filePath += "/";
	        }
	        // 写入到文件（注意文件保存路径的后面一定要加上文件的名称）
	        fileOut = new FileOutputStream(filePath + Q.getUuid() + ".mp3"); // ".amr"
	        BufferedOutputStream bos = new BufferedOutputStream(fileOut);
	        byte[] buf = new byte[4096];
	        int length = bis.read(buf);
	        // 保存文件
	        while (length != -1) {
	            bos.write(buf, 0, length);
	            length = bis.read(buf);
	        }
	        bos.close();
	        bis.close();
	        conn.disconnect();
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("抛出异常！！");
	    }
	    return file;
	}
	
	/**
     * 用于上传本地文件到服务器
     *
     * @param filePath
     * @param remoteUrl
     * @return
     * @throws IOException
     */
    public static String uploadLocalFile2Remote(String filePath, String remoteUrl) throws IOException {
        String path = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse httpResponse = null;
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000000).build();
        HttpPost httpPost = new HttpPost(remoteUrl);
        httpPost.setConfig(requestConfig);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

        File file = new File(filePath);
        if(!file.exists()) throw new FileNotFoundException("服务器文件不存在");
        multipartEntityBuilder.addBinaryBody("file", file);
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);

        httpResponse = httpClient.execute(httpPost);
        HttpEntity responseEntity = httpResponse.getEntity();
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        //返回值
        String result = "";
        if (statusCode == HttpStatus.SC_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
            StringBuffer buffer = new StringBuffer();
            String str = "";
            while (!StringUtils.isEmpty(str = reader.readLine())) {
                buffer.append(str);
            }
            result = buffer.toString();
        }
        httpClient.close();
        if (httpResponse != null) {
            httpResponse.close();
        }
        System.out.println(result);
//        Map<String, Object> map = mapper.readValue(result, Map.class);
//        boolean success = (boolean) map.get("Success");
//        if(!success){
//            throw new IOException("上传至文件服务器失败");
//        }
//        Object pathObj = map.get("FileDownLoadUrl");
//        if (pathObj != null) {
//            path = pathObj.toString();
//        }
        return path;
    }
    
    /**
     * amr格式转mp3格式
     * 
     * @param sourcePath
     * @param targetPath
     */
//    public static void changeToMp3(String sourcePath, String targetPath) {  
//        File source = new File(sourcePath);  
//        File target = new File(targetPath);  
//        AudioAttributes audio = new AudioAttributes();  
//        Encoder encoder = new Encoder();  
//  
//        audio.setCodec("libmp3lame"); 
//        EncodingAttributes attrs = new EncodingAttributes();  
//        attrs.setFormat("mp3");  
//        attrs.setAudioAttributes(audio);  
//  
//        try {  
//            encoder.encode(source, target, attrs);  
//        } catch (IllegalArgumentException e) {  
//            e.printStackTrace();  
//        } catch (InputFormatException e) {  
//            e.printStackTrace();  
//        } catch (EncoderException e) {  
//            e.printStackTrace();  
//        }  
//    } 
    
    /**
     * 用于上传文件流到服务器
     *
     * @param inputStream
     * @param remoteUrl
     * @return
     * @throws IOException
     */
    public static String uploadLocalFile2Remote(InputStream inputStream, String remoteUrl) throws IOException {
    	ObjectMapper mapper = new ObjectMapper();
        String path = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse httpResponse = null;
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000000).build();
        HttpPost httpPost = new HttpPost(remoteUrl);
        httpPost.setConfig(requestConfig);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

        multipartEntityBuilder.addBinaryBody("file", inputStream, ContentType.MULTIPART_FORM_DATA, Q.getUuid()+".amr");
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);

        httpResponse = httpClient.execute(httpPost);
        HttpEntity responseEntity = httpResponse.getEntity();
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        //返回值
        String result = "";
        if (statusCode == HttpStatus.SC_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
            StringBuffer buffer = new StringBuffer();
            String str = "";
            while (!StringUtils.isEmpty(str = reader.readLine())) {
                buffer.append(str);
            }
            result = buffer.toString();
        }
        httpClient.close();
        if (httpResponse != null) {
            httpResponse.close();
        }
        return result;
    }
	
	
	public static void main(String[] args) {

		
	}
}