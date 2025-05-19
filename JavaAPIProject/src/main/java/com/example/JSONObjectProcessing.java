package com.example;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class JSONObjectProcessing {
    public static void main(String[] args) throws Exception{
        String link = "https://api.thecatapi.com/v1/images/search?limit=1&has_breeds=1&api_key=live_36l2KLkGaEFjUXYdfnR9dh0DKbqEODPwdV8Xa6yuUExxkslOiBNEjLe2qYLpWAEA";
        String jsonString = API.getData(link);
        JSONArray arr = new JSONArray(jsonString);
        JSONObject obj = arr.getJSONObject(0); //not in breeds
        JSONArray breeds = obj.getJSONArray("breeds");
        
        JSONObject data = breeds.getJSONObject(0); //anything insides breeds
        String name = data.getString("name");
        String life = data.getString("life_span");
        System.out.println("Species: " + name +"\nLife Span: " + life + "\n");
        String fact = data.getString("description");
        System.out.println(fact);
        
        String id = obj.getString("id");
        System.out.println(id);
      
    }
}
