package com.baofeng.dispatchexecutor.crontab;

import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

import java.io.IOException;

import org.apache.commons.exec.ExecuteException;

import com.baofeng.dispatchexecutor.bean.ExecuteResult;
import com.baofeng.dispatchexecutor.utils.ExecuteCmdUtils;


public class ShellExecutorTask extends Task{
	private String shellCmd;
	private String shellTempFilePath;
	
	public void setShellTempFilePath(String shellTempFilePath) {
		this.shellTempFilePath = shellTempFilePath;
	}

	public void setShellCmd(String shellCmd) {
		this.shellCmd = shellCmd;
	}

	@Override
	public void execute(TaskExecutionContext context) throws RuntimeException {
			ExecuteResult result = ExecuteCmdUtils.executeCmd(this.shellCmd, this.shellTempFilePath);
		
	}

}
