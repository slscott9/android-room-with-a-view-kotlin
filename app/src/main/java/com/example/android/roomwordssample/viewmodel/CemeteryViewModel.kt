/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.roomwordssample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.roomwordssample.database.Cemetery
import com.example.android.roomwordssample.database.CemeteryRepo
import com.example.android.roomwordssample.database.CemeteryRoomDatabase
import com.example.android.roomwordssample.database.Grave
import kotlinx.coroutines.*

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class CemeteryViewModel(application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job() //1.
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)     //3.

    private val repository: CemeteryRepo
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allCems: LiveData<List<Cemetery>>

        //initailized from CemteryDetailActivity we observe it when it is not null
    var gravesWithId: LiveData<List<Grave>>? = null

    private val _cemetery = MutableLiveData<Cemetery>()
    val cemetery: LiveData<Cemetery>
        get() = _cemetery



    init {
        val wordsDao = CemeteryRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = CemeteryRepo(wordsDao)
        allCems = repository.allCems

    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertCemetery(word: Cemetery) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCemetery(word)
    }


    //Call this when we need grave list and then observe the list from CemeteryDetailActivity
     fun getGraveList(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            gravesWithId = getGraves(id)

        }
    }

    private suspend fun getGraves(id: Int): LiveData<List<Grave>>{
        return withContext(Dispatchers.IO){
            val gravesList = repository.getGravesWithId(id)
            gravesList
        }
    }

    fun getCemetery(id: Int){
        uiScope.launch {
             _cemetery.value = getCemeteryWithId(id)
        }
    }

    private suspend fun getCemeteryWithId(id: Int): Cemetery? {
        return withContext(Dispatchers.IO) {
            val cemetery = repository.getCemeteryWithId(id)

            cemetery
        }
    }

    fun insertGrave(grave: Grave){
        uiScope.launch {
            susInsertGrave(grave)
        }
    }

    private suspend fun susInsertGrave(grave: Grave){
        withContext(Dispatchers.IO){
            repository.insertGrave(grave)
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}
