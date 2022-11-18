package www.experianassessment.co.za.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "student_score_info")
public class StudentScoreRecordsInformation {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_score_info_id_seq")
	@SequenceGenerator(name = "student_score_info_id_seq", sequenceName = "student_score_info_id_seq", allocationSize = 1)
	@Column(name = "student_score_info_id")
	private Long id;
	
	@ManyToOne(targetEntity = StudentInformation.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "student_info_id")
	private StudentInformation studentInformation;
	
	private int currentScore;
	
	private int averageScore;
	
	private Date timeStamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public StudentInformation getStudentInformation() {
		return studentInformation;
	}

	public void setStudentInformation(StudentInformation studentInformation) {
		this.studentInformation = studentInformation;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public int getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(int averageScore) {
		this.averageScore = averageScore;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
