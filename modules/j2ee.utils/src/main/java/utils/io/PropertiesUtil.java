// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package utils.io;

import utils.validate.Validators;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Misc java.util.Properties utils.
 */
public class PropertiesUtil {

	// ---------------------------------------------------------------- to/from files

	/**
	 * Create properties from the file.
	 *
	 * @param fileName properties file name to load
	 */
	public static Properties createFromFile(String fileName) throws IOException {
		return createFromFile(new File(fileName));
	}

	/**
	 * Create properties from the file.
	 *
	 * @param file properties file to load
	 */
	public static Properties createFromFile(File file) throws IOException {
		Properties prop = new Properties();
		loadFromFile(prop, file);
		return prop;
	}

	/**
	 * Loads properties from the file. Properties are appended to the existing
	 * properties object.
	 *
	 * @param p        properties to fill in
	 * @param fileName properties file name to load
	 */
	public static void loadFromFile(Properties p, String fileName) throws IOException {
		loadFromFile(p, new File(fileName));
	}

	/**
	 * Loads properties from the file. Properties are appended to the existing
	 * properties object.
	 *
	 * @param p      properties to fill in
	 * @param file   file to read properties from
	 */
	public static void loadFromFile(Properties p, File file) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			p.load(fis);
		} finally {
			Streams.close(fis);
		}
	}


	/**
	 * Writes properties to a file.
	 *
	 * @param p        properties to write to file
	 * @param fileName destination file name
	 */
	public static void writeToFile(Properties p, String fileName) throws IOException {
		writeToFile(p, new File(fileName), null);
	}

	/**
	 * Writes properties to a file.
	 *
	 * @param p        properties to write to file
	 * @param fileName destination file name
	 * @param header   optional header
	 */
	public static void writeToFile(Properties p, String fileName, String header) throws IOException {
		writeToFile(p, new File(fileName), header);
	}

	/**
	 * Writes properties to a file.
	 *
	 * @param p      properties to write to file
	 * @param file   destination file
	 */
	public static void writeToFile(Properties p, File file) throws IOException {
		writeToFile(p, file, null);
	}

	/**
	 * Writes properties to a file.
	 *
	 * @param p      properties to write to file
	 * @param file   destination file
	 * @param header optional header
	 */
	public static void writeToFile(Properties p, File file, String header) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			p.store(fos, header);
		} finally {
			Streams.close(fos);
		}
	}



	// ---------------------------------------------------------------- subsets

	/**
	 * Creates new Properties object from the original one, by copying
	 * those properties that have specified first part of the key name.
	 * Prefix may be optionally stripped during this process.
	 *
	 * @param p         source properties, from which new object will be created
	 * @param prefix    key names prefix 
	 *
	 * @return subset properties
	 */
	public static Properties subset(Properties p, String prefix, boolean stripPrefix) {
		if (Validators.isNullOrEmpty(prefix)) {
			return p;
		}
		if (prefix.endsWith(".") == false) {
			prefix += '.';
		}
		Properties result = new Properties();
		int baseLen = prefix.length();
		for (Object o : p.keySet()) {
			String key = (String) o;
			if (key.startsWith(prefix) == true) {
				result.setProperty(stripPrefix == true ? key.substring(baseLen) : key, p.getProperty(key));
			}
		}
		return result;
	}




	// ---------------------------------------------------------------- variables

	/**
	 * Returns String property from a map. If key is not found, or if value is not a String, returns <code>null</code>.
	 * Mimics <code>Property.getProperty</code> but on map.
	 */
	public static String getProperty(Map map, String key) {
		return getProperty(map, key, null);
	}

	/**
	 * Returns String property from a map.
	 * @see #getProperty(java.util.Map, String) 
	 */
	public static String getProperty(Map map, String key, String defaultValue) {
		Object val = map.get(key);
		return (val instanceof String) ? (String) val : defaultValue;
	}



}