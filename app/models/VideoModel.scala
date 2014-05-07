package models

import anorm._
import anorm.SqlParser._

import play.api.Play.current

import play.api.db.DB
import java.util.Date

/**
 * Created by ytanaka on 2014/05/07.
 *
 * [Todo]
 * play.api.Play.current
 * の機能を調べておく
 * →Implicitly import the current running application in the context.
 * play.api.Applicationを取得するために使うらしい
 * 何に使うもの？→キャッシュを使うために使う
 *
 */

object VideoModel{

  //Anormのasで使うgパーサー
  val video = {
    get[Pk[Int]]("video_table.id")~
    get[String]("video_table.nicovideo_id")~
    get[String]("video_table.title")~
    get[String]("video_table.description")~
    get[String]("video_table.thumbnail_url")~
    get[Int]("video_table.view")~
    get[Int]("video_table.comment")~
    get[Int]("video_table.mylist")~
    get[String]("video_table.tags")~
    get[String]("video_table.category")~
    get[Int]("video_table.mvpoint")~
    get[Int]("video_table.cvpoint")~
    get[Date]("video_table.date")~
    get[String]("video_table.first_retrieve")~
    get[String]("video_table.length") map{
      case id~ nicovideo_id~ title~ description~ thumbnail_url~ view~ comment~ mylist~ tags~ category~
        mvpoint~ cvpoint~ date~ first_retrieve~ length
        => Video(id,nicovideo_id,title,description,thumbnail_url,view,comment,mylist,tags,category,
        mvpoint,cvpoint,date,first_retrieve,length)
    }

  }

  def findById(id: Int): Option[Video] = {
    //コードブロック内をJDBCコネクションで実行する
    //SQL().on(追加情報).as(パーサー)
    //パーサーはcaseクラス内に用意しておくものらしい
    DB.withConnection { implicit connection =>
      SQL("select * from video_table where id = {id}").on('id -> id).as(VideoModel.video.singleOpt)
    }
  }

  def findTagsArrayById(id: Int): Array[String] = {
    val v: Option[Video] = findById(id)
    v.get.tags.split(",")
  }

}

case class Video(
  id:Pk[Int] = NotAssigned,
  nicovideo_id:String,title:String,description:String,thumbnail_url:String,view:Int,comment:Int,mylist:Int,
  tags:String,category:String,mvpoint:Int,cvpoint:Int,date:Date,first_retrieve:String,length:String
                  )
