package com.replik.peksansevkiyat;

import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.replik.peksansevkiyat.Interface.Interfaces;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothDeviceFragment extends DialogFragment {

    View view;
    Button btnCancel;

    ListView data;
    private ArrayAdapter aAdapter;
    private BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bluetooth_device, null);

        setCancelable(false);

        data = view.findViewById(R.id.lstBluetoothDeviceList);
        btnCancel = view.findViewById(R.id.btnBluetoothDeviceCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Interfaces.BluetoothDeviceInterface intFace = (Interfaces.BluetoothDeviceInterface) getActivity();
                intFace.selectedDevice(false, "");
                dismiss();
            }
        });

        Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
        ArrayList list = new ArrayList();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String devicename = device.getName();
                //String macAddress = device.getAddress();
                list.add(devicename);
            }
            aAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
            data.setAdapter(aAdapter);

            data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Interfaces.BluetoothDeviceInterface intFace = (Interfaces.BluetoothDeviceInterface) getActivity();
                    intFace.selectedDevice(true, list.get(i).toString());
                    dismiss();
                }
            });
        }

        return view;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}