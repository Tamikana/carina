package com.qaprosoft.carina.core.foundation.zafira;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.utils.Configuration.Parameter;
import com.qaprosoft.zafira.client.ZafiraClient;
import com.qaprosoft.zafira.client.ZafiraClient.Response;
import com.qaprosoft.zafira.client.model.JobType;
import com.qaprosoft.zafira.client.model.TestCaseType;
import com.qaprosoft.zafira.client.model.TestRunType;
import com.qaprosoft.zafira.client.model.TestRunType.Initiator;
import com.qaprosoft.zafira.client.model.TestSuiteType;
import com.qaprosoft.zafira.client.model.TestType;

public class ZafiraClientTest
{
	private static final boolean ENABLED = false;
	
	private static final ZafiraClient zc = new ZafiraClient(Configuration.get(Parameter.ZAFIRA_SERVICE_URL));
	
	@Test(enabled=ENABLED)
	public void testCreateJob()
	{
		// name:R, jobURL:R, jenkinsHost:R, userId:R
		JobType job = new JobType("zafira-ws", "http://stg.caronfly.com:8081/view/zafira/job/zafira-ws", "http://stg.caronfly.com:8081", 1L);
		Response<JobType> response = zc.createJob(job);
		Assert.assertEquals(response.getStatus(), 200);
	}
	
	@Test(enabled=ENABLED)
	public void testCreateTestSuite()
	{
		// name:R, description:NR, userId:R
		TestSuiteType testSuite = new  TestSuiteType("sanity", 1L);
		Response<TestSuiteType> response = zc.createTestSuite(testSuite);
		Assert.assertEquals(response.getStatus(), 200);
	}
	
	@Test(enabled=ENABLED)
	public void testCreateTestRunByHUMAN()
	{
		// testSuiteId:R, userId:R, scmURL:NR, scmBranch:NR, scmRevision:NR, configXML:NR, jobId:R, buildNumber:R, startedBy:R, workItemJiraId:NR
		TestRunType testRun = new TestRunType(1L, 1L, "http://localhost:8081", "master", "sdfs232fs3rwf34f5", "<config></config>", 1L, 10, Initiator.HUMAN, "JIRA-1234");
		Response<TestRunType> response = zc.createTestRun(testRun);
		Assert.assertEquals(response.getStatus(), 200);
	}
	
	@Test(enabled=ENABLED)
	public void testCreateTestRunByUPSTREAM_JOB()
	{
		// testSuiteId:R, scmURL:NR, scmBranch:NR, scmRevision:NR, configXML:NR, jobId:R, upstreamJobId:R, upstreamJobBuildNumber:R, buildNumber:R, startedBy:R, workItemJiraId:NR
		TestRunType testRun = new TestRunType(1L, "http://localhost:8081", "master", "sdfs232fs3rwf34f5", "<config></config>", 1L, 1L, 20, 11, Initiator.UPSTREAM_JOB, "JIRA-1234");
		Response<TestRunType> response = zc.createTestRun(testRun);
		Assert.assertEquals(response.getStatus(), 200);
	}
	
	@Test(enabled=ENABLED)
	public void testCreateTestRunBySCHEDULER()
	{
		// testSuiteId:R, scmURL:NR, scmBranch:NR, scmRevision:NR, configXML:NR, jobId:R, buildNumber:R, startedBy:R, workItemJiraId:NR
		TestRunType testRun = new TestRunType(1L, "http://localhost:8081", "master", "sdfs232fs3rwf34f5", "<config></config>", 1L, 30, Initiator.SCHEDULER, "JIRA-1234");
		Response<TestRunType> response = zc.createTestRun(testRun);
		Assert.assertEquals(response.getStatus(), 200);
	}
	
	@Test(enabled=ENABLED)
	public void testFinishTestRun()
	{
		Response<TestRunType> response = zc.finishTestRun(4);
		Assert.assertEquals(response.getStatus(), 200);
	}

	@Test(enabled=ENABLED)
	public void testCreateTest()
	{
		// TODO: work items not created yet
		// name:R, status:R, testArgs:NR, testRunId:R, testCaseId:R, message:NR, startTime:NR, finishTime:NR, demoURL:NR, logURL:NR, workItems:NR
		TestType test = new TestType("Zafira login test", com.qaprosoft.zafira.client.model.TestType.Status.PASSED, "<config></config>", 1L, 1L, "Hello!", new Date().getTime(), new Date().getTime(), "http://localhost:8081/demo", "http://localhost:8081/log", new String[]{"JIRA-1234"});
		Response<TestType> response = zc.createTest(test);
		Assert.assertEquals(response.getStatus(), 200);
	}
	
	@Test(enabled=ENABLED)
	public void testCreateTestCase()
	{
		// testClass:R, testMethod:R, info:NR, testSuiteId:R, userId:R
		TestCaseType testCase = new TestCaseType("com.qaprosoft.zofira.Test", "testLogin", "Hello!", 1L, 1L);
		Response<TestCaseType> response = zc.createTestCase(testCase);
		Assert.assertEquals(response.getStatus(), 200);
	}
	
	@Test(enabled=ENABLED)
	public void testCreateTestCases()
	{
		// testClass:R, testMethod:R, info:NR, testSuiteId:R, userId:R
		TestCaseType[] testCases = new TestCaseType[] {new TestCaseType("com.qaprosoft.zofira.Test", "testLogin", "Hello!", 1L, 1L), new TestCaseType("com.qaprosoft.zofira.Test", "testLogout", "Hello!", 1L, 1L)};
		Response<TestCaseType[]> response = zc.createTestCases(testCases);
		Assert.assertEquals(response.getStatus(), 200);
	}
}