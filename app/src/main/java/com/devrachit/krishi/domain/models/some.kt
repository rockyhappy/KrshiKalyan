package com.devrachit.krishi.domain.models

import dagger.Component
import dagger.Provides
import javax.inject.Singleton

@Component
@SharedViewModelComponent.ContentProviderScope // Adjust scope if needed
interface SharedViewModelComponent {
    annotation class ContentProviderScope

    @Provides
    @Singleton
    fun provideSharedViewModel(): SharedViewModel
}