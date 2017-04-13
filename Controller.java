package com.javarush.task.task28.task2810;

import com.javarush.task.task28.task2810.model.Provider;
import com.javarush.task.task28.task2810.vo.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hanaria on 4/13/17.
 */
public class Controller {
    private Provider[] providers;
    public Controller(Provider... providers) {
        if (providers==null||providers.length==0){
            throw new IllegalArgumentException();
        }
        this.providers = providers;
    }

    @Override
    public String toString() {
        return "Controller{" +
                "providers=" + Arrays.toString(providers) +
                '}';
    }

    public void scan() {
        ArrayList<Vacancy> vacs = new ArrayList<>();
        try{
            for (Provider provider:providers){
                for (Vacancy vacancy:provider.getJavaVacancies("SOME TEXT")){
                    vacs.add(vacancy);
                }
            }
        }catch(NullPointerException e){/*NOP*/} catch (IOException e) {

        }
        System.out.println(vacs.size());
    }
}
