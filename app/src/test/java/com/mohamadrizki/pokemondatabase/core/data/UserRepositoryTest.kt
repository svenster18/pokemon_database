package com.mohamadrizki.pokemondatabase.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mohamadrizki.pokemondatabase.core.data.source.local.LocalDataSource
import com.mohamadrizki.pokemondatabase.core.utils.AppExecutors
import com.mohamadrizki.pokemondatabase.utils.DataDummy
import com.mohamadrizki.pokemondatabase.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var localDataSource: LocalDataSource
    @Mock
    private lateinit var appExecutors: AppExecutors
    private lateinit var userRepository: UserRepository
    private val dummyUser = DataDummy.generateDummyUser()
    private val dummyUserEntity = DataDummy.generateDummyUserEntity()

    @Before
    fun setUp() {
        userRepository = UserRepository.getInstance(localDataSource)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when register Should call register`() = runTest {
        userRepository.register(dummyUser)
        Mockito.verify(localDataSource).register(dummyUserEntity)
    }

    @Test
    fun `when login Should Not Null and Return Success`() = runTest {
        val expectedUser = flowOf( DataDummy.generateDummyUserEntity())
        `when`(localDataSource.login(dummyUserEntity)).thenReturn(expectedUser)
        val actualUser = userRepository.login(dummyUser).first()
        Assert.assertNotNull(actualUser)
        Assert.assertEquals(dummyUser, actualUser)
    }
}