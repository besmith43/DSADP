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

        List<Long> joltageArray = new ArrayList<>();

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

        long sum = 0;

        for (var joltage : joltageArray) {
            System.out.printf("Joltage Result: %d\n", joltage);
            sum += joltage;
        }

        System.out.printf("Total: %d\n", sum);
    }
}

class Battery {
    private final String bank;

    public Battery(String _bank) {
        bank = _bank;
    }

    public long FindLargestJoltage() {
        char[] answerBank = new char[12];

        var tmp_bank = bank.toCharArray();

        int last_index = -1;
        for (int i = 0; i < 12; i++) {
            answerBank[i] = '0';
            // System.err.printf("last index: %d\n", last_index);
            // System.err.printf("j upper limit: %d\n", tmp_bank.length-12+i);
            for (int j = last_index+1; j <= tmp_bank.length-12+i; j++) {
                var tmp = Character.getNumericValue(tmp_bank[j]);
                var lastLargest = Character.getNumericValue(answerBank[i]);
                // System.out.printf("Comparing %d to %d\n", tmp, lastLargest);
                if (tmp > lastLargest) {
                    answerBank[i] = tmp_bank[j];
                    last_index = j;
                }
            }
        }

        String ansString = new String(answerBank);
        // System.out.printf("Answer String: %s\n", ansString);
        long ansLong = Long.parseLong(ansString);
        // System.out.printf("Answer Long: %d\n", ansLong);
        return ansLong;
    }
}