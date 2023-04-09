package com.example.knowway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.knowway.usersettings.UserFragment
import com.example.knowway.study.StudyFragment
import com.example.knowway.travel.TravelFragment
import com.example.knowway.upload.UploadFragment
import com.example.knowway.upload.ArticleWriter
import com.example.knowway.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    val fragmentList:MutableList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.titleBar))
        supportActionBar?.let{
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.menu_pic)
        }
        initNavigationView()
        initBottomNavigation()
    }

    private fun initNavigationView() {
        val navView = findViewById<NavigationView>(R.id.navView)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_divine -> {
                    Toast.makeText(this,"功能尚未实现哦",Toast.LENGTH_SHORT).show()
                    false
                }
                R.id.nav_changeTheme -> {
                    Toast.makeText(this,"功能尚未实现哦",Toast.LENGTH_SHORT).show()
                    false
                }
                R.id.nav_todayLuck -> {
                    Toast.makeText(this,"功能尚未实现哦",Toast.LENGTH_SHORT).show()
                    false
                }
                R.id.nav_uploadArticle -> {
                    val intent = Intent(this,ArticleWriter::class.java)
                    startActivity(intent)
                    false
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.title_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> findViewById<DrawerLayout>(R.id.mainContainer).openDrawer(GravityCompat.START)
        }
        return true
    }
    private fun initBottomNavigation(){
        fragmentList.add(StudyFragment.newInstance())
        fragmentList.add(UploadFragment.newInstance())
        fragmentList.add(TravelFragment.newInstance())
        fragmentList.add(UserFragment.newInstance())
        val bottom_nav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom_nav.itemIconTintList = null
        val viewPager2: ViewPager2 = findViewById(R.id.viewPage2)
        viewPager2.adapter = ViewPage2Adapter(supportFragmentManager,lifecycle,fragmentList)
        viewPager2.registerOnPageChangeCallback(
            object:ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position:Int){
                    super.onPageSelected(position)
                    bottom_nav.menu.getItem(position).isChecked = true
                }
            }
        )
        bottom_nav.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.studyArea -> {
                    viewPager2.setCurrentItem(0,true)
                    return@setOnItemSelectedListener true
                }
                R.id.uploadArea -> {
                    viewPager2.currentItem = 1
                    return@setOnItemSelectedListener true
                }
                R.id.travelArea -> {
                    viewPager2.currentItem = 2
                    return@setOnItemSelectedListener true
                }
                R.id.researchArea -> {
                    viewPager2.currentItem = 3
                    return@setOnItemSelectedListener true
                }
                else -> { return@setOnItemSelectedListener false }
            }
            false
        }
    }
}