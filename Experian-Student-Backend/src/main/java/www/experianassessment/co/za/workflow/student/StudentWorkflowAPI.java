package www.experianassessment.co.za.workflow.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.experianassessment.co.za.enums.ProcessVariablesEnum;
import www.experianassessment.co.za.enums.StudentVariablesEnum;
import www.experianassessment.co.za.reply.StudentReply;
import www.experianassessment.co.za.request.StudentRequest;
import www.experianassessment.co.za.request.reply.model.StudentInfo;

@RestController
@RequestMapping("/student")
public class StudentWorkflowAPI {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentWorkflowAPI.class);

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private TaskService taskService;

	@PostMapping("/register")
	public StudentReply registerStudent(@RequestBody StudentRequest request) {
		StudentReply reply = new StudentReply();
		try {
			Map<String, Object> variables = new HashMap<String, Object>();

			identityService.setAuthenticatedUserId(request.getUsername());

			variables.put(StudentVariablesEnum.STUDENT_INFO.variableName(), request.getStudentInfo());

			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(ProcessVariablesEnum.PROCESS_REGISTER_STUDENT.variableName(), variables);

			HistoricVariableInstance continueProcess = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.CONTINUE_PROCESS.variableName()).singleResult();

			HistoricVariableInstance serviceSuccess = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.SERVICE_SUCCESS.variableName()).singleResult();

			HistoricVariableInstance errorMessage = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.ERROR_MESSAGE.variableName()).singleResult();

			HistoricVariableInstance warningMessage = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.WARNING_MESSAGE.variableName()).singleResult();

			if (!(Boolean) continueProcess.getValue() || !(Boolean) serviceSuccess.getValue()) {
				if (errorMessage != null && errorMessage.getValue() != null) {
					String errorMessageString = errorMessage.getValue().toString();
					reply.setErrorMessage(errorMessageString);
				}

				if (warningMessage != null && warningMessage.getValue() != null) {
					String warningMessageString = warningMessage.getValue().toString();
					reply.setWarningMessage(warningMessageString);
				}

				reply.setSuccess(false);
			} else {
				reply.setSuccessMessage("Student Registered Successfully ");
				reply.setSuccess(true);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			reply.setSuccess(false);
			reply.setErrorMessage(e.getMessage());
		}

		return reply;

	}

	@PostMapping("/getStudentInfo")
	public StudentReply getStudentInfo(@RequestBody StudentRequest request) {
		StudentReply reply = new StudentReply();
		try {
			Map<String, Object> variables = new HashMap<String, Object>();

			identityService.setAuthenticatedUserId(request.getUsername());

			variables.put(StudentVariablesEnum.STUDENT_INFO.variableName(), request.getStudentInfo());

			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
					ProcessVariablesEnum.PROCESS_GET_STUDENT_INFORMATION.variableName(), variables);

			HistoricVariableInstance continueProcess = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.CONTINUE_PROCESS.variableName()).singleResult();

			HistoricVariableInstance serviceSuccess = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.SERVICE_SUCCESS.variableName()).singleResult();

			HistoricVariableInstance errorMessage = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.ERROR_MESSAGE.variableName()).singleResult();

			HistoricVariableInstance warningMessage = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.WARNING_MESSAGE.variableName()).singleResult();

			if (!(Boolean) continueProcess.getValue() || !(Boolean) serviceSuccess.getValue()) {
				if (errorMessage != null && errorMessage.getValue() != null) {
					String errorMessageString = errorMessage.getValue().toString();
					reply.setErrorMessage(errorMessageString);
				}

				if (warningMessage != null && warningMessage.getValue() != null) {
					String warningMessageString = warningMessage.getValue().toString();
					reply.setWarningMessage(warningMessageString);
				}

				reply.setSuccess(false);
			} else {

				StudentInfo studentInfo = (StudentInfo) historyService.createHistoricVariableInstanceQuery()
						.processInstanceId(processInstance.getProcessInstanceId())
						.variableName(StudentVariablesEnum.STUDENT_INFO.variableName()).singleResult().getValue();
				reply.setStudentInfo(studentInfo);
				reply.setSuccess(true);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			reply.setSuccess(false);
			reply.setErrorMessage(e.getMessage());
		}

		return reply;

	}

	@PostMapping("/getStudentsList")
	public StudentReply getStudentsList(@RequestBody StudentRequest request) {
		StudentReply reply = new StudentReply();
		try {
			Map<String, Object> variables = new HashMap<String, Object>();

			identityService.setAuthenticatedUserId(request.getUsername());

			variables.put(StudentVariablesEnum.STUDENT_INFO.variableName(), request.getStudentInfo());

			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
					ProcessVariablesEnum.PROCESS_GET_STUDENTS_LIST_INFORMATION.variableName(), variables);

			HistoricVariableInstance continueProcess = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.CONTINUE_PROCESS.variableName()).singleResult();

			HistoricVariableInstance serviceSuccess = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.SERVICE_SUCCESS.variableName()).singleResult();

			HistoricVariableInstance errorMessage = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.ERROR_MESSAGE.variableName()).singleResult();

			HistoricVariableInstance warningMessage = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.WARNING_MESSAGE.variableName()).singleResult();

			if (!(Boolean) continueProcess.getValue() || !(Boolean) serviceSuccess.getValue()) {
				if (errorMessage != null && errorMessage.getValue() != null) {
					String errorMessageString = errorMessage.getValue().toString();
					reply.setErrorMessage(errorMessageString);
				}

				if (warningMessage != null && warningMessage.getValue() != null) {
					String warningMessageString = warningMessage.getValue().toString();
					reply.setWarningMessage(warningMessageString);
				}

				reply.setSuccess(false);
			} else {

				@SuppressWarnings("unchecked")
				List<StudentInfo> studentInfoList = (List<StudentInfo>) historyService
						.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getProcessInstanceId())
						.variableName(StudentVariablesEnum.STUDENT_INFO_LIST.variableName()).singleResult().getValue();
				reply.setStudentInfoList(studentInfoList);
				reply.setSuccess(true);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			reply.setSuccess(false);
			reply.setErrorMessage(e.getMessage());
		}

		return reply;

	}

	@PostMapping("/updateStudentInfo")
	public StudentReply updateStudentInfo(@RequestBody StudentRequest request) {
		StudentReply reply = new StudentReply();
		try {
			Map<String, Object> variables = new HashMap<String, Object>();

			identityService.setAuthenticatedUserId(request.getUsername());

			variables.put(StudentVariablesEnum.STUDENT_INFO.variableName(), request.getStudentInfo());

			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
					ProcessVariablesEnum.PROCESS_UPDATE_STUDENT_INFORMATION.variableName(), variables);

			HistoricVariableInstance continueProcess = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.CONTINUE_PROCESS.variableName()).singleResult();

			HistoricVariableInstance serviceSuccess = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.SERVICE_SUCCESS.variableName()).singleResult();

			HistoricVariableInstance errorMessage = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.ERROR_MESSAGE.variableName()).singleResult();

			HistoricVariableInstance warningMessage = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.WARNING_MESSAGE.variableName()).singleResult();

			if (!(Boolean) continueProcess.getValue() || !(Boolean) serviceSuccess.getValue()) {
				if (errorMessage != null && errorMessage.getValue() != null) {
					String errorMessageString = errorMessage.getValue().toString();
					reply.setErrorMessage(errorMessageString);
				}

				if (warningMessage != null && warningMessage.getValue() != null) {
					String warningMessageString = warningMessage.getValue().toString();
					reply.setWarningMessage(warningMessageString);
				}

				reply.setSuccess(false);
			} else {

				reply.setSuccessMessage("Student Updated Successfully");
				reply.setSuccess(true);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			reply.setSuccess(false);
			reply.setErrorMessage(e.getMessage());
		}

		return reply;

	}

	@PostMapping("/searchStudent")
	public StudentReply searchStudent(@RequestBody StudentRequest request) {
		StudentReply reply = new StudentReply();
		try {
			Map<String, Object> variables = new HashMap<String, Object>();

			identityService.setAuthenticatedUserId(request.getUsername());

			variables.put(StudentVariablesEnum.STUDENT_INFO.variableName(), request.getStudentInfo());
			variables.put(StudentVariablesEnum.SEARCH_TYPE.variableName(), request.getSearchType());

			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
					ProcessVariablesEnum.PROCESS_SEARCH_STUDENT_INFORMATION.variableName(), variables);

			HistoricVariableInstance continueProcess = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.CONTINUE_PROCESS.variableName()).singleResult();

			HistoricVariableInstance serviceSuccess = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.SERVICE_SUCCESS.variableName()).singleResult();

			HistoricVariableInstance errorMessage = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.ERROR_MESSAGE.variableName()).singleResult();

			HistoricVariableInstance warningMessage = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstance.getProcessInstanceId())
					.variableName(ProcessVariablesEnum.WARNING_MESSAGE.variableName()).singleResult();

			if (!(Boolean) continueProcess.getValue() || !(Boolean) serviceSuccess.getValue()) {
				if (errorMessage != null && errorMessage.getValue() != null) {
					String errorMessageString = errorMessage.getValue().toString();
					reply.setErrorMessage(errorMessageString);
				}

				if (warningMessage != null && warningMessage.getValue() != null) {
					String warningMessageString = warningMessage.getValue().toString();
					reply.setWarningMessage(warningMessageString);
				}

				reply.setSuccess(false);
			} else {

				if (request.getSearchType().equals(StudentVariablesEnum.TYPE_STUDENT_NUMBER.variableName())) {
					StudentInfo studentInfo = (StudentInfo) historyService.createHistoricVariableInstanceQuery()
							.processInstanceId(processInstance.getProcessInstanceId())
							.variableName(StudentVariablesEnum.STUDENT_INFO.variableName()).singleResult().getValue();
					reply.setStudentInfo(studentInfo);
				} else {
					@SuppressWarnings("unchecked")
					List<StudentInfo> studentInfoList = (List<StudentInfo>) historyService
							.createHistoricVariableInstanceQuery()
							.processInstanceId(processInstance.getProcessInstanceId())
							.variableName(StudentVariablesEnum.STUDENT_INFO_LIST.variableName()).singleResult()
							.getValue();
					reply.setStudentInfoList(studentInfoList);
				}

				reply.setSuccess(true);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			reply.setSuccess(false);
			reply.setErrorMessage(e.getMessage());
		}

		return reply;

	}
}
