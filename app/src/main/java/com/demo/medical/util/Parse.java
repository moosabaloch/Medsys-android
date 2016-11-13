package com.demo.medical.util;

import com.demo.medical.model.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Moosa moosa.bh@gmail.com on 11/13/2016 13 November.
 * Everything is possible in programming.
 */

public class Parse {

    public static ArrayList<Patient> getPatientsArray(String array) throws JSONException {
/*        [
            {"_id":"582866a841140624d30fa93d",
            "doc_id":"5826469fc5e53f3b975e09bd",
            "name":"Hammad",
            "disease":"Fever",
            "admit":true,
            "is_admin":false,
            "condition":"Normal",
            "admit_date":1479042725878,
            "discharge_date":null,
            "__v":0}]
  */
        ArrayList<Patient> patients = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(array);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                Patient p = new Patient();
                p.setId(json.getString("_id"));
                p.setName(json.getString("name"));
                p.setDisease(json.getString("disease"));
                p.setAdmit(json.getBoolean("admit"));
                p.setCondition(json.getString("condition"));
                p.setAdmitDate(json.getLong("admit_date"));
                p.setDoctor_id(json.getString("doc_id"));
                patients.add(p);
            }


        return patients;
    }

}
