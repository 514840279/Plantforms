create or replace procedure sp_analyst_zhcx is

  cursor c_tab is
    select substr(table_name, 1, instr(table_name, '.') - 1) as owner,
           substr(table_name, instr(table_name, '.') + 1) as table_name,
           table_desc
      from sys_zhcx_tabs
     where table_name not like 'ZHCX.SYS_%'
       and table_name not like 'ZHCX.VW_%'
       and table_name not in ('ZHXXYYPT.SYS_TEXT_FILE'); --ͳ������ѹ��صı�;
  r_tab c_tab%ROWTYPE;
begin

  delete from sys_zhcx_analyst
   where ͳ������ = to_char(sysdate - 1, 'YYYY-MM-DD');
  commit;

  OPEN c_tab;
  LOOP
    FETCH c_tab
      INTO r_tab;
    EXIT WHEN c_tab%notfound;
  
    --�ռ�����Ϣ
    dbms_stats.gather_table_stats(r_tab.owner,
                                  r_tab.table_name,
                                  method_opt       => 'for all indexed columns',
                                  degree           => 4);
  
    --�����ͳ������
    insert into sys_zhcx_analyst
      (ͳ������, ����, ��ע��, ������, ռ�ÿռ�, ��������)
      select to_char(sysdate - 1, 'YYYY-MM-DD') as ͳ������,
             t.����,
             r_tab.table_desc,
             t.������,
             t.ռ�ÿռ�,
             nvl(t.������ - c.������, 0) as ��������
        from (select table_name as ����,
                     NUM_ROWS as ������,
                     ROUND(BLOCKS * 8 / 1024, 0) || 'M' as ռ�ÿռ�
                from all_tables
               where owner = r_tab.owner
                 and table_name = r_tab.table_name) t
        left join (select ����, ������
                     from sys_zhcx_analyst
                    where ͳ������ = to_char(sysdate - 2, 'YYYY-MM-DD')) c
          on t.���� = c.����;
    commit;
  END LOOP;

  CLOSE c_tab;

  --�����ۺϲ�ѯ���ݱ�
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

  --���뵱�ջ���ͳ������
  insert into sys_zhcx_analyst
    (ͳ������, ����, ��ע��, ������, ռ�ÿռ�, ��������)
    select ͳ������,
           '����' as ����,
           '���ջ���' as ��ע��,
           sum(������) as ������,
           sum(cast(replace(ռ�ÿռ�, 'M', '') as integer)) || 'M' as ռ�ÿռ�,
           nvl(sum(��������), 0) as ��������
      from sys_zhcx_analyst
     where ͳ������ = to_char(sysdate - 1, 'YYYY-MM-DD')
     group by ͳ������;
  commit;

end;
/
