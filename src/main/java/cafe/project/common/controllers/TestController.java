package cafe.project.common.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	
	@GetMapping("/test-image")
	public ResponseEntity<Resource> testImage() {
	    Resource resource = new ClassPathResource("static/images/Open.png");

	    return ResponseEntity
	            .ok()
	            .contentType(MediaType.IMAGE_PNG)
	            .body(resource);
	}

}
