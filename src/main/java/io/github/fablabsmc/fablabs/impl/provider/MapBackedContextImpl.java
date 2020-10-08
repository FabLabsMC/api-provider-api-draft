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

package io.github.fablabsmc.fablabs.impl.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.github.fablabsmc.fablabs.api.provider.v1.ProviderContext;

final class MapBackedContextImpl implements ProviderContext {
	private final Map<ProviderContext.Key<?>, Object> values = new HashMap<>();

	MapBackedContextImpl() {
	}

	@Override
	public <V> ProviderContext with(ProviderContext.Key<V> key, V value) {
		if (this.values.putIfAbsent(key, value) != null) {
			throw new IllegalArgumentException("Cannot have multiple values of same provider key");
		}

		return this;
	}

	@Override
	public <V> V get(ProviderContext.Key<V> key) {
		Objects.requireNonNull(key, "Key cannot be null");
		//noinspection unchecked
		return (V) this.values.get(key);
	}
}
