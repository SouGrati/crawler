package com.journaldev.spring.jdbc.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Url implements Serializable{
	 
    private int recordID;
    private String url;
    private String adresse;
    private String tel;
    private boolean garde;

     
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
    public String toString(){
        return "{recordID="+recordID+",url="+url+"}";
    }
	
}