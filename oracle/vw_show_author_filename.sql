create or replace view vw_show_author_filename as
select distinct ���,substr(name,1,length(name)-29) as name  ,n.file_desc ,n.tab_name from (
select t.filename as �ļ�����
,substr(t.subname,1,length(t.subname)-12) as ���
,length(t.filename) as ����
,replace(t.name,t.filename,'') as name
 from
(
select m.filename,substr(m.filename,instr(m.filename,'_'��1,1)+1) as subname,replace(replace(m.filepath,'D:\zhcx_datafile_path\datafiles\download\���ݲɼ�\',''),'D:\zhcx_datafile_path\datafiles\webdownload\���ݲɼ�\','') as name
 from sys_lrd_filelist m where m.stateflag <> '10'
and  m.filename<>'���ײƾ����й�˾ͬ��ҵ�ʽ�����.txt'
  ) t

 ) k
 left join sys_lrd_cols_map  n on k.��� like '%'|| replace(n.file_desc,'%','')||'%'
 and n.file_col<>'id' ;
