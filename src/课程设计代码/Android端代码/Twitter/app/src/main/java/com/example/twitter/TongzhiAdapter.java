package com.example.twitter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by hasee on 2018/12/14.
 */

public class TongzhiAdapter extends RecyclerView.Adapter<TongzhiAdapter.myViewHodler> {
    private Context context;
    private ArrayList<Info> mTongzhi;

    //创建构造函数
    public TongzhiAdapter(Context context, ArrayList<Info> mTongzhi) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.mTongzhi = mTongzhi;//实体类数据ArrayList
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public TongzhiAdapter.myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.in_tongzhi, null);
        return new TongzhiAdapter.myViewHodler(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(TongzhiAdapter.myViewHodler holder, int position) {
        //根据点击位置绑定数据
        final Info data = mTongzhi.get(position);
//        holder.mItemGoodsImg;
       // StringBuilder builder = new StringBuilder();
        //builder.append("/sdcard/").append(data._imghead);
        holder.mheadimagel.setImageURI(Uri.parse(data.get_imghead()));
        holder.mnamel.setText(data._name);//获取实体类中的name字段并设置
        holder.mcontentl.setText(data._content);//获取实体类中的content字段并设置
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),UserArticle.class);
               //Toast.makeText(v.getContext(),"发送"+String.valueOf(data.getArticleId()),Toast.LENGTH_SHORT).show();
                intent.putExtra("articleId",data.getArticleId());
                v.getContext().startActivity(intent);
            }
        });

    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mTongzhi.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private ImageView mheadimagel;
        private TextView mnamel;
        private TextView mcontentl;
        View view;
        public myViewHodler(final View itemView) {
            super(itemView);
            view = itemView;
            mheadimagel = (ImageView) itemView.findViewById(R.id.headimage1);
            mnamel = (TextView) itemView.findViewById(R.id.name1);
            mcontentl = (TextView) itemView.findViewById(R.id.content1);


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
    private TongzhiAdapter.OnITemClickListener onITemClickListener;

    public void setOnITemClickListener(TongzhiAdapter.OnITemClickListener onITemClickListener) {
        this.onITemClickListener = onITemClickListener;


    }
}
