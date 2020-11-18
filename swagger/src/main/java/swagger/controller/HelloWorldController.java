package swagger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "hello controller")
@RestController
public class HelloWorldController {
	
	
	@ApiOperation(value = "hello", httpMethod = "GET")
	@ApiImplicitParams({@ApiImplicitParam(name = "username", value = "username", dataType = "string", paramType = "query")})
	@GetMapping(value = "/hello")
	public String helloWorld(@RequestParam("username") String username) {
		return "hello world";
	}

}
