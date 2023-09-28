package com.sumeyyesahin.kotlincountries.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumeyyesahin.kotlincountries.R
import com.sumeyyesahin.kotlincountries.adapter.CountryAdapter
import com.sumeyyesahin.kotlincountries.databinding.FragmentFeedBinding
import com.sumeyyesahin.kotlincountries.databinding.ItemCountryBinding
import com.sumeyyesahin.kotlincountries.model.Country
import com.sumeyyesahin.kotlincountries.viewmodel.FeedViewModel

class FeedFragment : Fragment() {

    private var countryAdapter= CountryAdapter(arrayListOf()) //adapteri oluşturduk
    private lateinit var viewModel: FeedViewModel //viewmodeli oluşturduk
    private lateinit var binding: FragmentFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentFeedBinding.bind(view) //binding oluşturduk

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java) //viewmodeli oluşturduk
        viewModel.refreshData() //viewmodeli çağırdık

        binding.countryList.layoutManager= LinearLayoutManager(context) //recyclerview için layoutmanager oluşturduk
        binding.countryList.adapter=countryAdapter //recyclerview için adapter oluşturduk

        binding.swipeRefreshLayout.setOnRefreshListener { //swipeRefreshLayout için listener oluşturduk
            binding.countryList.visibility=View.GONE //recyclerviewi gizle
            binding.countryError.visibility=View.GONE //hata mesajını gizle
            binding.countryLoading.visibility=View.VISIBLE //yükleniyor progressbarını göster
            viewModel.refreshData() //verileri yenile
            binding.swipeRefreshLayout.isRefreshing=false //swipeRefreshLayouti kapat
        }



        observeLiveData()
    }
    fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                binding.countryList.visibility=View.VISIBLE
                countryAdapter.updateCountryList(countries) //adaptere verileri gönder
            }

        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let { //hata varsa
                if (it){
                    binding.countryError.visibility=View.VISIBLE //hata mesajını göster
                    binding.countryList.visibility=View.GONE //recyclerviewi gizle

                }else{
                    binding.countryError.visibility=View.GONE //hata yoksa gösterme
                 }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let { //yükleniyor mu
                if (it){ //yükleniyor ise
                    binding.countryLoading.visibility=View.VISIBLE //yükleniyor progressbarını göster
                    binding.countryList.visibility=View.GONE //recyclerviewi gizle
                    binding.countryError.visibility=View.GONE //hata mesajını gizle
                }else{ //yüklenmiyor ise
                    binding.countryLoading.visibility=View.GONE //yükleniyor progressbarını gizle
                }
            }
        })
    }
}