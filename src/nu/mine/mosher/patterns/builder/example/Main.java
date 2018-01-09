package nu.mine.mosher.patterns.builder.example;

import static nu.mine.mosher.patterns.builder.example.Heart.*;
import static nu.mine.mosher.patterns.builder.example.Person.Defaults.*;

/**
 * Builder pattern example.
 */
public class Main {
    public static void main(final String... args) {
        Person p;
        p = Person.create("Chris", 51);
        p = Person.create("Holly", 15).withVip();
        p = Person.create("Hemingway", 22).withFingers(11);
        p = Person.create("Lefty", 34).withVip().withFingers(5);
        p = Person.create("Dave", 43).withVip().withoutEyebrows();
        p = Person.create("Alice Cooper", 18).withHeart(OLD_MANS);

        // User can access default values, if desired.
        System.out.println("Normal number of fingers: " + FINGERS);


        // Also allow user to do it "old-style" (without using the builder pattern)
        p = new Person("Moses", 112, true, FINGERS, HAS_EYEBROWS, HEART);

        // or they can do this:
        p = p.withoutEyebrows();
    }
}
