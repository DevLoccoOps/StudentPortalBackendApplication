package www.experianassessment.co.za.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import www.experianassessment.co.za.model.StudentInformation;

@Repository
public interface StudentInformationRepository extends JpaRepository<StudentInformation, Long> {
	
	Optional<StudentInformation> findStudentByStudentNumber(String studentNo);
	
	List<StudentInformation> findStudentByFirstName(String firstName);
	List<StudentInformation> findStudentByLastName(String lastName);
	List<StudentInformation> findStudentByEmail(String email);
	

}
