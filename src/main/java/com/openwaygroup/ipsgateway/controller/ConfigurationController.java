package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.Configuration;
import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.*;
import org.springframework.integration.monitor.IntegrationMBeanExporter;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@org.springframework.context.annotation.Configuration
@EnableIntegration
@Controller
@RequestMapping("/configuration")
public class ConfigurationController {
    public Socket socket;
    public ServerSocket serverSocket;
    org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);

    @Autowired
    IntegrationFlowContext integrationFlowContext;

    @Autowired
    public Configuration configuration;
    @Autowired
    private ApplicationEventPublisher publisher;

    AbstractConnectionFactory serverCf;

    IntegrationMBeanExporter integrationMBeanExporter;

    @RequestMapping(method = RequestMethod.GET)

    public String index(@ModelAttribute("configuration") Configuration config,Model model) throws UnknownHostException {
        model.addAttribute("configuration", configuration); //Đổ ra view
        configuration.setIpsIp(getLocalHostAddress());
        return "configuration";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute("configuration") Configuration config, Model model) throws UnknownHostException {
        model.addAttribute("configuration", configuration);
        configuration.setIpsIp(getLocalHostAddress());
        System.out.println("------------------------------");
        return "configurationEdit";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@ModelAttribute("configuration") Configuration config) throws IOException {
        System.out.println("------------------------------");
        YamlPropertySourceFactory yamlPropertySourceFactory = new YamlPropertySourceFactory();
            if(!config.getRole()){
                //Client
                initializeClient(config);
            }else{
                //Server
                configuration.setIpsPort(config.getIpsPort());
                commandServerFlow();
                saveParamChanges();
               /* initializeServer(configuration);*/
            }
            //Change Log Level
        String loggerLevel = config.getLogLevel();
        setLogLevel(loggerLevel);

        if (configuration.getStatus()){
            return "redirect:/configuration";
        }else{
            return "redirect:/configuration/edit";
        }

    }

    public boolean initializeClient(Configuration config) {
        System.out.println("------------------------------");
        try {
            socket = new Socket(config.getHostIp(), config.getHostPort());
            configuration.setStatus(true);
            configuration.setRole(false);
            configuration.setHostIp(config.getHostIp());
            configuration.setHostPort(config.getHostPort());
            log.info("Connect To Server Successfully");
            log.info(String.valueOf(socket));
            //Thread check connection status
            new Thread(() -> {
                try {
                    configuration.checkConnection();
                } catch (IOException e) {
                   /* throw new RuntimeException(e);*/
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            return true;
        } catch (Exception e) {
            configuration.setStatus(false);
            log.info("Connect To Server Failed");
            return false;
        }
    }

    public void handlerConnection() throws IOException {
        while (!serverSocket.isClosed()) {
            try {
                socket = serverSocket.accept();
                log.info("Client Accepted: " + socket);
                this.configuration.setClientNumber(this.configuration.getClientNumber()+1);
                new Thread(() -> {
                    try {
                        String sentence_from_client;
                        String sentence_to_client;
                        InputStream is = socket.getInputStream();
                        OutputStream os = socket.getOutputStream();
                        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(is));
                        DataOutputStream outToClient = new DataOutputStream(os);
                        while (!serverSocket.isClosed()) {
                            // Check for client disconnected
                            if(inFromClient.read() == -1){
                                this.configuration.setClientNumber(this.configuration.getClientNumber()-1);
                                log.info("Client Disconnected: "+ socket +" closing...");
                                socket.close();
                                break;
                            }
                            /*sentence_from_client = inFromClient.readLine();
                            sentence_to_client = sentence_from_client +" (Server accepted!)" + '\n';
                            outToClient.writeBytes(sentence_to_client);*/
                        }
                    } catch (IOException e) {
                        /*  throw new RuntimeException(e);*/
                    }
                }).start();
            } catch (IOException e) {
                log.info("Configuration Error: " + e); //log.error ???
            }
        }
    }

    public boolean initializeServer(Configuration config) throws IOException {
        try {
            log.info("Binding To Port " + config.getIpsPort() + ", Please Wait  ...");
            serverSocket = new ServerSocket(config.getIpsPort());
            log.info("Server Started: " + serverSocket);
            log.info("Waiting For A Client ...");
            //
            configuration.setStatus(true);
            configuration.setRole(true);
            configuration.setIpsIp(getLocalHostAddress());
            configuration.setIpsPort(config.getIpsPort());
            //
            new Thread(() -> {
                try {
                    handlerConnection();
                } catch (IOException e) {
                    /*  throw new RuntimeException(e);*/
                }
            }).start();
            return true;
        } catch (IOException e1) {
            configuration.setStatus(false);
            log.info("Binding Failed");
            e1.printStackTrace();
            return false;
        }
    }

    public void setLogLevel(String loggerLevel) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        if ("info".equalsIgnoreCase(loggerLevel)) {
            root.setLevel(ch.qos.logback.classic.Level.INFO);
        } else if ("debug".equalsIgnoreCase(loggerLevel)) {
            root.setLevel(ch.qos.logback.classic.Level.DEBUG);
        } else if ("warn".equalsIgnoreCase(loggerLevel)) {
            root.setLevel(ch.qos.logback.classic.Level.WARN);
        } else if ("trace".equalsIgnoreCase(loggerLevel)) {
            root.setLevel(ch.qos.logback.classic.Level.TRACE);
        } else if ("error".equalsIgnoreCase(loggerLevel)) {
            root.setLevel(ch.qos.logback.classic.Level.ERROR);
        } else if ("off".equalsIgnoreCase(loggerLevel)) {
            root.setLevel(ch.qos.logback.classic.Level.OFF);
        } else {
            root.setLevel(ch.qos.logback.classic.Level.ALL);
            loggerLevel = "all";
        }
        log.info("Logger Level Set As " + loggerLevel);
    }

    @RequestMapping(value = "/close", method = RequestMethod.PUT)
    public String closeConnection() throws Exception {
        if(!this.configuration.getRole()){
            socket.close();
            //new function ? base method ?
            this.configuration.setHostIp(null);
            this.configuration.setHostPort(null);
            this.configuration.setStatus(false);
            log.info("Configuration To The Server Has Been Closed");
        }else {
           /* serverSocket.close();*/
            /*log.info(String.valueOf(serverConnectionFactory().getListener()));*/
            integrationMBeanExporter.stopActiveComponents(0);
            this.configuration.setIpsIp(null);
            this.configuration.setIpsPort(null);
            this.configuration.setStatus(false);
            this.configuration.setRole(false);
            this.configuration.setClientNumber(0);
            log.info("Server Has Been Shut Down");
        }
        if (this.configuration.getStatus()){
            return "redirect:edit";
        }else{
            return "redirect:";
        }
    }

    public String getLocalHostAddress() throws UnknownHostException {
        InetAddress ia = InetAddress.getLocalHost();
        String str = ia.getHostAddress();
        return str;
    }

    //TCP Server
    public IntegrationFlow commandServerFlow() throws UnknownHostException {
        IntegrationFlow integrationFlow =  IntegrationFlows.from((serverConnectionFactory()))
              /*  .handle((payload, handlers) -> "Thread: " + Thread.currentThread().getName() + " Server response...")*/
                /*.channel("inboundChannel")*/

                .transform(Transformers.objectToString())
                .log(m -> "payload=" + m.getPayload())
                .get();
        this.integrationFlowContext.registration(integrationFlow)
                .register();
        return integrationFlow;
    }

    public TcpInboundGateway serverConnectionFactory() throws UnknownHostException {
       serverCf = Tcp.netServer(configuration.getIpsPort()).get();

        log.info("Server Started: " + getLocalHostAddress() +" Port: "+ serverCf.getPort() );
        log.info("Waiting For A Client ...");
        configuration.setStatus(true);
        configuration.setRole(true);
        configuration.setIpsIp(getLocalHostAddress());
        configuration.setIpsPort(configuration.getIpsPort());

    serverCf.setApplicationEventPublisher(publisher);
    System.out.println("publisher" + serverCf.getApplicationEventPublisher());

        return Tcp.inboundGateway(serverCf).get();
        /*return serverConnectionFactory;*/
    }
    public MessageChannel inboundChannel() {return new DirectChannel();}
    public TcpInboundGateway inboundGateway(AbstractServerConnectionFactory serverConnectionFactory,
                                            MessageChannel inboundChannel) {
        TcpInboundGateway tcpInboundGateway = new TcpInboundGateway();
        tcpInboundGateway.setConnectionFactory(serverConnectionFactory);
        tcpInboundGateway.setRequestChannel(inboundChannel);
        return tcpInboundGateway;
    }
    //Save configuration
    public void saveParamChanges() {
        try {
            // create and set properties into yaml object
            DumperOptions options = new DumperOptions();
           /* options.setPrettyFlow(true);*/
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            Yaml yaml = new Yaml(options);
            Map<String, LinkedHashMap> dataMap = new LinkedHashMap<>();
            LinkedHashMap<String, Object> config = new LinkedHashMap<>();
            dataMap.put("configuration", config);
            config.put("hostIp", configuration.getHostIp());
            config.put("hostPort", configuration.getHostPort());
            config.put("ipsPort", configuration.getIpsPort());
            config.put("timeout", configuration.getTimeout());
            config.put("logLevel", configuration.getLogLevel());
            config.put("role", configuration.getRole());
            Writer writer = new FileWriter("src/main/resources/configuration.yml");
            yaml.dump(dataMap,writer);
            writer.close();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

   /* @Bean
    public EventListener eventListener() {
        return new EventListener();
    }
    public TcpReceivingChannelAdapter inbound() throws UnknownHostException {
        TcpReceivingChannelAdapter adapter = new TcpReceivingChannelAdapter();
        adapter.setConnectionFactory(serverConnectionFactory());
        adapter.setOutputChannelName("foo");
        return adapter;
    }
    public class EventListener implements ApplicationListener<TcpConnectionCloseEvent> {
        @Override
        public void onApplicationEvent(TcpConnectionCloseEvent event) {
            log.info(String.valueOf(event));
        }
    }*/
        /* HOST TCP/IP
        host = "10.145.48.96";
        port = 7777;
        */
}
