package models

import java.sql.Date

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by Nelson on 4/18/2017.
 */

case class Project(id: Long, competencies: String, created: Date)

class ProjectTableDef(tag: Tag) extends Table[Project](tag, "project") {

    def id = column[Long]("id", O.PrimaryKey)
    def competencies = column[String]("competencies")
    def created = column[Date]("created")

    override def * = (id, competencies, created) <> (Project.tupled, Project.unapply)

}

object Projects {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val projects = TableQuery[ProjectTableDef]

    def getProjectById(id: Long): Future[Option[Project]] = dbConfig.db.run(
        projects.filter(_.id === id).result.headOption
    )

    def addToProjects(project: Project): Future[Long] = dbConfig.db.run(
        projects += project
    ).map(
        res => project.id
    )

}
