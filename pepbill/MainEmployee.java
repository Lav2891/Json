package lav.pepbill;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ashwin on 12/21/2017.
 */

public class MainEmployee extends AppCompatActivity {

    SearchView searchview_sv;
    RecyclerView recyclerView;
    LinearLayoutManager lm;
    MainEmpAdapter adapter;
    ArrayList<JSONObject> list = new ArrayList<>();
    ArrayList<ArrayList<String>> slist = new ArrayList<>();
    private ProgressDialog pDialog;
    String URL = "http://pepbill.in/pepbill/firstproject/employee_homealldata.php";
    String toolurl = "http://192.168.0.107/firstproject/employee_home_loginprofile.php";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    TextView profile_tv, changepassword_tv, logout_tv;

    TextView toolname, toolid,tooldesignation;
    ImageView toolimage;
    RequestQueue requestQueue;
    String un,id,design,im,dob,phone,blood,email,address,doj,marital;
    JSONObject oob = new JSONObject();
    HashMap<String, String> p;
    ArrayList<String> pmsdata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        profile_tv=(TextView)findViewById(R.id.tv_profile);
        changepassword_tv=(TextView)findViewById(R.id.tv_changepassword);
        logout_tv=(TextView)findViewById(R.id.tv_logout);
        logout_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainEmployee.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setGroupIndicator(null);

        toolname=(TextView)findViewById(R.id.tv_toolname);
        toolid=(TextView)findViewById(R.id.tv_toolid);
        tooldesignation=(TextView)findViewById(R.id.tv_tooldesignation);
        toolimage=(ImageView)findViewById(R.id.iv_toolimage);
       // toolmethod();

        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
       // threemethod();
        Intent intent = getIntent();
         un =intent.getStringExtra("UN");
        id = intent.getStringExtra("ID");
        design = intent.getStringExtra("DESIGN");
        im = intent.getStringExtra("IMG");
       // Log.e("OUT",un+id+design+im);
        dob = intent.getStringExtra("DOB");
        Log.e("DOB",dob);
        phone=intent.getStringExtra("PHONE");
        blood=intent.getStringExtra("BLOOD");
        email=intent.getStringExtra("EMAIL");
        address=intent.getStringExtra("ADDRESS");
        doj=intent.getStringExtra("DOJ");
        marital=intent.getStringExtra("MARITAL");
       // fourmethod(key);
       // fivemethod(key);

        toolname.setText(un);
        toolid.setText(id);
        tooldesignation.setText(design);
        Glide.with(MainEmployee.this).load("http://192.168.0.107/" + im)
                .into(toolimage);

