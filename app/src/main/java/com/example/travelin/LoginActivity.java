package com.example.travelin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.security.Provider;

import javax.mail.*;
import javax.mail.internet.*;
//import javax.activation.*;



import static android.Manifest.permission.READ_CONTACTS;
import static java.lang.System.exit;
import static org.passay.AllowedCharacterRule.ERROR_CODE;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private Realm realm = null;
    private SyncConfiguration config = null;
    private SyncUser user;

    //extraa test
    private TextView forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        Realm.init(this);
        //RealmConfiguration config = new RealmConfiguration.Builder() //
        //        .name("travelin.realm") //
        //        .build();
        //Realm.setDefaultConfiguration(config);

//        String username=SyncUser.current().getIdentity();     //Get the username from intent here
        forget = findViewById(R.id.textView);
        forget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_log_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                //resetPassword("burns140@purdue.edu");
                //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(intent, 0);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            Bitmap bitmap;

            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                ImageView imageView = (ImageView)findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
*/

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        //this returns a map with all of the currently logged in users
        Map<String, SyncUser> map  = SyncUser.all();

        /**
         * this is here for debugging purposes. It logs out any users
         * that are currently logged in on this device. this functionality
         * will later be transferred to a logout button
         */
        if (map.size() != 0) {
            for (Map.Entry<String, SyncUser> entry : map.entrySet()) {
                entry.getValue().logOut();
            }
        }
        map = SyncUser.all();





        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

//        final String email = "burns140@purdue.edu";
//        final String password = "passwordTest";

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            mPasswordView.setError("Login failed: invalid email or password");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (!isEmailValid(email)) {
            mEmailView.setError("Login failed: invalid email or password");
            focusView = mEmailView;
            cancel = true;
        }



        /**
         * there will eventually be an if statement here, but for
         * debugging purposes, I have left it outside the if
         */
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            cancel = false;
        } else {
            showProgress(true);

            //this is the URL for our main server
            final String authURL = "https://travelin.us1a.cloud.realm.io";

            //credentials stores the username, email, and a createUser variable
            //if that value is true, it will create the user if it doesn't exist
            //which is used to create account
            //if it is false, it can only be used to login
            final SyncCredentials credentials = SyncCredentials.usernamePassword(email, password, true);

            /**
             * this creates a separate thread that allows the server to login while
             * other things go on with the UI
             * Doing this asynchronously straight up didn't work
             */
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean success = true;
                    try {
                        user = SyncUser.logIn(credentials, authURL);
                    } catch (Exception e) {
                        Context context = getApplicationContext();
                        CharSequence text = "Invalid username and password combo";
                        success = false;
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    if (success) {
                        String url = "realms://travelin.us1a.cloud.realm.io/travelin";

                        //this is supposed to create the realm for this user at our specific URL
                        config = user.createConfiguration(url).build();
                        //realm = Realm.getInstance(config);

                        /*RealmQuery<User> query = realm.where(User.class);
                        query.equalTo("email", email);
                        RealmResults<User> results = query.findAll();
                        User user = results.get(0);
                        realm.beginTransaction();
                        user.setPassword(password);
                        realm.commitTransaction();*/

                        /*realm.beginTransaction();
                        User user = realm.createObject(User.class, 42);
                        user.setEmail(email);
                        user.setPassword(password);
                        realm.commitTransaction();*/
                    }

                }
            });
            thread.start();
            while (true) {
                if (config != null) {
                    break;
                }
            }
            realm = Realm.getInstance(config);
            //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //startActivityForResult(intent, 0);

            RealmQuery<User> query = realm.where(User.class);
            query.equalTo("email", "h5@purdue.edu");
            RealmResults<User> results = query.findAll();

            for (User u : results) {
                System.out.println(u.getId());
            }

            exit(1);

            /*RealmQuery<User> query = realm.where(User.class);
            query.equalTo("email", email);
            RealmResults<User> results = query.findAll();
            User u = results.get(0);
            */

            //RealmList<byte[]> bimages = u.getImages();

            //for (byte[] i : bimages) {
            //    Bitmap bitmap= BitmapFactory.decodeByteArray(i,0,i.length);
            //}

