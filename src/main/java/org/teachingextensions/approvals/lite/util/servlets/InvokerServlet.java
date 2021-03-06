package org.teachingextensions.approvals.lite.util.servlets;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.teachingextensions.approvals.lite.util.MySystem;
import org.teachingextensions.approvals.lite.util.ObjectUtils;

/**
 * The default servlet-invoking servlet for most web applications, used to serve
 * requests to servlets that have not been registered in the web application
 * deployment descriptor.
 *
 * @author Craig R. McClanahan
 * @version $Revision$ $Date$
 */
public final class InvokerServlet extends HttpServlet
{
  private static final long    serialVersionUID = 7573882633420881472L;
  HashMap<String, HttpServlet> servlets         = new HashMap<>();
  private String               mask;
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    serveRequest(request, response);
  }
  @Override
  public void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException,
      ServletException
  {
    serveRequest(request, response);
  }
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
      ServletException
  {
    serveRequest(request, response);
  }
  /**
   * Initialize this servlet.
   */
  @Override
  public void init() throws ServletException
  {
    mask = getServletConfig().getInitParameter("mask");
    MySystem.variable("Mask", mask);
  }
  @Override
  public void destroy()
  {
    for (HttpServlet servlet : servlets.values())
    {
      servlet.destroy();
    }
    super.destroy();
  }
  public void serveRequest(HttpServletRequest request, HttpServletResponse response) throws IOException,
      ServletException
  {
    String pathInfo = request.getPathInfo();
    String servletClass = pathInfo.substring(1);
    int slash = servletClass.indexOf('/');
    if (slash >= 0)
    {
      servletClass = servletClass.substring(0, slash);
    }
    if (!servletClass.startsWith(mask))
    {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, servletClass);
      return;
    }
    HttpServlet servlet;
    synchronized (this)
    {
      servlet = servlets.get(servletClass);
      if (servlet == null)
      {
        try
        {
          servlet = (HttpServlet) Class.forName(servletClass).newInstance();
          servlet.init(getServletConfig());
        }
        catch (Throwable e)
        {
          ObjectUtils.throwAsError(e);
        }
        servlets.put(servletClass, servlet);
      }
    }
    servlet.service(request, response);
  }
}
