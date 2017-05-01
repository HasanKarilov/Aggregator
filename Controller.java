package com.javarush.task.task28.task2810;

import com.javarush.task.task28.task2810.model.Model;

import java.io.IOException;

/**
 * Created by hanaria on 5/1/17.
 */
public class Controller {
    private Model model;

    public Controller(Model model) {
        if(model==null){
            throw new IllegalArgumentException();
        }
        this.model = model;
    }

    public  void onCitySelect(String cityName) throws IOException {
        model.selectCity(cityName);
    }
}