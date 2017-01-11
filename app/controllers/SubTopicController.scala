package controllers

import models.{AuditLog, SubTopic}
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
 * code: String
 * topic_id: long (points to Topic)
 *
 */
object SubTopicController extends Controller {

    implicit val subTopicRead = Json.reads[SubTopic]
    implicit val subTopicWrite = Json.writes[SubTopic]

    def get = Action.async {
        SubTopic.getSubTopics.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(subTopic, _) =>
                AuditLog.addToLog(request.uri, subTopic.toString).flatMap(res =>
                    SubTopic.addToSubTopics(subTopic)
                ).map(res =>
                    Ok("Sub Topic added")
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
