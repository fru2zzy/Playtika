import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;
import search.FailSearchEngineTest;
import search.ResultTest;
import servers.ClusterTest;
import servers.NodeTest;
import servers.ServerTest;

@RunWith(JUnitPlatform.class)
@SelectClasses({ClusterTest.class, ServerTest.class, NodeTest.class, GsonSerializerTest.class, FailSearchEngineTest.class, ResultTest.class})
public class AllTestsSuite {
}