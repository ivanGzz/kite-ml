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

    case class UserCompetencyRequest(user_id: Long, chat_room_id: Long, competencies: List[Int])

    implicit val UCPutRequestRead = Json.reads[UserCompetencyRequest]

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(postRequest, _) =>
                AuditLogs.addToLog(request.uri, "POST", postRequest.toString).flatMap(res => {
                    val userCompetency = UserCompetency(0, postRequest.user_id, postRequest.competencies.mkString(","))
                    UserCompetencies.addToUserCompetencies(userCompetency)
                }).map(res =>
                    Ok(res.toString)
                )
            case JsError(errors) => Future(BadRequest)
        }
    }

    def put = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(putRequest, _) =>
                AuditLogs.addToLog(request.uri, "PUT", putRequest.toString).flatMap(res => {
                    UserCompetencies.updateUserCompetencies(putRequest.user_id, putRequest.competencies.mkString(","))
                }).map(res =>
                    Ok
                )
            case JsError(errors) => Future(BadRequest)
        }
    }

    def train = Action {
        Ok
    }

}
