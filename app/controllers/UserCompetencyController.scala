package controllers

import models.{UserCompetencies, AuditLogs, UserCompetency}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 2/11/17.
 */
object UserCompetencyController extends Controller {

    case class UserCompetencyRequest(user_id: Long, chat_room_id: Long)

    implicit val UserCompetencyRead = Json.reads[UserCompetencyRequest]

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(userCompetencyRequest, _) =>
                AuditLogs.addToLog(request.uri, "POST", userCompetencyRequest.toString).flatMap(res => {
                    val userCompetency = UserCompetency(0, userCompetencyRequest.user_id, userCompetencyRequest.chat_room_id, List())
                    UserCompetencies.addToUserCompetencies(userCompetency)
                }).map(res =>
                    Ok(res)
                )
            case JsError(errors) => Future(BadRequest)
        }
    }

}
