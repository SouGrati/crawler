package com.journaldev.spring.jdbc.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
 



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 



import com.journaldev.spring.jdbc.dao.UrlDAO;
import com.journaldev.spring.jdbc.model.Url;
 
public class SpringMain {
 
    public static void main(String[] args) throws IOException {
        //Get the Spring Context
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
         
        //Get the UrlDAO Bean
        UrlDAO urlDAO = ctx.getBean("urlDAO", UrlDAO.class);
      //  Url emp1 = urlDAO.getById(1);
      //  System.out.println("Url Retrieved::"+emp1);
         
        //Run some tests for JDBC CRUD operations
       //  Url emp = new Url();
     urlDAO.runSql2("TRUNCATE recrd;");    
     urlDAO.processPage("Grande Pharmacie d'Agadir","av. Mly Abdallah , imm. M1 Agadir",BigDecimal.valueOf(30.4207298),BigDecimal.valueOf(-9.5971989),"Tél.: 05 28 84 79 52",false);
   //     int j=urlDAO.isUrlExists(20);
   //System.out.println("dfds"+j);
   /*     int rand = new Random().nextInt(1000);
        emp.setRecordID(rand);
        emp.setUrl("Pankajaha");
        
         
        //Create
        urlDAO.save(emp);
         
  /*      //Read
        Url emp1 = urlDAO.getById(rand);
        System.out.println("Url Retrieved::"+emp1);
         
        //Update
   //     emp.setRole("CEeO");
     //   UrlDAO.update(emp);
         
        //Get All
        List<Url> empList = urlDAO.getAll();
        System.out.println(empList);
         
        //Delete
   //   UrlDAO.deleteById(rand);
       */   
        //Close Spring Context
      //  boolean b=urlDAO.isPharmaGarde("pha");
        ctx.close();
        
        System.out.println("DONE");
    }
 

/*public static void isPharmaGarde(String pharmacie) throws IOException{
	boolean result=false;
	Document doc = Jsoup.connect("http://www.agadirbonsplans.com/pharmacie-de-garde-agadir/").timeout(10*1000).get(); 
	  
	   Elements newsGarde = doc.select("div .pharmaTitle");
	   for (int i = 1; i < newsGarde.size(); i++) {
		    Element elem = newsGarde.get(i); 
		    System.out.println("Value of element " + i + " = "+elem.text());
		    }
	 
	
}*/}