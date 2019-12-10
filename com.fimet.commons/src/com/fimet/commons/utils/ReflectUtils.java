package com.fimet.commons.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.fimet.commons.Activator;

public final class ReflectUtils {

	private ReflectUtils() {}

	public static <F,T> void copy(F from, T to) {
		try {
			if (from == null || to == null) {
				return;
			}
			List<Field> fields = getDeclaredFields(from.getClass(), new ArrayList<>());
	        if (fields == null || fields.isEmpty()){
	            return;
	        }
	        Field toField;
	        for (Field f : fields) {
	            if (Modifier.isStatic(f.getModifiers())){
	                continue;
	            }
	            toField = getField(to.getClass(),f.getName());
	            if (toField != null) {
	            	toField.setAccessible(true);
	            	f.setAccessible(true);
	            	if (f.getType() == toField.getType()) {
	            		toField.set(to, f.get(from));
	            	}
	            }
	        }
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	private static List<Field> getDeclaredFields(Class<?> clazz, List<Field> fields) {
		if (clazz == Object.class) {
			return fields;
		}
		Field[] df = clazz.getDeclaredFields();
		for (Field field : df) {
			fields.add(field);
		}
		return getDeclaredFields(clazz.getSuperclass(), fields);
	}
	private static Field getField(Class<?> clazz, String name) {
		if (clazz == Object.class) {
			return null;
		}
		try {
			return clazz.getDeclaredField(name);
		} catch (Exception e) {
			return getField(clazz.getSuperclass(), name);
		}
	}
    public static <T>Object invokeGetter(T instance, String name){
        try {
            return instance.getClass().getMethod(name).invoke(instance);
        } catch (IllegalAccessException e) {
        	throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() != null){
                throw new RuntimeException(e.getCause());
            }
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            if (e.getCause() != null){
                throw new RuntimeException(e.getCause());
            }
            throw new RuntimeException(e);
        }
    }
    public static <T>Object invokeSetter(T instance, String name, Class<?> type, Object value){
        try {
            return instance.getClass().getMethod(name,type).invoke(instance,value);
        } catch (Exception e) {
        	if (Activator.isEnabledDebug()) {
        		Activator.getInstance().warning("Reflection Exception",e);
        	}
        	return null;
            /*if (e.getCause() != null){
                throw new RuntimeException(e.getCause());
            }
            throw new RuntimeException(e);*/
        }
    }
    public static <T>Object invokeSetterSafely(T instance, String name, Class<?> type, Object value){
        try {
            return instance.getClass().getMethod(name,type).invoke(instance,value);
        } catch (Exception e) {
        	return null;
        }
    }    
    public static <E> Field getDeclaredField(Class<E> clazz, String fieldName){
    	try {
    		return clazz.getDeclaredField(fieldName);
    	} catch(Exception e) {}
    	return null;
    }
    public static <E> Method getSetterFromField(Class<E> clazz, String fieldName, Class<?> arg){
    	try {
    		return clazz.getMethod(createSetterString(fieldName), arg);
    	} catch(Exception e) {}
    	return null;
    }
    public static String createGetterString(String fieldName){
        return "get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1, fieldName.length());
    }
    public static String createSetterString(String fieldName){
        return "set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1, fieldName.length());
    }
}