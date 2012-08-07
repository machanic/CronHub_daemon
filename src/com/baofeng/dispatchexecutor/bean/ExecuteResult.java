package com.baofeng.dispatchexecutor.bean;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ExecuteResult {
	private int exitCode;
	private Date startTime;
	private Date endTime;
	private String returnString;
	private boolean success;
	private String originalCmd;
	private String realReplacedCmd;
	private String executeTempFilePath;
	
	
	public String getExecuteTempFilePath() {
		return executeTempFilePath;
	}
	public void setExecuteTempFilePath(String executeTempFilePath) {
		this.executeTempFilePath = executeTempFilePath;
	}
	public int getExitCode() {
		return exitCode;
	}
	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
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
	public String getReturnString() {
		return returnString;
	}
	public void setReturnString(String returnString) {
		this.returnString = returnString;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getOriginalCmd() {
		return originalCmd;
	}
	public void setOriginalCmd(String originalCmd) {
		this.originalCmd = originalCmd;
	}
	public String getRealReplacedCmd() {
		return realReplacedCmd;
	}
	public void setRealReplacedCmd(String realReplacedCmd) {
		this.realReplacedCmd = realReplacedCmd;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + exitCode;
		result = prime * result
				+ ((originalCmd == null) ? 0 : originalCmd.hashCode());
		result = prime * result
				+ ((realReplacedCmd == null) ? 0 : realReplacedCmd.hashCode());
		result = prime * result
				+ ((returnString == null) ? 0 : returnString.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + (success ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ExecuteResult other = (ExecuteResult) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (exitCode != other.exitCode)
			return false;
		if (originalCmd == null) {
			if (other.originalCmd != null)
				return false;
		} else if (!originalCmd.equals(other.originalCmd))
			return false;
		if (realReplacedCmd == null) {
			if (other.realReplacedCmd != null)
				return false;
		} else if (!realReplacedCmd.equals(other.realReplacedCmd))
			return false;
		if (returnString == null) {
			if (other.returnString != null)
				return false;
		} else if (!returnString.equals(other.returnString))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (success != other.success)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}
	
	
}
