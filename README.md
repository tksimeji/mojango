# Mojango

### Mojang API client for Java and Kotlin.

![Version](https://img.shields.io/badge/version-0.0.2-blue?style=flat-square)
![Licence](https://img.shields.io/badge/licence-MIT-red?style=flat-square)

![](./assets/c263de08-676b-423d-8126-19eb1aebbc90.png)

This is an unofficial 3rd party API client and is not affiliated with Mojang Studios or Microsoft.

## Installation

Mojango is available on Maven Central.
To add dependency using Gradle, write the following your `build.gradle` (Groovy DSL)

```groovy
dependencies {
    implementation 'com.tksimeji:mojango:x.y.z'
}
```

or in the `build.gradle.kts` file (Kotlin DSL)

```kotlin
dependencies {
    implementation("com.tksimeji:mojango:x.y.z")
}
```

To add dependency using Maven, write the following in `pom.xml`.

```xml
<dependency>
    <groupId>com.tksimeji</groupId>
    <artifactId>mojango</artifactId>
    <version>x.y.z</version>
</dependency>
```

## Configure

Set the time to store the request in the cache.
However, please note that the unit is milliseconds.

The default value is the 5 minutes (=`1000 * 60 * 5`).

```java
// Check your current ttl (Time To Live)
Mojango.INSTANCE.getTtl();

// Change the ttl (Time To Live)
Mojango.INSTANCE.setTtl(1000 * 60);
```

Set a limit on requests per minute.
The default value is the API rate (60 tunes/min).

```java
// Check your current rate limit
Mojango.INSTANCE.getRateLimit();

// Change the rate limit
Mojango.INSTANCE.setRateLimit(64);
```

## Usage

First, let's request a Minecraft account.

`com.tksimeji.mojango.MinecraftAccount` for the same account is guaranteed to always be unique.
Also, if the values attached to the instance are updated,
there is no need to retrieve the instance again.

```java
// Get by UUID
MinecraftAccount account = Mojango.INSTANCE.account(UUID.fromString("ee54c324-9ab4-472e-aa4d-392f15b820fb"));

// Get by name
MinecraftAccount account = Mojango.INSTANCE.account("tksimeji");
```

Next, use the account you obtained to carry out the process.

### Get the UUID

```java
account.getUniqueId();
```

### Get the account name
```java
account.getName();
```

### Get skin and cape.

If none of these are set, it returns null.

Additionally, instances representing the same skin and cape are guaranteed to be unique.

```java
Skin skin = account.getSkin();

skin.getUrl(); // Get the url of the skin image.
skin.getModel(); // Get the skin model.
skin.asImage(); // Get the skin as an image.
skin.isWide(); // Determine if the skin model is wide.
skin.isSlim(); // Determine if the skin model is slim.

Cape cape = account.getCape();

cape.getUrl(); // Get the url of the cape image.
cape.asIamge(); // Get the cape as an image.
```
