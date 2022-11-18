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

public class RegisterStudentAPITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterStudentAPITest.class);
	Gson gson = new Gson();

	
	/**
	 * @method registerStudent
	 * @return Success : Returns Code 200
	 */
	
	@Test
	public void registerStudentSuccessTest() {

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

		StudentReply studentReply = new StudentReply();
		studentReply.setSuccess(true);
		studentReply.setSuccessMessage("Student Registered Successfully ");

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/register");

		ResponseBody<?> body = response.getBody();
		String bodyAsString = body.asString();

		JsonPath jsonPathEvaluator = body.jsonPath();
		String successMessage = jsonPathEvaluator.get("successMessage");
		boolean successReponse = jsonPathEvaluator.getBoolean("success");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(successMessage, studentReply.getSuccessMessage());
		Assert.assertEquals(successReponse, true);

	}

	
	/**
	 * @method registerStudent
	 * @return Success : Returns Code 200
	 */
	
	@Test
	public void registerStudentInvalidEmailTest() {

//		 IF
		String firstName = "experian";
		String lastName = "assessement";
		String cellNumber = "+27815408956";
		String emailAddress = "info@experianassessment...co.za";
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

		StudentReply studentReply = new StudentReply();
		studentReply.setSuccess(true);
		studentReply.setWarningMessage("Email Entered Is Not Valid");

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/register");

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
	 * @method registerStudent
	 * @return Success : Returns Code 200
	 */
	
	@Test
	public void registerStudentScoreHigherThan100Test() {

//		 IF
		String firstName = "experian";
		String lastName = "assessement";
		String cellNumber = "+27815408956";
		String emailAddress = "info@experianassessment.co.za";
		String dob = "29/04/1998";
		int currentScore = 101;

		StudentRequest studentRequest = new StudentRequest();
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setFirstName(firstName);
		studentInfo.setLastName(lastName);
		studentInfo.setCellNumber(cellNumber);
		studentInfo.setDob(dob);
		studentInfo.setEmailAddress(emailAddress);
		studentInfo.setCurrentScore(currentScore);
		studentRequest.setStudentInfo(studentInfo);

		StudentReply studentReply = new StudentReply();
		studentReply.setSuccess(true);
		studentReply.setWarningMessage("Score Cannot Be Greater Than 100");

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/register");

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
	 * @method registerStudent
	 * @return Success : Returns Code 400
	 */
	@Test
	public void registerStudentInvalidBodyRequestTest() {

		// WHEN
		Response response = given().contentType("application/json").body("experianRequest").when()
				.post("http://localhost:8080/experian/student/register");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 400);

	}

	
	/**
	 * @method registerStudent
	 * @return Success : Returns Code 404
	 */
	
	@Test
	public void registerStudentInvalidUrlTest() {

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
