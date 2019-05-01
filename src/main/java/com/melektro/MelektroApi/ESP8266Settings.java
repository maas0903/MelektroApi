/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

import static com.melektro.tools.ExtAPIs.GetADay_Formatted;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
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
@Api(value = "restcontroller", description = "Settings server for an ESP8266 thermometer controller")
public class ESP8266Settings
{
    @RequestMapping(value = "/getsettings", method = RequestMethod.GET)
    @ApiOperation(value = "Get the ESP8266 thermometer settings", notes = "Returns a semicolon seperated string with settings for min and max temperature")
    public Object get(@RequestParam(value = "statusStr", defaultValue = "none") String statusStr) throws IOException, InterruptedException
    {
        return GetDS18B20Properties(statusStr);
    }

    @RequestMapping(value = "/putsettings", method = RequestMethod.PUT)
    @ApiOperation(value = "Put the ESP8266 thermometer settings", notes = "Modify the settings for min and max temperature")
    public Object put(@RequestParam String min,@RequestParam String max) throws IOException, InterruptedException
    {
        return "min="+min+";"+"max="+max;
    }

    private String GetDS18B20Properties(String statusStr)
    {
        return "min=21;max=22";
    }

}
