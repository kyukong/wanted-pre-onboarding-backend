package wanted.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static wanted.fixture.UserFixture.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import wanted.domain.ApplyRepository;
import wanted.domain.Company;
import wanted.domain.Job;
import wanted.domain.JobRepository;
import wanted.domain.User;
import wanted.domain.UserRepository;
import wanted.exception.WantedException;
import wanted.service.dto.request.ApplySaveRequest;

@DisplayName("ApplyService 클래스의")
@ExtendWith(MockitoExtension.class)
class ApplyServiceTest {

	@Mock
	private ApplyRepository applyRepository;

	@Mock
	private JobRepository jobRepository;

	@Mock
	private UserRepository userRepository;

	private ApplyService applyService;

	@BeforeEach
	void setUp() {
		applyService = new ApplyService(applyRepository, jobRepository, userRepository);
	}

	@DisplayName("apply 메서드는")
	@Nested
	class apply {

		@DisplayName("채용 공고를 지원한다")
		@Test
		void success() {
			Long jobId = 1L;
			Long userId = 2L;
			ApplySaveRequest request = new ApplySaveRequest(userId);

			Company savedCompany = new Company(1L, "원티드", "한국", "서울");
			Job savedJob = new Job(2L, savedCompany, "백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python");
			given(jobRepository.findById(jobId)).willReturn(Optional.of(savedJob));

			User savedUser = USER1.toDomain();
			given(userRepository.findById(userId)).willReturn(Optional.of(savedUser));

			assertThatCode(() -> applyService.apply(jobId, request))
				.doesNotThrowAnyException();
		}

		@DisplayName("존재하지 않는 채용 공고 아이디를 입력할 경우 예외가 발생한다")
		@Test
		void jobIsNotExist() {
			Long notExistJobId = 1L;
			Long userId = 2L;
			ApplySaveRequest request = new ApplySaveRequest(userId);

			assertThatThrownBy(() -> applyService.apply(notExistJobId, request))
				.isInstanceOf(WantedException.class)
				.hasMessage("존재하지 않는 채용 공고입니다.");
		}

		@DisplayName("존재하지 않는 사용자 아이디를 입력할 경우 예외가 발생한다")
		@Test
		void userIsNotExist() {
			Long jobId = 1L;
			Long notExistUserId = 2L;
			ApplySaveRequest request = new ApplySaveRequest(notExistUserId);

			Company savedCompany = new Company(1L, "원티드", "한국", "서울");
			Job savedJob = new Job(2L, savedCompany, "백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python");
			given(jobRepository.findById(jobId)).willReturn(Optional.of(savedJob));

			assertThatThrownBy(() -> applyService.apply(jobId, request))
				.isInstanceOf(WantedException.class)
				.hasMessage("존재하지 않는 사용자입니다.");
		}

		@DisplayName("채용 공고에 지원한 내역이 있을 경우 예외가 발생한다")
		@Test
		void userIsAlreadyApplied() {
			Long jobId = 1L;
			Long userId = 2L;
			ApplySaveRequest request = new ApplySaveRequest(userId);

			Company savedCompany = new Company(1L, "원티드", "한국", "서울");
			Job savedJob = new Job(2L, savedCompany, "백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python");
			given(jobRepository.findById(jobId)).willReturn(Optional.of(savedJob));

			User savedUser = USER1.toDomain();
			given(userRepository.findById(userId)).willReturn(Optional.of(savedUser));

			given(applyRepository.existsByJobIdAndUserId(any(), any())).willReturn(true);

			assertThatThrownBy(() -> applyService.apply(jobId, request))
				.isInstanceOf(WantedException.class)
				.hasMessage("지원한 채용 공고입니다.");
		}
	}
}
