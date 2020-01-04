package achwie.timelog.hours.eventlog;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author 03.01.2020, Achim Wiedemann
 */
public class InMemoryEventLog<ID> implements EventLog<ID> {
	private final Map<ID, List<IdentifiableEvent<ID>>> eventsByAggregateRoot = new ConcurrentHashMap<>();

	@Override
	public void append(IdentifiableEvent<ID> event) {
		eventsByAggregateRoot
				.computeIfAbsent(event.getAggregateRootId(), key -> new CopyOnWriteArrayList<IdentifiableEvent<ID>>())
				.add(event);
	}

	@Override
	public List<IdentifiableEvent<ID>> getAll(ID aggregateRootId) {
		return Collections
				.unmodifiableList(eventsByAggregateRoot.getOrDefault(aggregateRootId, Collections.emptyList()));
	}
}
