package ez.excel

import org.jxls.builder.JxlsOutput
import org.jxls.builder.JxlsTemplateFillerBuilder
import java.io.*

@Suppress("MemberVisibilityCanBePrivate", "unused")
class ExcelTemplate(
  private val templateStreamProvider: () -> InputStream
) {
  constructor(templateFile: File) : this({
    // not necessary to use BufferedInputStream because transformer already use it
    FileInputStream(templateFile)
  })

  fun export(map: Map<String, Any?>): ByteArray {
    val os = ByteArrayOutputStream()
    export(map) { os }
    return os.toByteArray()
  }

  fun export(map: Map<String, Any?>, output: JxlsOutput) {
    JxlsTemplateFillerBuilder.newInstance()
      .withTemplate(templateStreamProvider())
      .build()
      .fill(map, output)
  }

  fun export(map: Map<String, Any?>, output: () -> OutputStream) =
    export(map, JxlsOutput { output() })
}
