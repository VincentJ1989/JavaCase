/**
 * 传值小练习
 * @author VincentJ
 * @date 2019-05-13
 */
public class TransferDemo {

    public static void main(String[] args) {
        TransferDemo transferDemo = new TransferDemo();
        int age = 20;
        transferDemo.changeInt(age);
        System.out.println(age);

        String str = "ABC";
        transferDemo.changeName(str);
        System.out.println(str);

        Person person = new Person("ABC");
        transferDemo.changePerson(person);
        System.out.println(person.name);

    }

    public void changeInt(int age) {
        age = 30;
    }

    public void changeName(String name) {
        name = "xxx";
    }

    public void changePerson(Person person) {
        if (person != null) {
            person.setName("xxx");
        }
    }

    static class Person {
        private String name;

        public Person(String pName) {
            name = pName;
        }

        public String getName() {
            return name;
        }

        public void setName(String pName) {
            name = pName;
        }
    }
}
