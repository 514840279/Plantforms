create or replace procedure P_TEST_SQL is
  TYPE ref_cursor_type IS REF CURSOR; --定义一个动态游标   
  tablename varchar2(200) default 'ess_client';
  v_sql     varchar2(1000);
  V_DESC    varchar2(150);
  V_TEMP_DESC varchar2(150);
  C_DESC      ref_cursor_type;
begin
  --使用连接符拼接成一条完整SQL   
  v_sql := 'select distinct cm.file_desc from  sys_lrd_file_col_map fm   inner join sys_lrd_cols_map cm  on fm.file_name like cm.file_desc  where fm.lrd_task_id = ''LRD20151125008015''';
  --打开游标   
  open C_DESC for v_sql;
  loop
    fetch C_DESC
      into V_TEMP_DESC;
   
  end loop;
  close C_DESC;
end P_TEST_SQL;
