# velocity-allowlist

[![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)
[![GitHub](https://img.shields.io/github/license/self-crafted/velocity-allowlist?style=flat-square&color=b2204c)](https://github.com/self-crafted/velocity-allowlist/blob/master/LICENSE)
[![GitHub Repo stars](https://img.shields.io/github/stars/self-crafted/velocity-allowlist?style=flat-square)](https://github.com/self-crafted/velocity-allowlist/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/self-crafted/velocity-allowlist?style=flat-square)](https://github.com/self-crafted/velocity-allowlist/network/members)
[![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/self-crafted/velocity-allowlist?style=flat-square)](https://github.com/self-crafted/velocity-allowlist/releases/latest)
[![GitHub all releases](https://img.shields.io/github/downloads/self-crafted/velocity-allowlist/total?style=flat-square)](https://github.com/self-crafted/velocity-allowlist/releases)

Dead simple player allowlist for Minecraft Velocity proxy.

velocity-allowlist is built for my private network, so it may or may not fit your needs.
It adds no commands to interface with it. 
All attempts on joining the proxy are blocked, unless the players UUID is in a textfile named `allowlist.txt`.

## Table of Contents

- [Install](#install)
- [Usage](#usage)
- [Maintainers](#maintainers)
- [Contributing](#contributing)
- [License](#license)

## Install
You could either just download a [release](https://github.com/self-crafted/velocity-allowlist/releases) or you compile the server yourself using the following commands under Linux
```shell
git clone https://github.com/self-crafted/velocity-allowlist.git
cd velocity-allowlist
./gradlew build
```
The server jar will be located at `build/libs/velocity-allowlist-<VERSION>.jar`.

Note that for compiling you need to use a JDK 17.

## Usage
To use this plugin you need to run Java 17.
The file `allowlist.txt` is read every 10 seconds. So simply drop the UUIDs of friends and family in there.
You can write comments starting the line with `#` or `//`.
Player names can't be used. The plugin will accept UUIDs only.

```
# valid comment
<valid UUID>
// another valid comment
  <valid UUID>
```

## Maintainers

[@offby0point5](https://github.com/offby0point5)

## Contributing

PRs accepted.

Small note: If editing the README, please conform to the [standard-readme](https://github.com/RichardLitt/standard-readme) specification.

## License

This project is licensed under the [MIT license](LICENSE).
