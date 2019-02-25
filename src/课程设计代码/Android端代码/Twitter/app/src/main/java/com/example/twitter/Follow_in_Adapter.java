package com.example.twitter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by hasee on 2018/12/21.
 */

public class Follow_in_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Info> mfollowin;
    private static final int TYPE_FOLLOW_FAN = 1;// -1 follow  -2 fan
    private static final int TYPE_USER = 0;

    private Info info_user = new Info();
    //创建构造函数
    public Follow_in_Adapter(Context context, ArrayList<Info> mfollowin) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.mfollowin = mfollowin;//实体类数据ArrayList
    }

    @Override
    public int getItemViewType(int position) {
        if(mfollowin.get(position).getType() == 0)
            return TYPE_USER ;
        else
            return TYPE_FOLLOW_FAN;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder=null;
        View view = null;

        if(viewType == TYPE_FOLLOW_FAN ){
             view = View.inflate(context, R.layout.in_followin, null);
            holder = new myViewHolder(view);
        }
        else if(viewType == TYPE_USER ){
            view = inflater.inflate(R.layout.loading,parent,false);

            holder = new ViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //根据点击位置绑定数据
        final Info data = mfollowin.get(position);
        switch (data.getType()){
            case TYPE_USER:
                ViewHolder viewHolder = (ViewHolder)holder;
                // nViewHolder.load.setText(info.get_idnumber());
                info_user.set_idnumber(data.get_idnumber());
                info_user.set_name(data.get_name());
                info_user.set_imghead(data.get_imghead());
                info_user.setFan(data.getFan());
                info_user.setFollow(data.getFollow());
                break;
            default:
                myViewHolder holder1 = (myViewHolder)holder;
                //StringBuilder builder = new StringBuilder();
               // builder.append("/sdcard/").append(data._imghead);
                File file = new File(data.get_imghead());
                if(file.exists())
                    holder1.mheadimagef.setImageURI(Uri.parse(data.get_imghead()));
                else
                    holder1.mheadimagef.setImageResource(R.drawable.dft);
                holder1.mnamef.setText(data._name);//获取实体类中的name字段并设置
                holder1.midnumberf.setText(data._idnumber);//获取实体类中的content字段并设置

                holder1.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   Toast.makeText(context, "点击", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(),Other_Homepage.class);
                        //intent.putExtra("hostId", _idnumber);
                        intent.putExtra("userId",data.get_idnumber());
                        intent.putExtra("_imghead",data.get_imghead());
                        intent.putExtra("_name",data.get_name());
                        intent.putExtra("fan",data.getFan());
                        intent.putExtra("follow",data.getFollow());
                        intent.putExtra("followId",info_user.get_idnumber());
                        intent.putExtra("type",data.getType());
                        v.getContext().startActivity(intent);
                    }
                });
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mfollowin.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView load;
        public ViewHolder(View view){
            super(view);
            load = (TextView)view.findViewById(R.id.load);
        }
    }
    class myViewHolder extends RecyclerView.ViewHolder {
        View view;
        private ImageView mheadimagef;
        private TextView mnamef;
        private TextView midnumberf;

        public myViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            mheadimagef = (ImageView) itemView.findViewById(R.id.headimagef);
            mnamef = (TextView) itemView.findViewById(R.id.namef);
            midnumberf = (TextView) itemView.findViewById(R.id.idnumberf);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
//                    Intent intent=new Intent(v.getContext(),UserArticle.class);
//                    v.getContext().startActivity(intent);
                }
            });

        }
    }
    /**
     * 设置item的监听事件的接口
     */
    public interface OnITemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        public void OnItemClick(View view, Info data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnITemClickListener onITemClickListener;

    public void setOnITemClickListener(OnITemClickListener onITemClickListener) {
        this.onITemClickListener = onITemClickListener;


    }
}
