//package com.example.joinus.joinusapp.adapters
//
//import android.content.Context
//import android.graphics.Typeface
//import android.util.Log
//import android.util.TypedValue
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import android.widget.TextView
//
///**
// * Created by shwetatiwari on 14/12/19.
// */
//class HintAdapter<T>(context: Context, private val layoutResource: Int, private val hintResource: String, data: List<T>) : ArrayAdapter<T>(context, layoutResource, data) {
//    private val context: Context? = null
//
//    private val layoutInflater: LayoutInflater
//
//    /**
//     * Gets the position of the hint.
//     *
//     * @return Position of the hint
//     */
//    val hintPosition: Int
//        get() {
//            val count = count
//            return if (count > 0) count + 1 else count
//        }
//
//    constructor(context: Context, hintResource: Int, data: List<T>) : this(context, DEFAULT_LAYOUT_RESOURCE, context.getString(hintResource), data) {}
//    constructor(context: Context, data: List<T>) : this(context, DEFAULT_LAYOUT_RESOURCE, data) {}
//    constructor(context: Context, hint: String, data: List<T>) : this(context, DEFAULT_LAYOUT_RESOURCE, hint, data) {}
//
//    //    public HintAdapter(Context context, int layoutResource,  List<T> data) {
//    //        this.layoutResource = layoutResource;
//    //
//    //        this.layoutInflater = LayoutInflater.from(context);
//    //    }
//
//    constructor(context: Context, layoutResource: Int, hintResource: Int, data: List<T>) : this(context, layoutResource, context.getString(hintResource), data) {}
//
//    init {
//        this.layoutInflater = LayoutInflater.from(context)
//    }// Create a copy, as we need to be able to add the hint without modifying the array passed in
//    // or crashing when the user sets an unmodifiable.
//
//    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
//        return getCustomView(position, convertView, parent)
//    }
//
//    /**
//     * Hook method to set a custom view.
//     *
//     * Provides a default implementation using the simple spinner dropdown item.
//     *
//     * @param position Position selected
//     * @param convertView View
//     * @param parent Parent view group
//     */
//    protected fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = inflateDefaultLayout(parent)
//        val item = getItem(position)
//        val textView = view.findViewById<View>(android.R.id.text1) as TextView
//        textView.text = item!!.toString()
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
//        textView.hint = ""
//
//        //        textView.setTextColor(getResR.color.SecondaryLight);
//        textView.gravity = View.FOCUS_LEFT
//        return view
//    }
//
//    private fun inflateDefaultLayout(parent: ViewGroup): View {
//        return inflateLayout(DEFAULT_LAYOUT_RESOURCE, parent, false)
//    }
//
//    private fun inflateLayout(resource: Int, root: android.view.ViewGroup, attachToRoot: Boolean): View {
//        return layoutInflater.inflate(resource, root, attachToRoot)
//    }
//
//    fun inflateLayout(root: android.view.ViewGroup, attachToRoot: Boolean): View {
//        return layoutInflater.inflate(layoutResource, root, attachToRoot)
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
////        Log.d(TAG, "position: $position, getCount: $count")
//        val view: View
//        if (position == hintPosition) {
//            view = getDefaultView(parent)
//        } else {
//            view = getCustomView(position, convertView, parent)
//        }
//        return view
//    }
//
//    private fun getDefaultView(parent: ViewGroup): View {
//        val view = inflateDefaultLayout(parent)
//        val textView = view.findViewById<View>(android.R.id.text1) as TextView
//        textView.text = ""
//        textView.hint = hintResource
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
//        textView.setPadding(0, 20, 0, 20)
//        val tf = Typeface.createFromAsset(getContext().assets, "fonts/Roboto/Roboto-Regular.ttf")
//        textView.setTypeface(tf, Typeface.NORMAL)
//        return view
//    }
//
//    companion object {
////        private val TAG = HintAdapter<*>::class.java.simpleName
//
//        private val DEFAULT_LAYOUT_RESOURCE = android.R.layout.simple_spinner_dropdown_item
//    }
//}