<img src="icon.png" align="right" width="180px"/>

# API Provider draft

## What is the point ?
It's a way to define an API and get implementations of it from blocks, items, or anything else really.

## Use cases
### 1. Fluid API
A Fluid API mod defines a `FluidInsertable` and a `FluidExtractable` interface, and exposes flexible access to them.

### 2. Vanilla Inventory (or any other class)
Some mod exposes access to the Vanilla `Inventory`, so that it can be retrieved in a more flexible way.

### 3. Generic I/O API
Some mod defines an `Insertable<T>` and an `Extractable<T>`, and exposes flexible access to them.

## The solution
### `ApiKey`
Every provided API is represented by a unique reference: an instance of the class `ApiKey`.

### Exposing an API with `ApiProvider`
To expose an API for a block, one needs to register a `BlockApiProvider` that will provide an API implementation from the context parameters:
```java
@FunctionalInterface
public interface BlockApiProvider<T> {
    @Nullable T get(World world, BlockPos pos, @NotNull Direction direction);
}
```

It is also possible to register a provider for a block entity:
```java
@FunctionalInterface
public interface BlockEntityApiProvider<T> {
    @Nullable T get(BlockEntity blockEntity, @NotNull Direction direction);
}
```

Then, one needs to grab the `ApiKey`, and register that with the provider and the block / block entities in the `ApiProviderRegistry`.

### Getting an API
To access an api in the world, one needs to grab the `ApiKey` first, then one can get an instance by calling `ApiProviderRegistry.getFromBlock(world, pos, direction)`.

## Questions
### Why does registering an `ApiKey` need both a class and an `Identifier`?
It's pretty obvious that the class is needed for compile-time type checking.

The `Identifier` is also necessary because an API might be used with different semantics, see use case 2.

It should also be possible to expose multiple instances of a generic interface, which have the same `Class<>`, but need to have different `ApiKey`s. See use case 3

### Why include `Direction` in the context?
Because we can expect that in most cases block accesses will have that information: accesses from a side machine/pipe, or click from an entity.
It would be silly to ask that every api implementor check for the `null` direction case without knowing in what context it will be used. If you want to provide a wireless access api, you can use a custom registry, see below.

### For block access, is it possible to provide more context than `World`, `BlockPos` and `Direction` ? Say I also want a `Color`.
Yes. TODO

### How can I expose an API for something other than blocks, items or entities? Say a rift from Dimensional Doors.
Use a custom registry. TODO

### For block access, is it possible to provide less context than `World`, `BlockPos` and `Direction` ?
You need to provide a `World` and a `BlockPos`, or you need a custom provider because `ApiProviderRegistry` won't work without that!

If you don't want to provide a `Direction`, the api implementor will need to know that because it's an unusual case.
So you should define a new api and write very clearly in the documentation that the direction will be some fixed value.