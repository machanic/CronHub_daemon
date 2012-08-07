package com.baofeng.dispatchexecutor.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baofeng.dispatchexecutor.bean.ExecuteResult;

public class ReplaceRealCmdUtils {
	private static final Pattern pattern = Pattern.compile("`(.+?)`"); //得到`里面的内容的正则
	public static String replaceCmdFromOriginalToReal(String cmd){
		Matcher matcher = pattern.matcher(cmd);  
		StringBuffer realCmd = new StringBuffer();//返回的结果的StringBuffer,为何是StringBuffer，因为appendReplacement和appendTail要传入
		while(matcher.find()){ //每找到一个``里的
			String subCmd = matcher.group(1);//就拿出来这个比如date "+%Y%m%d" -d "-1 days"子命令额外再在命令行上执行一遍
			ExecuteResult subResult = ExecuteCmdUtils.executeCmd(subCmd);
			String subReturnString = subResult.getReturnString().replaceAll("\n", "");//得到这个子命令的运行结果，然后去掉换行符
			matcher.appendReplacement(realCmd, subReturnString);//详见API说明,注意放在while循环里
		}
		matcher.appendTail(realCmd);//替换掉完了后余下的字符追加上，详见API说明
		return realCmd.toString();
	}
	public static void main(String[] args) {
		String real = ReplaceRealCmdUtils.replaceCmdFromOriginalToReal("perl hello.pl `date \"+%Y%m%d\" -d \"-1 days\"` `date`");
		String real2 = ReplaceRealCmdUtils.replaceCmdFromOriginalToReal("perl hello.pl");
		System.out.println(real);
		System.out.println(real2);
	}
	
}
