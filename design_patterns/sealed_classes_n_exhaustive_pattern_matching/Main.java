

// https://neilmadden.blog/2026/04/24/java-sealed-classes-and-exhaustive-pattern-matching/

public sealed interface SealedType {
    record TypeA() implements SealedType {}
    record TypeB() implements SealedType {}
 
    static SealedType of(String type) {
        return switch (type) {
            case "A" -> new TypeA();
            case "B" -> new TypeB();
            default -> throw new IllegalArgumentException();
        };
    }
}



public class Main {
    public static void main(String[] args) {
        var val = SealedType.of(args[0]);
        System.out.println(switch (val) {
            case SealedType.TypeA() -> "A";
            case SealedType.TypeB() -> "B";
        });
    }
}
