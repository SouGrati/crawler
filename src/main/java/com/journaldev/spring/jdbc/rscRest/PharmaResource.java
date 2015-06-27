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
        return urlDAO.getAllGarde() ;
    }
    
    @GET
    @Path("/plusproche/{lat}/{lng}")
    @Produces(MediaType.APPLICATION_JSON)
    public Url getPlusProche(@PathParam("lat")Double lat,@PathParam("lng")Double lng) {
    	int id=urlDAO.minDistance(lat,lng);
    	System.out.println("§§§>"+id+"---->"+urlDAO.getById(id).toString());
        return urlDAO.getById(id);
    }
    
   
}
