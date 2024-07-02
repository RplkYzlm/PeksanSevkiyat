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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.replik.peksansevkiyat.DataClass.ListAdapter.ListAdapter_Customer;
import com.replik.peksansevkiyat.DataClass.ListAdapter.ListAdapter_Customer_Order;
import com.replik.peksansevkiyat.DataClass.ListAdapter.ListenerInterface;
import com.replik.peksansevkiyat.DataClass.ModelDto.Customer.Customer;
import com.replik.peksansevkiyat.DataClass.ModelDto.Customer.CustomerOrder;
import com.replik.peksansevkiyat.Interface.APIClient;
import com.replik.peksansevkiyat.Interface.APIInterface;
import com.replik.peksansevkiyat.Transection.GlobalVariable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipmentOrderListActivity extends AppCompatActivity implements ListenerInterface.ShipmentCustomerOrderListener {

    Customer customer;
    RecyclerView customerOrderListRecyclerView;
    EditText searchEditText;
    List<CustomerOrder> orderList = new ArrayList<>();
    TextView staffNameTextView, customerNameTextView, customerCodeTextView;
    ImageView logoImageView, settingsImageView;
    Context context = ShipmentOrderListActivity.this;
    ListAdapter_Customer_Order listAdapterCustomerOrder;
    ConstraintLayout pnlProgressBar;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_order_list);
        Intent intent = getIntent();

        customer = (Customer) intent.getSerializableExtra("customer");

        apiInterface = APIClient.getRetrofit().create(APIInterface.class);

        searchEditText = (EditText) findViewById(R.id.txtSearch);
        customerOrderListRecyclerView = (RecyclerView) findViewById(R.id.customer_order_list_recycler_view);
        settingsImageView = (ImageView) findViewById(R.id.imgSettings);
        logoImageView = (ImageView) findViewById(R.id.imgLogo);
        staffNameTextView = (TextView) findViewById(R.id.txtUserName);
        customerNameTextView = (TextView) findViewById(R.id.customer_fullname);
        customerCodeTextView = (TextView) findViewById(R.id.customer_code);
        pnlProgressBar = (ConstraintLayout) findViewById(R.id.pnlProgressBar);

        staffNameTextView.setText(GlobalVariable.getUserName());

        customerNameTextView.setText(customer.getFullName());
        customerCodeTextView.setText(customer.getCode().toString());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        listAdapterCustomerOrder = new ListAdapter_Customer_Order(orderList, this);
        customerOrderListRecyclerView.setAdapter(listAdapterCustomerOrder);
        customerOrderListRecyclerView.setLayoutManager(linearLayoutManager);

        searchEditText.setOnKeyListener(
                (v, keyCode, event) -> {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        final String term = searchEditText.getText().toString().toLowerCase().trim();

                        listAdapterCustomerOrder.search(term);
                    }

                    return false;
                }
        );

        logoImageView.setOnClickListener(v -> {
            finish();
        });

        settingsImageView.setOnClickListener(v -> {
            Intent i = new Intent(context, SettingsActivity.class);

            startActivity(i);
        });

        fetchCustomerOrders();
    }

    void fetchCustomerOrders() {
        apiInterface.getCustomerOrders(customer.getCode()).enqueue(
                new Callback<List<CustomerOrder>>() {
                    @Override
                    public void onResponse(Call<List<CustomerOrder>> call, Response<List<CustomerOrder>> response) {
                        if (response.isSuccessful()) {
                            listAdapterCustomerOrder.setList(response.body());
                        }

                        pnlProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<CustomerOrder>> call, Throwable t) {
                        pnlProgressBar.setVisibility(View.GONE);
                    }
                }
        );
    }

    @Override
    public void onItemClicked(CustomerOrder customerOrder) {
        Intent i = new Intent(context, ShipmentOrderDetailActivity.class);

        i.putExtra("customer", customer);
        i.putExtra("order", customerOrder);

        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        fetchCustomerOrders();
    }
}