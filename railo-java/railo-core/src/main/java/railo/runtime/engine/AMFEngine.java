package railo.runtime.engine;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openamf.AMFBody;
import org.openamf.AMFError;
import org.openamf.AMFMessage;
import org.openamf.ServiceRequest;
import org.openamf.io.AMFDeserializer;
import org.openamf.io.AMFSerializer;

import railo.commons.io.IOUtil;
import railo.runtime.exp.PageException;
import railo.runtime.net.amf.CFMLProxy;
import railo.runtime.net.amf.OpenAMFCaster;


/**
 * AMF Engine
 */
public final class AMFEngine {
    


    /**
     * Main entry point for the servlet
     * @param servlet 
     * @param req 
     * @param rsp 
     *
     * @throws ServletException
     * @throws IOException
     */
    public void service(HttpServlet servlet, HttpServletRequest req, HttpServletResponse rsp) throws IOException {
    	
    	AMFMessage requestMessage = null;
        AMFMessage responseMessage = null;
        requestMessage = deserializeAMFMessage(req);
        responseMessage = processMessage(servlet, req, rsp, requestMessage);
        serializeAMFMessage(rsp, responseMessage);
    }

    private AMFMessage deserializeAMFMessage(HttpServletRequest req) throws IOException {
        DataInputStream dis = null;
       	try {
       		dis = new DataInputStream(req.getInputStream());
       		AMFDeserializer deserializer = new AMFDeserializer(dis);
       		AMFMessage message = deserializer.getAMFMessage();
       		return message;
       	}
       	finally {
       		IOUtil.closeEL(dis);
       	}
    }

    private void serializeAMFMessage(HttpServletResponse resp, AMFMessage message) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        AMFSerializer serializer = new AMFSerializer(dos);
        serializer.serializeMessage(message);
        resp.setContentType("application/x-amf");
        resp.setContentLength(baos.size());
        ServletOutputStream sos = resp.getOutputStream(); 
        baos.writeTo(sos);
        sos.flush();
    }

    /**
     * Iterates through the request message's bodies, invokes each body and
     * then, builds a message to send as the results
     * @param req 
     * @param rsp 
     * @param message 
     * @return AMFMessage
     * @throws IOException 
     * @throws ServletException 
     */
    private AMFMessage processMessage(HttpServlet servlet, HttpServletRequest req, HttpServletResponse rsp, AMFMessage message)  {
        AMFMessage responseMessage = new AMFMessage();
        for (Iterator bodies = message.getBodies(); bodies.hasNext();) {
            AMFBody requestBody = (AMFBody) bodies.next();
            // invoke
            Object serviceResult = invokeBody(servlet,req, rsp, requestBody);
            String target = getTarget(requestBody, serviceResult);
            AMFBody responseBody = new AMFBody(target, "null", serviceResult);
            responseMessage.addBody(responseBody);
        }
        return responseMessage;
    }

    
    private Object invokeBody(HttpServlet servlet, HttpServletRequest req, HttpServletResponse rsp, AMFBody requestBody) { 
    	try {
	    	ServiceRequest request = new ServiceRequest(requestBody);
	        rsp.getOutputStream();// MUST muss das sein?
	       
	        return new CFMLProxy().invokeBody(OpenAMFCaster.getInstance(),null,servlet.getServletContext(),servlet.getServletConfig(), req, rsp, request.getServiceName(), request.getServiceMethodName(), request.getParameters());
		} 
    	catch (Exception e) {
    		e.printStackTrace();
            rsp.setStatus(200);
            AMFError error=new AMFError();
            e.setStackTrace(e.getStackTrace());
            error.setDescription(e.getMessage());
			
			if(e instanceof PageException){
				PageException pe = (PageException)e;
	            error.setCode(pe.getErrorCode());
	            error.setCode(pe.getErrorCode());
	            error.setDetails(pe.getDetail());
			}
			
			return error;
		} 
    }

    private String getTarget(AMFBody requestBody, Object serviceResult) {
        String target = "/onResult";
        if (serviceResult instanceof AMFError) {
            target = "/onStatus";
        }
        return requestBody.getResponse() + target;
    }
}