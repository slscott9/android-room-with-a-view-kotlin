package com.example.android.roomwordssample

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

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.roomwordssample.database.Cemetery
import com.example.android.roomwordssample.database.CemeteryDao
import com.example.android.roomwordssample.database.CemeteryRoomDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class WordDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var wordDao: CemeteryDao
    private lateinit var db: CemeteryRoomDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, CemeteryRoomDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        wordDao = db.wordDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWord() {
        val cemetery = Cemetery("word")
        wordDao.insert("name")
        val allWords = wordDao.getAllCemeteries().waitForValue()
        assertEquals(allWords[0], cemetery.name)
    }

    @Test
    @Throws(Exception::class)
    fun getAllWords() {
        val cemetery = Cemetery("aaa")
        wordDao.insert(cemetery)
        val cemetery2 = Cemetery("bbb")
        wordDao.insert(cemetery)
        val allWords = wordDao.getAllCemeteries().waitForValue()
        assertEquals(allWords[0].name, cemetery.name)
        assertEquals(allWords[1].name, cemetery2.name)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() {
        val word = Cemetery("word")
        wordDao.insert(word)
        val word2 = Cemetery("word2")
        wordDao.insert(word2)
        wordDao.deleteAll()
        val allWords = wordDao.getAllCemeteries().waitForValue()
        assertTrue(allWords.isEmpty())
    }
}
