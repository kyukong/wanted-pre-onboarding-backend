package wanted.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static wanted.fixture.CompanyFixture.*;
import static wanted.fixture.JobFixture.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import wanted.exception.WantedException;

@DisplayName("Job 클래스의")
class JobTest {

	private Job job;

	@BeforeEach
	void setUp() {
		job = WANDTED_BACKEND.toDomain();
	}

	@DisplayName("생성자는")
	@Nested
	class construct {

		@DisplayName("객체를 생성한다")
		@Test
		void success() {
			assertThatCode(WANDTED_BACKEND::toDomain).doesNotThrowAnyException();
		}

		@DisplayName("채용 보상금이 기준치보다 적을 경우 예외가 발생한다")
		@ParameterizedTest
		@ValueSource(ints = {-1, 0})
		void compensationIsUnderThanStandard(int compensation) {
			Company company = WANTED.toDomain();

			assertThatThrownBy(
				() -> new Job(company, "백엔드 개발자", compensation, "주니어 개발자 채용", "Python")
			).isInstanceOf(WantedException.class)
				.hasMessageContaining("채용 보상금은")
				.hasMessageContaining("원 이상이어야 합니다.");
		}

		@DisplayName("공고 내용의 길이가 기준치보다 클 경우 예외가 발생한다")
		@Test
		void descriptionLengthIsOverThanStandard() {
			Company company = WANTED.toDomain();

			String description = "a".repeat(10000000);

			assertThatThrownBy(
				() -> new Job(company, "백엔드 개발자", 1_000_000, description, "Python")
			).isInstanceOf(WantedException.class)
				.hasMessageContaining("채용 공고의 길이는")
				.hasMessageContaining("자 이하로 작성해주세요.");
		}
	}

	@DisplayName("assignCompany 메서드는")
	@Nested
	class assignCompany {

		@DisplayName("채용 공고의 회사 정보를 할당한다")
		@Test
		void success() {
			Company otherCompany = NAVER.toDomain();

			job.assignCompany(otherCompany);

			assertThat(job.getCompany()).isEqualTo(otherCompany);
		}
	}

	@DisplayName("update 메서드는")
	@Nested
	class update {

		@DisplayName("채용 공고 정보를 수정한다")
		@Test
		void success() {
			String position = "시니어 백엔드 개발자";
			int compensation = 10_000_000;
			String description = "주니어가 아닌 시니어 백엔드 개발자 채용";
			String techStack = "Flask";

			job.update(position, compensation, description, techStack);

			assertAll(
				() -> assertThat(job.getPosition()).isEqualTo(position),
				() -> assertThat(job.getCompensation()).isEqualTo(compensation),
				() -> assertThat(job.getDescription()).isEqualTo(description),
				() -> assertThat(job.getTechStack()).isEqualTo(techStack)
			);
		}

		@DisplayName("채용 보상금이 기준치보다 적을 경우 예외가 발생한다")
		@ParameterizedTest
		@ValueSource(ints = {-1, 0})
		void compensationIsUnderThanStandard(int compensation) {

			assertThatThrownBy(
				() -> job.update("시니어 백엔드 개발자", compensation, "주니어가 아닌 시니어 백엔드 개발자 채용", "Flask")
			).isInstanceOf(WantedException.class)
				.hasMessageContaining("채용 보상금은")
				.hasMessageContaining("원 이상이어야 합니다.");
		}

		@DisplayName("공고 내용의 길이가 기준치보다 클 경우 예외가 발생한다")
		@Test
		void descriptionLengthIsOverThanStandard() {
			String description = "a".repeat(10000000);

			assertThatThrownBy(
				() -> job.update("시니어 백엔드 개발자", 10_000_000, description, "Flask")
			).isInstanceOf(WantedException.class)
				.hasMessageContaining("채용 공고의 길이는")
				.hasMessageContaining("자 이하로 작성해주세요.");
		}
	}
}
