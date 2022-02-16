package com.example.newsapp.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.newsapp.R
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

class LogInActivity : BaseActivity() {

    @BindView(R.id.googleRL)
    lateinit var googleRL: RelativeLayout

    //Initialize
    var pro_pic = ""
    var google_name: String? = null
    var id: String? = null
    var first_name: String? = null
    var last_name: String? = null
    var email: String? = null
    var mGoogleSignInClient: GoogleSignInClient? = null
    protected val GOOGLE_SIGN_IN = 12324
    private var mFirebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        ButterKnife.bind(this)
        initializeGoogle()
        setStatusBar(mActivity)
    }
    @OnClick(
        R.id.googleRL,)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.googleRL -> performGoogleClick()

        }
    }
    private fun performGoogleClick() {
        preventMultipleClick()
        val signInIntent: Intent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    private fun initializeGoogle() {
        FirebaseApp.initializeApp(this)
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mFirebaseAuth = FirebaseAuth.getInstance()
        FirebaseAuth.getInstance().signOut()
        signOut()
    }
    private fun signOut() {
        if (mGoogleSignInClient != null) {
            mGoogleSignInClient!!.signOut()
                .addOnCompleteListener(this) {
                    Log.e("TAG", "==Logout Successfully==")
                }
        }
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GOOGLE_SIGN_IN -> {
                val completedTask: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(completedTask)
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            email = account!!.email
            id = account.id
            google_name = account.displayName

            val name = account.displayName

            val idx = name!!.lastIndexOf(' ')
            if (idx == -1) {
                return
            }

            first_name = name.substring(0, idx)
            last_name = name.substring(idx + 1)
            Log.e("SPLITED NAME", first_name + " - " + last_name)

            pro_pic = if (account.photoUrl != null) {
                account.photoUrl.toString()
            } else {
                ""
            }
            executeGoogleLogin()
        } catch (e: ApiException) {
            Log.e("MyTAG", "signInResult:failed code=" + e.statusCode)
            //showToast(mActivity, "Failed")
        }
    }

    private fun executeGoogleLogin() {
        val mIntent = Intent(mActivity, HomeActivity::class.java)
        startActivity(mIntent)
        finish()
    }

}