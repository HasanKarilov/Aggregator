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

    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) throws IOException
    {
        List<Vacancy> vacancies = new ArrayList<>();

        for (int i = 0; ; i++)
        {
            Document document = getDocument(searchString, i);
            Elements plainVacancies = document.select("[data-qa=\"vacancy-serp__vacancy\"]");
            if (plainVacancies.size() == 0)
            {
                break;
            }
            Iterator iterator = plainVacancies.iterator();
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
        }
        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException
    {
        String url = String.format(URL_FORMAT, searchString, page);
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:52.0) Gecko/20100101 Firefox/52.0")
                .referrer("none")
                .get();
    }
}