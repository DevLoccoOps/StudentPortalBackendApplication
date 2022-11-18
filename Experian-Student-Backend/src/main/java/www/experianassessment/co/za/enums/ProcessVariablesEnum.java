package www.experianassessment.co.za.enums;

public enum ProcessVariablesEnum {
	/**
	 * Process names
	 */
	
	PROCESS_REGISTER_STUDENT("process-register-student"), 
	PROCESS_GET_STUDENT_INFORMATION("process-get-student-info"), 
	PROCESS_GET_STUDENTS_LIST_INFORMATION("process-get-students-info-list"), 
	PROCESS_UPDATE_STUDENT_INFORMATION("process-update-student-info"), 
	PROCESS_SEARCH_STUDENT_INFORMATION("process-search-student-info"), 
	
	/**
	 * Process variables
	 */
	
	ERROR_MESSAGE("ERROR_MESSAGE"), 
	WARNING_MESSAGE("WARNING_MESSAGE"), 
	INFO_MESSAGE("INFO_MESSAGE"), 
	SERVICE_SUCCESS("SERVICE_SUCCESS"),
	CONTINUE_PROCESS("CONTINUE_PROCESS"), 
	PARENT_PROCESS_ID("PARENT_PROCESS_ID"), 
	PARENT_EXECUTION_ID("PARENT_EXECUTION_ID"), 
	MY_PROCESS_ID("MY_PROCESS_ID"),
	MY_EXECUTION_ID("MY_EXECUTION_ID"), 
	CHILD_PROCESS_ID("CHILD_PROCESS_ID"), 
	CHILD_EXECUTION_ID("CHILD_EXECUTION_ID"),
	SUBPROCESS_DECISION("SUBPROCESS_DECISION"),
	SYSTEM_TIMEOUT("SYSTEM_TIMEOUT"), 
	SYSTEM_TIMEOUT_MESSAGE("SYSTEM_TIMEOUT_MESSAGE"),
	PROCESS_TIMER("PROCESS_TIMER"),
	PROCESS_TIMER_PROPERTY("PROCESS_TIMER_PROPERTY"),
	SYSTEM_INFO("SYSTEM_INFO"),
	TASK_CANCELLED("TASK_CANCELLED");

	private String variableName;

	ProcessVariablesEnum(String variableName) {
		this.variableName = variableName;
	}

	public String variableName() {
		return variableName;
	}

}

