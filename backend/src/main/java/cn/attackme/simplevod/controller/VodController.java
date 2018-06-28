package cn.attackme.simplevod.controller;

import cn.attackme.simplevod.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@CrossOrigin
public class VodController {
    @GetMapping("/")
    public void index(String path,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        if (!FileUtils.isExist(path)) {
            response.setStatus(404);
            return;
        }
        long length = FileUtils.getLength(path);
        String range = request.getHeader("Range");
        if (range == null) {
            response.setHeader("Content-Length", String.valueOf(length));
            response.setStatus(200);
            FileUtils.read(path, response.getOutputStream());
        } else {
            range = range.split("=")[1];
            long start = Long.parseLong(range.split("-")[0]);
            response.setHeader("Content-Length", String.valueOf(length-start));
            response.setHeader("Content-Range", "bytes " +
                    String.valueOf(start) + "-" + String.valueOf(length-1) +
                    "/" + String.valueOf(length));
            response.setHeader("Content-Type","video/"+path.split("\\.")[1]);
            response.setStatus(206);
            FileUtils.readWithSeek(path, response.getOutputStream(), start);
        }
    }
}
