package com.carreiraconnect.Backend;

import org.springframework.ui.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class ModelUpdater {
    public static Optional<?> TryCast(Object obj, Class<?> destClass)
    {
        try {
            return Optional.of(destClass.cast(obj));
        }
        catch (ClassCastException e)
        {
            return Optional.empty();
        }
    }
    public static <T> void Update(T objectDest, T objectSrc)
    {
        Class<?> tClass = objectSrc.getClass();
        var methods = objectSrc.getClass().getDeclaredMethods();
        for(var m : methods)
        {
            if(m.getName().startsWith("get") && m.canAccess(objectSrc))
            {
                Object result;
                try {
                    result = m.invoke(objectSrc);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                if(result != null && !result.equals(0))
                {
                    Method setter;
                    try {
                        setter = tClass.getDeclaredMethod(m.getName().replace("get", "set"), m.getReturnType());
                        setter.invoke(objectDest, result);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    public static <T> void Update2(T objectDest, T objectSrc) {
        Class<?> tClass = objectSrc.getClass();
        var fields = tClass.getDeclaredFields();
        for(var f : fields)
        {
            f.setAccessible(true);
            Object newValue;
            try {
                newValue = f.get(objectSrc);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if(
                    newValue != null
                    //        &&
                    //(ModelUpdater.TryCast(newValue, Integer.class).filter(v -> v != (Integer)0).isPresent())
            )

            {
                try {
                    f.set(objectDest, newValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            f.setAccessible(false);
        }
    }


}
