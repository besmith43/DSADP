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
    private long start;
    private long end;


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

            for (int j = 1; j <= iStr.length()/2; j++) {
                String tmp = iStr.substring(0, j);
                // System.out.printf("Checking SubString: %s\n", tmp);
                if (iStr.length()%tmp.length() != 0) {
                    continue;
                }

                List<String> subStringsArray = new ArrayList<String>();
                int index = 0;
                while (index < iStr.length()) {
                    subStringsArray.add(iStr.substring(index, Math.min(index+tmp.length(), iStr.length())));
                    index += tmp.length();
                }

                // System.out.printf("subStringsArray Length: %d\n", subStringsArray.size());

                var repeats = true;
                for (var subStr : subStringsArray) {
                    // System.out.printf("Comparing %s to %s\n", subStr, tmp);
                    if (!subStr.equals(tmp)) {
                        // System.out.printf("not equal breaking loop\n");
                        repeats = false;
                        break;
                    }
                }

                if (repeats) {
                    System.out.printf("adding %d as a repeating pattern\n",i);
                    invalidIDs.add(i);
                    break;
                }
            }
        }

        return invalidIDs;
    }
}


