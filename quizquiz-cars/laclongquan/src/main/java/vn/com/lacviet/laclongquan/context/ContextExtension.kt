package vn.com.lacviet.laclongquan.context

import android.app.Activity
import android.app.ActivityManager
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.location.LocationManagerCompat
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import vn.com.lacviet.laclongquan.MP3_EXT
import vn.com.lacviet.laclongquan.MP3_PATH
import vn.com.lacviet.laclongquan.data.preference.BasePreference
import java.io.*
import java.nio.charset.Charset
import java.util.*
import kotlin.math.floor

/*
 * Copyright 2024 Hieu Luu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

fun Activity.hideSoftInput() {
    val imm = getSystemService<InputMethodManager>() ?: return
    val windowToken = currentFocus?.windowToken ?: return
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment.hideSoftInput() = requireActivity().hideSoftInput()

@Suppress("DEPRECATION") // Deprecated for third party Services.
fun <T> Context.isServiceRunning(service: Class<T>) =
    (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getRunningServices(Integer.MAX_VALUE)
        .any { it.service.className == service.name }

fun Context.link2App(packageName: String) {
    kotlin.runCatching {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            setPackage("com.android.vending")
        }
        startActivity(intent)
    }.onFailure {
        Toast.makeText(this, "Can not open this app", Toast.LENGTH_SHORT).show()
    }
}

fun Context.dpToPx(dp: Float) =
    resources.displayMetrics.densityDpi * dp / DisplayMetrics.DENSITY_DEFAULT

fun Long?.timestampToMSS(): String {
    if (this == null || this < 0) return "--:--"
    val totalSeconds = floor(this / 1E3).toInt()
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds - (minutes * 60)
    val formatter = "%d:%02d"
    return formatter.format(minutes, remainingSeconds)
}

/**
 * open web page from other browser
 */
fun Context.openBrowser(url: String): Boolean {
    try {
        val webPage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        startActivity(intent)
        return true
    } catch (ignored: Exception) {
        Log.e("openBrowser", "${ignored.localizedMessage} ")
    }
    return false
}

fun Context.mp3Path(fileName: String) = "$filesDir$MP3_PATH/$fileName$MP3_EXT"

fun Context.launchPackage(packageName: String) {
    val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
    if (launchIntent != null) {
        startActivity(launchIntent)
    } else {
        val uri = Uri.parse(String.format("market://details?id=%s", packageName))
        val marketLink = Intent(Intent.ACTION_VIEW, uri)
        startActivity(marketLink)
    }
}

fun Context.goToSetting() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.fromParts("package", applicationContext.packageName ?: "", null)
    startActivity(intent)
}

fun Context.openBluetoothSetting() {
    val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
    startActivity(intent)
}

