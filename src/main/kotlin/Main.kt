package org.example
import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDCIDFont
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts
import org.apache.pdfbox.text.PDFTextStripper
import java.awt.Font
import java.io.File
import java.util.regex.Matcher
import java.util.regex.Pattern


fun main() {
    // PDF file
    var list: MutableList<Int> = mutableListOf()
    val fileName = File("src/main/BPI.pdf")
    val doc: PDDocument = Loader.loadPDF(fileName)
    val stripper = PDFTextStripper()
    val text = stripper.getText(doc)

    // StringBuilder para guardar o texto extraido
    val sb = StringBuilder()


    // adicionar o text ao StringBuilder do PDF
    sb.append(stripper.getText(doc))


    // Regex-> Vai defenir o padrao de que estamos a procura neste caso uma data dd-MM-yyyy
    val p: Pattern= Pattern.compile("\\d{2}-\\d{2}-\\d{4}")


    // Encontra o primeiro index do que estamos a procura
    val m: Matcher = p.matcher(sb)
    while (m.find()) {
       //adiciona a uma lista todos as posicoes inicias de uma data encontradas num documento
        list.add(m.start())
    }
    // PDF comeca com uma data nao importante por isso temos que remover primeira localizacao de uma data
    list.removeFirst()


    if (doc != null) {
        doc.close()
    }
    for(i in list){
        println(text.substring(i,))
    }
}