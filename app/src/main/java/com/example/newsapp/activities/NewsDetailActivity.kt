package com.example.newsapp.activities

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.letmelisten.app.utils.DESCRIPTION
import com.letmelisten.app.utils.IMAGE
import com.letmelisten.app.utils.TITLE
import com.letmelisten.app.utils.URL

class NewsDetailActivity : BaseActivity() {

    @BindView(R.id.titleTV)
    lateinit var titleTV: TextView

    @BindView(R.id.descriptionTV)
    lateinit var descriptionTV: TextView

    @BindView(R.id.linkTV)
    lateinit var linkTV: TextView

    @BindView(R.id.imgIV)
    lateinit var imgIV: ImageView

    @BindView(R.id.imgBackIV)
    lateinit var imgBackIV: ImageView

    //Initialize
    var url: String = ""
    var description: String = ""
    var strImage: String = ""
    var strTtile: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail2)
        ButterKnife.bind(this)
        setStatusBar(mActivity)
        linkTV.setMovementMethod(LinkMovementMethod.getInstance());
        setOnClick()
        getIntentData()
    }


    private fun getIntentData() {
        if (intent != null) {
            url = intent.getStringExtra(URL).toString()
            description = intent.getStringExtra(DESCRIPTION).toString()
            strImage = intent.getStringExtra(IMAGE).toString()
            strTtile = intent.getStringExtra(TITLE).toString()
            //Set Data
            titleTV.text = strTtile
            descriptionTV.text = description
            linkTV.text = url
            if (mActivity != null) {
                Glide.with(mActivity)
                    .load(strImage)
                    .into(imgIV)
            }
        }
    }


    private fun setOnClick() {
        imgBackIV.setOnClickListener()
        {
            onBackPressed()
        }
    }
}