package com.kishordahiwadkar.restcountries

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import com.kishordahiwadkar.restcountries.databinding.ActivityMainBinding
import com.kishordahiwadkar.restcountries.models.Country


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        binding.viewModel = viewModel

        val recyclerView = binding.recyclerview
        val adapter = CountryListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getCountries()?.observe(this,
            { words -> // Update the cached copy of the words in the adapter.
                adapter.setCountries(words)
            })

        binding.swipeContainer.setOnRefreshListener {
            viewModel.fetchCountriesInBackground()
        }

        viewModel.workInfo.observe(this, progressObserver())
    }

    private fun progressObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->
            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }

            listOfWorkInfo.forEach { workInfo ->
                if (workInfo.state == WorkInfo.State.SUCCEEDED || workInfo.state == WorkInfo.State.FAILED) {
                    binding.swipeContainer.isRefreshing = false
                }
                if (workInfo.state == WorkInfo.State.ENQUEUED || workInfo.state == WorkInfo.State.RUNNING) {
                    binding.swipeContainer.isRefreshing = true
                }
            }

        }
    }
}