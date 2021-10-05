package com.example.shopapp2.di

import com.example.shopapp2.data.repository.*
import com.example.shopapp2.domain.repository.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLoginRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
    ): LoginRepository {
        return LoginRepositoryImpl(
            firestoreDb = firebaseFirestore,
            firebaseAuth = firebaseAuth,
        )
    }

    @Singleton
    @Provides
    fun provideHomeRepository(
        firebaseFirestore: FirebaseFirestore,
    ): HomeRepository {
        return HomeRepositoryImpl(
            firestoreDb = firebaseFirestore,
        )
    }

    @Singleton
    @Provides
    fun provideBookmarksRepository(
        firebaseFirestore: FirebaseFirestore,
    ): BookmarksRepository {
        return BookmarksRepositoryImpl(
            firebaseFirestore = firebaseFirestore,
        )
    }

    @Singleton
    @Provides
    fun provideMyCartRepository(
        firebaseFirestore: FirebaseFirestore,
    ): MyCartRepository {
        return MyCartRepositoryImpl(
            firebaseFirestore = firebaseFirestore,
        )
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        firebaseFirestore: FirebaseFirestore,
    ): UserRepository {
        return UserRepositoryImpl(
            firebaseFirestore = firebaseFirestore,
        )
    }

    @Singleton
    @Provides
    fun provideOrdersRepository(
        firebaseFirestore: FirebaseFirestore,
        fireRealtimeDatabase: FirebaseDatabase
    ): OrdersRepository {
        return OrdersRepositoryImpl(
            firebaseFirestore = firebaseFirestore,
            firebaseRealtimeDatabase = fireRealtimeDatabase
        )
    }

    @Singleton
    @Provides
    fun provideExploreRepository(
        firebaseFirestore: FirebaseFirestore,
    ): ExploreRepository {
        return ExploreRepositoryImpl(
            firebaseFirestore = firebaseFirestore,
        )
    }
}



