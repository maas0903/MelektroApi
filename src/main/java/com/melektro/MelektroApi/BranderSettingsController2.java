/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

import com.google.gson.Gson;
import static com.melektro.tools.LoadProperty.LoadProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author marius
 */
@RestController
//@RequestMapping("/url")
@Api(value = "restcontroller", description = "Settings server for brander - summer on every even day and 31st, winter has an off hour and degrees for heating")
public class BranderSettingsController2 {

    protected static final String PROPERTIESFILE = "brander2.prop";
    private static final String MESS_CONFIG = "Properties data must be set in the property file \"./brander2.prop\"";
    private static final String HOURON = "6";
    private static final String MINUTEON = "6";
    private static final String DURATIONON = "1";
    private static final String OVERRIDE = "0";

    private static final String SUMMER = "1";
    private static final String HOUROFF = "23";
    private static final String MINUTEOFF = "23";
    private static final String DEGREESOFF = "22";
    private static final String ABSOLUTEMIN = "10";
    private static final String URLNAME = "http://192.168.63.54/temps";

    private static final int TRYREADSENSORCOUNT = 3;

    static final Logger internalLogger = null;

    @RequestMapping(value = "/getbrandersettings2", method = RequestMethod.GET)
    @ApiOperation(value = "Get the brander settings", notes = "Returns a semicolon seperated string with settings")
    public Object get() throws IOException, InterruptedException {
        return GetBranderProperties();
    }

    @RequestMapping(value = "/putbrandersettings2", method = RequestMethod.PUT)
    @ApiOperation(value = "Put the brander settings", notes = "Modify the settings for values")
    public Object put(@RequestParam String hourOn,
            @RequestParam String minuteOn,
            @RequestParam String durationOn,
            @RequestParam String override,
            @RequestParam String summer,
            @RequestParam String hourOff,
            @RequestParam String minuteOff,
            @RequestParam String degreesOff,
            @RequestParam String absoluteMin,
            @RequestParam String urlName
    ) throws IOException, InterruptedException {
        SetProperties(hourOn, minuteOn, durationOn, override, summer, hourOff, minuteOff, degreesOff, absoluteMin, urlName);

        return "{"
                + "\"hourOn\":" + hourOn
                + "\"minuteOn\":" + minuteOn
                + ",\"durationOn\":" + durationOn
                + ",\"override\":" + override
                + ",\"summer\":" + summer
                + ",\"hourOff\":" + hourOff
                + ",\"minuteOff\":" + minuteOff
                + ",\"degreesOff\":" + degreesOff
                + ",\"absoluteMin\":" + absoluteMin
                + ",\"urlName\":" + urlName
                + "}";
    }

    public static void Log(String message) {
        System.out.println(message);
        //internalLogger.log(Level.ALL, message);
    }


    /*
   [T😎D😎] Move to Melektrotools
     */
    static String GetForConnection(String Url) throws MalformedURLException, IOException {
        try {
            URL url = new URL(Url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();

            if (status == 200) {
                String result;
                try ( BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    result = "";
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        result = result + inputLine;
                    }
                }
                if (!result.contains("No devices found")) {
                    return result;
                }
            }
            return "";
        } catch (IOException ex) {
            //ex.printStackTrace();
            System.out.println("Host unreachable");
            return "";
        }
    }

    String getReferenceTemperature(String urlName) throws InterruptedException, IOException {
        System.out.println("***********Reading " + urlName);
        int tryCount = 0;

        String jsonString = "";
        do {
            jsonString = GetForConnection(urlName);
            tryCount++;
            System.out.print("***********Attempt ");
            System.out.println(tryCount);
            if (jsonString.equals("")) {
                Thread.sleep(1000);
            }
        } while (jsonString.equals("") && tryCount <= TRYREADSENSORCOUNT);

        if (!jsonString.equals("")) {
            System.out.println("***********Read OK");
            //String sNTPDate = getNTPDate();
        }

        /*
        [TODO] get temp from json
         */
        Gson gson = new Gson();
        SensorsNormalised sensorsNormalised = gson.fromJson(jsonString, SensorsNormalised.class);
        System.out.println("Got JSON Object");
        
        return sensorsNormalised.sensors.get(0).getValue();
    }

