package poc.telstra.com.telstrapoc.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import poc.telstra.com.telstrapoc.R;
import poc.telstra.com.telstrapoc.adapter.FacilityAdapter;
import poc.telstra.com.telstrapoc.model.Country;
import poc.telstra.com.telstrapoc.model.CountryAPI;
import poc.telstra.com.telstrapoc.util.Constant;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

public class Home extends Activity implements Callback<Country> ,OnRefreshListener{

    private FacilityAdapter mFacilityAdapter;
    private PullToRefreshLayout mPullToRefreshLayout;
    private ProgressBar mProgressBar;
    private ListView mCountryDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    /**
     * Method to initialize the class variables
     */
    private void initialize() {
        mPullToRefreshLayout= (PullToRefreshLayout)findViewById(R.id.pull_to_refresh_layout);
        ActionBarPullToRefresh.from(this)
                // Mark All Children as pullable
                .allChildrenArePullable()
                        // Set the OnRefreshListener
                .listener(this)
                        // Finally commit the setup to our PullToRefreshLayout
                .setup(mPullToRefreshLayout);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mCountryDetailList = (ListView) findViewById(R.id.country_detail);

    }

    /**
     * Method to consume the webservice using RetroFit
     */
    private void requestData() {

        toggleLoading(true);

        //Initializing the Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        CountryAPI countryAPI = retrofit.create(CountryAPI.class);
        Call<Country> call = countryAPI.loadCountryDetail();
        //asynchronous call
        call.enqueue(this);
    }


    /**
     *
     * Callback method upon success over network operations
     *
     * @param response
     * @param retrofit
     */
    @Override
    public void onResponse(Response<Country> response, Retrofit retrofit) {
        mPullToRefreshLayout.setRefreshComplete();
        toggleLoading(false);

        if(response!=null && response.body()!=null) {
            if(!TextUtils.isEmpty(response.body().getTitle())) {
                getActionBar().setTitle(response.body().getTitle());
            }
            if(response.body().getRows()!=null && response.body().getRows().size()>0) {
                mFacilityAdapter = new FacilityAdapter(Home.this, response.body().getRows());
                mCountryDetailList.setAdapter(mFacilityAdapter);
            } else {
                //No data available
                showErrorMessage();
            }
        } else {
            //No data from server
            showErrorMessage();
        }
    }

    private void showErrorMessage() {
        Toast.makeText(getApplicationContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
    }
    /**
     * Callback method upon failure over network operation
     * @param throwable
     */
    @Override
    public void onFailure(Throwable throwable) {
        toggleLoading(false);
        mPullToRefreshLayout.setRefreshComplete();
        //Error Occurred
        showErrorMessage();
    }

    @Override
    public void onRefreshStarted(View view) {
        requestData();
    }

    /**
     * Method to toggle the UI according to network operations
     * @param isLoading
     */
    public void toggleLoading(boolean isLoading) {
        if(isLoading) {
            mProgressBar.setVisibility(View.VISIBLE);
            mCountryDetailList.setVisibility(View.INVISIBLE);
    } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mCountryDetailList.setVisibility(View.VISIBLE);
        }
    }
}
