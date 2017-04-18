package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;

import java.io.IOException;
import java.util.List;

/**
 * Created by hanaria on 4/18/17.
 */
public class HtmlView implements View {
    private Controller controller;
    private final String filePath = "./src/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/vacancies.html";


    @Override
    public void update(List<Vacancy> vacancies) {
        System.out.println(vacancies.size());
        String fileContent = getUpdatedFileContent(vacancies);
        updateFile(fileContent);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }


   public void userCitySelectEmulationMethod() throws IOException {
       controller.onCitySelect("Odessa");
   }
   private String getUpdatedFileContent(List<Vacancy> vacancies){
        return null;
   }
   private void updateFile(String filePath) {

   }
}
