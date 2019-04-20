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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author marius
 */
@RestController
//@RequestMapping("/gethistory")
@Api(value = "restcontroller", description = "get a day in history")
public class GetHistoryController
{

    //@GetMapping("/{dt}")
    @RequestMapping(value = "/gethistory", method = RequestMethod.GET)
    @ApiOperation(value = "Get a day in history", notes = "Returns JSON with todays date and lists for events, births and deaths")
    public Object get(@RequestParam(value = "dt", defaultValue = "december_25") String dt) throws IOException, InterruptedException
    {
        return GetADay_Formatted(dt);
    }

}
