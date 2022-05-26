package ez.excel

import org.jxls.common.Context
import org.jxls.util.JxlsHelper
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream

class ExcelTemplate(
  private val templateStreamProvider: () -> InputStream
) {
  constructor(templateFile: File): this({
    // not necessary to use BufferedInputStream because transformer already use it
    FileInputStream(templateFile)
  })

  fun export(context: Context): ByteArray {
    val os = ByteArrayOutputStream()
    export(context, os)
    return os.toByteArray()
  }

  fun export(context: Context, outputStream: OutputStream) {
    JxlsHelper.getInstance().processTemplate(templateStreamProvider(),outputStream, context)
  }
}
