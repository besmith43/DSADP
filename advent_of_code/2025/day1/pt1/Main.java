import java.nio.file.Files;
// import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        System.out.println(args.length);

        for (String arg : args) {
            System.out.println(arg);
        }

        var inputFile = "";

        if (args.length > 0 && Files.exists(Paths.get(args[0]))) {
            inputFile = args[0];
        } else {
            System.out.println("you need to give me a file to work on");
            System.exit(1);
        }

        Dial dial = new Dial();

        int count = 0;

        System.out.printf("Starting point: %d\n", dial.getPosition());

        try (Stream<String> stream = Files.lines(Paths.get(inputFile))) {
            for (var line : stream.toArray()) {
                dial.Move(line.toString());
                // System.out.println(dial.getPosition());
                if ( dial.getPosition() == 0) {
                    count++;
                }
            }
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
        }

        System.out.printf("Ending point: %d\n", dial.getPosition());

        System.out.printf("Final Count: %d\n", count);
    }
}

class Dial {
    private int position = 50;

    public int getPosition() {
        return position;
    }

    public void Move(String command) throws Exception {
        if (command != null && !command.isEmpty()) {
            char direction = command.charAt(0);
            int steps = Integer.parseInt(command.substring(1));
            // System.out.printf("Command: %s\n", command);

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
        // System.out.printf("Moving to the Left %d steps\n", steps);

        for (int i = steps; i > 0; i--) {
            if (position - 1 < 0) {
                position = 99;
            } else {
                position--;
            }
        }
    }

    private void Right(int steps) {
        // System.out.printf("Moving to the Right %d steps\n", steps);

        for (int i = steps; i > 0; i--) {
            if (position + 1 > 99) {
                position = 0;
            } else {
                position++;
            }
        }
    }
}
