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

public class SearchStudentInfoAPITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchStudentInfoAPITest.class);
	Gson gson = new Gson();

	/**
	 * @method searchStudent
	 * @return Success : Returns Code 200
	 */

	@Test
	public void searchStudentByStudentNumberTest() {

//		 IF

		String studentNumber = "M90959955M";

		StudentRequest studentRequest = new StudentRequest();
		StudentInfo studentInfo = new StudentInfo();

		studentInfo.setStudentNumber(studentNumber);
		studentRequest.setStudentInfo(studentInfo);
		studentRequest.setSearchType(StudentVariablesEnum.TYPE_STUDENT_NUMBER.variableName());

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/searchStudent");

		ResponseBody<?> body = response.getBody();

		JsonPath jsonPathEvaluator = body.jsonPath();
		String responseStudentInfoObject = jsonPathEvaluator.getString("studentInfo");
		boolean successReponse = jsonPathEvaluator.getBoolean("success");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(responseStudentInfoObject.contains(studentNumber), true);
		Assert.assertEquals(successReponse, true);
	}

	/**
	 * @method searchStudent
	 * @return Success : Returns Code 200
	 */

	@Test
	public void searchStudentByFirstNameTest() {

//		 IF

		String firstName = "experian";

		StudentRequest studentRequest = new StudentRequest();
		StudentInfo studentInfo = new StudentInfo();

		studentInfo.setFirstName(firstName);
		studentRequest.setStudentInfo(studentInfo);
		studentRequest.setSearchType(StudentVariablesEnum.TYPE_FIRST_NAME.variableName());

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/searchStudent");

		ResponseBody<?> body = response.getBody();

		JsonPath jsonPathEvaluator = body.jsonPath();
		String responseStudentInfoObject = jsonPathEvaluator.getString("studentInfoList");
		boolean successReponse = jsonPathEvaluator.getBoolean("success");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(responseStudentInfoObject.contains(firstName), true);
		Assert.assertEquals(successReponse, true);
	}

	/**
	 * @method searchStudent
	 * @return Success : Returns Code 200
	 */

	@Test
	public void searchStudentByLastNameTest() {

//		 IF

		String lastName = "Assessement";

		StudentRequest studentRequest = new StudentRequest();
		StudentInfo studentInfo = new StudentInfo();

		studentInfo.setLastName(lastName);
		studentRequest.setStudentInfo(studentInfo);
		studentRequest.setSearchType(StudentVariablesEnum.TYPE_LAST_NAME.variableName());

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/searchStudent");

		ResponseBody<?> body = response.getBody();

		JsonPath jsonPathEvaluator = body.jsonPath();
		String responseStudentInfoObject = jsonPathEvaluator.getString("studentInfoList");
		boolean successReponse = jsonPathEvaluator.getBoolean("success");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(responseStudentInfoObject.contains(lastName), true);
		Assert.assertEquals(successReponse, true);
	}

	/**
	 * @method searchStudent
	 * @return Success : Returns Code 200
	 */

	@Test
	public void searchStudentByEmailTest() {

//		 IF

		String email = "info@experianassessment.co.za";

		StudentRequest studentRequest = new StudentRequest();
		StudentInfo studentInfo = new StudentInfo();

		studentInfo.setEmailAddress(email);
		studentRequest.setStudentInfo(studentInfo);
		studentRequest.setSearchType(StudentVariablesEnum.TYPE_EMAIL.variableName());

		// WHEN
		Response response = given().contentType("application/json").body(studentRequest).when()
				.post("http://localhost:8080/experian/student/searchStudent");

		ResponseBody<?> body = response.getBody();

		JsonPath jsonPathEvaluator = body.jsonPath();
		String responseStudentInfoObject = jsonPathEvaluator.getString("studentInfoList");
		boolean successReponse = jsonPathEvaluator.getBoolean("success");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(responseStudentInfoObject.contains(email), true);
		Assert.assertEquals(successReponse, true);
	}

	/**
	 * @method searchStudent
	 * @return Success : Returns Code 400
	 */

	@Test
	public void searchStudentInvalidBodyRequestTest() {

		// WHEN
		Response response = given().contentType("application/json").body("experianRequest").when()
				.post("http://localhost:8080/experian/student/searchStudent");

		// THEN
		Assert.assertEquals(response.getStatusCode(), 400);

	}


	/**
	 * @method searchStudent
	 * @return Success : Returns Code 404
	 */


	@Test
	public void searchStudentInvalidUrlTest() {

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
