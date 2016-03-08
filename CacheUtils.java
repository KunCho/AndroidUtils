package com.xindeguo.activity.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuguangxin.utils.Md5Utils;
import com.xindeguo.activity.logic.data.UserManager;


import android.os.Environment;
import android.text.TextUtils;

/**
 * 数据缓存工具类(支持基本数据类型、JSONObject、JSONArray、List<?>、Map<?, ?>以及已实现Serializable的对象)
 * 
 * @author wuguangxin
 * @date: 2015-3-9 上午10:46:15
 */
public class CacheUtils {
	private static String CACHE_DATA = Constants.Path.DATA;

	/**
	 * 存储Object
	 * @param key
	 * @param obj
	 * @return
	 */
	public static boolean put(String key, Object obj){
		return writeObject(key, obj);
	}
	
	/**
	 * 获取Object
	 * @param key
	 * @param obj
	 * @return
	 */
	public static Object get(String key){
		return readObject(key);
	}
	
	/**
	 * 存储String字符串
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public static void putString(String key, String value){
		writeText(key, value);
	}

	/**
	 * 获取String字符串
	 * @param key 键
	 * @return 如果获取失败，返回null
	 */
	public static String getString(String key){
		return getString(key, null);
	}
	
	/**
	 * 获取String字符串
	 * @param key
	 * @param defaultValue 默认值
	 * @return 如果获取失败，返回defaultValue
	 */
	public static String getString(String key, String defaultValue){
		Object object = readObject(key);
		if(object instanceof String){
			return (String) object;
		}
		return defaultValue;
	}
	
	/**
	 * 存储int
	 * @param key
	 * @param i
	 * @return
	 */
	public static boolean putInt(String key, int i){ 
		return writeObject(key, i);
	}
	
	/**
	 * 获取int
	 * @param key
	 * @return 如果获取失败，返回-1
	 */
	public static int getInt(String key){ 
		return getInt(key, -1);
	}
	
	/**
	 * 获取int
	 * @param key
	 * @param defaultValue 默认值
	 * @return 如果获取失败，返回defaultValue
	 */
	public static int getInt(String key, int defaultValue){ 
		Object object = readObject(key);
		if(object instanceof Integer){
			return (Integer) object;
		}
		return defaultValue;
	}
	
	/**
	 * 存储long
	 * @param key
	 * @param l
	 * @return
	 */
	public static boolean putLong(String key, long l){
		return writeObject(key, l);
	}
	
	/**
	 * 获取boolean
	 * @param key
	 * @return 如果获取失败，返回-1l
	 */
	public static long getLong(String key){
		return getLong(key, -1l);
	}
	
	/**
	 * 获取boolean
	 * @param key
	 * @param defaultValue 默认值
	 * @return 如果获取失败，返回defaultValue
	 */
	public static long getLong(String key, long defaultValue){ 
		Object object = readObject(key);
		if(object instanceof Long){
			return (Long) object;
		}
		return defaultValue;
	}
	
	/**
	 * 存储Float
	 * @param key
	 * @param f
	 * @return
	 */
	public static boolean putFloat(String key, float f){
		return writeObject(key, f);
	}
	
	/**
	 * 获取float
	 * @param key
	 * @return 如果获取失败，返回-1f
	 */
	public static float getFloat(String key){
		return getFloat(key, -1f);
	}
	
	/**
	 * 获取float
	 * @param key
	 * @param defaultValue 默认值
	 * @return 如果获取失败，返回defaultValue
	 */
	public static float getFloat(String key, float defaultValue){ 
		Object object = readObject(key);
		if(object instanceof Boolean){
			return (Float) object;
		}
		return defaultValue;
	}
	
	/**
	 * 存储Double
	 * @param key
	 * @param d
	 * @return
	 */
	public static boolean putDouble(String key, double d){
		return writeObject(key, d);
	}
	
	/**
	 * 获取double
	 * @param key
	 * @return 如果获取失败，返回 -1d
	 */
	public static double getDouble(String key){
		return getDouble(key, -1d);
	}
	
	/**
	 * 获取double
	 * @param key
	 * @param defaultValue 默认值
	 * @return 如果获取失败，返回defaultValue
	 */
	public static double getDouble(String key, double defaultValue){ 
		Object object = readObject(key);
		if(object instanceof Boolean){
			return (Double) object;
		}
		return defaultValue;
	}
	
	/**
	 * 存储boolean
	 * @param key
	 * @param b
	 * @return
	 */
	public static boolean putBoolean(String key, boolean b){
		return writeObject(key, b);
	}
	
