package org.springframework.samples.SpringJDBCPharma;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.spring.jdbc.dao.UrlDAO;
import com.journaldev.spring.jdbc.model.Url;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public void testRech(){
    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        UrlDAO urlDAO = ctx.getBean("urlDAO", UrlDAO.class);
        
    }
}
