package com.zkyyo.www.service;

import com.zkyyo.www.dao.EmployeeDao;
import com.zkyyo.www.po.EmployeePo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 该类存放关于员工的用于逻辑处理的代码
 * 其中包含员工的相关信息的校验,增删查改方法
 */
public class EmployeeService {
    /**
     * 作为排序的标识符, 以用户ID作为排序依据
     */
    public static final int ORDER_BY_USER_ID = 1;
    /**
     * 作为排序的标识符, 以用户所在部门ID作为排序依据
     */
    public static final int ORDER_BY_DEPT_ID = 2;
    /**
     * 作为排序的标识符, 以用户的薪水作为排序依据
     */
    public static final int ORDER_BY_SALARY = 3;
    /**
     * 作为排序的标识符, 用户的就职日期作为排序依据
     */
    public static final int ORDER_BY_DATE = 4;
    /**
     * 用于创建懒汉模式下的一个单例, 默认为null
     */
    private static volatile EmployeeService INSTANCE = null;

    /**
     * 禁止实例化新的对象
     */
    private EmployeeService() {
    }

    /**
     * 创建一个该类的实例
     * @return 返回这个类的一个实例
     */
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

    /**
     * 检查用户输入的密码是否正确
     * @param employeeId 用户输入的员工号
     * @param password 用户输入的未检验的密码
     * @return 密码相同为true 密码不同为false
     */
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

