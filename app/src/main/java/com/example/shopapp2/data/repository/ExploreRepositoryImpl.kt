package com.example.shopapp2.data.repository

import com.example.shopapp2.common.FirebaseConstants
import com.example.shopapp2.common.ProductTypesConstants
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.domain.repository.ExploreRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class ExploreRepositoryImpl(
    val firebaseFirestore: FirebaseFirestore

) : ExploreRepository {

    override suspend fun getClothesProducts(
        setClothesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product> {
        val productsList = mutableListOf<Product>()
        firebaseFirestore
            .collection(FirebaseConstants.PRODUCTS_COLLECTION)
            .orderBy("title", Query.Direction.ASCENDING)
            .whereEqualTo("type", ProductTypesConstants.CLOTHES)
            .limit(4)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val result = task.result

                    if (result != null) {

                        if (result.documents.size >= 1) {

                            val document = result.documents[result.documents.size - 1]
                            setClothesLastVisibleDocumentSnapshot(document)
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
                Timber.d("getClothesProducts Failed $it")
            }
            .await()
        return productsList
    }

    override suspend fun loadClothesNextPage(
        product: DocumentSnapshot,
        setClothesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product> {
        val productsList = mutableListOf<Product>()
        firebaseFirestore
            .collection(FirebaseConstants.PRODUCTS_COLLECTION)
            .orderBy("title", Query.Direction.ASCENDING)
            .whereEqualTo("type", ProductTypesConstants.CLOTHES)
            .startAfter(product)
            .limit(4)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val result = task.result

                    if (result != null) {

                        if (result.documents.size >= 1) {
                            val document = result.documents[result.documents.size - 1]
                            setClothesLastVisibleDocumentSnapshot(document)
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
                // send error reports to Firebase Crashlytics
                Timber.d("loadClothesNextPage error $it")
            }
            .await()

        return productsList
    }

    // SHOES
    override suspend fun getShoesProducts(setShoesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit): List<Product> {
        val productsList = mutableListOf<Product>()

        firebaseFirestore
            .collection(FirebaseConstants.PRODUCTS_COLLECTION)
            .orderBy("title", Query.Direction.ASCENDING)
            .whereEqualTo("type", ProductTypesConstants.SHOES)
            .limit(4)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val result = task.result

                    if (result != null) {

                        if (result.documents.size >= 1) {

                            val document = result.documents[result.documents.size - 1]
                            setShoesLastVisibleDocumentSnapshot(document)
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
                Timber.d("getShoesProducts Failed $it")
            }
            .await()

        return productsList
    }

    override suspend fun loadShoesNextPage(
        product: DocumentSnapshot,
        setShoesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product> {
        val productsList = mutableListOf<Product>()
        firebaseFirestore
            .collection(FirebaseConstants.PRODUCTS_COLLECTION)
            .orderBy("title", Query.Direction.ASCENDING)
            .whereEqualTo("type", ProductTypesConstants.SHOES)
            .startAfter(product)
            .limit(4)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val result = task.result

                    if (result != null) {

                        if (result.documents.size >= 1) {
                            val document = result.documents[result.documents.size - 1]
                            setShoesLastVisibleDocumentSnapshot(document)
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
                // send error reports to Firebase Crashlytics
                Timber.d("loadShoesNextPage error $it")
            }
            .await()

        return productsList
    }

    // WATCHES
    override suspend fun getWatchesProducts(setWatchesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit): List<Product> {
        val productsList = mutableListOf<Product>()

        firebaseFirestore
            .collection(FirebaseConstants.PRODUCTS_COLLECTION)
            .orderBy("title", Query.Direction.ASCENDING)
            .whereEqualTo("type", ProductTypesConstants.WATCHES)
            .limit(4)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val result = task.result

                    if (result != null) {

                        if (result.documents.size >= 1) {

                            val document = result.documents[result.documents.size - 1]
                            setWatchesLastVisibleDocumentSnapshot(document)
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
                Timber.d("getWatchesProducts Failed $it")
            }
            .await()

        return productsList
    }

    override suspend fun loadWatchesNextPage(
        product: DocumentSnapshot,
        setWatchesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product> {

        val productsList = mutableListOf<Product>()
        firebaseFirestore
            .collection(FirebaseConstants.PRODUCTS_COLLECTION)
            .orderBy("title", Query.Direction.ASCENDING)
            .whereEqualTo("type", ProductTypesConstants.WATCHES)
            .startAfter(product)
            .limit(4)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val result = task.result

                    if (result != null) {

                        if (result.documents.size >= 1) {
                            val document = result.documents[result.documents.size - 1]
                            setWatchesLastVisibleDocumentSnapshot(document)
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
                // send error reports to Firebase Crashlytics
                Timber.d("getWatchesNextPage error $it")
            }
            .await()

        return productsList
    }

}
