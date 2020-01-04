package achwie.timelog.hours.timelog;

import java.time.LocalDateTime;

/**
 * 
 * @author 03.01.2020, Achim Wiedemann
 *
 */
public class TimeLogEntry {
	public final LocalDateTime start;
	public final LocalDateTime end;

	public TimeLogEntry(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
	}
}
