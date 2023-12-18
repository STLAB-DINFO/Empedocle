package it.unifi.ing.stlab.reflection.lite.converter.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

//FIXME gestione IOException
public class Zipper {

	public static byte[] compress(byte[] decompressed) {
		ByteArrayOutputStream out = null;
		DeflaterOutputStream dos = null;
		try {
			out = new ByteArrayOutputStream();
			Deflater d = new Deflater();
			dos = new DeflaterOutputStream( out, d );
	        dos.write( decompressed );
	        dos.close();
	        out.close();
	        
	        return out.toByteArray();
		} catch( IOException e) {
			throw new RuntimeException( e );
		}
	}
	
	public static byte[] decompress(byte[] compressed) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream( compressed );
			InflaterInputStream inflater = new InflaterInputStream( in );
			ByteArrayOutputStream out =new ByteArrayOutputStream();
			int b;
		    while ( ( b = inflater.read() ) != -1 ) {
		    	out.write( b );
		    }
		    inflater.close();
		    out.close();
		    in.close();
		    
		    return out.toByteArray();
		} catch( IOException e) {
			throw new RuntimeException( e );
		}
	}
	
	
}
