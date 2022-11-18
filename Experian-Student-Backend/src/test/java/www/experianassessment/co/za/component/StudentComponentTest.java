package www.experianassessment.co.za.component;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import www.experianassessment.co.za.component.student.StudentComponent;
import www.experianassessment.co.za.enums.ProcessVariablesEnum;
import www.experianassessment.co.za.enums.StanderdizedErrorCodeEnum;
import www.experianassessment.co.za.enums.StanderdizedWarningCodeEnum;
import www.experianassessment.co.za.enums.StudentVariablesEnum;
import www.experianassessment.co.za.model.StudentInformation;
import www.experianassessment.co.za.model.StudentScoreRecordsInformation;
import www.experianassessment.co.za.repository.StudentInformationRepository;
import www.experianassessment.co.za.repository.StudentScoreRecordsInformationRepository;
import www.experianassessment.co.za.request.reply.model.StudentInfo;
import www.experianassessment.co.za.util.ErrorHandlingConfigFileHelper;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class StudentComponentTest {

	private static Logger LOGGER = LoggerFactory.getLogger(StudentComponentTest.class);

	private ErrorHandlingConfigFileHelper errorConfigFileHelper = new ErrorHandlingConfigFileHelper();

	@Mock
	private StudentInformationRepository studentInformationRepository;

	@Mock
	private StudentScoreRecordsInformationRepository studentScoreRecordsInformationRepository;

	@Mock
	private RuntimeService runtimeService;

	@InjectMocks
	private StudentComponent studentComponentTest;

	
	/**
	 * @method generateStudentNumber
	 * @return Success : Student exists
	 */
	
	@Test
	public void generateStudentNumberTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.STUDENT_NUMBER_ERROR.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			// THEN
			studentComponentTest.generateStudentNumber(mockExecutionId, mockMessage);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), true);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	
	/**
	 * @method generateStudentNumber
	 * @return Failed : Throw exception
	 */
	
	@Test
	public void generateStudentNumberExceptionTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.STUDENT_NUMBER_ERROR.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";

			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(null);

			// THEN
			studentComponentTest.generateStudentNumber(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.ERROR_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method checkIfStudentNumberExists
	 * @return Success : Student exists
	 */
	@Test
	public void studentExistsTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.CHECK_STUDENT_EXISTS_ERROR.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			String studentNumber = "E55124634A";
			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);
			mockStudentInfo.setStudentNumber(studentNumber);

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setStudentNumber(mockStudentInfo.getStudentNumber());
			Optional<StudentInformation> mockmockStudentInformationOptional = Optional.of(mockStudentInformation);
			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			when(studentInformationRepository.findStudentByStudentNumber(mockStudentInfo.getStudentNumber()))
					.thenReturn(mockmockStudentInformationOptional);

			// THEN
			studentComponentTest.checkIfStudentNumberExists(mockExecutionId, mockMessage);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), true);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method checkIfStudentNumberExists
	 * @return Failed : Student does not exist
	 */
	@Test
	public void studentNotExistTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.CHECK_STUDENT_EXISTS_ERROR.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			String studentNumber = "E55124634A";
			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);
			mockStudentInfo.setStudentNumber(studentNumber);

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setStudentNumber(mockStudentInfo.getStudentNumber());
			Optional<StudentInformation> mockmockStudentInformationOptional = Optional.empty();
			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			when(studentInformationRepository.findStudentByStudentNumber(mockStudentInfo.getStudentNumber()))
					.thenReturn(mockmockStudentInformationOptional);

			// THEN
			studentComponentTest.checkIfStudentNumberExists(mockExecutionId, mockMessage);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method checkIfStudentNumberExists
	 * @return Exeption : Throw Exception
	 */
	@Test
	public void throwExceptionTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.CHECK_STUDENT_EXISTS_ERROR.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			String studentNumber = "E55124634A";
			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);
			mockStudentInfo.setStudentNumber(studentNumber);

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setStudentNumber(mockStudentInfo.getStudentNumber());

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			when(studentInformationRepository.findStudentByStudentNumber(mockStudentInfo.getStudentNumber()))
					.thenReturn(null);

			// THEN
			studentComponentTest.checkIfStudentNumberExists(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.ERROR_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method verifyEmail
	 * @return Success : Email Verified
	 */

	@Test
	public void verifiedEmailTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedWarningCodeEnum.EMAIL_NOT_VALID.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			// THEN
			studentComponentTest.verifyEmail(mockExecutionId, mockMessage);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), true);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method verifyEmail
	 * @return Failed : Email Not Verified
	 */

	@Test
	public void emailNotVerifiedTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedWarningMessage(StanderdizedWarningCodeEnum.EMAIL_NOT_VALID.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment...co.za";
			String dob = "29/04/1998";
			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			// THEN
			studentComponentTest.verifyEmail(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.WARNING_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method verifyEmail
	 * @return Exception : Throw Exception
	 */

	@Test
	public void throwExeptionTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = StanderdizedErrorCodeEnum.VERIFY_EMAIL_EEROR.variableName();
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment...co.za";
			String dob = "29/04/1998";
			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(null);

			// THEN
			studentComponentTest.verifyEmail(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.ERROR_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method verifyScore
	 * @return Success : Score is less than 100
	 */

	@Test
	public void scoreLessThan100Test() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedWarningCodeEnum.SCORE_GREATER_THAN_100.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			// THEN
			studentComponentTest.verifyScore(mockExecutionId, mockMessage);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), true);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method verifyScore
	 * @return Failed : Score is greater than 100
	 */

	@Test
	public void scoreGreaterThan100Test() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedWarningCodeEnum.SCORE_GREATER_THAN_100.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = 501;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			// THEN
			studentComponentTest.verifyScore(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.WARNING_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method verifyScore
	 * @return Failed : Score is greater than 0
	 */

	@Test
	public void scoreGreaterThan0Test() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedWarningCodeEnum.SCORE_GREATER_THAN_0.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = -1;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			// THEN
			studentComponentTest.verifyScore(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.WARNING_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method verifyScore
	 * @return Failed : Throw expetion
	 */

	@Test
	public void throwExeptionVerifyScoreTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.CHECK_SCORE_ERROR.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = -1;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(null);

			// THEN
			studentComponentTest.verifyScore(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.ERROR_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method registerStudent
	 * @return Success : Register Student and record history
	 */

	@Test
	public void registerStudentTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.REGISTER_STUDENT_ERROR.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setFirstName(mockStudentInfo.getFirstName());
			mockStudentInformation.setLastName(mockStudentInfo.getLastName());
			mockStudentInformation.setCellNumber(mockStudentInfo.getCellNumber());
			mockStudentInformation.setDob(mockStudentInfo.getDob());
			mockStudentInformation.setEmailAddress(mockStudentInfo.getEmailAddress().toLowerCase());
			mockStudentInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentInformation.setStudentNumber(mockStudentInfo.getStudentNumber());

			StudentScoreRecordsInformation mockStudentScoreRecordsInformation = new StudentScoreRecordsInformation();
			mockStudentScoreRecordsInformation.setStudentInformation(mockStudentInformation);
			mockStudentScoreRecordsInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentScoreRecordsInformation.setTimeStamp(new Date());
			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			when(studentInformationRepository.save(any(StudentInformation.class))).thenReturn(mockStudentInformation);
			when(studentScoreRecordsInformationRepository.save(any(StudentScoreRecordsInformation.class)))
					.thenReturn(mockStudentScoreRecordsInformation);
			// THEN
			studentComponentTest.registerStudent(mockExecutionId, mockMessage);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), true);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method registerStudent
	 * @return Failed : Throw Exception
	 */

	@Test
	public void registerStudentTthrowExceptionTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.REGISTER_STUDENT_ERROR.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setFirstName(mockStudentInfo.getFirstName());
			mockStudentInformation.setLastName(mockStudentInfo.getLastName());
			mockStudentInformation.setCellNumber(mockStudentInfo.getCellNumber());
			mockStudentInformation.setDob(mockStudentInfo.getDob());
			mockStudentInformation.setEmailAddress(mockStudentInfo.getEmailAddress().toLowerCase());
			mockStudentInformation.setCurrentScore(mockStudentInfo.getCurrentScore());

			StudentScoreRecordsInformation mockStudentScoreRecordsInformation = new StudentScoreRecordsInformation();
			mockStudentScoreRecordsInformation.setStudentInformation(mockStudentInformation);
			mockStudentScoreRecordsInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentScoreRecordsInformation.setTimeStamp(new Date());
			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(null);

			// THEN
			studentComponentTest.registerStudent(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.ERROR_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method calculateAverage
	 * @return Success : Average Calculated
	 */

	@Test
	public void calculateAverageTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.CALCULATE_AVERAGE_ERROR.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			StudentScoreRecordsInformation mockStudentScoreRecordsInformation = new StudentScoreRecordsInformation();

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setFirstName(mockStudentInfo.getFirstName());
			mockStudentInformation.setId(1L);

			mockStudentScoreRecordsInformation.setStudentInformation(mockStudentInformation);
			mockStudentScoreRecordsInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentScoreRecordsInformation.setTimeStamp(new Date());
			List<StudentScoreRecordsInformation> mockStudentScoreRecordsInformationList = Arrays
					.asList(mockStudentScoreRecordsInformation);
			when(studentScoreRecordsInformationRepository
					.findAverageScoresByStudentNumber(mockStudentInfo.getStudentNumber()))
							.thenReturn(mockStudentScoreRecordsInformationList);

			// THEN
			studentComponentTest.calculateAverage(mockExecutionId, mockMessage);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), true);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method calculateAverage
	 * @return Failed : Average not calculated
	 */

	@Test
	public void studentHasEmptyScoresTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.CALCULATE_AVERAGE_ERROR.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			StudentScoreRecordsInformation mockStudentScoreRecordsInformation = new StudentScoreRecordsInformation();

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setFirstName(mockStudentInfo.getFirstName());
			mockStudentInformation.setId(1L);

			mockStudentScoreRecordsInformation.setStudentInformation(mockStudentInformation);
			mockStudentScoreRecordsInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentScoreRecordsInformation.setTimeStamp(new Date());
			List<StudentScoreRecordsInformation> mockStudentScoreRecordsInformationList = new ArrayList<>();

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			when(studentScoreRecordsInformationRepository
					.findAverageScoresByStudentNumber(mockStudentInfo.getStudentNumber()))
							.thenReturn(mockStudentScoreRecordsInformationList);
			// THEN
			studentComponentTest.calculateAverage(mockExecutionId, mockMessage);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), true);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method calculateAverage
	 * @return Failed : Average not calculated
	 */

	@Test
	public void calculateAverageThrowExceptionTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.CALCULATE_AVERAGE_ERROR.variableName());
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);

			StudentScoreRecordsInformation mockStudentScoreRecordsInformation = new StudentScoreRecordsInformation();

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setFirstName(mockStudentInfo.getFirstName());
			mockStudentInformation.setId(1L);

			mockStudentScoreRecordsInformation.setStudentInformation(mockStudentInformation);
			mockStudentScoreRecordsInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentScoreRecordsInformation.setTimeStamp(new Date());

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(null);

			// THEN
			studentComponentTest.calculateAverage(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.ERROR_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method updateScores
	 * @return Success : Score Updated
	 */

	@Test
	public void updateScoreseTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.UPDATE_SCORE_ERROR.variableName());

			String studentNumber = "E55124634A";
			int currentScore = 50;
			int averageScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setStudentNumber(studentNumber);
			mockStudentInfo.setCurrentScore(currentScore);
			mockStudentInfo.setAverageScore(averageScore);

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentInformation.setAverageScore(mockStudentInfo.getAverageScore());
			mockStudentInformation.setId(1l);
			StudentScoreRecordsInformation mockStudentScoreRecordsInformation = new StudentScoreRecordsInformation();
			mockStudentScoreRecordsInformation.setStudentInformation(mockStudentInformation);
			mockStudentScoreRecordsInformation.setAverageScore(mockStudentInformation.getAverageScore());
			mockStudentScoreRecordsInformation.setCurrentScore(mockStudentInformation.getCurrentScore());
			mockStudentScoreRecordsInformation.setTimeStamp(new Date());
			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			Optional<StudentInformation> mockStudentInformationOptional = Optional.of(mockStudentInformation);
			when(studentInformationRepository.findStudentByStudentNumber(mockStudentInfo.getStudentNumber()))
					.thenReturn(mockStudentInformationOptional);

			Optional<StudentScoreRecordsInformation> mockStudentScoreRecordsInformationOptional = Optional
					.of(mockStudentScoreRecordsInformation);
			when(studentScoreRecordsInformationRepository
					.findRecordByStudentInformation(mockStudentInformation.getId()))
							.thenReturn(mockStudentScoreRecordsInformationOptional);

			when(studentInformationRepository.save(any(StudentInformation.class))).thenReturn(mockStudentInformation);
			when(studentScoreRecordsInformationRepository.save(any(StudentScoreRecordsInformation.class)))
					.thenReturn(mockStudentScoreRecordsInformation);

			// THEN
			studentComponentTest.updateScores(mockExecutionId, mockMessage);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), true);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method updateScores
	 * @return Success : Score Updated
	 */

	@Test
	public void studentDoesNotExistTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedWarningCodeEnum.STUDENT_DOES_NOT_EXIST.variableName());

			String studentNumber = "E55124634A";
			int currentScore = 50;
			int averageScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setStudentNumber(studentNumber);
			mockStudentInfo.setCurrentScore(currentScore);
			mockStudentInfo.setAverageScore(averageScore);

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentInformation.setAverageScore(mockStudentInfo.getAverageScore());
			mockStudentInformation.setId(1l);
			StudentScoreRecordsInformation mockStudentScoreRecordsInformation = new StudentScoreRecordsInformation();
			mockStudentScoreRecordsInformation.setStudentInformation(mockStudentInformation);
			mockStudentScoreRecordsInformation.setAverageScore(mockStudentInformation.getAverageScore());
			mockStudentScoreRecordsInformation.setCurrentScore(mockStudentInformation.getCurrentScore());
			mockStudentScoreRecordsInformation.setTimeStamp(new Date());
			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			Optional<StudentInformation> mockStudentInformationOptional = Optional.empty();
			when(studentInformationRepository.findStudentByStudentNumber(mockStudentInfo.getStudentNumber()))
					.thenReturn(mockStudentInformationOptional);

			// THEN
			studentComponentTest.updateScores(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.WARNING_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method updateScores
	 * @return Failed : Failed to update average score
	 */

	@Test
	public void scoreRecordsNotFoundTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.UPDATE_SCORE_ERROR.variableName());

			String studentNumber = "E55124634A";
			int currentScore = 50;
			int averageScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setStudentNumber(studentNumber);
			mockStudentInfo.setCurrentScore(currentScore);
			mockStudentInfo.setAverageScore(averageScore);

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentInformation.setAverageScore(mockStudentInfo.getAverageScore());
			mockStudentInformation.setId(1l);
			StudentScoreRecordsInformation mockStudentScoreRecordsInformation = new StudentScoreRecordsInformation();
			mockStudentScoreRecordsInformation.setStudentInformation(mockStudentInformation);
			mockStudentScoreRecordsInformation.setAverageScore(mockStudentInformation.getAverageScore());
			mockStudentScoreRecordsInformation.setCurrentScore(mockStudentInformation.getCurrentScore());
			mockStudentScoreRecordsInformation.setTimeStamp(new Date());
			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			Optional<StudentInformation> mockStudentInformationOptional = Optional.of(mockStudentInformation);
			when(studentInformationRepository.findStudentByStudentNumber(mockStudentInfo.getStudentNumber()))
					.thenReturn(mockStudentInformationOptional);

			// THEN
			studentComponentTest.updateScores(mockExecutionId, mockMessage);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), true);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method updateScores
	 * @return Failed : Failed to update average score
	 */

	@Test
	public void updateScoreThrowExceptionTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.UPDATE_SCORE_ERROR.variableName());

			String studentNumber = "E55124634A";
			int currentScore = 50;
			int averageScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setStudentNumber(studentNumber);
			mockStudentInfo.setCurrentScore(currentScore);
			mockStudentInfo.setAverageScore(averageScore);

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentInformation.setAverageScore(mockStudentInfo.getAverageScore());
			mockStudentInformation.setId(1l);
			StudentScoreRecordsInformation mockStudentScoreRecordsInformation = new StudentScoreRecordsInformation();
			mockStudentScoreRecordsInformation.setStudentInformation(mockStudentInformation);
			mockStudentScoreRecordsInformation.setAverageScore(mockStudentInformation.getAverageScore());
			mockStudentScoreRecordsInformation.setCurrentScore(mockStudentInformation.getCurrentScore());
			mockStudentScoreRecordsInformation.setTimeStamp(new Date());
			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			Optional<StudentInformation> mockStudentInformationOptional = Optional.of(mockStudentInformation);
			when(studentInformationRepository.findStudentByStudentNumber(mockStudentInfo.getStudentNumber()))
					.thenReturn(null);

			// THEN
			studentComponentTest.updateScores(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.ERROR_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method getStudentInfo
	 * @return Success : Score Updated
	 */

	@Test
	public void getStudentInfoTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedWarningCodeEnum.STUDENT_DOES_NOT_EXIST.variableName());

			String studentNumber = "E55124634A";
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = 50;
			int averageScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);
			mockStudentInfo.setStudentNumber(studentNumber);
			mockStudentInfo.setAverageScore(averageScore);

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setStudentNumber(mockStudentInfo.getStudentNumber());
			mockStudentInformation.setFirstName(mockStudentInfo.getFirstName());
			mockStudentInformation.setLastName(mockStudentInfo.getLastName());
			mockStudentInformation.setCellNumber(mockStudentInfo.getCellNumber());
			mockStudentInformation.setEmailAddress(mockStudentInfo.getEmailAddress());
			mockStudentInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentInformation.setAverageScore(mockStudentInfo.getAverageScore());

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			Optional<StudentInformation> mockStudentInformationOptional = Optional.of(mockStudentInformation);
			when(studentInformationRepository.findStudentByStudentNumber(mockStudentInfo.getStudentNumber()))
					.thenReturn(mockStudentInformationOptional);

			// THEN
			studentComponentTest.getStudentInfo(mockExecutionId, mockMessage);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * @method getStudentInfo
	 * @return Failed : Scored not updated
	 */

	@Test
	public void studentNotFoundTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedWarningCodeEnum.STUDENT_DOES_NOT_EXIST.variableName());

			String studentNumber = "E55124634A";
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = 50;
			int averageScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);
			mockStudentInfo.setStudentNumber(studentNumber);
			mockStudentInfo.setAverageScore(averageScore);

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setStudentNumber(mockStudentInfo.getStudentNumber());
			mockStudentInformation.setFirstName(mockStudentInfo.getFirstName());
			mockStudentInformation.setLastName(mockStudentInfo.getLastName());
			mockStudentInformation.setCellNumber(mockStudentInfo.getCellNumber());
			mockStudentInformation.setEmailAddress(mockStudentInfo.getEmailAddress());
			mockStudentInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentInformation.setAverageScore(mockStudentInfo.getAverageScore());

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(mockStudentInfo);

			// THEN
			studentComponentTest.getStudentInfo(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(0)).setVariable(mockExecutionId,
					ProcessVariablesEnum.ERROR_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}
	
	/**
	 * @method getStudentInfo
	 * @return Failed : Scored not updated
	 */

	@Test
	public void getStudentInfoThrowExceptionTest() {
		try {
			// IF
			String mockExecutionId = "123456";
			String mockMessage = errorConfigFileHelper
					.getStandardizedErrorMessage(StanderdizedErrorCodeEnum.UPDATE_STUDENT_INFORMATION_ERROR.variableName());

			String studentNumber = "E55124634A";
			String firstName = "experian";
			String lastName = "assessement";
			String cellNumber = "+27815408956";
			String emailAddress = "info@experianassessment.co.za";
			String dob = "29/04/1998";
			int currentScore = 50;
			int averageScore = 50;

			StudentInfo mockStudentInfo = new StudentInfo();
			mockStudentInfo.setFirstName(firstName);
			mockStudentInfo.setLastName(lastName);
			mockStudentInfo.setCellNumber(cellNumber);
			mockStudentInfo.setDob(dob);
			mockStudentInfo.setEmailAddress(emailAddress);
			mockStudentInfo.setCurrentScore(currentScore);
			mockStudentInfo.setStudentNumber(studentNumber);
			mockStudentInfo.setAverageScore(averageScore);

			StudentInformation mockStudentInformation = new StudentInformation();
			mockStudentInformation.setStudentNumber(mockStudentInfo.getStudentNumber());
			mockStudentInformation.setFirstName(mockStudentInfo.getFirstName());
			mockStudentInformation.setLastName(mockStudentInfo.getLastName());
			mockStudentInformation.setCellNumber(mockStudentInfo.getCellNumber());
			mockStudentInformation.setEmailAddress(mockStudentInfo.getEmailAddress());
			mockStudentInformation.setCurrentScore(mockStudentInfo.getCurrentScore());
			mockStudentInformation.setAverageScore(mockStudentInfo.getAverageScore());

			// WHEN
			when(runtimeService.getVariable(mockExecutionId, StudentVariablesEnum.STUDENT_INFO.variableName()))
					.thenReturn(null);

			// THEN
			studentComponentTest.getStudentInfo(mockExecutionId, mockMessage);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), false);
			verify(runtimeService, times(1)).setVariable(mockExecutionId,
					ProcessVariablesEnum.ERROR_MESSAGE.variableName(), mockMessage);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}
}
