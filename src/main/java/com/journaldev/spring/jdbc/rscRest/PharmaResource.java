package com.journaldev.spring.jdbc.rscRest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.spring.jdbc.dao.UrlDAO;
import com.journaldev.spring.jdbc.model.Url;

/**
 * Root resource (exposed at "myresource" path)
 */

@Path("pharmaresource")
public class PharmaResource {
	
	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
    UrlDAO urlDAO = ctx.getBean("urlDAO", UrlDAO.class);
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    */
    @GET
    //@Produces("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Url> getAll() {
        return urlDAO.getAll() ;
    }
    
    @GET
    @Path("/garde")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Url> getAllgarde() {
    	System.out.println("GADE");
        return urlDAO.getAllgarde() ;
    }
    
   
}
