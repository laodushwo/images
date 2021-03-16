package org.spring.springboot.ao;

import java.util.List;

public interface RedisAO {

	void saveCache(String cacheKey, String toSavedValue, int timeOutSeconds);

	String getFromCache(String cacheKey);

	void saveCacheList(String string, String content);

	List<String> getFromCacheList(String string);
}
