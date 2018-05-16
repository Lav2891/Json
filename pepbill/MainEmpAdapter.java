package lav.pepbill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashwin on 12/22/2017.
 */

public class MainEmpAdapter extends RecyclerView.Adapter<MainEmpAdapter.ViewHolder>{

    ArrayList<JSONObject> list;
    Context context;



    public MainEmpAdapter(Context context,ArrayList<JSONObject> list) {
        this.list = list;
        this.context = context;

    }

    @Override
    public MainEmpAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mainempadapter, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MainEmpAdapter.ViewHolder holder, int position) {
        final JSONObject jobj = list.get(position);
        String name = null;
        String id = null;
        String design = null;
        String img = null;
        String ph = null;
        try {
            name = jobj.getString("FIRSTNAME");
            id = jobj.getString("ID");
            design = jobj.getString("DESIGNATION");
            img = jobj.getString("IMAGE");
            ph = jobj.getString("PHONE");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        holder.employeename_tv.setText(name);
        holder.employeeid_tv.setText(id);
        holder.employeedesignation_tv.setText(design);
        Glide.with(context).load("http://192.168.0.103/" + img)
        .into(holder.employeeimage_iv);

        final String finalPh = ph;
        holder.textlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context,"text",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Text.class);
                intent.putExtra("PH", finalPh);
                context.startActivity(intent);
            }
        });

        holder.calllayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

      // listner.phonemethod(id);


     /*   JSONObject jobj = list.get(position);
        String id = null;
        String name=null;
        String designation = null;
        String image = null;
        try {
            id = jobj.getString("ID");
            name = jobj.getString("FIRSTNAME");
            designation = jobj.getString("DESIGNATION");
            image = jobj.getString("IMAGE");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
      /*  ArrayList<String> x = slist.get(position);
        Log.e("heeeeeee","heeeeeeeee");
        String name=null;
        for(int i=0;i<x.size();i++){
            Log.e("I", String.valueOf(i));
            name=x.get(i);
            Log.e("nnnnnn",name);
        }
        Log.e("NAME",name);
        holder.employeename_tv.setText(name);

        /*int sl = x.size();
        //Log.e("SL", String.valueOf(sl));
     /*   String zero = x.get(0);
        String one = x.get(1);
        String two = x.get(2);
        Log.e("0",zero);
         Log.e("1",one);
        Log.e("2",two);*/

     /*   int a = 0;
        int b =3;
        int c =a+b;
        a =c;

        String n = x.get(a);

        Log.e("0",n);
       // Log.e("1",one);
        //Log.e("2",two);*/
       /* String n = null;
        String id = null;
        String d = null;
        // String im=null;

        for (int i = 0; i < sl; i++) {
            if (i % 3 == 0) {
                n = x.get(i);
                id = x.get(i + 1);
                d = x.get(i + 2);
//                im = x.get(i + 3);

                //  Log.e("IM", im);

            }
            Log.e("N", n);
            holder.employeename_tv.setText(n);
            Log.e("I", id);
            holder.employeeid_tv.setText(id);
            holder.employeedesignation_tv.setText(d);
            Log.e("D", d);

            // Glide.with(context).load("http://192.168.0.103/" + im)
            //.into(holder.employeeimage_iv);
        }*/

    }


      /*  holder.employeename_tv.setText(name);
        holder.employeeid_tv.setText(id);
        holder.employeedesignation_tv.setText(designation);
       // Glide.with(context).load("http://192.168.0.103/"+image)
                .into(holder.employeeimage_iv);
        //Log.e("URL",image);*/


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView employeeimage_iv;
        TextView employeename_tv,employeeid_tv,employeedesignation_tv;
        LinearLayout textlayout;
        LinearLayout calllayout;

        public ViewHolder(View itemView) {
            super(itemView);
            employeeimage_iv=(ImageView)itemView.findViewById(R.id.iv_employeeimage);
            employeename_tv=(TextView)itemView.findViewById(R.id.tv_employeename);
            employeeid_tv=(TextView)itemView.findViewById(R.id.tv_employeeid);
            employeedesignation_tv=(TextView)itemView.findViewById(R.id.tv_employeedesignation);
            textlayout = (LinearLayout)itemView.findViewById(R.id.tv_textlatout);
            calllayout = (LinearLayout)itemView.findViewById(R.id.tv_calllayout);
        }
    }


}


