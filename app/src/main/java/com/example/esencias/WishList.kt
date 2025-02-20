package com.example.esencias

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WishList : ViewModel(){
    val wishList: MutableLiveData<MutableList<Producto>> by lazy {
        MutableLiveData<MutableList<Producto>>(mutableListOf())
    }
}