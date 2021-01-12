package com.example.paint

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.TextView
import android.view.Menu
import android.view.MenuItem
import com.example.paint.BallsView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    var counter1 = 0
    private var bitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1);
        }

        setContentView(R.layout.activity_main)

        val textViewCount1 = findViewById<TextView>(R.id.count)

        val ballsView1 = findViewById<BallsView>(R.id.ballsView1)

        ballsView1.setOnValueChange = {

           textViewCount1.text = it.toInt().toString()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.share,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_share){

            val ballsView1 = findViewById<BallsView>(R.id.ballsView1)

            val bitmap = Bitmap.createBitmap(ballsView1.width, ballsView1.height, Bitmap.Config.ARGB_8888)
            val c = Canvas(bitmap)
            ballsView1.draw(c)

            sharePalette(bitmap)


            val uri = sharePalette(bitmap)

            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
            }
            startActivity(Intent.createChooser(shareIntent, "bitmap"))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sharePalette (bitmap: Bitmap) : Uri {

        val bitmapPath = MediaStore.Images.Media.insertImage(contentResolver, bitmap, (System.currentTimeMillis().toString() + ".jpeg"), "Share Bitmap")

        val bitmapUri = Uri.parse(bitmapPath)

        return bitmapUri
    }


}