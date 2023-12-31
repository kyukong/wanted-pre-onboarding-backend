package wanted.service.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PagingRequest {

	private final int page;
	private final int size;
}
