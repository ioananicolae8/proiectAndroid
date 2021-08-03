package com.example.licenta.smartdoctor.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.licenta.BuildConfig;
import com.example.licenta.R;
import com.example.licenta.smartdoctor.dao.Pacient_UserDao;
import com.example.licenta.smartdoctor.enums.ETipUtilizator;
import com.example.licenta.smartdoctor.interfaces.IRegistration;
import com.example.licenta.smartdoctor.models.Pacient_User;
import com.example.licenta.smartdoctor.utils.common.AuthenticationHelper;
import com.example.licenta.smartdoctor.utils.common.ConnectionHelper;
import com.example.licenta.smartdoctor.utils.common.TipUtilizatorLogatHelper;
import com.example.licenta.smartdoctor.utils.common.ToastHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Pacient_RegisterActivity extends AppCompatActivity implements View.OnClickListener, IRegistration {

    private static int ANIMATION_DURATION = 150;

    private static final String PROVIDER = ".provider";

    private static final String SAVED_IMAGE_HEADER = "JPEG_";
    private static final String SAVED_IMAGE_EXTENSION = ".jpg";
    private static final String DATE_PATTERN = "yyyyMMdd_HHmmss";

    private static final int TAKE_PHOTO_RESULT_CODE = 0;
    private static final int PICK_PHOTO_RESULT_CODE = 1;

    private static final boolean HAS_COMPLETED_QUIZ = false;
    private static final boolean HAS_REQUIRED_INFORMATION = false;


    private ImageView imageViewAvatar;
    private AutoCompleteTextView viewEmail;
    private EditText viewPassword;
    private EditText viewUsername;
    private EditText viewAge;
    private EditText viewPhone;
    private EditText viewCNP;
    private EditText viewNume;
    private EditText viewPrenume;
    private EditText viewAdresa;
    private RadioGroup viewGen;
    private EditText viewDataNastere;
    private ProgressBar progressBar;
    private Button btnRegister;

    private FirebaseAuth mAuth;
    private String mCurrentPhotoPath;
    private Uri mSelectedImageUri;
    private boolean mDefaultAvatarChanged = false;

    private Pair<String, String> mCredentials;
    private Map<String, String> mUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pacient);

        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        viewEmail = findViewById(R.id.viewEmail);
        viewPassword = findViewById(R.id.viewPassword);
        viewUsername = findViewById(R.id.viewUsername);
        viewAge = findViewById(R.id.viewAge);
        viewPhone = findViewById(R.id.viewPhone);
        viewCNP = findViewById(R.id.viewCNP);
        viewNume = findViewById(R.id.viewNume);
        viewPrenume = findViewById(R.id.viewPrenume);
        viewAdresa = findViewById(R.id.viewAdresa);
        viewGen = findViewById(R.id.viewGen);
        viewDataNastere = findViewById(R.id.viewDataNastere);
        progressBar = findViewById(R.id.registerProgressBar);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(this);

        imageViewAvatar.setOnClickListener(this);

        //mUserDetails = new HashMap<String, String>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.hasConnection(Pacient_RegisterActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PHOTO_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImageUri = FileProvider.getUriForFile(Pacient_RegisterActivity.this,
                                BuildConfig.APPLICATION_ID + PROVIDER,
                                new File(mCurrentPhotoPath));
                        mSelectedImageUri = selectedImageUri;
                        mDefaultAvatarChanged = true;
                        Glide.with(Pacient_RegisterActivity.this)
                                .load(selectedImageUri)
                                .apply(RequestOptions.circleCropTransform())
                                .into(imageViewAvatar);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastHelper.showToastWithImage(Pacient_RegisterActivity.this, getString(R.string.error_image_creation), R.drawable.icon_error);
                }
                break;

            case PICK_PHOTO_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    mSelectedImageUri = selectedImageUri;
                    mDefaultAvatarChanged = true;
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        Glide.with(Pacient_RegisterActivity.this)
                                .load(bitmap)
                                .apply(RequestOptions.circleCropTransform())
                                .into(imageViewAvatar);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastHelper.showToastWithImage(Pacient_RegisterActivity.this, getString(R.string.error_image_creation), R.drawable.icon_error);
                }
                break;
        }
    }


    // Creates the File used for storing the image taken with the camera
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat(DATE_PATTERN).format(new Date());
        String imageFileName = SAVED_IMAGE_HEADER + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, SAVED_IMAGE_EXTENSION, storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnRegister:
                boolean success = validare();
                if (success) {
                    startAnimation();
                    register(mCredentials.first, mCredentials.second);
                }
                break;

            case R.id.imageViewAvatar:
                final String[] choices = {
                        getString(R.string.alege_galerie)
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Pacient_RegisterActivity.this);
                builder.setTitle(getString(R.string.dialog_photo_selection));
                builder.setItems(choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (getString(R.string.alege_camera).equals(choices[i])) {
                            // To handle exposed error
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());

                            Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // Ensure that there's a camera activity to handle the intent
                            if (takePhoto.resolveActivity(getPackageManager()) != null) {
                                // Create the File where the photo should go
                                File photoFile = null;
                                try {
                                    photoFile = createImageFile();
                                } catch (IOException ex) {
                                    ToastHelper.showToastWithImage(Pacient_RegisterActivity.this, getString(R.string.error_file_creation), R.drawable.icon_error);
                                    return;
                                }
                                // Continue only if the File was successfully created
                                if (photoFile != null) {
                                    Uri photoURI = null;
                                    photoURI = Uri.fromFile(photoFile);
                                    takePhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                    startActivityForResult(takePhoto, TAKE_PHOTO_RESULT_CODE);
                                }
                            }

                        } else if (getString(R.string.alege_galerie).equals(choices[i])) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_PHOTO_RESULT_CODE);
                        }
                    }
                });
                builder.show();
                break;
        }
    }

    private void startAnimation() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void resetAnimation() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private boolean validare() {
        viewEmail.setError(null);
        viewPassword.setError(null);
        viewUsername.setError(null);
        viewAge.setError(null);
        viewPhone.setError(null);
        viewCNP.setError(null);
        viewNume.setError(null);
        viewPrenume.setError(null);
        viewAdresa.setError(null);

        boolean success = true;
        View focusView = null;

        // Store values at the time of the login attempt.
        String email = viewEmail.getText().toString();
        String password = viewPassword.getText().toString();
        mCredentials = new Pair<>(email, password);
        mUserDetails = new HashMap<>();

        // Check for valid email
        if (TextUtils.isEmpty(email)) {
            viewEmail.setError(getString(R.string.error_field_required));
            focusView = viewEmail;
            success = false;
        } else if (!AuthenticationHelper.isEmailValid(email)) {
            viewEmail.setError(getString(R.string.error_invalid_email));
            focusView = viewEmail;
            success = false;
        } else {
            mUserDetails.put("email", email);
        }

        // Check for valid username
        String username = viewUsername.getText().toString();
        if (TextUtils.isEmpty(username)) {
            viewUsername.setError(getString(R.string.error_field_required));
            focusView = viewUsername;
            success = false;
        } else if (!AuthenticationHelper.isUsernameValid(username)) {
            viewUsername.setError(getString(R.string.error_invalid_username));
            focusView = viewUsername;
            success = false;
        } else {
            mUserDetails.put("username", username);
        }

        // Check for valid age
        String ageRaw = viewAge.getText().toString();
        if (TextUtils.isEmpty(ageRaw)) {
            viewAge.setError(getString(R.string.error_field_required));
            focusView = viewAge;
            success = false;
        } else {
            try {
                int age = Integer.parseInt(ageRaw);
                if (!AuthenticationHelper.isAgeValid(age)) {
                    viewAge.setError(getString(R.string.error_invalid_age));
                    focusView = viewAge;
                    success = false;
                } else {
                    mUserDetails.put("age", String.valueOf(age));
                }
            } catch (NumberFormatException e) {
                success = false;
            }
        }

        // Check for valid datanastere
        String data_nastereRaw = viewDataNastere.getText().toString();
        if (TextUtils.isEmpty(data_nastereRaw)) {
            viewDataNastere.setError(getString(R.string.error_field_required));
            focusView = viewDataNastere;
            success = false;
        } else if (!AuthenticationHelper.isDataNastereValid(data_nastereRaw)) {
            viewDataNastere.setError(getString(R.string.error_invalid_data_nastere));
            focusView = viewDataNastere;
            success = false;
        } else {
            mUserDetails.put("data_nastere", data_nastereRaw);
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            viewPassword.setError(getString(R.string.error_field_required));
            focusView = viewPassword;
            success = false;
        } else if (!AuthenticationHelper.isPasswordValid(password)) {
            viewPassword.setError(getString(R.string.error_invalid_password));
            focusView = viewPassword;
            success = false;
        }

        // Check for valid phone
        String phoneRaw = viewPhone.getText().toString();
        if (TextUtils.isEmpty(phoneRaw)) {
            viewPhone.setError(getString(R.string.error_field_required));
            focusView = viewPhone;
            success = false;
        } else {
            if (!AuthenticationHelper.isPhoneValid(phoneRaw)) {
                viewPhone.setError(getString(R.string.error_invalid_phone));
                focusView = viewPhone;
                success = false;
            } else {
                mUserDetails.put("phone", phoneRaw);
            }
        }

        // Check for valid cnp
        String cnpRaw = viewCNP.getText().toString();
        if (TextUtils.isEmpty(cnpRaw)) {
            viewCNP.setError(getString(R.string.error_field_required));
            focusView = viewCNP;
            success = false;
        } else {
            if (!AuthenticationHelper.isCNPValid(cnpRaw)) {
                viewCNP.setError(getString(R.string.error_invalid_cnp));
                focusView = viewCNP;
                success = false;
            } else {
                mUserDetails.put("cnp", cnpRaw);
            }
        }

        // Check for valid nume
        String numeRaw = viewNume.getText().toString();
        if (TextUtils.isEmpty(numeRaw)) {
            viewNume.setError(getString(R.string.error_field_required));
            focusView = viewNume;
            success = false;
        } else {
            if (!AuthenticationHelper.isUsernameValid(numeRaw)) {
                viewNume.setError(getString(R.string.error_invalid_nume));
                focusView = viewNume;
                success = false;
            } else {
                mUserDetails.put("nume", numeRaw);
            }
        }

        // Check for valid prenume
        String prenumeRaw = viewPrenume.getText().toString();
        if (TextUtils.isEmpty(prenumeRaw)) {
            viewPrenume.setError(getString(R.string.error_field_required));
            focusView = viewPrenume;
            success = false;
        } else {
            if (!AuthenticationHelper.isUsernameValid(prenumeRaw)) {
                viewPrenume.setError(getString(R.string.error_invalid_prenume));
                focusView = viewPrenume;
                success = false;
            } else {
                mUserDetails.put("prenume", prenumeRaw);
            }
        }

        // Check for valid adresa
        String adresaRaw = viewAdresa.getText().toString();
        if (TextUtils.isEmpty(adresaRaw)) {
            viewAdresa.setError(getString(R.string.error_field_required));
            focusView = viewAdresa;
            success = false;
        } else {
            mUserDetails.put("adresa", adresaRaw);
        }

        return success;
    }

    @Override
    public void register(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final Pacient_User pacientUser = new Pacient_User();
                            String username = viewUsername.getText().toString();
                            int age = Integer.parseInt(viewAge.getText().toString());
                            String cnp = viewCNP.getText().toString();

                            String email = viewEmail.getText().toString();
                            int genSelectatId = viewGen.getCheckedRadioButtonId();
                            String gen = genSelectatId == R.id.radioButton1 ?
                                    getString(R.string.gen_masculin) :
                                    getString(R.string.gen_feminin);

                            String zi_de_nastere = viewDataNastere.getText().toString();
                            String nume = viewNume.getText().toString();
                            String prenume = viewPrenume.getText().toString();
                            String adresa = viewAdresa.getText().toString();
                            String nr_telefon = viewPhone.getText().toString();

                            pacientUser.setEmail(email);
                            pacientUser.setUsername(username);
                            pacientUser.setCnp(cnp);
                            pacientUser.setGen(gen);
                            pacientUser.setVarsta(age);
                            pacientUser.setZi_de_nastere(zi_de_nastere);
                            pacientUser.setNume(nume);
                            pacientUser.setPrenume(prenume);
                            pacientUser.setAdresa(adresa);
                            pacientUser.setNr_telefon(nr_telefon);
                            pacientUser.setHasCompletedQuiz(HAS_COMPLETED_QUIZ);
                            pacientUser.setHasRequiredInformation(HAS_REQUIRED_INFORMATION);
                            final String userId = task.getResult().getUser().getUid();

                            if (mDefaultAvatarChanged) {

                                final StorageReference reference = FirebaseStorage
                                        .getInstance()
                                        .getReference()
                                        .child(AuthenticationHelper.getUserAvatarPath(
                                                Pacient_RegisterActivity.this,
                                                userId)
                                        );

                                reference.putFile(mSelectedImageUri)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                pacientUser.setAvatarPath(
                                                        AuthenticationHelper.getUserAvatarPath(Pacient_RegisterActivity.this, userId)
                                                );
                                                addUserToDb(userId, pacientUser);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                ToastHelper.showToastWithImage(Pacient_RegisterActivity.this, getString(R.string.error_upload_failed), R.drawable.icon_error);
                                            }
                                        });

                            } else {
                                pacientUser.setAvatarPath(getString(R.string.default_avatar_path));
                                addUserToDb(userId, pacientUser);
                            }
                        } else {
                            ToastHelper.showToastWithImage(Pacient_RegisterActivity.this, getString(R.string.register_access_denied), R.drawable.icon_access_denied);
                            resetAnimation();
                            viewEmail.getText().clear();
                            viewPassword.getText().clear();
                        }
                    }
                });
    }

    private void addUserToDb(String id, final Pacient_User pacientUser) {
        Pacient_UserDao pacientUserDAO = new Pacient_UserDao(FirebaseFirestore.getInstance());
        Task<Void> task = pacientUserDAO.addUserToDb(id, pacientUser);

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                TipUtilizatorLogatHelper.seteazaTipUtilizatorLogat(Pacient_RegisterActivity.this, ETipUtilizator.PACIENT);
                ToastHelper.showToastWithImage(Pacient_RegisterActivity.this, getString(R.string.login_access_granted), R.drawable.icon_success);

                Intent formular1 = new Intent(Pacient_RegisterActivity.this, Pacient_Formular1Activity.class);
                startActivity(formular1);
            }
        });
    }
}