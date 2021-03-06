/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.servlet;

import com.mycompany.softwaresanitario.commons.persistence.dao.SspDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.dao.UserDAO;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Ssp;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PC Andrea
 */
public class changePasswordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     * 
     */
    
    private UserDAO userDao;
    private SspDAO sspDao;
    
    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            userDao = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system", ex);
        }
        try {
            sspDao = daoFactory.getDAO(SspDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for ssp storage system", ex);
        }
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet changePassowordServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet changePassowordServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String password = request.getParameter("password");
        User user = null;
        Ssp ssp = null;
        
        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
       
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            user = (User) session.getAttribute("user");
            ssp = (Ssp) session.getAttribute("ssp");
        }else{
            request.getSession().setAttribute("newpassword", "nope");
            request.getSession().setAttribute("error", "nope");
            response.sendRedirect(response.encodeRedirectURL(contextPath + "index.html"));
        }
        
        if(password == ""){
            request.getSession().setAttribute("newpassword", "nope");
            request.getSession().setAttribute("error", "nope");
            response.sendRedirect(contextPath + "index.html");
            return;
        }
        
        try {
            Ssp ssp_ = null;
            User user_ = null;
            if(user != null) user_ = userDao.updatePassword(user.getEmail(), password);
            if(ssp != null)   ssp_ = sspDao.updatePassword(ssp.getEmail(), password);
            request.getSession().setAttribute("newpassword", "nope");
            request.getSession().setAttribute("error", "nope");
            if (user_ == null && ssp_ == null) {
                response.sendRedirect(response.encodeRedirectURL(contextPath + "index.html"));
            } else if(ssp_ == null){ 
                request.getSession().setAttribute("changePasswordUserSuccess", "yes");
                response.sendRedirect(response.encodeRedirectURL(contextPath + "restricted/homePage.html"));
            } else if(user_ == null){
                request.getSession().setAttribute("changePasswordSspSuccess", "yes");
                response.sendRedirect(response.encodeRedirectURL(contextPath + "restricted/homePageSsp.html"));
            }
        } catch (DAOException ex) {
            request.getServletContext().log("Impossible to retrieve the user", ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}