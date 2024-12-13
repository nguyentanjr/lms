package com.example.demo.Services.ServiceImpls;

import com.example.demo.Services.Service.JsonStorageService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class JsonStorageServiceImpl implements JsonStorageService {
    private static final String FILE_PATH = "C:\\Users\\ADMIN\\Documents\\Big Assignment OOP\\demo\\src\\main\\resources\\static\\Json\\book.json";

    @Autowired
    private Gson gson;


    public void saveBooks(Set<String> bookTitles) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(bookTitles, writer);
        }
    }

    public List<String> loadBooks() throws IOException {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new com.google.gson.reflect.TypeToken<List<String>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
