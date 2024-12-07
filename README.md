# Mojango

Mojang API client for Java and Kotlin developers.

## Configure

Set the time to store the request in the cache.
However, please note that the unit is milliseconds.

The default value is the 5 minutes (=`1000 * 60 * 5`).

```java
// Check your current ttl (Time To Live)
Mojango.getTtl();

// Change the ttl (Time To Live)
Mojango.setTtl(1000 * 60);
```

Set a limit on requests per minute.
The default value is the API rate (60 tunes/min).

```java
// Check your current rate limit
Mojango.getRateLimit();

// Change the rate limit
Mojango.setRateLimit(64);
```

## Usage

First, let's request a Minecraft account.

`com.tksimeji.mojango.MinecraftAccount` for the same account is guaranteed to always be unique.
Also, if the values attached to the instance are updated,
there is no need to retrieve the instance again.

```java
// Get by UUID
MinecraftAccount account = Mojango.account(UUID.fromString("ee54c324-9ab4-472e-aa4d-392f15b820fb"));

// Get by name
MinecraftAccount account = Mojango.account("tksimeji");
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
