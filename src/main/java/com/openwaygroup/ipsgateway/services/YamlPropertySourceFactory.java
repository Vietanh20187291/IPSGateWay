package com.openwaygroup.ipsgateway.services;

import com.openwaygroup.ipsgateway.entities.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Properties;

public class YamlPropertySourceFactory implements PropertySourceFactory {
    @Autowired
    public Configuration configuration;
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource)
            throws IOException {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(encodedResource.getResource());
            Properties properties = factory.getObject();
            return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
    }


}