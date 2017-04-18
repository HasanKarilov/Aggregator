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
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) throws IOException
    {
        List<Vacancy> vacancies = new ArrayList<>();

        for (int i = 0; ; i++)
        {
            Document document = getDocument(String.format(URL_FORMAT, searchString, i), i);
            /*Результаты поиска выходят в отдельный див
            <div data-qa="vacancy-serp__vacancy" class="search-result-item search-result-item_standard ">
            Записоваем в Elements все данные дивов этого data-ga
            Из объекта Document получили список html-элементов с атрибутом "vacancy-serp__vacancy". Для каждого элемента создали объект вакансии и добавили его в возвращающий методом список.
             */
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
                //                 <a href="/employer/18231" class="bloko-link bloko-link_secondary" data-qa="vacancy-serp__vacancy-employer"> Epam Systems Ukraine</a>
                vacancy.setCompanyName(rawVacancy.select("[data-qa=\"vacancy-serp__vacancy-employer\"]").first().text());
                vacancy.setSiteName(document.title());
                vacancy.setUrl(rawVacancy.select("[data-qa=\"vacancy-serp__vacancy-title\"]").first().attr("href"));

                vacancies.add(vacancy);
            }
        }
        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException
    {
        String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Sa";
        String referrer = "none";
        return Jsoup.connect(searchString).userAgent(userAgent).referrer(referrer).get();
    }
}