package phattrienungdungvoij2ee.bai4_qlsp.config;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Cấu hình để serve ảnh từ thư mục static/images trong project
		String projectRoot = System.getProperty("user.dir");
		File imagesDir = Paths.get(projectRoot, "src/main/resources/static/images").toFile();
		String imagesPath = imagesDir.getAbsolutePath();
		
		// Đảm bảo đường dẫn có dấu / ở cuối
		if (!imagesPath.endsWith(File.separator) && !imagesPath.endsWith("/")) {
			imagesPath += "/";
		}
		
		// Chuyển đổi đường dẫn Windows sang format URL
		imagesPath = imagesPath.replace("\\", "/");
		
		registry.addResourceHandler("/images/**")
				.addResourceLocations("file:" + imagesPath, "classpath:/static/images/")
				.setCachePeriod(3600);
	}
}
