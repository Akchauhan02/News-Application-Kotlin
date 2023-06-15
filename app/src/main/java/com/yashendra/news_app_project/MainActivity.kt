package com.yashendra.news_app_project

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yashendra.news_app_project.fragments.AboutAppFragment
import com.yashendra.news_app_project.fragments.Dashboard
import com.yashendra.news_app_project.fragments.ProfileFragment


class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorlayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationview: NavigationView
    var previousmenuitem: MenuItem?=null
    lateinit var tabLayout: TabLayout
    companion object{
        var currentselected:Int=0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout=findViewById(R.id.drawerLayout)
        coordinatorlayout=findViewById(R.id.coordinatorLayout)
        toolbar=findViewById(R.id.toolbar)
        frameLayout=findViewById(R.id.frame)
        navigationview=findViewById(R.id.navigationView)
        tabLayout=findViewById(R.id.include)
        setuptoolbar()
        opendasboard()

        val actionBarDrawerToggle= ActionBarDrawerToggle(this@MainActivity,drawerLayout,
            R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        actionBarDrawerToggle.syncState()

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){
                    0-> {
                        currentselected=0
                        opendasboard()
                        Toast.makeText(this@MainActivity, "home", Toast.LENGTH_SHORT).show()

                    }
                    1-> {
                        currentselected=1
                        opendasboard()
                        Toast.makeText(this@MainActivity, "sports", Toast.LENGTH_SHORT).show()
                    }
                    2-> {
                        currentselected=2
                        opendasboard()
                        Toast.makeText(this@MainActivity, "health", Toast.LENGTH_SHORT).show()
                    }
                    3-> {
                        currentselected=3
                        opendasboard()
                        Toast.makeText(this@MainActivity, "science ", Toast.LENGTH_SHORT).show()
                    }
                    4-> {
                        currentselected=4
                        opendasboard()
                        Toast.makeText(this@MainActivity, "entertainment ", Toast.LENGTH_SHORT).show()
                    }
                    5-> {
                        currentselected=5
                        opendasboard()
                        Toast.makeText(this@MainActivity, "technology", Toast.LENGTH_SHORT).show()
                    }

                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        navigationview.setNavigationItemSelectedListener{
            if (previousmenuitem!=null){
                previousmenuitem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousmenuitem=it

            when(it.itemId){
                R.id.dashboard ->{
//                    Toast.makeText(this, "dashboard", Toast.LENGTH_SHORT).show()
                    tabLayout.visibility = View.VISIBLE
                    opendasboard()
                    drawerLayout.closeDrawers()
                }
                R.id.exit ->{
//                    Toast.makeText(this, "favourites", Toast.LENGTH_SHORT).show()
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Exit")
                    builder.setMessage("Do you want to exit?")

                    builder.setPositiveButton("No") { dialog, which ->
                    }

                    builder.setNegativeButton("yes") { dialog, which ->
                        finish();
                        System.exit(0);
                    }
                    builder.show()


                }
                R.id.logOut ->{
//                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()

                    Firebase.auth.signOut()

                    val intent = Intent(this@MainActivity,LoginActivity::class.java)
                    startActivity(intent)
                }
                R.id.AboutApp ->{
//                    Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, AboutAppFragment())
//                        .addToBackStack("about app")
                        .commit()
                    supportActionBar?.title="About Us"
                    drawerLayout.closeDrawers()
                    tabLayout.visibility = View.GONE
                }
                R.id.profile ->{
//                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())
//                        .addToBackStack("about app")
                        .commit()
                    supportActionBar?.title="Profile"
                    drawerLayout.closeDrawers()
                    tabLayout.visibility = View.GONE
                }
            }
            return@setNavigationItemSelectedListener true
        }


    }
    fun setuptoolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="ToolBaar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }
    fun opendasboard(){
        val fragment= Dashboard()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,fragment)
        transaction.commit()
        supportActionBar?.title="DashBoard"
        navigationview.setCheckedItem(R.id.dashboard)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)

        }

        return super.onOptionsItemSelected(item)
    }

}
