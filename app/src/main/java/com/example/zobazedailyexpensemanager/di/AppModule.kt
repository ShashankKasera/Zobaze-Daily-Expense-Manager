package com.example.zobazedailyexpensemanager.di

import android.content.Context
import androidx.room.Room
import com.example.zobazedailyexpensemanager.core.Constants
import com.example.zobazedailyexpensemanager.data.local.Database
import com.example.zobazedailyexpensemanager.data.local.ExpensesDao
import com.example.zobazedailyexpensemanager.data.repository.ExpensesRepository
import com.example.zobazedailyexpensemanager.data.repository.ExpensesRepositoryImpl
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
    fun provideDbName(): String = Constants.ROOM_DB

    @Singleton
    @Provides
    fun provideRoomDb(
        @ApplicationContext context: Context,
        name: String
    ): Database = Room.databaseBuilder(context, Database::class.java, name).build()

    @Singleton
    @Provides
    fun provideExpensesDao(db: Database): ExpensesDao = db.getExpensesDao()

    @Singleton
    @Provides
    fun provideAddExpensesRepository(addExpensesRepositoryImpl: ExpensesRepositoryImpl): ExpensesRepository =
        addExpensesRepositoryImpl
}