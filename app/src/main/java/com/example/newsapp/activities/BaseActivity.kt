package com.example.newsapp.activities

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.SystemClock
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import java.util.*

open class BaseActivity : AppCompatActivity() {
    var progressDialog: Dialog? = null
    /*
 * Initialize Activity
 * */
    var mActivity: Activity = this@BaseActivity
    private var mLastClickTab1: Long = 0

    /*
Transparent status bar
 */
    fun setStatusBar(mActivity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mActivity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            // edited here
            mActivity.window.statusBarColor = resources.getColor(R.color.white)
        }
    }
    /*
    * Show Progress Dialog
    * */

    fun showProgressDialog(mActivity: Activity?) {
        progressDialog = Dialog(mActivity!!)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setContentView(R.layout.dialog_progress)
        Objects.requireNonNull(progressDialog!!.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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

    open fun preventMultipleClick() {
        // Preventing multiple clicks, using threshold of 1 second
        if (SystemClock.elapsedRealtime() - mLastClickTab1 < 2000) {
            return
        }
        mLastClickTab1 = SystemClock.elapsedRealtime()
    }
}