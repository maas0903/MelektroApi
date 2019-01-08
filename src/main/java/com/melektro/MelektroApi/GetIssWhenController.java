/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

import static com.melektro.Tools.ExtAPIs.GetIssWhen;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Calendar;
import java.util.TimeZone;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author marius
 */
@RestController
@Api(value = "restcontroller", description = "get the next specified number of times and time of the ISS given the latitude and longitude")
public class GetIssWhenController {

    @RequestMapping(value = "/GetIssWhen", method = RequestMethod.GET)
    @ApiOperation(value = "Get time and ISS position", notes = "Returns JSON with the information")
    public Object get(
            @RequestParam(value = "proxy", defaultValue = "") String proxy,
            @RequestParam(value = "proxyport", defaultValue = "") String proxyport,
            @RequestParam(value = "latitude", defaultValue = "-24.426663") String lat,
            @RequestParam(value = "longitude", defaultValue = "27.548342") String lon,
            @RequestParam(value = "numberofevents", defaultValue = "5") String cnt,
            @RequestParam(value = "yourgmtvalue", defaultValue = "1") Integer gmt
    ) throws Exception {
        Calendar now = Calendar.getInstance();

        //get current TimeZone using getTimeZone method of Calendar class
        TimeZone timeZone = now.getTimeZone();

        return GetIssWhen(proxy, proxyport, lat, lon, cnt, gmt);

    }
}
