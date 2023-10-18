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
import wanted.exception.WantedException;
import wanted.service.dto.request.ApplySaveRequest;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ApplyService {

	private final ApplyRepository applyRepository;
	private final JobRepository jobRepository;
	private final UserRepository userRepository;

	@Transactional
	public void apply(Long jobId, ApplySaveRequest request) {
		Job savedJob = findJobById(jobId);
		User savedUser = findUserById(request.getUserId());
		validateUserIsNotApplied(savedJob, savedUser);

		Apply apply = new Apply(savedJob.getId(), savedUser.getId());
		applyRepository.save(apply);
	}

	private void validateUserIsNotApplied(Job savedJob, User savedUser) {
		if (applyRepository.existsByJobIdAndUserId(savedJob.getId(), savedUser.getId())) {
			throw new WantedException("지원한 채용 공고입니다.");
		}
	}

	private Job findJobById(Long id) {
		return jobRepository.findById(id)
			.orElseThrow(() -> new WantedException("존재하지 않는 채용 공고입니다."));
	}

	private User findUserById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new WantedException("존재하지 않는 사용자입니다."));
	}
}
