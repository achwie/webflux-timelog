package achwie.timelog.users.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 
 * @author 31.12.2019, Achim Wiedemann
 */
public class InMemoryRepository<T extends Identifiable<ID>, ID> {
	private final Map<ID, T> items = new HashMap<>();

	public List<T> findAll() {
		return findAll((T item) -> true);
	}

	public List<T> findAll(Predicate<T> filterPredicate) {
		final List<T> filteredItems = items.values().stream().filter(filterPredicate::test)
				.collect(Collectors.toList());

		return filteredItems;
	}

	public T findById(ID id) {
		return items.get(id);
	}

	public T upsert(T item) {
		Objects.requireNonNull(item, "Item to upsert must not be null!");
		Objects.requireNonNull(item.getId(), "ID of item to upsert must not be null!");

		items.put(item.getId(), item);

		return item;
	}

	public T insert(T item) {
		Objects.requireNonNull(item, "Item to insert must not be null!");
		Objects.requireNonNull(item.getId(), "ID of item to insert must not be null!");

		items.put(item.getId(), item);

		return item;
	}

	public void deleteById(ID id) {
		items.remove(id);
	}
}
