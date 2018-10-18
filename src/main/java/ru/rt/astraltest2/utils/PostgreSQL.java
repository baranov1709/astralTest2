/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rt.astraltest2.utils;

import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.rt.astraltest2.entities.Vacancy;

/**
 *
 * @author egor
 */
public class PostgreSQL implements VacancySaverInterface{
    private static SessionFactory sessionFactory;
    private static PostgreSQL instance=new PostgreSQL();
    public static PostgreSQL getInstance(){
        if (instance==null)
            instance=new PostgreSQL();
        return instance;
    }
    private PostgreSQL(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
            e.printStackTrace();
        }
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public void shutdown() {
        getSessionFactory().close();
    }

    @Override
    public void saveVacancyList(ArrayList<Vacancy> vacancies) {
        Session session=PostgreSQL.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (int i = 0; i < vacancies.size(); i++) {
                session.save(vacancies.get(i));
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            session.close();
        }
    }
    
    public ArrayList<Vacancy> getVacanciesBySalary(long from,long to){
        ArrayList<Vacancy> vacancies = null;
        Session session=PostgreSQL.getInstance().getSessionFactory().openSession();
        try {//Query query=session.createQuery("from Vacancy where (salaryfrom between "+from+" and "+to+")or(salaryto between "+from+" and "+to+"");
            Query query=session.createQuery("from Vacancy where (salaryfrom between "+from+" and "+to+")or(salaryto between "+from+" and "+to+")");
            vacancies=(ArrayList<Vacancy>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            session.close();
        }
        return vacancies;
    }
}
