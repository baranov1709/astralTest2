/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rt.astraltest2;

import ru.rt.astraltest2.utils.PostgreSQL;


/**
 *
 * @author egor
 */
public class Start {
    public static void main(String[] args) {
        if (args[0].equals("getData")){
            if (args.length==2){
                Quests.getInstance().getData(args[1]);
                PostgreSQL.getInstance().shutdown();
            }
            else
                throwHelpMessage();
        }else
            if (args[0].equals("showData")){
                Quests.getInstance().showData();
            }else
                if (args[0].equals("saveData")){
                    Quests.getInstance().saveData();
                }else
                    if (args[0].equals("showRange")){
                        if (args.length==3){
                            long from=-1;
                            long to=-1;
                            try {
                                from=Long.parseLong(args[1]);
                                to=Long.parseLong(args[2]);
                            } catch (Exception e) {
                                throwHelpMessage();
                            }
                            if ((from!=-1)&&(to!=-1))
                                    Quests.getInstance().showRange(from, to);
                        }
                        else
                            throwHelpMessage();
                    }else
                        throwHelpMessage();
//        Quests.getInstance().getData("Россия");
//        Quests.getInstance().showData();
//        Quests.getInstance().saveData();
//        Quests.getInstance().showRange(10000, 15555);
//        PostgreSQL.getInstance().shutdown();
        
        
    }
    public static  void throwHelpMessage(){
        System.out.println("getData Country_name");
        System.out.println("showData");
        System.out.println("saveData");
        System.out.println("showRange from to");
    }
}
