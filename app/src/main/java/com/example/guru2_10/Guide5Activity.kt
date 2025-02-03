package com.example.guru2_10

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater

class Guide5Activity : AppCompatActivity() {

    private lateinit var pdfRecyclerView: RecyclerView
    private lateinit var tvTitle: TextView
    private val pdfPageList = mutableListOf<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide5)

        // 제목 설정
        tvTitle = findViewById(R.id.g5tvTitle)
        tvTitle.text = "재활용품 분리배출 가이드라인"

        // RecyclerView 설정
        pdfRecyclerView = findViewById(R.id.g5pdfRecyclerView)
        pdfRecyclerView.layoutManager = LinearLayoutManager(this)

        // PDF를 cacheDir에 복사
        copyPdfToCache()

        // PDF 파일을 화면에 표시
        loadPdfPages()
    }

    // assets에서 cacheDir로 PDF 파일 복사
    private fun copyPdfToCache() {
        try {
            val assetManager = assets
            val inputStream = assetManager.open("guide.pdf")
            val outputFile = File(cacheDir, "guide.pdf")

            inputStream.use { input ->
                outputFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "PDF 복사 중 오류 발생: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // PDF 파일을 화면에 표시
    private fun loadPdfPages() {
        try {
            val pdfFile = File(cacheDir, "guide.pdf")

            if (!pdfFile.exists()) {
                Toast.makeText(this, "PDF 파일이 존재하지 않습니다!", Toast.LENGTH_SHORT).show()
                return
            }

            val fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(fileDescriptor)

            pdfPageList.clear()

            // 모든 페이지를 비트맵 리스트로 변환
            for (i in 0 until pdfRenderer.pageCount) {
                val page = pdfRenderer.openPage(i)
                val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                pdfPageList.add(bitmap)
                page.close()
            }

            pdfRenderer.close()
            fileDescriptor.close()

            // RecyclerView에 adapter 설정
            pdfRecyclerView.adapter = PdfAdapter(pdfPageList)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "PDF를 불러오는 중 오류 발생: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // RecyclerView Adapter 클래스
    class PdfAdapter(private val pdfPageList: List<Bitmap>) : RecyclerView.Adapter<PdfAdapter.PdfViewHolder>() {

        inner class PdfViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imageView: ImageView = view.findViewById(R.id.pdfPageImageView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
            val view = LayoutInflater.from(parent.context) // Context를 가져와서 LayoutInflater 사용
                .inflate(R.layout.item_pdf_page, parent, false)
            return PdfViewHolder(view)
        }

        override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
            holder.imageView.setImageBitmap(pdfPageList[position]) // 각 페이지 이미지를 설정
        }

        override fun getItemCount(): Int = pdfPageList.size
    }
}
