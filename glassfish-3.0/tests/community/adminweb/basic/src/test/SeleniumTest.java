/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;
import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.server.SeleniumServer;

public class SeleniumTest {
    
    private DefaultSelenium selenium;
    
    //@Parameters({"selen-svr-addr","brwsr-path","aut-addr"})
    @BeforeClass
    private void init() throws Exception {
        System.out.println("Starting Selenium Launcher--->");
        selenium = new DefaultSelenium("localhost",
                SeleniumServer.getDefaultPort(), "*iexplore", "http://localhost:4848");
        
        
        
    /* selenium = new DefaultSelenium(selenSrvrAddr,
             SeleniumServer.getDefaultPort(), bpath, appPath);*/
        
        
     /*selClient = new DefaultSelenium("localhost", 4444,
            "*firefox", "http://localhost:" + PORT);*/
        
        selenium.start();
    }
    
    @Test
    public void test1(){
        Assert.assertTrue(true,"Pass");
    }
    
    @Test
    @Parameters({"aut-addr"})
    public void testLoginScreen() throws Exception {
        try{
        System.out.println("Running testSelenium--->");
        selenium.open("http://localhost:4848");
        selenium.open("/");
        /*selenium.type("sf", "glassfish");
        selenium.click("btnG");
        selenium.waitForPageToLoad("30000");
        selenium.click("//a/b[2]");*/
        selenium.waitForPageToLoad("30000");
        //verifyTrue(selenium.isTextPresent("JRuby"));
        //verifyEquals("glassfish: GlassFish Community", selenium.getTitle());
        
        
        //Assert.assertEquals("", selenium.getText("//img[@alt='GlassFish V2']"));
        
        selenium.open("/login.jsf");
        selenium.waitForPageToLoad("30000");
        //selenium.type("Login.username", "admin");
        selenium.type("j_username", "admin");        
        //selenium.waitForPageToLoad("30000");
        //selenium.type("Login.password", "adminadmin");
        selenium.type("j_password", "adminadmin");
        //selenium.waitForPageToLoad("3000");
        selenium.click("loginButton");
        //selenium.waitForPageToLoad("30000");
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
        
        
    }
    
    @AfterClass
    private void stop() throws Exception {
        selenium.stop();
    }    
    
    
}
