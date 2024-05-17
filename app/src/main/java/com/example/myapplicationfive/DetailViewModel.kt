package com.example.myapplicationfive

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel



class DetailViewModel(private val repository: ItemRepository, val bundle: Bundle?) : ViewModel() {

    var argAction: String? = null
    var argId: Int? = null
    var argId1: Int? = null
    var argId2: Int? = null
    var argTitle: String = "null"
    var argDescription: String = "null"
    var argPriority: Int? = null
    var argType: String = "null"
    var argType1: Boolean
    var argType2: Boolean
    var argCount: String = "null"
    var argPeriod: String = "null"

    var flag: Boolean

    init{
        flag = true
        argType1 = false
        argType2 = false
        argId1 = 0
        argId2 = 0

        readBundle(bundle)

        getId(bundle)
    }

    fun chooseTypeFirst(){
        argType1 = true
        argType2 = false
        argType = "Type 1"
    }

    fun chooseTypeSecond(){
        argType1 = false
        argType2 = true
        argType = "Type 2"
    }

    private fun readBundle(bundle:Bundle?){
        argAction = bundle?.getString("Action", "")
        argId = bundle?.getInt("Id",0 )
        argTitle = bundle!!.getString("Title", "Naming")
        argDescription = bundle!!.getString("Description", "...")
        argPriority = bundle!!.getString("Priority", "2")?.toInt()
        argType = bundle!!.getString("TYPE", "Type 1")

        if (argType != "Type 1") {
            argType2 = true
            argType1 = false
            argId2 = argId
        }
        if (argType != "Type 2") {
            argType1 = true
            argType2 = false
            argId1 = argId
        }

        argCount = bundle!!.getString("Count", "0")
        argPeriod = bundle!!.getString("Period", "0")

    }

    fun getId(bundle: Bundle?){
        if (bundle?.getString("Action", "")== "Create"){
            argId1 = repository.generateID("Type 1")
            argId2 = repository.generateID("Type 2")
        }
    }

    override fun onCleared() {}

    private fun callCreateMethod(item: Item){
        repository.insertItem(item)
    }

    private fun callUpdateMethod(item: Item){
        repository.updateItem(item)
    }

    fun callClick(){
        if (argAction == "Create"){
            callCreateMethod(makeItem())
        } else {
            callUpdateMethod(makeItem())
        }
    }

    private fun makeItem(): Item {

        if (argAction == "Create") {
            if (argType1 == true){
                argId = argId1
            } else {
                argId = argId2
            }
        }

        val item = Item(argId, argTitle, argDescription,
            argPriority, argType, argCount?.toInt(), argPeriod?.toInt()
        )

        return item
    }

}
