package controllers

import play.api.mvc._

object HomeController extends Controller {

    def home = Action {
		Ok("Hello World!")
	}

}