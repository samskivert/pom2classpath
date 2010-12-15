//
// $Id$

package com.samskivert.pom2classpath

import scala.xml.{Node, XML}

/**
 * The main entry point for the pom2classpath tool.
 */
object Main
{
  def main (args :Array[String]) {
    if (args.size == 0) {
      println("Usage: pom2classpath pom.xml [pom.xml ...]")
      exit(255)
    }

    args.map(XML.loadFile).flatMap(readDepends).foreach(d => println(d.toClasspathEntry))
  }

  case class Dependency (
    group :String, artifact :String, version :String, `type` :String, scope :String
  ) {
    def toClasspathEntry = <classpathentry kind="var" path={"M2_REPO/"+mavenPath}
      sourcepath={"M2_REPO/"+mavenSourcePath}/>
    def mavenPath = mavenDir + artifact + "-" + version + "." + `type`
    def mavenSourcePath = mavenDir + artifact + "-" + version + "-sources." + `type`
    protected def mavenDir = group.replace('.', '/') + "/" + artifact + "/" + version + "/";
  }

  protected def readDepends (pom :Node) = {
    for (dep <- pom \ "dependencies" \\ "dependency";
         val group = (dep \ "groupId").head.text;
         val artifact = (dep \ "artifactId").head.text;
         val version = (dep \ "version").head.text;
         val typ = (dep \ "type").headOption map(_.text) getOrElse("jar");
         val scope = (dep \ "scope").headOption map(_.text) getOrElse("compile"))
    yield Dependency(group, artifact, version, typ, scope)
  }
}
