# Git Workflow

This document defines the Git workflow and conventions used throughout the **event-driven-orders** project.

Its purpose is to keep the repository organized, maintain a clean and meaningful Git history, and establish a consistent development process.

---

# Table of Contents

- Purpose
- General Principles
- Main Branch
- Branch Naming
- Conventional Commits
- Development Workflow
- Merge Strategy
- Pull Requests
- Before Committing
- Summary

---

# Purpose

The project follows a lightweight Git workflow inspired by professional software development practices.

The goals are:

- Keep the Git history clean and easy to understand.
- Keep `main` always stable.
- Make every change easy to review.
- Use meaningful branch and commit names.
- Practice a professional workflow, even in a personal project.

---

# General Principles

The workflow follows a few simple principles.

- One branch = one objective.
- One commit = one logical idea.
- Keep `main` stable.
- Keep changes small and focused.
- Every decision should have a clear purpose.
- Git history should tell the story of the project.

---

# Main Branch

The main branch is:

```text
main
```

`main` must always remain stable because it represents the latest production-ready version of the project.

Development should not be performed directly on `main`, except for:

- Initial repository setup.
- Small documentation updates.
- Repository maintenance when appropriate.

Every completed feature should eventually be merged into `main`.

---

# Branch Naming

## Format

```text
<type>/<area>-<action>-<target>
```

Examples:

```text
feature/order-create-endpoint
feature/kafka-publish-order-created
feature/payment-consume-order-created

fix/order-validate-empty-items

test/order-add-service-tests

docs/add-git-workflow

build/maven-add-kafka-test
```

---

## Branch Types

| Type | Description |
|------|-------------|
| feature | New functionality |
| fix | Bug fix |
| refactor | Internal code changes without changing behavior |
| test | Tests |
| docs | Documentation |
| build | Maven, Docker or build tool changes |
| ci | Continuous Integration |
| chore | General maintenance |

---

## Branch Naming Rules

A branch name should clearly answer:

- What area is affected?
- What action is being performed?
- What is the objective?

Branch names should describe **the purpose of the work**, not implementation details.

### Good

```text
feature/order-create-endpoint

feature/kafka-add-retry-policy

feature/kafka-add-dead-letter-topic

fix/kafka-retry-invalid-backoff
```

### Bad

```text
feature/kafka

feature/new-kafka

feature/kafka-v2

feature/kafka-final

feature/testing

changes/update

fix/bugs
```

Avoid names such as:

```text
new
v2
final
test
fix2
changes
update
```

If you feel the need to append `v2`, `new`, or `final`, it usually means the branch is trying to solve more than one problem or the original name was too generic.

---

# Conventional Commits

## Format

```text
<type>(<scope>): <description>
```

The `scope` is optional and should only be used when it provides meaningful context.

### Recommended

```text
feat(order): add order creation endpoint

feat(kafka): publish OrderCreated event

test(payment): add payment service tests
```

### Also Valid

```text
docs: add Git workflow conventions

docs: update README

chore: initialize event-driven-orders repository

build: upgrade Spring Boot version
```

Avoid using a scope when it does not provide additional value.

---

## Commit Types

| Type | Description |
|------|-------------|
| feat | New functionality |
| fix | Bug fix |
| refactor | Internal code changes without changing behavior |
| test | Add or modify tests |
| docs | Documentation only |
| chore | General maintenance |
| build | Maven, Docker or dependency changes |
| ci | Continuous Integration |

---

## Commit Rules

- Use English.
- Use the imperative mood (`add`, `configure`, `publish`, `validate`, `remove`...).
- Do not end the message with a period.
- Keep the message concise.
- One commit should represent one logical idea.
- Do not commit broken code.
- Do not commit failing tests.
- Avoid vague commit messages.

### Good

```text
feat(order): add order creation endpoint

fix(kafka): handle null message key

docs: add Git workflow conventions
```

### Bad

```text
first commit

changes

update

fix

work in progress

feat: kafka and tests and docker
```

A good commit message should be understandable without opening the modified files.

---

# Commit Philosophy

Commits tell the story of the project.

A developer should be able to understand the evolution of the system simply by reading the Git history.

For this reason:

- Keep commits focused.
- Keep commits small.
- Keep commits meaningful.
- Avoid unrelated changes in the same commit.

---

# Development Workflow

Every change follows the same development flow.

```text
Issue
    ↓
Create Branch
    ↓
Develop
    ↓
TDD
    ↓
Self Review
    ↓
Commit
    ↓
Merge into main
```

Even though this is currently a personal project, the workflow intentionally mirrors a collaborative team environment.

---

# Merge Strategy

- Merge only completed work.
- Ensure the project builds successfully.
- Ensure all tests pass.
- Delete the feature branch after merging.
- Keep `main` clean and stable.

---

# Pull Requests

Every completed feature should be reviewed before being merged into `main`.

During the review, verify:

- Naming conventions
- Architecture
- Readability
- Maintainability
- Test coverage
- Documentation (when required)

Following a review process encourages better engineering practices and helps maintain project quality.

---

# Before Committing

Before creating a commit, answer the following questions:

1. What changed?
2. Why did it change?
3. Does this commit represent one logical idea?

If the answer to the third question is **no**, consider splitting the work into multiple commits.

---

# Summary

The philosophy of this repository can be summarized as follows:

- One branch = one objective.
- One commit = one logical idea.
- `main` is always stable.
- Every change should be understandable.
- Every decision should have a clear purpose.