/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

import com.google.gson.GsonBuilder;
import com.melektro.Tools.RandomEMSet;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author marius
 */
@RestController
@RequestMapping("/random")
public class RandomController
{

    @GetMapping("/")
    public Object get()
    {
        List<String> ThisSet;
        ThisSet = RandomEMSet.makeARandomEMSet();
        return new GsonBuilder().setPrettyPrinting().create().toJson(ThisSet);
    }
}
