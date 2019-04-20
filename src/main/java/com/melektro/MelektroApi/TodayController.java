/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

import static com.melektro.tools.ExtAPIs.GetToday_Formatted;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author marius
 */
@RestController
@RequestMapping("/today")
@Api(value = "restcontroller", description = "get today in history")
public class TodayController
{

    @GetMapping("/")
    @ApiOperation(value = "Get today in history", notes = "Returns JSON with todays date and lists for events, births and deaths")
    public String getJson() throws IOException, InterruptedException
    {
        return GetToday_Formatted();
    }
}
