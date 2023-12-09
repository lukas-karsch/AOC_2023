package karsch.lukas;

import karsch.lukas.d02.DayTwo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class AocRunner {
    public void runDay(int day) {
        var input = getInput(day);
        switch (day) {
            case 2 -> runDay2(input);
        }
    }

    private void runDay2(Stream<String> input) {
        System.out.println("running day 2");
        DayTwo solver = new DayTwo(input);
        solver.solve();
    }

    private Stream<String> getInput(int day) {
        String filename = day + ".txt";
        InputStream input = getClass().getClassLoader().getResourceAsStream(filename);
        if(input == null) {
            throw new RuntimeException("Could not find file: " + filename);
        }
        return new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8)).lines();
    }
}
