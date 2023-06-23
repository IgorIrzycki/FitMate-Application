package com.wat.edu.fitmateapp.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import android.location.Location;
import android.widget.Toast;

import com.wat.edu.fitmateapp.Model.ActivityRecord;
import com.wat.edu.fitmateapp.Model.LocationObject;
import com.wat.edu.fitmateapp.R;
import com.wat.edu.fitmateapp.Viewmodel.MealActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ActivityFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private long date;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;
    private int distance = 0;
    private int seconds = 0;
    private final ArrayList<LocationObject> locationObject = new ArrayList<>();
    private boolean buttonStartClicked = false;
    private TextView textViewDistance;
    private double caloriesBurned = 0;
    private double weight = 0;
    private MealActivityViewModel mealActivityViewModel;
    private String distanceText;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        date = System.currentTimeMillis();
        textViewDistance = view.findViewById(R.id.textViewDistance);
        Button buttonStart = view.findViewById(R.id.buttonStart);
        Button buttonStop = view.findViewById(R.id.buttonStop);

        mealActivityViewModel = new ViewModelProvider(this).get(MealActivityViewModel.class);
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build();

        String distanceInText = "0.000";
        textViewDistance.setText(distanceInText);


        buttonStart.setOnClickListener(v -> {
            buttonStart.setEnabled(false);
            buttonStop.setEnabled(true);
            buttonStart.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey));
            buttonStart.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_grey));
            buttonStop.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_green));
            buttonStop.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            buttonStartClicked = true;
        });

        buttonStop.setOnClickListener(v -> {
            buttonStart.setEnabled(true);
            buttonStop.setEnabled(false);
            buttonStart.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_green));
            buttonStart.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            buttonStop.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey));
            buttonStop.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_grey));
            textViewDistance.setText("0.000");
            buttonStartClicked = false;

            showWeightInputDialog();
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    currentLocation = location;
                }
            }
        };

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        startLocationUpdates();
        walkDistanceCounter();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                if (buttonStartClicked) {
                    Toast.makeText(requireContext(), "You need to click the Stop Button first.", Toast.LENGTH_SHORT).show();
                } else {
                    requireActivity().onBackPressed();
                }
                return true;
            }
            return false;
        });

        return view;
    }


    private void walkDistanceCounter() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (buttonStartClicked) {
                    if (currentLocation != null) {
                        if (seconds % 2 == 0) {
                            locationObject.add(new LocationObject(currentLocation.getLatitude(),
                                    currentLocation.getLongitude()));
                            updateDistance();
                        }
                        seconds++;
                    }
                    int kilometers = distance / 1000;
                    int meters = distance % 1000;
                    distanceText = String
                            .format(Locale.getDefault(), "%d.%03d", kilometers, meters);
                    textViewDistance.setText(distanceText);
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void updateDistance() {
        if (seconds > 2) {
            LocationObject location1 = locationObject.get(locationObject.size() - 1);
            LocationObject location2 = locationObject.get(locationObject.size() - 2);
            double distanceInDegrees = calculateLength(location1, location2);
            double converter = 111196.672;
            distance += distanceInDegrees * converter;
            Toast.makeText(requireContext(), "Updated distance: " + distance, Toast.LENGTH_SHORT).show();

        }
    }

    private double calculateLength(LocationObject location1, LocationObject location2) {
        double Y = location1.getLatitude() - location2.getLatitude();
        double X = location1.getLongitude() - location2.getLongitude();
        return Math.sqrt(Y * Y + X * X);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                Toast.makeText(requireContext(), "Start updates - request location updates", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "No permission for getting the location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private double calculateCaloriesBurnedDuringWalking(double weightInKg, double distanceInMeters) {
        double distanceInKm = distanceInMeters / 1000.0;
        double walkingSpeedKmPerHour = 5.0;
        double timeInHours = distanceInKm / walkingSpeedKmPerHour;
        double MET = 3.5;

        return weightInKg * MET * timeInHours;
    }

    private String formatDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date(date));
    }

    private void showWeightInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter your weight:");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_weight_input, null);
        builder.setView(dialogView);

        final EditText editTextWeight = dialogView.findViewById(R.id.editTextWeight);

        builder.setPositiveButton("Ok", (dialog, which) -> {
            String weightText = editTextWeight.getText().toString();
            weight = Double.parseDouble(weightText);
            if (weight == 0) {
                Toast.makeText(requireContext(), "You must enter a number greater than 0", Toast.LENGTH_SHORT).show();
                showWeightInputDialog();
            } else {
                caloriesBurned = calculateCaloriesBurnedDuringWalking(weight, distance);
                ActivityRecord activityRecord = new ActivityRecord();
                activityRecord.setDate(formatDate(date));
                activityRecord.setCaloriesBurned(caloriesBurned);
                activityRecord.setDistance(distanceText + " km");
                mealActivityViewModel.insertActivityRecord(activityRecord);
                NavController navController = NavHostFragment.findNavController(ActivityFragment.this);
                navController.navigate(R.id.action_activityFragment_to_hubFragment);
                Toast.makeText(requireContext(), "Activity Record saved, calories burned: " + caloriesBurned, Toast.LENGTH_SHORT).show();
            }

        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
