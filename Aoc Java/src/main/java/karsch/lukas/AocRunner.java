package karsch.lukas;

import karsch.lukas.d02.DayTwo;
import karsch.lukas.d03.DayThree;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class AocRunner {
    public void runDay(int day) {
        var input = getInput(day);
        getSolver(day, input).solve();
    }

    private Stream<String> getInput(int day) {
        String filename = day + ".txt";
        InputStream input = getClass().getClassLoader().getResourceAsStream(filename);
        if(input == null) {
            throw new RuntimeException("Could not find file: " + filename);
        }
        return new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8)).lines();
    }

    private AocSolver getSolver(int day, Stream<String> input) {
        return switch (day) {
            case 2 -> new DayTwo(input);
            case 3 -> new DayThree(input);
            default -> throw new IllegalArgumentException("Day " + day + " not implemented");
        };
    }
}
