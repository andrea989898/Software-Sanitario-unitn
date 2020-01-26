/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.filters;

import com.mycompany.softwaresanitario.commons.persistence.dao.CityDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.DoctorDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.DrugDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.ExamDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.ExaminationDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.PatientDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.SpecialistDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.SspDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.UserDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.entities.City;
import com.mycompany.softwaresanitario.commons.persistence.entities.Doctor;
import com.mycompany.softwaresanitario.commons.persistence.entities.Drug;
import com.mycompany.softwaresanitario.commons.persistence.entities.Exam;
import com.mycompany.softwaresanitario.commons.persistence.entities.Examination;
import com.mycompany.softwaresanitario.commons.persistence.entities.Patient;
import com.mycompany.softwaresanitario.commons.persistence.entities.Specialist;
import com.mycompany.softwaresanitario.commons.persistence.entities.Ssp;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author franc
 */
public class GeneralDoctorFilter implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public GeneralDoctorFilter() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException{
        if (debug) {
            log("GeneralDoctorFilter:DoBeforeProcessing");
        }

        DAOFactory daoFactory = (DAOFactory) request.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new RuntimeException(new ServletException("Impossible to get dao factory for user storage system"));
        }
        
        UserDAO userDao = null;
        try {
            userDao = daoFactory.getDAO(UserDAO.class);
            request.setAttribute("userDao", userDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get dao factory for user storage system", ex));
        }
        
        PatientDAO patientDao = null;
        try {
            patientDao = daoFactory.getDAO(PatientDAO.class);
            request.setAttribute("patientDao", patientDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for shopping list storage system", ex));
        }
        
        CityDAO cityDao = null;
        try {
            cityDao = daoFactory.getDAO(CityDAO.class);
            request.setAttribute("cityDao", cityDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for shopping list storage system", ex));
        }
        
        ExamDAO examDao = null;
        try {
            examDao = daoFactory.getDAO(ExamDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for exams storage system", ex));
        }
        
        ExaminationDAO examinationDao = null;
        try {
            examinationDao = daoFactory.getDAO(ExaminationDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for examinations storage system", ex));
        }
        
        SspDAO sspDao = null;
        try {
            sspDao = daoFactory.getDAO(SspDAO.class);
            request.setAttribute("sspDao", sspDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get dao factory for ssp storage system", ex));
        }
        
        DoctorDAO doctorDao = null;
        try {
            doctorDao = daoFactory.getDAO(DoctorDAO.class);
            request.setAttribute("doctorDao", doctorDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get dao factory for user storage system", ex));
        }
        
        SpecialistDAO specialistDao = null;
        try {
            specialistDao = daoFactory.getDAO(SpecialistDAO.class);
            request.setAttribute("Specialist", specialistDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for specialistDAO storage system", ex));
        }
        
        DrugDAO drugDao = null;
        try {
            drugDao = daoFactory.getDAO(DrugDAO.class);
            request.setAttribute("drugDao", drugDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for drugs storage system", ex));
        }
        
        
        String contextPath = request.getServletContext().getContextPath();
        if (contextPath.endsWith("/")) {
            contextPath = contextPath.substring(0, contextPath.length() - 1);
        }
        request.setAttribute("contextPath", contextPath);
        
        User user = null;
        String isGeneral = null;
        
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            user = (User) session.getAttribute("user");
            isGeneral = (String) session.getAttribute("generalDoctorSession");
        }
        
        if (user == null) {
            ((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL(contextPath + "/index.html"));
            return;
        }
        
        if(isGeneral == null) {
            ((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL(contextPath + "/restricted/homePage.html"));
            return;
        }
        
        try {
            User user2 = userDao.getByCode(user.getCf());
            Patient patient = patientDao.getByCode(user.getCf());
            if(patient != null){
                User generaldoctorpatient = userDao.getByCode(patient.getGeneralDoctorCf());
                //System.out.println(user.getBirth_city_id() + " " + user.getCity_id());
                City birth_city_Patient = cityDao.getByCode(user.getBirth_city_id());
                City city_Patient = cityDao.getByCode(user.getCity_id());
                request.setAttribute("patient", patient);
                request.setAttribute("generaldoctorpatient", generaldoctorpatient);
                request.setAttribute("birth_city_Patient", birth_city_Patient);
                request.setAttribute("city_Patient", city_Patient);
                String avatarPath = "../images/avatar/" + user2.getAvatarPath();
                request.setAttribute("avatarPath", avatarPath);
            }
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the patient", ex));
        }
        
        List<User> patients = patientDao.getAllByDoctor(user.getCf());
        List<Examination> examinations = null;
        List<Exam> exams = null;
        if(patients != null){
            for(User p:patients){
                
                try {
                    examinations = examinationDao.getExaminations(p.getCf());
                } catch (DAOException ex) {
                    throw new RuntimeException(new ServletException("Impossible to get examinations", ex));
                }
               
                p.setExaminations(examinations);
                
                try {
                    exams = examDao.getExams(p.getCf());
                } catch (DAOException ex) {
                    throw new RuntimeException(new ServletException("Impossible to get exams", ex));
                }
                
                p.setExams(exams);
                
            }
            
            request.setAttribute("patients", patients);
        }
        
        List<Doctor> doctors = null;
        try {
            doctors = doctorDao.getAllSpecialist();
        } catch (DAOException ex) {
            Logger.getLogger(GeneralDoctorFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(doctors != null)
            request.setAttribute("doctors", doctors);
        
        Ssp ssp = null;
        try {
            ssp = sspDao.getByCity(user.getCity_id());
        } catch (DAOException ex) {
            Logger.getLogger(GeneralDoctorFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(ssp != null)
            request.setAttribute("ssp", ssp);
        
        ArrayList<Drug> drugs = new ArrayList();
        try {
            drugs = drugDao.getAllDrugs();
            if(drugs.size() > 0)   request.setAttribute("drugs", drugs);
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get user or generalDoctor", ex));
        }
        
        try {
            Specialist specialist = specialistDao.getByCode(user.getCf());
            //System.out.println(specialist.getCf());
            if(specialist != null)      request.setAttribute("specialist", specialist);
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get user or specialist", ex));
        }
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("GeneralDoctorFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        if (debug) {
            log("GeneralDoctorFilter:doFilter()");
        }
        
        doBeforeProcessing(request, response);
        
        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }
        
        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("GeneralDoctorFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("GeneralDoctorFilter()");
        }
        StringBuffer sb = new StringBuffer("GeneralDoctorFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
