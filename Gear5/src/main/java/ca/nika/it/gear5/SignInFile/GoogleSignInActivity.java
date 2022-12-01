// CENG-322-0NC Francisco Santos n01423860, Pradeep Singh n00975892
// CENG-322-0NB Ralph Robes n01410324, Elijah Tanimowo n01433560
package ca.nika.it.gear5.SignInFile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.nika.it.gear5.LoginSetup.LoginActivity;
import ca.nika.it.gear5.LoginSetup.UserClass;
import ca.nika.it.gear5.MainActivity;
import ca.nika.it.gear5.R;

public class GoogleSignInActivity extends AppCompatActivity {
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.googleActivityLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        mAuth = FirebaseAuth.getInstance();

        signIn();

    }

    private void signIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);

                } catch (ApiException e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    returnLogin();
            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            validateUserGoogleEmailFireBase(user);
                        }else{
                            Toast.makeText(getApplicationContext(), R.string.failAuth, Toast.LENGTH_SHORT).show();
                            returnLogin();
                        }
                    }
                });
    }


    public void doSave(String googleId)  {
        SharedPreferences sharedPreferences= this.getSharedPreferences(getString(R.string.SettingsPref), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String googleLogin = "GearGoogleAccount";
        editor.putString("typeLogin", googleLogin);
        editor.putString(getString(R.string.userProfile), googleId);
        editor.apply();
    }


    private void validateUserGoogleEmailFireBase(FirebaseUser user) {
        String userGoogleID = user.getUid();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = db.child("users").child(userGoogleID);
        uidRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    enterMainActivity(userGoogleID);
                }else{
                    storeUserGoogleFirebase(user);
                }
            }
        });


    }

    private void storeUserGoogleFirebase(FirebaseUser user) {
        String userId = user.getUid();
        String username = user.getDisplayName();
        String password = "No PASSWORD";
        String email = "NO EMAIL";
        int startCurrency = 500;
        int startScore = 0;
        String phone = user.getPhoneNumber();
        FirebaseDatabase rootNode;
        DatabaseReference reference;
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference(getString(R.string.childRef_reg_regFrag));
        UserClass userClass = new UserClass(username, password, email, startCurrency, startScore, phone);
        reference.child(userId).setValue(userClass);
        enterMainActivity(userId);

    }

    private void returnLogin() {
        finish();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_login_from_startup, R.anim.exit_startup);
    }


    private void enterMainActivity(String googleId) {
        finish();

        doSave(googleId);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.exit_startup, R.anim.enter_login_from_startup);
    }
}