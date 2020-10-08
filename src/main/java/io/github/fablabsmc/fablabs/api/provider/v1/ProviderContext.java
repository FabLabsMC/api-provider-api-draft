/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.fablabsmc.fablabs.api.provider.v1;

import java.util.IdentityHashMap;
import java.util.Map;

import io.github.fablabsmc.fablabs.impl.provider.EmptyProviderContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import net.minecraft.util.math.Direction;

/**
 * Represents a context which is used when providing an api.
 * The provider context allows for specification of additional access parameters to discriminate between specific api instances on a game object based on specific additional and or optional parameters.
 *
 * <p>For users of an api wishing to resolve an instance, the provider context may be used to provide a more precise context in which the api instance should be resolved.
 * One common use of the provider context is to provide an api instance using the direction for context.
 * For example a block entity may only provide a certain api from the {@link Direction#SOUTH south}.
 * When trying to obtain an api instance from a block entity, typically via a world and block pos pair, you would use the overload with the {@link ProviderContext provider context} parameter.
 * To add the access direction to the parameter, you would simply {@link ProviderContext#with(Key, Object)} the context with the corresponding {@link AccessContextKeys#DIRECTION direction key} and {@link Direction direction value} you wish to access the api from.
 * <pre>{@code
 * Foo foo = ApiProviderRegistry.getFromBlock(MyApiKeys.FOO, world, pos, ProviderContext.empty().with(AccessContextKeys.DIRECTION, Direction.SOUTH));
 * }</pre>
 *
 * <p>For mod developers implementing an api onto game objects, the context may be optionally used to make the provision of api instances more precise.
 * Below is an example of how a mod developer could use the direction on the context to specify a more precise selection mechanism for api instances.
 * <pre>{@code
 * ApiProviderRegistry.registerForBlockEntity(MyApiKeys.FOO, (context, world, pos) -&gt; {
 *	if (context.has(AccessContextKeys.DIRECTION)) {
 *		// Get the instance taking direction into account
 *		return FooApiCaching.getWithDirection(world, pos, context.get(AccessContextKeys.DIRECTION));
 *	}
 *
 * 	// Get the instance regardless of direction
 * 	return FooApiCaching.get(world, pos);
 * });
 * }</pre>
 *
 * A mod developer is not required to consider the context either; a mod developer may ignore the context and just return an api instance regardless of specific context provided by a consumer of their api.
 */
@ApiStatus.NonExtendable
public /* sealed */ interface ProviderContext {
	/**
	 * Creates an empty access context.
	 *
	 * @return a new access context
	 */
	static ProviderContext empty() {
		return EmptyProviderContext.INSTANCE;
	}

	/**
	 * Resolves an access context key, creating it if it does not exist.
	 *
	 * @param type the class which represents the type of the key
	 * @param <V> the type of the key refers to
	 * @return an access context key
	 */
	static <V> ProviderContext.Key<V> key(Class<V> type) {
		//noinspection unchecked
		return (Key<V>) Key.KEY_INSTANCES.computeIfAbsent(type, k -> new Key<>());
	}

	<V> ProviderContext with(ProviderContext.Key<V> key, V value);

	/**
	 * Gets a value of an access context key on the context.
	 *
	 * @param key the key
	 * @param <V> the type of object
	 * @return the instance of the object, or null if an object obtained by the provided key is not on the context
	 */
	@Nullable
	<V> V get(ProviderContext.Key<V> key);

	final class Key<V> {
		private static final Map<Class<?>, Key<?>> KEY_INSTANCES = new IdentityHashMap<>();

		private Key() {
		}
	}
}
