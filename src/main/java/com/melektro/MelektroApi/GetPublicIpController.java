/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

import static com.melektro.Tools.ExtAPIs.GetPublicIp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author marius
 */
@RestController
@Api(value = "restcontroller", description = "get your public IP Address")
public class GetPublicIpController {
    
    @RequestMapping(value = "/GetPublicIp", method = RequestMethod.GET)
    @ApiOperation(value = "Input proxy and proxyport, default is not to use proxy",  notes = "Returns JSON with the information")
    public Object get(@RequestParam(value = "proxy", defaultValue = "") String proxy, @RequestParam(value = "proxyport", defaultValue = "") String proxyport) throws Exception {
        return GetPublicIp(proxy, proxyport);
    }
}
