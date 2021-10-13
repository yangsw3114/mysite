package com.douzone.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


public class ContextLoadListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext sc = sce.getServletContext();
    	String contextConfigLocation = sc.getInitParameter("contextConfigLocation");
    	System.out.println("Application starts....:" +contextConfigLocation);
    }
    public void contextDestroyed(ServletContextEvent sce)  { 
    }

	
}
