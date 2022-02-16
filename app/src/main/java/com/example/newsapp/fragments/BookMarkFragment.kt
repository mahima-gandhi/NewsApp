package com.example.newsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.newsapp.R
import com.example.newsapp.adapters.BookMarkListAdapter
import com.example.newsapp.model.ArticlesItem
import com.example.newsapp.sqlLiteDatabaseModule.DatabaseHandler
import com.example.newsapp.viewModels.HomeViewModel

class BookMarkFragment : BaseFragment()  {

    @BindView(R.id.newsListRV)
    lateinit var newsListRV: RecyclerView


    //Initialize
    lateinit var mUnbinder: Unbinder
    lateinit var mNewsListAdapter: BookMarkListAdapter
    private var viewModel: HomeViewModel? = null
    var mNewsList: ArrayList<ArticlesItem?>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var mView = inflater.inflate(R.layout.book_mark_fragment, container, false)
        mUnbinder = ButterKnife.bind(this, mView)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewRecord(activity)
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
    //method for read records from database in ListView
    fun viewRecord(activity: FragmentActivity?) {
        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler = DatabaseHandler(activity!!)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val emp: ArrayList<ArticlesItem> = databaseHandler.showNews()
        val empArrayId = Array<String>(emp.size) { "0" }
        val empArrayDesc = Array<String>(emp.size) { "1" }
        val empArrayTitle = Array<String>(emp.size) { "2" }
        val empArrayImage= Array<String>(emp.size) { "3" }
        var index = 0
        for (e in emp) {
            empArrayId[index] = e.source?.id.toString()
            empArrayDesc[index] = e.description.toString()
            empArrayTitle[index] = e.title.toString()
            empArrayImage[index] = e.urlToImage.toString()
            index++
        }
            setNewsListAdapter(emp);
    }


    private fun setNewsListAdapter(emp: ArrayList<ArticlesItem>) {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        newsListRV.layoutManager = layoutManager
        mNewsListAdapter = BookMarkListAdapter(activity, emp)
        newsListRV.adapter = mNewsListAdapter
    }
}
