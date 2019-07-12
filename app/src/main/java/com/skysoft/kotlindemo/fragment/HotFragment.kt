package com.skysoft.kotlindemo.fragment


import android.support.v7.widget.LinearLayoutManager
import com.ganxin.library.LoadDataLayout
import com.skysoft.kotlindemo.R
import com.skysoft.kotlindemo.adapter.HomeAdapter
import com.skysoft.kotlindemo.adapter.HotAdapter
import com.skysoft.kotlindemo.bean.HotBean
import com.skysoft.kotlindemo.bean.HotBeanData
import com.skysoft.kotlindemo.retrofit.ApiService
import com.skysoft.kotlindemo.retrofit.ConstantApi
import com.skysoft.kotlindemo.retrofit.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.smartrefresh_layout.*


class HotFragment : BaseFragment() {

    var hotList: MutableList<HotBeanData>? = ArrayList()
    var hotAdapter: HotAdapter? = null

    override fun setContentView(): Int {
        return R.layout.fragment_hot
    }

    override fun init() {
        loadDataLayout!!.status = LoadDataLayout.LOADING
        hotAdapter = HotAdapter(activity!!, hotList)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.adapter = hotAdapter
        smartRefreshLayout!!.setEnableLoadMore(false)
    }

    override fun lazyLoad() {
        getHoyData()
    }

    override fun setListern() {
        smartRefreshLayout!!.setOnRefreshListener {
            getHoyData()
        }
        loadDataLayout!!.setOnReloadListener { v, status ->
            loadDataLayout!!.status = LoadDataLayout.LOADING
        }
    }

    fun getHoyData() {
        RetrofitUtils.getAboutAndroidInstance(activity!!, ConstantApi.AboutAndroidUrlBase).create<ApiService>(ApiService::class.java)
                .getHotData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<HotBean> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(hotBean: HotBean) {
                        if (isFresh) {
                            hotList!!.clear()
                            smartRefreshLayout!!.finishRefresh(1000)
                            isFresh = false
                        }
                        loadDataLayout!!.status = LoadDataLayout.SUCCESS
                        val data = hotBean.data;
                        hotList!!.addAll(data!!)
                        hotAdapter!!.notifyDataSetChanged()

                    }

                    override fun onError(e: Throwable) {
                        loadDataLayout!!.status = LoadDataLayout.ERROR
                    }

                })
        // .subscribe(object :Observer<HotBean>{

        //  })
    }
}
