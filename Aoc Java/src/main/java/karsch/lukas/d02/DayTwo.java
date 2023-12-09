package karsch.lukas.d02;

import karsch.lukas.AocSolver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DayTwo implements AocSolver {
    private final List<String> input;

    private final static int RED_CUBES = 12;
    private final static int GREEN_CUBES = 13;
    private final static int BLUE_CUBES = 14;

    public DayTwo(Stream<String> input) {
        this.input = input.toList();
    }

    public void solve() {
        partOne(input);
        partTwo(input);
    }

    private void partOne(List<String> input) {
        int sum = input.stream()
                .map(line -> {
                    int gameId = Integer.parseInt(line.split(":")[0].split(" ")[1].trim());
                    var isGameValid = Arrays.stream(line.split(":")[1].split(";")).allMatch(
                            gameSection -> {
                                var split = gameSection.split(",");
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
                    if (isGameValid) return gameId;
                    return 0;
                }).reduce(0, Integer::sum);
        System.out.println("Sum of valid game IDs is " + sum);
    }

    private void partTwo(List<String> input) {
        int sumOfPowers = input.stream().
                map(line -> {
                    String gameContent = line.split(":")[1].trim();
                    final int[] cubes = new int[3];
                    Arrays.stream(gameContent.split(";")).forEach(
                            section -> {
                                var split = section.split(",");
                                for (String s : split) {
                                    String[] amountAndColor = s.trim().split(" ");
                                    if (amountAndColor[1].trim().equals("red")) {
                                        cubes[0] = Math.max(cubes[0], Integer.parseInt(amountAndColor[0]));
                                    } else if (amountAndColor[1].trim().equals("green")) {
                                        cubes[1] = Math.max(cubes[1], Integer.parseInt(amountAndColor[0]));
                                    } else {
                                        cubes[2] = Math.max(cubes[2], Integer.parseInt(amountAndColor[0]));
                                    }
                                }
                            });
                    return cubes[0] * cubes[1] * cubes[2];
                })
                .reduce(0, Integer::sum);
        System.out.println("Sum of powers is: " + sumOfPowers);
    }
}
