package order;

import com.github.javafaker.Faker;

public class OrderTestGen {
    private static final Faker faker = new Faker();

    public static Order getGreyColor() {
        return new Order(faker.name().firstName(), faker.name().lastName(), faker.address().fullAddress(),
                new String[]{"GREY"});
    }

    public static Order getBlackColor() {
        return new Order(faker.name().firstName(), faker.name().lastName(), faker.address().fullAddress(),
                new String[]{"BLACK"});
    }

    public static Order getWithoutColours() {
        return new Order(faker.name().firstName(), faker.name().lastName(), faker.address().fullAddress(), null);
    }

    public static Order getWithTwoColours() {
        return new Order(faker.name().firstName(), faker.name().lastName(), faker.address().fullAddress(),
                new String[]{"GREY", "BLACK"});
    }
}
