package lav.pepbill;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashwin on 12/22/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    ArrayList<JSONObject> list;
    Context context;

    public MainAdapter(Context context, ArrayList<JSONObject> list) {
        this.list=list;
        this.context=context;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mainadapter, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        JSONObject jobj = list.get(position);
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
        }


        holder.employeename_tv.setText(name);
        holder.employeeid_tv.setText(id);
        holder.employeedesignation_tv.setText(designation);
        Glide.with(context).load("http://192.168.0.103/"+image)
                .into(holder.employeeimage_iv);
        Log.e("URL",image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView employeeimage_iv;
        TextView employeename_tv,employeeid_tv,employeedesignation_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            employeeimage_iv=(ImageView)itemView.findViewById(R.id.iv_employeeimage);
            employeename_tv=(TextView)itemView.findViewById(R.id.tv_employeename);
            employeeid_tv=(TextView)itemView.findViewById(R.id.tv_employeeid);
            employeedesignation_tv=(TextView)itemView.findViewById(R.id.tv_employeedesignation);
        }
    }
}
