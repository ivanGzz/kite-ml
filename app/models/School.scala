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

case class School(id: Long,
                  town: String,
                  state: String,
                  country: String,
                  active: Boolean)

class SchoolTableDef(tag: Tag) extends Table[School](tag, "school") {

    def id = column[Long]("id", O.PrimaryKey)
    def town = column[String]("town")
    def state = column[String]("state")
    def country = column[String]("country")
    def active = column[Boolean]("active")

    override def * = (id, town, state, country, active) <> ((School.apply _).tupled, School.unapply)

}

object School {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val schools = TableQuery[SchoolTableDef]

    def getSchools: Future[Seq[School]] = dbConfig.db.run(schools.result)

    def addToSchool(school: School): Future[Boolean] = dbConfig.db.run(schools += school).map(res => true)

}
