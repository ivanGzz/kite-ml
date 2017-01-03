package controllers

import play.api.mvc.{Action, Controller}

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

    def get = Action {
        Ok
    }

    def post = Action {
        Ok
    }

    def put = Action {
        Ok
    }

    def delete = Action {
        Ok
    }

}
