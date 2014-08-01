package main.scala

import java.util
import net.sf.jasperreports.engine._
import net.sf.jasperreports.engine.util.JRLoader
import java.io.File
import java.sql.{DriverManager, Statement, ResultSet}
import net.sf.jasperreports.export.Exporter
import net.sf.jasperreports.engine.export.JRPdfExporter
/**
 * Created by dengsibao on 2014/7/29.
 *
 */
object jasperReportBase extends App{
  val fileName = "E:\\IdeaProjects\\test\\src\\main\\resources\\report2.jasper"
  val outFileName = "E:\\report\\report2.pdf"
  val hm = new util.HashMap[String,AnyRef]()

  /**
  * 所有JasperReports 主要的功能，像报表编辑，报表填充及导出，经常用到序列化的对象，
   * net.sf.jasperreports.engine.util.JRLoader可以帮助从文件或者URLs或者输入流中装载这些被序列化的对象。
  * */

  /**
   * Class net.sf.jasperreports.engine.JasperReport
   * 这个类的对象通过net.sf.jasperreports.engine.util.JRLoader从.jasper文件中获取，
   * 为用数据填充 和产生报表做准备，当用数据填充报表的时候，运行时求各种各样报表表达式的值。
   * 对象中包含了报表的各种元素，例如报表的大小，显示位置，显示的栏位，图片 信息等等。
  * */

   /**
   * Class net.sf.jasper.engine.JasperFillManager
   * 这个类用来实现报表的数据装填。这个类提供了很多方法来接受各种类型的report design--可以是一个对象，
   * 一个文件，或一个输入流。它的输出结果也是多样的：file，Object，output Stream。
   * report的装填引擎需要接收一个可以从中获取数据和value的数据源作为报表参数。
   * 参数值（Parameters value）通常使用Java.util.Map来提供，里面包含的KEY是报表的参数名。
   * 数据源可以通过两种方式提供，这取决于你的解决方案：
   *
    * 通常情况下，用户应该提供一个JRDataSource对象，例如我前面提到的那些。
   * 但是大多数的报表都是采用关系数据库中的值来装填数据，
    * 所以JasperReport拥有一个内置的缺省行为?让用户在报表设计的时候提供一个SQL查询。
    * 在运行期，这个查询将被执行以用来从数据库中获取要装填的数据。在这种情况下，
    * JasperReport仅需要一个java.sql.Connection对象来取代通常的数据对象。
    * JasperReport需要这个连接对象来连接数据库管理系统并执行查询操作。
   * 在查询结束之后，JasperReport将自动生成一个JRResultSetDataSource，并将它返回给报表装填过程。
   * */
  try{
    /**
     * net.sf.jasperreports.engine.JasperPrint
     * 这个类的对象是通过填充类根据JasperReport的对象用数据填充后的结果，
     * 这样可以被很直观的被别的类引用，将报表输出到网络或者文件
     * */

     val sqlDriver = "com.mysql.jdbc.Driver"
     Class.forName(sqlDriver)
     val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/landing","root","root")
     val reportFile = new File(fileName)
     val jasperReport = JRLoader.loadObject(reportFile).asInstanceOf[JasperReport]
/*     val statement = java.sql.Connection
     val set:ResultSet = Statement*/
/*     val source = new JRResultSetDataSource(set)*/
     val print:JasperPrint = JasperFillManager.fillReport(jasperReport,hm,conn)
/*     val exporter:Exporter = new JRPdfExporter()  //设置要导出那种类型报表*/
     JasperExportManager.exportReportToPdfFile(print,outFileName)
/*     exporter.setExporterOutput(JRExporterParameter.OUTPUT_FILE_NAME,outFileName)
     exporter.setExporterInput(JRExporterParameter.JASPER_PRINT,print)
     exporter.exportReport()
     System.out.println("Created file: " + outFileName)*/
  } catch {
    case e:JRException =>
      e.printStackTrace()
      System.exit(1)
    case e: Exception =>
      e.printStackTrace()
      System.exit(1)
  }
}
