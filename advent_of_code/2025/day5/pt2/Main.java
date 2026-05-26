import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {
        var inputFile = "";

        if (args.length > 0 && Files.exists(Paths.get(args[0]))) {
            inputFile = args[0];
        } else {
            System.out.println("you need to give me a file to work on");
            System.exit(1);
        }

        List<FreshRange> ranges = new ArrayList<>();
        Long total = 0L;

        try (Stream<String> stream = Files.lines(Paths.get(inputFile))) {
            for (var line : stream.toArray()) {
                if (line.toString().isEmpty()) {
                    break;
                } else {
                    ranges.add(new FreshRange(line.toString()));
                }
            }
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
            e.printStackTrace(System.out);
            System.exit(1);
        }

        Collections.sort(ranges);

        // for (var range : ranges) {
            // System.out.println(range.toString());
        // }

        // System.exit(0);

        for (int i = 0; i < ranges.size(); i++) {
            System.out.println("============================= Starting Comp ===============================");
            FreshRange currentRange = ranges.get(i);
            System.out.printf("Currrent Range: %s\n", currentRange.toString());

            if (i == 0) {
                System.out.printf("Counting first range: %s\n", currentRange.toString());
                total += currentRange.End() - currentRange.Start() + 1;
                System.out.printf("Current Total: %d\n", total);
                System.out.println("============================= Ending Comp ===============================");
                continue;
            }

            FreshRange previousRange = ranges.get(i-1);
            System.out.printf("Previous Range: %s\n", previousRange.toString());

            // boolean partialMatch = false;

            if (currentRange.Start() == previousRange.Start()) {
                System.out.println("Current Start equals Previous Start");
                if (currentRange.End() > previousRange.End()) {
                    System.out.println("Current End is bigger than Previous End");
                    System.out.printf("Counting partial match: %d to %d\n", currentRange.End(), previousRange.End());
                    total += currentRange.End() - previousRange.End();
                }
            } else if (currentRange.Start() >= previousRange.Start()){
                System.out.println("Current Start is bigger than Previous Start");
                if (currentRange.Start() > previousRange.End()) {
                    System.out.println("Current Start is bigger than Previous End");
                    System.out.printf("Counting unmatched range: %s\n", currentRange.toString());
                    total += currentRange.End() - currentRange.Start() + 1;
                } else if (currentRange.Start() <= previousRange.End()) {
                    System.out.println("Current Start is less than or equal to Previous End");
                    System.out.printf("Counting partial match: %d to %d\n", currentRange.End(), previousRange.End());
                    total += currentRange.End() - previousRange.End();
                }
            } else {
                System.out.println("this should never happen");
                System.out.printf("is less than: %b\n", currentRange.Start() < previousRange.Start());
                System.out.printf("is less than or equal to: %b\n", currentRange.Start() <= previousRange.Start());
                System.out.printf("Equals: %b\n", currentRange.Start() == previousRange.Start());
                System.out.printf("is greater than: %b\n", currentRange.Start() > previousRange.Start());
                System.out.printf("is greater than or equal to: %b\n", currentRange.Start() >= previousRange.Start());
                System.out.printf("Previous Start: %d\n", previousRange.Start());
                System.out.printf("Current Start:  %d\n", currentRange.Start());
                System.out.printf("I don't trust it: %b\n", true);
                System.exit(1);
            }

            // if (previousRange.Start() < currentRange.Start() &&
                    // currentRange.Start() < previousRange.End() &&
                    // previousRange.Start() < currentRange.End() &&
                    // currentRange.End() > previousRange.End()) {
                // System.out.printf("Counting partial match: %d to %d\n", currentRange.End(), previousRange.End());
                // Long tmp = currentRange.End() - previousRange.End();
                // total += tmp;
                // System.out.printf("Current Total: %d\n", total);
                // partialMatch = true;
            // }

            // if (currentRange.Start() > previousRange.Start() &&
                    // previousRange.Start() < currentRange.End() &&
                    // currentRange.Start() < previousRange.End() &&
                    // previousRange.End() < currentRange.End()) {
                // System.out.printf("Counting partial match: %d to %d\n", previousRange.Start(), currentRange.Start());
                // Long tmp = previousRange.Start() - currentRange.Start();
                // total += tmp;
                // System.out.printf("Current Total: %d\n", total);
                // partialMatch = true;
            // }

            // if (!partialMatch) {
                // System.out.printf("Counting unmatched range: %s\n", currentRange.toString());
                // total += currentRange.End() - currentRange.Start() + 1;
                // System.out.printf("Current Total: %d\n", total);
            // }
            System.out.printf("Current Total: %d\n", total);
            System.out.println("============================= Ending Comp ===============================");
        }

        System.out.printf("Total Fresh IDs: %d\n", total);
    }

    public static void PrintMemory() {
        Runtime rt = Runtime.getRuntime();
        long totalMemory = rt.totalMemory();
        long freeMemory = rt.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = rt.maxMemory();

        System.out.println("Used Memory: " + usedMemory / (1024 * 1024) + " MB");
        System.out.println("Free Memory: " + freeMemory / (1024 * 1024) + " MB");
        System.out.println("Total Memory: " + totalMemory / (1024 * 1024) + " MB");
        System.out.println("Max Memory: " + maxMemory / (1024 * 1024) + " MB");
    }
}

class FreshRange implements Comparable<FreshRange> {
    private Long start;
    private Long end;

    public FreshRange(String line) {
        String[] splitLine = line.split("-");

        start = Long.valueOf(splitLine[0]);
        end = Long.valueOf(splitLine[1]);
    }

    public boolean IsFresh(Long x) {
        return x >= start && x <= end;
    }

    public List<Long> GetIngredientIDs() {
        List<Long> ingredientIDs = new ArrayList<>();

        for (Long i = start; i <= end; i++) {
            ingredientIDs.add(i);
        }

        return ingredientIDs;
    }

    public Long Start() {
        return start;
    }

    public void Start(Long newStart) {
        start = newStart;
    }

    public Long End() {
        return end;
    }

    public void End(Long newEnd) {
        end = newEnd;
    }

    public Long Size() {
        return end - start + 1;
    }

    public String toString() {
        return String.format("Start: %d End: %d", start, end);
    }

    // -1 is less then
    // 0 is equal to
    // 1 is greater than
    public int compareTo(FreshRange range) {
        int startComp = 0;
        if (start < range.Start()) {
            startComp = -1;
        } else if (start > range.Start()) {
            startComp = 1;
        }

        int endComp = 0;
        if (end < range.End()) {
            endComp = -1;
        } else if (end > range.End()) {
            endComp = 1;
        }

        if (startComp == 0) {
            return endComp;
        } else if (endComp == 0) {
            return startComp;
        } else {
            return startComp;
        }
    }
}
