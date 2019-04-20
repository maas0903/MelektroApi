/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

import static com.melektro.tools.ExtAPIs.GetSerres_Formatted;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author marius
 */
@RestController
@RequestMapping("/serres")
@Api(value = "restcontroller", description = "Get the logs of the serres")
public class SerresController
{

    @GetMapping("/")
    @ApiOperation(value = "Get the logs of the serres", notes = "Returns JSON with the logs of the serres")
    public String getJson() throws IOException, InterruptedException
    {
        //return GetSerres_Formatted();
        File file = new File("/home/pi/usbdrv/NetBeansProjects/Thermometer/Temperature.log");
        String result = "";
        if (file.exists())
        {
            InputStream input = new FileInputStream(file);
            int content;
            while ((content = input.read()) != -1)
            {
                result = result + ((char) content);
            }
        }
        return result;
    }
}
