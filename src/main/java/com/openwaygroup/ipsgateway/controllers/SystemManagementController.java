package com.openwaygroup.ipsgateway.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "systemManagement";
    }
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute("keyManagement") KeyManagement keyManagement, Model model) {
        model.addAttribute("keyManagement", keyManagement);
        model.addAttribute("systemInformation", systemInformation);
        model.addAttribute("configuration", configuration);
        return "systemCreate";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String store(@ModelAttribute("keyManagement") KeyManagement keyManagement, RedirectAttributes redirAttrs, BindingResult bindingResult, Model model) {
        model.addAttribute("keyManagement", keyManagement);
        model.addAttribute("configuration", configuration);
        boolean isDuplicated = false;
        for (KeyManagement key : systemInformation.getSystemInformation()) {
            if(keyManagement.getId() == key.getId()){
              isDuplicated = true;
            }
        }

        if(!isDuplicated){
            systemInformation.getSystemInformation().add(keyManagement);
            saveParamChanges();
            redirAttrs.addFlashAttribute("success", "Create Key Group Success!");
            return "redirect:/system";
        }else {
            log.info("Duplicated Group ID");
            bindingResult.addError(new ObjectError("duplicated","Duplicated Group ID"));
            return "systemCreate";
        }
    }

    public void saveParamChanges() {
        try {
            Map<String,Object> map = new HashMap<>();
            SystemInformation systemInfo = new SystemInformation(systemInformation.getSystemInformation());
            map.put("system-information",systemInfo);
            ObjectMapper om = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
            Writer writer = new FileWriter("src/main/resources/systemInformation.yml");
            om.writeValue(writer, map);
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
        model.addAttribute("keyManagement", keyManagement);
        model.addAttribute("configuration", configuration);
        return "systemEdit";
    }
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Integer id,@ModelAttribute("keyManagement") KeyManagement keyManagement,RedirectAttributes redirAttrs,BindingResult bindingResult, Model model) {
        model.addAttribute("keyManagement", keyManagement);
        model.addAttribute("configuration", configuration);
        boolean isDuplicated = false;
        //Check Duplicate ID Except Editing Key
        for (KeyManagement key : systemInformation.getSystemInformation()) {
            if(keyManagement.getId() == key.getId() && keyManagement.getId()!=id){
                isDuplicated = true;
                break;
            }
        }
        if(!isDuplicated){
            //Remove old key
            for (KeyManagement key : systemInformation.getSystemInformation()) {
                if(key.getId()==id){
                    systemInformation.getSystemInformation().remove(key);
                    break;
                }
            }
            systemInformation.getSystemInformation().add(keyManagement);
            saveParamChanges();
            redirAttrs.addFlashAttribute("success", "Edit Key Group Success!");
            return "redirect:/system";
        }else {
            log.info("Duplicated Group ID");
            bindingResult.addError(new ObjectError("duplicated","Duplicated Group ID"));
            return "systemEdit";
        }
    }

}
