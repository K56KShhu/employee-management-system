package com.zkyyo.www.web;

import com.zkyyo.www.service.EmployeeService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class ManageSystemListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    public ManageSystemListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        EmployeeService employeeService = EmployeeService.getInstance();
        context.setAttribute("employeeService", employeeService);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {
    }

    public void attributeAdded(HttpSessionBindingEvent sbe) {
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
    }
}
