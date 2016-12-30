package models

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

    override def * = (id, score) <> ((Assessment.apply _).tupled, Assessment.unapply)

}

object Assessment {

}
