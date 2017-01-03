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
                      teamId: Long,
                      rubricId: Long,
                      rubricDimensionId: Long,
                      userId: Long,
                      userType: String)

class AssessmentTableDef(tag: Tag) extends Table[Assessment](tag, "assessment") {

    def id = column[Long]("id", O.PrimaryKey)
    def score = column[Long]("score")
    def teamId = column[Long]("team_id")
    def rubricId = column[Long]("rubric_id")
    def rubricDimensionId = column[Long]("rubric_dimension_id")
    def userId = column[Long]("user_id")
    def userType = column[String]("user_type")

    override def * = (id, score, teamId, rubricId, rubricDimensionId, userId, userType) <> ((Assessment.apply _).tupled, Assessment.unapply)

}

object Assessment {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val assessments = TableQuery[AssessmentTableDef]

    def addToAssessment(id: Long, score: Long, teamId: Long, rubricId: Long, rubricDimensionId: Long, userId: Long, userType: String): Future[Assessment] = {
        val assessment = Assessment(id, score, teamId, rubricId, rubricDimensionId, userId, userType)
        dbConfig.db.run(assessments += assessment).map(res => assessment)
    }

}
