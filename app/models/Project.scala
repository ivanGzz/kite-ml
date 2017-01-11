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

case class Project(id: Long,
                   author_id: Long,
                   project_type_id: Long,
                   parent_project_id: Long,
                   teams_count: Long,
                   team_member_count: Long)

class ProjectTableDef(tag: Tag) extends Table[Project](tag, "project") {

    def id = column[Long]("id", O.PrimaryKey)
    def author_id = column[Long]("author_id")
    def parent_project_id = column[Long]("parent_project_id")
    def teams_count = column[Long]("teams_count")
    def team_member_count = column[Long]("team_member_count")

    override def * = (id, author_id, parent_project_id, teams_count, team_member_count) <> ((Project.apply _).tupled, Project.unapply)

}

object Project {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val projects = TableQuery[ProjectTableDef]

    def getProjects: Future[Seq[Project]] = dbConfig.db.run(projects.result)

    def addToProjects(project: Project): Future[Boolean] = dbConfig.db.run(projects += project).map(res => true)

}
