/*
 * Copyright 2013 Michael Mackenzie High
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mackenziehigh.snowflake.designer.io;

import com.mackenziehigh.snowflake.NewlineStyles;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * An instance of this class represents a source-code file. 
 * 
 * @author Michael Mackenzie High
 */
public final class StringFile
{
    
    /**
     * This is the file-path that the source-code file must be written to.
     * This path may be relative. 
     */
    private File filepath;
    
    /**
     * This is the source-code that the file does/will contain. 
     */
    private String sourceCode;

    public File getFilepath() 
    {
        return filepath;
    }

    public void setFilepath(File filepath)
    {
        this.filepath = filepath;
    }

    public String getSourceCode()
    {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode)
    {
        this.sourceCode = sourceCode;
    }  
    
    public static void writeZipFile(final File file, 
                                    final List<StringFile> files) throws FileNotFoundException, 
                                                                         IOException
    {
        FileOutputStream fos = new FileOutputStream(file);
        
        ZipOutputStream zos = new ZipOutputStream(fos);
        
        for(StringFile source : files)
        {
            ZipEntry entry = new ZipEntry(source.filepath.getPath());
            zos.putNextEntry(entry);
            zos.write(source.sourceCode.getBytes());
        }
        
        zos.close();
        fos.close();
    }
    
    public static List<StringFile> readZipFile(final File file) throws FileNotFoundException, 
                                                                       IOException
    {
        final List<StringFile> files = new LinkedList<StringFile>();
        
        FileInputStream fos = new FileInputStream(file);
        
        ZipInputStream zos = new ZipInputStream(fos);
        
        ZipEntry entry;
        
        while((entry = zos.getNextEntry()) != null)
        {
            File filepath = new File(entry.getName());
            
            StringBuilder content = new StringBuilder();
            
            Scanner scanner = new Scanner(zos);
            
            String newline = NewlineStyles.fromSystem().newline();
            
            while(scanner.hasNextLine()) 
            { 
                content.append(scanner.nextLine()); 
                
                content.append(newline);
            }
            
            StringFile stringFile = new StringFile();
            
            stringFile.filepath = filepath;
            
            stringFile.sourceCode = content.toString();
            
            files.add(stringFile);
        }
        
        zos.close();
        fos.close();
        
        return files;
    }
    
}
