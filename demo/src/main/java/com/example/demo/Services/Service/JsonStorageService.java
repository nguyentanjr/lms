package com.example.demo.Services.Service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public interface JsonStorageService {
    public void saveBooks(Set<String> bookTitles) throws IOException;
    public List<String> loadBooks() throws IOException;
}