fun Context.goToLocationSetting() {
    try {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun Context.goToAppOnGooglePlay() {
    val appId = applicationContext.packageName ?: ""
    try {
        val uri = Uri.parse("market://details?id=$appId")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    } catch (e: ActivityNotFoundException) {
        val uri = Uri.parse("https://play.google.com/store/apps/details?id=$appId")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}

@Suppress("DEPRECATION")
fun Context.getAppVersionName(): String {
    val appId = applicationContext.packageName ?: ""
    val appInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(appId, PackageManager.PackageInfoFlags.of(0))
    } else {
        // use for Android OS < TIRAMISU
        packageManager.getPackageInfo(appId, 0)
    }
    return appInfo.versionName
}

@Suppress("DEPRECATION")
fun Context.getAppVersionCode(): String {
    val appId = applicationContext.packageName ?: ""
    val appInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(appId, PackageManager.PackageInfoFlags.of(0))
    } else {
        // use for Android OS < TIRAMISU
        packageManager.getPackageInfo(appId, 0)
    }
    return "(${appInfo.versionCode})"
}

fun Context.isLocationEnabled(): Boolean {
    val locationManager = getSystemService(LOCATION_SERVICE) as? LocationManager
    return locationManager?.let { LocationManagerCompat.isLocationEnabled(it) } ?: false
}

fun Context.hasPermission(vararg permissions: String) = permissions.map {
    ContextCompat.checkSelfPermission(this, it)
}.all { it == PackageManager.PERMISSION_GRANTED }

fun Context.hasSystemFeature(vararg features: String) = features.map {
    packageManager?.hasSystemFeature(it)
}.all { true == it }

fun setLocale(language: String) {
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
}

fun getLocale(): String = if (getApplicationLocales() == "vi") "VN" else "EN"

fun getApplicationLocales(): String = AppCompatDelegate.getApplicationLocales().toLanguageTags()
    .ifEmpty { Locale.getDefault().language }

fun isCheckLanguage(): Boolean {
    return getApplicationLocales() != "vi"
}

fun getLocalFirstTime(): String = AppCompatDelegate.getApplicationLocales().toLanguageTags()

fun setLangForFW(): Byte = if (getApplicationLocales() == "vi") 0x02 else 0x01

fun getDayOfWeek(dayOfWeek: String): String {
    return when (dayOfWeek) {
        "MONDAY" -> "T2"
        "TUESDAY" -> "T3"
        "WEDNESDAY" -> "T4"
        "THURSDAY" -> "T5"
        "FRIDAY" -> "T6"
        "SATURDAY" -> "T7"
        "SUNDAY" -> "CN"
        else -> "T2"
    }
}

fun getMonthShortenVn(month: Int): String {
    return when (month) {
        1 -> "Thg 1"
        2 -> "Thg 2"
        3 -> "Thg 3"
        4 -> "Thg 4"
        5 -> "Thg 5"
        6 -> "Thg 6"
        7 -> "Thg 7"
        8 -> "Thg 8"
        9 -> "Thg 9"
        10 -> "Thg 10"
        11 -> "Thg 11"
        else -> "Thg 12"
    }
}

fun Context.writeDeviceIdToFile(text: String, displayName: String, folder: String) {
    kotlin.runCatching {
        val file = File(filesDir, "1234568")
        file.createNewFile()
        FileOutputStream(file).use { os -> os.write(text.toByteArray()) }

        var values = ContentValues()
        if (Build.VERSION.SDK_INT >= 29) {
            // RELATIVE_PATH is limited, e.g., for MediaStore.Downloads the only option is DIRECTORY_DOWNLOADS
            values.put(
                MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/$folder"
            )
            values.put(MediaStore.Video.Media.IS_PENDING, true)
        } else {
            // on lower system versions the folder must exists, but you can use old-school method instead ("new File")
            values.put(
                MediaStore.Video.Media.DATA,
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .toString() + "/" + Environment.DIRECTORY_DCIM + "/$folder/" + displayName
            )
        }
        values.put(MediaStore.Video.Media.DISPLAY_NAME, displayName)
        val target = if (Build.VERSION.SDK_INT >= 29) {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        } else {
            MediaStore.Files.getContentUri("external")
        }
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/3gpp")
        val uri = contentResolver.insert(target, values) ?: return
        val stream = FileInputStream(file)

        val os = contentResolver.openOutputStream(uri, "rw")
        val b = ByteArray(8192)
        var r: Int
        while (stream.read(b).also { r = it } != -1) {
            os?.write(b, 0, r)
        }
        os?.flush()
        os?.close()
        stream.close()
        if (Build.VERSION.SDK_INT >= 29) {
            values = ContentValues()
            values.put(MediaStore.Video.VideoColumns.IS_PENDING, false)
            contentResolver.update(uri, values, null, null)
        }
    }.onFailure { it.printStackTrace() }
}

fun Context.checkDeviceIdInFile(folder: String): String {
    val projection = arrayOf(MediaStore.Video.Media.DISPLAY_NAME)
    val selection = "${MediaStore.Video.Media.DATA} like ?"
    val selectionArgs = arrayOf("%/DCIM/$folder/%")
    val sortOrder = "${MediaStore.Video.Media.DATE_TAKEN} DESC"
    val queryUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

    val cursor = contentResolver?.query(queryUri, projection, selection, selectionArgs, sortOrder)

    if (cursor != null) {
        while (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
        }
    }
    return ""
}

fun Context.checkNumberOfLoginsFile(folder: String, fileName: String): String {
    val projection = arrayOf(MediaStore.Video.Media.DISPLAY_NAME)
    val selection = "${MediaStore.Video.Media.DATA} like ?"
    val selectionArgs = arrayOf("%/DCIM/$folder/%")
    val sortOrder = "${MediaStore.Video.Media.DATE_TAKEN} DESC"
    val queryUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

    val cursor = contentResolver?.query(queryUri, projection, selection, selectionArgs, sortOrder)

    if (cursor != null) {
        while (cursor.moveToNext()) {
            val dirName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
            if (dirName.contains(fileName))
                return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
        }
    }
    return ""
}

/*
 * get info about app version, android version, device name
 */
fun Context.systemInfo(): Map<String, String> {
    return HashMap<String, String>().apply {
        put("appVersion", getAppVersionCode())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            put("systemVersion", Build.VERSION.RELEASE_OR_CODENAME)
        } else {
            put("systemVersion", Build.VERSION.RELEASE)
        }
        put("systemName", "Android")
        put("deviceType", Build.MODEL)
    }
}

/*
 * create user's action for logging
 */
fun Context.actionInfo(itemName: String): Map<String, String> {
    return HashMap<String, String>().apply {
        put("appVersion", getAppVersionCode())
        put("itemName", itemName)
        put("systemName", "Android")
    }
}

fun Context.isInternetConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // For Android M and later, use the new network capabilities API
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

    return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}

fun Context.getConnectedAudioDevice(): AudioDeviceInfo? {
    val audioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    val devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
    for (device in devices) {
        if (device.type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES ||
            device.type == AudioDeviceInfo.TYPE_WIRED_HEADSET ||
            device.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP ||
            device.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO
        ) {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                device
            } else {
                TODO("VERSION.SDK_INT < P")
            }
        }
    }

    // If no specific device is connected, return the default device name
    return null
}

fun loadJSONFromAsset(context: Context, filename: String): String? {
    var json: String? = null
    try {
        val inputStream: InputStream = context.assets.open(filename)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, Charset.defaultCharset())
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return json
}

fun Context.loadPDF(resourceId: Int, fileName: String): File {
    val inputStream = this.resources.openRawResource(resourceId)
    val pdf = File(this.filesDir, fileName)
    try {
        val outputStream = FileOutputStream(pdf)
        val buffer = ByteArray(1024)
        var length: Int

        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
        outputStream.close()
        inputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return pdf
}