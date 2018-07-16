package apps.com.coditasproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_Main extends AppCompatActivity {

    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    RelativeLayout relativeLayoutSort;
    SearchView searchView = null;
    RecyclerView recyclerViewUsers;
    TextView textViewResultCount;
    LinearLayoutManager mLayoutManager;
    BottomSheetBehavior bottomSheetBehavior;

    String stringSearch = "";
    List<List_Users> usersList = new ArrayList<List_Users>();
    private Adapter_RecyclerUsers pgAdapter;
    String[] spinnerListSort = {"Name [A-Z]", "Name [Z-A]","Rank Up","Rank Down"};
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        relativeLayoutSort = (RelativeLayout)findViewById(R.id.linlaysort);
        recyclerViewUsers = (RecyclerView)findViewById(R.id.recycler_viewUsers);
        textViewResultCount = (TextView)findViewById(R.id.tv_resultcount);
        pgAdapter = new Adapter_RecyclerUsers(usersList,Activity_Main.this);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        View bottomSheet_CancelButton = findViewById(R.id.btn_cancel);
        View bottomSheet_NameAZ = findViewById(R.id.tv_nameaz);
        View bottomSheet_NameZA = findViewById(R.id.tv_nameza);
        View bottomSheet_RankUp = findViewById(R.id.tv_rankup);
        View bottomSheet_RankDown = findViewById(R.id.tv_rankdown);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        setSupportActionBar(toolbar);
        initNavigationDrawer();

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setAdapter(pgAdapter);

        if (stringSearch.equals(""))
            stringSearch = "search";
        getUsersVolleyRequest();

        recyclerViewUsers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            getUsersVolleyRequest();
                        }
                    }
                }
            }
        });

        recyclerViewUsers.addOnItemTouchListener(new RecyclerTouchListener(Activity_Main.this, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Activity_Main.this, Activity_Details.class);
                intent.putExtra("name",usersList.get(position).getStringUserName());
                intent.putExtra("image",usersList.get(position).getStringProfileImage());
                startActivity(intent);
            }
        }));

        relativeLayoutSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        bottomSheet_CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

//        bottomSheet_RankUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Collections.sort(usersList);
//                }catch(NumberFormatException ex){ // handle your exception
//                    Toast.makeText(Activity_Main.this,ex.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        bottomSheet_NameAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(usersList, new Comparator<List_Users>() {
                    @Override
                    public int compare(List_Users o1, List_Users o2) {
                        return o1.stringUserName.compareTo(o2.stringUserName);
                    }
                });
                pgAdapter.notifyDataSetChanged();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        bottomSheet_NameZA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(usersList, new Comparator<List_Users>() {
                    @Override
                    public int compare(List_Users o1, List_Users o2) {
                        return o2.stringUserName.compareTo(o1.stringUserName);
                    }
                });
                pgAdapter.notifyDataSetChanged();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        bottomSheet_RankDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(usersList, new Comparator<List_Users>() {
                    @Override
                    public int compare(List_Users o1, List_Users o2) {
                        return o2.stringScore.compareTo(o1.stringScore);
                    }
                });
                pgAdapter.notifyDataSetChanged();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        bottomSheet_RankUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(usersList, new Comparator<List_Users>() {
                    @Override
                    public int compare(List_Users o1, List_Users o2) {
                        return o1.stringScore.compareTo(o2.stringScore);
                    }
                });
                pgAdapter.notifyDataSetChanged();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.main_menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //searchView.clearFocus();
                    if (query.length() > 2) {
                        stringSearch = query;
                        getUsersVolleyRequest();
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

        return super.onCreateOptionsMenu(menu);
    }

    public void initNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
//                    case R.id.action_search:
//                        //search code
//                        break;
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }
            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public void getUsersVolleyRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.GET_USER_LIST + stringSearch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);

                            if (json.has("total_count")) {
                                usersList.clear();
                                textViewResultCount.setText("Showing " + json.getString("total_count") + " results");

                                JSONArray items = new JSONArray(json.getString("items"));

                                for (int i =0 ;i<items.length();i++){
                                    JSONObject itemObj = items.getJSONObject(i);
                                    List_Users userList = new List_Users();

                                    userList.setStringId(itemObj.getString("id"));
                                    userList.setStringUserName(itemObj.getString("login"));
                                    userList.setStringScore("Score : " + itemObj.getString("score"));
                                    userList.setStringProfileImage(itemObj.getString("avatar_url"));
                                    usersList.add(userList);
                                }
                                pgAdapter.notifyDataSetChanged();
                            }else {
                                Toast.makeText(Activity_Main.this,json.getString("error_msg"), Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException je){
                            je.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_Main.this, error.toString(), Toast.LENGTH_SHORT).show();
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
}
