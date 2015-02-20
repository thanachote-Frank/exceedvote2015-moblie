
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._

import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._

/**/
object main extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template3[String,Html,Html,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String)(css: Html)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.43*/("""

"""),format.raw/*3.1*/("""<!DOCTYPE html>

<html>
    <head>
        <title>"""),_display_(/*7.17*/title),format.raw/*7.22*/("""</title>
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" media="screen" href=""""),_display_(/*9.54*/routes/*9.60*/.Assets.at("stylesheets/main.css")),format.raw/*9.94*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(/*10.59*/routes/*10.65*/.Assets.at("images/favicon.png")),format.raw/*10.97*/("""">
        <script src=""""),_display_(/*11.23*/routes/*11.29*/.Assets.at("javascripts/jquery-1.11.2.min.js")),format.raw/*11.75*/("""" type="text/javascript"></script>
        <link rel="stylesheet" media="screen" href=""""),_display_(/*12.54*/routes/*12.60*/.Assets.at("bootstrap-3.3.2/css/bootstrap.min.css")),format.raw/*12.111*/("""">
        <script src=""""),_display_(/*13.23*/routes/*13.29*/.Assets.at("bootstrap-3.3.2/js/bootstrap.min.js")),format.raw/*13.78*/("""" type="text/javascript"></script>
        """),_display_(/*14.10*/css),format.raw/*14.13*/("""
    """),format.raw/*15.5*/("""</head>
    <body>
        """),_display_(/*17.10*/content),format.raw/*17.17*/("""
    """),format.raw/*18.5*/("""</body>
</html>
"""))}
  }

  def render(title:String,css:Html,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title)(css)(content)

  def f:((String) => (Html) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title) => (css) => (content) => apply(title)(css)(content)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Wed Feb 25 20:02:39 ICT 2015
                  SOURCE: /Users/Frank/Documents/softspec/exceedvote2015-moblie/app/views/main.scala.html
                  HASH: dd944addda50c9f0f43f5889bd6e97b9ab3a5655
                  MATRIX: 732->1|861->42|889->44|966->95|991->100|1139->222|1153->228|1207->262|1295->323|1310->329|1363->361|1415->386|1430->392|1497->438|1612->526|1627->532|1700->583|1752->608|1767->614|1837->663|1908->707|1932->710|1964->715|2019->743|2047->750|2079->755
                  LINES: 26->1|29->1|31->3|35->7|35->7|37->9|37->9|37->9|38->10|38->10|38->10|39->11|39->11|39->11|40->12|40->12|40->12|41->13|41->13|41->13|42->14|42->14|43->15|45->17|45->17|46->18
                  -- GENERATED --
              */
          