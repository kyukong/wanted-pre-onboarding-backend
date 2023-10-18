package wanted.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static wanted.fixture.CompanyFixture.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import wanted.domain.Company;
import wanted.domain.CompanyRepository;
import wanted.domain.Job;
import wanted.domain.JobRepository;
import wanted.exception.WantedException;
import wanted.service.dto.request.JobSaveRequest;
import wanted.service.dto.request.JobUpdateRequest;
import wanted.service.dto.request.PagingRequest;
import wanted.service.dto.response.JobDetailResponse;
import wanted.service.dto.response.JobResponse;
import wanted.service.dto.response.PagingResponse;

@DisplayName("JobService 클래스의")
@ExtendWith(MockitoExtension.class)
class JobServiceTest {

	@Mock
	private JobRepository jobRepository;

	@Mock
	private CompanyRepository companyRepository;

	private JobService jobService;

	@BeforeEach
	void setUp() {
		jobService = new JobService(jobRepository, companyRepository);
	}

	@DisplayName("save 메서드는")
	@Nested
	class Save {

		@DisplayName("채용 공고를 저장한다")
		@Test
		void success() {
			Long companyId = 1L;
			JobSaveRequest request = new JobSaveRequest(companyId, "백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python");

			Company company = WANTED.toPersistedDomain(companyId);
			Job savedJob = new Job(2L, company, "백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python");
			given(companyRepository.findById(companyId)).willReturn(Optional.of(company));
			given(jobRepository.save(any())).willReturn(savedJob);

			Long savedJobId = jobService.save(request);

			assertThat(savedJobId).isEqualTo(savedJob.getId());
		}

		@DisplayName("존재하지 않는 회사 아이디를 입력할 경우 예외가 발생한다")
		@Test
		void companyIsNotExist() {
			Long notExistCompanyId = 1L;
			JobSaveRequest request = new JobSaveRequest(notExistCompanyId, "백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python");

			assertThatThrownBy(() -> jobService.save(request))
				.isInstanceOf(WantedException.class)
				.hasMessage("존재하지 않는 회사입니다.");
		}
	}

	@DisplayName("update 메서드는")
	@Nested
	class update {

		@DisplayName("채용 공고 정보를 수정한다")
		@Test
		void success() {
			Long id = 1L;
			JobUpdateRequest request = new JobUpdateRequest("백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python");

			Company company = WANTED.toPersistedDomain(2L);
			Job savedJob = new Job(id, company, "백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python");
			given(jobRepository.findById(id)).willReturn(Optional.of(savedJob));

			assertThatCode(() -> jobService.update(id, request))
				.doesNotThrowAnyException();
		}

		@DisplayName("존재하지 않는 채용 공고 아이디를 입력할 경우 예외가 발생한다")
		@Test
		void jobIsNotExist() {
			Long notExistJobId = 1L;
			JobUpdateRequest request = new JobUpdateRequest("백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python");

			assertThatThrownBy(() -> jobService.update(notExistJobId, request))
				.isInstanceOf(WantedException.class)
				.hasMessage("존재하지 않는 채용 공고입니다.");
		}
	}

	@DisplayName("delete 메서드는")
	@Nested
	class delete {

		@DisplayName("채용 공고를 삭제한다")
		@Test
		void success() {
			Long id = 1L;

			Company company = WANTED.toPersistedDomain(2L);
			Job savedJob = new Job(id, company, "백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python");
			given(jobRepository.findById(id)).willReturn(Optional.of(savedJob));
			doNothing().when(jobRepository).delete(savedJob);

			assertThatCode(() -> jobService.delete(id))
				.doesNotThrowAnyException();
		}

		@DisplayName("존재하지 않는 채용 공고 아이디를 입력할 경우 예외가 발생한다")
		@Test
		void jobIsNotExist() {
			Long notExistJobId = 1L;

			assertThatThrownBy(() -> jobService.delete(notExistJobId))
				.isInstanceOf(WantedException.class)
				.hasMessage("존재하지 않는 채용 공고입니다.");
		}
	}

	@DisplayName("findAll 메서드는")
	@Nested
	class findAll {

		@DisplayName("채용 공고 목록을 조회한다")
		@Test
		void success() {
			int page = 1;
			int size = 10;
			PagingRequest request = new PagingRequest(page, size);

			Company company = WANTED.toPersistedDomain(2L);
			Job savedJob = new Job(1L, company, "백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python");
			List<Job> savedJobs = List.of(savedJob);
			given(jobRepository.findAll(PageRequest.of(page - 1, size)))
				.willReturn(new PageImpl<>(savedJobs));

			PagingResponse<JobResponse> responses = jobService.findAll(request);

			assertAll(
				() -> assertThat(responses.getPage()).isEqualTo(page),
				() -> assertThat(responses.getSize()).isEqualTo(size),
				() -> assertThat(responses.getContent().size()).isEqualTo(savedJobs.size())
			);
		}
	}

	@DisplayName("findById 메서드는")
	@Nested
	class findById {

		@DisplayName("채용 공고를 조회한다")
		@Test
		void success() {
			Long id = 1L;

			Company company = WANTED.toPersistedDomain(2L);
			Job savedJob = new Job(id, company, "백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python");
			given(jobRepository.findById(id)).willReturn(Optional.of(savedJob));

			JobDetailResponse response = jobService.findById(id);

			assertThat(response.getJobId()).isEqualTo(id);
		}

		@DisplayName("존재하지 않는 채용 공고 아이디를 입력할 경우 예외가 발생한다")
		@Test
		void jobIsNotExist() {
			Long notExistJobId = 1L;

			assertThatThrownBy(() -> jobService.findById(notExistJobId))
				.isInstanceOf(WantedException.class)
				.hasMessage("존재하지 않는 채용 공고입니다.");
		}
	}
}
