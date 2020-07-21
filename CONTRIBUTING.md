# Contributing

Thanks for taking time contributing to the project. When contributing to this repository, please first discuss the change you wish to make via issue,
email, or any other method with the owners of this repository before making a change.

Please note we have a [code of conduct](CODE_OF_CONDUCT.md), please follow it in all your interactions with the project.

All types of contributions are welcomed, including, but not limited to:
* Filing issues
* User experience design
* Writing documentation
* Testing the app
* Writing code

## Creating a Pull Request
Our team follows [the GitHub pull request workflow][gh workflow]: fork, branch, commit,
pull request, automated tests, review, merge. If you're new to GitHub, check out [the official
guides][gh guides] for more information.

An example commit message summary looks like, `For #5: Upgrade gradle to v1.3.0`.

Please follow these guidelines for your pull requests:

- All Pull Requests should address an issue. If your pull request doesn't have an
issue, file it!
  - GitHub search defaults to issues, not PRs, so ensuring there is an issue for your PR
  means it'll be easier to find
- The commit message summary should briefly describe what code changed in the commit, *not
the issue you're fixing.*
  - We encourage you to use the commit message body to elaborate what changed and why
- Include the issue number in your commit messages. This links your PR to the issue it's
intended to fix.
  - If your PR closes an issue, include `Closes #...` in one of your commit messages. This
  will automatically close the linked issue ([more info][auto close]).
  - If your PR has to go through a longer process, for example QA verification, use the 
  `For #...` syntax to allow the linked issue to be closed at a later, more appropriate time.
- Prefer "micro commits".
  - A micro commit is a small commit that generally changes one thing.
  A single Pull Request may comprise of multiple incremental micro commits.
  - A series of micro commits should tell a story. For example, if your goal is to add a new
  icon to the toolbar, you can make a commit to add the icon asset and then make a commit to
  use the icon in the code.
  - Commits should generally not undo the work of previous commits in the same PR.
  - If you're not comfortable making micro commits, it's okay to begin contributing without
  them.
- Add a reviewer to ensure someone sees, and reviews, your pull request so it can be merged
- If the tests fail, please try to fix them! Keeping the tests passing ensures our code isn't
broken and the code is unlikely to get merged without passing tests. If you run into trouble,
ask for help!
- If there are UI changes, include a screenshot so UX can also do a visual review
- When in doubt, look at the closed PRs in the repository to follow as an example or ask
us online!

If your code is not approved, address the suggested comments, push your changes, and re-request
review from your reviewer again.

## Style guide

### Git Commit Messages

* Use the present tense ("Add feature" not "Added feature")
* Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
* Limit the first line to 72 characters or less

### Architecture

The app uses [Model-View-ViewModel architecture][mvvm bluprint] pattern of Android. 
Always use the guidelines from the reposistory before making a changes in the code. This helps keep the code clean and adopt changes easily in the future.

[gh workflow]: https://guides.github.com/introduction/flow/
[gh guides]: https://guides.github.com/
[mvvm bluprint]: https://github.com/android/architecture-samples/tree/todo-mvvm-live-kotlin
