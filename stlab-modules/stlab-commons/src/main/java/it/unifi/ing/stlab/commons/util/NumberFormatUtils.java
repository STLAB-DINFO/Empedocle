package it.unifi.ing.stlab.commons.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NumberFormatUtils {
	
	private final DecimalFormat df;
	
	public NumberFormatUtils() {
		DecimalFormatSymbols sym = new DecimalFormatSymbols();
		sym.setDecimalSeparator('.');
		df = new DecimalFormat("#.#");
		df.setRoundingMode(RoundingMode.DOWN);
		df.setDecimalFormatSymbols(sym);
	}
	
	public Double formatDouble(String value, Integer nDecimals) {
		df.setMaximumFractionDigits(nDecimals);
		return new Double(df.format(Double.parseDouble(value)));
	}
	
	public String formatDecimals(Number n, Integer minDecimals, Integer maxDecimals) {
		df.setMaximumFractionDigits(maxDecimals);
		df.setMinimumFractionDigits(minDecimals);
		return df.format(n);
	}
	
	public String formatDecimals(String s, Integer minDecimals, Integer maxDecimals) {
		if(isNumeric(s)) {
			df.setMaximumFractionDigits(maxDecimals);
			df.setMinimumFractionDigits(minDecimals);
			return df.format(Double.parseDouble(s));
		}
		else {
			return null;
		}
	}
	
	public Boolean isNumeric(String s) {
		if(s != null) {
			return s.matches("[\\+-]?\\d+(\\.\\d+)?");
		}
		else {
			return false;
		}
	}

}
