package com.miller.futurechat.di

import com.google.firebase.firestore.FirebaseFirestore
import com.miller.data.repository.FirebaseRepository
import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module

/**
 * Created by Miller on 18/09/2019
 */

val repositoryModule = module {
    factory { CompositeDisposable() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseRepository(get()) }
}