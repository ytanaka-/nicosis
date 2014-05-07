package controllers

import play.api._
import play.api.mvc._
import models.VideoModel

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(VideoModel.findTagsArrayById(20000)(0)))
  }

}