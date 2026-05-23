import java.nio.file.Files;
import java.nio.file.Paths;
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

        int row = 0;
        int column = 0;

        try {
            String firstLine = Files.lines(Paths.get(inputFile)).findFirst().orElse(null);
            row = firstLine.length();
            column = Files.readAllLines(Paths.get(inputFile)).size();
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
            e.printStackTrace(System.out);
            System.exit(1);
        }

        Grid grid = new Grid(row, column);
        int count = 0;

        try (Stream<String> stream = Files.lines(Paths.get(inputFile))) {
            for (var line : stream.toArray()) {
                grid.AddRow(line.toString(), count);
                count++;
            }
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
            e.printStackTrace(System.out);
            System.exit(1);
        }

        // grid.Print();
        System.out.printf("Forklifts can access %d rolls\n", grid.FindForkLiftAccessible());
    }
}


class Grid {
    char[][] grid;
    int rows;
    int columns;

    public Grid(int rowNumber, int columnNumber) {
        grid = new char[rowNumber][columnNumber];
        rows = rowNumber;
        columns = columnNumber;
    }

    public void AddRow(String line, int height) {
        grid[height] = line.toCharArray();
    }

    public void Print() {
        for (var row : grid) {
            System.out.println(row);
        }
    }

    public int FindForkLiftAccessible() {
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (compute(i, j)) {
                    count++;
                }
            }
        }

        return count;
    }

    private boolean compute(int x, int y) {
        boolean accessible = false;
        int countAdjacentRolls = 0;
        // System.out.printf("Checking x: %d, y: %d, with character %c\n", x, y, grid[x][y]);

        if (grid[x][y] != '@') {
            return accessible;
        }

        // up: x-1
        if (x-1 >= 0) {
            if (grid[x-1][y] == '@') {
                countAdjacentRolls++;
            }
        }

        // down: x+1
        if (x+1 < rows) {
            if (grid[x+1][y] == '@') {
                countAdjacentRolls++;
            }
        }

        // left: y-1
        if (y-1 >= 0) {
            if (grid[x][y-1] == '@') {
                countAdjacentRolls++;
            }
        }

        // right: y+1
        if (y+1 < columns) {
            if (grid[x][y+1] == '@') {
                countAdjacentRolls++;
            }
        }

        // upper left: x-1 && y-1
        if (x-1 >= 0 && y-1 >= 0) {
            if (grid[x-1][y-1] == '@') {
                countAdjacentRolls++;
            }
        }

        // upper right: x-1 && y+1
        if (x-1 >= 0 && y+1 < columns) {
            if (grid[x-1][y+1] == '@') {
                countAdjacentRolls++;
            }
        }

        // lower left: x+1 && y-1
        if (x+1 < rows && y-1 >= 0) {
            if (grid[x+1][y-1] == '@') {
                countAdjacentRolls++;
            }
        }

        //lower right: x+1 && y+1
        if (x+1 < rows && y+1 < columns) {
            if (grid[x+1][y+1] == '@') {
                countAdjacentRolls++;
            }
        }


        if (countAdjacentRolls < 4) {
            // System.out.printf("Grid[%d][%d] is accessible\n", x, y);
            accessible = true;
        }

        return accessible;
    }
}

