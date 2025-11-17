package com.mohamadrizki.pokemondatabase.core.data.source.local.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.couchbase.lite.Collection
import com.couchbase.lite.Database
import com.mohamadrizki.pokemondatabase.core.data.source.local.db.DatabaseContract.UserColumns.Companion.COLLECTION_NAME
import com.mohamadrizki.pokemondatabase.core.data.source.local.db.DatabaseHelper.Companion.DATABASE_NAME
import com.mohamadrizki.pokemondatabase.utils.DataDummy
import com.mohamadrizki.pokemondatabase.utils.MainDispatcherRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.internal.wait
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserHelperTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userHelper: UserHelper

    private val sampleUser = DataDummy.generateDummyUserEntity()
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun initDb() {
        Dispatchers.setMain(testDispatcher)
        userHelper = UserHelper(
            ApplicationProvider.getApplicationContext()
        )
        userHelper.open()
    }

    @After
    fun closeDb() {
        userHelper.close()
        Dispatchers.resetMain()
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun register_Success() = runTest {
        val docId = userHelper.register(sampleUser)
        val actualUser = userHelper.getUser(docId)
        assertEquals(sampleUser.name, actualUser?.name)
    }

    @Test
    fun login_Success() = runTest {
        userHelper.register(sampleUser)
        val loggedInUser = userHelper.login(sampleUser).first()
        assertEquals(sampleUser.name, loggedInUser.name)
    }
}