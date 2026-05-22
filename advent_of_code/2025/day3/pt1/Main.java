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

        List<Integer> joltageArray = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(inputFile))) {
            for (var line : stream.toArray()) {
                Battery battery = new Battery(line.toString());
                var result = battery.FindLargestJoltage();
                joltageArray.add(result);
            }
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
            e.printStackTrace(System.out);
        }

        int sum = 0;

        for (var joltage : joltageArray) {
            System.out.printf("Joltage Result: %d\n", joltage);
            sum += joltage;
        }

        System.out.printf("Total: %d\n", sum);
    }
}

class Battery {
    private String bank;

    public Battery(String _bank) {
        bank = _bank;
    }

    public int FindLargestJoltage() {
        int joltage = 0;
        int first = 0;
        int firstIndex = 0;
        int second = 0;

        var tmp_bank = bank.toCharArray();

        for (int i = 0; i < bank.length()-1; i++) {
            var tmp = Character.getNumericValue(tmp_bank[i]);
            if (tmp > first) {
                first = tmp;
                firstIndex = i;
            }
        }

        for (int i = firstIndex+1; i < bank.length(); i++) {
            var tmp = Character.getNumericValue(tmp_bank[i]);
            if (tmp > second) {
                second = tmp;
            }
        }

        joltage = first * 10 + second;

        return joltage;
    }
}