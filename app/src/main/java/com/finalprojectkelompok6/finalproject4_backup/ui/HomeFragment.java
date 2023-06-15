package com.finalprojectkelompok6.finalproject4_backup.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.finalprojectkelompok6.finalproject4_backup.NotificationActivity;
import com.finalprojectkelompok6.finalproject4_backup.R;
import com.finalprojectkelompok6.finalproject4_backup.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    FirebaseFirestore db;
    private int penumpang = 0;
    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener setListener;
    String linkBus = "";
    double jarak;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        binding.btSearchBus.setOnClickListener(this);
        binding.notif.setOnClickListener(this);

        // Spinner from
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Kota, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.from.setAdapter(adapter);
        // Spinner To
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.Tujuan, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.to.setAdapter(adapter1);

        // count penumpang
        binding.plus.setOnClickListener(this);
        binding.minus.setOnClickListener(this);

        // select Date
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        binding.date.setOnClickListener(view1 -> {
            datePickerDialog = new DatePickerDialog(getActivity(), setListener, year, month, day);
            datePickerDialog.show();
        });
        setListener = (datePicker, year1, month1, dayOfMonth) -> {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.YEAR, year1);
            calendar1.set(Calendar.MONTH, month1);
            calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String pickerDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar1.getTime());
            binding.date.setText(pickerDateString);

        };

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) { //penumpang
        int id = view.getId();
        if (id == R.id.plus) {
            penumpang++;
            binding.pessengers.setText(String.valueOf(penumpang));
        } else if (id == R.id.minus) {
            penumpang--;
            binding.pessengers.setText(String.valueOf(penumpang));
        } else if (id == R.id.btSearchBus) {
            if (binding.from.getSelectedItem().toString().equals(binding.to.getSelectedItem().toString())) {
                Toast.makeText(getActivity(), "lengkapi dulu", Toast.LENGTH_SHORT).show();
            } else if (binding.date == null) {
                Toast.makeText(getActivity(), "Plese select your date", Toast.LENGTH_SHORT).show();
            } else if (binding.pessengers == null) {
                Toast.makeText(getActivity(), "Plese select your pessengers", Toast.LENGTH_SHORT).show();
            } else {
                checkPriceAndBus();
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("from", binding.from.getSelectedItem().toString());
                intent.putExtra("to", binding.to.getSelectedItem().toString());
                intent.putExtra("pessengers", binding.pessengers.getText().toString());
                intent.putExtra("date", binding.date.getText().toString());
                intent.putExtra("jarak", String.valueOf(jarak));
                intent.putExtra("imgBus", String.valueOf(linkBus));
                startActivity(intent);
            }
        } else if (id == R.id.notif) {
            startActivity(new Intent(getActivity(), NotificationActivity.class));
        }
    }

    private void checkPriceAndBus() {

        if (binding.from.getSelectedItem().toString().equals("Solo") && binding.to.getSelectedItem().toString().equals("Bandung") || binding.from.getSelectedItem().toString().equals("Bandung") && binding.to.getSelectedItem().toString().equals("Solo")) {
            Toast.makeText(getActivity(), "sukses", Toast.LENGTH_SHORT).show();
            jarak = 87.8;
            linkBus = "https://4.bp.blogspot.com/-WbGOH6vIl-M/V5letupc-BI/AAAAAAAABDM/r0jqkLXxYfM7bFNuKQS0jV52s-BL2YqGACLcB/s1600/akas%2BI.JPG";
        } else if (binding.from.getSelectedItem().toString().equals("Bandung") && binding.to.getSelectedItem().toString().equals("Surabaya") || binding.from.getSelectedItem().toString().equals("Surabaya") && binding.to.getSelectedItem().toString().equals("Bandung")) {
            jarak = 149;
            linkBus = "";
        } else if (binding.from.getSelectedItem().toString().equals("Surabaya") && binding.to.getSelectedItem().toString().equals("Purwokerto") || binding.from.getSelectedItem().toString().equals("Purwokerto") && binding.to.getSelectedItem().toString().equals("Malang")) {
            jarak = 124;
            linkBus = "";
        }
    }

}