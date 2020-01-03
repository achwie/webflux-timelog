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

	@JsonCreator
	public Worker(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {
		this.id = id;
		this.name = name;
	}

	public static Worker copyNewId(UUID id, Worker p) {
		return new Worker(id, p.name);
	}

	@Override
	public UUID getId() {
		return id;
	}
}