/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.servlet;

import com.mycompany.softwaresanitario.commons.persistence.dao.DrugDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.RecipeDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
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
@WebServlet(name = "NewRecipeServlet", urlPatterns = {"/NewRecipeServlet"})
public class NewRecipeServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewRecipeServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewRecipeServlet at " + request.getContextPath() + "</h1>");
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
        
        RecipeDAO recipeDao;
        try {
            recipeDao = daoFactory.getDAO(RecipeDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for generalDoctor storage system", ex));
        }
        DrugDAO drugDao;
        try {
            drugDao = daoFactory.getDAO(DrugDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for generalDoctor storage system", ex));
        }
        String[] drugs;
        drugs = new String[5];
        
        String idpatient = request.getParameter("patient");
        String analysis = request.getParameter("analysis");
        if(request.getParameter("drug-1") != "false")
            drugs[0] = request.getParameter("drug-1");
        if(request.getParameter("drug-2") != "false")
            drugs[1] = request.getParameter("drug-2");
        if(request.getParameter("drug-3") != "false")    
            drugs[2] = request.getParameter("drug-3");
        if(request.getParameter("drug-4") != "false")
            drugs[3] = request.getParameter("drug-4");
        if(request.getParameter("drug-5") != "false")
            drugs[4] = request.getParameter("drug-5");
        
        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }

        try {
            int x=0;
            boolean drug = false;
            boolean recipe = recipeDao.newrecipe(idpatient, analysis);
            
            if (recipe) {
                for(int j=0;j<5;j++){
                if(drugs[j] != null){ 
                    drug = drugDao.newdrugforrecipe(drugs[j]);}
                    if (drug) x++;
                    drug = false;
                }
                if(x==5) response.sendRedirect(response.encodeRedirectURL(contextPath + "restricted/homeGeneralDoctor.html"));
                else response.sendRedirect(response.encodeRedirectURL(contextPath + "errorPage.html"));
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
