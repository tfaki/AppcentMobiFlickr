package com.example.appcentmobidemo.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcentmobidemo.R
import com.example.appcentmobidemo.helper.startActivity
import com.example.appcentmobidemo.model.PhotoDataResponse
import com.example.appcentmobidemo.ui.adapter.PhotoListAdapter
import com.example.appcentmobidemo.ui.interfaces.ItemClickListener
import com.example.appcentmobidemo.ui.interfaces.OnLoadMoreListener
import com.example.appcentmobidemo.ui.viewmodel.PhotoListViewModel
import com.example.appcentmobidemo.util.Const
import kotlinx.android.synthetic.main.activity_photo_list.*

class PhotoListActivity : BaseActivity() {

    private lateinit var photosViewModel: PhotoListViewModel
    private lateinit var adapter: PhotoListAdapter
    private var photoPartList = ArrayList<PhotoDataResponse?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_list)
        showLoading()

        rvPhotos.layoutManager = LinearLayoutManager(this)
        photosViewModel = PhotoListViewModel()

        photosViewModel.photosResult.observe(this, Observer {
            if (it.stat == "ok") {
                load(it.photos.photo)
            }
        })

        photosViewModel.getPhotos()
    }

    fun load(photoAllList: ArrayList<PhotoDataResponse>) {
       hideLoading()
        for (i in 0..20) {
            photoPartList.add(photoAllList[i])
        }

        adapter = PhotoListAdapter(photoPartList, object : ItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                val photoPath =
                    "https://farm" + photoAllList[position].farm + ".staticflickr.com/" +
                            photoAllList[position].server + "/" + photoAllList[position].id + "_" + photoAllList[position].secret + ".jpg"

                startActivity<ShowActivity> {
                    putExtra(Const.PHOTO_PATH, photoPath)
                }
            }

        })
        rvPhotos.adapter = adapter
        adapter.PhotoListAdapter(rvPhotos)

        adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                if (photoPartList.size < photoAllList.size) {
                    photoPartList.add(null)
                    adapter.notifyItemRemoved(photoPartList.size - 1)
                    Handler().postDelayed({
                        photoPartList.removeAt(photoPartList.size - 1)
                        adapter.notifyItemRemoved(photoPartList.size)

                        val index = photoPartList.size
                        val end = index + 20

                        if (end < 100){
                            for (i in index..end) {
                                photoPartList.add(photoAllList[i])
                            }
                            adapter.notifyDataSetChanged()
                            adapter.setLoaded()
                        }
                    }, 2000)
                }
            }

        })
    }
}