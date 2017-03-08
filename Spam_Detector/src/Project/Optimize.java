package Project;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by miral on 05/03/17.
 * Class adds a special feature to the main functionality of the program.
 * This feature removes words in a given map that appear less than a number of times
 */
public class Optimize {

     /**
     * Removes all keys and values from a TreeMap which occur less then a certain amount of times
     * @param map The TreeMap containing keys of type string and values of type Integer
     *            that you want to remove the values from
     * @param lessThen keys with values bellow this number will be removed.
     * @return the updated map
     */
    public Map<String,Integer> optFreqMap(Map<String,Integer> map, int lessThen){
        Set<String> keys = map.keySet();
        Iterator<String> keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if(map.get(key) < lessThen) {
                keyIterator.remove();
            }
        }

        return map;
    }
}
