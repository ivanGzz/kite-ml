package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by nigonzalez on 12/14/16.
 *
 * Model:
 *
 * id: long
 * due_date: Date
 * project_id: long (points to Project)
 * group_class_id: long (points to Group Class)
 * rubric_id: long (points to Rubric)
 *
 */
object ClassProjectController extends Controller {

    def get = Action {
        Ok(NOT_IMPLEMENTED)
    }

    def post = Action {
        Ok(NOT_IMPLEMENTED)
    }

    def put = Action {
        Ok(NOT_IMPLEMENTED)
    }

    def delete = Action {
        Ok(NOT_IMPLEMENTED)
    }

}
