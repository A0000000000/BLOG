package xyz.a00000.blog.utils;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {

    public static Object getValueByFieldName(Object obj, String fieldName) {
        try {
            Field field = null;
            for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
                try {
                    field = superClass.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException ignore) { }
            }
            Object value = null;
            if (field != null) {
                if (field.canAccess(obj)) {
                    value = field.get(obj);
                } else {
                    field.setAccessible(true);
                    value = field.get(obj);
                    field.setAccessible(false);
                }
            }
            return value;
        } catch (Exception ignore) { }
        return null;
    }

    public static Method getMethodByName(Class<?> clazz, String methodName) {
        if (clazz == null || StringUtils.isEmpty(methodName)) {
            return null;
        }
        while (clazz != Object.class) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                if (methodName.equals(declaredMethod.getName())) {
                    declaredMethod.setAccessible(true);
                    return declaredMethod;
                }
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (methodName.equals(method.getName())) {
                    return method;
                }
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public static void setValueByFieldName(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            if (field.canAccess(obj)) {
                field.set(obj, value);
            } else {
                field.setAccessible(true);
                field.set(obj, value);
                field.setAccessible(false);
            }
        } catch (Exception ignore) { }
    }
}
