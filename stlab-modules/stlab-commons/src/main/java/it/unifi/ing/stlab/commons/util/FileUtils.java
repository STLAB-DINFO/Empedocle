package it.unifi.ing.stlab.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
	
	public static void createFileAndDirectories(String filePath) throws IOException {

		File file = new File(filePath);

		File parent = file.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
			throw new IllegalStateException("Couldn't create dir: " + parent);
		}

		if (!file.exists()) {
			file.createNewFile();
		}
	}
	
	public static String readFile( File file ) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader( file ));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}		
	
}
