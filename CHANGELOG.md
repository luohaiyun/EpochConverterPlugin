<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# IntelliJ Platform Plugin Template Changelog

## [Unreleased]
### Added
- JetBrains Plugin badges and TODO list for the end users

### Changed
- `pluginUntilBuild` set to the correct format: `201.*`

### Fixed
- GitHub Actions - Template Cleanup - fixed adding files to git
- Update Template plugin name on cleanup

## [0.1.0]
### Added
- `settings.gradle.kts` for the [performance purposes](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#always_define_a_settings_file)
- `#REMOVE-ON-CLEANUP#` token to mark content to be removed with **Template Cleanup** workflow

### Changed
- README proofreading
- GitHub Actions - Update IDE versions for the Plugin Verifier
- Update platformVersion to 2020.1.2

## [0.0.2]
### Added
- [Gradle Changelog Plugin](https://github.com/JetBrains/gradle-changelog-plugin) integration

### Changed
- Bump Detekt version
- Change pluginSinceBuild to 193

## [0.0.1]
### Added
- Initial project scaffold
- GitHub Actions to automate testing and deployment
- Kotlin support
