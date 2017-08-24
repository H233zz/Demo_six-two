package com.example.dell.wangyouwei1506b20170728;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.wangzhenyu1506b20170728.Data;
import com.example.dell.wangzhenyu1506b20170728.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * 姓名：王有为
 * 时间：2017/7/28.
 * 适配器
 */

public class MyAdapter extends BaseAdapter {
    private List<Data.ListBean> list;
    private Context context;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    private static final int TWO=0;
    private static final int FOUR=1;
    public MyAdapter(List<Data.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                        .createDefault(context);
                //将configuration配置到imageloader中
                imageLoader= ImageLoader.getInstance();
                imageLoader.init(configuration);
                options=new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        .showImageOnLoading(R.mipmap.ic_launcher)
                        .showImageForEmptyUri(R.mipmap.ic_launcher)
                        .showImageOnFail(R.mipmap.ic_launcher)
                        .build();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Data.ListBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //多条目
        TwoHolder twoHolder = null;
        FourHolder fourHolder = null;
        int type = getItemViewType(position);
        if (convertView==null){
            if (type==TWO){
                twoHolder = new TwoHolder();
                convertView=View.inflate(context,R.layout.twoimage,null);
                twoHolder.textView=(TextView) convertView.findViewById(R.id.twoimagetext);
                twoHolder.imageView1=(ImageView)convertView.findViewById(R.id.twoiamge1);
                twoHolder.imageView2=(ImageView)convertView.findViewById(R.id.twoiamge2);
                convertView.setTag(twoHolder);
            }else if(type==FOUR){
                fourHolder = new FourHolder();
                convertView=View.inflate(context,R.layout.fourimage,null);
                fourHolder.textView=(TextView)convertView.findViewById(R.id.fourimagetext);
                fourHolder.imageView1=(ImageView)convertView.findViewById(R.id.fourimage1);
                fourHolder.imageView2=(ImageView)convertView.findViewById(R.id.fourimage2);
                fourHolder.imageView3=(ImageView)convertView.findViewById(R.id.fourimage3);
                fourHolder.imageView4=(ImageView)convertView.findViewById(R.id.fourimage4);
                convertView.setTag(fourHolder);
            }
        }else{
            if (type==TWO){
                twoHolder=(TwoHolder) convertView.getTag();
            }else if(type==FOUR){
                fourHolder=(FourHolder)convertView.getTag();
            }
        }
        Data.ListBean bean = list.get(position);
        if (type==TWO){
            String s = bean.getPic();
            String[] all = s.split("[|]");
            twoHolder.textView.setText(bean.getTitle());
            imageLoader.displayImage(all[0],twoHolder.imageView1,options);
            imageLoader.displayImage(all[1],twoHolder.imageView2,options);
        }else if(type==FOUR){
            String s = bean.getPic();
            String[] all = s.split("[|]");
            fourHolder.textView.setText(bean.getTitle());
            imageLoader.displayImage(all[0],fourHolder.imageView1,options);
            imageLoader.displayImage(all[1],fourHolder.imageView2,options);
            imageLoader.displayImage(all[2],fourHolder.imageView3,options);
            imageLoader.displayImage(all[3],fourHolder.imageView4,options);
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        int i = list.get(position).getType();
        if (i==2){
            return TWO;
        }else{
            return FOUR;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    class TwoHolder{
        TextView textView;
        ImageView imageView1,imageView2;
    }
    class FourHolder{
        TextView textView;
        ImageView imageView1,imageView2,imageView3,imageView4;
    }
}
