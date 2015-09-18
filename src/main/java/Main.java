import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import com.sucks.db.SucksDBManager;
import com.sucks.dto.SucksDto;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.List;

public class Main extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {


      resp.getWriter().print("Hello");

      showDatabase(req,resp);
  }



  private void showDatabase(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{

             List<SucksDto> SucksDto = SucksDBManager.getSucksData();
            resp.getWriter().print(SucksDto);
    }
        catch (Exception e) {
            resp.getWriter().print("There was an error: " + e.getMessage());
        }
    }

  public   static void main(String[] args) throws Exception {
    Server server = new Server(Integer.valueOf(System.getenv("PORT")));


      HandlerCollection handlers = new HandlerCollection();
      ContextHandlerCollection contexts = new ContextHandlerCollection();

      RequestLogHandler logHandler = new RequestLogHandler();
      NCSARequestLog requestLog = new NCSARequestLog("/app/request.log");
      requestLog.setAppend(true);
      requestLog.setExtended(false);
      requestLog.setLogTimeZone("GMT");

      RequestLogHandler requestLogHandler = new RequestLogHandler();
      requestLogHandler.setRequestLog(requestLog);
      handlers.addHandler(requestLogHandler);


     // logHandler.setHandler(webAppContext);

      WebAppContext webAppContext = new WebAppContext();

      webAppContext.setContextPath("/");

      // Parent loader priority is a class loader setting that Jetty accepts.
      // By default Jetty will behave like most web containers in that it will
      // allow your application to replace non-server libraries that are part of the
      // container. Setting parent loader priority to true changes this behavior.
      // Read more here: http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
      webAppContext.setParentLoaderPriority(true);

      final String webappDirLocation = "src/main/webapp/";
      webAppContext.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
      webAppContext.setResourceBase(webappDirLocation);

      webAppContext.setWar("sucks-1.0-SNAPSHOT.war");

      handlers.addHandler(webAppContext);


      server.setHandler(handlers);


    //server.setHandler(webAppContext);


   // context.addServlet(new ServletHolder(new Main()),"/*");

    server.start();
    server.join();
  }
}
