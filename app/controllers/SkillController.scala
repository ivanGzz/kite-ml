package controllers

import models.{AuditLog, Skill}
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
 * name: String
 * code: String
 *
 */
object SkillController extends Controller {

    implicit val skillRead = Json.reads[Skill]
    implicit val skillWrite = Json.writes[Skill]

    def get = Action.async {
        Skill.getSkills.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(skill, _) =>
                AuditLog.addToLog(request.uri, skill.toString).flatMap(res =>
                    Skill.addToSkills(skill)
                ).map(res => Ok("Skill added"))
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
