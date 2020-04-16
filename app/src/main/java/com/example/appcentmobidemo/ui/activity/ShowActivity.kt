package com.example.appcentmobidemo.ui.activity

import android.os.Bundle
import com.example.appcentmobidemo.R
import com.example.appcentmobidemo.helper.load
import com.example.appcentmobidemo.util.Const
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        val photoPath = intent.getStringExtra(Const.PHOTO_PATH)
        ivPhoto.load(photoPath)
    }
}