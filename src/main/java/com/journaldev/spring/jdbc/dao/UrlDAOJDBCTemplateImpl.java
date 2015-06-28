package com.journaldev.spring.jdbc.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.journaldev.spring.jdbc.model.Url;

public class UrlDAOJDBCTemplateImpl implements UrlDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void save(Url url) {
		String query = "insert into recrd (recordID, url,adresse,lattitude,longitude,tel,garde) values (?,?,?,?,?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] args = new Object[] { url.getRecordID(), url.getUrl(),
				url.getAdresse(), url.getLattitude(), url.getLongitude(),
				url.getTel(), url.getGarde() };

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {

		} else
			;
	}

	/*
	 * public int getById(String url) { final String query =
	 * "select recordID, url from recrd where url = ?"; final JdbcTemplate
	 * jdbcTemplate = new JdbcTemplate(dataSource); //using RowMapper anonymous
	 * class, we can create a separate RowMapper for reuse //RowMapper anonymous
	 * class implementation to map the ResultSet data //to Url bean object in
	 * queryForObject() method. Url url1 = jdbcTemplate.queryForObject (query,
	 * new Object[]{url}, new RowMapper<Url>(){
	 * 
	 * public Url mapRow(ResultSet rs, int rowNum) throws SQLException{ Url url
	 * = new Url(); //url.setRecordID(rs.getInt("recordID"));
	 * url.setUrl(rs.getString("url")); Object[] args = new Object[]
	 * {url.getUrl()};
	 * 
	 * return url; }});
	 * 
	 * int recrd =url1.getRecordID();
	 * 
	 * return recrd; }
	 */
	public Url getByUrl(String url) {
		final String query = "select recordID, url from recrd where url = ?";
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// using RowMapper anonymous class, we can create a separate RowMapper
		// for reuse
		// RowMapper anonymous class implementation to map the ResultSet data
		// to Url bean object in queryForObject() method.
		Url url1 = jdbcTemplate.queryForObject(query, new Object[] { url },
				new RowMapper<Url>() {

					public Url mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Url url = new Url();
						url.setRecordID(rs.getInt("recordID"));
						url.setUrl(rs.getString("url"));
						Object[] args = new Object[] { url.getRecordID(),
								url.getUrl() };

						return url;
					}
				});

		return url1;
	}

	public void update(Url url) {
		String query = "update recrd set garde=? where recordID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] args = new Object[] { url.getGarde(), url.getRecordID() };

		int out = jdbcTemplate.update(query, args);
		if (out != 0) {
			System.out.println("Url updated with id=" + url.getRecordID());
		} else
			System.out.println("No Url found with id=" + url.getRecordID());
	}

	// Liste des pharmacies
	public List<Url> getAll() {
		String query = "select recordID, url, adresse,tel,longitude,lattitude from recrd";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Url> empList = new ArrayList<Url>();

		List<Map<String, Object>> empRows = jdbcTemplate.queryForList(query);

		for (Map<String, Object> empRow : empRows) {
			Url emp = new Url();
			emp.setRecordID(Integer.parseInt(String.valueOf(empRow
					.get("recordID"))));
			emp.setUrl(String.valueOf(empRow.get("url")));
			emp.setAdresse(String.valueOf(empRow.get("adresse")));
			emp.setTel(String.valueOf(empRow.get("tel")));
			emp.setLongitude(String.valueOf(empRow.get("longitude")));
			emp.setLattitude(String.valueOf(empRow.get("lattitude")));
			empList.add(emp);
		}
		return empList;
	}

	// Vider la table afin de ne pas avoir une redondance des pharmacies dans la
	// BD
	public void runSql2(String sql) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.execute(sql);

		System.out.println("Trancate DONE!");

	}

	// verifier si la pharmacie exist deja dans la base de donnees
	public int isUrlExists(String url) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SELECT count(*) FROM recrd WHERE url = ?";
		int res = 0;
		int count = jdbcTemplate.queryForObject(sql, new Object[] { url },
				Integer.class);

		if (count > 0)
			res = count;

		return res;
	}

	// Veifier si la pharmacie est une pharmacie de garde
	public boolean isPharmaGarde(String pharmacie) throws IOException {
		boolean result = false;
		Document doc = Jsoup
				.connect(
						"http://www.agadirbonsplans.com/pharmacie-de-garde-agadir/")
				.timeout(10 * 10000).get();
		// Document doc =
		// Jsoup.connect("http://www.anahna.com/pharmacies-agadir-ca7-qa0.html").timeout(10*10000).get();
		Elements newsGarde = doc.select("div .pharmaTitle");
		System.out.printf("2");
		for (int j = 0; j < newsGarde.size(); j++) {
			System.out.printf("3");
			Element garde = newsGarde.get(j);
			String gardee = garde.text().replaceAll(" ", "");

			do {
				if (!pharmacie.equals(gardee)) {
					result = false; // System.out.printf(pharmacie.getUrl());
					// System.out.println(pharmaciee +"VS"+gardee);
				} else {
					result = true;
					return result;
				}
			} while ((result == true) || (j > newsGarde.size()));
		}
		return result;

	}

	
	public GeocodeResponse geo(String a) throws IOException {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
				.setAddress(a).setLanguage("en").getGeocoderRequest();
		for (int i = 0; i < 3; i++) {
			geocoderRequest = new GeocoderRequestBuilder().setAddress(a)
					.setLanguage("en").getGeocoderRequest();
			System.out.println(i);

		}
		return geocoder.geocode(geocoderRequest);
	}

	public Url getById(int recordID) {
		final String query = "select recordID,url, adresse,tel,longitude,lattitude from recrd where recordID = ?";
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Url url1 = jdbcTemplate.queryForObject(query, new Object[] { recordID },
				new RowMapper<Url>() {

					public Url mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Url url = new Url();
						url.setRecordID(rs.getInt("recordID"));
						url.setUrl(rs.getString("url"));
						url.setAdresse(rs.getString("adresse"));
						url.setTel(rs.getString("tel"));
						url.setLongitude(rs.getString("longitude"));
						url.setLattitude(rs.getString("lattitude"));
						Object[] args = new Object[] { url.getRecordID(),
								url.getUrl(),url.getAdresse(),url.getTel(),url.getLongitude(),url.getLattitude() };

						return url;
					}
				});

		return url1;
	}

	public void processPage(UrlDAO urlDAO) throws IOException {

		Document doc = Jsoup
				.connect("http://www.anahna.com/pharmacies-agadir-ca7-qa0.html")
				.timeout(10 * 10000).get();

		Url url = new Url();
		// pharma
		Elements newsHeadlines = doc.select("h1");
		// Adresse
		Elements questions = doc.select("div .right").select("p:eq(1)");
		// tel
		Elements tels = doc.select("div.right").select("p:eq(2)");
		// Localisation
		Elements maps = doc.select("div .left").select("a[href]");

		for (int i = 1; i < newsHeadlines.size() - 1; i++) {
			String longitude = "0";
			String lattitude = "0";
			Element elem = newsHeadlines.get(i);
			Element adress = questions.get(i - 1);
			Element tele = tels.get(i - 1);
			Element map = maps.get(i - 1);
			boolean b = false;
			// System.out.println(maps.get(99).attr("abs:href"));
			if (map.attr("href").contains("map.php?etabid")) { // System.out.println(map.attr("abs:href"));

				Document docs = Jsoup.connect(map.attr("abs:href"))
						.timeout(10 * 10000).get();

				// Recuperer le dernier tag 'script'
				Element scriptElem = docs.select("script").last();

				// charger tout le script
				String loadScript = scriptElem.html();
				// System.out.println(jsCode);

				// extraire la ligne qui contient les coordonées de la pharmacie
				// []
				loadScript = loadScript.substring(loadScript.indexOf('['),
						loadScript.indexOf(']'));
				// Etudier le cas ou les coordonnées sont introuvable
				if (loadScript.length() > 2) {

					longitude = loadScript
							.substring(1, loadScript.indexOf(','));
					lattitude = loadScript.substring(loadScript.indexOf(','));
					lattitude = lattitude.substring(2);
					url.setLattitude(lattitude);
					url.setLongitude(longitude);
					System.out.println(elem.text());
					System.out.println("longitude" + longitude);
					System.out.println("lattitude" + lattitude);
				} else

					System.out.println("Coordonnee introuvable");
				System.out.println(elem.text());
				url.setLattitude(lattitude);
				url.setLongitude(longitude);
			}
			// Insere l'Element dans La BD
			if ((elem.text().contains("Pharmacie"))
					&& (tele.text().contains("05"))) {

				url.setUrl(elem.text());
				url.setAdresse(adress.text());
				url.setTel(tele.text());
				url.setGarde(b);

				urlDAO.save(url);
				System.out.println("succeees!!!!!!!!!!!!!!");

			}

		}
	}

	public ArrayList<String> pharmacieCommune(UrlDAO urlDAO,
			ArrayList<String> allPharma, ArrayList<String> allPharmaGarde)
			throws IOException {
		ArrayList pharmaCommune = new ArrayList<String>();

		List<Url> set1 = urlDAO.getAll();

		for (int i = 0; i < set1.size(); i++) {
			Url pharmacie = set1.get(i);
			String pharmaciee = pharmacie.getUrl();// .replaceAll(" ", "");
			allPharma.add(pharmaciee);

		}

		Document doc1 = Jsoup
				.connect(
						"http://www.agadirbonsplans.com/pharmacie-de-garde-agadir/")
				.timeout(10 * 10000).get();
		Elements newsGarde = doc1.select("div .pharmaTitle");
		for (int j = 0; j < newsGarde.size(); j++) {
			Element garde = newsGarde.get(j);
			String gardee = garde.text();// .replaceAll(" ", "");
			allPharmaGarde.add(gardee);

		}

		System.out
				.println("-----------------Toutes les pharma--------------------");
		System.out.println(allPharma);
		System.out
				.println("-----------------Toutes les pharma de Garde------------------------------------------");
		System.out.println(allPharmaGarde);

		allPharma.retainAll(allPharmaGarde);
		pharmaCommune = allPharma;
		System.out
				.println("-------------------Les Pharma Commune--------------------------------");
		System.out.println(pharmaCommune);
		return pharmaCommune;

	}

	public void pharmaGarde(UrlDAO urlDAO, ArrayList pharmaCommune)
			throws IOException {
		for (int i = 0; i < pharmaCommune.size(); i++) {
			int j = 0;
			// Recupere l ID des pharmacies commune
			j = urlDAO.getByUrl(pharmaCommune.get(i).toString()).getRecordID();
			System.out.println(j + "-" + pharmaCommune.get(i).toString());
			// Update
			Url pharma = new Url();
			pharma.setRecordID(j);
			pharma.setGarde(true);
			urlDAO.update(pharma);
			System.out.println("updated");
		}
	}

	public List<Url> getAllGarde() {
		String query = "select recordID, url, adresse,tel from recrd where garde=1";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Url> empList = new ArrayList<Url>();

		List<Map<String, Object>> empRows = jdbcTemplate.queryForList(query);

		for (Map<String, Object> empRow : empRows) {
			Url emp = new Url();
			emp.setRecordID(Integer.parseInt(String.valueOf(empRow
					.get("recordID"))));
			emp.setUrl(String.valueOf(empRow.get("url")));
			emp.setAdresse(String.valueOf(empRow.get("adresse")));
			emp.setTel(String.valueOf(empRow.get("tel")));
			empList.add(emp);
		}
		return empList;
	}

	public double distance(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371000; // meters
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = (double) (earthRadius * c);

		return dist;
	}

	public int minDistance(double lat2, double lng2) {
		HashMap<Integer, Double> hmap = new HashMap<Integer, Double>();
		List<Url> list = this.getAll();
		for (Url l : list) {
			hmap.put(
					l.getRecordID(),
					distance(Double.parseDouble(l.getLattitude()),
							Double.parseDouble(l.getLongitude()), lat2,
							lng2));
		}
		//Map<Integer, Double> sortedMap = new TreeMap<Integer, Double>(hmap);
		Double a=(Collections.min(hmap.values()));
		int key=1;
		for (Entry<Integer, Double> entry : hmap.entrySet()) {  // Itrate through hashmap
            if (entry.getValue()==a) {
                key=entry.getKey();     // Print the key with max value
            }
        }
		return key;
	}

	

}
