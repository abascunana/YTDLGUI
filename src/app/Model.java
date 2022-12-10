package app;
import app.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Model {

    public Model(Controller c){
        ProcessBuilder builder = new ProcessBuilder(c.getPath());
        builder.redirectErrorStream(true);
        Process p;

        {
            try {
                p = builder.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            try {
                line = r.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line == null) { break; }
            System.out.println(line);
        }
    }

}

