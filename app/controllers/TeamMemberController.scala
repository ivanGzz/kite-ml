package controllers

import models.{AuditLog, TeamMember}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 12/15/16.
 *
 * Model:
 *
 * id: long
 * team_id: long (points to Team)
 * user_id: long (points to User)
 * project_id: long (points to Project)
 * role: String
 *
 */
object TeamMemberController extends Controller {

    implicit val teamMemberRead = Json.reads[TeamMember]
    implicit val teamMemberWrite = Json.writes[TeamMember]

    def get = Action.async {
        TeamMember.getTeamMembers.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(teamMember, _) =>
                AuditLog.addToLog(request.uri, teamMember.toString).flatMap(res =>
                    TeamMember.addToTeamMembers(teamMember)
                ).map(res =>
                    Ok("Team Member added")
                )
            case JsError(errors) => Future(BadRequest)
        }
    }

    def put = Action {
        Ok
    }

    def delete = Action {
        Ok
    }

}
