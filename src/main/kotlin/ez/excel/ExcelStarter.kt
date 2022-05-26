package ez.excel

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean

@ConfigurationProperties("ez.excel")
class ExcelStarter {
  var templateRoot = "excelTemplate"

  @Bean
  fun excelHelper() = ExcelHelper(templateRoot)
}