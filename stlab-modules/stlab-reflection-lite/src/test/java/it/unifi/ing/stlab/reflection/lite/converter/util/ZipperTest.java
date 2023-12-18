package it.unifi.ing.stlab.reflection.lite.converter.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ZipperTest {

	@Test
	public void testZipper() {
		String txt = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vel tellus ornare, fermentum nisl eget, sagittis mi. " +
						" Suspendisse molestie sit amet sem et volutpat. Vestibulum in tincidunt massa, a adipiscing eros. " +
						" Etiam in nisl laoreet velit pharetra ultricies eleifend vitae dolor";
		
		byte[] compressed = Zipper.compress( txt.getBytes() );
		byte[] decompressed = Zipper.decompress( compressed );
		
		assertEquals( txt.getBytes().length, decompressed.length );
		assertEquals( txt, new String( decompressed ) );
		
	}
	
	
}
