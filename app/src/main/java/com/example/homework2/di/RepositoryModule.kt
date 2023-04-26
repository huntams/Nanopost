package com.example.homework2.di

import com.example.homework2.data.remote.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule() {

    @Binds
    @Singleton
    abstract fun bindProfileRepository() : ProfileRepository
}