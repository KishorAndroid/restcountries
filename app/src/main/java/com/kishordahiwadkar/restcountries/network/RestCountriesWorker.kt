package com.kishordahiwadkar.restcountries.network

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kishordahiwadkar.restcountries.BuildConfig
import com.kishordahiwadkar.restcountries.db.CountryDatabase

const val WORKER_TAG = "RestCountriesWorker"

class RestCountriesWorker(val appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        val countryDatabase = CountryDatabase.getDatabase(appContext)
        val countryDao = countryDatabase?.countryDao()

        val data = service.listCountries(BuildConfig.region).execute()
        if (data.isSuccessful) {
            data.body()?.let { countries ->
                countries.forEach { country ->
                    countryDao?.insert(country)
                }
            }
            return Result.success()
        } else {
            return Result.failure()
        }
    }
}
