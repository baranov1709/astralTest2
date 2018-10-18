/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rt.astraltest2;

import java.util.ArrayList;
import ru.rt.astraltest2.entities.Vacancy;
import ru.rt.astraltest2.utils.CSV;
import ru.rt.astraltest2.utils.HHAPICriteria;
import ru.rt.astraltest2.utils.HHAPIQuery;
import ru.rt.astraltest2.utils.PostgreSQL;

/**
 *
 * @author egor
 */
public class Quests {
    private static Quests instance=new Quests();
    private static final long KALUGA_REGION_AREA_ID=1859;
    private static final long KALUGA_AREA_ID=43;
    private Quests(){
        
    }
    public static Quests getInstance(){
        return instance;
    }
    public void getData(String areaName){
        HHAPICriteria criteria=new HHAPICriteria();
        ArrayList<Vacancy> vacancies=HHAPIQuery.getInstance().getVacancies(criteria.setAreaID(HHAPIQuery.getInstance().getAreaIdByCountryName(areaName)), 100);
        PostgreSQL.getInstance().saveVacancyList(vacancies);
    }
    public void showData(){
        System.out.println("Average salary in Kaluga Region is "+HHAPIQuery.getInstance().getAverageSallaryAndOutputVacanciesCount(KALUGA_REGION_AREA_ID,true));
    }
    public void saveData(){
        long AverageSalary=(long) HHAPIQuery.getInstance().getAverageSallaryAndOutputVacanciesCount(KALUGA_REGION_AREA_ID,false);
        HHAPICriteria criteria=new HHAPICriteria();
        ArrayList<Vacancy> vacancies=HHAPIQuery.getInstance().getVacancies(criteria.setAreaID(KALUGA_AREA_ID).setSalary(AverageSalary), 0);
        new CSV("output.csv").saveVacancyList(vacancies);
    }
    public void showRange(long from,long to){
        ArrayList<Vacancy> vacancies=PostgreSQL.getInstance().getVacanciesBySalary(from, to);
        new CSV("output.csv").saveVacancyList(vacancies);
    }
    
}
