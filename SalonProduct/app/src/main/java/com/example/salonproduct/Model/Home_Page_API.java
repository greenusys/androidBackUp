package com.example.salonproduct.Model;



import androidx.appcompat.app.AppCompatActivity;
import com.example.salonproduct.Presenter.HomePresenter;
import com.example.salonproduct.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class Home_Page_API extends AppCompatActivity implements HomeActivityModel {



    HomePresenter homePresenter;

    ArrayList<Categre_model> categre_models = new ArrayList<>();
    ArrayList<Prodect_Model> prodect_models = new ArrayList<>();

    public Home_Page_API(HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
    }

    @Override
    public  void load_Category() {

        AsyncHttpClient client = new AsyncHttpClient();
      /*  RequestParams params = new RequestParams();
        params.put("type", "student");
        params.put("email", email);
        params.put("password", password);*/

        client.post("https://greenusys.com/salon/categories" ,new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                String myResponse=new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(myResponse);

                    if(jsonObject.getString("status").equals("1"))
                    {
                        JSONArray data=jsonObject.getJSONArray("data");

                        for(int i=0;i<data.length();i++)
                        {
                            JSONObject item=data.getJSONObject(i);

                            String cat_id=item.getString("cat_id");
                            String cat_name=item.getString("cat_name");

                            Categre_model categre_model=new Categre_model(cat_id,cat_name,R.drawable.haircondition);
                            categre_models.add(categre_model);

                        }
                        homePresenter.success_category_loaded(categre_models);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error)
            {




            }


        });





    }

    @Override
    public void load_Products() {
        Prodect_Model prodect_model = new Prodect_Model("nasdf", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Hair", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Color", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Garnier", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Nivia", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Garnier", R.drawable.haircondition);
        prodect_models.add(prodect_model);

        homePresenter.success_products_loaded(prodect_models);




    }

    @Override
    public void load_Slider_Images() {
        int[] img = {R.drawable.habib, R.drawable.amora, R.drawable.mac, R.drawable.himalaya, R.drawable.lotusbrand, R.drawable.habib};


        homePresenter.success_Slider_loaded(img);
    }


}
