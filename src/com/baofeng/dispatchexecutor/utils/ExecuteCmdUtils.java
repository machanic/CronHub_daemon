package com.baofeng.dispatchexecutor.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;

import com.baofeng.dispatchexecutor.bean.ExecuteResult;
import com.baofeng.dispatchexecutor.bean.ParamCommons;
public class ExecuteCmdUtils {
	private static String charCode = "utf-8";
	public static String makeTempFilePath(String cmd) throws UnsupportedEncodingException{
		String dir_path = PathUtils.getTempFolderPath()+File.separator+ParamCommons.SERVICE_PORT;
		File dir = new File(dir_path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		return dir_path+File.separator+MD5Utils.MD5Hash(cmd,charCode)+".sh";
	}
	public static ExecuteResult executeCmd(String cmd) {
		ExecuteResult result = null;
		try {
			result = executeCmd(cmd,makeTempFilePath(cmd));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result; 
	}
	public static ExecuteResult executeCmd(String cmd,String tempShellFilePath) {
		ExecuteResult result = new ExecuteResult();
		PrintWriter out = null;
		int exitValue = -1;
		File shellFile = new File(tempShellFilePath);//将这个命令写入shell，然后执行sh temp.sh，这样就可以避免不能执行cd /home/mac && python test.py的问题了
		boolean fileChanged = true;
		ByteArrayOutputStream stdout = null;
		try{
		if(shellFile.exists()){
			String content = FileUtils.readFileToString(shellFile,charCode);
			if(cmd.equals(content)){
				fileChanged = false;
			}
		}
		if(fileChanged){
			out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(tempShellFilePath),charCode));
			out.write(cmd);
			out.flush();
			out.close();
		}
		
		
		CommandLine commandLine = CommandLine.parse("sh "+tempShellFilePath); //执行那个temp的shell文件
		DefaultExecutor executor = new DefaultExecutor();
		//add return string ,stdout
		stdout = new ByteArrayOutputStream();//先放入ByteArrayOutputStream内部的toByteArray()方法返回的byte[]中
		ExecuteStreamHandler stream = new  PumpStreamHandler(stdout,stdout);//这样就把标准输出和标准错误输出都定向到stream里了，以供内存里返回给远程
		executor.setStreamHandler(stream);
		result.setOriginalCmd(cmd);
		result.setRealReplacedCmd(ReplaceRealCmdUtils.replaceCmdFromOriginalToReal(cmd));
		result.setStartTime(new Date());
		exitValue = executor.execute(commandLine); //得到运行返回"结果码",0 -- 成功, 其他 -- 失败s
		}catch(Exception e){
			e.printStackTrace();
			//System.out.println("GOD,ERROR!");
		}finally{
			result.setReturnString(stdout.toString());//这一句话就将 运行后的返回结果字符串放入了result的ReturnString里
			result.setExitCode(exitValue); //
			result.setSuccess(exitValue == ParamCommons.SUCCESS_EXIT_CODE?true:false);
			result.setEndTime(new Date());
			result.setExecuteTempFilePath(tempShellFilePath);
			if(null!=stdout){
				try {
				stdout.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}};
			
		}
		return result;
	}
	public static void main(String[] args) {
		ExecuteResult result = ExecuteCmdUtils.executeCmd("cd /home/machen && python hello.py");
		System.out.println(result.getExitCode());
		System.out.println(result.getReturnString());
		System.out.println(result.getStartTime().toString());
		System.out.println(result.getEndTime().toString());
	}
}
