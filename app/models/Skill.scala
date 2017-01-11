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

case class Skill(id: Long, name: String, code: String)

class SkillTableDef(tag: Tag) extends Table[Skill](tag, "skill") {

    def id = column[Long]("id", O.PrimaryKey)
    def name = column[String]("name")
    def code = column[String]("code")

    override def * = (id, name, code) <> ((Skill.apply _).tupled, Skill.unapply)

}

object Skill {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val skills = TableQuery[SkillTableDef]

    def getSkills: Future[Seq[Skill]] = dbConfig.db.run(skills.result)

    def addToSkills(skill: Skill): Future[Boolean] = dbConfig.db.run(skills += skill).map(res => true)

}
