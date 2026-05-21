

public record PersonRecord(String firstname, String lastname) {}

public class PersonClass{
    public String firstname;
    public String lastname;

    public PersonClass(String first, String last) {
        firstname = first;
        lastname = last;
    }
}




public class Main {
    public static void main(String[] args) {
        System.out.println("Working with tradional classes\n");

        PersonClass personA = new PersonClass("Sue", "Storm");

        getMarried(personA, "Smith");

        System.out.printf("%s %s\n", personA.firstname, personA.lastname);

        System.out.println("\n\nWorking with immutable records\n");

        PersonRecord personB = new PersonRecord("Beth", "Roberts");

        personB = getMarried(personB, "Smith");

        System.out.printf("%s %s\n", personB.firstname(), personB.lastname());
    }


    public static void getMarried(PersonClass a, String newLastName) {
        a.lastname = newLastName;
    }

    public static PersonRecord getMarried(PersonRecord a, String newLastName) {
        return new PersonRecord(a.firstname(), newLastName);
    }
}
