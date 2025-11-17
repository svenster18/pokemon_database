package com.mohamadrizki.pokemondatabase.core.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mohamadrizki.pokemondatabase.utils.DataDummy
import com.mohamadrizki.pokemondatabase.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LocalDataSourceTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var userHelper: UserDao
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        userHelper = FakeUserHelper()

        localDataSource = LocalDataSource.getInstance(userHelper)
    }

    @Test
    fun `when register ShouldExist in login`() = runTest {
        val expectedUser = DataDummy.generateDummyUserEntity()
        localDataSource.register(expectedUser)
        val actualUser = localDataSource.login(expectedUser).first()
        Assert.assertEquals(expectedUser.name, actualUser?.name)
    }
}