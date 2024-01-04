/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Ryan Chin
 */
public class NewsAPI {
    private static HttpURLConnection connection;
    private String[][] articles;
    
    public NewsAPI(){
        articles = new String[5][3];
        try{
            BufferedReader reader;
            String line;
            StringBuilder responseContent = new StringBuilder();
            
            URL url = new URL("https://newsapi.org/v2/everything?q=nature+climate+environment&from=2023-12-20&sortBy=publishedAt&apiKey=3b527360118545beb23c69faac50efdb");
            connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int status = connection.getResponseCode();
            System.out.println(status);        
            
            if (status>299){
                reader =new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) !=null){
                    responseContent.append(line);
                }
                reader.close();
            }
            
            try{
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(responseContent.toString());
                JSONArray newsArticle = (JSONArray) jsonObject.get("articles");
            
                int count=0;
                for(Object articleElement: newsArticle){
                    if (count>4)
                        break;
                    JSONObject article = (JSONObject) articleElement;
                    this.articles[count][0] = (String) article.get("title");
                    this.articles[count][1] = (String) article.get("url");
                    this.articles[count][2] = (String) article.get("publishedAt");
                    count++;
                }
            } catch(ParseException e){
                e.printStackTrace();
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public String[][] getArticles(){
        return articles;
    }
}
