package com.replik.peksansevkiyat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.replik.peksansevkiyat.DataClass.ListAdapter.ListAdapter_Customer;
import com.replik.peksansevkiyat.DataClass.ListAdapter.ListenerInterface;
import com.replik.peksansevkiyat.DataClass.ModelDto.Customer.Customer;
import com.replik.peksansevkiyat.Interface.APIClient;
import com.replik.peksansevkiyat.Interface.APIInterface;
import com.replik.peksansevkiyat.Transection.GlobalVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipmentCustomerListActivity extends AppCompatActivity implements ListenerInterface.ShipmentCustomerListener {

    RecyclerView customerListRecyclerView;
    ListAdapter_Customer customerListAdapter;
    TextView staffNameTextView;
    EditText searchEditText;
    List<Customer> customerList = new ArrayList<>();
    ImageView logoImageView, settingsImageView;
    ConstraintLayout circularProgress;
    APIInterface apiInterface;
    Context context = ShipmentCustomerListActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_customer_list);

        apiInterface = APIClient.getRetrofit().create(APIInterface.class);

        searchEditText = (EditText) findViewById(R.id.txtSearch);
        customerListRecyclerView = (RecyclerView) findViewById(R.id.customer_list_recycler_view);
        settingsImageView = (ImageView) findViewById(R.id.imgSettings);
        logoImageView = (ImageView) findViewById(R.id.imgLogo);
        circularProgress = (ConstraintLayout) findViewById(R.id.pnlProgressBar);
        staffNameTextView = (TextView) findViewById(R.id.txtUserName);

        staffNameTextView.setText(GlobalVariable.getUserName());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        customerListAdapter = new ListAdapter_Customer(customerList, this);
        customerListRecyclerView.setAdapter(customerListAdapter);
        customerListRecyclerView.setLayoutManager(linearLayoutManager);

        searchEditText.setOnKeyListener(
                (v, keyCode, event) -> {

                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        customerListAdapter.search(searchEditText.getText().toString().trim());
                        return true;
                    }

                    return false;
                }
        );

        logoImageView.setOnClickListener(v -> {
            finish();
        });

        settingsImageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
        });

        fetchCustomers();
    }

    void fetchCustomers() {
        apiInterface.getCustomers().enqueue(
                new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        if (response.isSuccessful()) {
                            customerList = response.body();

                            customerListAdapter.setList(customerList);
                        }

                        circularProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        circularProgress.setVisibility(View.GONE);
                    }
                }
        );
    }

    @Override
    public void onItemClicked(Customer customer) {
        Intent intent = new Intent(this, ShipmentOrderListActivity.class);
        intent.putExtra("customer", customer);

        startActivity(intent);
    }
}