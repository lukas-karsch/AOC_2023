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
        var times = getNums(input.get(0));
        var distances = getNums(input.get(1));

        int result = IntStream.range(0, times.size())
                .map(i -> {
                    int winning = 0;
                    int time = times.get(i);
                    for (int j = 0; j <= time; j++) {
                        int distance = j * (time - j);
                        if (distance > distances.get(i)) winning++;
                    }
                    return winning;
                })
                .reduce(1, (a, b) -> a * b);

        System.out.format("Result is: %d", result);
    }

    private List<Integer> getNums(String line) {
        return Arrays.stream(line.split(":")[1].trim().split(" ")).filter(s -> !s.isEmpty()).map(Integer::parseInt).toList();
    }
}
