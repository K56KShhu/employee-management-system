package com.zkyyo.www.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartmentService {
    private static volatile DepartmentService INSTANCE = null;

    private DepartmentService() {
    }

    public static DepartmentService getInstance() {
        if (INSTANCE == null) {
            synchronized (DepartmentService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DepartmentService();
                }
            }
        }
        return INSTANCE;
    }

    public boolean isValidId(String deptId) {
        Pattern p = null;
        Matcher m = null;
        String regex = "^[\\d]{1,10}$";

        if (deptId != null) {
            deptId = deptId.trim();
            p = Pattern.compile(regex);
            m = p.matcher(deptId);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }
}

