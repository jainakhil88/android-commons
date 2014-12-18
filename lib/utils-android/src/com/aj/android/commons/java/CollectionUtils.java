package com.aj.android.commons.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Collection of  commonly used {@link Collection} related utilities.
 * @author ajain
 *
 */
public class CollectionUtils {

		
	/**
     * Get size of list, Returns 0 if the provided {@link List} is <code>null</code> or empty, else the count
     * 
     * <pre>
     * getSize(null)   =   0;
     * getSize({})     =   0;
     * getSize({1})    =   1;
     * </pre>
     * 
     * @param <V> 
     * @param list - of {@link List} type.
     * @return int -if list is null or empty, return 0, else return {@link List#size()}.
     */
    public static <V> int getSize(List<V> list) {
        return list == null ? 0 : list.size();
    }

    
    
    
    /**
     * Returns true if list is null or its size is 0
     * 
     * <pre>
     * isEmpty(null)   =   true;
     * isEmpty({})     =   true;
     * isEmpty({1})    =   false;
     * </pre>
     * 
     * @param <V>
     * @param list - of {@link List} type.
     * @return boolean - true if list is null or empty, else false.
     */
    public static <V> boolean isEmpty(List<V> list) {
        return (list == null || list.size() == 0);
    }
	
	/**
	 * Copies the iterator object into the List(ArrayList) of the specific generic type 
	 * @param iter
	 * @return
	 */
	public static <T> List<T> copyIterator(Iterator<T> iter) 
	{
		List<T> copy = new ArrayList<T>();
		while (iter.hasNext())
			copy.add(iter.next());
		return copy;
	}
	  /**
     * Invert / Reverse the provided {@link List}.
     * <br>
     * Returns a new inverted copy for the list.
     * 
     * @param <V>
     * @param list - of {@link List} type.
     * @return {@link List} - an new inverted copy of the provided list. 
     */
    public static <V> List<V> invertList(List<V> list) {
        if (isEmpty(list)) {
            return list;
        }

        List<V> invertList = new ArrayList<V>(list.size());
        for (int i = list.size() - 1; i >= 0; i--) {
            invertList.add(list.get(i));
        }
        return invertList;
    }
	
    /**
	 * Get a random position(object) from an array of generic objects. <br/>
	 * Using generics saves the trouble of casting the return object.
	 * 
	 * @param <T> -the type of the array to get the object from.
	 * @param array -the array with objects.
	 * @return random object from given array or null of array is either null or empty.
	 */
	public static <T> T getRandomPosition(T[] array) {
		if (array == null || array.length == 0) {
			return null;
		}
		return array[MathUtils.getRandomInteger(array.length - 1)];
	}

	/**
	 * Get a random position(object) from a list of generic objects. <br/>
	 * Using generics saves the trouble of casting the return object.
	 * 
	 * @param <T> -the type of the list objects to get the object from.
	 * @param {{@link {@link List}} -the list with objects.
	 * @return random object from given list or null of list is either null or empty.
	 */
	public static <T> T getRandomPosition(List<T> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(MathUtils.getRandomInteger(list.size() - 1));
	}
	
}
