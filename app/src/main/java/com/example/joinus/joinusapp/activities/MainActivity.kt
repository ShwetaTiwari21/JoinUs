package com.example.joinus.joinusapp.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.joinus.joinusapp.Adapters.DrawerAdapter
import com.example.joinus.joinusapp.Adapters.EventAdapter
import com.example.joinus.joinusapp.MyApplication
import com.example.joinus.joinusapp.R
import com.example.joinus.joinusapp.models.DrawerItem
import com.example.joinus.joinusapp.models.GetAllPollResponse
import com.example.joinus.joinusapp.models.GetPollDataEvent
import com.example.joinus.joinusapp.models.PollEvent
import com.example.joinus.joinusapp.utils.AppUtils
import com.example.joinus.joinusapp.utils.Const
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

//    lateinit var eventBus:EventBus
    private lateinit var pollEventList:List<PollEvent>
    private lateinit var interestedEventList : List<PollEvent>

    lateinit var linearLayoutManager : LinearLayoutManager
    lateinit var eventAdapter: EventAdapter
    private lateinit var progressDialog: ProgressDialog


    lateinit var actionBarDrawerToggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }

        pollEventList = ArrayList()
        interestedEventList = ArrayList()
        setContentView(R.layout.activity_main)
        loadNavigationMenu()
        getAllPollData()

        iv_refresh.setOnClickListener {view->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }


    fun getAllPollData(){
        try {
//            if (AppUtils.isNetworkConnected(this)) {
//                progressDialog = ProgressDialog(this@MainActivity)
//                progressDialog.setMessage("Loading....")
//                progressDialog.show()
                AppUtils.showLoader(this@MainActivity)
                val username = AppUtils.getSharedPrefs(this).getString(Const.SHARED_PREF_USERNAME,"")
                val call = MyApplication.networkService.getPollData(username)
                call.enqueue(object : Callback<GetAllPollResponse> {
                    override fun onResponse(call: Call<GetAllPollResponse>, response: Response<GetAllPollResponse>?) {
//                        progressDialog.dismiss()
                        AppUtils.removeLoader(this@MainActivity)
                        if (response != null && response.body() != null && response.body().status.equals("OK")) {

                            pollEventList = pollEventList + response.body().expiring_soon + response.body().interested
                            setupRecyclerView()
                        }
                    }

                    override fun onFailure(call: Call<GetAllPollResponse>, t: Throwable) {
//                        progressDialog.dismiss()
                        AppUtils.removeLoader(this@MainActivity)
                        Log.e("errror", toString())
                        Toast.makeText(this@MainActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                    }
                })
//
        } catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
//        if (eventBus.isRegistered(this)){
//            eventBus.unregister(this)
//        }
    }

    fun openDetailActivity(event_id : String){
        val intent : Intent = Intent(this@MainActivity , EventDetailActivity::class.java)
        intent.putExtra(Const.EVENT_ID , event_id)
        startActivity(intent)
    }



    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        return menuInflater.inflate(R.menu.main , menu)
    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            when (item.itemId) {
                R.id.refresh_menu ->{
                    getAllPollData()
                    return true
                }
                else -> return super.onOptionsItemSelected(item)
            }
        }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        rl_main.setHasFixedSize(true)
        rl_main.layoutManager = linearLayoutManager
        eventAdapter = EventAdapter(this@MainActivity,pollEventList)
        rl_main.adapter = eventAdapter
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun loadNavigationMenu() {

        try {

            val drawerItems = getDrawerItems()
            val drawerAdapter = DrawerAdapter(this@MainActivity,
                    drawerItems, "English")
            lv_main_landing_drawer.setAdapter(drawerAdapter)

            actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity, drawer_layout, toolbar as Toolbar?,
                    R.string.app_name, R.string.app_name)
            drawer_layout.setDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()


            lv_main_landing_drawer.setOnItemClickListener { adapterView, view, pos, l ->
                try {
                    val drawerItem = drawerItems.get(pos)
                    when (drawerItem.id) {
                        Const.NAV_HOME_ID -> {
                            drawer_layout.closeDrawer(GravityCompat.START)
                        }
                        Const.NAV_COUPONS_ID -> try {
                            drawer_layout.closeDrawer(GravityCompat.START)
                            moveToCategoryActivity(Const.NAV_COUPONS_ID)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        Const.NAV_MOVIES_ID -> try {
                            drawer_layout.closeDrawer(GravityCompat.START)
                            moveToCategoryActivity(Const.NAV_MOVIES_ID)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        Const.NAV_MUNCHES_ID -> try {
                            drawer_layout.closeDrawer(GravityCompat.START)
                            moveToCategoryActivity(Const.NAV_MUNCHES_ID)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        Const.NAV_OTHERS_ID -> try {
                            drawer_layout.closeDrawer(GravityCompat.START)
                            moveToCategoryActivity(Const.NAV_OTHERS_ID)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        Const.NAV_SPORTS_ID -> try {
                            drawer_layout.closeDrawer(GravityCompat.START)
                            moveToCategoryActivity(Const.NAV_SPORTS_ID)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        Const.NAV_TRIPS_ID -> try {
                            drawer_layout.closeDrawer(GravityCompat.START)
                            moveToCategoryActivity(Const.NAV_TRIPS_ID)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun moveToCategoryActivity(cat_id : Int){
        val intent : Intent = Intent(this,CategoryDetailActivity::class.java)
        intent.putExtra(Const.CATEGORY_ID , cat_id.toString())
        startActivity(intent)
    }

    private fun getDrawerItems(): ArrayList<DrawerItem> {
        val drawerItems = ArrayList<DrawerItem>()
        drawerItems.add(DrawerItem(Const.NAV_HOME_ID, "Home", R.drawable.home_image, 0))
        drawerItems.add(DrawerItem(Const.NAV_SPORTS_ID, "Sports", R.drawable.sport_image, 0))
        drawerItems.add(DrawerItem(Const.NAV_MUNCHES_ID, "Munches", R.drawable.lunch_image, 0))
        drawerItems.add(DrawerItem(Const.NAV_TRIPS_ID, "Trips", R.drawable.trip_image, 0))
        drawerItems.add(DrawerItem(Const.NAV_COUPONS_ID, "Coupons", R.drawable.coupon_image, 0))
        drawerItems.add(DrawerItem(Const.NAV_MOVIES_ID, "Movies", R.drawable.movie_image, 0))
        drawerItems.add(DrawerItem(Const.NAV_OTHERS_ID, "Others", R.drawable.other_image, 0))
        return drawerItems
    }

    @Subscribe
    fun refreshData(getPollDataEvent: GetPollDataEvent){
        getAllPollData()
    }

}