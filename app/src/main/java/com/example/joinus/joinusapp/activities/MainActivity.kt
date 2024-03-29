package com.example.joinus.joinusapp.activities

import android.animation.Animator
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.joinus.joinusapp.Adapters.DrawerAdapter
import com.example.joinus.joinusapp.Adapters.EventAdapter
import com.example.joinus.joinusapp.MyApplication
import com.example.joinus.joinusapp.R
import com.example.joinus.joinusapp.utils.AppUtils
import com.example.joinus.joinusapp.utils.Const
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import android.animation.AnimatorListenerAdapter
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import com.example.joinus.joinusapp.R.id.fab
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.view.*
import kotlinx.android.synthetic.main.event_layout.*
import android.view.KeyEvent.KEYCODE_BACK
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.DatePicker
//import com.example.joinus.joinusapp.adapters.HintAdapter
import com.example.joinus.joinusapp.models.*
import com.example.joinus.joinusapp.network.HeaderGenerator
//import com.example.joinus.joinusapp.utils.HintSpinner
import kotlinx.android.synthetic.main.dialog_layout.*
import kotlinx.android.synthetic.main.dialog_layout.view.*
import java.text.SimpleDateFormat
import javax.xml.datatype.DatatypeConstants.MONTHS


class MainActivity : AppCompatActivity() {

    lateinit var eventBus:EventBus
    private lateinit var openPollEventList:List<PollEvent>
    lateinit var linearLayoutManager : LinearLayoutManager
    lateinit var eventAdapter: EventAdapter
    private lateinit var progressDialog: ProgressDialog
    var cal = Calendar.getInstance()
    lateinit var selectedCategory:String


    lateinit var actionBarDrawerToggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if(!EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().register(this)
//        }
        setContentView(R.layout.activity_main)
        loadNavigationMenu()
//        initializeSpinner()
        openPollEventList = ArrayList()
        getAllPollData()
        setupRecyclerView()

        fab.setOnClickListener { view ->
                showDialog()

//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }


    }


    private fun showDialog(){

        try {
            val dialogView = View.inflate(this, R.layout.dialog_layout, null);
            val dialog = Dialog(this, R.style.MyAlertDialogStyle);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(dialogView);

            dialogView.button_close.setOnClickListener {
                revealShow(dialogView, false, dialog);
            }

            dialogView.button_save.setOnClickListener {
                var id = AppUtils.getCurrentTimestamp()
                var pollEvent = PollEvent(dialogView.et_title.text.toString(), id, "cat", dialogView.et_description.text.toString(),
                        dialogView.et_url.text.toString(),
                        dialogView.tv_date.text.toString(), dialogView.tv_time.toString(),
                        dialogView.et_min_participants.text.toString(), dialogView.et_max_participants.text.toString())

                createPollEvent(pollEvent)
                revealShow(dialogView, false, dialog);

            }

            // create an OnDateSetListener
            val dateSetListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                       dayOfMonth: Int) {
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val myFormat = "MM/dd/yyyy" // mention the format you need
                    val sdf = SimpleDateFormat(myFormat, Locale.US)
                    dialogView.tv_date!!.text = sdf.format(cal.getTime())
                }
            }

            dialogView.ll_time.setOnClickListener {
                val c: Calendar = Calendar.getInstance()
                val hh = c.get(Calendar.HOUR_OF_DAY)
                val mm = c.get(Calendar.MINUTE)
                val timePickerDialog: TimePickerDialog = TimePickerDialog(this@MainActivity, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    dialogView.tv_time.setText("" + hourOfDay + ":" + minute);
                }, hh, mm, true)
                timePickerDialog.show()
            }

            dialogView.tv_date!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    DatePickerDialog(this@MainActivity,
                            dateSetListener,
                            // set DatePickerDialog to point to today's date when it loads up
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH)).show()
                }

            })


            dialog.setOnShowListener { revealShow(dialogView, true, null) }

            dialog.setOnKeyListener(object : DialogInterface.OnKeyListener {
                override fun onKey(dialogInterface: DialogInterface, i: Int, keyEvent: KeyEvent): Boolean {
                    if (i == KeyEvent.KEYCODE_BACK) {

                        revealShow(dialogView, false, dialog)
                        return true
                    }

                    return false
                }
            })

            dialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

            dialog.show();
        } catch (e:Exception){
            e.printStackTrace()
        }

    }

