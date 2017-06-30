package com.keep.safe.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;
import java.util.Set;

/**
 * Created by AND004 on 23-May-17.
 */

public class SharedPrefUtil {
    private static SharedPreferences sharedPreferences = null;

    private static SharedPreferences.Editor editor = null;

    /**
     * Initialize the SharedPreferences instance for the app.
     * This method must be called before using any other methods of this class.
     */

    public static void init(Context mcontext) {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mcontext);
            editor = sharedPreferences.edit();
        }
    }

    /**
     * Puts new Key and its Values into SharedPreference map.
     *
     * @param key
     * @param value
     */
    public static void putValue(String key, String value) {
        editor.putString(key, value);
    }

    /**
     * Puts new Key and its Values into SharedPreference map.
     *
     * @param key
     * @param value
     */
    public static void putValue(String key, int value) {
        editor.putInt(key, value);
    }

    /**
     * Puts new Key and its Values into SharedPreference map.
     *
     * @param key
     * @param value
     */
    public static void putValue(String key, long value) {
        editor.putLong(key, value);
    }

    /**
     * Puts new Key and its Values into SharedPreference map.
     *
     * @param key
     * @param value
     */
    public static void putValue(String key, boolean value) {
        editor.putBoolean(key, value);
    }

    /**
     * saves the values from the editor to the SharedPreference
     */
    public static void save() {
        editor.commit();
    }

    /**
     * returns a values associated with a Key default value ""
     *
     * @return String
     */
    public static String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    /**
     * returns a values associated with a Key default value -1
     *
     * @return String
     */
    public static int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    /**
     * returns a values associated with a Key default value -1
     *
     * @return String
     */
    public static long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    /**
     * returns a values associated with a Key default value false
     *
     * @return String
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     * Checks if key is exist in SharedPreference
     *
     * @param key
     * @return boolean
     */
    public static boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * returns map of all the key value pair available in SharedPreference
     *
     * @return Map<String, ?>
     */
    public static Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    public static void setList(String key, Set<String> list) {
        editor.putStringSet(key, list);
    }

    public static Set<String> getList(String key, Set<String> def) {
        return sharedPreferences.getStringSet(key, def);
    }


}
