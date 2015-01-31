package com.aj.android.commons.exception;

/**
 *
 * Extension of {@link RuntimeException}, exception thrown along with proper message, when particular
 * permission is not defined in the application manifest.
 *<br>
 *Message format is
 *<pre> 
 *You need "permission-name" permission. <br>
 *Permission "permission-name" is not defined, Please add <uses-permission android:name="permission-name""/> into AndroidManifest.xml
 *</pre>
 *
 *@author Akhil Jain
 */
public class PermissionNotDefinedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	String messageFormat="You need %1$s permission. Permission ~ %1$s is not defined, Please add <uses-permission android:name=\"%1$s\"/> into AndroidManifest.xml";	
	String message;
	
	/**
	 * Constructor 
	 * @param permissionName -{@link String} name of the permission.
	 */
	public PermissionNotDefinedException(String permissionName){
		this.message=String.format(messageFormat, permissionName);
	}
	
	@Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
    
}
