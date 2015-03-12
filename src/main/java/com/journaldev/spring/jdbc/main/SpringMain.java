package com.journaldev.spring.jdbc.main;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.spring.jdbc.dao.UrlDAO;

/* suppression des methodes non utilisees*/
public class SpringMain {

	public static void main(String[] args) throws IOException {
		// Get the Spring Context
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring.xml");
		// Get the UrlDAO Bean
		UrlDAO urlDAO = ctx.getBean("urlDAO", UrlDAO.class);

		urlDAO.runSql2("TRUNCATE Recrd;");
		// Parcourir la page et inserer les donnees dans la BD
		urlDAO.processPage(urlDAO);
		// Recuperer les Pharmacies d'agadir
		ArrayList<String> allPharma = new ArrayList<String>();
		ArrayList<String> allPharmaGarde = new ArrayList<String>();
		ArrayList<String> pharmaCommune = new ArrayList<String>();
		pharmaCommune = urlDAO.pharmacieCommune(urlDAO, allPharma,
				allPharmaGarde);
		// Selectionner les pharma de garde dans la BD
		urlDAO.pharmaGarde(urlDAO, pharmaCommune);
		// close the Spring Context
		ctx.close();
		System.out.println("DONE");
	}

}