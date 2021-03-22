package com.ekbana.firebaseml

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import kotlinx.android.synthetic.main.activity_face_detection.*

class FaceDetectionActivity : AppCompatActivity(), View.OnClickListener {
    val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
    private val PICK_IMAGE_REQUEST = 71
    var bitMapImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_detection)
        initListener()
        imvImage.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Choose image")
                .setItems(options) { dialog, item ->
                    when {
                        options[item] == getString(R.string.take_photo) -> {
                            val takePhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(takePhoto, 0)

                        }
                        options[item] == getString(R.string.choose_from_gallery) -> {
                            val intent = Intent()
                            intent.type = "image/*"
                            intent.action = Intent.ACTION_GET_CONTENT
                            startActivityForResult(
                                Intent.createChooser(intent, "Select Picture"),
                                1
                            )
                            /* val chooseImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                             startActivityForResult(chooseImage, 1)*/

                        }
                        options[item] == getString(R.string.cancel) -> {
                            dialog.dismiss()

                        }
                    }
                }
            alertDialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CANCELED) {
            when (requestCode) {
                0 -> {
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        bitMapImage = data.extras?.get("data") as Bitmap
                        imvImage.setImageBitmap(bitMapImage)
                    }
                }
                1 -> {
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        val selectedImage = data.data
                        bitMapImage =
                            MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)

                        imvImage.setImageBitmap(bitMapImage)
                    }

                }
            }
        }
    }

    private fun initListener() {
        btnDetectFace?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnDetectFace -> {
                val image = bitMapImage?.let { FirebaseVisionImage.fromBitmap(it) }

                //val detector = FirebaseVision.getInstance().visionFaceDetector

                // face classification and landmark detection
                val options = FirebaseVisionFaceDetectorOptions.Builder()
                    .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                    .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                    .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
                    .build()// contour detection
                /* val options = FirebaseVisionFaceDetectorOptions.Builder()
                     .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
                     .build()*/
                val detector = FirebaseVision.getInstance().getVisionFaceDetector(options)

                if (image != null) {
                    detector.detectInImage(image)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                            val mutableImage = bitMapImage?.copy(Bitmap.Config.ARGB_8888, true)

                            detectFaces(it, mutableImage)

                            imvImage.setImageBitmap(mutableImage)
                        }
                        .addOnFailureListener {
                            // Task failed with an exception
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }

    private fun detectFaces(faces: List<FirebaseVisionFace>, image: Bitmap?) {
        if (faces == null || image == null) {
            Toast.makeText(this, "There was some error", Toast.LENGTH_SHORT).show()
            return
        }

        val canvas = Canvas(image)
        val facePaint = Paint()
        facePaint.color = Color.RED
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

            canvas.drawRect(face.boundingBox, facePaint)
            canvas.drawText(
                "Face$index",
                (face.boundingBox.centerX() - face.boundingBox.width() / 2) + 8F,
                (face.boundingBox.centerY() + face.boundingBox.height() / 2) - 8F,
                faceTextPaint
            )
        }

    }
}
