package apiTests;

import api.CarApi;
import api.PersonApi;
import api.models.Car;
import api.models.Person;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class ApiTests {

    private static final Logger logger = LoggerFactory.getLogger(ApiTests.class);

    PersonApi userApi = new PersonApi();
    CarApi carApi = new CarApi();

    @Test
    @Description("Создание нового пользователя")
    public void createNewPersonTest(){

        Person person = new Person("Jack", "Rubenstein", 39, "MALE", new BigDecimal("1230022.00"));

        userApi.createPerson(person);

    }

    @Test
    @Description("Получение данных пользователя по его id")
    public void getPersonByIdTest() {

        Integer id = 7336;

        Person person = userApi.getPersonById(id);
        Assertions.assertEquals(id, person.getId());
    }

    @Test
    @Description("Покупка машины пользователем")
    public void buyCarTest(){
        Person person = new Person("Pol", "Enstein", 31, "MALE", new BigDecimal("5530022.00"));
        Car car = new Car("v8", "BMW", "X5", new BigDecimal(500000));

        Person createdPerson = userApi.createPerson(person);
        Car createdCar = carApi.createCar(car);

        userApi.buyCar(createdPerson.getId().toString(), createdCar.getId().toString());
    }
}
