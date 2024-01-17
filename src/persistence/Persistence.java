package persistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Persistence {
    private String fileName;

    public Persistence(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String> loadFile() throws IOException, FileNotFoundException {

        ArrayList<String> lines = new ArrayList<String>();
        BufferedReader buffer = new BufferedReader(new FileReader(fileName));
        String line = "";

        while ((line = buffer.readLine()) != null) {
            lines.add(line);
        }

        buffer.close();

        return lines;
    }

}
