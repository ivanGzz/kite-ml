package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by nigonzalez on 12/13/16.
 *
 * Model:
 * id: long
 * score: long
 * team_id: long (points to Team)
 * rubric_id: long (points to Rubric)
 * rubric_dimension_id: long (points to Rubric Dimension)
 * user_id: long (points to User)
 * user_type: String
 *
 */
object AssessmentController extends Controller {

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
