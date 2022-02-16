package com.example.newsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.newsapp.R
import com.example.newsapp.fragments.BookMarkFragment
import com.example.newsapp.fragments.HomeFragment

class HomeActivity : BaseActivity() {

    @BindView(R.id.bookmarkRL)
    lateinit var bookmarkRL: LinearLayout

    @BindView(R.id.homeRL)
    lateinit var homeRL: LinearLayout

    @BindView(R.id.imgHomeIV)
    lateinit var imgHomeIV: ImageView

    @BindView(R.id.bookMarkIV)
    lateinit var bookMarkIV: ImageView

    @BindView(R.id.homeTV)
    lateinit var homeTV: TextView

    @BindView(R.id.bookmarkTV)
    lateinit var bookmarkTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)
        setStatusBar(mActivity)
        //By Default Home Click
        performHomeClick()
    }


    @OnClick(
        R.id.homeRL,
        R.id.bookmarkRL,
    )
    fun onClick(view: View) {
        when (view.id) {
            R.id.homeRL -> performHomeClick()
            R.id.bookmarkRL -> performBookMarkClick()
        }
    }

    private fun performBookMarkClick() {
        bookMarkIV.setColorFilter(mActivity.getResources().getColor(R.color.blue))
        bookmarkTV.setTextColor(mActivity.getResources().getColor(R.color.blue))
        imgHomeIV.setColorFilter(mActivity.getResources().getColor(R.color.black))
        homeTV.setTextColor(mActivity.getResources().getColor(R.color.black))
        switchFragment(BookMarkFragment(), "", false, null)
    }
    private fun performHomeClick() {
        imgHomeIV.setColorFilter(mActivity.getResources().getColor(R.color.blue))
        homeTV.setTextColor(mActivity.getResources().getColor(R.color.blue))
        bookMarkIV.setColorFilter(mActivity.getResources().getColor(R.color.black))
        bookmarkTV.setTextColor(mActivity.getResources().getColor(R.color.black))
        switchFragment(HomeFragment(), "", false, null)
    }
    /*Switch between fragments*/
    fun switchFragment(
        fragment: Fragment?,
        Tag: String?,
        addToStack: Boolean,
        bundle: Bundle?
    ) {
        val fragmentManager = supportFragmentManager
        if (fragment != null) {
            val fragmentTransaction =
                fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.mainFL, fragment, Tag)
            if (addToStack) fragmentTransaction.addToBackStack(Tag)
            if (bundle != null) fragment.arguments = bundle
            fragmentTransaction.commit()
            fragmentManager.executePendingTransactions()
        }
    }
}