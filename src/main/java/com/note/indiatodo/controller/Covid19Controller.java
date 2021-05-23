package com.note.indiatodo.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.note.indiatodo.dto.VaccineDetails;
import io.github.bucket4j.Bucket;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * headers = {
 *     "accept":"application/json",
 *     "Accept-Language": "hi_IN",
 *     "cache-control":"max-age=0",
 *     "sec-fetch-dest": "document",
 *     "sec-fetch-mode": "navigate",
 *     "upgrade-insecure-requests": "1",
 *     "user-agent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36"
 * }
 */
@Controller
@RequestMapping("/")
public class Covid19Controller {
    @Autowired
    private WelcomeController wc;

    @RequestMapping("/covidgo2")
    public ModelAndView getDistricts(HttpServletRequest req) throws IOException, InterruptedException {
        String state_id = req.getParameter("state_id");
        var url="https://cdn-api.co-vin.in/api/v2/admin/location/districts/"+state_id;
        var request= HttpRequest.newBuilder().GET().uri(URI.create(url))
                .headers("content-type","application/json","user-agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36","Accept-Language","en_US")
                .build();
        var client= HttpClient.newBuilder().build();
        var response= client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject js= new JSONObject(response.body());
        JSONArray distArr=js.getJSONArray("districts");
        ModelAndView mv= new ModelAndView();
        mv.setViewName("CovidEntry");
        mv.addObject("distList",distArr);
        return mv;
    }
    @RequestMapping("/covidgo3")
    public ModelAndView getVaccineDetails(HttpServletRequest req){
        String dist_id = req.getParameter("dist_id");
        ModelAndView mv= new ModelAndView();
        mv.setViewName("CovidEntry");
        mv.addObject("count",1);
        mv.addObject("dist_id",dist_id);
        return mv;
    }
    @RequestMapping("/covidVaccine")
    public ModelAndView getVaccines(HttpServletRequest req) throws IOException, InterruptedException {
        ModelAndView mv = new ModelAndView();
        if(wc.getApiBucket().tryConsume(1)) {
            final String dist_id = req.getParameter("dist_id");
            final String date = req.getParameter("date");
            String[] split = date.split("-");
            String date2 = split[2] + "-" + split[1] + "-" + split[0];
            var url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id=" + dist_id + "&date=" + date2;
            var request = HttpRequest.newBuilder().GET().uri(URI.create(url))
                    .headers("content-type", "application/json", "user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36", "Accept-Language", "en_US")
                    .build();
            var client = HttpClient.newBuilder().build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject js = new JSONObject(response.body());
            JSONArray distArr = js.getJSONArray("sessions");
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            List<VaccineDetails> vaccineDetails = objectMapper.readValue(distArr.toString(), new TypeReference<List<VaccineDetails>>() {
            });
            mv.setViewName("VaccineTable");
            mv.addObject("vaccineList", vaccineDetails);
            return mv;
        }
        mv.setViewName("error");
        return mv;
    }
}
