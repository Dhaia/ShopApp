package com.example.shopapp2.di

import android.app.Application
import com.example.shopapp2.domain.repository.*
import com.example.shopapp2.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Singleton
    @Provides
    fun provideLoginUseCase(
        application: Application,
        loginRepository: LoginRepository
    ): LoginUserUseCase {
        return LoginUserUseCase(
            loginRepository,
            application,
        )
    }

    @Singleton
    @Provides
    fun provideGetMostPopularProductsUseCase(
        homeRepository: HomeRepository,
        application: Application,
    ): GetMostPopularProductsUseCase {
        return GetMostPopularProductsUseCase(
            homeRepository,
            application,
        )
    }

    @Singleton
    @Provides
    fun provideAddBookmarkUseCase(
        bookmarksRepository: BookmarksRepository,
        application: Application,
    ): AddBookmarkUseCase {
        return AddBookmarkUseCase(
            bookmarksRepository,
            application,
        )
    }

    @Singleton
    @Provides
    fun provideAddCartProductUseCase(
        cartRepository: MyCartRepository,
        application: Application,
    ): AddCartProductUseCase {
        return AddCartProductUseCase(
            cartRepository,
            application,
        )
    }

    @Singleton
    @Provides
    fun provideUpdateUserDataUseCase(
        userRepository: UserRepository,
        application: Application,
    ): UpdateUserDataUseCase {
        return UpdateUserDataUseCase(
            userRepository,
            application,
        )
    }

    @Singleton
    @Provides
    fun provideAddOrdersUseCase(
        ordersRepository: OrdersRepository,
        application: Application,
    ): AddOrdersUseCase {
        return AddOrdersUseCase(
            ordersRepository,
            application,
        )
    }

    @Singleton
    @Provides
    fun provideGetDiscountsProductsUseCase(
        homeRepository: HomeRepository
    ): GetDiscountsProductsUseCase {
        return GetDiscountsProductsUseCase(
            homeRepository,
        )
    }

    @Singleton
    @Provides
    fun provideGetNewProductsUseCase(
        homeRepository: HomeRepository
    ): GetNewProductsUseCase {
        return GetNewProductsUseCase(
            homeRepository,
        )
    }

    @Singleton
    @Provides
    fun provideGetClothesProductsUseCase(
        exploreRepository: ExploreRepository
    ): GetClothesProductsUseCase {
        return GetClothesProductsUseCase(
            exploreRepository,
        )
    }

    @Singleton
    @Provides
    fun provideGetShoesProductsUseCase(
        exploreRepository: ExploreRepository
    ): GetShoesProductsUseCase {
        return GetShoesProductsUseCase(
            exploreRepository,
        )
    }

    @Singleton
    @Provides
    fun provideGetWatchesProductsUseCase(
        exploreRepository: ExploreRepository
    ): GetWatchesProductsUseCase {
        return GetWatchesProductsUseCase(
            exploreRepository,
        )
    }
}