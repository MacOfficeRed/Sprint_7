package courier;

import com.github.javafaker.Faker;

public class CourierTestGen {
    private static final Faker faker = new Faker();

    public static Courier getRandomCourier() {
        return new Courier(faker.name().username(), faker.internet().password(4, 5,
                false, false, false), faker.name().firstName());
    }

    public static Courier getWithoutLogin() {
        return new Courier("", faker.internet().password(4, 5,
                false, false, false), faker.name().firstName());
    }

    public static Courier getWithoutPassword() {
        return new Courier(faker.name().username(), "", faker.name().firstName());
    }

    public static Courier getNonExist() {
        return new Courier(faker.internet().uuid(), faker.internet().uuid(), faker.internet().uuid());
    }
}
