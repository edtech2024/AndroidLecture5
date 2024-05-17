package com.example.myapplicationfive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ItemRepository {

    var _databaseType1: MutableLiveData<List<Item>> = MutableLiveData<List<Item>>()
    var _databaseType2: MutableLiveData<List<Item>> = MutableLiveData<List<Item>>()

    var databaseType1: LiveData<List<Item>> = _databaseType1
    var databaseType2: LiveData<List<Item>> = _databaseType2

    var ID: Int = 0

    fun generateID(type: String):Int{
        if (type == "Type 1"){
            var oldList: MutableList<Item> = _databaseType1.value.orEmpty().toMutableList()
            this.ID = oldList.size
        } else {
            var oldList: MutableList<Item> = _databaseType2.value.orEmpty().toMutableList()
            this.ID = oldList.size
        }

        return ID
    }

    fun insertItem(item: Item) {
        if (item.type == "Type 1") {
            val oldList: MutableList<Item> = _databaseType1.value.orEmpty().toMutableList()
            _databaseType1.value = oldList + item
        } else {
            val oldList: MutableList<Item> = _databaseType2.value.orEmpty().toMutableList()
            _databaseType2.value = oldList + item
        }
    }

    fun updateItem(item: Item) {
        if (item.type == "Type 1") {
            var updListT1: MutableList<Item> = _databaseType1.value.orEmpty().toMutableList()
            updListT1[item.id!!.toString().toInt()] = item
            _databaseType1.value = updListT1
        } else {
            var updListT2: MutableList<Item> = _databaseType2.value.orEmpty().toMutableList()
            updListT2[item.id!!.toString().toInt()] = item
            _databaseType2.value = updListT2
        }
    }

    fun default(type: String):LiveData<List<Item>>{
        if (type == "Type 1"){
            return databaseType1
        } else {
            return databaseType2
        }
    }

   fun callMethod1(value: String):LiveData<List<Item>> {
       return when(value){
           "default" -> default("Type 1")
           else -> throw Exception()
       }
   }

   fun callMethod2(value: String):LiveData<List<Item>>{
       return when(value){
           "default" -> default("Type 2")
           else -> throw Exception()
       }
   }

}
