package com.geekbrains.androidstart.notes.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.lang.reflect.Type

class MySharedPreferences(context: Context, key: String) {
    private val KEY = key
    private val sharedPref: SharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    fun <T> saveArrayList(list: ArrayList<T>?) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(KEY, json)
        editor.apply()
    }

    fun <T> getArrayList(type: Type): ArrayList<T>? {
        val gson = Gson()
        val json: String? = sharedPref.getString(KEY, null)
        return gson.fromJson(json, type) as ArrayList<T>?
    }
}