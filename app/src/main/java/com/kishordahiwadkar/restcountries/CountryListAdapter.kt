package com.kishordahiwadkar.restcountries

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kishordahiwadkar.restcountries.models.Country


class CountryListAdapter(context: Context) : RecyclerView.Adapter<CountryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var countries: List<Country>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemView: View = inflater.inflate(R.layout.list_item_country, parent, false)
        return CountryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        if (countries != null) {
            val country: Country = countries!![position]
            holder.textCountryName.text = country.name
            holder.textCountryCapital.text = country.capital
        }
    }

    override fun getItemCount(): Int = countries?.size ?: 0

    fun setCountries(countries: List<Country>) {
        this.countries = countries
        notifyDataSetChanged()
    }
}

class CountryViewHolder(private val iteView: View) : RecyclerView.ViewHolder(iteView) {
    val textCountryName: TextView = iteView.findViewById(R.id.textCountryName)
    val textCountryCapital: TextView = iteView.findViewById(R.id.textCountryCapital)

    init {
        iteView.setOnClickListener {
            iteView.context.startActivity(Intent(iteView.context, DetailActivity::class.java))
        }
    }
}