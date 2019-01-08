/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;
import static com.melektro.Tools.ExtAPIs.GetIss;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author marius
 */
@RestController
@Api(value = "restcontroller", description = "get the position and time of the ISS")
public class GetIssController {
    @RequestMapping(value = "/GetIss", method = RequestMethod.GET)
    @ApiOperation(value = "Get time and ISS position",  notes = "Returns JSON with the information")
    public Object get(@RequestParam(value = "proxy", defaultValue = "") String proxy, @RequestParam(value = "proxyport", defaultValue = "") String proxyport) throws Exception {
        return GetIss(proxy, proxyport);
    }
}
