<img src="icon.png" align="right" width="180px"/>

# API Provider draft

## What is the point ?
It's a way to define an API and get implementations of it from blocks, items, or anything else really.

This API defines the types for general api access, the building blocks for registring api accesses, and implementations for the most common use cases, i.e. blocks, items and entities.

## The solution
Every API access needs (1) minimum context for the query to function, (2) the api to query, and (3) additional context to provide.

For example, if you were to query a `FluidInsertable` from a block, (1) would be the world and the position, (2) would be an identifier for `FluidInsertable`, (3) would be the direction, or null if you don't want sided access.

If you were to query the `FluidInsertable` from an item stack, (1) would be the item stack, (2) would be the same identifier, and (3) would be a way to modify the current stack and somehow take care of the full buckets that could be produced.

1) The minimum context you need is encoded in a very rigid manner in the type system. For a block access, you will need to use `BlockApiLookupRegistry`. For an item stack access, you will need to use `ItemApiLookupRegistry`, etc... This API provides building blocks for building custom registries.
2) An `ApiKey` is the combination of an interface, and an extra identifier. For example, our fluid insertable identifier could be defined like this:
   ```java
   ApiKey<FluidInsertable> FLUID_INSERTABLE = ApiKey.create(FluidInsertable.class, new Identifier("mylib", "fluid_insertable"));
   ```
3) The extra context you need, `ContextKey`, is the combination of the context class for the extra context, and an extra identifier. It is very similar to an `ApiKey`:
   ```java
   /* An example interface that allows updating the current stack, and sending back filled buckets. */
   interface ExcessStacksContext {
       void setStack(ItemStack newStack);
       void sendExcessStack(ItemStack excessStack);
   }
   ContextKey<@NotNull Direction> SIDED = ContextKey.create(Direction.class, new Identifier("c", "sided"));
   ContextKey<@NotNull ExcessStacksContext> EXCESS_STACKS = ContextKey.create(ExcessStacksContext.class, new Identifier("mylib", "excess_stacks"));
   ```
   This Api will also provide a few common context, like `ContextKey<@Nullable Object> NO_CONTEXT` for example.

### Querying an API
Let's take our first example. We wish to query `FLUID_INSERTABLE` from a block in the world, using `SIDED` as context. The first step is getting a `BlockApiLookup` from the corresponding registry `BlockApiLookupRegistry` and caching it:
```java
BlockApiLookup<FluidInsertable, @NotNull Direction> LOOKUP = BlockApiLookupRegistry.get(FLUID_INSERTABLE, SIDED);
```
Now, we can query the api by providing the world, the position and the extra context (here `Direction`) to the Lookup:
```java
FluidInsertable fluidInsertable = LOOKUP.get(world, pos, direction);
```

So, (1) is encoded by the registry you are using, which returns a specific `ApiLookup` class. (2) and (3) are encoded in the `Lookup` instance you received from the registry.

### Registering an API
An _ApiProvider_ is a function that maps a context of type `C` to an implementation of an api `T`. For example, take a look at a `BlockApiProvider`:
```java
@FunctionalInterface
public interface BlockApiProvider<T, C> {
    @Nullable T get(World world, BlockPos pos, C context);
}
```

Now, let's register a sided `FluidInsertable` provider for a `FLUID_TRASH_CAN` block. We will use the `Lookup` we defined earlier:
```java
LOOKUP.registerForBlocks((world, pos, side) -> { /* return your instance of FluidInsertable */ }, FLUID_TRASH_CAN);
```

### Creating your own registry
TODO

For now have a look at the implementation for blocks.

## Questions
### Why does registering an `ApiKey` or a `ContextKey` need both a class and an `Identifier`?
It's pretty obvious that the class is needed for compile-time type checking.

The `Identifier` is also necessary because an API might be used with different semantics, see use case 2.

It should also be possible to expose multiple instances of a generic interface, which have the same `Class<>`, but need to have different `ApiKey`s.

Same answer for `ContextKey`s.