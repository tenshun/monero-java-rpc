package monero.wallet.model;

import java.util.Collection;

/**
 * Specifies filter options when querying transactions.
 * 
 * TODO: filter on NOT_RELAYED
 */
public class MoneroTxFilter {
  
  private boolean incoming;
  private boolean mempool;
  private boolean outgoing;
  private boolean pending;
  private boolean failed;
  private Integer minHeight;
  private Integer maxHeight;
  private Integer accountIndex;
  private Collection<Integer> subaddressIndices;
  private Collection<String> txIds;
  private Collection<String> paymentIds;
  private Boolean hasPayments;  // null means tx will not be filtered regardless of having payments
  
  public MoneroTxFilter() {
    incoming = true;
    mempool = true;
    outgoing = true;
    pending = true;
    failed = true;
    minHeight = null;
    maxHeight = null;
    accountIndex = null;
    subaddressIndices = null;
    txIds = null;
    paymentIds = null;
    hasPayments = null;
  }

  public MoneroTxFilter(boolean incoming, boolean mempool, boolean outgoing, boolean pending, boolean failed, Integer minHeight, Integer maxHeight, Integer accountIndex, Collection<Integer> subaddressIndices, Collection<String> txIds, Collection<String> paymentIds, Boolean hasPayments) {
    super();
    this.incoming = incoming;
    this.mempool = mempool;
    this.outgoing = outgoing;
    this.pending = pending;
    this.failed = failed;
    this.minHeight = minHeight;
    this.maxHeight = maxHeight;
    this.accountIndex = accountIndex;
    this.subaddressIndices = subaddressIndices;
    this.txIds = txIds;
    this.paymentIds = paymentIds;
    this.hasPayments = hasPayments;
  }

  public boolean isIncoming() {
    return incoming;
  }

  public void setIncoming(boolean incoming) {
    this.incoming = incoming;
  }

  public boolean isMempool() {
    return mempool;
  }

  public void setMempool(boolean mempool) {
    this.mempool = mempool;
  }

  public boolean isOutgoing() {
    return outgoing;
  }

  public void setOutgoing(boolean outgoing) {
    this.outgoing = outgoing;
  }

  public boolean isPending() {
    return pending;
  }

  public void setPending(boolean pending) {
    this.pending = pending;
  }

  public boolean isFailed() {
    return failed;
  }

  public void setFailed(boolean failed) {
    this.failed = failed;
  }

  public Integer getMinHeight() {
    return minHeight;
  }

  public void setMinHeight(Integer minHeight) {
    this.minHeight = minHeight;
  }

  public Integer getMaxHeight() {
    return maxHeight;
  }

  public void setMaxHeight(Integer maxHeight) {
    this.maxHeight = maxHeight;
  }

  public Integer getAccountIndex() {
    return accountIndex;
  }

  public void setAccountIndex(Integer accountIndex) {
    this.accountIndex = accountIndex;
  }

  public Collection<Integer> getSubaddressIndices() {
    return subaddressIndices;
  }

  public void setSubaddressIndices(Collection<Integer> subaddressIndices) {
    this.subaddressIndices = subaddressIndices;
  }

  public Collection<String> getTxIds() {
    return txIds;
  }

  public void setTxIds(Collection<String> txIds) {
    this.txIds = txIds;
  }

  public Collection<String> getPaymentIds() {
    return paymentIds;
  }

  public void setPaymentIds(Collection<String> paymentIds) {
    this.paymentIds = paymentIds;
  }

  public Boolean getHasPayments() {
    return hasPayments;
  }

  public void setHasPayments(Boolean hasPayments) {
    this.hasPayments = hasPayments;
  }
}
