/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

//import org.hamcrest.Matchers;
//import static org.hamcrest.text.MatchesPattern.matchesPattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mariu
 */
public class BranderSettingsController2Test {

    public BranderSettingsController2Test() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of get method, of class BranderSettingsController2.
     */
    @Test
    public void testGet() throws Exception {
        System.out.println("get");
        BranderSettingsController2 instance = new BranderSettingsController2();
        String expResult = "hourOn:[0-9],durationOn:[0-9],override:[0-9],summer:[0-1],hourOff:[0-9][0-9],degreesOff:[0-9][0-9],degrees:[0-9][0-9].[0-9][0-9]";
        Object res = instance.get();
        String result = (String)res;
        result = result.substring(1, result.length()-2).replace("\"", "");
        //assertThat(result, matchesPattern(expResult));
        assertNotNull(result);
    }

    /**
     * Test of put method, of class BranderSettingsController2.
     */
//    @Test
//    public void testPut() throws Exception {
//        System.out.println("put");
//        String hourOn = "";
//        String durationOn = "";
//        String override = "";
//        String summer = "";
//        String hourOff = "";
//        String degreesOff = "";
//        String urlName = "";
//        BranderSettingsController2 instance = new BranderSettingsController2();
//        Object expResult = null;
//        Object result = instance.put(hourOn, durationOn, override, summer, hourOff, degreesOff, urlName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of Log method, of class BranderSettingsController2.
     */
//    @Test
//    public void testLog() {
//        System.out.println("Log");
//        String message = "";
//        BranderSettingsController2.Log(message);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of GetForConnection method, of class BranderSettingsController2.
     */
    @Test
    public void testGetForConnection() throws Exception {
        System.out.println("GetForConnection");
        String Url = "http://192.168.63.54/temps";
        String expResult = "{  \"DEBUG\": \"false\",  \"Hostname\": \"living\",  \"IpAddress\": \"192.168.63.54\",  ";
        String result = BranderSettingsController2.GetForConnection(Url).substring(0, 76);
        assertEquals(expResult, result);
    }

    /**
     * Test of getReferenceTemperature method, of class
     * BranderSettingsController2.
     */
    @Test
    public void testGetReferenceTemperature() throws Exception {
        System.out.println("getReferenceTemperature");
        String urlName = "http://192.168.63.54/temps";
        BranderSettingsController2 instance = new BranderSettingsController2();
        String expResult = "^[0-90-9.0-90-9]+";
        String result = instance.getReferenceTemperature(urlName);
        //assertThat(result, matchesPattern(expResult));
        assertNotNull(result);
    }

    @Test
    public void testGetCurrentHourAndMinute() throws Exception {
        BranderSettingsController2 instance = new BranderSettingsController2();
        instance.getCurrentHourAndMinute();
        assertEquals(2, instance.hourOfDay.length());
        assertEquals(2, instance.minuteOfDay.length());
    }
    
    @Test
    public void testGetDayOfMonth() throws Exception {
        BranderSettingsController2 instance = new BranderSettingsController2();
        String day = instance.getCurrentDayOfMonth();
        assertEquals(2, day.length());
    }

}
