/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.listener;


import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.jdbc.JDBCDAOFactory;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author franc
 */
public class WebAppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String dburl = sce.getServletContext().getInitParameter("dburl");
        try {
            JDBCDAOFactory.configure(dburl);
            DAOFactory daoFactory = JDBCDAOFactory.getInstance();
            sce.getServletContext().setAttribute("daoFactory", daoFactory);
        } catch (DAOFactoryException ex) {
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DAOFactory daoFactory = (DAOFactory) sce.getServletContext().getAttribute("daoFactory");
        if (daoFactory != null) {
            daoFactory.shutdown();
        }
        daoFactory = null;   
    }
}
