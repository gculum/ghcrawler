# GhCrawler
Tool for fetching GitHub repository info for a particular user

## Table of contents
- Introduction
- Requirements
- Recommended modules
- Installation
- Configuration
- Troubleshooting & FAQ
- Maintainers


## Introduction
GhCrawler project is used for fetching respository data related to the Github user whose login name has to be provided as an input
Forked repositories will be skipped, only basic information will be displayed, such as repository name, Owner, branch name, last commit.
Project is developed with limited functionalities. Authorization is not supported  

Project uses https://developer.github.com/v3 as a backing API

## Requirements
In case of execution on a machine it is needed to have Docker installed.

## Recommended modules
No optional modules are suggested..


## Installation
Application is available on Docker Hub.
install/run with Docker: docker run -p 8080:8080 --name gh_crawler_01 -d gculum/ghcrawler:1.0


## Configuration
Configuration is predefined within application itself, thus no additional steps are required here. 

## Troubleshooting & FAQ
In case of querying API intensively there might be a case that API calls are suspended for some time for the incoming IP. This can be verified by issuing
request https://api.github.com/users/<USER-NAME>/repos within a web browser or curl. Be sure to replace <USER-NAME> with legitimate Github login string

## Maintainers
### Current maintainers
- [Goran Ćulum]

