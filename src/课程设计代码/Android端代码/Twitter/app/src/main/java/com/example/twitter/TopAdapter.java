package com.example.twitter;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/17.
 */

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ViewHolder> {
    private ArrayList<Info> mtopList;
    private Context context;
    //int _imgId, String _idnumber, String _wordTime, String _content
    public TopAdapter(Context context,ArrayList<Info> topList){
        this.context = context;//上下文
        this.mtopList = topList;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView top_image;
        TextView top_id;
        TextView top_time;
        TextView top_content;

        public ViewHolder(View view){
            super(view);
            top_image = (ImageView) view.findViewById(R.id.top_image);
            top_id = (TextView) view.findViewById(R.id.top_id);
            top_time = (TextView) view.findViewById(R.id.top_time);
            top_content = (TextView) view.findViewById(R.id.top_content);
        }

    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.top_list, null);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder (ViewHolder holder, int position){
        Info info = mtopList.get(position);

        holder.top_image.setImageURI(Uri.parse(info.get_imghead()));
        holder.top_id.setText(info._idnumber);
        holder.top_time.setText(info._wordTime);
        holder.top_content.setText(info._content);

    }

    public int getItemCount(){
        return mtopList.size();
    }

}
