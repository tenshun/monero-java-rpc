package daemon;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import daemon.model.MoneroBan;
import daemon.model.MoneroBlock;
import daemon.model.MoneroBlockHeader;
import daemon.model.MoneroBlockTemplate;
import daemon.model.MoneroChain;
import daemon.model.MoneroCoinbaseTxSum;
import daemon.model.MoneroDaemonConnection;
import daemon.model.MoneroDaemonInfo;
import daemon.model.MoneroDaemonResponseInfo;
import daemon.model.MoneroHardForkInfo;
import daemon.model.MoneroOutputDistributionEntry;
import daemon.model.MoneroOutputHistogramEntry;
import daemon.model.MoneroSyncInfo;
import daemon.model.MoneroTxPoolBacklog;

/**
 * Monero daemon interface.
 */
public interface MoneroDaemon {

  public void getBlockCount();
  
  public void getBlockHash(int height);
  
  public MoneroBlockTemplate getBlockTemplate(String walletAddress, int reserveSize);
  
  public String submitBlock(String blockBlob);
  
  public MoneroBlockHeader getLastBlockHeader();  
  
  public MoneroBlockHeader getBlockHeader(String hash);
  
  public MoneroBlockHeader getBlockHeader(int height);
  
  public List<MoneroBlockHeader> getBlockHeaders(int startHeight, int endHeight);
  
  public MoneroBlock getBlock(String hash);
  
  public MoneroBlock getBlock(int height);
  
  public List<MoneroDaemonConnection> getConnections();
  
  public MoneroDaemonInfo getInfo();
  
  /**
   * Returns the daemon version.
   * 
   * TODO: incorporate into getInfo()?
   * 
   * @return String is the daemon version
   */
  public String getVersion();
  
  public MoneroSyncInfo getSyncInfo();
  
  public MoneroHardForkInfo getHardForkInfo();
  
  public String setBan(MoneroBan ban );
  
  public String setBans(Collection<MoneroBan> bans);
  
  /**
   * Flush all transactions from the transaction pool.
   * 
   * @return String is the resulting RPC error code. "OK" means everything looks good
   */
  public String flushTxPool();
  
  /**
   * Flush specific transactions from the transaction pool or all if none specified.
   * 
   * @param txIds are transactions to flush from the pool, or all if none provided
   * @return String is the resulting RPC error code. "OK" means everything looks good
   */
  public String flushTxPool(Collection<String> txIds);
  
  public List<MoneroOutputHistogramEntry> getOutputHistogram(List<BigInteger> amounts, Integer minCount, Integer maxCount, Boolean isUnlocked, Integer recentCutoff);
  
  public List<MoneroOutputDistributionEntry> getOutputDistribution(List<BigInteger> amounts, Boolean cumulative, Integer startHeight, Integer endHeight);
  
  public MoneroCoinbaseTxSum getCoinbaseTxSum(Integer height, Integer count);
  
  public BigInteger getFeeEstimate(Integer graceBlocks);
  
  public List<MoneroChain> getAlternativeChains();
  
  public MoneroDaemonResponseInfo relayTx(String txId);
  
  public MoneroDaemonResponseInfo relayTxs(Collection<String> txIds);
  
  public MoneroTxPoolBacklog getTxPoolBacklog();
}