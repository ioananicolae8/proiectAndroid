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
import com.example.licenta.smartdoctor.dao.Pacient_UserDao;
import com.example.licenta.smartdoctor.enums.ETipUtilizator;
import com.example.licenta.smartdoctor.fragments.Pacient_AlegereDoctorFragment;
import com.example.licenta.smartdoctor.fragments.Pacient_Detalii_Fragment;
import com.example.licenta.smartdoctor.fragments.Pacient_HomeFragment;
import com.example.licenta.smartdoctor.fragments.Pacient_IstoriculMeuFragment;
import com.example.licenta.smartdoctor.fragments.Pacient_JurnalulMeuFragment;
import com.example.licenta.smartdoctor.fragments.SeteazaReminderFragment;
import com.example.licenta.smartdoctor.models.Pacient_User;
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

public class Pacient_MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
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
    private Pacient_UserDao pacientUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pacient);

        mAuth = FirebaseAuth.getInstance();
        idUtilizatorLogat = FirebaseAuth.getInstance().getCurrentUser().getUid();
        emailUtilizatorLogat = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        drawerLayout = findViewById(R.id.activityMenu);
        drawerToggle = new ActionBarDrawerToggle(Pacient_MenuActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
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

        pacientUserDao = new Pacient_UserDao(FirebaseFirestore.getInstance());

        setLoggedUserAvatar();
        setLoggedUserDetails();

        Fragment home = new Pacient_HomeFragment();
//        toolbarWindowIcon.setImageDrawable(getDrawable(R.drawable.icon_home));
        displayFragment(home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.hasConnection(Pacient_MenuActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu_pacient, menu);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            // return to Home fragment from any other fragment
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.menuFrame);
            if (!(currentFragment instanceof Pacient_HomeFragment)) {
//                toolbarWindowIcon.setImageDrawable(getDrawable(R.drawable.icon_home));
                displayFragment(new Pacient_HomeFragment());
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog
                        .setTitle(getString(R.string.mesaj_logout))
                        .setMessage(getString(R.string.mesaj_logout_extra))
                        .setNegativeButton(R.string.no, null)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TipUtilizatorLogatHelper.seteazaTipUtilizatorLogat(Pacient_MenuActivity.this, null);
                                mAuth.signOut();
                                Intent main = new Intent(Pacient_MenuActivity.this, MainActivity.class);
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
                        Pacient_MenuActivity.this,
                        loggedUserId)
                );

        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imgUrl = uri.toString();

                        // Loading the image into the navigation drawer
                        Glide.with(Pacient_MenuActivity.this)
                                .load(imgUrl)
                                .into(userAvatar);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Glide.with(Pacient_MenuActivity.this)
                                .load(AuthenticationHelper.getDefaultAvatarUrl())
                                .into(userAvatar);
                    }
                });
    }

    private void setLoggedUserDetails() {
        Pacient_UserDao pacientUserDAO = new Pacient_UserDao(FirebaseFirestore.getInstance());
        String loggedUserId = mAuth.getCurrentUser().getUid();

        pacientUserDAO.getUserFromDb(loggedUserId)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Pacient_User pacientUser = documentSnapshot.toObject(Pacient_User.class);

                        TextView username = navigationView.getHeaderView(HEADER_VIEW_INDEX).findViewById(R.id.textViewUserDetailsUsername);
                        username.setText(pacientUser.getUsername());

                        TextView email = navigationView.getHeaderView(HEADER_VIEW_INDEX).findViewById(R.id.textViewUserDetailsEmail);
                        email.setText(pacientUser.getEmail());
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
        final Fragment fragment;

        switch (id) {
            case R.id.actionHome:
                closeDrawer();
                fragment = new Pacient_HomeFragment();
                displayFragment(fragment);
                break;

            case R.id.actionIstoric:
                closeDrawer();
                fragment = new Pacient_IstoriculMeuFragment();
                displayFragment(fragment);
                break;

            case R.id.actionJurnalulMeu:
                closeDrawer();
                fragment = new Pacient_JurnalulMeuFragment();
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
                pacientUserDao.getUserFromDb(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Pacient_User pacientUser = documentSnapshot.toObject(Pacient_User.class);

                                if (pacientUser.getDoctorId() == null) {
                                    displayFragment(new Pacient_AlegereDoctorFragment());
                                } else {
                                    Intent intent = new Intent(Pacient_MenuActivity.this, ChatActivity.class);
                                    assert pacientUser != null;
                                    intent.putExtra("idDestinatar", pacientUser.getDoctorId());
                                    intent.putExtra("emailDestinatar", pacientUser.getDoctorEmail());
                                    intent.putExtra("tipDestinatar", ETipUtilizator.DOCTOR.name());
                                    startActivity(intent);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                e.printStackTrace();
                            }
                        });
                break;

            case R.id.actionSignOut:
                closeDrawer();
                Intent login = new Intent(Pacient_MenuActivity.this, MainActivity.class);
                TipUtilizatorLogatHelper.seteazaTipUtilizatorLogat(this, null);
                mAuth.signOut();
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
//        final Dialog info = new Dialog(this);
//        info.setContentView(R.layout.fragment_info_dialog);
//
//        Button backButton = info.findViewById(R.id.fragmentInfoDialogButton);
//        TextView titleTextView = info.findViewById(R.id.fragmentInfoDialogTitle);
//        TextView infoTextView = info.findViewById(R.id.fragmentInfoDialogTextView);
//        ImageView windowIcon = info.findViewById(R.id.fragmentInfoDialogImage);
//
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                info.dismiss();
//            }
//        });
//
//        // schimbi iconita din dreapta sus in functie
//        // de fragmentul pe care ai dat click si in care esti
//        if (v.getId() == R.id.toolbarWindowIcon) {
//            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.menuFrame);
//
//            if (currentFragment instanceof HomeFragment) {
//                windowIcon.setImageDrawable(getDrawable(R.drawable.icon_home));
//            }
//            if (currentFragment instanceof GeneratorFragment) {
//                titleTextView.setText(getString(R.string.generator));
//                infoTextView.setText(getString(R.string.generator_description));
//                windowIcon.setImageDrawable(getDrawable(R.drawable.icon_generator));
//            }
//            if (currentFragment instanceof SunglassesFragment) {
//                titleTextView.setText(getString(R.string.sunglasses));
//                infoTextView.setText(getString(R.string.sunglasses_description));
//                windowIcon.setImageDrawable(getDrawable(R.drawable.icon_sunglasses_dark));
//            }
//            if (currentFragment instanceof MapFragment) {
//                titleTextView.setText(getString(R.string.maps));
//                infoTextView.setText(getString(R.string.maps_description));
//                windowIcon.setImageDrawable(getDrawable(R.drawable.icon_maps));
//            }
//            if (currentFragment instanceof MyClothesFragment) {
//                titleTextView.setText(getString(R.string.my_clothes));
//                infoTextView.setText(getString(R.string.my_clothes_description));
//                windowIcon.setImageDrawable(getDrawable(R.drawable.icon_my_wardrobe));
//            }
//            if (currentFragment instanceof ClothingPickerFragment) {
//                titleTextView.setText(getString(R.string.available_clothes));
//                infoTextView.setText(getString(R.string.available_clothes_description));
//                windowIcon.setImageDrawable(getDrawable(R.drawable.icon_clothes));
//            }
//        }
//
//        info.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        info.show();
    }

    // Receive data from ClothingPickerFragment
//    @Override
//    public void onAttachFragment(Fragment fragment) {
//        if (fragment instanceof ClothingPickerFragment) {
//            ClothingPickerFragment picker = (ClothingPickerFragment) fragment;
//            picker.setOnMandatoryClothesPurchasedListener(this);
//        }
//    }
}
