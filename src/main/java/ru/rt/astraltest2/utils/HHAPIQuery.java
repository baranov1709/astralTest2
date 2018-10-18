/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rt.astraltest2.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.rt.astraltest2.entities.Country;
import ru.rt.astraltest2.entities.Vacancy;

/**
 *
 * @author egor
 */
public class HHAPIQuery {
    private static HHAPIQuery instance=new HHAPIQuery();
    private HHAPIQuery(){
        
    }
    public static HHAPIQuery getInstance(){
        return instance;
    }
    public static final String PATH="https://api.hh.ru";
    public static final String URL_COUNTRIES=PATH+"/areas/countries";
    public static final String URL_VACANCIES=PATH+"/vacancies";
    
    private String returnQueryResponse(String url){
        String result = null;
        if (url == null) {
            return result;
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            result=stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    private ArrayList<Country> getCountries(){
        ArrayList<Country> list=new ArrayList<Country>();
        JSONArray array=new JSONArray(returnQueryResponse(HHAPIQuery.URL_COUNTRIES));
        for (int i = 0; i < array.length(); i++) {
            JSONObject data=(JSONObject) array.get(i);
            list.add(new Country(data.getInt("id"), data.getString("name")));
        }
        return list;
    }
    public long getAreaIdByCountryName(String country){
        ArrayList<Country> countries=getCountries();
        long areaId=-1;
        for (int i = 0; i < countries.size(); i++) {
            if (countries.get(i).getName().equals(country))
            {
                areaId=countries.get(i).getId();
                break;
            }
        }
        return areaId;
    }
    private JSONObject getVacanciesJSON(long areaId, int perPage, int page){
        return new JSONObject(returnQueryResponse(HHAPIQuery.URL_VACANCIES+"?area="+areaId+"&per_page="+perPage+"&page="+page));
    }
    private JSONObject getVacanciesJSON(HHAPICriteria criteria){
        return new JSONObject(returnQueryResponse(HHAPIQuery.URL_VACANCIES+criteria.getContent()));
    }
    public ArrayList<Vacancy> getVacancies(HHAPICriteria criteria,int count){
        if(count<0)
            return null;
        ArrayList<Vacancy> list=new ArrayList<Vacancy>();
        int perPage=100;
        if ((count!=0)&&(count<=100))
            perPage=count;
        JSONObject first100Results=getVacanciesJSON(new HHAPICriteria(criteria).setPage(0).setPerPage(perPage));
        JSONArray array =new JSONArray(first100Results.get("items").toString());
        int pageCount=first100Results.getInt("pages");
        JSONObject[] arrayOfResults=new JSONObject[pageCount];
        arrayOfResults[0]=first100Results;
        for (int i = 1; i < pageCount; i++) {
            arrayOfResults[i]=getVacanciesJSON(new HHAPICriteria(criteria).setPage(0).setPerPage(perPage));
        }
        for (int i = 0; i < pageCount; i++) {
            JSONArray items=new JSONArray(arrayOfResults[i].get("items").toString());
            for (int j = 0; j < items.length(); j++) {
                list.add(getVacansyFromJSON((JSONObject)items.get(j)));
                if (list.size()==count)
                    return list;
            }
        }
        return list;
    }
    private Vacancy getVacansyFromJSON(JSONObject json){
        Vacancy vacancy = null;
        JSONObject area=json.getJSONObject("area");
        long areaId=area.getLong("id");
        long id=json.getLong("id");
        String url=json.getString("url");
        String name=json.getString("name");
        JSONObject employer=json.getJSONObject("employer");
        long employerId=employer.getLong("id");
        vacancy=new Vacancy(id, areaId, url, name, employerId);
        long salaryFrom;
        long salaryTo;
        try {
            JSONObject salary=json.getJSONObject("salary");
            try {
                salaryFrom=salary.getLong("from");
                vacancy.setSalaryFrom(salaryFrom);
            } catch (Exception e) {
            }
            try {
                salaryTo=salary.getLong("to");
                vacancy.setSalaryTo(salaryTo);
            } catch (Exception e) {
            }
        } catch (JSONException e) {
        }
        
        
        return vacancy;
    }
    public float getAverageSallaryAndOutputVacanciesCount(long areaId,boolean showCount){
        //Надо делать все быстро, а то кто-то добавит вакансию, а мы не получим этот результат, хотя шанс все равно есть) 
        //Поэтому сначала собираем все данные, а потом обрабатываем
        JSONObject first100Vacancies=getVacanciesJSON(areaId, 100, 0);
        int pageCount=first100Vacancies.getInt("pages");
        long found=first100Vacancies.getLong("found");
        if (showCount)
            System.out.println("Found "+found+" vacancies");
        if (found==0)
            return 0;
        long salaryIsSetCount=0;
        float result=0;
        JSONObject[] arrayOfResults=new JSONObject[pageCount];
        arrayOfResults[0]=first100Vacancies;
        for (int i = 1; i < pageCount; i++) {
            arrayOfResults[i]=getVacanciesJSON(areaId, 100, i);
        }
        //Собрали всю статистику теперь надо обработать данные
        for (int i = 0; i < pageCount; i++) {
            JSONArray items=new JSONArray(arrayOfResults[i].get("items").toString());
            for (int j = 0; j < items.length(); j++) {
                JSONObject data=(JSONObject)items.get(j);
                JSONObject salary=null;
                try{
                    salary=data.getJSONObject("salary");
                }
                catch(JSONException e){
                    
                }
                if (salary!=null){
                    int divider=0;
                    float salaryFrom=0;
                    float salaryTo=0;
                    try {
                        salaryFrom=salary.getLong("from");
                        divider++;
                    } catch (JSONException e) {
                    }
                    try {
                        salaryTo=salary.getLong("to");
                        divider++;
                    } catch (JSONException e) {
                    }
                    if (divider!=0){
                        result+=(salaryFrom+salaryTo)/divider;
                        salaryIsSetCount++;
                    }
                    
                }
                
            }
        }
        return result/salaryIsSetCount;
    }
}