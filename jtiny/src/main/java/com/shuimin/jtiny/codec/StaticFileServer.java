package com.shuimin.jtiny.codec;

import com.shuimin.base.S;
import com.shuimin.jtiny.core.RequestHandler;
import com.shuimin.jtiny.core.exception.YException;
import com.shuimin.jtiny.core.http.HttpMethod;
import com.shuimin.jtiny.core.http.Request;
import com.shuimin.jtiny.core.http.Response;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static com.shuimin.base.S.echo;
import static com.shuimin.jtiny.core.Server.G.debug;

/**
 * Created by ed on 2014/4/10.
 */
public class StaticFileServer implements RequestHandler {

    public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    public static final int HTTP_CACHE_SECONDS = 60;


    public String publicDir;

    public StaticFileServer(String absDirPath) {
        if (new File(absDirPath).exists()) {
            publicDir = absDirPath;
        } else {
            publicDir = null;
            throw new YException(this) {
                @Override
                public String brief() {
                    return "Path[" + absDirPath + "] not existed ";
                }
            };
        }
    }

    //TODO: 静态文件服务器，提供缓存策略
    @Override
    public void handle(Request req, Response resp) {
        if (HttpMethod.of(req.method()) != HttpMethod.GET) {
            resp.send(406);
            return;
        }
        final String path = req.path();
        final String absPath = validPath(path);
        echo(absPath);

        debug(path);
        if (S.str.isBlank(absPath)) {
            resp.send(403);
            return;
        }

        File file = new File(absPath);

        if (file.isHidden() || !file.exists()) {
            resp.send(404);
            return;
        }

        if (file.isDirectory()) {
            echo("is dir " + file.getAbsolutePath());
            if(path.endsWith("/")) {
                echo("enter....");
                Listing(resp,file);
            } else {
                resp.redirect(path + '/');
            }
            return;
        }

        if (!file.isFile()) {
            resp.send(403);
            return;
        }

        //cache

        // Cache Validation
        String[] strings = req.headers().get("If-Modified-Since");
        if(strings != null && strings.length > 0){
            SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
            Date ifModifiedSinceDate = null;
            try {
                ifModifiedSinceDate = dateFormatter.parse(strings[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
            long fileLastModifiedSeconds = file.lastModified() / 1000;
            if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
                resp.send(304);//not modified
                return;
            }
        }

       setContentLength(resp,file.length());

       setContentType(resp,file);

       setDateAndCacheHeaders(resp,file);

       resp.sendFile(file);

    }
    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

    private static void Listing(Response resp,File dir){
        resp.contentType("text/html; charset=UTF-8");

        StringBuilder buf = new StringBuilder();
        String dirPath = dir.getPath();

        buf.append("<!DOCTYPE html>\r\n");
        buf.append("<html><head><title>");
        buf.append("Listing of: ");
        buf.append(dirPath);
        buf.append("</title></head><body>\r\n");

        buf.append("<h3>Listing of: ");
        buf.append(dirPath);
        buf.append("</h3>\r\n");

        buf.append("<ul>");
        buf.append("<li><a href=\"../\">..</a></li>\r\n");

        for (File f: dir.listFiles()) {
            if (f.isHidden() || !f.canRead()) {
                continue;
            }

            String name = f.getName();
            if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
                continue;
            }

            buf.append("<li><a href=\"");
            buf.append(name);
            buf.append("\">");
            buf.append(name);
            buf.append("</a></li>\r\n");
        }

        buf.append("</ul></body></html>\r\n");
        resp.write(buf.toString());
        resp.send(200);
    }

    private static final Pattern INSECURE_PATH = Pattern.compile(".*[<>&\"].*");

    private String validPath(String path) {
        if (!path.startsWith("/")) {
            return null;
        }
        String _path = path.replace('/', File.separatorChar);

        if (_path.contains(File.separator + '.') ||
            _path.contains('.' + File.separator) ||
            _path.startsWith(".") || _path.endsWith(".") ||
            INSECURE_PATH.matcher(_path).matches()) {
            return null;
        }

        return publicDir + File.separator + _path;
    }


    private static void setContentLength(Response resp, long length){
        resp.header("Content-Length", String.valueOf(length));
    }

    private static void setDateHeader(Response resp){
        SimpleDateFormat dateFormat = new SimpleDateFormat(HTTP_DATE_FORMAT,Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

        Calendar time = new GregorianCalendar();
        resp.header("Date",dateFormat.format(time.getTime()));
    }

    private static void setDateAndCacheHeaders(Response resp, File toCache){
        SimpleDateFormat dateFormat  = new SimpleDateFormat(HTTP_DATE_FORMAT,Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

        Calendar time = new GregorianCalendar();
        resp.header("Date", dateFormat.format(time.getTime()));

        time.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
        resp.header("Expires", dateFormat.format(time.getTime()));
        resp.header("Cache-Control", "private, max-age=" + HTTP_CACHE_SECONDS);
        resp.header("Last-Modified", dateFormat.format(new Date(toCache.lastModified())));
    }

    private static void setContentType(Response resp, File file){
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        resp.header("Content-Type", mimetypesFileTypeMap.getContentType(file));
    }

}
