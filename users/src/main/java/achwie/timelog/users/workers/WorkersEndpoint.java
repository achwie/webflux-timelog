package achwie.timelog.users.workers;

import static achwie.timelog.users.workers.Worker.PROP_NAME;
import static java.lang.String.format;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import achwie.timelog.users.repo.PropertyFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author 23.12.2019, Achim Wiedemann
 */
@RestController
@RequestMapping("/workers")
public class WorkersEndpoint {
	private final WorkerService workerService;

	public WorkersEndpoint(WorkerService workerService) {
		this.workerService = workerService;
	}

	@GetMapping
	public Flux<Worker> findAll(@RequestParam(value = PROP_NAME, required = false) String filterName) {
		final PropertyFilter filter = PropertyFilter.builder().withProperty(PROP_NAME, filterName).build();

		return workerService.findAll(filter);
	}

	@GetMapping("/{id}")
	public Mono<Worker> findById(@PathVariable("id") UUID id) {
		return workerService.findById(id);
	}

	@PutMapping("/{id}")
	public Mono<Worker> upsert(@PathVariable("id") UUID id, @RequestBody Worker worker) {
		if (id != null) {
			if (worker.getId() == null) {
				worker = Worker.copyNewId(id, worker);
			} else if (!id.equals(worker.getId())) {
				throw new IllegalArgumentException(format(
						"Could not update worker - worker.id and path ID don't match (worker.id: %s, path ID: %s)!",
						worker.id, id));
			}
		}

		return workerService.upsert(worker);
	}

	@PostMapping
	public Mono<Worker> create(@RequestBody Worker worker) {
		return workerService.insert(worker);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		workerService.delete(id);
	}
}
