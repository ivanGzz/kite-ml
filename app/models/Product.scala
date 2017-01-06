package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 1/5/17.
 */

case class Product(id: Long,
                   product_type: String,
                   project_id: Long,
                   product_order: Long,
                   points: Long)

class ProductTableDef(tag: Tag) extends Table[Product](tag, "product") {

    def id = column[Long]("id", O.PrimaryKey)
    def product_type = column[String]("product_type")
    def project_id = column[Long]("project_id")
    def product_order = column[Long]("product_order")
    def points = column[Long]("points")

    override def * = (id, product_type, project_id, product_order, points) <> ((Product.apply _).tupled, Product.unapply)

}

object Product {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val products = TableQuery[ProductTableDef]

    def getProducts: Future[Seq[Product]] = dbConfig.db.run(products.result)

    def addToProducts(product: Product): Future[Boolean] = dbConfig.db.run(products += product).map(res => true)

}
