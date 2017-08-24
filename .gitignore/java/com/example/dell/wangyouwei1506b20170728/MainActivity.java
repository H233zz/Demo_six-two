package com.example.dell.wangyouwei1506b20170728;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.dell.wangzhenyu1506b20170728.R;
import com.example.dell.wangzhenyu1506b20170728.Data;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.utils.L;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
/**
 * 主方法，进行具体操作
 *
 */

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener{
private String URL="http://qhb.2dyt.com/Bwei/news?type=5&postkey=ff1d1AK&page=1";
    private XListView xListView;
    private List<Data.ListBean> list;
    private List<String> listimage;
    private List<String> listbanner;
    private Banner banner;

    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xListView=(XListView)findViewById(R.id.listview);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
        view = View.inflate(MainActivity.this,R.layout.ban,null);
        banner=(Banner)view.findViewById(R.id.bannsers);
        list=new ArrayList<>();
        listimage = new ArrayList<>();
        listbanner=new ArrayList<>();
        //判断是否有网
        if (isNet()){
            listviewcliner();
            loadTask(URL);
            xListView.addHeaderView(view);
            MyAdapter adapter = new MyAdapter(list,MainActivity.this);
            xListView.setAdapter(adapter);
        }else{
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setMessage("是否进行网络设置")
                    .setNegativeButton("取消",null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //跳转到网络设置
                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .create();
            dialog.show();

        }


    }
    private void listviewcliner(){
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetTask getTask = new GetTask();
                getTask.execute(URL);
                boolean b = getTask.cancel(true);
                if (b){
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setMessage("应用无响应")

                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .create();
                    dialog.show();
                }else{
                    Toast.makeText(MainActivity.this,"无",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public boolean isNet(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info!=null&&info.isConnected();
    }
    private void loadTask(String Url) {
        GetTask getTask = new GetTask();
        getTask.execute(Url);
    }

    /**
     *
     * */
    @Override
    public void onRefresh() {
        list.clear();
        listbanner.clear();
        listimage.clear();
        loadTask(URL);
    }
    /**
     *
     * */
    @Override
    public void onLoadMore() {

    }

    class GetTask extends AsyncTask<String ,Integer,Data>{

        @Override
        protected Data doInBackground(String... params) {
            return loadData(params[0]);
        }
        public Data loadData(String url){
            String result = Utils.loadConnection(url);
            Gson gson = new Gson();
            Data data = gson.fromJson(result, Data.class);
            return  data;
        }

        @Override
        protected void onPostExecute(Data data) {
            super.onPostExecute(data);
            list.addAll(data.getList());
            listimage.addAll(data.getListViewPager());
            listbanner.add(listimage.get(0));
            listbanner.add(listimage.get(1));
            listbanner.add(listimage.get(2));
            loadBanner();

            xListView.stopLoadMore();
            xListView.stopRefresh();
        }
        private void loadBanner(){
            banner.setImageLoader(new ImageActivity(MainActivity.this));
            banner.setImages(listbanner);
            banner.setDelayTime(1000);
            banner.start();
        }

    }


}
