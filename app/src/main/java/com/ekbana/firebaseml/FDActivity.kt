package com.ekbana.firebaseml

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ekbana.firebaseml.BlurBuilder.blur
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_f_d.*


class FDActivity : AppCompatActivity() {
    var image: Bitmap? = null
    var orginal: Bitmap? = null
    var blurredWholeImage: Bitmap? = null
    var mutableImage: Bitmap? = null
    var rectBitmap: Bitmap? = null

    var height: Int = 0
    var width: Int = 0

    private val imageView by lazy { findViewById<ImageView>(R.id.face_detection_image_view) }

    private val bottomSheetButton by lazy { findViewById<FrameLayout>(R.id.bottom_sheet_button) }

    private val faceDetectionModels = ArrayList<FaceDetectionModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_f_d)

        bottomSheetButton.setOnClickListener {
            CropImage.activity().start(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK) {
                val imageUri = result.uri
                analyzeImage(MediaStore.Images.Media.getBitmap(contentResolver, imageUri))
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(
                    this,
                    "There was some error : ${result.error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun analyzeImage(image: Bitmap?) {
        orginal = image
        if (image == null) {
            Toast.makeText(this, "There was some error", Toast.LENGTH_SHORT).show()
            return
        }

        imageView?.setImageBitmap(null)
        faceDetectionModels.clear()
        showProgress()

        val firebaseVisionImage = FirebaseVisionImage.fromBitmap(image)
        val options = FirebaseVisionFaceDetectorOptions.Builder()
            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
            .build()
        val faceDetector = FirebaseVision.getInstance().getVisionFaceDetector(options)
        faceDetector.detectInImage(firebaseVisionImage)
            .addOnSuccessListener {
                mutableImage = image?.copy(Bitmap.Config.ARGB_8888, true)
                blurredWholeImage = blur(this, image)
                //  mutableImage.
                imageView.setImageBitmap(mutableImage)
                hideProgress()
                detectFaces(it, mutableImage)
            }
            .addOnFailureListener {
                Toast.makeText(this, "There was some error", Toast.LENGTH_SHORT).show()
                hideProgress()
            }
    }

    private fun detectFaces(faces: List<FirebaseVisionFace>?, image: Bitmap?) {
        this.image = image
        if (faces == null || image == null) {
            Toast.makeText(this, "There was some error", Toast.LENGTH_SHORT).show()
            return
        }

        val canvas = Canvas(image)
        val facePaint = Paint()
        facePaint.color = Color.MAGENTA
        facePaint.style = Paint.Style.STROKE
        facePaint.strokeWidth = 8F
        val faceTextPaint = Paint()
        faceTextPaint.color = Color.RED
        faceTextPaint.textSize = 40F
        faceTextPaint.typeface = Typeface.DEFAULT_BOLD
        val landmarkPaint = Paint()
        landmarkPaint.color = Color.RED
        landmarkPaint.style = Paint.Style.FILL
        landmarkPaint.strokeWidth = 8F

        for ((index, face) in faces.withIndex()) {
            //  canvas.drawRect(face.boundingBox, facePaint)
            /*  canvas.drawText(
                  "Face$index",
                  (face.boundingBox.centerX() - face.boundingBox.width() / 2) + 8F,
                  (face.boundingBox.centerY() + face.boundingBox.height() / 2) - 8F,
                  faceTextPaint
              )*/

            val bounds = face.boundingBox
            val w = bounds.right.minus(bounds.left)
            val h = bounds.bottom.minus(bounds.top)

            val bmp = BitmapFactory.decodeResource(resources, R.drawable.xi_jin_ping)
            val croppedBmp: Bitmap = Bitmap.createBitmap(image, bounds.left, bounds.top, w, h)
            val blurryCrop = blur(this, croppedBmp)

            //Calculate proportional size, or make the method accept just a factor of scale.
            val small = getResizedBitmap(croppedBmp, 10, 10)
            val pixelated = small?.let { getResizedBitmap(it, w, h) }
            //Recycle small, recycle original if no longer needed.

            val dst = Rect()
            dst.set(bounds)

            pixelated?.let { canvas.drawBitmap(it, null, dst, null) }
            imv2?.setImageBitmap(image)
        }
    }

    private fun showProgress() {
        findViewById<View>(R.id.bottom_sheet_button_image).visibility = View.GONE
        findViewById<View>(R.id.bottom_sheet_button_progress).visibility = View.VISIBLE
    }

    private fun hideProgress() {
        findViewById<View>(R.id.bottom_sheet_button_image).visibility = View.VISIBLE
        findViewById<View>(R.id.bottom_sheet_button_progress).visibility = View.GONE
    }

    fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(
            bm, 0, 0, width, height, matrix, false
        )
    }
}
