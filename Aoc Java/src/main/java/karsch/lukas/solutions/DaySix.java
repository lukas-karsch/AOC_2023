package karsch.lukas.solutions;

import karsch.lukas.AocSolver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DaySix implements AocSolver {
    private final List<String> input;

    public DaySix(Stream<String> input) {
        this.input = input.toList();
    }

    @Override
    public void solve() {
        int resultPartOne = result(getNumsPartOne(input.get(0)), getNumsPartOne(input.get(1)));
        int resultPartTwo = result(getNumsPartTwo(input.get(0)), getNumsPartTwo(input.get(1)));

        System.out.format("Part 1: %d%n", resultPartOne);
        System.out.format("Part 2: %d%n", resultPartTwo);
    }

    private int result(List<Long> times, List<Long> distances) {
        return IntStream.range(0, times.size())
                .map(i -> {
                    int winning = 0;
                    long time = times.get(i);
                    for (int j = 0; j <= time; j++) {
                        long distance = j * (time - j);
                        if (distance > distances.get(i)) winning++;
                    }
                    return winning;
                })
                .reduce(1, (a, b) -> a * b);
    }

    private List<Long> getNumsPartOne(String line) {
        return Arrays.stream(line.split(":")[1].trim().split(" ")).filter(s -> !s.isEmpty()).map(Long::parseLong).toList();
    }

    private List<Long> getNumsPartTwo(String line) {
        return List.of(
                Long.parseLong(
                        Arrays.stream(line.split(":")[1].trim().split(" ")).filter(s -> !s.isEmpty()).reduce("", String::concat)
                )
        );
    }
}
