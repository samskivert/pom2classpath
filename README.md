# pom2classpath

Currently this tool just generates `<classpathentry>` elements for all of the
dependencies in one or more `pom.xml` files. You are left to merge those into
your existing `.classpath` file by hand.

Eventually, I'll add support for generating additional elements used by
`.classpath` based on the configuration in `pom.xml` as well as add support for
updating existing `.classpath` files in place.

The tool assumes you use `M2_REPO` as the Eclipse path variable indicating the
location of your local Maven installation. This seems to be moderately common
practice in the wild, but perhaps I'll eventually add an option for configuring
that as well.

Finally, I have yet to wire up the necessary build machinations to generate a
single executable jar file, so running the tool is not at all convenient as
well. So much to do!

## Example input and output

Given dependencies like so:

	<dependencies>
	  <dependency>
	    <groupId>commons-digester</groupId>
	    <artifactId>commons-digester</artifactId>
	    <version>1.8</version>
	    <scope>compile</scope>
	    <optional>true</optional>
	  </dependency>
	  <dependency>
	    <groupId>org.apache.ant</groupId>
	    <artifactId>ant</artifactId>
	    <version>1.7.1</version>
	    <scope>provided</scope>
	  </dependency>
	  <dependency>
	    <groupId>junit</groupId>
	    <artifactId>junit</artifactId>
	    <version>4.8.1</version>
	    <scope>test</scope>
	  </dependency>
	</dependencies>

You get output like so:

	<classpathentry sourcepath="M2_REPO/commons-digester/commons-digester/1.8/commons-digester-1.8-sources.jar" kind="var" path="M2_REPO/commons-digester/commons-digester/1.8/commons-digester-1.8.jar"></classpathentry>
	<classpathentry sourcepath="M2_REPO/org/apache/ant/ant/1.7.1/ant-1.7.1-sources.jar" kind="var" path="M2_REPO/org/apache/ant/ant/1.7.1/ant-1.7.1.jar"></classpathentry>
	<classpathentry sourcepath="M2_REPO/junit/junit/4.8.1/junit-4.8.1-sources.jar" kind="var" path="M2_REPO/junit/junit/4.8.1/junit-4.8.1.jar"></classpathentry>

Eventually I'll support filtering on scope, as well as checking whether source
files exist before adding them.
