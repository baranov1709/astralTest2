/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rt.astraltest2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author egor
 */
@Entity
@Table (name = "vacancy")
public class Vacancy {
    @Id
    @Column
    private long id;
    @Column
    private long areaID;
    @Column
    private String url;
    @Column
    private String name;
    @Column
    private long emloyerID;
    @Column
    private long salaryFrom;
    @Column
    private long salaryTo;

    public void setSalaryFrom(long salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public void setSalaryTo(long salaryTo) {
        this.salaryTo = salaryTo;
    }

    public long getSalaryFrom() {
        return salaryFrom;
    }

    public long getSalaryTo() {
        return salaryTo;
    }

    public Vacancy(long id, long areaID, String url, String name, long emloyerID, long salaryFrom, long salaryTo) {
        this.id = id;
        this.areaID = areaID;
        this.url = url;
        this.name = name;
        this.emloyerID = emloyerID;
        this.salaryFrom = salaryFrom;
        this.salaryTo = salaryTo;
    }

    public Vacancy(long id, long areaID, String url, String name, long emloyerID) {
        this.id = id;
        this.areaID = areaID;
        this.url = url;
        this.name = name;
        this.emloyerID = emloyerID;
    }
    
    public Vacancy() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAreaID() {
        return areaID;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public long getEmloyerID() {
        return emloyerID;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAreaID(long areaID) {
        this.areaID = areaID;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmloyerID(long emloyerID) {
        this.emloyerID = emloyerID;
    }
    
}


