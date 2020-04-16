package com.example.appcentmobidemo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appcentmobidemo.api.ApiService
import com.example.appcentmobidemo.model.DataResponse
import com.example.appcentmobidemo.util.Const
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PhotoListViewModel : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()
    var photosResult: MutableLiveData<DataResponse> = MutableLiveData()

    fun getPhotos() {
        disposable.add(
            ApiService()
                .apiService
                .getPhotos(Const.METHOD, Const.API_KEY, 1, Const.FORMAT, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    photosResult.postValue(it)
                }, { e ->
                    Log.d("TAG", e.message)
                }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}