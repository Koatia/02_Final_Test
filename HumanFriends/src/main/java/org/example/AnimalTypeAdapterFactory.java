package org.example;

// AnimalTypeAdapterFactory.java

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.example.Animal.*;

import java.io.IOException;
import java.util.*;

public class AnimalTypeAdapterFactory implements TypeAdapterFactory {
    private final Map<String, Class<? extends Animal>> animalTypeRegistry;

    public AnimalTypeAdapterFactory() {
        animalTypeRegistry = new HashMap<>();
        animalTypeRegistry.put("Cat", Cat.class);
        animalTypeRegistry.put("Dog", Dog.class);
        animalTypeRegistry.put("Hamster", Hamster.class);
        animalTypeRegistry.put("Horse", Horse.class);
        animalTypeRegistry.put("Camel", Camel.class);
        animalTypeRegistry.put("Donkey", Donkey.class);
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!Animal.class.isAssignableFrom(type.getRawType())) {
            return null;
        }

        TypeAdapter<JsonElement> jsonElementAdapter = gson.getAdapter(JsonElement.class);
        Map<String, TypeAdapter<? extends Animal>> animalTypeAdapters = new HashMap<>();
        for (Map.Entry<String, Class<? extends Animal>> entry : animalTypeRegistry.entrySet()) {
            TypeAdapter<? extends Animal> typeAdapter = gson.getDelegateAdapter(this, TypeToken.get(entry.getValue()));
            animalTypeAdapters.put(entry.getKey(), typeAdapter);
        }

        return (TypeAdapter<T>) new TypeAdapter<Animal>() {
            @Override
            public void write(JsonWriter out, Animal value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.beginObject();
                out.name("type").value(value.getClass().getSimpleName());
                out.name("name").value(value.getName());
                out.name("birthDay").value(value.getBirthDay().getTime());
                out.name("commands");
                out.beginArray();
                for (String command : value.getCommands()) {
                    out.value(command);
                }
                out.endArray();
                out.endObject();
            }

            @Override
            public Animal read(JsonReader in) throws IOException {
                JsonObject jsonObject = jsonElementAdapter.read(in).getAsJsonObject();
                JsonElement typeElement = jsonObject.get("type");
                if (typeElement == null) {
                    throw new JsonParseException("Missing 'type' field in JSON object");
                }

                String type = typeElement.getAsString();
                JsonElement nameElement = jsonObject.get("name");
                if (nameElement == null) {
                    throw new JsonParseException("Missing 'name' field in JSON object");
                }

                JsonElement birthDayElement = jsonObject.get("birthDay");
                if (birthDayElement == null) {
                    throw new JsonParseException("Missing 'birthDay' field in JSON object");
                }

                JsonArray commandsArray = jsonObject.getAsJsonArray("commands");

                String name = nameElement.getAsString();
                Date birthDay = new Date(birthDayElement.getAsLong());
                List<String> commands = new ArrayList<>();
                if (commandsArray != null) {
                    for (JsonElement commandElement : commandsArray) {
                        commands.add(commandElement.getAsString());
                    }
                }

                TypeAdapter<? extends Animal> typeAdapter = animalTypeAdapters.get(type);
                if (typeAdapter == null) {
                    throw new JsonParseException("Unknown animal type: " + type);
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
        };
    }
}