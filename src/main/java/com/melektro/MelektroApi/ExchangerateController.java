/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

import static com.melektro.Tools.ExtAPIs.GetExchangeRate;
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
@Api(value = "exchangeratecontroller", description = "get the exchangerate for a from to currency")
public class ExchangerateController
{

    @RequestMapping(value = "/exchangerate", method = RequestMethod.GET)
    @ApiOperation(value = "Get the exchange rate", notes = "Returns JSON with exchange rate")
    public Object get(@RequestParam(value = "from", defaultValue = "EUR") String fromCurrency, @RequestParam(value = "to", defaultValue = "GBP") String toCurrency) throws IOException, InterruptedException
    {
        return GetExchangeRate(fromCurrency, toCurrency);
    }

}
