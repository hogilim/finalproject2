package com.example.finalproject2.ui

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.finalproject2.BuildConfig
import com.example.finalproject2.databinding.ActivityCamBinding
import com.example.finalproject2.databinding.ActivityFoundBinding
import com.example.finalproject2.databinding.ActivityMainBinding
import com.example.finalproject2.retrofit2.RetrofitClient
import com.example.finalproject2.retrofit2.RetrofitService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DogFound: AppCompatActivity() {
    val retrofit = RetrofitClient.getInstnace() //
    val myAPI = retrofit.create(RetrofitService::class.java)
    val REQUEST_TAKE_PHOTO = 1
    var mCurrentPhotoPath: String = ""
    var memberId : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFoundBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val location_gu = resources.getStringArray(com.example.finalproject2.R.array.gu)
        val adapter_gu = ArrayAdapter<String>(this, R.layout.simple_list_item_1, location_gu)
        binding.selectGu.adapter = adapter_gu
        memberId = intent.getLongExtra("memberId", -1)
        takePicture(binding)
        confirm(binding,location_gu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val binding = ActivityFoundBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val location_gu = resources.getStringArray(com.example.finalproject2.R.array.gu)
        val adapter_gu = ArrayAdapter<String>(this, R.layout.simple_list_item_1, location_gu)
        binding.selectGu.adapter = adapter_gu
        takePicture(binding)
        confirm(binding,location_gu)
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
                                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
                            val exifDegree = exifOrientationToDegrees(exifOrientation)
                            myBitmap = rotate(myBitmap, exifDegree)
                            var bs = ByteArrayOutputStream()
                            var image = myBitmap.compress(Bitmap.CompressFormat.JPEG, 20,bs)
                            binding.picture.setImageBitmap(myBitmap)
                            //binding.rcode.setText(myBitmap.toString())
                        }
                        else {
                            Log.d("myBitmap null", "null")
                        }
                    }
                }
            }
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }

    private fun confirm(binding: ActivityFoundBinding, location_gu:Array<String>){
        var selected_gu = ""
        var gender = "unknown"
        var isExistBlank = false
        binding.confirm.setOnClickListener {
            binding.selectGu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selected_gu = location_gu[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
            if(binding.title.text.toString().isEmpty()||selected_gu==""){
                Toast.makeText(this, "모든 정보를 작성해주세요.", Toast.LENGTH_SHORT).show()
                isExistBlank = true
            }
            else{
                isExistBlank = false
            }
            binding.gender.setOnCheckedChangeListener { group, checkedId ->
                when(checkedId) {
                    binding.male.id->{
                        gender = "male"
                    }
                    binding.female.id->{
                        gender = "female"
                    }
                    else -> {
                        gender = "unknown"
                    }
                }
            }
            if(!isExistBlank){
                ///////////////////
                val memberId = MultipartBody.Part.createFormData("memberId", memberId.toString())
                val t = binding.picture.getDrawable() as BitmapDrawable
                val b = t.bitmap
                val ba = ByteArrayOutputStream()
                b.compress(Bitmap.CompressFormat.JPEG, 20, ba)
                val rb = RequestBody.create("image/jpg".toMediaTypeOrNull(),ba.toByteArray())
                val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val fileName = "photo" + timestamp +".jpeg"
                val u = MultipartBody.Part.createFormData("files",fileName ,rb)
                myAPI.found(
                    memberId,
                    //title,
                    //gender,
                    //content,
                    u
                ).enqueue(object : Callback<BoardUnit> {

                    //이때 onFaliure는 Call을 서버쪽으로 아예 보내지 못한 경우입니다.
                    override fun onFailure(call: Call<BoardUnit>, t: Throwable) {
                        Toast.makeText(this@DogFound,"오류 다시 시도하세요.", Toast.LENGTH_SHORT).show()
                        println("DogLost.onFailure -----")
                        println(t.message)
                    }

                    //만약 보낸 것이 성공했을 경우는 resonse를 가지고 들어옵니다.
                    //그리고 call을 때릴 때 RawResponseData로 갔으니까 Reponse도 그 타입을 가지고 옵니다.
                    override fun onResponse(
                        call: Call<BoardUnit>,
                        response: Response<BoardUnit>
                    ) {

                        println("response : ${response.errorBody()}")
                        println("response : ${response.message()}")
                        println("response : ${response.code()}")
                        println("response : ${response.raw().request.url.toUrl()}")
                        println("response : ${response.body()}")

                    }
                })
                ////////////////////
                Toast.makeText(this@DogFound, "신고 성공.${gender}", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun takePicture(binding: ActivityFoundBinding) {
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
                        this@DogFound,
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