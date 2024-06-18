import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Animal implements Serializable {
    private String name;
    private String dateOfBirth;
    private List<String> commands;

    public Animal(String name, String dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.commands = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public void printCommands() {
        System.out.println("Commands for " + name + ": " + String.join(", ", commands));
    }
}

class Dog extends Animal {
    public Dog(String name, String dateOfBirth) {
        super(name, dateOfBirth);
        addCommand("sit");
        addCommand("stay");
    }
}

class Cat extends Animal {
    public Cat(String name, String dateOfBirth) {
        super(name, dateOfBirth);
        addCommand("jump");
        addCommand("climb");
    }
}

class Hamster extends Animal {
    public Hamster(String name, String dateOfBirth) {
        super(name, dateOfBirth);
        addCommand("run");
        addCommand("hide");
    }
}

class Horse extends Animal {
    public Horse(String name, String dateOfBirth) {
        super(name, dateOfBirth);
        addCommand("gallop");
        addCommand("neigh");
    }
}

class Camel extends Animal {
    public Camel(String name, String dateOfBirth) {
        super(name, dateOfBirth);
        addCommand("walk");
        addCommand("rest");
    }
}

class Donkey extends Animal {
    public Donkey(String name, String dateOfBirth) {
        super(name, dateOfBirth);
        addCommand("carry");
        addCommand("bray");
    }
}

class AnimalRegistry implements Serializable {
    private List<Animal> animals;
    private Map<String, Integer> animalCountByType;

    public AnimalRegistry() {
        animals = new ArrayList<>();
        animalCountByType = new HashMap<>();
    }

    public void addAnimal(Animal animal, String type) {
        animals.add(animal);
        animalCountByType.put(type, animalCountByType.getOrDefault(type, 0) + 1);
    }

    public void listCommands(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name)) {
                animal.printCommands();
                return;
            }
        }
        System.out.println("Animal not found.");
    }

    public void teachNewCommand(String name, String command) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name)) {
                animal.addCommand(command);
                System.out.println("Command added.");
                return;
            }
        }
        System.out.println("Animal not found.");
    }

    public void listAnimalsByDateOfBirth() {
        animals.sort(Comparator.comparing(Animal::getDateOfBirth));
        for (Animal animal : animals) {
            System.out.println(animal.getName() + " - " + animal.getDateOfBirth());
        }
    }

    public void printAnimalCount() {
        int totalAnimals = animals.size();
        System.out.println("Total number of animals: " + totalAnimals);
        for (String type : animalCountByType.keySet()) {
            System.out.println("Total number of " + type + ": " + animalCountByType.get(type));
        }
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public Map<String, Integer> getAnimalCountByType() {
        return animalCountByType;
    }
}

public class AnimalRegistryApp {
    private static Scanner scanner = new Scanner(System.in);
    private static AnimalRegistry registry = new AnimalRegistry();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    addNewAnimal();
                    break;
                case 2:
                    listCommands();
                    break;
                case 3:
                    teachNewCommand();
                    break;
                case 4:
                    listAnimalsByDateOfBirth();
                    break;
                case 5:
                    registry.printAnimalCount();
                    break;
                case 6:
                    saveRegistry();
                    break;
                case 7:
                    importRegistry();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("1. Add new animal");
        System.out.println("2. List commands of an animal");
        System.out.println("3. Teach new command to an animal");
        System.out.println("4. List animals by date of birth");
        System.out.println("5. Show animal count");
        System.out.println("6. Save registry");
        System.out.println("7. Import registry");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addNewAnimal() {
        System.out.print("Enter animal type (dog, cat, hamster, horse, camel, donkey): ");
        String type = scanner.nextLine().toLowerCase();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine();

        Animal animal;
        switch (type) {
            case "dog":
                animal = new Dog(name, dateOfBirth);
                break;
            case "cat":
                animal = new Cat(name, dateOfBirth);
                break;
            case "hamster":
                animal = new Hamster(name, dateOfBirth);
                break;
            case "horse":
                animal = new Horse(name, dateOfBirth);
                break;
            case "camel":
                animal = new Camel(name, dateOfBirth);
                break;
            case "donkey":
                animal = new Donkey(name, dateOfBirth);
                break;
            default:
                System.out.println("Invalid animal type.");
                return;
        }
        registry.addAnimal(animal, type);
        System.out.println("Animal added.");
    }

    private static void listCommands() {
        System.out.print("Enter animal name: ");
        String name = scanner.nextLine();
        registry.listCommands(name);
    }

    private static void teachNewCommand() {
        System.out.print("Enter animal name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new command: ");
        String command = scanner.nextLine();
        registry.teachNewCommand(name, command);
    }

    private static void listAnimalsByDateOfBirth() {
        registry.listAnimalsByDateOfBirth();
    }

    private static void saveRegistry() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("animal_registry.dat"))) {
            oos.writeObject(registry);
            System.out.println("Registry saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving registry: " + e.getMessage());
        }
    }

    private static void importRegistry() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("animal_registry.dat"))) {
            registry = (AnimalRegistry) ois.readObject();
            System.out.println("Registry imported successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error importing registry: " + e.getMessage());
        }
    }
}