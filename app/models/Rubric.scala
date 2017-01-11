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

case class Rubric(id: Long,
                  code: String,
                  author_id: Long)

class RubricTableDef(tag: Tag) extends Table[Rubric](tag, "rubric") {

    def id = column[Long]("id", O.PrimaryKey)
    def code = column[String]("code")
    def author_id = column[Long]("author_id")

    override def * = (id, code, author_id) <> ((Rubric.apply _).tupled, Rubric.unapply)

}

object Rubric {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val rubrics = TableQuery[RubricTableDef]

    def getRubrics: Future[Seq[Rubric]] = dbConfig.db.run(rubrics.result)

    def addToRubrics(rubric: Rubric): Future[Boolean] = dbConfig.db.run(rubrics += rubric).map(res => true)

}
