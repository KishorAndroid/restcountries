package com.kishordahiwadkar.restcountries

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.kishordahiwadkar.restcountries.models.Country
import com.kishordahiwadkar.restcountries.network.WORKER_TAG
import com.kishordahiwadkar.restcountries.network.service
import com.kishordahiwadkar.restcountries.repository.CountryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CountryRepository(application)
    private val countries : LiveData<List<Country>>? = repository.getCountries()

    internal val workInfo: LiveData<List<WorkInfo>>
    private val workManager: WorkManager = WorkManager.getInstance(application)
    init {
        workInfo = workManager.getWorkInfosByTagLiveData(WORKER_TAG)
    }

    fun getCountries(): LiveData<List<Country>>? {
        return countries
    }

    fun fetchCountriesInBackground() {
        repository.fetchCountriesInBackground()
    }
}