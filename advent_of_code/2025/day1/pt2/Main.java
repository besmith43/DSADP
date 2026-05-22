import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
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

        Dial dial = new Dial();

        System.out.printf("Starting point: %d\n", dial.getPosition());

        try (Stream<String> stream = Files.lines(Paths.get(inputFile))) {
            for (var line : stream.toArray()) {
                dial.Move(line.toString());
            }
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
        }

        System.out.printf("Ending point: %d\n", dial.getPosition());

        System.out.printf("Final Count: %d\n", dial.getCount());
    }
}

class Dial {
    private int position = 50;
    private int count = 0;

    public int getPosition() {
        return position;
    }

    public int getCount() {
        return count;
    }

    public void Move(String command) throws Exception {
        if (command != null && !command.isEmpty()) {
            char direction = command.charAt(0);
            int steps = Integer.parseInt(command.substring(1));

            if (direction == 'L') {
                Left(steps);
            } else if (direction == 'R') {
                Right(steps);
            } else {
                throw new Exception("Directions should be left or right only");
            }
        }
    }

    private void Left(int steps) {
        for (int i = steps; i > 0; i--) {
            if (position - 1 < 0) {
                position = 99;
            } else {
                position--;
                if (position == 0) {
                    count++;
                }
            }
        }
    }

    private void Right(int steps) {
        for (int i = steps; i > 0; i--) {
            if (position + 1 > 99) {
                position = 0;
                count++;
            } else {
                position++;
            }
        }
    }
}
