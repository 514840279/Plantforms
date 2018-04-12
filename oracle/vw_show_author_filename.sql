create or replace view vw_show_author_filename as
select distinct 简称,substr(name,1,length(name)-29) as name  ,n.file_desc ,n.tab_name from (
select t.filename as 文件名称
,substr(t.subname,1,length(t.subname)-12) as 简称
,length(t.filename) as 长度
,replace(t.name,t.filename,'') as name
 from
(
select m.filename,substr(m.filename,instr(m.filename,'_'，1,1)+1) as subname,replace(replace(m.filepath,'D:\zhcx_datafile_path\datafiles\download\数据采集\',''),'D:\zhcx_datafile_path\datafiles\webdownload\数据采集\','') as name
 from sys_lrd_filelist m where m.stateflag <> '10'
and  m.filename<>'网易财经上市公司同行业资金流入.txt'
  ) t

 ) k
 left join sys_lrd_cols_map  n on k.简称 like '%'|| replace(n.file_desc,'%','')||'%'
 and n.file_col<>'id' ;
