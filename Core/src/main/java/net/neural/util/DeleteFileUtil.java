package net.zfp.util;

import java.io.File;     


public class DeleteFileUtil{     
    public  boolean delete(String fileName){     
        File file = new File(fileName);     
        if(!file.exists()){     
            System.out.println("Delete file failure:"+fileName+" does not exist");     
            return false;     
        }else{     
            if(file.isFile()){     
                     
                return deleteFile(fileName);     
            }else{     
                return deleteDirectory(fileName);     
            }     
        }     
    }     
         
    public  boolean deleteFile(String fileName){     
        File file = new File(fileName);     
        if(file.isFile() && file.exists()){     
            file.delete();     
            System.out.println("delete one file "+fileName+"success!");     
            return true;     
        }else{     
            System.out.println("delete one file "+fileName+"failure !");     
            return false;     
        }     
    }     
    public  boolean deleteDirectory(String dir){     
        if(!dir.endsWith(File.separator)){     
            dir = dir+File.separator;     
        }     
        File dirFile = new File(dir);     
        if(!dirFile.exists() || !dirFile.isDirectory()){     
            System.out.println(" delete directory "+dir+"does not exist!");     
            return false;     
        }     
        boolean flag = true;     
        File[] files = dirFile.listFiles();     
        for(int i=0;i<files.length;i++){     
            if(files[i].isFile()){     
                flag = deleteFile(files[i].getAbsolutePath());     
                if(!flag){     
                    break;     
                }     
            }     
            else{     
                flag = deleteDirectory(files[i].getAbsolutePath());     
                if(!flag){     
                    break;     
                }     
            }     
        }     
        if(!flag){     
            System.out.println("failure ");     
            return false;     
        }     
             
        if(dirFile.delete()){     
            System.out.println("elete directory"+dir+"ok");     
            return true;     
        }else{     
            System.out.println("elete directory"+dir+"failure");     
            return false;     
        }     
    }
    
//    public static void main(String[] args) {     
//        String fileDir = "C:/temp/portal.jrxml";   
//        DeleteFileUtil.delete(fileDir);     
//             
//    }     
    
}  