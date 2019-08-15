import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;
import servers.ClusterTest;
import servers.NodeTest;

@RunWith(JUnitPlatform.class)
@SelectClasses({ClusterTest.class, NodeTest.class})
public class AllTestsSuite {
}
