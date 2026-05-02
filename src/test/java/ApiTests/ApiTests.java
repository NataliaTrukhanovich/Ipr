package apiTests;

import api.CarApi;
import api.HouseApi;
import api.PersonApi;
import api.models.Car;
import api.models.House;
import api.models.Person;
import io.qameta.allure.Description;
import io.restassured.internal.common.assertion.Assertion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ApiTests {

    private static final Logger logger = LoggerFactory.getLogger(ApiTests.class);

    PersonApi personApi = new PersonApi();
    CarApi carApi = new CarApi();
    HouseApi houseApi = new HouseApi();

    @Test
    @Description("Проверка создания нового пользователя")
    public void createNewPersonTest() {

        Person person = new Person("Jack", "Rubenstein", 39, "MALE", new BigDecimal("1230022.00"));

        Person createdPerson = personApi.createPerson(person);

        Assertions.assertEquals(person, createdPerson);

    }

    @Test
    @Description("Получение данных пользователя по его id")
    public void getPersonByIdTest() {


        Person person = new Person("Jacky", "Rubenn", 19, "MALE", new BigDecimal("12322.00"));

        Person createdPerson = personApi.createPerson(person);

        Person obtainedPerson = personApi.getPersonById(createdPerson.getId());
        Assertions.assertEquals(person, obtainedPerson);
    }

    @Test
    @Description("Покупка машины пользователем")
    public void buyCarTest() {

        Person person = personApi.createPerson(new Person("Pol", "Einstein", 31, "MALE", new BigDecimal("5530022.00")));
        Car car = carApi.createCar(new Car("Diesel", "BMW", "X5", new BigDecimal(500000)));

        Person personWithCar = personApi.buyCar(person.getId(), car.getId());

        Assertions.assertEquals(person.getMoney().subtract(car.getPrice()), personWithCar.getMoney());

    }

    @Test
    @Description("Покупка квартир жильцами")
    public void buyHouse() {

        House house = new House(4, new BigDecimal(30000), new ArrayList<>(), new ArrayList<>());
        House createdHouse = houseApi.createHouse(house);

        ArrayList<Person> personList = new ArrayList();

        personList.add(personApi.createPerson(new Person("Иван", "Иванов", 40, "MALE", new BigDecimal(450000))));
        personList.add(personApi.createPerson(new Person("Петр", "Петров", 34, "MALE", new BigDecimal(1400000))));
        personList.add(personApi.createPerson(new Person("Иван", "Сидоров", 47, "MALE", new BigDecimal(500000))));

        for (Person person : personList) {
            houseApi.settlePerson(createdHouse.getId(), person.getId());
        }

        House houseWithLodgers = houseApi.getHouse(createdHouse.getId());

        Assertions.assertEquals(houseWithLodgers.getLodgers().size(), personList.size());
    }
}
