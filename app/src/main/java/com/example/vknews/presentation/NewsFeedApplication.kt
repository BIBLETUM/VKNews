package com.example.vknews.presentation

import android.app.Application
import com.example.vknews.di.ApplicationComponent
import com.example.vknews.di.DaggerApplicationComponent

class NewsFeedApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            this
        )
    }

}