package com.example.twitter;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class CmtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //origin
    public static final int TYPE_ORIGIN = 0;
    //原创 有图片
    public static final int TYPE_O_PIC = -4;
    //转发 但是被删除了
    public static final int TYPE_NON_DEL = -1;
    //大于0 的都是转发的 有具体内容的
    public static final int TYPE_NON= 1;
    //cmt
    public static final int TYPE_CMT = -2;

    private ArrayList<Info> mtopList;

    private LayoutInflater mLayoutInflater;

    private Context mContext;

    public CmtAdapter(ArrayList<Info> topList){
        mtopList = topList;
    }

    public CmtAdapter(ArrayList<Info> infos, Context mContext){
        this.mtopList = infos;
        this.mContext = mContext;
    }

    public int getItemViewType(int position) {
        if(mtopList.get(position).getType() == 0)
            return TYPE_ORIGIN;
        else if(mtopList.get(position).getType() == -1)
            return TYPE_NON_DEL;
        else if(mtopList.get(position).getType() == -2)
            return TYPE_CMT;
        else if (mtopList.get(position).getType() == -4)
            return TYPE_O_PIC;
        else
            return TYPE_NON;
    }

    @Override
    public int getItemCount() {
        return mtopList.size();
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
        //origin
        if(viewType == TYPE_ORIGIN){
            view = inflater.inflate(R.layout.user_original,parent,false);
            holder = new  ViewHolder(view);
        }
        else if(viewType == TYPE_NON_DEL){
            view = inflater.inflate(R.layout.user_non_original_del,parent,false);
            holder = new ViewHolder3(view);

        }
        else if(viewType == TYPE_CMT){
            view = inflater.inflate(R.layout.article_list_cmt,parent,false);
            holder = new ViewHolder4(view);
        }
        else if(viewType == TYPE_NON){
            view = inflater.inflate(R.layout.user_non_original,parent,false);
            holder = new ViewHolder2(view);
        }
        else if(viewType == TYPE_O_PIC){
            view = inflater.inflate(R.layout.user_original_pic,parent,false);
            holder = new ViewHolder5(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Info info = mtopList.get(position);
        switch (info.getType()){
            case TYPE_ORIGIN:
                ViewHolder h = (ViewHolder)holder;
               /* h.View1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),UserArticle.class);
                        intent.putExtra("articleId",info.getArticleId());
                        v.getContext().startActivity(intent);
                    }
                });*/
               // StringBuilder builder = new StringBuilder();
              //  builder.append("/sdcard/").append(info.get_imghead());
                File file = new File(info.get_imghead());
                if(!file.exists())
                    h._headimage.setImageResource(R.drawable.dft);
                else
                    h._headimage.setImageURI(Uri.parse(info.get_imghead()));
                //h._headimage.setImageResource(mtList.get(position).get_imghead());
                h._id.setText(mtopList.get(position).get_idnumber());
                h._name.setText(mtopList.get(position).get_name());
                h._time.setText(mtopList.get(position).get_wordTime());
                // h._time.setText(String.valueOf(info.getType()));
                h._content.setText(mtopList.get(position).get_content());
                h._cmt.setText(mtopList.get(position).get_ctm());
                h._lk.setText(mtopList.get(position).get_lk());
                h._tn.setText(mtopList.get(position).get_tn());
                break;
            case TYPE_O_PIC:
                ViewHolder5 h5 = (ViewHolder5)holder;
                //到具体页面
              /*  h5.View4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),UserArticle.class);
                        Toast.makeText(mContext, String.valueOf(info.getArticleId()),Toast.LENGTH_SHORT).show();
                        //intent.putExtra("articleId",String.valueOf(info.getArticleId()));
                        intent.putExtra("articleId",info.getArticleId());
                        v.getContext().startActivity(intent);
                    }
                });*/

                File file4 = new File(info.get_imghead());
                if(!file4.exists())
                    h5._headimage.setImageResource(R.drawable.dft);
                else
                    h5._headimage.setImageURI(Uri.parse(info.get_imghead()));

                h5._id.setText(mtopList.get(position).get_idnumber());
                h5._name.setText(mtopList.get(position).get_name());
                h5._time.setText(mtopList.get(position).get_wordTime());
                h5._content.setText(mtopList.get(position).get_content());
                h5._cmt.setText(mtopList.get(position).get_ctm());
                h5._lk.setText(mtopList.get(position).get_lk());
                h5._tn.setText(mtopList.get(position).get_tn());
                h5._pic.setImageURI(Uri.parse(mtopList.get(position).getPic()));
                break;
            case TYPE_NON_DEL:
                ViewHolder3 h3 = (ViewHolder3)holder;
                //StringBuilder builder3 = new StringBuilder();
               // builder3.append("/sdcard/").append(info.get_imghead());
                File file3 = new File(info.get_imghead());
                if(!file3.exists())
                    h3._headimage.setImageResource(R.drawable.dft);
                else
                    h3._headimage.setImageURI(Uri.parse(info.get_imghead()));
                //h2._headimage.setImageResource(mtList.get(position).get_imghead());
                h3._id.setText(mtopList.get(position).get_idnumber());
                h3._name.setText(mtopList.get(position).get_name());
                h3._time.setText(mtopList.get(position).get_wordTime());
                //h2._time.setText(String.valueOf(info.getType()));
                h3._content.setText(mtopList.get(position).get_content());
                h3._cmt.setText(mtopList.get(position).get_ctm());
                h3._lk.setText(mtopList.get(position).get_lk());
                h3._tn.setText(mtopList.get(position).get_tn());
                break;
            case TYPE_CMT:
                ViewHolder4 h1 = (ViewHolder4)holder;
              //  StringBuilder builder1= new StringBuilder();
             //   builder1.append("/sdcard/").append(info.get_imghead());
                File file1 = new File(info.get_imghead());
                if(!file1.exists())
                    h1._headimage.setImageResource(R.drawable.dft);
                else
                    h1._headimage.setImageURI(Uri.parse(info.get_imghead()));
                //  h1._headimage.setImageResource(mtList.get(position).get_imghead());
                h1._id.setText(mtopList.get(position).get_idnumber());
                h1._name.setText(mtopList.get(position).get_name());
                h1._time.setText(mtopList.get(position).get_wordTime());
                //h1._time.setText(String.valueOf(info.getType()));
                h1._content.setText(mtopList.get(position).get_content());
                break;
            default://转发 有内容的
                ViewHolder2 h2 = (ViewHolder2)holder;
              //  StringBuilder builder2= new StringBuilder();
              //  builder2.append("/sdcard/").append(info.get_imghead());
                /*h2.view2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/
                File file2 = new File(info.get_imghead());
                if(!file2.exists())
                    h2._headimage.setImageResource(R.drawable.dft);
                else
                    h2._headimage.setImageURI(Uri.parse(info.get_imghead()));
                //  h1._headimage.setImageResource(mtList.get(position).get_imghead());
                h2._id.setText(mtopList.get(position).get_idnumber());
                h2._name.setText(mtopList.get(position).get_name());
                h2._time.setText(mtopList.get(position).get_wordTime());
                //h1._time.setText(String.valueOf(info.getType()));
                h2._content.setText(mtopList.get(position).get_content());
                h2._cmt.setText(mtopList.get(position).get_ctm());
                h2._lk.setText(mtopList.get(position).get_lk());
                h2._tn.setText(mtopList.get(position).get_tn());
                h2._name2.setText(mtopList.get(position).get_name2());
                h2._content2.setText(mtopList.get(position).get_content2());
                h2._id2.setText(mtopList.get(position).get_idnumber2());
                h2._time2.setText(mtopList.get(position).get_wordTime2());
                h2._content2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),UserArticle.class);
                        //Toast.makeText(mContext, String.valueOf(info.getType()),Toast.LENGTH_SHORT).show();
                        //intent.putExtra("articleId",String.valueOf(info.getArticleId()));
                        intent.putExtra("articleId",info.getType());
                        v.getContext().startActivity(intent);
                    }
                });
                break;
        }
    }
    //原创
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
    //转发
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
        View view2;
        public ViewHolder2(View view){
            super(view);
            view2 = view;
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
    //转发 删除内容
    class ViewHolder3 extends RecyclerView.ViewHolder{
        ImageView _headimage;
        TextView _id;
        TextView _time;
        TextView _content;
        TextView _name;
        Button _cmt;
        Button _tn;
        Button _lk;

        public ViewHolder3(View view){
            super(view);
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
    //评论
    class ViewHolder4 extends RecyclerView.ViewHolder{
        ImageView _headimage;
        TextView _id;
        TextView _time;
        TextView _content;
        TextView _name;

        public ViewHolder4(View view){
            super(view);

            _headimage = (ImageView) view.findViewById(R.id.headimage_cmt);
            _id = (TextView) view.findViewById(R.id.idnumber_cmt);
            _time = (TextView) view.findViewById(R.id.ttime_cmt);
            _content = (TextView) view.findViewById(R.id.content_cmt);
            _name = (TextView) view.findViewById(R.id.name_cmt);
        }
    }

    class ViewHolder5 extends RecyclerView.ViewHolder{
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

        public ViewHolder5(View view){
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
