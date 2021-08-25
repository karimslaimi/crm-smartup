package org.sid.web;

import org.sid.dao.InterventionRepository;
import org.sid.dao.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {
    @Autowired
    private InterventionRepository IRepository ;
    @Autowired
    private TechnicienRepository TRepository ;

    @GetMapping("/techstat")
    public List<Object> prod() {


        Map<String,List<Object>> mp=new HashMap<String,List<Object>>();


        //mp.put("techstat", lstat);





        return IRepository.interstat();



    }
    @GetMapping("/api/stat")
    public Map<String,List<Object>> techstat() {


        Map<String,List<Object>> mp=new HashMap<String,List<Object>>();


        mp.put("techstat", TRepository.techstat());


        mp.put("interstat", IRepository.interstat());







        return mp ;



    }
}
