import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RefusalException extends Exception {
    public RefusalException(String message) {
        super(message);
    }
}

interface Human {
    String getName();
    int getHeight();
    int getWeight();
    boolean isAlive();
    void kill();
}

final class Biker implements Human { //КЛАСС БАЙКЕР
    private final String name;
    private final int height;
    private final int weight;
    private boolean hasClothes;
    private boolean alive;

    public Biker(String name, int height, int weight) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.hasClothes = true;
        this.alive = true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void kill() {
        alive = false;
        System.out.println(name + " убит");
    }

    public void giveClothes() throws RefusalException {
        if (!hasClothes) {
            throw new RefusalException("У меня нет одежды");
        }
        if (!alive) {
            throw new RefusalException("Я мертв");
        }
        hasClothes = false;
        System.out.println(name + ": Забирай мою куртку");
    }
}

class SaraKonor implements Human { //КЛАСС САРА КОННОР
    private String name;
    private int height;
    private int weight;
    private boolean alive;
    private String address;

    public SaraKonor(String name, int height, int weight, String address) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.alive = true;
        this.address = address;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void kill() {
        alive = false;
        System.out.println("Сара Коннор убита в " + address);
    }

    public String getAddress() {
        return address;
    }
}

abstract class Weapon { //КЛАСС ОРУЖИЕ
    protected String type;

    public String getType() {
        return type;
    }
    public abstract void use(Human target);
}

class Pistol extends Weapon { //КЛАСС ПИСТОЛЕТ-ОРУЖИЕ
    public Pistol() {
        this.type = "Пистолет";
    }

    @Override
    public void use(Human target) {
        System.out.println("Стреляю из пистолета в " + target.getName());
        target.kill();
    }
}

abstract class Transport { //КЛАСС ТРАНСПОРТ
    protected String type;

    public String getType() {
        return type;
    }
    public abstract void move(String destination);
}

class Motorcycle extends Transport { //КЛАСС МОТОЦИКЛ-ТРАНСПОРТ
    public Motorcycle() {
        this.type = "Мотоцикл";
    }

    @Override
    public void move(String destination) {
        System.out.println("Еду на мотоцикле в " + destination);
    }
}

class Car extends Transport { //КЛАСС МАШИНА-ТРАНСПОРТ
    public Car() {
        this.type = "Машина";
    }

    @Override
    public void move(String destination) {
        System.out.println("Еду на машине в " + destination);
    }
}

class Bicycle extends Transport { //КЛАСС ВЕЛОСИПЕД-ТРАНСПОРТ
    public Bicycle() {
        this.type = "Велосипед";
    }

    @Override
    public void move(String destination) {
        System.out.println("Еду на велосипеде в " + destination);
    }
}

class AddressBook { //КЛАСС АДРЕСНАЯ КНИГА
    private Map<String, List<String>> addresses;

    public AddressBook() {
        addresses = new HashMap<>();
    }

    public void addAddress(String name, String address) {
        addresses.computeIfAbsent(name, k -> new ArrayList<>()).add(address);
    }

    public List<String> getAddresses(String name) {
        return addresses.getOrDefault(name, new ArrayList<>());
    }
}

class PhoneBooth { //КЛАСС ТЕЛЕФОННАЯ БУДКА
    private AddressBook addressBook;

    public PhoneBooth(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }
}

final class Bar { //КЛАСС БАР
    private final List<Biker> bikers;

    public Bar() {
        bikers = new ArrayList<>();
    }

    public void addBiker(Biker biker) {
        bikers.add(biker);
    }

    public List<Biker> getBikers() {
        return new ArrayList<>(bikers);
    }

    public int getBikersCount() {
        return bikers.size();
    }
}

final class TerminatorT800 { //КЛАСС ТЕРМИНАТОР
    private final String missionTarget = "Сара Коннор";
    private Transport vehicle;
    private Weapon weapon;
    private boolean hasClothes;

    public void executeMission() { //ПЛАН МИССИИ
        System.out.println("||МИССИЯ ТЕРМИНАТОРА T-800||");
        System.out.println("Терминатор T-800 появляется");

        Bar bar = createBar();
        enterBar(bar);

        findClothes(bar);

        takeVehicleAndWeapon();

        PhoneBooth phoneBooth = findPhoneBooth();

        List<String> addresses = findAddresses(phoneBooth);

        findAndKillTarget(addresses);

        moveToDestination("точка сбора");

        System.out.println("||МИССИЯ ЗАВЕРШЕНА||");
    }

    private Bar createBar() {
        Bar bar = new Bar();
        bar.addBiker(new Biker("Джон", 185, 90));
        bar.addBiker(new Biker("Майк", 188, 95));
        bar.addBiker(new Biker("Том", 175, 70));
        bar.addBiker(new Biker("Тони", 190, 100));
        return bar;
    }

    private void enterBar(Bar bar) {
        System.out.println("Терминатор заходит в бар с " + bar.getBikersCount() + " человека");
    }

    private void findClothes(Bar bar) {
        System.out.println("Поиск подходящего по размерам человека...");

        for (Biker biker : bar.getBikers()) {
            System.out.println("Проверка: " + biker.getName() +
                    " (" + biker.getHeight() + "см, " + biker.getWeight() + "кг)");

            if (biker.getHeight() >= 185 && biker.getWeight() >= 85) {
                try {
                    System.out.println("Найден подходящий байкер: " + biker.getName());
                    biker.giveClothes();
                    hasClothes = true;
                    System.out.println("Одежда получена успешно");
                    return;
                } catch (RefusalException e) {
                    System.out.println("Отказ от " + biker.getName() + ": " + e.getMessage());
                }
            }
        }
        System.out.println("Не удалось найти одежду");
    }

    private void takeVehicleAndWeapon() {
        vehicle = new Motorcycle();
        weapon = new Pistol();
        System.out.println("Взяты: " + vehicle.getType() + " и " + weapon.getType());
    }
}