package com.aj.android.commons.io;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.aj.android.commons.java.StringUtils;

public class StreamUtils {

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	 /**
     * Convert {@link ByteBuffer} to byte array.
     *  
     * @param byteBuffer -{@link ByteBuffer}.
     * @return byte[] -byte array.
     */
    public static byte[] getByteArray(ByteBuffer byteBuffer){
    	if(StringUtils.isNull(byteBuffer)){
    		throw new NullPointerException("byteBuffer cannot be null");    		
    	}
		byte[] bytes = new byte[byteBuffer.capacity()];
		byteBuffer.get(bytes);
		return bytes;
	}
    
    /**
     * Convert byte array to {@link ByteBuffer}
     * @param bytes -byte array.
     * @return byteBuffer -{@link ByteBuffer}.
     */
    public static ByteBuffer getByteBuffer(byte[] bytes){
    	if(StringUtils.isNull(bytes)){
    		throw new NullPointerException("bytes cannot be null");    		
    	}
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return byteBuffer;
	}
    
    /**	 
	 * Get byte array of content present in the given file path. 
	 * 
	 * <br><br>
	 * The file content should be less than {@link Integer#MAX_VALUE},<br>
	 * Array indexing cannot be more than maximum value of int / {@link Integer}.
	 *  
	 * @param filePath -{@link String} Physical file system absolute path for file.
	 * @return byte[] -contents of array in byte form.
	 * @throws IOException
	 */
	  public static byte[] getBytesFromFile(String filePath) throws IOException {
		  if(StringUtils.isNull(filePath)){
			  throw new IOException("filePath cannot be null");
		  }
		  if(StringUtils.isEmpty(filePath)){
			  throw new IllegalArgumentException("filePath cannot be empty");
		  }
		  File file=new File(filePath);
	        
		  	// Get the size of the file
	        long length = file.length();

	        // Cannot create an array using a long type.
	        // It needs to be an int type.
	        // Before converting to an int type, check
	        // to ensure that file is not larger than Integer.MAX_VALUE.
	        if (length > Integer.MAX_VALUE) {
	            // File is too large
	            throw new IOException("File is too large!");
	        }

	        // Create the byte array to hold the data
	        byte[] bytes = new byte[(int)length];

	        // Read in the bytes
	        int offset = 0;
	        int numRead = 0;

	        InputStream is = new FileInputStream(file);
	        try {
	            while (offset < bytes.length
	                   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	                offset += numRead;
	            }
	        } finally {
	           closeQuietly(is);
	        }

	        // Ensure all the bytes have been read in
	        if (offset < bytes.length) {
	            throw new IOException("Could not completely read file "+file.getName());
	        }
	        return bytes;
	    }
    
