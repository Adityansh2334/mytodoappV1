package com.note.indiatodo.controller;

import com.note.indiatodo.dto.LoginUserValid;
import com.note.indiatodo.dto.ModalConfig;
import com.note.indiatodo.entity.Users;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


@Controller
public class WelcomeController {

    private final Bucket bucket;

    public WelcomeController() {
        Bandwidth b= Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(5)));
        bucket= Bucket4j.builder().addLimit(b).build();
    }

    @RequestMapping(value={"/welcome","/"})
    public ModelAndView welcomeUser(){
        ModelAndView mv= new ModelAndView();
        mv.setViewName("index");
        mv.addObject("content","homeview");
        mv.addObject("navbar","homenav");
        mv.addObject("modalCheck",new ModalConfig());
        mv.addObject("userForm",new Users());
        mv.addObject("loginValue",new LoginUserValid());
        return mv;
    }
    @RequestMapping("/covidgo")
    public ModelAndView covidRedirect() throws IOException, InterruptedException {
        ModelAndView mv = new ModelAndView();
        if(bucket.tryConsume(1)) {
            var url = "https://cdn-api.co-vin.in/api/v2/admin/location/states";
            var request = HttpRequest.newBuilder().GET().uri(URI.create(url))
                    .headers("content-type", "application/json", "user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36", "Accept-Language", "en_US")
                    .build();
            var client = HttpClient.newBuilder().build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject js = new JSONObject(response.body());
            JSONArray statesArr = js.getJSONArray("states");
            mv.setViewName("CovidEntry");
            mv.addObject("count", 0);
            mv.addObject("stateList", statesArr);
            return mv;
        }
        mv.setViewName("error");
        return mv;
    }
        public Bucket getApiBucket(){
            return this.bucket;
        }
}
