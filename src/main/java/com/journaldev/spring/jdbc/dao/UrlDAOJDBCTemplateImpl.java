package com.journaldev.spring.jdbc.dao;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
 













import com.journaldev.spring.jdbc.model.Url;
 
public class UrlDAOJDBCTemplateImpl implements UrlDAO {
 
    private DataSource dataSource;
 
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
     
    
    public void save(Url url) {
        String query = "insert into recrd (recordID, url,adresse,tel,garde) values (?,?,?,?,?)";
         
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
         
        Object[] args = new Object[] {url.getRecordID(), url.getUrl(), url.getAdresse(),url.getTel(),url.getGarde()};
         
        int out = jdbcTemplate.update(query, args);
      
         
        if(out !=0){
            //System.out.println("Url saved with id="+url.getRecordID());
        }else ;//System.out.println("Url save failed with id="+url.getRecordID());
    }
 
    
    public Url getById(int recordID) {
        final String query = "select recordID, url from recrd where recordID = ?";
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //using RowMapper anonymous class, we can create a separate RowMapper for reuse
        //RowMapper anonymous class implementation to map the ResultSet data
         //to Url bean object in queryForObject() method.
        Url url = jdbcTemplate.queryForObject
        		(query, new Object[]{recordID}, new RowMapper<Url>(){
 
            public Url mapRow(ResultSet rs, int rowNum) throws SQLException{
                Url url = new Url();
                url.setRecordID(rs.getInt("recordID"));
                url.setUrl(rs.getString("url"));
                Object[] args = new Object[] {url.getRecordID(), url.getUrl()};
                
                /*   int out = jdbcTemplate.update(query, args);
               
                
              if(out ==0){
                    System.out.println("Url saved with id="+url.getRecordID());
                }else System.out.println("Url save failed with id="+url.getRecordID());
           */
                return url;
            }});
    
     
    
        return url;
    }
  
	public int isUrlExists(String url) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	        String sql = "SELECT count(*) FROM recrd WHERE url = ?";
	        int res	=0;
		int count = jdbcTemplate.queryForObject(sql, new Object[] { url }, Integer.class);
	 
		if (count > 0) 
			 res=count;
		
