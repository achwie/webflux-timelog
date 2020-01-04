package achwie.timelog.hours.timelog;

import java.time.LocalDateTime;
import java.util.UUID;

import achwie.timelog.hours.eventlog.IdentifiableEvent;

/**
 * 
 * @author 04.01.2020, Achim Wiedemann
 */
public class TimeLoggedEvent implements IdentifiableEvent<UUID> {
	public final UUID aggregateRootId;
	public final LocalDateTime start;
	public final LocalDateTime end;

	public TimeLoggedEvent(UUID aggregateRootId, LocalDateTime start, LocalDateTime end) {
		this.aggregateRootId = aggregateRootId;
		this.start = start;
		this.end = end;
	}

	@Override
	public UUID getAggregateRootId() {
		return aggregateRootId;
	}
}