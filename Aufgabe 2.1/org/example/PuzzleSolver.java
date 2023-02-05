package org.example;

import java.io.FileWriter;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class PuzzleSolver implements Client {
    static Output output;

    public PuzzleSolver(Output output) {
        PuzzleSolver.output = output;
    }

    public void setService(Output output) {
        PuzzleSolver.output = output;
    }

    @Override
    public void main(String[] puzzle) throws IOException {
        FileWriter writer = new FileWriter("output.txt");
        writer.write("");
        writer.close();

        int[] map = new int[26];
        Arrays.fill(map, -2);
        boolean[] usedNum = new boolean[10];

        StringBuilder uniq = new StringBuilder();
        for (String value : puzzle) {

            for (int i = 0; i < value.length(); i++) {
                char ch = value.charAt(i);
                if (map[ch - 'A'] == -2) {
                    map[ch - 'A'] = -1;
                    uniq.append(ch);
                }
            }
        }

        SolutionBoolean run = posSolution(uniq.toString(), 0, map, usedNum, puzzle);

        if (run.getSecond()) {
            int[] map2 = run.getFirst();
            for (int i = 0; i < uniq.length(); i++) {
                System.out.print(uniq.charAt(i) + "=" + getNum(map2, String.valueOf(uniq.charAt(i))) + " ");
            }
        } else {
            System.out.println("No possible solution.");
        }
    }

    public static SolutionBoolean posSolution(String unique, int idx, int[] charIntMap, boolean[] usedNumbers, String[] puzzle) throws IOException {
        if (idx == unique.length()) {
            if (isValid(charIntMap, puzzle)) {
                return new SolutionBoolean(charIntMap, true);
            }
            return new SolutionBoolean(charIntMap, false);
        }


        for (int i = 0; i < usedNumbers.length; i++) {
            if (!usedNumbers[i]) {

                charIntMap[unique.charAt(idx) - 'A'] = i;
                usedNumbers[i] = true;

                if (posSolution(unique, idx + 1, charIntMap, usedNumbers, puzzle).getSecond()) {
                    return new SolutionBoolean(charIntMap, true);
                }

                usedNumbers[i] = false;
                charIntMap[unique.charAt(idx) - 'A'] = -1;
            }
        }
        return new SolutionBoolean(charIntMap, false);
    }

    public static boolean isValid(int[] map, String[] puzzle) throws IOException {
        int p1 = getNum(map, puzzle[0]);
        int p2 = getNum(map, puzzle[1]);
        int p3 = getNum(map, puzzle[2]);
        int p4 = getNum(map, puzzle[3]);
        int p5 = getNum(map, puzzle[4]);
        int p6 = getNum(map, puzzle[5]);
        int p7 = getNum(map, puzzle[6]);
        int p8 = getNum(map, puzzle[7]);
        int p9 = getNum(map, puzzle[8]);

        boolean b = p1 + p2 == p3
                && p4 + p5 == p6
                && p7 + p8 == p9
                && p1 + p4 == p7
                && p2 + p5 == p8
                && p3 + p6 == p9;

        String msg = puzzle[0] + "=" + p1 + " " +
                puzzle[1] + "=" + p2 + " " +
                puzzle[2] + "=" + p3 + " " +
                puzzle[3] + "=" + p4 + " " +
                puzzle[4] + "=" + p5 + " " +
                puzzle[5] + "=" + p6 + " " +
                puzzle[6] + "=" + p7 + " " +
                puzzle[7] + "=" + p8 + " " +
                puzzle[8] + "=" + p9 + " ";
        if (b) {
            msg += " = Valid solution!";
        } else {
            msg += " = No valid solution!";
        }

        if (output.getOutput().equals("File")) {
            FileWriter fileWriter = new FileWriter("output.txt", true);
            fileWriter.write(msg);
            fileWriter.write(System.lineSeparator());
            fileWriter.close();
        }

        if (output.getOutput().equals("Console")) {
            System.out.println(msg + output.getOutput());
        }

        if (output.getOutput().equals("MQTT")) {
            String broker = "tcp://localhost:1883";
            String topic = "test";
            String clientid = "pub_client";
            String content = msg;
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
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return b;
    }

    public static int getNum(int[] map, String s1) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            sb.append(map[s1.charAt(i) - 'A']);
        }
        return Integer.parseInt(sb.toString());
    }
}















