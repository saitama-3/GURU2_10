package com.example.guru2

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

class guide5Activity : AppCompatActivity() {

    private lateinit var pdfRecyclerView: RecyclerView
    private lateinit var tvGuideLink: TextView
    private val pdfPageList = mutableListOf<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide5)

        // 제목 설정
        val tvTitle: TextView = findViewById(R.id.tvTitle)
        tvTitle.text = "재활용품 분리배출 가이드라인"

        // RecyclerView 설정
        pdfRecyclerView = findViewById(R.id.pdfRecyclerView)
        pdfRecyclerView.layoutManager = LinearLayoutManager(this)

        // 가이드 보러가기 텍스트 클릭 이벤트 설정
        tvGuideLink = findViewById(R.id.tvGuideLink)
        tvGuideLink.setOnClickListener {
            // 텍스트 클릭 시 PDF 보이도록 처리
            showPdf()
        }

        // PDF 파일 준비
        val pdfFile = File(cacheDir, "guide.pdf")
        if (!pdfFile.exists()) {
            assets.open("guide.pdf").use { input ->
                pdfFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    private fun showPdf() {
        try {
            // RecyclerView 보이도록 설정
            pdfRecyclerView.visibility = View.VISIBLE

            // PDF 렌더링
            val pdfFile = File(cacheDir, "guide.pdf")
            val fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(fileDescriptor)

            // 기존 페이지 리스트 초기화
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

            // RecyclerView 어댑터 설정
            pdfRecyclerView.adapter = PdfAdapter(pdfPageList)
        } catch (e: Exception) {
            Toast.makeText(this, "PDF 파일을 불러오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // PdfAdapter 정의
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
            // PDF 페이지를 이미지로 설정
            holder.imageView.setImageBitmap(pdfPageList[position])
        }

        override fun getItemCount(): Int = pdfPageList.size // PDF의 총 페이지 수 반환
    }
}
