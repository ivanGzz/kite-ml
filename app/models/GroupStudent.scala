package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 1/5/17.
 */

case class GroupStudent(id: Long,
                        group_id: Long,
                        student_id: Long)

class GroupStudentTableDef(tag: Tag) extends Table[GroupStudent](tag, "group_student") {

    def id = column[Long]("id", O.PrimaryKey)
    def group_id = column[Long]("group_id")
    def student_id = column[Long]("student_id")

    override def * = (id, group_id, student_id) <> ((GroupStudent.apply _).tupled, GroupStudent.unapply)

}

object GroupStudent {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val groupStudents = TableQuery[GroupStudentTableDef]

    def getGroupStudents: Future[Seq[GroupStudent]] = dbConfig.db.run(groupStudents.result)

    def addToGroupStudents(groupStudent: GroupStudent): Future[Boolean] = dbConfig.db.run(groupStudents += groupStudent).map(res => true)

}
