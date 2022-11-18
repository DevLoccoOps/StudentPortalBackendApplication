package www.experianassessment.co.za.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import www.experianassessment.co.za.model.StudentScoreRecordsInformation;

@Repository
public interface StudentScoreRecordsInformationRepository extends JpaRepository<StudentScoreRecordsInformation, Long> {
	
	
	List<StudentScoreRecordsInformation> findAverageScoresByStudentNumber(String studentNo);
	
	Optional<StudentScoreRecordsInformation> findRecordByStudentInformation(Long studentInformationId);
	
}
