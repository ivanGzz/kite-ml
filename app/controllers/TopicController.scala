package controllers

import models.{AuditLog, Topic}
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
 * subject_id: long (points to Subject)
 *
 */
object TopicController extends Controller {

    implicit val topicRead = Json.reads[Topic]
    implicit val topicWrite = Json.writes[Topic]

    def get = Action.async {
        Topic.getTopics.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(topic, _) =>
                AuditLog.addToLog(request.uri, topic.toString).flatMap(res =>
                    Topic.addToTopics(topic)
                ).map(res =>
                    Ok("Topic added")
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
