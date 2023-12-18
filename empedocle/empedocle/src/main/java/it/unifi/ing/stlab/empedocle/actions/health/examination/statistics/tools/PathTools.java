package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.tools;


public class PathTools {
	
	public static String buildPath(String pathCandidate) {
		if (pathCandidate != null && !"".equals(pathCandidate)) {
			if (pathCandidate.endsWith("/")) {
				return normalize( pathCandidate );
			} else {
				return normalize( pathCandidate ) + "/";
			}
		} else {
			return "";
		}
	}
	
	public static String removeTrailingSlash(String s) {
		if(s.endsWith("/")) {
			return s.substring(0, s.length() - 1);
		}
		else {
			return s;
		}
	}
	
	public static String normalize(String s) {
		if(s != null) {
			return s.trim().replaceAll("\\s", "-");
		}
		else {
			return "";
		}
	}
	
	public static String appendSuffix(String original, String suffix) {
		return original + "-" + suffix;
	}

}
