-- 查询为入库的文件信息
select t.*,t.rowid from sys_lrd_filelist t
where t.stateflag not in('3','10','13')
--and t.filename not like'%企业_股东信息%'
--and t.lrd_task_id='LOG20151211038920'
order by t.date1 desc
;

-- 查询问入库文件形成的 insert 的语句
select t.*,t.rowid from sys_lrd_mapping_log t
where t.exe_start_time > sysdate -1
and t.exe_count is null
order by t.exe_start_time desc;

-- 查看最近下载的那些rar文件
select replace(substr(t.oldfile,instr(t.oldfile,'\',1,5)+1,3),'\') as name,
       substr(t.oldfile,instr(t.oldfile,'\',1,6)+1,8) as datet,
       t.*,
       t.rowid
  from sys_lrd_oldrar t
 where 1 = 1
   and t.flag = '2'
   and t.oldfile not like '%html%'
   and t.date1> sysdate-1
 order by date1 desc;

-- 查询关系映射
select t.*, t.rowid from sys_lrd_cols_map t
where 1=1
--and t.tab_name = 'SHXX_SWXG_FPXG_发票信息'
and t.tab_col is null
and t.tab_name is null;

-- 查询入库信息
select * from aaa_入库日志 t
where t.录入时间 > sysdate-1
order by t.录入时间 desc;

-- 查看错误的文件信息
select t.*, t.rowid from sys_lrd_errordatefile t
where t.flag not in ('3','10')
and t.inset_date > sysdate -2;

-- 数据增量
select * from sys_zhcx_analyst t
where 1=1
and t.统计日期 = to_char(sysdate-1,'yyyy-mm-dd')
--and t.表名 = '汇总'
and t.数据增量 <> '0'
order by t.统计日期 desc;

-- 查看错误入库信息
select * from sys_log t
where t.sttime >sysdate -1
and t.msg is not null
order by t.sttime;



