package wanted.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Apply 클래스의")
class ApplyTest {

	@DisplayName("생성자는")
	@Nested
	class construct {

		@DisplayName("객체를 생성한다")
		@Test
		void success() {
			assertThatCode(() -> new Apply(1L, 2L))
				.doesNotThrowAnyException();
		}
	}
}
