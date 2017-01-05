package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 12/28/16.
 */

case class Assessment(id: Long,
                      score: Long,
                      team_id: Long,
                      rubric_id: Long,
                      rubric_dimension_id: Long,
                      user_id: Long,
                      user_type: String)

class AssessmentTableDef(tag: Tag) extends Table[Assessment](tag, "assessment") {

    def id = column[Long]("id", O.PrimaryKey)
    def score = column[Long]("score")
    def team_id = column[Long]("team_id")
    def rubric_id = column[Long]("rubric_id")
    def rubric_dimension_id = column[Long]("rubric_dimension_id")
    def user_id = column[Long]("user_id")
    def user_type = column[String]("user_type")

    override def * = (id, score, team_id, rubric_id, rubric_dimension_id, user_id, user_type) <> ((Assessment.apply _).tupled, Assessment.unapply)

}

object Assessment {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val assessments = TableQuery[AssessmentTableDef]

    def getAssessments: Future[Seq[Assessment]] = dbConfig.db.run(assessments.result)

    def addToAssessment(assessment: Assessment): Future[Boolean] = dbConfig.db.run(assessments += assessment).map(res => true)

}
