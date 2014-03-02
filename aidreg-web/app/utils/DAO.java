package utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Contribution;
import models.DataContext;
import play.libs.Json;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.util.*;

/**
 * Created by lars on 3/1/14.
 */
public class DAO {

    private static JedisPool jpool = null;

    private final static String KEY_LAST = "last";
    private final static String KEY_CONTRIBUTORS = "contributors";
    private final static String KEY_TOTAL_AMOUNT = "total:amount";
    private final static String KEY_TOTAL_CNT = "total:cnt";

    public static JedisPool getJpool() {
        if (jpool == null) {
            createPool();
        }
        return jpool;
    }

    private static void createPool() {
        jpool = new JedisPool(new JedisPoolConfig(),"localhost");
    }

    public static DataContext getContext() {
        Jedis jedis = getJpool().getResource();

        ObjectMapper jsonMapper = new ObjectMapper();

        Exception exception = null;

        try {
            List<String> lastListS = jedis.lrange(KEY_LAST, 0, 19);
            Set<String> toplistS = jedis.zrevrange(KEY_CONTRIBUTORS, 0, 19);
            String totalAmountS = jedis.get(KEY_TOTAL_AMOUNT);
            String totalCntS = jedis.get(KEY_TOTAL_CNT);

            List<Contribution> lastList = new ArrayList<Contribution>();
            for (String json : lastListS) {
                lastList.add(jsonMapper.readValue(json, Contribution.class));
            }

            Set<Contribution> toplist = new LinkedHashSet<Contribution>();
            for (String json : toplistS) {
                toplist.add( jsonMapper.readValue(json, Contribution.class));
            }

            long totalAmount = Long.parseLong(totalAmountS);
            long totalCnt = Long.parseLong(totalCntS);

            return new DataContext(lastList, toplist, totalAmount, totalCnt);


        } catch(JedisConnectionException ex) {
            if (jedis != null) {
                getJpool().returnBrokenResource(jedis);
                jedis = null;
            }
            exception = ex;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            exception = e;
        } catch (JsonParseException e) {
            e.printStackTrace();
            exception = e;
        } catch (IOException e) {
            e.printStackTrace();
            exception = e;
        } finally {
            if (jedis != null) {
                getJpool().returnResource(jedis);
            }
        }

        throw new RuntimeException("Error fetching data from redis server", exception);
    }

    public static JsonNode getContextAsJson() {
        DataContext dataContext = getContext();

        return Json.toJson(dataContext);
    }

}
