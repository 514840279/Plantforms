SELECT * FROM  上传文件表 t 
WHERE t.`录入时间`> ADDDATE(CURDATE(),INTERVAL -1 DAY) ;
SELECT * FROM 增量表 t
ORDER BY  DATE DESC
WHERE t.`入库的单号`IS NOT NULL
AND t.`入库的数据量` >0
AND t.`utime` = ADDDATE(CURDATE(),INTERVAL -1 DAY) ;