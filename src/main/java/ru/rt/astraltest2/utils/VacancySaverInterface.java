/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rt.astraltest2.utils;

import java.util.ArrayList;
import ru.rt.astraltest2.entities.Vacancy;

/**
 *
 * @author egor
 */
public interface VacancySaverInterface {
    public void saveVacancyList(ArrayList<Vacancy> vacancies);
}
