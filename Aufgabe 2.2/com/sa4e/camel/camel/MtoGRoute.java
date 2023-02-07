package com.sa4e.camel.camel;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.json.JsonObject;
import org.springframework.stereotype.Component;


@Component
public class MtoGRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("paho-mqtt5:Zahlenraetsel?brokerUrl=tcp://localhost:1883")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange){
                        String payload = exchange.getMessage().getBody(String.class);
                        String[] pay = payload.split("\"");
                        String[] t = pay[6].split(",");
                        t[0] = t[0].replace(":","").replace(" ","");
                        String payP = t[0]+ " "
                                + pay[9]+ ","
                                + pay[11]+ ","
                                + pay[13]+ ","
                                + pay[17]+ ","
                                + pay[19]+ ","
                                + pay[21]+ ","
                                + pay[25]+ ","
                                + pay[27]+ ","
                                + pay[29];

                        exchange.getMessage().setBody(payP);    //payP = "rID v1,v2,v3,v4..."
                    }
                })
                .log("Received ${body}")
                .to("grpc://localhost:9091/org.example.SolvingPuzzle?method=solvePuzzle&synchronous=true");


    }
}


