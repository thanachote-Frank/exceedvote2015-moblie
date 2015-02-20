// @SOURCE:/Users/Frank/Documents/softspec/exceedvote2015-moblie/conf/routes
// @HASH:f93a8456bdb72b5c87123443f971889c7716e91d
// @DATE:Thu Feb 26 14:28:26 ICT 2015

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset
import _root_.play.libs.F

import Router.queryString


// @LINE:12
// @LINE:10
// @LINE:8
// @LINE:7
// @LINE:6
package controllers {

// @LINE:12
class ReverseAssets {


// @LINE:12
def at(file:String): Call = {
   implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                        

}
                          

// @LINE:10
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {


// @LINE:6
def index(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix)
}
                        

// @LINE:10
def authenticate(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "login")
}
                        

// @LINE:8
def login(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "login")
}
                        

// @LINE:7
def mainMenu(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "main_menu")
}
                        

}
                          
}
                  


// @LINE:12
// @LINE:10
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.javascript {
import ReverseRouteContext.empty

// @LINE:12
class ReverseAssets {


// @LINE:12
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        

}
              

// @LINE:10
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {


// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        

// @LINE:10
def authenticate : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.authenticate",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "login"})
      }
   """
)
                        

// @LINE:8
def login : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.login",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "login"})
      }
   """
)
                        

// @LINE:7
def mainMenu : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.mainMenu",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "main_menu"})
      }
   """
)
                        

}
              
}
        


// @LINE:12
// @LINE:10
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.ref {


// @LINE:12
class ReverseAssets {


// @LINE:12
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      

}
                          

// @LINE:10
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {


// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      

// @LINE:10
def authenticate(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.authenticate(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "authenticate", Seq(), "POST", """POST       /check_login        controllers.Application.checkLogin()""", _prefix + """login""")
)
                      

// @LINE:8
def login(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.login(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "login", Seq(), "GET", """""", _prefix + """login""")
)
                      

// @LINE:7
def mainMenu(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.mainMenu(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "mainMenu", Seq(), "GET", """""", _prefix + """main_menu""")
)
                      

}
                          
}
        
    