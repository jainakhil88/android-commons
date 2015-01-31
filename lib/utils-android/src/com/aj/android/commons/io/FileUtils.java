package com.aj.android.commons.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.aj.android.commons.java.CollectionUtils;
import com.aj.android.commons.java.StringUtils;

/**
 * Collection of {@link File} related utilities for reading, writing files. <br>
 * and other directory related operations.
 * 
 * @author Akhil Jain
 *
 */
public class FileUtils {

	public final static String FILE_EXTENSION_SEPARATOR = ".";

	/**
	 * {@link String} directory path where the number of files are to be counted. 
	 * @param directoryPath -{@link String} directory path.
	 * @param countDirectory -boolean, if set to true will count directory also, else not.
	 * @return int -number of files  and directories(if included) found in given directory path.  
	 */
	public static int getFileCount(String directoryPath,boolean countDirectory){
		if(StringUtils.isNull(directoryPath)){
			throw new NullPointerException("directoryPath cannot be null");
		}
		File f = new File(directoryPath);
		File[] files  = f.listFiles();
		int count=0;
		if(files != null){
			for(int i=0; i < files.length; i++){
				File file = files[i];
				if(file.isDirectory() && countDirectory){
						count ++;
				}
				else if(file.isFile()){
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Counts the number of file present in directory and sub-directory in the given {@link String} directory path. 
	 * @param directoryPath -{@link String} directory path.
	 * @param countDirectory -boolean, if set to true will count directory also, else not.
	 * @return int -number of files and directories(if included) found in given directory and sub-directory.  
	 */
	public static int getRecursiveFileCount(String directoryPath,boolean countDirectory) {
		if(StringUtils.isNull(directoryPath)){
			throw new NullPointerException("directoryPath cannot be null");
		}
		File f = new File(directoryPath);
		File[] files  = f.listFiles();
		int count=0;
		if(files != null){
			for(int i=0; i < files.length; i++){
				File file = files[i];
				if(file.isDirectory()){
					if(countDirectory){
						count ++;
					}
					count+=getRecursiveFileCount(file.getAbsolutePath(),countDirectory); 
				}
				else if(file.isFile()){
					count++;
				}
			}
		}
		return count;
	}
	
	/**
	 * Physical size of directory in terms of bytes.
	 *  
	 * @param directoryPath -{@link String} directory path.
	 * @param countDirectory -boolean, if set to true will count size of sub-directory also, else not.
	 * @return long -size of directory in terms of bytes. 
	 */
	public static long getDirectorySize(String directoryPath,boolean countDirectory) {
	    long length = 0;
	    if(StringUtils.isNull(directoryPath)){
			throw new NullPointerException("directoryPath cannot be null");
		}
	    File folder=new File(directoryPath);
	    if(folder.listFiles()!=null){
	    	for (File file : folder.listFiles()) {
	    		if (file.isFile())
	    			length += file.length();
	    		else if(countDirectory && file.isDirectory()){	
	    			length += getDirectorySize(file.getAbsolutePath(),countDirectory);
	    		}
	    	}
	    }
	    return length;
	}
	
	/**
	 * Get file name from path, include suffix.
	 * <pre>
	 *      getFileName(null)               =   null
	 *      getFileName("")                 =   ""
	 *      getFileName("   ")              =   "   "
	 *      getFileName("a.mp3")            =   "a.mp3"
	 *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
	 *      getFileName("abc")              =   "abc"
	 *      getFileName("c:\\")              =   ""
	 *      getFileName("c:\\a")             =   "a"
	 *      getFileName("c:\\a.b")           =   "a.b"
	 *      getFileName("c:a.txt\\a")        =   "a"
	 *      getFileName("/home/admin")      =   "admin"
	 *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
	 * </pre>
	 *
	 * @param filePath
	 * @return file name from path, include suffix
	 */
	public static String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}
		int filePosi = filePath.lastIndexOf(File.separator);
		return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
	}

	/**
	 * get folder name from path
	 * <p/>
	 * <pre>
	 *      getFolderName(null)               =   null
	 *      getFolderName("")                 =   ""
	 *      getFolderName("   ")              =   ""
	 *      getFolderName("a.mp3")            =   ""
	 *      getFolderName("a.b.rmvb")         =   ""
	 *      getFolderName("abc")              =   ""
	 *      getFolderName("c:\\")              =   "c:"
	 *      getFolderName("c:\\a")             =   "c:"
	 *      getFolderName("c:\\a.b")           =   "c:"
	 *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
	 *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
	 *      getFolderName("/home/admin")      =   "/home"
	 *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
	 * </pre>
	 *
	 * @param filePath
	 * @return
	 */
	public static String getFolderName(String filePath) {

		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(File.separator);
		return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
	}

	/**
	 * get suffix of file from path
	 * <p/>
	 * <pre>
	 *      getFileExtension(null)               =   ""
	 *      getFileExtension("")                 =   ""
	 *      getFileExtension("   ")              =   "   "
	 *      getFileExtension("a.mp3")            =   "mp3"
	 *      getFileExtension("a.b.rmvb")         =   "rmvb"
	 *      getFileExtension("abc")              =   ""
	 *      getFileExtension("c:\\")              =   ""
	 *      getFileExtension("c:\\a")             =   ""
	 *      getFileExtension("c:\\a.b")           =   "b"
	 *      getFileExtension("c:a.txt\\a")        =   ""
	 *      getFileExtension("/home/admin")      =   ""
	 *      getFileExtension("/home/admin/a.txt/b")  =   ""
	 *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
	 * </pre>
	 *
	 * @param filePath
	 * @return
	 */
	public static String getFileExtension(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePosi = filePath.lastIndexOf(File.separator);
		if (extenPosi == -1) {
			return "";
		}
		return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
	}
	
	/**
	 * get file name from path, not include suffix
	 * 
	 * <pre>
	 *      getFileNameWithoutExtension(null)               =   null
	 *      getFileNameWithoutExtension("")                 =   ""
	 *      getFileNameWithoutExtension("   ")              =   "   "
	 *      getFileNameWithoutExtension("abc")              =   "abc"
	 *      getFileNameWithoutExtension("a.mp3")            =   "a"
	 *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
	 *      getFileNameWithoutExtension("c:\\")              =   ""
	 *      getFileNameWithoutExtension("c:\\a")             =   "a"
	 *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
	 *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
	 *      getFileNameWithoutExtension("/home/admin")      =   "admin"
	 *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
	 * </pre>
	 * 
	 * @param filePath
	 * @return file name from path, not include suffix
	 * @see
	 */
	public static String getFileNameWithoutExtension(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}
		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePosi = filePath.lastIndexOf(File.separator);
		if (filePosi == -1) {
			return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
		}
		if (extenPosi == -1) {
			return filePath.substring(filePosi + 1);
		}
		return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
	}
	
	/**
	 * Gets Mime type for the file. 
	 * <br>
	 * Internally calls {@link MimeTypeMap#getExtensionFromMimeType(String)} 
	 * 
	 * @param file -{@link File} object.
	 * @return {@link String} mime extension for file.
	 */
	public static String getContentTypeFromFileString(File file) {
		String type = null;
		if (file != null) {
			type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
					MimeTypeMap.getFileExtensionFromUrl((Uri.fromFile(file)
							.toString())));
		}
		return type;
	}

