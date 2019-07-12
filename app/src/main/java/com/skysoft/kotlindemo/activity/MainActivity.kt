package com.skysoft.kotlindemo.activity

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.skysoft.kotlindemo.R
import com.skysoft.kotlindemo.fragment.BelleFragment
import com.skysoft.kotlindemo.fragment.HomeFragment
import com.skysoft.kotlindemo.fragment.HotFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    var mFragments: MutableList<Fragment>? = null
    var hotFragment: HotFragment = HotFragment()
    var homeFragment: HomeFragment = HomeFragment()
    var belleFragment: BelleFragment = BelleFragment()
    var lastIndex: Int = 0
    var bottomNavigationView:BottomNavigationView?=null
 //git 提交

    override fun initView() {
        setStatusBar(this)
        bottomNavigationView=findViewById(R.id.bottomNavigationView)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(this)

    }

    override fun setListern() {

    }

    override fun initData() {
        mFragments = ArrayList()
        mFragments!!.add(homeFragment)
        mFragments!!.add(belleFragment)
        mFragments!!.add(hotFragment)
        title_tv.text = resources.getString(R.string.home)
        setFragmentPosition(0)
    }


    fun setFragmentPosition(position: Int?) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        val currentFragment = mFragments!!.get(position!!)
        val lastFragment = mFragments!!.get(lastIndex);
        lastIndex=position
        beginTransaction.hide(lastFragment)
        if (!currentFragment.isAdded) {
            supportFragmentManager.beginTransaction().remove(currentFragment).commit()
            beginTransaction.add(R.id.framePage, currentFragment)
        }
        beginTransaction.show(currentFragment)
        beginTransaction.commitAllowingStateLoss()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.navigation_home -> {
              setFragmentPosition(0)
                title_tv.text = resources.getString(R.string.home)
            }
            R.id.navigation_gril -> {
                 setFragmentPosition(1)
                title_tv.text = resources.getString(R.string.belle)
            }
            R.id.navigation_hot -> {
                 setFragmentPosition(2)
                title_tv.text = resources.getString(R.string.hot)
            }
        }
        return true;
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}
