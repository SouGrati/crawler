package com.journaldev.spring.jdbc.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Url implements Serializable {

	private int recordID;
	private String url;
	private String adresse;
	private BigDecimal lattitude;
	private BigDecimal longitude;
	private String tel;
	private boolean garde;

	public Url() {
	}
	
	public Url(int recordID, String url, String adresse, BigDecimal lattitude,
			BigDecimal longitude, String tel, boolean garde) {
		super();
		this.recordID = recordID;
		this.url = url;
		this.adresse = adresse;
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.tel = tel;
		this.garde = garde;
	}

	public Url(int recordID, String url, String adresse, String tel,
			boolean garde) {
		super();
		this.recordID = recordID;
		this.url = url;
		this.adresse = adresse;
		this.tel = tel;
		this.garde = garde;
	}

	public BigDecimal getLattitude() {
		return lattitude;
	}

	public void setLattitude(BigDecimal lattitude) {
		this.lattitude = lattitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public int getRecordID() {
		return recordID;
	}

	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public boolean getGarde() {
		return garde;
	}

	public void setGarde(boolean garde) {
		this.garde = garde;
	}

	@Override
	public String toString() {
		return "{recordID=" + recordID + ",url=" + url + "}";
	}

}