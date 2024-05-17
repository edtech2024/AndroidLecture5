package com.example.myapplicationfive


import androidx.lifecycle.*


class ListViewModel(private val repository: ItemRepository) : ViewModel() {

    var argSearch: String = ""

    var queryInputValue: MutableLiveData<String> = MutableLiveData<String>()

    fun setQueryValue(value: String) {
        this.queryInputValue.value = value
    }

    fun changedItemsListType1(): LiveData<List<Item>> = queryInputValue.switchMap { query ->
        repository.callMethod1(query)
    }

    fun changedItemsListType2(): LiveData<List<Item>> = queryInputValue.switchMap { query ->
        repository.callMethod2(query)
    }

    private var mediatorType1: MediatorLiveData<List<Item>> = MediatorLiveData<List<Item>>()
    private var mediatorType2: MediatorLiveData<List<Item>> = MediatorLiveData<List<Item>>()

    var itemsListType1: LiveData<List<Item>> = mediatorType1
    var itemsListType2: LiveData<List<Item>> = mediatorType2

    var tempListType1: MutableList<Item> = mutableListOf<Item>()
    var tempListType2: MutableList<Item> = mutableListOf<Item>()

    init {
        setQueryValue("default")

        mediatorType1.addSource(changedItemsListType1()) { source ->
            for (item in source) {
                tempListType1.add(item)
                mediatorType1.postValue(tempListType1)
            }
        }

        mediatorType2.addSource(changedItemsListType2()) { source ->
            for (item in source) {
                tempListType2.add(item)
                mediatorType2.postValue(tempListType2)
            }
        }
    }


    override fun onCleared() {

    }

    fun callSortMethod() {
        sortByCount()
    }

    fun callFilterMethod() {
        filtrationByTitle(argSearch)
    }

    fun callResetFilter() {
        argSearch = ""
        sortById()
        mediatorType1.postValue(tempListType1)
        mediatorType2.postValue(tempListType2)
    }

    fun sortById(){
        tempListType1.sortBy { item -> item.id }
        mediatorType1.postValue(tempListType1)

        tempListType2.sortBy { item -> item.id }
        mediatorType2.postValue(tempListType2)
    }

    fun sortByCount() {

        tempListType1.sortBy { item -> item.count }
        mediatorType1.postValue(tempListType1)

        tempListType2.sortBy { item -> item.count }
        mediatorType2.postValue(tempListType2)
    }

    fun filtrationByTitle(title: String){
        var filteredListT1 = tempListType1.filter { it.title == title }
        mediatorType1.postValue(filteredListT1)

        var filteredListT2 = tempListType2.filter { it.title == title }
        mediatorType2.postValue(filteredListT2)
    }

}
