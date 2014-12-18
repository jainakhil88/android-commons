package com.aj.android.commons.net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aj.android.commons.java.CollectionUtils;
import com.aj.android.commons.java.StringUtils;

public class JSONUtils {
	
	/**
     * Get {@link Long} from {@link JSONObject}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link Long} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getLong(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getLong(String)}</li>
     *         </ul>
	 * @throws JSONException 
     */
    public static Long getLong(JSONObject jsonObject, String key, Long defaultValue) throws JSONException {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }
        try {
        		return jsonObject.getLong(key);
        } catch (JSONException e) {
           throw e;
        }
    }

    /**
     * Get {@link Long} from {@link String} json data.
     * 
     * @see {@link #getLong(JSONObject, String, long)}.
     * 
     * @param jsonData -{@link String}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link Long} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if {@link JSONObject#getLong(String)} exception, return defaultValue</li>
     *          <li>return {@link JSONObject#getLong(String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static Long getLong(String jsonData, String key, Long defaultValue) throws JSONException {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getLong(jsonObject, key, defaultValue);
        } catch (JSONException e) {
        	   throw e;
        }
    }

    /**
     * Get <code>long</code> from {@link JSONObject}.
     * 
     * @see {@link #getLong(JSONObject, String, Long)}
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param key -{@link String} key.
     * @param defaultValue -long default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if {@link JSONObject#getLong(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getLong(JSONObject, String, JSONObject)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static long getLong(JSONObject jsonObject, String key, long defaultValue) throws JSONException {
        return getLong(jsonObject, key, (Long)defaultValue);
    }

    /**
     * Get <code>long</code> from {@link String} json data.
     * 
     * @see JSONUtils#getLong(String, String, Long)
     * 
     * @param jsonData -{@link String}.
     * @param key -{@link String} key.
     * @param defaultValue -long default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if {@link JSONObject#getLong(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getLong(JSONObject, String, JSONObject)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static long getLong(String jsonData, String key, long defaultValue) throws JSONException {
        return getLong(jsonData, key, (Long)defaultValue);
    }

    /**
     * Get {@link Integer} from {@link JSONObject}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link Integer} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getInt(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getInt(String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static Integer getInt(JSONObject jsonObject, String key, Integer defaultValue) throws JSONException {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
        	   throw e;
        }
    }

    /**
     * Get {@link Integer} from {@link String} json data.
     * 
     * @see {@link #getInt(JSONObject, String, Integer)}.
     * 
     * @param jsonData -{@link String}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link Integer} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getInt(JSONObject, String, JSONObject)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static Integer getInt(String jsonData, String key, Integer defaultValue) throws JSONException {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getInt(jsonObject, key, defaultValue);
        } catch (JSONException e) {
        	   throw e;
        }
    }

    /**
     * Get <code>int</code> from {@link JSONObject}.
     * 
     * @see {@link #getInt(JSONObject, String, Integer)}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param key -{@link String} key.
     * @param defaultValue -int default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getInt(JSONObject, String, JSONObject)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static int getInt(JSONObject jsonObject, String key, int defaultValue) throws JSONException {
        return getInt(jsonObject, key, (Integer)defaultValue);
    }

    /**
     * Get <code>int</code> from {@link String} json data.
     * 
     * @see {@link #getInt(String, String, Integer)}.
     * 
     * @param jsonData -{@link String}.
     * @param key -{@link String} key.
     * @param defaultValue -int default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getInt(JSONObject, String, JSONObject)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static int getInt(String jsonData, String key, int defaultValue) throws JSONException {
        return getInt(jsonData, key, (Integer)defaultValue);
    }

    /**
     * Get {@link Double} from {@link JSONObject}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link Double} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getDouble(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getDouble(String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static Double getDouble(JSONObject jsonObject, String key, Double defaultValue) throws JSONException {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getDouble(key);
        } catch (JSONException e) {
        	   throw e;
        }
    }

    /**
     * Get {@link Double} from {@link String} json data.
     * 
     * @see {@link #getDouble(JSONObject, String, Double)}.
     * 
     * @param jsonData -{@link String}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link Double} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getDouble(JSONObject, String, JSONObject)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static Double getDouble(String jsonData, String key, Double defaultValue) throws JSONException {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getDouble(jsonObject, key, defaultValue);
        } catch (JSONException e) {
        	   throw e;
        }
    }

    /**
     * Get <code>double</code> from {@link JSONObject}.
     * 
     * @see {@link #getDouble(JSONObject, String, Double)}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param key -{@link String} key.
     * @param defaultValue -double default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getDouble(JSONObject, String, JSONObject)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static double getDouble(JSONObject jsonObject, String key, double defaultValue) throws JSONException {
        return getDouble(jsonObject, key, (Double)defaultValue);
    }

    /**
     * Get <code>double</code> from {@link String} json data.
     * 
     * @see {@link #getDouble(String, String, Double)}.
     * 
     * @param jsonData -{@link String}.
     * @param key -{@link String} key.
     * @param defaultValue -double default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getDouble(JSONObject, String, JSONObject)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static double getDouble(String jsonData, String key, double defaultValue) throws JSONException {
        return getDouble(jsonData, key, (Double)defaultValue);
    }

    /**
     * Get {@link String} from {@link JSONObject}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link String} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getString(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getString(String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static String getString(JSONObject jsonObject, String key, String defaultValue) throws JSONException {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
        	   throw e;
        }
    }

    /**
     * Get {@link String} from {@link String} json data.
     * 
     * @see {@link #getString(JSONObject, String, String)}.
     * 
     * @param jsonData -{@link String}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link String} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getString(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getString(String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static String getString(String jsonData, String key, String defaultValue) throws JSONException {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getString(jsonObject, key, defaultValue);
        } catch (JSONException e) {
        	   throw e;
        }
    }
    
    /**
     * Get {@link List} of {@link String} from {@link JSONObject}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link List} of{@link String} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getJSONArray(String)} exception, return defaultValue</li>
     *         <li>if {@link JSONArray#getString(int)} exception, return defaultValue</li>
     *         <li>return string array</li>
     *         </ul>
     * @throws JSONException 
     */
    public static List<String> getStringList(JSONObject jsonObject, String key, List<String> defaultValue) throws JSONException {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            JSONArray statusArray = jsonObject.getJSONArray(key);
            if (statusArray != null) {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < statusArray.length(); i++) {
                    list.add(statusArray.getString(i));
                }
                return list;
            }
        } catch (JSONException e) {
        	throw e;
        }
        return defaultValue;
    }

    /**
     * Get {@link List} of {@link String} json data.
     * 
     * @see {@link #getStringList(JSONObject, String, List)}.
     * 
     * @param jsonData -{@link String}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link List} of{@link String} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getJSONArray(String)} exception, return defaultValue</li>
     *         <li>if {@link JSONArray#getString(int)} exception, return defaultValue</li>
     *         <li>return string array</li>
     *         </ul>
     * @throws JSONException 
     */
    public static List<String> getStringList(String jsonData, String key, List<String> defaultValue) throws JSONException {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getStringList(jsonObject, key, defaultValue);
        } catch (JSONException e) {
        	throw e;
        }
    }

    /**
     * Get {@link JSONObject} from {@link JSONObject}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link JSONObject} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getJSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getJSONObject(String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String key, JSONObject defaultValue) throws JSONException {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getJSONObject(key);
        } catch (JSONException e) {
        	throw e;
        }
    }

    /**
     * Get {@link JSONObject} from {@link String} json data.
     * 
     * @see {@link #getJSONObject(JSONObject, String, JSONObject)}.
     * 
     * @param jsonData -{@link String}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link JSONObject} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getJSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getJSONObject(String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static JSONObject getJSONObject(String jsonData, String key, JSONObject defaultValue) throws JSONException {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONObject(jsonObject, key, defaultValue);
        } catch (JSONException e) {
        	throw e;
        }
    }

    /**
     * Get {@link JSONObject} from {@link JSONObject} by traversing.
     * <br>
     * Traverse the given json object to find corresponding json object for the provided key.
     * 
     * @see  {@link #getJSONObject(JSONObject, String, JSONObject)}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param defaultValue -{@link JSONObject}.
     * @param keyArray -variable length {@link String} argument.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if keyArray is null or empty, return defaultValue</li>
     *         <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by recursion, return it. if anyone is
     *         null, return directly</li>
     *         </ul>
     * @throws JSONException 
     */
    public static JSONObject getJSONObjectCascade(JSONObject jsonObject, JSONObject defaultValue, String... keyArray) throws JSONException {
        if(keyArray==null){
        	return defaultValue;
        }
    	if (jsonObject == null || CollectionUtils.isEmpty(Arrays.asList(keyArray))) {
            return defaultValue;
        }

        JSONObject js = jsonObject;
        for (String key : keyArray) {
            js = getJSONObject(js, key, defaultValue);
            if (js == null) {
                return defaultValue;
            }
        }
        return js;
    }

    /**
     * Get {@link JSONObject} from {@link String} json data.
     * <br>
     * Traverse the given json object to find corresponding json object for the provided key.
     * 
     * @see {@link #getJSONObject(JSONObject, String, JSONObject)}.
     * @see {@link #getJSONObjectCascade(JSONObject, JSONObject, String...)}.
     * 
     * @param jsonData -{@link String}.
     * @param defaultValue -{@link JSONObject}.
     * @param keyArray -variable length {@link String} argument.
     * @return <ul>
     *         <li>if jsonData is null, return defaultValue</li>
     *         <li>if keyArray is null or empty, return defaultValue</li>
     *         <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by recursion, return it. if anyone is
     *         null, return directly</li>
     *         </ul>
     * @throws JSONException 
     */
    public static JSONObject getJSONObjectCascade(String jsonData, JSONObject defaultValue, String... keyArray) throws JSONException {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONObjectCascade(jsonObject, defaultValue, keyArray);
        } catch (JSONException e) {
        	throw e;
        }
    }

    /**
     * Get {@link JSONArray} from {@link JSONObject}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link defaultValue} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getJSONArray(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getJSONArray(String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String key, JSONArray defaultValue) throws JSONException {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getJSONArray(key);
        } catch (JSONException e) {
        	throw e;
        }
    }

    /**
     * Get {@link JSONArray} from {@link String} json data.
     * 
     * @see {@link #getJSONArray(JSONObject, String, JSONArray)}.
     * 
     * @param jsonData -{@link String}.
     * @param key -{@link String} key.
     * @param defaultValue -{@link defaultValue} default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getJSONArray(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getJSONArray(String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static JSONArray getJSONArray(String jsonData, String key, JSONArray defaultValue) throws JSONException {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONArray(jsonObject, key, defaultValue);
        } catch (JSONException e) {
        	throw e;
        }
    }

    /**
     * Get <code>boolean</code> from {@link JSONObject}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param key -{@link String} key.
     * @param defaultValue -boolean default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>return {@link JSONObject#getBoolean(String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static boolean getBoolean(JSONObject jsonObject, String key, Boolean defaultValue) throws JSONException {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
        	throw e;
        }
    }

    /**
     * Get <code>boolean</code> from {@link String} json data.
     * 
     * @see {@link #getBoolean(JSONObject, String, Boolean)}.
     * 
     * @param jsonData -{@link String}.
     * @param key -{@link String} key.
     * @param defaultValue -boolean default value if json object is null.
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>return {@link JSONObject#getBoolean(String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static boolean getBoolean(String jsonData, String key, Boolean defaultValue) throws JSONException {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getBoolean(jsonObject, key, defaultValue);
        } catch (JSONException e) {
        	throw e;
        }
    }

    /**
     * Get {@link HashMap <String, String>} from {@link JSONObject}.
     * 
     * @see {@link #parseKeyAndValueToMap(String)}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @param key -{@link String} key.
     * @return <ul>
     *         <li>if jsonObject is null, return null</li>
     *         <li>return {@link JSONUtils#parseKeyAndValueToMap(String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static Map<String, String> getMap(JSONObject jsonObject, String key) throws JSONException {
        return JSONUtils.parseKeyAndValueToMap(JSONUtils.getString(jsonObject, key, null));
    }

    /**
     * Get {@link HashMap <String, String>} from {@link String} json data.
     * 
     * @see {@link #getMap(JSONObject, String)}.
     * 
     * @param jsonData -{@link String}.
     * @param key -{@link String} key.
     * @return <ul>
     *         <li>if jsonData is null, return null</li>
     *         <li>if jsonData length is 0, return empty map</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return null</li>
     *         <li>return {@link JSONUtils#getMap(JSONObject, String)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static Map<String, String> getMap(String jsonData, String key) throws JSONException {

        if (jsonData == null) {
            return null;
        }
        if (jsonData.length() == 0) {
            return new HashMap<String, String>();
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getMap(jsonObject, key);
        } catch (JSONException e) {
        	throw e;
        }
    }

    /**
     * Get {@link HashMap <String, String>} from {@link JSONObject}.
     * 
     * @param jsonObject -{@link JSONObject}.
     * @return <ul>
     *         <li>if sourceObj is null, return null</li>
     *         <li>else parse entry by {@link MapUtils#putMapNotEmptyKey(Map, String, String)} one by one</li>
     *         </ul>
     * @throws JSONException 
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, String> parseKeyAndValueToMap(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return null;
        }

        Map<String, String> keyAndValueMap = new HashMap<String, String>();
        for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
            String key = (String)iter.next();
            if(!StringUtils.isEmpty(key)){
            	keyAndValueMap.put(key, getString(jsonObject, key, ""));
            }
        }
        return keyAndValueMap;
    }

    /**
     * Get {@link HashMap <String, String>} from {@link String} json data.
     * 
     * @see {@link #parseKeyAndValueToMap(JSONObject)}.
     * 
     * @param jsonData -{@link String}.
     * @return <ul>
     *         <li>if source is null or source's length is 0, return empty map</li>
     *         <li>if source {@link JSONObject#JSONObject(String)} exception, return null</li>
     *         <li>return {@link JSONUtils#parseKeyAndValueToMap(JSONObject)}</li>
     *         </ul>
     * @throws JSONException 
     */
    public static Map<String, String> parseKeyAndValueToMap(String jsonData) throws JSONException {
        if (StringUtils.isEmpty(jsonData)) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return parseKeyAndValueToMap(jsonObject);
        } catch (JSONException e) {
        	throw e;
        }
    }
}
