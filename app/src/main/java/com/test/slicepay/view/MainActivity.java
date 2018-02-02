package com.test.slicepay.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.slicepay.R;
import com.test.slicepay.controller.ImageListAdapter;
import com.test.slicepay.model.ImageMainObject;
import com.test.slicepay.model.Photo;
import com.test.slicepay.utils.APIHandler;
import com.test.slicepay.utils.GridSpacingItemDecoration;
import com.test.slicepay.utils.RecyclerViewPositionHelper;
import com.test.slicepay.utils.RestApi;
import com.test.slicepay.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerViewImageList;
    ArrayList<Photo> arrayListImage = new ArrayList<>();
    ImageListAdapter adapter;
    Activity mActivity;

    ProgressDialog progressDialog;
    int pageNo = 1;
    int lastVisiblesItems, visibleItemCount, totalItemCount;
    Gson mGson;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerViewPositionHelper mRecyclerViewHelper;

    boolean isLoading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        mGson = new GsonBuilder().create();

        setUI();
    }

    private void setUI(){

        recyclerViewImageList = (RecyclerView) findViewById(R.id.recycler_image_list);


        arrayListImage = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewImageList.setLayoutManager(mLayoutManager);
        recyclerViewImageList.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(10, this), true));
        recyclerViewImageList.setItemAnimator(new DefaultItemAnimator());
        //recyclerViewImageList.setAdapter(adapter);

        getImageList();


        recyclerViewImageList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = arrayListImage.size()-1;
                    lastVisiblesItems = mRecyclerViewHelper.findLastVisibleItemPosition();
                    System.out.println("last "+lastVisiblesItems + "total "+totalItemCount);
                    if(lastVisiblesItems==(totalItemCount)) {
                        if (isLoading == false) {
                            isLoading = true;
                            getImageList();
                        }
                    }
                }

            }
        });
    }

    private void getImageList()
    {
        isLoading = true;
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        if(!progressDialog.isShowing()) {
            progressDialog.show();
        }


        try {


            new APIHandler().getsharedInstance(mActivity).execute(Request.Method.GET, RestApi.getImageListUrl(pageNo), null, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {

                    try {

                        ImageMainObject favItem = mGson.fromJson(response.toString(), ImageMainObject.class);
                        pageNo = favItem.getPhotos().getPage() + 1;

                        if(adapter == null){
                            adapter = new ImageListAdapter(mActivity, favItem.getPhotos().getPhoto());
                            recyclerViewImageList.setAdapter(adapter);
                        }else{
                            adapter.updateList(favItem.getPhotos().getPhoto());
                            adapter.notifyDataSetChanged();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Json Exception " + e.getMessage());
                    }
                    finally {
                        isLoading = false;
                        progressDialog.dismiss();
                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }


            }, null);

        }

        catch (Exception e)
        {

        }
        finally {


        }
    }
}
