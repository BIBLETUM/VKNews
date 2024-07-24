package com.example.vknews.presentation

import android.app.Application
import com.example.vknews.di.ApplicationComponent
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.StatisticItem
import com.example.vknews.domain.StatisticType

class NewsFeedApplication : Application() {

    val component: ApplicationComponent by lazy {

        TODO()
//        DaggerApplicationComponent.factory().create(
//            this,
//            FeedPost(0, 0, "", "", "", "", "", listOf(StatisticItem(StatisticType.SHARES, 0)), true)
//        )
    }

}