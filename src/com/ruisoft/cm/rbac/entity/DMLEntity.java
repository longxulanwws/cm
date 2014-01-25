package com.ruisoft.cm.rbac.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class DMLEntity {
	public static final String QUERY_NORMAL = "NORMAL";
	public static final String COND = "COND";
	public static final String ADD = "ADD";
	public static final String UPDATE = "UPDATE";
	public static final String DELETE = "DELETE";
	
	private String sql = null;
	
	private String preparedSQL = null;
	
	/** 
	 * ��ѯ����
	 * 1��Ĭ��ֵ���̶�������ѯ����������ѯ��
	 * 2����������ѯ���������̶�
	 */
	protected String dmlType = null;
	
	private static final Pattern PATTERN = Pattern.compile("\\{[^\\}]+\\}");
	
	private Map<String, String> conditions = null;
	
	private Map<String, ConditionConfig> condConf =  null;

	public void setConditions(Map<String, String> conditions) {
		this.conditions = conditions;
	}

	public Map<String, String> getConditions() {
		return conditions;
	}

	public Map<String, ConditionConfig> getCondConf() {
		return condConf;
	}

	public void setCondConf(Map<String, ConditionConfig> condConf) {
		this.condConf = condConf;
	}
	
	public void setSql(String sql) {
		this.sql = sql.trim();
	}

	public String getSql() {
		return sql;
	}
	
	public String getSql(String qCond) throws JSONException {
		if (!COND.equals(dmlType))
			return getPreparedSQL();
		
		return getSql(new JSONObject(qCond));
	}
	
	public String getSql(JSONObject json) throws JSONException {
		String sql = getPreparedSQL();
		
		if (!COND.equals(dmlType) || condConf.isEmpty())
			return sql;
		
		@SuppressWarnings("rawtypes")
		Iterator keys = json.keys();
		StringBuffer where = new StringBuffer();
		ConditionConfig cCfg = null;
		String matchType = null, likeF = null, val = null;
		String k = null;
		while (keys.hasNext()) {
			k = keys.next().toString();
			val = json.getString(k);
			if (val == null || "".equals(val.trim()))
				continue;
			
			cCfg = condConf.get(k);
			if (val == null || cCfg == null)
				continue;
			
			where.append(cCfg.getLinkType()).append(" ").append(cCfg.getColName());
			
			matchType = cCfg.getMatchType();
			if (matchType.startsWith("LIKE")) {
				where.append(" LIKE '");
				if (matchType.contains("(")) {
					likeF = matchType.replaceAll("LIKE\\s*\\(|\\)", "").trim();
				} else {
					likeF = "LR";
				}
				if ("LR".equals(likeF)) {
					where.append("%").append(val).append("%'");
				} else if ("L".equals(likeF)) {
					where.append("%").append(val).append("'");
				} else if ("R".equals(likeF)) {
					where.append(val).append("%'");
				}
			} else {
				where.append(matchType);
				if ("str".equals(cCfg.getColType())) {
					where.append("'").append(val).append("'");
				} else {
					where.append(val);
				}
			}
		}
		
		if (where.length() > 0)
			return sql.concat(" WHERE ").concat(
					where.toString().replaceFirst("^[^ ]+\\s+", ""));
		return sql;
	}
	
	public String getPreparedSQL() {
		if (preparedSQL != null)
			return preparedSQL;
		
		if (COND.equals(dmlType)) {
			preparedSQL = sql.trim();
			if (conditions != null && !conditions.isEmpty()) {
				condConf = new HashMap<String, ConditionConfig>(conditions.size());
				ConditionConfig condCfg = null;
				String[] cfg = null;
				for (String k : conditions.keySet()) {
					condCfg = new ConditionConfig();
					condCfg.setColName(k.toUpperCase());
					
					cfg = conditions.get(k).split("[,;:|]");
					try {
						if (!"".equals(cfg[0])) // ��������
							condCfg.setColType(cfg[0].trim());
						if (!"".equals(cfg[1])) // ƥ�䷽ʽ (=/!=/<>/LIKE/IN/NOT LIKE/NOT IN)
							condCfg.setMatchType(cfg[1].trim());
						if (!"".equals(cfg[2])) // ������������(AND/OR)
							condCfg.setLinkType(cfg[2].trim());
					} catch (ArrayIndexOutOfBoundsException e) {
						
					} finally {
						condConf.put(k, condCfg);
					}
				}
			}
		} else {
			Matcher matcher = PATTERN.matcher(sql);
			conditions = new LinkedHashMap<String, String>();
			String m = null, g = null, t = null;
			while (matcher.find()) {
				m = matcher.group().replaceAll("[{}]", "");
				if (m.contains(":")) {
					g = m.split(":")[0].trim();
					t = m.split(":")[1].trim();
				} else {
					g = m;
					t = "str";
				}
				conditions.put(g, t);
			}
			
			preparedSQL = matcher.replaceAll("?");
		}
		
		return preparedSQL.trim();
	}

	public String getDmlType() {
		return dmlType;
	}

	public void setDmlType(String dmlType) {
		this.dmlType = dmlType.toUpperCase();
	}
	
	protected class ConditionConfig {
		private String colName = null;
		
		private String colType = "str";
		
		private String matchType = "=";
		
		private String linkType = "AND";

		public String getColName() {
			return colName;
		}

		public void setColName(String colName) {
			this.colName = colName.toUpperCase();
		}

		public String getColType() {
			return colType;
		}

		public void setColType(String colType) {
			this.colType = colType;
		}

		public String getMatchType() {
			return matchType;
		}

		public void setMatchType(String matchType) {
			this.matchType = matchType.toUpperCase();
		}

		public String getLinkType() {
			return linkType;
		}

		public void setLinkType(String linkType) {
			this.linkType = linkType.toUpperCase();
		}
	}
}