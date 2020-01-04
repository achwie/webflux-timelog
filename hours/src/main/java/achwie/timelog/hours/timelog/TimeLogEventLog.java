package achwie.timelog.hours.timelog;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import achwie.timelog.hours.eventlog.EventLog;
import achwie.timelog.hours.eventlog.IdentifiableEvent;
import achwie.timelog.hours.eventlog.InMemoryEventLog;

/**
 * 
 * @author 04.01.2020, Achim Wiedemann
 *
 */
@Repository
public class TimeLogEventLog implements EventLog<UUID> {
	private final EventLog<UUID> eventLog = new InMemoryEventLog<>();

	@Override
	public void append(IdentifiableEvent<UUID> event) {
		eventLog.append(event);
	}

	@Override
	public List<IdentifiableEvent<UUID>> getAll(UUID aggregateRootId) {
		return eventLog.getAll(aggregateRootId);
	}
}
