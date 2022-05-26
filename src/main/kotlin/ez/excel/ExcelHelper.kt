package ez.excel

import java.io.File

class ExcelHelper(
  private val templateRoot: String
) {
  /**
   *
   * @param templatePath  template file path（relative to template root）
   * @return
   */
  fun useTemplate(templatePath: String) = ExcelTemplate(File(templateRoot).resolve(templatePath))
}