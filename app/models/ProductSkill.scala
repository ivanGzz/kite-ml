package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 1/7/17.
 */

case class ProductSkill(id: Long,
                        product_id: Long,
                        skill_id: Long,
                        value: Long)

class ProductSkillTableDef(tag: Tag) extends Table[ProductSkill](tag, "product_skill") {

    def id = column[Long]("id", O.PrimaryKey)
    def product_id = column[Long]("product_id")
    def skill_id = column[Long]("skill_id")
    def value = column[Long]("value_")

    override def * = (id, product_id, skill_id, value) <> ((ProductSkill.apply _).tupled, ProductSkill.unapply)

}

object ProductSkill {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val productSkills = TableQuery[ProductSkillTableDef]

    def getProductSkills: Future[Seq[ProductSkill]] = dbConfig.db.run(productSkills.result)

    def addToProductSkill(productSkill: ProductSkill): Future[Boolean] = dbConfig.db.run(productSkills += productSkill).map(res => true)

}