//    private fun initializeSpinner() {
//
//        var categoryList: List<String> = ArrayList()
//        categoryList.toMutableList().add("Sports")
//        categoryList.toMutableList().add("Munchies")
//        categoryList.toMutableList().add("Trips")
//        categoryList.toMutableList().add("Coupons")
//        categoryList.toMutableList().add("Movies")
//        categoryList.toMutableList().add("Others")
//
//
//        // Creating adapter for spinner
//        val dataAdapter = HintAdapter<String>(this@MainActivity, "Munchies", categoryList)
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
//
//        // attaching data adapter to spinner
//        val hintSpinner = HintSpinner(spinnerCategory, dataAdapter, object : HintSpinner.Callback<String>() {
//           override fun onItemSelected(position: Int, itemAtPosition: String) {
//                selectedCategory = categoryList.get(position)
//
//            }
//        })
//        hintSpinner.init()
//
//    }


    private fun updateDateInView() {

    }
    private fun revealShow(dialogView: View, b: Boolean, dialog: Dialog?) {

        try {

            var view = dialogView.dialog


            val w = view.getWidth()
            val h = view.getHeight()

            val endRadius = Math.hypot(w.toDouble(), h.toDouble()).toInt()

            val cx = (fab.x + fab.width / 2).toInt()
            val cy = fab.y.toInt() + fab.height + 56


            if (b) {
                val revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, endRadius.toFloat())

                view.setVisibility(View.VISIBLE)
                revealAnimator.duration = 700
                revealAnimator.start()

            } else {

                val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius.toFloat(), 0f)

                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        dialog?.dismiss()
                        view.setVisibility(View.INVISIBLE)

                    }
                })
                anim.duration = 700
                anim.start()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }

    }

    private fun createPollEvent(pollEvent: PollEvent){
        try {

            if (AppUtils.isNetworkConnected(this@MainActivity)) {
                progressDialog = ProgressDialog(this@MainActivity)
                progressDialog.setMessage("Loading....")
                progressDialog.show()

                val call = MyApplication.networkService.createPollEvent(HeaderGenerator.createHeaderMap(this@MainActivity),pollEvent)
                call.enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>?) {
                        progressDialog.dismiss()
                        if (response != null && response.body() != null && response.body().status.equals("OK")) {
                            Toast.makeText(this@MainActivity, "Event created successfully", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        progressDialog.dismiss()
                        Log.e("errror", toString())
                        Toast.makeText(this@MainActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@MainActivity, "Network not connected!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }


    private fun getAllPollData(){
        try {
            if (AppUtils.isNetworkConnected(this@MainActivity)) {
                progressDialog = ProgressDialog(this@MainActivity)
                progressDialog.setMessage("Loading....")
                progressDialog.show()
                val username = AppUtils.getSharedPrefs(this).getString(Const.SHARED_PREF_USERNAME,"");
                val call = MyApplication.networkService.getPollData(username)
                call.enqueue(object : Callback<GetAllPollResponse> {
                    override fun onResponse(call: Call<GetAllPollResponse>, response: Response<GetAllPollResponse>?) {
                        progressDialog.dismiss()
                        if (response != null && response.body() != null && response.body().status.equals("OK")) {
                            openPollEventList = response.body().expiring_soon

                        }
                    }

                    override fun onFailure(call: Call<GetAllPollResponse>, t: Throwable) {
                        progressDialog.dismiss()
                        Log.e("errror", toString())
                        Toast.makeText(this@MainActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@MainActivity, "Network not connected!", Toast.LENGTH_SHORT).show()
            }
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
//        if (EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().unregister(this)
//        }
    }



    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        return menuInflater.inflate(R.menu.main , menu)
    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            when (item.itemId) {
                R.id.action_settings -> return true
                else -> return super.onOptionsItemSelected(item)
            }
        }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        rl_main.setHasFixedSize(true)
        rl_main.layoutManager = linearLayoutManager
        eventAdapter = EventAdapter(this@MainActivity,openPollEventList)
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

            actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity, drawer_layout, toolbar,
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

}