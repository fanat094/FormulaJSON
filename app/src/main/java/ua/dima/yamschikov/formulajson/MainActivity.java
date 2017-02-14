package ua.dima.yamschikov.formulajson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> tt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.list);

        ReadJson();
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
            tt = new ArrayList<>();

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                //Log.d("Details-->id", jo_inside.getString("id"));
                // Log.d("Details-->parent_id", jo_inside.getString("parent_id"));
                String moldova_id = jo_inside.getString("id");
                String moldova_parent_id = jo_inside.getString("parent_id");

                int result = Integer.parseInt(moldova_parent_id);

                if (result == 0) {
                    //Log.d("RESULT:","RESULT = 0");
                    hm_parentziro = new HashMap<String, String>();
                    hm_parentziro.put("id", moldova_id);
                    hm_parentziro.put("parent_id", moldova_parent_id);

                    formList.add(hm_parentziro);
                    //Log.d("FORM1", hm_parentziro.get("id"));
                }
                else{
                    //Log.d("RESULT:","RESULT != 0");
                    hm_noparentziro = new HashMap<String, String>();
                    hm_noparentziro.put("id", moldova_id);
                    hm_noparentziro.put("parent_id", moldova_parent_id);

                    formList2.add(hm_noparentziro);
                    //Log.d("FORM2", formList2.get(0).get("id"));
                };
            }

            for (int t=0; t<formList.size();t++){
                String list1_id = formList.get(t).get("id");
                int int_list1_id = Integer.parseInt(list1_id);
                //Log.d("List1", "List1   "+int_list1_id);
                int int_list2_parent_id = 0;
                int int_list2_id =0;

                for (int t2=0; t2<formList2.size();t2++){
                    String list2_parent_id = formList2.get(t2).get("parent_id");
                    int_list2_parent_id = Integer.parseInt(list2_parent_id);
                    String list2_id = formList2.get(t2).get("id");
                    int_list2_id = Integer.parseInt(list2_id);
                    //Log.d("List2", "List2   "+int_list2_parent_id);

                    if (int_list1_id==int_list2_parent_id){

                        Log.d("YES", "YES_OUT: "+int_list1_id+":"+int_list2_id);
                    }

                    else {

                        Log.d("NO","NO");
                    }


                }
            }

            tt.add(String.valueOf(formList));
            tt.add(String.valueOf(formList2));
            Log.d("TT0",tt.get(0).toString());
            Log.d("TT1",tt.get(1).toString());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, tt);

            lv.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}