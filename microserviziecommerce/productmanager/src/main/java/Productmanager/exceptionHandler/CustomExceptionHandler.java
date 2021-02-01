package Productmanager.exceptionHandler;

import com.google.gson.Gson;
import Productmanager.jsonModel.ApiError;
import Productmanager.jsonModel.HttpErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import java.time.Clock;
import java.time.Instant;

import static org.springframework.http.HttpStatus.*;

@EnableWebMvc
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${kafkaTopicLogging}")
    private String topicLogging;

    @Autowired
    private KafkaTemplate<String,String> template;

    public void pushOnTopic(Integer HttpStatus, WebRequest request){
        HttpErrors value = new HttpErrors();
        value.setTimestamp(Instant.now(Clock.systemUTC()).toString());
        value.setError(HttpStatus);

        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        HttpServletRequest response = servletWebRequest.getRequest();
        String ipAddress = response.getHeader("X-FORWARDED-FOR");
        value.setSourceIp( (ipAddress == null) ? response.getRemoteAddr() : ipAddress );

        value.setRequest(((ServletWebRequest)request).getRequest().getRequestURI() + " " + ((ServletWebRequest)request).getRequest().getMethod());
        value.setService("products");
        template.send(topicLogging,"http_errors",new Gson().toJson(value,HttpErrors.class));

    }

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex,WebRequest request) {
        ApiError apiError = new ApiError("Product not found!",NOT_FOUND);
        pushOnTopic(NOT_FOUND.value(),request);
        return new ResponseEntity(apiError, apiError.getStatus());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    protected ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError("Category not found!",NOT_FOUND);
        pushOnTopic(NOT_FOUND.value(),request);
        return new ResponseEntity(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity <Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(ex.getMessage(),BAD_REQUEST);
        pushOnTopic(BAD_REQUEST.value(),request);
        return new ResponseEntity(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        ApiError apiError = new ApiError(ex.getMessage(),BAD_REQUEST);
        pushOnTopic(BAD_REQUEST.value(),request);
        return new ResponseEntity(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(ex.getMessage(),BAD_REQUEST);
        pushOnTopic(BAD_REQUEST.value(),request);
        return new ResponseEntity(apiError, apiError.getStatus());
    }

}
