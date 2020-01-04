package achwie.timelog.hours.timelog;

import static java.lang.String.format;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import achwie.timelog.hours.eventlog.IdentifiableEvent;
import achwie.timelog.hours.worker.Worker;

/**
 * 
 * @author 03.01.2020, Achim Wiedemann
 *
 */
public class TimeLog {
	// TODO: Use private fields to protect from mutation without events
	private final UUID id;
	private final Worker worker;
	private final TimeLogSummary summary = new TimeLogSummary();
	private final List<TimeLogEntry> entries = new ArrayList<TimeLogEntry>();

	public TimeLog(UUID id, Worker worker) {
		this.id = id;
		this.worker = worker;
	}

	public static TimeLog fromEvents(UUID id, Worker worker, Iterable<IdentifiableEvent<UUID>> events) {
		final TimeLog timeLog = new TimeLog(id, worker);

		for (IdentifiableEvent<UUID> event : events) {
			if (!id.equals(event.getAggregateRootId())) {
				throw new IllegalStateException(format(
						"Panic! Event in wrong aggregate root stream (stream's aggregateRootId: %s, event.aggregateRootId: %s)!",
						id, event.getAggregateRootId()));
			}

			timeLog.process(event);
		}

		return timeLog;
	}

	public UUID getId() {
		return id;
	}

	public Worker getWorker() {
		return worker;
	}

	public TimeLogSummary getSummary() {
		return summary;
	}

	public List<TimeLogEntry> getLogEntries() {
		return entries;
	}

	private void process(IdentifiableEvent<UUID> event) {
		if (TimeLoggedEvent.class.equals(event.getClass())) {
			processTimeLogged((TimeLoggedEvent) event);
		}
	}

	private void processTimeLogged(TimeLoggedEvent event) {
		if (!event.start.toLocalDate().equals(event.end.toLocalDate()))
			throw new IllegalStateException(format(
					"Event start and end time must be on the same day! (start: %s, end: %s)", event.start, event.end));

		entries.add(new TimeLogEntry(event.start, event.end));

		updateFirstLogDate(event);
		updateLastLogDate(event);
		updateBankedHours(event);
	}

	private void updateFirstLogDate(TimeLoggedEvent event) {
		final LocalDate eventStartDate = event.start.toLocalDate();
		if (eventStartDate.isBefore(summary.firstLogDate))
			summary.firstLogDate = eventStartDate;
	}

	private void updateLastLogDate(TimeLoggedEvent event) {
		final LocalDate eventEndDate = event.end.toLocalDate();
		if (eventEndDate.isAfter(summary.lastLogDate))
			summary.lastLogDate = eventEndDate;
	}

	private void updateBankedHours(TimeLoggedEvent event) {
		final float expectedHours = worker.getWorkingHours(event.start.toLocalDate());

		final ZoneOffset zoneOffset = ZoneOffset.UTC;
		final float actualSeconds = event.end.toEpochSecond(zoneOffset) - event.start.toEpochSecond(zoneOffset);
		final float actualHours = actualSeconds / 60 / 60;

		summary.bankedHours += actualHours - expectedHours;
	}

	/**
	 * 
	 * @author 03.01.2020, Achim Wiedemann
	 */
	public static class TimeLogSummary {
		private float bankedHours = 0;
		private LocalDate firstLogDate = LocalDate.MAX;
		private LocalDate lastLogDate = LocalDate.MIN;

		public float getBankedHours() {
			return bankedHours;
		}

		public LocalDate getFirstLogDate() {
			return firstLogDate;
		}

		public LocalDate getLastLogDate() {
			return lastLogDate;
		}
	}
}