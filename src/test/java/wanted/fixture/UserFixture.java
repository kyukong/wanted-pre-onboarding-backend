package wanted.fixture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wanted.domain.User;

@Getter
@RequiredArgsConstructor
public enum UserFixture {

	USER1(null, "김티드"),
	;

	private final Long id;
	private final String name;

	public User toDomain() {
		return new User(name);
	}
}
