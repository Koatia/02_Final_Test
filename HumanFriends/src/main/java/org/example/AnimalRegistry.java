package org.example;

// AnimalRegistry.java

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.example.Animal.Animal;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AnimalRegistry {
    private static int animalCount = 0;
    private List<Animal> animals;

    public AnimalRegistry() {
        animals = new ArrayList<>();
    }

    public static int getAnimalCount() {
        return animalCount;
    }

    public void loadFromFile(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AnimalTypeAdapterFactory()).create();
            Type animalListType = new TypeToken<ArrayList<Animal>>() {
            }.getType();
            animals = gson.fromJson(reader, animalListType);
            if (animals != null) {
                animalCount = animals.size();
            } else {
                animals = new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println("Файл не найден, создается новый.");
        } catch (JsonParseException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AnimalTypeAdapterFactory()).create();
            gson.toJson(animals, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
        animalCount++;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void listCommands(Animal animal) {
        System.out.println("Команды, которые выполняет " + animal.getName() + ": " + animal.getCommands());
    }

    public void addCommand(Animal animal, String command) {
        animal.addCommand(command);
    }

    public void listAnimalsByBirthDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        animals.stream().sorted((a1, a2) -> a1.getBirthDay().compareTo(a2.getBirthDay())).forEach(animal -> {
            String formattedDate = dateFormat.format(animal.getBirthDay());
            System.out.println(animal.getName() + " (" + formattedDate + ")");
        });
    }

    public void printTotalAnimalCount() {
        System.out.println("Общее количество животных: " + getAnimalCount());
    }
}