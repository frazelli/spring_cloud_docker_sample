package com.bankofaffiliates.common.utils;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

public class BeanCopy {
    public static void nullAwareBeanCopy(Object dest, Object source) throws IllegalAccessException, InvocationTargetException {
        new BeanUtilsBean() {
            @Override
            public void copyProperty(Object dest, String name, Object value)
                    throws IllegalAccessException, InvocationTargetException {
                if (value != null && !name.equals("id")) {
                    super.copyProperty(dest, name, value);
                }
            }
        }.copyProperties(dest, source);
    }
}
