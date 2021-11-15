package com.example.finalproject2.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.finalproject2.BuildConfig
import com.example.finalproject2.databinding.ActivityCamBinding
import com.example.finalproject2.databinding.ActivityFoundBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class TakePicture : AppCompatActivity() {
    val REQUEST_TAKE_PHOTO = 1
    var mCurrentPhotoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityFoundBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.selectPicture.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 카메라 실행 부분
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    dispatchTakePictureIntent()
                    //val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    //startActivityForResult(cameraIntent, 1)
                } else {
                    Log.d("test", "권한 설정 요청")
                    ActivityCompat.requestPermissions(
                        this@TakePicture,
                        arrayOf<String?>(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        1
                    )
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val binding = ActivityCamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onActivityResult(requestCode, resultCode, data)
        //findViewById(R.id.result_image).setImageURI(photoUri);
        try {
            when (requestCode) {
                REQUEST_TAKE_PHOTO -> {
                    if (resultCode == Activity.RESULT_OK) {
                        val file = File(mCurrentPhotoPath)
                        var myBitmap = MediaStore.Images.Media
                            .getBitmap(contentResolver, Uri.fromFile(file))
                        if (myBitmap != null) {
                            val exif = mCurrentPhotoPath?.let { ExifInterface(it) }
                            val exifOrientation: Int = exif!!.getAttributeInt(
                                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
                            )
                            val exifDegree = exifOrientationToDegrees(exifOrientation)
                            myBitmap = rotate(myBitmap, exifDegree)
                            var bs = ByteArrayOutputStream()
                            var image = myBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bs)
                            binding.image.setImageBitmap(myBitmap)
                            binding.rcode.setText(myBitmap.toString())
                        } else {
                            Log.d("myBitmap null", "null")
                        }
                    }
                }
            }
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) { // 파일을 만드는데 오류가 발생한 경우
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
    }

    fun exifOrientationToDegrees(exifOrientation: Int): Int {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270
        }
        return 0
    }

    fun rotate(bitmap: Bitmap?, degrees: Int): Bitmap? { // 이미지 회전 및 이미지 사이즈 압축
        var bitmap = bitmap
        if (degrees != 0 && bitmap != null) {
            val m = Matrix()
            m.setRotate(
                degrees.toFloat(), bitmap.width.toFloat() / 2,
                bitmap.height.toFloat() / 2
            )
            try {
                val converted = Bitmap.createBitmap(
                    bitmap, 0, 0,
                    bitmap.width, bitmap.height, m, true
                )
                if (bitmap != converted) {
                    bitmap.recycle()
                    bitmap = converted
                    val options = BitmapFactory.Options()
                    options.inSampleSize = 4
                    bitmap = Bitmap.createScaledBitmap(bitmap, 1280, 1280, true) // 이미지 사이즈 줄이기
                }
            } catch (ex: OutOfMemoryError) {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
            }
        }
        return bitmap
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timestamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        mCurrentPhotoPath = image.absolutePath
        return image
    }
}
