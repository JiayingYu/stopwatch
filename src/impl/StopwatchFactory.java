package impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 * 
 */
public class StopwatchFactory {

  private static Map<String, IStopwatch> stopwatchs = Collections
      .synchronizedMap(new HashMap<String, IStopwatch>());

  /**
   * Creates and returns a new IStopwatch object
   * 
   * @param id
   *          The identifier of the new object
   * @return The new IStopwatch object
   * @throws IllegalArgumentException
   *           if <code>id</code> is empty, null, or already taken.
   */
  public static synchronized IStopwatch getStopwatch(String id)
      throws IllegalArgumentException {
    if (id.isEmpty() || id == null) {
      throw new IllegalArgumentException("Error: id is empty or null!");
    }
    
		if (stopwatchs.containsKey(id)) {
			throw new IllegalArgumentException("Error: id is already exist!");
		} else {
			IStopwatch sw = StopWatchImpl.newStopWatch(id);
			stopwatchs.put(id, sw);
			return sw;
		}
    
  }

  /**
   * Returns a list of all created stopwatches
   * 
   * @return a List of all creates IStopwatch objects. Returns an empty list if
   *         no IStopwatches have been created.
   */
  public static List<IStopwatch> getStopwatches() {
    return Collections.unmodifiableList(
    		new ArrayList<IStopwatch>(stopwatchs.values()));
  }
}