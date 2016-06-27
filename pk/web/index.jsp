<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head></head>  
	<body>
            <%
                response.sendRedirect("faces/modules/Home.xhtml");
                //java.util.Enumeration names = request.getHeaderNames();
                //while (names.hasMoreElements()) {
                //    String name = (String)names.nextElement();
                //    out.println(name+": "+request.getHeader(name)+"<br/>");
                //}
            %>
	</body>
</html>