        profile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentprofile = new Intent(MainEmployee.this,ProfileEmp.class);
                intentprofile.putExtra("DOB",dob);
                intentprofile.putExtra("PHONE",phone);
                intentprofile.putExtra("BLOOD",blood);
                intentprofile.putExtra("EMAIL",email);
                intentprofile.putExtra("ADDRESS",address);
                intentprofile.putExtra("DOJ",doj);
                intentprofile.putExtra("MARITAL",marital);
                intentprofile.putExtra("UN",un);
                intentprofile.putExtra("ID",id);
                intentprofile.putExtra("DESIGN",design);
                intentprofile.putExtra("IM",im);
                startActivity(intentprofile);
            }
        });

        changepassword_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentchange = new Intent(MainEmployee.this,ChangePass.class);
                intentchange.putExtra("PHONE",phone);
                startActivity(intentchange);
            }
        });

        pmsdata.add(phone);
        pmsdata.add(un);
        pmsdata.add(id);
        pmsdata.add(design);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, pmsdata);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        searchview_sv = (SearchView)findViewById(R.id.sv_searchview);

        recyclerView=(RecyclerView)findViewById(R.id.rv);
        lm=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        method();

       p  = new HashMap<String, String>();
     //   p.put("user", key);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Pepbill Management System (PMS)");


        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("Task Management");
        top250.add("Leave Application");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }


    public void method() {
        JsonArrayRequest req = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int length = response.length();
                        Log.e("LENGTH", String.valueOf(length));
                        for(int i=0;i<response.length();i++) {
                            try {
                                JSONObject jobj = response.getJSONObject(i);
                                 String name = jobj.getString("FIRSTNAME");
                                String id = jobj.getString("ID");
                                 String designation = jobj.getString("DESIGNATION");
                                String image = jobj.getString("IMAGE");
                                String phone = jobj.getString("PHONE");
                                //Log.e("DES",designation);

                                // p.setName(name);
                                //p.setId(id);
                                //  p.setDesignation(designation);

                                // list.add(jobj);

                                /*String design = jobj.getString("DESIGNATION");
                                String name = jobj.getString("FIRSTNAME");
                                String id = jobj.getString("ID");*/
                                // Log.e("D",design);
                                //p.setId(id);
                                //p.setName(name);
                                // p.setDesignation(design);
                                list.add(jobj);

                              //  list.add(image);
                                //Log.e("LIST", String.valueOf(list));
                               // list.add(name);
                                //list.add(id);
                               // slist.add(list);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("CATCH ERROR", String.valueOf(e));
                            }
                        }

                        adapter=new MainEmpAdapter(MainEmployee.this,list);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("E",error.toString());

            }
        });


        AppController.getInstance().addToRequestQueue(req);
    }

    public void toolmethod(){
        Log.e("AA","aaaa");
        JsonArrayRequest req = new JsonArrayRequest(toolurl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("PONSE/response", String.valueOf(response));
                        for(int i=0;i<response.length();i++) {
                            try {
                                Log.e("PONSE", String.valueOf(response));
                                JSONObject jobj = response.getJSONObject(i);
                                String name = jobj.getString("FIRSTNAME");
                                String id = jobj.getString("ID");
                                String designation = jobj.getString("DESIGNATION");
                                String image = jobj.getString("IMAGE");

                                Log.e("FIRSTNAME",name);
                                Log.e("ID",id);
                                Log.e("IMAGE",image);

                                toolname.setText(name);
                                toolid.setText(id);
                                tooldesignation.setText(designation);
                                Glide.with(MainEmployee.this).load("http://192.168.0.103/" + image)
                                .into(toolimage);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("CATCH ERROR", String.valueOf(e));
                            }
                        }

                     //   adapter=new MainEmpAdapter(MainEmployee.this,slist);
                       // int x = list.size();
                       // Log.e("X", String.valueOf(x));
                       // recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("E",error.toString());

            }
        });


        AppController.getInstance().addToRequestQueue(req);
    }

    public void threemethod(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, toolurl, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
               // t.setText(response);
                Log.e("PONSE",response);
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


    public void fourmethod(final String key){
        StringRequest postRequest = new StringRequest(Request.Method.POST, toolurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("FETCH RESPONSE",response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                           String name= jsonResponse.getString("FIRSTNAME");
                            String id = jsonResponse.getString("ID");
Log.e("VALUE1",name);
                            Log.e("VALUE2",id);

                        } catch (JSONException e) {
                            e.printStackTrace();
                           Log.e("E", String.valueOf(e));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                       Log.e("EE",error.toString());
                    }
                }
        ) {
            // here is params will add to your url using post method
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", key);
                //params.put("2ndParamName","valueoF2ndParam");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    public void fivemethod(final String key){

       /* try {
            oob.put(String.valueOf("user"), key);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("PARM ERROR",e.toString());
        }*/

     /*   Volley.newRequestQueue(getApplicationContext())
                .add(new JsonRequest<JSONArray>(Request.Method.POST,
                        toolurl,
                             new JSONObject(p)  ,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray jsonArray) {
                                Log.d("response", "res-rec is" + jsonArray);
                                if (jsonArray == null) {
                                    Log.e("SERVER","nosever");

                                } else {

                                    Log.e("SUCCESS","success");
                                    for(int i=0;i<jsonArray.length();i++) {
                                        JSONObject jobj = null;
                                        try {
                                            jobj = jsonArray.getJSONObject(i);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.e("JOBJ",e.toString());
                                        }

                                        try {
                                          String  name = jobj.getString("FIRSTNAME");
                                             String id = jobj.getString("ID");
                                          //  String designation = jobj.getString("DESIGNATION");
                                            String image = jobj.getString("IMAGE");

                                            Log.e("FIRSTNAME",name);
                                            Log.e("ID",id);
                                            Log.e("IMAGE",image);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.e("STR",e.toString());
                                        }
                                    }


                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.d("Login request", "Error: " + volleyError.getMessage());
                        Log.d("Volley Error:", "Volley Error:" + volleyError.getMessage());
                    }
                })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {


                        Map<String, String> params = new HashMap<String, String>();
                        // params.put("uniquesessiontokenid", "39676161-b890-4d10-8c96-7aa3d9724119");
                        params.put("user", key);

                        return super.getParams();
                    }
                    @Override
                    protected Response<JSONArray> parseNetworkResponse(NetworkResponse networkResponse) {


                        try {
                            String jsonString = new String(networkResponse.data,
                                    HttpHeaderParser
                                            .parseCharset(networkResponse.headers));
                            return Response.success(new JSONArray(jsonString),
                                    HttpHeaderParser
                                            .parseCacheHeaders(networkResponse));
                        } catch (UnsupportedEncodingException e) {
                            return Response.error(new ParseError(e));
                        } catch (JSONException je) {
                            return Response.error(new ParseError(je));
                        }

                        //  return null;
                    }
                }
                );*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionemployee_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
