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
public class FullBackup {
    public String script(){
        return "USE master\n"
                + "GO\n"
                + "EXECUTE dbo.DatabaseBackup\n"
                + "@Databases = 'Rehabiliter_Database',\n"
                + "@Directory = '"+System.getProperty("user.home")+"/documents/Rehabiliter/Backups/Local',\n"
                + "@BackupType = 'FULL',\n"
                + "@Verify = 'Y',\n"
                + "@CheckSum = 'Y',\n"
                + "@CleanupTime = 24\n";
    }
}
