package com.kishordahiwadkar.restcountries.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.kishordahiwadkar.restcountries.BuildConfig
import com.kishordahiwadkar.restcountries.db.CountryDao
import com.kishordahiwadkar.restcountries.db.CountryDatabase
import com.kishordahiwadkar.restcountries.models.Country
import com.kishordahiwadkar.restcountries.network.RestCountriesWorker
import com.kishordahiwadkar.restcountries.network.WORKER_TAG
import com.kishordahiwadkar.restcountries.network.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryRepository(private val application: Application) {

    private var countryDao: CountryDao? = null
    private var countries: LiveData<List<Country>>? = null

    init {
        val countryDatabase = CountryDatabase.getDatabase(application)
        countryDao = countryDatabase?.countryDao()
        countries = countryDao?.getCountries()
    }

    fun getCountries(): LiveData<List<Country>>? {
        fetchCountriesInBackground()

        return countries
    }

    fun fetchCountriesInBackground() {
        val restCountriesWorkRequest =
            OneTimeWorkRequestBuilder<RestCountriesWorker>()
                .addTag(WORKER_TAG)
                .build()
        WorkManager
            .getInstance(application)
            .enqueueUniqueWork(WORKER_TAG, ExistingWorkPolicy.KEEP, restCountriesWorkRequest)
    }

    fun insert(country: Country) {
        //TODO
    }
}