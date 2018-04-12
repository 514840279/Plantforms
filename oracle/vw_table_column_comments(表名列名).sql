create or replace view vw_table_column_comments as
select
  t.TABLE_NAME,
  n.comments as table_comments,
  t.column_name,
  m.comments as column_comments,
  t.DATA_TYPE as type
  from user_tab_columns t
 inner join all_col_comments m
  on t.COLUMN_NAME = m.column_name
                              and t.TABLE_NAME = m.table_name
 inner join all_tab_comments n on n.table_name = t.table_name
 where t.TABLE_NAME  like 'SHX%'
 and t.TABLE_NAME not in ('shxx_yhxg_yhkhxx ','SHXX_QYXG_企业基本信息');