    /*
   [T😎D😎 end] Move to Melektrotools
     */
    
    String hourOfDay;
    String minuteOfDay;
    public void getCurrentHourAndMinute() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        hourOfDay = formatter.format(date);
        formatter = new SimpleDateFormat("mm");
        minuteOfDay = formatter.format(date);
    }
    
    public String getCurrentDayOfMonth() {
        //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }


    private String GetBranderProperties() throws InterruptedException, IOException {
        Properties prop = GetProperties();
        String hourOn = LoadProperty(prop, "hourOn", HOURON);
        String minuteOn = LoadProperty(prop, "minuteOn", MINUTEON);
        String durationOn = LoadProperty(prop, "durationOn", DURATIONON);
        String override = LoadProperty(prop, "override", OVERRIDE);
        String summer = LoadProperty(prop, "summer", SUMMER);
        String hourOff = LoadProperty(prop, "hourOff", HOUROFF);
        String minuteOff = LoadProperty(prop, "minuteOff", MINUTEOFF);
        String degreesOff = LoadProperty(prop, "degreesOff", DEGREESOFF);
        String absoluteMin = LoadProperty(prop, "absoluteMin", ABSOLUTEMIN);
        String urlName = LoadProperty(prop, "urlName", URLNAME);
        String degrees = getReferenceTemperature(urlName);
        getCurrentHourAndMinute();
        String dayOfMonth = getCurrentDayOfMonth();
        String result =  
               "{"
                + "\"hourOn\":\"" + hourOn + "\","
                + "\"minuteOn\":\"" + minuteOn + "\","
                + "\"durationOn\":\"" + durationOn + "\","
                + "\"override\":\"" + override + "\","
                + "\"summer\":\"" + summer + "\","
                + "\"hourOff\":\"" + hourOff + "\","
                + "\"minuteOff\":\"" + minuteOff + "\","
                + "\"degreesOff\":\"" + degreesOff + "\","
                + "\"absoluteMin\":\"" + absoluteMin + "\","
                + "\"degrees\":\"" + degrees + "\","
                + "\"hourOfDay\":\"" + hourOfDay + "\","
                + "\"minuteOfDay\":\"" + minuteOfDay + "\","
                + "\"dayOfMonth\":\"" + dayOfMonth + "\","
                + "\"urlName\":\"" + urlName + "\""
                + "}";
        Log(result);
        return result;
    }

    private static Properties GetProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        File file = new File(PROPERTIESFILE);

        if (!file.exists()) {
            SetProperties(HOURON, MINUTEON, DURATIONON, OVERRIDE, SUMMER, HOUROFF, MINUTEOFF, DEGREESOFF, ABSOLUTEMIN, URLNAME);
            //Log(MESS_CONFIG);
            System.exit(0);
        } else {
            try {
                input = new FileInputStream(file);
                prop.load(input);
            } catch (IOException e) {
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return prop;
    }

    private static void SetProperties(String hourOn, String minuteOn, String durationOn, String override, String summer, String hourOff, String minuteOff, String degreesOff, String absoluteMin, String urlName) {
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(PROPERTIESFILE);
            prop.setProperty("hourOn", hourOn);
            prop.setProperty("minuteOn", minuteOn);
            prop.setProperty("durationOn", durationOn);
            prop.setProperty("summer", summer);
            prop.setProperty("hourOff", hourOff);
            prop.setProperty("minuteOff", minuteOff);
            prop.setProperty("degreesOff", degreesOff);
            prop.setProperty("urlName", urlName);
            prop.setProperty("override", override);
            prop.setProperty("absoluteMin", absoluteMin);

            prop.store(output, null);
        } catch (IOException e) {
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