		return res;
	  }
		
	
 
   
 /*   public void update(Url Url) {
        String query = "update Url set name=?, role=? where id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Object[] args = new Object[] {Url.getName(), Url.getRole(), Url.getId()};
         
        int out = jdbcTemplate.update(query, args);
        if(out !=0){
            System.out.println("Url updated with id="+Url.getId());
        }else System.out.println("No Url found with id="+Url.getId());
    }
 
    
    public void deleteById(int id) {
 
        String query = "delete from Url where id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
         
        int out = jdbcTemplate.update(query, id);
        if(out !=0){
            System.out.println("Url deleted with id="+id);
        }else System.out.println("No Url found with id="+id);
    }
 
   */
    public List<Url> getAll() {
        String query = "select recordID, url,adresse from recrd";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Url> empList = new ArrayList<Url>();
 
        List<Map<String,Object>> empRows = jdbcTemplate.queryForList(query);
         
        for(Map<String,Object> empRow : empRows){
            Url emp = new Url();
            emp.setRecordID(Integer.parseInt(String.valueOf(empRow.get("recordID"))));
            emp.setUrl(String.valueOf(empRow.get("url")));
            emp.setAdresse(String.valueOf(empRow.get("adresse")));
            empList.add(emp);
        }
        return empList;
    }


	public void update(Url url) {
		// TODO Auto-generated method stub
		
	}


	public void deleteById(int recordID) {
		// TODO Auto-generated method stub
		
	}
	public void runSql2(String sql){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                       
        jdbcTemplate.execute(sql);
 
         System.out.println("Trancate DONE!");
             		
	}
	public boolean isPharmaGarde(String pharmacie) throws IOException{
		boolean result=false;
		Document doc = Jsoup.connect("http://www.agadirbonsplans.com/pharmacie-de-garde-agadir/").timeout(10*1000).get(); 
		int i = 0;
		   Elements newsGarde = doc.select("div .pharmaTitle");
		   for (int j= 0; j < newsGarde.size(); j++){
			   Element garde = newsGarde.get(j); 
		  
		   do{
			  
			   if(!pharmacie.equals(garde.text())){
			   result=false;	
			   
			   System.out.println(pharmacie +"VS"+garde.text());}
			   else {result=true;return result; }
			   }while((result==true) || (j >newsGarde.size())); }
		   return result;
		
	}
	public void processPage(String pharma,String adresse,String tel,boolean garde) throws IOException{
		//check if the given URL is already in database
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		UrlDAO urlDAO = ctx.getBean("urlDAO", UrlDAO.class);
		
		
		int n=urlDAO.isUrlExists(pharma);
	//    System.out.println("nb d'enregistrement"+n);
	
	if(n==1){
		// System.out.println("Deja exsts");
		 
 			
		}else{
		Url url= new Url();
		
        url.setUrl(pharma);
        url.setAdresse(adresse);
        url.setTel(tel);
        url.setGarde(garde);
		urlDAO.save(url);
		
	//	System.out.println("Url saved::"+url.getUrl());
		  Document doc = Jsoup.connect("http://www.anahna.com/pharmacies-agadir-ca7-qa0.html").timeout(10*1000).get(); 
		   
		  Elements newsHeadlines = doc.select("h1");
		  Elements questions = doc.select("div .right").select("p:eq(1)");
		  Elements tels = doc.select("div.right").select("p:eq(2)");
		 
		   for (int i = 1; i < newsHeadlines.size(); i++) {
		    Element elem = newsHeadlines.get(i);
		   Element adress = questions.get(i-1);
		    Element tele = tels.get(i-1);
		    boolean b=urlDAO.isPharmaGarde(elem.text());
		   
		  if((elem.text().contains("Pharmacie"))&&(tele.text().contains("05"))){
		  
		 processPage(elem.text(),adress.text(),tele.text(),b);
		   // System.out.println("Value of element " + i + " = "+elem.text()+ques.text());
		    
		}}}
		 
			//store the URL to database to avoid parsing again
		/*	sql = "INSERT INTO  `Crawler`.`Record` " + "(`URL`) VALUES " + "(?);";
			PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, URL);
			stmt.execute();
 
			//get useful information
			Document doc = Jsoup.connect("http://www.mit.edu/").get();
 			if(doc.text().contains("research")){
				System.out.println(URL);
			}
 		
			//get all links and recursively call the processPage method
			Elements questions = doc.select("a[href]");
			for(Element link: questions){
				if(link.attr("href").contains("mit.edu"))
					processPage(link.attr("abs:href"));
				
			}
		}*/
	
 
}


	public List<Url> getAllgarde() {
		String query = "select recordID, url,adresse from recrd where garde=1 ";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Url> gardeList = new ArrayList<Url>();
 
        List<Map<String,Object>> empRows = jdbcTemplate.queryForList(query);
         
        for(Map<String,Object> empRow : empRows){
            Url emp = new Url();
            emp.setRecordID(Integer.parseInt(String.valueOf(empRow.get("recordID"))));
            emp.setUrl(String.valueOf(empRow.get("url")));
            emp.setAdresse(String.valueOf(empRow.get("adresse")));
            gardeList.add(emp);
        }
        return gardeList;
	}
	
	
    public Url recherche(String r) {
        final String query = "select recordID, url from recrd where url like '%"+r+"%'";
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Url url = jdbcTemplate.queryForObject
        		(query, new Object[]{r}, new RowMapper<Url>(){
 
        public Url mapRow(ResultSet rs, int rowNum) throws SQLException{
                Url url = new Url();
                url.setRecordID(rs.getInt("recordID"));
                url.setUrl(rs.getString("url"));
                Object[] args = new Object[] {url.getRecordID(), url.getUrl()};
                return url;
            }});
    
     
    
        return url;
    }

	

	}