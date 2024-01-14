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
        //Create multidimensional array to store news article that will be obtained from API
        //5 articles, with 3 details for each article, which is title, date published and url link
        articles = new String[5][3];
        try{
            BufferedReader reader;
            String line;
            StringBuilder responseContent = new StringBuilder();
            
            //Make API request by creating connection to the link
            URL url = new URL("https://newsapi.org/v2/everything?q=nature+climate+environment&from=2023-12-20&sortBy=publishedAt&apiKey=3b527360118545beb23c69faac50efdb");
            connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            //Get response status of the API request
            int status = connection.getResponseCode();
            System.out.println(status);        
            
            if (status>299){ //If unsuccessful
                reader =new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine())!=null){
                    responseContent.append(line);
                }
                reader.close();
            }else{ //If successful
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) !=null){
                    responseContent.append(line); //Read the returned content from the API request
                }
                reader.close();
            }
            
            //The API returns content in a JSON format. Therefore, we need to extract the information out
            try{
                JSONParser parser = new JSONParser(); //Object to parse string to JSONObject
                JSONObject jsonObject = (JSONObject) parser.parse(responseContent.toString()); //Parsing
                JSONArray newsArticle = (JSONArray) jsonObject.get("articles"); //Get the news articles from the object
                
                //Loop through the elements in the JSONObject, and extract the information we want
                int count=0;
                for(Object articleElement: newsArticle){
                    if (count>4) //We only need 5 top records, so loop only 5 times
                        break;
                    //Store the extracted information into the array
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
