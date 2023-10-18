package wanted.fixture;

import static wanted.fixture.CompanyFixture.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wanted.domain.Company;
import wanted.domain.Job;

@Getter
@RequiredArgsConstructor
public enum JobFixture {

	BACKEND(null, null, "백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python"),
	WANDTED_BACKEND(null, WANTED.toDomain(), "백엔드 개발자", 1_000_000, "주니어 개발자 채용", "Python"),
	;

	private final Long id;
	private final Company company;
	private final String position;
	private final int compensation;
	private final String description;
	private final String techStack;

	public Job toDomain() {
		return new Job(null, company, position, compensation, description, techStack);
	}

	public Job toDomain(Company company) {
		return new Job(null, company, position, compensation, description, techStack);
	}

	public Job toPersistedDomain(long id) {
		return new Job(id, company, position, compensation, description, techStack);
	}

	public Job toPersistedDomain(long id, Company company) {
		return new Job(id, company, position, compensation, description, techStack);
	}
}
