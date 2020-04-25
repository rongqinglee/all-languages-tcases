//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2020, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.tcases.openapi.moco;

import org.cornutum.tcases.io.IndentedWriter;
import org.cornutum.tcases.openapi.moco.MocoServerConfigPojo.PojoWriter;
import org.cornutum.tcases.openapi.testwriter.JavaTestTarget;
import org.cornutum.tcases.openapi.testwriter.TestSource;
import org.cornutum.tcases.openapi.testwriter.TestWriterTest;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import static org.cornutum.hamcrest.ExpectedFailure.expectFailure;

import java.io.File;

/**
 * Runs tests for {@link RestServerTestWriter}.
 */
public class MocoServerTestWriterTest extends TestWriterTest
  {
  /**
   * Tests {@link MocoServerTestWriter#writeTest writeTest()} using the following inputs.
   * <P>
   * <TABLE border="1" cellpadding="8">
   * <TR align="left"><TH colspan=2> 0. writeTest (Success) </TH></TR>
   * <TR align="left"><TH> Input Choice </TH> <TH> Value </TH></TR>
   * <TR><TD> ServerType </TD> <TD> Rest </TD> </TR>
   * <TR><TD> Config.Name </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Config.Port </TD> <TD> Default </TD> </TR>
   * <TR><TD> Config.EachTest </TD> <TD> Yes </TD> </TR>
   * <TR><TD> Config.Source.Type </TD> <TD> JSON </TD> </TR>
   * <TR><TD> Config.Source.Content </TD> <TD> File </TD> </TR>
   * <TR><TD> Certificate.Name </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Source </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Passwords.KeyStore </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Passwords.Cert </TD> <TD> (not applicable) </TD> </TR>
   * </TABLE>
   * </P>
   */
  @Test
  public void writeTest_0() throws Exception
    {
    // Given...
    String testDefName = "RequestTestDef0";
    
    TestSource source =
      TestSource.from( requestTestDefFor( testDefName))
      .build();
    
    JavaTestTarget target =
      JavaTestTarget.builder()
      .named( testDefName)
      .inDir( getGeneratedTestDir())
      .build();

    MocoServerConfig config =
      MocoServerConfig.file( new File( getResourceDir(), "myConfig.json"))
      .forEachTest()
      .build();
    
    MocoServerTestWriter testWriter = new RestServerTestWriter( config, new MockTestCaseWriter());
    
    // When...
    testWriter.writeTest( source, target);

    // Then
    File outFile = new File( getGeneratedTestDir(), testDefName + "Test.java");
    String outFileResults = FileUtils.readFileToString( outFile, "UTF-8");
    verifyTest( testDefName, outFileResults);
    }

  /**
   * Tests {@link MocoServerTestWriter#writeTest writeTest()} using the following inputs.
   * <P>
   * <TABLE border="1" cellpadding="8">
   * <TR align="left"><TH colspan=2> 1. writeTest (Success) </TH></TR>
   * <TR align="left"><TH> Input Choice </TH> <TH> Value </TH></TR>
   * <TR><TD> ServerType </TD> <TD> Socket </TD> </TR>
   * <TR><TD> Config.Name </TD> <TD> Defined </TD> </TR>
   * <TR><TD> Config.Port </TD> <TD> Defined </TD> </TR>
   * <TR><TD> Config.EachTest </TD> <TD> No </TD> </TR>
   * <TR><TD> Config.Source.Type </TD> <TD> Pojo </TD> </TR>
   * <TR><TD> Config.Source.Content </TD> <TD> None </TD> </TR>
   * <TR><TD> Certificate.Name </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Source </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Passwords.KeyStore </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Passwords.Cert </TD> <TD> (not applicable) </TD> </TR>
   * </TABLE>
   * </P>
   */
  @Test
  public void writeTest_1() throws Exception
    {
    // Given...
    String testDefName = "RequestTestDef1";
    
    TestSource source =
      TestSource.from( requestTestDefFor( testDefName))
      .build();
    
    JavaTestTarget target =
      JavaTestTarget.builder()
      .named( testDefName)
      .inDir( getGeneratedTestDir())
      .build();

    MocoServerConfig config =
      MocoServerConfig.pojo()
      .name( "myServer")
      .port( 56789)
      .forEachTest( false)
      .build();
    
    MocoServerTestWriter testWriter = new SocketServerTestWriter( config, new MockTestCaseWriter());
    
    // When...
    testWriter.writeTest( source, target);

    // Then
    File outFile = new File( getGeneratedTestDir(), testDefName + "Test.java");
    String outFileResults = FileUtils.readFileToString( outFile, "UTF-8");
    verifyTest( testDefName, outFileResults);
    }

  /**
   * Tests {@link MocoServerTestWriter#writeTest writeTest()} using the following inputs.
   * <P>
   * <TABLE border="1" cellpadding="8">
   * <TR align="left"><TH colspan=2> 2. writeTest (Success) </TH></TR>
   * <TR align="left"><TH> Input Choice </TH> <TH> Value </TH></TR>
   * <TR><TD> ServerType </TD> <TD> Https </TD> </TR>
   * <TR><TD> Config.Name </TD> <TD> Default </TD> </TR>
   * <TR><TD> Config.Port </TD> <TD> Default </TD> </TR>
   * <TR><TD> Config.EachTest </TD> <TD> Default </TD> </TR>
   * <TR><TD> Config.Source.Type </TD> <TD> Pojo </TD> </TR>
   * <TR><TD> Config.Source.Content </TD> <TD> Pojo </TD> </TR>
   * <TR><TD> Certificate.Name </TD> <TD> Default </TD> </TR>
   * <TR><TD> Certificate.Source </TD> <TD> File </TD> </TR>
   * <TR><TD> Certificate.Passwords.KeyStore </TD> <TD> Defined </TD> </TR>
   * <TR><TD> Certificate.Passwords.Cert </TD> <TD> Defined </TD> </TR>
   * </TABLE>
   * </P>
   */
  @Test
  public void writeTest_2() throws Exception
    {
    // Given...
    String testDefName = "RequestTestDef2";
    
    TestSource source =
      TestSource.from( requestTestDefFor( testDefName))
      .build();
    
    JavaTestTarget target =
      JavaTestTarget.builder()
      .named( testDefName)
      .inDir( getGeneratedTestDir())
      .build();

    MocoServerConfig config =
      MocoServerConfig.pojo(
        new PojoWriter()
          {
          public void writePojo( String serverName, IndentedWriter targetWriter)
            {
            targetWriter.println( String.format( "%s.response( \"Your request has been received.\");", serverName));
            }
          })
      .build();

    CertConfig certConfig = new CertConfigFile( new File( getResourceDir(), "myCert.cks"), "kss!", "css!");
    
    MocoServerTestWriter testWriter = new HttpsServerTestWriter( config, certConfig, new MockTestCaseWriter());
    
    // When...
    testWriter.writeTest( source, target);

    // Then
    File outFile = new File( getGeneratedTestDir(), testDefName + "Test.java");
    String outFileResults = FileUtils.readFileToString( outFile, "UTF-8");
    verifyTest( testDefName, outFileResults);
    }

  /**
   * Tests {@link MocoServerTestWriter#writeTest writeTest()} using the following inputs.
   * <P>
   * <TABLE border="1" cellpadding="8">
   * <TR align="left"><TH colspan=2> 3. writeTest (Success) </TH></TR>
   * <TR align="left"><TH> Input Choice </TH> <TH> Value </TH></TR>
   * <TR><TD> ServerType </TD> <TD> Http </TD> </TR>
   * <TR><TD> Config.Name </TD> <TD> Defined </TD> </TR>
   * <TR><TD> Config.Port </TD> <TD> Defined </TD> </TR>
   * <TR><TD> Config.EachTest </TD> <TD> Yes </TD> </TR>
   * <TR><TD> Config.Source.Type </TD> <TD> Pojo </TD> </TR>
   * <TR><TD> Config.Source.Content </TD> <TD> None </TD> </TR>
   * <TR><TD> Certificate.Name </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Source </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Passwords.KeyStore </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Passwords.Cert </TD> <TD> (not applicable) </TD> </TR>
   * </TABLE>
   * </P>
   */
  @Test
  public void writeTest_3() throws Exception
    {
    // Given...
    String testDefName = "RequestTestDef3";
    
    TestSource source =
      TestSource.from( requestTestDefFor( testDefName))
      .build();
    
    JavaTestTarget target =
      JavaTestTarget.builder()
      .named( testDefName)
      .inDir( getGeneratedTestDir())
      .build();

    MocoServerConfig config =
      MocoServerConfig.pojo()
      .name( "myServer")
      .port( 321)
      .forEachTest()
      .build();
    
    MocoServerTestWriter testWriter = new HttpServerTestWriter( config, new MockTestCaseWriter());
    
    // When...
    testWriter.writeTest( source, target);

    // Then
    File outFile = new File( getGeneratedTestDir(), testDefName + "Test.java");
    String outFileResults = FileUtils.readFileToString( outFile, "UTF-8");
    verifyTest( testDefName, outFileResults);
    }

  /**
   * Tests {@link MocoServerTestWriter#writeTest writeTest()} using the following inputs.
   * <P>
   * <TABLE border="1" cellpadding="8">
   * <TR align="left"><TH colspan=2> 4. writeTest (Success) </TH></TR>
   * <TR align="left"><TH> Input Choice </TH> <TH> Value </TH></TR>
   * <TR><TD> ServerType </TD> <TD> Socket </TD> </TR>
   * <TR><TD> Config.Name </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Config.Port </TD> <TD> Default </TD> </TR>
   * <TR><TD> Config.EachTest </TD> <TD> No </TD> </TR>
   * <TR><TD> Config.Source.Type </TD> <TD> JSON </TD> </TR>
   * <TR><TD> Config.Source.Content </TD> <TD> Resource </TD> </TR>
   * <TR><TD> Certificate.Name </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Source </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Passwords.KeyStore </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Passwords.Cert </TD> <TD> (not applicable) </TD> </TR>
   * </TABLE>
   * </P>
   */
  @Test
  public void writeTest_4() throws Exception
    {
    // Given...
    String testDefName = "RequestTestDef4";
    
    TestSource source =
      TestSource.from( requestTestDefFor( testDefName))
      .build();
    
    JavaTestTarget target =
      JavaTestTarget.builder()
      .named( testDefName)
      .inDir( getGeneratedTestDir())
      .build();

    MocoServerConfig config =
      MocoServerConfig.resource( "mocoConfig.json")
      .name( "myServer")
      .forEachTest( false)
      .build();
    
    MocoServerTestWriter testWriter = new SocketServerTestWriter( config, new MockTestCaseWriter());
    
    // When...
    testWriter.writeTest( source, target);

    // Then
    File outFile = new File( getGeneratedTestDir(), testDefName + "Test.java");
    String outFileResults = FileUtils.readFileToString( outFile, "UTF-8");
    verifyTest( testDefName, outFileResults);
    }

  /**
   * Tests {@link MocoServerTestWriter#writeTest writeTest()} using the following inputs.
   * <P>
   * <TABLE border="1" cellpadding="8">
   * <TR align="left"><TH colspan=2> 5. writeTest (Success) </TH></TR>
   * <TR align="left"><TH> Input Choice </TH> <TH> Value </TH></TR>
   * <TR><TD> ServerType </TD> <TD> Https </TD> </TR>
   * <TR><TD> Config.Name </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Config.Port </TD> <TD> Defined </TD> </TR>
   * <TR><TD> Config.EachTest </TD> <TD> Default </TD> </TR>
   * <TR><TD> Config.Source.Type </TD> <TD> JSON </TD> </TR>
   * <TR><TD> Config.Source.Content </TD> <TD> File </TD> </TR>
   * <TR><TD> Certificate.Name </TD> <TD> Defined </TD> </TR>
   * <TR><TD> Certificate.Source </TD> <TD> Resource </TD> </TR>
   * <TR><TD> Certificate.Passwords.KeyStore </TD> <TD> Defined </TD> </TR>
   * <TR><TD> Certificate.Passwords.Cert </TD> <TD> Defined </TD> </TR>
   * </TABLE>
   * </P>
   */
  @Test
  public void writeTest_5() throws Exception
    {
    // Given...
    String testDefName = "RequestTestDef5";
    
    TestSource source =
      TestSource.from( requestTestDefFor( testDefName))
      .build();
    
    JavaTestTarget target =
      JavaTestTarget.builder()
      .named( testDefName)
      .inDir( getGeneratedTestDir())
      .build();

    MocoServerConfig config =
      MocoServerConfig.file( "mocoConfig.json")
      .port( 7777)
      .build();

    CertConfig certConfig = new CertConfigResource( "myCertificate", "myCert.cks", "kss!", "css!");
    
    MocoServerTestWriter testWriter = new HttpsServerTestWriter( config, certConfig, new MockTestCaseWriter());
    
    // When...
    testWriter.writeTest( source, target);

    // Then
    File outFile = new File( getGeneratedTestDir(), testDefName + "Test.java");
    String outFileResults = FileUtils.readFileToString( outFile, "UTF-8");
    verifyTest( testDefName, outFileResults);
    }

  /**
   * Tests {@link MocoServerTestWriter#writeTest writeTest()} using the following inputs.
   * <P>
   * <TABLE border="1" cellpadding="8">
   * <TR align="left"><TH colspan=2> 6. writeTest (Success) </TH></TR>
   * <TR align="left"><TH> Input Choice </TH> <TH> Value </TH></TR>
   * <TR><TD> ServerType </TD> <TD> Rest </TD> </TR>
   * <TR><TD> Config.Name </TD> <TD> Default </TD> </TR>
   * <TR><TD> Config.Port </TD> <TD> Default </TD> </TR>
   * <TR><TD> Config.EachTest </TD> <TD> Yes </TD> </TR>
   * <TR><TD> Config.Source.Type </TD> <TD> Pojo </TD> </TR>
   * <TR><TD> Config.Source.Content </TD> <TD> Pojo </TD> </TR>
   * <TR><TD> Certificate.Name </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Source </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Passwords.KeyStore </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Passwords.Cert </TD> <TD> (not applicable) </TD> </TR>
   * </TABLE>
   * </P>
   */
  @Test
  public void writeTest_6() throws Exception
    {
    // Given...
    String testDefName = "RequestTestDef6";
    
    TestSource source =
      TestSource.from( requestTestDefFor( testDefName))
      .build();
    
    JavaTestTarget target =
      JavaTestTarget.builder()
      .named( testDefName)
      .inDir( getGeneratedTestDir())
      .build();

    MocoServerConfig config =
      MocoServerConfig.pojo(
        new PojoWriter()
          {
          public void writePojo( String serverName, IndentedWriter targetWriter)
            {
            targetWriter.println( String.format( "%s.response( \"Your request has been ignored.\");", serverName));
            }
          })
      .forEachTest()
      .build();
    
    MocoServerTestWriter testWriter = new RestServerTestWriter( config, new MockTestCaseWriter());
    
    // When...
    testWriter.writeTest( source, target);

    // Then
    File outFile = new File( getGeneratedTestDir(), testDefName + "Test.java");
    String outFileResults = FileUtils.readFileToString( outFile, "UTF-8");
    verifyTest( testDefName, outFileResults);
    }

  /**
   * Tests {@link MocoServerTestWriter#writeTest writeTest()} using the following inputs.
   * <P>
   * <TABLE border="1" cellpadding="8">
   * <TR align="left"><TH colspan=2> 7. writeTest (Success) </TH></TR>
   * <TR align="left"><TH> Input Choice </TH> <TH> Value </TH></TR>
   * <TR><TD> ServerType </TD> <TD> Http </TD> </TR>
   * <TR><TD> Config.Name </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Config.Port </TD> <TD> Defined </TD> </TR>
   * <TR><TD> Config.EachTest </TD> <TD> No </TD> </TR>
   * <TR><TD> Config.Source.Type </TD> <TD> JSON </TD> </TR>
   * <TR><TD> Config.Source.Content </TD> <TD> Resource </TD> </TR>
   * <TR><TD> Certificate.Name </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Source </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Passwords.KeyStore </TD> <TD> (not applicable) </TD> </TR>
   * <TR><TD> Certificate.Passwords.Cert </TD> <TD> (not applicable) </TD> </TR>
   * </TABLE>
   * </P>
   */
  @Test
  public void writeTest_7() throws Exception
    {
    // Given...
    String testDefName = "RequestTestDef7";
    
    TestSource source =
      TestSource.from( requestTestDefFor( testDefName))
      .build();
    
    JavaTestTarget target =
      JavaTestTarget.builder()
      .named( testDefName)
      .inDir( getGeneratedTestDir())
      .build();

    MocoServerConfig config =
      MocoServerConfig.resource( "configs/httpConfig.json")
      .port( 321)
      .forEachTest( false)
      .build();
    
    MocoServerTestWriter testWriter = new HttpServerTestWriter( config, new MockTestCaseWriter());
    
    // When...
    testWriter.writeTest( source, target);

    // Then
    File outFile = new File( getGeneratedTestDir(), testDefName + "Test.java");
    String outFileResults = FileUtils.readFileToString( outFile, "UTF-8");
    verifyTest( testDefName, outFileResults);
    }

  @Test
  public void whenNoKeyStorePassword()
    {
    expectFailure( IllegalArgumentException.class)
      .when( () -> new CertConfigFile( "myCert.cks", null, "css!"));
    }

  @Test
  public void whenNoCertPassword()
    {
    expectFailure( IllegalArgumentException.class)
      .when( () -> new CertConfigFile( "myCert.cks", "kss!", null));
    }
  }
