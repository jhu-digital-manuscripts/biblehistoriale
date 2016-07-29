package edu.jhu.library.biblehistoriale.website.server;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;

/**
 * ContextRemoteServiceServlet is an extension of RemoteServiceServlet that
 * loads the RPC serialization policy based on the context name rather than URL if it is
 * not successfully loaded using the GWT's default method. This is useful if
 * you're proxying requests to RPC servlets from one server to another and the
 * paths to the servlets don't match perfectly (causing the RPC policy file to fail to load).
 *
 * Captured from: https://gist.github.com/BinaryMuse/476175
 */
public class ContextRemoteServiceServlet extends RemoteServiceServlet {

    private static final long serialVersionUID = -4332306688541651819L;

    /**
     * Attempt to load the RPC serialization policy normally. If it isn't found,
     * try loading it using the context path instead of the URL.
     */
    @Override
    protected SerializationPolicy doGetSerializationPolicy(HttpServletRequest request, String moduleBaseURL, String strongName)
    {
        SerializationPolicy policy = super.doGetSerializationPolicy(request, moduleBaseURL, strongName);
        if (policy == null) {
            String moduleName = getServletConfig().getInitParameter("moduleName");
            return ContextRemoteServiceServlet.loadSerializationPolicy(this, request, moduleBaseURL, moduleName, strongName);
        } else {
            return policy;
        }
    }

    /**
     * Load the RPC serialization policy via the context path.
     */
    static SerializationPolicy loadSerializationPolicy(HttpServlet servlet,
                                                       HttpServletRequest request, String moduleBaseUrl,
                                                       String moduleName, String strongName) {
        servlet.log("INFO: moduleName: (" + moduleName + "), strongName: (" + strongName + ") moduleBaseUrl: (" + moduleBaseUrl + ")");
        SerializationPolicy serializationPolicy = null;
        // The serialization policy path depends only by context path
        String contextPath = request.getContextPath();
        String contextRelativePath = contextPath + "/" + moduleName + "/";

        String serializationPolicyFilePath = SerializationPolicyLoader.getSerializationPolicyFileName(contextRelativePath
                + strongName);

//         Open the RPC resource file and read its contents.
        try (InputStream is = servlet.getServletContext().getResourceAsStream(serializationPolicyFilePath)) {
            if (is != null) {
                serializationPolicy = SerializationPolicyLoader.loadFromStream(is, null);
            } else {
                String message = "ERROR: The serialization policy file '" + serializationPolicyFilePath +
                        "' was not found; did you forget to include it in this deployment?";
                servlet.log(message);
                return null;
            }
        } catch (ParseException e) {
            servlet.log("ERROR: Failed to parse the policy file '" + serializationPolicyFilePath + "'", e);
        } catch (IOException e) {
            servlet.log("ERROR: Could not read the policy file '" + serializationPolicyFilePath + "'", e);
        }

        return serializationPolicy;

//        String serializationUrl = moduleBaseUrl + "/bibhist" + "/" + serializationPolicyFilePath;
//        servlet.log("INFO: serializationPolicyUrl: '" + serializationUrl + "'");
//        try (InputStream in = new URL(serializationUrl).openStream()) {
//            servlet.log("INFO: ");
//            return SerializationPolicyLoader.loadFromStream(in, null);
//        } catch (ParseException e) {
//            servlet.log("ERROR: Failed to parse the policy file '" + serializationPolicyFilePath + "'", e);
//        } catch (MalformedURLException e) {
//            servlet.log("ERROR: Failed to form valid URL for policy file '" + serializationPolicyFilePath + "'", e);
//        } catch (IOException e) {
//            servlet.log("ERROR: Could not read the policy file '" + serializationPolicyFilePath + "'", e);
//        }
    }

}