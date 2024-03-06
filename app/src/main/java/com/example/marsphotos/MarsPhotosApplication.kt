/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.marsphotos.DataBase.DatabaseSicenet
import com.example.marsphotos.data.AppContainer
import com.example.marsphotos.data.DefaultAppContainer
import com.example.marsphotos.data.ServiceLocator

class MarsPhotosApplication : Application() {

    lateinit var container: AppContainer
    lateinit var room: DatabaseSicenet

    override fun onCreate() {
        super.onCreate()

        Log.i("App start", "App initialization")
        ServiceLocator.context = applicationContext

        room = Room.databaseBuilder(ServiceLocator.context, DatabaseSicenet::class.java, "SICENET").build()
        Log.i("App start 2", "App initialization con base dedatos")

        // Verificar si la base de datos está abierta
        if (room.isOpen) {
            Log.i("App start 2", "App initialization con base de datos")
        } else {
            Log.e("App start 2", "Error al inicializar la base de datos")
        }
        container = DefaultAppContainer()


       // LocatorAlumnos.context=applicationContext
    }



}
