package www.experianassessment.co.za.workflowApi;

import static com.jayway.restassured.RestAssured.given;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;

import www.experianassessment.co.za.reply.StudentReply;
import www.experianassessment.co.za.request.StudentRequest;
import www.experianassessment.co.za.request.reply.model.StudentInfo;

public class UpdateStudentInfoAPITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateStudentInfoAPITest.class);
	Gson gson = new Gson();

	
	/**
	 * @method updateStudentInfo
	 * @return Success : Returns Code 200
	 */
	@Test
	public void updateStudentInfoExistingStudentTest() {

//		 IF
		String firstName = "experian";
		String lastName = "assessement";
		String cellNumber = "+27815408956";
		String emailAddress = "info@experianassessment.co.za";
		String dob = "29/04/1998";
		String studentNumber = "K64369244M";
		int currentScore = 50;

		StudentRequest studentRequest = new StudentRequest();
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setFirstName(firstName);
		studentInfo.setLastName(lastName);
		studentInfo.setCellNumber(cellNumber);
		studentInfo.setDob(dob);
		studentInfo.setEmailAddress(emailAddress);
		studentInfo.setCurrentScore(currentScore);
		studentInfo.setStudentNumber(studentNumber);
		studentRequest.setStudentInfo(studentInfo);

		StudentReply studentReply = new StudentReply();
		studentReply.setSuccess(true);
		studentReply.setSuccessMessage("Student Updated Successfully");

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/updateStudentInfo");

		ResponseBody<?> body = response.getBody();

		JsonPath jsonPathEvaluator = body.jsonPath();
		String successMessageResponse = jsonPathEvaluator.get("successMessage");
		boolean successReponse = jsonPathEvaluator.getBoolean("success");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(successMessageResponse, studentReply.getSuccessMessage());
		Assert.assertEquals(successReponse, true);

	}
	
	/**
	 * @method updateStudentInfo
	 * @return Success : Returns Code 200
	 */
	
	@Test
	public void updateStudentInfoInvalidEmailStudentTest() {

//		 IF
		String firstName = "experian";
		String lastName = "assessement";
		String cellNumber = "+27815408956";
		String emailAddress = "info@experianassessment...co.za";
		String dob = "29/04/1998";
		String studentNumber = "experianStudent";
		int currentScore = 50;

		StudentRequest studentRequest = new StudentRequest();
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setFirstName(firstName);
		studentInfo.setLastName(lastName);
		studentInfo.setCellNumber(cellNumber);
		studentInfo.setDob(dob);
		studentInfo.setEmailAddress(emailAddress);
		studentInfo.setCurrentScore(currentScore);
		studentInfo.setStudentNumber(studentNumber);
		studentRequest.setStudentInfo(studentInfo);

		StudentReply studentReply = new StudentReply();
		studentReply.setSuccess(true);
		studentReply.setWarningMessage("Email Entered Is Not Valid");

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/updateStudentInfo");

		ResponseBody<?> body = response.getBody();

		JsonPath jsonPathEvaluator = body.jsonPath();
		String warningMessage = jsonPathEvaluator.get("warningMessage");
		boolean successReponse = jsonPathEvaluator.getBoolean("success");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(warningMessage, studentReply.getWarningMessage());
		Assert.assertEquals(successReponse, false);

	}

	
	/**
	 * @method updateStudentInfo
	 * @return Success : Returns Code 200
	 */
	@Test
	public void updateStudentInfoNonExistingStudentTest() {

//		 IF
		String firstName = "experian";
		String lastName = "assessement";
		String cellNumber = "+27815408956";
		String emailAddress = "info@experianassessment.co.za";
		String dob = "29/04/1998";
		String studentNumber = "testingName";
		int currentScore = 50;

		StudentRequest studentRequest = new StudentRequest();
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setFirstName(firstName);
		studentInfo.setLastName(lastName);
		studentInfo.setCellNumber(cellNumber);
		studentInfo.setDob(dob);
		studentInfo.setEmailAddress(emailAddress);
		studentInfo.setCurrentScore(currentScore);
		studentInfo.setStudentNumber(studentNumber);
		studentRequest.setStudentInfo(studentInfo);

		StudentReply studentReply = new StudentReply();
		studentReply.setStudentInfo(studentInfo);

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/updateStudentInfo");

		ResponseBody<?> body = response.getBody();

		JsonPath jsonPathEvaluator = body.jsonPath();
		String warningMessageResponse = jsonPathEvaluator.getString("warningMessage");
		boolean successReponse = jsonPathEvaluator.getBoolean("success");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(warningMessageResponse.contains("Student Does Not Exist"), true);
		Assert.assertEquals(successReponse, false);

	}

	
	/**
	 * @method updateStudentInfo
	 * @return Success : Returns Code 400
	 */
	@Test
	public void updateStudentInfoInvalidBodyRequestTest() {

		// WHEN
		Response response = given().contentType("application/json").body("experianRequest").when()
				.post("http://localhost:8080/experian/student/updateStudentInfo");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 400);

	}

	
	/**
	 * @method updateStudentInfo
	 * @return Success : Returns Code 404
	 */
	@Test
	public void updateStudentInfoInvalidUrlTest() {

//		 IF
		String firstName = "experian";
		String lastName = "assessement";
		String cellNumber = "+27815408956";
		String emailAddress = "info@experianassessment.co.za";
		String dob = "29/04/1998";
		int currentScore = 50;

		StudentRequest studentRequest = new StudentRequest();
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setFirstName(firstName);
		studentInfo.setLastName(lastName);
		studentInfo.setCellNumber(cellNumber);
		studentInfo.setDob(dob);
		studentInfo.setEmailAddress(emailAddress);
		studentInfo.setCurrentScore(currentScore);
		studentRequest.setStudentInfo(studentInfo);

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/testingName");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 404);

	}

}
