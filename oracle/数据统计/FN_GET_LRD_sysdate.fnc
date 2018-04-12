create or replace function FN_GET_LRD_sysdate(p_tablename in varchar2)
  RETURN STRING is
  v_sysdate varchar2(200);
  begin
    if (p_tablename = 'shxg_qyxg_公司基本信息') then
       v_sysdate := 'to_char(''sysdate'',''YYYY-MM-DD'')';
    else
       v_sysdate := 'sysdate';
    end if;

  return v_sysdate;
  end;
/
