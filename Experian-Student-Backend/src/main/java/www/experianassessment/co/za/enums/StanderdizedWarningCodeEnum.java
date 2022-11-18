package www.experianassessment.co.za.enums;

public enum StanderdizedWarningCodeEnum {

	/**
	 * 
	 * @return USER CODES
	 */

	STUDENT_ALREADY_EXIST("WARN0001"),
	STUDENT_DOES_NOT_EXIST("WARN0002"),
	EMAIL_NOT_VALID("WARN0003"),
	SCORE_GREATER_THAN_100("WARN0004");

	private String variableName;

	StanderdizedWarningCodeEnum(String variableName) {
		this.variableName = variableName;
	}

	public String variableName() {
		return variableName;
	}

}
