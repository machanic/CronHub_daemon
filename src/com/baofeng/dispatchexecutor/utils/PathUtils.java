package com.baofeng.dispatchexecutor.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;


public class PathUtils {
	public synchronized static final String getProjectFolderPath(){
		String path = null;
	 try {
		//path = URLDecoder.decode(ProjectPathUtils.class.getClassLoader().getResource("").toString(),"UTF-8");
		 File directory = new File("");//设定为当前文件夹
		 path = directory.getAbsolutePath();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return path;
	}
	public synchronized static final String getTempFolderPath(){
		return System.getProperty("java.io.tmpdir");
	}
}
