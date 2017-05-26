package com.jiyun.kaiyuanzhongguo.adapter;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.jiyun.kaiyuanzhongguo.App;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.bean.Tweet;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.jiyun.kaiyuanzhongguo.utils.GlideCircleTransform;
import com.jiyun.kaiyuanzhongguo.view.DetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/4/12.
 */

public class MoveAdapter extends BaseAdapter<Tweet.TweetBean> {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int uid = 0;
    private int id=0;
    private int idOne=0;
    private int i=0;
    private int b=0;
    private CheckBox radioButton;
    private Map<Integer,Boolean> map;
    private int a=0;
    public MoveAdapter(Context context, int layoutId, List<Tweet.TweetBean> datas) {
        super(context, layoutId, datas);
    }
    public void init(List<Tweet.TweetBean> listTweet){
        sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String name=sharedPreferences.getString("name","");
        map=new HashMap<>();
        for(int a=0;a<listTweet.size();a++) {
            List<Tweet.TweetBean.User> list = listTweet.get(a).getLikeList();
            if (list == null) {
                map.put(a, false);
            } else {
                map.put(a, false);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getName().equals(name)) {
                        map.put(a, true);
                    }
                }
            }
        }
    }

    @Override
    public void convert(final ViewHolder holder, final Tweet.TweetBean newsBean) {
        holder.getAdapterPosition();
        final String cookie=sharedPreferences.getString("cookie","");
        holder.getView(R.id.move_linear).setOnCreateContextMenuListener(App.activity);
        holder.getAdapterPosition();
        radioButton=holder.getView(R.id.Fabulous);
        if(map.get(holder.getAdapterPosition()-1)==true) {
            radioButton.setChecked(true);
        }else{
            radioButton.setChecked(false);
        }
        holder.setText(R.id.move_title,newsBean.getAuthor());
        holder.setText(R.id.move_body,newsBean.getBody());
        holder.setText(R.id.fabulous_count, newsBean.getLikeCount());
        final String time=newsBean.getPubDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d=null;
        try {
            d = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long pub=d.getTime();
        Long One=System.currentTimeMillis();
        long timer=(One-pub)/1000/60/60/24;
        if(timer>0) {
            holder.setText(R.id.move_author,  timer + "天前");
        }else{
            long timeOne=(One-pub)/1000/60/60;
            if(timeOne>=1) {
                holder.setText(R.id.move_author,  timeOne + "小时前");
            }else{
                long timeTwo=(One-pub)/1000/60;
                holder.setText(R.id.move_author,  timeTwo + "分钟前");
            }
        }
        ImageView imageView=holder.getView(R.id.move_image);
        if(newsBean.getPortrait()==""){
            Glide.with(context).load(R.mipmap.widget_dface).transform(new GlideCircleTransform(context)).into(imageView);
        }else {
            Glide.with(context).load(newsBean.getPortrait()).transform(new GlideCircleTransform(context)).into(imageView);
        }
        ImageView imageView1=holder.getView(R.id.move_Images);
        if(newsBean.getImgBig()!=""){
            imageView1.setVisibility(View.VISIBLE);
            Glide.with(context).load(newsBean.getImgBig()).into(imageView1);
        }else{
            imageView1.setVisibility(View.GONE);
        }
        holder.setOnclickListener(R.id.move_linear, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("name",newsBean.getAuthor());
                intent.putExtra("body",newsBean.getBody());
                intent.putExtra("likecount",newsBean.getLikeCount());
                intent.putExtra("time",newsBean.getPubDate());
                intent.putExtra("image",newsBean.getPortrait());
                intent.putExtra("id",newsBean.getId());
                Bundle bundle=new Bundle();
                bundle.putSerializable("tweet",newsBean);
                intent.putExtra("bundle",bundle);
                context.startActivity(intent);
            }
        });
        LinearLayout layout=holder.getView(R.id.move_linear);
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dialog(newsBean);
                return true;
            }
        });

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = sharedPreferences.getInt("uid", 0);
                id= Integer.parseInt(newsBean.getId());
                idOne= Integer.parseInt(newsBean.getAuthorid());
                map.put(holder.getAdapterPosition()-1,!map.get(holder.getAdapterPosition()-1));
                b= Integer.parseInt(newsBean.getLikeCount());
                Log.e("aa",map.get(holder.getAdapterPosition()-1)+"");
                if(map.get(holder.getAdapterPosition()-1)){
                    getNews(cookie);
                    holder.setText(R.id.fabulous_count,b+1+"");
                }else{
                    getunNews(cookie);
                    holder.setText(R.id.fabulous_count,b+"");
                }
            }
        });
    }
    private void getNews(String cookie){
        NewsModel model=new NewsModel();
        model.getLike(cookie,uid, id, idOne, new MyCallback() {
            @Override
            public void onSuccess(String jsondate) {
                Log.e("a",jsondate);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
    private void getunNews(String cookie){
        NewsModel model=new NewsModel();
        model.getUnLike(cookie,uid, id, idOne, new MyCallback() {
            @Override
            public void onSuccess(String jsondate) {
                Log.e("a",jsondate);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
    private void dialog(final Tweet.TweetBean newsbean){
        final AlertDialog myDialog =new AlertDialog.Builder(context).create();
        myDialog.show();
        myDialog.getWindow().setContentView(R.layout.dialog_moban);
        myDialog.getWindow().findViewById(R.id.dialog_textOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(newsbean.getBody());
                Toast.makeText(context,"复制成功",Toast.LENGTH_SHORT).show();
            }
        });
        myDialog.getWindow().findViewById(R.id.dialog_textTwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        myDialog.getWindow().findViewById(R.id.dialog_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }
}
