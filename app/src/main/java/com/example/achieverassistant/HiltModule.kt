package com.example.achieverassistant

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.achieverassistant.achieverGoal.data.AchieverGoalDAO
import com.example.achieverassistant.achieverGoal.data.AchieverGoalDatabase
import com.example.achieverassistant.dailyPlan.data.DailyDAO
import com.example.achieverassistant.dailyPlan.data.DailyTasksDatabase
import com.example.achieverassistant.moments.data.MomentDAO
import com.example.achieverassistant.moments.data.MomentDatabase
import com.example.achieverassistant.quotes.data.QuoteDAO
import com.example.achieverassistant.quotes.data.QuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Scope


@InstallIn(SingletonComponent::class)
@Module
object HiltModule {

    @Provides
    fun provideAchieverDao(database : AchieverGoalDatabase) : AchieverGoalDAO{
        return database.achieverGoalDAO()
    }

    @Provides
    fun provideAchieverDatabase(@ApplicationContext appContext: Context) : AchieverGoalDatabase {
        return Room.databaseBuilder(appContext,
            AchieverGoalDatabase::class.java, "achieverGoalDatabase").build()
    }
    @Provides
    fun provideDailyDao(database : DailyTasksDatabase) : DailyDAO{
        return database.dailyDAO()
    }

    @Provides
    fun provideDailyDatabase(@ApplicationContext appContext: Context) : DailyTasksDatabase {
        return Room.databaseBuilder(appContext,
            DailyTasksDatabase::class.java, "dailyTasksDatabase").build()
    }

    @Provides
    fun provideMomentsDao(database : MomentDatabase) : MomentDAO{
        return database.momentDAO()
    }

    @Provides
    fun provideMomentsDatabase(@ApplicationContext appContext: Context) : MomentDatabase {
        return Room.databaseBuilder(appContext,
            MomentDatabase::class.java, "MomentsDatabase").build()
    }
    @Provides
    fun provideQuotesDao(database : QuoteDatabase) : QuoteDAO{
        return database.quoteDAO()
    }

    @Provides
    fun provideQuotesDatabase(@ApplicationContext appContext: Context) : QuoteDatabase {
        return Room.databaseBuilder(appContext,
            QuoteDatabase::class.java, "quotesDatabase").build()
    }



}