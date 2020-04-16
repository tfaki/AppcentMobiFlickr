package com.example.appcentmobidemo.model

data class PhotosDataResponse(val page: Int, val pages: Int, val perpage: Int, val total: Int, val photo: ArrayList<PhotoDataResponse>)