package com.example.newsapp.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.SystemClock
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import java.util.*

open class BaseFragment: Fragment() {
    private var mLastClickTab1: Long = 0
    var progressDialog: Dialog? = null
    /*
       * Get Class Name
       * */
    var TAG = this@BaseFragment.javaClass.simpleName

    open fun preventMultipleViewClick() {
        // Preventing multiple clicks, using threshold of 1 second
        if (SystemClock.elapsedRealtime() - mLastClickTab1 < 2000) {
            return
        }
        mLastClickTab1 = SystemClock.elapsedRealtime()
    }

    /*
     * Check Internet Connections
     * */
    fun isNetworkAvailable(mContext: Context): Boolean {
        val connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    /*
     * Toast Message
     * */
    fun showToast(mActivity: Activity?, strMessage: String?) {
        Toast.makeText(mActivity, strMessage, Toast.LENGTH_SHORT).show()
    }

    /*
     * Show Progress Dialog
     * */
    fun  showProgressDialog(mActivity: Activity?) {
        progressDialog = Dialog(mActivity!!)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setContentView(R.layout.dialog_progress)
        Objects.requireNonNull(progressDialog!!.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.setCancelable(false)
        if (progressDialog != null) progressDialog!!.show()
    }

    /*
     * Hide Progress Dialog
     * */
    fun dismissProgressDialog() {
        try {
            if (this.progressDialog != null && this.progressDialog!!.isShowing()) {
                this.progressDialog!!.dismiss()
            }
        } catch (e: IllegalArgumentException) {
            // Handle or log or ignore
        } catch (e: Exception) {
            // Handle or log or ignore
        } finally {
            this.progressDialog = null
        }
    }
}