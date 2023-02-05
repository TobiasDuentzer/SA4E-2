package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String o;
        try {
            o = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] puzzle = {"A","A","B","C","C","D","E","E","F"};

        Output output = new OutputConsole();
        PuzzleSolver client = new PuzzleSolver(output);
        if (o.equals("1")) {
            client.setService(new OutputFile());
        }
        if (o.equals("2")) {
            client.setService(new OutputMQTT());
        }

        client.main(puzzle);
    }


}