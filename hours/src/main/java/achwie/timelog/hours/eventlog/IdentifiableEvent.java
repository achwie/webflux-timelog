package achwie.timelog.hours.eventlog;

/**
 * 
 * @author 04.01.2020, Achim Wiedemann
 *
 */
public interface IdentifiableEvent<T> {
	public T getAggregateRootId();
}
