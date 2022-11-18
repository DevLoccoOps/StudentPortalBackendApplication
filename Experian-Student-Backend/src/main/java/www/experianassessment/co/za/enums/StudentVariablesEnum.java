package www.experianassessment.co.za.enums;

public enum StudentVariablesEnum {
	// User Variables
	STUDENT_INFO("STUDENT_INFO"),
	
	SEARCH_TYPE("SEARCH_TYPE"),
	TYPE_STUDENT_NUMBER("TYPE_STUDENT_NUMBER"),
	TYPE_FIRST_NAME("TYPE_FIRST_NAME"),
	TYPE_LAST_NAME("TYPE_LAST_NAME"),
	TYPE_EMAIL("TYPE_EMAIL"),
	STUDENT_INFO_LIST("STUDENT_INFO_LIST");

	private String variableName;

	StudentVariablesEnum(String variableName) {
		this.variableName = variableName;
	}

	public String variableName() {
		return variableName;
	}

}
