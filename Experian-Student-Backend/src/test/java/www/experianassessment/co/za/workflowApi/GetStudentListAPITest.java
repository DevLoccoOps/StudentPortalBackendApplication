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

public class GetStudentListAPITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetStudentListAPITest.class);
	Gson gson = new Gson();

	
	/**
	 * @method getStudentsList
	 * @return Success : Returns Code 200
	 */
	
	@Test
	public void getStudentsListExistingStudentsTest() {

//		 IF
		String firstName = "experian";
		String lastName = "assessement";
		String cellNumber = "+27815408956";
		String emailAddress = "info@experianassessment.co.za";
		String dob = "29/04/1998";
		String studentNumber = "E55124634A";
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
				.post("http://localhost:8080/experian/student/getStudentsList");

		ResponseBody<?> body = response.getBody();

		JsonPath jsonPathEvaluator = body.jsonPath();

		String responseStudentInfoObject = jsonPathEvaluator.getString("studentInfoList");
		LOGGER.info(responseStudentInfoObject);

		// THEN
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(responseStudentInfoObject.contains("E55124634A"), true);

	}

	/**
	 * @method getStudentsList
	 * @return Success : Returns Code 400
	 */
	@Test
	public void getStudentInfoListInvalidBodyRequestTest() {

		// WHEN
		Response response = given().contentType("application/json").body("experianRequest").when()
				.post("http://localhost:8080/experian/student/getStudentInfo");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 400);

	}

	/**
	 * @method getStudentsList
	 * @return Success : Returns Code 404
	 */
	
	@Test
	public void getStudentInfoListInvalidUrlTest() {

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
