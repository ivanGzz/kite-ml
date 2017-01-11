package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 1/10/17.
 */

case class TeamMember(id: Long,
                      team_id: Long,
                      user_id: Long,
                      project_id: Long,
                      role: String)

class TeamMemberTableDef(tag: Tag) extends Table[TeamMember](tag, "team_member") {

    def id = column[Long]("id", O.PrimaryKey)
    def team_id = column[Long]("team_id")
    def user_id = column[Long]("user_id")
    def project_id = column[Long]("project_id")
    def role = column[String]("role")

    override def * = (id, team_id, user_id, project_id, role) <> ((TeamMember.apply _).tupled, TeamMember.unapply)

}

object TeamMember {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val teamMembers = TableQuery[TeamMemberTableDef]

    def getTeamMembers: Future[Seq[TeamMember]] = dbConfig.db.run(teamMembers.result)

    def addToTeamMembers(teamMember: TeamMember): Future[Boolean] = dbConfig.db.run(teamMembers += teamMember).map(res => true)

}
