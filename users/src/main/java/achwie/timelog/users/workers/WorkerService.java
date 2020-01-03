package achwie.timelog.users.workers;

import static achwie.timelog.users.workers.Worker.PROP_NAME;
import static java.lang.String.format;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import achwie.timelog.users.repo.InMemoryRepository;
import achwie.timelog.users.repo.PropertyFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author 23.12.2019, Achim Wiedemann
 */
@Service
public class WorkerService {
	private final InMemoryRepository<Worker, UUID> workerRepo = new InMemoryRepository<>();

	public Flux<Worker> findAll(PropertyFilter propFilter) {
		final List<Worker> workers = workerRepo.findAll(
				(Worker p) -> propFilter.hasFilter(PROP_NAME) ? propFilter.testContains(PROP_NAME, p.name) : true);

		return Flux.fromIterable(workers);
	}

	public Mono<Worker> findById(UUID id) {
		return Mono.just(workerRepo.findById(id));
	}

	public Mono<Worker> upsert(Worker worker) {
		worker = ensureAssignedId(worker);

		final Worker upsertedWorker = workerRepo.upsert(worker);

		return Mono.just(upsertedWorker);
	}

	public Mono<Worker> insert(Worker worker) {
		worker = ensureAssignedId(worker);

		if (workerRepo.findById(worker.getId()) != null)
			throw new IllegalArgumentException(
					format("A worker with the same ID already exists! (id: %s)", worker.getId()));

		final Worker insertedWorker = workerRepo.insert(worker);

		return Mono.just(insertedWorker);
	}

	public void delete(UUID id) {
		workerRepo.deleteById(id);
	}

	private Worker ensureAssignedId(Worker worker) {
		if (worker.getId() == null)
			worker = Worker.copyNewId(nextId(), worker);

		return worker;
	}

	private UUID nextId() {
		return UUID.randomUUID();
	}
}
