package me.sheimi.util;

import java.lang.reflect.Constructor;

public class ReflectionUtils {

	public static <T> T newInstance(Class<T> clazz, String param) {
		T result;
		try {
			Constructor<T> meth = clazz.getDeclaredConstructor(String.class);
			result = meth.newInstance(param);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}
