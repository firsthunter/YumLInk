package com.example.beta.Services;

import java.util.Map;

public interface iService<T> {
    Map<String, Double> addUserNutrition(T t);
    void deleteUserNutrition(int id);

}
