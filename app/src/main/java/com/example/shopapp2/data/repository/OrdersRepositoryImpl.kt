package com.example.shopapp2.data.repository

import com.example.shopapp2.common.FirebaseConstants
import com.example.shopapp2.domain.models.Order
import com.example.shopapp2.domain.repository.OrdersRepository
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber


class OrdersRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseRealtimeDatabase: FirebaseDatabase
) : OrdersRepository {

    override suspend fun addOrders(
        ordersList: MutableList<Order>,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        val collectionRef = firebaseFirestore
            .collection(FirebaseConstants.ORDERS_COLLECTION)

        val rootRef = firebaseRealtimeDatabase.reference
        val countRef = rootRef
            .child("counters")
            .child("orders")
            .child("count")

        // Add multiple documents at once using batch writes
        firebaseFirestore.runBatch { batch ->
            for (order in ordersList) {
                val documentRef = collectionRef.document()
                batch.set(documentRef, order)
            }
        }.addOnSuccessListener {
            onSuccess()
            // After we successfully add the orders to Firestore
            // We update a field inside of FirebaseRealtimeDatabase that keep track of how many
            // Orders we have in the Orders colletion
            countRef
                .runTransaction(object : Transaction.Handler {
                    override fun doTransaction(
                        mutableData: MutableData
                    ): Transaction.Result {
                        val count =
                            mutableData.getValue(Int::class.java) ?: return Transaction.success(
                                mutableData
                            )
                        mutableData.value = count + ordersList.size
                        return Transaction.success(mutableData)
                    }
                    override fun onComplete(
                        error: DatabaseError?, b: Boolean,
                        dataSnapshot: DataSnapshot?
                    ) {
                        Timber.d("Updating count value in realtime database ${error?.message}")
                    }
                })
        }.addOnFailureListener { exception ->
            onFailure(exception.toString())
            Timber.d("addOrders Failed $exception")

        }
            .await()
    }

}