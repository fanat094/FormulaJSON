package ua.dima.yamschikov.formulajson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ReadJson();
        //loadJSONFromAsset();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("moldova.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Log.d("MOLDOVA", json);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void ReadJson (){

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("comments");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> hm_parentziro;
            ArrayList<HashMap<String, String>> formList2 = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> hm_noparentziro;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                //Log.d("Details-->id", jo_inside.getString("id"));
               // Log.d("Details-->parent_id", jo_inside.getString("parent_id"));
                String moldova_id = jo_inside.getString("id");
                String moldova_parent_id = jo_inside.getString("parent_id");

                int result = Integer.parseInt(moldova_parent_id);

                if (result == 0) {
                    Log.d("RESULT:","RESULT = 0");
                    hm_parentziro = new HashMap<String, String>();
                    hm_parentziro.put("id", moldova_id);
                    hm_parentziro.put("parent_id", moldova_parent_id);

                    formList.add(hm_parentziro);
                    Log.d("FORM1", hm_parentziro.get("id"));
                }
                else{
                    Log.d("RESULT:","RESULT != 0");
                    hm_noparentziro = new HashMap<String, String>();
                    hm_noparentziro.put("id", moldova_id);
                    hm_noparentziro.put("parent_id", moldova_parent_id);

                    formList2.add(hm_noparentziro);
                    Log.d("FORM2", formList2.get(0).get("id"));

                };
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
