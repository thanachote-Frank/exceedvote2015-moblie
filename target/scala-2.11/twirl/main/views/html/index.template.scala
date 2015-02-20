
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
object index extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(message: String):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {
def /*13.2*/css/*13.5*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*13.9*/("""

""")))};
Seq[Any](format.raw/*1.19*/("""

"""),_display_(/*3.2*/main("Welcome to Play")/*3.25*/(css)/*3.30*/{_display_(Seq[Any](format.raw/*3.31*/("""
	"""),format.raw/*4.2*/("""<div style="margin-left:40px;margin-right:40px;">
	<div style="margin-top:100px;"></div>

    <div class="alert alert-info" role="alert">Under Construction</div>
    </div>


""")))}),format.raw/*11.2*/("""

"""),format.raw/*15.2*/("""
"""))}
  }

  def render(message:String): play.twirl.api.HtmlFormat.Appendable = apply(message)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (message) => apply(message)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Wed Feb 25 20:02:39 ICT 2015
                  SOURCE: /Users/Frank/Documents/softspec/exceedvote2015-moblie/app/views/index.scala.html
                  HASH: e54760a6ae4ec44cb64f47b2f3060c9a7707be7f
                  MATRIX: 723->1|812->231|823->234|903->238|945->18|973->21|1004->44|1017->49|1055->50|1083->52|1289->228|1318->241
                  LINES: 26->1|28->13|28->13|30->13|33->1|35->3|35->3|35->3|35->3|36->4|43->11|45->15
                  -- GENERATED --
              */
          