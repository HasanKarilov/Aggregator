package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by hanaria on 4/13/17.
 */
public class HHStrategy implements Strategy {
    /*
        http://hh.ua/search/vacancy?text=java+ADDITIONAL_VALUE&page=PAGE_VALUE
        ADDITIONAL_VALUE - It is city name      PAGE_VALUE - It is page value
     */
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) throws IOException {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36";
        String referrer = "google.ru";
        Document document = Jsoup.connect(String.format(URL_FORMAT, searchString, 1)).userAgent(userAgent).referrer(referrer).get();
        document.html();

        return null;
    }
}