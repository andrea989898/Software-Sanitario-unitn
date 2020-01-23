/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.servlet;

import com.mycompany.softwaresanitario.commons.persistence.dao.ExamDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author franc
 */
public class confirmExamServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    DAOFactory daoFactory;
    
    
    public void init() throws ServletException {
        daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet confirmExamServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet confirmExamServlet at " + request.getContextPath() + "</h1>");
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
        ExamDAO examDao;
        try {
            examDao = daoFactory.getDAO(ExamDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for generalDoctor storage system", ex));
        }
        String id = request.getParameter("id");
        String result = request.getParameter("result");
        String type = request.getParameter("type");
        
        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }

        try {
            boolean exam = examDao.updateExam(Integer.parseInt(id), result);
            
            if (exam) {
                //request.getSession().setAttribute("user", user);
                if(type.contains("ssp")) response.sendRedirect(response.encodeRedirectURL(contextPath + "restricted/homePageSsp.html"));
                else    response.sendRedirect(response.encodeRedirectURL(contextPath + "restricted/homeSpecialist.html"));
            }else{
                response.sendRedirect(response.encodeRedirectURL(contextPath + "errorPage.html"));
            }
        } catch (DAOException ex) {
            //TODO: log exception
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