	/**
	 * Converts given {@link InputStream} to {@link String}, using {@link BufferedReader} with buffer size of 4KB.
	 * 
	 * @param inputStream -{@link InputStream} which needs to be converted.
	 * @return {@link String} -converted from given InputStream.
	 * @throws IOException
	 */
	public static String convertStreamToString(InputStream inputStream) throws IOException {
		if(StringUtils.isNull(inputStream)){
			throw new NullPointerException("inputStream cannot be null");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream),DEFAULT_BUFFER_SIZE);
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			throw e;
		} finally {
			closeQuietly(inputStream);
		}
		return sb.toString();
	}
	
	/**
	 * Converts given {@link InputStream} to {@link List}<{@link String}>, where each line is new List item. 
	 * 
	 * @param inputStream -{@link InputStream} which needs to be converted.
	 * @return {@link List}<{@link String}> -Stream in list format.
	 * @throws IOException
	 */
	public static List<String> convertStreamToList(InputStream inputStream) throws IOException {
		if(StringUtils.isNull(inputStream)){
			throw new NullPointerException("inputStream cannot be null");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream),DEFAULT_BUFFER_SIZE);
		List<String> content = new ArrayList<String>();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				content.add(line);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			closeQuietly(inputStream);
		}
		return content;
	}
	
	public static List<String> convertStreamToList(InputStream inputStream,String charsetName) throws IOException {
		if(StringUtils.isNull(inputStream)){
			throw new NullPointerException("inputStream cannot be null");
		}
		if(StringUtils.isNull(charsetName)){
			throw new NullPointerException("charsetName cannot be null");
		}
		if(StringUtils.isEmpty(charsetName)){
			throw new NullPointerException("charsetName cannot be empty");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,charsetName),DEFAULT_BUFFER_SIZE);
		List<String> content = new ArrayList<String>();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				content.add(line);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			closeQuietly(inputStream);
		}
		return content;
	}

	
	
	 
	
	/**
	 * Returns a byte array copy of {@link InputStream}.
	 * 
	 * @param inputStream -{@link InputStream} which needs to be converted.
	 * @return byte[] -contents of array in byte form.
	 * @throws IOException
	 */
	public static byte[] toByteArray(InputStream inputStream) throws IOException {
		if(StringUtils.isNull(inputStream)){
    		throw new NullPointerException("inputStream cannot be null");    		
    	}
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(inputStream, output);
		return output.toByteArray();
	}
	
    /**
	   * Write byte contents to given file path.
	   * 
	   * @param filePath -{@link String}  Physical file system absolute path for file.
	   * @param contents -in byte array form.
	   * @throws IOException
	   */
	public static void writeBytesToFile(String filePath,byte[] contents) throws IOException{
		if(StringUtils.isNull(filePath)){
			throw new IOException("filePath cannot be null");
		}
		if(StringUtils.isEmpty(filePath)){
			throw new IllegalArgumentException("filePath cannot be empty");
		}
		if(StringUtils.isNull(contents)){
  		throw new NullPointerException("contents cannot be null");    		
  	}
		File file=new File(filePath);
		FileOutputStream stream=null;		
		try{
			if(!file.exists()){
				file.createNewFile();
			}
			stream = new FileOutputStream(file);
			stream.write(contents);
		}catch (IOException e){
			throw e;
		} 
		catch(Exception e){
			throw e;
		}
		finally{
			closeQuietly(stream);
		}
	}
    
    /**
     * Copy from one stream to another stream.<br><br>
     * 
     * 1. Internally calls {@link #copyLarge(InputStream, OutputStream, byte[])}.<br>
     * 2. Buffer size used when reading is 4KB.
     * 
     * @param inputStream -Source {@link InputStream}.
     * @param outputStream -Destination {@link OutputStream}.
     * @return long -no of bytes copy from source to destination stream.
     * @throws IOException
     */
    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copyLarge(inputStream, outputStream,new byte[DEFAULT_BUFFER_SIZE]);        
    }
    
    /**
     * Copy from one stream to another stream.<br><br>
     * 
     * 1. Internally calls {@link #copyLarge(InputStream, OutputStream, byte[])}.<br>
     * 2. Buffer size used when reading is 8KB.
     * 
     * @param inputStream -Source {@link InputStream}.
     * @param outputStream -Destination {@link OutputStream}.
     * @return long -no of bytes copy from source to destination stream.
     * @throws IOException
     */
    public static long copyLarge(InputStream inputStream, OutputStream outputStream)throws IOException {
        return copyLarge(inputStream, outputStream, new byte[DEFAULT_BUFFER_SIZE*2]);
    }
    
    
    /**
     * Copy from one stream to another stream based on provided byte array.<br><br>
     * 
     * @param inputStream -Source {@link InputStream}.
     * @param outputStream -Destination {@link OutputStream}.
     * @param bytes - byte array buffer size, specifying how much byte to read each time.
     * @return long -no of bytes copy from source to destination stream.
     * @throws IOException
     */
    public static long copyLarge(InputStream inputStream, OutputStream outputStream, byte[] bytes)throws IOException {
    	if(StringUtils.isNull(inputStream)){
    		throw new NullPointerException("inputStream cannot be null");    		
    	}
    	if(StringUtils.isNull(outputStream)){
    		throw new NullPointerException("outputStream cannot be null");    		
    	}
    	if(StringUtils.isNull(bytes)){
    		throw new NullPointerException("bytes cannot be null");    		
    	}
        long count = 0;
        int length = 0;        
        while ((length = inputStream.read(bytes)) > 0) {
        	outputStream.write(bytes, 0, length);
        	count += length;
        }
        return count;
    }
    
    /**
	 * Close the {@link Closeable} object, does not throw back exception if any exception is raised. 
	 * 
	 * @param closeable -An instance object which implements {@link Closeable} interface.
	 */
    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
            	closeable.close();
            }
        } catch (IOException ignored) {
            // ignore
        }
    }
}
