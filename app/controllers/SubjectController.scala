package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by nigonzalez on 12/13/16.
 *
 * Model:
 *
 * id: long
 * course_id: long (points to Course)
 *
 */
object SubjectController extends Controller {

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
