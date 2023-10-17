package wanted.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wanted.domain.Company;
import wanted.domain.CompanyRepository;
import wanted.domain.Job;
import wanted.domain.JobRepository;
import wanted.service.dto.request.JobSaveRequest;
import wanted.service.dto.request.JobUpdateRequest;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JobService {

	private final JobRepository jobRepository;
	private final CompanyRepository companyRepository;

	@Transactional
	public Long save(JobSaveRequest request) {
		Company savedCompany = findCompanyById(request.getCompanyId());
		Job job = new Job(
			savedCompany, request.getPosition(), request.getCompensation(), request.getDescription(),
			request.getTechStack()
		);

		job.assignCompany(savedCompany);
		Job savedJob = jobRepository.save(job);
		return savedJob.getId();
	}

	@Transactional
	public void update(Long id, JobUpdateRequest request) {
		Job savedJob = findJobById(id);
		savedJob.update(request.getPosition(), request.getCompensation(), request.getDescription(), request.getTechStack());
	}

	@Transactional
	public void delete(Long id) {
		Job savedJob = findJobById(id);
		jobRepository.delete(savedJob);
	}

	public List<Job> findAll(int page, int size) {
		Page<Job> jobs = jobRepository.findAll(PageRequest.of(page - 1, size));
		return jobs.getContent();
	}

	public Job findById(Long id) {
		return findJobById(id);
	}

	private Job findJobById(Long id) {
		return jobRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채용 공고입니다."));
	}

	private Company findCompanyById(Long id) {
		return companyRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회사입니다."));
	}
}
