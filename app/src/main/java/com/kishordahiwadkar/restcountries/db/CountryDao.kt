package com.kishordahiwadkar.restcountries.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kishordahiwadkar.restcountries.models.Country

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: Country)

    @Query("select * from country order by name")
    fun getCountries(): LiveData<List<Country>>
}