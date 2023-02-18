# platform-release-gradle-plugin

This plugin contains a set of tasks to help with the release process of a Java project in GitHub repository.
You can find out these tasks in the `release` group.

## Usage

Apply this plugin in your `build.gradle` file:

```groovyplugins 
plugins {
    id 'java'
    id 'maven-publish'
    id 'com.robothy.github-repository-release-plugin' version '1.1'
}
```

Add `version` property to your `${rootProject}/gradle.properties` file as the first snapshot version of your project:

```properties
version=1.0-SNAPSHOT
```

Now you can update the versions in the `${rootProject}/gradle.properties` file via Gradle tasks.

+ `nextSnapshotVersion` - Update the version in `gradle.properties` to the next snapshot version. For example, 1.0-SNAPSHOT -> 1.1-SNAPSHOT, 1.0 -> 1.1-SNAPSHOT
+ `releaseVersion` - Update the version in `gradle.properties` number to a release version. For example, 1.0-SNAPSHOT -> 1.0, 1.0 -> 1.0 
+ `release` - The `release` task execute these works: 1. update to release version. 2. invoke `publish` or `publishPlugins` to publish artifacts. 3. Tag, commit, and push the changes in `gradle.properties` to the remote repository. 4. Update the version to the next snapshot version.

You can integrate these tasks in you CI/CD pipeline to automate the release process. One of the best practice of this plugin is this repository itself. It uses GitHub Actions as the CI tool. You can find out the release process in the [`.github/workflows/release.yml`](.github/workflows/release.yml) file. 

> A snapshot is a version number with a "-SNAPSHOT" suffix. It is a version number that is not yet released. For example, 1.0-SNAPSHOT. 
> On the contrary, a release version is a version number that without `-SNAPSHOT` suffix. For example, 1.0.