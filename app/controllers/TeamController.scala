package controllers

import models.{AuditLog, Team}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 12/13/16.
 *
 * Model:
 * id: long
 * published: boolean
 * class_project_id: long (points to Class Project)
 * project_id: long (points to Project)
 * avg_score: double
 * views_count: long
 * likes_count: long
 *
 */
object TeamController extends Controller {

    implicit val teamRead = Json.reads[Team]
    implicit val teamWrite = Json.writes[Team]

    def get = Action.async {
        Team.getTeams.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(team, _) =>
                AuditLog.addToLog(request.uri, team.toString).flatMap(res =>
                    Team.addToTeams(team)
                ).map(res =>
                    Ok("Team added")
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
