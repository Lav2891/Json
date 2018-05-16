package lav.pepbill;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Ashwin on 1/11/2018.
 */

public class EnterUserName extends AppCompatActivity {
    EditText enterusername_et;
    Button submit_b;
    String username;

    String URL = "http://pepbill.in/pepbill/firstproject/employee_forgetpassword1.php";
    JSONObject params = new JSONObject();
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterusername);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        enterusername_et=(EditText)findViewById(R.id.et_enterusername);
        submit_b=(Button)findViewById(R.id.b_submit);

        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = enterusername_et.getText().toString();
                upload();
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

    public void upload(){
        try {
            params.put("emp_phone", username);
            Log.e("PARAM", username);
        } catch (JSONException e) {
            Log.e("PARAM ERROR", "" + e);
            e.printStackTrace();
        }

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
                        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
                        Intent intentotp = new Intent(EnterUserName.this,OTP.class);
                        startActivity(intentotp);
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
}
