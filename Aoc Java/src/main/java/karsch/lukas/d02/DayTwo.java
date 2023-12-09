package karsch.lukas.d02;

import karsch.lukas.AocSolver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DayTwo implements AocSolver {
    private final Stream<String> input;

    private final static int RED_CUBES = 12;
    private final static int GREEN_CUBES = 13;
    private final static int BLUE_CUBES = 14;

    public DayTwo(Stream<String> input) {
        this.input = input;
    }

    public void solve() {
        int sum = input.map(line -> {
            int gameId = Integer.parseInt(line.split(":")[0].split(" ")[1].trim());
            var isGameValid = Arrays.stream(line.split(":")[1].split(";")).allMatch(
                    input -> {
                        System.out.print(input);
                        var split = input.split(",");
                        for (String s : split) {
                            String[] amountAndColor = s.trim().split(" ");
                            if (amountAndColor[1].trim().equals("blue")) {
                                if (Integer.parseInt(amountAndColor[0]) > BLUE_CUBES) return false;
                            } else if (amountAndColor[1].trim().equals("red")) {
                                if (Integer.parseInt(amountAndColor[0]) > RED_CUBES) return false;
                            } else {
                                if (Integer.parseInt(amountAndColor[0]) > GREEN_CUBES) return false;
                            }
                        }
                        return true;
                    }
            );
            System.out.println(line + "   is valid: " + isGameValid);
            if(isGameValid) return gameId;
            return 0;
        }).reduce(0, Integer::sum);
        System.out.println("Sum of valid game IDs is " + sum);
    }
}
