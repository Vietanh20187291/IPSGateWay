package com.openwaygroup.ipsgateway.controllers;

import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

@RequestMapping("/system")
@Controller
public class SystemManagementController {
    @Autowired
    public Configuration configuration;
    @Autowired
    public SystemInformation systemInformation;
    Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("systemInfo") SystemInformation systemInfo, Model model) {
        model.addAttribute("systemInfo", systemInformation);
        model.addAttribute("configuration", configuration);
        System.out.println(systemInformation.toString());
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
        List<KeyManagement> systemInfo = new ArrayList<>();
        boolean isDuplicated = false;
       /* SystemInformation systemInformationCreate = new SystemInformation();*/
        for (KeyManagement key : systemInformation.getSystemInformation()) {
            if(keyManagement.getId() == key.getId()){
              isDuplicated = true;

            }
            System.out.println(key.toString());
        }

        if(!isDuplicated){
            systemInfo.add(keyManagement);
            systemInformation.getSystemInformation().addAll(systemInfo);
            saveParamChanges();
        }else {
            log.info("Duplicate");
        }

        return "systemCreate";
    }

    public void saveParamChanges() {
        try {
            //YAML Format Options
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            // Create and set properties into YAML object
            Yaml yaml = new Yaml(options);
            Map<String,Map> prefix = new LinkedHashMap<>();
            Map<String,Object> dataMap = new LinkedHashMap<>();
            List<Map> list = new ArrayList<>();
            //

            for(int id =0; id<systemInformation.getSystemInformation().size();id++){
                Map<String, Object> systemInfo = new LinkedHashMap<>();
                LinkedHashMap<String, Object> zpk = new LinkedHashMap<>();
                LinkedHashMap<String, String> componentZpk1 = new LinkedHashMap<>();
                LinkedHashMap<String, String> componentZpk2 = new LinkedHashMap<>();
                LinkedHashMap<String, String> componentZpk3 = new LinkedHashMap<>();
                LinkedHashMap<String, Object> tcmk = new LinkedHashMap<>();
                LinkedHashMap<String, String> componentTcmk1 = new LinkedHashMap<>();
                LinkedHashMap<String, String> componentTcmk2 = new LinkedHashMap<>();
                LinkedHashMap<String, String> componentTcmk3 = new LinkedHashMap<>();
                //
                systemInfo.put("id",systemInformation.getSystemInformation().get(id).getId());
                systemInfo.put("zpk", zpk);
                systemInfo.put("tcmk", tcmk);
                zpk.put("component1",componentZpk1);
                zpk.put("component2",componentZpk2);
                zpk.put("component3",componentZpk3);
                zpk.put("combined",systemInformation.getSystemInformation().get(id).getZpk().getCombined());
                zpk.put("kcv",systemInformation.getSystemInformation().get(id).getZpk().getKcv());
                tcmk.put("component1",componentTcmk1);
                tcmk.put("component2",componentTcmk2);
                tcmk.put("component3",componentTcmk3);
                tcmk.put("combined",systemInformation.getSystemInformation().get(id).getTcmk().getCombined());
                tcmk.put("kcv",systemInformation.getSystemInformation().get(id).getTcmk().getKcv());
                componentZpk1.put("data", systemInformation.getSystemInformation().get(id).getZpk().getComponent1().getData());
                componentZpk1.put("kcv", systemInformation.getSystemInformation().get(id).getZpk().getComponent1().getKcv());
                componentZpk2.put("data", systemInformation.getSystemInformation().get(id).getZpk().getComponent2().getData());
                componentZpk2.put("kcv", systemInformation.getSystemInformation().get(id).getZpk().getComponent2().getKcv());
                componentZpk3.put("data", systemInformation.getSystemInformation().get(id).getZpk().getComponent3().getData());
                componentZpk3.put("kcv", systemInformation.getSystemInformation().get(id).getZpk().getComponent3().getKcv());
                componentTcmk1.put("data", systemInformation.getSystemInformation().get(id).getTcmk().getComponent1().getData());
                componentTcmk1.put("kcv", systemInformation.getSystemInformation().get(id).getTcmk().getComponent1().getKcv());
                componentTcmk2.put("data", systemInformation.getSystemInformation().get(id).getTcmk().getComponent2().getData());
                componentTcmk2.put("kcv", systemInformation.getSystemInformation().get(id).getTcmk().getComponent2().getKcv());
                componentTcmk3.put("data", systemInformation.getSystemInformation().get(id).getTcmk().getComponent3().getData());
                componentTcmk3.put("kcv", systemInformation.getSystemInformation().get(id).getTcmk().getComponent3().getKcv());
                list.add(systemInfo);
            }
            dataMap.put("systemInformation",list);
            //
            prefix.put("system-information",dataMap);
            //
            Writer writer = new FileWriter("src/main/resources/systemInformation.yml");
            yaml.dump(prefix,writer);

            writer.close();
            this.log.info("System Information Param Saved To File");
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, Model model) {
        KeyManagement keyManagement = new KeyManagement();
        for (KeyManagement key : systemInformation.getSystemInformation()) {
            if (key.getId().equals(id)) {
                keyManagement = key;
            }
        }
        model.addAttribute("keyManagement", keyManagement); //Đổ ra view
        model.addAttribute("configuration", configuration);
        model.addAttribute("id",id);
        return "systemEdit";
    }
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Integer id,@ModelAttribute("keyManagement") KeyManagement keyManagement, Model model) {
        model.addAttribute("keyManagement", keyManagement); //Đổ ra view
        model.addAttribute("configuration", configuration);
        model.addAttribute("id",id);
        List<KeyManagement> systemInfo = new ArrayList<>();
        boolean isDuplicated = false;
        for (KeyManagement key : systemInformation.getSystemInformation()) {
            if(keyManagement.getId() == key.getId()){
                isDuplicated = true;
            }
        }

        if(isDuplicated){
            systemInfo.add(keyManagement);
            systemInformation.getSystemInformation().addAll(systemInfo);
            saveParamChanges();
        }else {
            log.info("Duplicate");
        }


        return "systemEdit";
    }
}
