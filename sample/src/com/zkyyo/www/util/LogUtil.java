package com.zkyyo.www.util;

import com.zkyyo.www.dao.EmployeeDao;
import com.zkyyo.www.po.DepartmentPo;
import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.po.EvaluationPo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 提供日志相关的方法
 * 用于记录用户对员工, 部门和评价的增删改操作
 */
public class LogUtil {
    /**
     * 日志文件的存放地址
     */
    private static final String PATH = "/home/xu/java-new-land/log.txt";
    /**
     * 用于格式化日志输出, 美化目的
     */
    private static final String PRE_SPACE = "                        ";

    /**
     * 记录用户增加员工的操作
     *
     * @param operatorId 操作者员工号
     * @param newEp      新员工对象
     */
    public static void add(int operatorId, EmployeePo newEp) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        //操作者
        EmployeePo operator = employeeDao.selectEmployeeByUserId(operatorId);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 新增员工" + newEp.getUserName() + "(" + newEp.getUserId() + ")"
                    + " <" + operator.getUserName() + "(" + operator.getUserId() + ")>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录用户创建部门的操作
     *
     * @param operatorId 操作者员工号
     * @param newEval    新部门对象
     */
    public static void add(int operatorId, EvaluationPo newEval) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        //操作者兼评价者
        EmployeePo operator = employeeDao.selectEmployeeByUserId(operatorId);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            //被评价者对象
            EmployeePo ep = employeeDao.selectEmployeeByUserId(newEval.getBeEvaluatedId());
            bw.write("* " + timeStamp + " - 评价" + ep.getUserName() + "(" + ep.getUserId() + ")"
                    + " <" + operator.getUserName() + "(" + operator.getUserId() + ")>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录用户添加评论的操作
     *
     * @param operatorId 操作者员工号
     * @param newDept    新评价对象
     */
    public static void add(int operatorId, DepartmentPo newDept) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        //操作者
        EmployeePo operator = employeeDao.selectEmployeeByUserId(operatorId);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 建立部门" + newDept.getName() + "(" + newDept.getDeptId() + ")"
                    + " <" + operator.getUserName() + "(" + operator.getUserId() + ")>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录用户删除员工的操作
     *
     * @param operatorId 操作者员工号
     * @param deletedEp  被删除的员工对象
     */
    public static void delete(int operatorId, EmployeePo deletedEp) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        //操作者
        EmployeePo operator = employeeDao.selectEmployeeByUserId(operatorId);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 删除员工" + deletedEp.getUserName() + "(" + deletedEp.getUserId() + ")"
                    + " <" + operator.getUserName() + "(" + operator.getUserId() + ")>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录员工解散部门的操作
     *
     * @param operatorId  操作者员工号
     * @param deletedDept 被解散的部门对象
     */
    public static void delete(int operatorId, DepartmentPo deletedDept) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        //操作者
        EmployeePo operator = employeeDao.selectEmployeeByUserId(operatorId);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 解散部门" + deletedDept.getName() + "(" + deletedDept.getDeptId() + ")"
                    + " <" + operator.getUserName() + "(" + operator.getUserId() + ")>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录员工删除评论的操作
     *
     * @param operatorId  操作者员工号
     * @param deletedEval 被删除的评论对象
     */
    public static void delete(int operatorId, EvaluationPo deletedEval) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        //操作者
        EmployeePo operator = employeeDao.selectEmployeeByUserId(operatorId);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            //评价者
            EmployeePo employee = employeeDao.selectEmployeeByUserId(deletedEval.getBeEvaluatedId());
            //被评价者
            EmployeePo evaluator = employeeDao.selectEmployeeByUserId(deletedEval.getEvaluatorId());
            bw.write("* " + timeStamp + " - 删除评价(" + deletedEval.getEvaluationId() + ") "
                    + employee.getUserName() + "(" + employee.getUserId() + ")"
                    + "<<<" + evaluator.getUserName() + "(" + evaluator.getUserId() + ")"
                    + " <" + operator.getUserName() + "(" + operator.getUserId() + ")>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录用户更新员工的操作, 并记录新旧数据的区别
     *
     * @param operatorId 操作者员工号
     * @param initialEp  更新前的员工对象
     * @param updatedEp  更新后的员工对象
     */
    public static void update(int operatorId, EmployeePo initialEp, EmployeePo updatedEp) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        //操作者
        EmployeePo operator = employeeDao.selectEmployeeByUserId(operatorId);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 修改员工" + initialEp.getUserName() + "(" + initialEp.getUserId() + ")"
                    + " <" + operator.getUserName() + "(" + operator.getUserId() + ")>");
            bw.newLine();
            //姓名
            if (updatedEp.getUserName() != null && !updatedEp.getUserName().equals(initialEp.getUserName())) {
                bw.write(PRE_SPACE + "原姓名: " + initialEp.getUserName());
                bw.newLine();
                bw.write(PRE_SPACE + "现姓名: " + updatedEp.getUserName());
                bw.newLine();
            }
            //部门号
            if (updatedEp.getDeptId() != 0 && updatedEp.getDeptId() != initialEp.getDeptId()) {
                bw.write(PRE_SPACE + "原部门号: " + initialEp.getDeptId());
                bw.newLine();
                bw.write(PRE_SPACE + "现部门号: " + updatedEp.getDeptId());
                bw.newLine();
            }
            //手机号
            if (updatedEp.getMobile() != null && !updatedEp.getMobile().equals(initialEp.getMobile())) {
                bw.write(PRE_SPACE + "原手机号: " + initialEp.getMobile());
                bw.newLine();
                bw.write(PRE_SPACE + "现手机号: " + updatedEp.getMobile());
                bw.newLine();
            }
            //薪水
            if (updatedEp.getSalary() != 0.0 && updatedEp.getSalary() != initialEp.getSalary()) {
                bw.write(PRE_SPACE + "原薪水: " + initialEp.getSalary());
                bw.newLine();
                bw.write(PRE_SPACE + "现薪水: " + updatedEp.getSalary());
                bw.newLine();
            }
            //邮箱
            if (updatedEp.getEmail() != null && !updatedEp.getEmail().equals(initialEp.getEmail())) {
                bw.write(PRE_SPACE + "原邮箱: " + initialEp.getEmail());
                bw.newLine();
                bw.write(PRE_SPACE + "现邮箱: " + updatedEp.getEmail());
                bw.newLine();
            }
            //就职日期
            if (updatedEp.getEmployDate() != null && !updatedEp.getEmployDate().equals(initialEp.getEmployDate())) {
                bw.write(PRE_SPACE + "原就职日期: " + initialEp.getEmployDate());
                bw.newLine();
                bw.write(PRE_SPACE + "现就职日期: " + updatedEp.getEmployDate());
                bw.newLine();
            }
            //密码
            if (updatedEp.getPassword() != null && !updatedEp.getPassword().equals(initialEp.getPassword())) {
                bw.write(PRE_SPACE + "修改密码");
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录用户更新部门的操作, 并记录新旧数据的区别
     *
     * @param operatorId  操作者员工号
     * @param initialDept 更新前的部门对象
     * @param updatedDept 更新后的部门对象
     */
    public static void update(int operatorId, DepartmentPo initialDept, DepartmentPo updatedDept) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        //操作者
        EmployeePo operator = employeeDao.selectEmployeeByUserId(operatorId);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 修改部门" + initialDept.getName() + "("
                    + initialDept.getDeptId() + ") <" + operator.getUserName() + "(" + operator.getUserId() + ")>");
            bw.newLine();
            //部门名
            if (updatedDept.getName() != null && !updatedDept.getName().equals(initialDept.getName())) {
                bw.write(PRE_SPACE + "原部门名: " + initialDept.getName());
                bw.newLine();
                bw.write(PRE_SPACE + "现部门名: " + updatedDept.getName());
                bw.newLine();
            }
            //部门描述
            if (updatedDept.getDescription() != null && !updatedDept.getDescription().equals(initialDept.getDescription())) {
                String initialDesc = CleanUtil.cleanTest(initialDept.getDescription());
                String updatedDesc = CleanUtil.cleanTest(updatedDept.getDescription());
                bw.write(PRE_SPACE + "原部门描述: " + initialDesc);
                bw.newLine();
                bw.write(PRE_SPACE + "现部门描述: " + updatedDesc);
                bw.newLine();
            }
            //建立时间
            if (updatedDept.getBuildDate() != null && !updatedDept.getBuildDate().equals(initialDept.getBuildDate())) {
                bw.write(PRE_SPACE + "原部门建立时间: " + initialDept.getBuildDate());
                bw.newLine();
                bw.write(PRE_SPACE + "现部门建立时间: " + updatedDept.getBuildDate());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录用户更新评论的操作, 并记录新旧数据的区别
     *
     * @param operatorId  操作者员工号
     * @param initialEval 更新前的评论
     * @param updatedEval 更新后的评论
     */
    public static void update(int operatorId, EvaluationPo initialEval, EvaluationPo updatedEval) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        EmployeePo operator = employeeDao.selectEmployeeByUserId(operatorId);
        EmployeePo employee = employeeDao.selectEmployeeByUserId(initialEval.getBeEvaluatedId());
        EmployeePo evaluator = employeeDao.selectEmployeeByUserId(initialEval.getEvaluatorId());
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 修改评价(" + initialEval.getEvaluationId() + ") "
                    + employee.getUserName() + "(" + employee.getUserId() + ")"
                    + "<<<" + evaluator.getUserName() + "(" + evaluator.getUserId() + ")"
                    + " <" + operator.getUserName() + "(" + operator.getUserId() + ")>");
            bw.newLine();
            //评价等级
            if (updatedEval.getStarLevel() != 0 && updatedEval.getStarLevel() != initialEval.getStarLevel()) {
                bw.write(PRE_SPACE + "原评价等级: " + initialEval.getStarLevel());
                bw.newLine();
                bw.write(PRE_SPACE + "现评价等级: " + updatedEval.getStarLevel());
                bw.newLine();
            }
            //评价内容
            if (updatedEval.getComment() != null && !updatedEval.getComment().equals(initialEval.getComment())) {
                String initialComment = CleanUtil.cleanTest(initialEval.getComment());
                String updatedComment = CleanUtil.cleanTest(updatedEval.getComment());
                bw.write(PRE_SPACE + "原评价: " + initialComment);
                bw.newLine();
                bw.write(PRE_SPACE + "现评价: " + updatedComment);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
