package apps.com.coditasproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_Details extends AppCompatActivity{

    CircleImageView circleImageViewProfile;
    Button buttonViewProfile;
    ImageView imageViewBack;
    RecyclerView recyclerViewRepositories;
    TextView textViewName;

    String stringUserName = "";
    List<List_Repository> reposList = new ArrayList<List_Repository>();
    private Adapter_RecyclerRepository pgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        buttonViewProfile = (Button)findViewById(R.id.btn_viewprofile);
        circleImageViewProfile = (CircleImageView)findViewById(R.id.profile_image);
        imageViewBack = (ImageView)findViewById(R.id.imageViewBack);
        textViewName = (TextView)findViewById(R.id.tv_username);
        recyclerViewRepositories = (RecyclerView)findViewById(R.id.recycler_viewRepos);
        pgAdapter = new Adapter_RecyclerRepository(reposList);

        Picasso.with(this).load(getIntent().getStringExtra("image")).into(circleImageViewProfile);
        textViewName.setText(getIntent().getStringExtra("name"));
        stringUserName = getIntent().getStringExtra("name");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewRepositories.setLayoutManager(mLayoutManager);
        recyclerViewRepositories.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewRepositories.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRepositories.setAdapter(pgAdapter);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Details.this, Activity_Main.class);
                startActivity(intent);
            }
        });

        getUserDetailsVolleyRequest();
    }

    public void getUserDetailsVolleyRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.GET_REPOSITORIES + stringUserName + "/repos",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray json = new JSONArray(response);

                            for (int i =0 ;i<json.length();i++){
                                JSONObject itemObj = json.getJSONObject(i);
                                List_Repository repositoryList = new List_Repository();

                                repositoryList.setStringRepositoryName(itemObj.getString("name"));
                                repositoryList.setStringRepositoryDescription(itemObj.getString("description"));
                                repositoryList.setStringRepositoryURL(itemObj.getString("url"));
                                repositoryList.setStringLanguage(itemObj.getString("language"));
                                repositoryList.setStringCreatedOn(itemObj.getString("created_at"));
                                reposList.add(repositoryList);
                            }
                            pgAdapter.notifyDataSetChanged();
                        }catch (JSONException je){
                            je.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_Details.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                // params.put("q",stringSearch);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Activity_Details.this, Activity_Main.class);
        startActivity(intent);
    }
}
