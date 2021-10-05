package com.example.shopapp2.data.repository

import com.example.shopapp2.common.FirebaseConstants
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.domain.repository.HomeRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class HomeRepositoryImpl(
    val firestoreDb: FirebaseFirestore,
) : HomeRepository {

    override suspend fun getMostPopularProducts(
        setMostPopularLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product> {

        val productsList = mutableListOf<Product>()

        firestoreDb
            .collection(FirebaseConstants.PRODUCTS_COLLECTION)
            .orderBy("title", Query.Direction.ASCENDING)
            .limit(4)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val result = task.result

                    if (result != null) {

                        if (result.documents.size >= 1) {

                            val document = result.documents[result.documents.size - 1]
                            setMostPopularLastVisibleDocumentSnapshot(document)
                        }

                        for (document in result) {
                            val product: Product = document.toObject()
                            product.id = document.id

                            productsList.add(product)
                        }
                    }
                }
            }
            .addOnFailureListener {
                Timber.d("getMostPopularProducts Failed $it")
            }
            .await()

        return productsList
    }

    override suspend fun loadMostPopularNextPage(
        product: DocumentSnapshot,
        setMostPopularLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product> {

        val productsList = mutableListOf<Product>()
        firestoreDb
            .collection(FirebaseConstants.PRODUCTS_COLLECTION)
            .orderBy("title", Query.Direction.ASCENDING)
            .startAfter(product)
            .limit(4)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val result = task.result

                    if (result != null) {

                        if (result.documents.size >= 1) {
                            val document = result.documents[result.documents.size - 1]
                            setMostPopularLastVisibleDocumentSnapshot(document)
                        }

                        for (document in result) {
                            val product: Product = document.toObject()
                            product.id = document.id

                            productsList.add(product)
                        }
                    }
                }
            }
            .addOnFailureListener {
                Timber.d("loadMostPopularNextPage error $it")
            }
            .await()

        return productsList
    }

    // Discounts
    override suspend fun getDiscountsProducts(
        setDiscountsLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product> {

        val productsList = mutableListOf<Product>()

        firestoreDb
            .collection(FirebaseConstants.PRODUCTS_COLLECTION)
            .orderBy("title", Query.Direction.ASCENDING)
            .whereEqualTo("discountBoolean", true)
            .limit(4)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val result = task.result

                    if (result != null) {

                        if (result.documents.size >= 1) {
                            val document = result.documents[result.documents.size - 1]
                            setDiscountsLastVisibleDocumentSnapshot(document)
                        }

                        for (document in result) {
                            val product: Product = document.toObject()
                            product.id = document.id

                            productsList.add(product)
                        }
                    }
                }
            }
            .addOnFailureListener {
                Timber.d("getDiscountsProducts Failed $it")
            }
            .await()

        return productsList
    }

    override suspend fun loadDiscountsNextPage(
        product: DocumentSnapshot,
        setDiscountsLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product> {

        val productsList = mutableListOf<Product>()
        firestoreDb
            .collection(FirebaseConstants.PRODUCTS_COLLECTION)
            .orderBy("title", Query.Direction.ASCENDING)
            .whereEqualTo("discountBoolean", true)
            .startAfter(product)
            .limit(4)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val result = task.result

                    if (result != null) {

                        if (result.documents.size >= 1) {
                            val document = result.documents[result.documents.size - 1]
                            setDiscountsLastVisibleDocumentSnapshot(document)
                        }

                        for (document in result) {
                            val product: Product = document.toObject()
                            product.id = document.id

                            productsList.add(product)
                        }
                    }
                }
            }
            .addOnFailureListener {
                Timber.d("loadDiscountsNextPage error $it")
            }
            .await()

        return productsList
    }

    override suspend fun getNewProducts(
        setNewLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product> {
        val productsList = mutableListOf<Product>()

        firestoreDb
            .collection(FirebaseConstants.PRODUCTS_COLLECTION)
            .orderBy("lastModified", Query.Direction.DESCENDING)
            .limit(4)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val result = task.result

                    if (result != null) {

                        if (result.documents.size >= 1) {
                            val document = result.documents[result.documents.size - 1]
                            setNewLastVisibleDocumentSnapshot(document)
                        }

                        for (document in result) {
                            val product: Product = document.toObject()
                            product.id = document.id

                            productsList.add(product)
                        }
                    }
                }
            }
            .addOnFailureListener {
                Timber.d("getNewProducts Failed $it")
            }
            .await()

        return productsList
    }

    override suspend fun loadNewNextPage(
        product: DocumentSnapshot,
        setNewLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product> {
        val productsList = mutableListOf<Product>()
        firestoreDb
            .collection(FirebaseConstants.PRODUCTS_COLLECTION)
            .orderBy("lastModified", Query.Direction.DESCENDING)
            .startAfter(product)
            .limit(4)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val result = task.result

                    if (result != null) {

                        if (result.documents.size >= 1) {
                            val document = result.documents[result.documents.size - 1]
                            setNewLastVisibleDocumentSnapshot(document)
                        }

                        for (document in result) {
                            val product: Product = document.toObject()
                            product.id = document.id

                            productsList.add(product)
                        }
                    }
                }
            }
            .addOnFailureListener {
                Timber.d("loadNewNextPage error $it")
            }
            .await()

        return productsList
    }
}