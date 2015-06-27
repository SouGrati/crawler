package com.journaldev.spring.jdbc.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.code.geocoder.model.GeocodeResponse;
import com.journaldev.spring.jdbc.model.Url;

//CRUD operations
public interface UrlDAO {

	// Create
	public void save(Url url);

	// Read
	public Url getById(int recordID);

	// Update
	public void update(Url url);

	// Get All
	public List<Url> getAll();

	// getByUrl
	public Url getByUrl(String url);

	// Truncate Table
	public void runSql2(String sql);

	// processPage
	public void processPage(UrlDAO urlDAO) throws IOException;

	// Retourne les pharmacie d'agadir
	public ArrayList<String> pharmacieCommune(UrlDAO urlDAO,
			ArrayList<String> allPharma, ArrayList<String> allPharmaGarde)
			throws IOException;

	// Selectionner les pharma de garde dans la BD
	public void pharmaGarde(UrlDAO urlDAO, ArrayList pharmaCommune)
			throws IOException;

	// Localisation
	public GeocodeResponse geo(String a) throws IOException;

	public List<Url> getAllGarde();
	
	public int minDistance(double d, double e);

	public double distance(double d, double e, double f, double g);

}