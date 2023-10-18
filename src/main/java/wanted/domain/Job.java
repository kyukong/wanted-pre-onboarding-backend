package wanted.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.exception.WantedException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Job {

	private static final int DESCRIPTION_LENGTH = 1_000;
	private static final int COMPENSATION_MAXIMUM = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Company company;

	private String position;
	private int compensation;

	@Column(length = DESCRIPTION_LENGTH)
	private String description;
	private String techStack;

	public Job(Long id, Company company, String position, int compensation, String description, String techStack) {
		validateCompensationIsOverThanStandard(compensation);
		validateDescriptionLengthIsUnderThanStandard(description);

		this.id = id;
		this.company = company;
		this.position = position;
		this.compensation = compensation;
		this.description = description;
		this.techStack = techStack;
	}

	public Job(Company company, String position, int compensation, String description, String techStack) {
		this(null, company, position, compensation, description, techStack);
	}

	public void assignCompany(Company company) {
		this.company = company;
	}

	public void update(String position, int compensation, String description, String techStack) {
		validateCompensationIsOverThanStandard(compensation);
		validateDescriptionLengthIsUnderThanStandard(description);

		this.position = position;
		this.compensation = compensation;
		this.description = description;
		this.techStack = techStack;
	}

	private void validateCompensationIsOverThanStandard(int compensation) {
		if (compensation <= COMPENSATION_MAXIMUM) {
			throw new WantedException(String.format("채용 보상금은 %d원 이상이어야 합니다.", COMPENSATION_MAXIMUM));
		}
	}

	private void validateDescriptionLengthIsUnderThanStandard(String description) {
		if (description.length() > DESCRIPTION_LENGTH) {
			throw new WantedException(String.format("채용 공고의 길이는 %d자 이하로 작성해주세요.", DESCRIPTION_LENGTH));
		}
	}
}
