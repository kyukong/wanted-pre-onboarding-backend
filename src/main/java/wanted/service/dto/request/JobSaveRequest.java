package wanted.service.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JobSaveRequest {

	private final Long companyId;
	private final String position;
	private final Integer compensation;
	private final String description;
	private final String techStack;
}
