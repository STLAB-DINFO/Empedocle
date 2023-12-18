package it.unifi.ing.stlab.empedocle.actions.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UuidGenerator {
	
	static List<String> uuids = new ArrayList<String>();

	public static void main(String[] args) {
		
		for (int i = 0; i< 10; i++ )
			uuids.add( UUID.randomUUID().toString() );
		
		System.out.println( uuids );
	}

}
