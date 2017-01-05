package controllers

import models.{AuditLog, Comment}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 12/14/16.
 *
 * Model:
 *
 * id: long
 * commentable_id: long
 * commentable_type: String
 * user_id: long (points to User)
 * title: String
 * body: String
 *
 */
object CommentController extends Controller {

    implicit val commentRead = Json.reads[Comment]
    implicit val commentWrite = Json.writes[Comment]

    def get = Action.async {
        Comment.getComments.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(comment, _) =>
                AuditLog.addToLog(request.uri, comment.toString).flatMap(res =>
                    Comment.addToComments(comment).map(res =>
                        Ok("Comment added")
                    )
                )
            case JsError(errors) => {
                Future(BadRequest)
            }
        }
    }

    def put = Action {
        Ok
    }

    def delete = Action {
        Ok
    }

}
