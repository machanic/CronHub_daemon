package com.baofeng.dispatchexecutor.crontab;

import it.sauronsoftware.cron4j.SchedulingPattern;

public class CronItem {
	private SchedulingPattern pattern;
	private String cmd;
	public SchedulingPattern getPattern() {
		return pattern;
	}
	public void setPattern(SchedulingPattern pattern) {
		this.pattern = pattern;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public CronItem(SchedulingPattern pattern, String cmd) {
		this.pattern = pattern;
		this.cmd = cmd;
	}
	
}