	/**
	 * 获取boolean
	 * @param key
	 * @return 如果获取失败，返回 false
	 */
	public static boolean getBoolean(String key){
		return getBoolean(key, false);
	}
	
	/**
	 * 获取double
	 * @param key
	 * @param defaultValue 默认值
	 * @return 如果获取失败，返回defaultValue
	 */
	public static boolean getBoolean(String key, boolean defaultValue){ 
		Object object = readObject(key);
		if(object instanceof Boolean){
			return (Boolean) object;
		}
		return defaultValue;
	}
	
	/**
	 * 存储JSONObject
	 * @param key
	 * @param json
	 * @return
	 */
	public static void putJSONObject(String key, JSONObject json){
		if (json == null) {
			json = new JSONObject();
		}
		writeObject(key, json.toString()); 
	}

	/**
	 * 根据key获取JSONObject
	 * @param key 键
	 * @return
	 */
	public static JSONObject getJSONObject(String key){
		Object object = readObject(key);
		if(object != null && object instanceof String){
			try {
				return new JSONObject((String)object);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 存储JSONArray
	 * @param key 键
	 * @param jsonArray JSONArray对象
	 * @return
	 */
	public static void putJSONArray(String key, JSONArray jsonArray){
		if (jsonArray != null) {
			writeText(key, jsonArray.toString());
		}
	}

	/**
	 * 根据key获取JSONArray
	 * @param key 键
	 * @return
	 */
	public static JSONArray getJSONArray(String key){
		Object object = readObject(key);
		if(object != null && object instanceof JSONArray){
			try {
				return new JSONArray((String)object);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 存储List<?>
	 * @param key
	 * @param list
	 * @return
	 */
	public static boolean putList(String key, List<?> list){
		return writeObject(key, list);
	}
	
	/**
	 * 获取List<?>
	 * @param key
	 * @return
	 */
	public static List<?> getList(String key){
		Object obj = readObject(key);
		if(obj != null && obj instanceof List<?>){
			return (List<?>)obj;
		}
		return null;
	}
	
	/**
	 * 存储ArrayList<?>
	 * @param key
	 * @param arrayList
	 * @return
	 */
	public static boolean putArrayList(String key, ArrayList<?> arrayList){
		return writeObject(key, arrayList);
	}
	
	/**
	 * 获取ArrayList<?>
	 * @param key
	 * @return
	 */
	public static ArrayList<?> getArrayList(String key){
		Object obj = readObject(key);
		if(obj != null && obj instanceof ArrayList<?>){
			return (ArrayList<?>)obj;
		}
		return null;
	}
	
	/**
	 * 存储LinkedList<?>
	 * @param key
	 * @param linkedList
	 * @return
	 */
	public static boolean putLinkedList(String key, LinkedList<?> linkedList){
		return writeObject(key, linkedList);
	}
	
	/**
	 * 获取LinkedList<?>
	 * @param key
	 * @return
	 */
	public static LinkedList<?> getLinkedList(String key){
		Object obj = readObject(key);
		if(obj != null && obj instanceof LinkedList<?>){
			return (LinkedList<?>)obj;
		}
		return null;
	}
	
	/**
	 * 存储Map<?, ?>
	 * @param key
	 * @param map
	 * @return
	 */
	public static boolean putMap(String key, Map<?, ?> map){
		return writeObject(key, map);
	}
	
	/**
	 * 获取Map<?, ?>
	 * @param key
	 * @return
	 */
	public static Map<?, ?> getMap(String key){
		Object obj = readObject(key);
		if(obj != null && obj instanceof Map<?, ?>){
			return (Map<?, ?>)obj;
		}
		return null;
	}
	
	/**
	 * 存储HashMap<?, ?>
	 * @param key
	 * @param hashMap
	 * @return
	 */
	public static boolean putHashMap(String key, HashMap<?, ?> hashMap){
		return writeObject(key, hashMap);
	}
	
	/**
	 * 获取HashMap<?, ?>
	 * @param key
	 * @return
	 */
	public static HashMap<?, ?> getHashMap(String key){
		Object obj = readObject(key);
		if(obj != null && obj instanceof HashMap<?, ?>){
			return (HashMap<?, ?>)obj;
		}
		return null;
	}
	
	/**
	 * 存储LinkedHashMap<?, ?>
	 * @param key
	 * @param linkedHashMap
	 * @return
	 */
	public static boolean putLinkedHashMap(String key, LinkedHashMap<?, ?> linkedHashMap){
		return writeObject(key, linkedHashMap);
	}
	
	/**
	 * 获取LinkedHashMap<?, ?>
	 * @param key
	 * @return
	 */
	public static LinkedHashMap<?, ?> getLinkedHashMap(String key){
		Object obj = readObject(key);
		if(obj != null && obj instanceof LinkedHashMap<?, ?>){
			return (LinkedHashMap<?, ?>)obj;
		}
		return null;
	}
	
	/**
	 * 删除String
	 * @param key 键
	 * @param isPrivate 是否是用户私有的数据
	 * @return
	 */
	public static boolean deleteString(String key){
		return deleteText(key);
	}

	/**
	 * 删除JSONObject
	 * @param key 键
	 * @return
	 */
	public static boolean deleteJSONObject(String key){
		return deleteText(key);
	}

	/**
	 * 删除JSONArray
	 * @param key 键
	 * @return
	 */
	public static boolean deleteJSONArray(String key){
		return deleteText(key);
	}

	/**
	 * 删除XML
	 * @param key 键
	 * @return
	 */
	public static boolean deleteXML(String key){
		return deleteText(key);
	}

	public static boolean deleteText(String key){
		return FileUtils.deleteFile(readFile(key, CACHE_DATA));
	}

	/**
	 * 写入数据
	 * @param key 键(文件名)
	 * @param value 数据
	 * @return
	 */
	private static boolean writeText(String key, String value){
		if (!TextUtils.isEmpty(key)) {
			try {
				File file = createFile(key, CACHE_DATA);
				String encrypData = DESCryptUtil.getEncrypData(value);  // 加密的数据
				return FileUtils.writeTextToFile(encrypData, file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 序列化对象
	 * @param key
	 * @param obj
	 * @return
	 */
	private static boolean writeObject(String key, Object obj){
		if (TextUtils.isEmpty(key)) {
			return false;
		}
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			File file = createFile(key, CACHE_DATA);
			if(file != null && file.exists() && file.length() > 0){
				fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				if(oos != null){
					oos.writeObject(obj);
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			FileUtils.close(oos);
			FileUtils.close(fos);
		}
	}
	
	/**
	 * 反序列化对象
	 * @param key 文件名
	 * @return
	 */
	private static Object readObject(String key){
		if (TextUtils.isEmpty(key)) {
			return null;
		}
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			File file = readFile(key, CACHE_DATA);
			if(file != null && file.exists() && file.length() > 0){
				fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);
				if(ois != null){
					return ois.readObject();
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			FileUtils.close(fis);
			FileUtils.close(ois);
		}
	}

	/**
	 * 创建目标文件
	 * @param key
	 * @param dataType 分类存储文件目录（可自定义）
	 * @return
	 */
	private static File createFile(String key, String dataType){
		File file = getFile(key, dataType);
		if(file.getParentFile() != null && !file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * 获取目标文件
	 * @param key
	 * @param dataType 分类存储文件目录（可自定义）
	 * @return
	 */
	private static File readFile(String key, String dataType){
		File file = getFile(key, dataType);
		return file.exists() ? file : null;
	}

	private static File getFile(String key, String dataType){
		StringBuilder path = getCacheRoot().append(dataType).append(File.separator).append(Md5Utils.encode(key));
		File file = new File(getSDPath(), path.toString());
		return file;
	}

	private static StringBuilder getCacheRoot(){
		return new StringBuilder(Constants.Path.ROOT).append(File.separator).append(UserManager.getUserInfo().getUF_MOB());
	}

	/**
	 * 获取根目录 (内存存在，使用内存，否则使用外置SD卡)
	 * @return
	 */
	private static File getSDPath(){
		File sdCardPath = null;
		sdCardPath = Environment.getExternalStorageDirectory();
//		if(isExistSDCard()){
//			long totalExternalMemorySize = SystemUtils.getAvailExternalMemorySize();
//			System.out.println("totalExternalMemorySize = " + totalExternalMemorySize);
//			if(totalExternalMemorySize > 1024 * 1024 * 100){
//				rootPath = getDataDirectory();
//			} else {
//				rootPath = getExternalStorageDirectory();
//			}
//		} else {
//			rootPath = getExternalStorageDirectory();
//		}
		return sdCardPath;
	}

	/**
	 * 判断是否已经安装SD卡
	 * @return
	 */
	public static boolean isExistSDCard(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取手机扩展SD卡路径
	 * @return
	 */
	public static File getExternalStorageDirectory(){
		File externalStorageDirectory = Environment.getExternalStorageDirectory();
		if (externalStorageDirectory.exists()) {
			return externalStorageDirectory;
		}
		return null;
	}

	/**
	 * 获取手机内存路径
	 * @return
	 */
	public static File getDataDirectory(){
		File dataDirectory = Environment.getDataDirectory();
		if (dataDirectory.exists()) {
			return dataDirectory;
		}
		return null;
	}
}
