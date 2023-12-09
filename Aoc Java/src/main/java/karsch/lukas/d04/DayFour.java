package karsch.lukas.d04;

import karsch.lukas.AocSolver;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DayFour implements AocSolver {
    private final List<String> input;

    public DayFour(Stream<String> input) {
        this.input = input.toList();
    }

    @Override
    public void solve() {
        partOne();
        partTwo();
    }

    private void partOne() {
        int sum = input.stream()
                .map(line -> {
                    String[] split = line.split(":");
                    System.out.print(split[0]);
                    return split[1].trim();
                })
                .map(content -> {
                    String[] split = content.split("\\|");
                    var winning = getNumbers(split[0]);
                    var ownNumbers = getNumbers(split[1]);

                    ownNumbers.retainAll(winning);
                    int lineValue = getLineValue(ownNumbers);
                    System.out.format(" is worth %d points%n", lineValue);
                    return lineValue;
                })
                .reduce(0, Integer::sum);

        System.out.println("***************************");
        System.out.format("Sum of card values is %d%n", sum);
    }

    private int getLineValue(Set<Integer> retainedNumbers) {
        int lineValue = 0;
        if(!retainedNumbers.isEmpty()) {
            lineValue = (int) Math.pow(2, retainedNumbers.size() - 1);
        }
        return lineValue;
    }

    private void partTwo() {

    }

    private Set<Integer> getNumbers(String nums) {
        return Arrays.stream(
                        nums.trim().split(" ")
                )
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }
}
