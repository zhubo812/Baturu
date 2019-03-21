package org.bhu.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

public class FileReader {

	private String path;
	private String encoding;
	private boolean found = false;
	private BufferedReader reader;
	private String line = null;
	private File file;
//	private static final String UTF8 = "utf-8";
	
	/**
	 * 
	 * @param path
	 *            
	 */
	public FileReader(String path) {
		init(path);
		file = new File(this.path);
		getReader();
	}
	
	public FileReader(String path, String encoding) {
		init(path);
		file = new File(this.path);
		this.encoding = encoding;
		getReader();
	}
	
	public FileReader(File file) {
		init(file.getAbsolutePath());
		this.file = file;
		getReader();
	}
	
	private void init(String path){
		this.path = path;
		try {
//			this.encoding = getCode(path);
			this.encoding = codeString(path);
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param path
	 *            鏂囨湰鐨勭粷瀵硅矾寰�
	 * @param encoding
	 *            鏂囨湰鐨勫瓧绗︾紪鐮�
	 */


	public FileReader(InputStream inputStream) {
		getReader(inputStream);
	}
	

	/**
	 * 鎸夎璇诲彇鏂囨湰
	 * 
	 * @return 杩斿洖涓�琛�
	 */
	public String readLine() {
		try {
			line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * FileReader璇诲彇涓�涓枃鏈殑鎵�鏈夊唴瀹�
	 * 
	 * @return 杩斿洖涓�琛�
	 */
	public String read2End() {
		Long filelength = this.file.length();
		byte[] fileContent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(fileContent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(fileContent, this.encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * FileReader璇诲彇宸插垎濂借瘝涓旀棤鍒嗚瘝鏍囪鐨勬枃鏈�
	 * 
	 * @return 杩斿洖鍖呭惈鏂囨湰鎵�鏈夎瘝鐨凩ist锛屽彲浠ユ湁閲嶅鍊�
	 */
	public List<String> readWordSet() {
		List<String> wordset = new ArrayList<>();
		try {
			for (; (line = reader.readLine()) != null;) {
				line = line.concat(line);
				String[] array = line.split(" ");
				for (String word : array) {
					wordset.add(word);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wordset;
	}

	/***
	 * 
	 * 鏂囨湰鐨勭粷瀵硅矾寰�
	 * @return 杩斿洖鏂囨湰鎵�鏈夎鐨勯泦鍚圠ist
	 */
	public List<String> read2List() {
		List<String> list = new ArrayList<>();

				try {
					for (String line; (line = reader.readLine()) != null;) {
						if (line.trim().length() == 0)
							continue;
						list.add(line.trim());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		return list;
	}
	
	@SuppressWarnings("unused")
	private BufferedReader getReader(String path, String encoding) {
		try {
			this.reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.file), encoding));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reader;
	}

	private void getReader() {
		try {
			this.reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.file), this.encoding));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	private void getReader(InputStream in) {
		try {
			this.reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getCode(String path) throws IOException{
        
        InputStream in = new FileInputStream(new File(path));
        encoding = "";
        String[] prob;
        int lang = nsDetector.ALL;
        nsDetector det = new nsDetector(lang) ;
        det.Init(new nsICharsetDetectionObserver()
        {
            public void Notify(String charset)
            {
                found = true;
                encoding = charset;
            }
        });
        BufferedInputStream imp = new BufferedInputStream(in);
        byte[] buf = new byte[1024];
        int len;
        boolean isAscii = true;
        try {
			while ((len = imp.read(buf, 0, buf.length)) != -1)
			{
			    // Check if the stream is only ascii.
			    if (isAscii)
			        isAscii = det.isAscii(buf, len);
			    // DoIt if non-ascii and not done yet.
			    if (!isAscii)
			    {
			        if (det.DoIt(buf, len, false))
			            break;
			    }
			}
			 imp.close();
			 in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       

        det.DataEnd();
        if (isAscii)
        {
            found = true;
            prob = new String[]
                    {
                        "ASCII"
                    };
        } else if (found)
        {
            prob = new String[]
                    {
                    	encoding
                    };
        } else
        {
            prob = det.getProbableCharsets();
        }
        encoding =  prob[0];
         
        return encoding;
    }

	
	/**
	 * 杩斿洖鏂囨。鍚�
	 * @return
	 */
	public String getFileName(){
		return this.file.getName();
	}
	
	/**
	 * 杩斿洖鏂囨。鐨勭粷瀵硅矾寰�
	 * @return
	 */
	public String getAbsolutePath(){
		return this.file.getAbsolutePath();
	}
	
	public String getPath() {
		return this.file.getPath();
	}
	
	public static String codeString(String fileName) throws Exception{  
	    BufferedInputStream bin = new BufferedInputStream(  
	    new FileInputStream(fileName));  
	    int p = (bin.read() << 8) + bin.read();  
	    String code = null;  
	      
	    switch (p) {  
	        case 0xefbb:  
	            code = "UTF-8";  
	            break;  
	        case 0xfffe:  
	            code = "Unicode";  
	            break;  
	        case 0xfeff:  
	            code = "UTF-16BE";  
	            break;  
	        default:  
	            code = "GBK";  
	    }  
	      
	    return code;  
	}  
	
	public void close() {
		try {
			this.reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
