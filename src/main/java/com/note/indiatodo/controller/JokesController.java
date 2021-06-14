package com.note.indiatodo.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class JokesController {

    @RequestMapping("/jokesandmemes")
    public ModelAndView viewJokes(){
        ModelAndView mv=getMv();
        mv.addObject("flag",false);
        mv.setViewName("Jokes");
        return mv;
    }
    @RequestMapping("/memes")
    public ModelAndView getMemes() throws IOException, InterruptedException {
        ModelAndView mv = getMv();
        String[] memeUrl = getMemeUrl();
        mv.addObject("title","Title: "+memeUrl[1]);
        mv.addObject("link",memeUrl[0]);
        mv.addObject("flag",true);
        mv.setViewName("Jokes");
        return mv;
    }
    @RequestMapping("/jokes")
    public ModelAndView getJokes() throws IOException, InterruptedException {
        ModelAndView mv = getMv();
        String jokeData = getJokeData();
        if(jokeData.contains("#")){
            String[] split = jokeData.split("#");
            String one="Ketty: "+split[0];
            String two="Adi: "+split[1];
            mv.addObject("joke1",one);
            mv.addObject("joke2",two);
            mv.setViewName("Jokes");
            mv.addObject("flag",false);
            return mv;
        }
        mv.addObject("joke1",jokeData);
        mv.setViewName("Jokes");
        return mv;
    }


    private static ModelAndView getMv(){
        return new ModelAndView();
    }

    private static String getJokeData() throws IOException, InterruptedException {
        String url="https://v2.jokeapi.dev/joke/Any";
        HttpRequest build = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient cl= HttpClient.newBuilder().build();
        HttpResponse<String> send = cl.send(build, HttpResponse.BodyHandlers.ofString());
        JSONObject js= new JSONObject(send.body());
        if(js.getString("type").equalsIgnoreCase("single")){
            return js.getString("joke");
        }
        String one= js.getString("setup");
        String two=js.getString("delivery");
        return one+"#"+two;
    }
    private static String[] getMemeUrl() throws IOException, InterruptedException {
        String url="https://meme-api.herokuapp.com/gimme";
        HttpRequest build = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient cl= HttpClient.newBuilder().build();
        HttpResponse<String> send = cl.send(build, HttpResponse.BodyHandlers.ofString());
        JSONObject js= new JSONObject(send.body());
        String arr[]={js.getString("url"),js.getString("title")};
       return arr;
    }
}
