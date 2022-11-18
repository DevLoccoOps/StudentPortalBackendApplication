package www.experianassessment.co.za.reply;

import java.io.Serializable;
import java.util.List;

import www.experianassessment.co.za.common.CommonReply;
import www.experianassessment.co.za.request.reply.model.StudentInfo;

public class StudentReply extends CommonReply implements Serializable {

	private static final long serialVersionUID = 1L;

	private StudentInfo studentInfo;
	private List<StudentInfo> studentInfoList;
	
	

	public List<StudentInfo> getStudentInfoList() {
		return studentInfoList;
	}

	public void setStudentInfoList(List<StudentInfo> studentInfoList) {
		this.studentInfoList = studentInfoList;
	}

	public StudentInfo getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

}
