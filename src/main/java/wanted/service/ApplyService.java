package wanted.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wanted.domain.Apply;
import wanted.domain.ApplyRepository;
import wanted.domain.Job;
import wanted.domain.JobRepository;
import wanted.domain.User;
import wanted.domain.UserRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ApplyService {

	private final ApplyRepository applyRepository;
	private final JobRepository jobRepository;
	private final UserRepository userRepository;

	@Transactional
	public void apply(Long jobId, Long userId) {
		Job savedJob = findJobById(jobId);
		User savedUser = findUserById(userId);
		Apply apply = new Apply(savedJob.getId(), savedUser.getId());
		applyRepository.save(apply);
	}

	private Job findJobById(Long id) {
		return jobRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채용 공고입니다."));
	}

	private User findUserById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
	}
}
