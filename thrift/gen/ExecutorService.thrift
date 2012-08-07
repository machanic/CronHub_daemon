struct ExecuteDoneReportResult{
	1:i64 task_id,
	2:string real_cmd,
	3:i32 complete_status,
	4:bool success,
	5:i64 start_datetime,
	6:i64 end_datetime,
	7:i32 exec_type,
	8:string exec_return_str,
	9:i64 task_record_undo_id,
}
struct Extra{
	1:string report_undo_identifier,
}
service ExecutorService {
	ExecuteDoneReportResult executeCmd(1:string cmd,2:i64 task_id,3:bool undoReportHttp,4:string undoReportHttpUrl,5:i32 exec_type,6:bool delTempFile,7:Extra extra);
	bool ping();
	string update(1:string updateShellCmd);
	string getProjectFolderPath();
}