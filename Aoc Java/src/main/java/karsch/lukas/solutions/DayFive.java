package karsch.lukas.solutions;

import karsch.lukas.AocSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DayFive implements AocSolver {
    private final List<String> input;

    public DayFive(Stream<String> input) {
        this.input = Arrays.stream(input.reduce("", (a, b) -> a + "\n" + b).split("\n\n")).toList();
    }

    @Override
    public void solve() {
        List<Long> seedMapping = Arrays.stream(
                        input.get(0)
                                .split(":")[1].trim()
                                .split(" "))
                        .map(Long::parseLong)
                        .collect(Collectors.toCollection(ArrayList::new));
        IntStream.range(1, input.size())
                .forEach(i -> {
                    var currentMappings = getMappings(i);
                    for (int s = 0; s < seedMapping.size(); s++) {
                        for (Mapping currentMapping : currentMappings) {
                            long curr = seedMapping.get(s);
                            long newValue = getNewValueForMapping(
                                    curr,
                                    currentMapping);
                            seedMapping.set(s, newValue);
                            if (curr != newValue) break;
                        }
                    }
                });
        long min = seedMapping.stream().reduce(Math::min).orElseThrow(); //always present
        System.out.println("Minimum location: " + min);
    }

    private long getNewValueForMapping(long currentValue, Mapping mapping) {
        long diff = currentValue - mapping.source();
        if(diff >= 0 && diff < mapping.range()) return mapping.destination() + diff;
        return currentValue;
    }

    private List<Mapping> getMappings(int index) {
        var split = input.get(index).split("\n");
        return IntStream.range(1, split.length)
                .mapToObj(i -> getSingleMapping(split[i]))
                .toList();
    }

    private Mapping getSingleMapping(String line) {
        var splitLine = Arrays.stream(line.split(" ")).filter(s -> !toString().isBlank()).map(Long::parseLong).toList();
        return new Mapping(
                splitLine.get(0),
                splitLine.get(1),
                splitLine.get(2)
        );
    }
}

record Mapping(long destination, long source, long range){}
