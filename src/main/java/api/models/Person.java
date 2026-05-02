package api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Integer id;
    private String firstName;
    private String secondName;
    private Integer age;
    private String sex;
    private BigDecimal money;

    public Person(String firstName, String secondName, Integer age, String sex, BigDecimal money) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.sex = sex;
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(secondName, person.secondName) && Objects.equals(age, person.age) && Objects.equals(sex, person.sex) && Objects.equals(money, person.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, age, sex, money);
    }
}
