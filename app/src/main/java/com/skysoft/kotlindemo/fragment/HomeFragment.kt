package com.skysoft.kotlindemo.fragment


import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.ganxin.library.LoadDataLayout
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.skysoft.kotlindemo.R
import com.skysoft.kotlindemo.Util.BannerViewUtils
import com.skysoft.kotlindemo.Util.PicassoImage
import com.skysoft.kotlindemo.activity.WanAndroidDetailsActivity
import com.skysoft.kotlindemo.adapter.HomeAdapter
import com.skysoft.kotlindemo.bean.HomeBean
import com.skysoft.kotlindemo.bean.HomeBeanDataX
import com.skysoft.kotlindemo.bean.WanAndroidBeanData
import com.skysoft.kotlindemo.bean.WanAndroidBena
import com.skysoft.kotlindemo.retrofit.ApiService
import com.skysoft.kotlindemo.retrofit.ConstantApi
import com.skysoft.kotlindemo.retrofit.RetrofitUtils
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class HomeFragment : BaseFragment() {

    var bannerUrlMutablePathList: MutableList<String>? = null
    var bannerUrlMutableTitleList: MutableList<String>? = null
    var data: MutableList<HomeBeanDataX>? = ArrayList()
    var homeAdapter: HomeAdapter? = null
    var banner: Banner? = null
    var inflate: View? = null
    var bannerDataList: List<WanAndroidBeanData>? = null
//    var banner:Banner?=null


    override fun setContentView(): Int {
        return R.layout.fragment_home
    }

    override fun init() {

        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        homeAdapter = HomeAdapter(activity!!, R.layout.home_adapter_item_, data)
        inflate = LayoutInflater.from(activity).inflate(R.layout.banner_layout, null)
        banner = inflate!!.findViewById(R.id.bannerView)
        homeAdapter!!.addHeaderView(inflate)
        recyclerView!!.adapter = homeAdapter
    }

    override fun lazyLoad() {
        bannerUrlMutablePathList = ArrayList()
        bannerUrlMutableTitleList = ArrayList()
        getBannerData()
        getHomeData(page)

    }

    override fun setListern() {
        smartRefreshLayout!!.setOnRefreshListener {
            isFresh = true
            isLoadMore = false
            page = 1
            getHomeData(page)
        }
        smartRefreshLayout!!.setOnLoadMoreListener {
            isFresh = false
            isLoadMore = true
            page++
            getHomeData(page)
        }
        loadDataLayout!!.setOnReloadListener { v, status ->
            loadDataLayout!!.status = LoadDataLayout.LOADING
            loadDataLayout!!.postDelayed({ getHomeData(page) }, 500)
        }
        homeAdapter!!.setOnItemClickListener { adapter, view, position ->
            val link = data!![position].link
            Log.i("=====", "" + link)
            val intent = Intent(activity, WanAndroidDetailsActivity::class.java)
            intent.putExtra("url", link)
            intent.putExtra("title", data!![position].title)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
            }
        }
        banner!!.setOnBannerListener {
            val url = bannerDataList!![it].url
            val title = bannerDataList!![it].title
            val intent = Intent(activity, WanAndroidDetailsActivity::class.java)
            intent.putExtra("url", url)
            intent.putExtra("title", title
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
            }
        }

    }

    fun getBannerData() {
        RetrofitUtils.getAboutAndroidInstance(activity!!, ConstantApi.AboutAndroidUrlBase).create<ApiService>(ApiService::class.java)
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<WanAndroidBena> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(wanAndroidBena: WanAndroidBena) {
                        bannerDataList = wanAndroidBena.data
                        for (i in bannerDataList!!.indices) {
                            val title = bannerDataList!![i].title
                            val imagePath = bannerDataList!![i].imagePath
                            bannerUrlMutablePathList!!.add(imagePath)
                            bannerUrlMutableTitleList!!.add(title)
                        }
                        BannerViewUtils.setBanner(banner!!, bannerUrlMutablePathList!!, bannerUrlMutableTitleList!!)

                    }

                    override fun onError(e: Throwable) {

                    }

                })
    }

    fun getHomeData(page: Int?) {
        RetrofitUtils.getAboutAndroidInstance(activity!!, ConstantApi.AboutAndroidUrlBase).create<ApiService>(ApiService::class.java)
                .getHomeData(page!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeBean> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(homeBean: HomeBean) {
                        if (isFresh) {
                            smartRefreshLayout!!.finishRefresh(500)
                            data!!.clear()
                        }
                        if (isLoadMore) {
                            smartRefreshLayout!!.finishLoadMore(500)
                        }
                        loadDataLayout!!.status = LoadDataLayout.SUCCESS
                        val homeBeanData = homeBean.data;
                        val datas = homeBeanData.datas
                        data!!.addAll(datas)
                        Log.i("======",""+data!!.size)
                        homeAdapter!!.notifyDataSetChanged()

                    }

                    override fun onError(e: Throwable) {
                        loadDataLayout!!.status = LoadDataLayout.ERROR
                    }

                })
    }
}
