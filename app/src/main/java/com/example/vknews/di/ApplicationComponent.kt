package com.example.vknews.di

import android.content.Context
import com.example.vknews.domain.FeedPost
import com.example.vknews.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance feedPost: FeedPost,
        ): ApplicationComponent
    }

}