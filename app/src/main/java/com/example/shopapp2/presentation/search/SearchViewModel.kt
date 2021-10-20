package com.example.shopapp2.presentation.search

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.algolia.search.client.ClientSearch
import com.algolia.search.helper.deserialize
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName
import com.algolia.search.model.response.ResponseSearch
import com.algolia.search.model.search.Query
import com.example.shopapp2.BaseApplication
import com.example.shopapp2.R
import com.example.shopapp2.common.FirebaseConstants
import com.example.shopapp2.domain.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val application: BaseApplication
) : ViewModel() {

    val searchResultList = mutableStateListOf<Product>()

    fun search(string: String) {
        viewModelScope.launch {
            try {
                val client = ClientSearch(
                    applicationID = ApplicationID(
                        application.getString(R.string.algolia_application_id)
                    ),
                    apiKey = APIKey(application.getString(R.string.algolia_api_key))
                )
                val indexName = IndexName(FirebaseConstants.PRODUCTS_COLLECTION)
                val index = client.initIndex(indexName)

                val query = Query(
                    query = string,
                    hitsPerPage = 5,
                )
                val result: ResponseSearch = index.search(query)
                val resultList = result.hits.deserialize(Product.serializer())

                searchResultList.clear()
                searchResultList.addAll(resultList)

            } catch (e: Exception) {
                Timber.d("Algolia search exception $e")
            }
        }
    }
}
