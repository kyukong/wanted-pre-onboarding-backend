package wanted.service.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JobSearchRequest {

	private final String company;
	private final String techStack;
}
