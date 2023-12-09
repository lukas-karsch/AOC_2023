package karsch.lukas.d04;

import karsch.lukas.AocSolver;

import java.util.*;
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
        final Map<Integer, Integer> cardAmounts = new HashMap<>();
        int sum = IntStream.range(0, input.size())
                .mapToObj(index ->
                    new Card(index, input.get(index).split(":")[1].trim())
                )
                .map(card -> {
                    String[] split = card.content().split("\\|");
                    var winning = getNumbers(split[0]);
                    var ownNumbers = getNumbers(split[1]);

                    ownNumbers.retainAll(winning);
                    int lineValue = getLineValue(ownNumbers);
                    updateCardAmounts(
                            cardAmounts,
                            card.index(),
                            Math.min(card.index() + ownNumbers.size(), input.size()),
                            cardAmounts.getOrDefault(card.index(), 1)
                    );

                    System.out.format("Card %d is worth %d points%n", card.index(), lineValue);
                    return lineValue;
                })
                .reduce(0, Integer::sum);

        System.out.println("***************************");
        System.out.format("[Part 1] Sum of card values is %d%n", sum);
        int totalCards = cardAmounts.values().stream().reduce(0, Integer::sum);
        System.out.format("[Part 2] Total cards: %d%n", totalCards);
    }

    private void updateCardAmounts(Map<Integer, Integer> cardAmounts, int index, int until, int amount) {
        cardAmounts.put(
                index,
                cardAmounts.getOrDefault(index, 1)
        );
        for (int i = index + 1; i <= until; i++) {
            cardAmounts.put(
                    i,
                    cardAmounts.getOrDefault(i, 1) + amount
            );
        }
    }

    private int getLineValue(Set<Integer> retainedNumbers) {
        int lineValue = 0;
        if(!retainedNumbers.isEmpty()) {
            lineValue = (int) Math.pow(2, retainedNumbers.size() - 1);
        }
        return lineValue;
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

record Card(int index, String content){}
