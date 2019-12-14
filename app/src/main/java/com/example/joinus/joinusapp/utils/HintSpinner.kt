//package com.example.joinus.joinusapp.utils
//
//import android.util.Log
//import android.view.View
//import android.widget.AdapterView
//import android.widget.Spinner
//import com.example.joinus.joinusapp.R
//import com.example.joinus.joinusapp.adapters.HintAdapter
//
///**
// * Created by shwetatiwari on 14/12/19.
// */
//class HintSpinner<T>(private val spinner: Spinner, private val adapter: HintAdapter<T>, private val callback: Callback<T>?) {
//    private val LAYOUT_RESOURCE = R.layout.support_simple_spinner_dropdown_item
//
//    /**
//     * Used to handle the spinner events.
//     *
//     * @param <T> Type of the data used by the spinner
//    </T> */
//    interface Callback<T> {
//        /**
//         * When a spinner item has been selected.
//         *
//         * @param position Position selected
//         * @param itemAtPosition Item selected
//         */
//        fun onItemSelected(position: Int, itemAtPosition: T)
//    }
//
//    /**
//     * Initializes the hint spinner.
//     *
//     * By default, the hint is selected when calling this method.
//     */
//    fun init() {
//        adapter.setDropDownViewResource(LAYOUT_RESOURCE)
//        spinner.adapter = adapter
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                Log.d(TAG, "position selected: " + position)
//                if (this@HintSpinner.callback == null) {
//                    throw IllegalStateException("callback cannot be null")
//                }
//                if (!isHintPosition(position)) {
//                    val item = this@HintSpinner.spinner.getItemAtPosition(position)
//                    this@HintSpinner.callback.onItemSelected(position, item as T)
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                Log.d(TAG, "Nothing selected")
//            }
//        }
//        selectHint()
//    }
//
//    private fun isHintPosition(position: Int): Boolean {
//        return position == adapter.getHintPosition()
//    }
//
//    /**
//     * Selects the hint element.
//     */
//    fun selectHint() {
//        spinner.setSelection(adapter.getHintPosition())
//    }
//
//    companion object {
//        private val TAG = HintSpinner<*>::class.java.simpleName
//    }
//}