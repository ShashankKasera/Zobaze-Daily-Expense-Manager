package com.example.zobazedailyexpensemanager.di

import android.content.Context
import androidx.room.Room
import com.example.zobazedailyexpensemanager.core.Constants
import com.example.zobazedailyexpensemanager.data.local.Database
import com.example.zobazedailyexpensemanager.data.local.ExpensesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDbName(): String = Constants.ROOM_DB

    @Provides
    fun provideRoomDb(
        @ApplicationContext context: Context,
        name: String
    ): Database = Room.databaseBuilder(context, Database::class.java, name).build()

    @Provides
    fun provideExpensesDao(db: Database): ExpensesDao = db.getExpensesDao()
}