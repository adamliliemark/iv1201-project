package com.iv1201.project.recruitment.web;


import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class ListForm {

    private ArrayList<String> months;
    private ArrayList<String> years;
    private String date;
    private String competence;
    private String name;

    ListForm(){
        setMonths();
        setYears();
    }

    private void setMonths(){
        months = new ArrayList<>();
        months.addAll(Arrays.asList(new DateFormatSymbols().getMonths()));
    }

    public ArrayList<String> getMonths() {
        return months;
    }

    public ArrayList<String> getYears() {
        return years;
    }

    private void setYears() {

        int lowYear = 1980;
        int highYear = Calendar.getInstance().get(Calendar.YEAR);
        years = new ArrayList<>();

        for (int i = lowYear; i <= highYear; i++)
          years.add(Integer.toString(i));

        years.add("");
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
