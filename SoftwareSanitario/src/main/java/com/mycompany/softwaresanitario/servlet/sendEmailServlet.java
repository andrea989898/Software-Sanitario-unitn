/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.servlet;
import com.mycompany.softwaresanitario.mailer.Mailer;
import com.mycompany.softwaresanitario.commons.persistence.dao.UserDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.dao.UserDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author franc
 */
public class sendEmailServlet extends HttpServlet {

    private UserDAO userDao;

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
            out.println("<title>Servlet sendEmailServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet sendEmailServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String contextPath = getServletContext().getContextPath();
        
        
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        String to= request.getParameter("email");
        
        try {
            User user = userDao.getByEmail(to);
           
            if (user == null) {
                response.sendRedirect(response.encodeRedirectURL(contextPath +"index.html"));
                return;
            } 
        } catch (DAOException ex) {
            request.getServletContext().log("Impossible to retrieve the user", ex);
        }
        
        Random rnd = new Random ();
        char[] arr = new char[10];

        for (int i=0; i<10; i++) {
            int n = rnd.nextInt (36);
            arr[i] = (char) (n < 10 ? '0'+n : 'a'+n-10);
        }
        
        String password = new String(arr);
        
        String subject= new String("Recovery password\n");
        String msg= new String("New Password for the user: " + password);  
          
        Mailer.send(to, subject, msg);  
        try {
            /*out.println("<script type=\"text/javascript\">"+
            //"window.alert(\"Email inviata correttamente\")"+
            "</script>");*/
            User user = userDao.updatePassword(to, password);
            
            if (user != null) {
                response.sendRedirect(response.encodeRedirectURL(contextPath +"emailInviata.html"));
            } 
        } catch (DAOException ex) {
            Logger.getLogger(sendEmailServlet.class.getName()).log(Level.SEVERE, null, ex);
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
