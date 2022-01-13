package org.bhu.io;

import org.junit.Test;

public class FileReaderTest {

	
	@Test
	public void demo3() {
		String path =  "E:\\data\\lex\\hsj\\韩\\文学部分\\#诗词常用术语.txt";
		FileReader reader = new FileReader(path);
		
		System.out.println(reader.getEncoding());
	}
}
