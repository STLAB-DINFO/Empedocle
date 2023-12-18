package it.unifi.ing.stlab.commons.cdi.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyLoader {

	private static final Logger LOGGER = Logger.getLogger(PropertyLoader.class
			.getName());

	public PropertyLoader() {
	}

	public Properties load(String file) {
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(file);
		Properties properties = new Properties();

		try {
			properties.load(is);
			return properties;

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Apertura file di proprietà " + file
					+ "non riuscita");
			return null;
		} finally {
			try {
				is.close();
			} catch (Exception e) {
			}
		}
	}

}
