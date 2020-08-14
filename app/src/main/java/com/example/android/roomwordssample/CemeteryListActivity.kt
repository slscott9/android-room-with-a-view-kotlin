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

package com.example.android.roomwordssample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordssample.adapters.CemeteryListAdapter
import com.example.android.roomwordssample.adapters.CemeteryListener
import com.example.android.roomwordssample.viewmodel.CemeteryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CemeteryListActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private lateinit var viewModel: CemeteryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cemetery_list)

        val cemlistRecyclerView = findViewById<RecyclerView>(R.id.cemeteryListRecyclerView)
        val adapter = CemeteryListAdapter(CemeteryListener {
            id -> val intent = Intent(this, CemeteryDeatilActivity::class.java)
                        intent.putExtra("cemetery_id", id)
                        startActivity(intent)
        })


        // Get a new or existing ViewModel from the ViewModelProvider.
        viewModel = ViewModelProvider(this).get(CemeteryViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        viewModel.allCems.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.submitList(it) }
        })

        cemlistRecyclerView.adapter = adapter
        cemlistRecyclerView.layoutManager = LinearLayoutManager(this)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@CemeteryListActivity, NewCemeteryActivity::class.java)
            startActivity(intent)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
//        super.onActivityResult(requestCode, resultCode, intentData)
//
//        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
//            intentData?.let { data ->
//                val word = Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY))
//                wordViewModel.insert(word)
//                Unit
//            }
//        } else {
//            Toast.makeText(
//                    applicationContext,
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG
//            ).show()
//        }
//    }
}
