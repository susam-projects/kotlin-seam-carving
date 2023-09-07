package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val parsedArgs = parseArgs(args)
    val inFile = parsedArgs["-in"] ?: error("no -in arg provided")
    val outFile = parsedArgs["-out"] ?: error("no -out arg provided")

    val image = openImage(inFile)
    val energies = PixelEnergies(image)
    val energyGraph = EnergyGraph(energies)
//    val verticalSeam = energyGraph.findVerticalSeam()
    val horizontalSeam = energyGraph.findHorizontalSeam()
//    drawSeam(image, verticalSeam)
    drawSeam(image, horizontalSeam)
    saveImage(image, outFile)
}

fun drawSeam(image: BufferedImage, seam: PointList) {
    seam.forEach { point ->
        image.setRGB(point.x, point.y, Color.RED.rgb)
    }
}

fun parseArgs(args: Array<String>): Map<String, String> {
    val result = HashMap<String, String>()
    for (i in args.indices step 2) {
        val paramName = args[i]
        val paramValue = args[i + 1]
        result[paramName] = paramValue
    }
    return result
}

fun openImage(filePath: String): BufferedImage {
    return ImageIO.read(File(filePath))
}

fun saveImage(image: BufferedImage, fileName: String) {
    ImageIO.write(image, "png", File(fileName))
}
