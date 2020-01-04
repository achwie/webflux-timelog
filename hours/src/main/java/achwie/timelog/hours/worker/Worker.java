package achwie.timelog.hours.worker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author 03.01.2020, Achim Wiedemann
 *
 */
public class Worker {
	public final UUID id;
	public final String name;
	public final float hoursPerWeek;

	@JsonCreator
	public Worker(@JsonProperty("id") UUID id, @JsonProperty("name") String name,
			@JsonProperty("hoursPerWeek") float hoursPerWeek) {
		this.id = id;
		this.name = name;
		this.hoursPerWeek = hoursPerWeek;
	}

	public float getWorkingHours(LocalDate date) {
		final DayOfWeek dayOfWeek = date.getDayOfWeek();
		final boolean isWeekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(dayOfWeek);

		// TODO: What about part time workers (e.g. 3 days / week)
		return isWeekend ? 0 : (hoursPerWeek / 5);
	}
}
