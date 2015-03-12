package org.springframework.samples.SpringJDBCPharma;

import java.awt.Component;
import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import com.journaldev.spring.jdbc.dao.UrlDAO;
import com.journaldev.spring.jdbc.model.Url;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
/**
 * Unit test for simple App.
 */
public class AppTest   
{/*
    UrlDAO daoTest;
    Url pharmacie1=new Url(1,"pharma1","adress1","tel1",true);
   Url pharmacie2=new Url(2,"pharma2","adress2","tel2",true);
   
	@Before
	public void setUp() throws Exception {
    	daoTest=mock(UrlDAO.class);
        ArrayList lb=new ArrayList<Url>();
        lb.add(pharmacie1);
        lb.add(pharmacie2);
        when(daoTest.getAll()).thenReturn(lb);
        when(daoTest.isUrlExists("pharma1")).thenReturn(1);
        when(daoTest.isPharmaGarde("pharma1")).thenReturn(true);
        
	}

	@After
	public void tearDown() throws Exception {
		daoTest = null;
		
	}
	@Test
	public void save(){

		daoTest.save(pharmacie1);
	    assertEquals(1, pharmacie1.getRecordID());
		
	}
	@Test
	public void isUrlExists(){

		
	    assertEquals(1, daoTest.isUrlExists("pharma1"));
		
	}
	@Test
	public void isPharmaGarde(){

		
	    try {
			assertEquals(true, daoTest.isPharmaGarde(pharmacie1.getUrl()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void getAll(){
		
		ArrayList lb= (ArrayList) daoTest.getAll();
	     assertEquals(2, lb.size());
	
	}*/
}

