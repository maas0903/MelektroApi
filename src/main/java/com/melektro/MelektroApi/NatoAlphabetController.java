/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

import static com.melektro.Tools.Nato.GetNato;
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
//@RequestMapping("/nato")
@Api(value = "restcontroller", description = "get the nato phonetic alphabet and morse code of an input string")
public class NatoAlphabetController {
    
    //@GetMapping("/{str}")
    @RequestMapping(value = "/nato", method = RequestMethod.GET)
    @ApiOperation(value = "get the nato phonetic alphabet and morse code of an input string",  notes = "Returns JSON with the information")
    public Object get(@RequestParam(value = "input string", defaultValue = "abcde") String str) {
        return GetNato(str);
    }
}
