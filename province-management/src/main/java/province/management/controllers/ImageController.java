package province.management.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class ImageController {
    @PostMapping("/upload")
    public String upload(ModelMap modelAndView, @RequestParam("image")MultipartFile image){
        if (image.isEmpty()){
            modelAndView.addAttribute("message","please upload an image");
        }
        Path path = Paths.get("images/");
        try (InputStream inputStream = image.getInputStream()){
            Files.copy(inputStream, path.resolve(image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            modelAndView.addAttribute("message", "done!");
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.addAttribute("message", "Error:" + e.getMessage());
        }
        return "uploadForm";
    }
}
