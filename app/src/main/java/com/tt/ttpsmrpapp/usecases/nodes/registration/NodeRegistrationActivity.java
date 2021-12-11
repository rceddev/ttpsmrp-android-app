package com.tt.ttpsmrpapp.usecases.nodes.registration;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.network.bluetooth.BluetoothRepository;
import com.tt.ttpsmrpapp.usecases.nodes.registration.fragments.BluetoothPickerFragment;
import com.tt.ttpsmrpapp.usecases.nodes.registration.viewmodel.InitViewModel;

public class NodeRegistrationActivity extends AppCompatActivity {

    public static final String TAG = NodeRegistrationActivity.class.getSimpleName();
    private InitViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_registration);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_nr);
        setSupportActionBar(myToolbar);

        // ViewModel
        viewModel = new ViewModelProvider(this).get(InitViewModel.class);
        viewModel.setBluetoothRepository(new BluetoothRepository());
        /// Check BT, only add the fragment when BT controller is enabled
        if ( !viewModel.bluetoothControllerEnabled()  ) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    (ActivityResult result) -> {
                        switch (result.getResultCode()) {
                            case Activity.RESULT_OK:
                                Log.d(TAG, "bluetoothEnableIntent: Bluetooth habilitado correctamente!");
                                addBluetoothPickerFragment(savedInstanceState);
                                break;
                            case Activity.RESULT_CANCELED:
                                Log.d(TAG, "bluetoothEnableIntent: Permiso cerrado");
                                break;
                            default:
                                Log.e(TAG, String.format("bluetoothEnableIntent: Error habilitando bluetooth code %d",
                                        result.getResultCode()));
                                break;
                        }
                    }).launch(enableBtIntent);
        } else {
            addBluetoothPickerFragment(savedInstanceState);
        }
    }

    private void addBluetoothPickerFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, BluetoothPickerFragment.newInstance(BluetoothPickerFragment.TYPE_CENTRAL))
                    .commit();
        }
    }
}