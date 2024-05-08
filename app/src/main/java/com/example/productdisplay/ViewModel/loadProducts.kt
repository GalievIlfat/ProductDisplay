package com.example.productdisplay.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productdisplay.model.Product
import com.example.productdisplay.repository.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class loadProducts: ViewModel() {
    private var skip: Int = 0
    private var limit: Int = 20

    val productsLiveData: MutableLiveData<List<Product>> = MutableLiveData()
    val newproductsLiveData: MutableLiveData<List<Product>> = MutableLiveData()

    fun loadProduct() {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val response = RetrofitInstance.api.getAllProducts(skip,limit)
                if (response.products!=null) {
                    productsLiveData.postValue(response.products)
                } else {
                    // Обработка ошибки
                }
            } catch (e: Exception) {
                Log.d("ErrorLoadProduct", e.message.toString())
            }
        }
    }

    fun loadAdditionalProduct(){

        CoroutineScope(Dispatchers.IO).launch{
            try {
                skip+=limit
                val response = RetrofitInstance.api.getAllProducts(skip,limit)
                if (response.products!=null) {
                    newproductsLiveData.postValue(response.products)
                } else {

                }
            } catch (e: Exception) {
                Log.d("ErrorLoadAdditional",e.message.toString())
            }
        }
    }

}