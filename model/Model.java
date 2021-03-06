package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.view.View;
import com.javarush.task.task28.task2810.vo.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanaria on 5/2/17.
 */
public class Model {
    private View view;
    private Provider[] providers;

    public Model(View view, Provider...providers){
        if(view==null || providers==null || providers.length==0){
            throw new IllegalArgumentException();
        }
        this.view = view;
        this.providers = providers;
    }

    public void selectCity(String city) throws IOException {
        List<Vacancy> vacancies = new ArrayList<>();
        for(Provider provider: providers){
           List<Vacancy> vacanciesFromProvider = provider.getJavaVacancies(city);
           vacancies.addAll(vacanciesFromProvider);
        }
        view.update(vacancies);
    }
}
