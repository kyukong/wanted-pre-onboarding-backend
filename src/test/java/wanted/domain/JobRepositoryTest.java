package wanted.domain;

import static org.assertj.core.api.Assertions.*;
import static wanted.fixture.JobFixture.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("JobRepository 클래스의")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class JobRepositoryTest {

	@Autowired
	private JobRepository jobRepository;

	@DisplayName("findIdsByIdAndCompany 메서드는")
	@Nested
	class findIdsByIdAndCompany {

		@DisplayName("회사의 다른 채용 공고 목록을 조회한다")
		@Test
		void success() {
			Job job = WANDTED_BACKEND.toDomain();
			Job savedJob = jobRepository.save(job);

			int count = 3;
			for (int i = 0; i < count; i++) {
				jobRepository.save(WANDTED_BACKEND.toDomain());
			}

			List<Long> ids = jobRepository.findIdsByIdAndCompany(savedJob.getId(), job.getCompany());

			assertThat(ids.size()).isEqualTo(count);
		}
	}
}
