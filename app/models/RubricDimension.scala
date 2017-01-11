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

case class RubricDimension(id: Long,
                           code: String,
                           min_score: Long,
                           medium_score: Long,
                           max_score: Long,
                           rubric_id: Long,
                           author_id: Long)

class RubricDimensionTableDef(tag: Tag) extends Table[RubricDimension](tag, "rubric_dimension") {

    def id = column[Long]("id", O.PrimaryKey)
    def code = column[String]("code")
    def min_score = column[Long]("min_score")
    def medium_score = column[Long]("medium_score")
    def max_score = column[Long]("max_score")
    def rubric_id = column[Long]("rubric_id")
    def author_id = column[Long]("author_id")

    override def * = (id, code, min_score, medium_score, max_score, rubric_id, author_id) <> ((RubricDimension.apply _).tupled, RubricDimension.unapply)

}

object RubricDimension {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val rubricDimensions = TableQuery[RubricDimensionTableDef]

    def getRubricDimensions: Future[Seq[RubricDimension]] = dbConfig.db.run(rubricDimensions.result)

    def addToRubricDimension(rubricDimension: RubricDimension): Future[Boolean] = dbConfig.db.run(rubricDimensions += rubricDimension).map(res => true)

}
