# Plantforms

    文件自动加载平台，主要负责提供一个自动导入文本数据到oracle中，本平台使用java语言开发，连接mysql 和oracle数据库，临时数据库使用sqlite3，
    使用Timetask定时处理，间隔24小时自动执行一次，文本格式支持 txt 和csv 文件对文本格式要求及其严格，若格式错误直接导致文本录入失败。

#### 格式要求：
  
    1. 文本第一行代表数据项
  
    2. 数据项用反引号 ` 包围，数据项之间用逗号,分割开来
  
#### 执行步骤 java：

    1. 获取服务器上的文件列表
         文件列表是由采集用户每天导出一次数据上传到服务器上，然后将文件信息记录到外网的mysql中
    2. 将文件全部下载到本地dir.downloadDirectory
         dir = directory
         dir.downloadDirectory = directory.downloadDirectory
         配置信息在 resources中
    3. 将web文件全部下载到本地dir.downloadDirectory
         web与2中是两个ftp所以这里执行两次下载
    4. 将文件类型是（rar） 文件解压到本地dir.pressDirectory
        文件下载后需要进行解压，为了节省网络流量，采集把数据文件进行了压缩
    5. 同步数据到mysql
        这里同步表示在外网操作完成了对外网mysql 中对应的文件信息进行打标识，下次将不会进行下载了
    6. 遍历取出本地dir.pressDirectory的所有文件
        然后本地遍历文件路径下所有的文件信息，
    7. 将数据保存进数据库中（sqlite，oracle）
        记录文件信息，这里会录入到oracle中，保证每条记录不重复
    8. 执行数据文件修改
        因为不同时期，不同人导出的数据文件不是按要求进行操作的，这里进行了一些整理，将文件以行为单位读取然后处理成需要的格式，然后保存到结果文件中，将不能正确修改保存到另一个文件中，
    9. 执行sp
       调用oracle的过程，将处理过的文件直接导入到临时表中，对应一类表最后整理成一张表
       
#### 执行步骤 oracle：     
    
    oracle导入部分的程序位于 “自动加载平台/” 下，
    对于导入的文件需要设置 oracle directory 路径，否则oracle没有权限去读取本地磁盘中的文件

    SP_LOAD_FILE
    程序的入口，由java调用启动，参数文件的路径，名称
       
    1. SP_LOG
      记录日志   
      
    2.  FN_GET_LRD_TASK
       --获得任务
       V_LRD_ID := FN_GET_LRD_TASK(p_path, p_file);

    3. SP_LOAD_FILE_HEAD
        --载入文件头
        SP_LOAD_FILE_HEAD(V_LRD_ID,
                          p_comma => p_comma,
                          p_str => p_str,
                          p_ret => v_ret,
                          p_head =>p_head);
     4. SP_LOAD_MAP_TMP
        --载入 TMP_TAB 临时表名 状态 0:等待中
        SP_LOAD_MAP_TMP(V_LRD_ID, p_file);
  
     5. SP_LOAD_COLS_MAP
        --  载入关系映射字段 到 SYS_LRD_COLS_MAP 中
        SP_LOAD_COLS_MAP(V_LRD_ID,
                         P_RET => v_ret,
                         P_FILE => p_file);
                   
      6. SP_GET_TMP_TAB
        --获得临时表 并更新临时表状态 1:使用中
        SP_GET_TMP_TAB(V_LRD_ID, V_TMP_TAB, '1', v_ret);

      7. SP_LOAD_FILE_DATA
        --加载数据至临时表
        SP_LOAD_FILE_DATA(V_LRD_ID,
                          p_comma => p_comma,
                          p_str => p_str,
                          p_ret => v_ret);
      8. SP_LRD_MAPPING
        --载入执行日志
        SP_LRD_MAPPING(V_LRD_ID, v_ret);

      9.  SP_GET_TMP_TAB
        --更新临时表状态 2:使用完了
        SP_GET_TMP_TAB(V_LRD_ID, V_TMP_TAB, '2', v_ret);

      10. SP_LOG
        --写日志
        SP_LOG('SP_LOAD_FILE', V_LOGID);
