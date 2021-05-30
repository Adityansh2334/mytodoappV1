package com.note.indiatodo.dto;

import javax.persistence.*;
import java.math.BigInteger;


public class CountryMapping {
    private String Country_Name;
    private String Country_Code_2A;
    private String Country_Code_3A;
    private Integer Year;
    private BigInteger Country_Population;

    @Transient
    private String formated_population;

    public String getFormated_population() {
        return formated_population;
    }

    public void setFormated_population(String formated_population) {
        this.formated_population = formated_population;
    }

    public String getCountry_Name() {
        return Country_Name;
    }

    public void setCountry_Name(String country_Name) {
        Country_Name = country_Name;
    }

    public String getCountry_Code_2A() {
        return Country_Code_2A;
    }

    public void setCountry_Code_2A(String country_Code_2A) {
        Country_Code_2A = country_Code_2A;
    }

    public String getCountry_Code_3A() {
        return Country_Code_3A;
    }

    public void setCountry_Code_3A(String country_Code_3A) {
        Country_Code_3A = country_Code_3A;
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        this.Year = year;
    }

    public BigInteger getCountry_Population() {
        return Country_Population;
    }

    public void setCountry_Population(BigInteger country_Population) {
        Country_Population = country_Population;
    }

    @Override
    public String toString() {
        return "CountryMapping{" +
                "Country_Name='" + Country_Name + '\'' +
                ", Country_Code_2A='" + Country_Code_2A + '\'' +
                ", Country_Code_3A='" + Country_Code_3A + '\'' +
                ", Year=" + Year +
                ", Country_Population=" + Country_Population +
                '}';
    }
}
