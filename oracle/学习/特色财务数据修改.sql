select t.*, t.rowid from shxg_qyxg_特色财务分析 t;

update shxg_qyxg_特色财务分析 t
set t.收入1的值_元 = round(to_number(replace(t.收入1的值_元,',','')),2)
, t.收入2的值_元 = round(to_number(replace(t.收入2的值_元,',','')),2)
, t.收入3的值_元 = round(to_number(replace(t.收入3的值_元,',','')),2)
, t.收入4的值_元 = round(to_number(replace(t.收入4的值_元,',','')),2)
, t.收入5的值_元 = round(to_number(replace(t.收入5的值_元,',','')),2)
, t.收入6的值_元 = round(to_number(replace(t.收入6的值_元,',','')),2)
, t.单位活跃用户价值_元_人 = round(to_number(replace(t.单位活跃用户价值_元_人,',','')),2)
, t.单位营销效果_用户数_元 = round(to_number(replace(t.单位营销效果_用户数_元,',','')),2)
, t.综合指标 = round(to_number(replace(t.综合指标,',','')),2)
, t.研发费用_元 = round(to_number(replace(t.研发费用_元,',','')),2)
, t.对客户的强弱指标_元 = round(to_number(replace(t.对客户的强弱指标_元,',','')),2)
, t.对供应商的强弱指标_元 = round(to_number(replace(t.对供应商的强弱指标_元,',','')),2)
, t.营业成本_元 = round(to_number(replace(t.营业成本_元,',','')),2)
, t.营业税金及附加_元 = round(to_number(replace(t.营业税金及附加_元,',','')),2)
, t.销售费用_元 = round(to_number(replace(t.销售费用_元,',','')),2)
, t.应收票据_元 = round(to_number(replace(t.应收票据_元,',','')),2)
, t.应收账款_元 = round(to_number(replace(t.应收账款_元,',','')),2)
, t.预收账款_元 = round(to_number(replace(t.预收账款_元,',','')),2)
, t.应付票据_元 = round(to_number(replace(t.应付票据_元,',','')),2)
, t.应付账款_元 = round(to_number(replace(t.应付账款_元,',','')),2)
, t.预付账款_元 = round(to_number(replace(t.预付账款_元,',','')),2);