/**            Random rand = new Random(10000);
            for (int i = 0; i < 10; i++) {
                realm.beginTransaction();
                User user1 = realm.createObject(User.class, rand.nextInt());
                user1.setAge(rand.nextInt());
                if (i % 2 == 0) {
                    user1.setGender("male");
                } else {
                    user1.setGender("female");
                }
                realm.commitTransaction();
            }
**/
/*
            RealmQuery<User> query = realm.where(User.class);
            query.equalTo("gender", "female");
            RealmResults<User> results = query.findAll();

            for (User u : results) {
                System.out.println("here " + u.getAge());
            }
            showProgress(false);

            */
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri targetUri = data.getData();
        Bitmap bitmap = null;

        if (resultCode == RESULT_OK) {

            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                //imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,10,stream);

        byte[] byteArray=stream.toByteArray();
        bitmap.recycle();

        for (int i = 0; i < 10; i++) {
            realm.beginTransaction();
            User user1 = realm.createObject(User.class, 334);
            user1.setEmail("burns140@purdue.edu");
            user1.setGender("female");
            realm.commitTransaction();
        }


        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("email", "burns140@purdue.edu");

        RealmResults<User> results = query.findAll();
        User user=results.get(0);

        byte[] returnArray=user.getImg();
        Bitmap breturn= BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

        imageView.setImageBitmap(breturn);




    }

    public void addImg(String email, Bitmap bitmap){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,10,stream);

        byte[] byteArray=stream.toByteArray();

        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("email", email);

        RealmResults<User> results = query.findAll();
        User user=results.get(0);

        realm.beginTransaction();
        user.setImg(byteArray);
        realm.commitTransaction();


    }
    /**
     * must be purdue email
     */
    private boolean isEmailValid(String email) {
        return email.matches(".*@purdue\\.edu");
    }


    /**
     * password requires a number, special character,
     * upper case letter, lower case letter,
     * must be longer than 4 characters and shorter than 32 characters
     */
    private boolean isPasswordValid(String password) {
        boolean containsNum;
        boolean correctLength = false;
        boolean containsSpecial = true;
        boolean containsUpper;
        boolean containsLower;

        containsNum = password.matches(".*[0-9].*");
        if ((password.length() > 4) && (password.length() < 32)) {
            correctLength = true;
        }
        if (password.matches("[a-zA-Z0-9]*")) {
            containsSpecial = false;
        }
        containsUpper = password.matches(".*[A-Z].*");
        containsLower = password.matches(".*[a-z].*");

        if ((containsNum == true) && (correctLength == true) && (containsSpecial == true)
                && (containsUpper == true) && (containsLower == true)) {
            return true;
        }
        return false;
    }


    public String generatePass() {
        // TODO: write algorithm to generate random password
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(1);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(1);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(1);

        CharacterData specialChars = new CharacterData() {
            @Override
            public String getErrorCode() {
                return ERROR_CODE;
            }

            @Override
            public String getCharacters() {
                return "!@#$%^&*_+=~`";
            }
        };

        CharacterRule specialCharRule = new CharacterRule(specialChars);
        specialCharRule.setNumberOfCharacters(1);

        Random rand = new Random();
        int n = rand.nextInt(27) + 5;

        String password = gen.generatePassword(n, lowerCaseRule, upperCaseRule, digitRule, specialCharRule);
        return password;
    }

    /**
     * sends email to user for resetting password
     * @param email
     */
    public void resetPassword(String email) {
        String url = "https://unbranded-metal-bacon.us1a.cloud.realm.io";
        SyncUser.requestPasswordResetAsync(email, url, new SyncUser.Callback<Void>() {
            @Override
            public void onSuccess(Void result) {

            }

            @Override
            public void onError(ObjectServerError error) {

            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    //@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }



    /**
     * TODO: move to correct class
     * returns all users whose gender matches the gender
     * in the filter
     */
    public RealmResults<User> genderFilter(String gender) {
        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("gender", gender);

        RealmResults<User> resultGender = query.findAll();
        return resultGender;
    }

    /**
     * TODO: move to correct class
     * returns the reviews for the user with a given username
     * @param username
     * @return
     */
    public RealmList<Post> reviewQuery(String username) {
        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("username", username);

        RealmResults<User> userReviews = query.findAll();
        return userReviews.get(0).getReviews();
    }

    public Bitmap getImg(String email){
        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("email", email);

        RealmResults<User> results = query.findAll();
        User user=results.get(0);

        byte[] byteArray=user.getImg();
        Bitmap bitmap= BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

        return bitmap;
    }

}