	/**
	 * Gets Mime type for the {@link String} file path.
	 * <br>
	 * Internally calls {@link #getContentTypeFromFileString(File)} 
	 * 
	 * @param filePath -{@link String} physical file system absolute path for file.
	 * @return {@link String} -mime extension.
	 */
	public static String getContentTypeFromFileString(String filePath) {
		return getContentTypeFromFileString(new File(filePath));
	}
	
	public static byte[] readBytes(File file) throws IOException {
		return StreamUtils.toByteArray((new FileInputStream(file)));
	}
	
	/**
	 * Read file to string list, a element of list is a line
	 * 
	 * @param filePath
	 * @param charsetName The name of a supported {@link java.nio.charset.Charset <code>charset</code>}
	 * @return if file not exist, return null, else return content of file
	 * @throws RuntimeException if an error occurs while operator BufferedReader
	 */
	public static List<String> readFileToList(String filePath, String charsetName) {
		File file = new File(filePath);
		List<String> fileContent = new ArrayList<String>();
		if (file == null || !file.isFile()) {
			return null;
		}

		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
			reader = new BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileContent.add(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			StreamUtils.closeQuietly(reader);
		}
	}

	/**
	 * read file
	 * 
	 * @param filePath
	 * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
	 * @return if file not exist, return null, else return content of file
	 * @throws RuntimeException if an error occurs while operator BufferedReader
	 */
	public static StringBuilder readFile(String filePath, String charsetName) {
		File file = new File(filePath);
		StringBuilder fileContent = new StringBuilder("");
		if (file == null || !file.isFile()) {
			return null;
		}

		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
			reader = new BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!fileContent.toString().equals("")) {
					fileContent.append("\r\n");
				}
				fileContent.append(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			StreamUtils.closeQuietly(reader);
		}
	}
	
	/***
	 *  Check whether given {@link String} file path exist in file system.
	 *  
	 * @param filePath -{@link String}  physical file system absolute path for file.
	 * @return boolean -true if file exist, else false.
	 */
	public static boolean checkIfFileExists(String filePath){
		if(StringUtils.isNull(filePath)){
			throw new NullPointerException("filePath cannot be null");
		}
		boolean fileExist=false;
		File file=new File(filePath);
		if(file.exists()){
			fileExist=true;
		}
		else{
			fileExist=false;
		}
		return fileExist;
	}
	
	
	
	/**
	 * Gets the file size in formatted way.
	 * <br>
	 * 
	 * <br>
	 * Internally calls {@link #formatFileSize(long)}. 
	 * 
	 * @param filePath -{@link String}  physical file system absolute path for file.
	 * @return {@link String} - human readable form of file size.
	 */
	public static String formatFileSize(String filePath) {
		File file=new File(filePath);
		return formatFileSize(file.length());
	}
	
	/**
	 * Formats the long byte size into human readable form in terms of bytes,KB,MB and more.
	 * 
	 * @param size -long, size that needs to be converted.
	 * @return {@link String} - human readable form of given size.
	 */
	public static String formatFileSize(long size) {
		String hrSize = null;

		double bytes = size;
		double kiloByte = size/1024.0;
		double megaByte = (kiloByte/1024.0);
		double gigaByte = (megaByte/1024.0);
		double teraByte = (gigaByte/1024.0);
		double petaByte = (teraByte/1024.0);
		double exaByte = (petaByte/1024.0);
		DecimalFormat dec = new DecimalFormat("0.000");
		
		if (exaByte >1){
			hrSize = dec.format(teraByte).concat(" EB");
		}else if (petaByte>1 ) {
			hrSize = dec.format(teraByte).concat(" PB");
		}else if ( teraByte>1 ) {
			hrSize = dec.format(teraByte).concat(" TB");
		} else if ( gigaByte>1 ) {
			hrSize = dec.format(gigaByte).concat(" GB");
		} else if ( megaByte>1 ) {
			hrSize = dec.format(megaByte).concat(" MB");
		} else if ( kiloByte>1 ) {
			hrSize = dec.format(kiloByte).concat(" KB");
		} else {
			hrSize = dec.format(bytes).concat(" Bytes");
		}
		return hrSize;
	}
	
	
	
	

	/**
	 * Copy file from source to destination.
	 * 
	 * @param sourceFilePath - {@link String} source file path which file have to be copied.
	 * @param destFilePath - {@link String} destination file path where file has to be written. 
	 * @return
	 * @throws IOException 
	 * @throws RuntimeException if an error occurs while operator FileOutputStream
	 */
	public static boolean copyFile(String sourceFilePath, String destFilePath) throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(sourceFilePath);
			return writeFile(destFilePath, inputStream);
		}finally{
			StreamUtils.closeQuietly(inputStream);
		}
	}
	
	/**
	 * Copies the contents of one file to the other using {@link FileChannel}s.
	 *
	 * @param sourceFile - source {@link File}
	 * @param destinationFile - destination {@link File}
	 */
	public static void copyFile(File sourceFile, File destinationFile) throws IOException {
		FileInputStream in = new FileInputStream(sourceFile);
		FileOutputStream out = new FileOutputStream(destinationFile);
		FileChannel inChannel = in.getChannel();
		FileChannel outChannel = out.getChannel();

		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} finally {
			StreamUtils.closeQuietly(inChannel);
			StreamUtils.closeQuietly(outChannel);
			StreamUtils.closeQuietly(in);
			StreamUtils.closeQuietly(out);
		}
	}
	
	
	/**
	 * write file
	 *
	 * @param filePath
	 * @param stream
	 * @return
	 * @see {@link #writeFile(String, InputStream, boolean)}
	 */
	public static boolean writeStream(String filePath, InputStream stream) throws IOException {
		return writeStream(filePath, stream, false);
	}

	/**
	 * write file
	 *
	 * @param file   the file to be opened for writing.
	 * @param stream the input stream
	 * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
	 * @return return true
	 * @throws IOException if an error occurs while operator FileOutputStream
	 */
	public static boolean writeStream(String filePath, InputStream stream, boolean append) throws IOException {
		return writeStream(filePath != null ? new File(filePath) : null, stream, append);
	}

	/**
	 * write file
	 *
	 * @param file
	 * @param stream
	 * @return
	 * @see {@link #writeFile(File, InputStream, boolean)}
	 */
	public static boolean writeStream(File file, InputStream stream) throws IOException {
		return writeStream(file, stream, false);
	}

	/**
	 * write file
	 *
	 * @param file   the file to be opened for writing.
	 * @param stream the input stream
	 * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
	 * @return return true
	 * @throws IOException if an error occurs while operator FileOutputStream
	 */
	public static boolean writeStream(File file, InputStream stream, boolean append) throws IOException {
		OutputStream o = null;
		try {
			System.out.println("parent "+file.getParent());
			directoryCreator(file.getParent());
			if(!file.exists()){
				file.createNewFile();
			}
			o = new FileOutputStream(file, append);
			byte data[] = new byte[1024];
			int length = -1;
			while ((length = stream.read(data)) != -1) {
				o.write(data, 0, length);
			}
			o.flush();
			return true;
		} finally {
			StreamUtils.closeQuietly(o);
			StreamUtils.closeQuietly(stream);
		}
	}

	/**
	 * write file, also creates directory if not exist
	 * 
	 * @param filePath
	 * @param content
	 * @param append is append, if true, write to the end of file, else clear content of file and write into it
	 * @return return false if content is empty, true otherwise
	 * @throws RuntimeException if an error occurs while operator FileWriter
	 */
	public static boolean writeStringToFile(File file, String content, boolean append) throws IOException {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file, append);
			fileWriter.write(content);
			fileWriter.close();
			return true;
		} finally {
			StreamUtils.closeQuietly(fileWriter);
		}
	}

	/**
	 * write file
	 * 
	 * @param filePath
	 * @param contentList
	 * @param append is append, if true, write to the end of file, else clear content of file and write into it
	 * @return return false if contentList is empty, true otherwise
	 * @throws RuntimeException if an error occurs while operator FileWriter
	 */
	public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
		if (CollectionUtils.isEmpty(contentList)) {
			return false;
		}

		FileWriter fileWriter = null;
		try {
			directoryCreator(filePath);
			fileWriter = new FileWriter(filePath, append);
			int i = 0;
			for (String line : contentList) {
				if (i++ > 0) {
					fileWriter.write("\r\n");
				}
				fileWriter.write(line);
			}
			fileWriter.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			StreamUtils.closeQuietly(fileWriter);
		}
	}

	/**
	 * write file, the string will be written to the begin of the file
	 * 
	 * @param filePath
	 * @param content
	 * @return
	 * @throws IOException 
	 */
	public static boolean writeFile(String filePath, String content) throws IOException {
		return writeStringToFile(new File(filePath), content, false);
	}

	/**
	 * Write file, the string list will be written to the begin of the file
	 * 
	 * @param filePath
	 * @param contentList
	 * @return
	 */
	public static boolean writeFile(String filePath, List<String> contentList) {
		return writeFile(filePath, contentList, false);
	}

	/**
	 * Write file, the bytes will be over-written with new content.
	 * 
	 * @param filePath
	 * @param stream
	 * @return
	 * @throws IOException 
	 * @see {@link #writeFile(String, InputStream, boolean)}
	 */
	public static boolean writeFile(String filePath, InputStream stream) throws IOException {
		return writeFile(filePath, stream, false);
	}

	/**
	 * Write file
	 * 
	 * @param file the file to be opened for writing.
	 * @param stream the input stream
	 * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
	 * @return return true
	 * @throws IOException 
	 * @throws RuntimeException if an error occurs while operator FileOutputStream
	 */
	public static boolean writeFile(String filePath, InputStream stream, boolean append) throws IOException {
		return writeFile(filePath != null ? new File(filePath) : null, stream, append);
	}

	/**
	 * write file, the bytes will be written to the begin of the file
	 * 
	 * @param file
	 * @param stream
	 * @return
	 * @throws IOException 
	 * @see {@link #writeFile(File, InputStream, boolean)}
	 */
	public static boolean writeFile(File file, InputStream stream) throws IOException {
		return writeFile(file, stream, false);
	}

	/**
	 * write file
	 * 
	 * @param file the file to be opened for writing.
	 * @param stream the input stream
	 * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
	 * @return return true
	 * @throws IOException 
	 * @throws RuntimeException if an error occurs while operator FileOutputStream
	 */
	public static boolean writeFile(File file, InputStream stream, boolean append) throws IOException {
		OutputStream o = null;
		try {
			directoryCreator(file.getParent());
			o = new FileOutputStream(file, append);
			byte data[] = new byte[1024];
			int length = -1;
			while ((length = stream.read(data)) != -1) {
				o.write(data, 0, length);
			}
			o.flush();
			return true;
		} finally {
			StreamUtils.closeQuietly(o);
			StreamUtils.closeQuietly(stream);
		}
	}
	
	/***
	 * creates a diretory for the given string path if it is not created 
	 * @param dir
	 */
	public static void directoryCreator(String dir){ 
		File f = new File(dir); 
		if(!f.isDirectory()){ 
			f.mkdirs(); 
		}
	}
	
	public static void deleteFile(File fileOrDirectoryPath,boolean recursive){
        if (fileOrDirectoryPath.isDirectory()){
            for (File child : fileOrDirectoryPath.listFiles()){
            	if(recursive){
                	deleteFile(child,recursive);
                }
            }
        }
        fileOrDirectoryPath.delete();
    }
    
    public static void deleteFile(String fileOrDirectoryPath,boolean recursive){
      deleteFile(new File(fileOrDirectoryPath), recursive);
    }

	/**
	 * Deletes the file or directory at the given path using the rm shell
	 * command.
	 * @param path Path to the file to delete.
	 * @param recursive True to do the delete recursively, else false.
	 * @return True if the file existed and was deleted, or false if it didn't
	 * exist.
	 * @throws IOException if the shell execution fails.
	 */
	public static boolean deleteViaShell(String path, boolean recursive) throws IOException {
		File file = new File(path);
		return deleteViaShell(file, recursive);
	}

	/**
	 * Deletes the file or directory at the given path using the rm shell
	 * command.
	 * @param file The {@link File} to delete.
	 * @param recursive True to do the delete recursively, else false.
	 * @return True if the file existed and was deleted, or false if it didn't
	 * exist.
	 * @throws IOException if the shell execution fails.
	 */
	public static boolean deleteViaShell(File file, boolean recursive)throws IOException {
		if (file.exists()) {
			String deleleteCommand = (recursive ? "rm -r " : "rm ") + file.getAbsolutePath();
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(deleleteCommand);
			return true;
		}
		return false;
	}
	
}
