/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.servlet;

import com.mycompany.softwaresanitario.PDFmanagement.TicketPDF;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author franc
 */
public class PDFservlet extends HttpServlet {

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
            out.println("<title>Servlet PDFservlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PDFservlet at " + request.getContextPath() + "</h1>");
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
        
        String pdfFolder = getServletContext().getInitParameter("pdfFolder");
        if (pdfFolder == null) {
            throw new ServletException("PDFs folder not configured");
        }
        
        String contextPath = getServletContext().getContextPath();
        
        System.out.println(pdfFolder);
        //pdfFolder = contextPath + pdfFolder;
        pdfFolder = getServletContext().getRealPath(pdfFolder);
        pdfFolder = pdfFolder.substring(0, pdfFolder.length() - 42);
        pdfFolder =  pdfFolder +"src\\main\\webapp\\pdfs";
        
        String type = request.getParameter("type");
        if(type.equals("ticket")){
            TicketPDF.generateTicketPDF(request.getParameter("id"), daoFactory, request, response, pdfFolder);
            
                //doc.getDocumentCatalog();
                //System.out.println(doc);
                //doc.save(new File("C:\\Users\\franc\\Desktop\\Software-Sanitario-unitn\\SoftwareSanitario\\src\\main\\webapp\\pdfs", "ticket-" + request.getParameter("id") + "-" + Calendar.getInstance().getTimeInMillis() + ".pdf"));
            
                /*response.setContentType("application/pdf");
                //response.setHeader("Content-disposition", "inline; filename='shopping-lists.pdf'");
                response.setHeader("Content-disposition", "attachment; filename='ticket.pdf'");
                doc.save(response.getOutputStream());   */  
        }
        
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        response.sendRedirect(response.encodeRedirectURL(contextPath + "restricted/homePage.html"));
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
