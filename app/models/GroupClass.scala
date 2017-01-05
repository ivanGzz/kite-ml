package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 1/4/17.
 */

case class GroupClass(id: Long,
                      group_id: Long,
                      course_id: Long)

class GroupClassTableDef(tag: Tag) extends Table[GroupClass](tag, "group_class") {

    def id = column[Long]("id", O.PrimaryKey)
    def group_id = column[Long]("group_class")
    def course_id = column[Long]("course_id")

    override def * = (id, group_id, course_id) <> ((GroupClass.apply _).tupled, GroupClass.unapply)

}

object GroupClass {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val groupClasses = TableQuery[GroupClassTableDef]

    def getGroupClasses: Future[Seq[GroupClass]] = dbConfig.db.run(groupClasses.result)

    def addToGroupClasses(groupClass: GroupClass): Future[Boolean] = dbConfig.db.run(groupClasses += groupClass).map(res => true)

}
