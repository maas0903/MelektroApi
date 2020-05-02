/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

import static com.melektro.tools.LoadProperty.LoadProperty;
//import com.melektro.tools.initialstate.API;
//import com.melektro.tools.initialstate.Bucket;
//import com.melektro.tools.initialstate.Data;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author marius
 */
@RestController
//@RequestMapping("/url")
@Api(value = "restcontroller", description = "Settings server for an ESP8266 thermometer controller")
public class ESP8266Settings
{

    protected static final String PROPERTIESFILE = "serre.prop";
    private static final String MESS_CONFIG = "Properties data must be set in the property file \"./serre.prop\"";
    private static final String MINVALUE = "21";
    private static final String MAXVALUE = "22";
    private static final String WAKEUPTIME = "06:00";
    private static final String SLEEPTIME = "18:00";
    static Logger internalLogger = null;

    //protected static final String SENSITIVEFILE = "sensitive.prop";
    //private static String DefaultAccessKey = "DefaultAccessKey";
    //private static String DefaultBucketKey = "DefaultBucketKey";
    //private static String AccessKey;
    //private static String BucketKey;
    private static final String MESS = "Sensitive data must be set in the property file \"./sensitive.prop\"";

//    private static Properties GetSensitive()
//    {
//        Properties prop = new Properties();
//        InputStream input = null;
//
//        File file = new File(SENSITIVEFILE);
//
//        if (!file.exists())
//        {
//            SetSensitive();
//            Log(MESS);
//            System.exit(0);
//        } else
//        {
//            try
//            {
//                input = new FileInputStream(file);
//                prop.load(input);
//            } catch (IOException e)
//            {
//            } finally
//            {
//                if (input != null)
//                {
//                    try
//                    {
//                        input.close();
//                    } catch (IOException e)
//                    {
//                    }
//                }
//            }
//        }
//        return prop;
//    }
//
//    private static void SetSensitive()
//    {
//        Properties prop = new Properties();
//        OutputStream output = null;
//
//        try
//        {
//            output = new FileOutputStream(SENSITIVEFILE);
//            prop.setProperty("accessKey", DefaultAccessKey);
//            prop.setProperty("bucketKey", DefaultBucketKey);
//            prop.store(output, null);
//        } catch (IOException e)
//        {
//        } finally
//        {
//            if (output != null)
//            {
//                try
//                {
//                    output.close();
//                } catch (IOException e)
//                {
//                }
//            }
//        }
//    }
//
    @RequestMapping(value = "/getsettings", method = RequestMethod.GET)
    @ApiOperation(value = "Get the ESP8266 thermometer settings", notes = "Returns a semicolon seperated string with settings for min and max temperature")
    public Object get(@RequestParam(value = "statusStr", defaultValue = "0") String statusStr) throws IOException, InterruptedException
    {
//        //statusStr has the current temp
//        //send to initialState
//        Properties prop = GetSensitive();
//        AccessKey = LoadProperty(prop, "accessKey", DefaultAccessKey);
//        BucketKey = LoadProperty(prop, "bucketKey", DefaultBucketKey);
//        if (AccessKey.equals(DefaultAccessKey) || BucketKey.equals(DefaultBucketKey))
//        {
//            Log(MESS);
//            System.exit(0);
//        } else
//        {
//            API account = new API(AccessKey);
//            Bucket bucket = new Bucket(BucketKey);
//
//            account.createBucket(bucket);
//
//            Data data = new Data("Temperature", statusStr);
//            account.createData(bucket, data);
//        }

        return GetDS18B20Properties();
    }

    @RequestMapping(value = "/putsettings", method = RequestMethod.PUT)
    @ApiOperation(value = "Put the ESP8266 thermometer settings", notes = "Modify the settings for min and max temperature")
    public Object put(@RequestParam String min, @RequestParam String max, @RequestParam String wakeupTime, @RequestParam String sleepTime) throws IOException, InterruptedException
    {
        SetProperties(min, max, wakeupTime, sleepTime);

        return "{"
                + "\"minValue\":" + min
                + ",\"maxValue\":" + max
                + ",\"wakeupTime\":" + wakeupTime
                + ",\"sleepTime\":" + sleepTime
                + "}";
    }

    public static void Log(String message)
    {
        System.out.println(message);
        internalLogger.log(Level.ALL, message);
    }

    private String GetDS18B20Properties()
    {
        Properties prop = GetProperties();
        String min = LoadProperty(prop, "min", "21");
        String max = LoadProperty(prop, "max", "22");
        String wakeupTime = LoadProperty(prop, "wakeupTime", "06:00");
        String sleepTime = LoadProperty(prop, "sleepTime", "18:00");

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime wakeuptime = LocalTime.from(timeFormatter.parse(wakeupTime));
        LocalTime sleeptime = LocalTime.from(timeFormatter.parse(sleepTime));
        LocalTime time = LocalTime.now();

        String shouldSleepString;
        if (time.isAfter(wakeuptime) && time.isBefore(sleeptime))
        {
            shouldSleepString = "false";
        } else
        {
            shouldSleepString = "true";
        }

        return "{"
                + "\"minValue\":\"" + min + "\","
                + "\"maxValue\":\"" + max + "\","
                + "\"shouldSleep\":\"" + shouldSleepString + "\","
                + "\"currentTime\":\"" + dateFormat.format(date) + "\","
                + "\"wakeUpTime\":\"" + wakeupTime + "\","
                + "\"sleepTime\":\"" + sleepTime + "\""
                + "}";
    }

    private static Properties GetProperties()
    {
        Properties prop = new Properties();
        InputStream input = null;

        File file = new File(PROPERTIESFILE);

        if (!file.exists())
        {
            SetProperties(MINVALUE, MAXVALUE, WAKEUPTIME, SLEEPTIME);
            Log(MESS_CONFIG);
            System.exit(0);
        } else
        {
            try
            {
                input = new FileInputStream(file);
                prop.load(input);
            } catch (IOException e)
            {
            } finally
            {
                if (input != null)
                {
                    try
                    {
                        input.close();
                    } catch (IOException e)
                    {
                    }
                }
            }
        }
        return prop;
    }

    private static void SetProperties(String min, String max, String wakeupTime, String sleepTime)
    {
        Properties prop = new Properties();
        OutputStream output = null;

        try
        {
            output = new FileOutputStream(PROPERTIESFILE);
            prop.setProperty("min", min);
            prop.setProperty("max", max);
            prop.setProperty("wakeupTime", wakeupTime);
            prop.setProperty("sleepTime", sleepTime);

            prop.store(output, null);
        } catch (IOException e)
        {
        } finally
        {
            if (output != null)
            {
                try
                {
                    output.close();
                } catch (IOException e)
                {
                }
            }
        }
    }

}
