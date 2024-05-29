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
import java.io.FileWriter
import java.util.regex.Matcher
import java.util.regex.Pattern


fun main() {
    // PDF file
    var list: MutableList<Int> = mutableListOf()
    var list2: MutableList<Int> = mutableListOf()
    val fileName = File("src/main/BPI.pdf")
    val doc: PDDocument = Loader.loadPDF(fileName)
    val stripper = PDFTextStripper()
    val text = stripper.getText(doc)
    val fileCsv: File = File("src/main/kotlin/temp.csv")

    // StringBuilder para guardar o texto extraido
    val sb = StringBuilder()
    val sb2 = StringBuilder()


    // adicionar o text ao StringBuilder do PDF
    sb.append(stripper.getText(doc))
    sb2.append(stripper.getText(doc ))

    // Regex-> Vai defenir o padrao de que estamos a procura neste caso uma data dd-MM-yyyy
    val p: Pattern = Pattern.compile("\\d{2}-\\d{2}-\\d{4}")
    // Regex-> Vai defenir o padrao de que estamos a procura neste caso um numero x,xx
    val p2: Pattern = Pattern.compile("\\d{1},\\d{2}")


    // Encontra o primeiro index do que estamos a procura
    val m: Matcher = p.matcher(sb)
    while (m.find()) {
        //adiciona a uma lista todos as posicoes inicias de uma data encontradas num documento
        list.add(m.start())
    }
    // PDF comeca com uma data nao importante por isso temos que remover primeira localizacao de uma data, alem disto para extratos do banco a data e repetida 2 vezes em uma linha, para bom funcionamento do programa vamos ignorar e retirar da lista essas datas
    list.removeFirst()
    fun <T> removeValuesAtEvenPositions(list: List<T>): List<T> {
        return list.filterIndexed { index, _ -> (index + 1) % 2 != 0 }
    }
    fun <T> removeValuesAtOddPositions(list: List<T>): List<T> {
        return list.filterIndexed { index, _ -> (index + 1) % 2 == 0 }
    }
    fun containsLetters(input: String): Boolean {
        return input.any{ char -> char.isLetter() }
    }
    fun removeLetters(input: String): String {
        return input.filter { !it.isLetter() }
    }
    list = removeValuesAtEvenPositions(list).toMutableList()

    if (doc != null) {
        doc.close()
    }

    val m2: Matcher = p2.matcher(sb2)
    while (m2.find()) {
        //adiciona a uma lista todos as posicoes inicias de uma data encontradas num documento
        list2.add(m2.start())
    }

    list2.removeFirst()
    list2.removeFirst()
    list2.removeFirst()
    list2.removeFirst()
    list2 = removeValuesAtOddPositions(list2).toMutableList()
    list2 = list2.map { it + 3 }.toMutableList()
    list.reverse()
    list2.reverse()
    var tempNumber:Int = list.size - 1
    while (tempNumber != -1){
        var tempList: MutableList<String> = mutableListOf()
        tempList = text.substring(list[tempNumber],list2[tempNumber]).replace("\r\n","").split(" ").toMutableList()
        if (containsLetters(tempList[tempList.size-2])){
            tempList[tempList.size-2] = removeLetters(tempList[tempList.size-2])
        }
        FileWriter(fileCsv, true).use { out ->
            out.append("${tempList[0]}/${tempList[tempList.size-2]}/${tempList[tempList.size-1]}\n")
        }
        list.removeLast()
        list2.removeLast()
        tempNumber = tempNumber - 1
    }
}

