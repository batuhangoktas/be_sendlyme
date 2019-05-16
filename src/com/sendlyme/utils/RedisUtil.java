package com.sendlyme.utils;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.sendlyme.modals.FileListModal;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

public class RedisUtil {

	// address of your redis server
	private static final String redisHost = "localhost";
	private static final Integer redisPort = 6379;

	// the jedis connection pool..
	private static JedisPool pool = null;
	private static RedisUtil redisInstance = null; 
	
	private RedisUtil() {
		// configure our pool connection
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(5);
		poolConfig.setMaxWaitMillis(1000);

		pool = new JedisPool(poolConfig, redisHost, redisPort);

	}
	public static RedisUtil getInstance() 
    { 
        if (redisInstance == null) 
        	redisInstance = new RedisUtil(); 
  
        return redisInstance; 
    } 

	public boolean createSession(String sessionId, String userId, String ip) {

		Map<String, String> map = new HashMap<>();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		map.put("userId", userId);
		map.put("user1time", String.valueOf(timestamp.getTime()));
		map.put("user1Ip", ip);
		
		Jedis jedis = pool.getResource();
		try {
			// save to redis
			jedis.hmset(sessionId, map);
			jedis.close();
			return true;

		} catch (JedisException e) {
			return false;
		}
	}

	public String joinSession(String sessionId, String ip) {
		Jedis jedis = pool.getResource();
		List<String> list = new ArrayList<String>();
		// after saving the data, lets retrieve them to be sure that it has really added
		// in redis
//        Map<String, String> retrieveMap = jedis.hgetAll(sessionId);
//        for (String keyMap : retrieveMap.keySet()) {
//            list.add(keyMap + " " + retrieveMap.get(keyMap));
//        }

		try {
			Map<String, String> retrieveMap = jedis.hgetAll(sessionId);
			jedis.close();
			if (!retrieveMap.get("userId").equalsIgnoreCase("")) {
					if(retrieveMap.get("user2Id")!=null)
					return retrieveMap.get("user2Id");
				else
				{
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				String userId = UUID.randomUUID().toString();
				retrieveMap.put("user2Id", userId);
				retrieveMap.put("user2Ip", ip);
				retrieveMap.put("user2time", String.valueOf(timestamp.getTime()));
				jedis.hmset(sessionId, retrieveMap);
				jedis.close();
				return userId;
				}
			}
		} catch (Exception e) {
			return "";
		}

		return "";
	}

	public boolean hasSessionSync(String sessionId) {
		Jedis jedis = pool.getResource();
		
		try {
			Map<String, String> retrieveMap = jedis.hgetAll(sessionId);
			jedis.close();
					if(retrieveMap.get("user2Id")!=null)
					return true;
				else
					return false;
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean userFile(String userId, String fileId) {

		Jedis jedis = pool.getResource();
		try {
			// save to redis
			jedis.sadd(userId, fileId);
			jedis.close();
			return true;

		} catch (JedisException e) {
			return false;
		}
	}
	public boolean saveFile(String fileId, String filename, String filePath, String status) {

		Jedis jedis = pool.getResource();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
			// save to redis
			Map<String, String> map = new HashMap<>();
			map.put("filename", filename);
			map.put("filePath", filePath);
			map.put("status", status);
			map.put("time",String.valueOf(timestamp.getTime()));
			
			jedis.hmset(fileId, map);
			jedis.close();
			return true;

		} catch (JedisException e) {
			return false;
		}
	}
	
	public String getSessionCoupleId(String sessionId, String userId) {
		Jedis jedis = pool.getResource();
		
		try {
			Map<String, String> retrieveMap = jedis.hgetAll(sessionId);
			jedis.close();
			String tempUserId = retrieveMap.get("userId");
			String tempUser2Id = retrieveMap.get("user2Id");
					
			if(tempUserId.equals(userId))
				return tempUser2Id;
			else if (tempUser2Id.equals(userId))
				return tempUserId;
			else
					return null;
		}catch (Exception e) {
			return null;
		}
	}
	
	public List<String> getFileListFromSourceId(String userId) {
		Jedis jedis = pool.getResource();
		
		try {
			Set<String> strList = jedis.smembers(userId);
			jedis.close();
			List<String> fileList = new ArrayList<String>();
			
			fileList.addAll(strList);
			
			return fileList;
			
		}catch (Exception e) {
			return null;
		}
	}
	
	public List<FileListModal> getFileListInfo(List<String> fileIdList) {
	
		List<FileListModal> fileList = new ArrayList<FileListModal>();
		
		try {
			for (String fileId : fileIdList) {
				Jedis jedis = pool.getResource();
				Map<String,String> retrieveMap = jedis.hgetAll(fileId);
				jedis.close();
				String filename = retrieveMap.get("filename");
				String status = retrieveMap.get("status");
				
				fileList.add(new FileListModal(fileId,filename,status));
			}
			
			
			return fileList;
			
		}catch (Exception e) {
			return null;
		}
	}

	public String getFile(String fileId) {
		Jedis jedis = pool.getResource();
	
		try {
				Map<String,String> retrieveMap = jedis.hgetAll(fileId);
				jedis.close();
				String filePath = retrieveMap.get("filePath");				
		
			return filePath;
			
		}catch (Exception e) {
			return null;
		}
	}
	
	public boolean tookFile(String fileId) {
		Jedis jedis = pool.getResource();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
				jedis.hset(fileId, "status","1");
				jedis.hset(fileId, "downloadtime",String.valueOf(timestamp.getTime()));
				jedis.close();
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	
	public List<String> getFileKeysLike() {
		Jedis jedis = pool.getResource();
		List<String> fileList = new ArrayList<String>();
		try {
			Set<String> strList = jedis.keys("f_*");
			strList.forEach(element -> {
				Map<String,String> fileMap = jedis.hgetAll(element);
				
				if(fileMap.get("status").equals("0"))
				{
					int position = element.lastIndexOf("_");
					long timeStamp = Long.parseLong(element.substring(position+1));
					long nowTimeStamp = System.currentTimeMillis();
					
					if(((nowTimeStamp - timeStamp)/1000/60) >= 15)
					{
						fileList.add(element);
					}
				}
			});
			
			jedis.close();
			
			return fileList;
			
		}catch (Exception e) {
			return null;
		}
	}
	
	public String getFileName(String fileId) {
		Jedis jedis = pool.getResource();
	
		try {
				Map<String,String> retrieveMap = jedis.hgetAll(fileId);
				jedis.close();
				String fileName = retrieveMap.get("filename");				
		
			return fileName;
			
		}catch (Exception e) {
			return null;
		}
	}
}
