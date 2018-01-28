<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.fileupload.*, org.apache.commons.fileupload.disk.*, org.apache.commons.fileupload.servlet.*, java.io.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
	int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    int MAX_REQUEST_SIZE = 10 * 1024 * 1024;
    
    if (!ServletFileUpload.isMultipartContent(request)) {
    	response.sendRedirect("index.html");
    	return;
    }
    
    DiskFileItemFactory factory = new DiskFileItemFactory();
    factory.setSizeThreshold(MAX_MEMORY_SIZE);
    factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
    String uploadFolder = getServletContext().getRealPath("/") + "fotos";  // Directorio en el que queremos dejar la foto. Ojo: 
	new File(uploadFolder).mkdir();
    
    //TODO EN EL CLIENTE <img src "fotos/nombrefoto.jpg">	
    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setSizeMax(MAX_REQUEST_SIZE);

    try {
        // Parse the request
        List items = upload.parseRequest(request);
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();

            if (!item.isFormField()) {
                String fileName = new File(item.getName()).getName();
                String filePath = uploadFolder + File.separator + fileName;
                File uploadedFile = new File(filePath);
                System.out.println(filePath);
                item.write(uploadedFile);
            }
        }
    } catch (Exception ex) {
        out.print(ex.toString());
    }

    out.print("Foto subida correctamente");
	%>

	
</body>
</html>