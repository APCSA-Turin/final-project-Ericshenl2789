package com.example;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONObjectProcessing {

    private final String link = "https://api.thecatapi.com/v1/images/search?limit=1&has_breeds=1&api_key=live_36l2KLkGaEFjUXYdfnR9dh0DKbqEODPwdV8Xa6yuUExxkslOiBNEjLe2qYLpWAEA";
    private JSONObject obj;
    private JSONObject breeds;
    private ArrayList<ArrayList<String>> catsDiscovered;

    //initial information
    public JSONObjectProcessing() throws Exception{
        obj = new JSONArray(API.getData(link)).getJSONObject(0);
        breeds = obj.getJSONArray("breeds").getJSONObject(0);
        catsDiscovered = new ArrayList<>();
        catsDiscovered.add(new ArrayList<>(Arrays.asList(breeds.getString("name"), breeds.getString("id"), "1")));
    }

    //everytime the user presses new Cat to generate a new cat
    public void newCat() throws Exception{
        obj = new JSONArray(API.getData(link)).getJSONObject(0);
        breeds = obj.getJSONArray("breeds").getJSONObject(0);
        String name = breeds.getString("name");
        for(int i = 0; i < catsDiscovered.size(); i ++){
            if(catsDiscovered.get(i).get(0).equals(name)){
                int num = Integer.parseInt(catsDiscovered.get(i).get(2)) + 1;
                catsDiscovered.get(i).set(2, Integer.toString(num));
                break;
            }
            if(i == catsDiscovered.size() - 1 && !catsDiscovered.get(i).get(0).equals(name)){
                catsDiscovered.add(new ArrayList<>(Arrays.asList(name, breeds.getString("id"), "0")));
            }
        }
    }


    //gets the breed name, life span and description and returns it
    public String getInfo(){
        String name = breeds.getString("name");
        String life = breeds.getString("life_span");
        String fact = breeds.getString("description");
        return "Species: " + name +"\nLife Span: " + life + "\n" + fact;
    }

    //returns image url
    public String imageURL(){
        return obj.getString("url");
    }

    //returns the url for websites that have more info(if it is in the JSON)
    public ArrayList<String> moreInfo(String id) throws Exception {
        ArrayList<String> result = new ArrayList<>();
        JSONObject breeds = newCatId(id);

        if(breeds.has("cfa_url")){
            result.add(breeds.getString("cfa_url"));
        }
        if(breeds.has("vetstreet_url")){
            result.add(breeds.getString("vetstreet_url"));
        }
        if(breeds.has("vcahospitals_url")) {
            result.add(breeds.getString("vcahospitals_url"));
        }
        if (breeds.has("wikipedia_url")) {
            result.add(breeds.getString("wikipedia_url"));
        }
        return result;
    }

    //generates a cat with a specific breed
    public JSONObject newCatId(String id) throws Exception{
        String link = "https://api.thecatapi.com/v1/images/search?limit=1&breed_ids="+ id +"&api_key=live_36l2KLkGaEFjUXYdfnR9dh0DKbqEODPwdV8Xa6yuUExxkslOiBNEjLe2qYLpWAEA";
        obj = new JSONArray(API.getData(link)).getJSONObject(0);
        breeds = obj.getJSONArray("breeds").getJSONObject(0);
        return breeds;
    }

    public int totalCats(){
        int total = 0;
        for(ArrayList<String> a : catsDiscovered){
            total += Integer.parseInt(a.get(2));
        }
        return total;
    }

    public ArrayList<ArrayList<String>> getList(){return catsDiscovered;}
}
