package com.github.troydm.elite.cmdr

/**
 * TempFileManager class - temporary file manager class
 * Created by troydm on 6/20/2016.
 */
@Singleton
class TempFileManager {

    File dir

    void createDir(){
        dir =  File.createTempFile("elite-cmdr","")
        dir.delete()
        dir.mkdir()
    }

    List<File> tempFiles = Collections.synchronizedList(new ArrayList<>())

    public synchronized File getTempDir(){
        if(dir == null)
            createDir()
        return dir
    }

    public File newTempFile(){
        return newTempFile(UUID.randomUUID().toString())
    }

    public File newTempFile(String name){
        File f = new File(getTempDir(),name)
        tempFiles.add(f)
        return f
    }

    public void deleteAllTempFiles(){
        for(File f : tempFiles)
            f.delete()
        getTempDir().deleteDir()
    }
}
