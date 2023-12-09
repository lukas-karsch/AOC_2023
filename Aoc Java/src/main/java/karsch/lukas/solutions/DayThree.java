package karsch.lukas.solutions;

import karsch.lukas.AocSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DayThree implements AocSolver {
    private final List<String> input;
    private final Map<Symbol, List<Number>> numberToSymbol = new HashMap<>();

    public DayThree(Stream<String> input) {
        this.input = input.toList();
    }

    @Override
    public void solve() {
        partOne();
        partTwo();
    }

    private void partOne() {
        int sum = 0;
        for (int lineIndex = 0; lineIndex < input.size(); lineIndex++) {
            System.out.println("Line index: " + lineIndex);
            String line = input.get(lineIndex);
            int numStart = -1, numEnd = -1;
            for (int i = 0; i < line.length(); i++) {
                final char c = line.charAt(i);
                if (numStart == -1 && Character.isDigit(c)) {
                    numStart = i;
                } else if (numStart != -1) {
                    if (!Character.isDigit(c)) numEnd = i - 1;
                    else if (i == line.length() - 1) numEnd = i;
                }
                if (numStart != -1 && numEnd != -1) {
                    System.out.print("   Digit found from index " + numStart + " to " + numEnd);
                    Symbol adjacentSymbol = getAdjacentSymbol(lineIndex, numStart, numEnd);
                    if (adjacentSymbol != null) {
                        int digit = buildDigit(line, numStart, numEnd);
                        System.out.print(" (digit " + digit + " has adjacent symbol)");
                        sum += digit;
                        Number number = new Number(digit, lineIndex, numStart, numEnd);
                        var currentValue = numberToSymbol.getOrDefault(adjacentSymbol, new ArrayList<>());
                        currentValue.add(number);
                        numberToSymbol.put(
                                adjacentSymbol,
                                currentValue
                        );
                    }
                    System.out.println();
                    numStart = -1;
                    numEnd = -1;
                }
            }
        }
        System.out.println("*************************************");
        System.out.println("Total sum of engine part numbers: " + sum);
    }

    private void partTwo() {
        int sum = numberToSymbol.keySet().stream()
                .filter(symbol -> symbol.value() == '*')
                .filter(gear -> numberToSymbol.get(gear).size() == 2)
                .map(gear ->
                        numberToSymbol.get(gear).stream()
                                .reduce(
                                        1,
                                        (curr, number) -> curr * number.value(),
                                        Integer::sum
                                )
                )
                .reduce(0, Integer::sum);
        System.out.println("*************************************");
        System.out.println("Total sum of gear ratios: " + sum);
    }

    private Symbol getAdjacentSymbol(int lineIndex, int start, int end) {
        Symbol symbolLeft = symbolLeft(lineIndex, start);
        if (symbolLeft != null) return symbolLeft;
        Symbol symbolRight = hasSymbolRight(lineIndex, end);
        if (symbolRight != null) return symbolRight;
        Symbol symbolBottomAndDiagonal = hasSymbolBottomAndDiagonal(lineIndex, start, end);
        if (symbolBottomAndDiagonal != null) return symbolBottomAndDiagonal;
        return hasSymbolTopAndDiagonal(lineIndex, start, end);
    }

    private Symbol hasSymbolRight(int lineIndex, int end) {
        if (end == input.get(0).length() - 1) return null;
        int symbolIndex = end + 1;
        char c = input.get(lineIndex).charAt(symbolIndex);
        if (c != '.') {
            return new Symbol(c, lineIndex, symbolIndex);
        }
        return null;
    }

    private Symbol symbolLeft(int lineIndex, int start) {
        if (start == 0) return null;
        int symbolIndex = start - 1;
        char c = input.get(lineIndex).charAt(symbolIndex);
        if (c != '.') {
            return new Symbol(c, lineIndex, symbolIndex);
        }
        return null;
    }

    private Symbol hasSymbolTopAndDiagonal(int lineIndex, int start, int end) {
        if (lineIndex == 0) return null;
        String lineAbove = input.get(lineIndex - 1);
        for (int i = Math.max(0, start - 1); i <= Math.min(lineAbove.length() - 1, end + 1); i++) {
            char c = lineAbove.charAt(i);
            if (c != '.') {
                return new Symbol(c, lineIndex - 1, i);
            }
        }
        return null;
    }

    private Symbol hasSymbolBottomAndDiagonal(int lineIndex, int start, int end) {
        if (lineIndex == input.size() - 1) return null;
        String lineBelow = input.get(lineIndex + 1);
        for (int i = Math.max(0, start - 1); i <= Math.min(lineBelow.length() - 1, end + 1); i++) {
            char c = lineBelow.charAt(i);
            if (c != '.') {
                return new Symbol(c, lineIndex + 1, i);
            }
        }
        return null;
    }

    private int buildDigit(String line, int start, int end) {
        StringBuilder digitBuilder = new StringBuilder();
        for (int j = start; j <= end; j++) {
            digitBuilder.append(line.charAt(j));
        }
        return Integer.parseInt(digitBuilder.toString());
    }
}

record Number(int value, int rowIndex, int start, int end) {
}

record Symbol(char value, int rowIndex, int colIndex) {
}
