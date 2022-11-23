package com.robothy.platform.release.gradle.task;

import java.util.Set;
import org.gradle.api.Project;
import org.gradle.api.tasks.Exec;
import org.gradle.api.tasks.Input;

/**
 * Release task.
 */
public class ReleaseTask extends Exec {


  @Input
  private String commitUserName = "Github Release Plugin";

  @Input
  private String commitUserEmail = "bot@github.com";

  public String getCommitUserName() {
    return commitUserName;
  }

  public String getCommitUserEmail() {
    return commitUserEmail;
  }

  /**
   * Set dependencies of the ReleaseTask.
   */
  public ReleaseTask() {
    Project project = getProject();
    project.afterEvaluate(this::configureTaskDependencies);
    project.getSubprojects().forEach(subproject -> subproject.afterEvaluate(this::configureTaskDependencies));
  }

  protected void configureTaskDependencies(Project project) {
    Set<String> dependencies = Set.of(ReleaseTaskNames.NEXT_SNAPSHOT_VERSION.getName(),
        ReleaseTaskNames.GET_GIT_WORKING_BRANCH.getName(),
        "publish", "publishPlugins");
    project.getTasks().stream().filter(it -> dependencies.contains(it.getName()))
        .forEach(this::dependsOn);
  }

}
