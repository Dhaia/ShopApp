package com.example.shopapp2.di

import android.app.Application
import android.content.Context
import com.example.shopapp2.BaseApplication
import com.example.shopapp2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseRealtimeDatabase(
        application: Application
    ): FirebaseDatabase {
        return FirebaseDatabase.getInstance(
            application.getString(R.string.firebase_realtime_database_link)
        )
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    // Provide Shared Prefs
    /*
    @Singleton
    @Provides
    fun provideMySharedPreferences(
        @ApplicationContext context: Context
    ): MySharedPreferences {
        return MySharedPreferences(
            context = context
        )
    }
     */
}

/*
class MySharedPreferences(
    @ApplicationContext context: Context
) {
    val sharedPref = context.getSharedPreferences(
        "com.example.shopapp.PREFERENCE_FILE_KEY",
        Context.MODE_PRIVATE
    )
}
 */
