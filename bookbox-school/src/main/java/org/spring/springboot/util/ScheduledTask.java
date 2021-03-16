package org.spring.springboot.util;

import javax.annotation.Resource;

import org.spring.springboot.ao.RedisAO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

	@Resource
    private RedisAO redisAO;

    private static final String TOKEN_KEY = "wechat_access_token2";

    // 项目启动2秒中之后执行一次，然后每90min执行一次，单位都为ms
    @Scheduled(fixedRate= 1000*60*90, initialDelay = 2000)
    public void getToken(){
        //1. 获取token
        String access_token = WeChatTokenUtil.getToken();
//        System.out.println("从微信服务器获取的token======"+access_token);
        redisAO.saveCache(TOKEN_KEY, access_token, 60*90);
//        String getToken = redisAO.getFromCache(TOKEN_KEY);
//        System.out.println("从redis中获取的token === "+getToken);
    }

//	@Scheduled(cron = "0 0 2 1 * ?")
//	public void cleanLog() {
//		System.out.println("每月1号零晨2点执行!>>>>>>>>>>>>>>开始清理日志>>>>>>>>>>>>>>");
////		logsMapper.autoDelByTime(time);
////		deviceOperateMapper.autoDelByTime(time);
//		System.out.println(">>>>>>>>>>>>>>清理日志结束>>>>>>>>>>>>>>");
//	}

//	@Scheduled(cron = "0 0/5 * * * ?")
//	public void test1() {
//		System.out.println("每5分钟执行一次");
//	}

//	@Scheduled(cron = "0 0 23 * * ?")
//	public void test2() {
//		System.out.println("每天23点执行一次");
//	}
	
	

//	@Scheduled(fixedRate = 10000)
//	public void test3() {
//		System.oCut.println("每30秒执行一次");
		// List<LogsVO> logsList = logsMapper.listByTime("2019-11-10");
		// System.out.println("记录条数"+ logsList.size());
		// Integer years = UserMsgSuport.logSaveYears;
		// String time = getLastYear(years);
		// System.out.println(time);
//		try {
//			if (StringUtils.isBlank(UserMsgSuport.url)) {
//				return;
//			}
//			if (myWebSocketClient == null || myWebSocketClient.isClosed()) {
//				myWebSocketClient = new MyWebSocketClient(UserMsgSuport.url);
//				myWebSocketClient.connect();
//			}
//
//			if (myWebSocketClient.getReadyState().equals(READYSTATE.OPEN) && connectStatus == 1) {
//				DataMessageVO dataMessageVO = new DataMessageVO();
//				dataMessageVO.setWorkSerialno("service_client");
//				dataMessageVO.setWorkName("service_client");
//				dataMessageVO.setDeviceSerialno("service_client");
//				dataMessageVO.setDeviceName("service_client");
//				dataMessageVO.setIp("127.0.0.1");
//				// dataMessageVO.setUsername("service_clent");
//				dataMessageVO.setMsgType(1);
//				MessageVO messageVO = new MessageVO();
//				messageVO.setBizType(0);
//				messageVO.setMsgType(2);
//				messageVO.setToUser("service_client");
//				messageVO.setFromUser("service_client");
//				messageVO.setContent(JSON.toJSONString(dataMessageVO));
//
//				myWebSocketClient.send(JSON.toJSONString(messageVO));
//			}
//			if (myWebSocketClient.getReadyState().equals(READYSTATE.OPEN) && connectStatus == 2) {
//				MessageVO messageVO = new MessageVO();
//				messageVO.setStatus(1);
//				messageVO.setFromUser("service_client");
//				messageVO.setContent("心跳包检测");
//				messageVO.setMsgType(1);
//				myWebSocketClient.send(JSON.toJSONString(messageVO));
//			}
//			if (myWebSocketClient.getReadyState().equals(READYSTATE.NOT_YET_CONNECTED)) {
//				myWebSocketClient = new MyWebSocketClient(UserMsgSuport.url);
//				myWebSocketClient.connect();				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	public static String getLastYear(Integer years) {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		calendar.add(Calendar.YEAR, -years);
//		date = calendar.getTime();
//		return format.format(date);
//	}

}