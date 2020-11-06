/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

import static com.melektro.tools.LoadProperty.LoadProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
@Api(value = "restcontroller", description = "Settings server for an ESP8266 relay control - on - duration")
public class BranderSettingsController
{

    protected static final String PROPERTIESFILE = "brander.prop";
    private static final String MESS_CONFIG = "Properties data must be set in the property file \"./serre.prop\"";
    private static final String HOURON = "17";
    private static final String DURATIONON = "1";
    private static final String OVERRIDE = "0";
    static final Logger internalLogger = null;

    @RequestMapping(value = "/getbrandersettings", method = RequestMethod.GET)
    @ApiOperation(value = "Get the relay settings", notes = "Returns a semicolon seperated string with settings for HourOn and Duration in hours")
    public Object get() throws IOException, InterruptedException
    {
        return GetDS18B20Properties();
    }

    @RequestMapping(value = "/putbrandersettings", method = RequestMethod.PUT)
    @ApiOperation(value = "Put the ESP8266 thermometer settings", notes = "Modify the settings for min and max temperature")
    public Object put(@RequestParam String hourOn, @RequestParam String durationOn, @RequestParam String override) throws IOException, InterruptedException
    {
        SetProperties(hourOn, durationOn, override);

        return "{"
                + "\"hourOn\":" + hourOn
                + ",\"durationOn\":" + durationOn
                + ",\"override\":" + override
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
        String hourOn = LoadProperty(prop, "hourOn", "17");
        String durationOn = LoadProperty(prop, "durationOn", "1");
        String override = LoadProperty(prop, "override", "0");
        return "{"
                + "\"hourOn\":\"" + hourOn + "\","
                + "\"durationOn\":\"" + durationOn + "\","
                + "\"override\":\"" + override + "\""
                + "}";
    }

    private static Properties GetProperties()
    {
        Properties prop = new Properties();
        InputStream input = null;

        File file = new File(PROPERTIESFILE);

        if (!file.exists())
        {
            SetProperties(HOURON, DURATIONON, OVERRIDE);
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

    private static void SetProperties(String hourOn, String durationOn, String override)
    {
        Properties prop = new Properties();
        OutputStream output = null;

        try
        {
            output = new FileOutputStream(PROPERTIESFILE);
            prop.setProperty("hourOn", hourOn);
            prop.setProperty("durationOn", durationOn);
            prop.setProperty("override", override);

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
