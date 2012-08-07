package com.baofeng.dispatchexecutor.caller.impl;
import java.io.File;
import java.text.SimpleDateFormat;

import net.sf.json.JSONObject;

import org.apache.thrift.TException;
import com.baofeng.dispatchexecutor.caller.gen.Extra;
import com.baofeng.dispatchexecutor.bean.ExecuteResult;
import com.baofeng.dispatchexecutor.bean.HttpResult;
import com.baofeng.dispatchexecutor.bean.ParamCommons;
import com.baofeng.dispatchexecutor.caller.gen.ExecuteDoneReportResult;
import com.baofeng.dispatchexecutor.caller.gen.ExecutorService;
import com.baofeng.dispatchexecutor.utils.ExecuteCmdUtils;
import com.baofeng.dispatchexecutor.utils.HttpClientUtils;
import com.baofeng.dispatchexecutor.utils.PathUtils;
import com.baofeng.dispatchexecutor.utils.ProcessUtils;
import com.baofeng.dispatchexecutor.utils.ReplaceRealCmdUtils;
public class ExecutorServiceImpl implements ExecutorService.Iface{
	private static final HttpClientUtils httpClient = new HttpClientUtils();
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(ParamCommons.DATE_FORMAT);
	@Override
	public ExecuteDoneReportResult executeCmd(String cmd, long task_id,
			boolean undoReportHttp, String undoReportHttpUrl,int exec_type,boolean delTempFile,Extra extra) throws TException {
		Long task_record_undo_id = -1L;
		if(undoReportHttp){
			JSONObject undoJson = new JSONObject();
			undoJson.put(ParamCommons.REPORT_TASK_ID, task_id);
			undoJson.put(ParamCommons.REPORT_REAL_CMD, ReplaceRealCmdUtils.replaceCmdFromOriginalToReal(cmd));
			undoJson.put(ParamCommons.REPORT_SHELL_CMD,cmd);
//			undoJson.put(ParamCommons.REPORT_START_TIME,dateFormat.format(new Date()));
			undoJson.put(ParamCommons.REPORT_EXEC_TYPE, exec_type);
			undoJson.put(ParamCommons.REPORT_RUN_STATUS, ParamCommons.RUN_STATUS);
			undoJson.put(ParamCommons.REPORT_UNDO_IDENTIFIER,extra.getReport_undo_identifier());
		    try {
				HttpResult httpResult = httpClient.executePostRequest(undoReportHttpUrl, undoJson);
				task_record_undo_id = JSONObject.fromObject(httpResult.getContent()).getLong("task_record_undo_id");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("undo report url:"+undoReportHttpUrl+" ,delete undo id:"+task_record_undo_id);
		ExecuteDoneReportResult result = new ExecuteDoneReportResult();
		ExecuteResult localResult= ExecuteCmdUtils.executeCmd(cmd);
		System.out.println("execute result:"+localResult.toString());
		result.setTask_id(task_id);
		result.setStart_datetime(localResult.getStartTime().getTime());
		result.setEnd_datetime(localResult.getEndTime().getTime());
		result.setComplete_status(localResult.getExitCode());
		result.setSuccess(localResult.isSuccess());
		result.setReal_cmd(localResult.getRealReplacedCmd());
		result.setExec_type(exec_type);
		result.setExec_return_str(localResult.getReturnString());
		result.setTask_record_undo_id(task_record_undo_id);
		if(delTempFile){
			File tempFile = new File(localResult.getExecuteTempFilePath());
			if(tempFile.exists()){
				tempFile.delete();
			}
		}
		return result;
	}
	@Override
	public boolean ping() throws TException {
		return true;
	}
	@Override
	public String update(String updateShellCmd) throws TException {
		return ExecuteCmdUtils.executeCmd(updateShellCmd).getReturnString();
	}
	@Override
	public String getProjectFolderPath() throws TException {
		return PathUtils.getProjectFolderPath();
	}

}
