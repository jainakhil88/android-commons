package com.aj.android.commons.java;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ReflectionUtils {

	/**
	 * Gets an {@link ArrayList<{@link String>} of <code>public</code> and <code>protected</code> 
	 * methods for the given {@link Class}. 
	 * 
	 * @param parameterClass -{@link Class}
	 * @return {@link ArrayList<{@link String>} -list of all method names.
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getAllMethodName(Class parameterClass){
		ArrayList<String> methodNameList=new ArrayList<>();
		for (Method method : parameterClass.getDeclaredMethods()) {
			int modifiers = method.getModifiers();
			if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
				methodNameList.add(method.getName());
			}
		}
		return methodNameList;
	}
}
