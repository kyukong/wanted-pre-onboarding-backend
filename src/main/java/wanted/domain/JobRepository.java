package wanted.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobRepository extends JpaRepository<Job, Long> {

	@Query(
		"""
			select j.id
			from Job j
			where j.id != :id and j.company = :company
		"""
	)
	List<Long> findIdsByIdAndCompany(Long id, Company company);
}
