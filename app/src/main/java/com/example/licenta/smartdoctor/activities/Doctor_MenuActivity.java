package com.example.licenta.smartdoctor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.licenta.R;
import com.example.licenta.smartdoctor.dao.Doctor_UserDao;
import com.example.licenta.smartdoctor.enums.ETipUtilizator;
import com.example.licenta.smartdoctor.fragments.Doctor_AlegereChatCuPacientFragment;
import com.example.licenta.smartdoctor.fragments.Doctor_HomeFragment;
import com.example.licenta.smartdoctor.fragments.Doctor_ProgramariFragment;
import com.example.licenta.smartdoctor.fragments.Pacient_Detalii_Fragment;
import com.example.licenta.smartdoctor.fragments.SeteazaReminderFragment;
import com.example.licenta.smartdoctor.models.Doctor_User;
import com.example.licenta.smartdoctor.utils.common.AuthenticationHelper;
import com.example.licenta.smartdoctor.utils.common.ConnectionHelper;
import com.example.licenta.smartdoctor.utils.common.TipUtilizatorLogatHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Doctor_MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private static int HEADER_VIEW_INDEX = 0;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private ImageView toggleButton;
    private ImageView toolbarWindowIcon;

    private boolean hasAccessToGenerator = false;

    private FirebaseAuth mAuth;
    private String idUtilizatorLogat;

    private String emailUtilizatorLogat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__menu);

        mAuth = FirebaseAuth.getInstance();
        idUtilizatorLogat = FirebaseAuth.getInstance().getCurrentUser().getUid();
        emailUtilizatorLogat = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        drawerLayout = findViewById(R.id.activityMenuDoctor);
        drawerToggle = new ActionBarDrawerToggle(Doctor_MenuActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        toggleButton = findViewById(R.id.navigationViewToggleButton);
        toolbarWindowIcon = findViewById(R.id.toolbarWindowIcon);
        toolbarWindowIcon.setOnClickListener(this);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        setLoggedUserAvatar();
        setLoggedUserDetails();

        Fragment home = new Doctor_HomeFragment();
//        toolbarWindowIcon.setImageDrawable(getDrawable(R.drawable.icon_home));
        displayFragment(home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.hasConnection(Doctor_MenuActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu_doctor, menu);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            // return to Home fragment from any other fragment
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.menuFrame);
            if (!(currentFragment instanceof Doctor_HomeFragment)) {
//                toolbarWindowIcon.setImageDrawable(getDrawable(R.drawable.icon_home));
                displayFragment(new Doctor_HomeFragment());
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog
                        .setTitle(getString(R.string.mesaj_logout))
                        .setMessage(getString(R.string.mesaj_logout_extra))
                        .setNegativeButton(R.string.no, null)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TipUtilizatorLogatHelper.seteazaTipUtilizatorLogat(Doctor_MenuActivity.this, null);
                                mAuth.signOut();
                                Intent main = new Intent(Doctor_MenuActivity.this, MainActivity.class);
                                startActivity(main);
                            }
                        })
                        .create()
                        .show();
            }
        }
    }

    private void setLoggedUserAvatar() {
        String loggedUserId = mAuth.getCurrentUser().getUid();
        final ImageView userAvatar = navigationView.getHeaderView(HEADER_VIEW_INDEX).findViewById(R.id.imageViewAvatarDrawer);

        StorageReference reference = FirebaseStorage
                .getInstance()
                .getReference()
                .child(AuthenticationHelper.getUserAvatarPath(
                        Doctor_MenuActivity.this,
                        loggedUserId)
                );

        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imgUrl = uri.toString();

                        // Loading the image into the navigation drawer
                        Glide.with(Doctor_MenuActivity.this)
                                .load(imgUrl)
                                .into(userAvatar);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Glide.with(Doctor_MenuActivity.this)
                                .load(AuthenticationHelper.getDefaultAvatarUrl())
                                .into(userAvatar);
                    }
                });
    }

    private void setLoggedUserDetails() {
        Doctor_UserDao doctorUserDAO = new Doctor_UserDao(FirebaseFirestore.getInstance());
        String loggedUserId = mAuth.getCurrentUser().getUid();

        doctorUserDAO.getUserFromDb(loggedUserId)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Doctor_User doctorUser  = documentSnapshot.toObject(Doctor_User.class);

                        TextView username = navigationView.getHeaderView(HEADER_VIEW_INDEX).findViewById(R.id.textViewUserDetailsUsername);
                        username.setText(doctorUser.getUsername());

                        TextView email = navigationView.getHeaderView(HEADER_VIEW_INDEX).findViewById(R.id.textViewUserDetailsEmail);
                        email.setText(doctorUser.getEmail());
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment;

        switch (id) {
            case R.id.actionHome:
                closeDrawer();
                fragment = new Doctor_HomeFragment();
                displayFragment(fragment);
                break;

            case R.id.actionProgramari:
                closeDrawer();
                fragment = new Doctor_ProgramariFragment();
                displayFragment(fragment);
                break;

            case R.id.actionDespreDiabet:
                closeDrawer();
                fragment = new Pacient_Detalii_Fragment();
                displayFragment(fragment);
                break;

            case R.id.actionSeteazaReminder:
                closeDrawer();
                fragment = new SeteazaReminderFragment();
                displayFragment(fragment);
                break;

            case R.id.actionChat:
                closeDrawer();
                fragment = new Doctor_AlegereChatCuPacientFragment();
                displayFragment(fragment);
                break;

            case R.id.actionSignOut:
                closeDrawer();
                Intent login = new Intent(Doctor_MenuActivity.this, MainActivity.class);
                mAuth.signOut();
                TipUtilizatorLogatHelper.seteazaTipUtilizatorLogat(Doctor_MenuActivity.this, ETipUtilizator.DOCTOR);
                startActivity(login);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(R.id.menuFrame, fragment)
                .addToBackStack(null)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .commit();
    }

    private void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View v) {

    }
}