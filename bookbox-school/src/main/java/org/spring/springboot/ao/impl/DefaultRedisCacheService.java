package org.spring.springboot.ao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.spring.springboot.ao.RedisAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.amall.app.abstacts.AbstractAO;

@Service
public class DefaultRedisCacheService extends AbstractAO implements RedisAO {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
    /**
     * 把要存储的值放进缓存
     * 
     * @param cacheKey 键值，可以用来获取要存储的值
     * @param toSavedValue 要存储的值
     * @param timeOutSeconds 过期时间 单位为秒
     */
    public void saveCache(String cacheKey, String toSavedValue, int timeOutSeconds){
        if (toSavedValue == null){
            return;
        }
        try {
        	stringRedisTemplate.opsForValue().set(cacheKey, toSavedValue, timeOutSeconds, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * 把要存储的值放进缓存
     * 
     * @param cacheKey 键值，可以用来获取要存储的值
     * @param toSavedValue 要存储的值
     * @param timeOutSeconds 过期时间 单位为秒
     */
    public void saveCacheList(String cacheKey, String content){
        if (content == null){
            return;
        }
        try {
        	stringRedisTemplate.opsForSet().add(cacheKey, content);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据键值从缓存中获取数据
     * 
     * @param cacheKey 缓存的键值
     * @return
     */
    public String getFromCache(String cacheKey){
        try {
            String value = stringRedisTemplate.opsForValue().get(cacheKey);
            return value;
        }catch (Exception e){
            return null;
        }
    }
    
    /**
     * 根据键值从缓存中获取数据
     * 
     * @param cacheKey 缓存的键值
     * @return
     */
    public List<String> getFromCacheList(String cacheKey){
    	// 随机取出不重复的值
    	Set<String> rss = stringRedisTemplate.opsForSet().distinctRandomMembers(cacheKey, 20);
    	List<String> lss = new ArrayList<>();
    	for (String str: rss) {
    		lss.add(str);
    	}
    	return lss;
    	
//        try {
//        	long start = 0;
//        	long end = stringRedisTemplate.opsForList().size(cacheKey);
//        	List<Integer> list = random((int) start, (int) end);
//        	return stringRedisTemplate.opsForList().range(cacheKey, list.get(0), list.get(1));
//        }catch (Exception e){
//            return null;
//        }
    }
    
//    public static void main(String[] args) {
//    	for (int i=0; i<100; i++) {
//    		System.out.println(random(0, 47));
//    	}
//	}
    
    /**
     * 随机获取分页参数
     * 
     * @param start
     * @param end
     * @return
     */
    private static List<Integer> random(int start, int end) {
    	int z_start = 0, z_end = 0;
    	Random r=new Random();
    	for(int i=0;i<2;i++) {
    		if (i == 0) z_start = r.nextInt(end-start+1)+0;
    		else z_end = r.nextInt(end-start+1)+0;
    	}
    	if (z_end < z_start) {
    		int temp = z_start;
    		z_start = z_end; z_end = temp;
    	}
    	int value = z_end - z_start;
    	if (value < 10) {
    		z_start = end - 20;
    		z_end = end;
    	}
    	if (value > 20) {
			int [] a1 = {0,1,2,3,4,5,6,7,8,9,10};
			int [] a2 = {18,19,20,21,22,23,24,25,26,27};
			int index1 = (int) (Math.random() * a1.length);
			int index2 = (int) (Math.random() * a2.length);
			z_start = a1[index1];
			z_end = a2[index2];
    	}
    	List<Integer> l = new ArrayList<>();
    	l.add(z_start);
    	l.add(z_end);
    	return l;
    }
    
}
