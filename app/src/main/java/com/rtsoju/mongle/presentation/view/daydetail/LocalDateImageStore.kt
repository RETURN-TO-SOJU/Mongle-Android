package com.rtsoju.mongle.presentation.view.daydetail

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.rtsoju.mongle.presentation.util.toastLong
import com.rtsoju.mongle.presentation.view.daydetail.model.PhotoPresentationModel
import com.rtsoju.mongle.util.DatetimeFormats
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

/**
 * 특정 일에 추가된 사진만 추출해, MediaStore에서 가져오는 클래스. **반드시 onCreate / onStart에서 객체를 생성해야한다.**
 * Context argument로 인한 Activity 메모리 누수가 발생할 수 있으므로 사용이 끝나고 반드시 메모리 해제.
 * 즉 Activity가 종료된 이후에도 이 클래스를 사용하면 안됨. (메모리 누수)
 */
class LocalDateImageStore(private val activity: ComponentActivity) {
    private val requestPermissionLauncher: ActivityResultLauncher<String>
    private var callback: ((photos: List<PhotoPresentationModel>?) -> Unit)? = null
    private var date: LocalDate = LocalDate.now()

    init {
        requestPermissionLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    callback?.invoke(readPermissionGrantedImages(date))
                } else {
                    activity.toastLong("사진 권한을 부여하지 않아, 사진을 불러올 수 없습니다.")
                }
            }
    }

    fun readMediaStoreImages(
        date: LocalDate,
        callback: (photos: List<PhotoPresentationModel>?) -> Unit
    ) {
        val checkPermission = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (checkPermission == PackageManager.PERMISSION_GRANTED) {
            callback(readPermissionGrantedImages(date))
        } else {
            this.callback = callback
            this.date = date
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun readPermissionGrantedImages(date: LocalDate): List<PhotoPresentationModel>? {
        val instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val epochSeconds = instant.epochSecond
        val epochMilli = instant.toEpochMilli()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.DATE_TAKEN
        )
        val selection = "(${MediaStore.Images.Media.DATE_ADDED} >= ? and " +
                "${MediaStore.Images.Media.DATE_ADDED} <= ?) or (" +
                "${MediaStore.Images.Media.DATE_TAKEN} >= ? and " +
                "${MediaStore.Images.Media.DATE_TAKEN} <= ?)"
        val selectionArgs = arrayOf(
            epochSeconds.toString(),
            (epochSeconds + 86400).toString(),
            epochMilli.toString(),
            (epochMilli + 86400000).toString()
        )
        val query = activity.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )
        query?.use { cursor ->
            return readPhotosFromCursor(cursor, epochMilli)
        }
        return null
    }

    private fun readPhotosFromCursor(
        cursor: Cursor,
        epochMilli: Long
    ): List<PhotoPresentationModel> {
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val takenColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
        val addedColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
        val photoList = mutableListOf<PhotoPresentationModel>()

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val taken = cursor.getLong(takenColumn)
            val added = cursor.getLong(addedColumn)
            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
            )

            if (taken != 0L && (taken < epochMilli || taken > epochMilli + 86400000)) {
                continue
            }

            val addedText = LocalDateTime.ofEpochSecond(added, 0, ZoneOffset.UTC)
                .format(DatetimeFormats.TIME_12)
            photoList.add(PhotoPresentationModel(contentUri.toString(), addedText))
        }

        return photoList
    }
}