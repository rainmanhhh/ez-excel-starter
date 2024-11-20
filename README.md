# Excel starter

封装`jxls-poi`，用于导出excel文件

## 参数配置

excel模板所在目录，由spring配置属性`ez.excel.template-root`控制，默认值为`excelTemplate`
（位于springboot app根目录下，例如`/home/java/foo-app/excelTemplate/`）

## 使用方法

### 定义模板

假设模板文件为`/home/java/foo-app/excelTemplate/myTemplate.xlsx`，内容如下（第一行和第一列是excel文件自带的表头，不属于表格内容）：

| - | A            | B            |
|---|--------------|--------------|
| 1 | 姓名           | 手机号          |
| 2 | ${row.name}  | ${row.phone} |
| 3 | 页码：${pageNo} |              |

- A1格的批注为：`jx:area(lastCell="B2")`，表示模板中需要代码替换的部分从左上角A1格开始，到右下的B2格结束，表格的其他部分会保持原样
- A2格的批注为：`jx:each(items="list" var="row" lastCell="B2")`，这将为模板定义一个名为`list`
  的列表变量（代码中可以为此变量注入一个`List<String>`类型的值），
  同时还定义了一个在模板内部使用的变量`row`，用于从`list`列表的元素中取值
- 如果不需要表头（即包含“姓名”的行），则`jx:area`和`jx:each`都应定义在A1格的批注中
- 模板底部定义了一个非列表变量`pageNo`，代码中可以为此变量注入值（类型可以为`String`或`Integer`等）

模板定义的详细说明，参见`jxls-poi`官方文档。

### 在代码中使用模板

本starter提供了一个`ExcelHelper` bean，在负责导出的service类中，按如下方式调用，即可输出excel文件的内容（类型为`ByteArray`）

```kotlin
import ez.excel.ExcelHelper
import org.jxls.common.Context

@Component
class FooService(
  private val excelHelper: ExcelHelper
) {
  fun export(listItems: List<String>): ByteArray =
    excelHelper.useTemplate("myTemplate.xlsx").export(Context().apply {
      putVar("list", listItems)
      putVar("pageBottom", 1)
    })
}

```

以上代码中，指定了excel模板文件名为`myTemplate.xlsx`，该文件应位于模板目录下（即参数配置一节中提到的excelTemplate目录）。
`Context`对象用于给模板中的变量传值，例如以上代码中，`listItems`这个列表对象将被传给模板变量`list`
，而`1`这个数字被传给模板变量`pageNo`。
