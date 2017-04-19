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

public class LogUtil {
    public static final String PATH = "/home/xu/java_new_place/log.txt";
    private static final String PRE_SPACE = "                        ";

    //员工操作
    public static void add(EmployeePo handler, EmployeePo newEmployee) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 新增员工" + newEmployee.geteName() + "(" + newEmployee.geteUserId() + ")"
                    + " <" + handler.geteName() + "(" + handler.geteUserId() + ")>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(EmployeePo handler, EmployeePo deletedEmployee) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 删除员工" + deletedEmployee.geteName() + "(" + deletedEmployee.geteUserId() + ")"
                    + " <" + handler.geteName() + "(" + handler.geteUserId() + ")>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void update(EmployeePo handler, EmployeePo initialEp, EmployeePo updatedEp) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 修改员工" + initialEp.geteName() + "(" + initialEp.geteUserId() + ")"
                    + " <" + handler.geteName() + "(" + handler.geteUserId() + ")>");
            bw.newLine();
            if (initialEp.geteDeptId() != updatedEp.geteDeptId()) {
                bw.write(PRE_SPACE + "原部门号: " + initialEp.geteDeptId());
                bw.newLine();
                bw.write(PRE_SPACE + "现部门号: " + updatedEp.geteDeptId());
                bw.newLine();
            }
            if (!initialEp.geteMobile().equals(updatedEp.geteMobile())) {
                bw.write(PRE_SPACE + "原手机号: " + initialEp.geteMobile());
                bw.newLine();
                bw.write(PRE_SPACE + "现手机号: " + updatedEp.geteMobile());
                bw.newLine();
            }
            if (initialEp.geteSalary() != updatedEp.geteSalary()) {
                bw.write(PRE_SPACE + "原薪水: " + initialEp.geteSalary());
                bw.newLine();
                bw.write(PRE_SPACE + "现薪水: " + updatedEp.geteSalary());
                bw.newLine();
            }
            if (!initialEp.geteEmail().equals(updatedEp.geteEmail())) {
                bw.write(PRE_SPACE + "原邮箱: " + initialEp.geteEmail());
                bw.newLine();
                bw.write(PRE_SPACE + "现邮箱: " + updatedEp.geteEmail());
                bw.newLine();
            }
            if (!initialEp.geteEmployDate().equals(updatedEp.geteEmployDate())) {
                bw.write(PRE_SPACE + "原就职日期: " + initialEp.geteEmployDate());
                bw.newLine();
                bw.write(PRE_SPACE + "现就职日期: " + updatedEp.geteEmployDate());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //部门
    public static void add(EmployeePo handler, DepartmentPo newDepartment) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 建立部门" + newDepartment.getDeptName() + "(" + newDepartment.getDeptId() + ")"
                    + " <" + handler.geteName() + "(" + handler.geteUserId() + ")>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(EmployeePo handler, DepartmentPo deleteDepartment) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 解散部门" + deleteDepartment.getDeptName() + "(" + deleteDepartment.getDeptId() + ")"
                    + " <" + handler.geteName() + "(" + handler.geteUserId() + ")>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void update(EmployeePo handler, DepartmentPo initialDept, DepartmentPo updatedDept) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 修改部门" + updatedDept.getDeptName() + "("
                    + updatedDept.getDeptId() + ") <" + handler.geteName() + "(" + handler.geteUserId() + ")>");
            bw.newLine();
            if (!initialDept.getDeptName().equals(updatedDept.getDeptName())) {
                bw.write(PRE_SPACE + "原部门名: " + initialDept.getDeptName());
                bw.newLine();
                bw.write(PRE_SPACE + "现部门名: " + updatedDept.getDeptName());
                bw.newLine();
            }
            if (!initialDept.getDeptDesc().equals(updatedDept.getDeptDesc())) {
                bw.write(PRE_SPACE + "原部门描述: " + initialDept.getDeptDesc());
                bw.newLine();
                bw.write(PRE_SPACE + "现部门描述: " + updatedDept.getDeptDesc());
                bw.newLine();
            }
            if (!initialDept.getBuiltDate().equals(updatedDept.getBuiltDate())) {
                bw.write(PRE_SPACE + "原部门建立时间: " + initialDept.getBuiltDate());
                bw.newLine();
                bw.write(PRE_SPACE + "现部门建立时间: " + updatedDept.getBuiltDate());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //评价
    public static void add(EmployeePo handler, EvaluationPo newEvaluation) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            EmployeeDao empd = EmployeeDao.getInstance();
            EmployeePo ep = empd.selectEmployeeByUserId(newEvaluation.getBeEvaluatedId());
            bw.write("* " + timeStamp + " - 评价" + ep.geteName() + "(" + ep.geteUserId() + ")"
                    + " <" + handler.geteName() + "(" + handler.geteUserId() + ")>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(EmployeePo handler, EvaluationPo deletedEvaluation) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            EmployeeDao empd = EmployeeDao.getInstance();
            EmployeePo employee = empd.selectEmployeeByUserId(deletedEvaluation.getBeEvaluatedId());
            EmployeePo evaluator = empd.selectEmployeeByUserId(deletedEvaluation.getEvaluatorId());
            bw.write("* " + timeStamp + " - 删除评价(" + deletedEvaluation.getEvaluationId() + ") "
                    + employee.geteName() + "(" + employee.geteUserId() + ")"
                    + "<-" + evaluator.geteName() + "(" + evaluator.geteUserId() + ")"
                    + " <" + handler.geteName() + "(" + handler.geteUserId() + ")>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void update(EmployeePo handler, EvaluationPo initialEval, EvaluationPo updateEval) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        EmployeeDao empd = EmployeeDao.getInstance();
        EmployeePo employee = empd.selectEmployeeByUserId(initialEval.getBeEvaluatedId());
        EmployeePo evaluator = empd.selectEmployeeByUserId(initialEval.getEvaluatorId());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("* " + timeStamp + " - 修改评价(" + initialEval.getEvaluationId() + ") "
                    + employee.geteName() + "(" + employee.geteUserId() + ")"
                    + "<-" + evaluator.geteName() + "(" + evaluator.geteUserId() + ")"
                    + " <" + handler.geteName() + "(" + handler.geteUserId() + ")>");
            bw.newLine();
            if (initialEval.getStarLevel() != updateEval.getStarLevel()) {
                bw.write(PRE_SPACE + "原评价等级: " + initialEval.getStarLevel());
                bw.newLine();
                bw.write(PRE_SPACE + "现评价等级: " + updateEval.getStarLevel());
                bw.newLine();
            }
            if (!initialEval.getComment().equals(updateEval.getComment())) {
                bw.write(PRE_SPACE + "原评价: " + initialEval.getComment());
                bw.newLine();
                bw.write(PRE_SPACE + "现评价: " + updateEval.getComment());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
