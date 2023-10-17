package wanted.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wanted.service.JobService;
import wanted.service.dto.request.JobSaveRequest;
import wanted.service.dto.request.JobUpdateRequest;
import wanted.service.dto.request.PagingRequest;
import wanted.service.dto.response.JobDetailResponse;
import wanted.service.dto.response.JobResponse;
import wanted.service.dto.response.PagingResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {

	private final JobService jobService;

	@PostMapping
	public ResponseEntity<Void> save(@ModelAttribute JobSaveRequest request) {
		Long saveJobId = jobService.save(request);
		return ResponseEntity.created(URI.create("/jobs/" + saveJobId)).build();
	}

	@PutMapping("/{jobId}")
	public ResponseEntity<Void> update(@PathVariable Long jobId, @ModelAttribute JobUpdateRequest request) {
		jobService.update(jobId, request);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{jobId}")
	public ResponseEntity<Void> delete(@PathVariable Long jobId) {
		jobService.delete(jobId);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<PagingResponse<JobResponse>> findAll(@ModelAttribute PagingRequest request) {
		PagingResponse<JobResponse> responses = jobService.findAll(request);
		return ResponseEntity.ok().body(responses);
	}

	@GetMapping("/{jobId}")
	public ResponseEntity<JobDetailResponse> findById(@PathVariable Long jobId) {
		JobDetailResponse response = jobService.findById(jobId);
		return ResponseEntity.ok().body(response);
	}
}
