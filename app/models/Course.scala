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

case class Course(id: Long,
                  code: String,
                  multidisciplinary: Boolean,
                  grade: String,
                  school_id: Long,
                  active: Boolean,
                  tech: Boolean)

class CourseTableDef(tag: Tag) extends Table[Course](tag, "course") {

    def id = column[Long]("id", O.PrimaryKey)
    def code = column[String]("code")
    def multidisciplinary = column[Boolean]("multidisciplinary")
    def grade = column[String]("grade")
    def school_id = column[Long]("school_id")
    def active = column[Boolean]("active")
    def tech = column[Boolean]("tech")

    override def * = (id, code, multidisciplinary, grade, school_id, active, tech) <> ((Course.apply _).tupled, Course.unapply)

}

object Course {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val courses = TableQuery[CourseTableDef]

    def getCourses: Future[Seq[Course]] = dbConfig.db.run(courses.result)

    def addToCourses(course: Course): Future[Boolean] = dbConfig.db.run(courses += course).map(res => true)

}
