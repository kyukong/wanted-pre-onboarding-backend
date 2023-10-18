package wanted.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wanted.service.ApplyService;
import wanted.service.dto.request.ApplySaveRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs/{jobId}/apply")
public class ApplyController {

	private final ApplyService applyService;

	@PostMapping
	public ResponseEntity<Void> apply(@PathVariable Long jobId, @RequestBody ApplySaveRequest request) {
		applyService.apply(jobId, request);
		return ResponseEntity.ok().build();
	}
}
