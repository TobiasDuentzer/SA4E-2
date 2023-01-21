package org.example;

import java.io.FileWriter;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.concurrent.TimeUnit;

public class PuzzleSolver implements Client{
    Output output;

    public PuzzleSolver(Output output) {
        this.output = output;
    }
    public void setService(Output output) {
        this.output = output;
    }
    @Override
    public void main(String puzzle) throws IOException {

        FileWriter writer = new FileWriter("output.txt");
        writer.write("");
        writer.close();

        for (int i=0;i<30;i++){





            if (output.getOutput().equals("File")) {
                FileWriter fileWriter = new FileWriter("output.txt", true);
                fileWriter.write(puzzle);
                fileWriter.write(System.lineSeparator());
                fileWriter.close();
            }

            if (output.getOutput().equals("Console")) {
                System.out.println(puzzle + output.getOutput());
            }

            if (output.getOutput().equals("MQTT")) {
                    String broker = "tcp://localhost:1883";
                    String topic = "test";
                    String clientid = "pub_client";
                    String content = puzzle;
                    int qos = 0;

                    try {
                        MqttClient client = new MqttClient(broker, clientid, new MemoryPersistence());
                        MqttConnectOptions options = new MqttConnectOptions();
                        client.connect(options);

                        MqttMessage message = new MqttMessage(content.getBytes());
                        message.setQos(qos);
                        client.publish(topic, message);

                        client.disconnect();
                        client.close();
                    }catch (MqttException e) {
                        throw new RuntimeException(e);
                    }



            }

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

