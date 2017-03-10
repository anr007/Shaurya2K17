package red.shaurya2k17;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import red.shaurya2k17.NetworkTools.ConnectivityReceiver;

import static android.os.Build.VERSION_CODES.M;


public class UserLoginActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 100;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    ValueEventListener listener;
    SharedPreferences status;
    ProgressDialog mProgressDialog;
    EditText mEmailView;
    EditText mPasswordView;

    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseAuth mFirebaseAuth;
    GoogleApiClient mGoogleApiClient;
    Intent signInIntent;
    Context context;
    VideoView login_video;
    TextView admin_text;
    View email_login_form;
    View logo_view;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        email_login_form=findViewById(R.id.email_login_form);
        logo_view=findViewById(R.id.logo_view_login);


        login_video=(VideoView) findViewById(R.id.login_video);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.login);

        login_video.setDrawingCacheEnabled(true);
        login_video.setVideoURI(uri);
        login_video.requestFocus();
        login_video.start();
        login_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            //window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        }
        admin_text=(TextView)findViewById(R.id.admin_text_login);
        admin_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email_login_form.getVisibility()==View.VISIBLE) {
                    email_login_form.setVisibility(View.GONE);
                    logo_view.setVisibility(View.VISIBLE);
                }
                else {
                    email_login_form.setVisibility(View.VISIBLE);
                    logo_view.setVisibility(View.GONE);
                }
            }
        });



        context=this;


        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("users");
        mFirebaseAuth = FirebaseAuth.getInstance();





        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //google signin
        SignInButton signInButton = (SignInButton) findViewById(R.id.gin_but);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin(true);
            }
        });

        //normal signin
        mEmailView = (EditText) findViewById(R.id.email_usr_login);
        mPasswordView = (EditText) findViewById(R.id.pwd_usr_login);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.inbut_usr_login || id == EditorInfo.IME_NULL) {
                    attemptLogin(false);
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.inbut_usr_login);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(false);
            }
        });

        checkConnection();
        checkPlayServices();

    }



    private void attemptLogin(boolean gsignin) {


        if(gsignin) {

            if (Build.VERSION.SDK_INT >= M) {
                ActivityCompat.requestPermissions(UserLoginActivity.this,
                        new String[]{"android.permission.READ_CONTACTS",
                                "android.permission.READ_PHONE_STATE"},//see permission manager
                        1);
            } else {

                signInIntent = Auth.GoogleSignInApi
                        .getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        }
        else {

            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);

            // Store values at the time of the login attempt.
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (password.isEmpty()) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            } else if (!isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            } else if (!isEmailValid(email)) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                mFirebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    hideProgressDialog();
                                    status = context.getSharedPreferences("status", MODE_PRIVATE);
                                    status.edit().putBoolean("in",true).apply();
                                    Intent intent = new Intent(UserLoginActivity.this, Home.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    hideProgressDialog();
                                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                                    mPasswordView.requestFocus();
                                }
                            }
                        });
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                showProgressDialog("Validating User Credentials", "Loading..");
            }

        }
    }



    private boolean isEmailValid(String email) {

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        return password.length()>5 ;
    }




    //permission manager
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && ((grantResults[0] == PackageManager.PERMISSION_GRANTED))
                        &&(grantResults[1]== PackageManager.PERMISSION_GRANTED)){


                    signInIntent = Auth.GoogleSignInApi
                            .getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                    showProgressDialog(null,"Loading...");


                } else {

                    if(((grantResults[0] == PackageManager.PERMISSION_GRANTED))
                            &&(grantResults[1]== PackageManager.PERMISSION_GRANTED))
                    {
                        Toast.makeText(this,"Contacts read && Telephony Permission DENIED",Toast.LENGTH_SHORT)
                                .show();
                    }else if((grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Telephony Permission DENIED", Toast.LENGTH_SHORT)
                                .show();
                    }
                    else if((grantResults[1]== PackageManager.PERMISSION_GRANTED)){
                        Toast.makeText(this,"Contacts read Permission DENIED",Toast.LENGTH_SHORT)
                                .show();

                    }

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    // *****************************  google signin ***********************************************
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }


    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);

        } else {
            hideProgressDialog();
            Toast.makeText(this, "SignIn failed, Please try again", Toast.LENGTH_SHORT).show();
        }
    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful())
                        {


                            TelephonyManager tm = (TelephonyManager)getSystemService
                                    (Context.TELEPHONY_SERVICE);
                            final String device_id = tm.getDeviceId();
                            final String Uid=mFirebaseAuth.getCurrentUser().getUid();
                            final String email=mFirebaseAuth.getCurrentUser().getEmail();



                            listener=new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();

                                    Boolean key=false;
                                    while (iterator.hasNext()) {
                                        UserVerificationData value = iterator.next().
                                                getValue(UserVerificationData.class);
                                        if(value.getmDeviceId().equals(device_id))
                                        {
                                            if(value.getmEmail().equals(email))
                                            {
                                                status = context.getSharedPreferences("status", MODE_PRIVATE);
                                                status.edit().putBoolean("in",true).apply();
                                                hideProgressDialog();
                                                Intent intent = new Intent(UserLoginActivity.this, Home.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else {
                                                hideProgressDialog();
                                                Toast.makeText(UserLoginActivity.this,"Use "+
                                                        value.getmEmail()+
                                                        " to Login from this device",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                            key=true;
                                            break;
                                        }
                                    }
                                    if(!key)
                                    {
                                        final UserVerificationData newUser=new UserVerificationData();
                                        newUser.setmEmail(email);
                                        newUser.setmDeviceId(device_id);
                                        newUser.setmUid(Uid);
                                        mRef.child(Uid).setValue(newUser);
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    //Log.w(TAG, "Failed to read value.", error.toException());
                                }
                            };


                            mRef.addValueEventListener(listener);


                        }
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getBaseContext(),
                                    "SignIn failed, Please try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }






















    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        //Toast.makeText(this, "Connection failed, Please try again", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();

        /*
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog(null,"Loading...");
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }*/
    }


    private void showProgressDialog(String title, String message) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.setMessage(message);
        else
            mProgressDialog = ProgressDialog.show(this, title, message,true,true);
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }





    //connection checker
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        ConnectError(isConnected);
    }

    private void ConnectError(boolean isConnected)
    {
        if(!isConnected){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("  Connectivity Error");
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setMessage("Please Check Your Internet Connectivity");
            alertDialog.setIcon(R.drawable.ic_network_check_teal_500_48dp);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Exit",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    System.exit(0);
                }
            });
            alertDialog.show();
        }
    }



    public void playServicesErrorDialogue()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Google Play Services Error");
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage("Please Check Your Play Services");
        alertDialog.setIcon(R.drawable.ic_error_red_500_48dp);
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.exit(0);
            }

        });
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Dialog error= apiAvailability
                        .getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST );
                error.setCancelable(false);
                error.setCanceledOnTouchOutside(false);
                error.show();
            } else {
                playServicesErrorDialogue();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {

        if(listener!=null)
        mRef.removeEventListener(listener);
        super.onDestroy();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(!login_video.isPlaying())
        {
            login_video.start();
        }
    }
}

