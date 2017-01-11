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

case class ProjectType(id: Long,
                       name: String,
                       description: String,
                       code: String)

class ProjectTypeTableDef(tag: Tag) extends Table[ProjectType](tag, "project_type") {

    def id = column[Long]("id", O.PrimaryKey)
    def name = column[String]("name")
    def description = column[String]("description")
    def code = column[String]("code")

    override def * = (id, name, description, code) <> ((ProjectType.apply _).tupled, ProjectType.unapply)

}

object ProjectType {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val projectTypes = TableQuery[ProjectTypeTableDef]

    def getProjectTypes: Future[Seq[ProjectType]] = dbConfig.db.run(projectTypes.result)

    def addToProjectTypes(projectType: ProjectType): Future[Boolean] = dbConfig.db.run(projectTypes += projectType).map(res => true)

}
