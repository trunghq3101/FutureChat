package com.miller.futurechat

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

/**
 * Created by Miller on 25/09/2019
 */

class BaseKoinTest: KoinTest {

    // Use LiveData in test
    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        // Point app to use Firebase Emulator
        val settings = FirebaseFirestoreSettings.Builder()
            .setHost(LOCAL_HOST)
            .setSslEnabled(false)
            .setPersistenceEnabled(false)
            .build()

        loadKoinModules(module {
            single(override = true) { FirebaseFirestore.getInstance().apply {
                firestoreSettings = settings
            }}
        })
    }

    companion object {
        const val LOCAL_HOST = "10.0.2.2:8000"
    }
}