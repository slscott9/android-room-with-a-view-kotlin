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
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.android.roomwordssample.database.Cemetery
import com.example.android.roomwordssample.database.Grave
import com.example.android.roomwordssample.databinding.ActivityNewCemeteryBinding
import com.example.android.roomwordssample.viewmodel.CemeteryViewModel

/**
 * Activity for entering a word.
 */

class NewCemeteryActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText
    private lateinit var viewModel: CemeteryViewModel
    private lateinit var binding: ActivityNewCemeteryBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_cemetery)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(CemeteryViewModel::class.java)


        binding.addCemButton.setOnClickListener {
            val replyIntent = Intent()
            if (binding.nameEditText.text.isNullOrEmpty() ||
                    binding.locationEditText.text.isNullOrEmpty() ||
                    binding.stateEditText.text.isNullOrEmpty() ||
                    binding.countyEditText.text.isNullOrEmpty() ||
                    binding.townshipEditText.text.isNullOrEmpty() ||
                    binding.rangeEditText.text.isNullOrEmpty() ||
                    binding.sectionEditText.text.isNullOrEmpty() ||
                    binding.gpsEditText.text.isNullOrEmpty() ||
                    binding.firstYearEditText.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please entery all fields", Toast.LENGTH_SHORT).show()
            } else {

                val name = binding.nameEditText.text.toString()
                val location = binding.locationEditText.text.toString()
                val state = binding.stateEditText.text.toString()
                val county = binding.countyEditText.text.toString()
                val townShip = binding.townshipEditText.text.toString()
                val range = binding.rangeEditText.text.toString()
                val section = binding.sectionEditText.text.toString()
                val spot = binding.spotEditText.text.toString()
                val gps = binding.gpsEditText.text.toString()
                val firstYear = binding.firstYearEditText.text.toString()
                val cemetery =
                        Cemetery(cemeteryName = name,
                                cemeteryLocation = location,
                                cemeteryState = state,
                                cemeteryCounty = county,
                                township = townShip,
                                range = range,
                                section = section,
                                spot = spot,
                                gps = gps,
                                firstYear = firstYear)
                viewModel.insert(cemetery)
                finish()


            }
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}

