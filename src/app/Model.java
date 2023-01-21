package app;
import app.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Model implements Runnable{
private ProcessBuilder builder;
private Controller c;
    BufferedReader r;
    String line;
    public  Model(Controller c){
        this.c = c;
        builder = new ProcessBuilder(c.getPath());
        builder.redirectErrorStream(true);
        executeCommand();

    }
    private synchronized void executeCommand(){
        Process p;
        try {
            p = builder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        r = new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

    @Override
    public void run() {
        c.getViewer().outArea.setText(null);
        while (true) {
            try {
                line = r.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line == null) { break;
            }
            System.out.println(line);
            c.setOutput(line);
            c.getViewer().getOutArea().append(line +"\n");

        }
    }
}

