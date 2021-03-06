/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.filters;

import com.mycompany.softwaresanitario.commons.persistence.dao.CityDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.ExamDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.ExaminationDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.GeneralDoctorDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.PatientDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.PrescriptionDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.RecipeDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.SpecialistDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.TicketDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.UserDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.entities.City;
import com.mycompany.softwaresanitario.commons.persistence.entities.Exam;
import com.mycompany.softwaresanitario.commons.persistence.entities.Examination;
import com.mycompany.softwaresanitario.commons.persistence.entities.GeneralDoctor;
import com.mycompany.softwaresanitario.commons.persistence.entities.Patient;
import com.mycompany.softwaresanitario.commons.persistence.entities.Prescription;
import com.mycompany.softwaresanitario.commons.persistence.entities.Recipe;
import com.mycompany.softwaresanitario.commons.persistence.entities.Specialist;
import com.mycompany.softwaresanitario.commons.persistence.entities.Ssp;
import com.mycompany.softwaresanitario.commons.persistence.entities.Ticket;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import com.mycompany.softwaresanitario.manipulate.ManipulateExam;
import com.mycompany.softwaresanitario.manipulate.ManipulateExaminations;
import com.mycompany.softwaresanitario.manipulate.ManipulateTickets;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
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
public class PatientFilter implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public PatientFilter() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("PatientFilter:DoBeforeProcessing");
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
        
        GeneralDoctorDAO generalDoctorDao = null;
        try {
            generalDoctorDao = daoFactory.getDAO(GeneralDoctorDAO.class);
            request.setAttribute("GeneralDoctorDao", generalDoctorDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for generalDoctor storage system", ex));
        }
        
        SpecialistDAO specialistDao = null;
        try {
            specialistDao = daoFactory.getDAO(SpecialistDAO.class);
            request.setAttribute("Specialist", specialistDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for specialistDAO storage system", ex));
        }
        
        RecipeDAO recipeDao = null;
        try {
            recipeDao = daoFactory.getDAO(RecipeDAO.class);
            request.setAttribute("RecipeDao", recipeDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for recipes storage system", ex));
        }
        
        PrescriptionDAO prescriptionDao = null;
        try {
            prescriptionDao = daoFactory.getDAO(PrescriptionDAO.class);
            request.setAttribute("PrescriptionDao", prescriptionDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for prescriptions storage system", ex));
        }
        
        TicketDAO ticketDao = null;
        try {
            ticketDao = daoFactory.getDAO(TicketDAO.class);
            request.setAttribute("TicketDao", ticketDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for tickets storage system", ex));
        }
        
        ExaminationDAO examinationDao = null;
        try {
            examinationDao = daoFactory.getDAO(ExaminationDAO.class);
            request.setAttribute("ExaminationDao", examinationDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for examinations storage system", ex));
        }
        
        ExamDAO examDao = null;
        try {
            examDao = daoFactory.getDAO(ExamDAO.class);
            request.setAttribute("ExamDao", examDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for exams storage system", ex));
        }
        
        String contextPath = request.getServletContext().getContextPath();
        if (contextPath.endsWith("/")) {
            contextPath = contextPath.substring(0, contextPath.length() - 1);
        }
        request.setAttribute("contextPath", contextPath);
        
        User user = null;
        Ssp ssp = null;
        
        
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            user = (User) session.getAttribute("user");
            ssp = (Ssp) session.getAttribute("ssp");
        }
        
        if (user == null) {
            ((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL(contextPath + "/index.html"));
            return;
        }
        
        /*if (ssp != null) {
            session.setAttribute("ssp", null);
            ssp = null;
            ((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL(contextPath + "/index.html"));
            return;
        }*/
        
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
                //System.out.println(user2.getCf() + avatarPath);
                request.setAttribute("avatarPath", avatarPath);
            }
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get user or shopping lists", ex));
        }
        
        try {
            boolean isAGeneralDoctor = generalDoctorDao.isAGeneralDoctor(user.getCf());
            GeneralDoctor generalDoctor = generalDoctorDao.getByCode(user.getCf());
            List<User> generalDoctors = generalDoctorDao.getAllGeneralDoctors(user.getCf(), generalDoctor.getCf(), user.getCity_id());
            if(generalDoctors != null)   request.setAttribute("AllGeneralDoctor", generalDoctors);
            if(isAGeneralDoctor){
                request.setAttribute("generalDoctor", "he/she is a doctor");
                session.setAttribute("generalDoctorSession", "yes");
            }
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get user or generalDoctor", ex));
        }
        
        try {
            Specialist specialist = specialistDao.getByCode(user.getCf());
            //System.out.println(specialist.getCf());
            if(specialist != null){
                request.setAttribute("specialist", specialist);
                session.setAttribute("specialistSession", "yes");
            }
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get user or specialist", ex));
        }
        
        //System.out.println(request.getAttribute("patient"));
        List<Recipe> recipes;
        try {
            recipes = recipeDao.getAllBySSDPatient(user.getCf());
            if(recipes.size() > 0)   request.setAttribute("recipes", recipes);
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get user or prescription", ex));
        }
        
        ArrayList<Prescription> prescriptions = new ArrayList();
        try {
            prescriptions = prescriptionDao.getPrescriptions(user.getCf());
            if(prescriptions.size() > 0)   request.setAttribute("prescriptions", prescriptions);
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get user or prescription", ex));
        }
        
        try {
            List<Ticket> screamTickets = new ArrayList<Ticket>();
            List<Ticket> tickets = ticketDao.getTickets(user.getCf());
            System.out.println(tickets.size());
            if(tickets.size()>0){ 
                request.setAttribute("tickets", tickets);
                screamTickets = ManipulateTickets.ScreamTicketByPaid(tickets);
                if(screamTickets.size()>0)  request.setAttribute("screamTickets", screamTickets);
            }
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get tickets", ex));
        }
        
        try {
            List<Examination> screamExaminations = new ArrayList<Examination>();
            List<Examination> examinations = examinationDao.getExaminations(user.getCf());
            if(examinations.size()>0)  {
                screamExaminations = ManipulateExaminations.ScreamExaminationsByDate(examinations);
                request.setAttribute("examinations", examinations);
                if(screamExaminations.size()>0)    request.setAttribute("screamExaminations", screamExaminations);
            }
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get examinations", ex));
        }
        
        try {
            List<Exam> screamExams = new ArrayList<Exam>();
            List<Exam> exams = examDao.getExams(user.getCf());
            if(exams.size()>0) {
                screamExams = ManipulateExam.ScreamExamByDate(exams);
                request.setAttribute("exams", exams);
                if(screamExams.size()>0)    request.setAttribute("screamExams", screamExams);
            }
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get exams", ex));
        }
        
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("PatientFilter:DoAfterProcessing");
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
            log("PatientFilter:doFilter()");
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
                log("PatientFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("PatientFilter()");
        }
        StringBuffer sb = new StringBuffer("PatientFilter(");
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
