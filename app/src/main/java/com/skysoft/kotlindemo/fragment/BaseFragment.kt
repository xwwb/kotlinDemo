package com.skysoft.kotlindemo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ganxin.library.LoadDataLayout
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.skysoft.kotlindemo.R

/**
 * Created by apple on 17/11/30.
 */
abstract class BaseFragment : Fragment() {
    protected var rootView: View? = null
    var context = null
    private var isFirstLoad = false
    var isLoadMore: Boolean = false
    var isFresh: Boolean = false
    var page: Int = 1
    var size: Int = 30
    var toast: Toast? = null
    var recyclerView: RecyclerView? = null;
    var smartRefreshLayout: SmartRefreshLayout? = null
    var loadDataLayout: LoadDataLayout? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(setContentView(), container, false)
        }
        loadDataLayout = rootView!!.findViewById(R.id.loadDataLayout)
        smartRefreshLayout = rootView!!.findViewById(R.id.smartRefreshlayout)
        loadDataLayout!!.status = LoadDataLayout.LOADING
        recyclerView = rootView!!.findViewById(R.id.recycle_view)
        init()
        isFirstLoad = true//视图创建完成，将变量置为true
        if (userVisibleHint) {//如果Fragment可见进行数据加载
            lazyLoad()
            isFirstLoad = false
        }
        // lazyLoad()
        setListern()
        return rootView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见，获取该标志记录下来
        if (isFirstLoad && isVisibleToUser) {//视图变为可见并且是第一次加载
            lazyLoad()
            isFirstLoad = false
        }
    }


    /**
     * 加载页面布局文件
     */
    protected abstract fun setContentView(): Int

    /**
     * 让布局中的view与fragment中的变量建立起映射
     */
    protected abstract fun init()

    /**
     * 加载要显示的数据
     */
    protected abstract fun lazyLoad()

    /**
     * 设置监听
     */
    protected abstract fun setListern()

    override fun onDestroy() {
        super.onDestroy()
        isFirstLoad = false
    }

    /**
     * 显示短toast
     *
     * @param msg
     */
    fun toastShort(msg: String) {
        if (null == toast) {
            toast = Toast(activity)
            toast!!.duration = Toast.LENGTH_SHORT
            toast!!.setText(msg)
            toast!!.show()
        } else {
            toast!!.setText(msg)
            toast!!.show()
        }
    }
}
