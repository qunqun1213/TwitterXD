package com.example.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/12/18.
 */

public class fdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //不转发
    public static final int TYPE_1 = 0;
    //转发
    public static final int TYPE_2 = 1;
    //转发的原文被删除
    public static final int TYPE_3 = -1;
    //原创 有图片
    public static final int TYPE_O_PIC = -4;
    private List<Info> mtList;
    private Activity activity;
    private int Type = 0;
    private LayoutInflater mLayoutInflater;
    public static final String getType = "http://192.168.43.228:8080" + "/Twitter/getType.jsp";
    public fdAdapter(List<Info> m){
        this.mtList = m;
    }
    private Context mContext;
    public fdAdapter(List<Info> infos, Context mContext){
        this.mtList = infos;
        this.mContext = mContext;
        this.activity = (Homepage)mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if(mtList.get(position).getType() == 0)
            return TYPE_1;
        else if(mtList.get(position).getType() == -1)
            return TYPE_3;
        else if (mtList.get(position).getType() == -4)
            return TYPE_O_PIC;
        else
            return TYPE_2;
    }

    @Override
    public int getItemCount() {
        return mtList.size();
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder=null;
        View view;
        if(viewType == TYPE_1){
            view = inflater.inflate(R.layout.user_original,parent,false);

            holder = new ViewHolder(view);
        }
        else if(viewType == TYPE_2 ){
            view = inflater.inflate(R.layout.user_non_original,parent,false);
            holder = new ViewHolder2(view);
        }
        else if(viewType == TYPE_3){
            view = inflater.inflate(R.layout.user_non_original_del,parent,false);
            holder = new ViewHolder3(view);
        }
        else if(viewType == TYPE_O_PIC){
            view = inflater.inflate(R.layout.top_list,parent,false);
            holder = new ViewHolder4(view);
        }
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Info info = mtList.get(position);
        switch (info.getType()){
            case TYPE_1:
                ViewHolder h = (ViewHolder)holder;
                h.View1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),UserArticle.class);
                        intent.putExtra("articleId",info.getArticleId());
                        v.getContext().startActivity(intent);
                      //  Toast.makeText(v.getContext(),String.valueOf(info.getArticleId()),Toast.LENGTH_SHORT).show();
                    }
                });
                //StringBuilder builder = new StringBuilder();
               // builder.append("/sdcard/").append(info.get_imghead());
                File file = new File(info.get_imghead());
                if(!file.exists())
                    h._headimage.setImageResource(R.drawable.dft);
                else
                    h._headimage.setImageURI(Uri.parse(info.get_imghead()));
                //h._headimage.setImageResource(mtList.get(position).get_imghead());
                h._id.setText(mtList.get(position).get_idnumber());
                h._name.setText(mtList.get(position).get_name());
                h._time.setText(mtList.get(position).get_wordTime());
                // h._time.setText(String.valueOf(info.getType()));
                h._content.setText(mtList.get(position).get_content());
                h._cmt.setText(mtList.get(position).get_ctm());
                h._lk.setText(mtList.get(position).get_lk());
                h._tn.setText(mtList.get(position).get_tn());
                break;
            case TYPE_O_PIC:
                ViewHolder4 h4 = (ViewHolder4)holder;

                //到具体页面
                h4.View4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),UserArticle.class);
                       // Toast.makeText(mContext, String.valueOf(info.getArticleId()),Toast.LENGTH_SHORT).show();
                        //intent.putExtra("articleId",String.valueOf(info.getArticleId()));
                        intent.putExtra("articleId",info.getArticleId());
                        v.getContext().startActivity(intent);
                    }
                });
                h4._id.setText(mtList.get(position).get_idnumber());
                h4._time.setText(mtList.get(position).get_wordTime());
                // h._time.setText(String.valueOf(info.getType()));
                h4._content.setText(mtList.get(position).get_content());
                h4._pic.setImageURI(Uri.parse(mtList.get(position).getPic()));
                h4._name.setText(mtList.get(position)._name);
                break;
            case TYPE_3:
                ViewHolder3 h3 = (ViewHolder3)holder;
                h3.View3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),UserArticle.class);
                        intent.putExtra("articleId",info.getArticleId());
                        v.getContext().startActivity(intent);
                        //Toast.makeText(v.getContext(),String.valueOf(info.getArticleId()),Toast.LENGTH_SHORT).show();
                    }
                });
               // StringBuilder builder3 = new StringBuilder();
               // builder3.append("/sdcard/").append(info.get_imghead());
                File file3 = new File(info.get_imghead());
                if(!file3.exists())
                    h3._headimage.setImageResource(R.drawable.dft);
                else
                    h3._headimage.setImageURI(Uri.parse(info.get_imghead()));
                //h2._headimage.setImageResource(mtList.get(position).get_imghead());
                h3._id.setText(mtList.get(position).get_idnumber());
                h3._name.setText(mtList.get(position).get_name());
                h3._time.setText(mtList.get(position).get_wordTime());
                //h2._time.setText(String.valueOf(info.getType()));
                h3._content.setText(mtList.get(position).get_content());
                h3._cmt.setText(mtList.get(position).get_ctm());
                h3._lk.setText(mtList.get(position).get_lk());
                h3._tn.setText(mtList.get(position).get_tn());
                break;
            default:
                ViewHolder2 h1 = (ViewHolder2)holder;
                h1.View2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),UserArticle.class);
                        intent.putExtra("articleId",info.getArticleId());
                        v.getContext().startActivity(intent);
                    //    Toast.makeText(v.getContext(),String.valueOf(info.getArticleId()),Toast.LENGTH_SHORT).show();
                    }
                });
               // StringBuilder builder1= new StringBuilder();
               // builder1.append("/sdcard/").append(info.get_imghead());
                File file1 = new File(info.get_imghead());
                if(!file1.exists())
                    h1._headimage.setImageResource(R.drawable.dft);
                else
                    h1._headimage.setImageURI(Uri.parse(info.get_imghead()));
                //  h1._headimage.setImageResource(mtList.get(position).get_imghead());
                h1._id.setText(mtList.get(position).get_idnumber());
                h1._name.setText(mtList.get(position).get_name());
                h1._time.setText(mtList.get(position).get_wordTime());
                //h1._time.setText(String.valueOf(info.getType()));
                h1._content.setText(mtList.get(position).get_content());
                h1._cmt.setText(mtList.get(position).get_ctm());
                h1._lk.setText(mtList.get(position).get_lk());
                h1._tn.setText(mtList.get(position).get_tn());
                h1._name2.setText(mtList.get(position).get_name2());
                h1._content2.setText(mtList.get(position).get_content2());
                h1._id2.setText(mtList.get(position).get_idnumber2());
                h1._time2.setText(mtList.get(position).get_wordTime2());

                break;
        }
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        View View1;
        ImageView _headimage;
        TextView _id;
        TextView _time;
        TextView _content;
        TextView _name;
        Button _cmt;
        Button _tn;
        Button _lk;

        public ViewHolder(View view){
            super(view);
            View1 = view;
            _headimage = (ImageView) view.findViewById(R.id.headimage);
            _id = (TextView) view.findViewById(R.id.idnumber);
            _time = (TextView) view.findViewById(R.id.ttime);
            _content = (TextView) view.findViewById(R.id.content);
            _name = (TextView) view.findViewById(R.id.name);
            _cmt = (Button) view.findViewById(R.id.btn_cmt);
            _tn = (Button) view.findViewById(R.id.btn_zhuan);
            _lk = (Button) view.findViewById(R.id.btn_zan);
        }
    }
    class ViewHolder2 extends RecyclerView.ViewHolder{
        ImageView _headimage;
        TextView _id;
        TextView _time;
        TextView _content;
        TextView _name;
        Button _cmt;
        Button _tn;
        Button _lk;
        TextView _id2;
        TextView _time2;
        TextView _content2;
        TextView _name2;
        View View2;

        public ViewHolder2(View view){
            super(view);
            View2 = view;
            _headimage = (ImageView) view.findViewById(R.id.headimage2);
            _id = (TextView) view.findViewById(R.id.idnumber2);
            _time = (TextView) view.findViewById(R.id.ttime2);
            _content = (TextView) view.findViewById(R.id.content2);
            _name = (TextView) view.findViewById(R.id.name2);
            _cmt = (Button) view.findViewById(R.id.btn_cmt2);
            _tn = (Button) view.findViewById(R.id.btn_zhuan2);
            _lk = (Button) view.findViewById(R.id.btn_zan2);
            _id2 = (TextView) view.findViewById(R.id.idnumber1);
            _time2 = (TextView) view.findViewById(R.id.ttime1);
            _content2 = (TextView) view.findViewById(R.id.content1);
            _name2 = (TextView) view.findViewById(R.id.name1);
        }
    }
    class ViewHolder3 extends RecyclerView.ViewHolder{
        ImageView _headimage;
        TextView _id;
        TextView _time;
        TextView _content;
        TextView _name;
        Button _cmt;
        Button _tn;
        Button _lk;
        View View3;

        public ViewHolder3(View view){
            super(view);
            View3 = view;
            _headimage = (ImageView) view.findViewById(R.id.headimage3);
            _id = (TextView) view.findViewById(R.id.idnumber3);
            _time = (TextView) view.findViewById(R.id.ttime3);
            _content = (TextView) view.findViewById(R.id.content3);
            _name = (TextView) view.findViewById(R.id.name3);
            _cmt = (Button) view.findViewById(R.id.btn_cmt3);
            _tn = (Button) view.findViewById(R.id.btn_zhuan3);
            _lk = (Button) view.findViewById(R.id.btn_zan3);
        }
    }
    class ViewHolder4 extends RecyclerView.ViewHolder{
        View View4;
        TextView _id;
        TextView _time;
        TextView _content;
        ImageView _pic;
        TextView _name;
        public ViewHolder4(View view){
            super(view);
            View4 = view;
            _name = (TextView)view.findViewById(R.id.top_name);
            _id = (TextView) view.findViewById(R.id.top_id);
            _time = (TextView) view.findViewById(R.id.top_time);
            _content = (TextView) view.findViewById(R.id.top_content);
            _pic = (ImageView) view.findViewById(R.id.top_image);
        }
    }

}