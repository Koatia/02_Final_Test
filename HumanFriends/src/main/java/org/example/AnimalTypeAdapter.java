package org.example;

// AnimalTypeAdapter.java

import com.google.gson.*;
import org.example.Animal.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnimalTypeAdapter implements JsonSerializer<Animal>, JsonDeserializer<Animal> {
    @Override
    public JsonElement serialize(Animal animal, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", animal.getClass().getSimpleName());
        jsonObject.addProperty("name", animal.getName());
        jsonObject.addProperty("birthDay", animal.getBirthDay().getTime());
        JsonArray commandsArray = new JsonArray();
        for (String command : animal.getCommands()) {
            commandsArray.add(new JsonPrimitive(command));
        }
        jsonObject.add("commands", commandsArray);
        return jsonObject;
    }

    @Override
    public Animal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement typeElement = jsonObject.get("type");
        JsonElement nameElement = jsonObject.get("name");
        JsonElement birthDayElement = jsonObject.get("birthDay");

        if (typeElement == null || nameElement == null || birthDayElement == null) {
            throw new JsonParseException("Invalid JSON format.");
        }

        String type = typeElement.getAsString();
        String name = nameElement.getAsString();
        Date birthDay = new Date(birthDayElement.getAsLong());

        JsonArray commandsArray = jsonObject.getAsJsonArray("commands");
        List<String> commands = new ArrayList<>();
        if (commandsArray != null) {
            for (JsonElement commandElement : commandsArray) {
                commands.add(commandElement.getAsString());
            }
        }

        Animal animal;
        switch (type) {
            case "Cat":
                animal = new Cat(name, birthDay);
                break;
            case "Dog":
                animal = new Dog(name, birthDay);
                break;
            case "Hamster":
                animal = new Hamster(name, birthDay);
                break;
            case "Horse":
                animal = new Horse(name, birthDay);
                break;
            case "Camel":
                animal = new Camel(name, birthDay);
                break;
            case "Donkey":
                animal = new Donkey(name, birthDay);
                break;
            default:
                throw new JsonParseException("Unknown element type: " + type);
        }
        for (String command : commands) {
            animal.addCommand(command);
        }
        return animal;
    }
}