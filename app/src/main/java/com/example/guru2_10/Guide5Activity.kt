package com.example.guru2_10

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import android.view.ViewGroup

class Guide5Activity : AppCompatActivity() {

    private lateinit var pdfRecyclerView: RecyclerView
    private lateinit var tvGuideLink: TextView
    private val pdfPageList = mutableListOf<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide5)

        // 제목 설정
        val tvTitle: TextView = findViewById(R.id.g5tvTitle)
        tvTitle.text = "재활용품 분리배출 가이드라인"

        // RecyclerView 설정
        pdfRecyclerView = findViewById(R.id.g5pdfRecyclerView)
        pdfRecyclerView.layoutManager = LinearLayoutManager(this)

        // 가이드 보러가기 텍스트 클릭 이벤트 설정
        tvGuideLink = findViewById(R.id.g5vGuideLink)
        tvGuideLink.setOnClickListener {
            showPdf() // 클릭 시 PDF 표시
        }

        // PDF 파일 assets에서 cache로 복사
        copyPdfToCache()
    }

    private fun copyPdfToCache() {
        val pdfFile = File(cacheDir, "guide.pdf")
        if (!pdfFile.exists()) {
            try {
                assets.open("guide.pdf").use { input ->
                    pdfFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "PDF 파일을 로드하는 중 오류 발생!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPdf() {
        try {
            pdfRecyclerView.visibility = View.VISIBLE // RecyclerView 보이도록 설정

            val pdfFile = File(cacheDir, "guide.pdf")
            val fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(fileDescriptor)

            pdfPageList.clear()

            for (i in 0 until pdfRenderer.pageCount) {
                val page = pdfRenderer.openPage(i)
                val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                pdfPageList.add(bitmap)
                page.close()
            }

            pdfRenderer.close()
            fileDescriptor.close()

            pdfRecyclerView.adapter = PdfAdapter(pdfPageList)

        } catch (e: Exception) {
            Toast.makeText(this, "PDF를 불러오는 중 오류 발생!", Toast.LENGTH_SHORT).show()
        }
    }

    class PdfAdapter(private val pdfPageList: List<Bitmap>) : RecyclerView.Adapter<PdfAdapter.PdfViewHolder>() {

        inner class PdfViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
            val imageView = ImageView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                adjustViewBounds = true
            }
            return PdfViewHolder(imageView)
        }

        override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
            holder.imageView.setImageBitmap(pdfPageList[position])
        }

        override fun getItemCount(): Int = pdfPageList.size
    }
}
