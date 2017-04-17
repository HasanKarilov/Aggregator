package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hanaria on 4/13/17.
 */
public class HHStrategy implements Strategy {
    /*
        http://hh.ua/search/vacancy?text=java+ADDITIONAL_VALUE&page=PAGE_VALUE
        ADDITIONAL_VALUE - It is city name      PAGE_VALUE - It is page value
     */
    private static final String URL_FORMAT = "https://javarush.ru/testdata/big28data.html";

    @Override
    public List<Vacancy> getVacancies(String searchString) throws IOException
    {
        List<Vacancy> vacancies = new ArrayList<>();

            Document document = Jsoup.connect(URL_FORMAT).get();
            Elements vacanciesFromSite = document.select("[data-qa=\"vacancy-serp__vacancy\"]");

            Iterator iterator = vacanciesFromSite.iterator();
            Element rawVacancy;
            while (iterator.hasNext())
            {
                Vacancy vacancy = new Vacancy();
                rawVacancy = (Element) iterator.next();

                vacancy.setTitle(rawVacancy.select("[data-qa=\"vacancy-serp__vacancy-title\"]").first().text());

                Elements salaries = rawVacancy.select("[data-qa=\"vacancy-serp__vacancy-compensation\"]");
                if (salaries.size() > 0)
                {
                    vacancy.setSalary(salaries.first().text());
                } else
                {
                    vacancy.setSalary("");
                }

                vacancy.setCity(rawVacancy.select("[data-qa=\"vacancy-serp__vacancy-address\"]").first().text());
                vacancy.setCompanyName(rawVacancy.select("[data-qa=\"vacancy-serp__vacancy-employer\"]").first().text());
                vacancy.setSiteName("hh.ru");
                vacancy.setUrl(rawVacancy.select("[data-qa=\"vacancy-serp__vacancy-title\"]").first().attr("href"));

                vacancies.add(vacancy);
            }
       System.out.println("Vacancies ---------------------------------------------------");
        for(Vacancy v : vacancies)
            System.out.println(v.getTitle()+ " " +v.getCompanyName()+ " " + v.getCity()+ " " +v.getSalary());
        return vacancies;
    }

    public static void main(String[] args) {
        HHStrategy hh = new HHStrategy();
        try {
            hh.getVacancies("Java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Document getDocument(String searchString, int page) throws IOException{
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36";
        String referrer = "google.ru";
        return Jsoup.connect(searchString).userAgent(userAgent).referrer(referrer).get();

    }
}