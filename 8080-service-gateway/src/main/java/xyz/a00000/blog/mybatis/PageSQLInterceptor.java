package xyz.a00000.blog.mybatis;



import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.utils.ReflectUtils;

import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class PageSQLInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof RoutingStatementHandler statementHandler) {
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtils.getValueByFieldName(statementHandler, "delegate");
            BoundSql boundSql = delegate.getBoundSql();
            Object param = boundSql.getParameterObject();
            PageForm<?> page = null;
            if (param instanceof PageForm<?>) {
                page = (PageForm<?>) param;
            } else if (param instanceof Map<?, ?> m) {
                Collection<?> values = m.values();
                for (Object obj : values) {
                    if (obj instanceof PageForm<?>) {
                        page = (PageForm<?>) obj;
                        break;
                    }
                }
            }
            if (page != null) {
                String sql = boundSql.getSql();
                Integer begin = (page.getPage() - 1) * page.getCount();
                Integer count = page.getCount();
                String targetSql = String.format("%s limit %d, %d", sql, begin, count);
                ReflectUtils.setValueByFieldName(boundSql, "sql", targetSql);
            }
        }
        return invocation.proceed();
    }

}
