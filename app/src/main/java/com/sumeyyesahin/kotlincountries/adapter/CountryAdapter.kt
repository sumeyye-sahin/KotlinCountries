package com.sumeyyesahin.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import com.sumeyyesahin.kotlincountries.R
import com.sumeyyesahin.kotlincountries.databinding.ItemCountryBinding
import com.sumeyyesahin.kotlincountries.model.Country
import com.sumeyyesahin.kotlincountries.view.FeedFragmentDirections

class CountryAdapter (val countryList : ArrayList<Country>) : //modelimizdeki bilgileri tutması için list oluşturuyoruz
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        //item_country.xml i adapter ile bağlıyoruz
        return CountryViewHolder(ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.countryName.text=countryList[position].countryName
        holder.binding.countryCapital.text=countryList[position].countryCapital
        Picasso.get().load(countryList[position].countryFlag).into(holder.binding.imageView) //picasso kütüphanesini import ettik öncesinde

        holder.itemView.setOnClickListener(){
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }

    fun updateCountryList(newCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged() //adaptörü güncelle
    }

}