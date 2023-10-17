package wanted.service.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wanted.domain.Job;

@Getter
@RequiredArgsConstructor
public class JobResponse {

	private final long jobId;
	private final long companyId;
	private final String companyName;
	private final String country;
	private final String region;
	private final String position;
	private final int compensation;
	private final String techStack;

	public static JobResponse from(Job job) {
		return new JobResponse(
			job.getId(),
			job.getCompany().getId(),
			job.getCompany().getName(),
			job.getCompany().getCountry(),
			job.getCompany().getRegion(),
			job.getPosition(),
			job.getCompensation(),
			job.getTechStack()
		);
	}
}
