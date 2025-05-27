# bookService
使用了spring-boot, mybatis, mysql.

TOKEN简单拦截认证：
@GetMapping("/login")
WebConfig+TokenInterceptor

基本增删改查API：
@GetMapping("/findAllBook")
@GetMapping("/findBookById/{id}")
@PostMapping("/addBook")
@PutMapping("/updateBook")
@DeleteMapping("/deleteBook/{id}")

分页查询+自定义列filter+自定义列排序(原生实现,也可使用PageHelper框架):
@PostMapping("/pageSearchBook")

全局异常拦截，自定义错误状态码和错误信息展示:
BaseController

简单工厂+模板方法+策略模式 实现图书按年龄段分类以及有自己对应的折扣，按语言过滤，以及按状态展示:
@GetMapping("/recommendBookByAge/{age}")

责任链模式+声明式事务，确保订单流程正确，包括库存检查，余额检查，book状态检查，最后进行book库存更新，user余额展示(可替换成user表余额更新):
@PostMapping("/orderBook")

Mockito + MockMvc实现集成测试：
BookControllerIntegrationTest

Mockito实现单元测试：
BookControllerTest
BookServiceTest

总Test case覆盖率(设计模式加了以后有些分支没走进去, 需要花时间造数据，demo就小偷懒一下啦哈哈，规范流程应该是一个分支就是一个单独的Test method)：
class: 92%
method: 92%
line: 85%

数据库导出为bookDbSQL.sql
集成测试使用POSTMAN，截图都在testResult.doc中




