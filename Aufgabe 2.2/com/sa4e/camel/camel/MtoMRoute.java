package com.sa4e.camel.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.json.JsonObject;
import org.springframework.stereotype.Component;


@Component
public class MtoMRoute extends RouteBuilder {

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
                .to("grpc://localhost:9091/org.example.SolvingPuzzle?method=solvePuzzle&synchronous=true")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) {
                        String payload = exchange.getMessage().getBody(String.class);
                        //payload = "rID l1,l2,l3 l4,l5,l6 l7,l8,l9 time"
                        String[] pay = payload.split(" ");
                        JsonObject loesung = new JsonObject();
                        loesung.put("server_id", "Tobias Duentzer");
                        loesung.put("raetsel_id", pay[0]);
                        loesung.put("row1", "[" + pay[1] + "]");
                        loesung.put("row2", "[" + pay[2] + "]");
                        loesung.put("row3", "[" + pay[3] + "]");
                        loesung.put("time", pay[5]);
                        exchange.getMessage().setBody(loesung);
                    }
                })
                .marshal().json()
                .log("Received ${body}")
                .to("paho-mqtt5:Loesung?brokerUrl=tcp://localhost:1883");


    }
}


