package com.example.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/12/18.
 */

public class OtherAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //不转发
    public static final int TYPE_1 = 0;
    //有图片的原创文
    public static final int TYPE_4 = -4;
    //转发
    public static final int TYPE_2 = 1;
    //转发的原文被删除
    public static final int TYPE_3 = -1;

    //保存当前用户的信息
    public static final int TYPE_USER = -3;
    Info info_user = new Info();

    private String cmtTime;
    private String cmtContent;

    private String rTime;
    private String rContent;

    private String articleId;

    /*public static final String cmt = "http://169.254.118.48:8080" + "/Twitter/addCmt.jsp";
    public static final String like = "http://169.254.118.48:8080" + "/Twitter/doLike.jsp";
    public static final String repost = "http://169.254.118.48:8080" + "/Twitter/repost.jsp";
*/
    public static final String cmt = "http://192.168.43.228:8080" + "/Twitter/addCmt.jsp";
    public static final String like = "http://192.168.43.228:8080" + "/Twitter/doLike.jsp";
    public static final String repost = "http://192.168.43.228:8080" + "/Twitter/repost.jsp";

    private List<Info> mtList;

    private LayoutInflater mLayoutInflater;

    public OtherAllAdapter(List<Info> m){
        this.mtList = m;
    }
    private Context mContext;
    private Activity activity;
    public OtherAllAdapter(List<Info> info, Context mContext){
        this.mtList = info;
        this.mContext = mContext;
        this.activity = (Other_Homepage)mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if(mtList.get(position).getType() == 0)
            return TYPE_1;
        else if(mtList.get(position).getType() == -1)
            return TYPE_3;
        else if(mtList.get(position).getType() == -3)
            return TYPE_USER;
        else if (mtList.get(position).getType() == -4)
            return TYPE_4;
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
        View view = null;

        if(viewType == TYPE_1 ){
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
        else if (viewType == TYPE_4){
            view = inflater.inflate(R.layout.user_original_pic,parent,false);
            holder = new ViewHolder4(view);
        }
        else if(viewType == TYPE_USER){
           // view = inflater.inflate(R.layout.user_original,parent,false);
            view = inflater.inflate(R.layout.loading,parent,false);

            holder = new nViewHolder(view);
        }
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Info info = mtList.get(position);
        switch (info.getType()){
            case TYPE_USER:
               /* ViewHolder h0 = (ViewHolder)holder;
                h0._content.setText(info.get_idnumber());
                h0.View1.setVisibility(View.GONE);
                h0.View1.setVisibility(View.INVISIBLE);*/
                nViewHolder nViewHolder = (nViewHolder)holder;
               // nViewHolder.load.setText(info.get_idnumber());
                info_user.set_idnumber(info.get_idnumber());
                info_user.set_name(info.get_name());
                info_user.set_imghead(info.get_imghead());
                info_user.setFan(info.getFan());
                info_user.setFollow(info.getFollow());
               // Toast.makeText(mContext,info.get_idnumber(),Toast.LENGTH_SHORT).show();
                break;
            case TYPE_1:
                ViewHolder h = (ViewHolder)holder;

                //到具体页面
                h.View1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),UserArticle.class);
                        //Toast.makeText(mContext, String.valueOf(info.getArticleId()),Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(intent);
                    }
                });
                //评论
                h._cmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleId = String.valueOf(info.getArticleId());
                        cmtDialog();
                    }
                });
                //点赞
                h._lk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleId = String.valueOf(info.getArticleId());
                        like();
                    }
                });
                //转发
                h._tn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleId = String.valueOf(info.getArticleId());
                        repostDialog();
                    }
                });
                //StringBuilder builder = new StringBuilder();
                //builder.append("/sdcard/").append(info.get_imghead());
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
            case TYPE_4:
                ViewHolder4 h4 = (ViewHolder4)holder;

                //到具体页面
                h4.View4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),UserArticle.class);
                     //   Toast.makeText(mContext, String.valueOf(info.getArticleId()),Toast.LENGTH_SHORT).show();
                        //intent.putExtra("articleId",String.valueOf(info.getArticleId()));
                        intent.putExtra("articleId",info.getArticleId());
                        v.getContext().startActivity(intent);
                    }
                });
                //评论
                h4._cmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleId = String.valueOf(info.getArticleId());
                        cmtDialog();
                    }
                });
                //点赞
                h4._lk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleId = String.valueOf(info.getArticleId());
                        like();
                    }
                });
                //转发
                h4._tn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleId = String.valueOf(info.getArticleId());
                        repostDialog();
                    }
                });
                //StringBuilder builder4 = new StringBuilder();
                // builder4.append("/sdcard/").append(info.get_imghead());
                File file4 = new File(info.get_imghead());
                if(!file4.exists())
                    h4._headimage.setImageResource(R.drawable.dft);
                else
                    h4._headimage.setImageURI(Uri.parse(info.get_imghead()));
                //h._headimage.setImageResource(mtList.get(position).get_imghead());
                h4._id.setText(mtList.get(position).get_idnumber());
                h4._name.setText(mtList.get(position).get_name());
                h4._time.setText(mtList.get(position).get_wordTime());
                // h._time.setText(String.valueOf(info.getType()));
                h4._content.setText(mtList.get(position).get_content());
                h4._cmt.setText(mtList.get(position).get_ctm());
                h4._lk.setText(mtList.get(position).get_lk());
                h4._tn.setText(mtList.get(position).get_tn());
                h4._pic.setImageURI(Uri.parse(mtList.get(position).getPic()));
                break;
            case TYPE_3:
                ViewHolder3 h3 = (ViewHolder3)holder;
                h3.View3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),UserArticle.class);
                       // Toast.makeText(mContext, String.valueOf(info.getArticleId()),Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(intent);
                    }
                });
                h3._cmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleId = String.valueOf(info.getArticleId());
                        cmtDialog();
                    }
                });
                h3._lk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleId = String.valueOf(info.getArticleId());
                        like();
                    }
                });
                h3._tn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleId = String.valueOf(info.getArticleId());
                        repostDialog();
                    }
                });
                //StringBuilder builder3 = new StringBuilder();
                //builder3.append("/sdcard/").append(info.get_imghead());
                File file3 = new File(info.get_imghead());
                if(!file3.exists())
                    h3._headimage.setImageResource(R.drawable.star);
                else
                    h3._headimage.setImageURI(Uri.parse(info.get_imghead()));
                h3._id.setText(mtList.get(position).get_idnumber());
                h3._name.setText(mtList.get(position).get_name());
                h3._time.setText(mtList.get(position).get_wordTime());
                h3._content.setText(mtList.get(position).get_content());
                h3._cmt.setText(mtList.get(position).get_ctm());
                h3._lk.setText(mtList.get(position).get_lk());
                h3._tn.setText(mtList.get(position).get_tn());
                break;
            default:
                ViewHolder2 h1 = (ViewHolder2)holder;
                h1.Viewe2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),UserArticle.class);
                       // Toast.makeText(mContext, String.valueOf(info.getArticleId()),Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(intent);
                    }
                });
                h1._cmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleId = String.valueOf(info.getArticleId());
                        cmtDialog();
                    }
                });
                h1._lk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleId = String.valueOf(info.getArticleId());
                        like();
                    }
                });
                h1._tn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleId = String.valueOf(info.getArticleId());
                        repostDialog();
                    }
                });
               // StringBuilder builder1= new StringBuilder();
                //builder1.append("/sdcard/").append(info.get_imghead());
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

    private void cmtDialog() {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(mContext);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(mContext);
        inputDialog.setTitle("评论一下：").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cmtContent = editText.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        cmtTime = format.format(date);
                        addCmt();
                    }
                }).show();
    }
    //评论推文 本人的信息从哪里得到？？？
    private void addCmt(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();

                    RequestBody requestBody = new FormBody.Builder()
                            .add("_idnumber",info_user.get_idnumber())
                            .add("cmtContent",cmtContent)
                            .add("cmtTime",cmtTime)
                            .add("articleId",articleId)
                            .build();
                    Request request = new Request.Builder()
                            .url(cmt)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    final String data = response.body().string();
                    //1 成功 0 不成功 -1 原文被删除
                    if(data.trim().equals("1")){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext,"评论成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(activity,Homepage.class);
                                intent.putExtra("_idnumber",info_user.get_idnumber());
                                intent.putExtra("_imghead",info_user.get_imghead());
                                intent.putExtra("_name",info_user.get_name());
                                intent.putExtra("fan",info_user.getFan());
                                intent.putExtra("follow",info_user.getFollow());
                                activity.startActivity(intent);
                            }
                        });

                    }
                    else if(data.trim().equals("-1")){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(mContext,info_user.get_idnumber()+cmtContent+cmtTime,Toast.LENGTH_SHORT).show();
                                Toast.makeText(mContext,"原文已被删除",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else if(data.trim().equals("0")){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext,"评论失败",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //转发推文
    private void repostDialog() {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(mContext);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(mContext);
        inputDialog.setTitle("转发：").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rContent = editText.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        rTime = format.format(date);
                        repost();
                    }
                }).show();
    }
    //转发
    private void repost(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();

                    RequestBody requestBody = new FormBody.Builder()
                            .add("_idnumber",info_user.get_idnumber())
                            .add("rContent",rContent)
                            .add("rTime",rTime)
                            .add("articleId",articleId)
                            .build();
                    Request request = new Request.Builder()
                            .url(repost)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    final String data = response.body().string();
                    //1 成功 0 不成功 -1 原文被删除
                    if(data.trim().equals("1")){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext,"已转发",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(activity,Homepage.class);
                                intent.putExtra("_idnumber",info_user.get_idnumber());
                                intent.putExtra("_imghead",info_user.get_imghead());
                                intent.putExtra("_name",info_user.get_name());
                                intent.putExtra("fan",info_user.getFan());
                                intent.putExtra("follow",info_user.getFollow());
                                activity.startActivity(intent);
                            }
                        });

                    }
                    else if(data.trim().equals("-1")){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                              //  Toast.makeText(mContext,info_user.get_idnumber()+cmtContent+cmtTime,Toast.LENGTH_SHORT).show();
                                Toast.makeText(mContext,"原文已被删除",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if(data.trim().equals("0")){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext,"转发失败",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //转发推文

    //点赞
    private void like(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();

                    RequestBody requestBody = new FormBody.Builder()
                            .add("_idnumber",info_user.get_idnumber())
                            .add("articleId",articleId)
                            .build();
                    Request request = new Request.Builder()
                            .url(like)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    final String data = response.body().string();
                    //1 成功 0 不成功 -1 原文被删除
                    if(data.trim().equals("1")){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                             //   Toast.makeText(mContext,"操作成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(activity,Homepage.class);
                                intent.putExtra("_idnumber",info_user.get_idnumber());
                                intent.putExtra("_imghead",info_user.get_imghead());
                                intent.putExtra("_name",info_user.get_name());
                                intent.putExtra("fan",info_user.getFan());
                                intent.putExtra("follow",info_user.getFollow());
                                activity.startActivity(intent);
                            }
                        });

                    }
                    else if(data.trim().equals("-1")){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext,"原文已被删除",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if(data.trim().equals("0")){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext,"出错",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    class nViewHolder extends RecyclerView.ViewHolder{
        TextView load;
        public nViewHolder(View view){
            super(view);
            load = (TextView)view.findViewById(R.id.load);
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
        View Viewe2;
        public ViewHolder2(View view){
            super(view);
            Viewe2 = view;
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
        View  View3;
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
        ImageView _headimage;
        TextView _id;
        TextView _time;
        TextView _content;
        TextView _name;
        Button _cmt;
        Button _tn;
        Button _lk;
        ImageView _pic;

        public ViewHolder4(View view){
            super(view);
            View4 = view;
            _headimage = (ImageView) view.findViewById(R.id.headimage4);
            _id = (TextView) view.findViewById(R.id.idnumber4);
            _time = (TextView) view.findViewById(R.id.ttime4);
            _content = (TextView) view.findViewById(R.id.content4);
            _name = (TextView) view.findViewById(R.id.name4);
            _cmt = (Button) view.findViewById(R.id.btn_cmt4);
            _tn = (Button) view.findViewById(R.id.btn_zhuan4);
            _lk = (Button) view.findViewById(R.id.btn_zan4);
            _pic = (ImageView) view.findViewById(R.id.origin_pic4);
        }
    }

}