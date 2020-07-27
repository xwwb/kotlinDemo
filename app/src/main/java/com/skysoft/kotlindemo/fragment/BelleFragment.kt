package com.skysoft.kotlindemo.fragment


import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ganxin.library.LoadDataLayout
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.skysoft.kotlindemo.R
import com.skysoft.kotlindemo.activity.PictureActivity
import com.skysoft.kotlindemo.adapter.BelleAdapter
import com.skysoft.kotlindemo.bean.*
import com.skysoft.kotlindemo.retrofit.ApiService
import com.skysoft.kotlindemo.retrofit.ConstantApi
import com.skysoft.kotlindemo.retrofit.RetrofitUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 *
 */
class BelleFragment : BaseFragment(), OnRefreshListener, OnLoadMoreListener, LoadDataLayout.OnReloadListener, BaseQuickAdapter.OnItemClickListener {


    var belleAdapter: BelleAdapter? = null
    var belleList: MutableList<GankModelData>? = ArrayList()
    val TAG: String = "====BaseFragment"


    override fun setContentView(): Int {
        return R.layout.fragment_belle
    }

    override fun init() {
        Log.i("====", "====init2")
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        belleAdapter = BelleAdapter(this.activity!!, R.layout.belle_adapter_item, belleList)
        recyclerView!!.adapter = belleAdapter

    }

    override fun lazyLoad() {
        Log.i("====", "====lazyLoad2")
        getBelleData(page, size)

    }

    private fun getBelleData(page: Int?, size: Int?) {
        RetrofitUtils.getInstance(activity!!, ConstantApi.GankUrl)!!.create(ApiService::class.java).getPicture( page!!, size!!).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GankModel> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(gankModel: GankModel) {
                        Log.i(TAG, "====onNext")
                        loadDataLayout!!.status = LoadDataLayout.SUCCESS
                        if (isFresh) {
                            belleList!!.clear()
                            smartRefreshLayout!!.finishRefresh(100)
                        }
                        if (isLoadMore) {
                            smartRefreshLayout!!.finishLoadMore(1000)
                        }
                        val data = gankModel.data
                        belleList!!.addAll(data)
                        Log.i("======", "" + belleList!!.size)
                        belleAdapter?.notifyDataSetChanged()
                    }

                    override fun onError(e: Throwable) {
                        loadDataLayout!!.status = LoadDataLayout.ERROR
                    }
                }
                )
//        RetrofitUtils.getInstance(this.activity!!, ConstantApi.BaiDuPictureUrl)!!.create<ApiService>(ApiService::class.java).getPicture(page!!,size!!,"摄影","全部","高清","utf8")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Observer<PictureBean> {
//                    override fun onComplete() {
//                    }
//
//                    override fun onSubscribe(d: Disposable) {
//
//                    }
//
//                    override fun onNext(pictureBean: PictureBean) {
//                        Log.i(TAG, "====onNext")
//                        loadDataLayout!!.status = LoadDataLayout.SUCCESS
//                        if (isFresh) {
//                            belleList!!.clear()
//                            smartRefreshLayout!!.finishRefresh(100)
//                        }
//                        if (isLoadMore) {
//                            smartRefreshLayout!!.finishLoadMore(1000)
//                        }
//                        val data = pictureBean.data
//                        belleList!!.addAll(data)
//                        Log.i("======",""+belleList!!.size)
//                        belleAdapter?.notifyDataSetChanged()
//                    }
//
//                    override fun onError(e: Throwable) {
//                        Log.i("====", "====onError$e")
//                        loadDataLayout!!.status = LoadDataLayout.ERROR
//                    }
//                })
    }


    override fun setListern() {
        loadDataLayout!!.setOnReloadListener(this)
        smartRefreshLayout!!.setOnRefreshListener(this)
        smartRefreshLayout!!.setOnLoadMoreListener(this)
        belleAdapter!!.onItemClickListener = this
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 1
        isLoadMore = false
        isFresh = true
        getBelleData(page, size)

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        isLoadMore = true
        page++
        isFresh = false
        getBelleData(page, size)
    }

    override fun onReload(v: View?, status: Int) {
        page = 1
        loadDataLayout!!.status = LoadDataLayout.LOADING
        loadDataLayout!!.postDelayed({ getBelleData(page, size) }, 500)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val download_url = belleList!![position].url
        val id = belleList!![position]._id
        val share_url = belleList!![position].url
        val image_url = belleList!![position].url
        val intent = Intent(activity, PictureActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("download_url", download_url)
        intent.putExtra("share_url", share_url)
        intent.putExtra("image_url", image_url)
        startIntent(view!!, intent, activity!!)
    }

    private fun startIntent(view: View, intent: Intent, activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions
                    .makeSceneTransitionAnimation(activity, view, getString(R.string.image))
            startActivity(intent, options.toBundle())
        } else {
            val options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.width / 2, view.height / 2, 0, 0)
            ActivityCompat.startActivity(activity, intent, options.toBundle())
        }

    }
}
