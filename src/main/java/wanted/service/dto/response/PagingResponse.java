package wanted.service.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PagingResponse<E> {

	private final int page;
	private final int size;
	private final boolean hasNext;
	private final List<E> content;

	public static <E> PagingResponse<E> of(int page, int size, boolean hasNext, List<E> content) {
		return new PagingResponse<>(page, size, hasNext, content);
	}
}
