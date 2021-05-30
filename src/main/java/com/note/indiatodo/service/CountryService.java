package com.note.indiatodo.service;

import com.note.indiatodo.dao.CountryDao;
import com.note.indiatodo.dto.CountryMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    @Autowired
    private CountryDao countryDao;
    public List<CountryMapping> getCountryDetails(){
        return countryDao.getConDetails();
    }
}
