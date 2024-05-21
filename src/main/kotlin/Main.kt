package org.example
import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDCIDFont
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts
import java.awt.Font
import java.io.File




fun main() {
    //Loading an existing document
    val file = File("src/main/teste.pdf")
    val document: PDDocument = Loader.loadPDF(file)


    //Retrieving the pages of the document
    val page = document.getPage(0)
    val contentStream = PDPageContentStream(document, page)

    //Begin the Content stream
    contentStream.beginText()

    contentStream.setFont(PDType1Font(Standard14Fonts.FontName.HELVETICA),12F);

    //Setting the position for the line
    contentStream.newLineAtOffset(25F, 5000F)

    val text = "Reis"


    //Adding text in the form of string
    contentStream.showText(text)

    //Ending the content stream
    contentStream.endText()

    println("Content added")


    //Closing the content stream
    contentStream.close()


    //Saving the document
    document.save(File("src/main/teste.pdf"))


    //Closing the document
    document.close()
}
