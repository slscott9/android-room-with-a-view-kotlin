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

package com.example.android.roomwordssample.database

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.android.roomwordssample.database.Cemetery


/**
 * The Room Magic is in this file, where you map a Java method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
interface CemeteryDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from cemetery_table")
    fun getAllCemeteries(): LiveData<List<Cemetery>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCemetery(cem: Cemetery)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGrave(grave: Grave)

    @Query("DELETE FROM cemetery_table")
    fun deleteAll()

    @Query("select * from graves where cemeteryId= :cemeteryId")
    fun getAllGravesWithId(cemeteryId: Int) : LiveData<List<Grave>>

    @Query("select * from  cemetery_table where row_number= :rowNum")
    fun getCemeteryWithRowNum(rowNum: Int) : Cemetery
}
