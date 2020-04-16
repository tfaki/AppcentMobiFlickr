package com.example.appcentmobidemo.helper

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

fun ImageView.load(path: String?) {
    path?.let {
        Glide.with(this)
            .load(it)
            .into(this)
    }
}

inline fun <reified T : AppCompatActivity> Context.startActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
}