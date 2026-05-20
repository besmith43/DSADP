
// https://java-design-patterns.com/patterns/pipeline/#detailed-explanation-of-pipeline-pattern-with-real-world-examples


interface Handler<I, O> {
    O process(I input);
}

class RemoveAlphabetsHandler implements Handler<String, String> {
    @Override
    public String process(String input) {
        return input.replaceAll("[a-zA-Z]", "");
    }
}

class RemoveDigitsHandler implements Handler<String, String> {
    @Override
    public String process(String input) {
        return input.replaceAll("\\d", "");
    }
}

class ConvertToCharArrayHandler implements Handler<String, char[]> {
    @Override
    public char[] process(String input) {
        return input.toCharArray();
    }
}


class Pipeline<I, O> {

    private final Handler<I, O> currentHandler;

    Pipeline(Handler<I, O> currentHandler) {
        this.currentHandler = currentHandler;
    }

    <K> Pipeline<I, K> addHandler(Handler<O, K> newHandler) {
        return new Pipeline<>(input -> newHandler.process(currentHandler.process(input)));
    }

    O execute(I input) {
        return currentHandler.process(input);
    }
}


public class Main {
    public static void main(String[] args) {
        System.out.printf("Creating pipeline\n");
        var filters = new Pipeline<>(new RemoveAlphabetsHandler())
            .addHandler(new RemoveDigitsHandler())
            .addHandler(new ConvertToCharArrayHandler());
        var input = "GoYankees123!";
        System.out.printf("Executing pipeline with input: %s\n", input);
        var output = filters.execute(input);
        System.out.printf("Pipeline output: %s\n", new String(output));
    }
}

