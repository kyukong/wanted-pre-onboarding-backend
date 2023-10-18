package wanted.domain;

import static org.assertj.core.api.Assertions.*;
import static wanted.fixture.CompanyFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Company 클래스의")
class CompanyTest {

	@DisplayName("생성자는")
	@Nested
	class construct {

		@DisplayName("객체를 생성한다")
		@Test
		void success() {
			assertThatCode(WANTED::toDomain).doesNotThrowAnyException();
		}
	}
}
