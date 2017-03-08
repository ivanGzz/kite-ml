package controllers

import java.sql.Date

import learning.UserCompetencyNet
import models.{UserCompetencies, AuditLogs, UserCompetency}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 2/11/17.
 */
object UserCompetencyController extends Controller {

    case class UserCompetencyRequest(project_id: Long, user_id: Long, competencies: List[Int])

    implicit val UCPutRequestRead = Json.reads[UserCompetencyRequest]

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(postRequest, _) =>
                AuditLogs.addToLog(request.uri, "POST", postRequest.toString).flatMap(res => {
                    val today = new java.util.Date()
                    val userCompetency = UserCompetency(0, postRequest.project_id, postRequest.user_id, postRequest.competencies.mkString(","), "0", new Date(today.getTime))
                    UserCompetencies.addToUserCompetencies(userCompetency)
                }).map(res =>
                    Ok(UserCompetencyNet.rate(postRequest.competencies).toString)
                )
            case JsError(errors) => Future(BadRequest)
        }
    }

    def put = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(putRequest, _) =>
                AuditLogs.addToLog(request.uri, "PUT", putRequest.toString).flatMap(res => {
                    UserCompetencies.updateUserCompetencies(putRequest.project_id, putRequest.user_id, putRequest.competencies.mkString(","))
                }).map(res =>
                    Ok(UserCompetencyNet.rate(putRequest.competencies).toString)
                )
            case JsError(errors) => Future(BadRequest)
        }
    }

    def train = Action.async {
        UserCompetencyNet.train.map(res => Ok(res))
    }

}
