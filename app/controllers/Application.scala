package controllers

import play.api._
import play.api.mvc._
import models.{Video, VideoModel}

object Application extends Controller {

  def index = Action {
    var videos = new collection.mutable.ListBuffer[Video]
    videos.append(VideoModel.findById(20000).get)
    videos.append(VideoModel.findById(20001).get)
    Ok(views.html.videobox(videos.toList))
  }

}