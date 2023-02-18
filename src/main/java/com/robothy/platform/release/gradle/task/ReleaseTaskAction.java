package com.robothy.platform.release.gradle.task;

import java.util.ArrayList;
import java.util.List;
import org.gradle.api.Action;
import org.gradle.api.logging.Logging;
import org.slf4j.Logger;

/**
 * Register release task.
 */
public class ReleaseTaskAction implements Action<ReleaseTask> {

  private static final Logger log = Logging.getLogger(ReleaseTaskAction.class);

  @Override
  public void execute(ReleaseTask exec) {
    exec.setWorkingDir(exec.getProject().getProjectDir());

    exec.doFirst(task -> {
      String version = exec.getProject().getVersion().toString();
      List<String> cmd = new ArrayList<>();
      cmd.add("git config user.name '" + exec.getCommitUserName() + "'");
      cmd.add("git config user.email '" + exec.getCommitUserEmail() + "'");
      tag(cmd, version);

      GetGitWorkingBranch gitWorkingBranchTask =
          (GetGitWorkingBranch) task.getProject().getTasks().findByName(ReleaseTaskNames.GET_GIT_WORKING_BRANCH.getName());
      commitNextVersion(cmd, exec.getProject().getRootProject().getName(), version, gitWorkingBranchTask.getWorkingBranch());
      exec.commandLine("sh", "-c", String.join(" && ", cmd));
    });
  }

  private void tag(List<String> cmd, String version) {
    cmd.add("git tag " + version);
    cmd.add("git push origin " + version);
  }

  private void commitNextVersion(List<String> cmd, String projectName, String version, String workingBranch) {
    cmd.add("git add ./gradle.properties");
    cmd.add("git commit -m '" + projectName + " " + version + " released.'");
    cmd.add("git push origin " + workingBranch);
  }

}
