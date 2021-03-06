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
import com.mycompany.softwaresanitario.commons.persistence.entities.Ssp;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author franc
 */
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        
        
        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }

        try {
            User user = userDao.getByEmailAndPassword(email, password);
            
            if (user == null) {
                Ssp ssp = sspDao.getByEmailAndPassword(email, password);
                if(ssp == null){
                    //out.println("<script type=\"text/javascript\">");
                    //out.println("alert('User or password incorrect');");
                    //out.println("location='" + contextPath + "index.html';");
                    //out.println("</script>"); 
                    
                    request.getSession().setAttribute("error", "yes");
                    request.getSession().setAttribute("newpassword", "nope");
                    response.sendRedirect(response.encodeRedirectURL(contextPath + "index.html"));

                }else{
                    if(request.getParameter("remember")!=null){
                        String remember = request.getParameter("remember");
                        Cookie cEmail = new Cookie("cookemail", email.trim());
                        Cookie cPassword = new Cookie("cookpass", password.trim());
                        Cookie cRemember = new Cookie("cookrem", remember.trim());
                        cEmail.setMaxAge(60 /** 60 * 24 * 15*/);// 15 days
                        cPassword.setMaxAge(60 /** 60 * 24 * 15*/);
                        cRemember.setMaxAge(60 /** 60 * 24 * 15*/);
                        response.addCookie(cEmail);
                        response.addCookie(cPassword);
                        response.addCookie(cRemember);
                    }
                    
                    //request.getSession().setAttribute("error", '0');
                    request.getSession().setAttribute("newpassword", "nope");
                    request.getSession().setAttribute("error", "nope");
                    request.getSession().setAttribute("ssp", ssp);
                    response.sendRedirect(response.encodeRedirectURL(contextPath + "restricted/homePageSsp.html"));
                }
                
                
                //processRequest(request, response);
            } else {
                if(request.getParameter("remember")!=null){
                    String remember = request.getParameter("remember");
                    Cookie cEmail = new Cookie("cookemail", email.trim());
		    Cookie cPassword = new Cookie("cookpass", password.trim());
                    Cookie cRemember = new Cookie("cookrem", remember.trim());
		    cEmail.setMaxAge(60 /** 60 * 24 * 15*/);// 15 days
		    cPassword.setMaxAge(60 /** 60 * 24 * 15*/);
		    cRemember.setMaxAge(60 /** 60 * 24 * 15*/);
                    response.addCookie(cEmail);
		    response.addCookie(cPassword);
                    response.addCookie(cRemember);
                }
                
                //request.getSession().setAttribute("error", '0');
                request.getSession().setAttribute("newpassword", "nope");
                request.getSession().setAttribute("error", "nope");
                request.getSession().setAttribute("user", user);
                response.sendRedirect(response.encodeRedirectURL(contextPath + "restricted/homePage.html"));

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
