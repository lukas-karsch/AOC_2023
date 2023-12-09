package karsch.lukas.d03;

import karsch.lukas.AocSolver;

import java.util.List;
import java.util.stream.Stream;

public class DayThree implements AocSolver {
    private final List<String> input;

    public DayThree(Stream<String> input) {
        this.input = input.toList();
    }

    @Override
    public void solve() {
        partOne();
    }

    private void partOne() {
        int sum = 0;
        for (int lineIndex = 0; lineIndex < input.size(); lineIndex++) {
            System.out.println("Line index: " + lineIndex);
            String line = input.get(lineIndex);
            int numStart = -1, numEnd = -1;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if(numStart == -1 && Character.isDigit(c)) {
                    numStart = i;
                }
                else if(numStart != -1) {
                    if(!Character.isDigit(c)) numEnd = i - 1;
                    else if(i == line.length() -1) numEnd = i;
                }
                if(numStart != -1 && numEnd != -1) {
                    System.out.print("   Digit found from index " + numStart + " to " + numEnd);
                    boolean hasAdjacentSymbol = hasAdjacentSymbol(lineIndex, numStart, numEnd);
                    if(hasAdjacentSymbol) {
                        StringBuilder digitBuilder = new StringBuilder();
                        for (int j = numStart; j <= numEnd; j++) {
                            digitBuilder.append(line.charAt(j));
                        }
                        String digit = digitBuilder.toString();
                        System.out.print(" (digit " + digit + " has adjacent symbol)");
                        sum += Integer.parseInt(digit);
                    }
                    System.out.println();
                    numStart = -1;
                    numEnd = -1;
                }
            }
        }
        System.out.println("Total sum of engine part numbers: " + sum);
    }

    private boolean hasAdjacentSymbol(int lineIndex, int start, int end) {
        return symbolLeft(lineIndex, start, end)
                || hasSymbolRight(lineIndex, start, end)
                || hasSymbolBottomAndDiagonal(lineIndex, start, end)
                || hasSymbolTopAndDiagonal(lineIndex, start, end);
    }

    private boolean hasSymbolRight(int lineIndex, int start, int end) {
        if(end == input.get(0).length() - 1) return false;
        return input.get(lineIndex).charAt(end + 1) != '.';
    }

    private boolean symbolLeft(int lineIndex, int start, int end) {
        if(start == 0) return false;
        return input.get(lineIndex).charAt(start - 1) != '.';
    }

    private boolean hasSymbolTopAndDiagonal(int lineIndex, int start, int end) {
        if(lineIndex == 0) return false;
        String lineAbove = input.get(lineIndex -1);
        for(int i = Math.max(0, start - 1); i <= Math.min(lineAbove.length() - 1, end + 1); i++) {
            if(lineAbove.charAt(i) != '.') return true;
        }
        return false;
    }

    private boolean hasSymbolBottomAndDiagonal(int lineIndex, int start, int end) {
        if(lineIndex == input.size() -1) return false;
        String lineBelow = input.get(lineIndex + 1);
        for(int i = Math.max(0, start - 1); i <= Math.min(lineBelow.length() - 1, end + 1); i++) {
            if(lineBelow.charAt(i) != '.') return true;
        }
        return false;
    }
}
