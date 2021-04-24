**This action is under construction. I do not know a complete working kotlin parser that's publicly available, so this action will throw random errors for valid Kotlin files with documentation. Use at your own risk.**

# check-kdoc-present GitHub Action
A simple GitHub Action that runs a sanity check on your code to verify that every Kotlin file has at least one Kotlin documentation comment (kdoc) present. This helps keep your code documented and helps other developers looking at your code know what's going on.

## Getting Started
Assuming all your Kotlin source files are in the `~/src/main/kotlin` directory within the root of your repository, add the following to a new file `.github/workflows/verify_pr.yml`:

```yaml
name: Run PR Checks
on:
  pull_request:
    branches:
    - main

jobs:
  check_kdoc:
    name: Verify KDoc Present on all Kotlin Classes
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Verify KDoc Present on all Kotlin Classes
      uses: takanuva15/check-kdoc-present@main
```
Commit the file and create a PR to `main` - you should see the action run.

## Additional Usage Parameters
- `kotlin_dir`: The directory within your repository root that contains the Kotlin files you want to check
    - Default is `src/main/kotlin`

Sample usage:
```yaml
steps:
  - uses: actions/checkout@v2 #must checkout the repo for the action to have access to the Kotlin files
  - name: Verify KDoc Present on all Kotlin Classes
    uses: takanuva15/check-kdoc-present@main
    with:
      kotlin_dir: src/main/java
```

## Contributing
We are happy to take contributions. If you experience an issue or see something that can be improved, feel free to raise an issue. Assuming that there is agreement on the issue, fork the repo and raise a PR to make your change.

If you like this action, feel free to star/watch the repo to show your support!

## Credits
- Credit to [kotlinx/ast](https://github.com/kotlinx/ast) for providing the implementation of the kotlin parser.
