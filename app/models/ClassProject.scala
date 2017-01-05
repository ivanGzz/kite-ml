package models

import java.sql.Date

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 1/3/17.
 */

case class ClassProject(id: Long,
                        due_date: Date,
                        project_id: Long,
                        group_class_id: Long,
                        rubric_id: Long)

class ClassProjectTableDef(tag: Tag) extends Table[ClassProject](tag, "class_project") {

    def id = column[Long]("id", O.PrimaryKey)
    def due_date = column[Date]("due_date")
    def project_id = column[Long]("project_id")
    def group_class_id = column[Long]("group_class_id")
    def rubric_id = column[Long]("rubric_id")

    override def * = (id, due_date, project_id, group_class_id, rubric_id) <> ((ClassProject.apply _).tupled, ClassProject.unapply)

}

object ClassProject {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val classProjects = TableQuery[ClassProjectTableDef]

    def getClassProjects: Future[Seq[ClassProject]] = dbConfig.db.run(classProjects.result)

    def addToClassProjects(classProject: ClassProject): Future[Boolean] = dbConfig.db.run(classProjects += classProject).map(res => true)

}
