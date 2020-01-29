/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.servlet;

import com.mycompany.softwaresanitario.commons.persistence.dao.UserDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;


/**
 *
 * @author franc
 */
public class uploadImageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    UserDAO userDao;
    DAOFactory daoFactory;
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 60 * 1024;
    private int maxMemSize = 8 * 1024;
    private File file ;
    User user = null;

    
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
        filePath = getServletContext().getInitParameter("file-upload");
        String contextPath = getServletContext().getContextPath();
        filePath = getServletContext().getRealPath(filePath);
        filePath = filePath.substring(0, filePath.length() - 45);
        filePath =  filePath +"\\src\\main\\webapp\\images\\avatar\\";
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet uploadImageServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet uploadImageServlet at " + request.getContextPath() + "</h1>");
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
        
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        
        boolean updateImage = false;
        
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);

        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\temp"));
        
         // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );
        
        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
              contextPath += "/";
        }
                  
        
        try { 
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            while ( i.hasNext () ) {
               FileItem fi = (FileItem)i.next();
               if ( !fi.isFormField () ) {
                  // Get the uploaded file parameters
                  String fieldName = fi.getFieldName();
                  String fileName = fi.getName();
                  String contentType = fi.getContentType();
                  boolean isInMemory = fi.isInMemory();
                  long sizeInBytes = fi.getSize();
                  Random random = new Random();
                  String randomNumber = Integer.toString(random.nextInt(999));
                  

                  // Write the file
                  if( fileName.lastIndexOf("\\") >= 0 ) {
                     file = new File( filePath + user.getCf() + randomNumber + fileName.substring( fileName.lastIndexOf("\\"))) ;
                  } else {
                     file = new File( filePath + user.getCf() + randomNumber + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                  }
                  fi.write( file ) ;
                  
                  updateImage = userDao.updateImage(user.getCf(), user.getCf() + randomNumber + fileName);
                  
                  if(updateImage){
                      request.getSession().setAttribute("notUploadImage", "no");
                      response.sendRedirect(response.encodeRedirectURL(contextPath + "restricted/homePage.html"));
                  }
                  else response.sendRedirect(response.encodeRedirectURL(contextPath + "error.html"));
                  
               }
            }
         } catch(Exception ex) {
            System.out.println(ex);
            request.getSession().setAttribute("notUploadImage", "yes");
            response.sendRedirect(response.encodeRedirectURL(contextPath + "restricted/homePage.html"));
         }
        
        
        
        /*try {
            updateImage = userDao.getByCode(idpatient);
        } catch (DAOException ex) {
            Logger.getLogger(newExamServlet.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        
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
