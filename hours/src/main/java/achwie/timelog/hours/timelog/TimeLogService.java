package achwie.timelog.hours.timelog;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import achwie.timelog.hours.eventlog.IdentifiableEvent;
import achwie.timelog.hours.worker.Worker;
import achwie.timelog.hours.worker.WorkerService;

/**
 * 
 * @author 04.01.2020, Achim Wiedemann
 */
@Service
public class TimeLogService {
	private final TimeLogEventLog timeLogEventLog;
	private final WorkerService workerService;

	public TimeLogService(TimeLogEventLog timeLogEventLog, WorkerService workerService) {
		this.timeLogEventLog = timeLogEventLog;
		this.workerService = workerService;
	}

	public List<TimeLogEntry> findAllForWorker(UUID workerId) {
		final List<IdentifiableEvent<UUID>> events = timeLogEventLog.getAll(workerId);

		final Worker worker = workerService.findById(workerId);

		final TimeLog timeLog = TimeLog.fromEvents(workerId, worker, events);

		return timeLog.getLogEntries();
	}

	public void process(LogTimeCommand logTimeCommand) {
		// TODO: Business checks (e.g. start and end time on same day, time overlaps)

		final TimeLoggedEvent timeLoggedEvent = eventFromCommand(logTimeCommand);
		timeLogEventLog.append(timeLoggedEvent);
	}

	private TimeLoggedEvent eventFromCommand(LogTimeCommand logTimeCommand) {
		return new TimeLoggedEvent(logTimeCommand.workerId, logTimeCommand.start, logTimeCommand.end);
	}

}
