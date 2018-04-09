package nu.mine.mosher.patterns.builder.example;

@SuppressWarnings("WeakerAccess")
public final class Person {
    /*
    These are the default values for all (and only) the optional fields.
    They are used internally, as well as provided (public) to the user.
     */
    public static final class Defaults {
        public static final boolean VIP = false;
        public static final int FINGERS = 10;
        public static final boolean HAS_EYEBROWS = true;
        public static final Heart HEART = Heart.BIG;
    }

    /*
    These are all the fields.
    They are all final.
    They are public, but they could just as well be private, with getters.
     */
    public final String name;
    public final int age;
    public final boolean vip;
    public final int fingers;
    public final boolean hasEyebrows;
    public final Heart heart;



    /*
    The only constructor.
    It should do all validation, as well.
     */

    public Person(final String name, final int age, final boolean vip, final int fingers, final boolean hasEyebrows, final Heart heart) {
        this.name = name;
        if (this.name == null || this.name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.age = age;
        this.vip = vip;
        this.fingers = fingers;
        this.hasEyebrows = hasEyebrows;
        this.heart = heart;
        if (this.heart == null) {
            throw new IllegalArgumentException();
        }
    }


    /*
    Factory method, takes all (and only) the required fields.
    It sets the optional fields to the default values.
     */
    public static Person create(final String name, final int age) {
        return new Person(name, age, Defaults.VIP, Defaults.FINGERS, Defaults.HAS_EYEBROWS, Defaults.HEART);
    }



    /*
    "Builder" methods (for each optional field) follow:
     */



    /*
    Using this style of method, all method bodies will be identical.
    The only difference will be which variable is the parameter.
     */

    public Person withFingers(final int fingers) {
        return new Person(name, age, vip, fingers, hasEyebrows, heart);
    }

    public Person withHeart(final Heart heart) {
        return new Person(name, age, vip, fingers, hasEyebrows, heart);
    }





    /*
    This style of method is unique to booleans and any other data-type with only two values.
    They would be used only to set the non-default value, without specifying an argument.
    They may look nice, but are probably less useful than using the generic style, above.
     */

    public Person withVip() {
        return new Person(name, age, true, fingers, hasEyebrows, heart);
    }

    public Person withoutEyebrows() {
        return new Person(name, age, vip, fingers, false, heart);
    }
}
