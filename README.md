# Katalon Studio In-process Proxy/SSL Client Cert Demo

This project demonstrates how to use an in-process proxy server with Katalon Studio to attach SSL client certificates to requests when required for testing.

# Requirements

* Katalon Studio 8.5.5 or later
* Java 13 or later [^1]
* Application configured for authentication via SSL client certificate
  * The test case in this demo is based on this simple Java application: 

    <https://github.com/katalon-studio-samples/sample-2way-ssl-server>

* URL mapped to the application above [^2]

# Setting up the default profile

This demonstration expects several global variables to be set in the default profile. These variables are used to configure the proxy server and the test. The following variables are required:

<img width="876" alt="Defaults" src="https://user-images.githubusercontent.com/1128/214202689-4a446b8e-b384-42ed-9587-62eed6eb133d.png">

# Running the demo

You can view the demonstration by following these steps:

1. Install and configure the prerequisites listed above.

2. Start the Java server:

        java -jar target/sample-2way-ssl-server-0.0.1-SNAPSHOT.jar

3. Start Katalon Studio
4. Open this project
5. Open the test suite "2-Way SSL Test Suite"
6. Run the test suite with Edge Chromium (this is set as the default)

# Applying in your own project

To apply this demonstration to your own project, follow these steps:

1. Copy the script from the test suite "2-Way SSL Test Suite" to your own test suite.
2. Copy the code from the test case "Selenium SSL Proxy Test" to your own test case:

        // Copy the following code into test cases to use the embedded proxy for 2-way SSL authentication

        import org.openqa.selenium.edge.EdgeOptions as EdgeOptions
        import org.openqa.selenium.edge.EdgeDriver as EdgeDriver
        import org.openqa.selenium.remote.CapabilityType as CapabilityType
        import org.openqa.selenium.Proxy as Proxy
        import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

        Proxy seleniumProxy = GlobalVariable.proxy

        EdgeOptions options = new EdgeOptions()

        // Uncomment the following line to have the browser accept self-signed certificates
        // options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true)

        options.setProxy(seleniumProxy)

        String edgeChromiumDriverPath = DriverFactory.getEdgeChromiumDriverPath()
        System.setProperty('webdriver.edge.driver', edgeChromiumDriverPath)

        // start the browser up
        driver = new EdgeDriver(options)

        DriverFactory.changeWebDriver(driver)

        // End of proxy preamble. Standard test script code begins here.
        
3. Create the necessary variables in your profile:
      1. proxy (type: Null, value: null)
      2. client_cert_path (type: String, value: <path to client certificate file>)
      3. client_cert_password_encrypted (type: String, value: <encrypted password for client certificate>)

# How to encrypt the client certificate password

The client certificate password is encrypted using the Katalon Studio encryption utility. To encrypt the password, follow these steps:

1. Select "Encrypt Text" from the Help menu in Katalon Studio
2. Enter the plaintext password in the "Raw Text" field
3. Click "Copy and Close"
4. Paste the encrypted password into the "client_cert_password_encrypted" variable in your profile

<img width="400" alt="Encrypt Text dialog" src="https://user-images.githubusercontent.com/1128/214202537-43b81524-b20b-4b4c-a2dc-ce0da9a3d9eb.png">

# How to execute from command line

For execution with Katalon Runtime Engine (KRE), follow these steps:

1. Set KATALON_JAVA_HOME to the path to the Java installation
2. Run KRE as follows:

        ./katalonc -noSplash  -runMode=console -projectPath="<your path to this project>/cs-mitm-demo/selenium-extension-mitm.prj" -retry=0 -testSuitePath="Test Suites/2-Way SSL Test Suite" -apiKey=<your API key> -browserType="Edge Chromium" --config -webui.autoUpdateDrivers=true

## Overriding the project defaults from the command line

You can override the values for the client certificate path and password from the command line. To do so, use the following syntax:

        ./katalonc -noSplash  -runMode=console -projectPath="<your path to this project>/cs-mitm-demo/selenium-extension-mitm.prj" -retry=0 -testSuitePath="Test Suites/2-Way SSL Test Suite" -apiKey=<your API key> -browserType="Edge Chromium" --config -webui.autoUpdateDrivers=true -g_client_cert_path=<path to client certificate file> -g_client_cert_password_plain=<plaintext password for client certificate>

# How it works

This demonstration works by instantiating a [BrowserUp Proxy]() server in the setUp method of a Katalon Studio test suite. It then configures the selected browser driver to use that proxy to connect to the designated application URL. The proxy intercepts any HTTPS connections to the configured URL and attaches the configured client certificate and sends the request on to the application at the URL.

[^1]:Katalon Studio 8.5.5 embeds the Java 8 JRE. However, there is a bug related to the Netty library used by the proxy and Java 8 that is resolved in Java 13 and later.
[^2]:The URL used in this demo is https://local.foobar3000.com:8443. You can map the URL of your choosing by modifying your operating system appropriately.
