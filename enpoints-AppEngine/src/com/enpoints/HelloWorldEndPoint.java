package com.enpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

@Api(name = "example")
public class HelloWorldEndPoint {
	@ApiMethod(name = "holaMundo")
 public Container getThing() {
  Container c = new Container();
  c.Text = "Este es un testo desde mi backend!";
  return c;
 }

 public class Container {
  public String Text;
 }
}


