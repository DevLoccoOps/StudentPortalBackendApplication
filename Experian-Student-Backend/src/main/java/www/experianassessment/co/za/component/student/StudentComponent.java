package www.experianassessment.co.za.component.student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.validator.EmailValidator;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import www.experianassessment.co.za.enums.ProcessVariablesEnum;
import www.experianassessment.co.za.enums.StanderdizedErrorCodeEnum;
import www.experianassessment.co.za.enums.StanderdizedWarningCodeEnum;
import www.experianassessment.co.za.enums.StudentVariablesEnum;
import www.experianassessment.co.za.model.StudentInformation;
import www.experianassessment.co.za.model.StudentScoreRecordsInformation;
import www.experianassessment.co.za.repository.StudentInformationRepository;
import www.experianassessment.co.za.repository.StudentScoreRecordsInformationRepository;
import www.experianassessment.co.za.request.reply.model.StudentInfo;
import www.experianassessment.co.za.util.CamundaComponentMessageHandler;
import www.experianassessment.co.za.util.ErrorHandlingConfigFileHelper;

@Component
public class StudentComponent implements CamundaComponentMessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentComponent.class);

	private ErrorHandlingConfigFileHelper errorConfigFileHelper = new ErrorHandlingConfigFileHelper();

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private StudentInformationRepository studentInformationRepository;

	@Autowired
	private StudentScoreRecordsInformationRepository studentScoreRecordsInformationRepository;

	public void generateStudentNumber(String executionId, String processInstanceId) {

		try {
			StudentInfo studentInfo = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());

			studentInfo.setStudentNumber(
					generateStudentNumberField(convertToTitleCaseIteratingChars(studentInfo.getFirstName()),
							convertToTitleCaseIteratingChars(studentInfo.getLastName())));

			runtimeService.setVariable(executionId, StudentVariablesEnum.STUDENT_INFO.variableName(), studentInfo);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.STUDENT_NUMBER_ERROR.variableName()));

		}

	}

	public void checkIfStudentNumberExists(String executionId, String processInstanceId) {

		try {

			StudentInfo studentInfo = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());

			Optional<StudentInformation> studentInformationOptional = studentInformationRepository
					.findStudentByStudentNumber(studentInfo.getStudentNumber());

			if (studentInformationOptional.isPresent()) {

				studentInfo.setStudentNumber(
						studentInfo.getStudentNumber() + studentInformationOptional.get().getId() + 1);

			}

			runtimeService.setVariable(executionId, StudentVariablesEnum.STUDENT_INFO.variableName(), studentInfo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.CHECK_STUDENT_EXISTS_ERROR.variableName()));

		}

	}

	public void verifyEmail(String executionId, String processInstanceId) {

		try {
			StudentInfo studentInfo = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());

			boolean valid = EmailValidator.getInstance().isValid(studentInfo.getEmailAddress());

			if (!valid) {
				this.handleMessage(MessageTypeEnum.WARNING, executionId, errorConfigFileHelper
						.getStandardizedWarningMessage(StanderdizedWarningCodeEnum.EMAIL_NOT_VALID.variableName()));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.VERIFY_EMAIL_EEROR.variableName()));

		}

	}

	public void verifyScore(String executionId, String processInstanceId) {

		try {
			StudentInfo studentInfo = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());

			if (studentInfo.getCurrentScore() > 100) {
				this.handleMessage(MessageTypeEnum.WARNING, executionId,
						errorConfigFileHelper.getStandardizedWarningMessage(
								StanderdizedWarningCodeEnum.SCORE_GREATER_THAN_100.variableName()));
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.CHECK_SCORE_ERROR.variableName()));

		}

	}

	public void registerStudent(String executionId, String processInstanceId) {

		try {
			StudentInfo studentInfo = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());

			StudentInformation studentInformation = new StudentInformation();
			studentInformation.setFirstName(convertToTitleCaseIteratingChars(studentInfo.getFirstName()));
			studentInformation.setLastName(convertToTitleCaseIteratingChars(studentInfo.getLastName()));
			studentInformation.setCellNumber(studentInfo.getCellNumber());
			studentInformation.setDob(studentInfo.getDob());
			studentInformation.setEmailAddress(studentInfo.getEmailAddress().toLowerCase());
			studentInformation.setCurrentScore(studentInfo.getCurrentScore());
			studentInformation.setStudentNumber(studentInfo.getStudentNumber());
			StudentInformation savedStudentInformation = studentInformationRepository.save(studentInformation);

			recordNewStudent(studentInfo, savedStudentInformation);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.REGISTER_STUDENT_ERROR.variableName()));

		}

	}

	private void recordNewStudent(StudentInfo studentInfo, StudentInformation savedStudentInformation) {
		if (savedStudentInformation != null) {
			StudentScoreRecordsInformation studentScoreRecordsInformation = new StudentScoreRecordsInformation();
			studentScoreRecordsInformation.setStudentInformation(savedStudentInformation);
			studentScoreRecordsInformation.setCurrentScore(studentInfo.getCurrentScore());
			studentScoreRecordsInformation.setTimeStamp(new Date());
			studentScoreRecordsInformationRepository.save(studentScoreRecordsInformation);

		}
	}

	public void calculateAverage(String executionId, String processInstanceId) {

		try {
			StudentInfo studentInfo = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());
			List<StudentScoreRecordsInformation> studentInformationList = studentScoreRecordsInformationRepository
					.findAverageScoresByStudentNumber(studentInfo.getStudentNumber());

			if (!studentInformationList.isEmpty()) {

				int totalScore = 0;
				for (StudentScoreRecordsInformation score : studentInformationList) {

					totalScore = totalScore + score.getCurrentScore();

				}

				studentInfo.setAverageScore(totalScore / studentInformationList.size());

			}
			runtimeService.setVariable(executionId, StudentVariablesEnum.STUDENT_INFO.variableName(), studentInfo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.CALCULATE_AVERAGE_ERROR.variableName()));

		}

	}

	public void updateScores(String executionId, String processInstanceId) {

		try {

			StudentInfo studentInfo = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());

			Optional<StudentInformation> studentInformationOptional = studentInformationRepository
					.findStudentByStudentNumber(studentInfo.getStudentNumber());
			if (studentInformationOptional.isPresent()) {
				StudentInformation studentInformation = studentInformationOptional.get();
				studentInformation.setCurrentScore(studentInfo.getCurrentScore());
				studentInformation.setAverageScore(studentInfo.getAverageScore());
				studentInformationRepository.save(studentInformation);

				updateAverageScoreRecord(studentInformation);

			} else {
				this.handleMessage(MessageTypeEnum.WARNING, executionId,
						errorConfigFileHelper.getStandardizedWarningMessage(
								StanderdizedWarningCodeEnum.STUDENT_DOES_NOT_EXIST.variableName()));
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.UPDATE_SCORE_ERROR.variableName()));

		}

	}

	public void getStudentInfo(String executionId, String processInstanceId) {

		try {
			StudentInfo studentInfo = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());

			Optional<StudentInformation> studentInformationOptional = studentInformationRepository
					.findStudentByStudentNumber(studentInfo.getStudentNumber());

			studentInfo = new StudentInfo();
			if (studentInformationOptional.isPresent()) {
				StudentInformation studentInformation = studentInformationOptional.get();
				studentInfo.setStudentNumber(studentInformation.getStudentNumber());
				studentInfo.setFirstName(studentInformation.getFirstName());
				studentInfo.setLastName(studentInformation.getLastName());
				studentInfo.setCellNumber(studentInformation.getCellNumber());
				studentInfo.setEmailAddress(studentInformation.getEmailAddress());
				studentInfo.setCurrentScore(studentInformation.getCurrentScore());
				studentInfo.setAverageScore(studentInformation.getAverageScore());

			} else {
				this.handleMessage(MessageTypeEnum.WARNING, executionId,
						errorConfigFileHelper.getStandardizedWarningMessage(
								StanderdizedWarningCodeEnum.STUDENT_DOES_NOT_EXIST.variableName()));
			}

			runtimeService.setVariable(executionId, StudentVariablesEnum.STUDENT_INFO.variableName(), studentInfo);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper.getStandardizedErrorMessage(
					StanderdizedErrorCodeEnum.UPDATE_STUDENT_INFORMATION_ERROR.variableName()));

		}

	}

	public void updateStudentInfo(String executionId, String processInstanceId) {

		try {
			StudentInfo studentInfo = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());

			Optional<StudentInformation> studentInformationOptional = studentInformationRepository
					.findStudentByStudentNumber(studentInfo.getStudentNumber());

			if (studentInformationOptional.isPresent()) {
				StudentInformation studentInformation = studentInformationOptional.get();
				studentInformation.setStudentNumber(studentInfo.getStudentNumber());
				studentInformation.setFirstName(studentInfo.getFirstName());
				studentInformation.setLastName(studentInfo.getLastName());
				studentInformation.setCellNumber(studentInfo.getCellNumber());
				studentInformation.setEmailAddress(studentInfo.getEmailAddress().toLowerCase());
				studentInformationRepository.save(studentInformation);

			} else {
				this.handleMessage(MessageTypeEnum.WARNING, executionId,
						errorConfigFileHelper.getStandardizedWarningMessage(
								StanderdizedWarningCodeEnum.STUDENT_DOES_NOT_EXIST.variableName()));
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper.getStandardizedErrorMessage(
					StanderdizedErrorCodeEnum.UPDATE_STUDENT_INFORMATION_ERROR.variableName()));

		}

	}

	public void getStudentInfoList(String executionId, String processInstanceId) {

		try {

			List<StudentInformation> studentInformationList = studentInformationRepository.findAll();
			List<StudentInfo> studentInfoList = new ArrayList<>();
			if (!studentInformationList.isEmpty()) {

				studentInformationList.stream().map(studentInformation -> {
					StudentInfo studentInfo = new StudentInfo();
					studentInfo.setStudentId(studentInformation.getId());
					studentInfo.setStudentNumber(studentInformation.getStudentNumber());
					studentInfo.setFirstName(studentInformation.getFirstName());
					studentInfo.setLastName(studentInformation.getLastName());
					studentInfo.setCellNumber(studentInformation.getCellNumber());
					studentInfo.setEmailAddress(studentInformation.getEmailAddress());
					studentInfo.setCurrentScore(studentInformation.getCurrentScore());
					studentInfo.setAverageScore(studentInformation.getAverageScore());
					studentInfoList.add(studentInfo);
					return studentInfoList;
				}).collect(Collectors.toList());

			}

			runtimeService.setVariable(executionId, StudentVariablesEnum.STUDENT_INFO_LIST.variableName(),
					studentInfoList);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper.getStandardizedErrorMessage(
					StanderdizedErrorCodeEnum.UPDATE_STUDENT_INFORMATION_ERROR.variableName()));

		}

	}

	public void searchByStudentNumber(String executionId, String processInstanceId) {

		try {
			StudentInfo studentInfo = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());

			Optional<StudentInformation> studentInformationOptional = studentInformationRepository
					.findStudentByStudentNumber(studentInfo.getStudentNumber());

			studentInfo = new StudentInfo();
			if (studentInformationOptional.isPresent()) {

				StudentInformation studentInformation = studentInformationOptional.get();
				studentInfo.setStudentId(studentInformation.getId());
				studentInfo.setStudentNumber(studentInformation.getStudentNumber());
				studentInfo.setFirstName(studentInformation.getFirstName());
				studentInfo.setLastName(studentInformation.getLastName());
				studentInfo.setCellNumber(studentInformation.getCellNumber());
				studentInfo.setEmailAddress(studentInformation.getEmailAddress());
				studentInfo.setCurrentScore(studentInformation.getCurrentScore());
				studentInfo.setAverageScore(studentInformation.getAverageScore());

			} else {
				this.handleMessage(MessageTypeEnum.WARNING, executionId,
						errorConfigFileHelper.getStandardizedWarningMessage(
								StanderdizedWarningCodeEnum.STUDENT_DOES_NOT_EXIST.variableName()));
			}

			runtimeService.setVariable(executionId, StudentVariablesEnum.STUDENT_INFO.variableName(), studentInfo);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper.getStandardizedErrorMessage(
					StanderdizedErrorCodeEnum.UPDATE_STUDENT_INFORMATION_ERROR.variableName()));

		}

	}

	public void searchByFirstName(String executionId, String processInstanceId) {

		try {
			StudentInfo studentInfoDetails = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());

			List<StudentInformation> studentInformationList = studentInformationRepository
					.findStudentByFirstName(convertToTitleCaseIteratingChars(studentInfoDetails.getFirstName()));
			List<StudentInfo> studentInfoList = new ArrayList<>();
			if (!studentInformationList.isEmpty()) {

				studentInformationList.stream().map(studentInformation -> {
					StudentInfo studentInfo = new StudentInfo();
					studentInfo.setStudentId(studentInformation.getId());
					studentInfo.setStudentNumber(studentInformation.getStudentNumber());
					studentInfo.setFirstName(studentInformation.getFirstName());
					studentInfo.setLastName(studentInformation.getLastName());
					studentInfo.setCellNumber(studentInformation.getCellNumber());
					studentInfo.setEmailAddress(studentInformation.getEmailAddress());
					studentInfo.setCurrentScore(studentInformation.getCurrentScore());
					studentInfo.setAverageScore(studentInformation.getAverageScore());
					studentInfoList.add(studentInfo);
					return studentInfoList;
				}).collect(Collectors.toList());

			}

			runtimeService.setVariable(executionId, StudentVariablesEnum.STUDENT_INFO_LIST.variableName(),
					studentInfoList);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper.getStandardizedErrorMessage(
					StanderdizedErrorCodeEnum.UPDATE_STUDENT_INFORMATION_ERROR.variableName()));

		}

	}

	public void searchByLastName(String executionId, String processInstanceId) {

		try {
			StudentInfo studentInfoDetails = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());

			List<StudentInformation> studentInformationList = studentInformationRepository
					.findStudentByLastName(convertToTitleCaseIteratingChars(studentInfoDetails.getLastName()));
			List<StudentInfo> studentInfoList = new ArrayList<>();
			if (!studentInformationList.isEmpty()) {

				studentInformationList.stream().map(studentInformation -> {
					StudentInfo studentInfo = new StudentInfo();
					studentInfo.setStudentId(studentInformation.getId());
					studentInfo.setStudentNumber(studentInformation.getStudentNumber());
					studentInfo.setFirstName(studentInformation.getFirstName());
					studentInfo.setLastName(studentInformation.getLastName());
					studentInfo.setCellNumber(studentInformation.getCellNumber());
					studentInfo.setEmailAddress(studentInformation.getEmailAddress());
					studentInfo.setCurrentScore(studentInformation.getCurrentScore());
					studentInfo.setAverageScore(studentInformation.getAverageScore());
					studentInfoList.add(studentInfo);
					return studentInfoList;
				}).collect(Collectors.toList());

			}

			runtimeService.setVariable(executionId, StudentVariablesEnum.STUDENT_INFO_LIST.variableName(),
					studentInfoList);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper.getStandardizedErrorMessage(
					StanderdizedErrorCodeEnum.UPDATE_STUDENT_INFORMATION_ERROR.variableName()));

		}

	}

	public void searchByEmail(String executionId, String processInstanceId) {

		try {
			StudentInfo studentInfoDetails = (StudentInfo) runtimeService.getVariable(executionId,
					StudentVariablesEnum.STUDENT_INFO.variableName());

			List<StudentInformation> studentInformationList = studentInformationRepository
					.findStudentByEmail(studentInfoDetails.getEmailAddress().toLowerCase());
			List<StudentInfo> studentInfoList = new ArrayList<>();
			if (!studentInformationList.isEmpty()) {

				studentInformationList.stream().map(studentInformation -> {
					StudentInfo studentInfo = new StudentInfo();
					studentInfo.setStudentId(studentInformation.getId());
					studentInfo.setStudentNumber(studentInformation.getStudentNumber());
					studentInfo.setFirstName(studentInformation.getFirstName());
					studentInfo.setLastName(studentInformation.getLastName());
					studentInfo.setCellNumber(studentInformation.getCellNumber());
					studentInfo.setEmailAddress(studentInformation.getEmailAddress());
					studentInfo.setCurrentScore(studentInformation.getCurrentScore());
					studentInfo.setAverageScore(studentInformation.getAverageScore());
					studentInfoList.add(studentInfo);
					return studentInfoList;
				}).collect(Collectors.toList());

			}

			runtimeService.setVariable(executionId, StudentVariablesEnum.STUDENT_INFO_LIST.variableName(),
					studentInfoList);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.handleMessage(MessageTypeEnum.ERROR, executionId, errorConfigFileHelper.getStandardizedErrorMessage(
					StanderdizedErrorCodeEnum.UPDATE_STUDENT_INFORMATION_ERROR.variableName()));

		}

	}

	private void updateAverageScoreRecord(StudentInformation studentInformation) {
		Optional<StudentScoreRecordsInformation> studentScoreInformationOptional = studentScoreRecordsInformationRepository
				.findRecordByStudentInformation(studentInformation.getId());
		if (studentScoreInformationOptional.isPresent()) {
			StudentScoreRecordsInformation studentScoreRecordsInformation = studentScoreInformationOptional.get();

			studentScoreRecordsInformation.setAverageScore(studentInformation.getAverageScore());
			studentScoreRecordsInformationRepository.save(studentScoreRecordsInformation);

		}
	}

	private String generateStudentNumberField(String firstName, String lastName) {

		int min = 0;
		int max = 99999999;
		int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);

		char first = firstName.charAt(0);
		char second = lastName.charAt(0);
		String studentNumber = first + String.valueOf(random_int) + second;

		return studentNumber;

	}

	public static String convertToTitleCaseIteratingChars(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}

		StringBuilder converted = new StringBuilder();

		boolean convertNext = true;
		for (char ch : text.toCharArray()) {
			if (Character.isSpaceChar(ch)) {
				convertNext = true;
			} else if (convertNext) {
				ch = Character.toTitleCase(ch);
				convertNext = false;
			} else {
				ch = Character.toLowerCase(ch);
			}
			converted.append(ch);
		}

		return converted.toString();
	}

	@Override
	public void handleMessage(MessageTypeEnum type, String executionId, String message) {
		runtimeService.setVariable(executionId, ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
		runtimeService.setVariable(executionId, ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);

		switch (type) {
		case ERROR:
			runtimeService.setVariable(executionId, ProcessVariablesEnum.ERROR_MESSAGE.variableName(), message);
			break;
		case WARNING:
			runtimeService.setVariable(executionId, ProcessVariablesEnum.WARNING_MESSAGE.variableName(), message);
			break;
		}

	}

	@Override
	public void handleMessage(MessageTypeEnum type, String parentExecutionId, String executionId, String message) {
		this.handleMessage(type, executionId, message);
		this.handleMessage(type, parentExecutionId, message);
	}

}
