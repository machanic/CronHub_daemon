package com.baofeng.dispatchexecutor.bean;

import java.util.Date;

public class ReportUndoResult {
	private int daemonId;
	private int runStatus;
	private Date startTime;
	private int execType;
	public int getDaemonId() {
		return daemonId;
	}
	public void setDaemonId(int daemonId) {
		this.daemonId = daemonId;
	}
	public int getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(int runStatus) {
		this.runStatus = runStatus;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public int getExecType() {
		return execType;
	}
	public void setExecType(int execType) {
		this.execType = execType;
	}
	
}
