package www.experianassessment.co.za.request;

import java.io.Serializable;

import www.experianassessment.co.za.common.CommonRequest;
import www.experianassessment.co.za.request.reply.model.StudentInfo;

public class StudentRequest  extends CommonRequest implements Serializable {

	private static final long serialVersionUID = 1L;


	private StudentInfo studentInfo;

	public StudentInfo getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

	

}
