package ddd.caffeine.ratrip.common.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * RestControllerAdvice : 모든 @Controller 에 대한 전역적으로 발생 할 수 있는 예외를 잡아서 처리 가능.
 * RestControllerAdvice 는 응답을 JSON 객체로 리턴 할 수 있기 때문에 선택. (ErrorCode 객체 리턴)
 * ExceptionHandler : 어노테이션을 메서드에 선언하고 특정 예외 클래스를 지정해주면 해당 예외가 발생했을 때 메서드에 정의한 로직을 처리.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * BaseException 으로 throw 한 예외들 처리 하는 메서드.
	 */
	@ExceptionHandler(BaseException.class)
	protected ResponseEntity<ExceptionResponse> handleExpectedException(BaseException e) {
		log.error("code : {}, message : {}", e.getErrorCode(), e.getMessage());
		ExceptionResponse response = ExceptionResponse.builder()
			.httpStatus(e.getHttpStatus())
			.errorCode(e.getErrorCode())
			.message(e.getMessage())
			.build();

		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	/**
	 * 예상하지 못한 예외들 처리.
	 * 즉 throw 하지 못한 예외들. Runtime 중에 발생하는 모든 예외들 처리.
	 */
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<ExceptionResponse> handleUnexpectedException(RuntimeException e) {
		log.error("cause : {}, message : {}", e.getCause(), e.getMessage());
		ExceptionResponse response = ExceptionResponse.builder()
			.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
			.errorCode("UNEXPECTED_EXCEPTION")
			.message("Internal Server Error")
			.build();

		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	/**
	 * @Validated 어노테이션으로 잡히는 예외 핸들러.
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException e) {

		e.getConstraintViolations().stream().peek(exception -> log.error(exception.getMessage()));

		ExceptionResponse response = ExceptionResponse.builder()
			.errorCode(e.toString())
			.httpStatus(HttpStatus.BAD_REQUEST)
			.message(e.getMessage())
			.build();

		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
		HttpStatus status, WebRequest request) {

		log.error("cause : {}, message : {}", ex.getCause(), ex.getMessage());
		ExceptionResponse response = ExceptionResponse.builder()
			.httpStatus(status)
			.errorCode(ex.getClass().getSimpleName())
			.message(ex.getMessage())
			.build();

		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	/**
	 * 요청 경로는 있으나 지원하지 않는 Method인 경우 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
	}

	/**
	 * 요청의 Content Type을 핸들러가 지원하지 않는 경우 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleHttpMediaTypeNotSupported(ex, headers, status, request);
	}

	/**
	 * 핸들러가 Client가 요청한 Type으로 응답을 내려줄 수 없는 경우 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleHttpMediaTypeNotAcceptable(ex, headers, status, request);
	}

	/**
	 * 핸들러가 URL에서 기대한 Path Variable을 찾지 못한 경우 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		return super.handleMissingPathVariable(ex, headers, status, request);
	}

	/**
	 * 핸들러가 기대한 요청 Parameter를 찾지 못한 경우 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleMissingServletRequestParameter(ex, headers, status, request);
	}

	/**
	 * 바인딩 예외를 복구할 수 없는 것으로 처리하려는 경우 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleServletRequestBindingException(ex, headers, status, request);
	}

	/**
	 * bean property로 요청 내용을 변경하기 위한 editor 혹은 converter를 찾지 못한 경우 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleConversionNotSupported(ex, headers, status, request);
	}

	/**
	 * bean property로 값을 변경할 때, 핸들러가 예상한 class로 변경할 수 없는 경우 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		return super.handleTypeMismatch(ex, headers, status, request);
	}

	/**
	 * HttpMessageConverter에서 발생하며 read 메서드가 실패한 경우 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleHttpMessageNotReadable(ex, headers, status, request);
	}

	/**
	 * HttpMessageConverter에서 발생하며 write 메서드가 실패한 경우 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleHttpMessageNotWritable(ex, headers, status, request);
	}

	/**
	 * @RequestBody 사용시 @Valid가 붙은 파라미터에 대해 검증 실패시 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}

	/**
	 * multipart/form-data 요청의 일부가 손실(can’t be found)되었을 때 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleMissingServletRequestPart(ex, headers, status, request);
	}

	/**
	 * @ModelAttribute 사용시 @Valid가 붙은 파라미터에 대해 검증 실패시 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
		WebRequest request) {
		return super.handleBindException(ex, headers, status, request);
	}

	/**
	 * Dispatcher Servlet에서 핸들러를 찾지 못한 경우 기본적으로 404 응답을 내리지만
	 * Dispatcher Servlet의 throwExceptionIfNoHandlerFound 값이 true인 경우 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		return super.handleNoHandlerFoundException(ex, headers, status, request);
	}

	/**
	 * 비동기 요청의 응답시간이 초과될 때 발생하는 예외
	 */
	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
		HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		return super.handleAsyncRequestTimeoutException(ex, headers, status, webRequest);
	}
}

