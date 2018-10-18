/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rt.astraltest2.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.rt.astraltest2.entities.Vacancy;

/**
 *
 * @author egor
 */
public class CSV implements VacancySaverInterface{
    private String filePath;
    private static final String[] headers={"id","area_id","url","name","emloyerID","salaryFrom","salaryTo"};
    private String[][] values;

    public CSV(String filePath) {
        this.filePath = filePath;
    }
    
    
    
    private void saveCSV(){
        File file = new File(filePath);
        if (file.exists())
        file.delete();
        String content="";
        for (int i = 0; i < headers.length; i++) {
            content+=headers[i]+";";
        }
        content+="\n";
        for (int i = 0; i < values.length; i++) {
            String[] valuesLine=values[i];
            for (int j = 0; j < valuesLine.length; j++) {
                content+=valuesLine[j]+";";
            }
            content+="\n";
        }
        System.out.println(content);
        try {
            FileWriter writer=new FileWriter(filePath, false);
            writer.write(content);
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void saveVacancyList(ArrayList<Vacancy> vacancies) {
        values=new String[vacancies.size()][headers.length];
        for (int i = 0; i < vacancies.size(); i++) {
            values[i][0]=vacancies.get(i).getId()+"";
            values[i][1]=vacancies.get(i).getAreaID()+"";
            values[i][2]=vacancies.get(i).getUrl()+"";
            values[i][3]=vacancies.get(i).getName()+"";
            values[i][4]=vacancies.get(i).getEmloyerID()+"";
            values[i][5]=vacancies.get(i).getSalaryFrom()+"";
            values[i][6]=vacancies.get(i).getSalaryTo()+"";
        }
        saveCSV();
    }
}
