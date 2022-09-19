package com.won983212.mongle.presentation.view.daydetail

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.won983212.mongle.presentation.util.toastLong
import com.won983212.mongle.presentation.view.daydetail.model.Photo
import com.won983212.mongle.util.DatetimeFormats
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

/**
 * 특정 일에 추가된 사진만 추출해, MediaStore에서 가져오는 클래스.
 * Context argument로 인한 Activity 메모리 누수가 발생할 수 있으므로 사용이 끝나고 반드시 메모리 해제.
 * Activity가 종료된 이후에도 이 클래스를 사용하면 안됨. (메모리 누수)
 */
class LocalDateImageStore(
    private val activity: ComponentActivity
) {

    fun readMediaStoreImages(date: LocalDate, callback: (photos: List<Photo>?) -> Unit) {
        val requestPermissionLauncher =
            activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    callback(readPermissionGrantedImages(date))
                } else {
                    activity.toastLong("사진 권한을 부여하지 않아, 사진을 불러올 수 없습니다.")
                }
            }

        val checkPermission = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (checkPermission == PackageManager.PERMISSION_GRANTED) {
            callback(readPermissionGrantedImages(date))
        } else {
            requestPermissionLauncher.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun readPermissionGrantedImages(date: LocalDate): List<Photo>? {
        val instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val epoch = instant.epochSecond
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED
        )
        val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ? and " +
                "${MediaStore.Images.Media.DATE_ADDED} <= ?"
        val selectionArgs = arrayOf(
            epoch.toString(),
            (epoch + 86400).toString()
        )
        val query = activity.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )
        query?.use { cursor ->
            return readPhotosFromCursor(cursor)
        }
        return null
    }

    private fun readPhotosFromCursor(cursor: Cursor): List<Photo> {
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val addedColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
        val photoList = mutableListOf<Photo>()

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val added = cursor.getLong(addedColumn)
            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
            )
            val addedText = LocalDateTime.ofEpochSecond(added, 0, ZoneOffset.UTC)
                .format(DatetimeFormats.TIME_12)
            photoList.add(Photo(contentUri.toString(), addedText))
        }

        return photoList
    }
}