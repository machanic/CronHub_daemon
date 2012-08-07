package com.baofeng.dispatchexecutor.bean;

import java.util.Date;

public class ReportResult {
	private int daemonId;
	private int completeStatus;
	private String execReturnString;
	private int execType;
	private Date startTime;
	private Date endTime;
	private int currentRedoTimes;
	public int getDaemonId() {
		return daemonId;
	}
	public void setDaemonId(int daemonId) {
		this.daemonId = daemonId;
	}
	public int getCompleteStatus() {
		return completeStatus;
	}
	public void setCompleteStatus(int completeStatus) {
		this.completeStatus = completeStatus;
	}
	public String getExecReturnString() {
		return execReturnString;
	}
	public void setExecReturnString(String execReturnString) {
		this.execReturnString = execReturnString;
	}
	public int getExecType() {
		return execType;
	}
	public void setExecType(int execType) {
		this.execType = execType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getCurrentRedoTimes() {
		return currentRedoTimes;
	}
	public void setCurrentRedoTimes(int currentRedoTimes) {
		this.currentRedoTimes = currentRedoTimes;
	}
	
}
