package lav.pepbill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 1/2/2018.
 */

public class EditProfileEmp extends AppCompatActivity {
    TextView name_tv,id_tv,designation_tv;
    ImageView image_iv;
    Spinner edit_sp;
    EditText text_tv;
    Button edit_b,update_b;
    String count;
    JSONObject params = new JSONObject();
    String mobile,newname,newdob,newdoj,newemail,newblood,newmarital;
    String URL = "http://pepbill.in/pepbill/firstproject/employee_infoupdatedetails.php";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofileemp);

        intent = getIntent();

        name_tv=(TextView)findViewById(R.id.tv_name);
        id_tv=(TextView)findViewById(R.id.tv_id);
        designation_tv=(TextView)findViewById(R.id.tv_designation);
        image_iv=(ImageView)findViewById(R.id.iv_image);

        name_tv.setText(intent.getStringExtra("UN"));
        id_tv.setText(intent.getStringExtra("ID"));
        designation_tv.setText(intent.getStringExtra("DESIGNATION"));
        mobile=intent.getStringExtra("Mobile");

        edit_b=(Button)findViewById(R.id.b_edit);
        update_b=(Button)findViewById(R.id.b_update);
        text_tv=(EditText) findViewById(R.id.tv_text);
        edit_sp=(Spinner)findViewById(R.id.sp_edit);
        String[] edititems = new String[]{"Select","Name","DOB","DOJ","Email","Blood grp","Marital Status"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, edititems);
        edit_sp.setAdapter(adapter);


        edit_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = edit_sp.getSelectedItem().toString();

                if(item.equals("Name")){
                    String name = intent.getStringExtra("UN");
                    text_tv.setText(name);
                    text_tv.setEnabled(false);
                    count="one";
                    Log.e("COUNT", count);

                }else if(item.equals("DOB")){
                    String dob = intent.getStringExtra("DOB");
                    text_tv.setText(dob);
                    text_tv.setEnabled(false);
                    count="two";
                    Log.e("COUNT", count);

                }else if(item.equals("DOJ")){
                   String doj = intent.getStringExtra("DOJ");
                    text_tv.setText(doj);
                    text_tv.setEnabled(false);
                    count="three";
                    Log.e("COUNT", count);

                }else if(item.equals("Email")){
                    String email = intent.getStringExtra("Email");
                    text_tv.setText(email);
                    text_tv.setEnabled(false);
                    count="four";
                    Log.e("COUNT", count);

                }else if(item.equals("Blood grp")){
                    String blood = intent.getStringExtra("Blood");
                    text_tv.setText(blood);
                    text_tv.setEnabled(false);
                    count="five";
                    Log.e("COUNT", count);

                }else if(item.equals("Marital Status")){
                    String marital = intent.getStringExtra("Marital");
                    text_tv.setText(marital);
                    text_tv.setEnabled(false);
                    count="six";
                    Log.e("COUNT", count);

                }
                else if(item.equals("Select")){
                    text_tv.setText("");
                    text_tv.setEnabled(false);
                    count="seven";
                    Log.e("COUNT", count);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      /*  edit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_b.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });*/


      edit_b.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (count.equals("one")||count.equals("two")||count.equals("three")||count.equals("four")||count.equals("five")||count.equals("six")) {
                  text_tv.setEnabled(true);
              }else if (count.equals("seven")){
                  text_tv.setEnabled(false);
              }
          }
      });

        update_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count.equals("one")) {
                    newname = text_tv.getText().toString();
                    Log.e("NNAAMMEE", newname);
                    updateinfo();
                }else if (count.equals("two")) {
                    newdob = text_tv.getText().toString();
                    Log.e("NNAAMMEE", newdob);
                    updateinfo();
                }else if (count.equals("three")) {
                    newdoj = text_tv.getText().toString();
                    Log.e("NNAAMMEE", newdoj);
                    updateinfo();
                }else if (count.equals("four")) {
                    newemail = text_tv.getText().toString();
                    Log.e("NNAAMMEE", newemail);
                    updateinfo();
                }else if (count.equals("five")) {
                    newblood = text_tv.getText().toString();
                    Log.e("NNAAMMEE", newblood);
                    updateinfo();
                }else if (count.equals("six")) {
                    newmarital = text_tv.getText().toString();
                    Log.e("NNAAMMEE", newmarital);
                    updateinfo();
                }else if (count.equals("seven")) {

                }
            }
        });


    }

    public void updateinfo(){
        try {
            params.put("emp_phone", mobile);
            params.put("emp_dob", newdob);
            params.put("emp_dateofjoining", newdoj);
            params.put("emp_email", newemail);
            params.put("emp_bloodgroup", newblood);
            params.put("emp_materialstatus", newmarital);
            Log.e("PARAM", mobile);
        } catch (JSONException e) {
            Log.e("PARAM ERROR", "" + e);
            e.printStackTrace();
        }

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("METHOD PARAMS", String.valueOf(params));
                Log.e("URL", URL);
                Log.e("RES", response.toString());
                try {
                    String status = response.getString("status");
                    Log.e("STATUS", status);
                    if (status.equals("1")) {
                        Log.e("SUCCESS", "success");
                        String message = response.getString("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("INSERT", String.valueOf(e));
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOGIN ERROR", String.valueOf(error));

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");

                return map;
            }
        };
        obj.setRetryPolicy(new DefaultRetryPolicy(
                40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(obj, "tag");
    }
}
