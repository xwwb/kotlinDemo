package com.skysoft.kotlindemo.fragment


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ganxin.library.LoadDataLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.skysoft.kotlindemo.R
import com.skysoft.kotlindemo.adapter.HomeAdapter
import com.skysoft.kotlindemo.adapter.OtherAdapter
import com.skysoft.kotlindemo.bean.Item
import com.skysoft.kotlindemo.bean.OtherBean
import com.skysoft.kotlindemo.retrofit.ApiService
import com.skysoft.kotlindemo.retrofit.RetrofitUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.http.PATCH


/**
 * A simple [Fragment] subclass.
 *
 */
class OtherFragment : BaseFragment(), LoadDataLayout.OnReloadListener, OnRefreshListener, BaseQuickAdapter.OnItemClickListener, OnLoadMoreListener {

    var data:MutableList<Item>?= ArrayList()
    var otherAdapter: OtherAdapter? = null
    override fun init() {
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        otherAdapter = OtherAdapter(activity!!, data!!)
        recyclerView!!.adapter = otherAdapter
    }

    override fun lazyLoad() {
        size=5
        getData(page)

    }

    override fun setListern() {
        loadDataLayout!!.setOnReloadListener(this)
        smartRefreshLayout!!.setOnRefreshListener(this)
        smartRefreshLayout!!.setOnLoadMoreListener(this)
        otherAdapter!!.onItemClickListener = this
    }
    override fun setContentView(): Int {
        return R.layout.fragment_other
    }
    override fun onLoadMore(refreshLayout: RefreshLayout) {
        isFresh = false
        isLoadMore = true
        page++
        getData(page)

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 1
        isLoadMore = false
        isFresh = true
        getData(page)
    }

    override fun onReload(v: View?, status: Int) {
        page = 1
        isLoadMore = false
        isFresh = true
        getData(page)
    }

 fun getData(page:Int){

     RetrofitUtils.getOtherInstance(activity!!).create(ApiService::class.java).getOther(page,true,size).subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(object :Observer<OtherBean>{
                 override fun onComplete() {

                 }

                 override fun onSubscribe(d: Disposable) {

                 }

                 override fun onNext(otherBean: OtherBean) {
                     if (isFresh) {
                         smartRefreshLayout!!.finishRefresh(500)
                         data!!.clear()
                     }
                     if (isLoadMore) {
                         smartRefreshLayout!!.finishLoadMore(500)
                     }
                     loadDataLayout!!.status = LoadDataLayout.SUCCESS
                     val adExist = otherBean.adExist
                     val itemList = otherBean.itemList
                     data?.addAll(itemList)
                     val nextPageUrl = otherBean.nextPageUrl
                     otherAdapter?.notifyDataSetChanged()
                     Log.i("OtherFragment",""+otherBean.count+"====nextPageUrl==="+nextPageUrl)
                     if (adExist){
                     }else{

                     }

                 }

                 override fun onError(e: Throwable) {
                     loadDataLayout!!.status = LoadDataLayout.ERROR
                 }

             })
 }
}
