
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class POCSimulation extends Simulation {

  var sessionToken = ""

  val sessionHeader = () => Map("X-Silverpeas-Session" -> sessionToken)

  val uri1 = "http://localhost:8000"

  val httpProtocol = http
    .baseURL(uri1)
    .inferHtmlResources()
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate, sdch")
    .acceptLanguageHeader("fr-FR,fr;q=0.8,en-US;q=0.6,en;q=0.4")
    .authorizationHeader("Basic U2lsdmVyQWRtaW5AZG9tYWluMDpTaWx2ZXJBZG1pbg==")
    .contentTypeHeader("application/json")
    .userAgentHeader("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/41.0.2272.76 Chrome/41.0.2272.76 Safari/537.36")

  val headers_0 = Map(
    "Accept-Encoding" -> "gzip, deflate",
    "Origin" -> uri1)

  val headers_1 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
    "Accept-Encoding" -> "gzip, deflate",
    "Origin" -> uri1)

  val headers_26 = Map("Accept" -> "image/webp,*/*;q=0.8")

  val headers_27 = Map(
    "Accept" -> "application/json, text/javascript, */*; q=0.01",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_30 = Map(
    "Origin" -> uri1)

  val headers_60 = Map(
    "Accept-Encoding" -> "gzip, deflate",
    "Origin" -> uri1)

  /*val headers_255 = Map(
    "Content-Type" -> "text/plain;charset=UTF-8",
    "Origin" -> "http://localhost:8000")*/

  val loginscn = scenario("POCSimulation.login")
    .exec(http("authentication")
      .post("/silverpeas/services/authentication")
      .headers(headers_0)
      .basicAuth("SilverAdmin@domain0","SilverAdmin")
      .check(header("X-Silverpeas-Session").saveAs("token")))
    .exec { session =>
      sessionToken = session("token").as[String]
      session
    }

  val benchscn = scenario("POCSimulation.bench")
    .exec(http("home_page")
      .get("/silverpeas/services/spaces")
      .headers(sessionHeader())
      .resources(http("request_3")
      .get(uri1 + "/silverpeas/services/spaces/2/spaces")
      .headers(sessionHeader()),
            http("request_4")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_5")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_6")
      .get(uri1 + "/silverpeas/services/spaces/2/components")
      .headers(sessionHeader()),
            http("request_7")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_8")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader())))
    .pause(2)
    .exec(http("go_to_space_1")
      .get("/silverpeas/services/spaces/1")
      .headers(sessionHeader()))
    .pause(2)
    .exec(http("go_to_blog5")
      .get("/silverpeas/services/components/5")
      .headers(sessionHeader())
      .resources(http("request_11")
      .get(uri1 + "/silverpeas/services/spaces/1")
      .headers(sessionHeader()),
            http("request_12")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_13")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_14")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts")
      .headers(sessionHeader()),
            http("request_15")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=1;5")
      .headers(sessionHeader()),
            http("request_16")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader()),
            http("request_17")
      .get(uri1 + "/silverpeas/services/rating/blog5/Publication/41")
      .headers(sessionHeader()),
            http("request_18")
      .get(uri1 + "/silverpeas/services/rating/blog5/Publication/42")
      .headers(sessionHeader()),
            http("request_19")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader())))
    .pause(4)
    .exec(http("go_to_post_41_in_blog5")
      .get("/silverpeas/services/components/blog5")
      .headers(sessionHeader())
      .resources(http("request_21")
      .get(uri1 + "/silverpeas/services/spaces/1")
      .headers(sessionHeader()),
            http("request_22")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_23")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_24")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts/41")
      .headers(sessionHeader()),
            http("request_25")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader()),
            http("request_26")
      .get(uri1 + "/gwt-errai/blog/blog5/post/img/mandatoryField.gif")
      .headers(headers_26),
            http("request_27")
      .get(uri1 + "/silverpeas/services/comments/blog5/Publication/41?_=1433151123411")
      .headers(headers_27 ++  sessionHeader()) ,
            http("request_28")
      .get(uri1 + "/silverpeas/services/rating/blog5/Publication/41")
      .headers(sessionHeader()),
            http("request_29")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader())))
    .pause(7)
    .exec(http("update_post_41_in_blog5")
      .put("/silverpeas/services/blogs/blog5/posts/41")
      .headers(headers_30 ++ sessionHeader())
      .body(RawFileBody("POCSimulation_0030_request.txt")))
    .pause(3)
    .exec(http("scroll_blog_5-1")
      .get("/silverpeas/services/blogs/blog5/posts?page=6;1")
      .headers(sessionHeader())
      .resources(http("request_32")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=7;1")
      .headers(sessionHeader())))
    .pause(1)
    .exec(http("scroll_blog5-2")
      .get("/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader())
      .resources(http("request_34")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_35")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts")
      .headers(sessionHeader()),
            http("request_36")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=1;5")
      .headers(sessionHeader()),
            http("request_37")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader()),
            http("request_38")
      .get(uri1 + "/silverpeas/services/rating/blog5/Publication/41")
      .headers(sessionHeader()),
            http("request_39")
      .get(uri1 + "/silverpeas/services/rating/blog5/Publication/42")
      .headers(sessionHeader()),
            http("request_40")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader())))
    .pause(1)
    .exec(http("scroll-blog5-3")
      .get("/silverpeas/services/blogs/blog5/posts?page=8;1")
      .headers(sessionHeader()))
    .pause(7)
    .exec(http("go_directly_to_blog1")
      .get("/silverpeas/services/components/1")
      .headers(sessionHeader())
      .resources(http("request_43")
      .get(uri1 + "/silverpeas/services/spaces/1")
      .headers(sessionHeader()),
            http("request_44")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_45")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_46")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader()),
            http("request_47")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=1;5")
      .headers(sessionHeader()),
            http("request_48")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader()),
            http("request_49")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/9")
      .headers(sessionHeader()),
            http("request_50")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/7")
      .headers(sessionHeader()),
            http("request_51")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/6")
      .headers(sessionHeader()),
            http("request_52")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/48")
      .headers(sessionHeader()),
            http("request_53")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/52")
      .headers(sessionHeader()),
            http("request_54")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts")
      .headers(sessionHeader())))
    .pause(6)
    .exec(http("update_title_of_post_9_in_blog1")
      .options("/silverpeas/services/blogs/blog1/posts/9")
      .headers(headers_30 ++ sessionHeader())
      .resources(http("request_55")
      .put("/silverpeas/services/blogs/blog1/posts/9")
      .headers(headers_30 ++ sessionHeader())
      .body(RawFileBody("POCSimulation_0055_request.txt"))))
    .pause(5)
    .exec(http("request_56")
      .get("/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader())
      .resources(http("request_57")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_58")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader()),
            http("request_59")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader())))
    .pause(16)
    .exec(http("create_new_post_in_blog1")
      .post("/silverpeas/services/blogs/blog1/posts/")
      .headers(headers_60 ++ sessionHeader())
      .body(RawFileBody("POCSimulation_0060_request.txt"))
      .resources(http("request_61")
      .get(uri1 + "/silverpeas/services/comments/blog1/Publication/53?_=1433151123412")
      .headers(headers_27 ++  sessionHeader()) ,
            http("request_62")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/53")
      .headers(sessionHeader())))
    .pause(4)
    .exec(http("scroll_blog1-1")
      .get("/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader())
      .resources(http("request_64")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_65")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader()),
            http("request_66")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=1;5")
      .headers(sessionHeader()),
            http("request_67")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader()),
            http("request_68")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/9")
      .headers(sessionHeader()),
            http("request_69")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/7")
      .headers(sessionHeader()),
            http("request_70")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/6")
      .headers(sessionHeader()),
            http("request_71")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/53")
      .headers(sessionHeader()),
            http("request_72")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/52")
      .headers(sessionHeader()),
            http("request_73")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts")
      .headers(sessionHeader())))
    .pause(2)
    .exec(http("scroll_blog1-2")
      .get("/silverpeas/services/blogs/blog5/posts?page=9;1")
      .headers(sessionHeader())
      .resources(http("request_75")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=10;1")
      .headers(sessionHeader()),
            http("request_76")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=11;1")
      .headers(sessionHeader()),
            http("request_77")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=6;1")
      .headers(sessionHeader()),
            http("request_78")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=6;1")
      .headers(sessionHeader()),
            http("request_79")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/52")
      .headers(sessionHeader()),
            http("request_80")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=12;1")
      .headers(sessionHeader()),
            http("request_81")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=7;1")
      .headers(sessionHeader()),
            http("request_82")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/52")
      .headers(sessionHeader()),
            http("request_83")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/48")
      .headers(sessionHeader()),
            http("request_84")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=13;1")
      .headers(sessionHeader()),
            http("request_85")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=7;1")
      .headers(sessionHeader()),
            http("request_86")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=8;1")
      .headers(sessionHeader()),
            http("request_87")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=14;1")
      .headers(sessionHeader()),
            http("request_88")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/48")
      .headers(sessionHeader()),
            http("request_89")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/50")
      .headers(sessionHeader()),
            http("request_90")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=15;1")
      .headers(sessionHeader()),
            http("request_91")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=8;1")
      .headers(sessionHeader()),
            http("request_92")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=9;1")
      .headers(sessionHeader()),
            http("request_93")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/50")
      .headers(sessionHeader()),
            http("request_94")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/48")
      .headers(sessionHeader()),
            http("request_95")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=16;1")
      .headers(sessionHeader()),
            http("request_96")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=9;1")
      .headers(sessionHeader()),
            http("request_97")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=10;1")
      .headers(sessionHeader()),
            http("request_98")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=17;1")
      .headers(sessionHeader()),
            http("request_99")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/48")
      .headers(sessionHeader()),
            http("request_100")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/37")
      .headers(sessionHeader()),
            http("request_101")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=10;1")
      .headers(sessionHeader()),
            http("request_102")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=11;1")
      .headers(sessionHeader()),
            http("request_103")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/37")
      .headers(sessionHeader()),
            http("request_104")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/38")
      .headers(sessionHeader()),
            http("request_105")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=18;1")
      .headers(sessionHeader()),
            http("request_106")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=12;1")
      .headers(sessionHeader()),
            http("request_107")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=11;1")
      .headers(sessionHeader()),
            http("request_108")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/39")
      .headers(sessionHeader()),
            http("request_109")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/38")
      .headers(sessionHeader()),
            http("request_110")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=19;1")
      .headers(sessionHeader()),
            http("request_111")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=13;1")
      .headers(sessionHeader()),
            http("request_112")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=12;1")
      .headers(sessionHeader()),
            http("request_113")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/39")
      .headers(sessionHeader()),
            http("request_114")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/40")
      .headers(sessionHeader()),
            http("request_115")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=20;1")
      .headers(sessionHeader()),
            http("request_116")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=13;1")
      .headers(sessionHeader()),
            http("request_117")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=14;1")
      .headers(sessionHeader()),
            http("request_118")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=21;1")
      .headers(sessionHeader()),
            http("request_119")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/40")
      .headers(sessionHeader()),
            http("request_120")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/5")
      .headers(sessionHeader()),
            http("request_121")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=22;1")
      .headers(sessionHeader()),
            http("request_122")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=14;1")
      .headers(sessionHeader()),
            http("request_123")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=15;1")
      .headers(sessionHeader()),
            http("request_124")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/5")
      .headers(sessionHeader()),
            http("request_125")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/3")
      .headers(sessionHeader()),
            http("request_126")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=23;1")
      .headers(sessionHeader()),
            http("request_127")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=16;1")
      .headers(sessionHeader()),
            http("request_128")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=15;1")
      .headers(sessionHeader()),
            http("request_129")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/3")
      .headers(sessionHeader()),
            http("request_130")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/4")
      .headers(sessionHeader()),
            http("request_131")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=24;1")
      .headers(sessionHeader()),
            http("request_132")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=16;1")
      .headers(sessionHeader()),
            http("request_133")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=17;1")
      .headers(sessionHeader()),
            http("request_134")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/4")
      .headers(sessionHeader()),
            http("request_135")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/2")
      .headers(sessionHeader()),
            http("request_136")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=25;1")
      .headers(sessionHeader()),
            http("request_137")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=18;1")
      .headers(sessionHeader()),
            http("request_138")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=17;1")
      .headers(sessionHeader()),
            http("request_139")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/1")
      .headers(sessionHeader()),
            http("request_140")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/2")
      .headers(sessionHeader()),
            http("request_141")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=26;1")
      .headers(sessionHeader()),
            http("request_142")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=19;1")
      .headers(sessionHeader()),
            http("request_143")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=18;1")
      .headers(sessionHeader()),
            http("request_144")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=27;1")
      .headers(sessionHeader()),
            http("request_145")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/1")
      .headers(sessionHeader()),
            http("request_146")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=19;1")
      .headers(sessionHeader()),
            http("request_147")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=28;1")
      .headers(sessionHeader()),
            http("request_148")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=20;1")
      .headers(sessionHeader()),
            http("request_149")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=20;1")
      .headers(sessionHeader()),
            http("request_150")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=21;1")
      .headers(sessionHeader()),
            http("request_151")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=29;1")
      .headers(sessionHeader()),
            http("request_152")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=22;1")
      .headers(sessionHeader()),
            http("request_153")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=21;1")
      .headers(sessionHeader()),
            http("request_154")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=30;1")
      .headers(sessionHeader()),
            http("request_155")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=31;1")
      .headers(sessionHeader()),
            http("request_156")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=23;1")
      .headers(sessionHeader()),
            http("request_157")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=22;1")
      .headers(sessionHeader()),
            http("request_158")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=23;1")
      .headers(sessionHeader()),
            http("request_159")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=32;1")
      .headers(sessionHeader()),
            http("request_160")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=24;1")
      .headers(sessionHeader()),
            http("request_161")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=33;1")
      .headers(sessionHeader()),
            http("request_162")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=25;1")
      .headers(sessionHeader()),
            http("request_163")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=24;1")
      .headers(sessionHeader()),
            http("request_164")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=25;1")
      .headers(sessionHeader()),
            http("request_165")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=34;1")
      .headers(sessionHeader()),
            http("request_166")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=26;1")
      .headers(sessionHeader()),
            http("request_167")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=35;1")
      .headers(sessionHeader()),
            http("request_168")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=27;1")
      .headers(sessionHeader()),
            http("request_169")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=26;1")
      .headers(sessionHeader()),
            http("request_170")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=36;1")
      .headers(sessionHeader()),
            http("request_171")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=28;1")
      .headers(sessionHeader()),
            http("request_172")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=27;1")
      .headers(sessionHeader()),
            http("request_173")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=29;1")
      .headers(sessionHeader()),
            http("request_174")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=37;1")
      .headers(sessionHeader()),
            http("request_175")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=30;1")
      .headers(sessionHeader()),
            http("request_176")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=38;1")
      .headers(sessionHeader()),
            http("request_177")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=28;1")
      .headers(sessionHeader()),
            http("request_178")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=31;1")
      .headers(sessionHeader()),
            http("request_179")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=39;1")
      .headers(sessionHeader()),
            http("request_180")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=29;1")
      .headers(sessionHeader()),
            http("request_181")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=32;1")
      .headers(sessionHeader()),
            http("request_182")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=30;1")
      .headers(sessionHeader()),
            http("request_183")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=40;1")
      .headers(sessionHeader()),
            http("request_184")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=41;1")
      .headers(sessionHeader()),
            http("request_185")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=31;1")
      .headers(sessionHeader()),
            http("request_186")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=33;1")
      .headers(sessionHeader()),
            http("request_187")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=34;1")
      .headers(sessionHeader()),
            http("request_188")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=32;1")
      .headers(sessionHeader()),
            http("request_189")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=42;1")
      .headers(sessionHeader()),
            http("request_190")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=35;1")
      .headers(sessionHeader()),
            http("request_191")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=43;1")
      .headers(sessionHeader()),
            http("request_192")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=33;1")
      .headers(sessionHeader()),
            http("request_193")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=34;1")
      .headers(sessionHeader()),
            http("request_194")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=44;1")
      .headers(sessionHeader()),
            http("request_195")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=36;1")
      .headers(sessionHeader()),
            http("request_196")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=37;1")
      .headers(sessionHeader()),
            http("request_197")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=45;1")
      .headers(sessionHeader()),
            http("request_198")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=35;1")
      .headers(sessionHeader()),
            http("request_199")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=38;1")
      .headers(sessionHeader()),
            http("request_200")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=36;1")
      .headers(sessionHeader()),
            http("request_201")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=46;1")
      .headers(sessionHeader()),
            http("request_202")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=47;1")
      .headers(sessionHeader()),
            http("request_203")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=39;1")
      .headers(sessionHeader()),
            http("request_204")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=37;1")
      .headers(sessionHeader()),
            http("request_205")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=38;1")
      .headers(sessionHeader()),
            http("request_206")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=40;1")
      .headers(sessionHeader()),
            http("request_207")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=48;1")
      .headers(sessionHeader()),
            http("request_208")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=41;1")
      .headers(sessionHeader()),
            http("request_209")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=39;1")
      .headers(sessionHeader()),
            http("request_210")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=49;1")
      .headers(sessionHeader()),
            http("request_211")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=50;1")
      .headers(sessionHeader()),
            http("request_212")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=40;1")
      .headers(sessionHeader()),
            http("request_213")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=42;1")
      .headers(sessionHeader()),
            http("request_214")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=43;1")
      .headers(sessionHeader()),
            http("request_215")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=51;1")
      .headers(sessionHeader()),
            http("request_216")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=41;1")
      .headers(sessionHeader()),
            http("request_217")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=42;1")
      .headers(sessionHeader()),
            http("request_218")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=44;1")
      .headers(sessionHeader()),
            http("request_219")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=52;1")
      .headers(sessionHeader()),
            http("request_220")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=43;1")
      .headers(sessionHeader()),
            http("request_221")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=53;1")
      .headers(sessionHeader()),
            http("request_222")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=45;1")
      .headers(sessionHeader()),
            http("request_223")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=54;1")
      .headers(sessionHeader()),
            http("request_224")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=46;1")
      .headers(sessionHeader()),
            http("request_225")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=44;1")
      .headers(sessionHeader()),
            http("request_226")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=45;1")
      .headers(sessionHeader()),
            http("request_227")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=47;1")
      .headers(sessionHeader()),
            http("request_228")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=55;1")
      .headers(sessionHeader()),
            http("request_229")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=56;1")
      .headers(sessionHeader()),
            http("request_230")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=48;1")
      .headers(sessionHeader()),
            http("request_231")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=46;1")
      .headers(sessionHeader()),
            http("request_232")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=57;1")
      .headers(sessionHeader()),
            http("request_233")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=49;1")
      .headers(sessionHeader()),
            http("request_234")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=47;1")
      .headers(sessionHeader()),
            http("request_235")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=58;1")
      .headers(sessionHeader()),
            http("request_236")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=50;1")
      .headers(sessionHeader()),
            http("request_237")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=51;1")
      .headers(sessionHeader()),
            http("request_238")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=48;1")
      .headers(sessionHeader()),
            http("request_239")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=59;1")
      .headers(sessionHeader()),
            http("request_240")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=49;1")
      .headers(sessionHeader()),
            http("request_241")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=52;1")
      .headers(sessionHeader()),
            http("request_242")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=60;1")
      .headers(sessionHeader()),
            http("request_243")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=50;1")
      .headers(sessionHeader()),
            http("request_244")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=61;1")
      .headers(sessionHeader()),
            http("request_245")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=53;1")
      .headers(sessionHeader()),
            http("request_246")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=51;1")
      .headers(sessionHeader()),
            http("request_247")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=54;1")
      .headers(sessionHeader()),
            http("request_248")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=62;1")
      .headers(sessionHeader()),
            http("request_249")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=55;1")
      .headers(sessionHeader()),
            http("request_250")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=52;1")
      .headers(sessionHeader()),
            http("request_251")
      .get(uri1 + "/silverpeas/services/blogs/blog5/posts?page=63;1")
      .headers(sessionHeader())))
    .pause(11)
    .exec(http("ask_for_deletion")
      .get("/gwt-errai/css/jquery/images/ui-bg_highlight-soft_100_eeeeee_1x100.png")
      .headers(headers_26)
      .resources(http("request_253")
      .get(uri1 + "/gwt-errai/css/jquery/images/ui-bg_glass_100_f6f6f6_1x400.png")
      .headers(headers_26)
      .check(status.is(404)),
            http("request_254")
      .get(uri1 + "/gwt-errai/css/jquery/images/ui-bg_glass_100_fdf5ce_1x400.png")
      .headers(headers_26)
      .check(status.is(404)))
      .check(status.is(404)))
    .pause(1)
    .exec(http("delete_post_48_in_blog1")
      .delete("/silverpeas/services/blogs/blog1/posts/48")
      .headers(headers_30 ++ sessionHeader())
      .check(status.is(405))) // the data is already deleted
    .pause(8)
    .exec(http("delete_post_53_in_blog1")
      .delete("/silverpeas/services/blogs/blog1/posts/53")
      .headers(headers_30 ++ sessionHeader())
      .check(status.is(405))) // the data is already deleted
    .pause(5)
    .exec(http("back_to_space_1")
      .get("/silverpeas/services/spaces/1")
      .headers(sessionHeader()))
    .pause(1)
    .exec(http("go_to_blog1")
      .get("/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader())
      .resources(http("request_259")
      .get(uri1 + "/silverpeas/services/spaces/1/spaces")
      .headers(sessionHeader()),
            http("request_260")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader()),
            http("request_261")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=1;5")
      .headers(sessionHeader()),
            http("request_262")
      .get(uri1 + "/silverpeas/services/spaces/1/components")
      .headers(sessionHeader()),
            http("request_263")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/9")
      .headers(sessionHeader()),
            http("request_264")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/7")
      .headers(sessionHeader()),
            http("request_265")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/6")
      .headers(sessionHeader()),
            http("request_266")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/49")
      .headers(sessionHeader()),
            http("request_267")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/52")
      .headers(sessionHeader()),
            http("request_268")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts")
      .headers(sessionHeader())))
    .pause(1)
    .exec(http("scroll_blog1-3")
      .get("/silverpeas/services/blogs/blog1/posts?page=6;1")
      .headers(sessionHeader())
      .resources(http("request_270")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/49")
      .headers(sessionHeader()),
            http("request_271")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=7;1")
      .headers(sessionHeader()),
            http("request_272")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/49")
      .headers(sessionHeader()),
            http("request_273")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=8;1")
      .headers(sessionHeader()),
            http("request_274")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/37")
      .headers(sessionHeader()),
            http("request_275")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=9;1")
      .headers(sessionHeader()),
            http("request_276")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/38")
      .headers(sessionHeader()),
            http("request_277")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=10;1")
      .headers(sessionHeader()),
            http("request_278")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/39")
      .headers(sessionHeader()),
            http("request_279")
      .get(uri1 + "/silverpeas/services/blogs/blog1/posts?page=11;1")
      .headers(sessionHeader()),
            http("request_280")
      .get(uri1 + "/silverpeas/services/rating/blog1/Publication/40")
      .headers(sessionHeader())))
    .pause(4)
    .exec(http("delete_post_49_in_blog1")
      .delete("/silverpeas/services/blogs/blog1/posts/49")
      .headers(headers_30 ++ sessionHeader())
      .check(status.is(405))) // the data is already deleted

  setUp(loginscn.inject(atOnceUsers(1)), benchscn.inject(rampUsers(100).over(50 seconds))).protocols(httpProtocol)
}