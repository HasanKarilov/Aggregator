package com.javarush.task.task28.task2810;


import com.javarush.task.task28.task2810.model.HHStrategy;
import com.javarush.task.task28.task2810.model.Model;
import com.javarush.task.task28.task2810.model.Provider;
import com.javarush.task.task28.task2810.view.HtmlView;

import java.io.IOException;

/**
 * Created by hanaria on 5/1/17.
 */
public class Aggregator {
    public static void main(String[] args) throws IOException {
        HtmlView view = new HtmlView();
        Provider hhProvider = new Provider(new HHStrategy());
        Model model = new Model(view,hhProvider);
        Controller controller = new Controller(model);
        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
