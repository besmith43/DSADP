import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String inputFile = "";

        if (args.length > 0 && Files.exists(Paths.get(args[0]))) {
            inputFile = args[0];
        } else {
            System.out.println("you need to give me a file to work on");
            System.exit(1);
        }

        List<Long> foundInvalidIDs = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(inputFile))) {
            for (var line : stream.toArray()) {
                String[] ranges = line.toString().split(",");

                for (var range : ranges) {
                    System.out.println(range);
                    ProductIDRange idRange = new ProductIDRange(range);
                    var tmpResult = idRange.FindInvalidIDs();

                    System.out.printf("Found %d number of repeating product ids\n", tmpResult.size());
                    foundInvalidIDs.addAll(tmpResult);
                }
            }
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
            e.printStackTrace(System.out);
        }

        long sum = 0;

        for (var num : foundInvalidIDs) {
            sum += num;
        }

        System.out.printf("Found a total of %d invalid product ids\n", foundInvalidIDs.size());
        System.out.printf("Sum of Invalid Product IDs: %d", sum);
    }
}

class ProductIDRange {
    private final long start;
    private final long end;


    public ProductIDRange(String input) {
        String[] tmp = input.split("-");
        start = Long.parseLong(tmp[0]);
        end = Long.parseLong(tmp[1]);
    }

    public List<Long> FindInvalidIDs() {
        List<Long> invalidIDs = new ArrayList<>();

        for (long i = start; i <= end; i++) {
            String iStr = String.valueOf(i);
            // System.out.printf("Checking %d\n", i);

            if (iStr.length()%2 != 0) {
                continue;
            }

            int halfLength = iStr.length()/2;

            // System.out.printf("Half Length: %d\n", halfLength);

            String frontHalf = iStr.substring(0, halfLength);
            String backHalf = iStr.substring(halfLength);

            // System.out.printf("Comparing %s to %s\n", frontHalf, backHalf);
            if (frontHalf.equals(backHalf)) {
                invalidIDs.add(i);
            }
        }

        return invalidIDs;
    }
}


