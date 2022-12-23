package com.openwaygroup.ipsgateway.controllers;

import com.openwaygroup.ipsgateway.entities.Configuration;
import com.openwaygroup.ipsgateway.entities.KeyManagement;
import com.openwaygroup.ipsgateway.entities.SystemInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/system")
@Controller
public class SystemManagementController {
    @Autowired
    public Configuration configuration;
    @Autowired
    public SystemInformation systemInformation;
    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("systemInformation") SystemInformation systemInfo, Model model) {
        model.addAttribute("systemInformation", systemInformation);
        model.addAttribute("configuration", configuration);
        return "systemManagement";
    }
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute("keyManagement") KeyManagement keyManagement, Model model) {
        model.addAttribute("keyManagement", keyManagement); //Đổ ra view
        model.addAttribute("systemInformation", systemInformation);
        model.addAttribute("configuration", configuration);
        return "systemCreate";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String store(@ModelAttribute("keyManagement") KeyManagement keyManagement, Model model) {
        model.addAttribute("keyManagement", keyManagement); //Đổ ra view
        model.addAttribute("configuration", configuration);
        Map<Integer,KeyManagement> systemInfo = new LinkedHashMap<>();
        systemInfo.put(keyManagement.getId(),keyManagement);
        systemInformation.setSystemInformation(systemInfo);
      /*  systemInformation.getKeyManagement().get("K"+randomUUIDString); "K"+randomUUIDString, keyManagement*/
        // show map using method keySet()
        for (Map.Entry<Integer,KeyManagement> entry : systemInfo.entrySet()) {
            Integer key = entry.getKey();
            KeyManagement value = entry.getValue();
            System.out.println(key + " : " + value.getZpk().getComponent1().toString());
            System.out.println(key + " : " + value.getZpk().getComponent2().toString());
            System.out.println(key + " : " + value.getZpk().getComponent3().toString());
            System.out.println(key + " : " + value.getZpk().toString());
        }
        return "systemCreate";
    }
   /* @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute("systemInformation") SystemInformation systemInformation, Model model) {
        model.addAttribute("systemInformation", systemInformation);
        return "systemManagementEdit";
    }
*/
}
