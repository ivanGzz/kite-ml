package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by nigonzalez on 12/15/16.
 *
 * Model:
 *
 * id: long
 * code: String
 * min_score: long
 * medium_score: long
 * max_score: long
 * rubric_id: long (points to Rubric)
 * author_id: long
 *
 */
object RubricDimensionController extends Controller {

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
