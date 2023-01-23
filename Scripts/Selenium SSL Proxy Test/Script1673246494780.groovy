import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

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

WebUI.navigateToUrl('https://local.foobar3000.com:8443/server-app/data')

WebUI.verifyTextPresent('Hello from Server-App-data method', false)

println("Done")

