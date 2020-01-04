package achwie.timelog.hours.worker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * 
 * @author 23.12.2019, Achim Wiedemann
 */
@Service
public class WorkerService {
	private final WebClient webClient;

	public WorkerService(@Value("${userService.baseUrl}") String userServiceBaseUrl) {
		this.webClient = WebClient.create(userServiceBaseUrl);
	}

	// TODO: Return Flux
	public List<Worker> findByName(String name) {
		Mono<Worker[]> workers = webClient.get().uri("/workers?name={name}", name).accept(MediaType.APPLICATION_JSON)
				.retrieve().bodyToMono(Worker[].class);

		final CompletableFuture<Worker[]> workersResult = new CompletableFuture<>();
		workers.subscribe(workersResult::complete);

		try {
			return Arrays.asList(workersResult.get(3, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	// TODO: Return Flux
	public Worker findById(UUID workerId) {
		Mono<Worker> worker = webClient.get().uri("/workers/{id}", workerId).accept(MediaType.APPLICATION_JSON)
				.retrieve().bodyToMono(Worker.class);

		final CompletableFuture<Worker> workerResult = new CompletableFuture<>();
		worker.subscribe(workerResult::complete);

		try {
			return workerResult.get(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