    /**
     * 校验员工号是否符合格式
     * 格式为一串1-10位的大于0的数字G
     * @param id 待校验的用户号
     * @return 符合为true, 不符合为false
     */
    public boolean isValidId(String id) {
        Pattern p;
        Matcher m;
        String regex = "^\\d{1,10}$";

        if (id != null) {
            p = Pattern.compile(regex);
            m = p.matcher(id);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测员工号是否存在
     * @param id 待检测的员工号
     * @return 存在为true, 不存在为false
     */
    public boolean isIdExisted(int id) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        EmployeePo foundEp = employeeDao.selectEmployeeByUserId(id);
        return foundEp != null;
    }

    /**
     * 校验员工名是否符合格式
     * 格式为20个字以内的中文名或含空格下20个字母以内的英文名
     * @param name 待校验的用户名
     * @return 符合为true, 不符合为false
     */
    public boolean isValidName(String name) {
        Pattern p;
        Matcher m;
//        String regex = "^[\\u4e00-\\u9fa5]{1,20}$|^[A-Za-z\\s]{1,20}$";
        String regex = "^[\\u4e00-\\u9fa5]{1,20}$|^[A-Za-z\\s]{1,20}$";

        if (name != null) {
            p = Pattern.compile(regex);
            m = p.matcher(name);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验两个密码是否相同
     * @param password 第一次输入的密码
     * @param confirmedPassword 第二次输入的代码
     * @return 相同为true, 不相同为false
     */
    public boolean isValidPassword(String password, String confirmedPassword) {
        if (password != null && confirmedPassword != null) {
            if (password.length() > 0 && password.length() <= 50) {
                if (password.equals(confirmedPassword)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 校验日期是否符合格式, 且是否合法
     * @param date 待校验的日期
     * @return 符合为true, 不符合为false
     */
    public boolean isValidDate(String date) {
        Pattern p;
        Matcher m;
        String regex = "^\\d{4}-\\d{1,2}-\\d{1,2}";

        if (date != null) {
            p = Pattern.compile(regex);
            m = p.matcher(date);
            if (m.matches()) {
                //将日期拆分为年, 月, 日
                String[] yearMonthDay = date.split("-");
                int year = Integer.valueOf(yearMonthDay[0]);
                int month = Integer.valueOf(yearMonthDay[1]);
                int day = Integer.valueOf(yearMonthDay[2]);
                //校验月份
                if (month >= 1 && month <= 12) {
                    Calendar mycal = new GregorianCalendar(year, month - 1, 1); //起始月份为0
                    int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    //校验对应月份的日数
                    if (day < daysInMonth) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 校验邮箱是否符合格式
     * @param email 待校验的邮箱
     * @return 符合为true, 不符合为false
     */
    public boolean isValidEmail(String email) {
        Pattern p;
        Matcher m;
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

    /**
     * 校验薪水是否符合格式
     * 薪水整数部分最高10位, 小数部分精度为.50
     * @param salary 待校验的薪水
     * @return 符合为true, 不符合为false
     */
    public boolean isValidSalary(String salary) {
        Pattern p;
        Matcher m;
        String regex = "^\\d{1,10}\\.?[05]?0?$";

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

    /**
     * 校验邮箱是否符合格式
     * @param mobile 待校验的邮箱
     * @return 符合为true, 不符合为false
     */
    public boolean isValidMobile(String mobile) {
        Pattern p;
        Matcher m;
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

    /**
     * 添加员工, 并自动注册员工号
     * @param name 员工名
     * @param mobile 手机号
     * @param email 邮箱
     * @param password 密码
     * @param departmentId 密码
     * @param salary 薪水
     * @param date 就职日期
     * @return 添加成功为该员工对象, 添加失败为null
     */
    public EmployeePo addEmployee(String name, String mobile, String email, String password,
                                  String departmentId, String salary, String date) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        int employeeId = employeeDao.createEmployeeId();
        int deptId = Integer.valueOf(departmentId);
        double eSalary = Double.valueOf(salary);
        java.sql.Date eDate = java.sql.Date.valueOf(date);
        EmployeePo newEp = new EmployeePo(employeeId, password, name, deptId, mobile, eSalary, email, eDate);
        boolean isAdded = employeeDao.addEmployee(newEp);

        if (isAdded) {
            return newEp;
        } else {
            return null;
        }
    }

    /**
     * 删除员工
     * @param userId 待删除的员工号
     * @return 删除成功返回被删除的员工对象, 找不到员工或删除失败返回null
     */
    public EmployeePo deleteEmployee(int userId) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        EmployeePo deletedEp = employeeDao.selectEmployeeByUserId(userId);

        if (deletedEp == null) {
            return null;
        } else {
            boolean isDeleted = employeeDao.deleteEmployee(userId);
            if (isDeleted) {
                return deletedEp;
            } else {
                return null;
            }
        }
    }

    /**
     * 模糊查询员工, 通过员工号查找
     * @param userId 员工号
     * @return 符合的员工列表
     */
    public List<EmployeePo> fuzzyFindEmployeeByUserId(int userId) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectPossibleEmployeesByUserId(userId);
    }

    /**
     * 模糊查询员工, 通过员工名查找
     * @param userName 员工名
     * @return 符合的员工列表
     */
    public List<EmployeePo> fuzzyFindEmployeeByUserName(String userName) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectPossibleEmployeesByUserName(userName);
    }

    /**
     * 查找同一部门的员工
     * @param deptId 部门号
     * @return 相同部门的员工列表
     */
    public List<EmployeePo> findEmployeeByDeptId(int deptId) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectEmployeesByDeptId(deptId);
    }

    /**
     * 精确查询员工, 通过员工号查找
     * @param userId 员工号
     * @return 找到返回该员工对象, 否则返回null
     */
    public EmployeePo findEmployee(int userId) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectEmployeeByUserId(userId);
    }

    /**
     * 查询所有员工
     * @return 包含所有员工的列表
     */
    public List<EmployeePo> findEmployees() {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectEmployees();
    }

    /**
     * 对员工列表进行排序
     * @param list 待排序的员工列表
     * @param orderType 排序依据, 参考该类的静态成员变量
     * @param isReverse 降序为true, 升序为false
     * @return 排序完成工员工列表
     */
    public List<EmployeePo> sort(List<EmployeePo> list, int orderType, boolean isReverse) {
        //根据排序依据进行排序
        switch (orderType) {
            //通过员工号排序
            case ORDER_BY_USER_ID:
                Collections.sort(list, new UserIdCompare());
                break;
            //通过部门号排序
            case ORDER_BY_DEPT_ID:
                Collections.sort(list, new DeptIdCompare());
                break;
            //通过薪水排序
            case ORDER_BY_SALARY:
                Collections.sort(list, new SalaryCompare());
                break;
            //通过就职日期排序
            case ORDER_BY_DATE:
                Collections.sort(list, new DateCompare());
                break;
            default:
                break;
        }
        //根据升降序排序
        if (isReverse) {
            Collections.reverse(list);
        }
        return list;
    }

    /**
     * 更新员工信息
     * @param updatedEp 包含最新员工信息的员工对象
     * @return 更新成功返回最新员工对象, 否则返回null
     */
    public EmployeePo updateEmployee(EmployeePo updatedEp) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        EmployeePo initialEp = employeeDao.selectEmployeeByUserId(updatedEp.getUserId());
        List<Integer> updatedTypes = new ArrayList<>();

        //姓名
        if (updatedEp.getUserName() != null && !updatedEp.getUserName().equals(initialEp.getUserName())) {
            updatedTypes.add(EmployeeDao.UPDATE_NAME);
        }
        //密码
        if (updatedEp.getPassword() != null && !updatedEp.getPassword().equals(initialEp.getPassword())) {
            updatedTypes.add(EmployeeDao.UPDATE_PASSWORD);
        }
        //邮箱
        if (updatedEp.getEmail() != null && !updatedEp.getEmail().equals(initialEp.getEmail())) {
            updatedTypes.add(EmployeeDao.UPDATE_EMAIL);
        }
        //手机号
        if (updatedEp.getMobile() != null && !updatedEp.getMobile().equals(initialEp.getMobile())) {
            updatedTypes.add(EmployeeDao.UPDATE_MOBILE);
        }
        //部门号
        if (updatedEp.getDeptId() != 0 && updatedEp.getDeptId() != initialEp.getDeptId()) {
            updatedTypes.add(EmployeeDao.UPDATE_DEPARTMENT_ID);
        }
        //薪水
        if (updatedEp.getSalary() != 0.0 && updatedEp.getSalary() != initialEp.getSalary()) {
            updatedTypes.add(EmployeeDao.UPDATE_SALARY);
        }
        //就职日期
        if (updatedEp.getEmployDate() != null && !updatedEp.getEmployDate().equals(initialEp.getEmployDate())) {
            updatedTypes.add(EmployeeDao.UPDATE_EMPLOYEE_DATE);
        }
        boolean isUpdated = employeeDao.updateEmployee(updatedTypes, updatedEp);
        if (isUpdated) {
            return employeeDao.selectEmployeeByUserId(updatedEp.getUserId());
        } else {
            return null;
        }
    }

    /**
     * 提供排序依据: 员工号
     */
    class UserIdCompare implements Comparator<EmployeePo> {
        public int compare(EmployeePo one, EmployeePo two) {
            return one.getUserId() - two.getUserId();
        }
    }

    /**
     * 提供排序依据: 就职日期
     */
    class DateCompare implements Comparator<EmployeePo> {
        public int compare(EmployeePo one, EmployeePo two) {
            return one.getEmployDate().compareTo(two.getEmployDate());
        }
    }

    /**
     * 提供排序依据: 薪水
     */
    class SalaryCompare implements Comparator<EmployeePo> {
        public int compare(EmployeePo one, EmployeePo two) {
            Double d1 = one.getSalary();
            Double d2 = two.getSalary();
            return d1.compareTo(d2);
        }
    }

    /**
     * 提供排序依据: 部门号
     */
    class DeptIdCompare implements Comparator<EmployeePo> {
        public int compare(EmployeePo one, EmployeePo two) {
            return one.getDeptId() - two.getDeptId();
        }
    }
}