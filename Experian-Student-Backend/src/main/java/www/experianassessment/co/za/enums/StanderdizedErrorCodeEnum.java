package www.experianassessment.co.za.enums;

public enum StanderdizedErrorCodeEnum {

	/**
	 * 
	 * @return USER CODES
	 */

	CHECK_STUDENT_EXISTS_ERROR("ERR0001"),
	STUDENT_NUMBER_ERROR("ERR0002"),
	VERIFY_EMAIL_EEROR("ERR0003"),
	CHECK_SCORE_ERROR("ERR0004"),
	REGISTER_STUDENT_ERROR("ERR0005"),
	CALCULATE_AVERAGE_ERROR("ERR0006"),
	UPDATE_SCORE_ERROR("ERR0007"),
	UPDATE_STUDENT_INFORMATION_ERROR("ERR0008");
	
	private String variableName;

	StanderdizedErrorCodeEnum(String variableName) {
		this.variableName = variableName;
	}

	public String variableName() {
		return variableName;
	}

}
