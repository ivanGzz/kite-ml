package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 1/9/17.
 */

case class Team(id: Long,
                published: Boolean,
                class_project_id: Long,
                project_id: Long,
                avg_score: Double,
                views_count: Long,
                likes_count: Long)

class TeamTableDef(tag: Tag) extends Table[Team](tag, "team") {

    def id = column[Long]("id", O.PrimaryKey)
    def published = column[Boolean]("published")
    def class_project_id = column[Long]("class_project_id")
    def project_id = column[Long]("project_id")
    def avg_score = column[Double]("avg_score")
    def views_count = column[Long]("views_count")
    def likes_count = column[Long]("likes_count")

    override def * = (id, published, class_project_id, project_id, avg_score, views_count, likes_count) <> ((Team.apply _).tupled, Team.unapply)

}

object Team {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val teams = TableQuery[TeamTableDef]

    def getTeams: Future[Seq[Team]] = dbConfig.db.run(teams.result)

    def addToTeams(team: Team): Future[Boolean] = dbConfig.db.run(teams += team).map(res => true)

}
