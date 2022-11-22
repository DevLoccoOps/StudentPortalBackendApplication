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

import www.experianassessment.co.za.enums.StudentVariablesEnum;
import www.experianassessment.co.za.reply.StudentReply;
import www.experianassessment.co.za.request.StudentRequest;
import www.experianassessment.co.za.request.reply.model.StudentInfo;

public class CaptureLatestScoreAPITest {

	Gson gson = new Gson();

	/**
	 * @method captureScore
	 * @return Success : Returns Code 200
	 */
	
	@Test
	public void captureLatestScoreSuccessTest() {

//		 IF

		String studentNumber = "M90959955M";
		int currentScore = 50;

		StudentRequest studentRequest = new StudentRequest();
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setCurrentScore(currentScore);
		studentInfo.setStudentNumber(studentNumber);
		studentRequest.setStudentInfo(studentInfo);

		StudentReply studentReply = new StudentReply();
		studentReply.setSuccess(true);
		studentReply.setSuccessMessage("Student Score Captured Successfully");

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/captureScore");

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
	 * @method captureScore
	 * @return Success : Returns Code 200
	 */
	@Test
	public void captureLatestScoreGreaterThan100SuccessTest() {

//		 IF
		String studentNumber = "E55124634A";
		int currentScore = 501;

		StudentRequest studentRequest = new StudentRequest();
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setCurrentScore(currentScore);
		studentInfo.setStudentNumber(studentNumber);
		studentRequest.setStudentInfo(studentInfo);

		StudentReply studentReply = new StudentReply();
		studentReply.setSuccess(true);
		studentReply.setWarningMessage("Score Cannot Be Greater Than 100");

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/captureScore");

		ResponseBody<?> body = response.getBody();

		JsonPath jsonPathEvaluator = body.jsonPath();
		String warningMessageResponse = jsonPathEvaluator.get("warningMessage");
		boolean successReponse = jsonPathEvaluator.getBoolean("success");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(warningMessageResponse, studentReply.getWarningMessage());
		Assert.assertEquals(successReponse, false);

	}
	
	/**
	 * @method captureScore
	 * @return Success : Returns Code 200
	 */
	
	@Test
	public void captureLatestScoreInvalidStudentNumberTest() {

//		 IF

		String studentNumber = "E551246344334A";
		int currentScore = 50;

		StudentRequest studentRequest = new StudentRequest();
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setCurrentScore(currentScore);
		studentInfo.setStudentNumber(studentNumber);
		studentRequest.setStudentInfo(studentInfo);

		StudentReply studentReply = new StudentReply();
		studentReply.setSuccess(true);
		studentReply.setWarningMessage("Student Does Not Exist");

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/captureScore");

		ResponseBody<?> body = response.getBody();

		JsonPath jsonPathEvaluator = body.jsonPath();
		String warningMessageResponse = jsonPathEvaluator.get("warningMessage");
		boolean successReponse = jsonPathEvaluator.getBoolean("success");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(warningMessageResponse, studentReply.getWarningMessage());
		Assert.assertEquals(successReponse, false);

	}


	/**
	 * @method captureScore
	 * @return Success : Returns Code 400
	 */
	
	@Test
	public void captureLatestScoreInvalidBodyRequestTest() {

		// WHEN
		Response response = given().contentType("application/json").body("experianRequest").when()
				.post("http://localhost:8080/experian/student/captureScore");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 400);

	}

	/**
	 * @method captureScore
	 * @return Success : Returns Code 404
	 */
	
	@Test
	public void captureLatestScoreInvalidUrlTest() {

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
				.post("http://localhost:8080/experian/student/testing");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 404);

	}

}
