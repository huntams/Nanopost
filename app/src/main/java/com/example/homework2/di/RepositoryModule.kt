package com.example.homework2.di

import com.example.homework2.domain.repository.ProfileRepository
import com.example.homework2.data.remote.repository.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule() {

    @Binds
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl) : ProfileRepository
}