package com.tushuoBolg.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author Bear 不知道这哥们是谁。
 *  做一个方法，可以将一个JavaBean风格对象的属性值拷贝到另一个对象的同名属性中 (如果不存在同名属性的就不拷贝）
 **/
public class BeanCopy {

    /**
     * 拷贝两个对象中的属性.
     * @param target    拷贝到哪儿
     * @param source    从哪儿拷贝
     * @param copyNull  true：拷贝为NUll的字段，false：不拷贝为null的字段。
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void copy(Object target, Object source, Boolean copyNull) throws Exception {
		/*
		 * 分别获得源对象和目标对象的Class类型对象,Class对象是整个反射机制的源头和灵魂！
		 *
		 * Class对象是在类加载的时候产生,保存着类的相关属性，构造器，方法等信息
		 */
        Class sourceClz = source.getClass();
        Class targetClz = target.getClass();
        // 得到Class对象所表征的类的所有属性(包括私有属性)
        Field[] fields = sourceClz.getDeclaredFields();
        if (fields.length == 0) {
            fields = sourceClz.getSuperclass().getDeclaredFields();
        }
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            Field targetField = null;
            // 得到targetClz对象所表征的类的名为fieldName的属性，不存在就进入下次循环
            try {
                targetField = targetClz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                targetField = targetClz.getSuperclass().getDeclaredField(
                        fieldName);
            }
            // 判断sourceClz字段类型和targetClz同名字段类型是否相同
            if (fields[i].getType() == targetField.getType()) {
                // 由属性名字得到对应get和set方法的名字
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);

                String setMethodName = "set"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);

                // 由方法的名字得到get和set方法的Method对象
                Method getMethod;
                Method setMethod;
                try {
                    try {
                        getMethod = sourceClz.getDeclaredMethod(getMethodName,
                                new Class[] {});
                    } catch (NoSuchMethodException e) {
                        getMethod = sourceClz.getSuperclass()
                                .getDeclaredMethod(getMethodName,
                                        new Class[] {});
                    }
                    try {
                        setMethod = targetClz.getDeclaredMethod(setMethodName,
                                fields[i].getType());
                    } catch (NoSuchMethodException e) {
                        setMethod = targetClz.getSuperclass()
                                .getDeclaredMethod(setMethodName,
                                        fields[i].getType());
                    }

                    // 调用source对象的getMethod方法
                    Object result = getMethod.invoke(source, new Object[] {});

                    if( (result == null && !copyNull) || result instanceof Collection){
                        continue;
                    }

                    // 调用target对象的setMethod方法
                    setMethod.invoke(target, result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new Exception("同名属性类型不匹配！");
            }
        }
    }

}
