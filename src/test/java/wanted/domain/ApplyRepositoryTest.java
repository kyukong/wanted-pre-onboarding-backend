package wanted.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("ApplyRepository 클래스의")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ApplyRepositoryTest {

	@Autowired
	private ApplyRepository applyRepository;

	@DisplayName("existsByJobAndUser 메서드는")
	@Nested
	class existsByJobAndUser {

		@DisplayName("채용 공고의 지원 내역이 있을 경우 true 를 반환한다")
		@Test
		void successTrue() {
			Long jobId = 1L;
			Long userId = 2L;

			Apply apply = new Apply(jobId, userId);
			applyRepository.save(apply);

			boolean exists = applyRepository.existsByJobIdAndUserId(jobId, userId);

			assertThat(exists).isTrue();
		}

		@DisplayName("채용 공고의 지원 내역이 없을 경우 false 를 반환한다")
		@Test
		void successFalse() {
			Long notExistJobId = 1L;
			Long notExistUserId = 2L;

			boolean exists = applyRepository.existsByJobIdAndUserId(notExistJobId, notExistUserId);

			assertThat(exists).isFalse();
		}
	}
}
