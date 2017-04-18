package com.zkyyo.www.service;

import com.zkyyo.www.dao.EmployeeDao;
import com.zkyyo.www.po.EmployeePo;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeService {
    private static volatile EmployeeService INSTANCE = null;

    private EmployeeService() {
    }

    public static EmployeeService getInstance() {
        if (INSTANCE == null) {
            synchronized (EmployeeService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EmployeeService();
                }
            }
        }
        return INSTANCE;
    }

    public boolean checkLogin(int employeeId, String password) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        String realPsw = employeeDao.loginCheck(employeeId);
        if (realPsw != null && password != null) {
            if (realPsw.equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidName(String name) {
        Pattern p = null;
        Matcher m = null;
        String regex = "^[\\u4e00-\\u9fa5]+$|^[A-Za-z]+$";

        if (name != null) {
            p = Pattern.compile(regex);
            m = p.matcher(name);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidPassword(String password, String confirmedPassword) {
        if (password != null && confirmedPassword != null) {
            if (password.length() > 0) {
                if (password.equals(confirmedPassword)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidDate(String date) {
        Pattern p = null;
        Matcher m = null;
        String regex = "^\\d{4}-\\d{1,2}-\\d{1,2}";

        if (date != null) {
            p = Pattern.compile(regex);
            m = p.matcher(date);
            if (m.matches()) {
                String[] yearMonthDay = date.split("-");
                int year = Integer.valueOf(yearMonthDay[0]);
                int month = Integer.valueOf(yearMonthDay[1]);
                int day = Integer.valueOf(yearMonthDay[2]);
                if (month >= 1 && month <= 12) {
                    Calendar mycal = new GregorianCalendar(year, month - 1, 1); //起始月份为0
                    int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (day < daysInMonth) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isValidEmail(String email) {
        Pattern p = null;
        Matcher m = null;
        String regex = "^[\\w.+-]+@[\\w.+-]+\\.[\\w.+-]+$";

        if (email != null) {
            p = Pattern.compile(regex);
            m = p.matcher(email);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidSalary(String salary) {
        Pattern p = null;
        Matcher m = null;
        String regex = "^\\d{1,13}\\.?[05]?0?$";

        if (salary != null) {
            p = Pattern.compile(regex);
            m = p.matcher(salary);
            if (m.matches()) {
                if (Integer.valueOf(salary) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidMobile(String mobile) {
        Pattern p = null;
        Matcher m = null;
        String regex = "^0{0,2}((\\+86)|(86))?1[0-9]{10}$";

        if (mobile != null) {
            p = Pattern.compile(regex);
            m = p.matcher(mobile);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    public Integer addEmployee(String name, String mobile, String email, String password,
                               String departmentId, String salary, String date) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        int employeeId = employeeDao.createEmployeeId();
        int deptId = Integer.valueOf(departmentId);
        double eSalary = Double.valueOf(salary);
        java.sql.Date eDate = java.sql.Date.valueOf(date);
        EmployeePo newEp = new EmployeePo(employeeId, password, name, deptId, mobile, eSalary, email, eDate);
        boolean isAdded = employeeDao.addEmployee(newEp);
        if (isAdded) {
            return employeeId;
        } else {
            return null;
        }
    }

    public List<EmployeePo> fuzzyFindEmployeeByUserId(int userId) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectPossibleEmployeesByUserId(userId);
    }

    public List<EmployeePo> fuzzyFindEmployeeByUserName(String userName) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectPossibleEmployeesByUserName(userName);
    }

    public List<EmployeePo> findEmployeeByDeptId(int deptId) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectEmployeesByDeptId(deptId);
    }

    public List<EmployeePo> findEmployees() {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectEmployees();
    }

    public boolean updateEmployee(EmployeePo updatedEp) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        EmployeePo initialEp = employeeDao.selectEmployeeByUserId(updatedEp.getUserId());
        List<Integer> updatedTypes = new ArrayList<>();

        //姓名
        if (updatedEp.getUserName() != null && !initialEp.getUserName().equals(updatedEp.getUserName())) {
            updatedTypes.add(EmployeeDao.UPDATE_NAME);
        }
        //密码
        if (updatedEp.getPassword() != null && !initialEp.getPassword().equals(updatedEp.getPassword())) {
            updatedTypes.add(EmployeeDao.UPDATE_PASSWORD);
        }
        //邮箱
        if (updatedEp.getEmail() != null && !initialEp.getEmail().equals(updatedEp.getEmail())) {
            updatedTypes.add(EmployeeDao.UPDATE_EMAIL);
        }
        //手机号
        if (updatedEp.getMobile() != null && !initialEp.getMobile().equals(updatedEp.getMobile())) {
            updatedTypes.add(EmployeeDao.UPDATE_MOBILE);
        }
        //部门号
        if (updatedEp.getDeptId() != 0 && initialEp.getDeptId() != updatedEp.getDeptId()) {
            updatedTypes.add(EmployeeDao.UPDATE_DEPARTMENT_ID);
        }
        //薪水
        if (updatedEp.getSalary() != 0.0 && initialEp.getSalary() != updatedEp.getSalary()) {
            updatedTypes.add(EmployeeDao.UPDATE_SALARY);
        }
        //就职日期
        if (updatedEp.getEmployDate() != null && !initialEp.getEmployDate().equals(updatedEp.getEmployDate())) {
            updatedTypes.add(EmployeeDao.UPDATE_EMPLOYEE_DATE);
        }

        return employeeDao.updateEmployee(updatedTypes, updatedEp);

        /*
        //姓名
        if (updatedEp.getUserName() != null && !initialEp.getUserName().equals(updatedEp.getUserName())) {
            isUpdated = employeeDao.updateEmployee(EmployeeDao.UPDATE_NAME, updatedEp);
            if (!isUpdated) {
                return false;
            }
        }
        //密码
        if (updatedEp.getPassword() != null && !initialEp.getPassword().equals(updatedEp.getPassword())) {
            isUpdated = employeeDao.updateEmployee(EmployeeDao.UPDATE_PASSWORD, updatedEp);
            if (!isUpdated) {
                return false;
            }
        }
        //邮箱
        if (updatedEp.getEmail() != null && !initialEp.getEmail().equals(updatedEp.getEmail())) {
            isUpdated = employeeDao.updateEmployee(EmployeeDao.UPDATE_EMAIL, updatedEp);
            if (!isUpdated) {
                return false;
            }
        }
        //手机号
        if (updatedEp.getMobile() != null && !initialEp.getMobile().equals(updatedEp.getMobile())) {
            isUpdated = employeeDao.updateEmployee(EmployeeDao.UPDATE_MOBILE, updatedEp);
            if (!isUpdated) {
                return false;
            }
        }
        //部门号
        if (updatedEp.getDeptId() != 0 && initialEp.getDeptId() != updatedEp.getDeptId()) {
            isUpdated = employeeDao.updateEmployee(EmployeeDao.UPDATE_DEPARTMENT_ID, updatedEp);
            if (!isUpdated) {
                return false;
            }
        }
        //薪水
        if (updatedEp.getSalary() != 0.0 && initialEp.getSalary() != updatedEp.getSalary()) {
            isUpdated = employeeDao.updateEmployee(EmployeeDao.UPDATE_SALARY, updatedEp);
            if (!isUpdated) {
                return false;
            }
        }
        //就职日期
        if (updatedEp.getEmployDate() != null && !initialEp.getEmployDate().equals(updatedEp.getEmployDate())) {
            isUpdated = employeeDao.updateEmployee(EmployeeDao.UPDATE_EMPLOYEE_DATE, updatedEp);
            if (!isUpdated) {
                return false;
            }
        }
        return true;
        */
    }

    public boolean deleteEmployee(int userId) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        boolean isDeleted = employeeDao.deleteEmployee(userId);
        if (isDeleted) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        EmployeeService employeeService = EmployeeService.getInstance();
        do {
            Scanner in = new Scanner(System.in);
            System.out.println(employeeService.isValidName(in.next()));
        } while (true);
    }

}