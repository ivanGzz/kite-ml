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

case class GroupClassTeacher(id: Long,
                             group_class_id: Long,
                             teacher_id: Long)

class GroupClassTeacherTableDef(tag: Tag) extends Table[GroupClassTeacher](tag, "group_class_teacher") {

    def id = column[Long]("id", O.PrimaryKey)
    def group_class_id = column[Long]("group_class_id")
    def teacher_id = column[Long]("teacher_id")

    override def * = (id, group_class_id, teacher_id) <> ((GroupClassTeacher.apply _).tupled, GroupClassTeacher.unapply)

}

object GroupClassTeacher {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val groupClassTeachers = TableQuery[GroupClassTeacherTableDef]

    def getGroupClassTeachers: Future[Seq[GroupClassTeacher]] = dbConfig.db.run(groupClassTeachers.result)

    def addToGroupClassTeachers(groupClassTeacher: GroupClassTeacher): Future[Boolean] = dbConfig.db.run(groupClassTeachers += groupClassTeacher).map(res => true)

}
