package org.quizquiz.cars.carappservice

import android.content.Intent
import androidx.car.app.CarAppService
import androidx.car.app.Screen
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator

class PlacesCarAppService : CarAppService() {

    override fun createHostValidator(): HostValidator {
        // the ALLOW_ALL_HOSTS_VALIDATOR makes it easy to ensure your app connects but shouldn't be used in production.
        // See the documentation for createHostValidator for more on how to configure this for a production app:
        // https://developer.android.com/reference/androidx/car/app/validation/HostValidator
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    }

    override fun onCreateSession(): Session {
        return PlacesSession()
    }
}

class PlacesSession : Session() {
    override fun onCreateScreen(intent: Intent): Screen {
        return MainScreen(carContext)
    }
}