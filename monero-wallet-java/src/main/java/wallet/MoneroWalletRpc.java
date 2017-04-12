package wallet;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jooq.types.UInteger;

import types.HttpException;
import utils.JsonUtils;
import utils.StreamUtils;

/**
 * Implements a MoneroWallet backed by a monero wallet RPC endpoint.
 * 
 * @author woodser
 */
public class MoneroWalletRpc implements MoneroWallet {
  
  private String host;
  private int port;
  private URI uri;
  private HttpClient client;
  
  public MoneroWalletRpc(String endpoint) {
    this(parseUri(endpoint));
  }
  
  public MoneroWalletRpc(URI uri) {
    this.uri = uri;
    this.host = uri.getHost();
    this.port = uri.getPort();
    this.client = HttpClients.createDefault();
  }
  
  public MoneroWalletRpc(String host, int port) throws URISyntaxException {
    this.host = host;
    this.port = port;
    this.uri = new URI("http", null, host, port, "/json_rpc", null, null);
    this.client = HttpClients.createDefault();
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }
  
  public URI getUri() {
    return uri;
  }

  @SuppressWarnings("unchecked")
  public UInteger getBalance() {

    // get RPC response content
    String respContent = null;
    try {
      
      // build request body
      Map<String, String> body = new HashMap<String, String>();
      body.put("jsonrpc", "2.0");
      body.put("id", "0");
      body.put("method", "getbalance");
      
      // execute http request
      HttpPost post = new HttpPost(uri);
      HttpEntity entity = new StringEntity(JsonUtils.serialize(body));
      post.setEntity(entity);
      HttpResponse resp = client.execute(post);
      validateHttpResponse(resp);
      
      // get response
      respContent = StreamUtils.streamToString(resp.getEntity().getContent());
      EntityUtils.consume(resp.getEntity());
    } catch (HttpException e1) {
      throw e1;
    } catch (Exception e2) {
      throw new MoneroException(e2);
    }
      
    // interpret response content
    Map<String, Object> respMap = JsonUtils.toMap(respContent);
    validateRpcResponse(respMap);
    Map<String, Object> resultMap = (Map<String, Object>) respMap.get("result");
    return UInteger.valueOf((Integer) resultMap.get("balance"));  // TODO: this will not handle unsigned ints
  }
  
  public UInteger getUnlockedBalance() {
    throw new RuntimeException("Not yet implemented.");
  }

  public MoneroAddress getAddress() {
    throw new RuntimeException("Not yet implemented.");
  }

  public MoneroIntegratedAddress getIntegratedAddress(String paymentId) {
    throw new RuntimeException("Not yet implemented.");
  }

  public MoneroTransaction sendTransaction(MoneroAddress address, UInteger amount, UInteger fee, int mixin, int unlockTime) {
    throw new RuntimeException("Not yet implemented.");
  }

  public MoneroTransaction sendTransaction(MoneroPayment payment) {
    throw new RuntimeException("Not yet implemented.");
  }

  public MoneroTransaction sendTransaction(Set<MoneroPayment> payments, UInteger fee, int mixin, int unlockTime) {
    throw new RuntimeException("Not yet implemented.");
  }

  public Set<MoneroTransaction> sweepDust() {
    throw new RuntimeException("Not yet implemented.");
  }

  public Set<MoneroTransaction> getTransactions(Set<MoneroTransactionType> includeTypes, Integer minHeight, Integer maxHeight) {
    throw new RuntimeException("Not yet implemented.");
  }

  public String getSpendKey() {
    throw new RuntimeException("Not yet implemented.");
  }

  public String getViewKey() {
    throw new RuntimeException("Not yet implemented.");
  }

  public void save() {
    throw new RuntimeException("Not yet implemented.");
  }

  public URI getUri(MoneroUri uri) {
    throw new RuntimeException("Not yet implemented.");
  }

  public MoneroUri parseUri(URI uri) {
    throw new RuntimeException("Not yet implemented.");
  }
	
  private static URI parseUri(String endpoint) {
    try {
      return new URI(endpoint);
    } catch (Exception e) {
      throw new MoneroException(e);
    }
  }
  
  private static void validateHttpResponse(HttpResponse resp) {
    int code = resp.getStatusLine().getStatusCode();
    if (code < 200 || code > 299) {
      String content = null;
      try {
        content = StreamUtils.streamToString(resp.getEntity().getContent());
      } catch (Exception e) {
        // could not get content
      }
      throw new HttpException(code, resp.getStatusLine().getReasonPhrase() + (content != null ? (": " + content) : ""));
    }
  }
  
  @SuppressWarnings("unchecked")
  private static void validateRpcResponse(Map<String, Object> respMap) {
    Map<String, Object> error = (Map<String, Object>) respMap.get("error");
    if (error == null) return;
    int code = (Integer) error.get("code");
    String message = (String) error.get("message");
    throw new MoneroException(code, message);
  }
}
