/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.servlet;

import com.mycompany.softwaresanitario.commons.persistence.dao.ExamDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.ExaminationDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.UserDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import com.mycompany.softwaresanitario.mailer.Mailer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PC Andrea
 */
@WebServlet(name = "newExamServlet", urlPatterns = {"/newExamServlet"})
public class newExamServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     */
    DAOFactory daoFactory;
    ExamDAO examDao;
    UserDAO userDao;
    
    
    public void init() throws ServletException {
        daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            userDao = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system", ex);
        }
        try {
            examDao = daoFactory.getDAO(ExamDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for generalDoctor storage system", ex));
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
            out.println("<title>Servlet newExamServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet newExamServlet at " + request.getContextPath() + "</h1>");
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
        
        User userPatient = null;
        
        
        String prescriptor = request.getParameter("prescriptor");
        String recall = request.getParameter("recall");
        String analysis = request.getParameter("analysis");
        String time = request.getParameter("time");
        String date = request.getParameter("date");
        String idpatient = request.getParameter("patientExams");
        String iddoctor = request.getParameter("doctor");
        //System.out.println(recall);
        idpatient = idpatient.replaceAll("\\s+$", "");
        try {
            userPatient = userDao.getByCode(idpatient);
        } catch (DAOException ex) {
            Logger.getLogger(newExamServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String subject= new String("New exam\n");
        String patientName = userPatient.getName().replaceAll("\\s+$", "");
        String patientSurname = userPatient.getSurname().replaceAll("\\s+$", "");
        String msg= new String("Ciao "+ patientName + " " + patientSurname + " ti è stato prescritto un nuovo esame. Entra nella tua area riservata per visualizzarlo.");  
        //System.out.print(password);
          
        Mailer.send(userPatient.getEmail(), subject, msg);  
        
        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }

        try {
            boolean exam = examDao.newExam(date, time, idpatient, iddoctor, analysis, recall, prescriptor);
            
            if (exam) {
                //request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("creationExamSuccess", patientName + " " + patientSurname);
                response.sendRedirect(response.encodeRedirectURL(contextPath + "restricted/homeGeneralDoctor.html"));
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
