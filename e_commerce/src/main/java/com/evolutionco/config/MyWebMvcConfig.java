package com.evolutionco.config;

import java.nio.charset.Charset;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc
@Configuration
public class MyWebMvcConfig extends WebMvcConfigurerAdapter{
	 private static final Charset UTF8 = Charset.forName("UTF-8");
	 
	    // config UTF-8 Encoding.
	 	// allow us to change the default list of Http Converters with our own.
	 public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
	        stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "css", UTF8)));
	        converters.add(stringConverter);
	        System.out.println("Converters");
	 }
	 
	 	// Static Resource Config from classpath
	    // equivalents for <mvc:resources/> tags
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/webapp/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
	        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
	        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
	        System.out.println("registry");
	    }
	 
	    // equivalent for <mvc:default-servlet-handler/> tag
	    @Override
	    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	        configurer.enable();
	        System.out.println("configurer");
	    }
}
