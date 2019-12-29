/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.servlet;

import com.mycompany.softwaresanitario.XLSmanagement.PrescriptionsRecipeXLS;
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
public class XlsServlet extends HttpServlet {

    
    DAOFactory daoFactory;
    
    
    public void init() throws ServletException {
        daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet XlsServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet XlsServlet at " + request.getContextPath() + "</h1>");
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
        String xlsFolder = getServletContext().getInitParameter("xlsFolder");
        if (xlsFolder == null) {
            throw new ServletException("XLSs folder not configured");
        }
        
        String contextPath = getServletContext().getContextPath();
        xlsFolder = getServletContext().getRealPath(xlsFolder);
        xlsFolder = xlsFolder.substring(0, xlsFolder.length() - 42);
        xlsFolder =  xlsFolder +"\\src\\main\\webapp\\xls";
        
        PrescriptionsRecipeXLS.generatePrescriptionsXLS(request.getParameter("date"), request.getParameter("province"), daoFactory, request, response, xlsFolder);
        
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        response.sendRedirect(response.encodeRedirectURL(contextPath + "restricted/homePageSsp.html"));
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
        processRequest(request, response);
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
