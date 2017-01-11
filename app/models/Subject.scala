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

case class Subject(id: Long, course_id: Long)

class SubjectTableDef(tag: Tag) extends Table[Subject](tag, "subject") {

    def id = column[Long]("id", O.PrimaryKey)
    def course_id = column[Long]("course_id")

    override def * = (id, course_id) <> ((Subject.apply _).tupled, Subject.unapply)

}

object Subject {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val subjects = TableQuery[SubjectTableDef]

    def getSubjects: Future[Seq[Subject]] = dbConfig.db.run(subjects.result)

    def addToSubjects(subject: Subject): Future[Boolean] = dbConfig.db.run(subjects += subject).map(res => true)

}
