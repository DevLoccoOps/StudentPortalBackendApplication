package www.experianassessment.co.za.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorHandlingConfigFileHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlingConfigFileHelper.class);

	public String getStandardizedErrorMessage(String errorType) {
		Properties prop = getStandardizedErrorProperties();
		return prop.getProperty(errorType);
	}

	public String getStandardizedWarningMessage(String warningType) {
		Properties prop = getStandardizedWarningProperties();
		return prop.getProperty(warningType);
	}

	private Properties getStandardizedErrorProperties() {
		Properties prop = new Properties();
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("alerts/error/errors.properties");
			prop.load(in);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return prop;
	}

	private Properties getStandardizedWarningProperties() {
		Properties prop = new Properties();
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("alerts/warning/warning.properties");
			prop.load(in);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return prop;
	}
}
