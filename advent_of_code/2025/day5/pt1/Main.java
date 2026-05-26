import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
        List<Long> ingredientIDs = new ArrayList<>();
        boolean rangesDone = false;

        try (Stream<String> stream = Files.lines(Paths.get(inputFile))) {
            for (var line : stream.toArray()) {
                if (rangesDone) {
                    ingredientIDs.add(Long.valueOf(line.toString()));
                } else if (line.toString().isEmpty()) {
                    rangesDone = true;
                } else {
                    ranges.add(new FreshRange(line.toString()));
                }
            }
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
            e.printStackTrace(System.out);
            System.exit(1);
        }

        int freshnessCount = 0;

        for (Long id : ingredientIDs) {
            for (FreshRange range : ranges) {
                if (range.IsFresh(id)) {
                    freshnessCount++;
                    break;
                }
            }
        }

        System.out.printf("Fresh Ingredient Count: %d\n", freshnessCount);
    }
}

class FreshRange {
    private final Long start;
    private final Long end;

    public FreshRange(String line) {
        String[] splitLine = line.split("-");

        start = Long.valueOf(splitLine[0]);
        end = Long.valueOf(splitLine[1]);
    }

    public boolean IsFresh(Long x) {
        return x >= start && x <= end;
    }
}