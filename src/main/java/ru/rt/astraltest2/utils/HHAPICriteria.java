/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rt.astraltest2.utils;

/**
 *
 * @author egor
 */
public class HHAPICriteria {
    private String content="";
    private long areaID=0;
    private int page=0;
    private int perPage=0;
    private long salary=0;

    public long getAreaID() {
        return areaID;
    }

    public int getPage() {
        return page;
    }

    public int getPerPage() {
        return perPage;
    }

    public long getSalary() {
        return salary;
    }
    
    public HHAPICriteria(){
        content+="?";
    }
    public HHAPICriteria(HHAPICriteria parent){
        this.content=parent.getContent();
    }
    private void addAmpersant(){
        if (content.length()!=1)
            content+="&";
    }
    public HHAPICriteria setAreaID(long areaId){
        if (areaId==0)
            return this;
        addAmpersant();
        this.areaID=areaId;
        content+="area="+areaId;
        return this;
    }
    
    public HHAPICriteria setPage(int page){
        addAmpersant();
        this.page=page;
        content+="page="+page;
        return this;
    }
    public HHAPICriteria setPerPage(int perPage){
        addAmpersant();
        this.perPage=perPage;
        content+="per_page="+perPage;
        return this;
    }
    public HHAPICriteria setSalary(long salary){
        if (salary==0)
            return this;
        addAmpersant();
        this.salary=salary;
        content+="salary="+salary;
        return this;
    } 
    
    
    public String getContent(){
        if (content.length()==1)
            return "";
        return content;
    }
}
