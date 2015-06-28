/*******************************************************************************
 * Copyright 2011 Danny Kunz
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.omnaest.i18nbinder.grouping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class FileGrouperTest
{
  
  @Before
  public void setUp() throws Exception
  {
  }
  
  // https://github.com/uli-heller/i18n-binder/issues/2
  // On windows, C: is a root directory, so the canonical path of C:\\TEMP is C:\TEMP.
  // However, if you try this on linux, the result is quite different!
  // The obvious way of handling this is something related to FileSystem.getRootDirectories().
  // But since this is a Java7 method, we cannot use it.
  private String getBase() throws Exception {
     String TST="C:\\temp";
     String base = "";
     if (!TST.equals(new File("C:\\temp").getCanonicalPath())) {
        base =new File(".").getCanonicalPath() + File.separator;
     }
     return base;
  }
  
  @Test
  public void testDetermineFileGroupIdentifierToFileGroupMap() throws Exception
  {
    //
    String base = getBase();
    List<File> fileList = new ArrayList<File>();
    fileList.add( new File( "C:\\temp\\admin_de_DE.properties" ) );
    fileList.add( new File( "C:\\temp\\admin_en_US.properties" ) );
    fileList.add( new File( "C:\\temp\\article_de_DE.properties" ) );
    fileList.add( new File( "C:\\temp\\article_en_US.properties" ) );
    fileList.add( new File( "C:\\temp\\article_de.properties" ) );
    fileList.add( new File( "C:\\temp\\article_en.properties" ) );
    
    //
    FileGrouper fileGrouper = new FileGrouper();
    try
    {
      fileGrouper.addAllFiles( fileList );
      fileGrouper.setGroupingPatternReplacementToken( "{locale}" );
      fileGrouper.setGroupingPatternString( "(.*?_(\\w{2,3}_\\w{2,3}|\\w{2,3})|.*())\\.\\w*" );
      fileGrouper.setGroupingPatternGroupingGroupIndexList( Arrays.asList( 2, 3 ) );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      Assert.fail();
    }
    
    //
    Map<String, FileGroup> fileGroupIdentifierToFileGroupMap = fileGrouper.determineFileGroupIdentifierToFileGroupMap();
    
    //
    assertEquals( 2, fileGroupIdentifierToFileGroupMap.size() );
    Set<String> fileGroupIdentifierSet = fileGroupIdentifierToFileGroupMap.keySet();
    assertTrue( fileGroupIdentifierSet.contains( base + "C:\\temp\\admin_{locale}.properties" ) );
    assertTrue( fileGroupIdentifierSet.contains( base + "C:\\temp\\article_{locale}.properties" ) );
    
    //
    {
      //
      FileGroup fileGroup = fileGroupIdentifierToFileGroupMap.get( base+"C:\\temp\\admin_{locale}.properties" );
      
      //
      Map<String, File> groupTokenToFileMap = fileGroup.getGroupTokenToFileMap();
      assertEquals( 2, groupTokenToFileMap.size() );
      assertTrue( groupTokenToFileMap.containsKey( "de_DE" ) );
      assertTrue( groupTokenToFileMap.containsKey( "en_US" ) );
    }
    {
      //
      FileGroup fileGroup = fileGroupIdentifierToFileGroupMap.get( base+"C:\\temp\\article_{locale}.properties" );
      
      //
      Map<String, File> groupTokenToFileMap = fileGroup.getGroupTokenToFileMap();
      assertEquals( 4, groupTokenToFileMap.size() );
      assertTrue( groupTokenToFileMap.containsKey( "de_DE" ) );
      assertTrue( groupTokenToFileMap.containsKey( "en_US" ) );
      assertTrue( groupTokenToFileMap.containsKey( "de" ) );
      assertTrue( groupTokenToFileMap.containsKey( "en" ) );
    }
  }
  
}
