package com.zjx;

import com.zjx.config.ThreadPoolCommon;
import com.zjx.entity.JimiRelation;
import com.zjx.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * @Description 几米物联关联表标识从Mysql导入MongoDB
 * @Author Carson Cheng
 * @Date 2019/12/25 11:43
 * @Version V1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataToolsApplication.class)
public class JimiRelationImportTest {

    private static final Logger LOG = LoggerFactory.getLogger(JimiRelationImportTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String JIMI_HANDLE = "88.136.66/";

    private static final String JIMI_COMPANY = "深圳市几米物联有限公司";

    // 每页多少条
    private final int pageSize = 5000;

    // 最小id -- 查询开始索引，避免任务量过大，或者写死最小id
    private static final String MIN_ID_SQL = "SELECT MIN(id) FROM DataRelativeSheet_tmp";
    // 最大id -- id不连续采取查询最大id替代查询统计数量 ，或者写死最大id
    private static final String MAX_ID_SQL = "SELECT MAX(id) FROM DataRelativeSheet_tmp";
    // 统计数量
    private static final String COUNT_SQL = "SELECT COUNT(*) FROM DataRelativeSheet_tmp";

    // 大数据分页limit查询慢：采用查询id范围（适用于主键为int类型,连续自增）
    private static final String BASE_SQL = "SELECT IMEI1,IMEI2,IMEI3,IMEI4,IMEI6,IMEI7,IMEI8,IMEI9,IMEI10,IMEI11,IMEI12,IMEI13,ZhiDan,TestTime,_MASK_FROM_V2,NetMark,IMEI14 FROM DataRelativeSheet_tmp ";
    private static final String SQL = BASE_SQL + "WHERE Id >= ? AND Id < ?";


    @Test
    public void testImportRelation() {

        // 统计数量
        //Integer count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class);
        Integer count = jdbcTemplate.queryForObject(MAX_ID_SQL, Integer.class);
        LOG.info("总数量：" + count);

        // 查询最小id
        Integer minId = jdbcTemplate.queryForObject(MIN_ID_SQL, Integer.class);
        LOG.info("最小id：" + minId);

        // 总页数
        final int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        LOG.info("总页数：" + pageCount);

        // 读数据
        readData(pageCount, minId);
    }

    /**
     * 多线程分页读取数据
     *
     * @param pageCount
     * @param minId
     */
    private void readData(int pageCount, int minId) {

        List<CompletableFuture<Boolean>> futureList = new LinkedList<>();
        CountDownLatch latch = new CountDownLatch(pageCount);
        // 获取线程池
        Executor executor = ThreadPoolCommon.getExecutor();

        int curMinId = 0;
        for (int i = 0; i < pageCount; i++) {
            latch.countDown();

            final int n = i;
            if (i == 0) {
                curMinId = minId;
            } else {
                curMinId = minId + pageSize;
                minId = curMinId;
            }

            // 分页读取数据
            int finalCurMinId = curMinId;
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> dealData(SQL, finalCurMinId, n), executor).exceptionally(e -> {
                LOG.error("保存数据失败", e);
                return false;
            });
            futureList.add(future);

            // 同步
            //dealData(SQL, curMinId, n);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            LOG.error("保存数据失败", e);
        }
        futureList.forEach(e -> {
            try {
                if (!e.get()) {
                    LOG.error("处理数据异常");
                }
            } catch (InterruptedException ex) {
                LOG.error("保存数据失败", ex);
            } catch (ExecutionException ex) {
                LOG.error("保存数据失败", ex);
            }
        });

    }

    /**
     * 分页读取数据
     *
     * @param sql
     * @param curMinId 当前最小id
     * @param n        当前任务编号
     */
    private boolean dealData(String sql, int curMinId, int n) {

        final int maxId = curMinId + pageSize;

        List<JimiRelation> relationList = jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, curMinId);
                ps.setInt(2, maxId);
            }
        }, new RowMapper<JimiRelation>() {

            @Override
            public JimiRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
                JimiRelation relation = new JimiRelation();
                relation.setProductCode(JIMI_HANDLE + rs.getString("IMEI1"));
                relation.setComName(JIMI_COMPANY);
                relation.setSnNo(rs.getString("IMEI2"));
                relation.setSimNo(rs.getString("IMEI3"));
                relation.setIccidNo(rs.getString("IMEI4"));
                relation.setMacAddr(rs.getString("IMEI6"));
                relation.setMachineNo(rs.getString("IMEI7"));
                relation.setVipNo(rs.getString("IMEI8"));
                relation.setBatNo(rs.getString("IMEI9"));
                relation.setSecondLockId(rs.getString("IMEI10"));
                relation.setMachineCode(rs.getString("IMEI11"));
                relation.setImsiNo(rs.getString("IMEI12"));
                relation.setRfidNo(rs.getString("IMEI13"));
                relation.setOrderNo(rs.getString("ZhiDan"));
                relation.setUploadDate(DateUtils.formatDate(rs.getTimestamp("TestTime"), DateUtils.YYYY_MM_DD_HH_MM_SS));
                relation.setSimActiveDate(rs.getString("_MASK_FROM_V2"));
                relation.setNetNo(rs.getString("NetMark"));
                relation.setSecondIMEI(rs.getString("IMEI14"));
                return relation;
            }

        });

        // 保存数据
        mongoTemplate.insertAll(relationList);
        LOG.info("当前第{}个任务，线程:{}，查询id区间：{}-{}，成功保存数据 {}条", n, Thread.currentThread().getName(), curMinId, maxId, relationList.size());
        relationList = null;
        return true;
    }
}
