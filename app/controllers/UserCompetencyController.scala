package controllers

import java.sql.Date

import learning.UserCompetencyNet
import models.{UserCompetencies, AuditLogs, UserCompetency}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random

/**
 * Created by nigonzalez on 2/11/17.
 */
object UserCompetencyController extends Controller {

    case class RateRequest(project_id: Long, user_id: Long, competencies: List[Int])
    case class UserCompetencyRequest(project_id: Long, user_id: Long, competencies: List[Int], score: String)

    implicit val RateRequestRead = Json.reads[RateRequest]
    implicit val UserCompetencyRequestRead = Json.reads[UserCompetencyRequest]
    implicit val UserCompetencyWrite = Json.writes[UserCompetency]

    val rand = new Random()

    def rate = Action(parse.json) { implicit request =>
        request.body.validate(RateRequestRead) match {
            case JsSuccess(rateRequest, _) =>
                Ok(UserCompetencyNet.rate(rateRequest.competencies).toString)
            case JsError(errors) => BadRequest
        }
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate(UserCompetencyRequestRead) match {
            case JsSuccess(postRequest, _) =>
                AuditLogs.addToLog(request.uri, "POST", postRequest.toString).flatMap(res => {
                    val today = new java.util.Date()
                    val userCompetency = UserCompetency(0, postRequest.project_id, postRequest.user_id, postRequest.competencies.mkString(","), postRequest.score, new Date(today.getTime))
                    UserCompetencies.addToUserCompetencies(userCompetency)
                }).map(res =>
                    Ok(UserCompetencyNet.rate(postRequest.competencies).toString)
                )
            case JsError(errors) => Future(BadRequest)
        }
    }

    def put = Action.async(parse.json) { implicit request =>
        request.body.validate(UserCompetencyRequestRead) match {
            case JsSuccess(putRequest, _) =>
                AuditLogs.addToLog(request.uri, "PUT", putRequest.toString).flatMap(res => {
                    UserCompetencies.updateUser(putRequest.project_id, putRequest.user_id, putRequest.competencies.mkString(","), putRequest.score)
                }).map(res =>
                    Ok(UserCompetencyNet.rate(putRequest.competencies).toString)
                )
            case JsError(errors) => Future(BadRequest)
        }
    }

    def train = Action.async {
        UserCompetencyNet.train.map(res => Ok(if (res) "Ok" else "Error"))
    }

    def user(userid: Long) = Action.async {
        UserCompetencies.getUser(userid).map( res =>
            Ok(Json.toJson(res))
        )
    }

    def latest = Action {
        Ok
    }

    def load(version: Long) = Action {
        Ok
    }

    def random = Action.async {
        UserCompetencies.getUserCompetenciesCount.flatMap { res =>
            val skip = if (res > 0) rand.nextInt(res) else 0
            UserCompetencies.getUserByPosition(skip)
        }.map {
            _ match {
                case Some(userCompetency) => Ok(Json.toJson(userCompetency))
                case None => NotFound
            }
        }
    }

}
