package com.baofeng.dispatchexecutor.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

import com.baofeng.dispatchexecutor.bean.HttpResult;

public class HttpClientUtils {

	private HttpClient client = null;

	private String accept = "*/*";
	private String user_agent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322; InfoPath.1; .NET CLR 2.0.50727)";
	private String accept_language = "zh-cn";
	private String accept_encoding = "x-compress; x-zip";
	private String keep_alive_time = "115";
	private String connection = "keep_alive";
	private int connectionTimeout = 10000;
	private int soTimeout = 10000;

	public HttpClientUtils() {
		MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
		// multiThreadedHttpConnectionManager.setMaxConnectionsPerHost(10);
		// multiThreadedHttpConnectionManager.setMaxTotalConnections(20);
		client = new HttpClient(multiThreadedHttpConnectionManager);
		HttpConnectionManagerParams managerParams = client
				.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(connectionTimeout);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(soTimeout);
		// client.getHttpConnectionManager().setParams(managerParams);
	}

	public HttpMethodBase getHttpMethod(String url) {

		GetMethod method = new GetMethod(url);
		method.setRequestHeader("Accept", accept);
		method.setRequestHeader("User-Agent", user_agent);
		method.setRequestHeader("Accept-Language", accept_language);
		method.setRequestHeader("Accept-Encoding", accept_encoding);
		method.setRequestHeader("Keep-Alive", keep_alive_time);
		method.setRequestHeader("Connection", connection);
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, soTimeout);// 设置超时时间
		// method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new
		// DefaultHttpMethodRetryHandler(3, false));
		return method;
	}

	public HttpMethodBase postHttpMethod(String url) {
		PostMethod method = new PostMethod(url);
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, soTimeout);
		method.getParams().setContentCharset("utf-8");
		return method;
	}

	public HttpResult executePostRequest(PostMethod method, String url,
			JSONObject json) throws Exception {
		List<NameValuePair> pairsList = new ArrayList<NameValuePair>();
		for (Iterator iterator = json.keys(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = json.getString(key);
			if (StringUtils.isNotEmpty(key)) {
				NameValuePair pair = new NameValuePair(key, value);
				pairsList.add(pair);
			}
		}
		NameValuePair[] array = pairsList.toArray(new NameValuePair[pairsList
				.size()]);
		method.setRequestBody(array);
		try {
			int statusCode = client.executeMethod(method);

			HttpResult result = new HttpResult();
			InputStream stream = null;
			if ((method.getResponseHeader("Content-Encoding") != null)
					&& ("gzip".equalsIgnoreCase(method.getResponseHeader(
							"Content-Encoding").getValue()))) {
				stream = new GZIPInputStream(method.getResponseBodyAsStream());
			} else {
				stream = method.getResponseBodyAsStream();
			}

			result.setContent(getStringFromStreamAutoCharset(stream));
			result.setHttpCode(statusCode);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			method.releaseConnection();
		}
	}

	public HttpResult executePostRequest(String url, JSONObject json)
			throws Exception {
		return executePostRequest((PostMethod) this.postHttpMethod(url), url,
				json);
	}

	public HttpResult executePostRequest(String url, String paramName,
			String msg) throws Exception {
		PostMethod method = (PostMethod) postHttpMethod(url);
		method.addParameter(new NameValuePair(paramName, msg));
		try {
			int statusCode = client.executeMethod(method);
			HttpResult result = new HttpResult();
			InputStream stream = null;
			if ((method.getResponseHeader("Content-Encoding") != null)
					&& ("gzip".equalsIgnoreCase(method.getResponseHeader(
							"Content-Encoding").getValue()))) {
				stream = new GZIPInputStream(method.getResponseBodyAsStream());
			} else {
				stream = method.getResponseBodyAsStream();
			}
			result.setContent(getStringFromStreamAutoCharset(stream));
			result.setHttpCode(statusCode);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			method.releaseConnection();
		}
	}
	public HttpResult executePostRequest(String url, Map<String, String> params) throws Exception {
		PostMethod method = (PostMethod) postHttpMethod(url);
		for(Map.Entry<String, String> entry : params.entrySet()){
			method.addParameter(new NameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			int statusCode = client.executeMethod(method);
			HttpResult result = new HttpResult();
			InputStream stream = null;
			if ((method.getResponseHeader("Content-Encoding") != null)
					&& ("gzip".equalsIgnoreCase(method.getResponseHeader(
							"Content-Encoding").getValue()))) {
				stream = new GZIPInputStream(method.getResponseBodyAsStream());
			} else {
				stream = method.getResponseBodyAsStream();
			}
			result.setContent(getStringFromStreamAutoCharset(stream));
			result.setHttpCode(statusCode);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			method.releaseConnection();
		}
	}
	public HttpResult get(String url) throws Exception {
		GetMethod method = (GetMethod) getHttpMethod(url);

		HttpResult result = new HttpResult();
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK)
				System.err.println("Method failed: " + method.getStatusLine());

			InputStream stream = null;
			if ((method.getResponseHeader("Content-Encoding") != null)
					&& ("gzip".equalsIgnoreCase(method.getResponseHeader(
							"Content-Encoding").getValue()))) {
				stream = new GZIPInputStream(method.getResponseBodyAsStream());
			} else {
				stream = method.getResponseBodyAsStream();
			}

			result.setContent(getStringFromStreamAutoCharset(stream));
			result.setHttpCode(statusCode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return result;
	}

	public String getStringFromStreamAutoCharset(InputStream is)
			throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String result = "";
		try {
			byte[] buf = new byte[10240];
			int len;
			while ((len = is.read(buf)) > 0)
				os.write(buf, 0, len);
			result = EncodeDetectUtil.getInstance().getStringByByte(
					os.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			os.close();
		}

		return result;
	}
	public static void main(String[] args) throws Exception {
		HttpClientUtils httpClient = new HttpClientUtils();
		String json = "{\"task_id\":1,\"real_cmd\":\"cd /home/machen/test && sh test.sh\",\"shell_cmd\":\"cd /home/machen/test && sh test.sh\",\"start_time\":\"2011.11.29 17:18:36\",\"exec_type\":0,\"run_status\":0}";
		JSONObject js  = JSONObject.fromObject(json);
		httpClient.executePostRequest("http://192.168.50.177:8080/record_undo/report.action",js);
	}
}
