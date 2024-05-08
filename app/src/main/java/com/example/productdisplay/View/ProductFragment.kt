package com.example.productdisplay.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productdisplay.R
import com.example.productdisplay.ViewModel.adapter.productAdapter
import com.example.productdisplay.ViewModel.loadProducts



class ProductFragment : Fragment() {

    private var isLoading = false
    lateinit var adapter: productAdapter
    lateinit var recycleViewProducts : RecyclerView
    private lateinit var viewModels: loadProducts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        init(view)

        viewModels = ViewModelProvider(this).get(loadProducts::class.java)

        viewModels.loadProduct()
        viewModels.productsLiveData.observe(viewLifecycleOwner) {
            adapter = productAdapter(it.toMutableList())
            recycleViewProducts.adapter = adapter
            recycleViewProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        }

        recycleViewProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isLoading) return
                isLoading = true;

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItemPosition + visibleItemCount >= totalItemCount - 2) {

                    viewModels.loadAdditionalProduct()
                    viewModels.newproductsLiveData.observe(viewLifecycleOwner) {
                        adapter.addData(it.toMutableList())
                        isLoading = false;
                    }


                }
                else{
                    isLoading = false
                }
            }
        })

        return view
    }

    private fun init(view: View){
        recycleViewProducts = view.findViewById(R.id.productsList)
    }

}


