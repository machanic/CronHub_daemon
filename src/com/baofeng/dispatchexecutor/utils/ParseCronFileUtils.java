package com.baofeng.dispatchexecutor.utils;

import it.sauronsoftware.cron4j.InvalidPatternException;
import it.sauronsoftware.cron4j.SchedulingPattern;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.baofeng.dispatchexecutor.crontab.CronItem;

public class ParseCronFileUtils {
	public static CronItem getCronItemFromLine(String cronLine) throws InvalidPatternException{
		int firstCmdSpaceIndex =StringUtils.ordinalIndexOf(cronLine, " ", 5);
		String cmd =StringUtils.substring(cronLine, firstCmdSpaceIndex+1);
		String patternStr = cronLine.substring(0, firstCmdSpaceIndex);
		SchedulingPattern pattern = null;
		try{
			pattern = new SchedulingPattern(patternStr);
		}catch(InvalidPatternException e){
			System.err.println(patternStr + " is wrong!");
			throw e;
		}
		return new CronItem(pattern,cmd);
	}
	public static List<CronItem> parseCrontabFile(String filePath) throws IOException{
		return parseCrontabFile(FileUtils.readLines(new File(filePath)));
	}
	public static List<CronItem> parseCrontabFile(List<String> cronLines) throws IOException{
		List<CronItem> items = new ArrayList<CronItem>();
		for(String line : cronLines){
			if(StringUtils.isNotEmpty(line)){
			try{
				items.add(getCronItemFromLine(line));
			}catch(Exception e){
				continue;
			}
			}
		}
		return items;
	}
	public static void main(String[] args) throws IOException {
		for(CronItem item : ParseCronFileUtils.parseCrontabFile("D:/temp/cron.txt")){
			System.out.println(item.getCmd() + "!");
			System.out.println(item.getPattern().toString() + "!");
		}
	}
}
