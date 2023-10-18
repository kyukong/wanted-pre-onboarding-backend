package wanted.fixture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wanted.domain.Company;

@Getter
@RequiredArgsConstructor
public enum CompanyFixture {

	WANTED(null, "원티드", "한국", "서울"),
	NAVER(null, "네이버", "한국", "판교"),
	;

	private final Long id;
	private final String name;
	private final String country;
	private final String region;

	public Company toDomain() {
		return new Company(null, name, country, region);
	}

	public Company toPersistedDomain(long id) {
		return new Company(id, name, country, region);
	}
}
