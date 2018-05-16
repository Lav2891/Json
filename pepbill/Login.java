package lav.pepbill;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 12/19/2017.
 */

public class Login extends AppCompatActivity {
    EditText username_et, password_et;
    Button login_b;
    TextView forgotpass_tv;
    String username;
    String password;

    String URL = "http://pepbill.in/pepbill/firstproject/employee_loginpage.php";
    JSONObject params = new JSONObject();
    private ProgressDialog pDialog;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        username_et = (EditText) findViewById(R.id.et_username);
        password_et = (EditText) findViewById(R.id.et_password);
        login_b = (Button) findViewById(R.id.b_login);
        forgotpass_tv = (TextView) findViewById(R.id.tv_forgotpass);

        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

        login_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_et.getText().toString();
                password = password_et.getText().toString();
                int user = username.length();
                if (user == 10) {
                    logininsert();
                   // methodone();
                   // methodtwo();
                   // methodthree(Login.this,username,password);
                   // methodfour();
                  /*  Intent intent = new Intent(Login.this, MainEmployee.class);
                    intent.putExtra("key",username);
                    startActivity(intent);*/
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Username", Toast.LENGTH_SHORT).show();
                }

            }
        });

        forgotpass_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentforgot = new Intent(Login.this,EnterUserName.class);
                startActivity(intentforgot);
            }
        });
    }


    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void logininsert() {
        Log.e("METHOD", "METHOD");
        try {
            params.put("emp_phone", username);
            params.put("emp_password", password);
            Log.e("PARAM", username);
        } catch (JSONException e) {
            Log.e("PARAM ERROR", "" + e);
            e.printStackTrace();
        }
Log.e("x","x");
        showProgressDialog();
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
                        //Toast.makeText(getApplicationContext(),"oookkkkkkkk",Toast.LENGTH_SHORT).show();
                        String id = response.getString("emp_id");
                        String name = response.getString("emp_firstname");
                        String design = response.getString("emp_designation");
                        String img = response.getString("emp_image");
                        Log.e("IMG",img);
                        String dob = response.getString("emp_dob");
                        String phone = response.getString("emp_phone");
                        String blood = response.getString("emp_bloodgroup");
                        String email = response.getString("emp_email");
                        String address = response.getString("emp_address");
                        String doj = response.getString("emp_dateofjoining");
                        String marital = response.getString("emp_materialstatus");
                       /* byte[] decodedString = Base64.decode(img, Base64.DEFAULT | Base64.NO_WRAP);
                        Log.e("DECODE STRING", String.valueOf(decodedString));
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);*/


                      //  Log.e("ID",id);
                       // Log.e("NAME",name);
                        //Log.e("DESIGN",design);
                        //Log.e("MESSAGE",message);
                      //  Log.e("DECODE BYTE", String.valueOf(decodedByte));
                         Intent intent = new Intent(Login.this, MainEmployee.class);
                         intent.putExtra("UN",name);
                        intent.putExtra("ID",id);
                        intent.putExtra("DESIGN",design);
                        intent.putExtra("IMG",img);
                        intent.putExtra("DOB",dob);
                        intent.putExtra("PHONE",phone);
                        intent.putExtra("BLOOD",blood);
                        intent.putExtra("EMAIL",email);
                        intent.putExtra("ADDRESS",address);
                        intent.putExtra("DOJ",doj);
                        intent.putExtra("MARITAL",marital);
                      //  intent.putExtra("image",decodedByte);
                       /* Bitmap _bitmap = null; // your bitmap
                        ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                        _bitmap.compress(Bitmap.CompressFormat.PNG, 100, _bs);
                        byte [] b=_bs.toByteArray();
                        String temp= Base64.encodeToString(b, Base64.DEFAULT);
                        intent.putExtra("byteArray", temp);*/
                         startActivity(intent);
                        finish();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("INSERT", String.valueOf(e));
                }
                hideProgressDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOGIN ERROR", String.valueOf(error));
                hideProgressDialog();
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

    public void methodone(){
        Volley.newRequestQueue(Login.this).add(
                new JsonRequest<JSONArray>(Request.Method.POST, "http://192.168.0.103/firstproject/employee_loginpage.php",username ,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.e("SUCCESS",response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR METHOD",error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("emp_phone", username);
                        params.put("emp_password", password);
                        return params;
                    }

                    @Override
                    protected Response<JSONArray> parseNetworkResponse(
                            NetworkResponse response) {
                        try {
                            String jsonString = new String(response.data,
                                    HttpHeaderParser
                                            .parseCharset(response.headers));
                            return Response.success(new JSONArray(jsonString),
                                    HttpHeaderParser
                                            .parseCacheHeaders(response));
                        } catch (UnsupportedEncodingException e) {
                            return Response.error(new ParseError(e));
                        } catch (JSONException je) {
                            return Response.error(new ParseError(je));
                        }
                    }
                });
    }

    public void methodtwo(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Log.e("PONSE",response.toString());
                requestQueue.stop();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("OUTPUT","Error :" + error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }


        public void methodthree(Context context, final String username, final String password){

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest sr = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("PONSE",response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("RRE",error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("emp_phone", username);
                    params.put("emp_password", password);

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(sr);
        }

public void methodfour(){
    RequestQueue queue = Volley.newRequestQueue(Login.this);
    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the response string.
                    Log.e("PONSE",response.toString());

                   // Intent intent = new Intent(Login.this, MainEmployee.class);
                   // intent.putExtra("key",username);
                   // startActivity(intent);
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("RRE",error.toString());
        }
    }) {
        //adding parameters to the request
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("emp_phone", username);
            params.put("emp_password", password);
            return params;
        }
    };
    // Add the request to the RequestQueue.
    //queue.add(stringRequest);
    AppController.getInstance().addToRequestQueue(stringRequest, "tag");


}
}



