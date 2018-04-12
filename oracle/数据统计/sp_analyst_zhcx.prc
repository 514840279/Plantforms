create or replace procedure sp_analyst_zhcx is

  cursor c_tab is
    select substr(table_name, 1, instr(table_name, '.') - 1) as owner,
           substr(table_name, instr(table_name, '.') + 1) as table_name,
           table_desc
      from sys_zhcx_tabs
     where table_name not like 'ZHCX.SYS_%'
       and table_name not like 'ZHCX.VW_%'
       and table_name not in ('ZHXXYYPT.SYS_TEXT_FILE'); --统计针对已挂载的表;
  r_tab c_tab%ROWTYPE;
begin

  delete from sys_zhcx_analyst
   where 统计日期 = to_char(sysdate - 1, 'YYYY-MM-DD');
  commit;

  OPEN c_tab;
  LOOP
    FETCH c_tab
      INTO r_tab;
    EXIT WHEN c_tab%notfound;
  
    --收集表信息
    dbms_stats.gather_table_stats(r_tab.owner,
                                  r_tab.table_name,
                                  method_opt       => 'for all indexed columns',
                                  degree           => 4);
  
    --插入表统计数据
    insert into sys_zhcx_analyst
      (统计日期, 表名, 表注释, 数据量, 占用空间, 数据增量)
      select to_char(sysdate - 1, 'YYYY-MM-DD') as 统计日期,
             t.表名,
             r_tab.table_desc,
             t.数据量,
             t.占用空间,
             nvl(t.数据量 - c.数据量, 0) as 数据增量
        from (select table_name as 表名,
                     NUM_ROWS as 数据量,
                     ROUND(BLOCKS * 8 / 1024, 0) || 'M' as 占用空间
                from all_tables
               where owner = r_tab.owner
                 and table_name = r_tab.table_name) t
        left join (select 表名, 数据量
                     from sys_zhcx_analyst
                    where 统计日期 = to_char(sysdate - 2, 'YYYY-MM-DD')) c
          on t.表名 = c.表名;
    commit;
  END LOOP;

  CLOSE c_tab;

  --更新综合查询数据表
  merge into sys_zhcx_tabs t
  using (select OWNER || '.' || TABLE_NAME as table_name,
                NUM_ROWS as TABLE_ROWS,
                BLOCKS * 8 * 1024 as TABLE_SPACE
           from ALL_TABLES
            where OWNER = 'ZHCX') a
  on (a.table_name = t.table_name)
  when matched then
    update
       set TABLE_ROWS  = a.TABLE_ROWS,
           TABLE_SPACE = a.TABLE_SPACE,
           UPDATE_TIME = SYSDATE;
  commit;

  --插入当日汇总统计数据
  insert into sys_zhcx_analyst
    (统计日期, 表名, 表注释, 数据量, 占用空间, 数据增量)
    select 统计日期,
           '汇总' as 表名,
           '当日汇总' as 表注释,
           sum(数据量) as 数据量,
           sum(cast(replace(占用空间, 'M', '') as integer)) || 'M' as 占用空间,
           nvl(sum(数据增量), 0) as 数据增量
      from sys_zhcx_analyst
     where 统计日期 = to_char(sysdate - 1, 'YYYY-MM-DD')
     group by 统计日期;
  commit;

end;
/
