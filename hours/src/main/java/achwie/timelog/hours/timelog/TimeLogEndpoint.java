package achwie.timelog.hours.timelog;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author 23.12.2019, Achim Wiedemann
 */
@RestController
@RequestMapping("/timelog")
public class TimeLogEndpoint {
	private final TimeLogService timeLogService;

	public TimeLogEndpoint(TimeLogService timeLogService) {
		this.timeLogService = timeLogService;
	}

	@PostMapping("/{workerId}")
	public void logTime(LogTimeCommand logTimeCommand) {
		timeLogService.process(logTimeCommand);
	}

	// TODO: Return reactive type (e.g. Flux)
	@GetMapping("/{workerId}")
	public List<TimeLogEntry> findAllForWorker(UUID workerId) {
		return timeLogService.findAllForWorker(workerId);
	}
}
