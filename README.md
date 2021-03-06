![Alt text](./carina_logo.png "Carina Logo")

Carina Automation Framework
==================
Carina is a Java-based test automation framework that unites all testing layers: Mobile applications (web, native, hybrid), WEB applications, REST services, Databases.

* Carina framework is built on top of most popular open-source solutions like Selenium, Appium, TestNG allowing to reduce dependence on specific technology stack.

* Carina supports all popular browsers (IE, Safari, Chrome, Firefox) and mobile devices (iOS/Android). Special feature for mobile domain: it reuses test automation code between IOS/Android up to 70-80%.

* As far as our framework is built in Java, it is cross-platform. Tests may be easily executed both on Unix or Windows OS. All you need is JDK 8 installed.

* Framework supports different types of databases - both relational and nonrelational (MySQL, SQL Server, Oracle, PostgreSQL), providing amazing experience of DAO layer implementation using MyBatis ORM framework.

* API testing is based on Freemarker template engine. It enables great flexibility in generating REST requests and responses are dynamically changed by incoming arguments. 

![Alt text](./carina.svg "Carina Overview")

<B>[TRY DEMO PROJECT NOW](https://github.com/qaprosoft/carina-demo)</B>


## Сontents
* [Initial setup](#initial-setup)
* [Project structure](#project-structure)
* [Configuration and execution](#configuration-and-execution)
* [Web UI automation](#web-ui-automation)
* [Web services API automation](#web-services-api-automation)
* [Mobile automation](#mobile-automation)
* [Database access setup](#db-access)
* [Data-providers usage](#data-providers)
* [Git configuration](#git-configuration)
* [License](#license)


## Initial setup
* Install and configure JDK 1.8+
* Install and configure [Apache Maven 3.5.2+](http://maven.apache.org/)
* Download and start the latest [Selenium standalone server](http://www.seleniumhq.org/download/)
* Download the latest version of [Eclipse](http://www.eclipse.org/downloads/) and install [TestNG plugin](http://testng.org/doc/download.html)

### Generating project
The easiest way to initialize new project is to use Carina archetype, you will get correct project structure along with test samples:
```
mvn archetype:generate -DarchetypeGroupId=com.qaprosoft \
                       -DarchetypeArtifactId=carina-archetype \
                       -DarchetypeVersion=1.0 \
                       -DgroupId=<your_groupId> \ 
                       -DartifactId=<your_artifactId> \ 
                       -Dname="<you_proj_name>" \
                       -Durl=<your_proj_url> \
                       -Dversion=<your_proj_version>
```
If any attribute contains spaces, it should be set in quotes (e.g.: -Dname="Hello World"). In above Maven command you have to specify 5 attributes while the first 3 should be left unchanged. Let's go through these attributes:
<table> 
	<tr>
		<th>Attribute</th>
		<th>Meaning</th>
		<th>Example</th>
	</tr>
	<tr>
		<td>-DgroupId</td>
		<td>Company domain in reverce order</td>
		<td>com.qaprosoft</td>
	</tr>
	<tr>
		<td>-DartifactId</td>
		<td>Java project name</td>
		<td>carina-qa</td>
	</tr>
	<tr>
		<td>-Dname</td>
		<td>Name with more details</td>
		<td>"Carina Test Automation"</td>
	</tr>
	<tr>
		<td>-Durl</td>
		<td>Company URL</td>
		<td>http://qaprosoft.com</td>
	</tr>
	<tr>
		<td>-Dversion</td>
		<td>Project version</td>
		<td>1.0</td>
	</tr>
</table>

### Import to Eclipse
If generation is successfully complete, you would see a new project folder with a name equal to the artifactId attribute specified during generation, so navigate to that folder (where pom.xml is located) and execute the following Maven task:
```
mvn clean eclipse:eclipse
```
By executing this command, Maven should resolve all dependencies, downloading required libraries to your local repository and generating Eclipse classpath. Before importing new project to Eclipse, you should link your IDE to your Maven repository by executing the following task:
```
mvn -Dworkspace=<path_to_workspace> eclipse:configure-workspace
```
Here you have to specify the absolute path to the Eclipse workspace. After that, restart Eclipse IDE. Now you can import generated projects such as "Existing Java Project" into Eclipse IDE.
Generate Eclipse workspace using command:
```
mvn clean eclipse:eclipse
```
Now you are ready to import project into Eclipse.

![Eclipse view](https://github.com/qaprosoft/carina-demo/blob/gh-pages/img/001-Initial-setup.png?raw=true)


## Project structure
Carina test project is structured as a standard Maven project:
```
carina-demo
|-- pom.xml
|-- src/test/java        
|-- src/test/resources
    |-- api
    |-- testng_suites
    |-- xls
|-- src/main/java
|-- src/main/resources
    |-- l18n
```

* **src/test/java** - contains test classes organized using TestNG annotations

![src/test/java](https://raw.githubusercontent.com/qaprosoft/carina-demo/gh-pages/img/002-Project-structure.png)

* **src/test/resources** - contains TestNG xml files, API templates and XLS data providers

![src/test/resources](https://raw.githubusercontent.com/qaprosoft/carina-demo/gh-pages/img/003-Project-structure.png)

* **src/main/java** - contains page object classes, API domains and additional utilities

![src/main/java](https://raw.githubusercontent.com/qaprosoft/carina-demo/gh-pages/img/004-Project-structure.png)

* **src/main/resources** - contains l18n bundles, configuration properties files and MyBastis profiles if needed

![src/main/resources](https://raw.githubusercontent.com/qaprosoft/carina-demo/gh-pages/img/005-Project-structure.png)


## Configuration and execution
There are multiple properties files located in src/main/resources:
* **_api.properties** - API test endpoints reference
* **_config.properties** - global test configuration
* **_database.properties** - database connection properties
* **_email.properties** - emailable reports config
* **_testdata.properties** - test user credentials

All properties may be retrieved in test using R class:
```
R.API.get("GetUserMethods")
R.CONFIG.get("browser")
R.DATABASE.get("db.url")
R.EMAIL.get("title")
R.TESTDATA.get("user.email")
```
Default config properties can be obtained by 
```
Configuration.get(Parameter.EXTRA_CAPABILITIES)
```

All project configuration properties are located in **_config.properties** file. In the table below we are providing description for most of the parameters:
<table> 
    <col width="20">
    <col width="20">
    <col width="20">
    <col width="20"> 
	<tbody>
	<tr>
		<th>Attribute</th>
		<th>Meaning</th>
                <th>Default value</th>
		<th>Example</th>
	</tr>
	<tr>
		<td>url</td>
		<td>Base application URL</td>
		<td>{must_override}</td>
		<td>http://qaprosoft.com</td>
	</tr>
	<tr>
		<td>browser</td>
		<td>Browser for testing</td>
		<td>chrome</td>
		<td>chrome / firefox / safari / iexplore</td>
	</tr>
	<tr>
		<td>selenium_host</td>
		<td>Selenium server host</td>
		<td>{must_override}</td>
		<td>http://localhost:4444/wd/hub</td>
	</tr>
	<tr>
		<td>app_version</td>
		<td>Application version/build number for reporting</td>
		<td>n/a</td>
		<td>1.2.5</td>
	</tr>
	<tr>
		<td>locale</td>
		<td>Locale for using L10N feature. Enabled when enable_l10n=true</td>
		<td>en_US</td>
		<td>en_GB,de_DE,fr_FR</td>
	</tr>
	<tr>
		<td>language</td>
		<td>Language for i18n defature. Enabled when enable_i18n=true</td>
		<td>en_US</td>
		<td>en_GB,de_DE,fr_FR</td>
	</tr>
	<tr>
		<td>implicit_timeout</td>
		<td>Implicit timeout in seconds to wait for element</td>
		<td>10</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>retry_interval</td>
		<td>Timeout interval between calling HTML DOM for the element.<br><b>Note:</b> in ms. For mobile automation specify number from 500-1500 range</td>
		<td>2</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>auto_screenshot</td>
		<td>Global switch for taking screenshots. When disabled only failures will be captured</td>
		<td>true</td>
		<td>Boolean</td>
	</tr>
	<tr>
		<td>keep_all_screenshots</td>
		<td>Keep screenshots artifacts even for passed tests.</td>
		<td>false</td>
		<td></td>
	</tr>
	<tr>
		<td>report_url</td>
		<td>Direct HTTP link to Jenkins workspace report folder. Automatically specified by CI</td>
		<td>n/a</td>
		<td>http://localhost:8888/job/my_project/1/eTAF_Report</td>
	</tr>
	<tr>
		<td>max_screen_history</td>
		<td>Max number of reports in history</td>
		<td>10</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>jira_url</td>
		<td>JIRA base URL for direct links with bugs description</td>
		<td>n/a</td>
		<td>https://jira.carina.com/browse/</td>
	</tr>
	<tr>
		<td>email_list</td>
		<td>Comma-separated list of emails for reports</td>
		<td>{must_override}</td>
		<td>u1@gmail.com,u2@gmail.com</td>
	</tr>
	<tr>
		<td>sender_email</td>
		<td>Email account for reports sending. <br><b>Note:</b> Gmail smtp settings are used by default. Update _email.properties to use your own SMTP server</td>
		<td>{must_override}</td>
		<td>carina.qareport@qaprosoft.com</td>
	</tr>
	<tr>
		<td>sender_pswd</td>
		<td>Email password for reports sending</td>
		<td>{must_override}</td>
		<td>pwd123</td>
	</tr>
	<tr>
		<td>env</td>
		<td>Environment specific configuration. More about this [feature](#environment-specific-configuration)</td>
		<td>n/a</td>
		<td>STAG, PROD, DEMO</td>
	</tr>
	<tr>
		<td>env_arg_resolver</td>
		<td>This parametr is optional, if it isn't set default value will be used. In most cases <b>default value is enough</b></td>
		<td>com.qaprosoft.carina.core.foundation.utils.DefaultEnvArgResolver</td>
		<td>java class </td>
	</tr>
		<tr>
		<td>platform</td>
		<td>Platform version for Selenium Grid</td>
		<td>*</td>
		<td>ANDROID,IOS,WINDOWS,MAC,LINUX</td>
	</tr>
		<tr>
		<td>browser_version</td>
		<td>The browser version, or the empty string if unknown for Selenium Grid</td>
		<td>n/a</td>
		<td>"8.0", "52.1"</td>
	</tr>
		<tr>
		<td>driver_mode</td>
		<td>Rule for defining WebDriver lifecycle.</td>
		<td>method_mode</td>
		<td>method_mode / class_mode / suite_mode</td>
	</tr>
		<tr>
		<td>driver_mode</td>
		<td>Rule for defining WebDriver lifecycle.</td>
		<td>method_mode</td>
		<td>method_mode / class_mode / suite_mode</td>
	</tr>
		<tr>
		<td>driver_event_listeners</td>
		<td>Comma-separated list of listeners. Listeners provide more logs from WebDriver and have to be instances of WebDriverEventListener</td>
		<td>n/a</td>
		<td>com.someCompane.core.EventListener1,com.someCompane.core.EventListener2</td>
	</tr>
		<tr>
		<td>max_driver_count</td>
		<td>Max number of drivers per thread</td>
		<td>3</td>
		<td>Integer</td>
	</tr>
		<tr>
		<td>custom_capabilities</td>
		<td>Name of propertie file with custom capabilities(key-value)</td>
		<td>n/a</td>
		<td>custom.propertie</td>
	</tr>
		<tr>
		<td>proxy_host</td>
		<td>The hostname of the server</td>
		<td>n/a</td>
		<td>host.example.com</td>
	</tr>
		<tr>
		<td>proxy_port</td>
		<td>The port number</td>
		<td>n/a</td>
		<td>80</td>
	</tr>
		<tr>
		<td>proxy_protocols</td>
		<td>Comma-separated list of internet protocols used to carry connection information from the source requesting the connection to the destination for which the connection was requested.</td>
		<td>http,https,ftp</td>
		<td>http, https, ftp, socks</td>
	</tr>
		<tr>
		<td>browsermob_proxy</td>
		<td>Boolean parameter which enable or disable set up of mobile proxy</td>
		<td>false</td>
		<td>true, false</td>
	</tr>
		<tr>
		<td>browsermob_port</td>
		<td>The port number for mobile browser (make sense only for local debugging)</td>
		<td>0</td>
		<td>Integer</td>
	</tr>
		<tr>
		<td>proxy_set_to_system</td>
		<td>Boolean parameter which enable or disable set up of proxy</td>
		<td>true</td>
		<td>true, false</td>
	</tr>
		<tr>
		<td>failure_email_list</td>
		<td>Comma-separated list of emails for failure reports</td>
		<td>n/a</td>
		<td>u1@mail.com,u2@mail.com</td>
	</tr>
		<tr>
		<td>track_known_issues</td>
		<td>Boolean parameter. If it is true and some Jira tickets assosiated with test in case of failure Jira info will be added to report</td>
		<td>false</td>
		<td>true,false</td>
	</tr>
	<tr>
		<td>smart_screenshot</td>
		<td>This parameter currently avaliable only for Chrome browser. Enable taking screenshots with metadata</td>
		<td>false</td>
		<td>true,false</td>
	</tr>
	<tr>
		<td>smart_screenshot</td>
		<td>This parameter currently avaliable only for Chrome browser. Enable taking screenshots with metadata</td>
		<td>false</td>
		<td>true,false</td>
	</tr>
	<tr>
		<td>explicit_timeout</td>
		<td>Timeout in seconds to wait for a certain condition to occur before proceeding further in the code</td>
		<td>20</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>auto_download</td>
		<td>Enabled parametr prevent download dialog and download file automatically. Feature currently avaliable for Chrome and FireFox</td>
		<td>false</td>
		<td>false, true</td>
	</tr>
	<tr>
		<td>auto_download_apps</td>
		<td>MIME types / Internet Media Types. Parameter is necessary only for configure auto downloading for FireFox</td>
		<td>NULL</td>
		<td>application/pdf, video/x-msvideo, list of [values](https://freeformatter.com/mime-types-list.html)</td>
	</tr>
	<tr>
		<td>project_report_directory</td>
		<td>Path to folder where reports will be saved</td>
		<td>../reports/qa</td>
		<td>./reports/qa</td>
	</tr>
	<tr>
		<td>big_screen_width</td>
		<td>Sreenshots will be resized according this width if there own width is bigger</td>
		<td>-1</td>
		<td>500, 1200, Integer</td>
	</tr>
	<tr>
		<td>big_screen_height</td>
		<td>Sreenshots will be resized according this height if there own height is bigger</td>
		<td>-1</td>
		<td>500, 1200, Integer</td>
	</tr>
		<tr>
		<td>small_screen_width</td>
		<td>Thumbnails width</td>
		<td>90</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>big_screen_height</td>
		<td>Thumbnails height</td>
		<td>90</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>big_screen_height</td>
		<td>Thumbnails height</td>
		<td>90</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>init_retry_count</td>
		<td>Number of attempts to create driver.  Default value 0 means that it would be only 1 attempt</td>
		<td>0</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>init_retry_interval</td>
		<td>Interval is seconds between attempts to create driver</td>
		<td>1</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>retry_count</td>
		<td>Number of test-retrying in case of failure.  Default value 0 means that test would be performed only once</td>
		<td>0</td>
		<td>Integer</td>
	</tr>
		<tr>
		<td>enable_l10n</td>
		<td>Enable L10N feature</td>
		<td>true</td>
		<td>false, true</td>
	</tr>
			<tr>
		<td>l10n_encoding</td>
		<td>Charset for l10n feature</td>
		<td>ISO-8859-1</td>
		<td>ISO-8859-5, ISO-8859-6</td>
	</tr>
	<tr>
		<td>enable_i18n</td>
		<td>Enable i18n feature</td>
		<td>true</td>
		<td>false, true</td>
	</tr>
		<tr>
		<td>thread_count</td>
		<td>Default number of threads to use when running tests in parallel.</td>
		<td>1</td>
		<td>fInteger</td>
	</tr>
		<tr>
		<td>data_provider_thread_count</td>
		<td>Default number of threads to use for data providers when running tests in parallel.</td>
		<td>1</td>
		<td>Integer</td>
	</tr>
		<tr>
		<td>core_log_level</td>
		<td>Level for Carina logging</td>
		<td>INFO</td>
		<td>ALL, DEBUG, ERROR, WARN, FATAL, INFO, OFF, TRACE </td>
	</tr>
		<tr>
		<td>log_all_json</td>
		<td>API response will be logged in JSON format</td>
		<td>true</td>
		<td>true, false</td>
	</tr>
		<tr>
		<td>date_format</td>
		<td>Date format for DateUtils.class</td>
		<td>HH:mm:ss yyyy-MM-dd</td>
		<td>HH:mm:ss dd/MM/yyyy, HH:mm MM/dd/yyyy</td>
	</tr>
		<tr>
		<td>time_format</td>
		<td>Date format for DateUtils.class</td>
		<td>HH:mm:ss</td>
		<td>HH:mm:ss.SSS, HH:mm a zzz</td>
	</tr>
		<tr>
		<td>crypto_key_path</td>
		<td>Path to file with crypto key</td>
		<td>./src/main/resources/crypto.key</td>
		<td>./crypto</td>
	</tr>
		<tr>
		<td>suite_name</td>
		<td>Suite name for report and TestRail. If this parameter is NULL will be get from TestNG xml (parameter suite name) or _email.properties (title)</td>
		<td>NULL</td>
		<td>Advanced Acceptance</td>
	</tr>
	<tr>
		<td>jira_updater</td>
		<td>Class contains logic to update Jira. <b>Note</b> Custom updater have to implement DefaultJiraUpdater, by default methods do nothing</td>
		<td>com.qaprosoft.
		carina.core.foundation.
		jira.DefaultJiraUpdater</td>
		<td>Custom class</td>
	</tr>
	<tr>
		<td>jira_url</td>
		<td>Url to Jira</td>
		<td>NULL</td>
		<td>https://yourclass.atlassian.net</td>
	</tr>
		<tr>
		<td>jira_user</td>
		<td>Jira user email</td>
		<td>NULL</td>
		<td>admin@yourcompany.com</td>
	</tr>
		<tr>
		<td>jira_password</td>
		<td>Jira user password</td>
		<td>NULL</td>
		<td>admin123456</td>
	</tr>
		<tr>
		<td>jira_suite_id</td>
		<td>Jira suit id (if you have one)</td>
		<td>NULL</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>jira_project</td>
		<td>Jira project id</td>
		<td>NULL</td>
		<td>Integer</td>
	</tr>
		<tr>
		<td>jira_create_new_ticket</td>
		<td>If feature is enabled after test failure new jira ticket will be created</td>
		<td>false</td>
		<td>true, false</td>
	</tr>
	<tr>
		<td>video_recording</td>
		<td>If feature is test will be recorded</td>
		<td>false</td>
		<td>true, false</td>
	</tr>	
	<tr>
		<td>testrail_url</td>
		<td>Url to TestRail</td>
		<td>NULL</td>
		<td>https:/yourcompany.testrail.net</td>
	</tr>
	<tr>
		<td>testrail_user</td>
		<td>TestRail user email</td>
		<td>NULL</td>
		<td>admin@yourcompany.com</td>
	</tr>
		<tr>
		<td>testrail_updater</td>
		<td>Class contains logic to update TestRail. <b>Note</b> Custom updater have to implement EmptyTestRailUpdater, by default methods do nothing</td>
		<td>com.qaprosoft.carina.core.foundation.report.testrail.EmptyTestRailUpdater</td>
		<td>Custom class</td>
	</tr>
		<tr>
		<td>testrail_milestone</td>
		<td>Milestone to set on TestRail for run</td>
		<td>NULL</td>
		<td>some-milestone</td>
	</tr>
		<tr>
		<td>testrail_assignee</td>
		<td>User asserneed for the suit</td>
		<td>NULL</td>
		<td>asignee_user@yuorcompany.com</td>
	</tr>
		<tr>
		<td>s3_bucket_name</td>
		<td>Bucket name on S3 Amazon from which you suppose to download artifacts</td>
		<td>NULL</td>
		<td>some bucket</td>
	</tr>
		<tr>
		<td>access_key_id</td>
		<td>Acces key id for Amamzon S3. More info [here](#https://docs.aws.amazon.com/general/latest/gr/aws-sec-cred-types.html#access-keys-and-secret-access-keys)</td>
		<td>NULL</td>
		<td>gkhcvdgvceUYF67897hbjsbdc</td>
	</tr>
		<tr>
		<td>secret_key</td>
		<td>Secret key for Amamzon S3. More info [here](#https://docs.aws.amazon.com/general/latest/gr/aws-sec-cred-types.html#access-keys-and-secret-access-keys)</td>
		<td>NULL</td>
		<td>gkhcvdgvceUYF67897hbjsbdc</td>
	</tr>
		<tr>
		<td>s3_local_storage</td>
		<td>Directory for downloading artefacts</td>
		<td>.</td>
		<td>./s3</td>
	</tr>
		<tr>
		<td>s3_screenshot_bucket_name</td>
		<td>Bucket name on S3 Amamzon for saving report screenshots</td>
		<td>NULL</td>
		<td>Screenshot bucket name</td>
	</tr>
		<tr>
		<td>s3_save_screenshots</td>
		<td>If enabled screenshots would be saved on S3 Amamzon</td>
		<td>false</td>
		<td>false, true</td>
	</tr>
		<tr>
		<td>hockeyapp_token</td>
		<td>Token for authentication in Hockey App</td>
		<td>NULL</td>
		<td>gkhcvdgvceUYF67897hbjsbdc</td>
	</tr>
		<tr>
		<td>hockeyapp_local_storage</td>
		<td>Directory for Hockey App artefacts</td>
		<td>.</td>
		<td>./hockeyapp</td>
	</tr>
		<tr>
		<td>add_new_localization</td>
		<td>Should be set to 'true' if you want to create new localization files for required Locale.Otherwise there will be just localization checking</td>
		<td>false</td>
		<td>false, true</td>
	</tr>
		<tr>
		<td>add_new_localization_encoding</td>
		<td>Encoding for new lokalization</td>
		<td>utf-8</td>
		<td>utf-16, utf-32</td>
	</tr>
		<tr>
		<td>add_new_localization_path</td>
		<td>Path where created localization properties should be saved. If null - they will be added to artifactory folder in report</td>
		<td>utf-8</td>
		<td>utf-16, utf-32</td>
	</tr>
		<tr>
		<td>add_new_localization_property_name</td>
		<td>Path where created localization properties should be saved. If null - they will be added to artifactory folder in report</td>
		<td>new_custom_messages</td>
		<td>the basic template for property name. There will be locale added at the end of the filename.</td>
	</tr>
		<tr>
		<td>cucumber_tests</td>
		<td>If parametrs contains true Cucumber tests will be started</td>
		<td>n/a</td>
		<td>true, false</td>
	</tr>
		<tr>
		<td>cucumber_tests_app_version</td>
		<td>Version of app using in Cucumber tests</td>
		<td>n/a</td>
		<td>2.013</td>
	</tr>
		<tr>
		<td>cucumber_tests_name</td>
		<td>Cucucmber tests name</td>
		<td>n/a</td>
		<td>cucumber tests</td>
	</tr>
		<tr>
		<td>cucumber_tests_results_image_resize</td>
		<td>Percent of Scaling from default image. 100 - same size</td>
		<td>30</td>
		<td>Integer</td>
	</tr>
		<tr>
		<td>cucumber_report_subfolder</td>
		<td>Subfolder on Jenkins (jenkins_url/{someNumbersFr EveryRun}/artifacts/CucumberReport/{cucumber_report_subfolder})</td>
		<td>cucumber-html-reports</td>
		<td>cucumber-reports</td>
	</tr>
		<tr>
		<td>cucumber_user_js_in_report</td>
		<td>Enabled parameter help to get more beautiful reports</td>
		<td>true</td>
		<td>true, false</td>
	</tr>
		<tr>
		<td>tls_keysecure_location</td>
		<td>Path to directory with tls secure keys</td>
		<td>src/test/resources/keysecure</td>
		<td>./tls/keysecure</td>
	</tr>
		<tr>
		<td>health_check_class</td>
		<td>Class to execute helth checks</td>
		<td>NULL</td>
		<td>Custom class</td>
	</tr>
		<tr>
		<td>health_check_methods</td>
		<td>Comma-separate list of methods of health_check_class to execute preliminary</td>
		<td>NULL</td>
		<td>doThis, doThat</td>
	</tr>
		</tbody>
	</table>
Most of the properties may be read in the following way:
```
Configuration.get(Parameter.URL) // returns string value
Configuration.getBoolean(Parameter.AUTO_SCREENSHOT) // returns boolean value
Configuration.getInt(Parameter.SMALL_SCREEN_WIDTH) // returns integer value
Configuration.getDouble(Parameter.BROWSER_VERSION) // returns double value
```

### Environment specific configuration
In some cases it is required to support multiple environments for testing. Let's assume we have STAG and PROD environments which have different application URLs. In this case we need to specify the following properties in _config.properties:
```
env=PROD
STAG.url=http://stag-app-server.com
PROD.url=http://prod-app-server.com
```

And get env-specific argument in test the following way:
```
Configuration.getEnvArg("url")
```
As a result you switch between environments just changing env argument in _config.properties file.

### Execution
There are a several options to execute the test: you may run test suite from Eclipse IDE or initiate test execution from the console using Maven Surefire plugin built into Carina framework. Before running tests make sure you have downloaded Selenium standalone server jar file and started it by the following command:
```
java -jar selenium-server-standalone-3.6.0.jar
```

To run the test suite from Eclipse IDE, just select the required TestNG xml file: Right click > Run As > TestNG suite

![Execution from Eclipse IDE](https://raw.githubusercontent.com/qaprosoft/carina-demo/gh-pages/img/006-Configuration-and-execution.png)


To run the same test suite from the console, navigate to the test project root (where pom.xml is located) and execute the following command:

```
mvn clean -Dsuite=api test
```

## Web UI automation
In the table below we are providing description for most important WEB parameters:
<table> 
	<tr>
		<th>Attribute</th>
		<th>Meaning</th>
                <th>Default value</th>
		<th>Example</th>
	</tr>
	<tr>
		<td>browser</td>
		<td>Browser for testing</td>
		<td>chrome</td>
		<td>chrome / firefox / safari / iexplore</td>
	</tr>
	<tr>
		<td>selenium_host</td>
		<td>Selenium server host</td>
		<td>{must_override}</td>
		<td>http://localhost:4444/wd/hub</td>
	</tr>
	<tr>
		<td>driver_mode</td>
		<td>Rule for defining WebDriver lifecycle. </td>
		<td>method_mode</td>
		<td>method_mode / class_mode / suite_mode</td>
	</tr>
</table>

## Web services API automation
TODO

## Mobile automation
We could provide any Appium capabilty in **_config.properties** file using <i>capabilities.< name >=< value ></i> format. In the table below we are providing description for the most popular mobile capabilities:

<table> 
	<tr>
		<th>Attribute</th>
		<th>Meaning</th>
                <th>Default value</th>
		<th>Example</th>
	</tr>
        <tr>
		<td>capabilities.deviceName</td>
		<td>Device name for report</td>
                <td>n/a</td>
		<td>Sumsung_Galaxy_J5</td>
	</tr>
        <tr>
		<td>capabilities.deviceType</td>
		<td>The only custom carina capability to detmine type of device</td>
                <td>n/a</td>
		<td>phone/tablet/tv...</td>
	</tr>
        <tr>
		<td>capabilities.platformName</td>
		<td>Name of mobile platform</td>
                <td>n/a</td>
		<td>Android/iOS/AndroidTV/tvOS</td>
	</tr>
        <tr>
		<td>capabilities.platformVersion</td>
		<td>Version of mobile platform</td>
                <td>n/a</td>
		<td>6.0.1</td>
	</tr>
        <tr>
		<td>capabilities.automationName</td>
		<td>Name of programm using for automation (for Android 7+ use uiautomator2 instead of Appium)</td>
                <td>n/a</td>
		<td>Appium/uiautomator2/XCUITest</td>
	</tr>
        <tr>
		<td>capabilities.app</td>
		<td>Path to application (apk/app/ipa) which is tested, Can be provided as a pattern from AWS S3 storage with automatic downloading</td>
                <td>n/a</td>
		<td>D:/application.apk, s3://qaprosoft.com/android/myapk.*-release.apk</td>
	</tr>
        <tr>
		<td>capabilities.newCommandTimeout</td>
		<td>New implicit timeout in seconds to wait for element for mobile automation</td>
                <td>n/a</td>
		<td>180</td>
	</tr>
        <tr>
		<td>capabilities.udid</td>
		<td>Unique Device ID</td>
                <td>n/a</td>
		<td>759b543c</td>
	</tr>
        <tr>
		<td>capabilities.device</td>
		<td>Specifies a particular mobile device for the test environment on Browserstack</td>
                <td>n/a</td>
		<td>Galaxy Note 8, iPhone X</td>
	</tr>
	 <tr>
		<td>capabilities.os_versione</td>
		<td>Version of OS for Browserstack</td>
                <td>n/a</td>
		<td>ios, android</td>
	</tr>
	<tr>
		<td>capabilities.remoteURL</td>
		<td>Remote URL for using Selenium Grid</td>
                <td>n/a</td>
		<td> 'http://localhost:4444/wd/hub'</td>
	</tr>
		<tr>
		<td>capabilities.appActivity</td>
		<td>Activity name for the Android activity you want to launch from your package.</td>
                <td>n/a</td>
		<td>com.amazon.mShop.home.HomeActivity</td>
	</tr>
		<tr>
		<td>capabilities.appPackage</td>
		<td>Java package of the Android app you want to run</td>
                <td>n/a</td>
		<td>in.amazon.mShop.android.shopping</td>
	</tr>
	<tr>
		<td>capabilities.noSign</td>
		<td>Skip checking and signing of app with debug keys, will work only with UiAutomator and not with selendroid, default false</td>
                <td>n/a</td>
		<td>true, false</td>
	</tr>
	<tr>
		<td>capabilities.autoGrantPermissions</td>
		<td>Have Appium automatically determine which permissions your app requires and grant them to the app on install. Defaults to false</td>
                <td>n/a</td>
		<td>true, false</td>
	</tr>
		<tr>
		<td>Skip the device unlock process. Defaults to false</td>
                <td>n/a</td>
		<td>true, false</td>
	</tr>
</table>

### For Android:
```
#=============== Android Mobile ======================#
capabilities.deviceName=Samsung_Galaxy_J5
capabilities.app=s3://qaprosoft.com/android/myapk.*-release.apk
capabilities.skipUnlock=true
capabilities.noSign=true
capabilities.automationName=uiautomator2
capabilities.newCommandTimeout=180
capabilities.platformName=ANDROID
capabilities.autoGrantPermissions=true
#=====================================================#
```

### For iOS:
```
#=================== iOS Mobile ======================#
capabilities.autoAcceptAlerts=true
capabilities.app=/opt/apk/my-apk.app
capabilities.automationName=XCUITest
capabilities.newCommandTimeout=180
capabilities.platformName=IOS
#=====================================================#
```

### ExtendedWebElement

ExtendedWebElement is an extended version of selenium WebElement which you can find in org.openqa.selenium package. The best thing in using ExtendedWebElement is that you can use both old methods of WebElement and new more convenient Carina methods.

The easiest way to find ExtendedWebElement is using annotation @FindBy. The @FindBy annotation is used to locate one or more ExtendedWebElements using a single criterion. The list of criteria is standard:
* className
* css
* how...using
* id
* linkText
* name
* partialLinkText
* tagName
* xpath

```   
    @FindBy(name = "Hybrid")
    private ExtendedWebElement hybridUnits;

    @FindBy(id = "com.ua.record.debug:id/spinner_text")
    private List <ExtendedWebElement> unitsVersions;
```   

### ExtededWebElement's methods
Most usable methods are reperesented in the table bellow:

<table>
   <tr>
      <th>Method</th>
      <th>Return type</th>
      <th>Description</th>
   </tr>
   <tr>
      <td>getName()</td>
      <td>String</td>
      <td>Get the name of this element</td>
   </tr>
   <tr>
      <td>getText()</td>
      <td>String</td>
      <td>Get the visible innerText of this element</td>
   </tr>
   <tr>
      <td>getAttribute()</td>
      <td>String</td>
      <td>Get the value of a the given attribute of this element</td>
   </tr>
   <tr>
      <td>click()</td>
      <td>void</td>
      <td>Click on element</td>
   </tr>
   <tr>
      <td>doubleClick()</td>
      <td>void</td>
      <td>Double click on element</td>
   </tr>
   <tr>
      <td>isElementPresent()</td>
      <td>boolean</td>
      <td>Is element present or not?</td>
   </tr>
   <tr>
      <td>isElementPresent(long timeout)</td>
      <td>boolean</td>
      <td>Is element present or not during the timeout in seconds?</td>
   </tr>
   <tr>
      <td>isElementNotPresent(long timeout)</td>
      <td>boolean</td>
      <td>Is element not present during the timeout in seconds?</td>
   </tr>
   <tr>
      <td>isElementWithTextPresent(String text)</td>
      <td>boolean</td>
      <td>Is element with text present or not?</td>
   </tr>
   <tr>
      <td>isElementWithTextPresent(String text, long timeout)</td>
      <td>boolean</td>
      <td>Is element with text present or not during the timeout in seconds?</td>
   </tr>
   <tr>
      <td>clickIfPresent</td>
      <td>boolean</td>
      <td>Click on element if it's presented, return true if click is performed</td>
   </tr>
   <tr>
      <td>type(String text)</td>
      <td>void</td>
      <td>Clear the value of field and simulate typing the text</td>
   </tr>
   <tr>
      <td>scrollTo()</td>
      <td>void</td>
      <td>Scroll page until the element could be located</td>
   </tr>
   <tr>
      <td>check()</td>
      <td>void</td>
      <td>If element is checkable it will be checked</td>
   </tr>
   <tr>
      <td>uncheck()</td>
      <td>void</td>
      <td>If element is checkable it will be unchecked</td>
   </tr>
   <tr>
      <td>isCheck()</td>
      <td>boolean</td>
      <td>If element is checkable return is the element checked or not </td>
   </tr>
   <tr>
      <td>tapWithCoordinates(double x, double y)</td>
      <td>void</td>
      <td>Tap on screen using the given cordinates </td>
   </tr>
</table>

### How to use WebDriver methods?
You can simply transform ExtendedWebElement to WebElement using getElement() method. After this it's possible to operate with standard WebElement methods.
```   
   Point point = element.getElement().getLocation();
   Dimension dim = element.getElement().getSize();
```   

## Database access setup
TODO

## Data providers usage
TODO

## Git configuration 
1). **Fork repository** `https://github.com/qaprosoft/carina` to your own user.

2). **Clone your fork to your local machine**:

 `git clone git@github.com:your_fork_url/carina.git`

3). `git remote add origin <your_fork_url>` (can be already added)

4). `git fetch origin`

5). `git remote add upstream git@github.com:qaprosoft/carina.git`

6). `git fetch upstream`

7). `git checkout -b work_local_branch upstream/master`

And then after adding files (`git add` ...) use `git commit` (add description) and then`push`:

    git push origin work_local_branch:work_remote_branch
    
And on [https://github.com/qaprosoft/carina](https://github.com/qaprosoft/carina) you will see possibility to "Compare & Pull Request"

## Code formatter
We propose to use our configured java code formatter for Eclipse. 
It is available here [https://github.com/qaprosoft/carina/carina_formatter.xml](https://github.com/qaprosoft/carina/carina_formatter.xml)

To use same formatter in IntelliJ IDEA you should install[**Eclipse Code Formatter**](https://plugins.jetbrains.com/plugin/6546-eclipse-code-formatter)

And configure it.

## License
Code - [Apache Software License v2.0](http://www.apache.org/licenses/LICENSE-2.0)

Documentation and Site - [Creative Commons Attribution 4.0 International License](http://creativecommons.org/licenses/by/4.0/deed.en_US)

## Base flow of web test implementation
<b>Important!</b> If you want to create web automation test and run it the first thing to do is start Selenium server. [More information](https://www.seleniumhq.org/docs/05_selenium_rc.jsp) 

For example let's explain simple [test - testCompareModels] (https://github.com/qaprosoft/carina-demo/blob/master/src/test/java/com/qaprosoft/carina/demo/WebSampleTest.java) from Carina-deno step by step. 

1) Annotations. You have to use standart TestNG [annotations] (http://testng.org/doc/documentation-main.html#annotations) like @Test and can use custom Carina annotations like @Owner - annotation used to specify the TestNG methods owners. Each method caan have two owners @Owner(owner = "qpsowner", secondaryOwner="qpsowner2"). This feature is for mobile automation mostly and very convenient when different people implement one method for different platforms (ios and Android). Owner name will be in report.

2) HomePage homePage = new HomePage(getDriver());
Carina support and stromgly recomended to use [PageObject Pattern](https://www.seleniumhq.org/docs/06_test_design_considerations.jsp#page-object-design-pattern)
Each page have to implement AbstractPage from Carina (com.qaprosoft.carina.core.gui). This class provade some basic operations like openUrl, isPageOpened, savePageAsPdf.
On HomePage all necessary [ExtendedWebElements](#ExtendedWebElement) and methods are discribed.

3) Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
Assertions verify that the state of the application is same to what we are expecting. If Home page isn't opened there is no sense to continue the test. So if assertation isn't try test will be stoped. Message "Home page is not opened" will be in logs to better understanding of situation. Also TestNG provide different kinds of Assert like assertFalse and assertEquals. If you just want to know is assertation true or false but DON'T want to stop test you can use SoftAssert. It will show all failed asserts in the end of test. 


