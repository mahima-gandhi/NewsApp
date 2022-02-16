package com.example.newsapp.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsListAdapter
import com.example.newsapp.interfaces.ItemClickInterface
import com.example.newsapp.interfaces.PaginationRequestInterface
import com.example.newsapp.model.ArticlesItem
import com.example.newsapp.model.HomeNewsModel
import com.example.newsapp.sqlLiteDatabaseModule.DatabaseHandler
import com.example.newsapp.viewModels.HomeViewModel

class HomeFragment : BaseFragment(), ItemClickInterface {

    @BindView(R.id.newsListRV)
    lateinit var newsListRV: RecyclerView

    @BindView(R.id.mProgressBar)
    lateinit var mProgressBar: ProgressBar

    //Initialize
    lateinit var mUnbinder: Unbinder
    lateinit var mNewsListAdapter: NewsListAdapter
    private var viewModel: HomeViewModel? = null
    var mItemClickInterface: ItemClickInterface? = null

    var mNewsList: ArrayList<ArticlesItem?>? = ArrayList()
    var mPageNo: Int = 1
    var pageSize: Int = 20
    private var strLastPage = "FALSE"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var mView = inflater.inflate(R.layout.fragment_home, container, false)
        mUnbinder = ButterKnife.bind(this, mView)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        newsListRV = mView.findViewById(R.id.newsListRV)
        mItemClickInterface = this
        executeHomeRequest()
        return mView
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mUnbinder != null) {
            if (this::mUnbinder.isInitialized) {
                mUnbinder.unbind()
            }
        }
    }

    private fun executeHomeRequest() {
        if (mPageNo == 1) {
            showProgressDialog(activity)
        } else if (mPageNo > 1) {
            mProgressBar.visibility = View.GONE
        }

        activity?.let {
            viewModel?.getHomeData(
                activity,
                "Apple",
                "2022-02-15",
                "popularity",
                mPageNo,
                pageSize,
                "f250a6f087d649ae96baf9923c9be1a2"

            )?.observe(
                it,
                androidx.lifecycle.Observer<HomeNewsModel?> { mHomeModel ->
                    if (mHomeModel.status.equals("ok")) {
                        if (mPageNo == 1) {
                            dismissProgressDialog()
                        }
                        if (mHomeModel.totalResults!! > 0) {
                            strLastPage =
                                if (mHomeModel.articles == null || mHomeModel.articles.isEmpty() || mHomeModel.articles.size == 0) {
                                    "TRUE"
                                } else if (mHomeModel.articles.size < pageSize) {
                                    "TRUE"
                                } else {
                                    "FALSE"
                                }

                            var mTemporaryList: ArrayList<ArticlesItem?>? = ArrayList()

                            if (mHomeModel?.articles != null) {
                                if (mPageNo == 1) {
                                    mNewsList = mHomeModel.articles
                                } else if (mPageNo > 1) {
                                    mTemporaryList = mHomeModel.articles
                                }

                                if (mTemporaryList?.size!! > 1) {
                                    mNewsList?.addAll(mTemporaryList)
                                }

                                if (mPageNo == 1) {
                                    setNewsListAdapter()
                                } else {
                                    mNewsListAdapter.notifyDataSetChanged()
                                }
                            }
                        }

                    } else {
                        dismissProgressDialog()
                        showToast(activity, mHomeModel.status)
                    }

                })
        }
    }


    private fun setNewsListAdapter() {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        newsListRV.layoutManager = layoutManager
        mNewsListAdapter =
            NewsListAdapter(activity, mNewsList, paginationRequestInterface, mItemClickInterface)
        newsListRV.adapter = mNewsListAdapter
    }


    /**
     * interface used for pagination
     */
    private var paginationRequestInterface: PaginationRequestInterface =
        object : PaginationRequestInterface {
            override fun mPaginationRequestInterface(isLastScrolled: Boolean) {
                if (isLastScrolled) {
                    if (strLastPage == "FALSE") {
                        mProgressBar.visibility = View.VISIBLE
                    }

                    ++mPageNo
                    Handler().postDelayed({
                        if (strLastPage == "FALSE") {
                            executeHomeRequest()
                        } else {
                            mProgressBar.visibility = View.GONE
                        }
                    }, 1000)
                }
            }
        }

    //Recycleview itemClick ListenerŌ
    override fun onItemClick(mModel: ArticlesItem?) {
        saveRecord(mModel, activity)
    }

}

//method for saving records in database
fun saveRecord(mModel: ArticlesItem?, activity: FragmentActivity?) {
    val id = mModel?.source?.id
    val des = mModel?.description
    val title = mModel?.title
    val databaseHandler: DatabaseHandler = activity?.let { DatabaseHandler(it) }!!
    if (id?.trim() != "" && des?.trim() != "" && title?.trim() != "") {
        var articlesItem = ArticlesItem()
        articlesItem.title = mModel?.title
        articlesItem.description = mModel?.description
        articlesItem.source?.id = mModel?.source?.id
        val status = databaseHandler.addNews(articlesItem)
        if (status > -1) {
            Toast.makeText(activity, "Bookmarked!!!", Toast.LENGTH_LONG).show()
        }
    } else {
        Toast.makeText(activity, "error", Toast.LENGTH_LONG).show()
    }


}
