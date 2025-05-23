package com.example;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class JSONObjectProcessing {

    private String link = "https://api.thecatapi.com/v1/images/search?limit=1&has_breeds=1&api_key=live_36l2KLkGaEFjUXYdfnR9dh0DKbqEODPwdV8Xa6yuUExxkslOiBNEjLe2qYLpWAEA";
    private JSONObject obj;
    private JSONObject breeds;
    private ArrayList<String> catsDiscovered;

    public JSONObjectProcessing() throws Exception{
        obj = new JSONArray(API.getData(link)).getJSONObject(0);
        breeds = obj.getJSONArray("breeds").getJSONObject(0);
        catsDiscovered = new ArrayList<>();
        catsDiscovered.add(breeds.getString("name"));
    }
    public static void main(String[] args) throws Exception{
        // String jsonString = API.getData(link);
        // JSONArray arr = new JSONArray(jsonString);
        // JSONObject obj = arr.getJSONObject(0); //not in breeds
        // JSONArray breeds = obj.getJSONArray("breeds");

        // JSONObject data = breeds.getJSONObject(0); //anything insides breeds
        // String name = data.getString("name");
        // String life = data.getString("life_span");
        // System.out.println("Species: " + name +"\nLife Span: " + life + "\n");
        // String fact = data.getString("description");
        // System.out.println(fact);

        // String id = obj.getString("id");
        // System.out.println(id);
        JSONObjectProcessing a = new JSONObjectProcessing();
        System.out.println(a.getInfo());
    }

    public void newCat() throws Exception{
        obj = new JSONArray(API.getData(link)).getJSONObject(0);
        breeds = obj.getJSONArray("breeds").getJSONObject(0);
        catsDiscovered.add(breeds.getString("name"));
    }


    public String getInfo(){
        String name = breeds.getString("name");
        String life = breeds.getString("life_span");
        String fact = breeds.getString("description");
        return "Species: " + name +"\nLife Span: " + life + "\n" + fact;
    }

    public String imageURL(){
        String url = obj.getString("url");
        return url;
    }

    public int imageWidth(){
        return obj.getInt("width");
    }

    public int imageHeight(){
        return obj.getInt("height");
    }

    public ArrayList<String> getList(){
        return  catsDiscovered;
    }
}
