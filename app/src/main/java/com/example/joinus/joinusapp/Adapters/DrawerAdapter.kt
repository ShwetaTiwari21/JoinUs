package com.example.joinus.joinusapp.Adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.example.joinus.joinusapp.R
import com.example.joinus.joinusapp.models.DrawerItem
import kotlinx.android.synthetic.main.drawer_list_item.view.*

import java.util.ArrayList

/**
 * Created by Rishabh Bhatia on 6/14/2016.
 */
class DrawerAdapter(context: Context, navDrawerItems: ArrayList<DrawerItem>, language: String) : BaseAdapter() {

    private var context: Context? = null
    private var navDrawerItems: ArrayList<DrawerItem>? = null
    private var currentLanguage: String? = null

    init {
        try {
            this.context = context
            this.navDrawerItems = navDrawerItems
            this.currentLanguage = language
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getCount(): Int {
        return navDrawerItems!!.size
    }

    override fun getItem(position: Int): Any {
        return navDrawerItems!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView

        try {
            val drawerViewHolder: DrawerViewHolder

            if (convertView == null) {
                val mInflater = context!!.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = mInflater.inflate(R.layout.drawer_list_item, parent, false)
                drawerViewHolder = DrawerViewHolder(convertView!!)
                convertView!!.tag = drawerViewHolder
            } else {
                drawerViewHolder = convertView.tag as DrawerViewHolder
            }

            val drawerItem = navDrawerItems!![position]
            drawerViewHolder.clear()

            if (drawerItem.icon!== 0) {
                drawerViewHolder.imgIcon.setImageResource(drawerItem.icon)
            }

            drawerViewHolder.parentLayout.background = context!!.resources.getDrawable(R
                    .drawable.list_item_ripple)
            drawerViewHolder.txtTitle.setTextColor(context!!.resources.getColor(R.color.TextPrimaryDark))
            drawerViewHolder.txtTitle.textSize = 14f
            drawerViewHolder.txtTitle.setSingleLine(true)


            if (drawerItem.title != null) {
                drawerViewHolder.txtTitle.setText(drawerItem.title)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return convertView
    }

    private inner class DrawerViewHolder internal constructor(convertView: View) {
        internal var imgIcon: ImageView = convertView.iv_drawer_icon
        internal var txtTitle: TextView = convertView.tv_drawer_title
        internal var selectedLanguage: TextView = convertView.tv_drawer_language_title
        internal var txtCount: TextView = convertView.tv_drawer_counts
        internal var parentLayout: LinearLayout = convertView.ll_nav_drawer_item_parent
        internal var countHolder: LinearLayout = convertView.ll_drawer_count_holder

        internal fun clear() {
            try {
                imgIcon.setImageResource(R.drawable.ic_launcher_background)
                txtTitle.text = ""
                selectedLanguage.text = ""
                txtCount.text = ""
                txtCount.visibility = View.INVISIBLE
                countHolder.visibility = View.INVISIBLE
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}
