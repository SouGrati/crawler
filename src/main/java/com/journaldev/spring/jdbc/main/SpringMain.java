package com.journaldev.spring.jdbc.main;

import java.io.IOException;
import java.math.BigDecimal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.code.geocoder.model.GeocodeResponse;
import com.journaldev.spring.jdbc.dao.UrlDAO;
import com.journaldev.spring.jdbc.model.Url;

public class SpringMain {

	public static void main(String[] args) throws IOException {
		//Get the Spring Context
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		//Get the UrlDAO Bean
		UrlDAO urlDAO = ctx.getBean("urlDAO", UrlDAO.class);

		urlDAO.runSql2("TRUNCATE recrd;");    
		Url pharma = new Url(0,"Grande Pharmacie d'Agadir","av. Mly Abdallah , imm. M1 Agadir",BigDecimal.valueOf(30.4207298),BigDecimal.valueOf(-9.5971989),"Tél.: 05 28 84 79 52",false);
		urlDAO.save(pharma);
		Document doc = Jsoup.connect("http://www.anahna.com/pharmacies-agadir-ca7-qa0.html").timeout(10*10000).get(); 
		Url url= new Url();
		Elements newsHeadlines = doc.select("h1");
		Elements questions = doc.select("div .right").select("p:eq(1)");
		Elements tels = doc.select("div.right").select("p:eq(2)");

		for (int i = 1; i <newsHeadlines.size(); i++) {
			Element elem = newsHeadlines.get(i);
			Element adress = questions.get(i-1);
			Element tele = tels.get(i-1);
			BigDecimal lattitude = BigDecimal.valueOf(30.4207298) ;
			BigDecimal longitude = BigDecimal.valueOf(30.4207298);
			boolean b=false;
			GeocodeResponse geocoderResponse=urlDAO.geo(adress.text());
			if(geocoderResponse.getResults().isEmpty()){
				geocoderResponse=urlDAO.geo(adress.text());

			}else{
				lattitude = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLat();
				longitude = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLng();
				System.out.println(lattitude);
			}

			if((elem.text().contains("Pharmacie"))&&(tele.text().contains("05"))){


				url.setUrl(elem.text());
				url.setAdresse(adress.text());
				url.setTel(tele.text());
				url.setGarde(b);
				url.setLattitude(lattitude);
				url.setLongitude(longitude);
				urlDAO.save(url);



			}
		}


	

ctx.close();
System.out.println("DONE");


}}