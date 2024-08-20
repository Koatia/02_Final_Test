package org.example;

// Main.java

import org.example.Animal.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final String FILENAME = "animals.json";
    private static final AnimalRegistry registry = new AnimalRegistry();

    public static void main(String[] args) {
        registry.loadFromFile(FILENAME);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить новое животное");
            System.out.println("2. Список команд животного");
            System.out.println("3. Обучить животное новой команде");
            System.out.println("4. Вывести список животных по дате рождения");
            System.out.println("5. Сохранить данные");
            System.out.println("6. Вывести общее количество созданных животных");
            System.out.println("7. Выход");
            System.out.print("Выберите пункт: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    addAnimalMenu(scanner);
                    break;
                case 2:
                    listCommandsMenu(scanner);
                    break;
                case 3:
                    addCommandMenu(scanner);
                    break;
                case 4:
                    registry.listAnimalsByBirthDay();
                    break;
                case 5:
                    registry.saveToFile(FILENAME);
                    System.out.println("Данные сохранены.");
                    break;
                case 6:
                    registry.printTotalAnimalCount();
                    break;
                case 7:
                    System.out.println("Сохранить данные перед выходом? (y/n)");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        registry.saveToFile(FILENAME);
                    }
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void addAnimalMenu(Scanner scanner) {
        System.out.println("Выберите тип животного: 1. Кошка 2. Собака 3. Хомяк 4. Лошадь 5. Верблюд 6. Осел");
        int type = scanner.nextInt();
        scanner.nextLine();  // consume newline

        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите дату рождения (yyyy-MM-dd): ");
        String birthDateStr = scanner.nextLine();
        Date birthDate = null;
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateStr);
        } catch (ParseException e) {
            System.out.println("Неверный формат даты.");
            return;
        }

        Animal animal;
        switch (type) {
            case 1:
                animal = new Cat(name, birthDate);
                break;
            case 2:
                animal = new Dog(name, birthDate);
                break;
            case 3:
                animal = new Hamster(name, birthDate);
                break;
            case 4:
                animal = new Horse(name, birthDate);
                break;
            case 5:
                animal = new Camel(name, birthDate);
                break;
            case 6:
                animal = new Donkey(name, birthDate);
                break;
            default:
                System.out.println("Неверный тип животного.");
                return;
        }

        registry.addAnimal(animal);
        System.out.println("Животное добавлено.");
    }

    private static void listCommandsMenu(Scanner scanner) {
        System.out.print("Введите имя животного: ");
        String name = scanner.nextLine();
        Animal animal = findAnimalByName(name);
        if (animal != null) {
            registry.listCommands(animal);
        } else {
            System.out.println("Животное не найдено.");
        }
    }

    private static void addCommandMenu(Scanner scanner) {
        System.out.print("Введите имя животного: ");
        String name = scanner.nextLine();
        Animal animal = findAnimalByName(name);
        if (animal != null) {
            System.out.print("Введите новую команду: ");
            String command = scanner.nextLine();
            registry.addCommand(animal, command);
            System.out.println("Команда добавлена.");
        } else {
            System.out.println("Животное не найдено.");
        }
    }

    private static Animal findAnimalByName(String name) {
        for (Animal animal : registry.getAnimals()) {
            if (animal.getName().equalsIgnoreCase(name)) {
                return animal;
            }
        }
        return null;
    }
}