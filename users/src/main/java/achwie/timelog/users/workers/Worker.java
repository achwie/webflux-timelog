package achwie.timelog.users.workers;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import achwie.timelog.users.repo.Identifiable;

/**
 * A working person, i.e. somebody who wants to log their working hours.
 * 
 * @author 23.12.2019, Achim Wiedemann
 */
public class Worker implements Identifiable<UUID> {
	public static final String PROP_NAME = "name";
	public final UUID id;
	public final String name;
	public final float hoursPerWeek;

	@JsonCreator
	public Worker(@JsonProperty("id") UUID id, @JsonProperty("name") String name, @JsonProperty("hoursPerWeek")float hoursPerWeek) {
		this.id = id;
		this.name = name;
		this.hoursPerWeek = hoursPerWeek;
	}

	public static Worker copyNewId(UUID id, Worker p) {
		return new Worker(id, p.name, p.hoursPerWeek);
	}

	@Override
	public UUID getId() {
		return id;
	}
}