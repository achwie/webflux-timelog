package achwie.timelog.users.repo;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 02.01.2020, Achim Wiedemann
 */
public class PropertyFilter{
	private final Map<String, Object> filterProps;
	
	private PropertyFilter(Map<String, Object> filterProps) {
		this.filterProps = filterProps;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String filterProperty, Class<T> type) {
		return (T)filterProps.get(filterProperty);
	}
	
	public boolean hasFilter(String filterProperty) {
		return filterProps.get(filterProperty) != null;
	}
	
	public boolean testContains(String filterProperty, String str) {
		final String filterValue = get(filterProperty, String.class);
		if (filterValue == null || str == null) {
			return false;
		}
		
		return str.toLowerCase().contains(filterValue.toLowerCase());
	}

	
	public static PropertyFilterBuilder builder() {
		return new PropertyFilterBuilder();
	}
	
	/**
	 * 
	 * @author 03.01.2020, Achim Wiedemann
	 */
	public static class PropertyFilterBuilder{
		private final Map<String, Object> filterProps = new HashMap<>();
		
		public PropertyFilterBuilder withProperty(String filterProperty, Object filterValue) {
			this.filterProps.put(filterProperty, filterValue);
			return this;
		}
		
		public PropertyFilter build() {
			return new PropertyFilter(filterProps);
		}
	}
}