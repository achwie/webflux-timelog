package achwie.timelog.hours.eventlog;

import java.util.List;

/**
 * 
 * @author 04.01.2020, Achim Wiedemann
 *
 * @param <ID>
 */
public interface EventLog<ID> {
	public void append(IdentifiableEvent<ID> event);

	public List<IdentifiableEvent<ID>> getAll(ID aggregateRootId);
}