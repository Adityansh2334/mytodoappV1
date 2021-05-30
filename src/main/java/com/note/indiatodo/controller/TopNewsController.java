package com.note.indiatodo.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.note.indiatodo.dto.ArticlesNews;
import com.note.indiatodo.dto.CountryMapping;
import com.note.indiatodo.service.CountryService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class TopNewsController {
    @Autowired
    private CountryService countryService;
    private static List<CountryMapping> countryDetails;
    @RequestMapping("/topnews")
    public ModelAndView getNewsFeeds(){
        final List<CountryMapping> filteredCountry = this.getCountryDetails();
        final String[] cat = getCategoryArray();
        ModelAndView mv= new ModelAndView();
        mv.addObject("country",filteredCountry);
        mv.addObject("category",cat);
        mv.addObject("flag",false);
        mv.setViewName("TopNews");
        return mv;
    }
    @RequestMapping("/viewnews")
    public ModelAndView getTopHeadLines(HttpServletRequest req) throws IOException, InterruptedException {
        String country = req.getParameter("country");
        String category = req.getParameter("category");
        String search = req.getParameter("search");
        String newsCount = req.getParameter("newsCount");

        String url;
        List<ArticlesNews> articlesNews=null;
        int totalNews=0;
        int nwCount=0;

        if(!newsCount.isEmpty())nwCount=Integer.parseInt(newsCount);
        if(country.isEmpty() && category.isEmpty() && search.isEmpty()){
            url="https://saurav.tech/NewsAPI/everything/fox-news.json";
            articlesNews=getArticleNewsList(url);
            totalNews=articlesNews.size();
        }else if(!search.isEmpty()){
            String encode = URLEncoder.encode(search, StandardCharsets.UTF_8);
            url="https://newsapi.org/v2/top-headlines?country="+country+
                    "&category="+category+"&q="+encode+"&apiKey=165886f0c6fc4fd5add04751e2d4fd7c";
            articlesNews=getArticleNewsList(url);
            totalNews=articlesNews.size();
        }else if(category.isEmpty()){
            String[] cate=getCategoryArray();
            List<ArticlesNews> ar= new ArrayList<>();
                for (String s : cate) {
                    url = "https://saurav.tech/NewsAPI/top-headlines/category/" + s + "/" + country.toLowerCase() + ".json";
                    if (ar.isEmpty()) ar = getArticleNewsList(url);
                    ar.addAll(getArticleNewsList(url));
                }
                articlesNews=ar;
                totalNews=ar.size();
        }else{
            if(country.isEmpty())url="https://saurav.tech/NewsAPI/top-headlines/category/"+category+"/in.json";
            else url="https://saurav.tech/NewsAPI/top-headlines/category/"+category+"/"+country.toLowerCase()+".json";
            articlesNews=getArticleNewsList(url);
            totalNews=articlesNews.size();
        }
        final List<CountryMapping> filteredCountry = this.getCountryDetails();
        final String[] cat = getCategoryArray();
        ModelAndView mv =new ModelAndView();
        if(nwCount>totalNews || nwCount==0){
            mv.addObject("flag",true);
            mv.addObject("newsCn",totalNews);
            mv.addObject("articles",articlesNews);
            mv.addObject("country",filteredCountry);
            mv.addObject("category",cat);
            mv.addObject("count",1);
            mv.addObject("msg","There is Only "+totalNews+" News Available Till Now!");
            mv.setViewName("TopNews");
            return mv;
        }
        List<ArticlesNews> filteredArticles= new ArrayList<ArticlesNews>();
        if(!search.isEmpty()) {
            for (int i = 0; i < 20; i++) {
                filteredArticles.add(articlesNews.get(i));
            }
        }else{
            for (int i = 0; i < nwCount; i++) {
                filteredArticles.add(articlesNews.get(i));
            }
        }
        mv.addObject("flag",true);
        mv.addObject("newsCn",nwCount);
        mv.addObject("articles",filteredArticles);
        mv.addObject("country",filteredCountry);
        mv.addObject("category",cat);
        mv.setViewName("TopNews");
        return mv;
    }
    private static String getFrmatedNumber(float i){
        //Number Format
        String[] arr = {"", "K", "M", "B", "T", "P", "E"};
        int index = 0;
        while ((i / 1000) >= 1) {
            i = i / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%s%s", decimalFormat.format(i), arr[index]);
    }
    private  List<CountryMapping> getCountryDetails(){
        if(countryDetails==null)countryDetails = countryService.getCountryDetails();
        ArrayList<String> ars= new ArrayList<String>();
//        String s= "aearataubebgbrcachcncocuczdeegfrgbgrhkhuidieilinitjpkrltlvmamxmyngnlnonzphplptrorsrusasesgsiskthtrtwuausveza";
       String s="inusaurufrgb";
        int temp=0;
        int temp2=2;
        for(int i=0;i<s.length();i++){
            if(temp==s.length()-2) {
                ars.add(s.substring(temp).toUpperCase());
                break;
            }
            ars.add(s.substring(temp,temp2).toUpperCase());
            temp=temp2;
            temp2+=2;
        }
        Iterator<CountryMapping> iterator = countryDetails.iterator();
        List<CountryMapping> filteredCountry= new ArrayList<CountryMapping>();
        while(iterator.hasNext()){
            CountryMapping next = iterator.next();
            String ca = next.getCountry_Code_2A();
            if(ars.contains(ca)){
                BigInteger country_population = next.getCountry_Population();
                String frmatedNumber = getFrmatedNumber(country_population.floatValue());
                next.setFormated_population(frmatedNumber);
                filteredCountry.add(next);
            }
        }
        return filteredCountry;
    }
    public static String[] getCategoryArray(){
        return new String[]{"business","entertainment","general","health","science","sports","technology"};
    }

    public static List<ArticlesNews> getArticleNewsList(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
                .headers("content-type", "application/json", "user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36", "Accept-Language", "")
                .build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jObj= new JSONObject(send.body());
        JSONArray articles = jObj.getJSONArray("articles");
        final ObjectMapper obm= new ObjectMapper();
        obm.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        obm.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        return obm.readValue(articles.toString(), new TypeReference<List<ArticlesNews>>() {
        });
    }
}
