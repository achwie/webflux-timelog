package achwie.timelog.hours.timelog;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author 03.01.2020, Achim Wiedemann
 *
 */
public class LogTimeCommand {
	public final LocalDateTime start;
	public final LocalDateTime end;
	public final UUID workerId;

	@JsonCreator
	public LogTimeCommand(@JsonProperty("start") LocalDateTime start, @JsonProperty("end") LocalDateTime end,
			@JsonProperty("userId") UUID workerId) {
		this.start = start;
		this.end = end;
		this.workerId = workerId;
	}
}
