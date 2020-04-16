package com.example.appcentmobidemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentmobidemo.R
import com.example.appcentmobidemo.helper.load
import com.example.appcentmobidemo.model.PhotoDataResponse
import com.example.appcentmobidemo.ui.interfaces.ItemClickListener
import com.example.appcentmobidemo.ui.interfaces.OnLoadMoreListener


class PhotoListAdapter(val photoList: ArrayList<PhotoDataResponse?> = arrayListOf(), val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var isLoading = false

    fun PhotoListAdapter(rvPhotos: RecyclerView){
        val layoutManager = rvPhotos.layoutManager as LinearLayoutManager
        rvPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastPosition: Int = layoutManager.findLastVisibleItemPosition()
                val itemCount: Int = layoutManager.itemCount

                if (!isLoading && itemCount <= (lastPosition + 20)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener!!.onLoadMore()
                    }
                    isLoading = true
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.rv_recent_photo, parent, false)
            return HolderModel(view)
        }
        else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.load_more_progress, parent, false)
            return HolderLoading(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (photoList[position] != null){
            return VIEW_TYPE_ITEM
        } else {
            return VIEW_TYPE_LOADING
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: RecyclerView.ViewHolder = holder

        if (viewHolder is HolderLoading){
            val holder: HolderLoading = viewHolder
        } else if (viewHolder is HolderModel){
            val holder: HolderModel = viewHolder
            holder.tvTitle.text = photoList[position]!!.title
            holder.ivPhoto.load(
                "https://farm" + photoList[position]!!.farm + ".staticflickr.com/" +
                        photoList[position]!!.server + "/" + photoList[position]!!.id + "_" + photoList[position]!!.secret + ".jpg")
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(it, position)
            }
        }
    }

    fun setLoaded() {
        isLoading = false
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener
    }

    class HolderLoading(view: View): RecyclerView.ViewHolder(view)

    class HolderModel(view: View): RecyclerView.ViewHolder(view){
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val ivPhoto: ImageView = view.findViewById(R.id.ivPhoto)
    }

}

