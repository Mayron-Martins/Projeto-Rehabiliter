/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scripts;

/**
 *
 * @author Mayro
 */
public class BackupMaster {
    
    public String part1(){
        return "/*\n" +
"\n" +
"SQL Server Maintenance Solution - SQL Server 2008, SQL Server 2008 R2, SQL Server 2012, SQL Server 2014, SQL Server 2016, SQL Server 2017, and SQL Server 2019\n" +
"\n" +
"Backup: https://ola.hallengren.com/sql-server-backup.html\n" +
"Integrity Check: https://ola.hallengren.com/sql-server-integrity-check.html\n" +
"Index and Statistics Maintenance: https://ola.hallengren.com/sql-server-index-and-statistics-maintenance.html\n" +
"\n" +
"License: https://ola.hallengren.com/license.html\n" +
"\n" +
"GitHub: https://github.com/olahallengren/sql-server-maintenance-solution\n" +
"\n" +
"Version: 2020-12-31 18:58:56\n" +
"\n" +
"You can contact me by e-mail at ola@hallengren.com.\n" +
"\n" +
"Ola Hallengren\n" +
"https://ola.hallengren.com\n" +
"\n" +
"*/\n" +
"\n" +
"USE [master] -- Specify the database in which the objects will be created.\n" +
"\n" +
"SET NOCOUNT ON\n" +
"\n" +
"DECLARE @CreateJobs nvarchar(max)          = 'N'         -- Specify whether jobs should be created.\n" +
"DECLARE @BackupDirectory nvarchar(max)     = '"+System.getProperty("user.home")+"/documents/Rehabiliter/Backups/Local'        -- Specify the backup root directory. If no directory is specified, the default backup directory is used.\n" +
"DECLARE @CleanupTime int                   = NULL        -- Time in hours, after which backup files are deleted. If no time is specified, then no backup files are deleted.\n" +
"DECLARE @OutputFileDirectory nvarchar(max) = NULL        -- Specify the output file directory. If no directory is specified, then the SQL Server error log directory is used.\n" +
"DECLARE @LogToTable nvarchar(max)          = 'Y'         -- Log commands to a table.\n" +
"\n" +
"DECLARE @ErrorMessage nvarchar(max)\n" +
"\n" +
"IF IS_SRVROLEMEMBER('sysadmin') = 0 AND NOT (DB_ID('rdsadmin') IS NOT NULL AND SUSER_SNAME(0x01) = 'rdsa')\n" +
"BEGIN\n" +
"  SET @ErrorMessage = 'You need to be a member of the SysAdmin server role to install the SQL Server Maintenance Solution.'\n" +
"  RAISERROR(@ErrorMessage,16,1) WITH NOWAIT\n" +
"END\n" +
"\n" +
"IF NOT (SELECT [compatibility_level] FROM sys.databases WHERE database_id = DB_ID()) >= 90\n" +
"BEGIN\n" +
"  SET @ErrorMessage = 'The database ' + QUOTENAME(DB_NAME(DB_ID())) + ' has to be in compatibility level 90 or higher.'\n" +
"  RAISERROR(@ErrorMessage,16,1) WITH NOWAIT\n" +
"END\n" +
"\n" +
"IF OBJECT_ID('tempdb..#Config') IS NOT NULL DROP TABLE #Config\n" +
"\n" +
"CREATE TABLE #Config ([Name] nvarchar(max),\n" +
"                      [Value] nvarchar(max))\n" +
"\n" +
"INSERT INTO #Config ([Name], [Value]) VALUES('CreateJobs', @CreateJobs)\n" +
"INSERT INTO #Config ([Name], [Value]) VALUES('BackupDirectory', @BackupDirectory)\n" +
"INSERT INTO #Config ([Name], [Value]) VALUES('CleanupTime', @CleanupTime)\n" +
"INSERT INTO #Config ([Name], [Value]) VALUES('OutputFileDirectory', @OutputFileDirectory)\n" +
"INSERT INTO #Config ([Name], [Value]) VALUES('LogToTable', @LogToTable)\n" +
"INSERT INTO #Config ([Name], [Value]) VALUES('DatabaseName', DB_NAME(DB_ID()))\n" +
"GO\n" +
"SET ANSI_NULLS ON\n" +
"GO\n" +
"SET QUOTED_IDENTIFIER ON\n" +
"GO\n" +
"IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CommandLog]') AND type in (N'U'))\n" +
"BEGIN\n" +
"CREATE TABLE [dbo].[CommandLog](\n" +
"  [ID] [int] IDENTITY(1,1) NOT NULL,\n" +
"  [DatabaseName] [sysname] NULL,\n" +
"  [SchemaName] [sysname] NULL,\n" +
"  [ObjectName] [sysname] NULL,\n" +
"  [ObjectType] [char](2) NULL,\n" +
"  [IndexName] [sysname] NULL,\n" +
"  [IndexType] [tinyint] NULL,\n" +
"  [StatisticsName] [sysname] NULL,\n" +
"  [PartitionNumber] [int] NULL,\n" +
"  [ExtendedInfo] [xml] NULL,\n" +
"  [Command] [nvarchar](max) NOT NULL,\n" +
"  [CommandType] [nvarchar](60) NOT NULL,\n" +
"  [StartTime] [datetime2](7) NOT NULL,\n" +
"  [EndTime] [datetime2](7) NULL,\n" +
"  [ErrorNumber] [int] NULL,\n" +
"  [ErrorMessage] [nvarchar](max) NULL,\n" +
" CONSTRAINT [PK_CommandLog] PRIMARY KEY CLUSTERED\n" +
"(\n" +
"  [ID] ASC\n" +
")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)\n" +
")\n" +
"END\n" +
"GO\n" +
"SET ANSI_NULLS ON\n" +
"GO\n" +
"SET QUOTED_IDENTIFIER ON\n" +
"GO\n" +
"IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CommandExecute]') AND type in (N'P', N'PC'))\n" +
"BEGIN\n" +
"EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[CommandExecute] AS'\n" +
"END\n" +
"GO\n" +
"ALTER PROCEDURE [dbo].[CommandExecute]\n" +
"\n" +
"@DatabaseContext nvarchar(max),\n" +
"@Command nvarchar(max),\n" +
"@CommandType nvarchar(max),\n" +
"@Mode int,\n" +
"@Comment nvarchar(max) = NULL,\n" +
"@DatabaseName nvarchar(max) = NULL,\n" +
"@SchemaName nvarchar(max) = NULL,\n" +
"@ObjectName nvarchar(max) = NULL,\n" +
"@ObjectType nvarchar(max) = NULL,\n" +
"@IndexName nvarchar(max) = NULL,\n" +
"@IndexType int = NULL,\n" +
"@StatisticsName nvarchar(max) = NULL,\n" +
"@PartitionNumber int = NULL,\n" +
"@ExtendedInfo xml = NULL,\n" +
"@LockMessageSeverity int = 16,\n" +
"@ExecuteAsUser nvarchar(max) = NULL,\n" +
"@LogToTable nvarchar(max),\n" +
"@Execute nvarchar(max)\n" +
"\n" +
"AS\n" +
"\n" +
"BEGIN\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Source:  https://ola.hallengren.com                                                        //--\n" +
"  --// License: https://ola.hallengren.com/license.html                                           //--\n" +
"  --// GitHub:  https://github.com/olahallengren/sql-server-maintenance-solution                  //--\n" +
"  --// Version: 2020-12-31 18:58:56                                                               //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET NOCOUNT ON\n" +
"\n" +
"  DECLARE @StartMessage nvarchar(max)\n" +
"  DECLARE @EndMessage nvarchar(max)\n" +
"  DECLARE @ErrorMessage nvarchar(max)\n" +
"  DECLARE @ErrorMessageOriginal nvarchar(max)\n" +
"  DECLARE @Severity int\n" +
"\n" +
"  DECLARE @Errors TABLE (ID int IDENTITY PRIMARY KEY,\n" +
"                         [Message] nvarchar(max) NOT NULL,\n" +
"                         Severity int NOT NULL,\n" +
"                         [State] int)\n" +
"\n" +
"  DECLARE @CurrentMessage nvarchar(max)\n" +
"  DECLARE @CurrentSeverity int\n" +
"  DECLARE @CurrentState int\n" +
"\n" +
"  DECLARE @sp_executesql nvarchar(max) = QUOTENAME(@DatabaseContext) + '.sys.sp_executesql'\n" +
"\n" +
"  DECLARE @StartTime datetime2\n" +
"  DECLARE @EndTime datetime2\n" +
"\n" +
"  DECLARE @ID int\n" +
"\n" +
"  DECLARE @Error int = 0\n" +
"  DECLARE @ReturnCode int = 0\n" +
"\n" +
"  DECLARE @EmptyLine nvarchar(max) = CHAR(9)\n" +
"\n" +
"  DECLARE @RevertCommand nvarchar(max)\n";
    }
    
    public String part2(){
        return "  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check core requirements                                                                    //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF NOT (SELECT [compatibility_level] FROM sys.databases WHERE database_id = DB_ID()) >= 90\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The database ' + QUOTENAME(DB_NAME(DB_ID())) + ' has to be in compatibility level 90 or higher.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF NOT (SELECT uses_ansi_nulls FROM sys.sql_modules WHERE [object_id] = @@PROCID) = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'ANSI_NULLS has to be set to ON for the stored procedure.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF NOT (SELECT uses_quoted_identifier FROM sys.sql_modules WHERE [object_id] = @@PROCID) = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'QUOTED_IDENTIFIER has to be set to ON for the stored procedure.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @LogToTable = 'Y' AND NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'U' AND schemas.[name] = 'dbo' AND objects.[name] = 'CommandLog')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The table CommandLog is missing. Download https://ola.hallengren.com/scripts/CommandLog.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check input parameters                                                                     //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabaseContext IS NULL OR NOT EXISTS (SELECT * FROM sys.databases WHERE name = @DatabaseContext)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabaseContext is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @Command IS NULL OR @Command = ''\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Command is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @CommandType IS NULL OR @CommandType = '' OR LEN(@CommandType) > 60\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CommandType is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @Mode NOT IN(1,2) OR @Mode IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Mode is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @LockMessageSeverity NOT IN(10,16) OR @LockMessageSeverity IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @LockMessageSeverity is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF LEN(@ExecuteAsUser) > 128\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ExecuteAsUser is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @LogToTable NOT IN('Y','N') OR @LogToTable IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @LogToTable is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @Execute NOT IN('Y','N') OR @Execute IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Execute is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Raise errors                                                                               //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  DECLARE ErrorCursor CURSOR FAST_FORWARD FOR SELECT [Message], Severity, [State] FROM @Errors ORDER BY [ID] ASC\n" +
"\n" +
"  OPEN ErrorCursor\n" +
"\n" +
"  FETCH ErrorCursor INTO @CurrentMessage, @CurrentSeverity, @CurrentState\n" +
"\n" +
"  WHILE @@FETCH_STATUS = 0\n" +
"  BEGIN\n" +
"    RAISERROR('%s', @CurrentSeverity, @CurrentState, @CurrentMessage) WITH NOWAIT\n" +
"    RAISERROR(@EmptyLine, 10, 1) WITH NOWAIT\n" +
"\n" +
"    FETCH NEXT FROM ErrorCursor INTO @CurrentMessage, @CurrentSeverity, @CurrentState\n" +
"  END\n" +
"\n" +
"  CLOSE ErrorCursor\n" +
"\n" +
"  DEALLOCATE ErrorCursor\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @Errors WHERE Severity >= 16)\n" +
"  BEGIN\n" +
"    SET @ReturnCode = 50000\n" +
"    GOTO ReturnCode\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Execute as user                                                                            //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @ExecuteAsUser IS NOT NULL\n" +
"  BEGIN\n" +
"    SET @Command = 'EXECUTE AS USER = ''' + REPLACE(@ExecuteAsUser,'''','''''') + '''; ' + @Command + '; REVERT;'\n" +
"\n" +
"    SET @RevertCommand = 'REVERT'\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Log initial information                                                                    //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @StartTime = SYSDATETIME()\n" +
"\n" +
"  SET @StartMessage = 'Date and time: ' + CONVERT(nvarchar,@StartTime,120)\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Database context: ' + QUOTENAME(@DatabaseContext)\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Command: ' + @Command\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  IF @Comment IS NOT NULL\n" +
"  BEGIN\n" +
"    SET @StartMessage = 'Comment: ' + @Comment\n" +
"    RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"  END\n" +
"\n" +
"  IF @LogToTable = 'Y'\n" +
"  BEGIN\n" +
"    INSERT INTO dbo.CommandLog (DatabaseName, SchemaName, ObjectName, ObjectType, IndexName, IndexType, StatisticsName, PartitionNumber, ExtendedInfo, CommandType, Command, StartTime)\n" +
"    VALUES (@DatabaseName, @SchemaName, @ObjectName, @ObjectType, @IndexName, @IndexType, @StatisticsName, @PartitionNumber, @ExtendedInfo, @CommandType, @Command, @StartTime)\n" +
"  END\n" +
"\n" +
"  SET @ID = SCOPE_IDENTITY()\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Execute command                                                                            //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Mode = 1 AND @Execute = 'Y'\n" +
"  BEGIN\n" +
"    EXECUTE @sp_executesql @stmt = @Command\n" +
"    SET @Error = @@ERROR\n" +
"    SET @ReturnCode = @Error\n" +
"  END\n" +
"\n" +
"  IF @Mode = 2 AND @Execute = 'Y'\n" +
"  BEGIN\n" +
"    BEGIN TRY\n" +
"      EXECUTE @sp_executesql @stmt = @Command\n" +
"    END TRY\n" +
"    BEGIN CATCH\n" +
"      SET @Error = ERROR_NUMBER()\n" +
"      SET @ErrorMessageOriginal = ERROR_MESSAGE()\n" +
"\n" +
"      SET @ErrorMessage = 'Msg ' + CAST(ERROR_NUMBER() AS nvarchar) + ', ' + ISNULL(ERROR_MESSAGE(),'')\n" +
"      SET @Severity = CASE WHEN ERROR_NUMBER() IN(1205,1222) THEN @LockMessageSeverity ELSE 16 END\n" +
"      RAISERROR('%s',@Severity,1,@ErrorMessage) WITH NOWAIT\n" +
"\n" +
"      IF NOT (ERROR_NUMBER() IN(1205,1222) AND @LockMessageSeverity = 10)\n" +
"      BEGIN\n" +
"        SET @ReturnCode = ERROR_NUMBER()\n" +
"      END\n" +
"\n" +
"      IF @ExecuteAsUser IS NOT NULL\n" +
"      BEGIN\n" +
"        EXECUTE @sp_executesql @RevertCommand\n" +
"      END\n" +
"    END CATCH\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Log completing information                                                                 //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @EndTime = SYSDATETIME()\n" +
"\n" +
"  SET @EndMessage = 'Outcome: ' + CASE WHEN @Execute = 'N' THEN 'Not Executed' WHEN @Error = 0 THEN 'Succeeded' ELSE 'Failed' END\n" +
"  RAISERROR('%s',10,1,@EndMessage) WITH NOWAIT\n" +
"\n" +
"  SET @EndMessage = 'Duration: ' + CASE WHEN (DATEDIFF(SECOND,@StartTime,@EndTime) / (24 * 3600)) > 0 THEN CAST((DATEDIFF(SECOND,@StartTime,@EndTime) / (24 * 3600)) AS nvarchar) + '.' ELSE '' END + CONVERT(nvarchar,DATEADD(SECOND,DATEDIFF(SECOND,@StartTime,@EndTime),'1900-01-01'),108)\n" +
"  RAISERROR('%s',10,1,@EndMessage) WITH NOWAIT\n" +
"\n" +
"  SET @EndMessage = 'Date and time: ' + CONVERT(nvarchar,@EndTime,120)\n" +
"  RAISERROR('%s',10,1,@EndMessage) WITH NOWAIT\n" +
"\n" +
"  RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"  IF @LogToTable = 'Y'\n" +
"  BEGIN\n" +
"    UPDATE dbo.CommandLog\n" +
"    SET EndTime = @EndTime,\n" +
"        ErrorNumber = CASE WHEN @Execute = 'N' THEN NULL ELSE @Error END,\n" +
"        ErrorMessage = @ErrorMessageOriginal\n" +
"    WHERE ID = @ID\n" +
"  END\n" +
"\n" +
"  ReturnCode:\n" +
"  IF @ReturnCode <> 0\n" +
"  BEGIN\n" +
"    RETURN @ReturnCode\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"END\n" +
"GO\n" +
"SET ANSI_NULLS ON\n" +
"GO\n" +
"SET QUOTED_IDENTIFIER ON\n" +
"GO\n" +
"IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[DatabaseBackup]') AND type in (N'P', N'PC'))\n" +
"BEGIN\n" +
"EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[DatabaseBackup] AS'\n" +
"END\n" +
"GO\n" +
"ALTER PROCEDURE [dbo].[DatabaseBackup]\n" +
"\n" +
"@Databases nvarchar(max) = NULL,\n" +
"@Directory nvarchar(max) = NULL,\n" +
"@BackupType nvarchar(max),\n" +
"@Verify nvarchar(max) = 'N',\n" +
"@CleanupTime int = NULL,\n" +
"@CleanupMode nvarchar(max) = 'AFTER_BACKUP',\n" +
"@Compress nvarchar(max) = NULL,\n" +
"@CopyOnly nvarchar(max) = 'N',\n" +
"@ChangeBackupType nvarchar(max) = 'N',\n" +
"@BackupSoftware nvarchar(max) = NULL,\n" +
"@CheckSum nvarchar(max) = 'N',\n" +
"@BlockSize int = NULL,\n" +
"@BufferCount int = NULL,\n" +
"@MaxTransferSize int = NULL,\n" +
"@NumberOfFiles int = NULL,\n" +
"@MinBackupSizeForMultipleFiles int = NULL,\n" +
"@MaxFileSize int = NULL,\n" +
"@CompressionLevel int = NULL,\n" +
"@Description nvarchar(max) = NULL,\n" +
"@Threads int = NULL,\n" +
"@Throttle int = NULL,\n" +
"@Encrypt nvarchar(max) = 'N',\n" +
"@EncryptionAlgorithm nvarchar(max) = NULL,\n" +
"@ServerCertificate nvarchar(max) = NULL,\n" +
"@ServerAsymmetricKey nvarchar(max) = NULL,\n" +
"@EncryptionKey nvarchar(max) = NULL,\n" +
"@ReadWriteFileGroups nvarchar(max) = 'N',\n" +
"@OverrideBackupPreference nvarchar(max) = 'N',\n" +
"@NoRecovery nvarchar(max) = 'N',\n" +
"@URL nvarchar(max) = NULL,\n" +
"@Credential nvarchar(max) = NULL,\n" +
"@MirrorDirectory nvarchar(max) = NULL,\n" +
"@MirrorCleanupTime int = NULL,\n" +
"@MirrorCleanupMode nvarchar(max) = 'AFTER_BACKUP',\n" +
"@MirrorURL nvarchar(max) = NULL,\n" +
"@AvailabilityGroups nvarchar(max) = NULL,\n" +
"@Updateability nvarchar(max) = 'ALL',\n" +
"@AdaptiveCompression nvarchar(max) = NULL,\n" +
"@ModificationLevel int = NULL,\n" +
"@LogSizeSinceLastLogBackup int = NULL,\n" +
"@TimeSinceLastLogBackup int = NULL,\n" +
"@DataDomainBoostHost nvarchar(max) = NULL,\n" +
"@DataDomainBoostUser nvarchar(max) = NULL,\n" +
"@DataDomainBoostDevicePath nvarchar(max) = NULL,\n" +
"@DataDomainBoostLockboxPath nvarchar(max) = NULL,\n" +
"@DirectoryStructure nvarchar(max) = '{ServerName}${InstanceName}{DirectorySeparator}{DatabaseName}{DirectorySeparator}{BackupType}_{Partial}_{CopyOnly}',\n" +
"@AvailabilityGroupDirectoryStructure nvarchar(max) = '{ClusterName}${AvailabilityGroupName}{DirectorySeparator}{DatabaseName}{DirectorySeparator}{BackupType}_{Partial}_{CopyOnly}',\n" +
"@FileName nvarchar(max) = '{ServerName}${InstanceName}_{DatabaseName}_{BackupType}_{Partial}_{CopyOnly}_{Year}{Month}{Day}_{Hour}{Minute}{Second}_{FileNumber}.{FileExtension}',\n" +
"@AvailabilityGroupFileName nvarchar(max) = '{ClusterName}${AvailabilityGroupName}_{DatabaseName}_{BackupType}_{Partial}_{CopyOnly}_{Year}{Month}{Day}_{Hour}{Minute}{Second}_{FileNumber}.{FileExtension}',\n" +
"@FileExtensionFull nvarchar(max) = NULL,\n" +
"@FileExtensionDiff nvarchar(max) = NULL,\n" +
"@FileExtensionLog nvarchar(max) = NULL,\n" +
"@Init nvarchar(max) = 'N',\n" +
"@Format nvarchar(max) = 'N',\n" +
"@ObjectLevelRecoveryMap nvarchar(max) = 'N',\n" +
"@ExcludeLogShippedFromLogBackup nvarchar(max) = 'Y',\n" +
"@StringDelimiter nvarchar(max) = ',',\n" +
"@DatabaseOrder nvarchar(max) = NULL,\n" +
"@DatabasesInParallel nvarchar(max) = 'N',\n" +
"@LogToTable nvarchar(max) = 'N',\n" +
"@Execute nvarchar(max) = 'Y'\n" +
"\n" +
"AS\n" +
"\n" +
"BEGIN\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Source:  https://ola.hallengren.com                                                        //--\n" +
"  --// License: https://ola.hallengren.com/license.html                                           //--\n" +
"  --// GitHub:  https://github.com/olahallengren/sql-server-maintenance-solution                  //--\n" +
"  --// Version: 2020-12-31 18:58:56                                                               //--\n" +
"  ----------------------------------------------------------------------------------------------------\n";
    }
    
    public String part3(){
        return "  SET NOCOUNT ON\n" +
"\n" +
"  DECLARE @StartMessage nvarchar(max)\n" +
"  DECLARE @EndMessage nvarchar(max)\n" +
"  DECLARE @DatabaseMessage nvarchar(max)\n" +
"  DECLARE @ErrorMessage nvarchar(max)\n" +
"\n" +
"  DECLARE @StartTime datetime2 = SYSDATETIME()\n" +
"  DECLARE @SchemaName nvarchar(max) = OBJECT_SCHEMA_NAME(@@PROCID)\n" +
"  DECLARE @ObjectName nvarchar(max) = OBJECT_NAME(@@PROCID)\n" +
"  DECLARE @VersionTimestamp nvarchar(max) = SUBSTRING(OBJECT_DEFINITION(@@PROCID),CHARINDEX('--// Version: ',OBJECT_DEFINITION(@@PROCID)) + LEN('--// Version: ') + 1, 19)\n" +
"  DECLARE @Parameters nvarchar(max)\n" +
"\n" +
"  DECLARE @HostPlatform nvarchar(max)\n" +
"  DECLARE @DirectorySeparator nvarchar(max)\n" +
"\n" +
"  DECLARE @Updated bit\n" +
"\n" +
"  DECLARE @Cluster nvarchar(max)\n" +
"\n" +
"  DECLARE @DefaultDirectory nvarchar(4000)\n" +
"\n" +
"  DECLARE @QueueID int\n" +
"  DECLARE @QueueStartTime datetime2\n" +
"\n" +
"  DECLARE @CurrentRootDirectoryID int\n" +
"  DECLARE @CurrentRootDirectoryPath nvarchar(4000)\n" +
"\n" +
"  DECLARE @CurrentDBID int\n" +
"  DECLARE @CurrentDatabaseName nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentDatabase_sp_executesql nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentUserAccess nvarchar(max)\n" +
"  DECLARE @CurrentIsReadOnly bit\n" +
"  DECLARE @CurrentDatabaseState nvarchar(max)\n" +
"  DECLARE @CurrentInStandby bit\n" +
"  DECLARE @CurrentRecoveryModel nvarchar(max)\n" +
"  DECLARE @CurrentDatabaseSize bigint\n" +
"\n" +
"  DECLARE @CurrentIsEncrypted bit\n" +
"\n" +
"  DECLARE @CurrentBackupType nvarchar(max)\n" +
"  DECLARE @CurrentMaxTransferSize int\n" +
"  DECLARE @CurrentNumberOfFiles int\n" +
"  DECLARE @CurrentFileExtension nvarchar(max)\n" +
"  DECLARE @CurrentFileNumber int\n" +
"  DECLARE @CurrentDifferentialBaseLSN numeric(25,0)\n" +
"  DECLARE @CurrentDifferentialBaseIsSnapshot bit\n" +
"  DECLARE @CurrentLogLSN numeric(25,0)\n" +
"  DECLARE @CurrentLatestBackup datetime2\n" +
"  DECLARE @CurrentDatabaseNameFS nvarchar(max)\n" +
"  DECLARE @CurrentDirectoryStructure nvarchar(max)\n" +
"  DECLARE @CurrentDatabaseFileName nvarchar(max)\n" +
"  DECLARE @CurrentMaxFilePathLength nvarchar(max)\n" +
"  DECLARE @CurrentFileName nvarchar(max)\n" +
"  DECLARE @CurrentDirectoryID int\n" +
"  DECLARE @CurrentDirectoryPath nvarchar(max)\n" +
"  DECLARE @CurrentFilePath nvarchar(max)\n" +
"  DECLARE @CurrentDate datetime2\n" +
"  DECLARE @CurrentCleanupDate datetime2\n" +
"  DECLARE @CurrentIsDatabaseAccessible bit\n" +
"  DECLARE @CurrentReplicaID uniqueidentifier\n" +
"  DECLARE @CurrentAvailabilityGroupID uniqueidentifier\n" +
"  DECLARE @CurrentAvailabilityGroup nvarchar(max)\n" +
"  DECLARE @CurrentAvailabilityGroupRole nvarchar(max)\n" +
"  DECLARE @CurrentAvailabilityGroupBackupPreference nvarchar(max)\n" +
"  DECLARE @CurrentIsPreferredBackupReplica bit\n" +
"  DECLARE @CurrentDatabaseMirroringRole nvarchar(max)\n" +
"  DECLARE @CurrentLogShippingRole nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentBackupSetID int\n" +
"  DECLARE @CurrentIsMirror bit\n" +
"  DECLARE @CurrentLastLogBackup datetime2\n" +
"  DECLARE @CurrentLogSizeSinceLastLogBackup float\n" +
"  DECLARE @CurrentAllocatedExtentPageCount bigint\n" +
"  DECLARE @CurrentModifiedExtentPageCount bigint\n" +
"\n" +
"  DECLARE @CurrentDatabaseContext nvarchar(max)\n" +
"  DECLARE @CurrentCommand nvarchar(max)\n" +
"  DECLARE @CurrentCommandOutput int\n" +
"  DECLARE @CurrentCommandType nvarchar(max)\n" +
"\n" +
"  DECLARE @Errors TABLE (ID int IDENTITY PRIMARY KEY,\n" +
"                         [Message] nvarchar(max) NOT NULL,\n" +
"                         Severity int NOT NULL,\n" +
"                         [State] int)\n" +
"\n" +
"  DECLARE @CurrentMessage nvarchar(max)\n" +
"  DECLARE @CurrentSeverity int\n" +
"  DECLARE @CurrentState int\n" +
"\n" +
"  DECLARE @Directories TABLE (ID int PRIMARY KEY,\n" +
"                              DirectoryPath nvarchar(max),\n" +
"                              Mirror bit,\n" +
"                              Completed bit)\n" +
"\n" +
"  DECLARE @URLs TABLE (ID int PRIMARY KEY,\n" +
"                       DirectoryPath nvarchar(max),\n" +
"                       Mirror bit)\n" +
"\n" +
"  DECLARE @DirectoryInfo TABLE (FileExists bit,\n" +
"                                FileIsADirectory bit,\n" +
"                                ParentDirectoryExists bit)\n" +
"\n" +
"  DECLARE @tmpDatabases TABLE (ID int IDENTITY,\n" +
"                               DatabaseName nvarchar(max),\n" +
"                               DatabaseNameFS nvarchar(max),\n" +
"                               DatabaseType nvarchar(max),\n" +
"                               AvailabilityGroup bit,\n" +
"                               StartPosition int,\n" +
"                               DatabaseSize bigint,\n" +
"                               LogSizeSinceLastLogBackup float,\n" +
"                               [Order] int,\n" +
"                               Selected bit,\n" +
"                               Completed bit,\n" +
"                               PRIMARY KEY(Selected, Completed, [Order], ID))\n" +
"\n" +
"  DECLARE @tmpAvailabilityGroups TABLE (ID int IDENTITY PRIMARY KEY,\n" +
"                                        AvailabilityGroupName nvarchar(max),\n" +
"                                        StartPosition int,\n" +
"                                        Selected bit)\n" +
"\n" +
"  DECLARE @tmpDatabasesAvailabilityGroups TABLE (DatabaseName nvarchar(max),\n" +
"                                                 AvailabilityGroupName nvarchar(max))\n" +
"\n" +
"  DECLARE @SelectedDatabases TABLE (DatabaseName nvarchar(max),\n" +
"                                    DatabaseType nvarchar(max),\n" +
"                                    AvailabilityGroup nvarchar(max),\n" +
"                                    StartPosition int,\n" +
"                                    Selected bit)\n" +
"\n" +
"  DECLARE @SelectedAvailabilityGroups TABLE (AvailabilityGroupName nvarchar(max),\n" +
"                                             StartPosition int,\n" +
"                                             Selected bit)\n" +
"\n" +
"  DECLARE @CurrentBackupOutput bit\n" +
"\n" +
"  DECLARE @CurrentBackupSet TABLE (ID int IDENTITY PRIMARY KEY,\n" +
"                                   Mirror bit,\n" +
"                                   VerifyCompleted bit,\n" +
"                                   VerifyOutput int)\n" +
"\n" +
"  DECLARE @CurrentDirectories TABLE (ID int PRIMARY KEY,\n" +
"                                     DirectoryPath nvarchar(max),\n" +
"                                     Mirror bit,\n" +
"                                     DirectoryNumber int,\n" +
"                                     CleanupDate datetime2,\n" +
"                                     CleanupMode nvarchar(max),\n" +
"                                     CreateCompleted bit,\n" +
"                                     CleanupCompleted bit,\n" +
"                                     CreateOutput int,\n" +
"                                     CleanupOutput int)\n" +
"\n" +
"  DECLARE @CurrentURLs TABLE (ID int PRIMARY KEY,\n" +
"                              DirectoryPath nvarchar(max),\n" +
"                              Mirror bit,\n" +
"                              DirectoryNumber int)\n" +
"\n" +
"  DECLARE @CurrentFiles TABLE ([Type] nvarchar(max),\n" +
"                               FilePath nvarchar(max),\n" +
"                               Mirror bit)\n" +
"\n" +
"  DECLARE @CurrentCleanupDates TABLE (CleanupDate datetime2,\n" +
"                                      Mirror bit)\n" +
"\n" +
"  DECLARE @Error int = 0\n" +
"  DECLARE @ReturnCode int = 0\n" +
"\n" +
"  DECLARE @EmptyLine nvarchar(max) = CHAR(9)\n" +
"\n" +
"  DECLARE @Version numeric(18,10) = CAST(LEFT(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max)),CHARINDEX('.',CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max))) - 1) + '.' + REPLACE(RIGHT(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max)), LEN(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max))) - CHARINDEX('.',CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max)))),'.','') AS numeric(18,10))\n" +
"\n" +
"  IF @Version >= 14\n" +
"  BEGIN\n" +
"    SELECT @HostPlatform = host_platform\n" +
"    FROM sys.dm_os_host_info\n" +
"  END\n" +
"  ELSE\n" +
"  BEGIN\n" +
"    SET @HostPlatform = 'Windows'\n" +
"  END\n" +
"\n" +
"  DECLARE @AmazonRDS bit = CASE WHEN DB_ID('rdsadmin') IS NOT NULL AND SUSER_SNAME(0x01) = 'rdsa' THEN 1 ELSE 0 END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Log initial information                                                                    //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @Parameters = '@Databases = ' + ISNULL('''' + REPLACE(@Databases,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Directory = ' + ISNULL('''' + REPLACE(@Directory,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @BackupType = ' + ISNULL('''' + REPLACE(@BackupType,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Verify = ' + ISNULL('''' + REPLACE(@Verify,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @CleanupTime = ' + ISNULL(CAST(@CleanupTime AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @CleanupMode = ' + ISNULL('''' + REPLACE(@CleanupMode,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Compress = ' + ISNULL('''' + REPLACE(@Compress,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @CopyOnly = ' + ISNULL('''' + REPLACE(@CopyOnly,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @ChangeBackupType = ' + ISNULL('''' + REPLACE(@ChangeBackupType,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @BackupSoftware = ' + ISNULL('''' + REPLACE(@BackupSoftware,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @CheckSum = ' + ISNULL('''' + REPLACE(@CheckSum,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @BlockSize = ' + ISNULL(CAST(@BlockSize AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @BufferCount = ' + ISNULL(CAST(@BufferCount AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @MaxTransferSize = ' + ISNULL(CAST(@MaxTransferSize AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @NumberOfFiles = ' + ISNULL(CAST(@NumberOfFiles AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @MinBackupSizeForMultipleFiles = ' + ISNULL(CAST(@MinBackupSizeForMultipleFiles AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @MaxFileSize = ' + ISNULL(CAST(@MaxFileSize AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @CompressionLevel = ' + ISNULL(CAST(@CompressionLevel AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @Description = ' + ISNULL('''' + REPLACE(@Description,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Threads = ' + ISNULL(CAST(@Threads AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @Throttle = ' + ISNULL(CAST(@Throttle AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @Encrypt = ' + ISNULL('''' + REPLACE(@Encrypt,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @EncryptionAlgorithm = ' + ISNULL('''' + REPLACE(@EncryptionAlgorithm,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @ServerCertificate = ' + ISNULL('''' + REPLACE(@ServerCertificate,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @ServerAsymmetricKey = ' + ISNULL('''' + REPLACE(@ServerAsymmetricKey,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @EncryptionKey = ' + ISNULL('''' + REPLACE(@EncryptionKey,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @ReadWriteFileGroups = ' + ISNULL('''' + REPLACE(@ReadWriteFileGroups,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @OverrideBackupPreference = ' + ISNULL('''' + REPLACE(@OverrideBackupPreference,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @NoRecovery = ' + ISNULL('''' + REPLACE(@NoRecovery,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @URL = ' + ISNULL('''' + REPLACE(@URL,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Credential = ' + ISNULL('''' + REPLACE(@Credential,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @MirrorDirectory = ' + ISNULL('''' + REPLACE(@MirrorDirectory,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @MirrorCleanupTime = ' + ISNULL(CAST(@MirrorCleanupTime AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @MirrorCleanupMode = ' + ISNULL('''' + REPLACE(@MirrorCleanupMode,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @MirrorURL = ' + ISNULL('''' + REPLACE(@MirrorURL,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @AvailabilityGroups = ' + ISNULL('''' + REPLACE(@AvailabilityGroups,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Updateability = ' + ISNULL('''' + REPLACE(@Updateability,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @AdaptiveCompression = ' + ISNULL('''' + REPLACE(@AdaptiveCompression,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @ModificationLevel = ' + ISNULL(CAST(@ModificationLevel AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @LogSizeSinceLastLogBackup = ' + ISNULL(CAST(@LogSizeSinceLastLogBackup AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @TimeSinceLastLogBackup = ' + ISNULL(CAST(@TimeSinceLastLogBackup AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @DataDomainBoostHost = ' + ISNULL('''' + REPLACE(@DataDomainBoostHost,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @DataDomainBoostUser = ' + ISNULL('''' + REPLACE(@DataDomainBoostUser,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @DataDomainBoostDevicePath = ' + ISNULL('''' + REPLACE(@DataDomainBoostDevicePath,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @DataDomainBoostLockboxPath = ' + ISNULL('''' + REPLACE(@DataDomainBoostLockboxPath,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @DirectoryStructure = ' + ISNULL('''' + REPLACE(@DirectoryStructure,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @AvailabilityGroupDirectoryStructure = ' + ISNULL('''' + REPLACE(@AvailabilityGroupDirectoryStructure,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @FileName = ' + ISNULL('''' + REPLACE(@FileName,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @AvailabilityGroupFileName = ' + ISNULL('''' + REPLACE(@AvailabilityGroupFileName,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @FileExtensionFull = ' + ISNULL('''' + REPLACE(@FileExtensionFull,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @FileExtensionDiff = ' + ISNULL('''' + REPLACE(@FileExtensionDiff,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @FileExtensionLog = ' + ISNULL('''' + REPLACE(@FileExtensionLog,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Init = ' + ISNULL('''' + REPLACE(@Init,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Format = ' + ISNULL('''' + REPLACE(@Format,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @ObjectLevelRecoveryMap = ' + ISNULL('''' + REPLACE(@ObjectLevelRecoveryMap,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @ExcludeLogShippedFromLogBackup = ' + ISNULL('''' + REPLACE(@ExcludeLogShippedFromLogBackup,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @StringDelimiter = ' + ISNULL('''' + REPLACE(@StringDelimiter,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @DatabaseOrder = ' + ISNULL('''' + REPLACE(@DatabaseOrder,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @DatabasesInParallel = ' + ISNULL('''' + REPLACE(@DatabasesInParallel,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @LogToTable = ' + ISNULL('''' + REPLACE(@LogToTable,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Execute = ' + ISNULL('''' + REPLACE(@Execute,'''','''''') + '''','NULL')\n" +
"\n" +
"  SET @StartMessage = 'Date and time: ' + CONVERT(nvarchar,@StartTime,120)\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Server: ' + CAST(SERVERPROPERTY('ServerName') AS nvarchar(max))\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Version: ' + CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max))\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Edition: ' + CAST(SERVERPROPERTY('Edition') AS nvarchar(max))\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Platform: ' + @HostPlatform\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Procedure: ' + QUOTENAME(DB_NAME(DB_ID())) + '.' + QUOTENAME(@SchemaName) + '.' + QUOTENAME(@ObjectName)\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Parameters: ' + @Parameters\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Version: ' + @VersionTimestamp\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Source: https://ola.hallengren.com'\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check core requirements                                                                    //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF NOT (SELECT [compatibility_level] FROM sys.databases WHERE database_id = DB_ID()) >= 90\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The database ' + QUOTENAME(DB_NAME(DB_ID())) + ' has to be in compatibility level 90 or higher.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF NOT (SELECT uses_ansi_nulls FROM sys.sql_modules WHERE [object_id] = @@PROCID) = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'ANSI_NULLS has to be set to ON for the stored procedure.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF NOT (SELECT uses_quoted_identifier FROM sys.sql_modules WHERE [object_id] = @@PROCID) = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'QUOTED_IDENTIFIER has to be set to ON for the stored procedure.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'P' AND schemas.[name] = 'dbo' AND objects.[name] = 'CommandExecute')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The stored procedure CommandExecute is missing. Download https://ola.hallengren.com/scripts/CommandExecute.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'P' AND schemas.[name] = 'dbo' AND objects.[name] = 'CommandExecute' AND OBJECT_DEFINITION(objects.[object_id]) NOT LIKE '%@DatabaseContext%')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The stored procedure CommandExecute needs to be updated. Download https://ola.hallengren.com/scripts/CommandExecute.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @LogToTable = 'Y' AND NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'U' AND schemas.[name] = 'dbo' AND objects.[name] = 'CommandLog')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The table CommandLog is missing. Download https://ola.hallengren.com/scripts/CommandLog.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DatabasesInParallel = 'Y' AND NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'U' AND schemas.[name] = 'dbo' AND objects.[name] = 'Queue')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The table Queue is missing. Download https://ola.hallengren.com/scripts/Queue.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DatabasesInParallel = 'Y' AND NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'U' AND schemas.[name] = 'dbo' AND objects.[name] = 'QueueDatabase')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The table QueueDatabase is missing. Download https://ola.hallengren.com/scripts/QueueDatabase.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @@TRANCOUNT <> 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The transaction count is not 0.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @AmazonRDS = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The stored procedure DatabaseBackup is not supported on Amazon RDS.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select databases                                                                           //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @Databases = REPLACE(@Databases, CHAR(10), '')\n" +
"  SET @Databases = REPLACE(@Databases, CHAR(13), '')\n" +
"\n" +
"  WHILE CHARINDEX(@StringDelimiter + ' ', @Databases) > 0 SET @Databases = REPLACE(@Databases, @StringDelimiter + ' ', @StringDelimiter)\n" +
"  WHILE CHARINDEX(' ' + @StringDelimiter, @Databases) > 0 SET @Databases = REPLACE(@Databases, ' ' + @StringDelimiter, @StringDelimiter)\n" +
"\n" +
"  SET @Databases = LTRIM(RTRIM(@Databases));\n" +
"\n" +
"  WITH Databases1 (StartPosition, EndPosition, DatabaseItem) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Databases, 1), 0), LEN(@Databases) + 1) AS EndPosition,\n" +
"         SUBSTRING(@Databases, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Databases, 1), 0), LEN(@Databases) + 1) - 1) AS DatabaseItem\n" +
"  WHERE @Databases IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Databases, EndPosition + 1), 0), LEN(@Databases) + 1) AS EndPosition,\n" +
"         SUBSTRING(@Databases, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Databases, EndPosition + 1), 0), LEN(@Databases) + 1) - EndPosition - 1) AS DatabaseItem\n" +
"  FROM Databases1\n" +
"  WHERE EndPosition < LEN(@Databases) + 1\n" +
"  ),\n" +
"  Databases2 (DatabaseItem, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN DatabaseItem LIKE '-%' THEN RIGHT(DatabaseItem,LEN(DatabaseItem) - 1) ELSE DatabaseItem END AS DatabaseItem,\n" +
"         StartPosition,\n" +
"         CASE WHEN DatabaseItem LIKE '-%' THEN 0 ELSE 1 END AS Selected\n" +
"  FROM Databases1\n" +
"  ),\n" +
"  Databases3 (DatabaseItem, DatabaseType, AvailabilityGroup, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN DatabaseItem IN('ALL_DATABASES','SYSTEM_DATABASES','USER_DATABASES','AVAILABILITY_GROUP_DATABASES') THEN '%' ELSE DatabaseItem END AS DatabaseItem,\n" +
"         CASE WHEN DatabaseItem = 'SYSTEM_DATABASES' THEN 'S' WHEN DatabaseItem = 'USER_DATABASES' THEN 'U' ELSE NULL END AS DatabaseType,\n" +
"         CASE WHEN DatabaseItem = 'AVAILABILITY_GROUP_DATABASES' THEN 1 ELSE NULL END AvailabilityGroup,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Databases2\n" +
"  ),\n" +
"  Databases4 (DatabaseName, DatabaseType, AvailabilityGroup, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN LEFT(DatabaseItem,1) = '[' AND RIGHT(DatabaseItem,1) = ']' THEN PARSENAME(DatabaseItem,1) ELSE DatabaseItem END AS DatabaseItem,\n" +
"         DatabaseType,\n" +
"         AvailabilityGroup,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Databases3\n" +
"  )\n" +
"  INSERT INTO @SelectedDatabases (DatabaseName, DatabaseType, AvailabilityGroup, StartPosition, Selected)\n" +
"  SELECT DatabaseName,\n" +
"         DatabaseType,\n" +
"         AvailabilityGroup,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Databases4\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  IF @Version >= 11 AND SERVERPROPERTY('IsHadrEnabled') = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @tmpAvailabilityGroups (AvailabilityGroupName, Selected)\n" +
"    SELECT name AS AvailabilityGroupName,\n" +
"           0 AS Selected\n" +
"    FROM sys.availability_groups\n" +
"\n" +
"    INSERT INTO @tmpDatabasesAvailabilityGroups (DatabaseName, AvailabilityGroupName)\n" +
"    SELECT databases.name,\n" +
"           availability_groups.name\n" +
"    FROM sys.databases databases\n" +
"    INNER JOIN sys.availability_replicas availability_replicas ON databases.replica_id = availability_replicas.replica_id\n" +
"    INNER JOIN sys.availability_groups availability_groups ON availability_replicas.group_id = availability_groups.group_id\n" +
"  END\n" +
"\n" +
"  INSERT INTO @tmpDatabases (DatabaseName, DatabaseNameFS, DatabaseType, AvailabilityGroup, [Order], Selected, Completed)\n" +
"  SELECT [name] AS DatabaseName,\n" +
"         RTRIM(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE([name],'\\',''),'/',''),':',''),'*',''),'?',''),'\"',''),'<',''),'>',''),'|','')) AS DatabaseNameFS,\n" +
"         CASE WHEN name IN('master','msdb','model') OR is_distributor = 1 THEN 'S' ELSE 'U' END AS DatabaseType,\n" +
"         NULL AS AvailabilityGroup,\n" +
"         0 AS [Order],\n" +
"         0 AS Selected,\n" +
"         0 AS Completed\n" +
"  FROM sys.databases\n" +
"  WHERE [name] <> 'tempdb'\n" +
"  AND source_database_id IS NULL\n" +
"  ORDER BY [name] ASC\n" +
"\n" +
"  UPDATE tmpDatabases\n" +
"  SET AvailabilityGroup = CASE WHEN EXISTS (SELECT * FROM @tmpDatabasesAvailabilityGroups WHERE DatabaseName = tmpDatabases.DatabaseName) THEN 1 ELSE 0 END\n" +
"  FROM @tmpDatabases tmpDatabases\n" +
"\n" +
"  UPDATE tmpDatabases\n" +
"  SET tmpDatabases.Selected = SelectedDatabases.Selected\n" +
"  FROM @tmpDatabases tmpDatabases\n" +
"  INNER JOIN @SelectedDatabases SelectedDatabases\n" +
"  ON tmpDatabases.DatabaseName LIKE REPLACE(SelectedDatabases.DatabaseName,'_','[_]')\n" +
"  AND (tmpDatabases.DatabaseType = SelectedDatabases.DatabaseType OR SelectedDatabases.DatabaseType IS NULL)\n" +
"  AND (tmpDatabases.AvailabilityGroup = SelectedDatabases.AvailabilityGroup OR SelectedDatabases.AvailabilityGroup IS NULL)\n" +
"  WHERE SelectedDatabases.Selected = 1\n" +
"\n" +
"  UPDATE tmpDatabases\n" +
"  SET tmpDatabases.Selected = SelectedDatabases.Selected\n" +
"  FROM @tmpDatabases tmpDatabases\n" +
"  INNER JOIN @SelectedDatabases SelectedDatabases\n" +
"  ON tmpDatabases.DatabaseName LIKE REPLACE(SelectedDatabases.DatabaseName,'_','[_]')\n" +
"  AND (tmpDatabases.DatabaseType = SelectedDatabases.DatabaseType OR SelectedDatabases.DatabaseType IS NULL)\n" +
"  AND (tmpDatabases.AvailabilityGroup = SelectedDatabases.AvailabilityGroup OR SelectedDatabases.AvailabilityGroup IS NULL)\n" +
"  WHERE SelectedDatabases.Selected = 0\n" +
"\n" +
"  UPDATE tmpDatabases\n" +
"  SET tmpDatabases.StartPosition = SelectedDatabases2.StartPosition\n" +
"  FROM @tmpDatabases tmpDatabases\n" +
"  INNER JOIN (SELECT tmpDatabases.DatabaseName, MIN(SelectedDatabases.StartPosition) AS StartPosition\n" +
"              FROM @tmpDatabases tmpDatabases\n" +
"              INNER JOIN @SelectedDatabases SelectedDatabases\n" +
"              ON tmpDatabases.DatabaseName LIKE REPLACE(SelectedDatabases.DatabaseName,'_','[_]')\n" +
"              AND (tmpDatabases.DatabaseType = SelectedDatabases.DatabaseType OR SelectedDatabases.DatabaseType IS NULL)\n" +
"              AND (tmpDatabases.AvailabilityGroup = SelectedDatabases.AvailabilityGroup OR SelectedDatabases.AvailabilityGroup IS NULL)\n" +
"              WHERE SelectedDatabases.Selected = 1\n" +
"              GROUP BY tmpDatabases.DatabaseName) SelectedDatabases2\n" +
"  ON tmpDatabases.DatabaseName = SelectedDatabases2.DatabaseName\n" +
"\n" +
"  IF @Databases IS NOT NULL AND (NOT EXISTS(SELECT * FROM @SelectedDatabases) OR EXISTS(SELECT * FROM @SelectedDatabases WHERE DatabaseName IS NULL OR DatabaseName = ''))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Databases is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select availability groups                                                                 //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @AvailabilityGroups IS NOT NULL AND @Version >= 11 AND SERVERPROPERTY('IsHadrEnabled') = 1\n" +
"  BEGIN\n" +
"\n" +
"    SET @AvailabilityGroups = REPLACE(@AvailabilityGroups, CHAR(10), '')\n" +
"    SET @AvailabilityGroups = REPLACE(@AvailabilityGroups, CHAR(13), '')\n" +
"\n" +
"    WHILE CHARINDEX(@StringDelimiter + ' ', @AvailabilityGroups) > 0 SET @AvailabilityGroups = REPLACE(@AvailabilityGroups, @StringDelimiter + ' ', @StringDelimiter)\n" +
"    WHILE CHARINDEX(' ' + @StringDelimiter, @AvailabilityGroups) > 0 SET @AvailabilityGroups = REPLACE(@AvailabilityGroups, ' ' + @StringDelimiter, @StringDelimiter)\n" +
"\n" +
"    SET @AvailabilityGroups = LTRIM(RTRIM(@AvailabilityGroups));\n" +
"\n" +
"    WITH AvailabilityGroups1 (StartPosition, EndPosition, AvailabilityGroupItem) AS\n" +
"    (\n" +
"    SELECT 1 AS StartPosition,\n" +
"           ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @AvailabilityGroups, 1), 0), LEN(@AvailabilityGroups) + 1) AS EndPosition,\n" +
"           SUBSTRING(@AvailabilityGroups, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @AvailabilityGroups, 1), 0), LEN(@AvailabilityGroups) + 1) - 1) AS AvailabilityGroupItem\n" +
"    WHERE @AvailabilityGroups IS NOT NULL\n" +
"    UNION ALL\n" +
"    SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"           ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @AvailabilityGroups, EndPosition + 1), 0), LEN(@AvailabilityGroups) + 1) AS EndPosition,\n" +
"           SUBSTRING(@AvailabilityGroups, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @AvailabilityGroups, EndPosition + 1), 0), LEN(@AvailabilityGroups) + 1) - EndPosition - 1) AS AvailabilityGroupItem\n" +
"    FROM AvailabilityGroups1\n" +
"    WHERE EndPosition < LEN(@AvailabilityGroups) + 1\n" +
"    ),\n" +
"    AvailabilityGroups2 (AvailabilityGroupItem, StartPosition, Selected) AS\n" +
"    (\n" +
"    SELECT CASE WHEN AvailabilityGroupItem LIKE '-%' THEN RIGHT(AvailabilityGroupItem,LEN(AvailabilityGroupItem) - 1) ELSE AvailabilityGroupItem END AS AvailabilityGroupItem,\n" +
"           StartPosition,\n" +
"           CASE WHEN AvailabilityGroupItem LIKE '-%' THEN 0 ELSE 1 END AS Selected\n" +
"    FROM AvailabilityGroups1\n" +
"    ),\n" +
"    AvailabilityGroups3 (AvailabilityGroupItem, StartPosition, Selected) AS\n" +
"    (\n" +
"    SELECT CASE WHEN AvailabilityGroupItem = 'ALL_AVAILABILITY_GROUPS' THEN '%' ELSE AvailabilityGroupItem END AS AvailabilityGroupItem,\n" +
"           StartPosition,\n" +
"           Selected\n" +
"    FROM AvailabilityGroups2\n" +
"    ),\n" +
"    AvailabilityGroups4 (AvailabilityGroupName, StartPosition, Selected) AS\n" +
"    (\n" +
"    SELECT CASE WHEN LEFT(AvailabilityGroupItem,1) = '[' AND RIGHT(AvailabilityGroupItem,1) = ']' THEN PARSENAME(AvailabilityGroupItem,1) ELSE AvailabilityGroupItem END AS AvailabilityGroupItem,\n" +
"           StartPosition,\n" +
"           Selected\n" +
"    FROM AvailabilityGroups3\n" +
"    )\n" +
"    INSERT INTO @SelectedAvailabilityGroups (AvailabilityGroupName, StartPosition, Selected)\n" +
"    SELECT AvailabilityGroupName, StartPosition, Selected\n" +
"    FROM AvailabilityGroups4\n" +
"    OPTION (MAXRECURSION 0)\n" +
"\n" +
"    UPDATE tmpAvailabilityGroups\n" +
"    SET tmpAvailabilityGroups.Selected = SelectedAvailabilityGroups.Selected\n" +
"    FROM @tmpAvailabilityGroups tmpAvailabilityGroups\n" +
"    INNER JOIN @SelectedAvailabilityGroups SelectedAvailabilityGroups\n" +
"    ON tmpAvailabilityGroups.AvailabilityGroupName LIKE REPLACE(SelectedAvailabilityGroups.AvailabilityGroupName,'_','[_]')\n" +
"    WHERE SelectedAvailabilityGroups.Selected = 1\n" +
"\n" +
"    UPDATE tmpAvailabilityGroups\n" +
"    SET tmpAvailabilityGroups.Selected = SelectedAvailabilityGroups.Selected\n" +
"    FROM @tmpAvailabilityGroups tmpAvailabilityGroups\n" +
"    INNER JOIN @SelectedAvailabilityGroups SelectedAvailabilityGroups\n" +
"    ON tmpAvailabilityGroups.AvailabilityGroupName LIKE REPLACE(SelectedAvailabilityGroups.AvailabilityGroupName,'_','[_]')\n" +
"    WHERE SelectedAvailabilityGroups.Selected = 0\n" +
"\n" +
"    UPDATE tmpAvailabilityGroups\n" +
"    SET tmpAvailabilityGroups.StartPosition = SelectedAvailabilityGroups2.StartPosition\n" +
"    FROM @tmpAvailabilityGroups tmpAvailabilityGroups\n" +
"    INNER JOIN (SELECT tmpAvailabilityGroups.AvailabilityGroupName, MIN(SelectedAvailabilityGroups.StartPosition) AS StartPosition\n" +
"                FROM @tmpAvailabilityGroups tmpAvailabilityGroups\n" +
"                INNER JOIN @SelectedAvailabilityGroups SelectedAvailabilityGroups\n" +
"                ON tmpAvailabilityGroups.AvailabilityGroupName LIKE REPLACE(SelectedAvailabilityGroups.AvailabilityGroupName,'_','[_]')\n" +
"                WHERE SelectedAvailabilityGroups.Selected = 1\n" +
"                GROUP BY tmpAvailabilityGroups.AvailabilityGroupName) SelectedAvailabilityGroups2\n" +
"    ON tmpAvailabilityGroups.AvailabilityGroupName = SelectedAvailabilityGroups2.AvailabilityGroupName\n" +
"\n" +
"    UPDATE tmpDatabases\n" +
"    SET tmpDatabases.StartPosition = tmpAvailabilityGroups.StartPosition,\n" +
"        tmpDatabases.Selected = 1\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    INNER JOIN @tmpDatabasesAvailabilityGroups tmpDatabasesAvailabilityGroups ON tmpDatabases.DatabaseName = tmpDatabasesAvailabilityGroups.DatabaseName\n" +
"    INNER JOIN @tmpAvailabilityGroups tmpAvailabilityGroups ON tmpDatabasesAvailabilityGroups.AvailabilityGroupName = tmpAvailabilityGroups.AvailabilityGroupName\n" +
"    WHERE tmpAvailabilityGroups.Selected = 1\n" +
"\n" +
"  END\n" +
"\n" +
"  IF @AvailabilityGroups IS NOT NULL AND (NOT EXISTS(SELECT * FROM @SelectedAvailabilityGroups) OR EXISTS(SELECT * FROM @SelectedAvailabilityGroups WHERE AvailabilityGroupName IS NULL OR AvailabilityGroupName = '') OR @Version < 11 OR SERVERPROPERTY('IsHadrEnabled') = 0)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AvailabilityGroups is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF (@Databases IS NULL AND @AvailabilityGroups IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'You need to specify one of the parameters @Databases and @AvailabilityGroups.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF (@Databases IS NOT NULL AND @AvailabilityGroups IS NOT NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'You can only specify one of the parameters @Databases and @AvailabilityGroups.', 16, 3\n" +
"  END\n";
    }
    
    public String part4(){
        return "  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check database names                                                                       //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + ', '\n" +
"  FROM @tmpDatabases\n" +
"  WHERE Selected = 1\n" +
"  AND DatabaseNameFS = ''\n" +
"  ORDER BY DatabaseName ASC\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The names of the following databases are not supported: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 16, 1\n" +
"  END\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + ', '\n" +
"  FROM @tmpDatabases\n" +
"  WHERE UPPER(DatabaseNameFS) IN(SELECT UPPER(DatabaseNameFS) FROM @tmpDatabases GROUP BY UPPER(DatabaseNameFS) HAVING COUNT(*) > 1)\n" +
"  AND UPPER(DatabaseNameFS) IN(SELECT UPPER(DatabaseNameFS) FROM @tmpDatabases WHERE Selected = 1)\n" +
"  AND DatabaseNameFS <> ''\n" +
"  ORDER BY DatabaseName ASC\n" +
"  OPTION (RECOMPILE)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The names of the following databases are not unique in the file system: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select default directory                                                                      //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Directory IS NULL AND @URL IS NULL AND (@BackupSoftware <> 'DATA_DOMAIN_BOOST' OR @BackupSoftware IS NULL)\n" +
"  BEGIN\n" +
"    IF @Version >= 15\n" +
"    BEGIN\n" +
"      SET @DefaultDirectory = CAST(SERVERPROPERTY('InstanceDefaultBackupPath') AS nvarchar(max))\n" +
"    END\n" +
"    ELSE\n" +
"    BEGIN\n" +
"      EXECUTE [master].dbo.xp_instance_regread N'HKEY_LOCAL_MACHINE', N'SOFTWARE\\Microsoft\\MSSQLServer\\MSSQLServer', N'BackupDirectory', @DefaultDirectory OUTPUT\n" +
"    END\n" +
"\n" +
"    IF @DefaultDirectory LIKE 'http://%' OR @DefaultDirectory LIKE 'https://%'\n" +
"    BEGIN\n" +
"      SET @URL = @DefaultDirectory\n" +
"    END\n" +
"    ELSE\n" +
"    BEGIN\n" +
"      INSERT INTO @Directories (ID, DirectoryPath, Mirror, Completed)\n" +
"      SELECT 1, @DefaultDirectory, 0, 0\n" +
"    END\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select directories                                                                         //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @Directory = REPLACE(@Directory, CHAR(10), '')\n" +
"  SET @Directory = REPLACE(@Directory, CHAR(13), '')\n" +
"\n" +
"  WHILE CHARINDEX(@StringDelimiter + ' ', @Directory) > 0 SET @Directory = REPLACE(@Directory, @StringDelimiter + ' ', @StringDelimiter)\n" +
"  WHILE CHARINDEX(' ' + @StringDelimiter, @Directory) > 0 SET @Directory = REPLACE(@Directory, ' ' + @StringDelimiter, @StringDelimiter)\n" +
"\n" +
"  SET @Directory = LTRIM(RTRIM(@Directory));\n" +
"\n" +
"  WITH Directories (StartPosition, EndPosition, Directory) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"          ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Directory, 1), 0), LEN(@Directory) + 1) AS EndPosition,\n" +
"          SUBSTRING(@Directory, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Directory, 1), 0), LEN(@Directory) + 1) - 1) AS Directory\n" +
"  WHERE @Directory IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"          ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Directory, EndPosition + 1), 0), LEN(@Directory) + 1) AS EndPosition,\n" +
"          SUBSTRING(@Directory, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Directory, EndPosition + 1), 0), LEN(@Directory) + 1) - EndPosition - 1) AS Directory\n" +
"  FROM Directories\n" +
"  WHERE EndPosition < LEN(@Directory) + 1\n" +
"  )\n" +
"  INSERT INTO @Directories (ID, DirectoryPath, Mirror, Completed)\n" +
"  SELECT ROW_NUMBER() OVER(ORDER BY StartPosition ASC) AS ID,\n" +
"          Directory,\n" +
"          0,\n" +
"          0\n" +
"  FROM Directories\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  SET @MirrorDirectory = REPLACE(@MirrorDirectory, CHAR(10), '')\n" +
"  SET @MirrorDirectory = REPLACE(@MirrorDirectory, CHAR(13), '')\n" +
"\n" +
"  WHILE CHARINDEX(', ',@MirrorDirectory) > 0 SET @MirrorDirectory = REPLACE(@MirrorDirectory,', ',',')\n" +
"  WHILE CHARINDEX(' ,',@MirrorDirectory) > 0 SET @MirrorDirectory = REPLACE(@MirrorDirectory,' ,',',')\n" +
"\n" +
"  SET @MirrorDirectory = LTRIM(RTRIM(@MirrorDirectory));\n" +
"\n" +
"  WITH Directories (StartPosition, EndPosition, Directory) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @MirrorDirectory, 1), 0), LEN(@MirrorDirectory) + 1) AS EndPosition,\n" +
"         SUBSTRING(@MirrorDirectory, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @MirrorDirectory, 1), 0), LEN(@MirrorDirectory) + 1) - 1) AS Directory\n" +
"  WHERE @MirrorDirectory IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @MirrorDirectory, EndPosition + 1), 0), LEN(@MirrorDirectory) + 1) AS EndPosition,\n" +
"         SUBSTRING(@MirrorDirectory, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @MirrorDirectory, EndPosition + 1), 0), LEN(@MirrorDirectory) + 1) - EndPosition - 1) AS Directory\n" +
"  FROM Directories\n" +
"  WHERE EndPosition < LEN(@MirrorDirectory) + 1\n" +
"  )\n" +
"  INSERT INTO @Directories (ID, DirectoryPath, Mirror, Completed)\n" +
"  SELECT (SELECT COUNT(*) FROM @Directories) + ROW_NUMBER() OVER(ORDER BY StartPosition ASC) AS ID,\n" +
"         Directory,\n" +
"         1,\n" +
"         0\n" +
"  FROM Directories\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check directories                                                                          //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @Directories WHERE Mirror = 0 AND (NOT (DirectoryPath LIKE '_:' OR DirectoryPath LIKE '_:\\%' OR DirectoryPath LIKE '\\\\%\\%' OR (DirectoryPath LIKE '/%/%' AND @HostPlatform = 'Linux') OR DirectoryPath = 'NUL') OR DirectoryPath IS NULL OR LEFT(DirectoryPath,1) = ' ' OR RIGHT(DirectoryPath,1) = ' '))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Directory is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @Directories GROUP BY DirectoryPath HAVING COUNT(*) <> 1)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Directory is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF (SELECT COUNT(*) FROM @Directories WHERE Mirror = 0) <> (SELECT COUNT(*) FROM @Directories WHERE Mirror = 1) AND (SELECT COUNT(*) FROM @Directories WHERE Mirror = 1) > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Directory is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF (@Directory IS NOT NULL AND SERVERPROPERTY('EngineEdition') = 8) OR (@Directory IS NOT NULL AND @BackupSoftware = 'DATA_DOMAIN_BOOST')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Directory is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @Directories WHERE Mirror = 0 AND DirectoryPath = 'NUL') AND EXISTS(SELECT * FROM @Directories WHERE Mirror = 0 AND DirectoryPath <> 'NUL')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Directory is not supported.', 16, 5\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @Directories WHERE Mirror = 0 AND DirectoryPath = 'NUL') AND EXISTS(SELECT * FROM @Directories WHERE Mirror = 1)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Directory is not supported.', 16, 6\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS(SELECT * FROM @Directories WHERE Mirror = 1 AND (NOT (DirectoryPath LIKE '_:' OR DirectoryPath LIKE '_:\\%' OR DirectoryPath LIKE '\\\\%\\%' OR (DirectoryPath LIKE '/%/%' AND @HostPlatform = 'Linux')) OR DirectoryPath IS NULL OR LEFT(DirectoryPath,1) = ' ' OR RIGHT(DirectoryPath,1) = ' '))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorDirectory is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @Directories GROUP BY DirectoryPath HAVING COUNT(*) <> 1)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorDirectory is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF (SELECT COUNT(*) FROM @Directories WHERE Mirror = 0) <> (SELECT COUNT(*) FROM @Directories WHERE Mirror = 1) AND (SELECT COUNT(*) FROM @Directories WHERE Mirror = 1) > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorDirectory is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware IN('SQLBACKUP','SQLSAFE') AND (SELECT COUNT(*) FROM @Directories WHERE Mirror = 1) > 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorDirectory is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF @MirrorDirectory IS NOT NULL AND SERVERPROPERTY('EngineEdition') = 8\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorDirectory is not supported.', 16, 5\n" +
"  END\n" +
"\n" +
"  IF @MirrorDirectory IS NOT NULL AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorDirectory is not supported.', 16, 6\n" +
"  END\n" +
"\n" +
"  IF EXISTS(SELECT * FROM @Directories WHERE Mirror = 0 AND DirectoryPath = 'NUL') AND EXISTS(SELECT * FROM @Directories WHERE Mirror = 1)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorDirectory is not supported.', 16, 7\n" +
"  END\n" +
"\n" +
"  IF (@BackupSoftware IS NULL AND EXISTS(SELECT * FROM @Directories WHERE Mirror = 1) AND SERVERPROPERTY('EngineEdition') <> 3)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorDirectory is not supported. Mirrored backup to disk is only available in Enterprise and Developer Edition.', 16, 8\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF NOT EXISTS (SELECT * FROM @Errors WHERE Severity >= 16)\n" +
"  BEGIN\n" +
"    WHILE (1 = 1)\n" +
"    BEGIN\n" +
"      SELECT TOP 1 @CurrentRootDirectoryID = ID,\n" +
"                   @CurrentRootDirectoryPath = DirectoryPath\n" +
"      FROM @Directories\n" +
"      WHERE Completed = 0\n" +
"      AND DirectoryPath <> 'NUL'\n" +
"      ORDER BY ID ASC\n" +
"\n" +
"      IF @@ROWCOUNT = 0\n" +
"      BEGIN\n" +
"        BREAK\n" +
"      END\n" +
"\n" +
"      INSERT INTO @DirectoryInfo (FileExists, FileIsADirectory, ParentDirectoryExists)\n" +
"      EXECUTE [master].dbo.xp_fileexist @CurrentRootDirectoryPath\n" +
"\n" +
"      IF NOT EXISTS (SELECT * FROM @DirectoryInfo WHERE FileExists = 0 AND FileIsADirectory = 1 AND ParentDirectoryExists = 1)\n" +
"      BEGIN\n" +
"        INSERT INTO @Errors ([Message], Severity, [State])\n" +
"        SELECT 'The directory ' + @CurrentRootDirectoryPath + ' does not exist.', 16, 1\n" +
"      END\n" +
"\n" +
"      UPDATE @Directories\n" +
"      SET Completed = 1\n" +
"      WHERE ID = @CurrentRootDirectoryID\n" +
"\n" +
"      SET @CurrentRootDirectoryID = NULL\n" +
"      SET @CurrentRootDirectoryPath = NULL\n" +
"\n" +
"      DELETE FROM @DirectoryInfo\n" +
"    END\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select URLs                                                                                //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @URL = REPLACE(@URL, CHAR(10), '')\n" +
"  SET @URL = REPLACE(@URL, CHAR(13), '')\n" +
"\n" +
"  WHILE CHARINDEX(@StringDelimiter + ' ', @URL) > 0 SET @URL = REPLACE(@URL, @StringDelimiter + ' ', @StringDelimiter)\n" +
"  WHILE CHARINDEX(' ' + @StringDelimiter, @URL) > 0 SET @URL = REPLACE(@URL, ' ' + @StringDelimiter, @StringDelimiter)\n" +
"\n" +
"  SET @URL = LTRIM(RTRIM(@URL));\n" +
"\n" +
"  WITH URLs (StartPosition, EndPosition, [URL]) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"          ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @URL, 1), 0), LEN(@URL) + 1) AS EndPosition,\n" +
"          SUBSTRING(@URL, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @URL, 1), 0), LEN(@URL) + 1) - 1) AS [URL]\n" +
"  WHERE @URL IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"          ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @URL, EndPosition + 1), 0), LEN(@URL) + 1) AS EndPosition,\n" +
"          SUBSTRING(@URL, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @URL, EndPosition + 1), 0), LEN(@URL) + 1) - EndPosition - 1) AS [URL]\n" +
"  FROM URLs\n" +
"  WHERE EndPosition < LEN(@URL) + 1\n" +
"  )\n" +
"  INSERT INTO @URLs (ID, DirectoryPath, Mirror)\n" +
"  SELECT ROW_NUMBER() OVER(ORDER BY StartPosition ASC) AS ID,\n" +
"          [URL],\n" +
"          0\n" +
"  FROM URLs\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  SET @MirrorURL = REPLACE(@MirrorURL, CHAR(10), '')\n" +
"  SET @MirrorURL = REPLACE(@MirrorURL, CHAR(13), '')\n" +
"\n" +
"  WHILE CHARINDEX(@StringDelimiter + ' ', @MirrorURL) > 0 SET @MirrorURL = REPLACE(@MirrorURL, @StringDelimiter + ' ', @StringDelimiter)\n" +
"  WHILE CHARINDEX(' ' + @StringDelimiter ,@MirrorURL) > 0 SET @MirrorURL = REPLACE(@MirrorURL, ' ' + @StringDelimiter, @StringDelimiter)\n" +
"\n" +
"  SET @MirrorURL = LTRIM(RTRIM(@MirrorURL));\n" +
"\n" +
"  WITH URLs (StartPosition, EndPosition, [URL]) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @MirrorURL, 1), 0), LEN(@MirrorURL) + 1) AS EndPosition,\n" +
"         SUBSTRING(@MirrorURL, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @MirrorURL, 1), 0), LEN(@MirrorURL) + 1) - 1) AS [URL]\n" +
"  WHERE @MirrorURL IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @MirrorURL, EndPosition + 1), 0), LEN(@MirrorURL) + 1) AS EndPosition,\n" +
"         SUBSTRING(@MirrorURL, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @MirrorURL, EndPosition + 1), 0), LEN(@MirrorURL) + 1) - EndPosition - 1) AS [URL]\n" +
"  FROM URLs\n" +
"  WHERE EndPosition < LEN(@MirrorURL) + 1\n" +
"  )\n" +
"  INSERT INTO @URLs (ID, DirectoryPath, Mirror)\n" +
"  SELECT (SELECT COUNT(*) FROM @URLs) + ROW_NUMBER() OVER(ORDER BY StartPosition ASC) AS ID,\n" +
"         [URL],\n" +
"         1\n" +
"  FROM URLs\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check URLs                                                                          //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS(SELECT * FROM @URLs WHERE Mirror = 0 AND DirectoryPath NOT LIKE 'https://%/%')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @URL is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @URLs GROUP BY DirectoryPath HAVING COUNT(*) <> 1)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @URL is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF (SELECT COUNT(*) FROM @URLs WHERE Mirror = 0) <> (SELECT COUNT(*) FROM @URLs WHERE Mirror = 1) AND (SELECT COUNT(*) FROM @URLs WHERE Mirror = 1) > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @URL is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS(SELECT * FROM @URLs WHERE Mirror = 1 AND DirectoryPath NOT LIKE 'https://%/%')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorURL is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @URLs GROUP BY DirectoryPath HAVING COUNT(*) <> 1)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorURL is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF (SELECT COUNT(*) FROM @URLs WHERE Mirror = 0) <> (SELECT COUNT(*) FROM @URLs WHERE Mirror = 1) AND (SELECT COUNT(*) FROM @URLs WHERE Mirror = 1) > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorURL is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Get directory separator                                                                   //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SELECT @DirectorySeparator = CASE\n" +
"  WHEN @URL IS NOT NULL THEN '/'\n" +
"  WHEN @HostPlatform = 'Windows' THEN '\\'\n" +
"  WHEN @HostPlatform = 'Linux' THEN '/'\n" +
"  END\n" +
"\n" +
"  UPDATE @Directories\n" +
"  SET DirectoryPath = LEFT(DirectoryPath,LEN(DirectoryPath) - 1)\n" +
"  WHERE RIGHT(DirectoryPath,1) = @DirectorySeparator\n" +
"\n" +
"  UPDATE @URLs\n" +
"  SET DirectoryPath = LEFT(DirectoryPath,LEN(DirectoryPath) - 1)\n" +
"  WHERE RIGHT(DirectoryPath,1) = @DirectorySeparator\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Get file extension                                                                         //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @FileExtensionFull IS NULL\n" +
"  BEGIN\n" +
"    SELECT @FileExtensionFull = CASE\n" +
"    WHEN @BackupSoftware IS NULL THEN 'bak'\n" +
"    WHEN @BackupSoftware = 'LITESPEED' THEN 'bak'\n" +
"    WHEN @BackupSoftware = 'SQLBACKUP' THEN 'sqb'\n" +
"    WHEN @BackupSoftware = 'SQLSAFE' THEN 'safe'\n" +
"    END\n" +
"  END\n" +
"\n" +
"  IF @FileExtensionDiff IS NULL\n" +
"  BEGIN\n" +
"    SELECT @FileExtensionDiff = CASE\n" +
"    WHEN @BackupSoftware IS NULL THEN 'bak'\n" +
"    WHEN @BackupSoftware = 'LITESPEED' THEN 'bak'\n" +
"    WHEN @BackupSoftware = 'SQLBACKUP' THEN 'sqb'\n" +
"    WHEN @BackupSoftware = 'SQLSAFE' THEN 'safe'\n" +
"    END\n" +
"  END\n" +
"\n" +
"  IF @FileExtensionLog IS NULL\n" +
"  BEGIN\n" +
"    SELECT @FileExtensionLog = CASE\n" +
"    WHEN @BackupSoftware IS NULL THEN 'trn'\n" +
"    WHEN @BackupSoftware = 'LITESPEED' THEN 'trn'\n" +
"    WHEN @BackupSoftware = 'SQLBACKUP' THEN 'sqb'\n" +
"    WHEN @BackupSoftware = 'SQLSAFE' THEN 'safe'\n" +
"    END\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Get default compression                                                                    //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Compress IS NULL\n" +
"  BEGIN\n" +
"    SELECT @Compress = CASE WHEN @BackupSoftware IS NULL AND EXISTS(SELECT * FROM sys.configurations WHERE name = 'backup compression default' AND value_in_use = 1) THEN 'Y'\n" +
"                            WHEN @BackupSoftware IS NULL AND NOT EXISTS(SELECT * FROM sys.configurations WHERE name = 'backup compression default' AND value_in_use = 1) THEN 'N'\n" +
"                            WHEN @BackupSoftware IS NOT NULL AND (@CompressionLevel IS NULL OR @CompressionLevel > 0)  THEN 'Y'\n" +
"                            WHEN @BackupSoftware IS NOT NULL AND @CompressionLevel = 0  THEN 'N' END\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check input parameters                                                                     //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @BackupType NOT IN ('FULL','DIFF','LOG') OR @BackupType IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @BackupType is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF SERVERPROPERTY('EngineEdition') = 8 AND NOT (@BackupType = 'FULL' AND @CopyOnly = 'Y')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'SQL Database Managed Instance only supports COPY_ONLY full backups.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Verify NOT IN ('Y','N') OR @Verify IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Verify is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'SQLSAFE' AND @Encrypt = 'Y' AND @Verify = 'Y'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Verify is not supported. Verify is not supported with encrypted backups with Idera SQL Safe Backup', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @Verify = 'Y' AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Verify is not supported. Verify is not supported with Data Domain Boost', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @Verify = 'Y' AND EXISTS(SELECT * FROM @Directories WHERE DirectoryPath = 'NUL')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Verify is not supported. Verify is not supported when backing up to NUL.', 16, 4\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @CleanupTime < 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CleanupTime is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @CleanupTime IS NOT NULL AND @URL IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CleanupTime is not supported. Cleanup is not supported on Azure Blob Storage.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @CleanupTime IS NOT NULL AND EXISTS(SELECT * FROM @Directories WHERE DirectoryPath = 'NUL')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CleanupTime is not supported. Cleanup is not supported when backing up to NUL.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF @CleanupTime IS NOT NULL AND ((@DirectoryStructure NOT LIKE '%{DatabaseName}%' OR @DirectoryStructure IS NULL) OR (SERVERPROPERTY('IsHadrEnabled') = 1 AND (@AvailabilityGroupDirectoryStructure NOT LIKE '%{DatabaseName}%' OR @AvailabilityGroupDirectoryStructure IS NULL)))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CleanupTime is not supported. Cleanup is not supported if the token {DatabaseName} is not part of the directory.', 16, 5\n" +
"  END\n" +
"\n" +
"  IF @CleanupTime IS NOT NULL AND ((@DirectoryStructure NOT LIKE '%{BackupType}%' OR @DirectoryStructure IS NULL) OR (SERVERPROPERTY('IsHadrEnabled') = 1 AND (@AvailabilityGroupDirectoryStructure NOT LIKE '%{BackupType}%' OR @AvailabilityGroupDirectoryStructure IS NULL)))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CleanupTime is not supported. Cleanup is not supported if the token {BackupType} is not part of the directory.', 16, 6\n" +
"  END\n" +
"\n" +
"  IF @CleanupTime IS NOT NULL AND @CopyOnly = 'Y' AND ((@DirectoryStructure NOT LIKE '%{CopyOnly}%' OR @DirectoryStructure IS NULL) OR (SERVERPROPERTY('IsHadrEnabled') = 1 AND (@AvailabilityGroupDirectoryStructure NOT LIKE '%{CopyOnly}%' OR @AvailabilityGroupDirectoryStructure IS NULL)))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CleanupTime is not supported. Cleanup is not supported if the token {CopyOnly} is not part of the directory.', 16, 7\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @CleanupMode NOT IN('BEFORE_BACKUP','AFTER_BACKUP') OR @CleanupMode IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CleanupMode is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Compress NOT IN ('Y','N') OR @Compress IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Compress is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @Compress = 'Y' AND @BackupSoftware IS NULL AND NOT ((@Version >= 10 AND @Version < 10.5 AND SERVERPROPERTY('EngineEdition') = 3) OR (@Version >= 10.5 AND (SERVERPROPERTY('EngineEdition') IN (3, 8) OR SERVERPROPERTY('EditionID') IN (-1534726760, 284895786))))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Compress is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @Compress = 'N' AND @BackupSoftware IN ('LITESPEED','SQLBACKUP','SQLSAFE') AND (@CompressionLevel IS NULL OR @CompressionLevel >= 1)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Compress is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @Compress = 'Y' AND @BackupSoftware IN ('LITESPEED','SQLBACKUP','SQLSAFE') AND @CompressionLevel = 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Compress is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @CopyOnly NOT IN ('Y','N') OR @CopyOnly IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CopyOnly is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @ChangeBackupType NOT IN ('Y','N') OR @ChangeBackupType IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ChangeBackupType is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @BackupSoftware NOT IN ('LITESPEED','SQLBACKUP','SQLSAFE','DATA_DOMAIN_BOOST')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @BackupSoftware is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware IS NOT NULL AND @HostPlatform = 'Linux'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @BackupSoftware is not supported. Only native backups are supported on Linux', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'LITESPEED' AND NOT EXISTS (SELECT * FROM [master].sys.objects WHERE [type] = 'X' AND [name] = 'xp_backup_database')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'LiteSpeed for SQL Server is not installed. Download https://www.quest.com/products/litespeed-for-sql-server/.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'SQLBACKUP' AND NOT EXISTS (SELECT * FROM [master].sys.objects WHERE [type] = 'X' AND [name] = 'sqlbackup')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'Red Gate SQL Backup Pro is not installed. Download https://www.red-gate.com/products/dba/sql-backup/.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'SQLSAFE' AND NOT EXISTS (SELECT * FROM [master].sys.objects WHERE [type] = 'X' AND [name] = 'xp_ss_backup')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'Idera SQL Safe Backup is not installed. Download https://www.idera.com/productssolutions/sqlserver/sqlsafebackup.', 16, 5\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'DATA_DOMAIN_BOOST' AND NOT EXISTS (SELECT * FROM [master].sys.objects WHERE [type] = 'PC' AND [name] = 'emc_run_backup')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'EMC Data Domain Boost is not installed. Download https://www.emc.com/en-us/data-protection/data-domain.htm.', 16, 6\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @CheckSum NOT IN ('Y','N') OR @CheckSum IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CheckSum is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @BlockSize NOT IN (512,1024,2048,4096,8192,16384,32768,65536)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @BlockSize is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @BlockSize IS NOT NULL AND @BackupSoftware = 'SQLBACKUP'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @BlockSize is not supported. This parameter is not supported with Redgate SQL Backup Pro', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @BlockSize IS NOT NULL AND @BackupSoftware = 'SQLSAFE'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @BlockSize is not supported. This parameter is not supported with Idera SQL Safe', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @BlockSize IS NOT NULL AND @URL IS NOT NULL AND @Credential IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @BlockSize is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF @BlockSize IS NOT NULL AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @BlockSize is not supported. This parameter is not supported with Data Domain Boost', 16, 5\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @BufferCount <= 0 OR @BufferCount > 2147483647\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @BufferCount is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @BufferCount IS NOT NULL AND @BackupSoftware = 'SQLBACKUP'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @BufferCount is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @BufferCount IS NOT NULL AND @BackupSoftware = 'SQLSAFE'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @BufferCount is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @MaxTransferSize < 65536 OR @MaxTransferSize > 4194304\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MaxTransferSize is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @MaxTransferSize > 1048576 AND @BackupSoftware = 'SQLBACKUP'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MaxTransferSize is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @MaxTransferSize IS NOT NULL AND @BackupSoftware = 'SQLSAFE'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MaxTransferSize is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @MaxTransferSize IS NOT NULL AND @URL IS NOT NULL AND @Credential IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MaxTransferSize is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF @MaxTransferSize IS NOT NULL AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MaxTransferSize is not supported.', 16, 5\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @NumberOfFiles < 1 OR @NumberOfFiles > 64\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NumberOfFiles is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @NumberOfFiles > 32 AND @BackupSoftware = 'SQLBACKUP'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NumberOfFiles is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @NumberOfFiles < (SELECT COUNT(*) FROM @Directories WHERE Mirror = 0)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NumberOfFiles is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @NumberOfFiles % (SELECT NULLIF(COUNT(*),0) FROM @Directories WHERE Mirror = 0) > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NumberOfFiles is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF @URL IS NOT NULL AND @Credential IS NOT NULL AND @NumberOfFiles <> 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NumberOfFiles is not supported.', 16, 5\n" +
"  END\n" +
"\n" +
"  IF @NumberOfFiles > 1 AND @BackupSoftware IN('SQLBACKUP','SQLSAFE') AND EXISTS(SELECT * FROM @Directories WHERE Mirror = 1)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NumberOfFiles is not supported.', 16, 6\n" +
"  END\n" +
"\n" +
"  IF @NumberOfFiles > 32 AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NumberOfFiles is not supported.', 16, 7\n" +
"  END\n" +
"\n" +
"  IF @NumberOfFiles < (SELECT COUNT(*) FROM @URLs WHERE Mirror = 0)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NumberOfFiles is not supported.', 16, 8\n" +
"  END\n" +
"\n" +
"  IF @NumberOfFiles % (SELECT NULLIF(COUNT(*),0) FROM @URLs WHERE Mirror = 0) > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NumberOfFiles is not supported.', 16, 9\n" +
"  END\n" +
"\n" +
"    ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @MinBackupSizeForMultipleFiles <= 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MinBackupSizeForMultipleFiles is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @MinBackupSizeForMultipleFiles IS NOT NULL AND @NumberOfFiles IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MinBackupSizeForMultipleFiles is not supported. This parameter can only be used together with @NumberOfFiles.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @MinBackupSizeForMultipleFiles IS NOT NULL AND @BackupType = 'DIFF' AND NOT EXISTS(SELECT * FROM sys.all_columns WHERE object_id = OBJECT_ID('sys.dm_db_file_space_usage') AND name = 'modified_extent_page_count')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MinBackupSizeForMultipleFiles is not supported. The column sys.dm_db_file_space_usage.modified_extent_page_count is not available in this version of SQL Server.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @MinBackupSizeForMultipleFiles IS NOT NULL AND @BackupType = 'LOG' AND NOT EXISTS(SELECT * FROM sys.all_columns WHERE object_id = OBJECT_ID('sys.dm_db_log_stats') AND name = 'log_since_last_log_backup_mb')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MinBackupSizeForMultipleFiles is not supported. The column sys.dm_db_log_stats.log_since_last_log_backup_mb is not available in this version of SQL Server.', 16, 4\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @MaxFileSize <= 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MaxFileSize is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @MaxFileSize IS NOT NULL AND @NumberOfFiles IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The parameters @MaxFileSize and @NumberOfFiles cannot be used together.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @MaxFileSize IS NOT NULL AND @BackupType = 'DIFF' AND NOT EXISTS(SELECT * FROM sys.all_columns WHERE object_id = OBJECT_ID('sys.dm_db_file_space_usage') AND name = 'modified_extent_page_count')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MaxFileSize is not supported. The column sys.dm_db_file_space_usage.modified_extent_page_count is not available in this version of SQL Server.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @MaxFileSize IS NOT NULL AND @BackupType = 'LOG' AND NOT EXISTS(SELECT * FROM sys.all_columns WHERE object_id = OBJECT_ID('sys.dm_db_log_stats') AND name = 'log_since_last_log_backup_mb')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MaxFileSize is not supported. The column sys.dm_db_log_stats.log_since_last_log_backup_mb is not available in this version of SQL Server.', 16, 4\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF (@BackupSoftware IS NULL AND @CompressionLevel IS NOT NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CompressionLevel is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'LITESPEED' AND (@CompressionLevel < 0  OR @CompressionLevel > 8)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CompressionLevel is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'SQLBACKUP' AND (@CompressionLevel < 0 OR @CompressionLevel > 4)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CompressionLevel is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'SQLSAFE' AND (@CompressionLevel < 1 OR @CompressionLevel > 4)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CompressionLevel is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF @CompressionLevel IS NOT NULL AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CompressionLevel is not supported.', 16, 5\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF LEN(@Description) > 255\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Description is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'LITESPEED' AND LEN(@Description) > 128\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Description is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'DATA_DOMAIN_BOOST' AND LEN(@Description) > 254\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Description is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Threads IS NOT NULL AND (@BackupSoftware NOT IN('LITESPEED','SQLBACKUP','SQLSAFE') OR @BackupSoftware IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Threads is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'LITESPEED' AND (@Threads < 1 OR @Threads > 32)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Threads is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'SQLBACKUP' AND (@Threads < 2 OR @Threads > 32)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Threads is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'SQLSAFE' AND (@Threads < 1 OR @Threads > 64)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Threads is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Throttle < 1 OR @Throttle > 100\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Throttle is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @Throttle IS NOT NULL AND (@BackupSoftware NOT IN('LITESPEED') OR @BackupSoftware IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Throttle is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Encrypt NOT IN('Y','N') OR @Encrypt IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Encrypt is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @Encrypt = 'Y' AND @BackupSoftware IS NULL AND NOT (@Version >= 12 AND (SERVERPROPERTY('EngineEdition') = 3) OR SERVERPROPERTY('EditionID') IN(-1534726760, 284895786))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Encrypt is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @Encrypt = 'Y' AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Encrypt is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @BackupSoftware IS NULL AND @Encrypt = 'Y' AND (@EncryptionAlgorithm NOT IN('AES_128','AES_192','AES_256','TRIPLE_DES_3KEY') OR @EncryptionAlgorithm IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @EncryptionAlgorithm is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'LITESPEED' AND @Encrypt = 'Y' AND (@EncryptionAlgorithm NOT IN('RC2_40','RC2_56','RC2_112','RC2_128','TRIPLE_DES_3KEY','RC4_128','AES_128','AES_192','AES_256') OR @EncryptionAlgorithm IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @EncryptionAlgorithm is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'SQLBACKUP' AND @Encrypt = 'Y' AND (@EncryptionAlgorithm NOT IN('AES_128','AES_256') OR @EncryptionAlgorithm IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @EncryptionAlgorithm is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware = 'SQLSAFE' AND @Encrypt = 'Y' AND (@EncryptionAlgorithm NOT IN('AES_128','AES_256') OR @EncryptionAlgorithm IS NULL)\n" +
"  OR (@EncryptionAlgorithm IS NOT NULL AND @BackupSoftware = 'DATA_DOMAIN_BOOST')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @EncryptionAlgorithm is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF @EncryptionAlgorithm IS NOT NULL AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @EncryptionAlgorithm is not supported.', 16, 5\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF (NOT (@BackupSoftware IS NULL AND @Encrypt = 'Y') AND @ServerCertificate IS NOT NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ServerCertificate is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware IS NULL AND @Encrypt = 'Y' AND @ServerCertificate IS NULL AND @ServerAsymmetricKey IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ServerCertificate is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware IS NULL AND @Encrypt = 'Y' AND @ServerCertificate IS NOT NULL AND @ServerAsymmetricKey IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ServerCertificate is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @ServerCertificate IS NOT NULL AND NOT EXISTS(SELECT * FROM master.sys.certificates WHERE name = @ServerCertificate)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ServerCertificate is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF NOT (@BackupSoftware IS NULL AND @Encrypt = 'Y') AND @ServerAsymmetricKey IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ServerAsymmetricKey is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware IS NULL AND @Encrypt = 'Y' AND @ServerAsymmetricKey IS NULL AND @ServerCertificate IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ServerAsymmetricKey is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @BackupSoftware IS NULL AND @Encrypt = 'Y' AND @ServerAsymmetricKey IS NOT NULL AND @ServerCertificate IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ServerAsymmetricKey is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @ServerAsymmetricKey IS NOT NULL AND NOT EXISTS(SELECT * FROM master.sys.asymmetric_keys WHERE name = @ServerAsymmetricKey)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ServerAsymmetricKey is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @EncryptionKey IS NOT NULL AND @BackupSoftware IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @EncryptionKey is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @EncryptionKey IS NOT NULL AND @Encrypt = 'N'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @EncryptionKey is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @EncryptionKey IS NULL AND @Encrypt = 'Y' AND @BackupSoftware IN('LITESPEED','SQLBACKUP','SQLSAFE')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @EncryptionKey is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @EncryptionKey IS NOT NULL AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @EncryptionKey is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @ReadWriteFileGroups NOT IN('Y','N') OR @ReadWriteFileGroups IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ReadWriteFileGroups is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @ReadWriteFileGroups = 'Y' AND @BackupType = 'LOG'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ReadWriteFileGroups is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @OverrideBackupPreference NOT IN('Y','N') OR @OverrideBackupPreference IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @OverrideBackupPreference is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @NoRecovery NOT IN('Y','N') OR @NoRecovery IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NoRecovery is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @NoRecovery = 'Y' AND @BackupType <> 'LOG'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NoRecovery is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @NoRecovery = 'Y' AND @BackupSoftware = 'SQLSAFE'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NoRecovery is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @URL IS NOT NULL AND @Directory IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @URL is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @URL IS NOT NULL AND @MirrorDirectory IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @URL is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @URL IS NOT NULL AND @Version < 11.03339\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @URL is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @URL IS NOT NULL AND @BackupSoftware IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @URL is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Credential IS NULL AND @URL IS NOT NULL AND NOT (@Version >= 13 OR SERVERPROPERTY('EngineEdition') = 8)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Credential is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @Credential IS NOT NULL AND @URL IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Credential is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @URL IS NOT NULL AND @Credential IS NULL AND NOT EXISTS(SELECT * FROM sys.credentials WHERE UPPER(credential_identity) = 'SHARED ACCESS SIGNATURE')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Credential is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @Credential IS NOT NULL AND NOT EXISTS(SELECT * FROM sys.credentials WHERE name = @Credential)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Credential is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @MirrorCleanupTime < 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorCleanupTime is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @MirrorCleanupTime IS NOT NULL AND @MirrorDirectory IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorCleanupTime is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @MirrorCleanupMode NOT IN('BEFORE_BACKUP','AFTER_BACKUP') OR @MirrorCleanupMode IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorCleanupMode is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @MirrorURL IS NOT NULL AND @Directory IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorURL is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @MirrorURL IS NOT NULL AND @MirrorDirectory IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorURL is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @MirrorURL IS NOT NULL AND @Version < 11.03339\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorURL is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @MirrorURL IS NOT NULL AND @BackupSoftware IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorURL is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF @MirrorURL IS NOT NULL AND @URL IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MirrorURL is not supported.', 16, 5\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Updateability NOT IN('READ_ONLY','READ_WRITE','ALL') OR @Updateability IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Updateability is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @AdaptiveCompression NOT IN('SIZE','SPEED')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AdaptiveCompression is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @AdaptiveCompression IS NOT NULL AND (@BackupSoftware NOT IN('LITESPEED') OR @BackupSoftware IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AdaptiveCompression is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @ModificationLevel IS NOT NULL AND NOT EXISTS(SELECT * FROM sys.all_columns WHERE object_id = OBJECT_ID('sys.dm_db_file_space_usage') AND name = 'modified_extent_page_count')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ModificationLevel is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @ModificationLevel IS NOT NULL AND @ChangeBackupType = 'N'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ModificationLevel is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @ModificationLevel IS NOT NULL AND @BackupType <> 'DIFF'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ModificationLevel is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @LogSizeSinceLastLogBackup IS NOT NULL AND NOT EXISTS(SELECT * FROM sys.all_columns WHERE object_id = OBJECT_ID('sys.dm_db_log_stats') AND name = 'log_since_last_log_backup_mb')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @LogSizeSinceLastLogBackup is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @LogSizeSinceLastLogBackup IS NOT NULL AND @BackupType <> 'LOG'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @LogSizeSinceLastLogBackup is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @TimeSinceLastLogBackup IS NOT NULL AND NOT EXISTS(SELECT * FROM sys.all_columns WHERE object_id = OBJECT_ID('sys.dm_db_log_stats') AND name = 'log_backup_time')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @TimeSinceLastLogBackup is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @TimeSinceLastLogBackup IS NOT NULL AND @BackupType <> 'LOG'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @TimeSinceLastLogBackup is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF (@TimeSinceLastLogBackup IS NOT NULL AND @LogSizeSinceLastLogBackup IS NULL) OR (@TimeSinceLastLogBackup IS NULL AND @LogSizeSinceLastLogBackup IS NOT NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The parameters @TimeSinceLastLogBackup and @LogSizeSinceLastLogBackup can only be used together.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DataDomainBoostHost IS NOT NULL AND (@BackupSoftware <> 'DATA_DOMAIN_BOOST' OR @BackupSoftware IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DataDomainBoostHost is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DataDomainBoostHost IS NULL AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DataDomainBoostHost is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DataDomainBoostUser IS NOT NULL AND (@BackupSoftware <> 'DATA_DOMAIN_BOOST' OR @BackupSoftware IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DataDomainBoostUser is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DataDomainBoostUser IS NULL AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DataDomainBoostUser is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DataDomainBoostDevicePath IS NOT NULL AND (@BackupSoftware <> 'DATA_DOMAIN_BOOST' OR @BackupSoftware IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DataDomainBoostDevicePath is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DataDomainBoostDevicePath IS NULL AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DataDomainBoostDevicePath is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DataDomainBoostLockboxPath IS NOT NULL AND (@BackupSoftware <> 'DATA_DOMAIN_BOOST' OR @BackupSoftware IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DataDomainBoostLockboxPath is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DirectoryStructure = ''\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DirectoryStructure is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @AvailabilityGroupDirectoryStructure = ''\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AvailabilityGroupDirectoryStructure is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @FileName IS NULL OR @FileName = ''\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FileName is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @FileName NOT LIKE '%.{FileExtension}'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FileName is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF (@NumberOfFiles > 1 AND @FileName NOT LIKE '%{FileNumber}%')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FileName is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @FileName LIKE '%{DirectorySeparator}%'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FileName is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF @FileName LIKE '%/%'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FileName is not supported.', 16, 5\n" +
"  END\n" +
"\n" +
"  IF @FileName LIKE '%\\%'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FileName is not supported.', 16, 6\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF (SERVERPROPERTY('IsHadrEnabled') = 1 AND @AvailabilityGroupFileName IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AvailabilityGroupFileName is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @AvailabilityGroupFileName = ''\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AvailabilityGroupFileName is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @AvailabilityGroupFileName NOT LIKE '%.{FileExtension}'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AvailabilityGroupFileName is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF (@NumberOfFiles > 1 AND @AvailabilityGroupFileName NOT LIKE '%{FileNumber}%')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AvailabilityGroupFileName is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF @AvailabilityGroupFileName LIKE '%{DirectorySeparator}%'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AvailabilityGroupFileName is not supported.', 16, 5\n" +
"  END\n" +
"\n" +
"  IF @AvailabilityGroupFileName LIKE '%/%'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AvailabilityGroupFileName is not supported.', 16, 6\n" +
"  END\n" +
"\n" +
"  IF @AvailabilityGroupFileName LIKE '%\\%'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AvailabilityGroupFileName is not supported.', 16, 7\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS (SELECT * FROM (SELECT REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(@DirectoryStructure,'{DirectorySeparator}',''),'{ServerName}',''),'{InstanceName}',''),'{ServiceName}',''),'{ClusterName}',''),'{AvailabilityGroupName}',''),'{DatabaseName}',''),'{BackupType}',''),'{Partial}',''),'{CopyOnly}',''),'{Description}',''),'{Year}',''),'{Month}',''),'{Day}',''),'{Week}',''),'{Hour}',''),'{Minute}',''),'{Second}',''),'{Millisecond}',''),'{Microsecond}',''),'{MajorVersion}',''),'{MinorVersion}','') AS DirectoryStructure) Temp WHERE DirectoryStructure LIKE '%{%' OR DirectoryStructure LIKE '%}%')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The parameter @DirectoryStructure contains one or more tokens that are not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS (SELECT * FROM (SELECT REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(@AvailabilityGroupDirectoryStructure,'{DirectorySeparator}',''),'{ServerName}',''),'{InstanceName}',''),'{ServiceName}',''),'{ClusterName}',''),'{AvailabilityGroupName}',''),'{DatabaseName}',''),'{BackupType}',''),'{Partial}',''),'{CopyOnly}',''),'{Description}',''),'{Year}',''),'{Month}',''),'{Day}',''),'{Week}',''),'{Hour}',''),'{Minute}',''),'{Second}',''),'{Millisecond}',''),'{Microsecond}',''),'{MajorVersion}',''),'{MinorVersion}','') AS AvailabilityGroupDirectoryStructure) Temp WHERE AvailabilityGroupDirectoryStructure LIKE '%{%' OR AvailabilityGroupDirectoryStructure LIKE '%}%')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The parameter @AvailabilityGroupDirectoryStructure contains one or more tokens that are not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS (SELECT * FROM (SELECT REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(@FileName,'{DirectorySeparator}',''),'{ServerName}',''),'{InstanceName}',''),'{ServiceName}',''),'{ClusterName}',''),'{AvailabilityGroupName}',''),'{DatabaseName}',''),'{BackupType}',''),'{Partial}',''),'{CopyOnly}',''),'{Description}',''),'{Year}',''),'{Month}',''),'{Day}',''),'{Week}',''),'{Hour}',''),'{Minute}',''),'{Second}',''),'{Millisecond}',''),'{Microsecond}',''),'{FileNumber}',''),'{NumberOfFiles}',''),'{FileExtension}',''),'{MajorVersion}',''),'{MinorVersion}','') AS [FileName]) Temp WHERE [FileName] LIKE '%{%' OR [FileName] LIKE '%}%')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The parameter @FileName contains one or more tokens that are not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS (SELECT * FROM (SELECT REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(@AvailabilityGroupFileName,'{DirectorySeparator}',''),'{ServerName}',''),'{InstanceName}',''),'{ServiceName}',''),'{ClusterName}',''),'{AvailabilityGroupName}',''),'{DatabaseName}',''),'{BackupType}',''),'{Partial}',''),'{CopyOnly}',''),'{Description}',''),'{Year}',''),'{Month}',''),'{Day}',''),'{Week}',''),'{Hour}',''),'{Minute}',''),'{Second}',''),'{Millisecond}',''),'{Microsecond}',''),'{FileNumber}',''),'{NumberOfFiles}',''),'{FileExtension}',''),'{MajorVersion}',''),'{MinorVersion}','') AS AvailabilityGroupFileName) Temp WHERE AvailabilityGroupFileName LIKE '%{%' OR AvailabilityGroupFileName LIKE '%}%')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The parameter @AvailabilityGroupFileName contains one or more tokens that are not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @FileExtensionFull LIKE '%.%'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FileExtensionFull is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n";
    }
    
    public String part5(){
        return "\n" +
"  IF @FileExtensionDiff LIKE '%.%'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FileExtensionDiff is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @FileExtensionLog LIKE '%.%'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FileExtensionLog is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Init NOT IN('Y','N') OR @Init IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Init is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @Init = 'Y' AND @BackupType = 'LOG'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Init is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @Init = 'Y' AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Init is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Format NOT IN('Y','N') OR @Format IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Format is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @Format = 'Y' AND @BackupType = 'LOG'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Format is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @Format = 'Y' AND @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Format is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @ObjectLevelRecoveryMap NOT IN('Y','N') OR @ObjectLevelRecoveryMap IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ObjectLevelRecovery is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @ObjectLevelRecoveryMap = 'Y' AND @BackupSoftware IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ObjectLevelRecovery is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @ObjectLevelRecoveryMap = 'Y' AND @BackupSoftware <> 'LITESPEED'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ObjectLevelRecovery is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @ObjectLevelRecoveryMap = 'Y' AND @BackupType = 'LOG'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ObjectLevelRecovery is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @ExcludeLogShippedFromLogBackup NOT IN('Y','N') OR @ExcludeLogShippedFromLogBackup IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ExcludeLogShippedFromLogBackup is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @StringDelimiter IS NULL OR LEN(@StringDelimiter) > 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @StringDelimiter is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabaseOrder NOT IN('DATABASE_NAME_ASC','DATABASE_NAME_DESC','DATABASE_SIZE_ASC','DATABASE_SIZE_DESC','LOG_SIZE_SINCE_LAST_LOG_BACKUP_ASC','LOG_SIZE_SINCE_LAST_LOG_BACKUP_DESC')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabaseOrder is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IN('LOG_SIZE_SINCE_LAST_LOG_BACKUP_ASC','LOG_SIZE_SINCE_LAST_LOG_BACKUP_DESC') AND NOT EXISTS(SELECT * FROM sys.all_columns WHERE object_id = OBJECT_ID('sys.dm_db_log_stats') AND name = 'log_since_last_log_backup_mb')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabaseOrder is not supported. The column sys.dm_db_log_stats.log_since_last_log_backup_mb is not available in this version of SQL Server.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IS NOT NULL AND SERVERPROPERTY('EngineEdition') = 5\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabaseOrder is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabasesInParallel NOT IN('Y','N') OR @DatabasesInParallel IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabasesInParallel is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DatabasesInParallel = 'Y' AND SERVERPROPERTY('EngineEdition') = 5\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabasesInParallel is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @LogToTable NOT IN('Y','N') OR @LogToTable IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @LogToTable is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Execute NOT IN('Y','N') OR @Execute IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Execute is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS(SELECT * FROM @Errors)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The documentation is available at https://ola.hallengren.com/sql-server-backup.html.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check that selected databases and availability groups exist                                //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + ', '\n" +
"  FROM @SelectedDatabases\n" +
"  WHERE DatabaseName NOT LIKE '%[%]%'\n" +
"  AND DatabaseName NOT IN (SELECT DatabaseName FROM @tmpDatabases)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The following databases in the @Databases parameter do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 10, 1\n" +
"  END\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(AvailabilityGroupName) + ', '\n" +
"  FROM @SelectedAvailabilityGroups\n" +
"  WHERE AvailabilityGroupName NOT LIKE '%[%]%'\n" +
"  AND AvailabilityGroupName NOT IN (SELECT AvailabilityGroupName FROM @tmpAvailabilityGroups)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The following availability groups do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 10, 1\n" +
"  END\n";
    }
    
    public String part6(){
        return "  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check @@SERVERNAME                                                                         //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @@SERVERNAME <> CAST(SERVERPROPERTY('ServerName') AS nvarchar(max)) AND SERVERPROPERTY('IsHadrEnabled') = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The @@SERVERNAME does not match SERVERPROPERTY(''ServerName''). See ' + CASE WHEN SERVERPROPERTY('IsClustered') = 0 THEN 'https://docs.microsoft.com/en-us/sql/database-engine/install-windows/rename-a-computer-that-hosts-a-stand-alone-instance-of-sql-server' WHEN SERVERPROPERTY('IsClustered') = 1 THEN 'https://docs.microsoft.com/en-us/sql/sql-server/failover-clusters/install/rename-a-sql-server-failover-cluster-instance' END + '.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Raise errors                                                                               //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  DECLARE ErrorCursor CURSOR FAST_FORWARD FOR SELECT [Message], Severity, [State] FROM @Errors ORDER BY [ID] ASC\n" +
"\n" +
"  OPEN ErrorCursor\n" +
"\n" +
"  FETCH ErrorCursor INTO @CurrentMessage, @CurrentSeverity, @CurrentState\n" +
"\n" +
"  WHILE @@FETCH_STATUS = 0\n" +
"  BEGIN\n" +
"    RAISERROR('%s', @CurrentSeverity, @CurrentState, @CurrentMessage) WITH NOWAIT\n" +
"    RAISERROR(@EmptyLine, 10, 1) WITH NOWAIT\n" +
"\n" +
"    FETCH NEXT FROM ErrorCursor INTO @CurrentMessage, @CurrentSeverity, @CurrentState\n" +
"  END\n" +
"\n" +
"  CLOSE ErrorCursor\n" +
"\n" +
"  DEALLOCATE ErrorCursor\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @Errors WHERE Severity >= 16)\n" +
"  BEGIN\n" +
"    SET @ReturnCode = 50000\n" +
"    GOTO Logging\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check Availability Group cluster name                                                      //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Version >= 11 AND SERVERPROPERTY('IsHadrEnabled') = 1\n" +
"  BEGIN\n" +
"    SELECT @Cluster = NULLIF(cluster_name,'')\n" +
"    FROM sys.dm_hadr_cluster\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Update database order                                                                      //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabaseOrder IN('DATABASE_SIZE_ASC','DATABASE_SIZE_DESC')\n" +
"  BEGIN\n" +
"    UPDATE tmpDatabases\n" +
"    SET DatabaseSize = (SELECT SUM(CAST(size AS bigint)) FROM sys.master_files WHERE [type] = 0 AND database_id = DB_ID(tmpDatabases.DatabaseName))\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IN('LOG_SIZE_SINCE_LAST_LOG_BACKUP_ASC','LOG_SIZE_SINCE_LAST_LOG_BACKUP_DESC')\n" +
"  BEGIN\n" +
"    UPDATE tmpDatabases\n" +
"    SET LogSizeSinceLastLogBackup = (SELECT log_since_last_log_backup_mb FROM sys.dm_db_log_stats(DB_ID(tmpDatabases.DatabaseName)))\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IS NULL\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY StartPosition ASC, DatabaseName ASC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_NAME_ASC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY DatabaseName ASC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_NAME_DESC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY DatabaseName DESC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_SIZE_ASC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY DatabaseSize ASC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_SIZE_DESC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY DatabaseSize DESC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'LOG_SIZE_SINCE_LAST_LOG_BACKUP_ASC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY LogSizeSinceLastLogBackup ASC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'LOG_SIZE_SINCE_LAST_LOG_BACKUP_DESC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY LogSizeSinceLastLogBackup DESC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Update the queue                                                                           //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabasesInParallel = 'Y'\n" +
"  BEGIN\n" +
"\n" +
"    BEGIN TRY\n" +
"\n" +
"      SELECT @QueueID = QueueID\n" +
"      FROM dbo.[Queue]\n" +
"      WHERE SchemaName = @SchemaName\n" +
"      AND ObjectName = @ObjectName\n" +
"      AND [Parameters] = @Parameters\n" +
"\n" +
"      IF @QueueID IS NULL\n" +
"      BEGIN\n" +
"        BEGIN TRANSACTION\n" +
"\n" +
"        SELECT @QueueID = QueueID\n" +
"        FROM dbo.[Queue] WITH (UPDLOCK, HOLDLOCK)\n" +
"        WHERE SchemaName = @SchemaName\n" +
"        AND ObjectName = @ObjectName\n" +
"        AND [Parameters] = @Parameters\n" +
"\n" +
"        IF @QueueID IS NULL\n" +
"        BEGIN\n" +
"          INSERT INTO dbo.[Queue] (SchemaName, ObjectName, [Parameters])\n" +
"          SELECT @SchemaName, @ObjectName, @Parameters\n" +
"\n" +
"          SET @QueueID = SCOPE_IDENTITY()\n" +
"        END\n" +
"\n" +
"        COMMIT TRANSACTION\n" +
"      END\n" +
"\n" +
"      BEGIN TRANSACTION\n" +
"\n" +
"      UPDATE [Queue]\n" +
"      SET QueueStartTime = SYSDATETIME(),\n" +
"          SessionID = @@SPID,\n" +
"          RequestID = (SELECT request_id FROM sys.dm_exec_requests WHERE session_id = @@SPID),\n" +
"          RequestStartTime = (SELECT start_time FROM sys.dm_exec_requests WHERE session_id = @@SPID)\n" +
"      FROM dbo.[Queue] [Queue]\n" +
"      WHERE QueueID = @QueueID\n" +
"      AND NOT EXISTS (SELECT *\n" +
"                      FROM sys.dm_exec_requests\n" +
"                      WHERE session_id = [Queue].SessionID\n" +
"                      AND request_id = [Queue].RequestID\n" +
"                      AND start_time = [Queue].RequestStartTime)\n" +
"      AND NOT EXISTS (SELECT *\n" +
"                      FROM dbo.QueueDatabase QueueDatabase\n" +
"                      INNER JOIN sys.dm_exec_requests ON QueueDatabase.SessionID = session_id AND QueueDatabase.RequestID = request_id AND QueueDatabase.RequestStartTime = start_time\n" +
"                      WHERE QueueDatabase.QueueID = @QueueID)\n" +
"\n" +
"      IF @@ROWCOUNT = 1\n" +
"      BEGIN\n" +
"        INSERT INTO dbo.QueueDatabase (QueueID, DatabaseName)\n" +
"        SELECT @QueueID AS QueueID,\n" +
"               DatabaseName\n" +
"        FROM @tmpDatabases tmpDatabases\n" +
"        WHERE Selected = 1\n" +
"        AND NOT EXISTS (SELECT * FROM dbo.QueueDatabase WHERE DatabaseName = tmpDatabases.DatabaseName AND QueueID = @QueueID)\n" +
"\n" +
"        DELETE QueueDatabase\n" +
"        FROM dbo.QueueDatabase QueueDatabase\n" +
"        WHERE QueueID = @QueueID\n" +
"        AND NOT EXISTS (SELECT * FROM @tmpDatabases tmpDatabases WHERE DatabaseName = QueueDatabase.DatabaseName AND Selected = 1)\n" +
"\n" +
"        UPDATE QueueDatabase\n" +
"        SET DatabaseOrder = tmpDatabases.[Order]\n" +
"        FROM dbo.QueueDatabase QueueDatabase\n" +
"        INNER JOIN @tmpDatabases tmpDatabases ON QueueDatabase.DatabaseName = tmpDatabases.DatabaseName\n" +
"        WHERE QueueID = @QueueID\n" +
"      END\n" +
"\n" +
"      COMMIT TRANSACTION\n" +
"\n" +
"      SELECT @QueueStartTime = QueueStartTime\n" +
"      FROM dbo.[Queue]\n" +
"      WHERE QueueID = @QueueID\n" +
"\n" +
"    END TRY\n" +
"\n" +
"    BEGIN CATCH\n" +
"      IF XACT_STATE() <> 0\n" +
"      BEGIN\n" +
"        ROLLBACK TRANSACTION\n" +
"      END\n" +
"      SET @ErrorMessage = 'Msg ' + CAST(ERROR_NUMBER() AS nvarchar) + ', ' + ISNULL(ERROR_MESSAGE(),'')\n" +
"      RAISERROR('%s',16,1,@ErrorMessage) WITH NOWAIT\n" +
"      RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"      SET @ReturnCode = ERROR_NUMBER()\n" +
"      GOTO Logging\n" +
"    END CATCH\n" +
"\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Execute backup commands                                                                    //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  WHILE (1 = 1)\n" +
"  BEGIN\n" +
"\n" +
"    IF @DatabasesInParallel = 'Y'\n" +
"    BEGIN\n" +
"      UPDATE QueueDatabase\n" +
"      SET DatabaseStartTime = NULL,\n" +
"          SessionID = NULL,\n" +
"          RequestID = NULL,\n" +
"          RequestStartTime = NULL\n" +
"      FROM dbo.QueueDatabase QueueDatabase\n" +
"      WHERE QueueID = @QueueID\n" +
"      AND DatabaseStartTime IS NOT NULL\n" +
"      AND DatabaseEndTime IS NULL\n" +
"      AND NOT EXISTS (SELECT * FROM sys.dm_exec_requests WHERE session_id = QueueDatabase.SessionID AND request_id = QueueDatabase.RequestID AND start_time = QueueDatabase.RequestStartTime)\n" +
"\n" +
"      UPDATE QueueDatabase\n" +
"      SET DatabaseStartTime = SYSDATETIME(),\n" +
"          DatabaseEndTime = NULL,\n" +
"          SessionID = @@SPID,\n" +
"          RequestID = (SELECT request_id FROM sys.dm_exec_requests WHERE session_id = @@SPID),\n" +
"          RequestStartTime = (SELECT start_time FROM sys.dm_exec_requests WHERE session_id = @@SPID),\n" +
"          @CurrentDatabaseName = DatabaseName,\n" +
"          @CurrentDatabaseNameFS = (SELECT DatabaseNameFS FROM @tmpDatabases WHERE DatabaseName = QueueDatabase.DatabaseName)\n" +
"      FROM (SELECT TOP 1 DatabaseStartTime,\n" +
"                         DatabaseEndTime,\n" +
"                         SessionID,\n" +
"                         RequestID,\n" +
"                         RequestStartTime,\n" +
"                         DatabaseName\n" +
"            FROM dbo.QueueDatabase\n" +
"            WHERE QueueID = @QueueID\n" +
"            AND (DatabaseStartTime < @QueueStartTime OR DatabaseStartTime IS NULL)\n" +
"            AND NOT (DatabaseStartTime IS NOT NULL AND DatabaseEndTime IS NULL)\n" +
"            ORDER BY DatabaseOrder ASC\n" +
"            ) QueueDatabase\n" +
"    END\n" +
"    ELSE\n" +
"    BEGIN\n" +
"      SELECT TOP 1 @CurrentDBID = ID,\n" +
"                   @CurrentDatabaseName = DatabaseName,\n" +
"                   @CurrentDatabaseNameFS = DatabaseNameFS\n" +
"      FROM @tmpDatabases\n" +
"      WHERE Selected = 1\n" +
"      AND Completed = 0\n" +
"      ORDER BY [Order] ASC\n" +
"    END\n" +
"\n" +
"    IF @@ROWCOUNT = 0\n" +
"    BEGIN\n" +
"      BREAK\n" +
"    END\n" +
"\n" +
"    SET @CurrentDatabase_sp_executesql = QUOTENAME(@CurrentDatabaseName) + '.sys.sp_executesql'\n" +
"\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Date and time: ' + CONVERT(nvarchar,SYSDATETIME(),120)\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Database: ' + QUOTENAME(@CurrentDatabaseName)\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    SELECT @CurrentUserAccess = user_access_desc,\n" +
"           @CurrentIsReadOnly = is_read_only,\n" +
"           @CurrentDatabaseState = state_desc,\n" +
"           @CurrentInStandby = is_in_standby,\n" +
"           @CurrentRecoveryModel = recovery_model_desc,\n" +
"           @CurrentIsEncrypted = is_encrypted,\n" +
"           @CurrentDatabaseSize = (SELECT SUM(CAST(size AS bigint)) FROM sys.master_files WHERE [type] = 0 AND database_id = sys.databases.database_id)\n" +
"    FROM sys.databases\n" +
"    WHERE [name] = @CurrentDatabaseName\n" +
"\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'State: ' + @CurrentDatabaseState\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Standby: ' + CASE WHEN @CurrentInStandby = 1 THEN 'Yes' ELSE 'No' END\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage =  'Updateability: ' + CASE WHEN @CurrentIsReadOnly = 1 THEN 'READ_ONLY' WHEN  @CurrentIsReadOnly = 0 THEN 'READ_WRITE' END\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage =  'User access: ' + @CurrentUserAccess\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Recovery model: ' + @CurrentRecoveryModel\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Encrypted: ' + CASE WHEN @CurrentIsEncrypted = 1 THEN 'Yes' WHEN @CurrentIsEncrypted = 0 THEN 'No' ELSE 'N/A' END\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    SELECT @CurrentMaxTransferSize = CASE\n" +
"    WHEN @MaxTransferSize IS NOT NULL THEN @MaxTransferSize\n" +
"    WHEN @MaxTransferSize IS NULL AND @Compress = 'Y' AND @CurrentIsEncrypted = 1 AND @BackupSoftware IS NULL AND (@Version >= 13 AND @Version < 15.0404316) THEN 65537\n" +
"    END\n" +
"\n" +
"    IF @CurrentDatabaseState = 'ONLINE'\n" +
"    BEGIN\n" +
"      IF EXISTS (SELECT * FROM sys.database_recovery_status WHERE database_id = DB_ID(@CurrentDatabaseName) AND database_guid IS NOT NULL)\n" +
"      BEGIN\n" +
"        SET @CurrentIsDatabaseAccessible = 1\n" +
"      END\n" +
"      ELSE\n" +
"      BEGIN\n" +
"        SET @CurrentIsDatabaseAccessible = 0\n" +
"      END\n" +
"    END\n" +
"\n" +
"    IF @Version >= 11 AND SERVERPROPERTY('IsHadrEnabled') = 1\n" +
"    BEGIN\n" +
"      SELECT @CurrentReplicaID = databases.replica_id\n" +
"      FROM sys.databases databases\n" +
"      INNER JOIN sys.availability_replicas availability_replicas ON databases.replica_id = availability_replicas.replica_id\n" +
"      WHERE databases.[name] = @CurrentDatabaseName\n" +
"\n" +
"      SELECT @CurrentAvailabilityGroupID = group_id\n" +
"      FROM sys.availability_replicas\n" +
"      WHERE replica_id = @CurrentReplicaID\n" +
"\n" +
"      SELECT @CurrentAvailabilityGroupRole = role_desc\n" +
"      FROM sys.dm_hadr_availability_replica_states\n" +
"      WHERE replica_id = @CurrentReplicaID\n" +
"\n" +
"      SELECT @CurrentAvailabilityGroup = [name],\n" +
"             @CurrentAvailabilityGroupBackupPreference = UPPER(automated_backup_preference_desc)\n" +
"      FROM sys.availability_groups\n" +
"      WHERE group_id = @CurrentAvailabilityGroupID\n" +
"    END\n" +
"\n" +
"    IF @Version >= 11 AND SERVERPROPERTY('IsHadrEnabled') = 1 AND @CurrentAvailabilityGroup IS NOT NULL\n" +
"    BEGIN\n" +
"      SELECT @CurrentIsPreferredBackupReplica = sys.fn_hadr_backup_is_preferred_replica(@CurrentDatabaseName)\n" +
"    END\n" +
"\n" +
"    SELECT @CurrentDifferentialBaseLSN = differential_base_lsn\n" +
"    FROM sys.master_files\n" +
"    WHERE database_id = DB_ID(@CurrentDatabaseName)\n" +
"    AND [type] = 0\n" +
"    AND [file_id] = 1\n" +
"\n" +
"    IF @CurrentDatabaseState = 'ONLINE'\n" +
"    BEGIN\n" +
"      SELECT @CurrentLogLSN = last_log_backup_lsn\n" +
"      FROM sys.database_recovery_status\n" +
"      WHERE database_id = DB_ID(@CurrentDatabaseName)\n" +
"    END\n" +
"\n" +
"    IF @CurrentDatabaseState = 'ONLINE' AND EXISTS(SELECT * FROM sys.all_columns WHERE object_id = OBJECT_ID('sys.dm_db_file_space_usage') AND name = 'modified_extent_page_count') AND (@CurrentAvailabilityGroupRole = 'PRIMARY' OR @CurrentAvailabilityGroupRole IS NULL)\n" +
"    BEGIN\n" +
"      SET @CurrentCommand = 'SELECT @ParamAllocatedExtentPageCount = SUM(allocated_extent_page_count), @ParamModifiedExtentPageCount = SUM(modified_extent_page_count) FROM sys.dm_db_file_space_usage'\n" +
"\n" +
"      EXECUTE @CurrentDatabase_sp_executesql @stmt = @CurrentCommand, @params = N'@ParamAllocatedExtentPageCount bigint OUTPUT, @ParamModifiedExtentPageCount bigint OUTPUT', @ParamAllocatedExtentPageCount = @CurrentAllocatedExtentPageCount OUTPUT, @ParamModifiedExtentPageCount = @CurrentModifiedExtentPageCount OUTPUT\n" +
"    END\n" +
"\n" +
"    SET @CurrentBackupType = @BackupType\n" +
"\n" +
"    IF @ChangeBackupType = 'Y'\n" +
"    BEGIN\n" +
"      IF @CurrentBackupType = 'LOG' AND @CurrentRecoveryModel IN('FULL','BULK_LOGGED') AND @CurrentLogLSN IS NULL AND @CurrentDatabaseName <> 'master'\n" +
"      BEGIN\n" +
"        SET @CurrentBackupType = 'DIFF'\n" +
"      END\n" +
"      IF @CurrentBackupType = 'DIFF' AND (@CurrentDatabaseName = 'master' OR @CurrentDifferentialBaseLSN IS NULL OR (@CurrentModifiedExtentPageCount * 1. / @CurrentAllocatedExtentPageCount * 100 >= @ModificationLevel))\n" +
"      BEGIN\n" +
"        SET @CurrentBackupType = 'FULL'\n" +
"      END\n" +
"    END\n" +
"\n" +
"    IF @CurrentDatabaseState = 'ONLINE' AND EXISTS(SELECT * FROM sys.all_columns WHERE object_id = OBJECT_ID('sys.dm_db_log_stats') AND name = 'log_since_last_log_backup_mb')\n" +
"    BEGIN\n" +
"      SELECT @CurrentLastLogBackup = log_backup_time,\n" +
"             @CurrentLogSizeSinceLastLogBackup = log_since_last_log_backup_mb\n" +
"      FROM sys.dm_db_log_stats (DB_ID(@CurrentDatabaseName))\n" +
"    END\n" +
"\n" +
"    IF @CurrentBackupType = 'DIFF'\n" +
"    BEGIN\n" +
"      SELECT @CurrentDifferentialBaseIsSnapshot = is_snapshot\n" +
"      FROM msdb.dbo.backupset\n" +
"      WHERE database_name = @CurrentDatabaseName\n" +
"      AND [type] = 'D'\n" +
"      AND checkpoint_lsn = @CurrentDifferentialBaseLSN\n" +
"    END\n" +
"\n" +
"    IF @ChangeBackupType = 'Y'\n" +
"    BEGIN\n" +
"      IF @CurrentBackupType = 'DIFF' AND @CurrentDifferentialBaseIsSnapshot = 1\n" +
"      BEGIN\n" +
"        SET @CurrentBackupType = 'FULL'\n" +
"      END\n" +
"    END;\n" +
"\n" +
"    WITH CurrentDatabase AS\n" +
"    (\n" +
"    SELECT BackupSize = CASE WHEN @CurrentBackupType = 'FULL' THEN COALESCE(CAST(@CurrentAllocatedExtentPageCount AS bigint) * 8192, CAST(@CurrentDatabaseSize AS bigint) * 8192)\n" +
"                             WHEN @CurrentBackupType = 'DIFF' THEN CAST(@CurrentModifiedExtentPageCount AS bigint) * 8192\n" +
"                             WHEN @CurrentBackupType = 'LOG' THEN CAST(@CurrentLogSizeSinceLastLogBackup * 1024 * 1024 AS bigint)\n" +
"                             END,\n" +
"           MaxNumberOfFiles = CASE WHEN @BackupSoftware IN('SQLBACKUP','DATA_DOMAIN_BOOST') THEN 32 ELSE 64 END,\n" +
"           CASE WHEN (SELECT COUNT(*) FROM @Directories WHERE Mirror = 0) > 0 THEN (SELECT COUNT(*) FROM @Directories WHERE Mirror = 0) ELSE (SELECT COUNT(*) FROM @URLs WHERE Mirror = 0) END AS NumberOfDirectories,\n" +
"           CAST(@MinBackupSizeForMultipleFiles AS bigint) * 1024 * 1024 AS MinBackupSizeForMultipleFiles,\n" +
"           CAST(@MaxFileSize AS bigint) * 1024 * 1024 AS MaxFileSize\n" +
"    )\n" +
"    SELECT @CurrentNumberOfFiles = CASE WHEN @NumberOfFiles IS NULL AND @BackupSoftware = 'DATA_DOMAIN_BOOST' THEN 1\n" +
"                                        WHEN @NumberOfFiles IS NULL AND @MaxFileSize IS NULL THEN NumberOfDirectories\n" +
"                                        WHEN @NumberOfFiles = 1 THEN @NumberOfFiles\n" +
"                                        WHEN @NumberOfFiles > 1 AND (BackupSize >= MinBackupSizeForMultipleFiles OR MinBackupSizeForMultipleFiles IS NULL OR BackupSize IS NULL) THEN @NumberOfFiles\n" +
"                                        WHEN @NumberOfFiles > 1 AND (BackupSize < MinBackupSizeForMultipleFiles) THEN NumberOfDirectories\n" +
"                                        WHEN @NumberOfFiles IS NULL AND @MaxFileSize IS NOT NULL AND (BackupSize IS NULL OR BackupSize = 0) THEN NumberOfDirectories\n" +
"                                        WHEN @NumberOfFiles IS NULL AND @MaxFileSize IS NOT NULL THEN (SELECT MIN(NumberOfFilesInEachDirectory)\n" +
"                                                                                                       FROM (SELECT ((BackupSize / NumberOfDirectories) / MaxFileSize + CASE WHEN (BackupSize / NumberOfDirectories) % MaxFileSize = 0 THEN 0 ELSE 1 END) AS NumberOfFilesInEachDirectory\n" +
"                                                                                                             UNION\n" +
"                                                                                                             SELECT MaxNumberOfFiles / NumberOfDirectories) Files) * NumberOfDirectories\n" +
"                                        END\n" +
"\n" +
"    FROM CurrentDatabase\n" +
"\n" +
"    SELECT @CurrentDatabaseMirroringRole = UPPER(mirroring_role_desc)\n" +
"    FROM sys.database_mirroring\n" +
"    WHERE database_id = DB_ID(@CurrentDatabaseName)\n" +
"\n" +
"    IF EXISTS (SELECT * FROM msdb.dbo.log_shipping_primary_databases WHERE primary_database = @CurrentDatabaseName)\n" +
"    BEGIN\n" +
"      SET @CurrentLogShippingRole = 'PRIMARY'\n" +
"    END\n" +
"    ELSE\n" +
"    IF EXISTS (SELECT * FROM msdb.dbo.log_shipping_secondary_databases WHERE secondary_database = @CurrentDatabaseName)\n" +
"    BEGIN\n" +
"      SET @CurrentLogShippingRole = 'SECONDARY'\n" +
"    END\n" +
"\n" +
"    IF @CurrentIsDatabaseAccessible IS NOT NULL\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Is accessible: ' + CASE WHEN @CurrentIsDatabaseAccessible = 1 THEN 'Yes' ELSE 'No' END\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    IF @CurrentAvailabilityGroup IS NOT NULL\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Availability group: ' + ISNULL(@CurrentAvailabilityGroup,'N/A')\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Availability group role: ' + ISNULL(@CurrentAvailabilityGroupRole,'N/A')\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Availability group backup preference: ' + ISNULL(@CurrentAvailabilityGroupBackupPreference,'N/A')\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Is preferred backup replica: ' + CASE WHEN @CurrentIsPreferredBackupReplica = 1 THEN 'Yes' WHEN @CurrentIsPreferredBackupReplica = 0 THEN 'No' ELSE 'N/A' END\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    IF @CurrentDatabaseMirroringRole IS NOT NULL\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Database mirroring role: ' + @CurrentDatabaseMirroringRole\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    IF @CurrentLogShippingRole IS NOT NULL\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Log shipping role: ' + @CurrentLogShippingRole\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    SET @DatabaseMessage = 'Differential base LSN: ' + ISNULL(CAST(@CurrentDifferentialBaseLSN AS nvarchar),'N/A')\n" +
"    RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"    IF @CurrentBackupType = 'DIFF' OR @CurrentDifferentialBaseIsSnapshot IS NOT NULL\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Differential base is snapshot: ' + CASE WHEN @CurrentDifferentialBaseIsSnapshot = 1 THEN 'Yes' WHEN @CurrentDifferentialBaseIsSnapshot = 0 THEN 'No' ELSE 'N/A' END\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    SET @DatabaseMessage = 'Last log backup LSN: ' + ISNULL(CAST(@CurrentLogLSN AS nvarchar),'N/A')\n" +
"    RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"    IF @CurrentBackupType IN('DIFF','FULL') AND EXISTS(SELECT * FROM sys.all_columns WHERE object_id = OBJECT_ID('sys.dm_db_file_space_usage') AND name = 'modified_extent_page_count')\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Allocated extent page count: ' + ISNULL(CAST(@CurrentAllocatedExtentPageCount AS nvarchar) + ' (' + CAST(@CurrentAllocatedExtentPageCount * 1. * 8 / 1024 AS nvarchar) + ' MB)','N/A')\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Modified extent page count: ' + ISNULL(CAST(@CurrentModifiedExtentPageCount AS nvarchar) + ' (' + CAST(@CurrentModifiedExtentPageCount * 1. * 8 / 1024 AS nvarchar) + ' MB)','N/A')\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    IF @CurrentBackupType = 'LOG' AND EXISTS(SELECT * FROM sys.all_columns WHERE object_id = OBJECT_ID('sys.dm_db_log_stats') AND name = 'log_since_last_log_backup_mb')\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Last log backup: ' + ISNULL(CONVERT(nvarchar(19),NULLIF(@CurrentLastLogBackup,'1900-01-01'),120),'N/A')\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Log size since last log backup (MB): ' + ISNULL(CAST(@CurrentLogSizeSinceLastLogBackup AS nvarchar),'N/A')\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"    IF @CurrentDatabaseState = 'ONLINE'\n" +
"    AND NOT (@CurrentUserAccess = 'SINGLE_USER' AND @CurrentIsDatabaseAccessible = 0)\n" +
"    AND NOT (@CurrentInStandby = 1)\n" +
"    AND NOT (@CurrentBackupType = 'LOG' AND @CurrentRecoveryModel = 'SIMPLE')\n" +
"    AND NOT (@CurrentBackupType = 'LOG' AND @CurrentRecoveryModel IN('FULL','BULK_LOGGED') AND @CurrentLogLSN IS NULL)\n" +
"    AND NOT (@CurrentBackupType = 'DIFF' AND @CurrentDifferentialBaseLSN IS NULL)\n" +
"    AND NOT (@CurrentBackupType IN('DIFF','LOG') AND @CurrentDatabaseName = 'master')\n" +
"    AND NOT (@CurrentAvailabilityGroup IS NOT NULL AND @CurrentBackupType = 'FULL' AND @CopyOnly = 'N' AND (@CurrentAvailabilityGroupRole <> 'PRIMARY' OR @CurrentAvailabilityGroupRole IS NULL))\n" +
"    AND NOT (@CurrentAvailabilityGroup IS NOT NULL AND @CurrentBackupType = 'FULL' AND @CopyOnly = 'Y' AND (@CurrentIsPreferredBackupReplica <> 1 OR @CurrentIsPreferredBackupReplica IS NULL) AND @OverrideBackupPreference = 'N')\n" +
"    AND NOT (@CurrentAvailabilityGroup IS NOT NULL AND @CurrentBackupType = 'DIFF' AND (@CurrentAvailabilityGroupRole <> 'PRIMARY' OR @CurrentAvailabilityGroupRole IS NULL))\n" +
"    AND NOT (@CurrentAvailabilityGroup IS NOT NULL AND @CurrentBackupType = 'LOG' AND @CopyOnly = 'N' AND (@CurrentIsPreferredBackupReplica <> 1 OR @CurrentIsPreferredBackupReplica IS NULL) AND @OverrideBackupPreference = 'N')\n" +
"    AND NOT (@CurrentAvailabilityGroup IS NOT NULL AND @CurrentBackupType = 'LOG' AND @CopyOnly = 'Y' AND (@CurrentAvailabilityGroupRole <> 'PRIMARY' OR @CurrentAvailabilityGroupRole IS NULL))\n" +
"    AND NOT ((@CurrentLogShippingRole = 'PRIMARY' AND @CurrentLogShippingRole IS NOT NULL) AND @CurrentBackupType = 'LOG' AND @ExcludeLogShippedFromLogBackup = 'Y')\n" +
"    AND NOT (@CurrentIsReadOnly = 1 AND @Updateability = 'READ_WRITE')\n" +
"    AND NOT (@CurrentIsReadOnly = 0 AND @Updateability = 'READ_ONLY')\n" +
"    AND NOT (@CurrentBackupType = 'LOG' AND @LogSizeSinceLastLogBackup IS NOT NULL AND @TimeSinceLastLogBackup IS NOT NULL AND NOT(@CurrentLogSizeSinceLastLogBackup >= @LogSizeSinceLastLogBackup OR @CurrentLogSizeSinceLastLogBackup IS NULL OR DATEDIFF(SECOND,@CurrentLastLogBackup,SYSDATETIME()) >= @TimeSinceLastLogBackup OR @CurrentLastLogBackup IS NULL))\n" +
"    AND NOT (@CurrentBackupType = 'LOG' AND @Updateability = 'READ_ONLY' AND @BackupSoftware = 'DATA_DOMAIN_BOOST')\n" +
"    BEGIN\n" +
"\n" +
"      IF @CurrentBackupType = 'LOG' AND (@CleanupTime IS NOT NULL OR @MirrorCleanupTime IS NOT NULL)\n" +
"      BEGIN\n" +
"        SELECT @CurrentLatestBackup = MAX(backup_start_date)\n" +
"        FROM msdb.dbo.backupset\n" +
"        WHERE ([type] IN('D','I')\n" +
"        OR ([type] = 'L' AND last_lsn < @CurrentDifferentialBaseLSN))\n" +
"        AND is_damaged = 0\n" +
"        AND [database_name] = @CurrentDatabaseName\n" +
"      END\n" +
"\n" +
"      SET @CurrentDate = SYSDATETIME()\n" +
"\n" +
"      INSERT INTO @CurrentCleanupDates (CleanupDate)\n" +
"      SELECT @CurrentDate\n" +
"\n" +
"      IF @CurrentBackupType = 'LOG'\n" +
"      BEGIN\n" +
"        INSERT INTO @CurrentCleanupDates (CleanupDate)\n" +
"        SELECT @CurrentLatestBackup\n" +
"      END\n" +
"\n" +
"      SELECT @CurrentDirectoryStructure = CASE\n" +
"      WHEN @CurrentAvailabilityGroup IS NOT NULL THEN @AvailabilityGroupDirectoryStructure\n" +
"      ELSE @DirectoryStructure\n" +
"      END\n" +
"\n" +
"      IF @CurrentDirectoryStructure IS NOT NULL\n" +
"      BEGIN\n" +
"      -- Directory structure - remove tokens that are not needed\n" +
"        IF @ReadWriteFileGroups = 'N' SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Partial}','')\n" +
"        IF @CopyOnly = 'N' SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{CopyOnly}','')\n" +
"        IF @Cluster IS NULL SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{ClusterName}','')\n" +
"        IF @CurrentAvailabilityGroup IS NULL SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{AvailabilityGroupName}','')\n" +
"        IF SERVERPROPERTY('InstanceName') IS NULL SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{InstanceName}','')\n" +
"        IF @@SERVICENAME IS NULL SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{ServiceName}','')\n" +
"        IF @Description IS NULL SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Description}','')\n" +
"\n" +
"        IF @Directory IS NULL AND @MirrorDirectory IS NULL AND @URL IS NULL AND @DefaultDirectory LIKE '%' + '.' + @@SERVICENAME + @DirectorySeparator + 'MSSQL' + @DirectorySeparator + 'Backup'\n" +
"        BEGIN\n" +
"          SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{ServerName}','')\n" +
"          SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{InstanceName}','')\n" +
"          SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{ClusterName}','')\n" +
"          SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{AvailabilityGroupName}','')\n" +
"        END\n" +
"\n" +
"        WHILE (@Updated = 1 OR @Updated IS NULL)\n" +
"        BEGIN\n" +
"          SET @Updated = 0\n" +
"\n" +
"          IF CHARINDEX('\\',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'\\','{DirectorySeparator}')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF CHARINDEX('/',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'/','{DirectorySeparator}')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF CHARINDEX('__',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'__','_')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF CHARINDEX('--',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'--','-')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF CHARINDEX('{DirectorySeparator}{DirectorySeparator}',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{DirectorySeparator}{DirectorySeparator}','{DirectorySeparator}')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF CHARINDEX('{DirectorySeparator}$',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{DirectorySeparator}$','{DirectorySeparator}')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"          IF CHARINDEX('${DirectorySeparator}',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'${DirectorySeparator}','{DirectorySeparator}')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF CHARINDEX('{DirectorySeparator}_',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{DirectorySeparator}_','{DirectorySeparator}')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"          IF CHARINDEX('_{DirectorySeparator}',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'_{DirectorySeparator}','{DirectorySeparator}')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF CHARINDEX('{DirectorySeparator}-',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{DirectorySeparator}-','{DirectorySeparator}')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"          IF CHARINDEX('-{DirectorySeparator}',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'-{DirectorySeparator}','{DirectorySeparator}')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF CHARINDEX('_$',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'_$','_')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"          IF CHARINDEX('$_',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'$_','_')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF CHARINDEX('-$',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'-$','-')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"          IF CHARINDEX('$-',@CurrentDirectoryStructure) > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'$-','-')\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF LEFT(@CurrentDirectoryStructure,1) = '_'\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = RIGHT(@CurrentDirectoryStructure,LEN(@CurrentDirectoryStructure) - 1)\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"          IF RIGHT(@CurrentDirectoryStructure,1) = '_'\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = LEFT(@CurrentDirectoryStructure,LEN(@CurrentDirectoryStructure) - 1)\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF LEFT(@CurrentDirectoryStructure,1) = '-'\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = RIGHT(@CurrentDirectoryStructure,LEN(@CurrentDirectoryStructure) - 1)\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"          IF RIGHT(@CurrentDirectoryStructure,1) = '-'\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = LEFT(@CurrentDirectoryStructure,LEN(@CurrentDirectoryStructure) - 1)\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF LEFT(@CurrentDirectoryStructure,1) = '$'\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = RIGHT(@CurrentDirectoryStructure,LEN(@CurrentDirectoryStructure) - 1)\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"          IF RIGHT(@CurrentDirectoryStructure,1) = '$'\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = LEFT(@CurrentDirectoryStructure,LEN(@CurrentDirectoryStructure) - 1)\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"\n" +
"          IF LEFT(@CurrentDirectoryStructure,20) = '{DirectorySeparator}'\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = RIGHT(@CurrentDirectoryStructure,LEN(@CurrentDirectoryStructure) - 20)\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"          IF RIGHT(@CurrentDirectoryStructure,20) = '{DirectorySeparator}'\n" +
"          BEGIN\n" +
"            SET @CurrentDirectoryStructure = LEFT(@CurrentDirectoryStructure,LEN(@CurrentDirectoryStructure) - 20)\n" +
"            SET @Updated = 1\n" +
"          END\n" +
"        END\n" +
"\n" +
"        SET @Updated = NULL\n" +
"\n" +
"        -- Directory structure - replace tokens with real values\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{DirectorySeparator}',@DirectorySeparator)\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{ServerName}',CASE WHEN SERVERPROPERTY('EngineEdition') = 8 THEN LEFT(CAST(SERVERPROPERTY('ServerName') AS nvarchar(max)),CHARINDEX('.',CAST(SERVERPROPERTY('ServerName') AS nvarchar(max))) - 1) ELSE CAST(SERVERPROPERTY('MachineName') AS nvarchar(max)) END)\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{InstanceName}',ISNULL(CAST(SERVERPROPERTY('InstanceName') AS nvarchar(max)),''))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{ServiceName}',ISNULL(@@SERVICENAME,''))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{ClusterName}',ISNULL(@Cluster,''))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{AvailabilityGroupName}',ISNULL(@CurrentAvailabilityGroup,''))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{DatabaseName}',@CurrentDatabaseNameFS)\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{BackupType}',@CurrentBackupType)\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Partial}','PARTIAL')\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{CopyOnly}','COPY_ONLY')\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Description}',LTRIM(RTRIM(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(ISNULL(@Description,''),'\\',''),'/',''),':',''),'*',''),'?',''),'\"',''),'<',''),'>',''),'|',''))))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Year}',CAST(DATEPART(YEAR,@CurrentDate) AS nvarchar))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Month}',RIGHT('0' + CAST(DATEPART(MONTH,@CurrentDate) AS nvarchar),2))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Day}',RIGHT('0' + CAST(DATEPART(DAY,@CurrentDate) AS nvarchar),2))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Week}',RIGHT('0' + CAST(DATEPART(WEEK,@CurrentDate) AS nvarchar),2))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Hour}',RIGHT('0' + CAST(DATEPART(HOUR,@CurrentDate) AS nvarchar),2))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Minute}',RIGHT('0' + CAST(DATEPART(MINUTE,@CurrentDate) AS nvarchar),2))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Second}',RIGHT('0' + CAST(DATEPART(SECOND,@CurrentDate) AS nvarchar),2))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Millisecond}',RIGHT('00' + CAST(DATEPART(MILLISECOND,@CurrentDate) AS nvarchar),3))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{Microsecond}',RIGHT('00000' + CAST(DATEPART(MICROSECOND,@CurrentDate) AS nvarchar),6))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{MajorVersion}',ISNULL(CAST(SERVERPROPERTY('ProductMajorVersion') AS nvarchar),PARSENAME(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar),4)))\n" +
"        SET @CurrentDirectoryStructure = REPLACE(@CurrentDirectoryStructure,'{MinorVersion}',ISNULL(CAST(SERVERPROPERTY('ProductMinorVersion') AS nvarchar),PARSENAME(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar),3)))\n" +
"      END\n" +
"\n" +
"      INSERT INTO @CurrentDirectories (ID, DirectoryPath, Mirror, DirectoryNumber, CreateCompleted, CleanupCompleted)\n" +
"      SELECT ROW_NUMBER() OVER (ORDER BY ID),\n" +
"             DirectoryPath + CASE WHEN DirectoryPath = 'NUL' THEN '' WHEN @CurrentDirectoryStructure IS NOT NULL THEN @DirectorySeparator + @CurrentDirectoryStructure ELSE '' END,\n" +
"             Mirror,\n" +
"             ROW_NUMBER() OVER (PARTITION BY Mirror ORDER BY ID ASC),\n" +
"             0,\n" +
"             0\n" +
"      FROM @Directories\n" +
"      ORDER BY ID ASC\n" +
"\n" +
"      INSERT INTO @CurrentURLs (ID, DirectoryPath, Mirror, DirectoryNumber)\n" +
"      SELECT ROW_NUMBER() OVER (ORDER BY ID),\n" +
"             DirectoryPath + CASE WHEN @CurrentDirectoryStructure IS NOT NULL THEN @DirectorySeparator + @CurrentDirectoryStructure ELSE '' END,\n" +
"             Mirror,\n" +
"             ROW_NUMBER() OVER (PARTITION BY Mirror ORDER BY ID ASC)\n" +
"      FROM @URLs\n" +
"      ORDER BY ID ASC\n" +
"\n" +
"      SELECT @CurrentDatabaseFileName = CASE\n" +
"      WHEN @CurrentAvailabilityGroup IS NOT NULL THEN @AvailabilityGroupFileName\n" +
"      ELSE @FileName\n" +
"      END\n" +
"\n" +
"      -- File name - remove tokens that are not needed\n" +
"      IF @ReadWriteFileGroups = 'N' SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Partial}','')\n" +
"      IF @CopyOnly = 'N' SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{CopyOnly}','')\n" +
"      IF @Cluster IS NULL SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{ClusterName}','')\n" +
"      IF @CurrentAvailabilityGroup IS NULL SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{AvailabilityGroupName}','')\n" +
"      IF SERVERPROPERTY('InstanceName') IS NULL SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{InstanceName}','')\n" +
"      IF @@SERVICENAME IS NULL SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{ServiceName}','')\n" +
"      IF @Description IS NULL SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Description}','')\n" +
"      IF @CurrentNumberOfFiles = 1 SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{FileNumber}','')\n" +
"      IF @CurrentNumberOfFiles = 1 SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{NumberOfFiles}','')\n" +
"\n" +
"      WHILE (@Updated = 1 OR @Updated IS NULL)\n" +
"      BEGIN\n" +
"        SET @Updated = 0\n" +
"\n" +
"        IF CHARINDEX('__',@CurrentDatabaseFileName) > 0\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'__','_')\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"\n" +
"        IF CHARINDEX('--',@CurrentDatabaseFileName) > 0\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'--','-')\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"\n" +
"        IF CHARINDEX('_$',@CurrentDatabaseFileName) > 0\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'_$','_')\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"        IF CHARINDEX('$_',@CurrentDatabaseFileName) > 0\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'$_','_')\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"\n" +
"        IF CHARINDEX('-$',@CurrentDatabaseFileName) > 0\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'-$','-')\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"        IF CHARINDEX('$-',@CurrentDatabaseFileName) > 0\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'$-','-')\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"\n" +
"        IF CHARINDEX('_.',@CurrentDatabaseFileName) > 0\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'_.','.')\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"\n" +
"        IF CHARINDEX('-.',@CurrentDatabaseFileName) > 0\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'-.','.')\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"\n" +
"        IF CHARINDEX('of.',@CurrentDatabaseFileName) > 0\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'of.','.')\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"\n" +
"        IF LEFT(@CurrentDatabaseFileName,1) = '_'\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = RIGHT(@CurrentDatabaseFileName,LEN(@CurrentDatabaseFileName) - 1)\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"        IF RIGHT(@CurrentDatabaseFileName,1) = '_'\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = LEFT(@CurrentDatabaseFileName,LEN(@CurrentDatabaseFileName) - 1)\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"\n" +
"        IF LEFT(@CurrentDatabaseFileName,1) = '-'\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = RIGHT(@CurrentDatabaseFileName,LEN(@CurrentDatabaseFileName) - 1)\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"        IF RIGHT(@CurrentDatabaseFileName,1) = '-'\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = LEFT(@CurrentDatabaseFileName,LEN(@CurrentDatabaseFileName) - 1)\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"\n" +
"        IF LEFT(@CurrentDatabaseFileName,1) = '$'\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = RIGHT(@CurrentDatabaseFileName,LEN(@CurrentDatabaseFileName) - 1)\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"        IF RIGHT(@CurrentDatabaseFileName,1) = '$'\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseFileName = LEFT(@CurrentDatabaseFileName,LEN(@CurrentDatabaseFileName) - 1)\n" +
"          SET @Updated = 1\n" +
"        END\n" +
"      END\n" +
"\n" +
"      SET @Updated = NULL\n" +
"\n" +
"      SELECT @CurrentFileExtension = CASE\n" +
"      WHEN @CurrentBackupType = 'FULL' THEN @FileExtensionFull\n" +
"      WHEN @CurrentBackupType = 'DIFF' THEN @FileExtensionDiff\n" +
"      WHEN @CurrentBackupType = 'LOG' THEN @FileExtensionLog\n" +
"      END\n" +
"\n" +
"      -- File name - replace tokens with real values\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{ServerName}',CASE WHEN SERVERPROPERTY('EngineEdition') = 8 THEN LEFT(CAST(SERVERPROPERTY('ServerName') AS nvarchar(max)),CHARINDEX('.',CAST(SERVERPROPERTY('ServerName') AS nvarchar(max))) - 1) ELSE CAST(SERVERPROPERTY('MachineName') AS nvarchar(max)) END)\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{InstanceName}',ISNULL(CAST(SERVERPROPERTY('InstanceName') AS nvarchar(max)),''))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{ServiceName}',ISNULL(@@SERVICENAME,''))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{ClusterName}',ISNULL(@Cluster,''))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{AvailabilityGroupName}',ISNULL(@CurrentAvailabilityGroup,''))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{BackupType}',@CurrentBackupType)\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Partial}','PARTIAL')\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{CopyOnly}','COPY_ONLY')\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Description}',LTRIM(RTRIM(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(ISNULL(@Description,''),'\\',''),'/',''),':',''),'*',''),'?',''),'\"',''),'<',''),'>',''),'|',''))))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Year}',CAST(DATEPART(YEAR,@CurrentDate) AS nvarchar))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Month}',RIGHT('0' + CAST(DATEPART(MONTH,@CurrentDate) AS nvarchar),2))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Day}',RIGHT('0' + CAST(DATEPART(DAY,@CurrentDate) AS nvarchar),2))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Week}',RIGHT('0' + CAST(DATEPART(WEEK,@CurrentDate) AS nvarchar),2))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Hour}',RIGHT('0' + CAST(DATEPART(HOUR,@CurrentDate) AS nvarchar),2))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Minute}',RIGHT('0' + CAST(DATEPART(MINUTE,@CurrentDate) AS nvarchar),2))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Second}',RIGHT('0' + CAST(DATEPART(SECOND,@CurrentDate) AS nvarchar),2))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Millisecond}',RIGHT('00' + CAST(DATEPART(MILLISECOND,@CurrentDate) AS nvarchar),3))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{Microsecond}',RIGHT('00000' + CAST(DATEPART(MICROSECOND,@CurrentDate) AS nvarchar),6))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{NumberOfFiles}',@CurrentNumberOfFiles)\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{FileExtension}',@CurrentFileExtension)\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{MajorVersion}',ISNULL(CAST(SERVERPROPERTY('ProductMajorVersion') AS nvarchar),PARSENAME(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar),4)))\n" +
"      SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{MinorVersion}',ISNULL(CAST(SERVERPROPERTY('ProductMinorVersion') AS nvarchar),PARSENAME(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar),3)))\n" +
"\n" +
"      SELECT @CurrentMaxFilePathLength = CASE\n" +
"      WHEN EXISTS (SELECT * FROM @CurrentDirectories) THEN (SELECT MAX(LEN(DirectoryPath + @DirectorySeparator)) FROM @CurrentDirectories)\n" +
"      WHEN EXISTS (SELECT * FROM @CurrentURLs) THEN (SELECT MAX(LEN(DirectoryPath + @DirectorySeparator)) FROM @CurrentURLs)\n" +
"      END\n" +
"      + LEN(REPLACE(REPLACE(@CurrentDatabaseFileName,'{DatabaseName}',@CurrentDatabaseNameFS), '{FileNumber}', CASE WHEN @CurrentNumberOfFiles >= 1 AND @CurrentNumberOfFiles <= 9 THEN '1' WHEN @CurrentNumberOfFiles >= 10 THEN '01' END))\n" +
"\n" +
"      -- The maximum length of a backup device is 259 characters\n" +
"      IF @CurrentMaxFilePathLength > 259\n" +
"      BEGIN\n" +
"        SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{DatabaseName}',LEFT(@CurrentDatabaseNameFS,CASE WHEN (LEN(@CurrentDatabaseNameFS) + 259 - @CurrentMaxFilePathLength - 3) < 20 THEN 20 ELSE (LEN(@CurrentDatabaseNameFS) + 259 - @CurrentMaxFilePathLength - 3) END) + '...')\n" +
"      END\n" +
"      ELSE\n" +
"      BEGIN\n" +
"        SET @CurrentDatabaseFileName = REPLACE(@CurrentDatabaseFileName,'{DatabaseName}',@CurrentDatabaseNameFS)\n" +
"      END\n" +
"\n" +
"      IF EXISTS (SELECT * FROM @CurrentDirectories WHERE Mirror = 0)\n" +
"      BEGIN\n" +
"        SET @CurrentFileNumber = 0\n" +
"\n" +
"        WHILE @CurrentFileNumber < @CurrentNumberOfFiles\n" +
"        BEGIN\n" +
"          SET @CurrentFileNumber = @CurrentFileNumber + 1\n" +
"\n" +
"          SELECT @CurrentDirectoryPath = DirectoryPath\n" +
"          FROM @CurrentDirectories\n" +
"          WHERE @CurrentFileNumber >= (DirectoryNumber - 1) * (SELECT @CurrentNumberOfFiles / COUNT(*) FROM @CurrentDirectories WHERE Mirror = 0) + 1\n" +
"          AND @CurrentFileNumber <= DirectoryNumber * (SELECT @CurrentNumberOfFiles / COUNT(*) FROM @CurrentDirectories WHERE Mirror = 0)\n" +
"          AND Mirror = 0\n" +
"\n" +
"          SET @CurrentFileName = REPLACE(@CurrentDatabaseFileName, '{FileNumber}', CASE WHEN @CurrentNumberOfFiles >= 1 AND @CurrentNumberOfFiles <= 9 THEN CAST(@CurrentFileNumber AS nvarchar) WHEN @CurrentNumberOfFiles >= 10 THEN RIGHT('0' + CAST(@CurrentFileNumber AS nvarchar),2) END)\n" +
"\n" +
"          IF @CurrentDirectoryPath = 'NUL'\n" +
"          BEGIN\n" +
"            SET @CurrentFilePath = 'NUL'\n" +
"          END\n" +
"          ELSE\n" +
"          BEGIN\n" +
"            SET @CurrentFilePath = @CurrentDirectoryPath + @DirectorySeparator + @CurrentFileName\n" +
"          END\n" +
"\n" +
"          INSERT INTO @CurrentFiles ([Type], FilePath, Mirror)\n" +
"          SELECT 'DISK', @CurrentFilePath, 0\n" +
"\n" +
"          SET @CurrentDirectoryPath = NULL\n" +
"          SET @CurrentFileName = NULL\n" +
"          SET @CurrentFilePath = NULL\n" +
"        END\n" +
"\n" +
"        INSERT INTO @CurrentBackupSet (Mirror, VerifyCompleted)\n" +
"        SELECT 0, 0\n" +
"      END\n" +
"\n" +
"      IF EXISTS (SELECT * FROM @CurrentDirectories WHERE Mirror = 1)\n" +
"      BEGIN\n" +
"        SET @CurrentFileNumber = 0\n" +
"\n" +
"        WHILE @CurrentFileNumber < @CurrentNumberOfFiles\n" +
"        BEGIN\n" +
"          SET @CurrentFileNumber = @CurrentFileNumber + 1\n" +
"\n" +
"          SELECT @CurrentDirectoryPath = DirectoryPath\n" +
"          FROM @CurrentDirectories\n" +
"          WHERE @CurrentFileNumber >= (DirectoryNumber - 1) * (SELECT @CurrentNumberOfFiles / COUNT(*) FROM @CurrentDirectories WHERE Mirror = 1) + 1\n" +
"          AND @CurrentFileNumber <= DirectoryNumber * (SELECT @CurrentNumberOfFiles / COUNT(*) FROM @CurrentDirectories WHERE Mirror = 1)\n" +
"          AND Mirror = 1\n" +
"\n" +
"          SET @CurrentFileName = REPLACE(@CurrentDatabaseFileName, '{FileNumber}', CASE WHEN @CurrentNumberOfFiles > 1 AND @CurrentNumberOfFiles <= 9 THEN CAST(@CurrentFileNumber AS nvarchar) WHEN @CurrentNumberOfFiles >= 10 THEN RIGHT('0' + CAST(@CurrentFileNumber AS nvarchar),2) ELSE '' END)\n" +
"\n" +
"          SET @CurrentFilePath = @CurrentDirectoryPath + @DirectorySeparator + @CurrentFileName\n" +
"\n" +
"          INSERT INTO @CurrentFiles ([Type], FilePath, Mirror)\n" +
"          SELECT 'DISK', @CurrentFilePath, 1\n" +
"\n" +
"          SET @CurrentDirectoryPath = NULL\n" +
"          SET @CurrentFileName = NULL\n" +
"          SET @CurrentFilePath = NULL\n" +
"        END\n" +
"\n" +
"        INSERT INTO @CurrentBackupSet (Mirror, VerifyCompleted)\n" +
"        SELECT 1, 0\n" +
"      END\n" +
"\n" +
"      IF EXISTS (SELECT * FROM @CurrentURLs WHERE Mirror = 0)\n" +
"      BEGIN\n" +
"        SET @CurrentFileNumber = 0\n" +
"\n" +
"        WHILE @CurrentFileNumber < @CurrentNumberOfFiles\n" +
"        BEGIN\n" +
"          SET @CurrentFileNumber = @CurrentFileNumber + 1\n" +
"\n" +
"          SELECT @CurrentDirectoryPath = DirectoryPath\n" +
"          FROM @CurrentURLs\n" +
"          WHERE @CurrentFileNumber >= (DirectoryNumber - 1) * (SELECT @CurrentNumberOfFiles / COUNT(*) FROM @CurrentURLs WHERE Mirror = 0) + 1\n" +
"          AND @CurrentFileNumber <= DirectoryNumber * (SELECT @CurrentNumberOfFiles / COUNT(*) FROM @CurrentURLs WHERE Mirror = 0)\n" +
"          AND Mirror = 0\n" +
"\n" +
"          SET @CurrentFileName = REPLACE(@CurrentDatabaseFileName, '{FileNumber}', CASE WHEN @CurrentNumberOfFiles > 1 AND @CurrentNumberOfFiles <= 9 THEN CAST(@CurrentFileNumber AS nvarchar) WHEN @CurrentNumberOfFiles >= 10 THEN RIGHT('0' + CAST(@CurrentFileNumber AS nvarchar),2) ELSE '' END)\n" +
"\n" +
"          SET @CurrentFilePath = @CurrentDirectoryPath + @DirectorySeparator + @CurrentFileName\n" +
"\n" +
"          INSERT INTO @CurrentFiles ([Type], FilePath, Mirror)\n" +
"          SELECT 'URL', @CurrentFilePath, 0\n" +
"\n" +
"          SET @CurrentDirectoryPath = NULL\n" +
"          SET @CurrentFileName = NULL\n" +
"          SET @CurrentFilePath = NULL\n" +
"        END\n" +
"\n" +
"        INSERT INTO @CurrentBackupSet (Mirror, VerifyCompleted)\n" +
"        SELECT 0, 0\n" +
"      END\n" +
"\n" +
"      IF EXISTS (SELECT * FROM @CurrentURLs WHERE Mirror = 1)\n" +
"      BEGIN\n" +
"        SET @CurrentFileNumber = 0\n" +
"\n" +
"        WHILE @CurrentFileNumber < @CurrentNumberOfFiles\n" +
"        BEGIN\n" +
"          SET @CurrentFileNumber = @CurrentFileNumber + 1\n" +
"\n" +
"          SELECT @CurrentDirectoryPath = DirectoryPath\n" +
"          FROM @CurrentURLs\n" +
"          WHERE @CurrentFileNumber >= (DirectoryNumber - 1) * (SELECT @CurrentNumberOfFiles / COUNT(*) FROM @CurrentURLs WHERE Mirror = 0) + 1\n" +
"          AND @CurrentFileNumber <= DirectoryNumber * (SELECT @CurrentNumberOfFiles / COUNT(*) FROM @CurrentURLs WHERE Mirror = 0)\n" +
"          AND Mirror = 1\n" +
"\n" +
"          SET @CurrentFileName = REPLACE(@CurrentDatabaseFileName, '{FileNumber}', CASE WHEN @CurrentNumberOfFiles > 1 AND @CurrentNumberOfFiles <= 9 THEN CAST(@CurrentFileNumber AS nvarchar) WHEN @CurrentNumberOfFiles >= 10 THEN RIGHT('0' + CAST(@CurrentFileNumber AS nvarchar),2) ELSE '' END)\n" +
"\n" +
"          SET @CurrentFilePath = @CurrentDirectoryPath + @DirectorySeparator + @CurrentFileName\n" +
"\n" +
"          INSERT INTO @CurrentFiles ([Type], FilePath, Mirror)\n" +
"          SELECT 'URL', @CurrentFilePath, 1\n" +
"\n" +
"          SET @CurrentDirectoryPath = NULL\n" +
"          SET @CurrentFileName = NULL\n" +
"          SET @CurrentFilePath = NULL\n" +
"        END\n" +
"\n" +
"        INSERT INTO @CurrentBackupSet (Mirror, VerifyCompleted)\n" +
"        SELECT 1, 0\n" +
"      END\n" +
"\n" +
"      -- Create directory\n" +
"      IF @HostPlatform = 'Windows'\n" +
"      AND (@BackupSoftware <> 'DATA_DOMAIN_BOOST' OR @BackupSoftware IS NULL)\n" +
"      AND NOT EXISTS(SELECT * FROM @CurrentDirectories WHERE DirectoryPath = 'NUL')\n" +
"      BEGIN\n" +
"        WHILE (1 = 1)\n" +
"        BEGIN\n" +
"          SELECT TOP 1 @CurrentDirectoryID = ID,\n" +
"                       @CurrentDirectoryPath = DirectoryPath\n" +
"          FROM @CurrentDirectories\n" +
"          WHERE CreateCompleted = 0\n" +
"          ORDER BY ID ASC\n" +
"\n" +
"          IF @@ROWCOUNT = 0\n" +
"          BEGIN\n" +
"            BREAK\n" +
"          END\n" +
"\n" +
"          SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"          SET @CurrentCommandType = 'xp_create_subdir'\n" +
"\n" +
"          SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.xp_create_subdir N''' + REPLACE(@CurrentDirectoryPath,'''','''''') + ''' IF @ReturnCode <> 0 RAISERROR(''Error creating directory.'', 16, 1)'\n" +
"\n" +
"          EXECUTE @CurrentCommandOutput = dbo.CommandExecute @DatabaseContext = @CurrentDatabaseContext, @Command = @CurrentCommand, @CommandType = @CurrentCommandType, @Mode = 1, @DatabaseName = @CurrentDatabaseName, @LogToTable = @LogToTable, @Execute = @Execute\n" +
"          SET @Error = @@ERROR\n" +
"          IF @Error <> 0 SET @CurrentCommandOutput = @Error\n" +
"          IF @CurrentCommandOutput <> 0 SET @ReturnCode = @CurrentCommandOutput\n" +
"\n" +
"          UPDATE @CurrentDirectories\n" +
"          SET CreateCompleted = 1,\n" +
"              CreateOutput = @CurrentCommandOutput\n" +
"          WHERE ID = @CurrentDirectoryID\n" +
"\n" +
"          SET @CurrentDirectoryID = NULL\n" +
"          SET @CurrentDirectoryPath = NULL\n" +
"\n" +
"          SET @CurrentDatabaseContext = NULL\n" +
"          SET @CurrentCommand = NULL\n" +
"          SET @CurrentCommandOutput = NULL\n" +
"          SET @CurrentCommandType = NULL\n" +
"        END\n" +
"      END\n" +
"\n" +
"      IF @CleanupMode = 'BEFORE_BACKUP'\n" +
"      BEGIN\n" +
"        INSERT INTO @CurrentCleanupDates (CleanupDate, Mirror)\n" +
"        SELECT DATEADD(hh,-(@CleanupTime),SYSDATETIME()), 0\n" +
"\n" +
"        IF NOT EXISTS(SELECT * FROM @CurrentCleanupDates WHERE (Mirror = 0 OR Mirror IS NULL) AND CleanupDate IS NULL)\n" +
"        BEGIN\n" +
"          UPDATE @CurrentDirectories\n" +
"          SET CleanupDate = (SELECT MIN(CleanupDate)\n" +
"                             FROM @CurrentCleanupDates\n" +
"                             WHERE (Mirror = 0 OR Mirror IS NULL)),\n" +
"              CleanupMode = 'BEFORE_BACKUP'\n" +
"          WHERE Mirror = 0\n" +
"        END\n" +
"      END\n" +
"\n" +
"      IF @MirrorCleanupMode = 'BEFORE_BACKUP'\n" +
"      BEGIN\n" +
"        INSERT INTO @CurrentCleanupDates (CleanupDate, Mirror)\n" +
"        SELECT DATEADD(hh,-(@MirrorCleanupTime),SYSDATETIME()), 1\n" +
"\n" +
"        IF NOT EXISTS(SELECT * FROM @CurrentCleanupDates WHERE (Mirror = 1 OR Mirror IS NULL) AND CleanupDate IS NULL)\n" +
"        BEGIN\n" +
"          UPDATE @CurrentDirectories\n" +
"          SET CleanupDate = (SELECT MIN(CleanupDate)\n" +
"                             FROM @CurrentCleanupDates\n" +
"                             WHERE (Mirror = 1 OR Mirror IS NULL)),\n" +
"              CleanupMode = 'BEFORE_BACKUP'\n" +
"          WHERE Mirror = 1\n" +
"        END\n" +
"      END\n" +
"\n" +
"      -- Delete old backup files, before backup\n" +
"      IF NOT EXISTS (SELECT * FROM @CurrentDirectories WHERE CreateOutput <> 0 OR CreateOutput IS NULL)\n" +
"      AND (@BackupSoftware <> 'DATA_DOMAIN_BOOST' OR @BackupSoftware IS NULL)\n" +
"      AND @CurrentBackupType = @BackupType\n" +
"      BEGIN\n" +
"        WHILE (1 = 1)\n" +
"        BEGIN\n" +
"          SELECT TOP 1 @CurrentDirectoryID = ID,\n" +
"                       @CurrentDirectoryPath = DirectoryPath,\n" +
"                       @CurrentCleanupDate = CleanupDate\n" +
"          FROM @CurrentDirectories\n" +
"          WHERE CleanupDate IS NOT NULL\n" +
"          AND CleanupMode = 'BEFORE_BACKUP'\n" +
"          AND CleanupCompleted = 0\n" +
"          ORDER BY ID ASC\n" +
"\n" +
"          IF @@ROWCOUNT = 0\n" +
"          BEGIN\n" +
"            BREAK\n" +
"          END\n" +
"\n" +
"          IF @BackupSoftware IS NULL\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"            SET @CurrentCommandType = 'xp_delete_file'\n" +
"\n" +
"            SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.xp_delete_file 0, N''' + REPLACE(@CurrentDirectoryPath,'''','''''') + ''', ''' + @CurrentFileExtension + ''', ''' + CONVERT(nvarchar(19),@CurrentCleanupDate,126) + ''' IF @ReturnCode <> 0 RAISERROR(''Error deleting files.'', 16, 1)'\n" +
"          END\n" +
"\n" +
"          IF @BackupSoftware = 'LITESPEED'\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"            SET @CurrentCommandType = 'xp_slssqlmaint'\n" +
"\n" +
"            SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.xp_slssqlmaint N''-MAINTDEL -DELFOLDER \"' + REPLACE(@CurrentDirectoryPath,'''','''''') + '\" -DELEXTENSION \"' + @CurrentFileExtension + '\" -DELUNIT \"' + CAST(DATEDIFF(mi,@CurrentCleanupDate,SYSDATETIME()) + 1 AS nvarchar) + '\" -DELUNITTYPE \"minutes\" -DELUSEAGE'' IF @ReturnCode <> 0 RAISERROR(''Error deleting LiteSpeed backup files.'', 16, 1)'\n" +
"          END\n" +
"\n" +
"          IF @BackupSoftware = 'SQLBACKUP'\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"            SET @CurrentCommandType = 'sqbutility'\n" +
"\n" +
"            SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.sqbutility 1032, N''' + REPLACE(@CurrentDatabaseName,'''','''''') + ''', N''' + REPLACE(@CurrentDirectoryPath,'''','''''') + ''', ''' + CASE WHEN @CurrentBackupType = 'FULL' THEN 'D' WHEN @CurrentBackupType = 'DIFF' THEN 'I' WHEN @CurrentBackupType = 'LOG' THEN 'L' END + ''', ''' + CAST(DATEDIFF(hh,@CurrentCleanupDate,SYSDATETIME()) + 1 AS nvarchar) + 'h'', ' + ISNULL('''' + REPLACE(@EncryptionKey,'''','''''') + '''','NULL') + ' IF @ReturnCode <> 0 RAISERROR(''Error deleting SQLBackup backup files.'', 16, 1)'\n" +
"          END\n" +
"\n" +
"          IF @BackupSoftware = 'SQLSAFE'\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"            SET @CurrentCommandType = 'xp_ss_delete'\n" +
"\n" +
"            SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.xp_ss_delete @filename = N''' + REPLACE(@CurrentDirectoryPath,'''','''''') + '\\*.' + @CurrentFileExtension + ''', @age = ''' + CAST(DATEDIFF(mi,@CurrentCleanupDate,SYSDATETIME()) + 1 AS nvarchar) + 'Minutes'' IF @ReturnCode <> 0 RAISERROR(''Error deleting SQLsafe backup files.'', 16, 1)'\n" +
"          END\n" +
"\n" +
"          EXECUTE @CurrentCommandOutput = dbo.CommandExecute @DatabaseContext = @CurrentDatabaseContext, @Command = @CurrentCommand, @CommandType = @CurrentCommandType, @Mode = 1, @DatabaseName = @CurrentDatabaseName, @LogToTable = @LogToTable, @Execute = @Execute\n" +
"          SET @Error = @@ERROR\n" +
"          IF @Error <> 0 SET @CurrentCommandOutput = @Error\n" +
"          IF @CurrentCommandOutput <> 0 SET @ReturnCode = @CurrentCommandOutput\n" +
"\n" +
"          UPDATE @CurrentDirectories\n" +
"          SET CleanupCompleted = 1,\n" +
"              CleanupOutput = @CurrentCommandOutput\n" +
"          WHERE ID = @CurrentDirectoryID\n" +
"\n" +
"          SET @CurrentDirectoryID = NULL\n" +
"          SET @CurrentDirectoryPath = NULL\n" +
"          SET @CurrentCleanupDate = NULL\n" +
"\n" +
"          SET @CurrentDatabaseContext = NULL\n" +
"          SET @CurrentCommand = NULL\n" +
"          SET @CurrentCommandOutput = NULL\n" +
"          SET @CurrentCommandType = NULL\n" +
"        END\n" +
"      END\n" +
"\n" +
"      -- Perform a backup\n" +
"      IF NOT EXISTS (SELECT * FROM @CurrentDirectories WHERE DirectoryPath <> 'NUL' AND (CreateOutput <> 0 OR CreateOutput IS NULL)) OR @HostPlatform = 'Linux'\n" +
"      BEGIN\n" +
"        IF @BackupSoftware IS NULL\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"          SELECT @CurrentCommandType = CASE\n" +
"          WHEN @CurrentBackupType IN('DIFF','FULL') THEN 'BACKUP_DATABASE'\n" +
"          WHEN @CurrentBackupType = 'LOG' THEN 'BACKUP_LOG'\n" +
"          END\n" +
"\n" +
"          SELECT @CurrentCommand = CASE\n" +
"          WHEN @CurrentBackupType IN('DIFF','FULL') THEN 'BACKUP DATABASE ' + QUOTENAME(@CurrentDatabaseName)\n" +
"          WHEN @CurrentBackupType = 'LOG' THEN 'BACKUP LOG ' + QUOTENAME(@CurrentDatabaseName)\n" +
"          END\n" +
"\n" +
"          IF @ReadWriteFileGroups = 'Y' AND @CurrentDatabaseName <> 'master' SET @CurrentCommand += ' READ_WRITE_FILEGROUPS'\n" +
"\n" +
"          SET @CurrentCommand += ' TO'\n" +
"\n" +
"          SELECT @CurrentCommand += ' ' + [Type] + ' = N''' + REPLACE(FilePath,'''','''''') + '''' + CASE WHEN ROW_NUMBER() OVER (ORDER BY FilePath ASC) <> @CurrentNumberOfFiles THEN ',' ELSE '' END\n" +
"          FROM @CurrentFiles\n" +
"          WHERE Mirror = 0\n" +
"          ORDER BY FilePath ASC\n" +
"\n" +
"          IF EXISTS(SELECT * FROM @CurrentFiles WHERE Mirror = 1)\n" +
"          BEGIN\n" +
"            SET @CurrentCommand += ' MIRROR TO'\n" +
"\n" +
"            SELECT @CurrentCommand += ' ' + [Type] + ' = N''' + REPLACE(FilePath,'''','''''') + '''' + CASE WHEN ROW_NUMBER() OVER (ORDER BY FilePath ASC) <> @CurrentNumberOfFiles THEN ',' ELSE '' END\n" +
"            FROM @CurrentFiles\n" +
"            WHERE Mirror = 1\n" +
"            ORDER BY FilePath ASC\n" +
"          END\n" +
"\n" +
"          SET @CurrentCommand += ' WITH '\n" +
"          IF @CheckSum = 'Y' SET @CurrentCommand += 'CHECKSUM'\n" +
"          IF @CheckSum = 'N' SET @CurrentCommand += 'NO_CHECKSUM'\n" +
"\n" +
"          IF @Version >= 10\n" +
"          BEGIN\n" +
"            SET @CurrentCommand += CASE WHEN @Compress = 'Y' AND (@CurrentIsEncrypted = 0 OR (@CurrentIsEncrypted = 1 AND ((@Version >= 13 AND @CurrentMaxTransferSize >= 65537) OR @Version >= 15.0404316 OR SERVERPROPERTY('EngineEdition') = 8))) THEN ', COMPRESSION' ELSE ', NO_COMPRESSION' END\n" +
"          END\n" +
"\n" +
"          IF @CurrentBackupType = 'DIFF' SET @CurrentCommand += ', DIFFERENTIAL'\n" +
"\n" +
"          IF EXISTS(SELECT * FROM @CurrentFiles WHERE Mirror = 1)\n" +
"          BEGIN\n" +
"            SET @CurrentCommand += ', FORMAT'\n" +
"          END\n" +
"\n" +
"          IF @CopyOnly = 'Y' SET @CurrentCommand += ', COPY_ONLY'\n" +
"          IF @NoRecovery = 'Y' AND @CurrentBackupType = 'LOG' SET @CurrentCommand += ', NORECOVERY'\n" +
"          IF @Init = 'Y' SET @CurrentCommand += ', INIT'\n" +
"          IF @Format = 'Y' SET @CurrentCommand += ', FORMAT'\n" +
"          IF @BlockSize IS NOT NULL SET @CurrentCommand += ', BLOCKSIZE = ' + CAST(@BlockSize AS nvarchar)\n" +
"          IF @BufferCount IS NOT NULL SET @CurrentCommand += ', BUFFERCOUNT = ' + CAST(@BufferCount AS nvarchar)\n" +
"          IF @CurrentMaxTransferSize IS NOT NULL SET @CurrentCommand += ', MAXTRANSFERSIZE = ' + CAST(@CurrentMaxTransferSize AS nvarchar)\n" +
"          IF @Description IS NOT NULL SET @CurrentCommand += ', DESCRIPTION = N''' + REPLACE(@Description,'''','''''') + ''''\n" +
"          IF @Encrypt = 'Y' SET @CurrentCommand += ', ENCRYPTION (ALGORITHM = ' + UPPER(@EncryptionAlgorithm) + ', '\n" +
"          IF @Encrypt = 'Y' AND @ServerCertificate IS NOT NULL SET @CurrentCommand += 'SERVER CERTIFICATE = ' + QUOTENAME(@ServerCertificate)\n" +
"          IF @Encrypt = 'Y' AND @ServerAsymmetricKey IS NOT NULL SET @CurrentCommand += 'SERVER ASYMMETRIC KEY = ' + QUOTENAME(@ServerAsymmetricKey)\n" +
"          IF @Encrypt = 'Y' SET @CurrentCommand += ')'\n" +
"          IF @URL IS NOT NULL AND @Credential IS NOT NULL SET @CurrentCommand += ', CREDENTIAL = N''' + REPLACE(@Credential,'''','''''') + ''''\n" +
"        END\n" +
"\n" +
"        IF @BackupSoftware = 'LITESPEED'\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"          SELECT @CurrentCommandType = CASE\n" +
"          WHEN @CurrentBackupType IN('DIFF','FULL') THEN 'xp_backup_database'\n" +
"          WHEN @CurrentBackupType = 'LOG' THEN 'xp_backup_log'\n" +
"          END\n" +
"\n" +
"          SELECT @CurrentCommand = CASE\n" +
"          WHEN @CurrentBackupType IN('DIFF','FULL') THEN 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.xp_backup_database @database = N''' + REPLACE(@CurrentDatabaseName,'''','''''') + ''''\n" +
"          WHEN @CurrentBackupType = 'LOG' THEN 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.xp_backup_log @database = N''' + REPLACE(@CurrentDatabaseName,'''','''''') + ''''\n" +
"          END\n" +
"\n" +
"          SELECT @CurrentCommand += ', @filename = N''' + REPLACE(FilePath,'''','''''') + ''''\n" +
"          FROM @CurrentFiles\n" +
"          WHERE Mirror = 0\n" +
"          ORDER BY FilePath ASC\n" +
"\n" +
"          IF EXISTS(SELECT * FROM @CurrentFiles WHERE Mirror = 1)\n";
    }
    
    
    public String part7(){
        return "          BEGIN\n" +
"            SELECT @CurrentCommand += ', @mirror = N''' + REPLACE(FilePath,'''','''''') + ''''\n" +
"            FROM @CurrentFiles\n" +
"            WHERE Mirror = 1\n" +
"            ORDER BY FilePath ASC\n" +
"          END\n" +
"\n" +
"          SET @CurrentCommand += ', @with = '''\n" +
"          IF @CheckSum = 'Y' SET @CurrentCommand += 'CHECKSUM'\n" +
"          IF @CheckSum = 'N' SET @CurrentCommand += 'NO_CHECKSUM'\n" +
"          IF @CurrentBackupType = 'DIFF' SET @CurrentCommand += ', DIFFERENTIAL'\n" +
"          IF @CopyOnly = 'Y' SET @CurrentCommand += ', COPY_ONLY'\n" +
"          IF @NoRecovery = 'Y' AND @CurrentBackupType = 'LOG' SET @CurrentCommand += ', NORECOVERY'\n" +
"          IF @BlockSize IS NOT NULL SET @CurrentCommand += ', BLOCKSIZE = ' + CAST(@BlockSize AS nvarchar)\n" +
"          SET @CurrentCommand += ''''\n" +
"          IF @ReadWriteFileGroups = 'Y' AND @CurrentDatabaseName <> 'master' SET @CurrentCommand += ', @read_write_filegroups = 1'\n" +
"          IF @CompressionLevel IS NOT NULL SET @CurrentCommand += ', @compressionlevel = ' + CAST(@CompressionLevel AS nvarchar)\n" +
"          IF @AdaptiveCompression IS NOT NULL SET @CurrentCommand += ', @adaptivecompression = ''' + CASE WHEN @AdaptiveCompression = 'SIZE' THEN 'Size' WHEN @AdaptiveCompression = 'SPEED' THEN 'Speed' END + ''''\n" +
"          IF @BufferCount IS NOT NULL SET @CurrentCommand += ', @buffercount = ' + CAST(@BufferCount AS nvarchar)\n" +
"          IF @CurrentMaxTransferSize IS NOT NULL SET @CurrentCommand += ', @maxtransfersize = ' + CAST(@CurrentMaxTransferSize AS nvarchar)\n" +
"          IF @Threads IS NOT NULL SET @CurrentCommand += ', @threads = ' + CAST(@Threads AS nvarchar)\n" +
"          IF @Init = 'Y' SET @CurrentCommand += ', @init = 1'\n" +
"          IF @Format = 'Y' SET @CurrentCommand += ', @format = 1'\n" +
"          IF @Throttle IS NOT NULL SET @CurrentCommand += ', @throttle = ' + CAST(@Throttle AS nvarchar)\n" +
"          IF @Description IS NOT NULL SET @CurrentCommand += ', @desc = N''' + REPLACE(@Description,'''','''''') + ''''\n" +
"          IF @ObjectLevelRecoveryMap = 'Y' SET @CurrentCommand += ', @olrmap = 1'\n" +
"\n" +
"          IF @EncryptionAlgorithm IS NOT NULL SET @CurrentCommand += ', @cryptlevel = ' + CASE\n" +
"          WHEN @EncryptionAlgorithm = 'RC2_40' THEN '0'\n" +
"          WHEN @EncryptionAlgorithm = 'RC2_56' THEN '1'\n" +
"          WHEN @EncryptionAlgorithm = 'RC2_112' THEN '2'\n" +
"          WHEN @EncryptionAlgorithm = 'RC2_128' THEN '3'\n" +
"          WHEN @EncryptionAlgorithm = 'TRIPLE_DES_3KEY' THEN '4'\n" +
"          WHEN @EncryptionAlgorithm = 'RC4_128' THEN '5'\n" +
"          WHEN @EncryptionAlgorithm = 'AES_128' THEN '6'\n" +
"          WHEN @EncryptionAlgorithm = 'AES_192' THEN '7'\n" +
"          WHEN @EncryptionAlgorithm = 'AES_256' THEN '8'\n" +
"          END\n" +
"\n" +
"          IF @EncryptionKey IS NOT NULL SET @CurrentCommand += ', @encryptionkey = N''' + REPLACE(@EncryptionKey,'''','''''') + ''''\n" +
"          SET @CurrentCommand += ' IF @ReturnCode <> 0 RAISERROR(''Error performing LiteSpeed backup.'', 16, 1)'\n" +
"        END\n" +
"\n" +
"        IF @BackupSoftware = 'SQLBACKUP'\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"          SET @CurrentCommandType = 'sqlbackup'\n" +
"\n" +
"          SELECT @CurrentCommand = CASE\n" +
"          WHEN @CurrentBackupType IN('DIFF','FULL') THEN 'BACKUP DATABASE ' + QUOTENAME(@CurrentDatabaseName)\n" +
"          WHEN @CurrentBackupType = 'LOG' THEN 'BACKUP LOG ' + QUOTENAME(@CurrentDatabaseName)\n" +
"          END\n" +
"\n" +
"          IF @ReadWriteFileGroups = 'Y' AND @CurrentDatabaseName <> 'master' SET @CurrentCommand += ' READ_WRITE_FILEGROUPS'\n" +
"\n" +
"          SET @CurrentCommand += ' TO'\n" +
"\n" +
"          SELECT @CurrentCommand += ' DISK = N''' + REPLACE(FilePath,'''','''''') + '''' + CASE WHEN ROW_NUMBER() OVER (ORDER BY FilePath ASC) <> @CurrentNumberOfFiles THEN ',' ELSE '' END\n" +
"          FROM @CurrentFiles\n" +
"          WHERE Mirror = 0\n" +
"          ORDER BY FilePath ASC\n" +
"\n" +
"          SET @CurrentCommand += ' WITH '\n" +
"\n" +
"          IF EXISTS(SELECT * FROM @CurrentFiles WHERE Mirror = 1)\n" +
"          BEGIN\n" +
"            SET @CurrentCommand += ' MIRRORFILE' + ' = N''' + REPLACE((SELECT FilePath FROM @CurrentFiles WHERE Mirror = 1),'''','''''') + ''', '\n" +
"          END\n" +
"\n" +
"          IF @CheckSum = 'Y' SET @CurrentCommand += 'CHECKSUM'\n" +
"          IF @CheckSum = 'N' SET @CurrentCommand += 'NO_CHECKSUM'\n" +
"          IF @CurrentBackupType = 'DIFF' SET @CurrentCommand += ', DIFFERENTIAL'\n" +
"          IF @CopyOnly = 'Y' SET @CurrentCommand += ', COPY_ONLY'\n" +
"          IF @NoRecovery = 'Y' AND @CurrentBackupType = 'LOG' SET @CurrentCommand += ', NORECOVERY'\n" +
"          IF @Init = 'Y' SET @CurrentCommand += ', INIT'\n" +
"          IF @Format = 'Y' SET @CurrentCommand += ', FORMAT'\n" +
"          IF @CompressionLevel IS NOT NULL SET @CurrentCommand += ', COMPRESSION = ' + CAST(@CompressionLevel AS nvarchar)\n" +
"          IF @Threads IS NOT NULL SET @CurrentCommand += ', THREADCOUNT = ' + CAST(@Threads AS nvarchar)\n" +
"          IF @CurrentMaxTransferSize IS NOT NULL SET @CurrentCommand += ', MAXTRANSFERSIZE = ' + CAST(@CurrentMaxTransferSize AS nvarchar)\n" +
"          IF @Description IS NOT NULL SET @CurrentCommand += ', DESCRIPTION = N''' + REPLACE(@Description,'''','''''') + ''''\n" +
"\n" +
"          IF @EncryptionAlgorithm IS NOT NULL SET @CurrentCommand += ', KEYSIZE = ' + CASE\n" +
"          WHEN @EncryptionAlgorithm = 'AES_128' THEN '128'\n" +
"          WHEN @EncryptionAlgorithm = 'AES_256' THEN '256'\n" +
"          END\n" +
"\n" +
"          IF @EncryptionKey IS NOT NULL SET @CurrentCommand += ', PASSWORD = N''' + REPLACE(@EncryptionKey,'''','''''') + ''''\n" +
"          SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.sqlbackup N''-SQL \"' + REPLACE(@CurrentCommand,'''','''''') + '\"''' + ' IF @ReturnCode <> 0 RAISERROR(''Error performing SQLBackup backup.'', 16, 1)'\n" +
"        END\n" +
"\n" +
"        IF @BackupSoftware = 'SQLSAFE'\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"          SET @CurrentCommandType = 'xp_ss_backup'\n" +
"\n" +
"          SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.xp_ss_backup @database = N''' + REPLACE(@CurrentDatabaseName,'''','''''') + ''''\n" +
"\n" +
"          SELECT @CurrentCommand += ', ' + CASE WHEN ROW_NUMBER() OVER (ORDER BY FilePath ASC) = 1 THEN '@filename' ELSE '@backupfile' END + ' = N''' + REPLACE(FilePath,'''','''''') + ''''\n" +
"          FROM @CurrentFiles\n" +
"          WHERE Mirror = 0\n" +
"          ORDER BY FilePath ASC\n" +
"\n" +
"          SELECT @CurrentCommand += ', @mirrorfile = N''' + REPLACE(FilePath,'''','''''') + ''''\n" +
"          FROM @CurrentFiles\n" +
"          WHERE Mirror = 1\n" +
"          ORDER BY FilePath ASC\n" +
"\n" +
"          SET @CurrentCommand += ', @backuptype = ' + CASE WHEN @CurrentBackupType = 'FULL' THEN '''Full''' WHEN @CurrentBackupType = 'DIFF' THEN '''Differential''' WHEN @CurrentBackupType = 'LOG' THEN '''Log''' END\n" +
"          IF @ReadWriteFileGroups = 'Y' AND @CurrentDatabaseName <> 'master' SET @CurrentCommand += ', @readwritefilegroups = 1'\n" +
"          SET @CurrentCommand += ', @checksum = ' + CASE WHEN @CheckSum = 'Y' THEN '1' WHEN @CheckSum = 'N' THEN '0' END\n" +
"          SET @CurrentCommand += ', @copyonly = ' + CASE WHEN @CopyOnly = 'Y' THEN '1' WHEN @CopyOnly = 'N' THEN '0' END\n" +
"          IF @CompressionLevel IS NOT NULL SET @CurrentCommand += ', @compressionlevel = ' + CAST(@CompressionLevel AS nvarchar)\n" +
"          IF @Threads IS NOT NULL SET @CurrentCommand += ', @threads = ' + CAST(@Threads AS nvarchar)\n" +
"          IF @Init = 'Y' SET @CurrentCommand += ', @overwrite = 1'\n" +
"          IF @Description IS NOT NULL SET @CurrentCommand += ', @desc = N''' + REPLACE(@Description,'''','''''') + ''''\n" +
"\n" +
"          IF @EncryptionAlgorithm IS NOT NULL SET @CurrentCommand += ', @encryptiontype = N''' + CASE\n" +
"          WHEN @EncryptionAlgorithm = 'AES_128' THEN 'AES128'\n" +
"          WHEN @EncryptionAlgorithm = 'AES_256' THEN 'AES256'\n" +
"          END + ''''\n" +
"\n" +
"          IF @EncryptionKey IS NOT NULL SET @CurrentCommand += ', @encryptedbackuppassword = N''' + REPLACE(@EncryptionKey,'''','''''') + ''''\n" +
"          SET @CurrentCommand += ' IF @ReturnCode <> 0 RAISERROR(''Error performing SQLsafe backup.'', 16, 1)'\n" +
"        END\n" +
"\n" +
"        IF @BackupSoftware = 'DATA_DOMAIN_BOOST'\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"          SET @CurrentCommandType = 'emc_run_backup'\n" +
"\n" +
"          SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.emc_run_backup '''\n" +
"\n" +
"          SET @CurrentCommand += ' -c ' + CASE WHEN @CurrentAvailabilityGroup IS NOT NULL THEN @Cluster ELSE CAST(SERVERPROPERTY('MachineName') AS nvarchar) END\n" +
"\n" +
"          SET @CurrentCommand += ' -l ' + CASE\n" +
"          WHEN @CurrentBackupType = 'FULL' THEN 'full'\n" +
"          WHEN @CurrentBackupType = 'DIFF' THEN 'diff'\n" +
"          WHEN @CurrentBackupType = 'LOG' THEN 'incr'\n" +
"          END\n" +
"\n" +
"          IF @NoRecovery = 'Y' SET @CurrentCommand += ' -H'\n" +
"\n" +
"          IF @CleanupTime IS NOT NULL SET @CurrentCommand += ' -y +' + CAST(@CleanupTime/24 + CASE WHEN @CleanupTime%24 > 0 THEN 1 ELSE 0 END AS nvarchar) + 'd'\n" +
"\n" +
"          IF @CheckSum = 'Y' SET @CurrentCommand += ' -k'\n" +
"\n" +
"          SET @CurrentCommand += ' -S ' + CAST(@CurrentNumberOfFiles AS nvarchar)\n" +
"\n" +
"          IF @Description IS NOT NULL SET @CurrentCommand += ' -b \"' + REPLACE(@Description,'''','''''') + '\"'\n" +
"\n" +
"          IF @BufferCount IS NOT NULL SET @CurrentCommand += ' -O \"BUFFERCOUNT=' + CAST(@BufferCount AS nvarchar) + '\"'\n" +
"\n" +
"          IF @ReadWriteFileGroups = 'Y' AND @CurrentDatabaseName <> 'master' SET @CurrentCommand += ' -O \"READ_WRITE_FILEGROUPS\"'\n" +
"\n" +
"          IF @DataDomainBoostHost IS NOT NULL SET @CurrentCommand += ' -a \"NSR_DFA_SI_DD_HOST=' + REPLACE(@DataDomainBoostHost,'''','''''') + '\"'\n" +
"          IF @DataDomainBoostUser IS NOT NULL SET @CurrentCommand += ' -a \"NSR_DFA_SI_DD_USER=' + REPLACE(@DataDomainBoostUser,'''','''''') + '\"'\n" +
"          IF @DataDomainBoostDevicePath IS NOT NULL SET @CurrentCommand += ' -a \"NSR_DFA_SI_DEVICE_PATH=' + REPLACE(@DataDomainBoostDevicePath,'''','''''') + '\"'\n" +
"          IF @DataDomainBoostLockboxPath IS NOT NULL SET @CurrentCommand += ' -a \"NSR_DFA_SI_DD_LOCKBOX_PATH=' + REPLACE(@DataDomainBoostLockboxPath,'''','''''') + '\"'\n" +
"          SET @CurrentCommand += ' -a \"NSR_SKIP_NON_BACKUPABLE_STATE_DB=TRUE\"'\n" +
"          SET @CurrentCommand += ' -a \"BACKUP_PROMOTION=NONE\"'\n" +
"          IF @CopyOnly = 'Y' SET @CurrentCommand += ' -a \"NSR_COPY_ONLY=TRUE\"'\n" +
"\n" +
"          IF SERVERPROPERTY('InstanceName') IS NULL SET @CurrentCommand += ' \"MSSQL' + ':' + REPLACE(REPLACE(@CurrentDatabaseName,'''',''''''),'.','\\.') + '\"'\n" +
"          IF SERVERPROPERTY('InstanceName') IS NOT NULL SET @CurrentCommand += ' \"MSSQL$' + CAST(SERVERPROPERTY('InstanceName') AS nvarchar) + ':' + REPLACE(REPLACE(@CurrentDatabaseName,'''',''''''),'.','\\.') + '\"'\n" +
"\n" +
"          SET @CurrentCommand += ''''\n" +
"\n" +
"          SET @CurrentCommand += ' IF @ReturnCode <> 0 RAISERROR(''Error performing Data Domain Boost backup.'', 16, 1)'\n" +
"        END\n" +
"\n" +
"        EXECUTE @CurrentCommandOutput = dbo.CommandExecute @DatabaseContext = @CurrentDatabaseContext, @Command = @CurrentCommand, @CommandType = @CurrentCommandType, @Mode = 1, @DatabaseName = @CurrentDatabaseName, @LogToTable = @LogToTable, @Execute = @Execute\n" +
"        SET @Error = @@ERROR\n" +
"        IF @Error <> 0 SET @CurrentCommandOutput = @Error\n" +
"        IF @CurrentCommandOutput <> 0 SET @ReturnCode = @CurrentCommandOutput\n" +
"        SET @CurrentBackupOutput = @CurrentCommandOutput\n" +
"      END\n" +
"\n" +
"      -- Verify the backup\n" +
"      IF @CurrentBackupOutput = 0 AND @Verify = 'Y'\n" +
"      BEGIN\n" +
"        WHILE (1 = 1)\n" +
"        BEGIN\n" +
"          SELECT TOP 1 @CurrentBackupSetID = ID,\n" +
"                       @CurrentIsMirror = Mirror\n" +
"          FROM @CurrentBackupSet\n" +
"          WHERE VerifyCompleted = 0\n" +
"          ORDER BY ID ASC\n" +
"\n" +
"          IF @@ROWCOUNT = 0\n" +
"          BEGIN\n" +
"            BREAK\n" +
"          END\n" +
"\n" +
"          IF @BackupSoftware IS NULL\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"            SET @CurrentCommandType = 'RESTORE_VERIFYONLY'\n" +
"\n" +
"            SET @CurrentCommand = 'RESTORE VERIFYONLY FROM'\n" +
"\n" +
"            SELECT @CurrentCommand += ' ' + [Type] + ' = N''' + REPLACE(FilePath,'''','''''') + '''' + CASE WHEN ROW_NUMBER() OVER (ORDER BY FilePath ASC) <> @CurrentNumberOfFiles THEN ',' ELSE '' END\n" +
"            FROM @CurrentFiles\n" +
"            WHERE Mirror = @CurrentIsMirror\n" +
"            ORDER BY FilePath ASC\n" +
"\n" +
"            SET @CurrentCommand += ' WITH '\n" +
"            IF @CheckSum = 'Y' SET @CurrentCommand += 'CHECKSUM'\n" +
"            IF @CheckSum = 'N' SET @CurrentCommand += 'NO_CHECKSUM'\n" +
"            IF @URL IS NOT NULL AND @Credential IS NOT NULL SET @CurrentCommand += ', CREDENTIAL = N''' + REPLACE(@Credential,'''','''''') + ''''\n" +
"          END\n" +
"\n" +
"          IF @BackupSoftware = 'LITESPEED'\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"            SET @CurrentCommandType = 'xp_restore_verifyonly'\n" +
"\n" +
"            SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.xp_restore_verifyonly'\n" +
"\n" +
"            SELECT @CurrentCommand += ' @filename = N''' + REPLACE(FilePath,'''','''''') + '''' + CASE WHEN ROW_NUMBER() OVER (ORDER BY FilePath ASC) <> @CurrentNumberOfFiles THEN ',' ELSE '' END\n" +
"            FROM @CurrentFiles\n" +
"            WHERE Mirror = @CurrentIsMirror\n" +
"            ORDER BY FilePath ASC\n" +
"\n" +
"            SET @CurrentCommand += ', @with = '''\n" +
"            IF @CheckSum = 'Y' SET @CurrentCommand += 'CHECKSUM'\n" +
"            IF @CheckSum = 'N' SET @CurrentCommand += 'NO_CHECKSUM'\n" +
"            SET @CurrentCommand += ''''\n" +
"            IF @EncryptionKey IS NOT NULL SET @CurrentCommand += ', @encryptionkey = N''' + REPLACE(@EncryptionKey,'''','''''') + ''''\n" +
"\n" +
"            SET @CurrentCommand += ' IF @ReturnCode <> 0 RAISERROR(''Error verifying LiteSpeed backup.'', 16, 1)'\n" +
"          END\n" +
"\n" +
"          IF @BackupSoftware = 'SQLBACKUP'\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"            SET @CurrentCommandType = 'sqlbackup'\n" +
"\n" +
"            SET @CurrentCommand = 'RESTORE VERIFYONLY FROM'\n" +
"\n" +
"            SELECT @CurrentCommand += ' DISK = N''' + REPLACE(FilePath,'''','''''') + '''' + CASE WHEN ROW_NUMBER() OVER (ORDER BY FilePath ASC) <> @CurrentNumberOfFiles THEN ',' ELSE '' END\n" +
"            FROM @CurrentFiles\n" +
"            WHERE Mirror = @CurrentIsMirror\n" +
"            ORDER BY FilePath ASC\n" +
"\n" +
"            SET @CurrentCommand += ' WITH '\n" +
"            IF @CheckSum = 'Y' SET @CurrentCommand += 'CHECKSUM'\n" +
"            IF @CheckSum = 'N' SET @CurrentCommand += 'NO_CHECKSUM'\n" +
"            IF @EncryptionKey IS NOT NULL SET @CurrentCommand += ', PASSWORD = N''' + REPLACE(@EncryptionKey,'''','''''') + ''''\n" +
"\n" +
"            SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.sqlbackup N''-SQL \"' + REPLACE(@CurrentCommand,'''','''''') + '\"''' + ' IF @ReturnCode <> 0 RAISERROR(''Error verifying SQLBackup backup.'', 16, 1)'\n" +
"          END\n" +
"\n" +
"          IF @BackupSoftware = 'SQLSAFE'\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"            SET @CurrentCommandType = 'xp_ss_verify'\n" +
"\n" +
"            SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.xp_ss_verify @database = N''' + REPLACE(@CurrentDatabaseName,'''','''''') + ''''\n" +
"\n" +
"            SELECT @CurrentCommand += ', ' + CASE WHEN ROW_NUMBER() OVER (ORDER BY FilePath ASC) = 1 THEN '@filename' ELSE '@backupfile' END + ' = N''' + REPLACE(FilePath,'''','''''') + ''''\n" +
"            FROM @CurrentFiles\n" +
"            WHERE Mirror = @CurrentIsMirror\n" +
"            ORDER BY FilePath ASC\n" +
"\n" +
"            SET @CurrentCommand += ' IF @ReturnCode <> 0 RAISERROR(''Error verifying SQLsafe backup.'', 16, 1)'\n" +
"          END\n" +
"\n" +
"          EXECUTE @CurrentCommandOutput = dbo.CommandExecute @DatabaseContext = @CurrentDatabaseContext, @Command = @CurrentCommand, @CommandType = @CurrentCommandType, @Mode = 1, @DatabaseName = @CurrentDatabaseName, @LogToTable = @LogToTable, @Execute = @Execute\n" +
"          SET @Error = @@ERROR\n" +
"          IF @Error <> 0 SET @CurrentCommandOutput = @Error\n" +
"          IF @CurrentCommandOutput <> 0 SET @ReturnCode = @CurrentCommandOutput\n" +
"\n" +
"          UPDATE @CurrentBackupSet\n" +
"          SET VerifyCompleted = 1,\n" +
"              VerifyOutput = @CurrentCommandOutput\n" +
"          WHERE ID = @CurrentBackupSetID\n" +
"\n" +
"          SET @CurrentBackupSetID = NULL\n" +
"          SET @CurrentIsMirror = NULL\n" +
"\n" +
"          SET @CurrentDatabaseContext = NULL\n" +
"          SET @CurrentCommand = NULL\n" +
"          SET @CurrentCommandOutput = NULL\n" +
"          SET @CurrentCommandType = NULL\n" +
"        END\n" +
"      END\n" +
"\n" +
"      IF @CleanupMode = 'AFTER_BACKUP'\n" +
"      BEGIN\n" +
"        INSERT INTO @CurrentCleanupDates (CleanupDate, Mirror)\n" +
"        SELECT DATEADD(hh,-(@CleanupTime),SYSDATETIME()), 0\n" +
"\n" +
"        IF NOT EXISTS(SELECT * FROM @CurrentCleanupDates WHERE (Mirror = 0 OR Mirror IS NULL) AND CleanupDate IS NULL)\n" +
"        BEGIN\n" +
"          UPDATE @CurrentDirectories\n" +
"          SET CleanupDate = (SELECT MIN(CleanupDate)\n" +
"                             FROM @CurrentCleanupDates\n" +
"                             WHERE (Mirror = 0 OR Mirror IS NULL)),\n" +
"              CleanupMode = 'AFTER_BACKUP'\n" +
"          WHERE Mirror = 0\n" +
"        END\n" +
"      END\n" +
"\n" +
"      IF @MirrorCleanupMode = 'AFTER_BACKUP'\n" +
"      BEGIN\n" +
"        INSERT INTO @CurrentCleanupDates (CleanupDate, Mirror)\n" +
"        SELECT DATEADD(hh,-(@MirrorCleanupTime),SYSDATETIME()), 1\n" +
"\n" +
"        IF NOT EXISTS(SELECT * FROM @CurrentCleanupDates WHERE (Mirror = 1 OR Mirror IS NULL) AND CleanupDate IS NULL)\n" +
"        BEGIN\n" +
"          UPDATE @CurrentDirectories\n" +
"          SET CleanupDate = (SELECT MIN(CleanupDate)\n" +
"                             FROM @CurrentCleanupDates\n" +
"                             WHERE (Mirror = 1 OR Mirror IS NULL)),\n" +
"              CleanupMode = 'AFTER_BACKUP'\n" +
"          WHERE Mirror = 1\n" +
"        END\n" +
"      END\n";
    }
    
    
    public String part8(){
        return "      -- Delete old backup files, after backup\n" +
"      IF ((@CurrentBackupOutput = 0 AND @Verify = 'N')\n" +
"      OR (@CurrentBackupOutput = 0 AND @Verify = 'Y' AND NOT EXISTS (SELECT * FROM @CurrentBackupSet WHERE VerifyOutput <> 0 OR VerifyOutput IS NULL)))\n" +
"      AND (@BackupSoftware <> 'DATA_DOMAIN_BOOST' OR @BackupSoftware IS NULL)\n" +
"      AND @CurrentBackupType = @BackupType\n" +
"      BEGIN\n" +
"        WHILE (1 = 1)\n" +
"        BEGIN\n" +
"          SELECT TOP 1 @CurrentDirectoryID = ID,\n" +
"                       @CurrentDirectoryPath = DirectoryPath,\n" +
"                       @CurrentCleanupDate = CleanupDate\n" +
"          FROM @CurrentDirectories\n" +
"          WHERE CleanupDate IS NOT NULL\n" +
"          AND CleanupMode = 'AFTER_BACKUP'\n" +
"          AND CleanupCompleted = 0\n" +
"          ORDER BY ID ASC\n" +
"\n" +
"          IF @@ROWCOUNT = 0\n" +
"          BEGIN\n" +
"            BREAK\n" +
"          END\n" +
"\n" +
"          IF @BackupSoftware IS NULL\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"            SET @CurrentCommandType = 'xp_delete_file'\n" +
"\n" +
"            SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.xp_delete_file 0, N''' + REPLACE(@CurrentDirectoryPath,'''','''''') + ''', ''' + @CurrentFileExtension + ''', ''' + CONVERT(nvarchar(19),@CurrentCleanupDate,126) + ''' IF @ReturnCode <> 0 RAISERROR(''Error deleting files.'', 16, 1)'\n" +
"          END\n" +
"\n" +
"          IF @BackupSoftware = 'LITESPEED'\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"            SET @CurrentCommandType = 'xp_slssqlmaint'\n" +
"\n" +
"            SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.xp_slssqlmaint N''-MAINTDEL -DELFOLDER \"' + REPLACE(@CurrentDirectoryPath,'''','''''') + '\" -DELEXTENSION \"' + @CurrentFileExtension + '\" -DELUNIT \"' + CAST(DATEDIFF(mi,@CurrentCleanupDate,SYSDATETIME()) + 1 AS nvarchar) + '\" -DELUNITTYPE \"minutes\" -DELUSEAGE'' IF @ReturnCode <> 0 RAISERROR(''Error deleting LiteSpeed backup files.'', 16, 1)'\n" +
"          END\n" +
"\n" +
"          IF @BackupSoftware = 'SQLBACKUP'\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"            SET @CurrentCommandType = 'sqbutility'\n" +
"\n" +
"            SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.sqbutility 1032, N''' + REPLACE(@CurrentDatabaseName,'''','''''') + ''', N''' + REPLACE(@CurrentDirectoryPath,'''','''''') + ''', ''' + CASE WHEN @CurrentBackupType = 'FULL' THEN 'D' WHEN @CurrentBackupType = 'DIFF' THEN 'I' WHEN @CurrentBackupType = 'LOG' THEN 'L' END + ''', ''' + CAST(DATEDIFF(hh,@CurrentCleanupDate,SYSDATETIME()) + 1 AS nvarchar) + 'h'', ' + ISNULL('''' + REPLACE(@EncryptionKey,'''','''''') + '''','NULL') + ' IF @ReturnCode <> 0 RAISERROR(''Error deleting SQLBackup backup files.'', 16, 1)'\n" +
"          END\n" +
"\n" +
"          IF @BackupSoftware = 'SQLSAFE'\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = 'master'\n" +
"\n" +
"            SET @CurrentCommandType = 'xp_ss_delete'\n" +
"\n" +
"            SET @CurrentCommand = 'DECLARE @ReturnCode int EXECUTE @ReturnCode = dbo.xp_ss_delete @filename = N''' + REPLACE(@CurrentDirectoryPath,'''','''''') + '\\*.' + @CurrentFileExtension + ''', @age = ''' + CAST(DATEDIFF(mi,@CurrentCleanupDate,SYSDATETIME()) + 1 AS nvarchar) + 'Minutes'' IF @ReturnCode <> 0 RAISERROR(''Error deleting SQLsafe backup files.'', 16, 1)'\n" +
"          END\n" +
"\n" +
"          EXECUTE @CurrentCommandOutput = dbo.CommandExecute @DatabaseContext = @CurrentDatabaseContext, @Command = @CurrentCommand, @CommandType = @CurrentCommandType, @Mode = 1, @DatabaseName = @CurrentDatabaseName, @LogToTable = @LogToTable, @Execute = @Execute\n" +
"          SET @Error = @@ERROR\n" +
"          IF @Error <> 0 SET @CurrentCommandOutput = @Error\n" +
"          IF @CurrentCommandOutput <> 0 SET @ReturnCode = @CurrentCommandOutput\n" +
"\n" +
"          UPDATE @CurrentDirectories\n" +
"          SET CleanupCompleted = 1,\n" +
"              CleanupOutput = @CurrentCommandOutput\n" +
"          WHERE ID = @CurrentDirectoryID\n" +
"\n" +
"          SET @CurrentDirectoryID = NULL\n" +
"          SET @CurrentDirectoryPath = NULL\n" +
"          SET @CurrentCleanupDate = NULL\n" +
"\n" +
"          SET @CurrentDatabaseContext = NULL\n" +
"          SET @CurrentCommand = NULL\n" +
"          SET @CurrentCommandOutput = NULL\n" +
"          SET @CurrentCommandType = NULL\n" +
"        END\n" +
"      END\n" +
"    END\n" +
"\n" +
"    IF @CurrentDatabaseState = 'SUSPECT'\n" +
"    BEGIN\n" +
"      SET @ErrorMessage = 'The database ' + QUOTENAME(@CurrentDatabaseName) + ' is in a SUSPECT state.'\n" +
"      RAISERROR('%s',16,1,@ErrorMessage) WITH NOWAIT\n" +
"      SET @Error = @@ERROR\n" +
"      RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    -- Update that the database is completed\n" +
"    IF @DatabasesInParallel = 'Y'\n" +
"    BEGIN\n" +
"      UPDATE dbo.QueueDatabase\n" +
"      SET DatabaseEndTime = SYSDATETIME()\n" +
"      WHERE QueueID = @QueueID\n" +
"      AND DatabaseName = @CurrentDatabaseName\n" +
"    END\n" +
"    ELSE\n" +
"    BEGIN\n" +
"      UPDATE @tmpDatabases\n" +
"      SET Completed = 1\n" +
"      WHERE Selected = 1\n" +
"      AND Completed = 0\n" +
"      AND ID = @CurrentDBID\n" +
"    END\n" +
"\n" +
"    -- Clear variables\n" +
"    SET @CurrentDBID = NULL\n" +
"    SET @CurrentDatabaseName = NULL\n" +
"\n" +
"    SET @CurrentDatabase_sp_executesql = NULL\n" +
"\n" +
"    SET @CurrentUserAccess = NULL\n" +
"    SET @CurrentIsReadOnly = NULL\n" +
"    SET @CurrentDatabaseState = NULL\n" +
"    SET @CurrentInStandby = NULL\n" +
"    SET @CurrentRecoveryModel = NULL\n" +
"    SET @CurrentIsEncrypted = NULL\n" +
"    SET @CurrentDatabaseSize = NULL\n" +
"\n" +
"    SET @CurrentBackupType = NULL\n" +
"    SET @CurrentMaxTransferSize = NULL\n" +
"    SET @CurrentNumberOfFiles = NULL\n" +
"    SET @CurrentFileExtension = NULL\n" +
"    SET @CurrentFileNumber = NULL\n" +
"    SET @CurrentDifferentialBaseLSN = NULL\n" +
"    SET @CurrentDifferentialBaseIsSnapshot = NULL\n" +
"    SET @CurrentLogLSN = NULL\n" +
"    SET @CurrentLatestBackup = NULL\n" +
"    SET @CurrentDatabaseNameFS = NULL\n" +
"    SET @CurrentDirectoryStructure = NULL\n" +
"    SET @CurrentDatabaseFileName = NULL\n" +
"    SET @CurrentMaxFilePathLength = NULL\n" +
"    SET @CurrentDate = NULL\n" +
"    SET @CurrentCleanupDate = NULL\n" +
"    SET @CurrentIsDatabaseAccessible = NULL\n" +
"    SET @CurrentReplicaID = NULL\n" +
"    SET @CurrentAvailabilityGroupID = NULL\n" +
"    SET @CurrentAvailabilityGroup = NULL\n" +
"    SET @CurrentAvailabilityGroupRole = NULL\n" +
"    SET @CurrentAvailabilityGroupBackupPreference = NULL\n" +
"    SET @CurrentIsPreferredBackupReplica = NULL\n" +
"    SET @CurrentDatabaseMirroringRole = NULL\n" +
"    SET @CurrentLogShippingRole = NULL\n" +
"    SET @CurrentLastLogBackup = NULL\n" +
"    SET @CurrentLogSizeSinceLastLogBackup = NULL\n" +
"    SET @CurrentAllocatedExtentPageCount = NULL\n" +
"    SET @CurrentModifiedExtentPageCount = NULL\n" +
"\n" +
"    SET @CurrentDatabaseContext = NULL\n" +
"    SET @CurrentCommand = NULL\n" +
"    SET @CurrentCommandOutput = NULL\n" +
"    SET @CurrentCommandType = NULL\n" +
"\n" +
"    SET @CurrentBackupOutput = NULL\n" +
"\n" +
"    DELETE FROM @CurrentDirectories\n" +
"    DELETE FROM @CurrentURLs\n" +
"    DELETE FROM @CurrentFiles\n" +
"    DELETE FROM @CurrentCleanupDates\n" +
"    DELETE FROM @CurrentBackupSet\n" +
"\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Log completing information                                                                 //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  Logging:\n" +
"  SET @EndMessage = 'Date and time: ' + CONVERT(nvarchar,SYSDATETIME(),120)\n" +
"  RAISERROR('%s',10,1,@EndMessage) WITH NOWAIT\n" +
"\n" +
"  RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"  IF @ReturnCode <> 0\n" +
"  BEGIN\n" +
"    RETURN @ReturnCode\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"END\n" +
"GO\n" +
"SET ANSI_NULLS ON\n" +
"GO\n" +
"SET QUOTED_IDENTIFIER ON\n" +
"GO\n" +
"IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[DatabaseIntegrityCheck]') AND type in (N'P', N'PC'))\n" +
"BEGIN\n" +
"EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[DatabaseIntegrityCheck] AS'\n" +
"END\n" +
"GO\n" +
"ALTER PROCEDURE [dbo].[DatabaseIntegrityCheck]\n" +
"\n" +
"@Databases nvarchar(max) = NULL,\n" +
"@CheckCommands nvarchar(max) = 'CHECKDB',\n" +
"@PhysicalOnly nvarchar(max) = 'N',\n" +
"@DataPurity nvarchar(max) = 'N',\n" +
"@NoIndex nvarchar(max) = 'N',\n" +
"@ExtendedLogicalChecks nvarchar(max) = 'N',\n" +
"@TabLock nvarchar(max) = 'N',\n" +
"@FileGroups nvarchar(max) = NULL,\n" +
"@Objects nvarchar(max) = NULL,\n" +
"@MaxDOP int = NULL,\n" +
"@AvailabilityGroups nvarchar(max) = NULL,\n" +
"@AvailabilityGroupReplicas nvarchar(max) = 'ALL',\n" +
"@Updateability nvarchar(max) = 'ALL',\n" +
"@TimeLimit int = NULL,\n" +
"@LockTimeout int = NULL,\n" +
"@LockMessageSeverity int = 16,\n" +
"@StringDelimiter nvarchar(max) = ',',\n" +
"@DatabaseOrder nvarchar(max) = NULL,\n" +
"@DatabasesInParallel nvarchar(max) = 'N',\n" +
"@LogToTable nvarchar(max) = 'N',\n" +
"@Execute nvarchar(max) = 'Y'\n" +
"\n" +
"AS\n" +
"\n" +
"BEGIN\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Source:  https://ola.hallengren.com                                                        //--\n" +
"  --// License: https://ola.hallengren.com/license.html                                           //--\n" +
"  --// GitHub:  https://github.com/olahallengren/sql-server-maintenance-solution                  //--\n" +
"  --// Version: 2020-12-31 18:58:56                                                               //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET NOCOUNT ON\n" +
"\n" +
"  DECLARE @StartMessage nvarchar(max)\n" +
"  DECLARE @EndMessage nvarchar(max)\n" +
"  DECLARE @DatabaseMessage nvarchar(max)\n" +
"  DECLARE @ErrorMessage nvarchar(max)\n" +
"  DECLARE @Severity int\n" +
"\n" +
"  DECLARE @StartTime datetime2 = SYSDATETIME()\n" +
"  DECLARE @SchemaName nvarchar(max) = OBJECT_SCHEMA_NAME(@@PROCID)\n" +
"  DECLARE @ObjectName nvarchar(max) = OBJECT_NAME(@@PROCID)\n" +
"  DECLARE @VersionTimestamp nvarchar(max) = SUBSTRING(OBJECT_DEFINITION(@@PROCID),CHARINDEX('--// Version: ',OBJECT_DEFINITION(@@PROCID)) + LEN('--// Version: ') + 1, 19)\n" +
"  DECLARE @Parameters nvarchar(max)\n" +
"\n" +
"  DECLARE @HostPlatform nvarchar(max)\n" +
"\n" +
"  DECLARE @QueueID int\n" +
"  DECLARE @QueueStartTime datetime2\n" +
"\n" +
"  DECLARE @CurrentDBID int\n" +
"  DECLARE @CurrentDatabaseName nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentDatabase_sp_executesql nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentUserAccess nvarchar(max)\n" +
"  DECLARE @CurrentIsReadOnly bit\n" +
"  DECLARE @CurrentDatabaseState nvarchar(max)\n" +
"  DECLARE @CurrentInStandby bit\n" +
"  DECLARE @CurrentRecoveryModel nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentIsDatabaseAccessible bit\n" +
"  DECLARE @CurrentReplicaID uniqueidentifier\n" +
"  DECLARE @CurrentAvailabilityGroupID uniqueidentifier\n" +
"  DECLARE @CurrentAvailabilityGroup nvarchar(max)\n" +
"  DECLARE @CurrentAvailabilityGroupRole nvarchar(max)\n" +
"  DECLARE @CurrentAvailabilityGroupBackupPreference nvarchar(max)\n" +
"  DECLARE @CurrentSecondaryRoleAllowConnections nvarchar(max)\n" +
"  DECLARE @CurrentIsPreferredBackupReplica bit\n" +
"  DECLARE @CurrentDatabaseMirroringRole nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentFGID int\n" +
"  DECLARE @CurrentFileGroupID int\n" +
"  DECLARE @CurrentFileGroupName nvarchar(max)\n" +
"  DECLARE @CurrentFileGroupExists bit\n" +
"\n" +
"  DECLARE @CurrentOID int\n" +
"  DECLARE @CurrentSchemaID int\n" +
"  DECLARE @CurrentSchemaName nvarchar(max)\n" +
"  DECLARE @CurrentObjectID int\n" +
"  DECLARE @CurrentObjectName nvarchar(max)\n" +
"  DECLARE @CurrentObjectType nvarchar(max)\n" +
"  DECLARE @CurrentObjectExists bit\n" +
"\n" +
"  DECLARE @CurrentDatabaseContext nvarchar(max)\n" +
"  DECLARE @CurrentCommand nvarchar(max)\n" +
"  DECLARE @CurrentCommandOutput int\n" +
"  DECLARE @CurrentCommandType nvarchar(max)\n" +
"\n" +
"  DECLARE @Errors TABLE (ID int IDENTITY PRIMARY KEY,\n" +
"                         [Message] nvarchar(max) NOT NULL,\n" +
"                         Severity int NOT NULL,\n" +
"                         [State] int)\n" +
"\n" +
"  DECLARE @CurrentMessage nvarchar(max)\n" +
"  DECLARE @CurrentSeverity int\n" +
"  DECLARE @CurrentState int\n" +
"\n" +
"  DECLARE @tmpDatabases TABLE (ID int IDENTITY,\n" +
"                               DatabaseName nvarchar(max),\n" +
"                               DatabaseType nvarchar(max),\n" +
"                               AvailabilityGroup bit,\n" +
"                               [Snapshot] bit,\n" +
"                               StartPosition int,\n" +
"                               LastCommandTime datetime2,\n" +
"                               DatabaseSize bigint,\n" +
"                               LastGoodCheckDbTime datetime2,\n" +
"                               [Order] int,\n" +
"                               Selected bit,\n" +
"                               Completed bit,\n" +
"                               PRIMARY KEY(Selected, Completed, [Order], ID))\n" +
"\n" +
"  DECLARE @tmpAvailabilityGroups TABLE (ID int IDENTITY PRIMARY KEY,\n" +
"                                        AvailabilityGroupName nvarchar(max),\n" +
"                                        StartPosition int,\n" +
"                                        Selected bit)\n" +
"\n" +
"  DECLARE @tmpDatabasesAvailabilityGroups TABLE (DatabaseName nvarchar(max),\n" +
"                                                 AvailabilityGroupName nvarchar(max))\n" +
"\n" +
"  DECLARE @tmpFileGroups TABLE (ID int IDENTITY,\n" +
"                                FileGroupID int,\n" +
"                                FileGroupName nvarchar(max),\n" +
"                                StartPosition int,\n" +
"                                [Order] int,\n" +
"                                Selected bit,\n" +
"                                Completed bit,\n" +
"                                PRIMARY KEY(Selected, Completed, [Order], ID))\n" +
"\n" +
"  DECLARE @tmpObjects TABLE (ID int IDENTITY,\n" +
"                             SchemaID int,\n" +
"                             SchemaName nvarchar(max),\n" +
"                             ObjectID int,\n" +
"                             ObjectName nvarchar(max),\n" +
"                             ObjectType nvarchar(max),\n" +
"                             StartPosition int,\n" +
"                             [Order] int,\n" +
"                             Selected bit,\n" +
"                             Completed bit,\n" +
"                             PRIMARY KEY(Selected, Completed, [Order], ID))\n" +
"\n" +
"  DECLARE @SelectedDatabases TABLE (DatabaseName nvarchar(max),\n" +
"                                    DatabaseType nvarchar(max),\n" +
"                                    AvailabilityGroup nvarchar(max),\n" +
"                                    StartPosition int,\n" +
"                                    Selected bit)\n" +
"\n" +
"  DECLARE @SelectedAvailabilityGroups TABLE (AvailabilityGroupName nvarchar(max),\n" +
"                                             StartPosition int,\n" +
"                                             Selected bit)\n" +
"\n" +
"  DECLARE @SelectedFileGroups TABLE (DatabaseName nvarchar(max),\n" +
"                                     FileGroupName nvarchar(max),\n" +
"                                     StartPosition int,\n" +
"                                     Selected bit)\n" +
"\n" +
"  DECLARE @SelectedObjects TABLE (DatabaseName nvarchar(max),\n" +
"                                  SchemaName nvarchar(max),\n" +
"                                  ObjectName nvarchar(max),\n" +
"                                  StartPosition int,\n" +
"                                  Selected bit)\n" +
"\n" +
"  DECLARE @SelectedCheckCommands TABLE (CheckCommand nvarchar(max))\n" +
"\n" +
"  DECLARE @Error int = 0\n" +
"  DECLARE @ReturnCode int = 0\n" +
"\n" +
"  DECLARE @EmptyLine nvarchar(max) = CHAR(9)\n" +
"\n" +
"  DECLARE @Version numeric(18,10) = CAST(LEFT(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max)),CHARINDEX('.',CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max))) - 1) + '.' + REPLACE(RIGHT(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max)), LEN(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max))) - CHARINDEX('.',CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max)))),'.','') AS numeric(18,10))\n" +
"\n" +
"  IF @Version >= 14\n" +
"  BEGIN\n" +
"    SELECT @HostPlatform = host_platform\n" +
"    FROM sys.dm_os_host_info\n" +
"  END\n" +
"  ELSE\n" +
"  BEGIN\n" +
"    SET @HostPlatform = 'Windows'\n" +
"  END\n" +
"\n" +
"  DECLARE @AmazonRDS bit = CASE WHEN DB_ID('rdsadmin') IS NOT NULL AND SUSER_SNAME(0x01) = 'rdsa' THEN 1 ELSE 0 END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Log initial information                                                                    //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @Parameters = '@Databases = ' + ISNULL('''' + REPLACE(@Databases,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @CheckCommands = ' + ISNULL('''' + REPLACE(@CheckCommands,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @PhysicalOnly = ' + ISNULL('''' + REPLACE(@PhysicalOnly,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @DataPurity = ' + ISNULL('''' + REPLACE(@DataPurity,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @NoIndex = ' + ISNULL('''' + REPLACE(@NoIndex,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @ExtendedLogicalChecks = ' + ISNULL('''' + REPLACE(@ExtendedLogicalChecks,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @TabLock = ' + ISNULL('''' + REPLACE(@TabLock,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @FileGroups = ' + ISNULL('''' + REPLACE(@FileGroups,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Objects = ' + ISNULL('''' + REPLACE(@Objects,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @MaxDOP = ' + ISNULL(CAST(@MaxDOP AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @AvailabilityGroups = ' + ISNULL('''' + REPLACE(@AvailabilityGroups,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @AvailabilityGroupReplicas = ' + ISNULL('''' + REPLACE(@AvailabilityGroupReplicas,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Updateability = ' + ISNULL('''' + REPLACE(@Updateability,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @TimeLimit = ' + ISNULL(CAST(@TimeLimit AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @LockTimeout = ' + ISNULL(CAST(@LockTimeout AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @LockMessageSeverity = ' + ISNULL(CAST(@LockMessageSeverity AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @StringDelimiter = ' + ISNULL('''' + REPLACE(@StringDelimiter,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @DatabaseOrder = ' + ISNULL('''' + REPLACE(@DatabaseOrder,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @DatabasesInParallel = ' + ISNULL('''' + REPLACE(@DatabasesInParallel,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @LogToTable = ' + ISNULL('''' + REPLACE(@LogToTable,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Execute = ' + ISNULL('''' + REPLACE(@Execute,'''','''''') + '''','NULL')\n" +
"\n" +
"  SET @StartMessage = 'Date and time: ' + CONVERT(nvarchar,@StartTime,120)\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Server: ' + CAST(SERVERPROPERTY('ServerName') AS nvarchar(max))\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Version: ' + CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max))\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Edition: ' + CAST(SERVERPROPERTY('Edition') AS nvarchar(max))\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Platform: ' + @HostPlatform\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Procedure: ' + QUOTENAME(DB_NAME(DB_ID())) + '.' + QUOTENAME(@SchemaName) + '.' + QUOTENAME(@ObjectName)\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Parameters: ' + @Parameters\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Version: ' + @VersionTimestamp\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Source: https://ola.hallengren.com'\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check core requirements                                                                    //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF NOT (SELECT [compatibility_level] FROM sys.databases WHERE database_id = DB_ID()) >= 90\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT  'The database ' + QUOTENAME(DB_NAME(DB_ID())) + ' has to be in compatibility level 90 or higher.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF NOT (SELECT uses_ansi_nulls FROM sys.sql_modules WHERE [object_id] = @@PROCID) = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'ANSI_NULLS has to be set to ON for the stored procedure.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF NOT (SELECT uses_quoted_identifier FROM sys.sql_modules WHERE [object_id] = @@PROCID) = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'QUOTED_IDENTIFIER has to be set to ON for the stored procedure.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'P' AND schemas.[name] = 'dbo' AND objects.[name] = 'CommandExecute')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The stored procedure CommandExecute is missing. Download https://ola.hallengren.com/scripts/CommandExecute.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'P' AND schemas.[name] = 'dbo' AND objects.[name] = 'CommandExecute' AND OBJECT_DEFINITION(objects.[object_id]) NOT LIKE '%@DatabaseContext%')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The stored procedure CommandExecute needs to be updated. Download https://ola.hallengren.com/scripts/CommandExecute.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @LogToTable = 'Y' AND NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'U' AND schemas.[name] = 'dbo' AND objects.[name] = 'CommandLog')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The table CommandLog is missing. Download https://ola.hallengren.com/scripts/CommandLog.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DatabasesInParallel = 'Y' AND NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'U' AND schemas.[name] = 'dbo' AND objects.[name] = 'Queue')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The table Queue is missing. Download https://ola.hallengren.com/scripts/Queue.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DatabasesInParallel = 'Y' AND NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'U' AND schemas.[name] = 'dbo' AND objects.[name] = 'QueueDatabase')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The table QueueDatabase is missing. Download https://ola.hallengren.com/scripts/QueueDatabase.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @@TRANCOUNT <> 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The transaction count is not 0.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select databases                                                                           //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @Databases = REPLACE(@Databases, CHAR(10), '')\n" +
"  SET @Databases = REPLACE(@Databases, CHAR(13), '')\n" +
"\n" +
"  WHILE CHARINDEX(@StringDelimiter + ' ', @Databases) > 0 SET @Databases = REPLACE(@Databases, @StringDelimiter + ' ', @StringDelimiter)\n" +
"  WHILE CHARINDEX(' ' + @StringDelimiter, @Databases) > 0 SET @Databases = REPLACE(@Databases, ' ' + @StringDelimiter, @StringDelimiter)\n" +
"\n" +
"  SET @Databases = LTRIM(RTRIM(@Databases));\n" +
"\n" +
"  WITH Databases1 (StartPosition, EndPosition, DatabaseItem) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Databases, 1), 0), LEN(@Databases) + 1) AS EndPosition,\n" +
"         SUBSTRING(@Databases, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Databases, 1), 0), LEN(@Databases) + 1) - 1) AS DatabaseItem\n" +
"  WHERE @Databases IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Databases, EndPosition + 1), 0), LEN(@Databases) + 1) AS EndPosition,\n" +
"         SUBSTRING(@Databases, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Databases, EndPosition + 1), 0), LEN(@Databases) + 1) - EndPosition - 1) AS DatabaseItem\n" +
"  FROM Databases1\n" +
"  WHERE EndPosition < LEN(@Databases) + 1\n" +
"  ),\n" +
"  Databases2 (DatabaseItem, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN DatabaseItem LIKE '-%' THEN RIGHT(DatabaseItem,LEN(DatabaseItem) - 1) ELSE DatabaseItem END AS DatabaseItem,\n" +
"         StartPosition,\n" +
"         CASE WHEN DatabaseItem LIKE '-%' THEN 0 ELSE 1 END AS Selected\n" +
"  FROM Databases1\n" +
"  ),\n" +
"  Databases3 (DatabaseItem, DatabaseType, AvailabilityGroup, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN DatabaseItem IN('ALL_DATABASES','SYSTEM_DATABASES','USER_DATABASES','AVAILABILITY_GROUP_DATABASES') THEN '%' ELSE DatabaseItem END AS DatabaseItem,\n" +
"         CASE WHEN DatabaseItem = 'SYSTEM_DATABASES' THEN 'S' WHEN DatabaseItem = 'USER_DATABASES' THEN 'U' ELSE NULL END AS DatabaseType,\n" +
"         CASE WHEN DatabaseItem = 'AVAILABILITY_GROUP_DATABASES' THEN 1 ELSE NULL END AvailabilityGroup,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Databases2\n" +
"  ),\n" +
"  Databases4 (DatabaseName, DatabaseType, AvailabilityGroup, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN LEFT(DatabaseItem,1) = '[' AND RIGHT(DatabaseItem,1) = ']' THEN PARSENAME(DatabaseItem,1) ELSE DatabaseItem END AS DatabaseItem,\n" +
"         DatabaseType,\n" +
"         AvailabilityGroup,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Databases3\n" +
"  )\n" +
"  INSERT INTO @SelectedDatabases (DatabaseName, DatabaseType, AvailabilityGroup, StartPosition, Selected)\n" +
"  SELECT DatabaseName,\n" +
"         DatabaseType,\n" +
"         AvailabilityGroup,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Databases4\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  IF @Version >= 11 AND SERVERPROPERTY('IsHadrEnabled') = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @tmpAvailabilityGroups (AvailabilityGroupName, Selected)\n" +
"    SELECT name AS AvailabilityGroupName,\n" +
"           0 AS Selected\n" +
"    FROM sys.availability_groups\n" +
"\n" +
"    INSERT INTO @tmpDatabasesAvailabilityGroups (DatabaseName, AvailabilityGroupName)\n" +
"    SELECT databases.name,\n" +
"           availability_groups.name\n" +
"    FROM sys.databases databases\n" +
"    INNER JOIN sys.availability_replicas availability_replicas ON databases.replica_id = availability_replicas.replica_id\n" +
"    INNER JOIN sys.availability_groups availability_groups ON availability_replicas.group_id = availability_groups.group_id\n" +
"  END\n" +
"\n" +
"  INSERT INTO @tmpDatabases (DatabaseName, DatabaseType, AvailabilityGroup, [Snapshot], [Order], Selected, Completed)\n" +
"  SELECT [name] AS DatabaseName,\n" +
"         CASE WHEN name IN('master','msdb','model') OR is_distributor = 1 THEN 'S' ELSE 'U' END AS DatabaseType,\n" +
"         NULL AS AvailabilityGroup,\n" +
"         CASE WHEN source_database_id IS NOT NULL THEN 1 ELSE 0 END AS [Snapshot],\n" +
"         0 AS [Order],\n" +
"         0 AS Selected,\n" +
"         0 AS Completed\n" +
"  FROM sys.databases\n" +
"  ORDER BY [name] ASC\n" +
"\n" +
"  UPDATE tmpDatabases\n" +
"  SET AvailabilityGroup = CASE WHEN EXISTS (SELECT * FROM @tmpDatabasesAvailabilityGroups WHERE DatabaseName = tmpDatabases.DatabaseName) THEN 1 ELSE 0 END\n" +
"  FROM @tmpDatabases tmpDatabases\n" +
"\n" +
"  UPDATE tmpDatabases\n" +
"  SET tmpDatabases.Selected = SelectedDatabases.Selected\n" +
"  FROM @tmpDatabases tmpDatabases\n" +
"  INNER JOIN @SelectedDatabases SelectedDatabases\n" +
"  ON tmpDatabases.DatabaseName LIKE REPLACE(SelectedDatabases.DatabaseName,'_','[_]')\n" +
"  AND (tmpDatabases.DatabaseType = SelectedDatabases.DatabaseType OR SelectedDatabases.DatabaseType IS NULL)\n" +
"  AND (tmpDatabases.AvailabilityGroup = SelectedDatabases.AvailabilityGroup OR SelectedDatabases.AvailabilityGroup IS NULL)\n" +
"  AND NOT ((tmpDatabases.DatabaseName = 'tempdb' OR tmpDatabases.[Snapshot] = 1) AND tmpDatabases.DatabaseName <> SelectedDatabases.DatabaseName)\n" +
"  WHERE SelectedDatabases.Selected = 1\n" +
"\n" +
"  UPDATE tmpDatabases\n" +
"  SET tmpDatabases.Selected = SelectedDatabases.Selected\n" +
"  FROM @tmpDatabases tmpDatabases\n" +
"  INNER JOIN @SelectedDatabases SelectedDatabases\n" +
"  ON tmpDatabases.DatabaseName LIKE REPLACE(SelectedDatabases.DatabaseName,'_','[_]')\n" +
"  AND (tmpDatabases.DatabaseType = SelectedDatabases.DatabaseType OR SelectedDatabases.DatabaseType IS NULL)\n" +
"  AND (tmpDatabases.AvailabilityGroup = SelectedDatabases.AvailabilityGroup OR SelectedDatabases.AvailabilityGroup IS NULL)\n" +
"  AND NOT ((tmpDatabases.DatabaseName = 'tempdb' OR tmpDatabases.[Snapshot] = 1) AND tmpDatabases.DatabaseName <> SelectedDatabases.DatabaseName)\n" +
"  WHERE SelectedDatabases.Selected = 0\n" +
"\n" +
"  UPDATE tmpDatabases\n" +
"  SET tmpDatabases.StartPosition = SelectedDatabases2.StartPosition\n" +
"  FROM @tmpDatabases tmpDatabases\n" +
"  INNER JOIN (SELECT tmpDatabases.DatabaseName, MIN(SelectedDatabases.StartPosition) AS StartPosition\n" +
"              FROM @tmpDatabases tmpDatabases\n" +
"              INNER JOIN @SelectedDatabases SelectedDatabases\n" +
"              ON tmpDatabases.DatabaseName LIKE REPLACE(SelectedDatabases.DatabaseName,'_','[_]')\n" +
"              AND (tmpDatabases.DatabaseType = SelectedDatabases.DatabaseType OR SelectedDatabases.DatabaseType IS NULL)\n" +
"              AND (tmpDatabases.AvailabilityGroup = SelectedDatabases.AvailabilityGroup OR SelectedDatabases.AvailabilityGroup IS NULL)\n" +
"              WHERE SelectedDatabases.Selected = 1\n" +
"              GROUP BY tmpDatabases.DatabaseName) SelectedDatabases2\n" +
"  ON tmpDatabases.DatabaseName = SelectedDatabases2.DatabaseName\n" +
"\n" +
"  IF @Databases IS NOT NULL AND (NOT EXISTS(SELECT * FROM @SelectedDatabases) OR EXISTS(SELECT * FROM @SelectedDatabases WHERE DatabaseName IS NULL OR DatabaseName = ''))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Databases is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select availability groups                                                                 //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @AvailabilityGroups IS NOT NULL AND @Version >= 11 AND SERVERPROPERTY('IsHadrEnabled') = 1\n" +
"  BEGIN\n" +
"\n" +
"    SET @AvailabilityGroups = REPLACE(@AvailabilityGroups, CHAR(10), '')\n" +
"    SET @AvailabilityGroups = REPLACE(@AvailabilityGroups, CHAR(13), '')\n" +
"\n" +
"    WHILE CHARINDEX(@StringDelimiter + ' ', @AvailabilityGroups) > 0 SET @AvailabilityGroups = REPLACE(@AvailabilityGroups, @StringDelimiter + ' ', @StringDelimiter)\n" +
"    WHILE CHARINDEX(' ' + @StringDelimiter, @AvailabilityGroups) > 0 SET @AvailabilityGroups = REPLACE(@AvailabilityGroups, ' ' + @StringDelimiter, @StringDelimiter)\n" +
"\n" +
"    SET @AvailabilityGroups = LTRIM(RTRIM(@AvailabilityGroups));\n" +
"\n" +
"    WITH AvailabilityGroups1 (StartPosition, EndPosition, AvailabilityGroupItem) AS\n" +
"    (\n" +
"    SELECT 1 AS StartPosition,\n" +
"           ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @AvailabilityGroups, 1), 0), LEN(@AvailabilityGroups) + 1) AS EndPosition,\n" +
"           SUBSTRING(@AvailabilityGroups, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @AvailabilityGroups, 1), 0), LEN(@AvailabilityGroups) + 1) - 1) AS AvailabilityGroupItem\n" +
"    WHERE @AvailabilityGroups IS NOT NULL\n" +
"    UNION ALL\n" +
"    SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"           ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @AvailabilityGroups, EndPosition + 1), 0), LEN(@AvailabilityGroups) + 1) AS EndPosition,\n" +
"           SUBSTRING(@AvailabilityGroups, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @AvailabilityGroups, EndPosition + 1), 0), LEN(@AvailabilityGroups) + 1) - EndPosition - 1) AS AvailabilityGroupItem\n" +
"    FROM AvailabilityGroups1\n" +
"    WHERE EndPosition < LEN(@AvailabilityGroups) + 1\n" +
"    ),\n" +
"    AvailabilityGroups2 (AvailabilityGroupItem, StartPosition, Selected) AS\n" +
"    (\n" +
"    SELECT CASE WHEN AvailabilityGroupItem LIKE '-%' THEN RIGHT(AvailabilityGroupItem,LEN(AvailabilityGroupItem) - 1) ELSE AvailabilityGroupItem END AS AvailabilityGroupItem,\n" +
"           StartPosition,\n" +
"           CASE WHEN AvailabilityGroupItem LIKE '-%' THEN 0 ELSE 1 END AS Selected\n" +
"    FROM AvailabilityGroups1\n" +
"    ),\n" +
"    AvailabilityGroups3 (AvailabilityGroupItem, StartPosition, Selected) AS\n" +
"    (\n" +
"    SELECT CASE WHEN AvailabilityGroupItem = 'ALL_AVAILABILITY_GROUPS' THEN '%' ELSE AvailabilityGroupItem END AS AvailabilityGroupItem,\n" +
"           StartPosition,\n" +
"           Selected\n" +
"    FROM AvailabilityGroups2\n" +
"    ),\n" +
"    AvailabilityGroups4 (AvailabilityGroupName, StartPosition, Selected) AS\n" +
"    (\n" +
"    SELECT CASE WHEN LEFT(AvailabilityGroupItem,1) = '[' AND RIGHT(AvailabilityGroupItem,1) = ']' THEN PARSENAME(AvailabilityGroupItem,1) ELSE AvailabilityGroupItem END AS AvailabilityGroupItem,\n" +
"           StartPosition,\n" +
"           Selected\n" +
"    FROM AvailabilityGroups3\n" +
"    )\n" +
"    INSERT INTO @SelectedAvailabilityGroups (AvailabilityGroupName, StartPosition, Selected)\n" +
"    SELECT AvailabilityGroupName, StartPosition, Selected\n" +
"    FROM AvailabilityGroups4\n" +
"    OPTION (MAXRECURSION 0)\n" +
"\n" +
"    UPDATE tmpAvailabilityGroups\n" +
"    SET tmpAvailabilityGroups.Selected = SelectedAvailabilityGroups.Selected\n" +
"    FROM @tmpAvailabilityGroups tmpAvailabilityGroups\n" +
"    INNER JOIN @SelectedAvailabilityGroups SelectedAvailabilityGroups\n" +
"    ON tmpAvailabilityGroups.AvailabilityGroupName LIKE REPLACE(SelectedAvailabilityGroups.AvailabilityGroupName,'_','[_]')\n" +
"    WHERE SelectedAvailabilityGroups.Selected = 1\n" +
"\n" +
"    UPDATE tmpAvailabilityGroups\n" +
"    SET tmpAvailabilityGroups.Selected = SelectedAvailabilityGroups.Selected\n" +
"    FROM @tmpAvailabilityGroups tmpAvailabilityGroups\n" +
"    INNER JOIN @SelectedAvailabilityGroups SelectedAvailabilityGroups\n" +
"    ON tmpAvailabilityGroups.AvailabilityGroupName LIKE REPLACE(SelectedAvailabilityGroups.AvailabilityGroupName,'_','[_]')\n" +
"    WHERE SelectedAvailabilityGroups.Selected = 0\n" +
"\n" +
"    UPDATE tmpAvailabilityGroups\n" +
"    SET tmpAvailabilityGroups.StartPosition = SelectedAvailabilityGroups2.StartPosition\n" +
"    FROM @tmpAvailabilityGroups tmpAvailabilityGroups\n" +
"    INNER JOIN (SELECT tmpAvailabilityGroups.AvailabilityGroupName, MIN(SelectedAvailabilityGroups.StartPosition) AS StartPosition\n" +
"                FROM @tmpAvailabilityGroups tmpAvailabilityGroups\n" +
"                INNER JOIN @SelectedAvailabilityGroups SelectedAvailabilityGroups\n" +
"                ON tmpAvailabilityGroups.AvailabilityGroupName LIKE REPLACE(SelectedAvailabilityGroups.AvailabilityGroupName,'_','[_]')\n" +
"                WHERE SelectedAvailabilityGroups.Selected = 1\n" +
"                GROUP BY tmpAvailabilityGroups.AvailabilityGroupName) SelectedAvailabilityGroups2\n" +
"    ON tmpAvailabilityGroups.AvailabilityGroupName = SelectedAvailabilityGroups2.AvailabilityGroupName\n" +
"\n" +
"    UPDATE tmpDatabases\n" +
"    SET tmpDatabases.StartPosition = tmpAvailabilityGroups.StartPosition,\n" +
"        tmpDatabases.Selected = 1\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    INNER JOIN @tmpDatabasesAvailabilityGroups tmpDatabasesAvailabilityGroups ON tmpDatabases.DatabaseName = tmpDatabasesAvailabilityGroups.DatabaseName\n" +
"    INNER JOIN @tmpAvailabilityGroups tmpAvailabilityGroups ON tmpDatabasesAvailabilityGroups.AvailabilityGroupName = tmpAvailabilityGroups.AvailabilityGroupName\n" +
"    WHERE tmpAvailabilityGroups.Selected = 1\n" +
"\n" +
"  END\n" +
"\n" +
"  IF @AvailabilityGroups IS NOT NULL AND (NOT EXISTS(SELECT * FROM @SelectedAvailabilityGroups) OR EXISTS(SELECT * FROM @SelectedAvailabilityGroups WHERE AvailabilityGroupName IS NULL OR AvailabilityGroupName = '') OR @Version < 11 OR SERVERPROPERTY('IsHadrEnabled') = 0)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AvailabilityGroups is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF (@Databases IS NULL AND @AvailabilityGroups IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'You need to specify one of the parameters @Databases and @AvailabilityGroups.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF (@Databases IS NOT NULL AND @AvailabilityGroups IS NOT NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'You can only specify one of the parameters @Databases and @AvailabilityGroups.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select filegroups                                                                          //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @FileGroups = REPLACE(@FileGroups, CHAR(10), '')\n" +
"  SET @FileGroups = REPLACE(@FileGroups, CHAR(13), '')\n" +
"\n" +
"  WHILE CHARINDEX(@StringDelimiter + ' ', @FileGroups) > 0 SET @FileGroups = REPLACE(@FileGroups, @StringDelimiter + ' ', @StringDelimiter)\n" +
"  WHILE CHARINDEX(' ' + @StringDelimiter, @FileGroups) > 0 SET @FileGroups = REPLACE(@FileGroups, ' ' + @StringDelimiter, @StringDelimiter)\n" +
"\n" +
"  SET @FileGroups = LTRIM(RTRIM(@FileGroups));\n" +
"\n" +
"  WITH FileGroups1 (StartPosition, EndPosition, FileGroupItem) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FileGroups, 1), 0), LEN(@FileGroups) + 1) AS EndPosition,\n" +
"         SUBSTRING(@FileGroups, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FileGroups, 1), 0), LEN(@FileGroups) + 1) - 1) AS FileGroupItem\n" +
"  WHERE @FileGroups IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FileGroups, EndPosition + 1), 0), LEN(@FileGroups) + 1) AS EndPosition,\n" +
"         SUBSTRING(@FileGroups, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FileGroups, EndPosition + 1), 0), LEN(@FileGroups) + 1) - EndPosition - 1) AS FileGroupItem\n" +
"  FROM FileGroups1\n" +
"  WHERE EndPosition < LEN(@FileGroups) + 1\n" +
"  ),\n" +
"  FileGroups2 (FileGroupItem, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN FileGroupItem LIKE '-%' THEN RIGHT(FileGroupItem,LEN(FileGroupItem) - 1) ELSE FileGroupItem END AS FileGroupItem,\n" +
"         StartPosition,\n" +
"         CASE WHEN FileGroupItem LIKE '-%' THEN 0 ELSE 1 END AS Selected\n" +
"  FROM FileGroups1\n" +
"  ),\n" +
"  FileGroups3 (FileGroupItem, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN FileGroupItem = 'ALL_FILEGROUPS' THEN '%.%' ELSE FileGroupItem END AS FileGroupItem,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM FileGroups2\n" +
"  ),\n" +
"  FileGroups4 (DatabaseName, FileGroupName, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN PARSENAME(FileGroupItem,4) IS NULL AND PARSENAME(FileGroupItem,3) IS NULL THEN PARSENAME(FileGroupItem,2) ELSE NULL END AS DatabaseName,\n" +
"         CASE WHEN PARSENAME(FileGroupItem,4) IS NULL AND PARSENAME(FileGroupItem,3) IS NULL THEN PARSENAME(FileGroupItem,1) ELSE NULL END AS FileGroupName,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM FileGroups3\n" +
"  )\n" +
"  INSERT INTO @SelectedFileGroups (DatabaseName, FileGroupName, StartPosition, Selected)\n" +
"  SELECT DatabaseName, FileGroupName, StartPosition, Selected\n" +
"  FROM FileGroups4\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select objects                                                                             //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @Objects = REPLACE(@Objects, CHAR(10), '')\n" +
"  SET @Objects = REPLACE(@Objects, CHAR(13), '')\n" +
"\n" +
"  WHILE CHARINDEX(@StringDelimiter + ' ', @Objects) > 0 SET @Objects = REPLACE(@Objects, @StringDelimiter + ' ', @StringDelimiter)\n" +
"  WHILE CHARINDEX(' ' + @StringDelimiter, @Objects) > 0 SET @Objects = REPLACE(@Objects, ' ' + @StringDelimiter, @StringDelimiter)\n" +
"\n" +
"  SET @Objects = LTRIM(RTRIM(@Objects));\n" +
"\n" +
"  WITH Objects1 (StartPosition, EndPosition, ObjectItem) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Objects, 1), 0), LEN(@Objects) + 1) AS EndPosition,\n" +
"         SUBSTRING(@Objects, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Objects, 1), 0), LEN(@Objects) + 1) - 1) AS ObjectItem\n" +
"  WHERE @Objects IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Objects, EndPosition + 1), 0), LEN(@Objects) + 1) AS EndPosition,\n" +
"         SUBSTRING(@Objects, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Objects, EndPosition + 1), 0), LEN(@Objects) + 1) - EndPosition - 1) AS ObjectItem\n" +
"  FROM Objects1\n" +
"  WHERE EndPosition < LEN(@Objects) + 1\n" +
"  ),\n" +
"  Objects2 (ObjectItem, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN ObjectItem LIKE '-%' THEN RIGHT(ObjectItem,LEN(ObjectItem) - 1) ELSE ObjectItem END AS ObjectItem,\n" +
"          StartPosition,\n" +
"         CASE WHEN ObjectItem LIKE '-%' THEN 0 ELSE 1 END AS Selected\n" +
"  FROM Objects1\n" +
"  ),\n" +
"  Objects3 (ObjectItem, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN ObjectItem = 'ALL_OBJECTS' THEN '%.%.%' ELSE ObjectItem END AS ObjectItem,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Objects2\n" +
"  ),\n" +
"  Objects4 (DatabaseName, SchemaName, ObjectName, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN PARSENAME(ObjectItem,4) IS NULL THEN PARSENAME(ObjectItem,3) ELSE NULL END AS DatabaseName,\n" +
"         CASE WHEN PARSENAME(ObjectItem,4) IS NULL THEN PARSENAME(ObjectItem,2) ELSE NULL END AS SchemaName,\n" +
"         CASE WHEN PARSENAME(ObjectItem,4) IS NULL THEN PARSENAME(ObjectItem,1) ELSE NULL END AS ObjectName,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Objects3\n" +
"  )\n" +
"  INSERT INTO @SelectedObjects (DatabaseName, SchemaName, ObjectName, StartPosition, Selected)\n" +
"  SELECT DatabaseName, SchemaName, ObjectName, StartPosition, Selected\n" +
"  FROM Objects4\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select check commands                                                                      //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @CheckCommands = REPLACE(@CheckCommands, @StringDelimiter + ' ', @StringDelimiter);\n" +
"\n" +
"  WITH CheckCommands (StartPosition, EndPosition, CheckCommand) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @CheckCommands, 1), 0), LEN(@CheckCommands) + 1) AS EndPosition,\n" +
"         SUBSTRING(@CheckCommands, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @CheckCommands, 1), 0), LEN(@CheckCommands) + 1) - 1) AS CheckCommand\n" +
"  WHERE @CheckCommands IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @CheckCommands, EndPosition + 1), 0), LEN(@CheckCommands) + 1) AS EndPosition,\n" +
"         SUBSTRING(@CheckCommands, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @CheckCommands, EndPosition + 1), 0), LEN(@CheckCommands) + 1) - EndPosition - 1) AS CheckCommand\n" +
"  FROM CheckCommands\n" +
"  WHERE EndPosition < LEN(@CheckCommands) + 1\n" +
"  )\n" +
"  INSERT INTO @SelectedCheckCommands (CheckCommand)\n" +
"  SELECT CheckCommand\n" +
"  FROM CheckCommands\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check input parameters                                                                     //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @SelectedCheckCommands WHERE CheckCommand NOT IN('CHECKDB','CHECKFILEGROUP','CHECKALLOC','CHECKTABLE','CHECKCATALOG'))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CheckCommands is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @SelectedCheckCommands GROUP BY CheckCommand HAVING COUNT(*) > 1)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CheckCommands is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF NOT EXISTS (SELECT * FROM @SelectedCheckCommands)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CheckCommands is not supported.' , 16, 3\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @SelectedCheckCommands WHERE CheckCommand IN('CHECKDB')) AND EXISTS (SELECT CheckCommand FROM @SelectedCheckCommands WHERE CheckCommand IN('CHECKFILEGROUP','CHECKALLOC','CHECKTABLE','CHECKCATALOG'))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CheckCommands is not supported.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @SelectedCheckCommands WHERE CheckCommand IN('CHECKFILEGROUP')) AND EXISTS (SELECT CheckCommand FROM @SelectedCheckCommands WHERE CheckCommand IN('CHECKALLOC','CHECKTABLE'))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @CheckCommands is not supported.', 16, 5\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @PhysicalOnly NOT IN ('Y','N') OR @PhysicalOnly IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @PhysicalOnly is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DataPurity NOT IN ('Y','N') OR @DataPurity IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DataPurity is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @PhysicalOnly = 'Y' AND @DataPurity = 'Y'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The parameters @PhysicalOnly and @DataPurity cannot be used together.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @NoIndex NOT IN ('Y','N') OR @NoIndex IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @NoIndex is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @ExtendedLogicalChecks NOT IN ('Y','N') OR @ExtendedLogicalChecks IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ExtendedLogicalChecks is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @PhysicalOnly = 'Y' AND @ExtendedLogicalChecks = 'Y'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The parameters @PhysicalOnly and @ExtendedLogicalChecks cannot be used together.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @TabLock NOT IN ('Y','N') OR @TabLock IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @TabLock is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS(SELECT * FROM @SelectedFileGroups WHERE DatabaseName IS NULL OR FileGroupName IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FileGroups is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @FileGroups IS NOT NULL AND NOT EXISTS(SELECT * FROM @SelectedFileGroups)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FileGroups is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @FileGroups IS NOT NULL AND NOT EXISTS (SELECT * FROM @SelectedCheckCommands WHERE CheckCommand = 'CHECKFILEGROUP')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FileGroups is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS(SELECT * FROM @SelectedObjects WHERE DatabaseName IS NULL OR SchemaName IS NULL OR ObjectName IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Objects is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF (@Objects IS NOT NULL AND NOT EXISTS(SELECT * FROM @SelectedObjects))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Objects is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF (@Objects IS NOT NULL AND NOT EXISTS (SELECT * FROM @SelectedCheckCommands WHERE CheckCommand = 'CHECKTABLE'))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Objects is not supported.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @MaxDOP < 0 OR @MaxDOP > 64\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MaxDOP is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @MaxDOP IS NOT NULL AND NOT (@Version >= 12.050000 OR SERVERPROPERTY('EngineEdition') IN (5, 8))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MaxDOP is not supported. MAXDOP is not available in this version of SQL Server.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @AvailabilityGroupReplicas NOT IN('ALL','PRIMARY','SECONDARY','PREFERRED_BACKUP_REPLICA') OR @AvailabilityGroupReplicas IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AvailabilityGroupReplicas is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Updateability NOT IN('READ_ONLY','READ_WRITE','ALL') OR @Updateability IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Updateability is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @TimeLimit < 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @TimeLimit is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @LockTimeout < 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @LockTimeout is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @LockMessageSeverity NOT IN(10, 16)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @LockMessageSeverity is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @StringDelimiter IS NULL OR LEN(@StringDelimiter) > 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @StringDelimiter is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabaseOrder NOT IN('DATABASE_NAME_ASC','DATABASE_NAME_DESC','DATABASE_SIZE_ASC','DATABASE_SIZE_DESC','DATABASE_LAST_GOOD_CHECK_ASC','DATABASE_LAST_GOOD_CHECK_DESC','REPLICA_LAST_GOOD_CHECK_ASC','REPLICA_LAST_GOOD_CHECK_DESC')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabaseOrder is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IN('DATABASE_LAST_GOOD_CHECK_ASC','DATABASE_LAST_GOOD_CHECK_DESC') AND NOT ((@Version >= 12.06024 AND @Version < 13) OR (@Version >= 13.05026 AND @Version < 14) OR @Version >= 14.0302916)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabaseOrder is not supported. DATABASEPROPERTYEX(''DatabaseName'', ''LastGoodCheckDbTime'') is not available in this version of SQL Server.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IN('REPLICA_LAST_GOOD_CHECK_ASC','REPLICA_LAST_GOOD_CHECK_DESC') AND @LogToTable = 'N'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabaseOrder is not supported. You need to provide the parameter @LogToTable = ''Y''.', 16, 3\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IN('DATABASE_LAST_GOOD_CHECK_ASC','DATABASE_LAST_GOOD_CHECK_DESC','REPLICA_LAST_GOOD_CHECK_ASC','REPLICA_LAST_GOOD_CHECK_DESC') AND @CheckCommands <> 'CHECKDB'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabaseOrder is not supported. You need to provide the parameter @CheckCommands = ''CHECKDB''.', 16, 4\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IS NOT NULL AND SERVERPROPERTY('EngineEdition') = 5\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabaseOrder is not supported. This parameter is not supported in Azure SQL Database.', 16, 5\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabasesInParallel NOT IN('Y','N') OR @DatabasesInParallel IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabasesInParallel is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DatabasesInParallel = 'Y' AND SERVERPROPERTY('EngineEdition') = 5\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabasesInParallel is not supported. This parameter is not supported in Azure SQL Database.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @LogToTable NOT IN('Y','N') OR @LogToTable IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @LogToTable is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Execute NOT IN('Y','N') OR @Execute IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Execute is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS(SELECT * FROM @Errors)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The documentation is available at https://ola.hallengren.com/sql-server-integrity-check.html.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check that selected databases and availability groups exist                                //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + ', '\n" +
"  FROM @SelectedDatabases\n" +
"  WHERE DatabaseName NOT LIKE '%[%]%'\n" +
"  AND DatabaseName NOT IN (SELECT DatabaseName FROM @tmpDatabases)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The following databases in the @Databases parameter do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 10, 1\n" +
"  END\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + ', '\n" +
"  FROM @SelectedFileGroups\n" +
"  WHERE DatabaseName NOT LIKE '%[%]%'\n" +
"  AND DatabaseName NOT IN (SELECT DatabaseName FROM @tmpDatabases)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The following databases in the @FileGroups parameter do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 10, 1\n" +
"  END\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + ', '\n" +
"  FROM @SelectedObjects\n" +
"  WHERE DatabaseName NOT LIKE '%[%]%'\n" +
"  AND DatabaseName NOT IN (SELECT DatabaseName FROM @tmpDatabases)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The following databases in the @Objects parameter do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 10, 1\n" +
"  END\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(AvailabilityGroupName) + ', '\n" +
"  FROM @SelectedAvailabilityGroups\n" +
"  WHERE AvailabilityGroupName NOT LIKE '%[%]%'\n" +
"  AND AvailabilityGroupName NOT IN (SELECT AvailabilityGroupName FROM @tmpAvailabilityGroups)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The following availability groups do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 10, 1\n" +
"  END\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + ', '\n" +
"  FROM @SelectedFileGroups\n" +
"  WHERE DatabaseName NOT LIKE '%[%]%'\n" +
"  AND DatabaseName IN (SELECT DatabaseName FROM @tmpDatabases)\n" +
"  AND DatabaseName NOT IN (SELECT DatabaseName FROM @tmpDatabases WHERE Selected = 1)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The following databases have been selected in the @FileGroups parameter, but not in the @Databases or @AvailabilityGroups parameters: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 10, 1\n" +
"  END\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + ', '\n" +
"  FROM @SelectedObjects\n" +
"  WHERE DatabaseName NOT LIKE '%[%]%'\n" +
"  AND DatabaseName IN (SELECT DatabaseName FROM @tmpDatabases)\n" +
"  AND DatabaseName NOT IN (SELECT DatabaseName FROM @tmpDatabases WHERE Selected = 1)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The following databases have been selected in the @Objects parameter, but not in the @Databases or @AvailabilityGroups parameters: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 10, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check @@SERVERNAME                                                                         //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @@SERVERNAME <> CAST(SERVERPROPERTY('ServerName') AS nvarchar(max)) AND SERVERPROPERTY('IsHadrEnabled') = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The @@SERVERNAME does not match SERVERPROPERTY(''ServerName''). See ' + CASE WHEN SERVERPROPERTY('IsClustered') = 0 THEN 'https://docs.microsoft.com/en-us/sql/database-engine/install-windows/rename-a-computer-that-hosts-a-stand-alone-instance-of-sql-server' WHEN SERVERPROPERTY('IsClustered') = 1 THEN 'https://docs.microsoft.com/en-us/sql/sql-server/failover-clusters/install/rename-a-sql-server-failover-cluster-instance' END + '.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Raise errors                                                                               //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  DECLARE ErrorCursor CURSOR FAST_FORWARD FOR SELECT [Message], Severity, [State] FROM @Errors ORDER BY [ID] ASC\n" +
"\n" +
"  OPEN ErrorCursor\n" +
"\n" +
"  FETCH ErrorCursor INTO @CurrentMessage, @CurrentSeverity, @CurrentState\n" +
"\n" +
"  WHILE @@FETCH_STATUS = 0\n" +
"  BEGIN\n" +
"    RAISERROR('%s', @CurrentSeverity, @CurrentState, @CurrentMessage) WITH NOWAIT\n" +
"    RAISERROR(@EmptyLine, 10, 1) WITH NOWAIT\n" +
"\n" +
"    FETCH NEXT FROM ErrorCursor INTO @CurrentMessage, @CurrentSeverity, @CurrentState\n" +
"  END\n" +
"\n" +
"  CLOSE ErrorCursor\n" +
"\n" +
"  DEALLOCATE ErrorCursor\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @Errors WHERE Severity >= 16)\n" +
"  BEGIN\n" +
"    SET @ReturnCode = 50000\n" +
"    GOTO Logging\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Update database order                                                                      //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabaseOrder IN('DATABASE_SIZE_ASC','DATABASE_SIZE_DESC')\n" +
"  BEGIN\n" +
"    UPDATE tmpDatabases\n" +
"    SET DatabaseSize = (SELECT SUM(CAST(size AS bigint)) FROM sys.master_files WHERE [type] = 0 AND database_id = DB_ID(tmpDatabases.DatabaseName))\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IN('DATABASE_LAST_GOOD_CHECK_ASC','DATABASE_LAST_GOOD_CHECK_DESC')\n" +
"  BEGIN\n" +
"    UPDATE tmpDatabases\n" +
"    SET LastGoodCheckDbTime = NULLIF(CAST(DATABASEPROPERTYEX (DatabaseName,'LastGoodCheckDbTime') AS datetime2),'1900-01-01 00:00:00.000')\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IN('REPLICA_LAST_GOOD_CHECK_ASC','REPLICA_LAST_GOOD_CHECK_DESC')\n" +
"  BEGIN\n" +
"    UPDATE tmpDatabases\n" +
"    SET LastCommandTime = MaxStartTime\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    INNER JOIN (SELECT DatabaseName, MAX(StartTime) AS MaxStartTime\n" +
"                FROM dbo.CommandLog\n" +
"                WHERE CommandType = 'DBCC_CHECKDB'\n" +
"                AND ErrorNumber = 0\n" +
"                GROUP BY DatabaseName) CommandLog\n" +
"    ON tmpDatabases.DatabaseName = CommandLog.DatabaseName COLLATE DATABASE_DEFAULT\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IS NULL\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY StartPosition ASC, DatabaseName ASC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_NAME_ASC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY DatabaseName ASC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_NAME_DESC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY DatabaseName DESC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_SIZE_ASC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY DatabaseSize ASC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_SIZE_DESC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY DatabaseSize DESC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_LAST_GOOD_CHECK_ASC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY LastGoodCheckDbTime ASC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_LAST_GOOD_CHECK_DESC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY LastGoodCheckDbTime DESC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'REPLICA_LAST_GOOD_CHECK_ASC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY LastCommandTime ASC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'REPLICA_LAST_GOOD_CHECK_DESC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY LastCommandTime DESC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n";
    }
    
    public String part9(){
        return "  ----------------------------------------------------------------------------------------------------\n" +
"  --// Update the queue                                                                           //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabasesInParallel = 'Y'\n" +
"  BEGIN\n" +
"\n" +
"    BEGIN TRY\n" +
"\n" +
"      SELECT @QueueID = QueueID\n" +
"      FROM dbo.[Queue]\n" +
"      WHERE SchemaName = @SchemaName\n" +
"      AND ObjectName = @ObjectName\n" +
"      AND [Parameters] = @Parameters\n" +
"\n" +
"      IF @QueueID IS NULL\n" +
"      BEGIN\n" +
"        BEGIN TRANSACTION\n" +
"\n" +
"        SELECT @QueueID = QueueID\n" +
"        FROM dbo.[Queue] WITH (UPDLOCK, HOLDLOCK)\n" +
"        WHERE SchemaName = @SchemaName\n" +
"        AND ObjectName = @ObjectName\n" +
"        AND [Parameters] = @Parameters\n" +
"\n" +
"        IF @QueueID IS NULL\n" +
"        BEGIN\n" +
"          INSERT INTO dbo.[Queue] (SchemaName, ObjectName, [Parameters])\n" +
"          SELECT @SchemaName, @ObjectName, @Parameters\n" +
"\n" +
"          SET @QueueID = SCOPE_IDENTITY()\n" +
"        END\n" +
"\n" +
"        COMMIT TRANSACTION\n" +
"      END\n" +
"\n" +
"      BEGIN TRANSACTION\n" +
"\n" +
"      UPDATE [Queue]\n" +
"      SET QueueStartTime = SYSDATETIME(),\n" +
"          SessionID = @@SPID,\n" +
"          RequestID = (SELECT request_id FROM sys.dm_exec_requests WHERE session_id = @@SPID),\n" +
"          RequestStartTime = (SELECT start_time FROM sys.dm_exec_requests WHERE session_id = @@SPID)\n" +
"      FROM dbo.[Queue] [Queue]\n" +
"      WHERE QueueID = @QueueID\n" +
"      AND NOT EXISTS (SELECT *\n" +
"                      FROM sys.dm_exec_requests\n" +
"                      WHERE session_id = [Queue].SessionID\n" +
"                      AND request_id = [Queue].RequestID\n" +
"                      AND start_time = [Queue].RequestStartTime)\n" +
"      AND NOT EXISTS (SELECT *\n" +
"                      FROM dbo.QueueDatabase QueueDatabase\n" +
"                      INNER JOIN sys.dm_exec_requests ON QueueDatabase.SessionID = session_id AND QueueDatabase.RequestID = request_id AND QueueDatabase.RequestStartTime = start_time\n" +
"                      WHERE QueueDatabase.QueueID = @QueueID)\n" +
"\n" +
"      IF @@ROWCOUNT = 1\n" +
"      BEGIN\n" +
"        INSERT INTO dbo.QueueDatabase (QueueID, DatabaseName)\n" +
"        SELECT @QueueID AS QueueID,\n" +
"               DatabaseName\n" +
"        FROM @tmpDatabases tmpDatabases\n" +
"        WHERE Selected = 1\n" +
"        AND NOT EXISTS (SELECT * FROM dbo.QueueDatabase WHERE DatabaseName = tmpDatabases.DatabaseName AND QueueID = @QueueID)\n" +
"\n" +
"        DELETE QueueDatabase\n" +
"        FROM dbo.QueueDatabase QueueDatabase\n" +
"        WHERE QueueID = @QueueID\n" +
"        AND NOT EXISTS (SELECT * FROM @tmpDatabases tmpDatabases WHERE DatabaseName = QueueDatabase.DatabaseName AND Selected = 1)\n" +
"\n" +
"        UPDATE QueueDatabase\n" +
"        SET DatabaseOrder = tmpDatabases.[Order]\n" +
"        FROM dbo.QueueDatabase QueueDatabase\n" +
"        INNER JOIN @tmpDatabases tmpDatabases ON QueueDatabase.DatabaseName = tmpDatabases.DatabaseName\n" +
"        WHERE QueueID = @QueueID\n" +
"      END\n" +
"\n" +
"      COMMIT TRANSACTION\n" +
"\n" +
"      SELECT @QueueStartTime = QueueStartTime\n" +
"      FROM dbo.[Queue]\n" +
"      WHERE QueueID = @QueueID\n" +
"\n" +
"    END TRY\n" +
"\n" +
"    BEGIN CATCH\n" +
"      IF XACT_STATE() <> 0\n" +
"      BEGIN\n" +
"        ROLLBACK TRANSACTION\n" +
"      END\n" +
"      SET @ErrorMessage = 'Msg ' + CAST(ERROR_NUMBER() AS nvarchar) + ', ' + ISNULL(ERROR_MESSAGE(),'')\n" +
"      RAISERROR('%s',16,1,@ErrorMessage) WITH NOWAIT\n" +
"      RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"      SET @ReturnCode = ERROR_NUMBER()\n" +
"      GOTO Logging\n" +
"    END CATCH\n" +
"\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Execute commands                                                                           //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  WHILE (1 = 1)\n" +
"  BEGIN\n" +
"\n" +
"    IF @DatabasesInParallel = 'Y'\n" +
"    BEGIN\n" +
"      UPDATE QueueDatabase\n" +
"      SET DatabaseStartTime = NULL,\n" +
"          SessionID = NULL,\n" +
"          RequestID = NULL,\n" +
"          RequestStartTime = NULL\n" +
"      FROM dbo.QueueDatabase QueueDatabase\n" +
"      WHERE QueueID = @QueueID\n" +
"      AND DatabaseStartTime IS NOT NULL\n" +
"      AND DatabaseEndTime IS NULL\n" +
"      AND NOT EXISTS (SELECT * FROM sys.dm_exec_requests WHERE session_id = QueueDatabase.SessionID AND request_id = QueueDatabase.RequestID AND start_time = QueueDatabase.RequestStartTime)\n" +
"\n" +
"      UPDATE QueueDatabase\n" +
"      SET DatabaseStartTime = SYSDATETIME(),\n" +
"          DatabaseEndTime = NULL,\n" +
"          SessionID = @@SPID,\n" +
"          RequestID = (SELECT request_id FROM sys.dm_exec_requests WHERE session_id = @@SPID),\n" +
"          RequestStartTime = (SELECT start_time FROM sys.dm_exec_requests WHERE session_id = @@SPID),\n" +
"          @CurrentDatabaseName = DatabaseName\n" +
"      FROM (SELECT TOP 1 DatabaseStartTime,\n" +
"                         DatabaseEndTime,\n" +
"                         SessionID,\n" +
"                         RequestID,\n" +
"                         RequestStartTime,\n" +
"                         DatabaseName\n" +
"            FROM dbo.QueueDatabase\n" +
"            WHERE QueueID = @QueueID\n" +
"            AND (DatabaseStartTime < @QueueStartTime OR DatabaseStartTime IS NULL)\n" +
"            AND NOT (DatabaseStartTime IS NOT NULL AND DatabaseEndTime IS NULL)\n" +
"            ORDER BY DatabaseOrder ASC\n" +
"            ) QueueDatabase\n" +
"    END\n" +
"    ELSE\n" +
"    BEGIN\n" +
"      SELECT TOP 1 @CurrentDBID = ID,\n" +
"                   @CurrentDatabaseName = DatabaseName\n" +
"      FROM @tmpDatabases\n" +
"      WHERE Selected = 1\n" +
"      AND Completed = 0\n" +
"      ORDER BY [Order] ASC\n" +
"    END\n" +
"\n" +
"    IF @@ROWCOUNT = 0\n" +
"    BEGIN\n" +
"     BREAK\n" +
"    END\n" +
"\n" +
"    SET @CurrentDatabase_sp_executesql = QUOTENAME(@CurrentDatabaseName) + '.sys.sp_executesql'\n" +
"\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Date and time: ' + CONVERT(nvarchar,SYSDATETIME(),120)\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Database: ' + QUOTENAME(@CurrentDatabaseName)\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    SELECT @CurrentUserAccess = user_access_desc,\n" +
"           @CurrentIsReadOnly = is_read_only,\n" +
"           @CurrentDatabaseState = state_desc,\n" +
"           @CurrentInStandby = is_in_standby,\n" +
"           @CurrentRecoveryModel = recovery_model_desc\n" +
"    FROM sys.databases\n" +
"    WHERE [name] = @CurrentDatabaseName\n" +
"\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'State: ' + @CurrentDatabaseState\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Standby: ' + CASE WHEN @CurrentInStandby = 1 THEN 'Yes' ELSE 'No' END\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Updateability: ' + CASE WHEN @CurrentIsReadOnly = 1 THEN 'READ_ONLY' WHEN  @CurrentIsReadOnly = 0 THEN 'READ_WRITE' END\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'User access: ' + @CurrentUserAccess\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Recovery model: ' + @CurrentRecoveryModel\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    IF @CurrentDatabaseState = 'ONLINE' AND SERVERPROPERTY('EngineEdition') <> 5\n" +
"    BEGIN\n" +
"      IF EXISTS (SELECT * FROM sys.database_recovery_status WHERE database_id = DB_ID(@CurrentDatabaseName) AND database_guid IS NOT NULL)\n" +
"      BEGIN\n" +
"        SET @CurrentIsDatabaseAccessible = 1\n" +
"      END\n" +
"      ELSE\n" +
"      BEGIN\n" +
"        SET @CurrentIsDatabaseAccessible = 0\n" +
"      END\n" +
"    END\n" +
"\n" +
"    IF @Version >= 11 AND SERVERPROPERTY('IsHadrEnabled') = 1\n" +
"    BEGIN\n" +
"      SELECT @CurrentReplicaID = databases.replica_id\n" +
"      FROM sys.databases databases\n" +
"      INNER JOIN sys.availability_replicas availability_replicas ON databases.replica_id = availability_replicas.replica_id\n" +
"      WHERE databases.[name] = @CurrentDatabaseName\n" +
"\n" +
"      SELECT @CurrentAvailabilityGroupID = group_id,\n" +
"             @CurrentSecondaryRoleAllowConnections = secondary_role_allow_connections_desc\n" +
"      FROM sys.availability_replicas\n" +
"      WHERE replica_id = @CurrentReplicaID\n" +
"\n" +
"      SELECT @CurrentAvailabilityGroupRole = role_desc\n" +
"      FROM sys.dm_hadr_availability_replica_states\n" +
"      WHERE replica_id = @CurrentReplicaID\n" +
"\n" +
"      SELECT @CurrentAvailabilityGroup = [name],\n" +
"             @CurrentAvailabilityGroupBackupPreference = UPPER(automated_backup_preference_desc)\n" +
"      FROM sys.availability_groups\n" +
"      WHERE group_id = @CurrentAvailabilityGroupID\n" +
"    END\n" +
"\n" +
"    IF @Version >= 11 AND SERVERPROPERTY('IsHadrEnabled') = 1 AND @CurrentAvailabilityGroup IS NOT NULL AND @AvailabilityGroupReplicas = 'PREFERRED_BACKUP_REPLICA'\n" +
"    BEGIN\n" +
"      SELECT @CurrentIsPreferredBackupReplica = sys.fn_hadr_backup_is_preferred_replica(@CurrentDatabaseName)\n" +
"    END\n" +
"\n" +
"    IF SERVERPROPERTY('EngineEdition') <> 5\n" +
"    BEGIN\n" +
"      SELECT @CurrentDatabaseMirroringRole = UPPER(mirroring_role_desc)\n" +
"      FROM sys.database_mirroring\n" +
"      WHERE database_id = DB_ID(@CurrentDatabaseName)\n" +
"    END\n" +
"\n" +
"    IF @CurrentIsDatabaseAccessible IS NOT NULL\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Is accessible: ' + CASE WHEN @CurrentIsDatabaseAccessible = 1 THEN 'Yes' ELSE 'No' END\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    IF @CurrentAvailabilityGroup IS NOT NULL\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage =  'Availability group: ' + ISNULL(@CurrentAvailabilityGroup,'N/A')\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Availability group role: ' + ISNULL(@CurrentAvailabilityGroupRole,'N/A')\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      IF @CurrentAvailabilityGroupRole = 'SECONDARY'\n" +
"      BEGIN\n" +
"        SET @DatabaseMessage =  'Readable Secondary: ' + ISNULL(@CurrentSecondaryRoleAllowConnections,'N/A')\n" +
"        RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"      END\n" +
"\n" +
"      IF @AvailabilityGroupReplicas = 'PREFERRED_BACKUP_REPLICA'\n" +
"      BEGIN\n" +
"        SET @DatabaseMessage = 'Availability group backup preference: ' + ISNULL(@CurrentAvailabilityGroupBackupPreference,'N/A')\n" +
"        RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"        SET @DatabaseMessage = 'Is preferred backup replica: ' + CASE WHEN @CurrentIsPreferredBackupReplica = 1 THEN 'Yes' WHEN @CurrentIsPreferredBackupReplica = 0 THEN 'No' ELSE 'N/A' END\n" +
"        RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"      END\n" +
"    END\n" +
"\n" +
"    IF @CurrentDatabaseMirroringRole IS NOT NULL\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Database mirroring role: ' + @CurrentDatabaseMirroringRole\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"    IF @CurrentDatabaseState = 'ONLINE'\n" +
"    AND NOT (@CurrentUserAccess = 'SINGLE_USER' AND @CurrentIsDatabaseAccessible = 0)\n" +
"    AND (@CurrentAvailabilityGroupRole = 'PRIMARY' OR @CurrentAvailabilityGroupRole IS NULL OR SERVERPROPERTY('EngineEdition') = 3)\n" +
"    AND ((@AvailabilityGroupReplicas = 'PRIMARY' AND @CurrentAvailabilityGroupRole = 'PRIMARY') OR (@AvailabilityGroupReplicas = 'SECONDARY' AND @CurrentAvailabilityGroupRole = 'SECONDARY') OR (@AvailabilityGroupReplicas = 'PREFERRED_BACKUP_REPLICA' AND @CurrentIsPreferredBackupReplica = 1) OR @AvailabilityGroupReplicas = 'ALL' OR @CurrentAvailabilityGroupRole IS NULL)\n" +
"    AND NOT (@CurrentIsReadOnly = 1 AND @Updateability = 'READ_WRITE')\n" +
"    AND NOT (@CurrentIsReadOnly = 0 AND @Updateability = 'READ_ONLY')\n" +
"    BEGIN\n" +
"\n" +
"      -- Check database\n" +
"      IF EXISTS(SELECT * FROM @SelectedCheckCommands WHERE CheckCommand = 'CHECKDB') AND (SYSDATETIME() < DATEADD(SECOND,@TimeLimit,@StartTime) OR @TimeLimit IS NULL)\n" +
"      BEGIN\n" +
"        SET @CurrentDatabaseContext = CASE WHEN SERVERPROPERTY('EngineEdition') = 5 THEN @CurrentDatabaseName ELSE 'master' END\n" +
"\n" +
"        SET @CurrentCommandType = 'DBCC_CHECKDB'\n" +
"\n" +
"        SET @CurrentCommand = ''\n" +
"        IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"        SET @CurrentCommand += 'DBCC CHECKDB (' + QUOTENAME(@CurrentDatabaseName)\n" +
"        IF @NoIndex = 'Y' SET @CurrentCommand += ', NOINDEX'\n" +
"        SET @CurrentCommand += ') WITH NO_INFOMSGS, ALL_ERRORMSGS'\n" +
"        IF @DataPurity = 'Y' SET @CurrentCommand += ', DATA_PURITY'\n" +
"        IF @PhysicalOnly = 'Y' SET @CurrentCommand += ', PHYSICAL_ONLY'\n" +
"        IF @ExtendedLogicalChecks = 'Y' SET @CurrentCommand += ', EXTENDED_LOGICAL_CHECKS'\n" +
"        IF @TabLock = 'Y' SET @CurrentCommand += ', TABLOCK'\n" +
"        IF @MaxDOP IS NOT NULL SET @CurrentCommand += ', MAXDOP = ' + CAST(@MaxDOP AS nvarchar)\n" +
"\n" +
"        EXECUTE @CurrentCommandOutput = dbo.CommandExecute @DatabaseContext = @CurrentDatabaseContext, @Command = @CurrentCommand, @CommandType = @CurrentCommandType, @Mode = 1, @DatabaseName = @CurrentDatabaseName, @LogToTable = @LogToTable, @Execute = @Execute\n" +
"        SET @Error = @@ERROR\n" +
"        IF @Error <> 0 SET @CurrentCommandOutput = @Error\n" +
"        IF @CurrentCommandOutput <> 0 SET @ReturnCode = @CurrentCommandOutput\n" +
"      END\n" +
"\n" +
"      -- Check filegroups\n" +
"      IF EXISTS(SELECT * FROM @SelectedCheckCommands WHERE CheckCommand = 'CHECKFILEGROUP')\n" +
"      AND (@CurrentAvailabilityGroupRole = 'PRIMARY' OR (@CurrentAvailabilityGroupRole = 'SECONDARY' AND @CurrentSecondaryRoleAllowConnections = 'ALL') OR @CurrentAvailabilityGroupRole IS NULL)\n" +
"      AND (SYSDATETIME() < DATEADD(SECOND,@TimeLimit,@StartTime) OR @TimeLimit IS NULL)\n" +
"      BEGIN\n" +
"        SET @CurrentCommand = 'SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; SELECT data_space_id AS FileGroupID, name AS FileGroupName, 0 AS [Order], 0 AS Selected, 0 AS Completed FROM sys.filegroups filegroups WHERE [type] <> ''FX'' ORDER BY CASE WHEN filegroups.name = ''PRIMARY'' THEN 1 ELSE 0 END DESC, filegroups.name ASC'\n" +
"\n" +
"        INSERT INTO @tmpFileGroups (FileGroupID, FileGroupName, [Order], Selected, Completed)\n" +
"        EXECUTE @CurrentDatabase_sp_executesql  @stmt = @CurrentCommand\n" +
"        SET @Error = @@ERROR\n" +
"        IF @Error <> 0 SET @ReturnCode = @Error\n" +
"\n" +
"        IF @FileGroups IS NULL\n" +
"        BEGIN\n" +
"          UPDATE tmpFileGroups\n" +
"          SET tmpFileGroups.Selected = 1\n" +
"          FROM @tmpFileGroups tmpFileGroups\n" +
"        END\n" +
"        ELSE\n" +
"        BEGIN\n" +
"          UPDATE tmpFileGroups\n" +
"          SET tmpFileGroups.Selected = SelectedFileGroups.Selected\n" +
"          FROM @tmpFileGroups tmpFileGroups\n" +
"          INNER JOIN @SelectedFileGroups SelectedFileGroups\n" +
"          ON @CurrentDatabaseName LIKE REPLACE(SelectedFileGroups.DatabaseName,'_','[_]') AND tmpFileGroups.FileGroupName LIKE REPLACE(SelectedFileGroups.FileGroupName,'_','[_]')\n" +
"          WHERE SelectedFileGroups.Selected = 1\n" +
"\n" +
"          UPDATE tmpFileGroups\n" +
"          SET tmpFileGroups.Selected = SelectedFileGroups.Selected\n" +
"          FROM @tmpFileGroups tmpFileGroups\n" +
"          INNER JOIN @SelectedFileGroups SelectedFileGroups\n" +
"          ON @CurrentDatabaseName LIKE REPLACE(SelectedFileGroups.DatabaseName,'_','[_]') AND tmpFileGroups.FileGroupName LIKE REPLACE(SelectedFileGroups.FileGroupName,'_','[_]')\n" +
"          WHERE SelectedFileGroups.Selected = 0\n" +
"\n" +
"          UPDATE tmpFileGroups\n" +
"          SET tmpFileGroups.StartPosition = SelectedFileGroups2.StartPosition\n" +
"          FROM @tmpFileGroups tmpFileGroups\n" +
"          INNER JOIN (SELECT tmpFileGroups.FileGroupName, MIN(SelectedFileGroups.StartPosition) AS StartPosition\n" +
"                      FROM @tmpFileGroups tmpFileGroups\n" +
"                      INNER JOIN @SelectedFileGroups SelectedFileGroups\n" +
"                      ON @CurrentDatabaseName LIKE REPLACE(SelectedFileGroups.DatabaseName,'_','[_]') AND tmpFileGroups.FileGroupName LIKE REPLACE(SelectedFileGroups.FileGroupName,'_','[_]')\n" +
"                      WHERE SelectedFileGroups.Selected = 1\n" +
"                      GROUP BY tmpFileGroups.FileGroupName) SelectedFileGroups2\n" +
"          ON tmpFileGroups.FileGroupName = SelectedFileGroups2.FileGroupName\n" +
"        END;\n" +
"\n" +
"        WITH tmpFileGroups AS (\n" +
"        SELECT FileGroupName, [Order], ROW_NUMBER() OVER (ORDER BY StartPosition ASC, FileGroupName ASC) AS RowNumber\n" +
"        FROM @tmpFileGroups tmpFileGroups\n" +
"        WHERE Selected = 1\n" +
"        )\n" +
"        UPDATE tmpFileGroups\n" +
"        SET [Order] = RowNumber\n" +
"\n" +
"        SET @ErrorMessage = ''\n" +
"        SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + '.' + QUOTENAME(FileGroupName) + ', '\n" +
"        FROM @SelectedFileGroups SelectedFileGroups\n" +
"        WHERE DatabaseName = @CurrentDatabaseName\n" +
"        AND FileGroupName NOT LIKE '%[%]%'\n" +
"        AND NOT EXISTS (SELECT * FROM @tmpFileGroups WHERE FileGroupName = SelectedFileGroups.FileGroupName)\n" +
"        IF @@ROWCOUNT > 0\n" +
"        BEGIN\n" +
"          SET @ErrorMessage = 'The following file groups do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.'\n" +
"          RAISERROR('%s',10,1,@ErrorMessage) WITH NOWAIT\n" +
"          SET @Error = @@ERROR\n" +
"          RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"        END\n" +
"\n" +
"        WHILE (SYSDATETIME() < DATEADD(SECOND,@TimeLimit,@StartTime) OR @TimeLimit IS NULL)\n" +
"        BEGIN\n" +
"          SELECT TOP 1 @CurrentFGID = ID,\n" +
"                       @CurrentFileGroupID = FileGroupID,\n" +
"                       @CurrentFileGroupName = FileGroupName\n" +
"          FROM @tmpFileGroups\n" +
"          WHERE Selected = 1\n" +
"          AND Completed = 0\n" +
"          ORDER BY [Order] ASC\n" +
"\n" +
"          IF @@ROWCOUNT = 0\n" +
"          BEGIN\n" +
"            BREAK\n" +
"          END\n" +
"\n" +
"          -- Does the filegroup exist?\n" +
"          SET @CurrentCommand = ''\n" +
"          IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"          SET @CurrentCommand += 'IF EXISTS(SELECT * FROM sys.filegroups filegroups WHERE [type] <> ''FX'' AND filegroups.data_space_id = @ParamFileGroupID AND filegroups.[name] = @ParamFileGroupName) BEGIN SET @ParamFileGroupExists = 1 END'\n" +
"\n" +
"          BEGIN TRY\n" +
"            EXECUTE @CurrentDatabase_sp_executesql @stmt = @CurrentCommand, @params = N'@ParamFileGroupID int, @ParamFileGroupName sysname, @ParamFileGroupExists bit OUTPUT', @ParamFileGroupID = @CurrentFileGroupID, @ParamFileGroupName = @CurrentFileGroupName, @ParamFileGroupExists = @CurrentFileGroupExists OUTPUT\n" +
"\n" +
"            IF @CurrentFileGroupExists IS NULL SET @CurrentFileGroupExists = 0\n" +
"          END TRY\n" +
"          BEGIN CATCH\n" +
"            SET @ErrorMessage = 'Msg ' + CAST(ERROR_NUMBER() AS nvarchar) + ', ' + ISNULL(ERROR_MESSAGE(),'') + CASE WHEN ERROR_NUMBER() = 1222 THEN ', ' + ' The file group ' + QUOTENAME(@CurrentFileGroupName) + ' in the database ' + QUOTENAME(@CurrentDatabaseName) + ' is locked. It could not be checked if the filegroup exists.' ELSE '' END\n" +
"            SET @Severity = CASE WHEN ERROR_NUMBER() IN(1205,1222) THEN @LockMessageSeverity ELSE 16 END\n" +
"            RAISERROR('%s',@Severity,1,@ErrorMessage) WITH NOWAIT\n" +
"            RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"            IF NOT (ERROR_NUMBER() IN(1205,1222) AND @LockMessageSeverity = 10)\n" +
"            BEGIN\n" +
"              SET @ReturnCode = ERROR_NUMBER()\n" +
"            END\n" +
"          END CATCH\n" +
"\n" +
"          IF @CurrentFileGroupExists = 1\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = @CurrentDatabaseName\n" +
"\n" +
"            SET @CurrentCommandType = 'DBCC_CHECKFILEGROUP'\n" +
"\n" +
"            SET @CurrentCommand = ''\n" +
"            IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"            SET @CurrentCommand += 'DBCC CHECKFILEGROUP (' + QUOTENAME(@CurrentFileGroupName)\n" +
"            IF @NoIndex = 'Y' SET @CurrentCommand += ', NOINDEX'\n" +
"            SET @CurrentCommand += ') WITH NO_INFOMSGS, ALL_ERRORMSGS'\n" +
"            IF @PhysicalOnly = 'Y' SET @CurrentCommand += ', PHYSICAL_ONLY'\n" +
"            IF @TabLock = 'Y' SET @CurrentCommand += ', TABLOCK'\n" +
"            IF @MaxDOP IS NOT NULL SET @CurrentCommand += ', MAXDOP = ' + CAST(@MaxDOP AS nvarchar)\n" +
"\n" +
"            EXECUTE @CurrentCommandOutput = dbo.CommandExecute @DatabaseContext = @CurrentDatabaseContext, @Command = @CurrentCommand, @CommandType = @CurrentCommandType, @Mode = 1, @DatabaseName = @CurrentDatabaseName, @LogToTable = @LogToTable, @Execute = @Execute\n" +
"            SET @Error = @@ERROR\n" +
"            IF @Error <> 0 SET @CurrentCommandOutput = @Error\n" +
"            IF @CurrentCommandOutput <> 0 SET @ReturnCode = @CurrentCommandOutput\n" +
"          END\n" +
"\n" +
"          UPDATE @tmpFileGroups\n" +
"          SET Completed = 1\n" +
"          WHERE Selected = 1\n" +
"          AND Completed = 0\n" +
"          AND ID = @CurrentFGID\n" +
"\n" +
"          SET @CurrentFGID = NULL\n" +
"          SET @CurrentFileGroupID = NULL\n" +
"          SET @CurrentFileGroupName = NULL\n" +
"          SET @CurrentFileGroupExists = NULL\n" +
"\n" +
"          SET @CurrentDatabaseContext = NULL\n" +
"          SET @CurrentCommand = NULL\n" +
"          SET @CurrentCommandOutput = NULL\n" +
"          SET @CurrentCommandType = NULL\n" +
"        END\n" +
"      END\n" +
"\n" +
"      -- Check disk space allocation structures\n" +
"      IF EXISTS(SELECT * FROM @SelectedCheckCommands WHERE CheckCommand = 'CHECKALLOC') AND (SYSDATETIME() < DATEADD(SECOND,@TimeLimit,@StartTime) OR @TimeLimit IS NULL)\n" +
"      BEGIN\n" +
"        SET @CurrentDatabaseContext = CASE WHEN SERVERPROPERTY('EngineEdition') = 5 THEN @CurrentDatabaseName ELSE 'master' END\n" +
"\n" +
"        SET @CurrentCommandType = 'DBCC_CHECKALLOC'\n" +
"\n" +
"        SET @CurrentCommand = ''\n" +
"        IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"        SET @CurrentCommand += 'DBCC CHECKALLOC (' + QUOTENAME(@CurrentDatabaseName)\n" +
"        SET @CurrentCommand += ') WITH NO_INFOMSGS, ALL_ERRORMSGS'\n" +
"        IF @TabLock = 'Y' SET @CurrentCommand += ', TABLOCK'\n" +
"\n" +
"        EXECUTE @CurrentCommandOutput = dbo.CommandExecute @DatabaseContext = @CurrentDatabaseContext, @Command = @CurrentCommand, @CommandType = @CurrentCommandType, @Mode = 1, @DatabaseName = @CurrentDatabaseName, @LogToTable = @LogToTable, @Execute = @Execute\n" +
"        SET @Error = @@ERROR\n" +
"        IF @Error <> 0 SET @CurrentCommandOutput = @Error\n" +
"        IF @CurrentCommandOutput <> 0 SET @ReturnCode = @CurrentCommandOutput\n" +
"      END\n" +
"\n" +
"      -- Check objects\n" +
"      IF EXISTS(SELECT * FROM @SelectedCheckCommands WHERE CheckCommand = 'CHECKTABLE')\n" +
"      AND (@CurrentAvailabilityGroupRole = 'PRIMARY' OR (@CurrentAvailabilityGroupRole = 'SECONDARY' AND @CurrentSecondaryRoleAllowConnections = 'ALL') OR @CurrentAvailabilityGroupRole IS NULL)\n" +
"      AND (SYSDATETIME() < DATEADD(SECOND,@TimeLimit,@StartTime) OR @TimeLimit IS NULL)\n" +
"      BEGIN\n" +
"        SET @CurrentCommand = 'SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; SELECT schemas.[schema_id] AS SchemaID, schemas.[name] AS SchemaName, objects.[object_id] AS ObjectID, objects.[name] AS ObjectName, RTRIM(objects.[type]) AS ObjectType, 0 AS [Order], 0 AS Selected, 0 AS Completed FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.schema_id = schemas.schema_id LEFT OUTER JOIN sys.tables tables ON objects.object_id = tables.object_id WHERE objects.[type] IN(''U'',''V'') AND EXISTS(SELECT * FROM sys.indexes indexes WHERE indexes.object_id = objects.object_id)' + CASE WHEN @Version >= 12 THEN ' AND (tables.is_memory_optimized = 0 OR is_memory_optimized IS NULL)' ELSE '' END + ' ORDER BY schemas.name ASC, objects.name ASC'\n" +
"\n" +
"        INSERT INTO @tmpObjects (SchemaID, SchemaName, ObjectID, ObjectName, ObjectType, [Order], Selected, Completed)\n" +
"        EXECUTE @CurrentDatabase_sp_executesql @stmt = @CurrentCommand\n" +
"        SET @Error = @@ERROR\n" +
"        IF @Error <> 0 SET @ReturnCode = @Error\n" +
"\n" +
"        IF @Objects IS NULL\n" +
"        BEGIN\n" +
"          UPDATE tmpObjects\n" +
"          SET tmpObjects.Selected = 1\n" +
"          FROM @tmpObjects tmpObjects\n" +
"        END\n" +
"        ELSE\n" +
"        BEGIN\n" +
"          UPDATE tmpObjects\n" +
"          SET tmpObjects.Selected = SelectedObjects.Selected\n" +
"          FROM @tmpObjects tmpObjects\n" +
"          INNER JOIN @SelectedObjects SelectedObjects\n" +
"          ON @CurrentDatabaseName LIKE REPLACE(SelectedObjects.DatabaseName,'_','[_]') AND tmpObjects.SchemaName LIKE REPLACE(SelectedObjects.SchemaName,'_','[_]') AND tmpObjects.ObjectName LIKE REPLACE(SelectedObjects.ObjectName,'_','[_]')\n" +
"          WHERE SelectedObjects.Selected = 1\n" +
"\n" +
"          UPDATE tmpObjects\n" +
"          SET tmpObjects.Selected = SelectedObjects.Selected\n" +
"          FROM @tmpObjects tmpObjects\n" +
"          INNER JOIN @SelectedObjects SelectedObjects\n" +
"          ON @CurrentDatabaseName LIKE REPLACE(SelectedObjects.DatabaseName,'_','[_]') AND tmpObjects.SchemaName LIKE REPLACE(SelectedObjects.SchemaName,'_','[_]') AND tmpObjects.ObjectName LIKE REPLACE(SelectedObjects.ObjectName,'_','[_]')\n" +
"          WHERE SelectedObjects.Selected = 0\n" +
"\n" +
"          UPDATE tmpObjects\n" +
"          SET tmpObjects.StartPosition = SelectedObjects2.StartPosition\n" +
"          FROM @tmpObjects tmpObjects\n" +
"          INNER JOIN (SELECT tmpObjects.SchemaName, tmpObjects.ObjectName, MIN(SelectedObjects.StartPosition) AS StartPosition\n" +
"                      FROM @tmpObjects tmpObjects\n" +
"                      INNER JOIN @SelectedObjects SelectedObjects\n" +
"                      ON @CurrentDatabaseName LIKE REPLACE(SelectedObjects.DatabaseName,'_','[_]') AND tmpObjects.SchemaName LIKE REPLACE(SelectedObjects.SchemaName,'_','[_]') AND tmpObjects.ObjectName LIKE REPLACE(SelectedObjects.ObjectName,'_','[_]')\n" +
"                      WHERE SelectedObjects.Selected = 1\n" +
"                      GROUP BY tmpObjects.SchemaName, tmpObjects.ObjectName) SelectedObjects2\n" +
"          ON tmpObjects.SchemaName = SelectedObjects2.SchemaName AND tmpObjects.ObjectName = SelectedObjects2.ObjectName\n" +
"        END;\n" +
"\n" +
"        WITH tmpObjects AS (\n" +
"        SELECT SchemaName, ObjectName, [Order], ROW_NUMBER() OVER (ORDER BY StartPosition ASC, SchemaName ASC, ObjectName ASC) AS RowNumber\n" +
"        FROM @tmpObjects tmpObjects\n" +
"        WHERE Selected = 1\n" +
"        )\n" +
"        UPDATE tmpObjects\n" +
"        SET [Order] = RowNumber\n" +
"\n" +
"        SET @ErrorMessage = ''\n" +
"        SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + '.' + QUOTENAME(SchemaName) + '.' + QUOTENAME(ObjectName) + ', '\n" +
"        FROM @SelectedObjects SelectedObjects\n" +
"        WHERE DatabaseName = @CurrentDatabaseName\n" +
"        AND SchemaName NOT LIKE '%[%]%'\n" +
"        AND ObjectName NOT LIKE '%[%]%'\n" +
"        AND NOT EXISTS (SELECT * FROM @tmpObjects WHERE SchemaName = SelectedObjects.SchemaName AND ObjectName = SelectedObjects.ObjectName)\n" +
"        IF @@ROWCOUNT > 0\n" +
"        BEGIN\n" +
"          SET @ErrorMessage = 'The following objects do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.'\n" +
"          RAISERROR('%s',10,1,@ErrorMessage) WITH NOWAIT\n" +
"          SET @Error = @@ERROR\n" +
"          RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"        END\n" +
"\n" +
"        WHILE (SYSDATETIME() < DATEADD(SECOND,@TimeLimit,@StartTime) OR @TimeLimit IS NULL)\n" +
"        BEGIN\n" +
"          SELECT TOP 1 @CurrentOID = ID,\n" +
"                       @CurrentSchemaID = SchemaID,\n" +
"                       @CurrentSchemaName = SchemaName,\n" +
"                       @CurrentObjectID = ObjectID,\n" +
"                       @CurrentObjectName = ObjectName,\n" +
"                       @CurrentObjectType = ObjectType\n" +
"          FROM @tmpObjects\n" +
"          WHERE Selected = 1\n" +
"          AND Completed = 0\n" +
"          ORDER BY [Order] ASC\n" +
"\n" +
"          IF @@ROWCOUNT = 0\n" +
"          BEGIN\n" +
"            BREAK\n" +
"          END\n" +
"\n" +
"          -- Does the object exist?\n" +
"          SET @CurrentCommand = ''\n" +
"          IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"          SET @CurrentCommand += 'IF EXISTS(SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.schema_id = schemas.schema_id LEFT OUTER JOIN sys.tables tables ON objects.object_id = tables.object_id WHERE objects.[type] IN(''U'',''V'') AND EXISTS(SELECT * FROM sys.indexes indexes WHERE indexes.object_id = objects.object_id)' + CASE WHEN @Version >= 12 THEN ' AND (tables.is_memory_optimized = 0 OR is_memory_optimized IS NULL)' ELSE '' END + ' AND schemas.[schema_id] = @ParamSchemaID AND schemas.[name] = @ParamSchemaName AND objects.[object_id] = @ParamObjectID AND objects.[name] = @ParamObjectName AND objects.[type] = @ParamObjectType) BEGIN SET @ParamObjectExists = 1 END'\n" +
"\n" +
"          BEGIN TRY\n" +
"            EXECUTE @CurrentDatabase_sp_executesql @stmt = @CurrentCommand, @params = N'@ParamSchemaID int, @ParamSchemaName sysname, @ParamObjectID int, @ParamObjectName sysname, @ParamObjectType sysname, @ParamObjectExists bit OUTPUT', @ParamSchemaID = @CurrentSchemaID, @ParamSchemaName = @CurrentSchemaName, @ParamObjectID = @CurrentObjectID, @ParamObjectName = @CurrentObjectName, @ParamObjectType = @CurrentObjectType, @ParamObjectExists = @CurrentObjectExists OUTPUT\n" +
"\n" +
"            IF @CurrentObjectExists IS NULL SET @CurrentObjectExists = 0\n" +
"          END TRY\n" +
"          BEGIN CATCH\n" +
"            SET @ErrorMessage = 'Msg ' + CAST(ERROR_NUMBER() AS nvarchar) + ', ' + ISNULL(ERROR_MESSAGE(),'') + CASE WHEN ERROR_NUMBER() = 1222 THEN ', ' + 'The object ' + QUOTENAME(@CurrentDatabaseName) + '.' + QUOTENAME(@CurrentSchemaName) + '.' + QUOTENAME(@CurrentObjectName) + ' is locked. It could not be checked if the object exists.' ELSE '' END\n" +
"            SET @Severity = CASE WHEN ERROR_NUMBER() IN(1205,1222) THEN @LockMessageSeverity ELSE 16 END\n" +
"            RAISERROR('%s',@Severity,1,@ErrorMessage) WITH NOWAIT\n" +
"            RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"            IF NOT (ERROR_NUMBER() IN(1205,1222) AND @LockMessageSeverity = 10)\n" +
"            BEGIN\n" +
"              SET @ReturnCode = ERROR_NUMBER()\n" +
"            END\n" +
"          END CATCH\n" +
"\n" +
"          IF @CurrentObjectExists = 1\n" +
"          BEGIN\n" +
"            SET @CurrentDatabaseContext = @CurrentDatabaseName\n" +
"\n" +
"            SET @CurrentCommandType = 'DBCC_CHECKTABLE'\n" +
"\n" +
"            SET @CurrentCommand = ''\n" +
"            IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"            SET @CurrentCommand += 'DBCC CHECKTABLE (''' + QUOTENAME(@CurrentSchemaName) + '.' + QUOTENAME(@CurrentObjectName) + ''''\n" +
"            IF @NoIndex = 'Y' SET @CurrentCommand += ', NOINDEX'\n" +
"            SET @CurrentCommand += ') WITH NO_INFOMSGS, ALL_ERRORMSGS'\n" +
"            IF @DataPurity = 'Y' SET @CurrentCommand += ', DATA_PURITY'\n" +
"            IF @PhysicalOnly = 'Y' SET @CurrentCommand += ', PHYSICAL_ONLY'\n" +
"            IF @ExtendedLogicalChecks = 'Y' SET @CurrentCommand += ', EXTENDED_LOGICAL_CHECKS'\n" +
"            IF @TabLock = 'Y' SET @CurrentCommand += ', TABLOCK'\n" +
"            IF @MaxDOP IS NOT NULL SET @CurrentCommand += ', MAXDOP = ' + CAST(@MaxDOP AS nvarchar)\n" +
"\n" +
"            EXECUTE @CurrentCommandOutput = dbo.CommandExecute @DatabaseContext = @CurrentDatabaseContext, @Command = @CurrentCommand, @CommandType = @CurrentCommandType, @Mode = 1, @DatabaseName = @CurrentDatabaseName, @SchemaName = @CurrentSchemaName, @ObjectName = @CurrentObjectName, @ObjectType = @CurrentObjectType, @LogToTable = @LogToTable, @Execute = @Execute\n" +
"            SET @Error = @@ERROR\n" +
"            IF @Error <> 0 SET @CurrentCommandOutput = @Error\n" +
"            IF @CurrentCommandOutput <> 0 SET @ReturnCode = @CurrentCommandOutput\n" +
"          END\n" +
"\n" +
"          UPDATE @tmpObjects\n" +
"          SET Completed = 1\n" +
"          WHERE Selected = 1\n" +
"          AND Completed = 0\n" +
"          AND ID = @CurrentOID\n" +
"\n" +
"          SET @CurrentOID = NULL\n" +
"          SET @CurrentSchemaID = NULL\n" +
"          SET @CurrentSchemaName = NULL\n" +
"          SET @CurrentObjectID = NULL\n" +
"          SET @CurrentObjectName = NULL\n" +
"          SET @CurrentObjectType = NULL\n" +
"          SET @CurrentObjectExists = NULL\n" +
"\n" +
"          SET @CurrentDatabaseContext = NULL\n" +
"          SET @CurrentCommand = NULL\n" +
"          SET @CurrentCommandOutput = NULL\n" +
"          SET @CurrentCommandType = NULL\n" +
"        END\n" +
"      END\n" +
"\n" +
"      -- Check catalog\n" +
"      IF EXISTS(SELECT * FROM @SelectedCheckCommands WHERE CheckCommand = 'CHECKCATALOG') AND (SYSDATETIME() < DATEADD(SECOND,@TimeLimit,@StartTime) OR @TimeLimit IS NULL)\n" +
"      BEGIN\n" +
"        SET @CurrentDatabaseContext = CASE WHEN SERVERPROPERTY('EngineEdition') = 5 THEN @CurrentDatabaseName ELSE 'master' END\n" +
"\n" +
"        SET @CurrentCommandType = 'DBCC_CHECKCATALOG'\n" +
"\n" +
"        SET @CurrentCommand = ''\n" +
"        IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"        SET @CurrentCommand += 'DBCC CHECKCATALOG (' + QUOTENAME(@CurrentDatabaseName)\n" +
"        SET @CurrentCommand += ') WITH NO_INFOMSGS'\n" +
"\n" +
"        EXECUTE @CurrentCommandOutput = dbo.CommandExecute @DatabaseContext = @CurrentDatabaseContext, @Command = @CurrentCommand, @CommandType = @CurrentCommandType, @Mode = 1, @DatabaseName = @CurrentDatabaseName, @LogToTable = @LogToTable, @Execute = @Execute\n" +
"        SET @Error = @@ERROR\n" +
"        IF @Error <> 0 SET @CurrentCommandOutput = @Error\n" +
"        IF @CurrentCommandOutput <> 0 SET @ReturnCode = @CurrentCommandOutput\n" +
"      END\n" +
"\n" +
"    END\n" +
"\n" +
"    IF @CurrentDatabaseState = 'SUSPECT'\n" +
"    BEGIN\n" +
"      SET @ErrorMessage = 'The database ' + QUOTENAME(@CurrentDatabaseName) + ' is in a SUSPECT state.'\n" +
"      RAISERROR('%s',16,1,@ErrorMessage) WITH NOWAIT\n" +
"      SET @Error = @@ERROR\n" +
"      RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    -- Update that the database is completed\n" +
"    IF @DatabasesInParallel = 'Y'\n" +
"    BEGIN\n" +
"      UPDATE dbo.QueueDatabase\n" +
"      SET DatabaseEndTime = SYSDATETIME()\n" +
"      WHERE QueueID = @QueueID\n" +
"      AND DatabaseName = @CurrentDatabaseName\n" +
"    END\n" +
"    ELSE\n" +
"    BEGIN\n" +
"      UPDATE @tmpDatabases\n" +
"      SET Completed = 1\n" +
"      WHERE Selected = 1\n" +
"      AND Completed = 0\n" +
"      AND ID = @CurrentDBID\n" +
"    END\n" +
"\n" +
"    -- Clear variables\n" +
"    SET @CurrentDBID = NULL\n" +
"    SET @CurrentDatabaseName = NULL\n" +
"\n" +
"    SET @CurrentDatabase_sp_executesql = NULL\n" +
"\n" +
"    SET @CurrentUserAccess = NULL\n" +
"    SET @CurrentIsReadOnly = NULL\n" +
"    SET @CurrentDatabaseState = NULL\n" +
"    SET @CurrentInStandby = NULL\n" +
"    SET @CurrentRecoveryModel = NULL\n" +
"\n" +
"    SET @CurrentIsDatabaseAccessible = NULL\n" +
"    SET @CurrentReplicaID = NULL\n" +
"    SET @CurrentAvailabilityGroupID = NULL\n" +
"    SET @CurrentAvailabilityGroup = NULL\n" +
"    SET @CurrentAvailabilityGroupRole = NULL\n" +
"    SET @CurrentAvailabilityGroupBackupPreference = NULL\n" +
"    SET @CurrentSecondaryRoleAllowConnections = NULL\n" +
"    SET @CurrentIsPreferredBackupReplica = NULL\n" +
"    SET @CurrentDatabaseMirroringRole = NULL\n" +
"\n" +
"    SET @CurrentDatabaseContext = NULL\n" +
"    SET @CurrentCommand = NULL\n" +
"    SET @CurrentCommandOutput = NULL\n" +
"    SET @CurrentCommandType = NULL\n" +
"\n" +
"    DELETE FROM @tmpFileGroups\n" +
"    DELETE FROM @tmpObjects\n" +
"\n" +
"  END\n";
    }
    
    public String part10(){
        return "  ----------------------------------------------------------------------------------------------------\n" +
"  --// Log completing information                                                                 //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  Logging:\n" +
"  SET @EndMessage = 'Date and time: ' + CONVERT(nvarchar,SYSDATETIME(),120)\n" +
"  RAISERROR('%s',10,1,@EndMessage) WITH NOWAIT\n" +
"\n" +
"  RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"  IF @ReturnCode <> 0\n" +
"  BEGIN\n" +
"    RETURN @ReturnCode\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"END\n" +
"\n" +
"GO\n" +
"SET ANSI_NULLS ON\n" +
"GO\n" +
"SET QUOTED_IDENTIFIER ON\n" +
"GO\n" +
"IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[IndexOptimize]') AND type in (N'P', N'PC'))\n" +
"BEGIN\n" +
"EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[IndexOptimize] AS'\n" +
"END\n" +
"GO\n" +
"ALTER PROCEDURE [dbo].[IndexOptimize]\n" +
"\n" +
"@Databases nvarchar(max) = NULL,\n" +
"@FragmentationLow nvarchar(max) = NULL,\n" +
"@FragmentationMedium nvarchar(max) = 'INDEX_REORGANIZE,INDEX_REBUILD_ONLINE,INDEX_REBUILD_OFFLINE',\n" +
"@FragmentationHigh nvarchar(max) = 'INDEX_REBUILD_ONLINE,INDEX_REBUILD_OFFLINE',\n" +
"@FragmentationLevel1 int = 5,\n" +
"@FragmentationLevel2 int = 30,\n" +
"@MinNumberOfPages int = 1000,\n" +
"@MaxNumberOfPages int = NULL,\n" +
"@SortInTempdb nvarchar(max) = 'N',\n" +
"@MaxDOP int = NULL,\n" +
"@FillFactor int = NULL,\n" +
"@PadIndex nvarchar(max) = NULL,\n" +
"@LOBCompaction nvarchar(max) = 'Y',\n" +
"@UpdateStatistics nvarchar(max) = NULL,\n" +
"@OnlyModifiedStatistics nvarchar(max) = 'N',\n" +
"@StatisticsModificationLevel int = NULL,\n" +
"@StatisticsSample int = NULL,\n" +
"@StatisticsResample nvarchar(max) = 'N',\n" +
"@PartitionLevel nvarchar(max) = 'Y',\n" +
"@MSShippedObjects nvarchar(max) = 'N',\n" +
"@Indexes nvarchar(max) = NULL,\n" +
"@TimeLimit int = NULL,\n" +
"@Delay int = NULL,\n" +
"@WaitAtLowPriorityMaxDuration int = NULL,\n" +
"@WaitAtLowPriorityAbortAfterWait nvarchar(max) = NULL,\n" +
"@Resumable nvarchar(max) = 'N',\n" +
"@AvailabilityGroups nvarchar(max) = NULL,\n" +
"@LockTimeout int = NULL,\n" +
"@LockMessageSeverity int = 16,\n" +
"@StringDelimiter nvarchar(max) = ',',\n" +
"@DatabaseOrder nvarchar(max) = NULL,\n" +
"@DatabasesInParallel nvarchar(max) = 'N',\n" +
"@ExecuteAsUser nvarchar(max) = NULL,\n" +
"@LogToTable nvarchar(max) = 'N',\n" +
"@Execute nvarchar(max) = 'Y'\n" +
"\n" +
"AS\n" +
"\n" +
"BEGIN\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Source:  https://ola.hallengren.com                                                        //--\n" +
"  --// License: https://ola.hallengren.com/license.html                                           //--\n" +
"  --// GitHub:  https://github.com/olahallengren/sql-server-maintenance-solution                  //--\n" +
"  --// Version: 2020-12-31 18:58:56                                                               //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET NOCOUNT ON\n" +
"\n" +
"  SET ARITHABORT ON\n" +
"\n" +
"  SET NUMERIC_ROUNDABORT OFF\n" +
"\n" +
"  DECLARE @StartMessage nvarchar(max)\n" +
"  DECLARE @EndMessage nvarchar(max)\n" +
"  DECLARE @DatabaseMessage nvarchar(max)\n" +
"  DECLARE @ErrorMessage nvarchar(max)\n" +
"  DECLARE @Severity int\n" +
"\n" +
"  DECLARE @StartTime datetime2 = SYSDATETIME()\n" +
"  DECLARE @SchemaName nvarchar(max) = OBJECT_SCHEMA_NAME(@@PROCID)\n" +
"  DECLARE @ObjectName nvarchar(max) = OBJECT_NAME(@@PROCID)\n" +
"  DECLARE @VersionTimestamp nvarchar(max) = SUBSTRING(OBJECT_DEFINITION(@@PROCID),CHARINDEX('--// Version: ',OBJECT_DEFINITION(@@PROCID)) + LEN('--// Version: ') + 1, 19)\n" +
"  DECLARE @Parameters nvarchar(max)\n" +
"\n" +
"  DECLARE @HostPlatform nvarchar(max)\n" +
"\n" +
"  DECLARE @PartitionLevelStatistics bit\n" +
"\n" +
"  DECLARE @QueueID int\n" +
"  DECLARE @QueueStartTime datetime2\n" +
"\n" +
"  DECLARE @CurrentDBID int\n" +
"  DECLARE @CurrentDatabaseName nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentDatabase_sp_executesql nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentExecuteAsUserExists bit\n" +
"  DECLARE @CurrentUserAccess nvarchar(max)\n" +
"  DECLARE @CurrentIsReadOnly bit\n" +
"  DECLARE @CurrentDatabaseState nvarchar(max)\n" +
"  DECLARE @CurrentInStandby bit\n" +
"  DECLARE @CurrentRecoveryModel nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentIsDatabaseAccessible bit\n" +
"  DECLARE @CurrentReplicaID uniqueidentifier\n" +
"  DECLARE @CurrentAvailabilityGroupID uniqueidentifier\n" +
"  DECLARE @CurrentAvailabilityGroup nvarchar(max)\n" +
"  DECLARE @CurrentAvailabilityGroupRole nvarchar(max)\n" +
"  DECLARE @CurrentDatabaseMirroringRole nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentDatabaseContext nvarchar(max)\n" +
"  DECLARE @CurrentCommand nvarchar(max)\n" +
"  DECLARE @CurrentCommandOutput int\n" +
"  DECLARE @CurrentCommandType nvarchar(max)\n" +
"  DECLARE @CurrentComment nvarchar(max)\n" +
"  DECLARE @CurrentExtendedInfo xml\n" +
"\n" +
"  DECLARE @Errors TABLE (ID int IDENTITY PRIMARY KEY,\n" +
"                         [Message] nvarchar(max) NOT NULL,\n" +
"                         Severity int NOT NULL,\n" +
"                         [State] int)\n" +
"\n" +
"  DECLARE @CurrentMessage nvarchar(max)\n" +
"  DECLARE @CurrentSeverity int\n" +
"  DECLARE @CurrentState int\n" +
"\n" +
"  DECLARE @CurrentIxID int\n" +
"  DECLARE @CurrentIxOrder int\n" +
"  DECLARE @CurrentSchemaID int\n" +
"  DECLARE @CurrentSchemaName nvarchar(max)\n" +
"  DECLARE @CurrentObjectID int\n" +
"  DECLARE @CurrentObjectName nvarchar(max)\n" +
"  DECLARE @CurrentObjectType nvarchar(max)\n" +
"  DECLARE @CurrentIsMemoryOptimized bit\n" +
"  DECLARE @CurrentIndexID int\n" +
"  DECLARE @CurrentIndexName nvarchar(max)\n" +
"  DECLARE @CurrentIndexType int\n" +
"  DECLARE @CurrentStatisticsID int\n" +
"  DECLARE @CurrentStatisticsName nvarchar(max)\n" +
"  DECLARE @CurrentPartitionID bigint\n" +
"  DECLARE @CurrentPartitionNumber int\n" +
"  DECLARE @CurrentPartitionCount int\n" +
"  DECLARE @CurrentIsPartition bit\n" +
"  DECLARE @CurrentIndexExists bit\n" +
"  DECLARE @CurrentStatisticsExists bit\n" +
"  DECLARE @CurrentIsImageText bit\n" +
"  DECLARE @CurrentIsNewLOB bit\n" +
"  DECLARE @CurrentIsFileStream bit\n" +
"  DECLARE @CurrentIsColumnStore bit\n" +
"  DECLARE @CurrentIsComputed bit\n" +
"  DECLARE @CurrentIsTimestamp bit\n" +
"  DECLARE @CurrentAllowPageLocks bit\n" +
"  DECLARE @CurrentNoRecompute bit\n" +
"  DECLARE @CurrentIsIncremental bit\n" +
"  DECLARE @CurrentRowCount bigint\n" +
"  DECLARE @CurrentModificationCounter bigint\n" +
"  DECLARE @CurrentOnReadOnlyFileGroup bit\n" +
"  DECLARE @CurrentResumableIndexOperation bit\n" +
"  DECLARE @CurrentFragmentationLevel float\n" +
"  DECLARE @CurrentPageCount bigint\n" +
"  DECLARE @CurrentFragmentationGroup nvarchar(max)\n" +
"  DECLARE @CurrentAction nvarchar(max)\n" +
"  DECLARE @CurrentMaxDOP int\n" +
"  DECLARE @CurrentUpdateStatistics nvarchar(max)\n" +
"  DECLARE @CurrentStatisticsSample int\n" +
"  DECLARE @CurrentStatisticsResample nvarchar(max)\n" +
"  DECLARE @CurrentDelay datetime\n" +
"\n" +
"  DECLARE @tmpDatabases TABLE (ID int IDENTITY,\n" +
"                               DatabaseName nvarchar(max),\n" +
"                               DatabaseType nvarchar(max),\n" +
"                               AvailabilityGroup bit,\n" +
"                               StartPosition int,\n" +
"                               DatabaseSize bigint,\n" +
"                               [Order] int,\n" +
"                               Selected bit,\n" +
"                               Completed bit,\n" +
"                               PRIMARY KEY(Selected, Completed, [Order], ID))\n" +
"\n" +
"  DECLARE @tmpAvailabilityGroups TABLE (ID int IDENTITY PRIMARY KEY,\n" +
"                                        AvailabilityGroupName nvarchar(max),\n" +
"                                        StartPosition int,\n" +
"                                        Selected bit)\n" +
"\n" +
"  DECLARE @tmpDatabasesAvailabilityGroups TABLE (DatabaseName nvarchar(max),\n" +
"                                                 AvailabilityGroupName nvarchar(max))\n" +
"\n" +
"  DECLARE @tmpIndexesStatistics TABLE (ID int IDENTITY,\n" +
"                                       SchemaID int,\n" +
"                                       SchemaName nvarchar(max),\n" +
"                                       ObjectID int,\n" +
"                                       ObjectName nvarchar(max),\n" +
"                                       ObjectType nvarchar(max),\n" +
"                                       IsMemoryOptimized bit,\n" +
"                                       IndexID int,\n" +
"                                       IndexName nvarchar(max),\n" +
"                                       IndexType int,\n" +
"                                       AllowPageLocks bit,\n" +
"                                       IsImageText bit,\n" +
"                                       IsNewLOB bit,\n" +
"                                       IsFileStream bit,\n" +
"                                       IsColumnStore bit,\n" +
"                                       IsComputed bit,\n" +
"                                       IsTimestamp bit,\n" +
"                                       OnReadOnlyFileGroup bit,\n" +
"                                       ResumableIndexOperation bit,\n" +
"                                       StatisticsID int,\n" +
"                                       StatisticsName nvarchar(max),\n" +
"                                       [NoRecompute] bit,\n" +
"                                       IsIncremental bit,\n" +
"                                       PartitionID bigint,\n" +
"                                       PartitionNumber int,\n" +
"                                       PartitionCount int,\n" +
"                                       StartPosition int,\n" +
"                                       [Order] int,\n" +
"                                       Selected bit,\n" +
"                                       Completed bit,\n" +
"                                       PRIMARY KEY(Selected, Completed, [Order], ID))\n" +
"\n" +
"  DECLARE @SelectedDatabases TABLE (DatabaseName nvarchar(max),\n" +
"                                    DatabaseType nvarchar(max),\n" +
"                                    AvailabilityGroup nvarchar(max),\n" +
"                                    StartPosition int,\n" +
"                                    Selected bit)\n" +
"\n" +
"  DECLARE @SelectedAvailabilityGroups TABLE (AvailabilityGroupName nvarchar(max),\n" +
"                                             StartPosition int,\n" +
"                                             Selected bit)\n" +
"\n" +
"  DECLARE @SelectedIndexes TABLE (DatabaseName nvarchar(max),\n" +
"                                  SchemaName nvarchar(max),\n" +
"                                  ObjectName nvarchar(max),\n" +
"                                  IndexName nvarchar(max),\n" +
"                                  StartPosition int,\n" +
"                                  Selected bit)\n" +
"\n" +
"  DECLARE @Actions TABLE ([Action] nvarchar(max))\n" +
"\n" +
"  INSERT INTO @Actions([Action]) VALUES('INDEX_REBUILD_ONLINE')\n" +
"  INSERT INTO @Actions([Action]) VALUES('INDEX_REBUILD_OFFLINE')\n" +
"  INSERT INTO @Actions([Action]) VALUES('INDEX_REORGANIZE')\n" +
"\n" +
"  DECLARE @ActionsPreferred TABLE (FragmentationGroup nvarchar(max),\n" +
"                                   [Priority] int,\n" +
"                                   [Action] nvarchar(max))\n" +
"\n" +
"  DECLARE @CurrentActionsAllowed TABLE ([Action] nvarchar(max))\n" +
"\n" +
"  DECLARE @CurrentAlterIndexWithClauseArguments TABLE (ID int IDENTITY,\n" +
"                                                       Argument nvarchar(max),\n" +
"                                                       Added bit DEFAULT 0)\n" +
"\n" +
"  DECLARE @CurrentAlterIndexArgumentID int\n" +
"  DECLARE @CurrentAlterIndexArgument nvarchar(max)\n" +
"  DECLARE @CurrentAlterIndexWithClause nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentUpdateStatisticsWithClauseArguments TABLE (ID int IDENTITY,\n" +
"                                                             Argument nvarchar(max),\n" +
"                                                             Added bit DEFAULT 0)\n" +
"\n" +
"  DECLARE @CurrentUpdateStatisticsArgumentID int\n" +
"  DECLARE @CurrentUpdateStatisticsArgument nvarchar(max)\n" +
"  DECLARE @CurrentUpdateStatisticsWithClause nvarchar(max)\n" +
"\n" +
"  DECLARE @Error int = 0\n" +
"  DECLARE @ReturnCode int = 0\n" +
"\n" +
"  DECLARE @EmptyLine nvarchar(max) = CHAR(9)\n" +
"\n" +
"  DECLARE @Version numeric(18,10) = CAST(LEFT(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max)),CHARINDEX('.',CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max))) - 1) + '.' + REPLACE(RIGHT(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max)), LEN(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max))) - CHARINDEX('.',CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max)))),'.','') AS numeric(18,10))\n" +
"\n" +
"  IF @Version >= 14\n" +
"  BEGIN\n" +
"    SELECT @HostPlatform = host_platform\n" +
"    FROM sys.dm_os_host_info\n" +
"  END\n" +
"  ELSE\n" +
"  BEGIN\n" +
"    SET @HostPlatform = 'Windows'\n" +
"  END\n" +
"\n" +
"  DECLARE @AmazonRDS bit = CASE WHEN DB_ID('rdsadmin') IS NOT NULL AND SUSER_SNAME(0x01) = 'rdsa' THEN 1 ELSE 0 END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Log initial information                                                                    //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @Parameters = '@Databases = ' + ISNULL('''' + REPLACE(@Databases,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @FragmentationLow = ' + ISNULL('''' + REPLACE(@FragmentationLow,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @FragmentationMedium = ' + ISNULL('''' + REPLACE(@FragmentationMedium,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @FragmentationHigh = ' + ISNULL('''' + REPLACE(@FragmentationHigh,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @FragmentationLevel1 = ' + ISNULL(CAST(@FragmentationLevel1 AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @FragmentationLevel2 = ' + ISNULL(CAST(@FragmentationLevel2 AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @MinNumberOfPages = ' + ISNULL(CAST(@MinNumberOfPages AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @MaxNumberOfPages = ' + ISNULL(CAST(@MaxNumberOfPages AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @SortInTempdb = ' + ISNULL('''' + REPLACE(@SortInTempdb,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @MaxDOP = ' + ISNULL(CAST(@MaxDOP AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @FillFactor = ' + ISNULL(CAST(@FillFactor AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @PadIndex = ' + ISNULL('''' + REPLACE(@PadIndex,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @LOBCompaction = ' + ISNULL('''' + REPLACE(@LOBCompaction,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @UpdateStatistics = ' + ISNULL('''' + REPLACE(@UpdateStatistics,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @OnlyModifiedStatistics = ' + ISNULL('''' + REPLACE(@OnlyModifiedStatistics,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @StatisticsModificationLevel = ' + ISNULL(CAST(@StatisticsModificationLevel AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @StatisticsSample = ' + ISNULL(CAST(@StatisticsSample AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @StatisticsResample = ' + ISNULL('''' + REPLACE(@StatisticsResample,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @PartitionLevel = ' + ISNULL('''' + REPLACE(@PartitionLevel,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @MSShippedObjects = ' + ISNULL('''' + REPLACE(@MSShippedObjects,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Indexes = ' + ISNULL('''' + REPLACE(@Indexes,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @TimeLimit = ' + ISNULL(CAST(@TimeLimit AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @Delay = ' + ISNULL(CAST(@Delay AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @WaitAtLowPriorityMaxDuration = ' + ISNULL(CAST(@WaitAtLowPriorityMaxDuration AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @WaitAtLowPriorityAbortAfterWait = ' + ISNULL('''' + REPLACE(@WaitAtLowPriorityAbortAfterWait,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Resumable = ' + ISNULL('''' + REPLACE(@Resumable,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @AvailabilityGroups = ' + ISNULL('''' + REPLACE(@AvailabilityGroups,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @LockTimeout = ' + ISNULL(CAST(@LockTimeout AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @LockMessageSeverity = ' + ISNULL(CAST(@LockMessageSeverity AS nvarchar),'NULL')\n" +
"  SET @Parameters += ', @StringDelimiter = ' + ISNULL('''' + REPLACE(@StringDelimiter,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @DatabaseOrder = ' + ISNULL('''' + REPLACE(@DatabaseOrder,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @DatabasesInParallel = ' + ISNULL('''' + REPLACE(@DatabasesInParallel,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @ExecuteAsUser = ' + ISNULL('''' + REPLACE(@ExecuteAsUser,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @LogToTable = ' + ISNULL('''' + REPLACE(@LogToTable,'''','''''') + '''','NULL')\n" +
"  SET @Parameters += ', @Execute = ' + ISNULL('''' + REPLACE(@Execute,'''','''''') + '''','NULL')\n" +
"\n" +
"  SET @StartMessage = 'Date and time: ' + CONVERT(nvarchar,@StartTime,120)\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Server: ' + CAST(SERVERPROPERTY('ServerName') AS nvarchar(max))\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Version: ' + CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max))\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Edition: ' + CAST(SERVERPROPERTY('Edition') AS nvarchar(max))\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Platform: ' + @HostPlatform\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Procedure: ' + QUOTENAME(DB_NAME(DB_ID())) + '.' + QUOTENAME(@SchemaName) + '.' + QUOTENAME(@ObjectName)\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Parameters: ' + @Parameters\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Version: ' + @VersionTimestamp\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  SET @StartMessage = 'Source: https://ola.hallengren.com'\n" +
"  RAISERROR('%s',10,1,@StartMessage) WITH NOWAIT\n" +
"\n" +
"  RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check core requirements                                                                    //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF NOT (SELECT [compatibility_level] FROM sys.databases WHERE database_id = DB_ID()) >= 90\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The database ' + QUOTENAME(DB_NAME(DB_ID())) + ' has to be in compatibility level 90 or higher.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF NOT (SELECT uses_ansi_nulls FROM sys.sql_modules WHERE [object_id] = @@PROCID) = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'ANSI_NULLS has to be set to ON for the stored procedure.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF NOT (SELECT uses_quoted_identifier FROM sys.sql_modules WHERE [object_id] = @@PROCID) = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'QUOTED_IDENTIFIER has to be set to ON for the stored procedure.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'P' AND schemas.[name] = 'dbo' AND objects.[name] = 'CommandExecute')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The stored procedure CommandExecute is missing. Download https://ola.hallengren.com/scripts/CommandExecute.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'P' AND schemas.[name] = 'dbo' AND objects.[name] = 'CommandExecute' AND OBJECT_DEFINITION(objects.[object_id]) NOT LIKE '%@DatabaseContext%')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The stored procedure CommandExecute needs to be updated. Download https://ola.hallengren.com/scripts/CommandExecute.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @LogToTable = 'Y' AND NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'U' AND schemas.[name] = 'dbo' AND objects.[name] = 'CommandLog')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The table CommandLog is missing. Download https://ola.hallengren.com/scripts/CommandLog.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DatabasesInParallel = 'Y' AND NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'U' AND schemas.[name] = 'dbo' AND objects.[name] = 'Queue')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The table Queue is missing. Download https://ola.hallengren.com/scripts/Queue.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DatabasesInParallel = 'Y' AND NOT EXISTS (SELECT * FROM sys.objects objects INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] = 'U' AND schemas.[name] = 'dbo' AND objects.[name] = 'QueueDatabase')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The table QueueDatabase is missing. Download https://ola.hallengren.com/scripts/QueueDatabase.sql.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @@TRANCOUNT <> 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The transaction count is not 0.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select databases                                                                           //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @Databases = REPLACE(@Databases, CHAR(10), '')\n" +
"  SET @Databases = REPLACE(@Databases, CHAR(13), '')\n" +
"\n" +
"  WHILE CHARINDEX(@StringDelimiter + ' ', @Databases) > 0 SET @Databases = REPLACE(@Databases, @StringDelimiter + ' ', @StringDelimiter)\n" +
"  WHILE CHARINDEX(' ' + @StringDelimiter, @Databases) > 0 SET @Databases = REPLACE(@Databases, ' ' + @StringDelimiter, @StringDelimiter)\n" +
"\n" +
"  SET @Databases = LTRIM(RTRIM(@Databases));\n" +
"\n" +
"  WITH Databases1 (StartPosition, EndPosition, DatabaseItem) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Databases, 1), 0), LEN(@Databases) + 1) AS EndPosition,\n" +
"         SUBSTRING(@Databases, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Databases, 1), 0), LEN(@Databases) + 1) - 1) AS DatabaseItem\n" +
"  WHERE @Databases IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Databases, EndPosition + 1), 0), LEN(@Databases) + 1) AS EndPosition,\n" +
"         SUBSTRING(@Databases, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Databases, EndPosition + 1), 0), LEN(@Databases) + 1) - EndPosition - 1) AS DatabaseItem\n" +
"  FROM Databases1\n" +
"  WHERE EndPosition < LEN(@Databases) + 1\n" +
"  ),\n" +
"  Databases2 (DatabaseItem, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN DatabaseItem LIKE '-%' THEN RIGHT(DatabaseItem,LEN(DatabaseItem) - 1) ELSE DatabaseItem END AS DatabaseItem,\n" +
"         StartPosition,\n" +
"         CASE WHEN DatabaseItem LIKE '-%' THEN 0 ELSE 1 END AS Selected\n" +
"  FROM Databases1\n" +
"  ),\n" +
"  Databases3 (DatabaseItem, DatabaseType, AvailabilityGroup, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN DatabaseItem IN('ALL_DATABASES','SYSTEM_DATABASES','USER_DATABASES','AVAILABILITY_GROUP_DATABASES') THEN '%' ELSE DatabaseItem END AS DatabaseItem,\n" +
"         CASE WHEN DatabaseItem = 'SYSTEM_DATABASES' THEN 'S' WHEN DatabaseItem = 'USER_DATABASES' THEN 'U' ELSE NULL END AS DatabaseType,\n" +
"         CASE WHEN DatabaseItem = 'AVAILABILITY_GROUP_DATABASES' THEN 1 ELSE NULL END AvailabilityGroup,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Databases2\n" +
"  ),\n" +
"  Databases4 (DatabaseName, DatabaseType, AvailabilityGroup, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN LEFT(DatabaseItem,1) = '[' AND RIGHT(DatabaseItem,1) = ']' THEN PARSENAME(DatabaseItem,1) ELSE DatabaseItem END AS DatabaseItem,\n" +
"         DatabaseType,\n" +
"         AvailabilityGroup,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Databases3\n" +
"  )\n" +
"  INSERT INTO @SelectedDatabases (DatabaseName, DatabaseType, AvailabilityGroup, StartPosition, Selected)\n" +
"  SELECT DatabaseName,\n" +
"         DatabaseType,\n" +
"         AvailabilityGroup,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Databases4\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  IF @Version >= 11 AND SERVERPROPERTY('IsHadrEnabled') = 1\n" +
"  BEGIN\n" +
"    INSERT INTO @tmpAvailabilityGroups (AvailabilityGroupName, Selected)\n" +
"    SELECT name AS AvailabilityGroupName,\n" +
"           0 AS Selected\n" +
"    FROM sys.availability_groups\n" +
"\n" +
"    INSERT INTO @tmpDatabasesAvailabilityGroups (DatabaseName, AvailabilityGroupName)\n" +
"    SELECT databases.name,\n" +
"           availability_groups.name\n" +
"    FROM sys.databases databases\n" +
"    INNER JOIN sys.availability_replicas availability_replicas ON databases.replica_id = availability_replicas.replica_id\n" +
"    INNER JOIN sys.availability_groups availability_groups ON availability_replicas.group_id = availability_groups.group_id\n" +
"  END\n" +
"\n" +
"  INSERT INTO @tmpDatabases (DatabaseName, DatabaseType, AvailabilityGroup, [Order], Selected, Completed)\n" +
"  SELECT [name] AS DatabaseName,\n" +
"         CASE WHEN name IN('master','msdb','model') OR is_distributor = 1 THEN 'S' ELSE 'U' END AS DatabaseType,\n" +
"         NULL AS AvailabilityGroup,\n" +
"         0 AS [Order],\n" +
"         0 AS Selected,\n" +
"         0 AS Completed\n" +
"  FROM sys.databases\n" +
"  WHERE [name] <> 'tempdb'\n" +
"  AND source_database_id IS NULL\n" +
"  ORDER BY [name] ASC\n" +
"\n" +
"  UPDATE tmpDatabases\n" +
"  SET AvailabilityGroup = CASE WHEN EXISTS (SELECT * FROM @tmpDatabasesAvailabilityGroups WHERE DatabaseName = tmpDatabases.DatabaseName) THEN 1 ELSE 0 END\n" +
"  FROM @tmpDatabases tmpDatabases\n" +
"\n" +
"  UPDATE tmpDatabases\n" +
"  SET tmpDatabases.Selected = SelectedDatabases.Selected\n" +
"  FROM @tmpDatabases tmpDatabases\n" +
"  INNER JOIN @SelectedDatabases SelectedDatabases\n" +
"  ON tmpDatabases.DatabaseName LIKE REPLACE(SelectedDatabases.DatabaseName,'_','[_]')\n" +
"  AND (tmpDatabases.DatabaseType = SelectedDatabases.DatabaseType OR SelectedDatabases.DatabaseType IS NULL)\n" +
"  AND (tmpDatabases.AvailabilityGroup = SelectedDatabases.AvailabilityGroup OR SelectedDatabases.AvailabilityGroup IS NULL)\n" +
"  WHERE SelectedDatabases.Selected = 1\n" +
"\n" +
"  UPDATE tmpDatabases\n" +
"  SET tmpDatabases.Selected = SelectedDatabases.Selected\n" +
"  FROM @tmpDatabases tmpDatabases\n" +
"  INNER JOIN @SelectedDatabases SelectedDatabases\n" +
"  ON tmpDatabases.DatabaseName LIKE REPLACE(SelectedDatabases.DatabaseName,'_','[_]')\n" +
"  AND (tmpDatabases.DatabaseType = SelectedDatabases.DatabaseType OR SelectedDatabases.DatabaseType IS NULL)\n" +
"  AND (tmpDatabases.AvailabilityGroup = SelectedDatabases.AvailabilityGroup OR SelectedDatabases.AvailabilityGroup IS NULL)\n" +
"  WHERE SelectedDatabases.Selected = 0\n" +
"\n" +
"  UPDATE tmpDatabases\n" +
"  SET tmpDatabases.StartPosition = SelectedDatabases2.StartPosition\n" +
"  FROM @tmpDatabases tmpDatabases\n" +
"  INNER JOIN (SELECT tmpDatabases.DatabaseName, MIN(SelectedDatabases.StartPosition) AS StartPosition\n" +
"              FROM @tmpDatabases tmpDatabases\n" +
"              INNER JOIN @SelectedDatabases SelectedDatabases\n" +
"              ON tmpDatabases.DatabaseName LIKE REPLACE(SelectedDatabases.DatabaseName,'_','[_]')\n" +
"              AND (tmpDatabases.DatabaseType = SelectedDatabases.DatabaseType OR SelectedDatabases.DatabaseType IS NULL)\n" +
"              AND (tmpDatabases.AvailabilityGroup = SelectedDatabases.AvailabilityGroup OR SelectedDatabases.AvailabilityGroup IS NULL)\n" +
"              WHERE SelectedDatabases.Selected = 1\n" +
"              GROUP BY tmpDatabases.DatabaseName) SelectedDatabases2\n" +
"  ON tmpDatabases.DatabaseName = SelectedDatabases2.DatabaseName\n" +
"\n" +
"  IF @Databases IS NOT NULL AND (NOT EXISTS(SELECT * FROM @SelectedDatabases) OR EXISTS(SELECT * FROM @SelectedDatabases WHERE DatabaseName IS NULL OR DatabaseName = ''))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Databases is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select availability groups                                                                 //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @AvailabilityGroups IS NOT NULL AND @Version >= 11 AND SERVERPROPERTY('IsHadrEnabled') = 1\n" +
"  BEGIN\n" +
"\n" +
"    SET @AvailabilityGroups = REPLACE(@AvailabilityGroups, CHAR(10), '')\n" +
"    SET @AvailabilityGroups = REPLACE(@AvailabilityGroups, CHAR(13), '')\n" +
"\n" +
"    WHILE CHARINDEX(@StringDelimiter + ' ', @AvailabilityGroups) > 0 SET @AvailabilityGroups = REPLACE(@AvailabilityGroups, @StringDelimiter + ' ', @StringDelimiter)\n" +
"    WHILE CHARINDEX(' ' + @StringDelimiter, @AvailabilityGroups) > 0 SET @AvailabilityGroups = REPLACE(@AvailabilityGroups, ' ' + @StringDelimiter, @StringDelimiter)\n" +
"\n" +
"    SET @AvailabilityGroups = LTRIM(RTRIM(@AvailabilityGroups));\n" +
"\n" +
"    WITH AvailabilityGroups1 (StartPosition, EndPosition, AvailabilityGroupItem) AS\n" +
"    (\n" +
"    SELECT 1 AS StartPosition,\n" +
"           ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @AvailabilityGroups, 1), 0), LEN(@AvailabilityGroups) + 1) AS EndPosition,\n" +
"           SUBSTRING(@AvailabilityGroups, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @AvailabilityGroups, 1), 0), LEN(@AvailabilityGroups) + 1) - 1) AS AvailabilityGroupItem\n" +
"    WHERE @AvailabilityGroups IS NOT NULL\n" +
"    UNION ALL\n" +
"    SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"           ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @AvailabilityGroups, EndPosition + 1), 0), LEN(@AvailabilityGroups) + 1) AS EndPosition,\n" +
"           SUBSTRING(@AvailabilityGroups, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @AvailabilityGroups, EndPosition + 1), 0), LEN(@AvailabilityGroups) + 1) - EndPosition - 1) AS AvailabilityGroupItem\n" +
"    FROM AvailabilityGroups1\n" +
"    WHERE EndPosition < LEN(@AvailabilityGroups) + 1\n" +
"    ),\n" +
"    AvailabilityGroups2 (AvailabilityGroupItem, StartPosition, Selected) AS\n" +
"    (\n" +
"    SELECT CASE WHEN AvailabilityGroupItem LIKE '-%' THEN RIGHT(AvailabilityGroupItem,LEN(AvailabilityGroupItem) - 1) ELSE AvailabilityGroupItem END AS AvailabilityGroupItem,\n" +
"           StartPosition,\n" +
"           CASE WHEN AvailabilityGroupItem LIKE '-%' THEN 0 ELSE 1 END AS Selected\n" +
"    FROM AvailabilityGroups1\n" +
"    ),\n" +
"    AvailabilityGroups3 (AvailabilityGroupItem, StartPosition, Selected) AS\n" +
"    (\n" +
"    SELECT CASE WHEN AvailabilityGroupItem = 'ALL_AVAILABILITY_GROUPS' THEN '%' ELSE AvailabilityGroupItem END AS AvailabilityGroupItem,\n" +
"           StartPosition,\n" +
"           Selected\n" +
"    FROM AvailabilityGroups2\n" +
"    ),\n" +
"    AvailabilityGroups4 (AvailabilityGroupName, StartPosition, Selected) AS\n" +
"    (\n" +
"    SELECT CASE WHEN LEFT(AvailabilityGroupItem,1) = '[' AND RIGHT(AvailabilityGroupItem,1) = ']' THEN PARSENAME(AvailabilityGroupItem,1) ELSE AvailabilityGroupItem END AS AvailabilityGroupItem,\n" +
"           StartPosition,\n" +
"           Selected\n" +
"    FROM AvailabilityGroups3\n" +
"    )\n" +
"    INSERT INTO @SelectedAvailabilityGroups (AvailabilityGroupName, StartPosition, Selected)\n" +
"    SELECT AvailabilityGroupName, StartPosition, Selected\n" +
"    FROM AvailabilityGroups4\n" +
"    OPTION (MAXRECURSION 0)\n" +
"\n" +
"    UPDATE tmpAvailabilityGroups\n" +
"    SET tmpAvailabilityGroups.Selected = SelectedAvailabilityGroups.Selected\n" +
"    FROM @tmpAvailabilityGroups tmpAvailabilityGroups\n" +
"    INNER JOIN @SelectedAvailabilityGroups SelectedAvailabilityGroups\n" +
"    ON tmpAvailabilityGroups.AvailabilityGroupName LIKE REPLACE(SelectedAvailabilityGroups.AvailabilityGroupName,'_','[_]')\n" +
"    WHERE SelectedAvailabilityGroups.Selected = 1\n" +
"\n" +
"    UPDATE tmpAvailabilityGroups\n" +
"    SET tmpAvailabilityGroups.Selected = SelectedAvailabilityGroups.Selected\n" +
"    FROM @tmpAvailabilityGroups tmpAvailabilityGroups\n" +
"    INNER JOIN @SelectedAvailabilityGroups SelectedAvailabilityGroups\n" +
"    ON tmpAvailabilityGroups.AvailabilityGroupName LIKE REPLACE(SelectedAvailabilityGroups.AvailabilityGroupName,'_','[_]')\n" +
"    WHERE SelectedAvailabilityGroups.Selected = 0\n" +
"\n" +
"    UPDATE tmpAvailabilityGroups\n" +
"    SET tmpAvailabilityGroups.StartPosition = SelectedAvailabilityGroups2.StartPosition\n" +
"    FROM @tmpAvailabilityGroups tmpAvailabilityGroups\n" +
"    INNER JOIN (SELECT tmpAvailabilityGroups.AvailabilityGroupName, MIN(SelectedAvailabilityGroups.StartPosition) AS StartPosition\n" +
"                FROM @tmpAvailabilityGroups tmpAvailabilityGroups\n" +
"                INNER JOIN @SelectedAvailabilityGroups SelectedAvailabilityGroups\n" +
"                ON tmpAvailabilityGroups.AvailabilityGroupName LIKE REPLACE(SelectedAvailabilityGroups.AvailabilityGroupName,'_','[_]')\n" +
"                WHERE SelectedAvailabilityGroups.Selected = 1\n" +
"                GROUP BY tmpAvailabilityGroups.AvailabilityGroupName) SelectedAvailabilityGroups2\n" +
"    ON tmpAvailabilityGroups.AvailabilityGroupName = SelectedAvailabilityGroups2.AvailabilityGroupName\n" +
"\n" +
"    UPDATE tmpDatabases\n" +
"    SET tmpDatabases.StartPosition = tmpAvailabilityGroups.StartPosition,\n" +
"        tmpDatabases.Selected = 1\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    INNER JOIN @tmpDatabasesAvailabilityGroups tmpDatabasesAvailabilityGroups ON tmpDatabases.DatabaseName = tmpDatabasesAvailabilityGroups.DatabaseName\n" +
"    INNER JOIN @tmpAvailabilityGroups tmpAvailabilityGroups ON tmpDatabasesAvailabilityGroups.AvailabilityGroupName = tmpAvailabilityGroups.AvailabilityGroupName\n" +
"    WHERE tmpAvailabilityGroups.Selected = 1\n" +
"\n" +
"  END\n" +
"\n" +
"  IF @AvailabilityGroups IS NOT NULL AND (NOT EXISTS(SELECT * FROM @SelectedAvailabilityGroups) OR EXISTS(SELECT * FROM @SelectedAvailabilityGroups WHERE AvailabilityGroupName IS NULL OR AvailabilityGroupName = '') OR @Version < 11 OR SERVERPROPERTY('IsHadrEnabled') = 0)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @AvailabilityGroups is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF (@Databases IS NULL AND @AvailabilityGroups IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'You need to specify one of the parameters @Databases and @AvailabilityGroups.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF (@Databases IS NOT NULL AND @AvailabilityGroups IS NOT NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'You can only specify one of the parameters @Databases and @AvailabilityGroups.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select indexes                                                                             //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @Indexes = REPLACE(@Indexes, CHAR(10), '')\n" +
"  SET @Indexes = REPLACE(@Indexes, CHAR(13), '')\n" +
"\n" +
"  WHILE CHARINDEX(@StringDelimiter + ' ', @Indexes) > 0 SET @Indexes = REPLACE(@Indexes, @StringDelimiter + ' ', @StringDelimiter)\n" +
"  WHILE CHARINDEX(' ' + @StringDelimiter, @Indexes) > 0 SET @Indexes = REPLACE(@Indexes, ' ' + @StringDelimiter, @StringDelimiter)\n" +
"\n" +
"  SET @Indexes = LTRIM(RTRIM(@Indexes));\n" +
"\n" +
"  WITH Indexes1 (StartPosition, EndPosition, IndexItem) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Indexes, 1), 0), LEN(@Indexes) + 1) AS EndPosition,\n" +
"         SUBSTRING(@Indexes, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Indexes, 1), 0), LEN(@Indexes) + 1) - 1) AS IndexItem\n" +
"  WHERE @Indexes IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Indexes, EndPosition + 1), 0), LEN(@Indexes) + 1) AS EndPosition,\n" +
"         SUBSTRING(@Indexes, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @Indexes, EndPosition + 1), 0), LEN(@Indexes) + 1) - EndPosition - 1) AS IndexItem\n" +
"  FROM Indexes1\n" +
"  WHERE EndPosition < LEN(@Indexes) + 1\n" +
"  ),\n" +
"  Indexes2 (IndexItem, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN IndexItem LIKE '-%' THEN RIGHT(IndexItem,LEN(IndexItem) - 1) ELSE IndexItem END AS IndexItem,\n" +
"         StartPosition,\n" +
"         CASE WHEN IndexItem LIKE '-%' THEN 0 ELSE 1 END AS Selected\n" +
"  FROM Indexes1\n" +
"  ),\n" +
"  Indexes3 (IndexItem, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN IndexItem = 'ALL_INDEXES' THEN '%.%.%.%' ELSE IndexItem END AS IndexItem,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Indexes2\n" +
"  ),\n" +
"  Indexes4 (DatabaseName, SchemaName, ObjectName, IndexName, StartPosition, Selected) AS\n" +
"  (\n" +
"  SELECT CASE WHEN PARSENAME(IndexItem,4) IS NULL THEN PARSENAME(IndexItem,3) ELSE PARSENAME(IndexItem,4) END AS DatabaseName,\n" +
"         CASE WHEN PARSENAME(IndexItem,4) IS NULL THEN PARSENAME(IndexItem,2) ELSE PARSENAME(IndexItem,3) END AS SchemaName,\n" +
"         CASE WHEN PARSENAME(IndexItem,4) IS NULL THEN PARSENAME(IndexItem,1) ELSE PARSENAME(IndexItem,2) END AS ObjectName,\n" +
"         CASE WHEN PARSENAME(IndexItem,4) IS NULL THEN '%' ELSE PARSENAME(IndexItem,1) END AS IndexName,\n" +
"         StartPosition,\n" +
"         Selected\n" +
"  FROM Indexes3\n" +
"  )\n" +
"  INSERT INTO @SelectedIndexes (DatabaseName, SchemaName, ObjectName, IndexName, StartPosition, Selected)\n" +
"  SELECT DatabaseName, SchemaName, ObjectName, IndexName, StartPosition, Selected\n" +
"  FROM Indexes4\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Select actions                                                                             //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @FragmentationLow = REPLACE(@FragmentationLow, @StringDelimiter + ' ', @StringDelimiter);\n" +
"\n" +
"  WITH FragmentationLow (StartPosition, EndPosition, [Action]) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FragmentationLow, 1), 0), LEN(@FragmentationLow) + 1) AS EndPosition,\n" +
"         SUBSTRING(@FragmentationLow, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FragmentationLow, 1), 0), LEN(@FragmentationLow) + 1) - 1) AS [Action]\n" +
"  WHERE @FragmentationLow IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FragmentationLow, EndPosition + 1), 0), LEN(@FragmentationLow) + 1) AS EndPosition,\n" +
"         SUBSTRING(@FragmentationLow, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FragmentationLow, EndPosition + 1), 0), LEN(@FragmentationLow) + 1) - EndPosition - 1) AS [Action]\n" +
"  FROM FragmentationLow\n" +
"  WHERE EndPosition < LEN(@FragmentationLow) + 1\n" +
"  )\n" +
"  INSERT INTO @ActionsPreferred(FragmentationGroup, [Priority], [Action])\n" +
"  SELECT 'Low' AS FragmentationGroup,\n" +
"         ROW_NUMBER() OVER(ORDER BY StartPosition ASC) AS [Priority],\n" +
"         [Action]\n" +
"  FROM FragmentationLow\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  SET @FragmentationMedium = REPLACE(@FragmentationMedium, @StringDelimiter + ' ', @StringDelimiter);\n" +
"\n" +
"  WITH FragmentationMedium (StartPosition, EndPosition, [Action]) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FragmentationMedium, 1), 0), LEN(@FragmentationMedium) + 1) AS EndPosition,\n" +
"         SUBSTRING(@FragmentationMedium, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FragmentationMedium, 1), 0), LEN(@FragmentationMedium) + 1) - 1) AS [Action]\n" +
"  WHERE @FragmentationMedium IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FragmentationMedium, EndPosition + 1), 0), LEN(@FragmentationMedium) + 1) AS EndPosition,\n" +
"         SUBSTRING(@FragmentationMedium, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FragmentationMedium, EndPosition + 1), 0), LEN(@FragmentationMedium) + 1) - EndPosition - 1) AS [Action]\n" +
"  FROM FragmentationMedium\n" +
"  WHERE EndPosition < LEN(@FragmentationMedium) + 1\n" +
"  )\n" +
"  INSERT INTO @ActionsPreferred(FragmentationGroup, [Priority], [Action])\n" +
"  SELECT 'Medium' AS FragmentationGroup,\n" +
"         ROW_NUMBER() OVER(ORDER BY StartPosition ASC) AS [Priority],\n" +
"         [Action]\n" +
"  FROM FragmentationMedium\n" +
"  OPTION (MAXRECURSION 0)\n" +
"\n" +
"  SET @FragmentationHigh = REPLACE(@FragmentationHigh, @StringDelimiter + ' ', @StringDelimiter);\n" +
"\n" +
"  WITH FragmentationHigh (StartPosition, EndPosition, [Action]) AS\n" +
"  (\n" +
"  SELECT 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FragmentationHigh, 1), 0), LEN(@FragmentationHigh) + 1) AS EndPosition,\n" +
"         SUBSTRING(@FragmentationHigh, 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FragmentationHigh, 1), 0), LEN(@FragmentationHigh) + 1) - 1) AS [Action]\n" +
"  WHERE @FragmentationHigh IS NOT NULL\n" +
"  UNION ALL\n" +
"  SELECT CAST(EndPosition AS int) + 1 AS StartPosition,\n" +
"         ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FragmentationHigh, EndPosition + 1), 0), LEN(@FragmentationHigh) + 1) AS EndPosition,\n" +
"         SUBSTRING(@FragmentationHigh, EndPosition + 1, ISNULL(NULLIF(CHARINDEX(@StringDelimiter, @FragmentationHigh, EndPosition + 1), 0), LEN(@FragmentationHigh) + 1) - EndPosition - 1) AS [Action]\n" +
"  FROM FragmentationHigh\n" +
"  WHERE EndPosition < LEN(@FragmentationHigh) + 1\n" +
"  )\n" +
"  INSERT INTO @ActionsPreferred(FragmentationGroup, [Priority], [Action])\n" +
"  SELECT 'High' AS FragmentationGroup,\n" +
"         ROW_NUMBER() OVER(ORDER BY StartPosition ASC) AS [Priority],\n" +
"         [Action]\n" +
"  FROM FragmentationHigh\n" +
"  OPTION (MAXRECURSION 0)\n";
    }
    
    public String part11(){
        return "  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check input parameters                                                                     //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS (SELECT [Action] FROM @ActionsPreferred WHERE FragmentationGroup = 'Low' AND [Action] NOT IN(SELECT * FROM @Actions))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FragmentationLow is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @ActionsPreferred WHERE FragmentationGroup = 'Low' GROUP BY [Action] HAVING COUNT(*) > 1)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FragmentationLow is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS (SELECT [Action] FROM @ActionsPreferred WHERE FragmentationGroup = 'Medium' AND [Action] NOT IN(SELECT * FROM @Actions))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FragmentationMedium is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @ActionsPreferred WHERE FragmentationGroup = 'Medium' GROUP BY [Action] HAVING COUNT(*) > 1)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FragmentationMedium is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS (SELECT [Action] FROM @ActionsPreferred WHERE FragmentationGroup = 'High' AND [Action] NOT IN(SELECT * FROM @Actions))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FragmentationHigh is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @ActionsPreferred WHERE FragmentationGroup = 'High' GROUP BY [Action] HAVING COUNT(*) > 1)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FragmentationHigh is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @FragmentationLevel1 <= 0 OR @FragmentationLevel1 >= 100 OR @FragmentationLevel1 IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FragmentationLevel1 is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @FragmentationLevel1 >= @FragmentationLevel2\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FragmentationLevel1 is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @FragmentationLevel2 <= 0 OR @FragmentationLevel2 >= 100 OR @FragmentationLevel2 IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FragmentationLevel2 is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @FragmentationLevel2 <= @FragmentationLevel1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FragmentationLevel2 is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @MinNumberOfPages < 0 OR @MinNumberOfPages IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MinNumberOfPages is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @MaxNumberOfPages < 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MaxNumberOfPages is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @SortInTempdb NOT IN('Y','N') OR @SortInTempdb IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @SortInTempdb is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @MaxDOP < 0 OR @MaxDOP > 64\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MaxDOP is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @FillFactor <= 0 OR @FillFactor > 100\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @FillFactor is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @PadIndex NOT IN('Y','N')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @PadIndex is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @LOBCompaction NOT IN('Y','N') OR @LOBCompaction IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @LOBCompaction is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @UpdateStatistics NOT IN('ALL','COLUMNS','INDEX')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @UpdateStatistics is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @OnlyModifiedStatistics NOT IN('Y','N') OR @OnlyModifiedStatistics IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @OnlyModifiedStatistics is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @StatisticsModificationLevel <= 0 OR @StatisticsModificationLevel > 100\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @StatisticsModificationLevel is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @OnlyModifiedStatistics = 'Y' AND @StatisticsModificationLevel IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'You can only specify one of the parameters @OnlyModifiedStatistics and @StatisticsModificationLevel.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @StatisticsSample <= 0 OR @StatisticsSample  > 100\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @StatisticsSample is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @StatisticsResample NOT IN('Y','N') OR @StatisticsResample IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @StatisticsResample is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @StatisticsResample = 'Y' AND @StatisticsSample IS NOT NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @StatisticsResample is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @PartitionLevel NOT IN('Y','N') OR @PartitionLevel IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @PartitionLevel is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @MSShippedObjects NOT IN('Y','N') OR @MSShippedObjects IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @MSShippedObjects is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS(SELECT * FROM @SelectedIndexes WHERE DatabaseName IS NULL OR SchemaName IS NULL OR ObjectName IS NULL OR IndexName IS NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Indexes is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @Indexes IS NOT NULL AND NOT EXISTS(SELECT * FROM @SelectedIndexes)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Indexes is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @TimeLimit < 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @TimeLimit is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Delay < 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Delay is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @WaitAtLowPriorityMaxDuration < 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @WaitAtLowPriorityMaxDuration is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @WaitAtLowPriorityMaxDuration IS NOT NULL AND @Version < 12\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @WaitAtLowPriorityMaxDuration is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @WaitAtLowPriorityAbortAfterWait NOT IN('NONE','SELF','BLOCKERS')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @WaitAtLowPriorityAbortAfterWait is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @WaitAtLowPriorityAbortAfterWait IS NOT NULL AND @Version < 12\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @WaitAtLowPriorityAbortAfterWait is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF (@WaitAtLowPriorityAbortAfterWait IS NOT NULL AND @WaitAtLowPriorityMaxDuration IS NULL) OR (@WaitAtLowPriorityAbortAfterWait IS NULL AND @WaitAtLowPriorityMaxDuration IS NOT NULL)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The parameters @WaitAtLowPriorityMaxDuration and @WaitAtLowPriorityAbortAfterWait can only be used together.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Resumable NOT IN('Y','N') OR @Resumable IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Resumable is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @Resumable = 'Y' AND NOT (@Version >= 14 OR SERVERPROPERTY('EngineEdition') IN (5, 8))\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Resumable is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  IF @Resumable = 'Y' AND @SortInTempdb = 'Y'\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'You can only specify one of the parameters @Resumable and @SortInTempdb.', 16, 3\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @LockTimeout < 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @LockTimeout is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @LockMessageSeverity NOT IN(10, 16)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @LockMessageSeverity is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @StringDelimiter IS NULL OR LEN(@StringDelimiter) > 1\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @StringDelimiter is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabaseOrder NOT IN('DATABASE_NAME_ASC','DATABASE_NAME_DESC','DATABASE_SIZE_ASC','DATABASE_SIZE_DESC')\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabaseOrder is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IS NOT NULL AND SERVERPROPERTY('EngineEdition') = 5\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabaseOrder is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabasesInParallel NOT IN('Y','N') OR @DatabasesInParallel IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabasesInParallel is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  IF @DatabasesInParallel = 'Y' AND SERVERPROPERTY('EngineEdition') = 5\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @DatabasesInParallel is not supported.', 16, 2\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF LEN(@ExecuteAsUser) > 128\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @ExecuteAsUser is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @LogToTable NOT IN('Y','N') OR @LogToTable IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @LogToTable is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @Execute NOT IN('Y','N') OR @Execute IS NULL\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The value for the parameter @Execute is not supported.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF EXISTS(SELECT * FROM @Errors)\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The documentation is available at https://ola.hallengren.com/sql-server-index-and-statistics-maintenance.html.', 16, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Check that selected databases and availability groups exist                                //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + ', '\n" +
"  FROM @SelectedDatabases\n" +
"  WHERE DatabaseName NOT LIKE '%[%]%'\n" +
"  AND DatabaseName NOT IN (SELECT DatabaseName FROM @tmpDatabases)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The following databases in the @Databases parameter do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 10, 1\n" +
"  END\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + ', '\n" +
"  FROM @SelectedIndexes\n" +
"  WHERE DatabaseName NOT LIKE '%[%]%'\n" +
"  AND DatabaseName NOT IN (SELECT DatabaseName FROM @tmpDatabases)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The following databases in the @Indexes parameter do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 10, 1\n" +
"  END\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(AvailabilityGroupName) + ', '\n" +
"  FROM @SelectedAvailabilityGroups\n" +
"  WHERE AvailabilityGroupName NOT LIKE '%[%]%'\n" +
"  AND AvailabilityGroupName NOT IN (SELECT AvailabilityGroupName FROM @tmpAvailabilityGroups)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The following availability groups do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 10, 1\n" +
"  END\n" +
"\n" +
"  SET @ErrorMessage = ''\n" +
"  SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + ', '\n" +
"  FROM @SelectedIndexes\n" +
"  WHERE DatabaseName NOT LIKE '%[%]%'\n" +
"  AND DatabaseName IN (SELECT DatabaseName FROM @tmpDatabases)\n" +
"  AND DatabaseName NOT IN (SELECT DatabaseName FROM @tmpDatabases WHERE Selected = 1)\n" +
"  IF @@ROWCOUNT > 0\n" +
"  BEGIN\n" +
"    INSERT INTO @Errors ([Message], Severity, [State])\n" +
"    SELECT 'The following databases have been selected in the @Indexes parameter, but not in the @Databases or @AvailabilityGroups parameters: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.', 10, 1\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Raise errors                                                                               //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  DECLARE ErrorCursor CURSOR FAST_FORWARD FOR SELECT [Message], Severity, [State] FROM @Errors ORDER BY [ID] ASC\n" +
"\n" +
"  OPEN ErrorCursor\n" +
"\n" +
"  FETCH ErrorCursor INTO @CurrentMessage, @CurrentSeverity, @CurrentState\n" +
"\n" +
"  WHILE @@FETCH_STATUS = 0\n" +
"  BEGIN\n" +
"    RAISERROR('%s', @CurrentSeverity, @CurrentState, @CurrentMessage) WITH NOWAIT\n" +
"    RAISERROR(@EmptyLine, 10, 1) WITH NOWAIT\n" +
"\n" +
"    FETCH NEXT FROM ErrorCursor INTO @CurrentMessage, @CurrentSeverity, @CurrentState\n" +
"  END\n" +
"\n" +
"  CLOSE ErrorCursor\n" +
"\n" +
"  DEALLOCATE ErrorCursor\n" +
"\n" +
"  IF EXISTS (SELECT * FROM @Errors WHERE Severity >= 16)\n" +
"  BEGIN\n" +
"    SET @ReturnCode = 50000\n" +
"    GOTO Logging\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Should statistics be updated on the partition level?                                       //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  SET @PartitionLevelStatistics = CASE WHEN @PartitionLevel = 'Y' AND ((@Version >= 12.05 AND @Version < 13) OR @Version >= 13.04422 OR SERVERPROPERTY('EngineEdition') IN (5,8)) THEN 1 ELSE 0 END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Update database order                                                                      //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabaseOrder IN('DATABASE_SIZE_ASC','DATABASE_SIZE_DESC')\n" +
"  BEGIN\n" +
"    UPDATE tmpDatabases\n" +
"    SET DatabaseSize = (SELECT SUM(CAST(size AS bigint)) FROM sys.master_files WHERE [type] = 0 AND database_id = DB_ID(tmpDatabases.DatabaseName))\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"  END\n" +
"\n" +
"  IF @DatabaseOrder IS NULL\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY StartPosition ASC, DatabaseName ASC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_NAME_ASC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY DatabaseName ASC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_NAME_DESC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY DatabaseName DESC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_SIZE_ASC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY DatabaseSize ASC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n" +
"  ELSE\n" +
"  IF @DatabaseOrder = 'DATABASE_SIZE_DESC'\n" +
"  BEGIN\n" +
"    WITH tmpDatabases AS (\n" +
"    SELECT DatabaseName, [Order], ROW_NUMBER() OVER (ORDER BY DatabaseSize DESC) AS RowNumber\n" +
"    FROM @tmpDatabases tmpDatabases\n" +
"    WHERE Selected = 1\n" +
"    )\n" +
"    UPDATE tmpDatabases\n" +
"    SET [Order] = RowNumber\n" +
"  END\n";
    }
    
    public String part12(){
        return "  ----------------------------------------------------------------------------------------------------\n" +
"  --// Update the queue                                                                           //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  IF @DatabasesInParallel = 'Y'\n" +
"  BEGIN\n" +
"\n" +
"    BEGIN TRY\n" +
"\n" +
"      SELECT @QueueID = QueueID\n" +
"      FROM dbo.[Queue]\n" +
"      WHERE SchemaName = @SchemaName\n" +
"      AND ObjectName = @ObjectName\n" +
"      AND [Parameters] = @Parameters\n" +
"\n" +
"      IF @QueueID IS NULL\n" +
"      BEGIN\n" +
"        BEGIN TRANSACTION\n" +
"\n" +
"        SELECT @QueueID = QueueID\n" +
"        FROM dbo.[Queue] WITH (UPDLOCK, HOLDLOCK)\n" +
"        WHERE SchemaName = @SchemaName\n" +
"        AND ObjectName = @ObjectName\n" +
"        AND [Parameters] = @Parameters\n" +
"\n" +
"        IF @QueueID IS NULL\n" +
"        BEGIN\n" +
"          INSERT INTO dbo.[Queue] (SchemaName, ObjectName, [Parameters])\n" +
"          SELECT @SchemaName, @ObjectName, @Parameters\n" +
"\n" +
"          SET @QueueID = SCOPE_IDENTITY()\n" +
"        END\n" +
"\n" +
"        COMMIT TRANSACTION\n" +
"      END\n" +
"\n" +
"      BEGIN TRANSACTION\n" +
"\n" +
"      UPDATE [Queue]\n" +
"      SET QueueStartTime = SYSDATETIME(),\n" +
"          SessionID = @@SPID,\n" +
"          RequestID = (SELECT request_id FROM sys.dm_exec_requests WHERE session_id = @@SPID),\n" +
"          RequestStartTime = (SELECT start_time FROM sys.dm_exec_requests WHERE session_id = @@SPID)\n" +
"      FROM dbo.[Queue] [Queue]\n" +
"      WHERE QueueID = @QueueID\n" +
"      AND NOT EXISTS (SELECT *\n" +
"                      FROM sys.dm_exec_requests\n" +
"                      WHERE session_id = [Queue].SessionID\n" +
"                      AND request_id = [Queue].RequestID\n" +
"                      AND start_time = [Queue].RequestStartTime)\n" +
"      AND NOT EXISTS (SELECT *\n" +
"                      FROM dbo.QueueDatabase QueueDatabase\n" +
"                      INNER JOIN sys.dm_exec_requests ON QueueDatabase.SessionID = session_id AND QueueDatabase.RequestID = request_id AND QueueDatabase.RequestStartTime = start_time\n" +
"                      WHERE QueueDatabase.QueueID = @QueueID)\n" +
"\n" +
"      IF @@ROWCOUNT = 1\n" +
"      BEGIN\n" +
"        INSERT INTO dbo.QueueDatabase (QueueID, DatabaseName)\n" +
"        SELECT @QueueID AS QueueID,\n" +
"               DatabaseName\n" +
"        FROM @tmpDatabases tmpDatabases\n" +
"        WHERE Selected = 1\n" +
"        AND NOT EXISTS (SELECT * FROM dbo.QueueDatabase WHERE DatabaseName = tmpDatabases.DatabaseName AND QueueID = @QueueID)\n" +
"\n" +
"        DELETE QueueDatabase\n" +
"        FROM dbo.QueueDatabase QueueDatabase\n" +
"        WHERE QueueID = @QueueID\n" +
"        AND NOT EXISTS (SELECT * FROM @tmpDatabases tmpDatabases WHERE DatabaseName = QueueDatabase.DatabaseName AND Selected = 1)\n" +
"\n" +
"        UPDATE QueueDatabase\n" +
"        SET DatabaseOrder = tmpDatabases.[Order]\n" +
"        FROM dbo.QueueDatabase QueueDatabase\n" +
"        INNER JOIN @tmpDatabases tmpDatabases ON QueueDatabase.DatabaseName = tmpDatabases.DatabaseName\n" +
"        WHERE QueueID = @QueueID\n" +
"      END\n" +
"\n" +
"      COMMIT TRANSACTION\n" +
"\n" +
"      SELECT @QueueStartTime = QueueStartTime\n" +
"      FROM dbo.[Queue]\n" +
"      WHERE QueueID = @QueueID\n" +
"\n" +
"    END TRY\n" +
"\n" +
"    BEGIN CATCH\n" +
"      IF XACT_STATE() <> 0\n" +
"      BEGIN\n" +
"        ROLLBACK TRANSACTION\n" +
"      END\n" +
"      SET @ErrorMessage = 'Msg ' + CAST(ERROR_NUMBER() AS nvarchar) + ', ' + ISNULL(ERROR_MESSAGE(),'')\n" +
"      RAISERROR('%s',16,1,@ErrorMessage) WITH NOWAIT\n" +
"      RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"      SET @ReturnCode = ERROR_NUMBER()\n" +
"      GOTO Logging\n" +
"    END CATCH\n" +
"\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Execute commands                                                                           //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  WHILE (1 = 1)\n" +
"  BEGIN\n" +
"\n" +
"    IF @DatabasesInParallel = 'Y'\n" +
"    BEGIN\n" +
"      UPDATE QueueDatabase\n" +
"      SET DatabaseStartTime = NULL,\n" +
"          SessionID = NULL,\n" +
"          RequestID = NULL,\n" +
"          RequestStartTime = NULL\n" +
"      FROM dbo.QueueDatabase QueueDatabase\n" +
"      WHERE QueueID = @QueueID\n" +
"      AND DatabaseStartTime IS NOT NULL\n" +
"      AND DatabaseEndTime IS NULL\n" +
"      AND NOT EXISTS (SELECT * FROM sys.dm_exec_requests WHERE session_id = QueueDatabase.SessionID AND request_id = QueueDatabase.RequestID AND start_time = QueueDatabase.RequestStartTime)\n" +
"\n" +
"      UPDATE QueueDatabase\n" +
"      SET DatabaseStartTime = SYSDATETIME(),\n" +
"          DatabaseEndTime = NULL,\n" +
"          SessionID = @@SPID,\n" +
"          RequestID = (SELECT request_id FROM sys.dm_exec_requests WHERE session_id = @@SPID),\n" +
"          RequestStartTime = (SELECT start_time FROM sys.dm_exec_requests WHERE session_id = @@SPID),\n" +
"          @CurrentDatabaseName = DatabaseName\n" +
"      FROM (SELECT TOP 1 DatabaseStartTime,\n" +
"                         DatabaseEndTime,\n" +
"                         SessionID,\n" +
"                         RequestID,\n" +
"                         RequestStartTime,\n" +
"                         DatabaseName\n" +
"            FROM dbo.QueueDatabase\n" +
"            WHERE QueueID = @QueueID\n" +
"            AND (DatabaseStartTime < @QueueStartTime OR DatabaseStartTime IS NULL)\n" +
"            AND NOT (DatabaseStartTime IS NOT NULL AND DatabaseEndTime IS NULL)\n" +
"            ORDER BY DatabaseOrder ASC\n" +
"            ) QueueDatabase\n" +
"    END\n" +
"    ELSE\n" +
"    BEGIN\n" +
"      SELECT TOP 1 @CurrentDBID = ID,\n" +
"                   @CurrentDatabaseName = DatabaseName\n" +
"      FROM @tmpDatabases\n" +
"      WHERE Selected = 1\n" +
"      AND Completed = 0\n" +
"      ORDER BY [Order] ASC\n" +
"    END\n" +
"\n" +
"    IF @@ROWCOUNT = 0\n" +
"    BEGIN\n" +
"      BREAK\n" +
"    END\n" +
"\n" +
"    SET @CurrentDatabase_sp_executesql = QUOTENAME(@CurrentDatabaseName) + '.sys.sp_executesql'\n" +
"\n" +
"    IF @ExecuteAsUser IS NOT NULL\n" +
"    BEGIN\n" +
"      SET @CurrentCommand = ''\n" +
"      SET @CurrentCommand += 'IF EXISTS(SELECT * FROM sys.database_principals database_principals WHERE database_principals.[name] = @ParamExecuteAsUser) BEGIN SET @ParamExecuteAsUserExists = 1 END ELSE BEGIN SET @ParamExecuteAsUserExists = 0 END'\n" +
"\n" +
"      EXECUTE @CurrentDatabase_sp_executesql @stmt = @CurrentCommand, @params = N'@ParamExecuteAsUser sysname, @ParamExecuteAsUserExists bit OUTPUT', @ParamExecuteAsUser = @ExecuteAsUser, @ParamExecuteAsUserExists = @CurrentExecuteAsUserExists OUTPUT\n" +
"    END\n" +
"\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Date and time: ' + CONVERT(nvarchar,SYSDATETIME(),120)\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Database: ' + QUOTENAME(@CurrentDatabaseName)\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    SELECT @CurrentUserAccess = user_access_desc,\n" +
"           @CurrentIsReadOnly = is_read_only,\n" +
"           @CurrentDatabaseState = state_desc,\n" +
"           @CurrentInStandby = is_in_standby,\n" +
"           @CurrentRecoveryModel = recovery_model_desc\n" +
"    FROM sys.databases\n" +
"    WHERE [name] = @CurrentDatabaseName\n" +
"\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'State: ' + @CurrentDatabaseState\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Standby: ' + CASE WHEN @CurrentInStandby = 1 THEN 'Yes' ELSE 'No' END\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Updateability: ' + CASE WHEN @CurrentIsReadOnly = 1 THEN 'READ_ONLY' WHEN  @CurrentIsReadOnly = 0 THEN 'READ_WRITE' END\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'User access: ' + @CurrentUserAccess\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Recovery model: ' + @CurrentRecoveryModel\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    IF @CurrentDatabaseState = 'ONLINE' AND SERVERPROPERTY('EngineEdition') <> 5\n" +
"    BEGIN\n" +
"      IF EXISTS (SELECT * FROM sys.database_recovery_status WHERE database_id = DB_ID(@CurrentDatabaseName) AND database_guid IS NOT NULL)\n" +
"      BEGIN\n" +
"        SET @CurrentIsDatabaseAccessible = 1\n" +
"      END\n" +
"      ELSE\n" +
"      BEGIN\n" +
"        SET @CurrentIsDatabaseAccessible = 0\n" +
"      END\n" +
"    END\n" +
"\n" +
"    IF @Version >= 11 AND SERVERPROPERTY('IsHadrEnabled') = 1\n" +
"    BEGIN\n" +
"      SELECT @CurrentReplicaID = databases.replica_id\n" +
"      FROM sys.databases databases\n" +
"      INNER JOIN sys.availability_replicas availability_replicas ON databases.replica_id = availability_replicas.replica_id\n" +
"      WHERE databases.[name] = @CurrentDatabaseName\n" +
"\n" +
"      SELECT @CurrentAvailabilityGroupID = group_id\n" +
"      FROM sys.availability_replicas\n" +
"      WHERE replica_id = @CurrentReplicaID\n" +
"\n" +
"      SELECT @CurrentAvailabilityGroupRole = role_desc\n" +
"      FROM sys.dm_hadr_availability_replica_states\n" +
"      WHERE replica_id = @CurrentReplicaID\n" +
"\n" +
"      SELECT @CurrentAvailabilityGroup = [name]\n" +
"      FROM sys.availability_groups\n" +
"      WHERE group_id = @CurrentAvailabilityGroupID\n" +
"    END\n" +
"\n" +
"    IF SERVERPROPERTY('EngineEdition') <> 5\n" +
"    BEGIN\n" +
"      SELECT @CurrentDatabaseMirroringRole = UPPER(mirroring_role_desc)\n" +
"      FROM sys.database_mirroring\n" +
"      WHERE database_id = DB_ID(@CurrentDatabaseName)\n" +
"    END\n" +
"\n" +
"    IF @CurrentIsDatabaseAccessible IS NOT NULL\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Is accessible: ' + CASE WHEN @CurrentIsDatabaseAccessible = 1 THEN 'Yes' ELSE 'No' END\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    IF @CurrentAvailabilityGroup IS NOT NULL\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Availability group: ' + ISNULL(@CurrentAvailabilityGroup,'N/A')\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"\n" +
"      SET @DatabaseMessage = 'Availability group role: ' + ISNULL(@CurrentAvailabilityGroupRole,'N/A')\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    IF @CurrentDatabaseMirroringRole IS NOT NULL\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'Database mirroring role: ' + @CurrentDatabaseMirroringRole\n" +
"      RAISERROR('%s',10,1,@DatabaseMessage) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"    IF @CurrentExecuteAsUserExists = 0\n" +
"    BEGIN\n" +
"      SET @DatabaseMessage = 'The user ' + QUOTENAME(@ExecuteAsUser) + ' does not exist in the database ' + QUOTENAME(@CurrentDatabaseName) + '.'\n" +
"      RAISERROR('%s',16,1,@DatabaseMessage) WITH NOWAIT\n" +
"      RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"    END\n" +
"\n" +
"    IF @CurrentDatabaseState = 'ONLINE'\n" +
"    AND NOT (@CurrentUserAccess = 'SINGLE_USER' AND @CurrentIsDatabaseAccessible = 0)\n" +
"    AND DATABASEPROPERTYEX(@CurrentDatabaseName,'Updateability') = 'READ_WRITE'\n" +
"    AND (@CurrentExecuteAsUserExists = 1 OR @CurrentExecuteAsUserExists IS NULL)\n" +
"    BEGIN\n" +
"\n" +
"      -- Select indexes in the current database\n" +
"      IF (EXISTS(SELECT * FROM @ActionsPreferred) OR @UpdateStatistics IS NOT NULL) AND (SYSDATETIME() < DATEADD(SECOND,@TimeLimit,@StartTime) OR @TimeLimit IS NULL)\n" +
"      BEGIN\n" +
"        SET @CurrentCommand = 'SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;'\n" +
"                              + ' SELECT SchemaID, SchemaName, ObjectID, ObjectName, ObjectType, IsMemoryOptimized, IndexID, IndexName, IndexType, AllowPageLocks, IsImageText, IsNewLOB, IsFileStream, IsColumnStore, IsComputed, IsTimestamp, OnReadOnlyFileGroup, ResumableIndexOperation, StatisticsID, StatisticsName, NoRecompute, IsIncremental, PartitionID, PartitionNumber, PartitionCount, [Order], Selected, Completed'\n" +
"                              + ' FROM ('\n" +
"\n" +
"        IF EXISTS(SELECT * FROM @ActionsPreferred) OR @UpdateStatistics IN('ALL','INDEX')\n" +
"        BEGIN\n" +
"          SET @CurrentCommand = @CurrentCommand + 'SELECT schemas.[schema_id] AS SchemaID'\n" +
"                                                    + ', schemas.[name] AS SchemaName'\n" +
"                                                    + ', objects.[object_id] AS ObjectID'\n" +
"                                                    + ', objects.[name] AS ObjectName'\n" +
"                                                    + ', RTRIM(objects.[type]) AS ObjectType'\n" +
"                                                    + ', ' + CASE WHEN @Version >= 12 THEN 'tables.is_memory_optimized' ELSE '0' END + ' AS IsMemoryOptimized'\n" +
"                                                    + ', indexes.index_id AS IndexID'\n" +
"                                                    + ', indexes.[name] AS IndexName'\n" +
"                                                    + ', indexes.[type] AS IndexType'\n" +
"                                                    + ', indexes.allow_page_locks AS AllowPageLocks'\n" +
"\n" +
"                                                    + ', CASE WHEN indexes.[type] = 1 AND EXISTS(SELECT * FROM sys.columns columns INNER JOIN sys.types types ON columns.system_type_id = types.user_type_id WHERE columns.[object_id] = objects.object_id AND types.name IN(''image'',''text'',''ntext'')) THEN 1 ELSE 0 END AS IsImageText'\n" +
"\n" +
"                                                    + ', CASE WHEN indexes.[type] = 1 AND EXISTS(SELECT * FROM sys.columns columns INNER JOIN sys.types types ON columns.system_type_id = types.user_type_id OR (columns.user_type_id = types.user_type_id AND types.is_assembly_type = 1) WHERE columns.[object_id] = objects.object_id AND (types.name IN(''xml'') OR (types.name IN(''varchar'',''nvarchar'',''varbinary'') AND columns.max_length = -1) OR (types.is_assembly_type = 1 AND columns.max_length = -1))) THEN 1'\n" +
"                                                    + ' WHEN indexes.[type] = 2 AND EXISTS(SELECT * FROM sys.index_columns index_columns INNER JOIN sys.columns columns ON index_columns.[object_id] = columns.[object_id] AND index_columns.column_id = columns.column_id INNER JOIN sys.types types ON columns.system_type_id = types.user_type_id OR (columns.user_type_id = types.user_type_id AND types.is_assembly_type = 1) WHERE index_columns.[object_id] = objects.object_id AND index_columns.index_id = indexes.index_id AND (types.[name] IN(''xml'') OR (types.[name] IN(''varchar'',''nvarchar'',''varbinary'') AND columns.max_length = -1) OR (types.is_assembly_type = 1 AND columns.max_length = -1))) THEN 1 ELSE 0 END AS IsNewLOB'\n" +
"\n" +
"                                                    + ', CASE WHEN indexes.[type] = 1 AND EXISTS(SELECT * FROM sys.columns columns WHERE columns.[object_id] = objects.object_id  AND columns.is_filestream = 1) THEN 1 ELSE 0 END AS IsFileStream'\n" +
"\n" +
"                                                    + ', CASE WHEN EXISTS(SELECT * FROM sys.indexes indexes WHERE indexes.[object_id] = objects.object_id AND [type] IN(5,6)) THEN 1 ELSE 0 END AS IsColumnStore'\n" +
"\n" +
"                                                    + ', CASE WHEN EXISTS(SELECT * FROM sys.index_columns index_columns INNER JOIN sys.columns columns ON index_columns.object_id = columns.object_id AND index_columns.column_id = columns.column_id WHERE (index_columns.key_ordinal > 0 OR index_columns.partition_ordinal > 0) AND columns.is_computed = 1 AND index_columns.object_id = indexes.object_id AND index_columns.index_id = indexes.index_id) THEN 1 ELSE 0 END AS IsComputed'\n" +
"\n" +
"                                                    + ', CASE WHEN EXISTS(SELECT * FROM sys.index_columns index_columns INNER JOIN sys.columns columns ON index_columns.[object_id] = columns.[object_id] AND index_columns.column_id = columns.column_id INNER JOIN sys.types types ON columns.system_type_id = types.system_type_id WHERE index_columns.[object_id] = objects.object_id AND index_columns.index_id = indexes.index_id AND types.[name] = ''timestamp'') THEN 1 ELSE 0 END AS IsTimestamp'\n" +
"\n" +
"                                                    + ', CASE WHEN EXISTS (SELECT * FROM sys.indexes indexes2 INNER JOIN sys.destination_data_spaces destination_data_spaces ON indexes.data_space_id = destination_data_spaces.partition_scheme_id INNER JOIN sys.filegroups filegroups ON destination_data_spaces.data_space_id = filegroups.data_space_id WHERE filegroups.is_read_only = 1 AND indexes2.[object_id] = indexes.[object_id] AND indexes2.[index_id] = indexes.index_id' + CASE WHEN @PartitionLevel = 'Y' THEN ' AND destination_data_spaces.destination_id = partitions.partition_number' ELSE '' END + ') THEN 1'\n" +
"                                                    + ' WHEN EXISTS (SELECT * FROM sys.indexes indexes2 INNER JOIN sys.filegroups filegroups ON indexes.data_space_id = filegroups.data_space_id WHERE filegroups.is_read_only = 1 AND indexes.[object_id] = indexes2.[object_id] AND indexes.[index_id] = indexes2.index_id) THEN 1'\n" +
"                                                    + ' WHEN indexes.[type] = 1 AND EXISTS (SELECT * FROM sys.tables tables INNER JOIN sys.filegroups filegroups ON tables.lob_data_space_id = filegroups.data_space_id WHERE filegroups.is_read_only = 1 AND tables.[object_id] = objects.[object_id]) THEN 1 ELSE 0 END AS OnReadOnlyFileGroup'\n" +
"\n" +
"                                                    + ', ' + CASE WHEN @Version >= 14 THEN 'CASE WHEN EXISTS(SELECT * FROM sys.index_resumable_operations index_resumable_operations WHERE state_desc = ''PAUSED'' AND index_resumable_operations.object_id = indexes.object_id AND index_resumable_operations.index_id = indexes.index_id AND (index_resumable_operations.partition_number = partitions.partition_number OR index_resumable_operations.partition_number IS NULL)) THEN 1 ELSE 0 END' ELSE '0' END + ' AS ResumableIndexOperation'\n" +
"\n" +
"                                                    + ', stats.stats_id AS StatisticsID'\n" +
"                                                    + ', stats.name AS StatisticsName'\n" +
"                                                    + ', stats.no_recompute AS NoRecompute'\n" +
"                                                    + ', ' + CASE WHEN @Version >= 12 THEN 'stats.is_incremental' ELSE '0' END + ' AS IsIncremental'\n" +
"                                                    + ', ' + CASE WHEN @PartitionLevel = 'Y' THEN 'partitions.partition_id AS PartitionID' WHEN @PartitionLevel = 'N' THEN 'NULL AS PartitionID' END\n" +
"                                                    + ', ' + CASE WHEN @PartitionLevel = 'Y' THEN 'partitions.partition_number AS PartitionNumber' WHEN @PartitionLevel = 'N' THEN 'NULL AS PartitionNumber' END\n" +
"                                                    + ', ' + CASE WHEN @PartitionLevel = 'Y' THEN 'IndexPartitions.partition_count AS PartitionCount' WHEN @PartitionLevel = 'N' THEN 'NULL AS PartitionCount' END\n" +
"                                                    + ', 0 AS [Order]'\n" +
"                                                    + ', 0 AS Selected'\n" +
"                                                    + ', 0 AS Completed'\n" +
"                                                    + ' FROM sys.indexes indexes'\n" +
"                                                    + ' INNER JOIN sys.objects objects ON indexes.[object_id] = objects.[object_id]'\n" +
"                                                    + ' INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id]'\n" +
"                                                    + ' LEFT OUTER JOIN sys.tables tables ON objects.[object_id] = tables.[object_id]'\n" +
"                                                    + ' LEFT OUTER JOIN sys.stats stats ON indexes.[object_id] = stats.[object_id] AND indexes.[index_id] = stats.[stats_id]'\n" +
"          IF @PartitionLevel = 'Y'\n" +
"          BEGIN\n" +
"            SET @CurrentCommand = @CurrentCommand + ' LEFT OUTER JOIN sys.partitions partitions ON indexes.[object_id] = partitions.[object_id] AND indexes.index_id = partitions.index_id'\n" +
"                                                      + ' LEFT OUTER JOIN (SELECT partitions.[object_id], partitions.index_id, COUNT(DISTINCT partitions.partition_number) AS partition_count FROM sys.partitions partitions GROUP BY partitions.[object_id], partitions.index_id) IndexPartitions ON partitions.[object_id] = IndexPartitions.[object_id] AND partitions.[index_id] = IndexPartitions.[index_id]'\n" +
"          END\n" +
"\n" +
"          SET @CurrentCommand = @CurrentCommand + ' WHERE objects.[type] IN(''U'',''V'')'\n" +
"                                                    + CASE WHEN @MSShippedObjects = 'N' THEN ' AND objects.is_ms_shipped = 0' ELSE '' END\n" +
"                                                    + ' AND indexes.[type] IN(1,2,3,4,5,6,7)'\n" +
"                                                    + ' AND indexes.is_disabled = 0 AND indexes.is_hypothetical = 0'\n" +
"        END\n" +
"\n" +
"        IF (EXISTS(SELECT * FROM @ActionsPreferred) AND @UpdateStatistics = 'COLUMNS') OR @UpdateStatistics = 'ALL'\n" +
"        BEGIN\n" +
"          SET @CurrentCommand = @CurrentCommand + ' UNION '\n" +
"        END\n" +
"\n" +
"        IF @UpdateStatistics IN('ALL','COLUMNS')\n" +
"        BEGIN\n" +
"          SET @CurrentCommand = @CurrentCommand + 'SELECT schemas.[schema_id] AS SchemaID'\n" +
"                                                    + ', schemas.[name] AS SchemaName'\n" +
"                                                    + ', objects.[object_id] AS ObjectID'\n" +
"                                                    + ', objects.[name] AS ObjectName'\n" +
"                                                    + ', RTRIM(objects.[type]) AS ObjectType'\n" +
"                                                    + ', ' + CASE WHEN @Version >= 12 THEN 'tables.is_memory_optimized' ELSE '0' END + ' AS IsMemoryOptimized'\n" +
"                                                    + ', NULL AS IndexID, NULL AS IndexName'\n" +
"                                                    + ', NULL AS IndexType'\n" +
"                                                    + ', NULL AS AllowPageLocks'\n" +
"                                                    + ', NULL AS IsImageText'\n" +
"                                                    + ', NULL AS IsNewLOB'\n" +
"                                                    + ', NULL AS IsFileStream'\n" +
"                                                    + ', NULL AS IsColumnStore'\n" +
"                                                    + ', NULL AS IsComputed'\n" +
"                                                    + ', NULL AS IsTimestamp'\n" +
"                                                    + ', NULL AS OnReadOnlyFileGroup'\n" +
"                                                    + ', NULL AS ResumableIndexOperation'\n" +
"                                                    + ', stats.stats_id AS StatisticsID'\n" +
"                                                    + ', stats.name AS StatisticsName'\n" +
"                                                    + ', stats.no_recompute AS NoRecompute'\n" +
"                                                    + ', ' + CASE WHEN @Version >= 12 THEN 'stats.is_incremental' ELSE '0' END + ' AS IsIncremental'\n" +
"                                                    + ', NULL AS PartitionID'\n" +
"                                                    + ', ' + CASE WHEN @PartitionLevelStatistics = 1 THEN 'dm_db_incremental_stats_properties.partition_number' ELSE 'NULL' END + ' AS PartitionNumber'\n" +
"                                                    + ', NULL AS PartitionCount'\n" +
"                                                    + ', 0 AS [Order]'\n" +
"                                                    + ', 0 AS Selected'\n" +
"                                                    + ', 0 AS Completed'\n" +
"                                                    + ' FROM sys.stats stats'\n" +
"                                                    + ' INNER JOIN sys.objects objects ON stats.[object_id] = objects.[object_id]'\n" +
"                                                    + ' INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id]'\n" +
"                                                    + ' LEFT OUTER JOIN sys.tables tables ON objects.[object_id] = tables.[object_id]'\n" +
"\n" +
"          IF @PartitionLevelStatistics = 1\n" +
"          BEGIN\n" +
"            SET @CurrentCommand = @CurrentCommand + ' OUTER APPLY sys.dm_db_incremental_stats_properties(stats.object_id, stats.stats_id) dm_db_incremental_stats_properties'\n" +
"          END\n" +
"\n" +
"          SET @CurrentCommand = @CurrentCommand + ' WHERE objects.[type] IN(''U'',''V'')'\n" +
"                                                    + CASE WHEN @MSShippedObjects = 'N' THEN ' AND objects.is_ms_shipped = 0' ELSE '' END\n" +
"                                                    + ' AND NOT EXISTS(SELECT * FROM sys.indexes indexes WHERE indexes.[object_id] = stats.[object_id] AND indexes.index_id = stats.stats_id)'\n" +
"        END\n" +
"\n" +
"        SET @CurrentCommand = @CurrentCommand + ') IndexesStatistics'\n" +
"\n" +
"        INSERT INTO @tmpIndexesStatistics (SchemaID, SchemaName, ObjectID, ObjectName, ObjectType, IsMemoryOptimized, IndexID, IndexName, IndexType, AllowPageLocks, IsImageText, IsNewLOB, IsFileStream, IsColumnStore, IsComputed, IsTimestamp, OnReadOnlyFileGroup, ResumableIndexOperation, StatisticsID, StatisticsName, [NoRecompute], IsIncremental, PartitionID, PartitionNumber, PartitionCount, [Order], Selected, Completed)\n" +
"        EXECUTE @CurrentDatabase_sp_executesql @stmt = @CurrentCommand\n" +
"        SET @Error = @@ERROR\n" +
"        IF @Error <> 0\n" +
"        BEGIN\n" +
"          SET @ReturnCode = @Error\n" +
"        END\n" +
"      END\n" +
"\n" +
"      IF @Indexes IS NULL\n" +
"      BEGIN\n" +
"        UPDATE tmpIndexesStatistics\n" +
"        SET tmpIndexesStatistics.Selected = 1\n" +
"        FROM @tmpIndexesStatistics tmpIndexesStatistics\n" +
"      END\n" +
"      ELSE\n" +
"      BEGIN\n" +
"        UPDATE tmpIndexesStatistics\n" +
"        SET tmpIndexesStatistics.Selected = SelectedIndexes.Selected\n" +
"        FROM @tmpIndexesStatistics tmpIndexesStatistics\n" +
"        INNER JOIN @SelectedIndexes SelectedIndexes\n" +
"        ON @CurrentDatabaseName LIKE REPLACE(SelectedIndexes.DatabaseName,'_','[_]') AND tmpIndexesStatistics.SchemaName LIKE REPLACE(SelectedIndexes.SchemaName,'_','[_]') AND tmpIndexesStatistics.ObjectName LIKE REPLACE(SelectedIndexes.ObjectName,'_','[_]') AND COALESCE(tmpIndexesStatistics.IndexName,tmpIndexesStatistics.StatisticsName) LIKE REPLACE(SelectedIndexes.IndexName,'_','[_]')\n" +
"        WHERE SelectedIndexes.Selected = 1\n" +
"\n" +
"        UPDATE tmpIndexesStatistics\n" +
"        SET tmpIndexesStatistics.Selected = SelectedIndexes.Selected\n" +
"        FROM @tmpIndexesStatistics tmpIndexesStatistics\n" +
"        INNER JOIN @SelectedIndexes SelectedIndexes\n" +
"        ON @CurrentDatabaseName LIKE REPLACE(SelectedIndexes.DatabaseName,'_','[_]') AND tmpIndexesStatistics.SchemaName LIKE REPLACE(SelectedIndexes.SchemaName,'_','[_]') AND tmpIndexesStatistics.ObjectName LIKE REPLACE(SelectedIndexes.ObjectName,'_','[_]') AND COALESCE(tmpIndexesStatistics.IndexName,tmpIndexesStatistics.StatisticsName) LIKE REPLACE(SelectedIndexes.IndexName,'_','[_]')\n" +
"        WHERE SelectedIndexes.Selected = 0\n" +
"\n" +
"        UPDATE tmpIndexesStatistics\n" +
"        SET tmpIndexesStatistics.StartPosition = SelectedIndexes2.StartPosition\n" +
"        FROM @tmpIndexesStatistics tmpIndexesStatistics\n" +
"        INNER JOIN (SELECT tmpIndexesStatistics.SchemaName, tmpIndexesStatistics.ObjectName, tmpIndexesStatistics.IndexName, tmpIndexesStatistics.StatisticsName, MIN(SelectedIndexes.StartPosition) AS StartPosition\n" +
"                    FROM @tmpIndexesStatistics tmpIndexesStatistics\n" +
"                    INNER JOIN @SelectedIndexes SelectedIndexes\n" +
"                    ON @CurrentDatabaseName LIKE REPLACE(SelectedIndexes.DatabaseName,'_','[_]') AND tmpIndexesStatistics.SchemaName LIKE REPLACE(SelectedIndexes.SchemaName,'_','[_]') AND tmpIndexesStatistics.ObjectName LIKE REPLACE(SelectedIndexes.ObjectName,'_','[_]') AND COALESCE(tmpIndexesStatistics.IndexName,tmpIndexesStatistics.StatisticsName) LIKE REPLACE(SelectedIndexes.IndexName,'_','[_]')\n" +
"                    WHERE SelectedIndexes.Selected = 1\n" +
"                    GROUP BY tmpIndexesStatistics.SchemaName, tmpIndexesStatistics.ObjectName, tmpIndexesStatistics.IndexName, tmpIndexesStatistics.StatisticsName) SelectedIndexes2\n" +
"        ON tmpIndexesStatistics.SchemaName = SelectedIndexes2.SchemaName\n" +
"        AND tmpIndexesStatistics.ObjectName = SelectedIndexes2.ObjectName\n" +
"        AND (tmpIndexesStatistics.IndexName = SelectedIndexes2.IndexName OR tmpIndexesStatistics.IndexName IS NULL)\n" +
"        AND (tmpIndexesStatistics.StatisticsName = SelectedIndexes2.StatisticsName OR tmpIndexesStatistics.StatisticsName IS NULL)\n" +
"      END;\n" +
"\n" +
"      WITH tmpIndexesStatistics AS (\n" +
"      SELECT SchemaName, ObjectName, [Order], ROW_NUMBER() OVER (ORDER BY ISNULL(ResumableIndexOperation,0) DESC, StartPosition ASC, SchemaName ASC, ObjectName ASC, CASE WHEN IndexType IS NULL THEN 1 ELSE 0 END ASC, IndexType ASC, IndexName ASC, StatisticsName ASC, PartitionNumber ASC) AS RowNumber\n" +
"      FROM @tmpIndexesStatistics tmpIndexesStatistics\n" +
"      WHERE Selected = 1\n" +
"      )\n" +
"      UPDATE tmpIndexesStatistics\n" +
"      SET [Order] = RowNumber\n" +
"\n" +
"      SET @ErrorMessage = ''\n" +
"      SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + '.' + QUOTENAME(SchemaName) + '.' + QUOTENAME(ObjectName) + ', '\n" +
"      FROM @SelectedIndexes SelectedIndexes\n" +
"      WHERE DatabaseName = @CurrentDatabaseName\n" +
"      AND SchemaName NOT LIKE '%[%]%'\n" +
"      AND ObjectName NOT LIKE '%[%]%'\n" +
"      AND IndexName LIKE '%[%]%'\n" +
"      AND NOT EXISTS (SELECT * FROM @tmpIndexesStatistics WHERE SchemaName = SelectedIndexes.SchemaName AND ObjectName = SelectedIndexes.ObjectName)\n" +
"      IF @@ROWCOUNT > 0\n" +
"      BEGIN\n" +
"        SET @ErrorMessage = 'The following objects in the @Indexes parameter do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.'\n" +
"        RAISERROR('%s',10,1,@ErrorMessage) WITH NOWAIT\n" +
"        SET @Error = @@ERROR\n" +
"        RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"      END\n" +
"\n" +
"      SET @ErrorMessage = ''\n" +
"      SELECT @ErrorMessage = @ErrorMessage + QUOTENAME(DatabaseName) + QUOTENAME(SchemaName) + '.' + QUOTENAME(ObjectName) + '.' + QUOTENAME(IndexName) + ', '\n" +
"      FROM @SelectedIndexes SelectedIndexes\n" +
"      WHERE DatabaseName = @CurrentDatabaseName\n" +
"      AND SchemaName NOT LIKE '%[%]%'\n" +
"      AND ObjectName NOT LIKE '%[%]%'\n" +
"      AND IndexName NOT LIKE '%[%]%'\n" +
"      AND NOT EXISTS (SELECT * FROM @tmpIndexesStatistics WHERE SchemaName = SelectedIndexes.SchemaName AND ObjectName = SelectedIndexes.ObjectName AND IndexName = SelectedIndexes.IndexName)\n" +
"      IF @@ROWCOUNT > 0\n" +
"      BEGIN\n" +
"        SET @ErrorMessage = 'The following indexes in the @Indexes parameter do not exist: ' + LEFT(@ErrorMessage,LEN(@ErrorMessage)-1) + '.'\n" +
"        RAISERROR('%s',10,1,@ErrorMessage) WITH NOWAIT\n" +
"        SET @Error = @@ERROR\n" +
"        RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"      END\n" +
"\n" +
"      WHILE (SYSDATETIME() < DATEADD(SECOND,@TimeLimit,@StartTime) OR @TimeLimit IS NULL)\n" +
"      BEGIN\n" +
"        SELECT TOP 1 @CurrentIxID = ID,\n" +
"                     @CurrentIxOrder = [Order],\n" +
"                     @CurrentSchemaID = SchemaID,\n" +
"                     @CurrentSchemaName = SchemaName,\n" +
"                     @CurrentObjectID = ObjectID,\n" +
"                     @CurrentObjectName = ObjectName,\n" +
"                     @CurrentObjectType = ObjectType,\n" +
"                     @CurrentIsMemoryOptimized = IsMemoryOptimized,\n" +
"                     @CurrentIndexID = IndexID,\n" +
"                     @CurrentIndexName = IndexName,\n" +
"                     @CurrentIndexType = IndexType,\n" +
"                     @CurrentAllowPageLocks = AllowPageLocks,\n" +
"                     @CurrentIsImageText = IsImageText,\n" +
"                     @CurrentIsNewLOB = IsNewLOB,\n" +
"                     @CurrentIsFileStream = IsFileStream,\n" +
"                     @CurrentIsColumnStore = IsColumnStore,\n" +
"                     @CurrentIsComputed = IsComputed,\n" +
"                     @CurrentIsTimestamp = IsTimestamp,\n" +
"                     @CurrentOnReadOnlyFileGroup = OnReadOnlyFileGroup,\n" +
"                     @CurrentResumableIndexOperation = ResumableIndexOperation,\n" +
"                     @CurrentStatisticsID = StatisticsID,\n" +
"                     @CurrentStatisticsName = StatisticsName,\n" +
"                     @CurrentNoRecompute = [NoRecompute],\n" +
"                     @CurrentIsIncremental = IsIncremental,\n" +
"                     @CurrentPartitionID = PartitionID,\n" +
"                     @CurrentPartitionNumber = PartitionNumber,\n" +
"                     @CurrentPartitionCount = PartitionCount\n" +
"        FROM @tmpIndexesStatistics\n" +
"        WHERE Selected = 1\n" +
"        AND Completed = 0\n" +
"        ORDER BY [Order] ASC\n" +
"\n" +
"        IF @@ROWCOUNT = 0\n" +
"        BEGIN\n" +
"          BREAK\n" +
"        END\n" +
"\n" +
"        -- Is the index a partition?\n" +
"        IF @CurrentPartitionNumber IS NULL OR @CurrentPartitionCount = 1 BEGIN SET @CurrentIsPartition = 0 END ELSE BEGIN SET @CurrentIsPartition = 1 END\n" +
"\n" +
"        -- Does the index exist?\n" +
"        IF @CurrentIndexID IS NOT NULL AND EXISTS(SELECT * FROM @ActionsPreferred)\n" +
"        BEGIN\n" +
"          SET @CurrentCommand = ''\n" +
"\n" +
"          IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"\n" +
"          IF @CurrentIsPartition = 0 SET @CurrentCommand += 'IF EXISTS(SELECT * FROM sys.indexes indexes INNER JOIN sys.objects objects ON indexes.[object_id] = objects.[object_id] INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] IN(''U'',''V'') AND indexes.[type] IN(1,2,3,4,5,6,7) AND indexes.is_disabled = 0 AND indexes.is_hypothetical = 0 AND schemas.[schema_id] = @ParamSchemaID AND schemas.[name] = @ParamSchemaName AND objects.[object_id] = @ParamObjectID AND objects.[name] = @ParamObjectName AND objects.[type] = @ParamObjectType AND indexes.index_id = @ParamIndexID AND indexes.[name] = @ParamIndexName AND indexes.[type] = @ParamIndexType) BEGIN SET @ParamIndexExists = 1 END'\n" +
"          IF @CurrentIsPartition = 1 SET @CurrentCommand += 'IF EXISTS(SELECT * FROM sys.indexes indexes INNER JOIN sys.objects objects ON indexes.[object_id] = objects.[object_id] INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] INNER JOIN sys.partitions partitions ON indexes.[object_id] = partitions.[object_id] AND indexes.index_id = partitions.index_id WHERE objects.[type] IN(''U'',''V'') AND indexes.[type] IN(1,2,3,4,5,6,7) AND indexes.is_disabled = 0 AND indexes.is_hypothetical = 0 AND schemas.[schema_id] = @ParamSchemaID AND schemas.[name] = @ParamSchemaName AND objects.[object_id] = @ParamObjectID AND objects.[name] = @ParamObjectName AND objects.[type] = @ParamObjectType AND indexes.index_id = @ParamIndexID AND indexes.[name] = @ParamIndexName AND indexes.[type] = @ParamIndexType AND partitions.partition_id = @ParamPartitionID AND partitions.partition_number = @ParamPartitionNumber) BEGIN SET @ParamIndexExists = 1 END'\n" +
"\n" +
"          BEGIN TRY\n" +
"            EXECUTE @CurrentDatabase_sp_executesql @stmt = @CurrentCommand, @params = N'@ParamSchemaID int, @ParamSchemaName sysname, @ParamObjectID int, @ParamObjectName sysname, @ParamObjectType sysname, @ParamIndexID int, @ParamIndexName sysname, @ParamIndexType int, @ParamPartitionID bigint, @ParamPartitionNumber int, @ParamIndexExists bit OUTPUT', @ParamSchemaID = @CurrentSchemaID, @ParamSchemaName = @CurrentSchemaName, @ParamObjectID = @CurrentObjectID, @ParamObjectName = @CurrentObjectName, @ParamObjectType = @CurrentObjectType, @ParamIndexID = @CurrentIndexID, @ParamIndexName = @CurrentIndexName, @ParamIndexType = @CurrentIndexType, @ParamPartitionID = @CurrentPartitionID, @ParamPartitionNumber = @CurrentPartitionNumber, @ParamIndexExists = @CurrentIndexExists OUTPUT\n" +
"\n" +
"            IF @CurrentIndexExists IS NULL\n" +
"            BEGIN\n" +
"              SET @CurrentIndexExists = 0\n" +
"              GOTO NoAction\n" +
"            END\n" +
"          END TRY\n" +
"          BEGIN CATCH\n" +
"            SET @ErrorMessage = 'Msg ' + CAST(ERROR_NUMBER() AS nvarchar) + ', ' + ISNULL(ERROR_MESSAGE(),'') + CASE WHEN ERROR_NUMBER() = 1222 THEN ' The index ' + QUOTENAME(@CurrentIndexName) + ' on the object ' + QUOTENAME(@CurrentDatabaseName) + '.' + QUOTENAME(@CurrentSchemaName) + '.' + QUOTENAME(@CurrentObjectName) + ' is locked. It could not be checked if the index exists.' ELSE '' END\n" +
"            SET @Severity = CASE WHEN ERROR_NUMBER() IN(1205,1222) THEN @LockMessageSeverity ELSE 16 END\n" +
"            RAISERROR('%s',@Severity,1,@ErrorMessage) WITH NOWAIT\n" +
"            RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"            IF NOT (ERROR_NUMBER() IN(1205,1222) AND @LockMessageSeverity = 10)\n" +
"            BEGIN\n" +
"              SET @ReturnCode = ERROR_NUMBER()\n" +
"            END\n" +
"\n" +
"            GOTO NoAction\n" +
"          END CATCH\n" +
"        END\n" +
"\n" +
"        -- Does the statistics exist?\n" +
"        IF @CurrentStatisticsID IS NOT NULL AND @UpdateStatistics IS NOT NULL\n" +
"        BEGIN\n" +
"          SET @CurrentCommand = ''\n" +
"\n" +
"          IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"\n" +
"          SET @CurrentCommand += 'IF EXISTS(SELECT * FROM sys.stats stats INNER JOIN sys.objects objects ON stats.[object_id] = objects.[object_id] INNER JOIN sys.schemas schemas ON objects.[schema_id] = schemas.[schema_id] WHERE objects.[type] IN(''U'',''V'')' + CASE WHEN @MSShippedObjects = 'N' THEN ' AND objects.is_ms_shipped = 0' ELSE '' END + ' AND schemas.[schema_id] = @ParamSchemaID AND schemas.[name] = @ParamSchemaName AND objects.[object_id] = @ParamObjectID AND objects.[name] = @ParamObjectName AND objects.[type] = @ParamObjectType AND stats.stats_id = @ParamStatisticsID AND stats.[name] = @ParamStatisticsName) BEGIN SET @ParamStatisticsExists = 1 END'\n" +
"\n" +
"          BEGIN TRY\n" +
"            EXECUTE @CurrentDatabase_sp_executesql @stmt = @CurrentCommand, @params = N'@ParamSchemaID int, @ParamSchemaName sysname, @ParamObjectID int, @ParamObjectName sysname, @ParamObjectType sysname, @ParamStatisticsID int, @ParamStatisticsName sysname, @ParamStatisticsExists bit OUTPUT', @ParamSchemaID = @CurrentSchemaID, @ParamSchemaName = @CurrentSchemaName, @ParamObjectID = @CurrentObjectID, @ParamObjectName = @CurrentObjectName, @ParamObjectType = @CurrentObjectType, @ParamStatisticsID = @CurrentStatisticsID, @ParamStatisticsName = @CurrentStatisticsName, @ParamStatisticsExists = @CurrentStatisticsExists OUTPUT\n" +
"\n" +
"            IF @CurrentStatisticsExists IS NULL\n" +
"            BEGIN\n" +
"              SET @CurrentStatisticsExists = 0\n" +
"              GOTO NoAction\n" +
"            END\n" +
"          END TRY\n" +
"          BEGIN CATCH\n" +
"            SET @ErrorMessage = 'Msg ' + CAST(ERROR_NUMBER() AS nvarchar) + ', ' + ISNULL(ERROR_MESSAGE(),'') + CASE WHEN ERROR_NUMBER() = 1222 THEN ' The statistics ' + QUOTENAME(@CurrentStatisticsName) + ' on the object ' + QUOTENAME(@CurrentDatabaseName) + '.' + QUOTENAME(@CurrentSchemaName) + '.' + QUOTENAME(@CurrentObjectName) + ' is locked. It could not be checked if the statistics exists.' ELSE '' END\n" +
"            SET @Severity = CASE WHEN ERROR_NUMBER() IN(1205,1222) THEN @LockMessageSeverity ELSE 16 END\n" +
"            RAISERROR('%s',@Severity,1,@ErrorMessage) WITH NOWAIT\n" +
"            RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"            IF NOT (ERROR_NUMBER() IN(1205,1222) AND @LockMessageSeverity = 10)\n" +
"            BEGIN\n" +
"              SET @ReturnCode = ERROR_NUMBER()\n" +
"            END\n" +
"\n" +
"            GOTO NoAction\n" +
"          END CATCH\n" +
"        END\n" +
"\n" +
"        -- Has the data in the statistics been modified since the statistics was last updated?\n" +
"        IF @CurrentStatisticsID IS NOT NULL AND @UpdateStatistics IS NOT NULL\n" +
"        BEGIN\n" +
"          SET @CurrentCommand = ''\n" +
"\n" +
"          IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"\n" +
"          IF @PartitionLevelStatistics = 1 AND @CurrentIsIncremental = 1\n" +
"          BEGIN\n" +
"            SET @CurrentCommand += 'SELECT @ParamRowCount = [rows], @ParamModificationCounter = modification_counter FROM sys.dm_db_incremental_stats_properties (@ParamObjectID, @ParamStatisticsID) WHERE partition_number = @ParamPartitionNumber'\n" +
"          END\n" +
"          ELSE\n" +
"          IF (@Version >= 10.504000 AND @Version < 11) OR @Version >= 11.03000\n" +
"          BEGIN\n" +
"            SET @CurrentCommand += 'SELECT @ParamRowCount = [rows], @ParamModificationCounter = modification_counter FROM sys.dm_db_stats_properties (@ParamObjectID, @ParamStatisticsID)'\n" +
"          END\n" +
"          ELSE\n" +
"          BEGIN\n" +
"            SET @CurrentCommand += 'SELECT @ParamRowCount = rowcnt, @ParamModificationCounter = rowmodctr FROM sys.sysindexes sysindexes WHERE sysindexes.[id] = @ParamObjectID AND sysindexes.[indid] = @ParamStatisticsID'\n" +
"          END\n" +
"\n" +
"          BEGIN TRY\n" +
"            EXECUTE @CurrentDatabase_sp_executesql @stmt = @CurrentCommand, @params = N'@ParamObjectID int, @ParamStatisticsID int, @ParamPartitionNumber int, @ParamRowCount bigint OUTPUT, @ParamModificationCounter bigint OUTPUT', @ParamObjectID = @CurrentObjectID, @ParamStatisticsID = @CurrentStatisticsID, @ParamPartitionNumber = @CurrentPartitionNumber, @ParamRowCount = @CurrentRowCount OUTPUT, @ParamModificationCounter = @CurrentModificationCounter OUTPUT\n" +
"\n" +
"            IF @CurrentRowCount IS NULL SET @CurrentRowCount = 0\n" +
"            IF @CurrentModificationCounter IS NULL SET @CurrentModificationCounter = 0\n" +
"          END TRY\n" +
"          BEGIN CATCH\n" +
"            SET @ErrorMessage = 'Msg ' + CAST(ERROR_NUMBER() AS nvarchar) + ', ' + ISNULL(ERROR_MESSAGE(),'') + CASE WHEN ERROR_NUMBER() = 1222 THEN ' The statistics ' + QUOTENAME(@CurrentStatisticsName) + ' on the object ' + QUOTENAME(@CurrentDatabaseName) + '.' + QUOTENAME(@CurrentSchemaName) + '.' + QUOTENAME(@CurrentObjectName) + ' is locked. The rows and modification_counter could not be checked.' ELSE '' END\n" +
"            SET @Severity = CASE WHEN ERROR_NUMBER() IN(1205,1222) THEN @LockMessageSeverity ELSE 16 END\n" +
"            RAISERROR('%s',@Severity,1,@ErrorMessage) WITH NOWAIT\n" +
"            RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"            IF NOT (ERROR_NUMBER() IN(1205,1222) AND @LockMessageSeverity = 10)\n" +
"            BEGIN\n" +
"              SET @ReturnCode = ERROR_NUMBER()\n" +
"            END\n" +
"\n" +
"            GOTO NoAction\n" +
"          END CATCH\n" +
"        END\n" +
"\n" +
"        -- Is the index fragmented?\n" +
"        IF @CurrentIndexID IS NOT NULL\n" +
"        AND @CurrentOnReadOnlyFileGroup = 0\n" +
"        AND EXISTS(SELECT * FROM @ActionsPreferred)\n" +
"        AND (EXISTS(SELECT [Priority], [Action], COUNT(*) FROM @ActionsPreferred GROUP BY [Priority], [Action] HAVING COUNT(*) <> 3) OR @MinNumberOfPages > 0 OR @MaxNumberOfPages IS NOT NULL)\n" +
"        BEGIN\n" +
"          SET @CurrentCommand = ''\n" +
"\n" +
"          IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"\n" +
"          SET @CurrentCommand += 'SELECT @ParamFragmentationLevel = MAX(avg_fragmentation_in_percent), @ParamPageCount = SUM(page_count) FROM sys.dm_db_index_physical_stats(DB_ID(@ParamDatabaseName), @ParamObjectID, @ParamIndexID, @ParamPartitionNumber, ''LIMITED'') WHERE alloc_unit_type_desc = ''IN_ROW_DATA'' AND index_level = 0'\n" +
"\n" +
"          BEGIN TRY\n" +
"            EXECUTE sp_executesql @stmt = @CurrentCommand, @params = N'@ParamDatabaseName nvarchar(max), @ParamObjectID int, @ParamIndexID int, @ParamPartitionNumber int, @ParamFragmentationLevel float OUTPUT, @ParamPageCount bigint OUTPUT', @ParamDatabaseName = @CurrentDatabaseName, @ParamObjectID = @CurrentObjectID, @ParamIndexID = @CurrentIndexID, @ParamPartitionNumber = @CurrentPartitionNumber, @ParamFragmentationLevel = @CurrentFragmentationLevel OUTPUT, @ParamPageCount = @CurrentPageCount OUTPUT\n" +
"          END TRY\n" +
"          BEGIN CATCH\n" +
"            SET @ErrorMessage = 'Msg ' + CAST(ERROR_NUMBER() AS nvarchar) + ', ' + ISNULL(ERROR_MESSAGE(),'') + CASE WHEN ERROR_NUMBER() = 1222 THEN ' The index ' + QUOTENAME(@CurrentIndexName) + ' on the object ' + QUOTENAME(@CurrentDatabaseName) + '.' + QUOTENAME(@CurrentSchemaName) + '.' + QUOTENAME(@CurrentObjectName) + ' is locked. The page_count and avg_fragmentation_in_percent could not be checked.' ELSE '' END\n" +
"            SET @Severity = CASE WHEN ERROR_NUMBER() IN(1205,1222) THEN @LockMessageSeverity ELSE 16 END\n" +
"            RAISERROR('%s',@Severity,1,@ErrorMessage) WITH NOWAIT\n" +
"            RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"            IF NOT (ERROR_NUMBER() IN(1205,1222) AND @LockMessageSeverity = 10)\n" +
"            BEGIN\n" +
"              SET @ReturnCode = ERROR_NUMBER()\n" +
"            END\n" +
"\n" +
"            GOTO NoAction\n" +
"          END CATCH\n" +
"        END\n" +
"\n" +
"        -- Select fragmentation group\n" +
"        IF @CurrentIndexID IS NOT NULL AND @CurrentOnReadOnlyFileGroup = 0 AND EXISTS(SELECT * FROM @ActionsPreferred)\n" +
"        BEGIN\n" +
"          SET @CurrentFragmentationGroup = CASE\n" +
"          WHEN @CurrentFragmentationLevel >= @FragmentationLevel2 THEN 'High'\n" +
"          WHEN @CurrentFragmentationLevel >= @FragmentationLevel1 AND @CurrentFragmentationLevel < @FragmentationLevel2 THEN 'Medium'\n" +
"          WHEN @CurrentFragmentationLevel < @FragmentationLevel1 THEN 'Low'\n" +
"          END\n" +
"        END\n" +
"\n" +
"        -- Which actions are allowed?\n" +
"        IF @CurrentIndexID IS NOT NULL AND EXISTS(SELECT * FROM @ActionsPreferred)\n" +
"        BEGIN\n" +
"          IF @CurrentOnReadOnlyFileGroup = 0 AND @CurrentIndexType IN (1,2,3,4,5) AND (@CurrentIsMemoryOptimized = 0 OR @CurrentIsMemoryOptimized IS NULL) AND (@CurrentAllowPageLocks = 1 OR @CurrentIndexType = 5)\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentActionsAllowed ([Action])\n" +
"            VALUES ('INDEX_REORGANIZE')\n" +
"          END\n" +
"          IF @CurrentOnReadOnlyFileGroup = 0 AND @CurrentIndexType IN (1,2,3,4,5) AND (@CurrentIsMemoryOptimized = 0 OR @CurrentIsMemoryOptimized IS NULL)\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentActionsAllowed ([Action])\n" +
"            VALUES ('INDEX_REBUILD_OFFLINE')\n" +
"          END\n" +
"          IF @CurrentOnReadOnlyFileGroup = 0\n" +
"          AND (@CurrentIsMemoryOptimized = 0 OR @CurrentIsMemoryOptimized IS NULL)\n" +
"          AND (@CurrentIsPartition = 0 OR @Version >= 12)\n" +
"          AND ((@CurrentIndexType = 1 AND @CurrentIsImageText = 0 AND @CurrentIsNewLOB = 0)\n" +
"          OR (@CurrentIndexType = 2 AND @CurrentIsNewLOB = 0)\n" +
"          OR (@CurrentIndexType = 1 AND @CurrentIsImageText = 0 AND @CurrentIsFileStream = 0 AND @Version >= 11)\n" +
"          OR (@CurrentIndexType = 2 AND @Version >= 11))\n" +
"          AND (@CurrentIsColumnStore = 0 OR @Version < 11)\n" +
"          AND SERVERPROPERTY('EngineEdition') IN (3,5,8)\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentActionsAllowed ([Action])\n" +
"            VALUES ('INDEX_REBUILD_ONLINE')\n" +
"          END\n" +
"        END\n" +
"\n" +
"        -- Decide action\n" +
"        IF @CurrentIndexID IS NOT NULL\n" +
"        AND EXISTS(SELECT * FROM @ActionsPreferred)\n" +
"        AND (@CurrentPageCount >= @MinNumberOfPages OR @MinNumberOfPages = 0)\n" +
"        AND (@CurrentPageCount <= @MaxNumberOfPages OR @MaxNumberOfPages IS NULL)\n" +
"        AND @CurrentResumableIndexOperation = 0\n" +
"        BEGIN\n" +
"          IF EXISTS(SELECT [Priority], [Action], COUNT(*) FROM @ActionsPreferred GROUP BY [Priority], [Action] HAVING COUNT(*) <> 3)\n" +
"          BEGIN\n" +
"            SELECT @CurrentAction = [Action]\n" +
"            FROM @ActionsPreferred\n" +
"            WHERE FragmentationGroup = @CurrentFragmentationGroup\n" +
"            AND [Priority] = (SELECT MIN([Priority])\n" +
"                              FROM @ActionsPreferred\n" +
"                              WHERE FragmentationGroup = @CurrentFragmentationGroup\n" +
"                              AND [Action] IN (SELECT [Action] FROM @CurrentActionsAllowed))\n" +
"          END\n" +
"          ELSE\n" +
"          BEGIN\n" +
"            SELECT @CurrentAction = [Action]\n" +
"            FROM @ActionsPreferred\n" +
"            WHERE [Priority] = (SELECT MIN([Priority])\n" +
"                                FROM @ActionsPreferred\n" +
"                                WHERE [Action] IN (SELECT [Action] FROM @CurrentActionsAllowed))\n" +
"          END\n" +
"        END\n" +
"\n" +
"        IF @CurrentResumableIndexOperation = 1\n" +
"        BEGIN\n" +
"          SET @CurrentAction = 'INDEX_REBUILD_ONLINE'\n" +
"        END\n" +
"\n" +
"        -- Workaround for limitation in SQL Server, http://support.microsoft.com/kb/2292737\n" +
"        IF @CurrentIndexID IS NOT NULL\n" +
"        BEGIN\n" +
"          SET @CurrentMaxDOP = @MaxDOP\n" +
"\n" +
"          IF @CurrentAction = 'INDEX_REBUILD_ONLINE' AND @CurrentAllowPageLocks = 0\n" +
"          BEGIN\n" +
"            SET @CurrentMaxDOP = 1\n" +
"          END\n" +
"        END\n" +
"\n" +
"        -- Update statistics?\n" +
"        IF @CurrentStatisticsID IS NOT NULL\n" +
"        AND ((@UpdateStatistics = 'ALL' AND (@CurrentIndexType IN (1,2,3,4,7) OR @CurrentIndexID IS NULL)) OR (@UpdateStatistics = 'INDEX' AND @CurrentIndexID IS NOT NULL AND @CurrentIndexType IN (1,2,3,4,7)) OR (@UpdateStatistics = 'COLUMNS' AND @CurrentIndexID IS NULL))\n" +
"        AND ((@OnlyModifiedStatistics = 'N' AND @StatisticsModificationLevel IS NULL) OR (@OnlyModifiedStatistics = 'Y' AND @CurrentModificationCounter > 0) OR ((@CurrentModificationCounter * 1. / NULLIF(@CurrentRowCount,0)) * 100 >= @StatisticsModificationLevel) OR (@StatisticsModificationLevel IS NOT NULL AND @CurrentModificationCounter > 0 AND (@CurrentModificationCounter >= SQRT(@CurrentRowCount * 1000))) OR (@CurrentIsMemoryOptimized = 1 AND NOT (@Version >= 13 OR SERVERPROPERTY('EngineEdition') IN (5,8))))\n" +
"        AND ((@CurrentIsPartition = 0 AND (@CurrentAction NOT IN('INDEX_REBUILD_ONLINE','INDEX_REBUILD_OFFLINE') OR @CurrentAction IS NULL)) OR (@CurrentIsPartition = 1 AND (@CurrentPartitionNumber = @CurrentPartitionCount OR (@PartitionLevelStatistics = 1 AND @CurrentIsIncremental = 1))))\n" +
"        BEGIN\n" +
"          SET @CurrentUpdateStatistics = 'Y'\n" +
"        END\n" +
"        ELSE\n" +
"        BEGIN\n" +
"          SET @CurrentUpdateStatistics = 'N'\n" +
"        END\n" +
"\n" +
"        SET @CurrentStatisticsSample = @StatisticsSample\n" +
"        SET @CurrentStatisticsResample = @StatisticsResample\n" +
"\n" +
"        -- Memory-optimized tables only supports FULLSCAN and RESAMPLE in SQL Server 2014\n" +
"        IF @CurrentIsMemoryOptimized = 1 AND NOT (@Version >= 13 OR SERVERPROPERTY('EngineEdition') IN (5,8)) AND (@CurrentStatisticsSample <> 100 OR @CurrentStatisticsSample IS NULL)\n" +
"        BEGIN\n" +
"          SET @CurrentStatisticsSample = NULL\n" +
"          SET @CurrentStatisticsResample = 'Y'\n" +
"        END\n" +
"\n" +
"        -- Incremental statistics only supports RESAMPLE\n" +
"        IF @PartitionLevelStatistics = 1 AND @CurrentIsIncremental = 1\n" +
"        BEGIN\n" +
"          SET @CurrentStatisticsSample = NULL\n" +
"          SET @CurrentStatisticsResample = 'Y'\n" +
"        END\n" +
"\n" +
"        -- Create index comment\n" +
"        IF @CurrentIndexID IS NOT NULL\n" +
"        BEGIN\n" +
"          SET @CurrentComment = 'ObjectType: ' + CASE WHEN @CurrentObjectType = 'U' THEN 'Table' WHEN @CurrentObjectType = 'V' THEN 'View' ELSE 'N/A' END + ', '\n" +
"          SET @CurrentComment += 'IndexType: ' + CASE WHEN @CurrentIndexType = 1 THEN 'Clustered' WHEN @CurrentIndexType = 2 THEN 'NonClustered' WHEN @CurrentIndexType = 3 THEN 'XML' WHEN @CurrentIndexType = 4 THEN 'Spatial' WHEN @CurrentIndexType = 5 THEN 'Clustered Columnstore' WHEN @CurrentIndexType = 6 THEN 'NonClustered Columnstore' WHEN @CurrentIndexType = 7 THEN 'NonClustered Hash' ELSE 'N/A' END + ', '\n" +
"          SET @CurrentComment += 'ImageText: ' + CASE WHEN @CurrentIsImageText = 1 THEN 'Yes' WHEN @CurrentIsImageText = 0 THEN 'No' ELSE 'N/A' END + ', '\n" +
"          SET @CurrentComment += 'NewLOB: ' + CASE WHEN @CurrentIsNewLOB = 1 THEN 'Yes' WHEN @CurrentIsNewLOB = 0 THEN 'No' ELSE 'N/A' END + ', '\n" +
"          SET @CurrentComment += 'FileStream: ' + CASE WHEN @CurrentIsFileStream = 1 THEN 'Yes' WHEN @CurrentIsFileStream = 0 THEN 'No' ELSE 'N/A' END + ', '\n" +
"          IF @Version >= 11 SET @CurrentComment += 'ColumnStore: ' + CASE WHEN @CurrentIsColumnStore = 1 THEN 'Yes' WHEN @CurrentIsColumnStore = 0 THEN 'No' ELSE 'N/A' END + ', '\n" +
"          IF @Version >= 14 AND @Resumable = 'Y' SET @CurrentComment += 'Computed: ' + CASE WHEN @CurrentIsComputed = 1 THEN 'Yes' WHEN @CurrentIsComputed = 0 THEN 'No' ELSE 'N/A' END + ', '\n" +
"          IF @Version >= 14 AND @Resumable = 'Y' SET @CurrentComment += 'Timestamp: ' + CASE WHEN @CurrentIsTimestamp = 1 THEN 'Yes' WHEN @CurrentIsTimestamp = 0 THEN 'No' ELSE 'N/A' END + ', '\n" +
"          SET @CurrentComment += 'AllowPageLocks: ' + CASE WHEN @CurrentAllowPageLocks = 1 THEN 'Yes' WHEN @CurrentAllowPageLocks = 0 THEN 'No' ELSE 'N/A' END + ', '\n" +
"          SET @CurrentComment += 'PageCount: ' + ISNULL(CAST(@CurrentPageCount AS nvarchar),'N/A') + ', '\n" +
"          SET @CurrentComment += 'Fragmentation: ' + ISNULL(CAST(@CurrentFragmentationLevel AS nvarchar),'N/A')\n" +
"        END\n" +
"\n" +
"        IF @CurrentIndexID IS NOT NULL AND (@CurrentPageCount IS NOT NULL OR @CurrentFragmentationLevel IS NOT NULL)\n" +
"        BEGIN\n" +
"        SET @CurrentExtendedInfo = (SELECT *\n" +
"                                    FROM (SELECT CAST(@CurrentPageCount AS nvarchar) AS [PageCount],\n" +
"                                                 CAST(@CurrentFragmentationLevel AS nvarchar) AS Fragmentation\n" +
"                                    ) ExtendedInfo FOR XML RAW('ExtendedInfo'), ELEMENTS)\n" +
"        END\n" +
"\n" +
"        IF @CurrentIndexID IS NOT NULL AND @CurrentAction IS NOT NULL AND (SYSDATETIME() < DATEADD(SECOND,@TimeLimit,@StartTime) OR @TimeLimit IS NULL)\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseContext = @CurrentDatabaseName\n" +
"\n" +
"          SET @CurrentCommandType = 'ALTER_INDEX'\n" +
"\n" +
"          SET @CurrentCommand = ''\n" +
"          IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"          SET @CurrentCommand += 'ALTER INDEX ' + QUOTENAME(@CurrentIndexName) + ' ON ' + QUOTENAME(@CurrentSchemaName) + '.' + QUOTENAME(@CurrentObjectName)\n" +
"          IF @CurrentResumableIndexOperation = 1 SET @CurrentCommand += ' RESUME'\n" +
"          IF @CurrentAction IN('INDEX_REBUILD_ONLINE','INDEX_REBUILD_OFFLINE') AND @CurrentResumableIndexOperation = 0 SET @CurrentCommand += ' REBUILD'\n" +
"          IF @CurrentAction IN('INDEX_REORGANIZE') AND @CurrentResumableIndexOperation = 0 SET @CurrentCommand += ' REORGANIZE'\n" +
"          IF @CurrentIsPartition = 1 AND @CurrentResumableIndexOperation = 0 SET @CurrentCommand += ' PARTITION = ' + CAST(@CurrentPartitionNumber AS nvarchar)\n" +
"\n" +
"          IF @CurrentAction IN('INDEX_REBUILD_ONLINE','INDEX_REBUILD_OFFLINE') AND @SortInTempdb = 'Y' AND @CurrentIndexType IN(1,2,3,4) AND @CurrentResumableIndexOperation = 0\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentAlterIndexWithClauseArguments (Argument)\n" +
"            SELECT 'SORT_IN_TEMPDB = ON'\n" +
"          END\n" +
"\n" +
"          IF @CurrentAction IN('INDEX_REBUILD_ONLINE','INDEX_REBUILD_OFFLINE') AND @SortInTempdb = 'N' AND @CurrentIndexType IN(1,2,3,4) AND @CurrentResumableIndexOperation = 0\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentAlterIndexWithClauseArguments (Argument)\n" +
"            SELECT 'SORT_IN_TEMPDB = OFF'\n" +
"          END\n" +
"\n" +
"          IF @CurrentAction = 'INDEX_REBUILD_ONLINE' AND (@CurrentIsPartition = 0 OR @Version >= 12) AND @CurrentResumableIndexOperation = 0\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentAlterIndexWithClauseArguments (Argument)\n" +
"            SELECT 'ONLINE = ON' + CASE WHEN @WaitAtLowPriorityMaxDuration IS NOT NULL THEN ' (WAIT_AT_LOW_PRIORITY (MAX_DURATION = ' + CAST(@WaitAtLowPriorityMaxDuration AS nvarchar) + ', ABORT_AFTER_WAIT = ' + UPPER(@WaitAtLowPriorityAbortAfterWait) + '))' ELSE '' END\n" +
"          END\n" +
"\n" +
"          IF @CurrentAction = 'INDEX_REBUILD_OFFLINE' AND (@CurrentIsPartition = 0 OR @Version >= 12) AND @CurrentResumableIndexOperation = 0\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentAlterIndexWithClauseArguments (Argument)\n" +
"            SELECT 'ONLINE = OFF'\n" +
"          END\n" +
"\n" +
"          IF @CurrentAction IN('INDEX_REBUILD_ONLINE','INDEX_REBUILD_OFFLINE') AND @CurrentMaxDOP IS NOT NULL\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentAlterIndexWithClauseArguments (Argument)\n" +
"            SELECT 'MAXDOP = ' + CAST(@CurrentMaxDOP AS nvarchar)\n" +
"          END\n" +
"\n" +
"          IF @CurrentAction IN('INDEX_REBUILD_ONLINE','INDEX_REBUILD_OFFLINE') AND @FillFactor IS NOT NULL AND @CurrentIsPartition = 0 AND @CurrentIndexType IN(1,2,3,4) AND @CurrentResumableIndexOperation = 0\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentAlterIndexWithClauseArguments (Argument)\n" +
"            SELECT 'FILLFACTOR = ' + CAST(@FillFactor AS nvarchar)\n" +
"          END\n" +
"\n" +
"          IF @CurrentAction IN('INDEX_REBUILD_ONLINE','INDEX_REBUILD_OFFLINE') AND @PadIndex = 'Y' AND @CurrentIsPartition = 0 AND @CurrentIndexType IN(1,2,3,4) AND @CurrentResumableIndexOperation = 0\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentAlterIndexWithClauseArguments (Argument)\n" +
"            SELECT 'PAD_INDEX = ON'\n" +
"          END\n" +
"\n" +
"          IF (@Version >= 14 OR SERVERPROPERTY('EngineEdition') IN (5,8)) AND @CurrentAction = 'INDEX_REBUILD_ONLINE' AND @CurrentResumableIndexOperation = 0\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentAlterIndexWithClauseArguments (Argument)\n" +
"            SELECT CASE WHEN @Resumable = 'Y' AND @CurrentIndexType IN(1,2) AND @CurrentIsComputed = 0 AND @CurrentIsTimestamp = 0 THEN 'RESUMABLE = ON' ELSE 'RESUMABLE = OFF' END\n" +
"          END\n" +
"\n" +
"          IF (@Version >= 14 OR SERVERPROPERTY('EngineEdition') IN (5,8)) AND @CurrentAction = 'INDEX_REBUILD_ONLINE' AND @CurrentResumableIndexOperation = 0 AND @Resumable = 'Y'  AND @CurrentIndexType IN(1,2) AND @CurrentIsComputed = 0 AND @CurrentIsTimestamp = 0 AND @TimeLimit IS NOT NULL\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentAlterIndexWithClauseArguments (Argument)\n" +
"            SELECT 'MAX_DURATION = ' + CAST(DATEDIFF(MINUTE,SYSDATETIME(),DATEADD(SECOND,@TimeLimit,@StartTime)) AS nvarchar(max))\n" +
"          END\n" +
"\n" +
"          IF @CurrentAction IN('INDEX_REORGANIZE') AND @LOBCompaction = 'Y'\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentAlterIndexWithClauseArguments (Argument)\n" +
"            SELECT 'LOB_COMPACTION = ON'\n" +
"          END\n" +
"\n" +
"          IF @CurrentAction IN('INDEX_REORGANIZE') AND @LOBCompaction = 'N'\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentAlterIndexWithClauseArguments (Argument)\n" +
"            SELECT 'LOB_COMPACTION = OFF'\n" +
"          END\n" +
"\n" +
"          IF EXISTS (SELECT * FROM @CurrentAlterIndexWithClauseArguments)\n" +
"          BEGIN\n" +
"            SET @CurrentAlterIndexWithClause = ' WITH ('\n" +
"\n" +
"            WHILE (1 = 1)\n" +
"            BEGIN\n" +
"              SELECT TOP 1 @CurrentAlterIndexArgumentID = ID,\n" +
"                           @CurrentAlterIndexArgument = Argument\n" +
"              FROM @CurrentAlterIndexWithClauseArguments\n" +
"              WHERE Added = 0\n" +
"              ORDER BY ID ASC\n" +
"\n" +
"              IF @@ROWCOUNT = 0\n" +
"              BEGIN\n" +
"                BREAK\n" +
"              END\n" +
"\n" +
"              SET @CurrentAlterIndexWithClause += @CurrentAlterIndexArgument + ', '\n" +
"\n" +
"              UPDATE @CurrentAlterIndexWithClauseArguments\n" +
"              SET Added = 1\n" +
"              WHERE [ID] = @CurrentAlterIndexArgumentID\n" +
"            END\n" +
"\n" +
"            SET @CurrentAlterIndexWithClause = RTRIM(@CurrentAlterIndexWithClause)\n" +
"\n" +
"            SET @CurrentAlterIndexWithClause = LEFT(@CurrentAlterIndexWithClause,LEN(@CurrentAlterIndexWithClause) - 1)\n" +
"\n" +
"            SET @CurrentAlterIndexWithClause = @CurrentAlterIndexWithClause + ')'\n" +
"          END\n" +
"\n" +
"          IF @CurrentAlterIndexWithClause IS NOT NULL SET @CurrentCommand += @CurrentAlterIndexWithClause\n" +
"\n" +
"          EXECUTE @CurrentCommandOutput = dbo.CommandExecute @DatabaseContext = @CurrentDatabaseName, @Command = @CurrentCommand, @CommandType = @CurrentCommandType, @Mode = 2, @Comment = @CurrentComment, @DatabaseName = @CurrentDatabaseName, @SchemaName = @CurrentSchemaName, @ObjectName = @CurrentObjectName, @ObjectType = @CurrentObjectType, @IndexName = @CurrentIndexName, @IndexType = @CurrentIndexType, @PartitionNumber = @CurrentPartitionNumber, @ExtendedInfo = @CurrentExtendedInfo, @LockMessageSeverity = @LockMessageSeverity, @ExecuteAsUser = @ExecuteAsUser, @LogToTable = @LogToTable, @Execute = @Execute\n" +
"          SET @Error = @@ERROR\n" +
"          IF @Error <> 0 SET @CurrentCommandOutput = @Error\n" +
"          IF @CurrentCommandOutput <> 0 SET @ReturnCode = @CurrentCommandOutput\n" +
"\n" +
"          IF @Delay > 0\n" +
"          BEGIN\n" +
"            SET @CurrentDelay = DATEADD(ss,@Delay,'1900-01-01')\n" +
"            WAITFOR DELAY @CurrentDelay\n" +
"          END\n" +
"        END\n" +
"\n" +
"        SET @CurrentMaxDOP = @MaxDOP\n" +
"\n" +
"        -- Create statistics comment\n" +
"        IF @CurrentStatisticsID IS NOT NULL\n" +
"        BEGIN\n" +
"          SET @CurrentComment = 'ObjectType: ' + CASE WHEN @CurrentObjectType = 'U' THEN 'Table' WHEN @CurrentObjectType = 'V' THEN 'View' ELSE 'N/A' END + ', '\n" +
"          SET @CurrentComment += 'IndexType: ' + CASE WHEN @CurrentIndexID IS NOT NULL THEN 'Index' ELSE 'Column' END + ', '\n" +
"          IF @CurrentIndexID IS NOT NULL SET @CurrentComment += 'IndexType: ' + CASE WHEN @CurrentIndexType = 1 THEN 'Clustered' WHEN @CurrentIndexType = 2 THEN 'NonClustered' WHEN @CurrentIndexType = 3 THEN 'XML' WHEN @CurrentIndexType = 4 THEN 'Spatial' WHEN @CurrentIndexType = 5 THEN 'Clustered Columnstore' WHEN @CurrentIndexType = 6 THEN 'NonClustered Columnstore' WHEN @CurrentIndexType = 7 THEN 'NonClustered Hash' ELSE 'N/A' END + ', '\n" +
"          SET @CurrentComment += 'Incremental: ' + CASE WHEN @CurrentIsIncremental = 1 THEN 'Y' WHEN @CurrentIsIncremental = 0 THEN 'N' ELSE 'N/A' END + ', '\n" +
"          SET @CurrentComment += 'RowCount: ' + ISNULL(CAST(@CurrentRowCount AS nvarchar),'N/A') + ', '\n" +
"          SET @CurrentComment += 'ModificationCounter: ' + ISNULL(CAST(@CurrentModificationCounter AS nvarchar),'N/A')\n" +
"        END\n" +
"\n" +
"        IF @CurrentStatisticsID IS NOT NULL AND (@CurrentRowCount IS NOT NULL OR @CurrentModificationCounter IS NOT NULL)\n" +
"        BEGIN\n" +
"        SET @CurrentExtendedInfo = (SELECT *\n" +
"                                    FROM (SELECT CAST(@CurrentRowCount AS nvarchar) AS [RowCount],\n" +
"                                                 CAST(@CurrentModificationCounter AS nvarchar) AS ModificationCounter\n" +
"                                    ) ExtendedInfo FOR XML RAW('ExtendedInfo'), ELEMENTS)\n" +
"        END\n" +
"\n" +
"        IF @CurrentStatisticsID IS NOT NULL AND @CurrentUpdateStatistics = 'Y' AND (SYSDATETIME() < DATEADD(SECOND,@TimeLimit,@StartTime) OR @TimeLimit IS NULL)\n" +
"        BEGIN\n" +
"          SET @CurrentDatabaseContext = @CurrentDatabaseName\n" +
"\n" +
"          SET @CurrentCommandType = 'UPDATE_STATISTICS'\n" +
"\n" +
"          SET @CurrentCommand = ''\n" +
"          IF @LockTimeout IS NOT NULL SET @CurrentCommand = 'SET LOCK_TIMEOUT ' + CAST(@LockTimeout * 1000 AS nvarchar) + '; '\n" +
"          SET @CurrentCommand += 'UPDATE STATISTICS ' + QUOTENAME(@CurrentSchemaName) + '.' + QUOTENAME(@CurrentObjectName) + ' ' + QUOTENAME(@CurrentStatisticsName)\n" +
"\n" +
"          IF @CurrentMaxDOP IS NOT NULL AND ((@Version >= 12.06024 AND @Version < 13) OR (@Version >= 13.05026 AND @Version < 14) OR @Version >= 14.030154)\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentUpdateStatisticsWithClauseArguments (Argument)\n" +
"            SELECT 'MAXDOP = ' + CAST(@CurrentMaxDOP AS nvarchar)\n" +
"          END\n" +
"\n" +
"          IF @CurrentStatisticsSample = 100\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentUpdateStatisticsWithClauseArguments (Argument)\n" +
"            SELECT 'FULLSCAN'\n" +
"          END\n" +
"\n" +
"          IF @CurrentStatisticsSample IS NOT NULL AND @CurrentStatisticsSample <> 100\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentUpdateStatisticsWithClauseArguments (Argument)\n" +
"            SELECT 'SAMPLE ' + CAST(@CurrentStatisticsSample AS nvarchar) + ' PERCENT'\n" +
"          END\n" +
"\n" +
"          IF @CurrentStatisticsResample = 'Y'\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentUpdateStatisticsWithClauseArguments (Argument)\n" +
"            SELECT 'RESAMPLE'\n" +
"          END\n" +
"\n" +
"          IF @CurrentNoRecompute = 1\n" +
"          BEGIN\n" +
"            INSERT INTO @CurrentUpdateStatisticsWithClauseArguments (Argument)\n" +
"            SELECT 'NORECOMPUTE'\n" +
"          END\n" +
"\n" +
"          IF EXISTS (SELECT * FROM @CurrentUpdateStatisticsWithClauseArguments)\n" +
"          BEGIN\n" +
"            SET @CurrentUpdateStatisticsWithClause = ' WITH'\n" +
"\n" +
"            WHILE (1 = 1)\n" +
"            BEGIN\n" +
"              SELECT TOP 1 @CurrentUpdateStatisticsArgumentID = ID,\n" +
"                           @CurrentUpdateStatisticsArgument = Argument\n" +
"              FROM @CurrentUpdateStatisticsWithClauseArguments\n" +
"              WHERE Added = 0\n" +
"              ORDER BY ID ASC\n" +
"\n" +
"              IF @@ROWCOUNT = 0\n" +
"              BEGIN\n" +
"                BREAK\n" +
"              END\n" +
"\n" +
"              SET @CurrentUpdateStatisticsWithClause = @CurrentUpdateStatisticsWithClause + ' ' + @CurrentUpdateStatisticsArgument + ','\n" +
"\n" +
"              UPDATE @CurrentUpdateStatisticsWithClauseArguments\n" +
"              SET Added = 1\n" +
"              WHERE [ID] = @CurrentUpdateStatisticsArgumentID\n" +
"            END\n" +
"\n" +
"            SET @CurrentUpdateStatisticsWithClause = LEFT(@CurrentUpdateStatisticsWithClause,LEN(@CurrentUpdateStatisticsWithClause) - 1)\n" +
"          END\n" +
"\n" +
"          IF @CurrentUpdateStatisticsWithClause IS NOT NULL SET @CurrentCommand += @CurrentUpdateStatisticsWithClause\n" +
"\n" +
"          IF @PartitionLevelStatistics = 1 AND @CurrentIsIncremental = 1 AND @CurrentPartitionNumber IS NOT NULL SET @CurrentCommand += ' ON PARTITIONS(' + CAST(@CurrentPartitionNumber AS nvarchar(max)) + ')'\n" +
"\n" +
"          EXECUTE @CurrentCommandOutput = dbo.CommandExecute @DatabaseContext = @CurrentDatabaseName, @Command = @CurrentCommand, @CommandType = @CurrentCommandType, @Mode = 2, @Comment = @CurrentComment, @DatabaseName = @CurrentDatabaseName, @SchemaName = @CurrentSchemaName, @ObjectName = @CurrentObjectName, @ObjectType = @CurrentObjectType, @IndexName = @CurrentIndexName, @IndexType = @CurrentIndexType, @StatisticsName = @CurrentStatisticsName, @ExtendedInfo = @CurrentExtendedInfo, @LockMessageSeverity = @LockMessageSeverity, @ExecuteAsUser = @ExecuteAsUser, @LogToTable = @LogToTable, @Execute = @Execute\n" +
"          SET @Error = @@ERROR\n" +
"          IF @Error <> 0 SET @CurrentCommandOutput = @Error\n" +
"          IF @CurrentCommandOutput <> 0 SET @ReturnCode = @CurrentCommandOutput\n" +
"        END\n" +
"\n" +
"        NoAction:\n";
    }
    
    public String part13(){
        return "        -- Update that the index or statistics is completed\n" +
"        UPDATE @tmpIndexesStatistics\n" +
"        SET Completed = 1\n" +
"        WHERE Selected = 1\n" +
"        AND Completed = 0\n" +
"        AND [Order] = @CurrentIxOrder\n" +
"        AND ID = @CurrentIxID\n" +
"\n" +
"        -- Clear variables\n" +
"        SET @CurrentDatabaseContext = NULL\n" +
"\n" +
"        SET @CurrentCommand = NULL\n" +
"        SET @CurrentCommandOutput = NULL\n" +
"        SET @CurrentCommandType = NULL\n" +
"        SET @CurrentComment = NULL\n" +
"        SET @CurrentExtendedInfo = NULL\n" +
"\n" +
"        SET @CurrentIxID = NULL\n" +
"        SET @CurrentIxOrder = NULL\n" +
"        SET @CurrentSchemaID = NULL\n" +
"        SET @CurrentSchemaName = NULL\n" +
"        SET @CurrentObjectID = NULL\n" +
"        SET @CurrentObjectName = NULL\n" +
"        SET @CurrentObjectType = NULL\n" +
"        SET @CurrentIsMemoryOptimized = NULL\n" +
"        SET @CurrentIndexID = NULL\n" +
"        SET @CurrentIndexName = NULL\n" +
"        SET @CurrentIndexType = NULL\n" +
"        SET @CurrentStatisticsID = NULL\n" +
"        SET @CurrentStatisticsName = NULL\n" +
"        SET @CurrentPartitionID = NULL\n" +
"        SET @CurrentPartitionNumber = NULL\n" +
"        SET @CurrentPartitionCount = NULL\n" +
"        SET @CurrentIsPartition = NULL\n" +
"        SET @CurrentIndexExists = NULL\n" +
"        SET @CurrentStatisticsExists = NULL\n" +
"        SET @CurrentIsImageText = NULL\n" +
"        SET @CurrentIsNewLOB = NULL\n" +
"        SET @CurrentIsFileStream = NULL\n" +
"        SET @CurrentIsColumnStore = NULL\n" +
"        SET @CurrentIsComputed = NULL\n" +
"        SET @CurrentIsTimestamp = NULL\n" +
"        SET @CurrentAllowPageLocks = NULL\n" +
"        SET @CurrentNoRecompute = NULL\n" +
"        SET @CurrentIsIncremental = NULL\n" +
"        SET @CurrentRowCount = NULL\n" +
"        SET @CurrentModificationCounter = NULL\n" +
"        SET @CurrentOnReadOnlyFileGroup = NULL\n" +
"        SET @CurrentResumableIndexOperation = NULL\n" +
"        SET @CurrentFragmentationLevel = NULL\n" +
"        SET @CurrentPageCount = NULL\n" +
"        SET @CurrentFragmentationGroup = NULL\n" +
"        SET @CurrentAction = NULL\n" +
"        SET @CurrentMaxDOP = NULL\n" +
"        SET @CurrentUpdateStatistics = NULL\n" +
"        SET @CurrentStatisticsSample = NULL\n" +
"        SET @CurrentStatisticsResample = NULL\n" +
"        SET @CurrentAlterIndexArgumentID = NULL\n" +
"        SET @CurrentAlterIndexArgument = NULL\n" +
"        SET @CurrentAlterIndexWithClause = NULL\n" +
"        SET @CurrentUpdateStatisticsArgumentID = NULL\n" +
"        SET @CurrentUpdateStatisticsArgument = NULL\n" +
"        SET @CurrentUpdateStatisticsWithClause = NULL\n" +
"\n" +
"        DELETE FROM @CurrentActionsAllowed\n" +
"        DELETE FROM @CurrentAlterIndexWithClauseArguments\n" +
"        DELETE FROM @CurrentUpdateStatisticsWithClauseArguments\n" +
"\n" +
"      END\n" +
"\n" +
"    END\n" +
"\n" +
"    IF @CurrentDatabaseState = 'SUSPECT'\n" +
"    BEGIN\n" +
"      SET @ErrorMessage = 'The database ' + QUOTENAME(@CurrentDatabaseName) + ' is in a SUSPECT state.'\n" +
"      RAISERROR('%s',16,1,@ErrorMessage) WITH NOWAIT\n" +
"      RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"      SET @Error = @@ERROR\n" +
"    END\n" +
"\n" +
"    -- Update that the database is completed\n" +
"    IF @DatabasesInParallel = 'Y'\n" +
"    BEGIN\n" +
"      UPDATE dbo.QueueDatabase\n" +
"      SET DatabaseEndTime = SYSDATETIME()\n" +
"      WHERE QueueID = @QueueID\n" +
"      AND DatabaseName = @CurrentDatabaseName\n" +
"    END\n" +
"    ELSE\n" +
"    BEGIN\n" +
"      UPDATE @tmpDatabases\n" +
"      SET Completed = 1\n" +
"      WHERE Selected = 1\n" +
"      AND Completed = 0\n" +
"      AND ID = @CurrentDBID\n" +
"    END\n" +
"\n" +
"    -- Clear variables\n" +
"    SET @CurrentDBID = NULL\n" +
"    SET @CurrentDatabaseName = NULL\n" +
"\n" +
"    SET @CurrentDatabase_sp_executesql = NULL\n" +
"\n" +
"    SET @CurrentExecuteAsUserExists = NULL\n" +
"    SET @CurrentUserAccess = NULL\n" +
"    SET @CurrentIsReadOnly = NULL\n" +
"    SET @CurrentDatabaseState = NULL\n" +
"    SET @CurrentInStandby = NULL\n" +
"    SET @CurrentRecoveryModel = NULL\n" +
"\n" +
"    SET @CurrentIsDatabaseAccessible = NULL\n" +
"    SET @CurrentReplicaID = NULL\n" +
"    SET @CurrentAvailabilityGroupID = NULL\n" +
"    SET @CurrentAvailabilityGroup = NULL\n" +
"    SET @CurrentAvailabilityGroupRole = NULL\n" +
"    SET @CurrentDatabaseMirroringRole = NULL\n" +
"\n" +
"    SET @CurrentCommand = NULL\n" +
"\n" +
"    DELETE FROM @tmpIndexesStatistics\n" +
"\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"  --// Log completing information                                                                 //--\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"  Logging:\n" +
"  SET @EndMessage = 'Date and time: ' + CONVERT(nvarchar,SYSDATETIME(),120)\n" +
"  RAISERROR('%s',10,1,@EndMessage) WITH NOWAIT\n" +
"\n" +
"  RAISERROR(@EmptyLine,10,1) WITH NOWAIT\n" +
"\n" +
"  IF @ReturnCode <> 0\n" +
"  BEGIN\n" +
"    RETURN @ReturnCode\n" +
"  END\n" +
"\n" +
"  ----------------------------------------------------------------------------------------------------\n" +
"\n" +
"END\n" +
"\n" +
"GO\n" +
"IF (SELECT [Value] FROM #Config WHERE Name = 'CreateJobs') = 'Y' AND SERVERPROPERTY('EngineEdition') NOT IN(4, 5) AND (IS_SRVROLEMEMBER('sysadmin') = 1 OR (DB_ID('rdsadmin') IS NOT NULL AND SUSER_SNAME(0x01) = 'rdsa')) AND (SELECT [compatibility_level] FROM sys.databases WHERE database_id = DB_ID()) >= 90\n" +
"BEGIN\n" +
"\n" +
"  DECLARE @BackupDirectory nvarchar(max)\n" +
"  DECLARE @CleanupTime int\n" +
"  DECLARE @OutputFileDirectory nvarchar(max)\n" +
"  DECLARE @LogToTable nvarchar(max)\n" +
"  DECLARE @DatabaseName nvarchar(max)\n" +
"\n" +
"  DECLARE @HostPlatform nvarchar(max)\n" +
"  DECLARE @DirectorySeparator nvarchar(max)\n" +
"  DECLARE @LogDirectory nvarchar(max)\n" +
"\n" +
"  DECLARE @TokenServer nvarchar(max)\n" +
"  DECLARE @TokenJobID nvarchar(max)\n" +
"  DECLARE @TokenJobName nvarchar(max)\n" +
"  DECLARE @TokenStepID nvarchar(max)\n" +
"  DECLARE @TokenStepName nvarchar(max)\n" +
"  DECLARE @TokenDate nvarchar(max)\n" +
"  DECLARE @TokenTime nvarchar(max)\n" +
"  DECLARE @TokenLogDirectory nvarchar(max)\n" +
"\n" +
"  DECLARE @JobDescription nvarchar(max)\n" +
"  DECLARE @JobCategory nvarchar(max)\n" +
"  DECLARE @JobOwner nvarchar(max)\n" +
"\n" +
"  DECLARE @Jobs TABLE (JobID int IDENTITY,\n" +
"                       [Name] nvarchar(max),\n" +
"                       CommandTSQL nvarchar(max),\n" +
"                       CommandCmdExec nvarchar(max),\n" +
"                       DatabaseName varchar(max),\n" +
"                       OutputFileNamePart01 nvarchar(max),\n" +
"                       OutputFileNamePart02 nvarchar(max),\n" +
"                       Selected bit DEFAULT 0,\n" +
"                       Completed bit DEFAULT 0)\n" +
"\n" +
"  DECLARE @CurrentJobID int\n" +
"  DECLARE @CurrentJobName nvarchar(max)\n" +
"  DECLARE @CurrentCommandTSQL nvarchar(max)\n" +
"  DECLARE @CurrentCommandCmdExec nvarchar(max)\n" +
"  DECLARE @CurrentDatabaseName nvarchar(max)\n" +
"  DECLARE @CurrentOutputFileNamePart01 nvarchar(max)\n" +
"  DECLARE @CurrentOutputFileNamePart02 nvarchar(max)\n" +
"\n" +
"  DECLARE @CurrentJobStepCommand nvarchar(max)\n" +
"  DECLARE @CurrentJobStepSubSystem nvarchar(max)\n" +
"  DECLARE @CurrentJobStepDatabaseName nvarchar(max)\n" +
"  DECLARE @CurrentOutputFileName nvarchar(max)\n" +
"\n" +
"  DECLARE @Version numeric(18,10) = CAST(LEFT(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max)),CHARINDEX('.',CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max))) - 1) + '.' + REPLACE(RIGHT(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max)), LEN(CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max))) - CHARINDEX('.',CAST(SERVERPROPERTY('ProductVersion') AS nvarchar(max)))),'.','') AS numeric(18,10))\n" +
"\n" +
"  DECLARE @AmazonRDS bit = CASE WHEN DB_ID('rdsadmin') IS NOT NULL AND SUSER_SNAME(0x01) = 'rdsa' THEN 1 ELSE 0 END\n" +
"\n" +
"  IF @Version >= 14\n" +
"  BEGIN\n" +
"    SELECT @HostPlatform = host_platform\n" +
"    FROM sys.dm_os_host_info\n" +
"  END\n" +
"  ELSE\n" +
"  BEGIN\n" +
"    SET @HostPlatform = 'Windows'\n" +
"  END\n" +
"\n" +
"  SELECT @DirectorySeparator = CASE\n" +
"  WHEN @HostPlatform = 'Windows' THEN '\\'\n" +
"  WHEN @HostPlatform = 'Linux' THEN '/'\n" +
"  END\n" +
"\n" +
"  SET @TokenServer = '$' + '(ESCAPE_SQUOTE(SRVR))'\n" +
"  SET @TokenJobID = '$' + '(ESCAPE_SQUOTE(JOBID))'\n" +
"  SET @TokenStepID = '$' + '(ESCAPE_SQUOTE(STEPID))'\n" +
"  SET @TokenDate = '$' + '(ESCAPE_SQUOTE(DATE))'\n" +
"  SET @TokenTime = '$' + '(ESCAPE_SQUOTE(TIME))'\n" +
"\n" +
"  IF @Version >= 13\n" +
"  BEGIN\n" +
"    SET @TokenJobName = '$' + '(ESCAPE_SQUOTE(JOBNAME))'\n" +
"    SET @TokenStepName = '$' + '(ESCAPE_SQUOTE(STEPNAME))'\n" +
"  END\n" +
"\n" +
"  IF @Version >= 12 AND @HostPlatform = 'Windows'\n" +
"  BEGIN\n" +
"    SET @TokenLogDirectory = '$' + '(ESCAPE_SQUOTE(SQLLOGDIR))'\n" +
"  END\n" +
"\n" +
"  SELECT @BackupDirectory = Value\n" +
"  FROM #Config\n" +
"  WHERE [Name] = 'BackupDirectory'\n" +
"\n" +
"  IF @HostPlatform = 'Windows'\n" +
"  BEGIN\n" +
"    SELECT @CleanupTime = Value\n" +
"    FROM #Config\n" +
"    WHERE [Name] = 'CleanupTime'\n" +
"  END\n" +
"\n" +
"  SELECT @OutputFileDirectory = Value\n" +
"  FROM #Config\n" +
"  WHERE [Name] = 'OutputFileDirectory'\n" +
"\n" +
"  SELECT @LogToTable = Value\n" +
"  FROM #Config\n" +
"  WHERE [Name] = 'LogToTable'\n" +
"\n" +
"  SELECT @DatabaseName = Value\n" +
"  FROM #Config\n" +
"  WHERE [Name] = 'DatabaseName'\n" +
"\n" +
"  IF @Version >= 11\n" +
"  BEGIN\n" +
"    SELECT @LogDirectory = [path]\n" +
"    FROM sys.dm_os_server_diagnostics_log_configurations\n" +
"  END\n" +
"  ELSE\n" +
"  BEGIN\n" +
"    SELECT @LogDirectory = LEFT(CAST(SERVERPROPERTY('ErrorLogFileName') AS nvarchar(max)),LEN(CAST(SERVERPROPERTY('ErrorLogFileName') AS nvarchar(max))) - CHARINDEX('\\',REVERSE(CAST(SERVERPROPERTY('ErrorLogFileName') AS nvarchar(max)))))\n" +
"  END\n" +
"\n" +
"  IF @OutputFileDirectory IS NOT NULL AND RIGHT(@OutputFileDirectory,1) = @DirectorySeparator\n" +
"  BEGIN\n" +
"    SET @OutputFileDirectory = LEFT(@OutputFileDirectory, LEN(@OutputFileDirectory) - 1)\n" +
"  END\n" +
"\n" +
"  IF @LogDirectory IS NOT NULL AND RIGHT(@LogDirectory,1) = @DirectorySeparator\n" +
"  BEGIN\n" +
"    SET @LogDirectory = LEFT(@LogDirectory, LEN(@LogDirectory) - 1)\n" +
"  END\n" +
"\n" +
"  SET @JobDescription = 'Source: https://ola.hallengren.com'\n" +
"  SET @JobCategory = 'Database Maintenance'\n" +
"\n" +
"  IF @AmazonRDS = 0\n" +
"  BEGIN\n" +
"    SET @JobOwner = SUSER_SNAME(0x01)\n" +
"  END\n" +
"\n" +
"  INSERT INTO @Jobs ([Name], CommandTSQL, DatabaseName, OutputFileNamePart01, OutputFileNamePart02)\n" +
"  SELECT 'DatabaseBackup - SYSTEM_DATABASES - FULL',\n" +
"         'EXECUTE [dbo].[DatabaseBackup]' + CHAR(13) + CHAR(10) + '@Databases = ''SYSTEM_DATABASES'',' + CHAR(13) + CHAR(10) + '@Directory = ' + ISNULL('N''' + REPLACE(@BackupDirectory,'''','''''') + '''','NULL') + ',' + CHAR(13) + CHAR(10) + '@BackupType = ''FULL'',' + CHAR(13) + CHAR(10) + '@Verify = ''Y'',' + CHAR(13) + CHAR(10) + '@CleanupTime = ' + ISNULL(CAST(@CleanupTime AS nvarchar),'NULL') + ',' + CHAR(13) + CHAR(10) + '@CheckSum = ''Y'',' + CHAR(13) + CHAR(10) + '@LogToTable = ''' + @LogToTable + '''',\n" +
"         @DatabaseName,\n" +
"         'DatabaseBackup',\n" +
"         'FULL'\n" +
"\n" +
"  INSERT INTO @Jobs ([Name], CommandTSQL, DatabaseName, OutputFileNamePart01, OutputFileNamePart02)\n" +
"  SELECT 'DatabaseBackup - USER_DATABASES - DIFF',\n" +
"         'EXECUTE [dbo].[DatabaseBackup]' + CHAR(13) + CHAR(10) + '@Databases = ''USER_DATABASES'',' + CHAR(13) + CHAR(10) + '@Directory = ' + ISNULL('N''' + REPLACE(@BackupDirectory,'''','''''') + '''','NULL') + ',' + CHAR(13) + CHAR(10) + '@BackupType = ''DIFF'',' + CHAR(13) + CHAR(10) + '@Verify = ''Y'',' + CHAR(13) + CHAR(10) + '@CleanupTime = ' + ISNULL(CAST(@CleanupTime AS nvarchar),'NULL') + ',' + CHAR(13) + CHAR(10) + '@CheckSum = ''Y'',' + CHAR(13) + CHAR(10) + '@LogToTable = ''' + @LogToTable + '''',\n" +
"          @DatabaseName,\n" +
"         'DatabaseBackup',\n" +
"         'DIFF'\n" +
"\n" +
"  INSERT INTO @Jobs ([Name], CommandTSQL, DatabaseName, OutputFileNamePart01, OutputFileNamePart02)\n" +
"  SELECT 'DatabaseBackup - USER_DATABASES - FULL',\n" +
"         'EXECUTE [dbo].[DatabaseBackup]' + CHAR(13) + CHAR(10) + '@Databases = ''USER_DATABASES'',' + CHAR(13) + CHAR(10) + '@Directory = ' + ISNULL('N''' + REPLACE(@BackupDirectory,'''','''''') + '''','NULL') + ',' + CHAR(13) + CHAR(10) + '@BackupType = ''FULL'',' + CHAR(13) + CHAR(10) + '@Verify = ''Y'',' + CHAR(13) + CHAR(10) + '@CleanupTime = ' + ISNULL(CAST(@CleanupTime AS nvarchar),'NULL') + ',' + CHAR(13) + CHAR(10) + '@CheckSum = ''Y'',' + CHAR(13) + CHAR(10) + '@LogToTable = ''' + @LogToTable + '''',\n" +
"         @DatabaseName,\n" +
"         'DatabaseBackup',\n" +
"         'FULL'\n" +
"\n" +
"  INSERT INTO @Jobs ([Name], CommandTSQL, DatabaseName, OutputFileNamePart01, OutputFileNamePart02)\n" +
"  SELECT 'DatabaseBackup - USER_DATABASES - LOG',\n" +
"         'EXECUTE [dbo].[DatabaseBackup]' + CHAR(13) + CHAR(10) + '@Databases = ''USER_DATABASES'',' + CHAR(13) + CHAR(10) + '@Directory = ' + ISNULL('N''' + REPLACE(@BackupDirectory,'''','''''') + '''','NULL') + ',' + CHAR(13) + CHAR(10) + '@BackupType = ''LOG'',' + CHAR(13) + CHAR(10) + '@Verify = ''Y'',' + CHAR(13) + CHAR(10) + '@CleanupTime = ' + ISNULL(CAST(@CleanupTime AS nvarchar),'NULL') + ',' + CHAR(13) + CHAR(10) + '@CheckSum = ''Y'',' + CHAR(13) + CHAR(10) + '@LogToTable = ''' + @LogToTable + '''',\n" +
"         @DatabaseName,\n" +
"         'DatabaseBackup',\n" +
"         'LOG'\n" +
"\n" +
"  INSERT INTO @Jobs ([Name], CommandTSQL, DatabaseName, OutputFileNamePart01)\n" +
"  SELECT 'DatabaseIntegrityCheck - SYSTEM_DATABASES',\n" +
"         'EXECUTE [dbo].[DatabaseIntegrityCheck]' + CHAR(13) + CHAR(10) + '@Databases = ''SYSTEM_DATABASES'',' + CHAR(13) + CHAR(10) + '@LogToTable = ''' + @LogToTable + '''',\n" +
"         @DatabaseName,\n" +
"         'DatabaseIntegrityCheck'\n" +
"\n" +
"  INSERT INTO @Jobs ([Name], CommandTSQL, DatabaseName, OutputFileNamePart01)\n" +
"  SELECT 'DatabaseIntegrityCheck - USER_DATABASES',\n" +
"         'EXECUTE [dbo].[DatabaseIntegrityCheck]' + CHAR(13) + CHAR(10) + '@Databases = ''USER_DATABASES' + CASE WHEN @AmazonRDS = 1 THEN ', -rdsadmin' ELSE '' END + ''',' + CHAR(13) + CHAR(10) + '@LogToTable = ''' + @LogToTable + '''',\n" +
"         @DatabaseName,\n" +
"         'DatabaseIntegrityCheck'\n" +
"\n" +
"  INSERT INTO @Jobs ([Name], CommandTSQL, DatabaseName, OutputFileNamePart01)\n" +
"  SELECT 'IndexOptimize - USER_DATABASES',\n" +
"         'EXECUTE [dbo].[IndexOptimize]' + CHAR(13) + CHAR(10) + '@Databases = ''USER_DATABASES'',' + CHAR(13) + CHAR(10) + '@LogToTable = ''' + @LogToTable + '''',\n" +
"         @DatabaseName,\n" +
"         'IndexOptimize'\n" +
"\n" +
"  INSERT INTO @Jobs ([Name], CommandTSQL, DatabaseName, OutputFileNamePart01)\n" +
"  SELECT 'sp_delete_backuphistory',\n" +
"         'DECLARE @CleanupDate datetime' + CHAR(13) + CHAR(10) + 'SET @CleanupDate = DATEADD(dd,-30,GETDATE())' + CHAR(13) + CHAR(10) + 'EXECUTE dbo.sp_delete_backuphistory @oldest_date = @CleanupDate',\n" +
"         'msdb',\n" +
"         'sp_delete_backuphistory'\n" +
"\n" +
"  INSERT INTO @Jobs ([Name], CommandTSQL, DatabaseName, OutputFileNamePart01)\n" +
"  SELECT 'sp_purge_jobhistory',\n" +
"         'DECLARE @CleanupDate datetime' + CHAR(13) + CHAR(10) + 'SET @CleanupDate = DATEADD(dd,-30,GETDATE())' + CHAR(13) + CHAR(10) + 'EXECUTE dbo.sp_purge_jobhistory @oldest_date = @CleanupDate',\n" +
"         'msdb',\n" +
"         'sp_purge_jobhistory'\n" +
"\n" +
"  INSERT INTO @Jobs ([Name], CommandTSQL, DatabaseName, OutputFileNamePart01)\n" +
"  SELECT 'CommandLog Cleanup',\n" +
"         'DELETE FROM [dbo].[CommandLog]' + CHAR(13) + CHAR(10) + 'WHERE StartTime < DATEADD(dd,-30,GETDATE())',\n" +
"         @DatabaseName,\n" +
"         'CommandLogCleanup'\n" +
"\n" +
"  INSERT INTO @Jobs ([Name], CommandCmdExec, OutputFileNamePart01)\n" +
"  SELECT 'Output File Cleanup',\n" +
"         'cmd /q /c \"For /F \"tokens=1 delims=\" %v In (''ForFiles /P \"' + COALESCE(@OutputFileDirectory,@TokenLogDirectory,@LogDirectory) + '\" /m *_*_*_*.txt /d -30 2^>^&1'') do if EXIST \"' + COALESCE(@OutputFileDirectory,@TokenLogDirectory,@LogDirectory) + '\"\\%v echo del \"' + COALESCE(@OutputFileDirectory,@TokenLogDirectory,@LogDirectory) + '\"\\%v& del \"' + COALESCE(@OutputFileDirectory,@TokenLogDirectory,@LogDirectory) + '\"\\%v\"',\n" +
"         'OutputFileCleanup'\n" +
"\n" +
"  IF @AmazonRDS = 1\n" +
"  BEGIN\n" +
"   UPDATE @Jobs\n" +
"   SET Selected = 1\n" +
"   WHERE [Name] IN('DatabaseIntegrityCheck - USER_DATABASES','IndexOptimize - USER_DATABASES','CommandLog Cleanup')\n" +
"  END\n" +
"  ELSE IF SERVERPROPERTY('EngineEdition') = 8\n" +
"  BEGIN\n" +
"   UPDATE @Jobs\n" +
"   SET Selected = 1\n" +
"   WHERE [Name] IN('DatabaseIntegrityCheck - SYSTEM_DATABASES','DatabaseIntegrityCheck - USER_DATABASES','IndexOptimize - USER_DATABASES','CommandLog Cleanup','sp_delete_backuphistory','sp_purge_jobhistory')\n" +
"  END\n" +
"  ELSE IF @HostPlatform = 'Windows'\n" +
"  BEGIN\n" +
"   UPDATE @Jobs\n" +
"   SET Selected = 1\n" +
"  END\n" +
"  ELSE IF @HostPlatform = 'Linux'\n" +
"  BEGIN\n" +
"   UPDATE @Jobs\n" +
"   SET Selected = 1\n" +
"   WHERE CommandTSQL IS NOT NULL\n" +
"  END\n" +
"\n" +
"  WHILE EXISTS (SELECT * FROM @Jobs WHERE Completed = 0 AND Selected = 1)\n" +
"  BEGIN\n" +
"    SELECT @CurrentJobID = JobID,\n" +
"           @CurrentJobName = [Name],\n" +
"           @CurrentCommandTSQL = CommandTSQL,\n" +
"           @CurrentCommandCmdExec = CommandCmdExec,\n" +
"           @CurrentDatabaseName = DatabaseName,\n" +
"           @CurrentOutputFileNamePart01 = OutputFileNamePart01,\n" +
"           @CurrentOutputFileNamePart02 = OutputFileNamePart02\n" +
"    FROM @Jobs\n" +
"    WHERE Completed = 0\n" +
"    AND Selected = 1\n" +
"    ORDER BY JobID ASC\n" +
"\n" +
"    IF @CurrentCommandTSQL IS NOT NULL AND @AmazonRDS = 1\n" +
"    BEGIN\n" +
"      SET @CurrentJobStepSubSystem = 'TSQL'\n" +
"      SET @CurrentJobStepCommand = @CurrentCommandTSQL\n" +
"      SET @CurrentJobStepDatabaseName = @CurrentDatabaseName\n" +
"    END\n" +
"    ELSE IF @CurrentCommandTSQL IS NOT NULL AND SERVERPROPERTY('EngineEdition') = 8\n" +
"    BEGIN\n" +
"      SET @CurrentJobStepSubSystem = 'TSQL'\n" +
"      SET @CurrentJobStepCommand = @CurrentCommandTSQL\n" +
"      SET @CurrentJobStepDatabaseName = @CurrentDatabaseName\n" +
"    END\n" +
"    ELSE IF @CurrentCommandTSQL IS NOT NULL AND @HostPlatform = 'Linux'\n" +
"    BEGIN\n" +
"      SET @CurrentJobStepSubSystem = 'TSQL'\n" +
"      SET @CurrentJobStepCommand = @CurrentCommandTSQL\n" +
"      SET @CurrentJobStepDatabaseName = @CurrentDatabaseName\n" +
"    END\n" +
"    ELSE IF @CurrentCommandTSQL IS NOT NULL AND @HostPlatform = 'Windows' AND @Version >= 11\n" +
"    BEGIN\n" +
"      SET @CurrentJobStepSubSystem = 'TSQL'\n" +
"      SET @CurrentJobStepCommand = @CurrentCommandTSQL\n" +
"      SET @CurrentJobStepDatabaseName = @CurrentDatabaseName\n" +
"    END\n" +
"    ELSE IF @CurrentCommandTSQL IS NOT NULL AND @HostPlatform = 'Windows' AND @Version < 11\n" +
"    BEGIN\n" +
"      SET @CurrentJobStepSubSystem = 'CMDEXEC'\n" +
"      SET @CurrentJobStepCommand = 'sqlcmd -E -S ' + @TokenServer + ' -d ' + @CurrentDatabaseName + ' -Q \"' + REPLACE(@CurrentCommandTSQL,(CHAR(13) + CHAR(10)),' ') + '\" -b'\n" +
"      SET @CurrentJobStepDatabaseName = NULL\n" +
"    END\n" +
"    ELSE IF @CurrentCommandCmdExec IS NOT NULL AND @HostPlatform = 'Windows'\n" +
"    BEGIN\n" +
"      SET @CurrentJobStepSubSystem = 'CMDEXEC'\n" +
"      SET @CurrentJobStepCommand = @CurrentCommandCmdExec\n" +
"      SET @CurrentJobStepDatabaseName = NULL\n" +
"    END\n" +
"\n" +
"    IF @AmazonRDS = 0 AND SERVERPROPERTY('EngineEdition') <> 8\n" +
"    BEGIN\n" +
"      SET @CurrentOutputFileName = COALESCE(@OutputFileDirectory,@TokenLogDirectory,@LogDirectory) + @DirectorySeparator + ISNULL(CASE WHEN @TokenJobName IS NULL THEN @CurrentOutputFileNamePart01 END + '_','') + ISNULL(CASE WHEN @TokenJobName IS NULL THEN @CurrentOutputFileNamePart02 END + '_','') + ISNULL(@TokenJobName,@TokenJobID) + '_' + @TokenStepID + '_' + @TokenDate + '_' + @TokenTime + '.txt'\n" +
"      IF LEN(@CurrentOutputFileName) > 200 SET @CurrentOutputFileName = COALESCE(@OutputFileDirectory,@TokenLogDirectory,@LogDirectory) + @DirectorySeparator + ISNULL(CASE WHEN @TokenJobName IS NULL THEN @CurrentOutputFileNamePart01 END + '_','') + ISNULL(@TokenJobName,@TokenJobID) + '_' + @TokenStepID + '_' + @TokenDate + '_' + @TokenTime + '.txt'\n" +
"      IF LEN(@CurrentOutputFileName) > 200 SET @CurrentOutputFileName = COALESCE(@OutputFileDirectory,@TokenLogDirectory,@LogDirectory) + @DirectorySeparator + ISNULL(@TokenJobName,@TokenJobID) + '_' + @TokenStepID + '_' + @TokenDate + '_' + @TokenTime + '.txt'\n" +
"      IF LEN(@CurrentOutputFileName) > 200 SET @CurrentOutputFileName = NULL\n" +
"    END\n" +
"\n" +
"    IF @CurrentJobStepSubSystem IS NOT NULL AND @CurrentJobStepCommand IS NOT NULL AND NOT EXISTS (SELECT * FROM msdb.dbo.sysjobs WHERE [name] = @CurrentJobName)\n" +
"    BEGIN\n" +
"      EXECUTE msdb.dbo.sp_add_job @job_name = @CurrentJobName, @description = @JobDescription, @category_name = @JobCategory, @owner_login_name = @JobOwner\n" +
"      EXECUTE msdb.dbo.sp_add_jobstep @job_name = @CurrentJobName, @step_name = @CurrentJobName, @subsystem = @CurrentJobStepSubSystem, @command = @CurrentJobStepCommand, @output_file_name = @CurrentOutputFileName, @database_name = @CurrentJobStepDatabaseName\n" +
"      EXECUTE msdb.dbo.sp_add_jobserver @job_name = @CurrentJobName\n" +
"    END\n" +
"\n" +
"    UPDATE Jobs\n" +
"    SET Completed = 1\n" +
"    FROM @Jobs Jobs\n" +
"    WHERE JobID = @CurrentJobID\n" +
"\n" +
"    SET @CurrentJobID = NULL\n" +
"    SET @CurrentJobName = NULL\n" +
"    SET @CurrentCommandTSQL = NULL\n" +
"    SET @CurrentCommandCmdExec = NULL\n" +
"    SET @CurrentDatabaseName = NULL\n" +
"    SET @CurrentOutputFileNamePart01 = NULL\n" +
"    SET @CurrentOutputFileNamePart02 = NULL\n" +
"    SET @CurrentJobStepCommand = NULL\n" +
"    SET @CurrentJobStepSubSystem = NULL\n" +
"    SET @CurrentJobStepDatabaseName = NULL\n" +
"    SET @CurrentOutputFileName = NULL\n" +
"\n" +
"  END\n" +
"\n" +
"END\n" +
"GO\n" +
"";
    }
    
}
